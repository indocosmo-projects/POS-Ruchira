/**
 * @author jojesh
 *This class will handle the Object list creation, manipulations etc.
 *Add arraylist searching sorting filtering functionalities here 
 */

package com.indocosmo.pos.data.providers.shopdb;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.sql.rowset.CachedRowSet;

import com.indocosmo.pos.common.PosEnvSettings;
import com.indocosmo.pos.common.PosLog;
import com.indocosmo.pos.common.enums.PosItemDisplayStyle;
import com.indocosmo.pos.common.utilities.PosImageUtils;
import com.indocosmo.pos.common.utilities.PosTaxUtil;
import com.indocosmo.pos.data.beans.BeanCustomerType;
import com.indocosmo.pos.data.beans.BeanDiscount;
import com.indocosmo.pos.data.beans.BeanSaleItem;
import com.indocosmo.pos.data.beans.BeanSaleItemAttribute;
import com.indocosmo.pos.data.beans.BeanSaleItemCombo;
import com.indocosmo.pos.data.beans.BeanTax;
import com.indocosmo.pos.data.beans.BeanTaxGlobalParam.POSTaxationBasedOn;
import com.indocosmo.pos.data.providers.shopdb.PosTaxItemProvider.TaxCalculationMethod;


/**
 * @author jojesh
 *
 */
public  class PosSaleItemProvider extends PosShopDBProviderBase {

	public static final String DEFAULT_ATTRIBUTE_OPTION="DEFAULT";


	private static HashMap<String, BeanSaleItem> subSaleItemList;
	private static HashMap<Integer, String> subSaleItemCodeMap;
	private PosDiscountItemProvider mDiscountItemProvider;
	private PosSaleItemPromotionProvider mPromotionItemProvider;
	private PosTaxItemProvider mTaxItemProvider;

	private PosUOMProvider mUOMProvider;

	/**
	 * Default constructor
	 */
	public PosSaleItemProvider() {
		super("v_sale_items");
		initSalesItemProvider();
	}

	/**
	 * Overloaded constructor
	 * @param table: table name 
	 */
	public PosSaleItemProvider(String table) {
		super(table);
		initSalesItemProvider();
	}

	private void initSalesItemProvider(){

		mDiscountItemProvider=new PosDiscountItemProvider();
		mPromotionItemProvider=new PosSaleItemPromotionProvider();
		mTaxItemProvider=PosTaxItemProvider.getInstance();

		mUOMProvider=PosUOMProvider.getInstance();

		if (subSaleItemList==null){
			subSaleItemList=new HashMap<String,BeanSaleItem>();
			subSaleItemCodeMap=new HashMap<Integer,String>();
		}
	}

	/**
	 * overloaded loadItems function which takes no parameter
	 * @return the list of PosSaleItems
	 */
	protected ArrayList<BeanSaleItem> loadItems(){
		return loadItems(null);
	}

	protected String getDisplayOrderField(){
		return "ifnull(display_order,100000),"+"display_order"; 
	}

	/**
	 * Function which retries the list of PosSaleitem from table
	 * @param where: the where condition for filtering the records
	 * @return
	 */
	protected ArrayList<BeanSaleItem> loadItems(String where){
		ArrayList<BeanSaleItem> itemList=null;
		try {
			final CachedRowSet res=getData(where,getDisplayOrderField());
			if(res!=null){
				itemList=new ArrayList<BeanSaleItem>();
				while (res.next()) {
					
					PosLog.debug("In Loop..." + res);
					
					BeanSaleItem posItem=createPosItemFromResult(res);
					itemList.add(posItem);
				}
				res.close();
			}
		} catch (Exception e) {
			PosLog.write(this,"loadItems",e);
		}
		return itemList;
	}

