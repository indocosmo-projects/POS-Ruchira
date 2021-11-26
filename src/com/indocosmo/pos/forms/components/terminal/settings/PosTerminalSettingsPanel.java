package com.indocosmo.pos.forms.components.terminal.settings;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;

import com.indocosmo.pos.common.PosEnvSettings;
import com.indocosmo.pos.common.enums.PosTerminalServiceType;
import com.indocosmo.pos.common.utilities.PosFormUtil;
import com.indocosmo.pos.data.beans.BeanShop;
import com.indocosmo.pos.data.beans.terminal.BeanTerminalInfo;
import com.indocosmo.pos.data.beans.terminal.BeanTerminalSetting;
import com.indocosmo.pos.data.providers.shopdb.PosStationProvider;
import com.indocosmo.pos.data.providers.terminaldb.PosTerminalSettingsProvider;
import com.indocosmo.pos.forms.PosOrderEntryForm;
import com.indocosmo.pos.forms.components.buttons.PosButton;
import com.indocosmo.pos.forms.components.textfields.PosItemBrowsableField;
import com.indocosmo.pos.forms.components.textfields.PosTouchableFieldBase;
import com.indocosmo.pos.forms.components.textfields.PosTouchableTextField;
import com.indocosmo.pos.forms.components.textfields.listners.IPosTouchableFieldListner;


@SuppressWarnings("serial")
public final class PosTerminalSettingsPanel extends PosTermnalSettingsBase {		

	private JLabel mLabelTerminalNumber;
	private PosItemBrowsableField mTxtStationCode;

	private JLabel mLabelShopCode;
	private PosTouchableTextField mTxtShopCode;

	private JLabel mLabelTerminalName;
	private PosItemBrowsableField mTxtTerminalName;

	private JLabel mLabelTerminalType;
	private PosItemBrowsableField mComboTerminalType;	

	private PosButton mSyncButton;

	private static final int TEXT_FIELD_WIDTH=400;

	private static final int LABEL_WIDTH=150;
	private static final int LABEL_HEIGHT=PosTouchableFieldBase.LAYOUT_DEF_HEIGHT;

	private static final int PANEL_CONTENT_H_GAP=PosOrderEntryForm.PANEL_CONTENT_H_GAP;
	private static final int PANEL_CONTENT_V_GAP=PosOrderEntryForm.PANEL_CONTENT_V_GAP;	

	private static final String INIT_SYNC_IMAGE_NORMAL="init_terminal.png";
	private static final String INIT_SYNC_IMAGE_TOUCH="init_terminal_touch.png";
	private static final int INIT_SYNC_BUTTON_HEIGHT=60;
	private static final int INIT_SYNC_BUTTON_WIDTH=175;
	private static final int SYNC_PANEL_WIDTH=400;
	private static final int SYNC_PANEL_HEIGHT=200;

	private JDialog mParent;
//	private PosShopProvider mShopProvider;
	private BeanTerminalInfo mSelectedTerminalInfo;
//	private BeanTerminalSetting mTerminalSettings;
	private PosTerminalSettingsProvider mSettingsProvider;
	
	private JPanel mSyncPanel;

	public PosTerminalSettingsPanel(JDialog parent) {	
		super(parent,"Terminal");
		
		mParent=parent;
		
		setSize(LAYOUT_WIDTH,LAYOUT_HEIGHT);
		setLayout(null);
		setOpaque(true);
		mSettingsProvider=new PosTerminalSettingsProvider();
//		mShopProvider=new PosShopProvider();
		setTerminalDetails();
		
		/*
		 * For testing purpose only.. Remove it
		 */
			mTxtStationCode.setEditable(true);
		/*
		 * 
		 */
	}

	private void setTerminalDetails(){	
		setShopCode();
		setTerminalNumber();
		setTerminalDescription();
		setTerminalType();
		createSyncPanel();
		setEnableControls();
		loadData();
	}	

	private void setShopCode(){
		int left=PANEL_CONTENT_V_GAP/4+1;
		int top=PANEL_CONTENT_V_GAP/4+1;
		mLabelShopCode=new JLabel();
		mLabelShopCode.setText("Shop Code :");
		mLabelShopCode.setOpaque(true);
		mLabelShopCode.setBackground(Color.LIGHT_GRAY);
		mLabelShopCode.setBorder(new EmptyBorder(2, 2, 2, 2));
		mLabelShopCode.setHorizontalAlignment(SwingConstants.LEFT);		
		mLabelShopCode.setBounds(left, top, LABEL_WIDTH, LABEL_HEIGHT);		
		mLabelShopCode.setFont(PosFormUtil.getLabelFont());
		mLabelShopCode.setFocusable(true);
		add(mLabelShopCode);

		left=mLabelShopCode.getX()+mLabelShopCode.getWidth()+PANEL_CONTENT_H_GAP/8;
		mTxtShopCode=new PosTouchableTextField(mParent,TEXT_FIELD_WIDTH);
		mTxtShopCode.setTitle("Enter Shop Code.");
		mTxtShopCode.setLocation(left, top);	
		mTxtShopCode.setEditable(false);
		add(mTxtShopCode);
	}

