/**
 * 
 */
package com.indocosmo.pos.data.providers.shopdb;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.sql.rowset.CachedRowSet;

import com.indocosmo.pos.common.PosEnvSettings;
import com.indocosmo.pos.common.PosLog;
import com.indocosmo.pos.common.beans.BeanReceiptTaxSummary;
import com.indocosmo.pos.common.enums.PosItemDisplayStyle;
import com.indocosmo.pos.common.enums.PosOrderServiceTypes;
import com.indocosmo.pos.common.utilities.PosCurrencyUtil;
import com.indocosmo.pos.common.utilities.PosDBUtil;
import com.indocosmo.pos.common.utilities.PosDateUtil;
import com.indocosmo.pos.common.utilities.PosImageUtils;
import com.indocosmo.pos.common.utilities.PosNumberUtil;
import com.indocosmo.pos.common.utilities.PosOrderUtil;
import com.indocosmo.pos.common.utilities.PosSaleItemUtil;
import com.indocosmo.pos.common.utilities.PosStringUtil;
import com.indocosmo.pos.common.utilities.PosTaxUtil;
import com.indocosmo.pos.data.beans.BeanChoice;
import com.indocosmo.pos.data.beans.BeanDiscount;
import com.indocosmo.pos.data.beans.BeanDiscount.EditTypes;
import com.indocosmo.pos.data.beans.BeanDiscountSummary;
import com.indocosmo.pos.data.beans.BeanItemDiscount;
import com.indocosmo.pos.data.beans.BeanOrderDetail;
import com.indocosmo.pos.data.beans.BeanOrderDetail.OrderDetailItemType;
import com.indocosmo.pos.data.beans.BeanOrderHeader.PosOrderStatus;
import com.indocosmo.pos.data.beans.BeanCurrency;
import com.indocosmo.pos.data.beans.BeanOrderDetailPS;
import com.indocosmo.pos.data.beans.BeanOrderHeader;
import com.indocosmo.pos.data.beans.BeanSaleItem;
import com.indocosmo.pos.data.beans.BeanSaleItemChoice;
import com.indocosmo.pos.data.beans.BeanSaleItemCombo;
import com.indocosmo.pos.data.beans.BeanSaleItemComboContent;
import com.indocosmo.pos.data.beans.BeanServingTable;
import com.indocosmo.pos.data.beans.BeanSubClass;
import com.indocosmo.pos.data.beans.BeanTax;
import com.indocosmo.pos.data.providers.shopdb.PosTaxItemProvider.TaxCalculationMethod;

/**
 * @author jojesh
 *
 */
public class PosOrderDtlProvider extends PosShopDBProviderBase {

	private PosSubItemClassProvider msubItemClassProvider;
	private BeanOrderDetailPS mOrderDetailStatement;
	private PosUOMProvider mUOMProvider;

	private PosServiceTableProvider mServiceTableProvider;
	private PosWaiterProvider mWaiterProvider;
	private PosOrderSplitDetailProvider mOrderSplitDetailProvider;

	private PosUsersProvider mUserProvider;
	
	private int mDetailIdCounter=0;
	
	private PreparedStatement mDetailItemInsPs;
	private PreparedStatement mDetailItemUpdPs;
	private BeanOrderHeader mOrderHeader;

	/**
	 * 
	 */
	public PosOrderDtlProvider() {
		super("order_id","v_order_dtls");

		msubItemClassProvider=PosSubItemClassProvider.getInstance();
		mServiceTableProvider=new PosServiceTableProvider();
		mWaiterProvider=new PosWaiterProvider();
		mOrderSplitDetailProvider=new PosOrderSplitDetailProvider();
		mUserProvider=new PosUsersProvider();
	}
	
	

	/**
	 * @param orderDetlList 
	 * @return
	 * @throws SQLException
	 */
	public void save(BeanOrderHeader orderHdrItem) throws Exception{
		
		PosLog.debug("Satrting to save order dtl...... ");

		final ArrayList<BeanOrderDetail> orderDetlList=orderHdrItem.getOrderDetailItems();
		
		initPreparedStatment();
		mDetailIdCounter=0;
		buildDetailPS(orderDetlList);
		executePS();
		
		PosLog.debug("Finished save order dtl...... ");
	}

	
	/**
	 * @throws SQLException
	 * Update/Insert the data to the DB
	 */
	private void executePS() throws SQLException{
		
		if(mDetailItemInsPs!=null)
			mDetailItemInsPs.executeBatch();
		
		if(mDetailItemUpdPs!=null)
			mDetailItemUpdPs.executeBatch();
			
	}

	
	/**
	 * Initializes the prepared statements
	 * @throws SQLException
	 */
	private void initPreparedStatment() throws SQLException{

		if(mDetailItemInsPs!=null && PosDBUtil.getInstance().isValidConnection(mDetailItemInsPs.getConnection())){
			mDetailItemInsPs.clearParameters();
		}else{
			mDetailItemInsPs=getConnection().prepareStatement(getInsertStatement());
		}
		
		if(mDetailItemUpdPs!=null && PosDBUtil.getInstance().isValidConnection(mDetailItemUpdPs.getConnection())){
			mDetailItemUpdPs.clearParameters();
		}else{
			mDetailItemUpdPs=getConnection().prepareStatement(getUpdateStatement());
		}
	}

	/**
	 * Create the SQL for insert records
	 * @return
	 */
	private String getInsertStatement(){

		final String fieldsNames="(" +
				"id,	order_id,	sale_item_id,	sale_item_code,	sub_class_id,	" +
				"sub_class_code,	sub_class_name,	name,	description,	alternative_name,	" +
				"name_to_print,	alternative_name_to_print,	qty,	is_open,	uom_code,	" +
				"uom_name,	uom_symbol,	is_combo_item,	fixed_price,	tax_calculation_method,	" +
				"tax_id,	tax_code,	tax_name,	is_tax1_applied,	tax1_name,	" +
				"tax1_pc,	tax1_amount,	is_tax2_applied,	tax2_name,	tax2_pc,	" +
				"tax2_amount,	is_tax3_applied,	tax3_name,	tax3_pc,	tax3_amount,	" +
				"is_sc_applied,	sc_name,	sc_pc,	sc_amount,	is_gst_applied,	" +
				"gst_name,	gst_pc,	is_tax1_included_in_gst,	is_tax2_included_in_gst,	is_tax3_included_in_gst,	" +
				"is_sc_included_in_gst,	gst_amount,	item_total,	discount_type,	discount_id,	" +
				"discount_code,	discount_name,	discount_description,	discount_price,	discount_is_percentage,	" +
				"discount_is_overridable,	discount_is_item_specific,	discount_permitted_for,	discount_amount,	discount_is_promotion,	" +
				"discount_grouping_quantity,	discount_allow_editing,	round_adjustment,	attrib1_name,	attrib1_options,	" +
				"attrib2_name,	attrib2_options,	attrib3_name,	attrib3_options,	attrib4_name,	" +
				"attrib4_options,	attrib5_name,	attrib5_options,	attrib1_selected_option,	attrib2_selected_option,	" +
				"attrib3_selected_option,	attrib4_selected_option,	attrib5_selected_option,	status,	is_printed_to_kitchen,	" +
				"remarks,	is_void,	cashier_id,	order_date,	order_time,	" +
				"customer_price_variance,	discount_variants,	service_type,	serving_table_id,	served_by,	" +
				"parent_dtl_id,	has_choices,	item_type,	sale_item_choices_id,	sale_item_choices_free_items,	" +
				"sale_item_choices_max_items,	sale_item_choices_choice_id,	sale_item_choices_choice_code,	sale_item_choices_choice_name,	sale_item_choices_choice_description,	" +
				"sale_item_choices_choice_is_global,	combo_content_id, combo_content_code,	combo_content_name,	combo_content_description,	combo_content_max_items," +
				"combo_content_uom_id, seat_no,kitchen_print_status, kitchen_id,tax_id_home_service,tax_id_table_service,tax_id_take_away_service," +
				"void_by,void_at,tray_weight,tray_code,rtls_price,whls_price,is_whls_price_pc,sale_item_hsn_code,sub_class_hsn_code,void_time)";

		final String values="(" +
				"?,	?,	?,	?,	?,	" +
				"?,	?,	?,	?,	?,	" +
				"?,	?,	?,	?,	?,	" +
				"?,	?,	?,	?,	?,	" +
				"?,	?,	?,	?,	?,	" +
				"?,	?,	?,	?,	?,	" +
				"?,	?,	?,	?,	?,	" +
				"?,	?,	?,	?,	?,	" +
				"?,	?,	?,	?,	?,	" +
				"?,	?,	?,	?,	?,	" +
				"?,	?,	?,	?,	?,	" +
				"?,	?,	?,	?,	?,	" +
				"?,	?,	?,	?,	?,	" +
				"?,	?,	?,	?,	?,	" +
				"?,	?,	?,	?,	?,	" +
				"?,	?,	?,	?,	?,	" +
				"?,	?,	?,	?,	?,	" +
				"?,	?,	?,	?,	?,	" +
				"?,	?,	?,	?,	?,	" +
				"?,	?,	?,	?,	?,	" +
				"?,	?,	?,	?,	?,  " +
				"?, ?,  ?,  ?,  ?,  " +
				"?, ?,  ?,  ?,  ?,  " +
				"?, ?,  ?,  ?,  ?,  " + 
				"?, ?, ?)";
		final String insert_sql = "insert into order_dtls " + fieldsNames + " values " +values;
		return insert_sql;
	}