	/**
	 * Creates the PosSaleItem object from the resultset
	 * @param res: the result set which contains the PosSaleItem values from table.
	 * @return PosSaleItem object.
	 * @throws Exception 
	 * @throws  
	 */
	public BeanSaleItem createPosItemFromResult(final CachedRowSet res) throws  Exception{
		//Added by Udhayan for DayStart module problem On 23-Jul-2021
		//PosLog.debug("createPosItemFromResult==========pre createPosItemFromResult"+res.getString(9));
		BeanSaleItem item=null;
		//PosLog.debug("before==========if===== "+res.getBoolean("is_combo_item"));
		if(res.getBoolean("is_combo_item")){
		//	PosLog.debug("res.getBoolean==========if pre res.getBoolean");
			item=new  BeanSaleItemCombo();
			//PosLog.debug("res.getBoolean==========if res.getBoolean"+item);
			}
		else{
			//PosLog.debug("res.getBoolean==========before else res.getBoolean===========>");
		//	try{
				item=new BeanSaleItem();
			//	PosLog.debug("commented BeanSaleI");
		//	}catch(Exception e){
			//	PosLog.debug("BeanSaleItem Exception============>"+e);
			//}
			
		//	PosLog.debug("res.getBoolean==========else res.getBoolean===========>+"+item);
		}
	//	PosLog.debug("before createPosItemFromResult");
		createPosItemFromResult(res, item);
		return item;
	}

	/**
	 * Populates the PosSaleItem object passed using the resultset
	 * @param res: the result set which contains the PosSaleItem values from table.
	 * @param item: the PosSaleItemObject
	 * @throws Exception 
	 * @throws NumberFormatException 
	 */
	public void createPosItemFromResult(final CachedRowSet res,BeanSaleItem item) throws  Exception{
		//PosLog.debug("createPosItemFromResult==========inside createPosItemFromResult");
		//		try {
		item.setId(res.getInt("id"));
		item.setStockItemId(res.getInt("stock_item_id"));
		item.setCode(res.getString("code"));
		item.setName(res.getString("name"));
		item.setDescription(res.getString("description"));
		item.setSubClassId(res.getInt("sub_class_id"));
//		item.setAlternativeName(res.getString("alternative_name")!=null&&res.getString("alternative_name").length()>0?res.getString("alternative_name"):res.getString("name"));
		item.setAlternativeName(res.getString("alternative_name"));
		item.setNameToPrint(res.getString("name_to_print"));
//		item.setAlternativeNameToPrint(res.getString("alternative_name_to_print")!=null&&res.getString("alternative_name_to_print").length()>0?res.getString("alternative_name_to_print"):res.getString("name_to_print"));
		item.setAlternativeNameToPrint(res.getString("alternative_name_to_print"));
		item.setGroupItem(res.getBoolean("is_group_item"));
		item.setGroupID(res.getInt("group_item_id"));
		item.setKitchenId(res.getInt("kitchen_id"));
		item.setForeground(res.getString("fg_color"));
		item.setBackground(res.getString("bg_color"));
		item.setSubClass(PosSubItemClassProvider.getInstance().getClassItem(res.getInt("sub_class_id")));

		item.setHSNCode(res.getString("hsn_code"));
		

		item.setDiscount(mDiscountItemProvider.getNoneDiscount().clone());
		item.setPromotionList(mPromotionItemProvider.getPromotionCodes(item.getId()));
		
		final int txCalc=res.getInt("tax_calculation_method");
		final TaxCalculationMethod txCalMethod=TaxCalculationMethod.get(txCalc);
		item.setTaxCalculationMethod(txCalMethod);
		item.setTaxationBasedOn(POSTaxationBasedOn.get(res.getInt("taxation_based_on")));
		
		if(txCalMethod!=TaxCalculationMethod.None){
		
			final int taxId=res.getInt("tax_id");
			final BeanTax itemTax=mTaxItemProvider.getTaxItem(taxId);
			item.setTax(itemTax);

			if(item.getTaxationBasedOn()== POSTaxationBasedOn.ServiceType){
			
				final int taxIdHomeService=res.getInt("tax_id_home_service");
				final BeanTax itemTaxHomeService=mTaxItemProvider.getTaxItem(taxIdHomeService);
				item.setTaxHomeService(itemTaxHomeService);
				
				final int taxIdTableService=res.getInt("tax_id_table_service");
				final BeanTax itemTaxTableService=mTaxItemProvider.getTaxItem(taxIdTableService);
				item.setTaxTableService(itemTaxTableService);
				
				final int taxIdTakeAwayService=res.getInt("tax_id_take_away_service");
				final BeanTax itemTaxTakeAwayService=mTaxItemProvider.getTaxItem(taxIdTakeAwayService);
				item.setTaxTakeAwayService(itemTaxTakeAwayService);
			}
		}

		item.hasChoices(PosSaleItemChoiceProvider.getInstance().hasChoices(item.getCode()));

		if(item.isGroupItem()){
			item.setSubItemList(getGroupItemContents(item));
		}
		else{
			item.setFixedPrice(Float.parseFloat(res.getString("fixed_price")));
			item.setRetailPrice(Float.parseFloat(res.getString("fixed_price")));
			item.setWholesalePrice( res.getDouble("whls_price"));
			item.setIsPercetangeWholesalePrice(res.getBoolean("is_whls_price_pc"));

		}
		
		item.setComboItem(res.getBoolean("is_combo_item"));

		for(int index=0; index<BeanSaleItem.ITEM_ATTRIBUTE_COUNT; index++){
			String attributeName= res.getString("attrib"+String.valueOf(index+1)+"_name");
			String attributeOptions =res.getString("attrib"+String.valueOf(index+1)+"_options");
			if(attributeName!=null && !attributeName.equals(""))
				item.addAttribute(attributeName, DEFAULT_ATTRIBUTE_OPTION+"," + attributeOptions);
			else
				break;
		}

		item.setOpen(res.getBoolean("is_open"));
		item.setUom(mUOMProvider.getUom(res.getInt("uom_id")));
		item.setRequireWeighing(res.getBoolean("is_require_weighing"));
		item.setBarCode(res.getString("barcode"));
		item.setIsPrintableToKitchen(res.getBoolean("is_printable_to_kitchen"));
		if(PosEnvSettings.getInstance().getUISetting().getOrderEntryUISettings().getItemListPanelSetting().getButtonType()!=PosItemDisplayStyle.TEXT_ONLY){
			try{
				final String encodedImage=res.getString("item_thumb");
				if(encodedImage!=null && !encodedImage.equals("")) 
					item.setSaleItemImage(PosImageUtils.decodeFromBase64(encodedImage));
			}catch(Exception e){
				item.setSaleItemImage(null);
				PosLog.write(this,"createPosItemFromResult(itemCode:"+item.getCode()+")",e);
			}
		}

		if(item.getTax()!=null)
			PosTaxUtil.calculateTax(item);
	}