	private void setTerminalNumber(){
		int left=mLabelShopCode.getX();
		int top=mTxtShopCode.getY()+mTxtShopCode.getHeight()+ PANEL_CONTENT_V_GAP/4;
		mLabelTerminalNumber=new JLabel();
		mLabelTerminalNumber.setText(PosFormUtil.getMnemonicString("Station", 'S'));
		mLabelTerminalNumber.setOpaque(true);
		mLabelTerminalNumber.setBackground(Color.LIGHT_GRAY);
		mLabelTerminalNumber.setBorder(new EmptyBorder(2, 2, 2, 2));
		mLabelTerminalNumber.setHorizontalAlignment(SwingConstants.LEFT);		
		mLabelTerminalNumber.setBounds(left, top, LABEL_WIDTH, LABEL_HEIGHT);		
		mLabelTerminalNumber.setFont(PosFormUtil.getLabelFont());
		mLabelTerminalNumber.setFocusable(true);
		add(mLabelTerminalNumber);

		PosStationProvider  stationProvider= new PosStationProvider();
		ArrayList<BeanTerminalInfo> staions=stationProvider.getStations();
		left=mLabelTerminalNumber.getX()+mLabelTerminalNumber.getWidth()+PANEL_CONTENT_H_GAP/8;
		
		mTxtStationCode=new PosItemBrowsableField(mParent,TEXT_FIELD_WIDTH);
		mTxtStationCode.setMnemonic('S');
		mTxtStationCode.setBrowseWindowSize(3,3);
		mTxtStationCode.setBrowseItemList(staions);
		mTxtStationCode.setListner(new IPosTouchableFieldListner() {
			@Override
			public void onValueSelected(Object value) {
				mSelectedTerminalInfo=(BeanTerminalInfo) value;
				mTxtStationCode.setText(mSelectedTerminalInfo.getCode());
				mComboTerminalType.setText(mSelectedTerminalInfo.getServiceType().getDisplayText());
				mTxtTerminalName.setText(mSelectedTerminalInfo.getName());
			}
			@Override
			public void onReset() {}
		});
		mTxtStationCode.setTitle("Station Code.");
		mTxtStationCode.setLocation(left, top);	
		mTxtStationCode.setEditable(false);
		add(mTxtStationCode);
	}

	private void setTerminalDescription(){
		int left=mLabelTerminalNumber.getX();		
		int top=mLabelTerminalNumber.getY()+mLabelTerminalNumber.getHeight()+PANEL_CONTENT_V_GAP/4;
		mLabelTerminalName=new JLabel();
		mLabelTerminalName.setText("Name :");
		mLabelTerminalName.setOpaque(true);
		mLabelTerminalName.setBackground(Color.LIGHT_GRAY);
		mLabelTerminalName.setBorder(new EmptyBorder(2, 2, 2, 2));
		mLabelTerminalName.setHorizontalAlignment(SwingConstants.LEFT);		
		mLabelTerminalName.setBounds(left, top, LABEL_WIDTH, LABEL_HEIGHT);		
		mLabelTerminalName.setFont(PosFormUtil.getLabelFont());
		mLabelTerminalName.setFocusable(true);
		add(mLabelTerminalName);

		left=mLabelTerminalName.getX()+mLabelTerminalName.getWidth()+PANEL_CONTENT_H_GAP/8;
		mTxtTerminalName=new PosItemBrowsableField(mParent,TEXT_FIELD_WIDTH);
		mTxtTerminalName.setTitle("Terminal Name");
		mTxtTerminalName.setLocation(left, top);
		mTxtTerminalName.setEditable(false);
		add(mTxtTerminalName);
	}

	private void setTerminalType(){	
		int left=mLabelTerminalName.getX();		
		int top=mLabelTerminalName.getY()+mLabelTerminalName.getHeight()+PANEL_CONTENT_V_GAP/4;
		mLabelTerminalType=new JLabel();
		mLabelTerminalType.setText("Type :");
		mLabelTerminalType.setOpaque(true);
		mLabelTerminalType.setBackground(Color.LIGHT_GRAY);
		mLabelTerminalType.setBorder(new EmptyBorder(2, 2, 2, 2));
		mLabelTerminalType.setHorizontalAlignment(SwingConstants.LEFT);		
		mLabelTerminalType.setBounds(left, top, LABEL_WIDTH, LABEL_HEIGHT);		
		mLabelTerminalType.setFont(PosFormUtil.getLabelFont());
		mLabelTerminalType.setFocusable(true);
		add(mLabelTerminalType);

		left=mLabelTerminalType.getX()+mLabelTerminalType.getWidth()+PANEL_CONTENT_H_GAP/8;
		mComboTerminalType=new PosItemBrowsableField(mParent,TEXT_FIELD_WIDTH);
		mComboTerminalType.setBrowseItemList(PosTerminalServiceType.values());
		mComboTerminalType.setTitle("Terminal Type");
		mComboTerminalType.setLocation(left, top);	
		mComboTerminalType.setFont(PosFormUtil.getTextFieldFont());	
		mComboTerminalType.setEditable(false);
		add(mComboTerminalType);
	}
	
