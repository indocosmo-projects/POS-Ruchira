package com.indocosmo.pos.forms.messageboxes;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;

import com.indocosmo.pos.common.utilities.PosFormUtil;
import com.indocosmo.pos.common.utilities.PosResUtil;
import com.indocosmo.pos.forms.listners.IPosWaitMessageBoxFormListner;

@SuppressWarnings("serial")
public class PosWaitMessageBoxForm extends JDialog {
	
	private static final int ICON_HEIGHT=60;
	private static final int ICON_WIDTH=60;
	private static final String ICON_SRC="msg_wait.png";
	
	private static int PANEL_CONTENT_H_GAP=8;
	private static int PANEL_CONTENT_V_GAP=8;
	
	private static int MESSAGE_PANEL_HEIGHT=100;
		
	private static final int FORM_WIDTH=550;
	private static int FORM_HEIGHT=PANEL_CONTENT_V_GAP*2+MESSAGE_PANEL_HEIGHT;

	private static final int MESSAGE_PANEL_WIDTH=FORM_WIDTH-PANEL_CONTENT_H_GAP*2;
	
	private JPanel mContentPane;
	private JPanel mMessagePanel;
	
	private JLabel mMessageLabel;
	private JLabel mMessageIconLabel;
	
	private IPosWaitMessageBoxFormListner mMessageBoxListner;
	
	
	
	
	public PosWaitMessageBoxForm(){
		initControls();
	}
	public PosWaitMessageBoxForm(String message){
		
	}
	
	private void initControls(){
		setSize(FORM_WIDTH, FORM_HEIGHT);
		mContentPane = new JPanel();
		mContentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		mContentPane.setLayout(null);
		setContentPane(mContentPane);
		createPanels();
	}
	
	private void createPanels(){
		mMessagePanel=new JPanel();
		mMessagePanel.setBounds(PANEL_CONTENT_H_GAP, PANEL_CONTENT_V_GAP, MESSAGE_PANEL_WIDTH, MESSAGE_PANEL_HEIGHT);
		mMessagePanel.setLayout(null);
		mMessagePanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
		add(mMessagePanel);
		
		int top=(mMessagePanel.getHeight()/2)-(ICON_HEIGHT/2);
		mMessageIconLabel=new JLabel();
		mMessageIconLabel.setBounds(PANEL_CONTENT_H_GAP, top, ICON_WIDTH,ICON_HEIGHT);
		mMessageIconLabel.setIcon(PosResUtil.getImageIconFromResource(ICON_SRC));
		mMessagePanel.add(mMessageIconLabel);
		
		int left=mMessageIconLabel.getX()+mMessageIconLabel.getWidth()+ PANEL_CONTENT_H_GAP;
		int width=MESSAGE_PANEL_WIDTH-ICON_WIDTH-PANEL_CONTENT_H_GAP*3;
		mMessageLabel=new JLabel();
		mMessageLabel.setBounds(left,PANEL_CONTENT_V_GAP,width, MESSAGE_PANEL_HEIGHT-PANEL_CONTENT_V_GAP*2);
		mMessageLabel.setFont(PosFormUtil.getMessageBoxTextFont());
		mMessagePanel.add(mMessageLabel);
		
	}
	
	public void finish(){
		this.setVisible(false);
		if(mMessageBoxListner!=null)
			mMessageBoxListner.onClosed();
		dispose();
	}
	
	public void setListner(IPosWaitMessageBoxFormListner listner){
		this.mMessageBoxListner =listner;
	}
	
	public void setMessage(String message){
		mMessageLabel.setText("<html>"+message+"</html>");
	}
	


}