	/**
	 * Returns the GroupItem contents
	 * @param item: PosSaleItem which is the main group Item
	 * @return List of PosSaleItem objects which are the content of the group
	 */
	private ArrayList<BeanSaleItem> getGroupItemContents(BeanSaleItem item){
		return new PosGroupItemContentsProvider().getList(item);
	}

	/**
	 * Function which builds the list of the PosSaleItem
	 * @return List of PosSaleItem
	 */
	public ArrayList<BeanSaleItem> getList(){
		return getListByCode(null);
	}

	/**
	 * Function which builds the list of the PosSaleItem
	 * @param where: The filtering condition
	 * @return List of PosSaleItem
	 */
	public ArrayList<BeanSaleItem> getList(String where){
		return loadItems(where);
	}

	/**
	 * Function which builds the list of the PosSaleItem
	 * @param classCode: the subclass code for which the PosSaleItem is needed.
	 * @return List of PosSaleItem
	 */
	public ArrayList<BeanSaleItem> getListByCode(String classCode){
		String where="sub_class_code='"+classCode+"'";
		return loadItems(where);
	}

	/**
	 * @param classId
	 * @return
	 */
	public ArrayList<BeanSaleItem> getListByClassId(int classId){
		//		String where="sub_class_id='"+classId+"'";
		return loadItemsbyMenuDisplayOrder(classId);
	}

	/**
	 * @param classId - Selection of sale items by sub class id.
	 * Here select the sale items in the order specified in the sale_item_display_order
	 * @return
	 */
	private ArrayList<BeanSaleItem> loadItemsbyMenuDisplayOrder(int classId) {
		//Comments Added by Udhayan for DayStart module problem On 23-Jul-2021
		//PosLog.debug("loadItemsbyMenuDisplayOrder==========inside loadItemsbyMenuDisplayOrder");
		
		ArrayList<BeanSaleItem> itemList=null;
		int menuId =PosEnvSettings.getInstance().getMenu().getId();
		//PosLog.debug("class id==============>"+classId+"=========menuid===="+menuId);

		/******Query for selecting the sale items in the order specified in the sale_item_display_order ****/
		String SQL="select sitem.*,itm_order.display_order as item_disply_order from v_sale_items as sitem " +
				"left  join  sale_item_display_order as  itm_order on sitem.id=itm_order.sale_item_id" +
				" and sitem.sub_class_id=itm_order.sub_class_id and itm_order.sub_class_id='"+classId+"'" +
				"and itm_order.menu_id='"+menuId+"' and sitem.sub_class_id='"+classId+"' where sitem.sub_class_id='"+classId+"' order by  itm_order.display_order";
		try {
			final CachedRowSet res=executeQuery(SQL);
			if(res!=null){
				itemList=new ArrayList<BeanSaleItem>();
				while (res.next()) {
					PosLog.debug("In Loop..." + res.getString(9));
					BeanSaleItem posItem = createPosItemFromResult(res);
					itemList.add(posItem); 
				}
				res.close();
			}
		} catch (Exception e) {
			PosLog.write(this,"loadItems",e);
		}
		return itemList;
	}

