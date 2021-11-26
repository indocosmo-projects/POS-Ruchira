package com.indocosmo.pos.forms;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;

import com.indocosmo.pos.common.utilities.PosFormUtil;
import com.indocosmo.pos.common.utilities.PosResUtil;
import com.indocosmo.pos.forms.components.buttons.PosButton;
import com.indocosmo.pos.forms.components.buttons.listners.IPosButtonListner;
import com.indocosmo.pos.forms.components.buttons.listners.PosButtonListnerAdapter;
import com.indocosmo.pos.forms.components.terminal.shift.PosAttendancePanel;
import com.indocosmo.pos.forms.listners.IPosAttendanceFormListener;

public final class PosAttendanceForm extends JDialog {

	private static final long serialVersionUID = 1L;
	private static final int TITLE_PANEL_HEIGHT=60;	
	private static final int BOTTOM_PANEL_HEIGHT=76; 

	private static final int IMAGE_BUTTON_WIDTH=150;
	private static final int IMAGE_BUTTON_HEIGHT=60;

	private static final int PANEL_CONTENT_H_GAP=PosOrderEntryForm.PANEL_CONTENT_H_GAP;
	private static final int PANEL_CONTENT_V_GAP=PosOrderEntryForm.PANEL_CONTENT_V_GAP;

	
	private static final String IMAGE_BUTTON_IN="dlg_ok.png";
	private static final String IMAGE_BUTTON_IN_TOUCH="dlg_ok_touch.png";
	
	private static final String IMAGE_BUTTON_OUT="dlg_ok.png";
	private static final String IMAGE_BUTTON_OUT_TOUCH="dlg_ok_touch.png";	

	private static final String IMAGE_BUTTON_CANCEL="dlg_cancel.png";
	private static final String IMAGE_BUTTON_CANCEL_TOUCH="dlg_cancel_touch.png";	

	private static final Color PANEL_BG_COLOR=new Color(78,128,188);
	private static final Color LABEL_BG_COLOR=new Color(78,128,188);
	private static final Color LABEL_FG_COLOR=Color.WHITE;

	private static final int CONTENT_PANEL_HEIGHT=PosAttendancePanel.LAYOUT_HEIGHT;
	private static final int CONTENT_PANEL_WIDTH=PosAttendancePanel.LAYOUT_WIDTH;

	private  int FORM_HEIGHT=CONTENT_PANEL_HEIGHT+TITLE_PANEL_HEIGHT+BOTTOM_PANEL_HEIGHT+PANEL_CONTENT_V_GAP*4;
	private  int FORM_WIDTH=CONTENT_PANEL_WIDTH+PANEL_CONTENT_H_GAP*2;
	
	private JPanel mContentPane;
	private JPanel mTitlePanel;
	protected JPanel mContentPanel;
	private JPanel mBottomPanel;
	private JLabel mlabelTitle;

	private PosButton mButtonIn;
	private PosButton mButtonOut;
	private PosButton mButtonCancel;

	private String mTitle;
	protected IPosAttendanceFormListener mListner;
//	private PosShiftOpenPanel mShiftPanel;
	private PosAttendancePanel mAttendancePanel;
	private PosMainMenuForm mParent;
	private boolean mIsOrderEntry=false;
	
	public PosAttendanceForm(PosMainMenuForm parent,boolean isOrderEntry) {
		mIsOrderEntry = isOrderEntry;
		mParent=parent;
		mTitle="Staff Attendance";
		initControls();
		
	}

	private void initControls(){
		setSize(FORM_WIDTH, FORM_HEIGHT);

		mContentPane = new JPanel();
//		mContentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		mContentPane.setLayout(null);
		setContentPane(mContentPane);

		mTitlePanel = new JPanel();
		mTitlePanel.setBounds(PANEL_CONTENT_H_GAP, 
				PANEL_CONTENT_V_GAP, 
				FORM_WIDTH-PANEL_CONTENT_H_GAP*2, TITLE_PANEL_HEIGHT);
		mTitlePanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
		mTitlePanel.setBackground(PANEL_BG_COLOR);	
		add(mTitlePanel);	

		mContentPanel = new JPanel();
		mContentPanel.setBounds(PANEL_CONTENT_H_GAP, 
				mTitlePanel.getY()+ mTitlePanel.getHeight()+PANEL_CONTENT_V_GAP, 
				FORM_WIDTH-PANEL_CONTENT_H_GAP*2, CONTENT_PANEL_HEIGHT);
		mContentPanel.setLayout(null);
		mContentPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
		add(mContentPanel);

		mBottomPanel = new JPanel();
		mBottomPanel.setBounds(PANEL_CONTENT_H_GAP, 
				mContentPanel.getY()+ mContentPanel.getHeight()+PANEL_CONTENT_V_GAP, 
				FORM_WIDTH-PANEL_CONTENT_H_GAP*2, BOTTOM_PANEL_HEIGHT);
		mBottomPanel.setLayout(new FlowLayout());
		mBottomPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
		add(mBottomPanel);

		setTitlePanelContent();	
		setContentPanel();
		setBottomPanel();	
		addWindowListener(wndListener);

	}

	private void setTitlePanelContent(){
		setTitle();
	}		

