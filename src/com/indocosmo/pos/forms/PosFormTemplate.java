package com.indocosmo.pos.forms;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;

import com.indocosmo.pos.common.PosEnvSettings;
import com.indocosmo.pos.common.utilities.PosFormUtil;
import com.indocosmo.pos.common.utilities.PosResUtil;
import com.indocosmo.pos.common.utilities.PosUtil;
import com.indocosmo.pos.forms.components.buttons.PosButton;
import com.indocosmo.pos.forms.components.buttons.listners.IPosButtonListner;
import com.indocosmo.pos.forms.components.buttons.listners.PosButtonListnerAdapter;
import com.indocosmo.pos.forms.listners.IPosTerminalSettingsFormListner;

public  class PosFormTemplate extends JDialog {


	private static final int TITLE_PANEL_HEIGHT=30;	
	private static final int CONTENT_PANEL_HEIGHT=520; 
	private static final int BOTTOM_PANEL_HEIGHT=80; 


	private static final int TITLE_LABEL_WIDTH=20;
	private static final int TITLE_LABEL_HEIGHT=20;

	private static final int IMAGE_BUTTON_WIDTH=150;
	private static final int IMAGE_BUTTON_HEIGHT=60;

	private static final int PANEL_CONTENT_H_GAP=PosOrderEntryForm.PANEL_CONTENT_H_GAP;
	private static final int PANEL_CONTENT_V_GAP=PosOrderEntryForm.PANEL_CONTENT_V_GAP;

	private static final int FORM_HEIGHT=TITLE_PANEL_HEIGHT+CONTENT_PANEL_HEIGHT+BOTTOM_PANEL_HEIGHT+PANEL_CONTENT_V_GAP*8;
	private static final int FORM_WIDTH=600;

	private JPanel mContentPane;
	private JPanel mTitlePanel;
	private JPanel mContentPanel;
	private JPanel mBottomPanel;

	private JLabel mlabelTitle;

	private PosButton mButtonOk;
	private PosButton mButtonCacel;			

	
	private static final String IMAGE_BUTTON_OK="settings.png";
	private static final String IMAGE_BUTTON_OK_TOUCH="settings_touch.png";

	private static final String IMAGE_BUTTON_CANCEL="settings_cancel.png";
	private static final String IMAGE_BUTTON_CANCEL_TOUCH="settings_cancel_touch.png";	

	private static final Color PANEL_BG_COLOR=new Color(78,128,188);
	private static final Color LABEL_BG_COLOR=new Color(78,128,188);
	private static final Color LABEL_FG_COLOR=Color.WHITE;

//	private IPosTerminalSettingsFormListner mPosTerminalFormListner;
//	private PosTabControl mTerminalSettingsTab;
//	private ArrayList<PosTab> mTabList;

	public PosFormTemplate(){

		initControls();				
	}	

	private void initControls(){

		Dimension dim =PosUtil.getScreenSize(PosEnvSettings.getInstance().getPrimaryScreen());
		int left = (dim.width-FORM_WIDTH)/2;
		int top = (dim.height-FORM_HEIGHT)/2;
		setUndecorated(true);
		//		setAlwaysOnTop(false);		
		setBounds(left,top,FORM_WIDTH, FORM_HEIGHT);

		mContentPane = new JPanel();
		mContentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		mContentPane.setLayout(null);
		setContentPane(mContentPane);

		mTitlePanel = new JPanel();
		mTitlePanel.setBounds(PANEL_CONTENT_H_GAP, 
				PANEL_CONTENT_V_GAP, 
				FORM_WIDTH-PANEL_CONTENT_H_GAP*2, TITLE_PANEL_HEIGHT*2);
		mTitlePanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
		mTitlePanel.setBackground(PANEL_BG_COLOR);	
		add(mTitlePanel);	

		mContentPanel = new JPanel();
		mContentPanel.setBounds(PANEL_CONTENT_H_GAP, 
				mTitlePanel.getY()+ mTitlePanel.getHeight()+PANEL_CONTENT_V_GAP, 
				FORM_WIDTH-PANEL_CONTENT_H_GAP*2, CONTENT_PANEL_HEIGHT);
		mContentPanel.setLayout(null);
		//		mContentPanel.setBackground(Color.GREEN);
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
		setBottomPanel();		

	}


	private void setTitlePanelContent(){
		setTitle();
	}		

	private void setTitle(){
		mlabelTitle=new JLabel();		
		mlabelTitle.setHorizontalAlignment(SwingConstants.CENTER);	
		mlabelTitle.setVerticalAlignment(SwingConstants.CENTER);
		mlabelTitle.setText("Shift");
		mlabelTitle.setPreferredSize(new Dimension(mTitlePanel.getWidth()-PANEL_CONTENT_H_GAP*2, mTitlePanel.getHeight()-PANEL_CONTENT_V_GAP*2));
		mlabelTitle.setFont(PosFormUtil.getHeadingFont());
		mlabelTitle.setOpaque(true);
		mlabelTitle.setBackground(LABEL_BG_COLOR);
		mlabelTitle.setForeground(LABEL_FG_COLOR);
		mTitlePanel.add(mlabelTitle);		
	}

	private void setBottomPanel(){
		createOkButton();
	}

	private void createOkButton(){			
		int left=mBottomPanel.getX()+PANEL_CONTENT_H_GAP*10;
		mButtonOk=new PosButton();		
		mButtonOk.setText("OK");
		mButtonOk.setImage(PosResUtil.getImageIconFromResource(IMAGE_BUTTON_OK));
		mButtonOk.setTouchedImage(PosResUtil.getImageIconFromResource(IMAGE_BUTTON_OK_TOUCH));	
		mButtonOk.setHorizontalAlignment(SwingConstants.CENTER);		
		mButtonOk.setBounds(left, PANEL_CONTENT_V_GAP*5, IMAGE_BUTTON_WIDTH, IMAGE_BUTTON_HEIGHT );	
		mButtonOk.setOnClickListner(okButtonListner);
		mBottomPanel.add(mButtonOk);

		left=mButtonOk.getX()+mButtonOk.getWidth()+PANEL_CONTENT_V_GAP*2;
		mButtonCacel=new PosButton();		
		mButtonCacel.setText("Cancel");
		mButtonCacel.setImage(PosResUtil.getImageIconFromResource(IMAGE_BUTTON_CANCEL));
		mButtonCacel.setTouchedImage(PosResUtil.getImageIconFromResource(IMAGE_BUTTON_CANCEL_TOUCH));	
		mButtonCacel.setHorizontalAlignment(SwingConstants.CENTER);		
		mButtonCacel.setBounds(left, PANEL_CONTENT_V_GAP, IMAGE_BUTTON_WIDTH, IMAGE_BUTTON_HEIGHT);		
		mButtonCacel.setOnClickListner(imgCancelButtonListner);
		mBottomPanel.add(mButtonCacel);				
	}		


	private  IPosButtonListner imgCancelButtonListner= new PosButtonListnerAdapter() {
		@Override
		public void onClicked(PosButton button) {			
			setVisible(false);
			
			dispose();			
		}
	};		

	private IPosButtonListner okButtonListner=new IPosButtonListner(){
		public void onClicked(PosButton button) {		
			
			closeWindow();
		}
	};	
	
	public void closeWindow(){
		PosFormTemplate.this.setVisible(false);
		dispose();
	}

	public void setListner(IPosTerminalSettingsFormListner listner){
//		mPosTerminalFormListner=listner;
	}




}