	/**
	 * Creates the SQL for updating the records
	 * @return
	 */
	private String getUpdateStatement() {


		final String update_sql = "update order_dtls set " + "id=?,    order_id=?,    sale_item_id=?,    sale_item_code=?,  sub_class_id=?,    " +
				"sub_class_code=?, sub_class_name=?,  name=?,    description=?, alternative_name=?,    " +
				"name_to_print=?,  alternative_name_to_print=?,   qty=?, is_open=?, uom_code=?,    " +
				"uom_name=?,   uom_symbol=?,  is_combo_item=?,   fixed_price=?, tax_calculation_method=?,  " +
				"tax_id=?, tax_code=?,    tax_name=?,    is_tax1_applied=?, tax1_name=?,   " +
				"tax1_pc=?,    tax1_amount=?, is_tax2_applied=?, tax2_name=?,   tax2_pc=?, " +
				"tax2_amount=?,    is_tax3_applied=?, tax3_name=?,   tax3_pc=?, tax3_amount=?, " +
				"is_sc_applied=?,  sc_name=?, sc_pc=?,   sc_amount=?,   is_gst_applied=?,  " +
				"gst_name=?,   gst_pc=?,  is_tax1_included_in_gst=?, is_tax2_included_in_gst=?, is_tax3_included_in_gst=?, " +
				"is_sc_included_in_gst=?,  gst_amount=?,  item_total=?,  discount_type=?,   discount_id=?, " +
				"discount_code=?,  discount_name=?,   discount_description=?,    discount_price=?,  discount_is_percentage=?,  " +
				"discount_is_overridable=?,    discount_is_item_specific=?,   discount_permitted_for=?,  discount_amount=?, discount_is_promotion=?,   " +
				"discount_grouping_quantity=?, discount_allow_editing=?,  round_adjustment=?,    attrib1_name=?,    attrib1_options=?, " +
				"attrib2_name=?,   attrib2_options=?, attrib3_name=?,    attrib3_options=?, attrib4_name=?,    " +
				"attrib4_options=?,    attrib5_name=?,    attrib5_options=?, attrib1_selected_option=?, attrib2_selected_option=?, " +
				"attrib3_selected_option=?,    attrib4_selected_option=?, attrib5_selected_option=?, status=?,  is_printed_to_kitchen=?,   " +
				"remarks=?,    is_void=?, cashier_id=?,  order_date=?,  order_time=?,  " +
				"customer_price_variance=?,    discount_variants=?,   service_type=?,    serving_table_id=?,    served_by=?,   " +
				"parent_dtl_id=?,  has_choices=?, item_type=?,   sale_item_choices_id=?,    sale_item_choices_free_items=?,    " +
				"sale_item_choices_max_items=?,    sale_item_choices_choice_id=?, sale_item_choices_choice_code=?,   sale_item_choices_choice_name=?,   sale_item_choices_choice_description=?,    " +
				"sale_item_choices_choice_is_global=?, combo_content_id=?, combo_content_code=?,  combo_content_name=?,  combo_content_description=?,   combo_content_max_items=?, " +
				"combo_content_uom_id=?, seat_no=?, kitchen_print_status=?,kitchen_id=?, tax_id_home_service=?,tax_id_table_service=?,tax_id_take_away_service=?, " + 
				"void_by=?, void_at=?,tray_weight=?,tray_code=?,rtls_price=?,whls_price=?,is_whls_price_pc=?, " +
				"sale_item_hsn_code=?,sub_class_hsn_code=?,void_time=? "+ 
				" where id=?";
		
		return update_sql;
	}
	
//	/*
//	 * 
//	 */
//	public void voidOrderDetails(String orderId){
//		
//		 	
//		final String sql = "update order_dtls set is_void=1, void_by=" + PosEnvSettings.getInstance().getCashierShiftInfo().getCashierInfo().getId() +
//						",void_at='" + PosDateUtil.getDateTime() + "' where order_id='" + orderId + "'";
//		
//	 	try {
//			executeNonQuery(sql);
//		}  catch (SQLException e) {
//			PosLog.write(this,"void order details", e);
//		}
//		
//		
//	}
	
	private int getNumericIdFromOrderDetID(String orderDetId) {
		 
		int result=0;	
		String splitValues[]=orderDetId.split(PosOrderUtil.ORDER_ID_FILED_SEPERATOR) ;
			
		if(splitValues.length>0 )
			result=PosNumberUtil.parseIntegerSafely(splitValues[splitValues.length-1]);
		return result;	
	}
	/**
	 * Creates and update the details ID
	 * @param detlItem
	 */
	private void updateDetailID(BeanOrderDetail detlItem) {

		
		if (detlItem.getParentDtlId() == null || detlItem.getParentDtlId().equals("")) {
			String detailItemID;
			
//			detailItemID=   PosOrderUtil.appendToId(detlItem.getOrderId(), mDetailIdCounter++);
			if ( detlItem.isNewItem() ||  detlItem.getId()==null || detlItem.getId().trim().equals(""))
				detailItemID=   PosOrderUtil.appendToId(detlItem.getOrderId(), mDetailIdCounter);
			else {
				detailItemID=detlItem.getId();
				mDetailIdCounter=getNumericIdFromOrderDetID(detailItemID);
			}
			mDetailIdCounter++;
			detlItem.setId(detailItemID);
			int mSubItemCounter = 0;
			
			//Extras (choices)
			HashMap<String, ArrayList<BeanOrderDetail>> extrasList = detlItem.getExtraItemList();
			String detailSubItemID="";
			if (extrasList != null) {
				for (String key : extrasList.keySet()) {
					ArrayList<BeanOrderDetail> subItems = extrasList.get(key);
					if (subItems != null) {
						for (BeanOrderDetail dtlSubItem : subItems) {
							dtlSubItem.setParentDtlId(detailItemID);
							/*Set new id for sub item */
							detailSubItemID = PosOrderUtil.appendToId(dtlSubItem.getParentDtlId(), mSubItemCounter++);
							dtlSubItem.setId(detailSubItemID);
						}
					}
				}
			}
			//Combo contents
			HashMap<String, ArrayList<BeanOrderDetail>> csList = detlItem.getComboSubstitutes();
			if (csList != null) {
				for (String key : csList.keySet()) {
					ArrayList<BeanOrderDetail> subItems = csList.get(key);
					if (subItems != null) {
						for (BeanOrderDetail dtlSubItem : subItems) {
							dtlSubItem.setParentDtlId(detailItemID);
							/*Set new id for combo item */
							detailSubItemID = PosOrderUtil.appendToId(dtlSubItem.getParentDtlId(), mSubItemCounter++);
							dtlSubItem.setId(detailSubItemID);
						}
					}
				}
			}
		}


	}
	
	

