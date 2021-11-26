package com.indocosmo.pos.forms;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;

import com.indocosmo.pos.common.PosEnvSettings;
import com.indocosmo.pos.common.PosLog;
import com.indocosmo.pos.common.utilities.PosDateUtil;
import com.indocosmo.pos.common.utilities.PosFormUtil;
import com.indocosmo.pos.common.utilities.PosOrderUtil;
import com.indocosmo.pos.common.utilities.PosResUtil;
import com.indocosmo.pos.data.beans.BeanOrderHeader;
import com.indocosmo.pos.data.beans.terminal.BeanTerminalInfo;
import com.indocosmo.pos.forms.components.buttons.PosButton;
import com.indocosmo.pos.forms.components.buttons.listners.IPosButtonListner;
import com.indocosmo.pos.forms.components.buttons.listners.PosButtonListnerAdapter;
import com.indocosmo.pos.forms.components.textfields.PosTouchableFieldBase;
import com.indocosmo.pos.forms.components.textfields.PosTouchableTextField;
import com.indocosmo.pos.forms.components.textfields.listners.IPosTouchableFieldListner;
import com.indocosmo.pos.forms.listners.IPosHoldOrderFormFormListner;

@SuppressWarnings("serial")
public final class PosOrderHoldForm extends JDialog {

	private static final int TITLE_PANEL_HEIGHT=30;	
	private static final int TOKEN_PANEL_HEIGHT=65; 
	private static final int CONTENT_PANEL_HEIGHT=230; 
	private static final int BOTTOM_PANEL_HEIGHT=76; 


	private static final int TITLE_LABEL_WIDTH=100;
	private static final int TITLE_LABEL_HEIGHT=20;

	private static final int TOKEN_LABEL_WIDTH=480;
	private static final int TOKEN_LABEL_HEIGHT=40;

	private static final int IMAGE_BUTTON_WIDTH=150;
	private static final int IMAGE_BUTTON_HEIGHT=60;

	protected static final int TEXT_FIELD_WIDTH=300;
	protected static final int TEXT_FIELD_HEIGHT=PosTouchableFieldBase.LAYOUT_DEF_HEIGHT;

	protected static final int LABEL_WIDTH=170;
	protected static final int LABEL_HEIGHT=PosTouchableFieldBase.LAYOUT_DEF_HEIGHT;	

	protected static final int PANEL_CONTENT_H_GAP=PosOrderEntryForm.PANEL_CONTENT_H_GAP;
	protected static final int PANEL_CONTENT_V_GAP=PosOrderEntryForm.PANEL_CONTENT_V_GAP;

	private static final int H_GAP_BTWN_CMPNTS = 1;
	private static final int V_GAP_BTWN_CMPNTS = 2;

	private static final int FORM_HEIGHT=TITLE_PANEL_HEIGHT+TOKEN_PANEL_HEIGHT+CONTENT_PANEL_HEIGHT+BOTTOM_PANEL_HEIGHT+PANEL_CONTENT_V_GAP*9;
	private static final int FORM_WIDTH=TOKEN_LABEL_WIDTH+PANEL_CONTENT_H_GAP*2;

	private JPanel mContentPane;
	private JPanel mTitlePanel;
	private JPanel mTokenPanel;
	protected JPanel mContentPanel;
	private JPanel mBottomPanel;

	private JLabel mlabelTitle;	
	private JLabel mlabelToken;	

	private PosButton mButtonOk;
	private PosButton mButtonRemarks;

	private JLabel mlabelStation;
	private JTextField mTxtStation;

	private JLabel mLabelDate;
	private JTextField mTxtDate;

	private JLabel mLabelTime;
	private JTextField mTxtTime;

	private JLabel mLabelCashier;
	private JTextField mTxtCashier;	

	private static final String MAGE_BUTTON_OK="retrieve_ok.png";
	private static final String MAGE_BUTTON_OK_TOUCH="retrieve_ok_touch.png";

