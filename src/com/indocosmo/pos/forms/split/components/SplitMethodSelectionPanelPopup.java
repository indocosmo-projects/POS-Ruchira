/**
 * 
 */
package com.indocosmo.pos.forms.split.components;

import javax.swing.JLabel;
import javax.swing.RootPaneContainer;

import com.indocosmo.pos.common.enums.split.SplitBasedOn;
import com.indocosmo.pos.common.utilities.PosFormUtil;
import com.indocosmo.pos.forms.components.textfields.PosItemBrowsableField;
import com.indocosmo.pos.forms.components.textfields.listners.IPosTouchableFieldListner;

/**
 * @author jojesh
 *
 */
public class SplitMethodSelectionPanelPopup extends SplitMethodSelectionPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final int PANEL_CONTENT_V_GAP=8;
	private static final int PANEL_CONTENT_H_GAP=8;
	
	private PosItemBrowsableField mBillSplitBasedOn;
	
	/**
	 * @param parent
	 * @param width
	 * @param height
	 */
	public SplitMethodSelectionPanelPopup(RootPaneContainer parent, int width,
			int height) {
		super(parent, width, height);
	}


	
	/* (non-Javadoc)
	 * @see com.indocosmo.pos.forms.split.components.SplitMethodSelectionPanel#initComponent()
	 */
	@Override
	protected void initComponent() {
		super.initComponent();
		
		int left=0;
		int top=(getHeight()/2)-(PosItemBrowsableField.LAYOUT_DEF_HEIGHT/2);
		
		JLabel titleSplitMethod=new JLabel("Based on :");
		titleSplitMethod.setBounds(left,top, 100, PosItemBrowsableField.LAYOUT_DEF_HEIGHT);
		titleSplitMethod.setFont(PosFormUtil.getLabelFont());
		titleSplitMethod.setHorizontalAlignment(JLabel.RIGHT);
		add(titleSplitMethod);

		left=titleSplitMethod.getWidth()+PANEL_CONTENT_H_GAP;
		mBillSplitBasedOn=new PosItemBrowsableField(mParent,getWidth()-left);
		mBillSplitBasedOn.setBrowseWindowSize(2, 3);
		mBillSplitBasedOn.setBrowseItemList(SplitBasedOn.values());
		mBillSplitBasedOn.setTitle("Split Based on");
		mBillSplitBasedOn.setLocation(left, top);	
		mBillSplitBasedOn.setFont(PosFormUtil.getTextFieldFont());
		mBillSplitBasedOn.setListner(new IPosTouchableFieldListner() {

			@Override
			public void onValueSelected(Object value) {
				
				SplitBasedOn newBasedon=(SplitBasedOn) value;
				if(splitBasedOn !=newBasedon){
				
					if(onSPlitMethodChanged(newBasedon)){
					
						splitBasedOn=newBasedon;
					}else{
						
						mBillSplitBasedOn.setSelectedItem(splitBasedOn);
					}
				}
						
			}

			@Override
			public void onReset() {

				mBillSplitBasedOn.setSelectedItem(DEFAULT_SPLIT_BASED_ON);
			}
		});

		add(mBillSplitBasedOn);
	}



	/* (non-Javadoc)
	 * @see com.indocosmo.pos.forms.split.components.SplitMethodSelectionPanel#setSplitMethod(com.indocosmo.pos.common.enums.split.SplitBasedOn)
	 */
	@Override
	protected void setSplitMethod(SplitBasedOn splitBasedOn) {

		mBillSplitBasedOn.setSelectedItem(splitBasedOn);
	}



	/* (non-Javadoc)
	 * @see com.indocosmo.pos.forms.split.components.SplitMethodSelectionPanel#setSplitMethodEnabled(com.indocosmo.pos.common.enums.split.SplitBasedOn, boolean)
	 */
	@Override
	public void setSplitMethodEnabled(SplitBasedOn basedOn, boolean enabled) {
		// TODO Auto-generated method stub
		
	}


	
	

}