	/**
	 * @param orderDetlList
	 * @throws Exception
	 */
	private void buildDetailPS(ArrayList<BeanOrderDetail> orderDetlList) throws Exception{

		PreparedStatement detailItemPs=null;
		
		for (BeanOrderDetail detlItem: orderDetlList){	

			final BeanSaleItem saleItemObject=detlItem.getSaleItem();
			final BeanTax itemTaxObject=saleItemObject.getTax();

			final BeanDiscount itemDiscount=saleItemObject.getDiscount();
			final BeanSubClass subClass=msubItemClassProvider.getClassItem(saleItemObject.getSubClassID());
//			final String detailId=PosOrderUtil.appendToId(detlItem.getOrderId(), mDetailIdCounter++);
			
			updateDetailID(detlItem);
			
            final boolean isExist = isExist("id='"+detlItem.getId()+"'");
            
            detailItemPs=((isExist)?mDetailItemUpdPs:mDetailItemInsPs);
            
			detailItemPs.setString(1, detlItem.getId());
			detailItemPs.setString(2, detlItem.getOrderId());
			detailItemPs.setInt(3, saleItemObject.getId());
			detailItemPs.setString(4, saleItemObject.getCode());
			if(subClass!=null){
				detailItemPs.setInt(5, subClass.getID());
				detailItemPs.setString(6, subClass.getCode());
				detailItemPs.setString(7, subClass.getName());
			}else{
				detailItemPs.setNull(5, java.sql.Types.INTEGER);
				detailItemPs.setNull(6, java.sql.Types.VARCHAR);
				detailItemPs.setNull(7, java.sql.Types.VARCHAR);
			}
			detailItemPs.setString(8, saleItemObject.getName());
			detailItemPs.setString(9, saleItemObject.getDescription());
			detailItemPs.setString(10, saleItemObject.getAlternativeName());
			detailItemPs.setString(11, saleItemObject.getNameToPrint());
			detailItemPs.setString(12, saleItemObject.getAlternativeNameToPrint());
			detailItemPs.setDouble(13, saleItemObject.getQuantity());
			detailItemPs.setBoolean(14, saleItemObject.isOpen());
			if(saleItemObject.getUom()!=null){
				detailItemPs.setString(15, saleItemObject.getUom().getCode());
				detailItemPs.setString(16, saleItemObject.getUom().getName());
				detailItemPs.setString(17, saleItemObject.getUom().getSymbol());
			}else{
				detailItemPs.setNull(15, java.sql.Types.VARCHAR);
				detailItemPs.setNull(16, java.sql.Types.VARCHAR);
				detailItemPs.setNull(17, java.sql.Types.VARCHAR);
			}
			detailItemPs.setBoolean(18, saleItemObject.isComboItem());
			detailItemPs.setDouble(19, saleItemObject.getFixedPrice());
			detailItemPs.setInt(20, saleItemObject.getTaxCalculationMethod().getCode());
			if(saleItemObject.getTaxCalculationMethod()!=TaxCalculationMethod.None   ){
				final PosTaxAmountObject taxAmount=itemTaxObject.getTaxAmount();
				detailItemPs.setInt(21, itemTaxObject.getId());
				detailItemPs.setString(22, itemTaxObject.getCode());
				detailItemPs.setString(23, itemTaxObject.getName());
				detailItemPs.setBoolean(24, itemTaxObject.isTaxOneApplicable());
				detailItemPs.setString(25, itemTaxObject.getTaxOneName());
				detailItemPs.setDouble(26, itemTaxObject.getTaxOnePercentage());
				detailItemPs.setDouble(27, taxAmount.getTaxOneAmount());
				detailItemPs.setBoolean(28, itemTaxObject.isTaxTwoApplicable());
				detailItemPs.setString(29, itemTaxObject.getTaxTwoName());
				detailItemPs.setDouble(30, itemTaxObject.getTaxTwoPercentage());
				detailItemPs.setDouble(31, taxAmount.getTaxTwoAmount());
				detailItemPs.setBoolean(32, itemTaxObject.isTaxThreeApplicable());
				detailItemPs.setString(33, itemTaxObject.getTaxThreeName());
				detailItemPs.setDouble(34, itemTaxObject.getTaxThreePercentage());
				detailItemPs.setDouble(35, taxAmount.getTaxThreeAmount());
				detailItemPs.setBoolean(36, itemTaxObject.isServiceTaxApplicable());
				detailItemPs.setString(37, itemTaxObject.getServiceTaxName());
				detailItemPs.setDouble(38, itemTaxObject.getServiceTaxPercentage());
				detailItemPs.setDouble(39, taxAmount.getServiceTaxAmount());
				detailItemPs.setBoolean(40, itemTaxObject.isGSTDefined());
				detailItemPs.setString(41, itemTaxObject.getGSTName());
				detailItemPs.setDouble(42, itemTaxObject.getGSTPercentage());
				detailItemPs.setBoolean(43, itemTaxObject.isTaxOneIncludeInGST());
				detailItemPs.setBoolean(44, itemTaxObject.isTaxTwoIncludeInGST());
				detailItemPs.setBoolean(45, itemTaxObject.isTaxThreeIncludeInGST());
				detailItemPs.setBoolean(46, itemTaxObject.isServiceTaxIncludeInGST());
				detailItemPs.setDouble(47, taxAmount.getGSTAmount());
			}else{

				detailItemPs.setNull(21, java.sql.Types.INTEGER);
				detailItemPs.setNull(22, java.sql.Types.VARCHAR);
				detailItemPs.setNull(23, java.sql.Types.VARCHAR);
				detailItemPs.setNull(24, java.sql.Types.BOOLEAN);
				detailItemPs.setNull(25, java.sql.Types.VARCHAR);
				detailItemPs.setNull(26, java.sql.Types.DOUBLE);
				detailItemPs.setNull(27, java.sql.Types.DOUBLE);
				detailItemPs.setNull(28, java.sql.Types.BOOLEAN);
				detailItemPs.setNull(29, java.sql.Types.VARCHAR);
				detailItemPs.setNull(30, java.sql.Types.DOUBLE);
				detailItemPs.setNull(31, java.sql.Types.DOUBLE);
				detailItemPs.setNull(32, java.sql.Types.BOOLEAN);
				detailItemPs.setNull(33, java.sql.Types.VARCHAR);
				detailItemPs.setNull(34, java.sql.Types.DOUBLE);
				detailItemPs.setNull(35, java.sql.Types.DOUBLE);
				detailItemPs.setNull(36, java.sql.Types.BOOLEAN);
				detailItemPs.setNull(37, java.sql.Types.VARCHAR);
				detailItemPs.setNull(38, java.sql.Types.DOUBLE);
				detailItemPs.setNull(39, java.sql.Types.DOUBLE);
				detailItemPs.setNull(40, java.sql.Types.BOOLEAN);
				detailItemPs.setNull(41, java.sql.Types.VARCHAR);
				detailItemPs.setNull(42, java.sql.Types.DOUBLE);
				detailItemPs.setNull(43, java.sql.Types.BOOLEAN);
				detailItemPs.setNull(44, java.sql.Types.BOOLEAN);
				detailItemPs.setNull(45, java.sql.Types.BOOLEAN);
				detailItemPs.setNull(46, java.sql.Types.BOOLEAN);
				detailItemPs.setNull(47, java.sql.Types.DOUBLE);

			}
			detailItemPs.setDouble(48, PosSaleItemUtil.getGrandTotal(saleItemObject));
			detailItemPs.setInt(49, detlItem.getDiscountType());
			if(itemDiscount!=null){
				detailItemPs.setInt(50, itemDiscount.getId());
				detailItemPs.setString(51, itemDiscount.getCode());
				detailItemPs.setString(52, itemDiscount.getName());
				detailItemPs.setString(53, itemDiscount.getDescription());
				detailItemPs.setDouble(54, itemDiscount.getPrice());
				detailItemPs.setBoolean(55, itemDiscount.isPercentage());
				detailItemPs.setBoolean(56, itemDiscount.isOverridable());
				detailItemPs.setBoolean(57, itemDiscount.isItemSpecific());
				detailItemPs.setInt(58, ((itemDiscount.getPermittedLevel()!=null)?
						itemDiscount.getPermittedLevel().getCode():1));
				detailItemPs.setDouble(59, PosSaleItemUtil.getTotalDiscountAmount(saleItemObject));
				detailItemPs.setBoolean(60, itemDiscount.isPromotion());
				//To do verify with db structure -->
				detailItemPs.setDouble(87, itemDiscount.getVariants());
				//<--
				detailItemPs.setDouble(61, itemDiscount.getRequiredQuantity());
				detailItemPs.setInt(62, itemDiscount.getEditType().getCode());
			}else{
				detailItemPs.setNull(50, java.sql.Types.INTEGER);
				detailItemPs.setNull(51, java.sql.Types.VARCHAR);
				detailItemPs.setNull(52, java.sql.Types.VARCHAR);
				detailItemPs.setNull(53, java.sql.Types.VARCHAR);
				detailItemPs.setNull(54, java.sql.Types.DOUBLE);
				detailItemPs.setNull(55, java.sql.Types.BOOLEAN);
				detailItemPs.setNull(56, java.sql.Types.BOOLEAN);
				detailItemPs.setNull(57, java.sql.Types.BOOLEAN);
				detailItemPs.setNull(58, java.sql.Types.INTEGER);
				detailItemPs.setNull(59, java.sql.Types.DOUBLE);
				detailItemPs.setNull(60, java.sql.Types.BOOLEAN);
				//To do verify with db structure -->
				detailItemPs.setNull(87, java.sql.Types.DOUBLE);
				//<--
				detailItemPs.setNull(61, java.sql.Types.DOUBLE);
				detailItemPs.setNull(62, java.sql.Types.INTEGER);
			}

			detailItemPs.setDouble(63, detlItem.getRoundAdjustment());
			detailItemPs.setString(64, saleItemObject.getAttributeName(0));
			detailItemPs.setString(65, PosStringUtil.getCommaSeperatedList(saleItemObject.getAttributeOptions(0)));
			detailItemPs.setString(66, saleItemObject.getAttributeName(1));
			detailItemPs.setString(67, PosStringUtil.getCommaSeperatedList(saleItemObject.getAttributeOptions(1)));
			detailItemPs.setString(68, saleItemObject.getAttributeName(2));
			detailItemPs.setString(69, PosStringUtil.getCommaSeperatedList(saleItemObject.getAttributeOptions(2)));
			detailItemPs.setString(70, saleItemObject.getAttributeName(3));
			detailItemPs.setString(71, PosStringUtil.getCommaSeperatedList(saleItemObject.getAttributeOptions(3)));
			detailItemPs.setString(72, saleItemObject.getAttributeName(4));
			detailItemPs.setString(73, PosStringUtil.getCommaSeperatedList(saleItemObject.getAttributeOptions(4)));
			detailItemPs.setString(74, saleItemObject.getAttribSelectedOption(0).trim());
			detailItemPs.setString(75, saleItemObject.getAttribSelectedOption(1).trim());
			detailItemPs.setString(76, saleItemObject.getAttribSelectedOption(2).trim());
			detailItemPs.setString(77, saleItemObject.getAttribSelectedOption(3).trim());
			detailItemPs.setString(78, saleItemObject.getAttribSelectedOption(4).trim());
			detailItemPs.setInt(79, detlItem.getStatus());
			detailItemPs.setBoolean(80, detlItem.isPrintedToKitchen());
			detailItemPs.setString(81, detlItem.getRemarks());

			detailItemPs.setBoolean(82, detlItem.isVoid());
			detailItemPs.setInt(83, detlItem.getCashierId());
			detailItemPs.setString(84, detlItem.getOrderDate());
			detailItemPs.setString(85, detlItem.getOrderTime());
			detailItemPs.setDouble(86, saleItemObject.getCustomerPrice());
			//87 discount variants in discount section
			detailItemPs.setInt(88, detlItem.getServiceType().getCode());
			if(detlItem.getServingTable()!=null)
				detailItemPs.setDouble(89, detlItem.getServingTable().getId());
			else
				detailItemPs.setNull(89, java.sql.Types.DOUBLE);
			if(detlItem.getServedBy()!=null)
				detailItemPs.setDouble(90, detlItem.getServedBy().getId());
			else
				detailItemPs.setNull(90, java.sql.Types.DOUBLE);

			if(detlItem.getParentDtlId()!=null){
				detailItemPs.setString(91, detlItem.getParentDtlId());
				detailItemPs.setBoolean(92, detlItem.getSaleItem().hasChoices());
				detailItemPs.setInt(93, detlItem.getItemType().getValue());

				if(detlItem.getSaleItemChoice()!=null){

					BeanSaleItemChoice saleItemChoice=detlItem.getSaleItemChoice();
					detailItemPs.setInt(94, saleItemChoice.getId());
					detailItemPs.setDouble(95, saleItemChoice.getFreeItemCount());
					detailItemPs.setDouble(96, saleItemChoice.getMaxItems());

					BeanChoice choice=saleItemChoice.getChoice();
					detailItemPs.setInt(97, choice.getId());
					detailItemPs.setString(98, choice.getCode());
					detailItemPs.setString(99, choice.getName());
					detailItemPs.setString(100, choice.getDescription());
					detailItemPs.setBoolean(101, choice.isGlobal());

				}else{

					detailItemPs.setNull(94, java.sql.Types.INTEGER);
					detailItemPs.setNull(95, java.sql.Types.INTEGER);
					detailItemPs.setNull(96, java.sql.Types.INTEGER);

					detailItemPs.setNull(97, java.sql.Types.INTEGER);
					detailItemPs.setNull(98, java.sql.Types.VARCHAR);
					detailItemPs.setNull(99, java.sql.Types.VARCHAR);
					detailItemPs.setNull(100, java.sql.Types.VARCHAR);
					detailItemPs.setNull(101, java.sql.Types.BOOLEAN);
				}

				if(detlItem.getComboContentItem()!=null){

					BeanSaleItemComboContent ccItem=detlItem.getComboContentItem();
					detailItemPs.setInt(102, ccItem.getId());
					detailItemPs.setString(103, ccItem.getCode());
					detailItemPs.setString(104, ccItem.getName());
					detailItemPs.setString(105, ccItem.getDescription());
					detailItemPs.setDouble(106, ccItem.getMaxItems());
					detailItemPs.setInt(107, ccItem.getUom().getID());

				}else{

					detailItemPs.setNull(102, java.sql.Types.INTEGER);
					detailItemPs.setNull(103, java.sql.Types.VARCHAR);
					detailItemPs.setNull(104, java.sql.Types.VARCHAR);
					detailItemPs.setNull(105, java.sql.Types.VARCHAR);
					detailItemPs.setNull(106, java.sql.Types.INTEGER);
					detailItemPs.setNull(107, java.sql.Types.INTEGER);
				}
			}else{

				detailItemPs.setNull(91, java.sql.Types.VARCHAR);
				detailItemPs.setNull(92, java.sql.Types.BOOLEAN);
				detailItemPs.setNull(93, java.sql.Types.INTEGER);

				detailItemPs.setNull(94, java.sql.Types.INTEGER);
				detailItemPs.setNull(95, java.sql.Types.INTEGER);
				detailItemPs.setNull(96, java.sql.Types.INTEGER);

				detailItemPs.setNull(97, java.sql.Types.INTEGER);
				detailItemPs.setNull(98, java.sql.Types.VARCHAR);
				detailItemPs.setNull(99, java.sql.Types.VARCHAR);
				detailItemPs.setNull(100, java.sql.Types.VARCHAR);
				detailItemPs.setNull(101, java.sql.Types.BOOLEAN);

				detailItemPs.setNull(102, java.sql.Types.INTEGER);
				detailItemPs.setNull(103, java.sql.Types.VARCHAR);
				detailItemPs.setNull(104, java.sql.Types.VARCHAR);
				detailItemPs.setNull(105, java.sql.Types.VARCHAR);
				detailItemPs.setNull(106, java.sql.Types.INTEGER);
				detailItemPs.setNull(107, java.sql.Types.INTEGER);

			}
			
			 if (detlItem.getServingTable() != null)
				 detailItemPs.setDouble(108, detlItem.getServingSeat());
             else
            	 detailItemPs.setNull(108, java.sql.Types.DOUBLE);
			 
			 if(detlItem.isPrintedToKitchen())
				 detailItemPs.setNull(109, java.sql.Types.VARCHAR);
			 else
				 detailItemPs.setString(109, PosOrderUtil.getStatusString(detlItem));
			 
			 detailItemPs.setInt(110, detlItem.getSaleItem().getKitchenId());
			 
			 if(detlItem.getSaleItem().getTaxHomeService()!=null)
				 detailItemPs.setInt(111, detlItem.getSaleItem().getTaxHomeService().getId());
			 else
				 detailItemPs.setNull(111, java.sql.Types.INTEGER);
			 
			 if(detlItem.getSaleItem().getTaxTableService()!=null)
				 detailItemPs.setInt(112, detlItem.getSaleItem().getTaxTableService().getId());
			 else
				 detailItemPs.setNull(112, java.sql.Types.INTEGER);
			 
			 if(detlItem.getSaleItem().getTaxTakeAwayService()!=null)
				 detailItemPs.setInt(113, detlItem.getSaleItem().getTaxTakeAwayService().getId());
			 else
				 detailItemPs.setNull(113, java.sql.Types.INTEGER);
			 if(detlItem.isVoid()){
        		 detailItemPs.setInt(114, detlItem.getVoidBy().getId());
        		 detailItemPs.setString(115, detlItem.getVoidAt());
        		 detailItemPs.setString(123, detlItem.getVoidTime());
        	 }else{
        		 detailItemPs.setNull(114, java.sql.Types.INTEGER);
        		 detailItemPs.setNull(115, java.sql.Types.DATE);
        		 detailItemPs.setNull(123,java.sql.Types.DATE);
        	 }
			 
			 detailItemPs.setString(116, detlItem.getTrayWeight());
			 detailItemPs.setString(117, detlItem.getTrayCode());
			 detailItemPs.setDouble(118, saleItemObject.getRetailPrice());
			 detailItemPs.setDouble(119, saleItemObject.getWholesalePrice());
			 detailItemPs.setBoolean(120, saleItemObject.isPercetangeWholesalePrice());
			 detailItemPs.setString(121, saleItemObject.getHSNCode());
			 detailItemPs.setString(122, saleItemObject.getSubClass().getHSNCode());
			 
			 
             /*If item exist set additional where condition parameter for update query*/
             if (isExist){
            	 detailItemPs.setString(124, detlItem.getId());
             }
			

			detailItemPs.addBatch();

			detlItem.setNewItem(false);
			
			/*
			 * Update if the item has sub items.
			 * Extras and combos
			 */
			if(detlItem.getItemType()==OrderDetailItemType.SALE_ITEM)
				addSubItemListToPS(detlItem);

		}
	}

