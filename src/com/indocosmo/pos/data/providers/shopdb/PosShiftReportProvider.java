/**
 * 
 */
package com.indocosmo.pos.data.providers.shopdb;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.rowset.CachedRowSet;

import com.indocosmo.pos.common.PosEnvSettings;
import com.indocosmo.pos.common.PosLog;
import com.indocosmo.pos.common.enums.PosItemDisplayStyle;
import com.indocosmo.pos.common.enums.PosOrderServiceTypes;
import com.indocosmo.pos.common.utilities.PosImageUtils;
import com.indocosmo.pos.common.utilities.PosOrderUtil;
import com.indocosmo.pos.common.utilities.PosTaxUtil;
import com.indocosmo.pos.common.utilities.PosUomUtil;
import com.indocosmo.pos.data.beans.BeanOrderDetail;
import com.indocosmo.pos.data.beans.BeanOrderDetailPS;
import com.indocosmo.pos.data.beans.BeanOrderHeader;
import com.indocosmo.pos.data.beans.BeanSaleItem;
import com.indocosmo.pos.data.beans.BeanSaleItemCombo;
import com.indocosmo.pos.data.beans.BeanServingTable;
import com.indocosmo.pos.data.beans.BeanTax;
import com.indocosmo.pos.data.beans.BeanOrderDetail.OrderDetailItemType;
import com.indocosmo.pos.data.beans.BeanOrderHeader.PosOrderStatus;
import com.indocosmo.pos.data.providers.shopdb.PosTaxItemProvider.TaxCalculationMethod;

/**
 * @author sandhya
 *
 */
public class PosShiftReportProvider  extends PosShopDBProviderBase {

	private PosUOMProvider mUOMProvider;
	private PosUsersProvider mUserProvider;
 
	/**
	 * 
	 */
	public PosShiftReportProvider() {
		
		super("order_id","v_void_item_dtls");
		mUserProvider=new PosUsersProvider();
		
	}
	
