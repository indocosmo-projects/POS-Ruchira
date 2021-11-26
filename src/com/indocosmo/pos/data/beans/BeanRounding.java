/**
 * 
 */
package com.indocosmo.pos.data.beans;


/**
 * @author jojesh
 *
 */
public final class BeanRounding {

	private int mId;					
	private String mCode;
	private String mName;	
	private String mDescription;					
	private float mRoundTo;
	

	public int getId() {
		return mId;
	}

	public void setId(int id) {
		this.mId = id;
	}

	public String getCode() {
		return mCode;
	}

	public void setCode(String code) {
		this.mCode = code;
	}
	
	public String getName() {
		return mName;
	}

	public void setName(String name) {
		this.mName = name;
	}

	public float getRoundTo() {
		return mRoundTo;
	}

	public void setRoundTo(float roundTo) {
		this.mRoundTo = roundTo;
	}

	public String getDescription() {
		return mDescription;
	}

	public void setDescription(String description) {
		this.mDescription = description;
	}
	
//	public double roundTo(double amount){
//		return PosNumberUtil.nRound(amount, mRoundTo);
//	}
	
}