	private void addSubItemListToPS(BeanOrderDetail detlItem) throws Exception{
		/*
		 * Add combo contents first if any 
		 */
		if(detlItem.getComboSubstitutes()!=null && detlItem.getComboSubstitutes().size()>0)
			addComboSubstitutionsToPS(detlItem);
		/*
		 * 
		 * Add any extras if exist.
		 */
		if(detlItem.getExtraItemList()!=null && detlItem.getExtraItemList().size()>0)
			addExtrasToPS(detlItem);
	}

	private void addComboSubstitutionsToPS(BeanOrderDetail detlItem) throws Exception{

		HashMap<String, ArrayList<BeanOrderDetail>> subList=detlItem.getComboSubstitutes();
		for(String key:subList.keySet()){
			buildDetailPS(subList.get(key));
		}
	}

	private void addExtrasToPS(BeanOrderDetail detlItem) throws Exception{

		HashMap<String, ArrayList<BeanOrderDetail>> subList=detlItem.getExtraItemList();
		for(String key:subList.keySet()){
			buildDetailPS(subList.get(key));
		}
	}

	@Override
	public int deleteData(String where) throws SQLException {
		//		mOrderComboContentProvider.deleteData(where);
		//		return super.deleteData(where);
		return executeNonQuery("delete from order_dtls where " + where);
	}
	
	
	

	/**
	 * @param orderID
	 * @return
	 * @throws Exception
	 */
	public ArrayList<BeanOrderDetail> getOrderDetailDataById(String orderID) throws Exception{
		return getOrderDetailDataById(orderID,null);
	}
	/**
	 * @param orderID
	 * @param parentId
	 * @return
	 * @throws Exception
	 */
	public ArrayList<BeanOrderDetail> getOrderDetailDataById(String orderID,String parentId) throws Exception{
		
		ArrayList<BeanOrderDetail> detailList=null;
		String where="order_id='"+orderID+"'";
		
		if(parentId!=null)
			where+=" and parent_id='"+ parentId +"'";
		else
			where+=" and parent_dtl_id is null";
		
		final String orderBy="id";
		
		CachedRowSet crs=getData(where,orderBy);
		try{
			if(crs!=null){
				detailList=new ArrayList<BeanOrderDetail>();
				while(crs.next()){
					BeanOrderDetail orderDetail=getOrderDetailsDataFromRecordSet(crs);
					detailList.add(orderDetail);
				}		
				crs.close();
			}
		}catch (Exception ex){
			PosLog.write(this, "getOrderDetailDataById", ex);
			throw ex;
		}
		return detailList;	
	}

