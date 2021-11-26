package com.indocosmo.pos.forms;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import com.indocosmo.pos.common.PosEnvSettings;
import com.indocosmo.pos.common.utilities.PosDateUtil;
import com.indocosmo.pos.common.utilities.PosLicenceUtil;
import com.indocosmo.pos.common.utilities.PosResUtil;
import com.indocosmo.pos.data.beans.BeanAbout;
import com.indocosmo.pos.data.beans.BeanAppVersion;
import com.indocosmo.pos.data.providers.shopdb.PosAboutProvider;
import com.indocosmo.pos.data.providers.shopdb.PosAppVersionProvider;
import com.indocosmo.pos.forms.components.buttons.PosButton;
import com.indocosmo.pos.forms.components.buttons.PosButton.ButtonStyle;
import com.indocosmo.pos.forms.components.buttons.listners.IPosButtonListner;

public final class PosAboutForm extends JDialog {

	private static final long serialVersionUID = 1L;
 
	private static final int PANEL_CONTENT_GAP=2;
 
	private static final String ABOUT_IMAGE = "about.png";

	private static final int PANEL_WIDTH=475;
	
	private static final int TEXT_CONTENT_WIDTH=430;
	
	
	private static final int CONTENT_TEXT_HEIGHT= 120;
	
	private static final int IMAGE_LABEL_HEIGHT= 150;
	private static final int CLOSE_BUTTON_HEIGHT= 20;
	private static final int CLOSE_BUTTON_WIDTH= 80;
	
	private static final int LABEL_HEIGHT= 20;
	private static final int TEXT_CONTENT_PANEL_HEIGHT=LABEL_HEIGHT*8+CONTENT_TEXT_HEIGHT;

	private static int FORM_HEIGHT= IMAGE_LABEL_HEIGHT+TEXT_CONTENT_PANEL_HEIGHT+PANEL_CONTENT_GAP*4 ;
	private static int FORM_WIDTH=PANEL_WIDTH+PANEL_CONTENT_GAP*2;
	 
	private JPanel mContentPanel;
  
	private JLabel mLabelLogoImage ;
	private JLabel mLabelContent;
	private JLabel mLabelVersion;
	private JLabel mLabelBuildNo;
	private JLabel mLabelBuildDate;
	private JLabel mLabelLicensedTo;
	private JLabel mLabelLicensedTillDt;
	private JLabel mLabelCopyRight;
	private PosButton mCloseButton;
	
	public PosAboutForm() {
		
		setSize(  FORM_WIDTH, FORM_HEIGHT);
		setLayout(null);
		setContentPanel();
	}

 
	protected void setContentPanel( ) {

		mContentPanel = new JPanel();
		mContentPanel.setBounds(0, 0, FORM_WIDTH, FORM_HEIGHT);
		mContentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		mContentPanel.setLayout(null);
		mContentPanel.setBackground(Color.WHITE);
		setContentPane(mContentPanel);


	    // create ActionListener
	    ActionListener a=new ActionListener(){
	     
		@Override
		public void actionPerformed(ActionEvent e) {

			dispose();
		}
	    };
	    
	    /*
	    * Register the actionlistener which should
	    * be fired on ESCAPE when the rootpane is
	    * in focused window
	    */
	    
	    this.getRootPane().registerKeyboardAction(a,KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE,0),JComponent.WHEN_IN_FOCUSED_WINDOW);
	 
		createUI();
		loadUI();
	}
	/**
	 * 
	 */
	private void loadUI() {

		PosAppVersionProvider appVersionProvider=new PosAppVersionProvider();
		PosAboutProvider aboutProvider=new PosAboutProvider();
		
		try {
			BeanAppVersion appVersion=appVersionProvider.getAppVerion();
			BeanAbout about=aboutProvider.getAboutDetails();
			mLabelVersion.setText("Posella eezDine " );
			mLabelBuildNo.setText("Version " +
					String.valueOf( String.valueOf(appVersion.getMajor()) + ". " + 
					String.valueOf(appVersion.getMinor())+  ". " + String.valueOf(appVersion.getPatch())) +
					" (Build " + String.valueOf(appVersion.getBuildNo()) + ")");
			mLabelBuildDate.setText("Built on " + 
			(appVersion.getBuildDate()==null?"": 
				PosDateUtil.format(PosDateUtil.DATE_FORMAT_DDMMYY, PosDateUtil.parse(PosDateUtil.DATE_FORMAT_YYYYMMDD, appVersion.getBuildDate()))));
			
			if(about.getContents()!=null && about.getContents().trim().length()>0)
				mLabelContent.setText(about.getContents());
			
			
			if(about.getCopyright()!=null && about.getCopyright().trim().length()>0)
			mLabelCopyRight.setText(about.getCopyright());
			
			mLabelLicensedTo.setText("Licensed to " + PosEnvSettings.getInstance().getShop().getName());
			
			final Date validTill= PosEnvSettings.getInstance().getValidTillDate();
			mLabelLicensedTillDt.setText("Licensed Till " + (validTill==null?"": PosDateUtil.format(PosDateUtil.DATE_FORMAT_DDMMYY, validTill)));
			
		} catch (Exception e) {
			 
		}
		
	}


	private void createUI(){
		
		createAboutImage();
		createAboutContent();
	}
	private void createAboutImage() {
		 
		mLabelLogoImage = new JLabel();
		mLabelLogoImage.setVerticalAlignment(SwingConstants.TOP);
		mLabelLogoImage.setBounds(PANEL_CONTENT_GAP, PANEL_CONTENT_GAP, PANEL_WIDTH, IMAGE_LABEL_HEIGHT );
		mLabelLogoImage.setOpaque(false);
		ImageIcon iconLogo = PosResUtil.getLogoFromResource(ABOUT_IMAGE);
		mLabelLogoImage.setIcon(iconLogo);
		mContentPanel.add(mLabelLogoImage);

	}
	private void createAboutContent() {
		
		final int top=mLabelLogoImage.getY()+ mLabelLogoImage.getHeight()  ;
		
		JPanel textContentPanel=new JPanel();
		textContentPanel.setBounds(PANEL_CONTENT_GAP, top, PANEL_WIDTH  , TEXT_CONTENT_PANEL_HEIGHT);
		textContentPanel.setBorder(null);
		textContentPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 30, 0));
		textContentPanel.setOpaque(false);