	public ArrayList<BeanSaleItem> getListByID(int id){
		String where="id="+id;
		return loadItems(where);
	}

	public BeanDiscount getItemPriceAsDiscountByCustType(BeanCustomerType custType, BeanSaleItem saleItem){
		
		final int custTypeId=custType.getId();
		final int saleItemId=saleItem.getId();
		
		BeanDiscount customerDiscount=null;
		
		String sql="SELECT price_variance_pc,is_percentage,is_price_variance from v_customers_type_item_prices "+
				"where (customer_type_id='' or customer_type_id=" + custTypeId + ") and sale_item_id="+
				saleItemId;
		boolean isPriceVariance=true;
		CachedRowSet res=executeQuery(sql);
		if(res!=null){
			try {
				
				customerDiscount = new PosDiscountItemProvider().getCustomerDiscount().clone();
				
				if(res.next()){
					customerDiscount.setPercentage(res.getBoolean("is_percentage"));
					customerDiscount.setPrice( res.getDouble("price_variance_pc"));
					isPriceVariance=res.getBoolean("is_price_variance");
					
				}else{
					customerDiscount.setPercentage(true);
					customerDiscount.setPrice(custType.getDefPriceVariance());
				}
				if(!isPriceVariance && customerDiscount.getPrice()>0){
					saleItem.setFixedPrice(customerDiscount.getPrice());
					customerDiscount.setPrice(0);
				}
				
			} catch (SQLException e) {
				PosLog.write(this,"getItemPriceAsDiscountByCustType",e);
			}finally{
				try {
					res.close();
				} catch (SQLException e) {
					PosLog.write(this,"getItemPriceAsDiscountByCustType",e);
				}
			}
		}

		return customerDiscount;
	}

	public Double getItemPriceByCustType(BeanCustomerType custType, BeanSaleItem saleItem){

		final int custTypeId=custType.getId();
		final int saleItemId=saleItem.getId();
		Double priceVariance=null;
		String sql="SELECT price_variance_pc,is_percentage from v_customers_type_item_prices "+
				"where (customer_type_id='' or customer_type_id=" + custTypeId + ") and sale_item_id="+
				saleItemId;
		CachedRowSet res=executeQuery(sql);
		if(res!=null){
			try {
				if(res.next()){
					final boolean  isPercentage=res.getBoolean("is_percentage");
				 	if(isPercentage)
				 		priceVariance= saleItem.getFixedPrice()* res.getDouble("price_variance_pc")/100;
				 	else
				 		priceVariance=  res.getDouble("price_variance_pc");
					
				}else
					priceVariance=saleItem.getFixedPrice()* custType.getDefPriceVariance()/100;
			} catch (SQLException e) {
				PosLog.write(this,"getItemPriceByCustType",e);
			}finally{
				try {
					res.close();
				} catch (SQLException e) {
					PosLog.write(this,"getItemPriceByCustType",e);
				}
			}
		}

		return priceVariance;
	}

	public Double getItemPriceById(int saleItemId){
		double price=0;
		String where="id="+saleItemId;
		CachedRowSet res=getData(where);
		if(res!=null){
			try {
				if(res.next())
					price=res.getDouble("fixed_price");
				res.close();
			} catch (SQLException e) {
				PosLog.write(this,"getItemPriceById",e);
			}
		}

		return price;
	} 

