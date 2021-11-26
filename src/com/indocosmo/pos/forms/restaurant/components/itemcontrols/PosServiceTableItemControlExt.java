/**
 * 
 */
package com.indocosmo.pos.forms.restaurant.components.itemcontrols;

import javax.swing.ImageIcon;

import com.indocosmo.pos.common.utilities.PosResUtil;
import com.indocosmo.pos.data.beans.BeanServingTable;
import com.indocosmo.pos.data.beans.BeanServingTableExt;

/**
 * @author jojesh
 *
 */
@SuppressWarnings("serial")
public final class PosServiceTableItemControlExt extends
		PosServiceTabletemControl {
	
	private static final String IMAGE_BUTTON_NORMAL_USED="service_table_used.png";
	protected static ImageIcon mImageIconUsed=null;

	public boolean isUsed(){
		if(mServiceTableItem!=null)
			return ((BeanServingTableExt) mServiceTableItem).isUsed();
		else
			return false;
	}
	
	@Override
	protected void loadItemImages() {
			if(mImageIconUsed==null)
				mImageIconUsed=PosResUtil.getImageIconFromResource(IMAGE_BUTTON_NORMAL_USED);
		super.loadItemImages();
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.pos.forms.restaurant.components.itemcontrols.PosServiceTabletemControl#getNormalImage()
	 */
	@Override
	protected ImageIcon getNormalImage() {
		if(!isUsed())
			return super.getNormalImage();
		else
			return mImageIconUsed;
	}
	
	/* (non-Javadoc)
	 * @see com.indocosmo.pos.forms.restaurant.components.itemcontrols.PosServiceTabletemControl#setServiceTable(com.indocosmo.pos.dataobjects.PosServiceTableObject)
	 */
	@Override
	public void setServiceTable(BeanServingTable item) {
		this.mServiceTableItem = item;
		if(mServiceTableItem!=null){
			setText(((BeanServingTableExt)item).getDisplayText(true));
			setVisible(true);
		}
		else{
			setText("");
			setVisible(false);
		}
		
		setImages();
	}
	

}