	private void setTitle(){
		mlabelTitle=new JLabel();		
		mlabelTitle.setHorizontalAlignment(SwingConstants.CENTER);	
		mlabelTitle.setVerticalAlignment(SwingConstants.CENTER);
		mlabelTitle.setText(mTitle);
		mlabelTitle.setPreferredSize(new Dimension(mTitlePanel.getWidth()-PANEL_CONTENT_H_GAP*2, mTitlePanel.getHeight()-PANEL_CONTENT_V_GAP*2));
		mlabelTitle.setFont(PosFormUtil.getHeadingFont());
		mlabelTitle.setOpaque(true);
		mlabelTitle.setBackground(LABEL_BG_COLOR);
		mlabelTitle.setForeground(LABEL_FG_COLOR);
		mTitlePanel.add(mlabelTitle);		
	}

	private void setBottomPanel(){
		createButtons();
	}

	public void setCardNumber(String cardNumber){
		mAttendancePanel.setCardNumber(cardNumber);
	}
	
	private void createButtons(){			
		int left=mBottomPanel.getX()+PANEL_CONTENT_H_GAP*10;
		mButtonIn=new PosButton();		
		mButtonIn.setText("IN");
		mButtonIn.setImage(PosResUtil.getImageIconFromResource(IMAGE_BUTTON_IN));
		mButtonIn.setTouchedImage(PosResUtil.getImageIconFromResource(IMAGE_BUTTON_IN_TOUCH));	
		mButtonIn.setHorizontalAlignment(SwingConstants.CENTER);		
		mButtonIn.setBounds(left, PANEL_CONTENT_V_GAP*5, IMAGE_BUTTON_WIDTH, IMAGE_BUTTON_HEIGHT );	
		mButtonIn.setOnClickListner(mInButtonListner);
		mButtonIn.setEnabled(false);
		mBottomPanel.add(mButtonIn);

		left=mButtonIn.getX()+mButtonIn.getWidth()+PANEL_CONTENT_V_GAP*2;
		mButtonOut=new PosButton();		
		mButtonOut.setText("Out");
		mButtonOut.setImage(PosResUtil.getImageIconFromResource(IMAGE_BUTTON_OUT));
		mButtonOut.setTouchedImage(PosResUtil.getImageIconFromResource(IMAGE_BUTTON_OUT_TOUCH));	
		mButtonOut.setHorizontalAlignment(SwingConstants.CENTER);		
		mButtonOut.setBounds(left, PANEL_CONTENT_V_GAP, IMAGE_BUTTON_WIDTH, IMAGE_BUTTON_HEIGHT);		
		mButtonOut.setOnClickListner(mOutButtonListner);
		mButtonOut.setEnabled(false);
		mBottomPanel.add(mButtonOut);				

		left=mButtonOut.getX()+mButtonOut.getWidth()+PANEL_CONTENT_V_GAP*2;
		mButtonCancel=new PosButton();		
		mButtonCancel.setText("Cancel");
		mButtonCancel.setCancel(true);
		mButtonCancel.setImage(PosResUtil.getImageIconFromResource(IMAGE_BUTTON_CANCEL));
		mButtonCancel.setTouchedImage(PosResUtil.getImageIconFromResource(IMAGE_BUTTON_CANCEL_TOUCH));	
		mButtonCancel.setHorizontalAlignment(SwingConstants.CENTER);		
		mButtonCancel.setBounds(left, PANEL_CONTENT_V_GAP, IMAGE_BUTTON_WIDTH, IMAGE_BUTTON_HEIGHT);		
		mButtonCancel.setOnClickListner(mCancelButtonListner);
		mBottomPanel.add(mButtonCancel);				
}		

	WindowListener wndListener = new WindowAdapter() {
		@Override
		public void windowActivated(WindowEvent e) {
			mAttendancePanel.setDefaultFocus();
		}
	};
	private  IPosButtonListner mCancelButtonListner= new PosButtonListnerAdapter() {
		@Override
		public void onClicked(PosButton button) {	
			onCancelButtonClicked();
		}
	};	

	private void onCancelButtonClicked(){
		mAttendancePanel.cancelButtonClicked();
		if(mListner!=null)
			mListner.cancelButtonClicked();
		closeWindow();
	}

	private IPosButtonListner mInButtonListner=new IPosButtonListner(){
		public void onClicked(PosButton button) {		
			onInButtonClicked();
		}
	};
	
	private void onInButtonClicked(){
		if(!mAttendancePanel.inButtonClicked()) return;
		if(mListner!=null)
			mListner.inButtonClicked();
		closeWindow();
	}
	
	private IPosButtonListner mOutButtonListner=new IPosButtonListner(){
		public void onClicked(PosButton button) {		
			onOutButtonClicked();
		}
	};
	
	private void onOutButtonClicked(){
		if(!mAttendancePanel.outButtonClicked()) return;
		if(mListner!=null)
			mListner.outButtonClicked();
		closeWindow();
	}

	public void setInButtonEnabled(boolean inButtonEnable) {
		mButtonIn.setEnabled(inButtonEnable);
	}

	public void setOutButtonEnabled(boolean outButtonEnable) {
		mButtonOut.setEnabled(outButtonEnable);
	}

	public void setListner(IPosAttendanceFormListener  listner){
		mListner=listner;
	}

	/**
	 * Closes the window.
	 */
	private void closeWindow(){
		setVisible(false);
		dispose();
	}

//	@Override
	private void setContentPanel() {
		mAttendancePanel=new PosAttendancePanel(this,mIsOrderEntry);
		mAttendancePanel.setLocation(0, 0);
		mContentPanel.add(mAttendancePanel);
	}
}
