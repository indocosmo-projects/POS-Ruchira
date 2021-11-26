package com.indocosmo.pos.forms.components.orderentry.itemedit;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import com.indocosmo.pos.common.PosLog;
import com.indocosmo.pos.common.utilities.PosFormUtil;
import com.indocosmo.pos.data.beans.BeanOrderDetail;
import com.indocosmo.pos.data.beans.BeanSaleItem;
import com.indocosmo.pos.data.beans.BeanSaleItemAttribute;
import com.indocosmo.pos.forms.PosOrderEntryForm;
import com.indocosmo.pos.forms.PosOrderListItemEditForm;
import com.indocosmo.pos.forms.components.orderentry.itemedit.PosAttributeOptionListContainer.IPosOptionListContainerListner;
import com.indocosmo.pos.forms.components.orderentry.itemedit.PosOrderItemAttributeContainer.IPosAttributeContainerListner;
import com.indocosmo.pos.forms.components.tab.PosTab;
import com.indocosmo.pos.forms.listners.IPosFormEventsListner;

@SuppressWarnings("serial")
public final class PosTabOrderItemAttributeEdit extends PosTab implements IPosFormEventsListner{

	private static final int PANEL_CONTENT_H_GAP=PosOrderEntryForm.PANEL_CONTENT_H_GAP;
//	private static final int PANEL_CONTENT_V_GAP=PosOrderEntryForm.PANEL_CONTENT_V_GAP;

	public static final int LAYOUT_HEIGHT=420;
	public static final int LAYOUT_WIDTH=PosOrderListItemEditForm.LAYOUT_WIDTH-PANEL_CONTENT_H_GAP; //790;	
	public static final String TAB_CAPTON="Modifiers";

	private PosOrderItemAttributeContainer mAttributeContainer;
	private PosAttributeOptionListContainer mOptionContainer;
	private PosSelectedOptionItemContainer mSelectedOptionItemContainer;
	private BeanSaleItem mPosSaleItemToEdit;
	private BeanOrderDetail mOrderDetailItem;
	
	ArrayList<BeanSaleItemAttribute> mAttributeList;

	public  PosTabOrderItemAttributeEdit(Object parent,BeanOrderDetail orderItem){
		super(parent,TAB_CAPTON);
		setMnemonicChar('f');
		mOrderDetailItem=orderItem;
		mPosSaleItemToEdit=mOrderDetailItem.getSaleItem();//mPosSaleItem.clone();
		initComponent();
		reset();
	}

	private void initComponent(){
		setLayout(null);
		setSize(LAYOUT_WIDTH, LAYOUT_HEIGHT);
		if(mPosSaleItemToEdit.getAtrributeList().size()<=0){
			JLabel infoLabel=new JLabel();
			infoLabel.setText("There are no items to edit.");
			infoLabel.setVerticalTextPosition(SwingConstants.CENTER);
			infoLabel.setHorizontalAlignment(SwingConstants.CENTER);
			infoLabel.setForeground(Color.LIGHT_GRAY);
			infoLabel.setFont(PosFormUtil.getLabelHeaderFont());
			infoLabel.setSize(new Dimension(LAYOUT_WIDTH, LAYOUT_HEIGHT));
			infoLabel.setLocation(2,2);
			infoLabel.setOpaque(true);
			add(infoLabel);
			return;
		}
		int left=0;//PANEL_CONTENT_H_GAP;
		final int top=0;//PANEL_CONTENT_V_GAP;
		mAttributeContainer=new PosOrderItemAttributeContainer();
		mAttributeContainer.setLocation(left, top);
		mAttributeContainer.setListner(new IPosAttributeContainerListner() {
			@Override
			public void onAttributeSelected(BeanSaleItemAttribute attribute) {
				if(attribute!=null){
					mOptionContainer.setAttribute(attribute);	
					return;
				}

			}
		});
		add(mAttributeContainer);

		left=mAttributeContainer.getX()+mAttributeContainer.getWidth();//+PANEL_CONTENT_H_GAP;
		mOptionContainer=new PosAttributeOptionListContainer();
		mOptionContainer.setLocation(left, top);
		mOptionContainer.setListner(new IPosOptionListContainerListner() {
			@Override
			public void onSelected(BeanSaleItemAttribute attribute, int optionIndex) {
				if(attribute!=null && !isReadOnly()){
					mSelectedOptionItemContainer.addItem(attribute, optionIndex);	
					setDirty(true);
				}
			}
		});
		add(mOptionContainer);

		left=mOptionContainer.getX()+mOptionContainer.getWidth();//+PANEL_CONTENT_H_GAP;
		mSelectedOptionItemContainer=new PosSelectedOptionItemContainer();
		mSelectedOptionItemContainer.setLocation(left, top);
		add(mSelectedOptionItemContainer);
		
	}

	@Override
	public boolean onOkButtonClicked() {
		if(isDirty()){
			mOrderDetailItem.getSaleItem().setAttributeList(mAttributeList);
			mOrderDetailItem.setDirty(true);
			mOrderDetailItem.setKitchenDirty(isDirty());
		}
		return true;
	}

	@Override
	public boolean onCancelButtonClicked() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onResetButtonClicked() {
		reset();
	}
	
	private void reset(){
		mAttributeList=getAttributeList(mPosSaleItemToEdit);	
		mAttributeContainer.setAttribList(mAttributeList);
		mSelectedOptionItemContainer.setAttributeList(mAttributeList);
		setDirty(false);
	}

	private ArrayList<BeanSaleItemAttribute> getAttributeList(BeanSaleItem saleItem){
		
		ArrayList<BeanSaleItemAttribute> newAttrList=null;
		try {
			newAttrList=new ArrayList<BeanSaleItemAttribute>();
			for(BeanSaleItemAttribute attr:saleItem.getAtrributeList()){
				BeanSaleItemAttribute attrNew=attr.clone();
				newAttrList.add(attrNew);	
			}
		} catch (CloneNotSupportedException e) {
			PosLog.write(this, "getAttributeList", e);
			PosFormUtil.showErrorMessageBox(mParent, "Failed to get attribute list from sale item. Please contact Administrator.");
		}
		
		return newAttrList;
	}


}