	public ArrayList<BeanOrderDetail> getOrderDetailData(String where) throws SQLException{
		final String orderBy="sub_class_name,name";
		
//		final String orderBy="id";
		
		ArrayList<BeanOrderDetail> detailList=null;
		CachedRowSet crs=getData(where+" and is_void <> 1 ",orderBy);
		try{
			if(crs!=null){
				detailList=new ArrayList<BeanOrderDetail>();
				while(crs.next()){
					BeanOrderDetail orderDetail=getOrderDetailsDataFromRecordSet(crs);
					detailList.add(orderDetail);
				}		
				crs.close();
			}
		}catch (Exception ex){
			PosLog.write(this, "getOrderDetailData", ex);
		}
		return detailList;	
	}

	
	public ArrayList<BeanOrderDetail> getVoidItemData(String where) throws SQLException{
		
		final String orderBy="sub_class_name,name";
		
//		final String orderBy="id";
		
		ArrayList<BeanOrderDetail> detailList=null;
		CachedRowSet crs=getData(where+" and is_void <> 1 ",orderBy);
		try{
			if(crs!=null){
				detailList=new ArrayList<BeanOrderDetail>();
				while(crs.next()){
					BeanOrderDetail orderDetail=getOrderDetailsDataFromRecordSet(crs);
					detailList.add(orderDetail);
				}		
				crs.close();
			}
		}catch (Exception ex){
			PosLog.write(this, "getOrderDetailData", ex);
		}
		return detailList;	
	}

	
	/**
	 * @param crs
	 * @return
	 * @throws Exception
	 */
	private BeanOrderDetail getOrderDetailsDataFromRecordSet(CachedRowSet crs) throws Exception{
		
		BeanOrderDetail orderDetail=new BeanOrderDetail();
		orderDetail.setId(crs.getString("id"));
		orderDetail.setOrderId(crs.getString("order_id"));
		orderDetail.setSaleItem(createSaleItem(orderDetail.getId(),crs));
		orderDetail.setDiscountType(crs.getInt("discount_type"));
		orderDetail.setNewItem(false);
		orderDetail.setAddedToOrder(true);
		orderDetail.setKitchenPrintedStatus(crs.getString("kitchen_print_status"));
		orderDetail.setVoid(crs.getBoolean("is_void"));
		orderDetail.setOrderDate(crs.getString("order_date"));
		orderDetail.setOrderTime(crs.getString("order_time"));
		orderDetail.setCashierId(crs.getInt("cashier_id"));
		orderDetail.setPrintedToKitchen(crs.getBoolean("is_printed_to_kitchen"));
		orderDetail.setRemarks(crs.getString("remarks"));
		orderDetail.setTrayWeight(crs.getString("tray_weight"));
		orderDetail.setTrayCode(crs.getString("tray_code"));
		
		if(orderDetail.isVoid()){
			orderDetail.setVoidBy(mUserProvider.getUserByID(crs.getInt("void_by")));
			orderDetail.setVoidAt(crs.getString("void_at"));
		}
		/*
		 * Make sure that the waiter table does not contain any records having id=0;
		 */
		orderDetail.setServedBy(mWaiterProvider.getWaiterById(crs.getInt("served_by")));
		/*
		 * Load the table from header serving table list.
		 */
		BeanServingTable table=null;
		if(mOrderHeader!=null && mOrderHeader.getOrderTableList().size()>0 ){
		
			final String code=mServiceTableProvider.getTableCode(crs.getInt("serving_table_id"));
			if(code!=null)
				table=mOrderHeader.getOrderTableList().get(code);
		}
		
		if(table==null)
			table=mServiceTableProvider.getTableByID(crs.getInt("serving_table_id"));
		
		orderDetail.setServingTable(table);
		orderDetail.setServingSeat(crs.getInt("seat_no"));
		orderDetail.setServiceType(PosOrderServiceTypes.get(crs.getInt("service_type")));
		orderDetail.setParentDtlId(crs.getString("parent_dtl_id"));
		orderDetail.setItemType(OrderDetailItemType.get(crs.getInt("item_type")));

		orderDetail.setPrintingOrder(crs.getInt("print_order"));
		
		/*
		 * Set the refund details  
		 */
		orderDetail.setRefundId(crs.getInt("refund_id"));
	 	orderDetail.setRefundStatus(orderDetail.getRefundId()>0?true:false);
	 	orderDetail.setRefundAmount(crs.getDouble("refund_amount"));
	 	orderDetail.setRefundQuantity(crs.getDouble("refund_qty"));
	 	orderDetail.setRefundTax1Amount(crs.getDouble("refund_tax1_amount"));
	 	orderDetail.setRefundTax2Amount(crs.getDouble("refund_tax2_amount"));
	 	orderDetail.setRefundTax3Amount(crs.getDouble("refund_tax3_amount"));
	 	orderDetail.setRefundSCAmount(crs.getDouble("refund_sc_amount"));
	 	orderDetail.setRefundGSTAmount(crs.getDouble("refund_gst_amount"));
	 	
		/*
		 * if is a sub item then
		 * set the properties
		 */
		if(orderDetail.getParentDtlId()!=null){

			if(orderDetail.getItemType()==OrderDetailItemType.EXTRA_ITEM){
				orderDetail.setSaleItemChoice(getSaleItemChoice(orderDetail.getId()));
			}else if(orderDetail.getItemType()==OrderDetailItemType.COMBO_CONTENT_ITEM){
				orderDetail.setComboContentItem(getComboContentItem(orderDetail.getId()));
			}

		}

		/*
		 * loads the sub items if any 
		 */

		if(crs.getBoolean("has_choice_items"))
			orderDetail.setExtraItemList(loadExtras(orderDetail.getId()));
		if(crs.getBoolean("has_combo_content_items"))
			orderDetail.setComboSubstitutes(loadComboContents(orderDetail.getId()));
		
		/**
		 * Loads Split details
		 */
		
		orderDetail.setSplitList(mOrderSplitDetailProvider.getSplitDetailsByDetailItem(orderDetail.getId()));

		return orderDetail;
	}


	/**
	 * Loads the extras for the details item
	 * @param parentDtlId
	 * @return
	 * @throws Exception 
	 */
	private HashMap<String, ArrayList<BeanOrderDetail>> loadExtras(String parentDtlId) throws Exception {
		HashMap<String, ArrayList<BeanOrderDetail>> extras=null;
		final String whereSQL="item_type=1 and parent_dtl_id='"+parentDtlId+"'";
//		final String oderBySQL="sale_item_choices_id";
		final String oderBySQL="id";
		CachedRowSet crs=getData(whereSQL, oderBySQL);
		try{
			if(crs!=null){
				extras=new HashMap<String, ArrayList<BeanOrderDetail>>();
				ArrayList<BeanOrderDetail> subList=null;
				while(crs.next()){
					BeanOrderDetail orderDetail=getOrderDetailsDataFromRecordSet(crs);
					BeanSaleItemChoice saleItemChoice=orderDetail.getSaleItemChoice();
					if(extras.containsKey(saleItemChoice.getChoice().getCode()))
						subList=extras.get(saleItemChoice.getChoice().getCode());
					else{
						subList=new ArrayList<BeanOrderDetail>();
						extras.put(saleItemChoice.getChoice().getCode(),subList);
					}
					subList.add(orderDetail);
				}		
				crs.close();
			}
		}catch (Exception ex){
			PosLog.write(this, "loadExtras", ex);
			throw ex;
		}
		return extras;
	}

	/**
	 * Loads the extras for the details item
	 * @param parentDtlId
	 * @return
	 * @throws Exception 
	 */
	private HashMap<String, ArrayList<BeanOrderDetail>> loadComboContents(String parentDtlId) throws Exception {
		HashMap<String, ArrayList<BeanOrderDetail>> comboContents=null;
		final String whereSQL="item_type=2 and parent_dtl_id='"+parentDtlId+"'";
//		final String oderBySQL="combo_content_id";
		final String oderBySQL="id";
		CachedRowSet crs=getData(whereSQL, oderBySQL);
		try{
			if(crs!=null){
				comboContents=new HashMap<String, ArrayList<BeanOrderDetail>>();
				ArrayList<BeanOrderDetail> subList=null;
				while(crs.next()){
					BeanOrderDetail orderDetail=getOrderDetailsDataFromRecordSet(crs);
					BeanSaleItemComboContent ccItem=orderDetail.getComboContentItem();
					if(comboContents.containsKey(ccItem.getCode()))
						subList=comboContents.get(ccItem.getCode());
					else{
						subList=new ArrayList<BeanOrderDetail>();
						comboContents.put(ccItem.getCode(),subList);
					}
					subList.add(orderDetail);
				}		
				crs.close();
			}
		}catch (Exception ex){
			PosLog.write(this, "loadComboContents", ex);
			throw ex;
		}
		return comboContents;
	}

	/**
	 * @param orderDetail
	 * @param crs
	 * @throws Exception 
	 */
	private BeanSaleItemChoice getSaleItemChoice(String dtlId
			) throws Exception {

		BeanSaleItemChoice item=null;
		final String sql="select * from v_order_dtl_sale_item_choices where order_dtl_id='"+dtlId+"'";
		CachedRowSet res=executeQuery(sql);
		if(res!=null){
			try {
				if(res.next()) {
					item = PosSaleItemChoiceProvider.getInstance().createSaleItemChoice(res, false);
					item.setChoice(getChoiceItem(dtlId));
				}
				res.close();
			} catch (SQLException e) {
				PosLog.write(this,"getSaleItemChoice",e.getMessage());
				res.close();
				throw e;
			}
		}
		return item;

	}

	/**
	 * @param crs
	 * @return
	 * @throws SQLException 
	 */
	private BeanChoice getChoiceItem(String dtlId) throws Exception {

		BeanChoice item=null;
		final String sql="select * from v_order_dtl_choices where order_dtl_id='"+dtlId+"'";
		CachedRowSet res=executeQuery(sql);
		if(res!=null){
			try {
				if(res.next()) {
					item = PosChoiceItemProvider.getInstance().createItemFromCrs(res);
				}
				res.close();
			} catch (SQLException e) {
				PosLog.write(this,"getChoiceItem",e.getMessage());
				res.close();
				throw e;
			}
		}
		return item;
	}

