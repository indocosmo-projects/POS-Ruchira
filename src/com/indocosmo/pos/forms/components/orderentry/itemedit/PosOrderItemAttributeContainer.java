package com.indocosmo.pos.forms.components.orderentry.itemedit;

import java.awt.FlowLayout;
import java.util.ArrayList;

import javax.swing.JPanel;

import com.indocosmo.pos.data.beans.BeanSaleItem;
import com.indocosmo.pos.data.beans.BeanSaleItemAttribute;
import com.indocosmo.pos.forms.PosOrderEntryForm;
import com.indocosmo.pos.forms.components.orderentry.itemedit.PosOrderItemAttribControl.IPosItemAttributeControlListner;

@SuppressWarnings("serial")
public class PosOrderItemAttributeContainer extends JPanel {


	private static final int PANEL_CONTENT_H_GAP=PosOrderEntryForm.PANEL_CONTENT_H_GAP;
	private static final int PANEL_CONTENT_V_GAP=PosOrderEntryForm.PANEL_CONTENT_V_GAP;

	public static final int LAYOUT_HEIGHT=460;//PosSaleItem.ITEM_ATTRIBUTE_COUNT*PosOrderItemAttribControl.LAYOUT_HEIGHT+
	//PANEL_CONTENT_V_GAP*(PosSaleItem.ITEM_ATTRIBUTE_COUNT+1)+PANEL_CONTENT_V_GAP+3;

	public static final int LAYOUT_WIDTH=PANEL_CONTENT_H_GAP*2+PosOrderItemAttribControl.LAYOUT_WIDTH;

	private ArrayList<PosOrderItemAttribControl> mSaleItemAttribControlList;		
	private PosOrderItemAttribControl mSeletedAttribControl;
	//	private PosSaleItem mGridItemControl;
	private ArrayList<BeanSaleItemAttribute> mAttributeList;


	public PosOrderItemAttributeContainer(){
		initComponent();		
	}

	private void initComponent(){
		setLayout(createLayout());
		//		setBorder(new EtchedBorder(EtchedBorder.LOWERED));
		//		setBackground(Color.RED);
		setSize(LAYOUT_WIDTH,LAYOUT_HEIGHT);
		createAttributeItemList();
	}



	private void createAttributeItemList(){
		mSaleItemAttribControlList=new ArrayList<PosOrderItemAttribControl>();
		for(int index=0;index<BeanSaleItem.ITEM_ATTRIBUTE_COUNT;index++){
			PosOrderItemAttribControl btn=new PosOrderItemAttribControl();
			btn.setListner(mItemAttributeControlListner);
			add(btn);
			mSaleItemAttribControlList.add(btn);
		}
		//		mSaleItemAttribControlList.get(0).setSelected(true);
	}	

	private IPosItemAttributeControlListner mItemAttributeControlListner=new IPosItemAttributeControlListner() {

		@Override
		public void onSelected(PosOrderItemAttribControl item) {
			if(mSeletedAttribControl!=null)
				mSeletedAttribControl.setSelected(false);
			mSeletedAttribControl=item;	
			if(mContainerListner!=null)
				mContainerListner.onAttributeSelected(mSeletedAttribControl.getAttribute());
		}
	};

	public void setSelectedAttribute(int index){
		if(mSaleItemAttribControlList.size()>index && index>=0)
			mSaleItemAttribControlList.get(index).setSelected(true);
	}

	public void reset(){
		if(mSeletedAttribControl!=null)
			mSeletedAttribControl.setSelected(false);
		mSeletedAttribControl=null;
		for(int index=0;index<BeanSaleItem.ITEM_ATTRIBUTE_COUNT;index++){
			PosOrderItemAttribControl btn=mSaleItemAttribControlList.get(index);
			if(index<mAttributeList.size()){
				btn.setAttribute(mAttributeList.get(index));
				btn.setVisible(true);
			}else
				btn.setVisible(false);
		}
		mSaleItemAttribControlList.get(0).setSelected(true);
	}

	private FlowLayout createLayout(){
		FlowLayout flowLayout=new FlowLayout();
		flowLayout.setVgap(PANEL_CONTENT_V_GAP);
		flowLayout.setHgap(PANEL_CONTENT_H_GAP);
		flowLayout.setAlignment(FlowLayout.CENTER);
		return flowLayout;
	}

	/**
	 * @return the mAttribList
	 */
	public ArrayList<BeanSaleItemAttribute> getAttribList() {
		return mAttributeList;
	}

	/**
	 * @param attribList the mAttribList to set
	 */
	public void setAttribList(ArrayList<BeanSaleItemAttribute> attribList) {
		this.mAttributeList = attribList;
		reset();
	}

	private IPosAttributeContainerListner mContainerListner;

	public void setListner(IPosAttributeContainerListner listner){
		mContainerListner=listner;
	}

	public interface IPosAttributeContainerListner{
		public void onAttributeSelected(BeanSaleItemAttribute attribute);	
	}

}
