/**
 * 
 */
package com.indocosmo.pos.data.beans;

import com.indocosmo.pos.common.utilities.reflecion.PosBeanUtil;

/**
 * @author jojesh-13.2
 *
 */
public class BeanOrderServingTable extends BeanServingTable{

	private BeanServingSeat selectedSeat;
	private boolean isSelected;
	private boolean isVoid=false;
	
	public BeanOrderServingTable(){
		
	}
	
	public BeanOrderServingTable(BeanServingTable table) throws Exception{
		
		PosBeanUtil.copyProperties(table,this);
	}

	/**
	 * @return the selectedSeat
	 */
	public BeanServingSeat getSelectedSeat() {
		return selectedSeat;
	}

	/**
	 * @return the isSelected
	 */
	public boolean IsSelected() {
		return isSelected;
	}


	/**
	 * @param isSelected the isSelected to set
	 */
	public void setSelected(Boolean isSelected) {
		this.isSelected = isSelected;
	}


	/**
	 * @return the isVoid
	 */
	public boolean isVoid() {
		return isVoid;
	}


	/**
	 * @param isVoid the isVoid to set
	 */
	public void setVoid(Boolean isVoid) {
		this.isVoid = isVoid;
	}
	

}
