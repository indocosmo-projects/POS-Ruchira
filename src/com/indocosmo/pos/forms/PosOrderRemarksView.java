package com.indocosmo.pos.forms;

import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.indocosmo.pos.common.utilities.PosFormUtil;
import com.indocosmo.pos.data.beans.BeanOrderHeader;

@SuppressWarnings("serial")
public final class PosOrderRemarksView extends PosBaseForm {
	
	private static final int TEXT_AREA_HEIGHT=300;
	private static final int TEXT_AREA_WIDTH=400;
	private static final int SCROLL_BAR_WIDTH=20;
	private static final int PANEL_CONTENT_H_GAP=2;
	private static final int PANEL_CONTENT_V_GAP=2;
	
	private static final int LAYOUT_WIDTH=TEXT_AREA_WIDTH+PANEL_CONTENT_H_GAP*2;;
	private static final int LAYOUT_HEIGHT=TEXT_AREA_HEIGHT+PANEL_CONTENT_V_GAP*2;
	
//	private static final int TEXT_AREA_WIDTH=LAYOUT_WIDTH-PANEL_CONTENT_H_GAP;
	
	private JTextArea mTextArea;

	public PosOrderRemarksView() {
		super("Order Remarks", LAYOUT_WIDTH, LAYOUT_HEIGHT);
		setCancelButtonVisible(false);
	}

	@Override
	protected void setContentPanel(JPanel panel) {
		panel.setLayout(null);
		panel.setSize(LAYOUT_WIDTH, LAYOUT_HEIGHT);
//		panel.setBackground(new Color(6,38,76));
		mTextArea=new JTextArea(5,30);
		mTextArea.setLineWrap(true);
		mTextArea.setEditable(false);
		mTextArea.setFont(PosFormUtil.getTextFieldBoldFont());

		JScrollPane scrolPane=new JScrollPane(mTextArea);
		scrolPane.getVerticalScrollBar().setPreferredSize(new Dimension(SCROLL_BAR_WIDTH,0));
		scrolPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrolPane.setBounds(PANEL_CONTENT_H_GAP,2,TEXT_AREA_WIDTH, TEXT_AREA_HEIGHT);
//		scrolPane.setBorder(new LineBorder(new Color(134, 184, 232)));
		scrolPane.setBorder(null);
		panel.add(scrolPane);

	}
	
	public void setOrderHeaderItem(BeanOrderHeader item ){
		mTextArea.setText(item.getRemarks());
	}


	@Override
	public boolean onOkButtonClicked() {
		return true;
	}

	@Override
	public boolean onCancelButtonClicked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void onResetButtonClicked() {
		
	}

}
