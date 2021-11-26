/**
 * This class will handle all the Taxes table interactions
 */
package com.indocosmo.pos.data.providers.shopdb;

import java.sql.ResultSet;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import com.indocosmo.pos.common.PosEnvSettings;
import com.indocosmo.pos.common.PosLog;
import com.indocosmo.pos.data.beans.BeanTax;
import com.indocosmo.pos.data.beans.BeanTaxGlobalParam;


/**
 * @author jojesh
 *
 */
public class PosTaxItemProvider extends PosShopDBProviderBase {

//	final static  BeanTaxGlobalParam mTaxParam=PosEnvSettings.getInstance().getTaxParam();
	public final static String NO_TAXCODE="NOTAX";
	public enum TaxCalculationMethod{

		InclusiveOfTax(0),
		ExclusiveOfTax(1),
		None(2);

		private static final Map<Integer,TaxCalculationMethod> mLookup=new HashMap<Integer,TaxCalculationMethod>();

		static {
			for(TaxCalculationMethod tcm:EnumSet.allOf(TaxCalculationMethod.class))
				mLookup.put(tcm.getCode(), tcm);
		}

		private int mCode;
		private TaxCalculationMethod(Integer code){
			mCode=code;
		}

		/**
		 *  Return internal representation code
		 * @return integer 
		 */
		public int getCode(){
			return mCode;
		}

		/**
		 * Return actual enum
		 * @param code
		 * @return
		 */
		public static TaxCalculationMethod get(Integer code){
			return mLookup.get(code);
		}

	}


	private static PosTaxItemProvider mInstance;

	private Map<Integer, BeanTax> taxIdMap;

	public static PosTaxItemProvider getInstance(){

		if(mInstance==null)
			mInstance=new PosTaxItemProvider();

		return mInstance;
	}


	/**
	 * Default constructor
	 */
	private PosTaxItemProvider() {
		super("taxes");

		taxIdMap=new HashMap<Integer, BeanTax>();

	}

	/**
	 * Returns PosSalitem whose id is passed
	 * @param id : id of the sale item tax 
	 * @return PosTaxItem
	 */
	public BeanTax getTaxItem(int id){
		if (id==0) return null;
		BeanTax item=null;
		try {

			if(taxIdMap.containsKey(id))

				item=taxIdMap.get(id).clone();

			else{

				final String where="id="+String.valueOf(id);
				ResultSet res=getData(where);

				res.next();
				item=createTaxItemFromResult(res);
				taxIdMap.put(item.getId(), item.clone());

			} 
		}catch (Exception e) {
			
			PosLog.write(this,"getTaxItem",e);
		}

		return item;
	}

	/**
	 * Builds the PosTaxItem from the result set passed
	 * @param res the result set containing tax item definition
	 * @return PosTaxItem
	 */
	private BeanTax createTaxItemFromResult(ResultSet res){
		BeanTax item=null;

		try {
			
			final BeanTaxGlobalParam mTaxParam=PosEnvSettings.getInstance().getTaxParam();

			item=new BeanTax();
			item.setId(res.getInt("id"));
			item.setName(res.getString("name"));
			item.setCode(res.getString("code"));

			item.setTaxOneName(mTaxParam.getTax1Name());
			item.setTaxOneApplicable(res.getBoolean("is_tax1_applicable"));
			item.setTaxOnePercentage(res.getDouble("tax1_percentage"));
			item.setTax1_refund_rate(res.getDouble("tax1_refund_rate"));

			item.setTaxTwoName(mTaxParam.getTax2Name());
			item.setTaxTwoApplicable(res.getBoolean("is_tax2_applicable"));
			item.setTaxTwoPercentage(res.getDouble("tax2_percentage"));
			item.setTax2_refund_rate(res.getDouble("tax2_refund_rate"));

			item.setTaxThreeName(mTaxParam.getTax3Name());
			item.setTaxThreeApplicable(res.getBoolean("is_tax3_applicable"));
			item.setTaxThreePercentage(res.getDouble("tax3_percentage"));
			item.setTax3_refund_rate(res.getDouble("tax3_refund_rate"));

			item.setServiceTaxName(mTaxParam.getServiceTaxName());
			item.setServiceTaxApplicable(res.getBoolean("is_sc_applicable"));
			item.setServiceTaxPercentage(res.getDouble("sc_percentage"));
			item.setService_tax_refund_rate(res.getDouble("sc_refund_rate"));

			item.setGSTName(mTaxParam.getGSTName());
			item.setGSTDefined(res.getBoolean("is_define_gst"));
			item.setGSTPercentage(res.getDouble("gst_percentage"));
			item.setGst_refund_rate(res.getDouble("gst_refund_rate"));

			item.setTaxOneIncludeInGST(res.getBoolean("is_tax1_included_in_gst"));
			item.setTaxTwoIncludeInGST(res.getBoolean("is_tax2_included_in_gst"));
			item.setTaxThreeIncludeInGST(res.getBoolean("is_tax3_included_in_gst"));
			item.setServiceTaxIncludeInGST(res.getBoolean("is_sc_included_in_gst"));

		} catch (Exception e) {
			
			PosLog.write(this,"createTaxItemFromResult",e);
			item=null;
		}
		return item;
	}


}
