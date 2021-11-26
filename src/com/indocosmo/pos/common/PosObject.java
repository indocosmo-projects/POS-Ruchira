/**
 * 
 */
package com.indocosmo.pos.common;

/**
 * @author joe.12.3
 *
 */
public class PosObject implements IPosObject{

	private PosObject mPosParent;
	/**
	 * 
	 */
	public PosObject() {
		// TODO Auto-generated constructor stub
	}
	/* (non-Javadoc)
	 * @see com.indocosmo.pos.common.IPosObject#setPosParent(com.indocosmo.pos.common.PosObject)
	 */
	@Override
	public void setPosParent(PosObject posParent) {
		mPosParent=posParent;
		
	}
	/* (non-Javadoc)
	 * @see com.indocosmo.pos.common.IPosObject#getPosParent()
	 */
	@Override
	public PosObject getPosParent() {
		// TODO Auto-generated method stub
		return mPosParent;
	}
	
}