	private void setEnableControls(){
		try {
			if(PosEnvSettings.getInstance().getShop()!=null){
					mTxtStationCode.setEditable(true);
				}
			else{
				mSyncPanel.setVisible(true);
			}
		} catch (Exception e) {
			PosFormUtil.showErrorMessageBox(null,e.getMessage());
		}
	}
	
	private void createSyncPanel(){
		final int top=230;
		final int left=(getWidth()/2)-(SYNC_PANEL_WIDTH/2);
		mSyncPanel=new JPanel();
		mSyncPanel.setLayout(null);
		mSyncPanel.setLocation(left,top);
		mSyncPanel.setSize(SYNC_PANEL_WIDTH,SYNC_PANEL_HEIGHT);
		mSyncPanel.setBorder(new EtchedBorder());
		this.add(mSyncPanel);
		
		final JLabel shortMessage= createShortMessagePanel(mSyncPanel ,"Synchronization.");
		mSyncPanel.setVisible(false);
		mSyncPanel.add(shortMessage);
		createSyncButton(mSyncPanel);
	}

	private void createSyncButton(JPanel panel){
		final int top=((SYNC_PANEL_HEIGHT/2)-INIT_SYNC_BUTTON_HEIGHT/2)+20;
		final int left=(SYNC_PANEL_WIDTH/2)-INIT_SYNC_BUTTON_WIDTH/2;
		mSyncButton=new PosButton();
		mSyncButton.setText("Init. Terminal");
		mSyncButton.setImage(INIT_SYNC_IMAGE_NORMAL);
		mSyncButton.setTouchedImage(INIT_SYNC_IMAGE_TOUCH);
		mSyncButton.setBounds(left, top, INIT_SYNC_BUTTON_WIDTH, INIT_SYNC_BUTTON_HEIGHT);
		panel.add(mSyncButton);
	}

	protected JLabel createShortMessagePanel(JPanel parentPanel ,String message){
		final Color LABEL_BG_COLOR=new Color(78,128,188);
		final int MESSAGE_PANEL_HEIGHT=30;
		JLabel labelMessage=new JLabel();
		labelMessage.setText(message);
		labelMessage.setHorizontalAlignment(SwingConstants.CENTER);		
		labelMessage.setSize(new Dimension(parentPanel.getWidth()-2, MESSAGE_PANEL_HEIGHT));				
		labelMessage.setOpaque(true);
		labelMessage.setBackground(LABEL_BG_COLOR);
		labelMessage.setForeground(Color.WHITE);
		return labelMessage;
	}
	
	private void loadData(){
		mSelectedTerminalInfo=null;
		BeanShop shop= PosEnvSettings.getInstance().getShop();
		mTxtShopCode.setText(shop.getCode());
		mTerminalSettings=mSettingsProvider.getTerminalSetting();
		if(mTerminalSettings!=null){
			mSelectedTerminalInfo=mTerminalSettings.getTerminalInfo();
			mTxtTerminalName.setText(mTerminalSettings.getName());
			mTxtStationCode.setText(mTerminalSettings.getCode());
			mComboTerminalType.setText(mTerminalSettings.getType().getDisplayText());
			mTxtStationCode.setEditable(false);
		}else
			mTerminalSettings=new BeanTerminalSetting();
	}

	@Override
	public String getTabCaption() {
		return "Terminal";	
	}

	@Override
	public boolean onValidating() {
		if(mTxtStationCode.getText().trim().length()<=0
				|| mTxtShopCode.getText().trim().length()<=0 
				|| mComboTerminalType.getText().trim().length()<=0){
			PosFormUtil.showErrorMessageBox(mParent, "The terminal settings are not valid. Please enter valid setting.");	
			return false;
		}
		return true;
	}

	@Override
	public void onGotFocus() {
		mTxtStationCode.requestFocus();
		super.onGotFocus();
	}

	@Override
	public boolean onSave() {
		mTerminalSettings.setShopCode(mTxtShopCode.getText());
		mTerminalSettings.setTerminalInfo(mSelectedTerminalInfo);
		
		try {
			mSettingsProvider.setTerminalSettings(mTerminalSettings);
		} catch (Exception e) {
			PosFormUtil.showErrorMessageBox(null,e.getMessage());
			return false;
		}
		return true;
	}

	/**
	 * @return the TerminalSettings
	 */
	public BeanTerminalSetting getTerminalSettings() {
		
		return mTerminalSettings;
	}

}