	private BeanSaleItemComboContent getComboContentItem(String dtlId) throws SQLException{

		BeanSaleItemComboContent item=null;
		final String sql="select * from v_order_dtl_combo_contents where order_dtl_id='"+dtlId+"'";
		CachedRowSet res=executeQuery(sql);
		if(res!=null){
			try {
				if(res.next()) {
					item = PosSaleItemComboContentProvider.getInstance().createItemFromCrs(res, true);
				}
				res.close();
			} catch (SQLException e) {
				PosLog.write(this,"getChoiceItem",e.getMessage());
				res.close();
				throw e;
			}
		}
		return item;
	}

	private BeanItemDiscount createDiscountFromRowset(CachedRowSet res) throws SQLException{
		BeanItemDiscount discount=new BeanItemDiscount();
		discount.setId(res.getInt("discount_id"));
		discount.setCode(res.getString("discount_code"));
		discount.setName(res.getString("discount_name"));
		discount.setDescription(res.getString("discount_description"));
		discount.setPrice(res.getDouble("discount_price"));
		discount.setPercentage(res.getBoolean("discount_is_percentage"));
		discount.setOverridable(res.getBoolean("discount_is_overridable"));
		discount.setItemSpecific(res.getBoolean("discount_is_item_specific"));
		discount.setRequiredQuantity(res.getDouble("discount_grouping_quantity"));
		discount.setVariants(res.getDouble("discount_variants"));
		discount.setEditType(EditTypes.get(res.getInt("discount_allow_editing")));
		discount.setIsPromotion(res.getBoolean("discount_is_promotion"));
		return discount;
	}

	private BeanTax createTaxItemFromRowset(CachedRowSet res) throws SQLException{

		BeanTax tax=new BeanTax();
		tax.setId(res.getInt("tax_id"));
		tax.setCode(res.getString("tax_code"));
		tax.setName(res.getString("tax_name"));

		final boolean isTax1Applicable=res.getBoolean("is_tax1_applied");
		//		if(isTax1Applicable){
		tax.setTaxOneApplicable(isTax1Applicable);
		tax.setTaxOneName(res.getString("tax1_name"));
		tax.setTaxOnePercentage(res.getDouble("tax1_pc"));
		//		}

		final boolean isTax2Applicable=res.getBoolean("is_tax2_applied");
		//		if(isTax2Applicable){
		tax.setTaxTwoApplicable(isTax2Applicable);
		tax.setTaxTwoName(res.getString("tax2_name"));
		tax.setTaxTwoPercentage(res.getDouble("tax2_pc"));
		//		}

		final boolean isTax3Applicable=res.getBoolean("is_tax3_applied");
		//		if(isTax3Applicable){
		tax.setTaxThreeApplicable(isTax3Applicable);
		tax.setTaxThreeName(res.getString("tax3_name"));
		tax.setTaxThreePercentage(res.getDouble("tax3_pc"));
		//		}

		final boolean isSTApplicable=res.getBoolean("is_sc_applied");
		//		if(isSTApplicable){
		tax.setServiceTaxApplicable(isSTApplicable);
		tax.setServiceTaxName(res.getString("sc_name"));
		tax.setServiceTaxPercentage(res.getDouble("sc_pc"));
		//		}

		final boolean isGSTApplicable=res.getBoolean("is_gst_applied");
		//		if(isGSTApplicable){
		tax.setGSTDefined(isGSTApplicable);
		tax.setGSTName(res.getString("gst_name"));
		tax.setGSTPercentage(res.getDouble("gst_pc"));
		tax.setServiceTaxPercentage(res.getDouble("sc_pc"));
		tax.setTaxOneIncludeInGST(res.getBoolean("is_tax1_included_in_gst"));
		tax.setTaxTwoIncludeInGST(res.getBoolean("is_tax2_included_in_gst"));
		tax.setTaxThreeIncludeInGST(res.getBoolean("is_tax3_included_in_gst"));
		tax.setServiceTaxIncludeInGST(res.getBoolean("is_tax1_included_in_gst"));
		//		}
		return tax;

	}

	private BeanSaleItem createSaleItem(String orderDtlId,CachedRowSet res) throws Exception{

		BeanSaleItem item=(res.getBoolean("is_combo_item"))?new BeanSaleItemCombo():new BeanSaleItem();
		mUOMProvider=PosUOMProvider.getInstance();
		item.setId(res.getInt("sale_item_id"));
		item.setStockItemId(res.getInt("stock_item_id"));
		item.setCode(res.getString("sale_item_code"));
		item.setName(res.getString("name"));
		item.setDescription(res.getString("description"));
		item.setAlternativeName(res.getString("alternative_name"));
		item.setAlternativeNameToPrint(res.getString("alternative_name_to_print"));
		item.setNameToPrint(res.getString("name_to_print"));
		item.setSubClassId(res.getInt("sub_class_id"));
		item.setUom(mUOMProvider.getUom(res.getString("uom_code")));
		item.setQuantity(res.getDouble("qty"));
		item.setOpen(res.getBoolean("is_open"));
		item.setComboItem(res.getBoolean("is_combo_item"));
		item.setKitchenId(res.getInt("kitchen_id"));
		item.setDiscount(createDiscountFromRowset(res));
		item.setTaxCalculationMethod(TaxCalculationMethod.get(res.getInt("tax_calculation_method")));
		//		final int txCalc=res.getInt("tax_calculation_method");
		//		final TaxCalculationMethod txCalMethod=TaxCalculationMethod.get(txCalc);
		//		item.setTaxCalculationMethod(txCalMethod);
		//		
		//		if(txCalMethod!=TaxCalculationMethod.None){
		//			final int taxId=res.getInt("tax_id");
		//			final PosTaxObject itemTax=mTaxItemProvider.getTaxItem(taxId);
		//			item.setTax(itemTax);
		//			
		//		}
		item.setSubClass(PosSubItemClassProvider.getInstance().getClassItem(res.getInt("sub_class_id")));
		//		item.setSubClass(res.gets)
		if(item.getTaxCalculationMethod()!=TaxCalculationMethod.None){
			
			item.setTax(createTaxItemFromRowset(res));
			
			final PosTaxItemProvider  taxItemProvider=PosTaxItemProvider.getInstance();

			final int taxIdHomeService=res.getInt("tax_id_home_service");
			final BeanTax itemTaxHomeService=taxItemProvider.getTaxItem(taxIdHomeService);
			item.setTaxHomeService(itemTaxHomeService);
			
			final int taxIdTableService=res.getInt("tax_id_table_service");
			final BeanTax itemTaxTableService=taxItemProvider.getTaxItem(taxIdTableService);
			item.setTaxTableService(itemTaxTableService);
			
			final int taxIdTakeAwayService=res.getInt("tax_id_take_away_service");
			final BeanTax itemTaxTakeAwayService=taxItemProvider.getTaxItem(taxIdTakeAwayService);
			item.setTaxTakeAwayService(itemTaxTakeAwayService);
		}
		item.setFixedPrice(Float.parseFloat(res.getString("fixed_price")));
		item.setItemTotal(Float.parseFloat(res.getString("item_total")));
		item.setCustomerPrice(res.getDouble("customer_price_variance"));
		if(item.isComboItem()){
			//			((BeanSaleItemCombo) item).setComboContentItemList(mOrderComboContentProvider.getComboContentList(orderDtlId, item));
		}

		for(int index=0; index<BeanSaleItem.ITEM_ATTRIBUTE_COUNT; index++){
			String attributeName= res.getString("attrib"+String.valueOf(index+1)+"_name");
			String attributeOptions =res.getString("attrib"+String.valueOf(index+1)+"_options");
			String selectedAttribute = res.getString("attrib"+String.valueOf(index+1)+"_selected_option");
			if(attributeName!=null&&!attributeName.equals(""))
				item.addAttribute(attributeName,attributeOptions,selectedAttribute);
			else
				break;
		}
		/**
		 * Load the image
		 */
		if(PosEnvSettings.getInstance().getUISetting().getOrderEntryUISettings().getItemListPanelSetting().getButtonType()!=PosItemDisplayStyle.TEXT_ONLY){
			try{
				final String encodedImage=res.getString("item_thumb");
				if(encodedImage!=null && !encodedImage.equals("")) 
					item.setSaleItemImage(PosImageUtils.decodeFromBase64(encodedImage));
			}catch(Exception e){
				item.setSaleItemImage(null);
				PosLog.write(this,"createSaleItem(itemCode:"+item.getCode()+")",e);
			}
		}
		
		item.setHSNCode(res.getString("sale_item_hsn_code"));
		item.getSubClass().setHSNCode(res.getString("sub_class_hsn_code"));
		item.setRetailPrice(Float.parseFloat(res.getString("rtls_price")));
		item.setWholesalePrice( res.getDouble("whls_price"));
		item.setIsPercetangeWholesalePrice(res.getBoolean("is_whls_price_pc"));
		item.setIsPrintableToKitchen(res.getBoolean("is_printable_to_kitchen"));
		item.setBarCode(res.getString("barcode"));
		PosTaxUtil.calculateTax(item);
		//		PosTaxUtil.resetItemPriceBasedOnTaxCalcMethod(item);

		//		item.setVoid(res.getBoolean("is_void"));
		return item;
	}



