/**
 * 
 */
package com.indocosmo.pos.data.beans;

/**
 * @author jojesh-13.2
 *
 */
public class BeanServingSeat implements Cloneable {

	
	private Integer seatNo;
	private boolean isSelected=true;


	/* (non-Javadoc)
     * @see java.lang.Object#clone()
     */
    @Override
    public BeanServingSeat clone() throws CloneNotSupportedException {
    	
        BeanServingSeat cloneObject = null;
        cloneObject = (BeanServingSeat) super.clone();

        return cloneObject;
    }


	/**
	 * @return the seatNo
	 */
	public Integer getSeatNo() {
		return seatNo;
	}


	/**
	 * @param seatNo the seatNo to set
	 */
	public void setSeatNo(Integer seatNo) {
		this.seatNo = seatNo;
	}


	/**
	 * @return the isSelected
	 */
	public boolean isSelected() {
		return isSelected;
	}


	/**
	 * @param isSelected the isSelected to set
	 */
	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}
}
