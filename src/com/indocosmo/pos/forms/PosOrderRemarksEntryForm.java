package com.indocosmo.pos.forms;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;

import com.indocosmo.pos.common.utilities.PosFormUtil;
import com.indocosmo.pos.data.beans.BeanOrderHeader;
import com.indocosmo.pos.forms.components.keypads.PosSoftKeyPad;

@SuppressWarnings("serial")
public final class PosOrderRemarksEntryForm extends PosBaseForm {
	
	private static final int TEXT_AREA_HEIGHT=200;
	private static final int SCROLL_BAR_WIDTH=20;
	private static final int PANEL_CONTENT_H_GAP=2;
	private static final int PANEL_CONTENT_V_GAP=5;
	
	private static final int LAYOUT_WIDTH=PosSoftKeyPad.LAYOUT_WIDTH;
	private static final int LAYOUT_HEIGHT=PosSoftKeyPad.LAYOUT_HEIGHT+TEXT_AREA_HEIGHT+PANEL_CONTENT_V_GAP*2;
	
	private static final int TEXT_AREA_WIDTH=LAYOUT_WIDTH-PANEL_CONTENT_H_GAP;
	
	private JTextArea mTextArea;
	private PosSoftKeyPad mPosSoftkeyPad;
	private boolean isDirty=false;
	
	public PosOrderRemarksEntryForm() {
		super("Order Remarks", LAYOUT_WIDTH, LAYOUT_HEIGHT);
		setResetButtonVisible(true);
	}

	@Override
	protected void setContentPanel(JPanel panel) {
		panel.setLayout(null);
		panel.setSize(LAYOUT_WIDTH, LAYOUT_HEIGHT);
		panel.setBackground(new Color(6,38,76));
		mTextArea=new JTextArea(5,30);
		mTextArea.setLineWrap(true);
		mTextArea.setFont(PosFormUtil.getTextFieldBoldFont());
		
		
		JScrollPane scrolPane=new JScrollPane(mTextArea);
		scrolPane.getVerticalScrollBar().setPreferredSize(new Dimension(SCROLL_BAR_WIDTH,0));
		scrolPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrolPane.setBounds(PANEL_CONTENT_H_GAP,2,TEXT_AREA_WIDTH, TEXT_AREA_HEIGHT);
		scrolPane.setBorder(new LineBorder(new Color(134, 184, 232)));
		panel.add(scrolPane);
		
		final int left=LAYOUT_WIDTH/2- PosSoftKeyPad.LAYOUT_WIDTH/2;
		final int top=scrolPane.getY()+scrolPane.getHeight()+PANEL_CONTENT_V_GAP;
		mPosSoftkeyPad=new PosSoftKeyPad(mTextArea,true);
		mPosSoftkeyPad.setLocation(left, top);
		mPosSoftkeyPad.setActionButtonsDisabled(true);
		panel.add(mPosSoftkeyPad);

	}
	
	private BeanOrderHeader mOrderItemHeader;
	public void setOrderHeaderItem(BeanOrderHeader item ){
		mOrderItemHeader=item;
		mTextArea.setText(item.getRemarks());
	}

	public String getRemarks(){
		return mTextArea.getText();
	}

	@Override
	public boolean onOkButtonClicked() {
		isDirty=(mOrderItemHeader.getRemarks()!=getRemarks());
		mOrderItemHeader.setRemarks(getRemarks());
		return true;
	}

	@Override
	public boolean onCancelButtonClicked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void onResetButtonClicked() {
		mTextArea.setText(mOrderItemHeader.getRemarks());
	}

	/**
	 * @return
	 */
	public boolean IsEdited() {
		// TODO Auto-generated method stub
		return isDirty;
	}

	/**
	 * @return
	 */
	public String getEditedRemarks() {
		// TODO Auto-generated method stub
		return mTextArea.getText();
	}
}
