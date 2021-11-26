/**
 * 
 */
package com.indocosmo.pos.forms.components.orderentry.itemedit;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.ScrollPane;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.indocosmo.pos.common.utilities.PosFormUtil;
import com.indocosmo.pos.data.beans.BeanOrderDetail;
import com.indocosmo.pos.forms.PosOrderEntryForm;
import com.indocosmo.pos.forms.components.keypads.PosSoftKeyPad;
import com.indocosmo.pos.forms.components.keypads.PosSoftKeyPadMultiLineInput;
import com.indocosmo.pos.forms.components.tab.PosTab;
import com.indocosmo.pos.forms.listners.IPosFormEventsListner;

/**
 * @author LAP-L530
 *
 */
@SuppressWarnings("serial")
public class PosTabOrderItemRemarksQuickEdit extends PosTab implements IPosFormEventsListner {

	
	private static final int TEXT_AREA_HEIGHT=80;
	private static final int SCROLL_BAR_WIDTH=30;
	private static final int PANEL_CONTENT_H_GAP=2;
	private static final int LAYOUT_WIDTH=700;
	
	private static final int PANEL_CONTENT_V_GAP=PosOrderEntryForm.PANEL_CONTENT_V_GAP;
	private static final int LAYOUT_HEIGHT=PosSoftKeyPadMultiLineInput.LAYOUT_HEIGHT+TEXT_AREA_HEIGHT+PANEL_CONTENT_V_GAP*2;
	private static final int TEXT_AREA_WIDTH=LAYOUT_WIDTH-(PANEL_CONTENT_V_GAP*2);
	
	private static final int KEYPAD_WIDTH = LAYOUT_WIDTH-PANEL_CONTENT_H_GAP;
	private static final int KEYPAD_HEIGHT = 300;
	
	private JTextArea mTextArea;
	private PosSoftKeyPad mPosSoftkeyPad;
	private BeanOrderDetail mOrderDetailItem;
	
	public PosTabOrderItemRemarksQuickEdit(Object parent,BeanOrderDetail orderDetailItem){
		super(parent,"Remarks");
		setMnemonicChar('m');
		mOrderDetailItem=orderDetailItem;
		initControls();	
	}
	
	private void initControls(){
		
		this.setLayout(null);
		this.setSize(LAYOUT_WIDTH, LAYOUT_HEIGHT);
		this.setBackground(new Color(6,38,76));
		
		mTextArea=new JTextArea(5,30);
		mTextArea.setLineWrap(true);
		mTextArea.setFont(PosFormUtil.getTextFieldBoldFont());
		mTextArea.setText(mOrderDetailItem.getRemarks());
		mTextArea.getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent arg0) {
				setDirty(true);
			}
			
			@Override
			public void insertUpdate(DocumentEvent arg0) {
				setDirty(true);
				
			}
			
			@Override
			public void changedUpdate(DocumentEvent arg0) {
				setDirty(true);
				
			}
		});
		
		JScrollPane scrolPane=new JScrollPane(mTextArea);
		scrolPane.getVerticalScrollBar().setPreferredSize(new Dimension(SCROLL_BAR_WIDTH,0));
		scrolPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrolPane.setBounds(PANEL_CONTENT_V_GAP,PANEL_CONTENT_V_GAP,TEXT_AREA_WIDTH-PANEL_CONTENT_H_GAP, TEXT_AREA_HEIGHT);
		scrolPane.setBorder(new LineBorder(new Color(134, 184, 232)));
		add(scrolPane);
		
		mPosSoftkeyPad=new PosSoftKeyPad(mTextArea,true);
		mPosSoftkeyPad.setLocation(0, 85);
		mPosSoftkeyPad.setSize(KEYPAD_WIDTH-PANEL_CONTENT_V_GAP, KEYPAD_HEIGHT);
		mPosSoftkeyPad.setActionButtonsDisabled(true);
		add(mPosSoftkeyPad);
		setDirty(false);
	}
	
	@Override
	public void onGotFocus() {
		mTextArea.requestFocus(true);
	}
	
	private String getRemarks(){
		return mTextArea.getText();
	}

	@Override
	public boolean onOkButtonClicked() {
		if(isDirty()){
			mOrderDetailItem.setRemarks(getRemarks());
			mOrderDetailItem.setDirty(isDirty());
			mOrderDetailItem.setKitchenDirty(isDirty());
		}
		return true;
	}

	@Override
	public boolean onCancelButtonClicked() {
		return false;
	}

	@Override
	public void onResetButtonClicked() {
		mTextArea.setText(mOrderDetailItem.getRemarks());
		setDirty(false);
		mTextArea.requestFocus(true);
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.pos.forms.listners.IPosFormEventsListner#setReadOnly(boolean)
	 */
	@Override
	public void setReadOnly(boolean isReadOnly) {
		mTextArea.setEditable(!isReadOnly);
		mPosSoftkeyPad.setEnabled(!isReadOnly);
	}


}