	public ArrayList<BeanDiscountSummary> getDiscountSummary(String where) throws SQLException{
		final String SQL="SELECT dtl.discount_id, dtl.discount_name, dtl.discount_code, sum(discount_amount) as amount,sum(qty) as quantity FROM `order_dtls` dtl where dtl.is_void <> 1 and dtl.discount_id not in (1,2) and "+where+" group by dtl.discount_name  ";
		ArrayList<BeanDiscountSummary> discountSummary=null;
		CachedRowSet crs=executeQuery(SQL);
		try{
			if(crs!=null){
				discountSummary=new ArrayList<BeanDiscountSummary>();
				while(crs.next()){
					BeanDiscountSummary discount=getDiscountSummaryFromRecordSet(crs);
					discountSummary.add(discount);
				}		
				crs.close();
			}
		}catch (Exception ex){
			PosLog.write(this, "getDiscountSummary", ex);
		}
		return discountSummary;	
	}

	/**
	 * @param crs
	 * @return
	 * @throws SQLException 
	 */
	private BeanDiscountSummary getDiscountSummaryFromRecordSet(
			CachedRowSet crs) throws SQLException {
		BeanDiscountSummary discount = new BeanDiscountSummary();
		discount.setId(crs.getInt("discount_id"));
		discount.setName(crs.getString("discount_name"));
		discount.setCode(crs.getString("discount_code"));
		discount.setAmount(crs.getDouble("amount"));
		discount.setQuantity(crs.getDouble("quantity"));

		return discount;
	}
	
	/* (non-Javadoc)
	 * @see com.indocosmo.pos.data.providers.PosDatabaseProvider#isExist(java.lang.String)
	 */
	@Override
	public boolean isExist(String where) {

		return isExist("order_dtls", where);
	}



	/**
	 * @param orderObject
	 * @return
	 * @throws Exception 
	 */
	public ArrayList<BeanOrderDetail> getOrderDetailData(
			BeanOrderHeader orderObject) throws Exception {

		this.mOrderHeader=orderObject;
		
		final  ArrayList<BeanOrderDetail> details=getOrderDetailDataById(mOrderHeader.getOrderId());
		
		this.mOrderHeader=null;
		return details;
	}
	
//	/*
//	 * 
//	 */
//	public HashMap<Integer, BeanReceiptTaxSummary>   getItemTaxSummaryList(String where) throws SQLException{
//		
//		HashMap<Integer, BeanReceiptTaxSummary> itemTaxList =null;
//		BeanReceiptTaxSummary taxSummary;
//		where=where.replace("order_id", "dtl.order_id");
//	
//		BeanCurrency currnecy= PosEnvSettings.getInstance().getCurrency();
//		final int prec = currnecy.getDecimalPlaces(); 
//		String taxSummarySql="SELECT  dtl.tax_id,dtl.tax1_pc," + 
//				" sum(round((dtl.tax1_amount- dtl.tax1_amount *ohdr.bill_discount_percentage/100.00)- " + 
//				"CASE WHEN oref.tax1_amount IS NULL THEN  0 ELSE oref.tax1_amount END ,"+ prec + "))  taxt1_amount " +
//				 " FROM order_dtls dtl" +
//				 "   JOIN order_hdrs ohdr ON dtl.order_id=ohdr.order_id  " + 
//				 " LEFT JOIN order_refunds oref ON dtl.id=oref.order_dtl_id " + 
//				" WHERE dtl.is_tax1_applied=1  and dtl.is_void <> 1  " + 
//				((where!=null && !where.equals(""))?" AND " + where:"") + 
//				"  GROUP BY tax_id,tax1_pc order by tax1_pc" ;
//				
//		CachedRowSet crs= executeQuery(taxSummarySql);
//		try{
//			if(crs!=null){
//				itemTaxList=new HashMap<Integer, BeanReceiptTaxSummary>();
//
//			 	while(crs.next()){
//					
//					taxSummary=new BeanReceiptTaxSummary();
//					taxSummary.setTaxAmount(PosCurrencyUtil.roundTo(crs.getDouble("taxt1_amount")));
//					taxSummary.setTaxPercentage(crs.getDouble("tax1_pc") );
//					taxSummary.setTaxableAmount(0);
//					
//					itemTaxList.put(crs.getInt("tax_id"), taxSummary);
//					 
//				}		
//				crs.close();
//			}
//		}catch (Exception ex){
//			PosLog.write(this, "getItemTaxSummaryList", ex);
//		}
//		return itemTaxList;	
//	}
//	
	/*
	 * 
	 */
	public double getTotalBillTax(String where,Integer shiftId) throws Exception{
		
		
		where = where + " and ohdr.status in (" + PosOrderStatus.Closed.getCode()+   "," + PosOrderStatus.Refunded.getCode() + ")";
		where =where.replace("service_type", "ohdr.service_type");
		
		String whereSale=  where.replace("payment_date", "closing_date");
		whereSale = whereSale.replace("payment_time", "closing_time");
		whereSale = whereSale +  (shiftId!=0? " and shift_id="+ shiftId : "");
		
		where = where +  (shiftId!=0? " and cashier_shift_id="+ shiftId : "");
		double taxTotal=0;
//		where=where.replace("order_id", "dtl.order_id");
	
		BeanCurrency currnecy= PosEnvSettings.getInstance().getCurrency();
		final int prec = currnecy.getDecimalPlaces(); 
		
		final String saleTaxSql="(SELECT IFNULL( " +
					"sum( round(dtl.tax1_amount- dtl.tax1_amount *ohdr.bill_discount_percentage/100.00,2) ) +" + 
					"sum(round(dtl.tax2_amount- dtl.tax2_amount *ohdr.bill_discount_percentage/100.00,2 ))  +" + 
					"sum(round(dtl.tax3_amount- dtl.tax3_amount *ohdr.bill_discount_percentage/100.00,2) ) +" +
					"sum(round(dtl.sc_amount- dtl.sc_amount *ohdr.bill_discount_percentage/100.00,2) ),0)  sales_tax_amount "+
					"FROM order_dtls dtl JOIN order_hdrs ohdr ON dtl.order_id=ohdr.order_id  " + 
					"WHERE dtl.is_tax1_applied=1  and dtl.is_void <> 1   AND " + whereSale + ")";
		
		final String refundTaxSql="(SELECT IFNULL(" + 
				" sum(oref.tax1_amount) +sum(oref.tax2_amount) +sum(oref.tax3_amount) +	" +
				" sum(oref.sc_amount),0) as refund_tax_amount " + 
				" FROM order_refunds oref " + 
				" JOIN order_payments opay ON oref.order_payment_id= opay.id " +
				" JOIN order_dtls odtl ON oref.order_dtl_id =odtl.id " +
				" JOIN order_hdrs ohdr ON odtl.order_id=ohdr.order_id  "+
				" WHERE " + where + ")"; 
		
		final String sql= "	SELECT " + saleTaxSql + " - " + refundTaxSql + " as tax_total";
		
//		String sql=" SELECT   " +   
//					"sum(round(round(dtl.tax1_amount- dtl.tax1_amount *ohdr.bill_discount_percentage/100.00,"+ prec + ")-" + 
//					"CASE WHEN oref.tax1_amount IS NULL THEN  0 ELSE oref.tax1_amount END ,"+ prec + "))  +" +
//					"sum(round(round(dtl.tax2_amount- dtl.tax2_amount *ohdr.bill_discount_percentage/100.00,"+ prec + ")-" +  
//					"CASE WHEN oref.tax2_amount IS NULL THEN  0 ELSE oref.tax2_amount END ,"+ prec + "))  +"+
//					"sum(round(round(dtl.tax3_amount- dtl.tax3_amount *ohdr.bill_discount_percentage/100.00,"+ prec + ")-"+ 
//					"CASE WHEN oref.tax3_amount IS NULL THEN  0 ELSE oref.tax3_amount END ,"+ prec + "))  +" +
//					"sum(round(round(dtl.sc_amount- dtl.sc_amount *ohdr.bill_discount_percentage/100.00,"+ prec + ")-" + 
//					"CASE WHEN oref.sc_amount IS NULL THEN  0 ELSE oref.sc_amount END ,"+ prec + "))  as tax_total " +   
//					"FROM order_dtls dtl " + 
//					"JOIN order_hdrs ohdr ON dtl.order_id=ohdr.order_id " +    
//					"LEFT JOIN order_refunds oref ON dtl.id=oref.order_dtl_id  " + 
//				" WHERE dtl.is_tax1_applied=1  and dtl.is_void <> 1  " + 
//				((where!=null && !where.equals(""))?" AND " + where:"")  ;
//				
		CachedRowSet crs=executeQuery(sql);
		if(crs!=null){
			try {
				if(crs.next()){

					taxTotal=crs.getDouble("tax_total")   ;
				}
					
			} catch (Exception e) {
				PosLog.write(this, "getTotalBillTax", e);
				throw new Exception("Failed to get tax details.");
			}finally{
				try {
					crs.close();
				} catch (SQLException e) {
					PosLog.write(this, "getTotalBillTax", e);
					throw new Exception("Failed to get order  payment header details.");
				}
			}
		}
		return taxTotal;
	}
	

