package com.indocosmo.pos.forms.components.terminal;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.RootPaneContainer;

import com.indocosmo.pos.common.PosLog;
import com.indocosmo.pos.common.utilities.PosFormUtil;
import com.indocosmo.pos.common.utilities.PosResUtil;
import com.indocosmo.pos.forms.components.buttons.PosButton;
import com.indocosmo.pos.forms.components.buttons.listners.IPosButtonListner;
import com.indocosmo.pos.forms.messageboxes.PosMessageBoxForm.MessageBoxButtonTypes;
import com.indocosmo.pos.forms.messageboxes.PosMessageBoxForm.MessageBoxResults;

@SuppressWarnings("serial")
public final class PosProcessLogDisplayPanel extends JPanel{

	private static final int PANEL_CONTENT_H_GAP=2;
	private static final int PANEL_CONTENT_V_GAP=2;
	private static final String IMAGE_BUTTON_LOG_CLEAR ="process_log_clear_button.png";
	private static final String IMAGE_BUTTON_LOG_CLEAR_TOUCHED = "process_log_clear_button_touched.png";
	private static final int MESSAGE_PANEL_HEIGHT=30;
	private static final int LOG_BUTTON_HEIGHT=50;
	private static final Color LABEL_BG_COLOR=new Color(78,128,188);
	
	private JTextPane mTextLogDetails;
	private int mHeight;
	private int mWidth;
	private JScrollPane mScrollPane ;
	private PosButton mLogSaveButton;
	private PosButton mLogClearButton;
	private String mMessage="";
	private RootPaneContainer mParent;
	
	public PosProcessLogDisplayPanel(RootPaneContainer parent,int width, int height){
		mParent=parent;
		mHeight=height;
		mWidth=width-PANEL_CONTENT_H_GAP*2;	
		initControls();
	}
	
	private void initControls(){
		this.setSize(mWidth,mHeight );
		this.setPreferredSize(new Dimension(mWidth,mHeight ));
		this.setLayout(new FlowLayout(FlowLayout.CENTER,PANEL_CONTENT_H_GAP,PANEL_CONTENT_V_GAP));
		setShortMessagePanel("Process Logs");
		setLogDisplayArea();
		setLogControlButtons();
	}
	
	

	private void setLogDisplayArea(){
		final int width=getWidth()-PANEL_CONTENT_H_GAP*2;
		final int height=getHeight()-LOG_BUTTON_HEIGHT-MESSAGE_PANEL_HEIGHT-PANEL_CONTENT_V_GAP*4;
		mTextLogDetails=new JTextPane();
		mTextLogDetails.setContentType("text/html");
		mTextLogDetails.setEditable(false);
		mTextLogDetails.setFocusable(false);
		mTextLogDetails.setFont(PosFormUtil.getTextAreaFont());
		mScrollPane = new JScrollPane(mTextLogDetails);
		mScrollPane.setPreferredSize(new Dimension(width,height ));
		mScrollPane.setSize(new Dimension(width,height ));
		add(mScrollPane); 
	}
	
	private void setShortMessagePanel(String message){
//		JLabel labelMessage=new JLabel();
//		labelMessage.setText(message);
//		labelMessage.setHorizontalAlignment(SwingConstants.CENTER);		
//		labelMessage.setPreferredSize(new Dimension(getWidth()-4, MESSAGE_PANEL_HEIGHT));				
//		labelMessage.setOpaque(true);
//		labelMessage.setBackground(LABEL_BG_COLOR);
//		labelMessage.setForeground(Color.WHITE);
		JLabel labelMessage=PosFormUtil.setHeading(message, getWidth()-5,MESSAGE_PANEL_HEIGHT);
		add(labelMessage);
	}
	
	
	public void write(String message) {
		mMessage+=message+"<br>";
//		mTextLogDetails.setText("<html><font face='verdana' size='15'>"+mMessage+"</font></html>");
		mTextLogDetails.setText("<html>"+mMessage+"</html>");
		mTextLogDetails.setCaretPosition( mTextLogDetails.getDocument().getLength() );
	}
	
	/**
	 * 
	 */
	private void setLogControlButtons() {
		final int width=(mScrollPane.getWidth()-PANEL_CONTENT_H_GAP)/2;
		final int height = LOG_BUTTON_HEIGHT;
		mLogSaveButton = new PosButton("Save");
//		mLogSaveButton.setMnemonic('S');
		mLogSaveButton.setPreferredSize(new Dimension(width, height));
		mLogSaveButton.setImage(PosResUtil.getImageIconFromResource(IMAGE_BUTTON_LOG_CLEAR));
		mLogSaveButton.setTouchedImage(PosResUtil.getImageIconFromResource(IMAGE_BUTTON_LOG_CLEAR_TOUCHED));
		mLogSaveButton.setOnClickListner(new IPosButtonListner() {
			
			@Override
			public void onClicked(PosButton button) {
				PosLog.writeProcessLog(mTextLogDetails.getText());
				if(PosFormUtil.showQuestionMessageBox(mParent, MessageBoxButtonTypes.YesNo, "The log has been saved to log folder. Do you want to clear the log now?", null)==MessageBoxResults.Yes){
					mTextLogDetails.setText("");
				}
			}
		});
		add(mLogSaveButton);
		mLogClearButton = new PosButton("Clear");
//		mLogClearButton.setMnemonic('C');
		mLogClearButton.setImage(PosResUtil.getImageIconFromResource(IMAGE_BUTTON_LOG_CLEAR));
		mLogClearButton.setTouchedImage(PosResUtil.getImageIconFromResource(IMAGE_BUTTON_LOG_CLEAR_TOUCHED));
		mLogClearButton.setPreferredSize(new Dimension(width, height));
		mLogClearButton.setOnClickListner(new IPosButtonListner() {
			
			@Override
			public void onClicked(PosButton button) {
				PosLog.writeProcessLog(mTextLogDetails.getText());
				mTextLogDetails.setText("");
				mMessage="";
			}
		});
		add(mLogClearButton);
	}
}
