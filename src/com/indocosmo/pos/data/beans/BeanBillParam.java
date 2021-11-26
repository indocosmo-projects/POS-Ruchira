/**
 * 
 */
package com.indocosmo.pos.data.beans;

/**
 * @author jojesh
 *
 */
public final class BeanBillParam {
	
	public static int SHOW_TAX_SUMMARY=0;
	public static int SHOW_TAX_TABLE=1;
	public static int SHOW_TAX_NONE=2;
	
	private int 	showTaxSummary;
	private boolean	mCanShowDiscountSummary;
	private boolean mCanShowItemTax;
	private boolean mCanShowItemDiscount;
	private String	mHeaderLine1;
	private String	mHeaderLine2;
	private String	mHeaderLine3;
	private String	mHeaderLine4;
	private String	mHeaderLine5;
	private String	mHeaderLine6;
	private String	mHeaderLine7;
	private String	mHeaderLine8;
	private String	mHeaderLine9;
	private String	mHeaderLine10;
	
	
	private String	mFooterLine1;
	private String	mFooterLine2;
	private String	mFooterLine3;
	private String	mFooterLine4;
	private String	mFooterLine5;
	private String	mFooterLine6;
	private String	mFooterLine7;
	private String	mFooterLine8;
	private String	mFooterLine9;
	private String	mFooterLine10;
	
	private BeanTax mTax;
	private BeanRounding mRounding;
	