	public BeanSaleItem getSaleItemByID(int id){
		String where="id="+id+"";
		CachedRowSet crs=getData(where);
		BeanSaleItem saleItem=null;
		if(crs!=null){
			try {
				if(crs.next())
					saleItem = createPosItemFromResult(crs);
			} catch (Exception e) {
				PosLog.write(this,"getSaleItemByID",e);
			}finally{
				try {
					crs.close();
				} catch (Exception e) {
					PosLog.write(this,"getSaleItemByID",e);
				}
			}
		}
		return saleItem;
	}

	public BeanSaleItem getSaleItemByCode(String code){
		String where="code='"+code+"'";
		CachedRowSet crs=getData(where);
		BeanSaleItem saleItem=null;
		if(crs!=null){
			try {
				if(crs.next())
					saleItem = createPosItemFromResult(crs);
			} catch (Exception e) {
				PosLog.write(this,"getSaleItemByCode",e);
			}finally{
				try {
					crs.close();
				} catch (Exception e) {
					PosLog.write(this,"getSaleItemByCode",e);
				}
			}
		}
		return saleItem;
	}



	// sub items...


	public ArrayList<BeanSaleItem> getSaleItemsByChoiceId(int choiceId) throws Exception{

		ArrayList<BeanSaleItem> itemList=null;
		final String cond=" choice_ids like '%,"+choiceId+",%'";
		itemList=createSubSaleItemList(cond);
		return itemList;

	}

	/**
	 * @param whereCond
	 * @return
	 * @throws Exception 
	 */
	private ArrayList<BeanSaleItem> createSubSaleItemList(String whereCond) throws Exception {

		final String sql="select si.id as id from " + mTablename + " si where " +whereCond;
		CachedRowSet crs=executeQuery(sql);
		BeanSaleItem item=null;
		ArrayList<BeanSaleItem> itemList=null;
		if(crs!=null){
			itemList=new ArrayList<BeanSaleItem>();
			try {
				while(crs.next()){
					item = getSubSaleItem(crs.getInt("id"));
					itemList.add(item);
				}
			} catch (Exception e) {
				PosLog.write(this,"createSubSaleItemList",e);
				throw e;
			}finally{
				try {
					crs.close();
				} catch (Exception e) {
					PosLog.write(this,"createSubSaleItemList",e);
					throw e;
				}
			}
		}
		return itemList;
	}

	public BeanSaleItem getSubSaleItem(String code) throws Exception{

		BeanSaleItem item=null;
		if(subSaleItemList.containsKey(code)){
			item=subSaleItemList.get(code);
		}else{
			final String whereCond="code="+code; 
			item=createSubSaleItem(whereCond);
		}
		return item;
	}

	public BeanSaleItem getSubSaleItem(int id) throws Exception{

		BeanSaleItem item=null;
		if(subSaleItemCodeMap.containsKey(id)){
			final String code=subSaleItemCodeMap.get(id);
			item=subSaleItemList.get(code);
		}else{
			final String whereCond="id="+id; 
			item=createSubSaleItem(whereCond);
		}
		return item;

	}


	/**
	 * @param whereCond
	 * @return
	 * @throws Exception 
	 */
	private BeanSaleItem createSubSaleItem(String whereCond) throws Exception {
		CachedRowSet crs=getData(whereCond);
		BeanSaleItem item=null;
		if(crs!=null){
			try {
				if(crs.next()){
					item=new BeanSaleItem();
					createPosItemFromResult(crs, item);
					subSaleItemList.put(item.getCode(), item);
					subSaleItemCodeMap.put(item.getId(), item.getCode());
				}
			} catch (Exception e) {
				PosLog.write(this,"createSubSaleItem",e);
				throw e;
			}finally{
				try {
					crs.close();
				} catch (Exception e) {
					PosLog.write(this,"createSubSaleItem",e);
					throw e;
				}
			}
		}
		return item;
	}

	/**
	 * @param crs
	 * @return
	 * @throws SQLException 
	 */
	private BeanSaleItem createSubSaleItemFromResult(CachedRowSet res) throws SQLException {

		BeanSaleItem item=new BeanSaleItem();
		item.setId(res.getInt("id"));
		item.setCode(res.getString("code"));
		item.setName(res.getString("name"));
		item.setAlternativeName(res.getString("alternative_name"));
		item.setNameToPrint(res.getString("name_to_print"));
		item.setAlternativeNameToPrint(res.getString("alternative_name_to_print"));
		item.setFixedPrice(Float.parseFloat(res.getString("fixed_price")));
		return item;
	}
}
