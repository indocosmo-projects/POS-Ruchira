package com.indocosmo.pos.forms.components.orderentry.itemedit;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.border.MatteBorder;

@SuppressWarnings("serial")
public class PosSelectedOptionItemControl extends JLabel {
	
	public static final int LAYOUT_HEIGHT=50;
	public static final int LAYOUT_WIDTH=20;
	private static final Color SELECTED_ITEM_BG_COLOR=new Color(78,128,188);
	private static final MatteBorder NORMAL_BORDER=new MatteBorder(1,1,1,1,Color.LIGHT_GRAY);
	private static final MatteBorder SELECTED_BORDER=new MatteBorder(1,1,1,1,new Color(4,27,65) );
	private int mSelectedAttribIndex;
	private int mSelectedOptionIndex;
	private int mItemIndex;
	private Boolean mIsSelected=false;
	private IPosOptionListItemControlListner mOptionListItemControlListener;
	
	
	/**
	 * 
	 */
	public PosSelectedOptionItemControl(){
		super();
		initComponets();
	}
	
	/**
	 * 
	 */
	private void initComponets(){
		setSize(new Dimension(LAYOUT_WIDTH,LAYOUT_HEIGHT ));
		setBorder(NORMAL_BORDER);
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				mOptionListItemControlListener.onSelected(getSelectedAttribIndex());				
			}
		});		
	}
	
	/**
	 * @return the mSelectedAttribIndex
	 */
	public final int getSelectedAttribIndex() {
		return mSelectedAttribIndex;
	}
	
	/**
	 * @param mSelectedAttribIndex the mSelectedAttribIndex to set
	 */
	public final void setSelectedAttribIndex(int selectedAttribIndex) {
		this.mSelectedAttribIndex = selectedAttribIndex;
	}
	
	/**
	 * @return the mSelectedItemIndex
	 */
	public final int getSelectedOptionIndex() {
		return mSelectedOptionIndex;
	}
	
	/**
	 * @param mSelectedItemIndex the mSelectedItemIndex to set
	 */
	public final void setSelectedOptionIndex(int selectedItemIndex) {
		this.mSelectedOptionIndex = selectedItemIndex;
	}

	/**
	 * @return the mItemIndex
	 */
	public final int getItemIndex() {
		return mItemIndex;
	}

	/**
	 * @param mItemIndex the mItemIndex to set
	 */
	public final void setItemIndex(int itemIndex) {
		this.mItemIndex = itemIndex;
	}
	
	
	/**
	 * @param selected
	 */
	public void setSelected(Boolean selected){		
		if(mIsSelected==selected)return;
		mIsSelected=selected;
		setOpaque(true);
		setBackground((selected)?SELECTED_ITEM_BG_COLOR:Color.WHITE);
		setBorder((selected)?SELECTED_BORDER:NORMAL_BORDER);
		if(mIsSelected)
			if(mOptionListItemControlListener!=null)
				mOptionListItemControlListener.onSelected(getSelectedAttribIndex());
	}
	
	/**
	 * @param oPtionListItemControlListner
	 */
	public void setOptionListItemControl(IPosOptionListItemControlListner oPtionListItemControlListner){
		mOptionListItemControlListener=oPtionListItemControlListner;
	}
	
	/**
	 * @author jojesh
	 *
	 */
	public interface IPosOptionListItemControlListner{
		public void onSelected(int index);
	}

	
}