//		textContentPanel.setBackground(Color.RED);
		mContentPanel.add(textContentPanel);
		
		JLabel labelEmpty=new JLabel();
		labelEmpty.setPreferredSize(new Dimension(TEXT_CONTENT_WIDTH,LABEL_HEIGHT));
		labelEmpty.setHorizontalAlignment(SwingConstants.LEFT);
		labelEmpty.setOpaque(false);
//		labelEmpty.setBackground(Color.BLUE);
		textContentPanel.add(labelEmpty);
		
		mLabelVersion=new JLabel();
		mLabelVersion.setPreferredSize(new Dimension(TEXT_CONTENT_WIDTH,LABEL_HEIGHT));
		mLabelVersion.setHorizontalAlignment(SwingConstants.LEFT);
//		mLabelVersion.setOpaque(true);
//		mLabelVersion.setBackground(Color.BLUE);
		textContentPanel.add(mLabelVersion);
		
		mLabelBuildNo=new JLabel();
		mLabelBuildNo.setPreferredSize(new Dimension(TEXT_CONTENT_WIDTH,LABEL_HEIGHT));
		mLabelBuildNo.setHorizontalAlignment(SwingConstants.LEFT);
		mLabelBuildNo.setOpaque(false);
		textContentPanel.add(mLabelBuildNo);
		
		mLabelBuildDate=new JLabel();
		mLabelBuildDate.setPreferredSize(new Dimension(TEXT_CONTENT_WIDTH,LABEL_HEIGHT));
		mLabelBuildDate.setHorizontalAlignment(SwingConstants.LEFT);
		mLabelBuildDate.setOpaque(false);
		textContentPanel.add(mLabelBuildDate);
		
		mLabelLicensedTo=new JLabel();
		mLabelLicensedTo.setPreferredSize(new Dimension(TEXT_CONTENT_WIDTH,LABEL_HEIGHT));
		mLabelLicensedTo.setHorizontalAlignment(SwingConstants.LEFT);
		mLabelLicensedTo.setOpaque(false);
		textContentPanel.add(mLabelLicensedTo);
		
		
		mLabelLicensedTillDt=new JLabel();
		mLabelLicensedTillDt.setPreferredSize(new Dimension(TEXT_CONTENT_WIDTH,LABEL_HEIGHT));
		mLabelLicensedTillDt.setHorizontalAlignment(SwingConstants.LEFT);
		mLabelLicensedTillDt.setOpaque(false);
		textContentPanel.add(mLabelLicensedTillDt);
		
		final String contentHtml="<html><body><p><br>Warning: This computer program is protected by copyright law and international treaties. "
				+ "Unauthorized reproduction or distribution of this program, or any portion of it, may result in severe civil and criminal penalties, "
				+ "and will be prosecuted to the maximum extent possible under the law. <a href=\"http://www.indocosmo.com\">http://www.indocosmo.com/</a> </p> </body></html>";
		
		mLabelContent = new JLabel();
		mLabelContent.setVerticalAlignment(SwingConstants.TOP);
		mLabelContent.setPreferredSize(new Dimension(TEXT_CONTENT_WIDTH, CONTENT_TEXT_HEIGHT ));
		
		mLabelContent.setOpaque(true);
		mLabelContent.setBackground(Color.white);
		mLabelContent.setText(contentHtml);
		textContentPanel.add(mLabelContent);

		final String copyRightHtml="<html><body>Copyright @ 2014-"+ PosDateUtil.getDate("YYYY")+" Indocosmo Systems Pvt. Ltd. All rights reserverd.</body></html>";
		
		mLabelCopyRight=new JLabel();
		mLabelCopyRight.setPreferredSize(new Dimension(PANEL_WIDTH,LABEL_HEIGHT));
		mLabelCopyRight.setHorizontalAlignment(SwingConstants.LEFT);
		mLabelCopyRight.setText(copyRightHtml);
		mLabelCopyRight.setOpaque(false);
		textContentPanel.add(mLabelCopyRight);
		
		mCloseButton=new PosButton("Close");
		mCloseButton.setForeground(Color.RED);
		mCloseButton.setHorizontalAlignment(SwingConstants.RIGHT);
		mCloseButton.setButtonStyle(ButtonStyle.COLORED);
		mCloseButton.setBackgroundColor(Color.RED, Color.PINK);
		mCloseButton.setBounds(FORM_WIDTH-CLOSE_BUTTON_WIDTH-5,FORM_HEIGHT-CLOSE_BUTTON_HEIGHT-6,CLOSE_BUTTON_WIDTH,CLOSE_BUTTON_HEIGHT);
		mCloseButton.setOnClickListner(new IPosButtonListner() {
			
			@Override
			public void onClicked(PosButton button) {
			
				dispose(); 
				
			}
		});
		mContentPanel.add(mCloseButton); 
		
	}
}