	public ArrayList<BeanOrderHeader> getVoidItemList(String where,boolean voidOrdersonly) throws SQLException{
		 
		ArrayList<BeanOrderHeader> hdrList=null;
		
		String sql="SELECT " +
				" hdr.order_id ,hdr.invoice_no AS invoice_no, order_queue.id as queue_no, hdr.status, "+ 
				" dtl.name,dtl.qty,dtl.item_total	,dtl.uom_code, " + 
				"  parent_dtl_id,item_type,dtl.id,is_combo_item,is_void ,hdr.service_type,hdr.serving_table_id " ;
		sql=sql + (voidOrdersonly? ",hdr.void_by , hdr.void_at" :",dtl.void_by , dtl.void_at");
		sql=sql + 	" FROM order_hdrs hdr " + 
				" LEFT JOIN order_queue ON hdr.order_id = order_queue.order_id " + 
				" LEFT JOIN order_dtls dtl ON hdr.order_id = dtl.order_id  " + 
				" LEFT JOIN users ON dtl.void_by=users.id WHERE " ;
		if(voidOrdersonly){
			sql=sql +  " hdr.status=" + PosOrderStatus.Void.getCode();
		}else{
			sql=sql +  " dtl.is_void = 1 AND hdr.status in "
				+ "("+PosOrderStatus.Closed.getCode()+","+PosOrderStatus.Refunded.getCode()+")";	
		}
		
		sql=sql + ((where!=null && !where.equals(""))?" AND " + where.replace("service_type", "hdr.service_type") :"");
		
		CachedRowSet crs=executeQuery(sql);
		try{
			String orderId=null;
			
			if(crs!=null){
				hdrList=new ArrayList<BeanOrderHeader>();
				BeanOrderHeader orderHdr=null;
				while(crs.next()){
					final String tmpOrderId=crs.getString("order_id");
					if(orderId==null || !orderId.equals(tmpOrderId)){
						if(orderId!=null){
							hdrList.add(orderHdr);
						}
						 orderHdr=getOrderHdrsDataFromRecordSet(crs);
						 orderId=crs.getString("order_id");
					}
					BeanOrderDetail ordDtl= getOrderDetailsDataFromRecordSet(crs);
					if(orderHdr.getOrderDetailItems()==null)
						orderHdr.setOrderDetailItems(new ArrayList<BeanOrderDetail>());
					orderHdr.getOrderDetailItems().add(ordDtl);
				}		
				crs.close();
				if(orderId!=null && orderHdr!=null){
					hdrList.add(orderHdr);
				}
			}
		}catch (Exception ex){
			PosLog.write(this, "getVoidHdrData", ex);
		}
		return hdrList;	
	}
	/**
	 * @param crs
	 * @return
	 * @throws Exception
	 */
	private BeanOrderHeader getOrderHdrsDataFromRecordSet(CachedRowSet crs) throws Exception{
		
		BeanOrderHeader orderHdr=new BeanOrderHeader();
		
		orderHdr.setOrderId(crs.getString("order_id"));
			
		orderHdr.setInvoiceNo(PosOrderUtil.getFormatedInvoiceNumber( crs.getInt("invoice_no")) );	
		orderHdr.setStatus(PosOrderStatus.get(crs.getInt("status")));
		orderHdr.setUser(mUserProvider.getUserByID(crs.getInt("void_by")));
		
		
		Integer queueNo=   crs.getInt("queue_no");
		PosOrderServiceTypes serviceType=PosOrderServiceTypes.get(crs.getInt("service_type"));
		if(serviceType== PosOrderServiceTypes.TABLE_SERVICE){
			
			BeanServingTable servingTable=(new PosOrderServingTableProvider()).getTableByID(crs.getInt("serving_table_id"));
			orderHdr.setQueueNo(PosOrderUtil.getFormatedOrderQueueNo( queueNo,serviceType,servingTable ));
		}else
			orderHdr.setQueueNo(PosOrderUtil.getFormatedOrderQueueNo( queueNo,serviceType,null ));
		
		return orderHdr;
		
	}
	
//	public ArrayList<BeanOrderDetail> getOrderDetailData(CachedRowSet crs) throws SQLException{
//	 
//		
// 
//		try{
//			if(crs!=null){
//				detailList=new ArrayList<BeanOrderDetail>();
//				while(crs.next()){
//					BeanOrderDetail orderDetail=getOrderDetailsDataFromRecordSet(crs);
//					detailList.add(orderDetail);
//				}		
//				crs.close();
//			}
//		}catch (Exception ex){
//			PosLog.write(this, "getOrderDetailData", ex);
//		}
//		return detailList;	
//	}
//	
	/**
	 * @param crs
	 * @return
	 * @throws Exception
	 */
	private BeanOrderDetail getOrderDetailsDataFromRecordSet(CachedRowSet crs ) throws Exception{
 
//		PosOrderDtlProvider ordDtlProvider=new PosOrderDtlProvider();
		
		BeanOrderDetail orderDetail=new BeanOrderDetail();
		orderDetail.setId(crs.getString("id"));
		orderDetail.setOrderId(crs.getString("order_id"));
		orderDetail.setSaleItem(createSaleItem(orderDetail.getId(),crs));
		orderDetail.setVoid(crs.getBoolean("is_void"));
		orderDetail.setVoidBy(mUserProvider.getUserByID(crs.getInt("void_by")));
		orderDetail.setVoidAt(crs.getString("void_at"));
		orderDetail.setParentDtlId(crs.getString("parent_dtl_id"));
		orderDetail.setItemType(OrderDetailItemType.get(crs.getInt("item_type")));
		
//		/*
//		 * if is a sub item then
//		 * set the properties
//		 */
//		if(orderDetail.getParentDtlId()!=null){
//
//			if(orderDetail.getItemType()==OrderDetailItemType.EXTRA_ITEM){
//				orderDetail.setSaleItemChoice(ordDtlProvider.getSaleItemChoice(orderDetail.getId()));
//			}else if(orderDetail.getItemType()==OrderDetailItemType.COMBO_CONTENT_ITEM){
//				orderDetail.setComboContentItem(ordDtlProvider.getComboContentItem(orderDetail.getId()));
//			}
//
//		}
//
//		/*
//		 * loads the sub items if any 
//		 */
//
//		if(crs.getBoolean("has_choice_items"))
//			orderDetail.setExtraItemList(ordDtlProvider.loadExtras(orderDetail.getId()));
//		if(crs.getBoolean("has_combo_content_items"))
//			orderDetail.setComboSubstitutes(ordDtlProvider.loadComboContents(orderDetail.getId()));
		
		 
		return orderDetail;
	}

	private BeanSaleItem createSaleItem(String orderDtlId,CachedRowSet res) throws Exception{

		BeanSaleItem item=(res.getBoolean("is_combo_item"))?new BeanSaleItemCombo():new BeanSaleItem();
		mUOMProvider=PosUOMProvider.getInstance();
//		item.setId(res.getInt("sale_item_id"));
//		item.setCode(res.getString("sale_item_code"));
		item.setName(res.getString("name"));
		item.setUom(mUOMProvider.getUom(res.getString("uom_code")));
		item.setQuantity(res.getDouble("qty"));
		item.setComboItem(res.getBoolean("is_combo_item"));
		
//		item.setSubClass(PosSubItemClassProvider.getInstance().getClassItem(res.getInt("sub_class_id")));
//		
//		item.setFixedPrice(Float.parseFloat(res.getString("fixed_price")));
		item.setItemTotal(Float.parseFloat(res.getString("item_total")));
//		item.setCustomerPrice(res.getDouble("customer_price_variance"));
		
//
//		for(int index=0; index<BeanSaleItem.ITEM_ATTRIBUTE_COUNT; index++){
//			String attributeName= res.getString("attrib"+String.valueOf(index+1)+"_name");
//			String attributeOptions =res.getString("attrib"+String.valueOf(index+1)+"_options");
//			String selectedAttribute = res.getString("attrib"+String.valueOf(index+1)+"_selected_option");
//			if(attributeName!=null&&!attributeName.equals(""))
//				item.addAttribute(attributeName,attributeOptions,selectedAttribute);
//			else
//				break;
//		}
		return item;
	}

}
