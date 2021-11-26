/**
 * 
 */
package com.indocosmo.pos.common.beans.settings.ui.orderlist;

import java.util.ArrayList;

import com.indocosmo.pos.common.enums.RefundAdjustmentType;
import com.indocosmo.pos.common.utilities.PosNumberUtil;
import com.indocosmo.pos.data.beans.BeanOrderRefund.RefundType;
/**
 * @author sandhya
 *
 */
public class BeanUIOrderRefundSetting {
 
	public static final String  REFUND_TYPE="order_list_form.refund_form.refund_type";
	public static final String  REFUND_ADJUSTMENT_METHOD="order_list_form.refund_form.refund_adjustment_method";
	
	private ArrayList<RefundAdjustmentType> mRefundAdjustmentMethods;
	private RefundType mRefundType;
 	
	
	/**
	 * @return the mRefundType
	 */
	public RefundType getRefundType() {
		return mRefundType;
	}


	/**
	 * @param mRefundType the mRefundType to set
	 */
	public void setRefundType(RefundType mRefundType) {
		this.mRefundType = mRefundType;
	}


	/**
	 * @return the mRefundAdjustmentMethods
	 */
	public ArrayList<RefundAdjustmentType> getRefundAdjustmentMethods() {
		return mRefundAdjustmentMethods;
	}


	/**
	 * @param mRefundAdjustmentMethods the mRefundAdjustmentMethods to set
	 */
	public void setRefundAdjustmentMethods(String refundAdjustmentMethods) {
		
		mRefundAdjustmentMethods=new ArrayList<RefundAdjustmentType>();
		
		if (refundAdjustmentMethods==null || refundAdjustmentMethods.trim().equals("") || refundAdjustmentMethods.trim().equals("0"))
			mRefundAdjustmentMethods.add(RefundAdjustmentType.NONE);
		else
			for(String adjustmentType:refundAdjustmentMethods.trim().split(",")){
				
				if (PosNumberUtil.parseIntegerSafely(adjustmentType)==RefundAdjustmentType.QUANTITY.getCode())
					mRefundAdjustmentMethods.add(RefundAdjustmentType.QUANTITY);
				else if (PosNumberUtil.parseIntegerSafely(adjustmentType)==RefundAdjustmentType.AMOUNT.getCode())
					mRefundAdjustmentMethods.add(RefundAdjustmentType.AMOUNT);
			}
			
		
	}
	
}
