package com.indocosmo.pos.forms.components.orderentry;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.RootPaneContainer;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import com.indocosmo.pos.common.PosEnvSettings;
import com.indocosmo.pos.common.utilities.PosDateUtil;
import com.indocosmo.pos.common.utilities.PosResUtil;
import com.indocosmo.pos.forms.PosOrderEntryForm;
import com.indocosmo.pos.forms.components.labels.PosMarqueeLabel;

public final class PosTitlePanel extends JPanel  {

	public static final int LAYOUT_HEIGHT=PosOrderEntryForm.TOP_PANEL_LAYOUT_HEIGHT;
	public static final int LAYOUT_WIDTH=150;
	private static final int PANEL_H_GAP=PosOrderEntryForm.PANEL_CONTENT_H_GAP;
	private static final int PANEL_V_GAP=PosOrderEntryForm.PANEL_CONTENT_V_GAP;
	private static final int LABEL_WIDTH=90;	
	
	private static final int DATE_LABEL_WIDTH=400;
	private static final int DATE_LABEL_HEIGHT=15;

	//public static final String TOP_TITLE_PANEL_BG_COLOR="#2B4564";
	public static final String TOP_TITLE_PANEL_BG_COLOR="#000000";

	public static final Color PANEL_BG_COLOR=Color.decode(TOP_TITLE_PANEL_BG_COLOR);
	
	private static final String IMAGE_LOGO = "order_entry_header.png";
//	public static final Color PANEL_BG_COLOR=PosOrderEntryForm.PANEL_BG_COLOR;
	
		
//	private int mLeft, mTop;
	private RootPaneContainer mParent;
	private JLabel mLabelLogoImage;
	private PosMarqueeLabel mLabelScrollMessage;
	private JLabel mLabelDateTime;
	private int mWidth;
	private JPanel mContainer;
	private static Font labelDateTimeFont = new Font("SanSerif", Font.BOLD, 16);

//	public PosTitlePanel(int left, int top) {
//		mLeft=left;
//		mTop=top;
//		initComponent();
//		createTitleControls();
//	}
	
	/**
	 * @param posOrderEntryForm
	 */
	public PosTitlePanel(RootPaneContainer parent,int width) {
		mParent=parent;
		mWidth=width;
		initComponent();
		createTitleControls();
		
	}

	private void createTitleControls()
	{
		createLogoImage();
		createTitle();
//		createFlashMessage();
		createDateTime();
		setTimer();
//		PosFormUtil.checkForScrollMessges(PosTitlePanel.this);
	}
	
	
	private void createLogoImage()
	{		
		mLabelLogoImage = new JLabel();			
		mLabelLogoImage.setBounds(PANEL_H_GAP, PANEL_V_GAP, this.getWidth(), LAYOUT_HEIGHT-PANEL_V_GAP);
//		mLabelLogoImage.setIcon(PosResUtil.getImageIconFromResource("logo.png"));
		ImageIcon iconLogo = PosResUtil.getLogoFromResource(IMAGE_LOGO);
		mLabelLogoImage.setIcon(iconLogo);
//		mLabelLogoImage.setIcon(PosResUtil.getImageIconFromResource("ics_logo.png"));
		mContainer.add(mLabelLogoImage);
	}
	
 

	private void createTitle()
	{
		JLabel labelTitle;	
		int top=PANEL_V_GAP;		
		int left=mLabelLogoImage.getX()+mLabelLogoImage.getWidth()+PANEL_H_GAP;		
		labelTitle = new JLabel();	
		labelTitle.setText(PosEnvSettings.getInstance().getShop().getName());
//		labelTitle = new JLabel("CHAK DE RESTAURANT");	
//		labelTitle = new JLabel("INDOCOSMO SYSTEMS DEMO SHOP");	
		labelTitle.setBounds(left, top, 400, 20);
		labelTitle.setForeground(new Color(28,143,2));
		labelTitle.setFont(new Font("Dialog", Font.BOLD, 18));
//		mContainer.add(labelTitle);
		
//		top = labelTitle.getX()+labelTitle.getHeight()+PANEL_V_GAP;
		JLabel labelShopName = new JLabel();
		labelShopName.setText(PosEnvSettings.getInstance().getShop().getName());
		labelShopName.setBounds(left, top+50, 400, 20);
		labelShopName.setForeground(new Color(28,143,2));
		labelShopName.setFont(new Font("Dialog", Font.BOLD, 10));
//		mContainer.add(labelShopName);
	}
	
	
	/**
	 * 
	 */
//	private void createFlashMessage() {
//
//		PosFlashMessagePanel flashMsgPnl = new PosFlashMessagePanel();
//		int top=this.getHeight()-DATE_LABEL_HEIGHT-PANEL_V_GAP;	
//		int left=mLabelLogoImage.getX()+mLabelLogoImage.getWidth()+PANEL_H_GAP;		
//		flashMsgPnl.setBounds(left, top, 400, 20);
//		add(flashMsgPnl);
//		
//	}
	
	private void createDateTime()
	{	
		int left=0;//mContainer.getWidth()-DATE_LABEL_WIDTH-PANEL_H_GAP;
		int top=mContainer.getHeight()-DATE_LABEL_HEIGHT;
		mLabelDateTime = new JLabel(PosDateUtil.getDateTime().toString(),SwingConstants.RIGHT);		
		mLabelDateTime.setForeground(Color.WHITE);		
		mLabelDateTime.setBounds(left, top,getWidth()-PANEL_H_GAP,DATE_LABEL_HEIGHT );
		mLabelDateTime.setFont(labelDateTimeFont);
		mLabelDateTime.setHorizontalAlignment(SwingConstants.RIGHT);
//		mLabelDateTime.setFont(PosFormUtil.getDigitalFont());
		mContainer.add(mLabelDateTime);
		
	}
	
	private  void  setTimer() {
		ActionListener actionListener = new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				mLabelDateTime.setText(PosDateUtil.getDateTime().toString());	
			}
		};
		Timer timer = new Timer(500, actionListener);
		timer.start();
	}
	
	private void initComponent() {			
//		int width=PosUtil.getScreenWidth()-mWidth;
		int height=LAYOUT_HEIGHT;
		setSize(mWidth, height);
		setPreferredSize(new Dimension(mWidth, height));
		setBackground(PANEL_BG_COLOR);
		setLayout(new FlowLayout(FlowLayout.LEFT,0,0));
		setBackground(Color.GREEN);
		
		mContainer=new JPanel();
		mContainer.setLayout(null);
		mContainer.setSize(mWidth, height);
		mContainer.setPreferredSize(new Dimension(mWidth, height));
		mContainer.setBackground(PANEL_BG_COLOR);
		this.add(mContainer);
	}

	public void validateComponent() {
		// TODO Auto-generated method stub
		
	}


}
