package com.indocosmo.pos.forms.components.orderentry.itemedit;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.MatteBorder;

import com.indocosmo.pos.common.utilities.PosFormUtil;
import com.indocosmo.pos.common.utilities.PosResUtil;
import com.indocosmo.pos.data.beans.BeanSaleItemAttribute;
import com.indocosmo.pos.forms.components.buttons.PosSelectButton;
import com.indocosmo.pos.forms.components.buttons.listners.IPosSelectButtonListner;

@SuppressWarnings("serial")
public final class PosAttributeOptionListContainer extends JPanel{
 
	private final static int PANEL_CONTENT_H_GAP=8;
	private final static int PANEL_CONTENT_V_GAP=8;
	private static final int IMAGE_BUTTON_WIDTH=130;
	private static final int IMAGE_BUTTON_HEIGHT=87;	
//	private static final int DISPLAY_COLS=3;
//	private static final int DISPLAY_ROWS=((int)BeanSaleItem.ITEM_ATTRIBUTE_OPTION_MAX_COUNT+1/DISPLAY_COLS);
	public static final int LAYOUT_HEIGHT=PosOrderItemAttributeContainer.LAYOUT_HEIGHT;
	public static final int LAYOUT_WIDTH=PANEL_CONTENT_H_GAP*4+IMAGE_BUTTON_WIDTH*2;
	private static final String IMAGE_BUTTON_NORMAL="attribute_option_item.png";
	private static final String IMAGE_BUTTON_TOUCHED="attribute_option_item_touch.png";
	private static final String IMAGE_DEF_BUTTON_NORMAL="attribute_def_option_item.png";
	private static final String IMAGE_DEF_BUTTON_TOUCHED="attribute_def_option_item_touch.png";

	private IPosOptionListContainerListner mOptionListListner;
	private BeanSaleItemAttribute mItemAttribute;
	private PosSelectButton mSelectedOptionButton;	
	private ArrayList<PosSelectButton> mAttribOptionControlList;	
	
	/**
	 * Create the dialog.
	 */
	public PosAttributeOptionListContainer() {
		super();
		setSize(new Dimension(LAYOUT_WIDTH,LAYOUT_HEIGHT));
		setLayout(createLayout());
		setBorder(new MatteBorder(0, 1, 0, 1,  Color.LIGHT_GRAY));
		initComponent();
	}
	
	/**
	 * 
	 */
	private  void initComponent() {
		createItemButtons();
	}
	
	/**
	 * 
	 */
	private void createItemButtons(){		
		mAttribOptionControlList=new ArrayList<PosSelectButton>();
		PosSelectButton optionButton;
		for(int index=0; index<8; index++){
			optionButton=new PosSelectButton();			
			optionButton.setBounds(0,0,IMAGE_BUTTON_WIDTH, IMAGE_BUTTON_HEIGHT);		
			optionButton.setHorizontalAlignment(SwingConstants.CENTER);			
			optionButton.setVerticalTextPosition(SwingConstants.CENTER);
			optionButton.setHorizontalTextPosition(SwingConstants.CENTER);				
			optionButton.setImage(PosResUtil.getImageIconFromResource(IMAGE_BUTTON_NORMAL));	
			optionButton.setTouchedImage(PosResUtil.getImageIconFromResource(IMAGE_BUTTON_TOUCHED));
			optionButton.setFont(PosFormUtil.getMainmenuItemFont());			
			optionButton.setOnSelectListner(optionButtonListner);
			optionButton.setVisible(false);			
			mAttribOptionControlList.add(optionButton);
			add(optionButton);					
		}
		
	}
	
	public void setAttribute(BeanSaleItemAttribute atribute){
		reset();
		mItemAttribute=atribute;
		String[] Items =atribute.getAttributeOptions();
		for(int index=0; index<8; index++){
			PosSelectButton optionButton=mAttribOptionControlList.get(index);
			if(index<Items.length){
				optionButton.setText(Items[index]);
//				Commented this code as it may confuse the selected items
//				if(Items[index].equals(PosSaleItemProvider.DEFAULT_ATTRIBUTE_OPTION)){
//					optionButton.setImage(PosResUtil.getImageIconFromResource(IMAGE_DEF_BUTTON_NORMAL));	
//					optionButton.setTouchedImage(PosResUtil.getImageIconFromResource(IMAGE_DEF_BUTTON_TOUCHED));
//				}
				optionButton.setTag(index);			
				optionButton.setVisible(true);
			}
			else{				
				optionButton.setVisible(false);
			}			
			optionButton.setSelected(false);
		}
		mAttribOptionControlList.get(atribute.getSelectedOptionIndex()).setSelected(true);		
	}
	
	public void reset(){
		if(mSelectedOptionButton!=null)
			mSelectedOptionButton.setSelected(false);
		mSelectedOptionButton=null;
	}

	private FlowLayout createLayout(){
		FlowLayout flowLayout=new FlowLayout();
		flowLayout.setVgap(PANEL_CONTENT_V_GAP);
		flowLayout.setHgap(PANEL_CONTENT_H_GAP);
		flowLayout.setAlignment(FlowLayout.LEFT);
		return flowLayout;
	}
		
	private IPosSelectButtonListner optionButtonListner=new IPosSelectButtonListner() {
		public void onSelected(PosSelectButton button) {
			if(mSelectedOptionButton!=null)
				mSelectedOptionButton.setSelected(false);
			mSelectedOptionButton=button;
			mItemAttribute.setSelectedOptionIndex((int)mSelectedOptionButton.getTag());
			if(mOptionListListner!=null)
				mOptionListListner.onSelected(mItemAttribute,(int)mSelectedOptionButton.getTag());
		}
	};
	
	public void setListner(IPosOptionListContainerListner listner){
		mOptionListListner=listner;
	}
	
	public interface IPosOptionListContainerListner{
		public void onSelected(BeanSaleItemAttribute attribute,int optionIndex);
//		public void onSelectedItemLoad(PosSaleItemAttribute attribute,int optionIndex);
	}



	
}
