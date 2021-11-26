package com.indocosmo.pos.forms.components.orderentry.itemedit;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
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

@SuppressWarnings("serial")
public final class PosTabOrderItemRemarksEdit extends PosTab implements IPosFormEventsListner{
	
	private static final int TEXT_AREA_HEIGHT=200;
	private static final int SCROLL_BAR_WIDTH=20;
	private static final int PANEL_CONTENT_H_GAP=2;
	private static final int PANEL_CONTENT_V_GAP=PosOrderEntryForm.PANEL_CONTENT_V_GAP;
	
	private static final int LAYOUT_WIDTH=PosTabOrderItemAttributeEdit.LAYOUT_WIDTH;
	private static final int LAYOUT_HEIGHT=PosSoftKeyPadMultiLineInput.LAYOUT_HEIGHT+TEXT_AREA_HEIGHT+PANEL_CONTENT_V_GAP*2;
	
	private static final int TEXT_AREA_WIDTH=LAYOUT_WIDTH-PANEL_CONTENT_H_GAP;
	
	private JTextArea mTextArea;
	private PosSoftKeyPad mPosSoftkeyPad;
	private BeanOrderDetail mOrderDetailItem;
	
	public PosTabOrderItemRemarksEdit(Object parent,BeanOrderDetail orderDetailItem){
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
		scrolPane.setBounds(PANEL_CONTENT_H_GAP,2,TEXT_AREA_WIDTH, TEXT_AREA_HEIGHT);
		scrolPane.setBorder(new LineBorder(new Color(134, 184, 232)));
		add(scrolPane);
		
		final int left=LAYOUT_WIDTH/2- PosSoftKeyPad.LAYOUT_WIDTH/2;
		final int top=scrolPane.getY()+scrolPane.getHeight()+PANEL_CONTENT_V_GAP;
		mPosSoftkeyPad=new PosSoftKeyPad(mTextArea,true);
		mPosSoftkeyPad.setLocation(left, top);
		mPosSoftkeyPad.setActionButtonsDisabled(true);
		add(mPosSoftkeyPad);
		setDirty(false);
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