	/*
	 * 
	 */
	public HashMap<Integer, BeanReceiptTaxSummary>   getItemTaxSummaryList(String where,Integer shiftId) throws SQLException{
		
		HashMap<Integer, BeanReceiptTaxSummary> itemTaxList =null;
		BeanReceiptTaxSummary taxSummary;
		where = where + " and ohdr.status in (" + PosOrderStatus.Closed.getCode()+   "," + PosOrderStatus.Refunded.getCode() + ")";
		where =where.replace("service_type", "ohdr.service_type");
		String whereSale=  where.replace("payment_date", "closing_date");
		whereSale = whereSale.replace("payment_time", "closing_time");
		whereSale = whereSale +  (shiftId!=0? " and shift_id="+ shiftId : "");
		
		where = where +  (shiftId!=0? " and cashier_shift_id="+ shiftId : "");
		
		BeanCurrency currnecy= PosEnvSettings.getInstance().getCurrency();
		final int prec = currnecy.getDecimalPlaces(); 
		
		
		final String saleTaxSql="(SELECT  dtl.tax_id,dtl.tax1_pc,dtl.tax_name,"+
				"sum( round(dtl.tax1_amount- dtl.tax1_amount *ohdr.bill_discount_percentage/100.00,2) )  tax1_amount ," + 
				"sum(round(dtl.tax2_amount- dtl.tax2_amount *ohdr.bill_discount_percentage/100.00,2 ))  tax2_amount," +
				"sum(round(dtl.tax3_amount- dtl.tax3_amount *ohdr.bill_discount_percentage/100.00,2) )  tax3_amount," +
				"sum(round(dtl.sc_amount- dtl.sc_amount *ohdr.bill_discount_percentage/100.00,2) )  sc_amount " +
				"FROM order_dtls dtl JOIN order_hdrs ohdr ON dtl.order_id=ohdr.order_id  " +
				"WHERE dtl.is_tax1_applied=1  and dtl.is_void <> 1   AND " +  whereSale   +
				" GROUP BY tax_id,tax1_pc,dtl.tax_name  ) ";
		final String refundTaxSql="(SELECT odtl.tax_id,sum(oref.tax1_amount) as tax1_amount, "+ 
					" sum(oref.tax2_amount) as tax2_amount,sum(oref.tax3_amount) as tax3_amount," +
					" sum(oref.sc_amount) as sc_amount FROM order_refunds oref " +  
					" JOIN order_payments opay ON oref.order_payment_id= opay.id " + 
					" JOIN order_dtls odtl ON oref.order_dtl_id =odtl.id "+ 
					" JOIN order_hdrs ohdr ON odtl.order_id=ohdr.order_id " +   
					" WHERE " + where  + " GROUP BY odtl.tax_id )";
		
		String taxSummarySql=" SELECT Tx.id AS tax_id,Tx.tax1_percentage,Tx.name AS tax_name," +
				"ROUND(CASE WHEN T1.tax1_amount IS NULL THEN 0 ELSE T1.tax1_amount END - " + 
						" CASE WHEN T2.tax1_amount IS NULL THEN 0 ELSE T2.tax1_amount END ,2) AS tax1_amount," + 
				"ROUND(CASE WHEN T1.tax2_amount IS NULL THEN 0 ELSE T1.tax2_amount END -"+
						" CASE WHEN T2.tax2_amount IS NULL THEN 0 ELSE T2.tax2_amount END ,2) AS tax2_amount," +
			    "ROUND(CASE WHEN T1.tax3_amount IS NULL THEN 0 ELSE T1.tax3_amount END - " +
						" CASE WHEN T2.tax3_amount IS NULL THEN 0 ELSE T2.tax3_amount END ,2)  AS tax3_amount," +
				"ROUND(CASE WHEN T1.sc_amount IS NULL THEN 0 ELSE T1.sc_amount END -"+
						" CASE WHEN T2.sc_amount IS NULL THEN 0 ELSE T2.sc_amount END ,2) AS sc_amount " +
			" FROM taxes Tx LEFT JOIN " + saleTaxSql + " T1 ON Tx.id=T1.tax_id "+
			" LEFT JOIN " + refundTaxSql + " T2 ON Tx.id=T2.tax_id" +
			" where (T1.tax1_amount<>0 or T1.tax2_amount<>0 or T1.tax3_amount<>0 or T1.sc_amount<>0 " + 
					 " or T2.tax1_amount<>0 or T2.tax2_amount<>0 or T2.tax3_amount<>0 or T2.sc_amount<>0) "+  
			" ORDER BY T1.tax1_pc";
				
		CachedRowSet crs= executeQuery(taxSummarySql);
		try{
			if(crs!=null){
				itemTaxList=new HashMap<Integer, BeanReceiptTaxSummary>();

			 	while(crs.next()){
					
					taxSummary=new BeanReceiptTaxSummary();
					taxSummary.setTaxName(crs.getString("tax_name"));
					taxSummary.setTaxPercentage(crs.getDouble("tax1_percentage") );
//					taxSummary.setTaxAmount(PosCurrencyUtil.roundTo(crs.getDouble("taxt1_amount")));
					taxSummary.setTaxableAmount(0);
					taxSummary.setTax1Amount(PosCurrencyUtil.roundTo(crs.getDouble("tax1_amount")));
					taxSummary.setTax2Amount(PosCurrencyUtil.roundTo(crs.getDouble("tax2_amount")));
					taxSummary.setTax3Amount(PosCurrencyUtil.roundTo(crs.getDouble("tax3_amount")));
					taxSummary.setTaxSCAmount(PosCurrencyUtil.roundTo(crs.getDouble("sc_amount")));
					taxSummary.setTaxAmount(taxSummary.getTax1Amount() +taxSummary.getTax2Amount() +
							taxSummary.getTax3Amount() +taxSummary.getTaxSCAmount());
					itemTaxList.put(crs.getInt("tax_id"), taxSummary);
					 
				}		
				crs.close();
			}
		}catch (Exception ex){
			PosLog.write(this, "getItemTaxSummaryList", ex);
		}
		return itemTaxList;	
	}
	
//	
//	/*
//	 * 
//	 */
//	public HashMap<Integer, BeanReceiptTaxSummary>   getItemTaxSummaryList(String where) throws SQLException{
//		
//		HashMap<Integer, BeanReceiptTaxSummary> itemTaxList =null;
//		BeanReceiptTaxSummary taxSummary;
//		where=where.replace("order_id", "dtl.order_id");
//	
//		BeanCurrency currnecy= PosEnvSettings.getInstance().getCurrency();
//		final int prec = currnecy.getDecimalPlaces(); 
//		String taxSummarySql=" SELECT  dtl.tax_id,dtl.tax1_pc,dtl.tax_name," +   
//					"sum(round(round(dtl.tax1_amount- dtl.tax1_amount *ohdr.bill_discount_percentage/100.00,"+ prec + ")-" + 
//					"CASE WHEN oref.tax1_amount IS NULL THEN  0 ELSE oref.tax1_amount END ,"+ prec + "))  tax1_amount  ," +
//					"sum(round(round(dtl.tax2_amount- dtl.tax2_amount *ohdr.bill_discount_percentage/100.00,"+ prec + ")-" +  
//					"CASE WHEN oref.tax2_amount IS NULL THEN  0 ELSE oref.tax2_amount END ,"+ prec + "))  tax2_amount,"+
//					"sum(round(round(dtl.tax3_amount- dtl.tax3_amount *ohdr.bill_discount_percentage/100.00,"+ prec + ")-"+ 
//					"CASE WHEN oref.tax3_amount IS NULL THEN  0 ELSE oref.tax3_amount END ,"+ prec + "))  tax3_amount," +
//					"sum(round(round(dtl.sc_amount- dtl.sc_amount *ohdr.bill_discount_percentage/100.00,"+ prec + ")-" + 
//					"CASE WHEN oref.sc_amount IS NULL THEN  0 ELSE oref.sc_amount END ,"+ prec + "))  sc_amount " +   
//					"FROM order_dtls dtl " + 
//					"JOIN order_hdrs ohdr ON dtl.order_id=ohdr.order_id " +    
//					"LEFT JOIN order_refunds oref ON dtl.id=oref.order_dtl_id  " + 
//				" WHERE dtl.is_tax1_applied=1  and dtl.is_void <> 1  " + 
//				((where!=null && !where.equals(""))?" AND " + where:"") + 
//				"  GROUP BY tax_id,tax1_pc,dtl.tax_name order by tax1_pc" ;
//				
//		CachedRowSet crs= executeQuery(taxSummarySql);
//		try{
//			if(crs!=null){
//				itemTaxList=new HashMap<Integer, BeanReceiptTaxSummary>();
//
//			 	while(crs.next()){
//					
//					taxSummary=new BeanReceiptTaxSummary();
//					taxSummary.setTaxName(crs.getString("tax_name"));
//					taxSummary.setTaxPercentage(crs.getDouble("tax1_pc") );
////					taxSummary.setTaxAmount(PosCurrencyUtil.roundTo(crs.getDouble("taxt1_amount")));
//					taxSummary.setTaxableAmount(0);
//					taxSummary.setTax1Amount(PosCurrencyUtil.roundTo(crs.getDouble("tax1_amount")));
//					taxSummary.setTax2Amount(PosCurrencyUtil.roundTo(crs.getDouble("tax2_amount")));
//					taxSummary.setTax3Amount(PosCurrencyUtil.roundTo(crs.getDouble("tax3_amount")));
//					taxSummary.setTaxSCAmount(PosCurrencyUtil.roundTo(crs.getDouble("sc_amount")));
//					taxSummary.setTaxAmount(taxSummary.getTax1Amount() +taxSummary.getTax2Amount() +
//							taxSummary.getTax3Amount() +taxSummary.getTaxSCAmount());
//					itemTaxList.put(crs.getInt("tax_id"), taxSummary);
//					 
//				}		
//				crs.close();
//			}
//		}catch (Exception ex){
//			PosLog.write(this, "getItemTaxSummaryList", ex);
//		}
//		return itemTaxList;	
//	}
	
	

	/**
	 * @param orderDetlList 
	 * @return
	 * @throws SQLException
	 */
	public void updatePrintStatus(String orderId) throws Exception{
		
		PosLog.debug("update kitchen print status to order dtl...... ");

		final String sql="update order_dtls set is_printed_to_kitchen =1, kitchen_print_status=null where order_id='" + orderId + "'";
		executeNonQuery(sql);
	}
}