	private static final String REMARKS_IMAGE_ITEM_BUTTON="dlg_remarks.png";
	private static final String REMARKS_IMAGE_ITEM_BUTTON_TOUCH="dlg_remarks_touch.png";	

	private static final Color PANEL_BG_COLOR=new Color(78,128,188);
	private static final Color LABEL_FG_COLOR=Color.WHITE;	

	private BeanTerminalInfo mStation;
	private String mCashier;

	private IPosHoldOrderFormFormListner mPosHoldOrderFormFormListner;
	private BeanOrderHeader mOrderHeaderItem;

	private PosTouchableTextField mTxtAliasText;

	public PosOrderHoldForm(BeanOrderHeader mPosOrderEntryItem){
		mOrderHeaderItem=mPosOrderEntryItem;
		initControls();	
	}

	private void initValues(){
		mStation=PosEnvSettings.getInstance().getStation();
		try {
			mCashier = mOrderHeaderItem.getUser().getName();
		} catch (Exception e) {
			PosLog.write(this, "initValues", e);
		}
	}

	private void initControls(){

		initValues();
		setSize(FORM_WIDTH, FORM_HEIGHT);
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
		mTitlePanel.setLayout(null);		
		add(mTitlePanel);

		mTokenPanel = new JPanel();
		mTokenPanel.setBounds(PANEL_CONTENT_H_GAP, 
				mTitlePanel.getY()+mTitlePanel.getHeight()+PANEL_CONTENT_V_GAP, 
				FORM_WIDTH-PANEL_CONTENT_H_GAP*2, TOKEN_PANEL_HEIGHT);
		//		mTokenPanel.setBackground(Color.GREEN);
		mTokenPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
		mTokenPanel.setLayout(null);
		add(mTokenPanel);

		mContentPanel = new JPanel();
		mContentPanel.setBounds(PANEL_CONTENT_H_GAP, 
				mTokenPanel.getY()+ mTokenPanel.getHeight()+PANEL_CONTENT_V_GAP, 
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
		setTokenPanel();
		setTokenDetails();	
		setBottomPanel();
		setDateTimeValue();
		
		this.addWindowListener(new WindowAdapter() {
			
			/* (non-Javadoc)
			 * @see java.awt.event.WindowAdapter#windowOpened(java.awt.event.WindowEvent)
			 */
			@Override
			public void windowOpened(WindowEvent e) {

				mTxtAliasText.requestFocus();
				super.windowOpened(e);
			}
		});
	}

	private void setTitlePanelContent(){
		setTitle();
	}		

	/**
	 * 
	 */
	private void setTokenDetails(){
		setStation();
		setDate();
		setTime();
		setCashier();
		setAliasTextField();
	}

	/**
	 * 
	 */
	private void setAliasTextField() {

		int left=mLabelCashier.getX();		
		int top=mLabelCashier.getY()+mLabelCashier.getHeight()+V_GAP_BTWN_CMPNTS;
		JLabel lblAlis=new JLabel();
		lblAlis.setOpaque(true);
		lblAlis.setBackground(Color.LIGHT_GRAY);
		lblAlis.setText("Alias :");
		lblAlis.setBorder(new EmptyBorder(2, 2, 2, 2));
		lblAlis.setHorizontalAlignment(SwingConstants.LEFT);		
		lblAlis.setBounds(left, top, LABEL_WIDTH, LABEL_HEIGHT);		
		lblAlis.setFont(PosFormUtil.getLabelFont());
		lblAlis.setFocusable(true);
		mContentPanel.add(lblAlis);

		left=lblAlis.getX()+lblAlis.getWidth()+H_GAP_BTWN_CMPNTS;
		mTxtAliasText=new PosTouchableTextField(this,TEXT_FIELD_WIDTH);	
		mTxtAliasText.setHorizontalTextAlignment(JTextField.LEFT);
		mTxtAliasText.setTitle("Alias Text?");
		mTxtAliasText.setText(mOrderHeaderItem.getAliasText());
		mTxtAliasText.setLocation(left, top);	
		mTxtAliasText.setFont(PosFormUtil.getTextFieldBoldFont());
		mTxtAliasText.setListner(new IPosTouchableFieldListner() {
			
			@Override
			public void onValueSelected(Object value) {

				okButtonListner.onClicked(mButtonOk);
			}
			
			@Override
			public void onReset() {
				// TODO Auto-generated method stub
				
			}
		});
		mContentPanel.add(mTxtAliasText);
	}

	private void setTitle(){

		mlabelTitle=new JLabel();
		mlabelTitle.setText("Save Order");
		mlabelTitle.setHorizontalAlignment(SwingConstants.CENTER);	
		mlabelTitle.setVerticalAlignment(SwingConstants.CENTER);
		mlabelTitle.setBounds(PANEL_CONTENT_H_GAP, PANEL_CONTENT_V_GAP, mTitlePanel.getWidth()-PANEL_CONTENT_H_GAP*2, mTitlePanel.getHeight()-PANEL_CONTENT_V_GAP*2);		
		mlabelTitle.setFont(PosFormUtil.getHeadingFont());
		mlabelTitle.setForeground(LABEL_FG_COLOR);
		mTitlePanel.add(mlabelTitle);		
	}

	private void setTokenPanel(){
		int left=(mTokenPanel.getWidth()/2)-(TOKEN_LABEL_WIDTH/2);
		int top=(mTokenPanel.getHeight()/2)-(TOKEN_LABEL_HEIGHT/2);
		mlabelToken=new JLabel();	

		mlabelToken.setHorizontalAlignment(SwingConstants.CENTER);		
		mlabelToken.setBounds(left, top, TOKEN_LABEL_WIDTH, TOKEN_LABEL_HEIGHT);		
		mlabelToken.setFont(PosFormUtil.getTokenFont());
		mlabelToken.setText(String.valueOf(PosOrderUtil.getFormattedReferenceNo(mOrderHeaderItem)));
		mTokenPanel.add(mlabelToken);		
	}

	private void setStation(){
		int left=PANEL_CONTENT_H_GAP/2;		
		int top=PANEL_CONTENT_V_GAP/2;
		mlabelStation=new JLabel();
		mlabelStation.setOpaque(true);
		mlabelStation.setBackground(Color.LIGHT_GRAY);
		mlabelStation.setText("Station :");
		mlabelStation.setBorder(new EmptyBorder(2, 2, 2, 2));
		mlabelStation.setHorizontalAlignment(SwingConstants.LEFT);		
		mlabelStation.setBounds(left, top, LABEL_WIDTH, LABEL_HEIGHT);		
		mlabelStation.setFont(PosFormUtil.getLabelFont());
		mlabelStation.setFocusable(true);
		mContentPanel.add(mlabelStation);

		left=mlabelStation.getX()+mlabelStation.getWidth()+H_GAP_BTWN_CMPNTS;
		mTxtStation=new JTextField();	
		mTxtStation.setHorizontalAlignment(JTextField.LEFT);
		mTxtStation.setBounds(left, top, TEXT_FIELD_WIDTH, TEXT_FIELD_HEIGHT);	
		mTxtStation.setFont(PosFormUtil.getTextFieldBoldFont());		
		mTxtStation.setEditable(false);
		mTxtStation.setText(String.valueOf(mStation.getCode()));
		mContentPanel.add(mTxtStation);
	}

	private void setDate(){
		int left=mlabelStation.getX();		
		int top=mlabelStation.getY()+mlabelStation.getHeight()+V_GAP_BTWN_CMPNTS;
		mLabelDate=new JLabel();
		mLabelDate.setOpaque(true);
		mLabelDate.setBackground(Color.LIGHT_GRAY);
		mLabelDate.setText("Date :");
		mLabelDate.setBorder(new EmptyBorder(2, 2, 2, 2));
		mLabelDate.setHorizontalAlignment(SwingConstants.LEFT);		
		mLabelDate.setBounds(left, top, LABEL_WIDTH, LABEL_HEIGHT);		
		mLabelDate.setFont(PosFormUtil.getLabelFont());
		mLabelDate.setFocusable(true);
		mContentPanel.add(mLabelDate);

		left=mLabelDate.getX()+mLabelDate.getWidth()+H_GAP_BTWN_CMPNTS;
		mTxtDate=new JTextField();	
		mTxtDate.setHorizontalAlignment(JTextField.LEFT);
		mTxtDate.setBounds(left, top, TEXT_FIELD_WIDTH, TEXT_FIELD_HEIGHT);	
		mTxtDate.setFont(PosFormUtil.getTextFieldBoldFont());		 
		mTxtDate.setEditable(false);
		mContentPanel.add(mTxtDate);
	}
	private void setTime(){
		int left=mLabelDate.getX();		
		int top=mLabelDate.getY()+mLabelDate.getHeight()+V_GAP_BTWN_CMPNTS;
		mLabelTime=new JLabel();
		mLabelTime.setOpaque(true);
		mLabelTime.setBackground(Color.lightGray);
		mLabelTime.setText("Time :");
		mLabelTime.setBorder(new EmptyBorder(2, 2, 2, 2));
		mLabelTime.setHorizontalAlignment(SwingConstants.LEFT);		
		mLabelTime.setBounds(left, top, LABEL_WIDTH, LABEL_HEIGHT);		
		mLabelTime.setFont(PosFormUtil.getLabelFont());
		mLabelTime.setFocusable(true);
		mContentPanel.add(mLabelTime);

		left=mLabelTime.getX()+mLabelTime.getWidth()+H_GAP_BTWN_CMPNTS;
		mTxtTime=new JTextField();	
		mTxtTime.setHorizontalAlignment(JTextField.LEFT);
		mTxtTime.setBounds(left, top, TEXT_FIELD_WIDTH, TEXT_FIELD_HEIGHT);	
		mTxtTime.setFont(PosFormUtil.getTextFieldBoldFont());	
		mTxtTime.setEditable(false);
		mContentPanel.add(mTxtTime);
	}

	private void setCashier(){
		int left=mLabelTime.getX();		
		int top=mLabelTime.getY()+mLabelTime.getHeight()+V_GAP_BTWN_CMPNTS;
		mLabelCashier=new JLabel();
		mLabelCashier.setOpaque(true);
		mLabelCashier.setBackground(Color.LIGHT_GRAY);
		mLabelCashier.setText("Cashier :");
		mLabelCashier.setBorder(new EmptyBorder(2, 2, 2, 2));
		mLabelCashier.setHorizontalAlignment(SwingConstants.LEFT);		
		mLabelCashier.setBounds(left, top, LABEL_WIDTH, LABEL_HEIGHT);		
		mLabelCashier.setFont(PosFormUtil.getLabelFont());
		mLabelCashier.setFocusable(true);
		mContentPanel.add(mLabelCashier);

		left=mLabelCashier.getX()+mLabelCashier.getWidth()+H_GAP_BTWN_CMPNTS;
		mTxtCashier=new JTextField();	
		mTxtCashier.setHorizontalAlignment(JTextField.LEFT);
		mTxtCashier.setBounds(left, top, TEXT_FIELD_WIDTH, TEXT_FIELD_HEIGHT);	
		mTxtCashier.setFont(PosFormUtil.getTextFieldBoldFont());		
		mTxtCashier.setEditable(false);
		mTxtCashier.setText(mCashier);
		mContentPanel.add(mTxtCashier);
	}

	private void setBottomPanel(){
		int left=mBottomPanel.getX()+PANEL_CONTENT_H_GAP*10;
		mButtonOk=new PosButton();	
		mButtonOk.setDefaultButton(true);
		mButtonOk.setText("OK");
		mButtonOk.setImage(PosResUtil.getImageIconFromResource(MAGE_BUTTON_OK));
		mButtonOk.setTouchedImage(PosResUtil.getImageIconFromResource(MAGE_BUTTON_OK_TOUCH));	
		mButtonOk.setHorizontalAlignment(SwingConstants.CENTER);		
		mButtonOk.setBounds(left, PANEL_CONTENT_V_GAP*5, IMAGE_BUTTON_WIDTH, IMAGE_BUTTON_HEIGHT );	
		mButtonOk.setOnClickListner(okButtonListner);
		mBottomPanel.add(mButtonOk);

		left=mButtonOk.getX()+mButtonOk.getWidth()+PANEL_CONTENT_V_GAP*2;
		mButtonRemarks=new PosButton();		
		mButtonRemarks.setText("Remarks");
		mButtonRemarks.setImage(PosResUtil.getImageIconFromResource(REMARKS_IMAGE_ITEM_BUTTON));
		mButtonRemarks.setTouchedImage(PosResUtil.getImageIconFromResource(REMARKS_IMAGE_ITEM_BUTTON_TOUCH));	
		mButtonRemarks.setHorizontalAlignment(SwingConstants.CENTER);		
		mButtonRemarks.setBounds(left, PANEL_CONTENT_V_GAP, IMAGE_BUTTON_WIDTH, IMAGE_BUTTON_HEIGHT);		
		mButtonRemarks.setOnClickListner(remarksButtonListner);
		mBottomPanel.add(mButtonRemarks);	
	}

	private void setDateTimeValue(){		
		mTxtDate.setText(PosEnvSettings.getPosEnvSettings().getPosDate().toString());
		mTxtTime.setText(PosDateUtil.getDateTime().toString());		
	}

	private IPosButtonListner okButtonListner=new PosButtonListnerAdapter() {
		@Override
		public void onClicked(PosButton button) {
			setVisible(false);
			mOrderHeaderItem.setAliasText(mTxtAliasText.getText());
			if(mPosHoldOrderFormFormListner!=null)
				mPosHoldOrderFormFormListner.onOkClicked(mOrderHeaderItem);			
			dispose();	
		}
	};

	private  IPosButtonListner remarksButtonListner= new PosButtonListnerAdapter() {
		@Override
		public void onClicked(PosButton button) {			
			PosOrderRemarksEntryForm remarks=new PosOrderRemarksEntryForm();
			remarks.setOrderHeaderItem(mOrderHeaderItem);
			PosFormUtil.showLightBoxModal(remarks);		
		}
	};	

	/**
	 * @return the mTokenNumber
	 */
	//	public int getTokenNumber() {
	//		return mTokenNumber;
	//	}

	//	/**
	//	 * @param mTokenNumber the mTokenNumber to set
	//	 */
	//	public void setTokenNumber(int tokenNumber) {
	//		this.mTokenNumber = tokenNumber;
	//		mlabelToken.setText(String.valueOf(mTokenNumber));
	//	}

	//	/**
	//	 * @return the mStation
	//	 */
	//	public String getStation() {
	//		return mStation;
	//	}

	//	/**
	//	 * @param mStation the mStation to set
	//	 */
	//	public void setStation(String station) {
	//		this.mStation = station;
	//		mTxtStation.setText(mStation);
	//	}

	//	/**
	//	 * @return the mCashier
	//	 */
	//	public String getCashier() {
	//		return mCashier;
	//	}

	/**
//	 * @param mCashier the mCashier to set
//	 */
	//	public void setCashier(String cashier) {
	//		this.mCashier = cashier;
	//		mTxtCashier.setText(mCashier);
	//	}

	//	/**
	//	 * @return the mSno
	//	 */
	//	public final Integer getSno() {
	//		return mSno;
	//	}

	//	private void setHoldOrderQueData(){
	//		mPosOrderQueItem=new PosOrderQHeaderObject();
	//		mPosOrderQueItem.setOriginstationId(mStation.getId());
	//		mPosOrderQueItem.setOrderQueuNo(mSno);
	//	 }

	public void setonOkClickedListner(IPosHoldOrderFormFormListner posHoldOrderFormListner) {	
		mPosHoldOrderFormFormListner=posHoldOrderFormListner;
	}

	//	public PosOrderQHeaderObject getHoldOrderQueItem(){
	//		return mPosOrderQueItem;		
	//	}


}
