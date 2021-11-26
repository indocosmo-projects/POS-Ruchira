package com.indocosmo.pos.forms.components.terminal;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.JPanel;

import com.indocosmo.pos.common.PosConstants;
import com.indocosmo.pos.common.PosEnvSettings;
import com.indocosmo.pos.common.enums.CardTypes;
import com.indocosmo.pos.common.utilities.PosFormUtil;
import com.indocosmo.pos.data.beans.BeanCashierShift;
import com.indocosmo.pos.data.providers.shopdb.PosCashierShiftProvider;
import com.indocosmo.pos.forms.PosMainMenuForm;
import com.indocosmo.pos.forms.components.terminal.PosCashierShiftContainerPanel.IPosCashierShiftContainerListner;
import com.indocosmo.pos.forms.components.textfields.PosCardReaderField;
import com.indocosmo.pos.forms.components.textfields.listners.IPosTouchableFieldListner;

@SuppressWarnings("serial")
public class PosShiftDetailsPanel extends JPanel {
	
	private static final int PANEL_CONTENT_H_GAP=PosConstants.PANEL_CONTENT_H_GAP/2;
	private static final int PANEL_CONTENT_V_GAP=PosConstants.PANEL_CONTENT_V_GAP/2;
	private static final int MESSAGE_PANEL_HEIGHT=30;
	
	private Map<String, BeanCashierShift>  mCashierShifts;
	private int mHeight;
	private int mWidth;
	private PosCardReaderField mTxtCashierID;
	private PosMainMenuForm mParent;
	private PosCashierShiftContainerPanel mShiftContainer;
	
	public PosShiftDetailsPanel(PosMainMenuForm parent, int width, int height){
		mParent=parent;
		mWidth=width;
		mHeight=height;
		initControls();
	}
	
	public void initControls(){
		this.setSize(mWidth,mHeight );
		this.setPreferredSize(new Dimension(mWidth,mHeight));
		this.setLayout(new FlowLayout(FlowLayout.CENTER,PANEL_CONTENT_H_GAP,PANEL_CONTENT_V_GAP));
		
		this.setOpaque(false);
		if(PosEnvSettings.getInstance().getShop()!=null){
			setShortMessagePanel("Active Cashiers");
		}
		setCashierID();
		setCashierList();
	}
	
	public boolean isShiftOpen(String code){
		return mShiftContainer.isShiftOpen(code);
	}
	
	public boolean isShiftOpen(){
		return mShiftContainer.isShiftOpen();
	}

	public void resetCashierDetails(){
		mCashierShifts=null;
		PosCashierShiftProvider mCashierShiftProvider=new PosCashierShiftProvider();
		mCashierShifts=mCashierShiftProvider.getAllOpenCashierShifts();
		mCashierShiftProvider=null;
		mShiftContainer.setFocusable(false);
		mShiftContainer.setCashierShifts(mCashierShifts);
	}
	
	public void setSelectedCashier(String id){
		mShiftContainer.setSelectedCashier(true, id);
	}
	
	private void setShortMessagePanel(String message){
//		JLabel labelMessage=new JLabel();
//		labelMessage.setText(message);
//		labelMessage.setHorizontalAlignment(SwingConstants.CENTER);		
//		labelMessage.setPreferredSize(new Dimension(getWidth()-PANEL_CONTENT_H_GAP*2-4, MESSAGE_PANEL_HEIGHT));				
//		labelMessage.setOpaque(true);
//		labelMessage.setBackground(LABEL_BG_COLOR);
//		labelMessage.setForeground(Color.WHITE);
		JLabel labelMessage=PosFormUtil.setHeading(message, getWidth()-PANEL_CONTENT_H_GAP*2-4, MESSAGE_PANEL_HEIGHT);
		add(labelMessage);
	}
	
	private void setCashierID(){
		mTxtCashierID=new PosCardReaderField(mParent, getWidth()-PANEL_CONTENT_H_GAP*2-4);
		mTxtCashierID.setTitle("Enter Card Number");
		mTxtCashierID.setPosCardType(CardTypes.User);
		mTxtCashierID.setListner(new IPosTouchableFieldListner() {
			@Override
			public void onValueSelected(Object value) {
				String cashierCode=String.valueOf(value);
				if(mShiftContainer.setSelectedCashier(mTxtCashierID.isCardSwiped(), cashierCode)){
					BeanCashierShift shift=mShiftContainer.getSelectedCashierShift();
					if(mListner!=null && shift!=null)
						mListner.onCashierShiftSelected(shift);
				}
				else
					if(mListner!=null && !cashierCode.equalsIgnoreCase(""))
						mListner.onNewCashier(cashierCode,mTxtCashierID.isCardSwiped());
				mTxtCashierID.setText("");
			}
			
			@Override
			public void onReset() {
				// TODO Auto-generated method stub
				
			}
		});
		add(mTxtCashierID);
	}
	
	
	private void setCashierList(){
		final int width=mTxtCashierID.getWidth();
		final int height=getHeight()-MESSAGE_PANEL_HEIGHT-PosCardReaderField.LAYOUT_DEF_HEIGHT-(PANEL_CONTENT_V_GAP*2);
		mShiftContainer=new PosCashierShiftContainerPanel(width, height);
		mShiftContainer.setListner(mShiftContainerListner);
//		this.setOpaque(true);
//		this.setBackground(Color.RED);
		add(mShiftContainer);
//		resetCashierDetails();
	}
	
	public void loadCashierList(){
		resetCashierDetails();
	}
	
	@Override
	public void setEnabled(boolean enabled) {
		if(!enabled)
			clearSelection();
		mTxtCashierID.setEditable(enabled);
		mShiftContainer.setEnabled(enabled);
		super.setEnabled(enabled);
	}
	
	private IPosCashierShiftContainerListner mShiftContainerListner=new IPosCashierShiftContainerListner() {
		
		@Override
		public boolean onCashierShiftSelected(boolean validated,BeanCashierShift cashierShift) {
			boolean valid =false;
			
//			Commented by Deepak for removing the shift detail touch action.........
			
//			if(validated || validateCashier(cashierShift)){
//				if(mListner!=null)
//					mListner.onCashierShiftSelected(cashierShift);
//				valid = true;
//			}else{
//				PosFormUtil.showMessageBox(mParent, MessageBoxTypes.OkOnly, "Invalid user id or password. ", null);
//				valid = false;
//			}
//			return valid;
			
			if(validated){
				if(mListner!=null)
					mListner.onCashierShiftSelected(cashierShift);
				valid = true;
			}
			
//			if(validated || PosPasswordUtil.getValidatePassword(cashierShift)){
//				if(mListner!=null)
//					mListner.onCashierShiftSelected(cashierShift);
//				valid = true;
//			}
			return valid;
			
		}
	};
	
	public void requestFocus() {
		mTxtCashierID.requestFocus();
	};
	
	public void clearSelection(){
		mShiftContainer.clearSelection();
	}
	
//	private boolean validateCashier(PosCashierShiftObject cashierShift){
//		final String password=PosPasswordUtil.getPassword();
//		return cashierShift.getCashierInfo().getPassword().equals(password);
//	}
	
	private IPosShiftDetailsPanelListner mListner;
	public void setListner(IPosShiftDetailsPanelListner listner){
		mListner=listner;
	}
	
	public interface IPosShiftDetailsPanelListner{
		public void onCashierShiftSelected(BeanCashierShift cashierShift);
		public void onNewCashier(String id, boolean byCard);
	}
	
	
	 
}
