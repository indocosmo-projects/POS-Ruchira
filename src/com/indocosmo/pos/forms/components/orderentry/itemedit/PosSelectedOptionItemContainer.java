package com.indocosmo.pos.forms.components.orderentry.itemedit;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JPanel;

import com.indocosmo.pos.common.utilities.PosFormUtil;
import com.indocosmo.pos.data.beans.BeanSaleItemAttribute;
import com.indocosmo.pos.forms.components.orderentry.itemedit.PosSelectedOptionItemControl.IPosOptionListItemControlListner;

@SuppressWarnings("serial")
public final class PosSelectedOptionItemContainer extends JPanel{

	private final static int PANEL_CONTENT_H_GAP=8;
	private final static int PANEL_CONTENT_V_GAP=8;
	public static final int LEFT_PANELS_WIDTH=PosAttributeOptionListContainer.LAYOUT_WIDTH+PosOrderItemAttributeContainer.LAYOUT_WIDTH;
	public static final int PARENT_CONTAINER_WIDTH=PosTabOrderItemAttributeEdit.LAYOUT_WIDTH;
	public static final int LAYOUT_WIDTH=PARENT_CONTAINER_WIDTH-LEFT_PANELS_WIDTH;
	public final static int LAYOUT_HEIGHT=PosOrderItemAttributeContainer.LAYOUT_HEIGHT;
	public static final int CONTAINER_PANEL_WIDTH=LAYOUT_WIDTH;

	private ArrayList<PosSelectedOptionItemControl> mOptionList;
	private int mControlIndex=0;
	private JPanel mControlPanel;
	private PosSelectedOptionItemControl mSelectedLabelItem;
	
	private ArrayList<BeanSaleItemAttribute> mAttributeList;


	/**
	 * Create the dialog.
	 */
	public PosSelectedOptionItemContainer() {
		super();
		setSize(new Dimension(LAYOUT_WIDTH,LAYOUT_HEIGHT));
		setLayout(null);
		initComponent();
		mOptionList=new ArrayList<PosSelectedOptionItemControl>();		
	}
	
	
	
	public void reset(){
		mOptionList.clear();
		mControlPanel.removeAll();
		loadAttribList();
		if(mSelectedLabelItem!=null)
			mSelectedLabelItem.setSelected(false);
		if(mOptionList!=null && mOptionList.size()>0){
			mOptionList.get(0).setSelected(true);
		}
	}

	/**
	 * 
	 */
	private  void initComponent() {
		createControlPanel();
	}

	/**
	 * 
	 */
	private void createControlPanel(){
		mControlPanel = new JPanel();
		mControlPanel.setBounds(0,0,
				CONTAINER_PANEL_WIDTH,200);
		mControlPanel.setLayout(createLayout());
		add(mControlPanel);	
	}

	/**
	 * @return
	 */
	private FlowLayout createLayout(){
		FlowLayout flowLayout=new FlowLayout();
		flowLayout.setVgap(PANEL_CONTENT_V_GAP);
		//		flowLayout.setHgap(PANEL_CONTENT_H_GAP);
		flowLayout.setAlignment(FlowLayout.RIGHT);
		return flowLayout;
	}

	/**
	 * 
	 */
	public void loadAttribList(){
				
		for(BeanSaleItemAttribute attrib:mAttributeList)
			if(attrib!=null)
				addItem(attrib);
		
	}

	/**
	 * @param labelItem
	 */
	private void createItemList(PosSelectedOptionItemControl labelItem){				
		final int noRows=(int)mOptionList.size();		
		final int controlPanelHeight=(noRows*PosSelectedOptionItemControl.LAYOUT_HEIGHT)+((noRows+1)*PANEL_CONTENT_H_GAP);
		mControlPanel.setSize(CONTAINER_PANEL_WIDTH,controlPanelHeight);
		mControlPanel.add(labelItem);				
	}
	
	/**
	 * @param attrib
	 */
	private void addItem(BeanSaleItemAttribute attrib){
		addItem(attrib,attrib.getSelectedOptionIndex());
	}
	
	/**
	 * @param attrib
	 * @param optionIndex
	 */
	public void addItem(BeanSaleItemAttribute attrib,int optionIndex){
		PosSelectedOptionItemControl labelItem=isExist(attrib);
		if(attrib.getAttributeOptions()[optionIndex].equals("None")){
			if(labelItem!=null){
				final int itemIndex=mOptionList.indexOf(labelItem)-1;
				if(mOptionList!=null)
					mOptionList.remove(labelItem);
				mSelectedLabelItem=(itemIndex>=0)?mOptionList.get(itemIndex):null;
				if(mSelectedLabelItem!=null)
					mSelectedLabelItem.setSelected(true);	
				removeItem(labelItem);
			}
		}else{
			if(labelItem==null){
				labelItem =new PosSelectedOptionItemControl();				
				labelItem.setItemIndex(mControlIndex);	
				labelItem.setPreferredSize(new Dimension(CONTAINER_PANEL_WIDTH-PANEL_CONTENT_H_GAP*2, PosSelectedOptionItemControl.LAYOUT_HEIGHT));
				labelItem.setFont(PosFormUtil.getLabelFont());	
				labelItem.setOptionListItemControl(mOptionListItemControlListener);
				mOptionList.add(labelItem);					
				createItemList(labelItem);
				mControlIndex++;			
			}
			if(mSelectedLabelItem!=null)
				mSelectedLabelItem.setSelected(false);	
			labelItem.setSelected(true);
			labelItem.setSelectedOptionIndex(optionIndex);			
			labelItem.setSelectedAttribIndex(attrib.getAttributeIndex());
			String itemName=" "+attrib.getArtributeName()+" : "+attrib.getAttributeOptions()[optionIndex];
			labelItem.setText(itemName);
			mSelectedLabelItem=labelItem;
		}		
		revalidate();	
		repaint();
	}

	/**
	 * @param item
	 * @return
	 */
	private PosSelectedOptionItemControl isExist(BeanSaleItemAttribute item){
		PosSelectedOptionItemControl listItemControl=null;
		for (Iterator<PosSelectedOptionItemControl> optionListIterator=mOptionList.iterator(); optionListIterator.hasNext();){
			PosSelectedOptionItemControl listItem=optionListIterator.next();
			if(listItem.getSelectedAttribIndex()==item.getAttributeIndex()){				
				listItemControl=listItem;
				break;
			}
		}
		return listItemControl;
	}

	/**
	 * @param labelItem
	 */
	public void removeItem(PosSelectedOptionItemControl labelItem){
		final int noRows=(int)mOptionList.size();
		final int controlPanelHeight=(noRows*PosSelectedOptionItemControl.LAYOUT_HEIGHT)+((noRows+1)*PANEL_CONTENT_H_GAP);		
		mControlPanel.setSize(CONTAINER_PANEL_WIDTH,controlPanelHeight);
		mControlPanel.remove(labelItem);				
	}

	/**
	 * @return the mAttributeList
	 */
	public ArrayList<BeanSaleItemAttribute> getAttributeList() {
		return mAttributeList;
	}

	/**
	 * @param mAttributeList the mAttributeList to set
	 */
	public void setAttributeList(ArrayList<BeanSaleItemAttribute> attributeList) {
		this.mAttributeList = attributeList;
		reset();
	}
	/**
	 * 
	 */
	private IPosOptionListItemControlListner mOptionListItemControlListener=new 
			IPosOptionListItemControlListner(){
		@Override
		public void onSelected(int index) {

		}
	};


}
