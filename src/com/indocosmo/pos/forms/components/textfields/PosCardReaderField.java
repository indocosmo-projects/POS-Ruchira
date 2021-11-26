/**
 * 
 */
package com.indocosmo.pos.forms.components.textfields;

import javax.swing.JTextField;
import javax.swing.RootPaneContainer;

import com.indocosmo.pos.common.enums.CardTypes;
import com.indocosmo.pos.common.utilities.PosCardUtil;

/**
 * @author jojesh
 *
 */
@SuppressWarnings("serial")
public final class PosCardReaderField extends PosTouchableDigitalField {

	private boolean mIsCardSwiped;
	private CardTypes mCardType;
	/**
	 * @param parent
	 * @param width
	 * @param height
	 */
	public PosCardReaderField(RootPaneContainer parent, int width, int height) {
		super(parent, width, height);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param parent
	 * @param width
	 */
	public PosCardReaderField(RootPaneContainer parent, int width) {
		super(parent, width);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param parent
	 */
	public PosCardReaderField(RootPaneContainer parent) {
		super(parent);
		// TODO Auto-generated constructor stub
	}
	
	public void setPosCardType(CardTypes cardType){
		mCardType=cardType;
	}
	
	public void setCardNumber(String cardNumber){
		mTextFiled.setText(cardNumber);
		onValueSelected(cardNumber);
	}
	
	@Override
	protected void onValueSelected(Object value) {
		
		if(value==null || String.valueOf(value).trim().length()<=0) return;
		
		mIsCardSwiped=false;
		
		String id=String.valueOf(value);
		if(PosCardUtil.checkForValidPosCard(mCardType,id)){
			value=PosCardUtil.getPosCardNumber(mCardType, id);
			mIsCardSwiped=true;
		}
		super.onValueSelected(value);
	}
	
	@Override
	public void reset() {
		mIsCardSwiped = false;
		super.reset();
	}
	
	public boolean isCardSwiped(){
		return mIsCardSwiped;
	}
	
	/* (non-Javadoc)
	 * @see com.indocosmo.pos.forms.components.textfields.PosTouchableFieldBase#setDocType()
	 */
	@Override
	protected void setDocType() {
		mTextFiled.setDocument((new JTextField()).getDocument());
	}
	

}