	/**
	 * @return the mCanShowTaxSummary
	 */
	public int getTaxSummaryDisplayType() {
		return showTaxSummary;
	}
	/**
	 * @param canShowTaxSummary the mCanShowTaxSummary to set
	 */
	public void setTaxSummaryDisplayType(int type) {
		this.showTaxSummary = type;
	}
	/**
	 * @return the mCanShowDiscountSummary
	 */
	public boolean canShowDiscountSummary() {
		return mCanShowDiscountSummary;
	}
	/**
	 * @param canShowDiscountSummary the mCanShowDiscountSummary to set
	 */
	public void setShowDiscountSummary(boolean canShowDiscountSummary) {
		this.mCanShowDiscountSummary = canShowDiscountSummary;
	}
	/**
	 * @return the mCanShowItemTax
	 */
	public boolean canShowItemTax() {
		return mCanShowItemTax;
	}
	/**
	 * @param canShowItemTax the mCanShowItemTax to set
	 */
	public void setShowItemTax(boolean canShowItemTax) {
		this.mCanShowItemTax = canShowItemTax;
	}
	/**
	 * @return the mCanShowItemDiscount
	 */
	public boolean canShowItemDiscount() {
		return mCanShowItemDiscount;
	}
	/**
	 * @param canShowItemDiscount the canShowItemDiscount to set
	 */
	public void setShowItemDiscount(boolean canShowItemDiscount) {
		this.mCanShowItemDiscount = canShowItemDiscount;
	}
	/**
	 * @return the mHeaderLine1
	 */
	public String getHeaderLine1() {
		return mHeaderLine1;
	}
	/**
	 * @param billHeaderLine1 the mHeaderLine1 to set
	 */
	public void setHeaderLine1(String billHeaderLine1) {
		this.mHeaderLine1 = billHeaderLine1;
	}
	/**
	 * @return the mHeaderLine2
	 */
	public String getHeaderLine2() {
		return mHeaderLine2;
	}
	/**
	 * @param mHeaderLine2 the mHeaderLine2 to set
	 */
	public void setHeaderLine2(String billHeaderLine2) {
		this.mHeaderLine2 = billHeaderLine2;
	}
	/**
	 * @return the mHeaderLine3
	 */
	public String getHeaderLine3() {
		return mHeaderLine3;
	}
	/**
	 * @param billHeaderLine3 the mHeaderLine3 to set
	 */
	public void setHeaderLine3(String billHeaderLine3) {
		this.mHeaderLine3 = billHeaderLine3;
	}
	/**
	 * @return the mHeaderLine4
	 */
	public String getHeaderLine4() {
		return mHeaderLine4;
	}
	/**
	 * @param billHeaderLine4 the mHeaderLine4 to set
	 */
	public void setHeaderLine4(String billHeaderLine4) {
		this.mHeaderLine4 = billHeaderLine4;
	}
	/**
	 * @return the mFooterLine1
	 */
	public String getFooterLine1() {
		return mFooterLine1;
	}
	/**
	 * @param billFooterLine1 the mFooterLine1 to set
	 */
	public void setFooterLine1(String billFooterLine1) {
		this.mFooterLine1 = billFooterLine1;
	}
	/**
	 * @return the mFooterLine2
	 */
	public String getFooterLine2() {
		return mFooterLine2;
	}
	/**
	 * @param mFooterLine2 the mFooterLine2 to set
	 */
	public void setFooterLine2(String billFooterLine2) {
		this.mFooterLine2 = billFooterLine2;
	}
	/**
	 * @return the mFooterLine3
	 */
	public String getFooterLine3() {
		return mFooterLine3;
	}
	/**
	 * @param mFooterLine3 the mFooterLine3 to set
	 */
	public void setFooterLine3(String billFooterLine3) {
		this.mFooterLine3 = billFooterLine3;
	}
	/**
	 * @return the mFooterLine4
	 */
	public String getFooterLine4() {
		return mFooterLine4;
	}
	/**
	 * @param billFooterLine4 the mFooterLine4 to set
	 */
	public void setFooterLine4(String billFooterLine4) {
		this.mFooterLine4 = billFooterLine4;
	}
	/**
	 * @return the mTax
	 */
	public BeanTax getTax() {
		return mTax;
	}
	/**
	 * @param billTax the mTax to set
	 */
	public void setTax(BeanTax billTax) {
		this.mTax = billTax;
	}
	/**
	 * @return the mRounding
	 */
	public BeanRounding getRounding() {
		return mRounding;
	}
	/**
	 * @param rounding the mRounding to set
	 */
	public void setRounding(BeanRounding rounding) {
		this.mRounding = rounding;
	}
	/**
	 * @return the mFooterLine5
	 */
	public String getFooterLine5() {
		return mFooterLine5;
	}
	/**
	 * @param mFooterLine5 the mFooterLine5 to set
	 */
	public void setFooterLine5(String mFooterLine5) {
		this.mFooterLine5 = mFooterLine5;
	}
	/**
	 * @return the mFooterLine6
	 */
	public String getFooterLine6() {
		return mFooterLine6;
	}
	/**
	 * @param mFooterLine6 the mFooterLine6 to set
	 */
	public void setFooterLine6(String mFooterLine6) {
		this.mFooterLine6 = mFooterLine6;
	}
	/**
	 * @return the mFooterLine7
	 */
	public String getFooterLine7() {
		return mFooterLine7;
	}
	/**
	 * @param mFooterLine7 the mFooterLine7 to set
	 */
	public void setFooterLine7(String mFooterLine7) {
		this.mFooterLine7 = mFooterLine7;
	}
	/**
	 * @return the mFooterLine8
	 */
	public String getFooterLine8() {
		return mFooterLine8;
	}
	/**
	 * @param mFooterLine8 the mFooterLine8 to set
	 */
	public void setFooterLine8(String mFooterLine8) {
		this.mFooterLine8 = mFooterLine8;
	}
	/**
	 * @return the mFooterLine9
	 */
	public String getFooterLine9() {
		return mFooterLine9;
	}
	/**
	 * @param mFooterLine9 the mFooterLine9 to set
	 */
	public void setFooterLine9(String mFooterLine9) {
		this.mFooterLine9 = mFooterLine9;
	}
	/**
	 * @return the mFooterLine10
	 */
	public String getFooterLine10() {
		return mFooterLine10;
	}
	/**
	 * @param mFooterLine10 the mFooterLine10 to set
	 */
	public void setFooterLine10(String mFooterLine10) {
		this.mFooterLine10 = mFooterLine10;
	}
	/**
	 * @return the mHeaderLine5
	 */
	public String getHeaderLine5() {
		return mHeaderLine5;
	}
	/**
	 * @param mHeaderLine5 the mHeaderLine5 to set
	 */
	public void setHeaderLine5(String mHeaderLine5) {
		this.mHeaderLine5 = mHeaderLine5;
	}
	/**
	 * @return the mHeaderLine6
	 */
	public String getHeaderLine6() {
		return mHeaderLine6;
	}
	/**
	 * @param mHeaderLine6 the mHeaderLine6 to set
	 */
	public void setHeaderLine6(String mHeaderLine6) {
		this.mHeaderLine6 = mHeaderLine6;
	}
	/**
	 * @return the mHeaderLine7
	 */
	public String getHeaderLine7() {
		return mHeaderLine7;
	}
	/**
	 * @param mHeaderLine7 the mHeaderLine7 to set
	 */
	public void setHeaderLine7(String mHeaderLine7) {
		this.mHeaderLine7 = mHeaderLine7;
	}
	/**
	 * @return the mHeaderLine8
	 */
	public String getHeaderLine8() {
		return mHeaderLine8;
	}
	/**
	 * @param mHeaderLine8 the mHeaderLine8 to set
	 */
	public void setHeaderLine8(String mHeaderLine8) {
		this.mHeaderLine8 = mHeaderLine8;
	}
	/**
	 * @return the mHeaderLine9
	 */
	public String getHeaderLine9() {
		return mHeaderLine9;
	}
	/**
	 * @param mHeaderLine9 the mHeaderLine9 to set
	 */
	public void setHeaderLine9(String mHeaderLine9) {
		this.mHeaderLine9 = mHeaderLine9;
	}
	/**
	 * @return the mHeaderLine10
	 */
	public String getHeaderLine10() {
		return mHeaderLine10;
	}
	/**
	 * @param mHeaderLine10 the mHeaderLine10 to set
	 */
	public void setHeaderLine10(String mHeaderLine10) {
		this.mHeaderLine10 = mHeaderLine10;
	}
	


}
