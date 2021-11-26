/**
 * 
 */
package com.indocosmo.pos.forms.components.terminal;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.Map;

import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import com.indocosmo.pos.data.beans.BeanCashierShift;
import com.indocosmo.pos.forms.components.itemcontrols.PosCashierShiftControl;
import com.indocosmo.pos.forms.components.itemcontrols.listners.IPosItemSelectorListner;

/**
 * @author jojesh
 *
 */
@SuppressWarnings("serial")
public final class PosCashierShiftContainerPanel extends JPanel {
	
	private Map<String, BeanCashierShift> mCashierShifts;
	private ArrayList<PosCashierShiftControl> mCashierShiftControls;
	private ArrayList<String> mCashierIDs; //CashierCodes
	private static final int SHIFT_CONTROL_HEIGHT=PosCashierShiftControl.LAYOUT_HEIGHT;
	private static final int SHIFT_CONTROL_WIDTH=PosCashierShiftControl.LAYOUT_WIDTH;
	private final static int PANEL_CONTENT_H_GAP=3;
	private final static int PANEL_CONTENT_V_GAP=3;
	
	private int mMaxCols;
	private int mMaxRows;
	private int mHeight;
	private int mWidth;
	
	private PosCashierShiftControl mSelectedShiftControl;
	/**
	 * 
	 */
	public PosCashierShiftContainerPanel(int width, int height) {
		mHeight=height;
		mWidth=width;
		initControls();
	}
	
	private void initControls(){
		this.setOpaque(false);
		this.setSize(mWidth, mHeight);
		this.setPreferredSize(new Dimension(mWidth, mHeight));
		this.setLayout(new FlowLayout(FlowLayout.CENTER,PANEL_CONTENT_H_GAP,PANEL_CONTENT_V_GAP));
		this.setBackground(Color.LIGHT_GRAY);
		this.setBorder(new LineBorder(Color.GRAY, 1));
		this.setOpaque(true);
//		this.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
		mMaxCols=mWidth/(SHIFT_CONTROL_WIDTH+PANEL_CONTENT_H_GAP);
		mMaxRows=mHeight/(SHIFT_CONTROL_HEIGHT+PANEL_CONTENT_V_GAP);
		createShiftItems();
	}
	
	private void createShiftItems(){
		mCashierShiftControls=new ArrayList<PosCashierShiftControl>();
		mCashierIDs=new ArrayList<String>();
		for(int index=0; index<mMaxCols*mMaxRows;index++){
			PosCashierShiftControl shiftControl=new PosCashierShiftControl();
			this.add(shiftControl);
			mCashierShiftControls.add(shiftControl);
			mCashierIDs.add("");
		}
	}
	
	public boolean isShiftOpen(String cardNumber){
		return mCashierIDs.contains(cardNumber);
	}
	
	public boolean isShiftOpen(){
		return (mCashierShifts!=null && mCashierShifts.size()>0);
	}
	
	public void setCashierShifts(Map<String, BeanCashierShift> cashierShifts){
		mCashierShifts=cashierShifts;
		int index=0;
		for(BeanCashierShift shift:mCashierShifts.values()){
			
			if(index>=mCashierShiftControls.size()) break;
			PosCashierShiftControl shiftControl=mCashierShiftControls.get(index);
			shiftControl.setCashierShift(shift);
			shiftControl.setListner(mShiftControlListner);
			mCashierIDs.set(index, shift.getCashierInfo().getCardNumber());
			index++;
		}
		for(;index<mCashierShiftControls.size();index++){
			mCashierShiftControls.get(index).setCashierShift(null);
			mCashierIDs.set(index, "");
		}
	}
	
	private boolean mAsk4Password=true;;
	public boolean setSelectedCashier(boolean isValidated,String cashierCode){
		if(cashierCode==null || cashierCode.equals("")) return false;
		final int index=mCashierIDs.indexOf(cashierCode);
		if(index>=0){
			PosCashierShiftControl shiftControl=mCashierShiftControls.get(index);
			if(!shiftControl.isSelected()){
				mAsk4Password=!isValidated;
				shiftControl.setSelected(true);
			}
			return true;
		}
		return false;
	}
	
	@Override
	public void setEnabled(boolean enabled) {
		for(PosCashierShiftControl control: mCashierShiftControls)
			control.setEnabled(enabled);
		super.setEnabled(enabled);
	}
	
	public BeanCashierShift getSelectedCashierShift(){
		return (mSelectedShiftControl!=null)?mSelectedShiftControl.getCashierShift():null;
	}
	
	public void clearSelection(){
		if(mSelectedShiftControl!=null)
			mSelectedShiftControl.setSelected(false);
		mSelectedShiftControl=null;
	}
	
	private IPosItemSelectorListner mShiftControlListner = new IPosItemSelectorListner() {	
		@Override
		public boolean onSelected(Object itemSender) {
			PosCashierShiftControl item=(PosCashierShiftControl)itemSender;
			if(mListner!=null)
				if(!mListner.onCashierShiftSelected(!mAsk4Password,item.getCashierShift())){
//					item.setSelected(false);
					mAsk4Password=true;
					return false;
				}
			
			if(mSelectedShiftControl!=null)
				mSelectedShiftControl.setSelected(false);
			mSelectedShiftControl=item;
			mAsk4Password=true;
			return true;
		}
		
		@Override
		public boolean onDoubleClick(Object item) {
			return true;
		}
	};
	
	private IPosCashierShiftContainerListner mListner;
	public void setListner(IPosCashierShiftContainerListner listner){
		mListner=listner;
	}
	
	public interface IPosCashierShiftContainerListner{
		public boolean onCashierShiftSelected(boolean validated,BeanCashierShift cashierShift);
	}
}
