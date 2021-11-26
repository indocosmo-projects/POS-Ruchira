package com.indocosmo.pos.forms.components.terminal.settings;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.indocosmo.pos.common.PosConstants;
import com.indocosmo.pos.common.PosEnvSettings;
import com.indocosmo.pos.common.utilities.PosFormUtil;
import com.indocosmo.pos.data.beans.BeanShop;
import com.indocosmo.pos.forms.PosOrderEntryForm;
import com.indocosmo.pos.forms.components.textfields.PosTouchableFieldBase;
import com.indocosmo.pos.forms.components.textfields.PosTouchableTextField;
import com.indocosmo.pos.forms.components.textfields.listners.IPosTouchableFieldListner;
import com.indocosmo.pos.process.sync.SynchronizeFromServer;
import com.indocosmo.pos.process.sync.listner.IPosSyncListener;


@SuppressWarnings("serial")
public final class PosTerminalInitSynchPanel extends PosTermnalSettingsBase {		

	private JLabel mLabelShopCode;
	private PosTouchableTextField mTxtShopCode;

	private static final int TEXT_FIELD_WIDTH=400;
	private static final int LABEL_WIDTH=130;
	private static final int LABEL_HEIGHT=PosTouchableFieldBase.LAYOUT_DEF_HEIGHT;
	private static final int PANEL_CONTENT_H_GAP=PosOrderEntryForm.PANEL_CONTENT_H_GAP;
	private static final int PANEL_CONTENT_V_GAP=PosOrderEntryForm.PANEL_CONTENT_V_GAP;	
	private static final int MESSAGE_PANEL_HEIGHT=30;

	private JDialog mParent;
	
	public PosTerminalInitSynchPanel(JDialog parent) {	
		super(parent,"Terminal");
		mParent=parent;
		setSize(LAYOUT_WIDTH,LAYOUT_HEIGHT);
		setLayout(null);
		setOpaque(true);
		setTerminalDetails();
	}	

	private void setTerminalDetails(){	
		setShopCode();
		createSyncPanel();
	}	

	private void setShopCode(){
		int left=20;
		int top=((this.getHeight()/2)-PosTouchableTextField.LAYOUT_DEF_HEIGHT/2);
		mLabelShopCode=new JLabel();
		mLabelShopCode.setText("Shop Code :");
		mLabelShopCode.setHorizontalAlignment(SwingConstants.LEFT);		
		mLabelShopCode.setBounds(left, top, LABEL_WIDTH, LABEL_HEIGHT);		
		mLabelShopCode.setFont(PosFormUtil.getLabelFont());
		mLabelShopCode.setFocusable(false);
		add(mLabelShopCode);

		left=mLabelShopCode.getX()+mLabelShopCode.getWidth()+PANEL_CONTENT_H_GAP;
		mTxtShopCode=new PosTouchableTextField(mParent,TEXT_FIELD_WIDTH);
		mTxtShopCode.setListner(new IPosTouchableFieldListner() {
			
			@Override
			public void onValueSelected(Object value) {
				
				if(listener!=null)
					listener.onShopeCodeEntered(String.valueOf(value));
			}
			
			@Override
			public void onReset() {
				// TODO Auto-generated method stub
				
			}
		});
		mTxtShopCode.setTitle("Enter Shop Code.");
		mTxtShopCode.setLocation(left, top);		
		add(mTxtShopCode);
	}
	
	private void createSyncPanel(){
		final JLabel shortMessage= createShortMessagePanel(this ,"Synchronization.");
		add(shortMessage);
	}

	protected JLabel createShortMessagePanel(JPanel parentPanel ,String message){
		final Color LABEL_BG_COLOR=new Color(78,128,188);
		JLabel labelMessage=new JLabel();
		labelMessage.setText(message);
		labelMessage.setHorizontalAlignment(SwingConstants.CENTER);		
		labelMessage.setSize(new Dimension(parentPanel.getWidth()-2, MESSAGE_PANEL_HEIGHT));				
		labelMessage.setOpaque(true);
		labelMessage.setBackground(LABEL_BG_COLOR);
		labelMessage.setForeground(Color.WHITE);
		return labelMessage;
	}

	@Override
	public String getTabCaption() {
		return "Terminal";	
	}

	@Override
	public boolean onValidating() {
		if(mTxtShopCode.getText().trim().length()<=0 ){
			PosFormUtil.showInformationMessageBox(mParent, "Please enter the shop code.");	
			return false;
		}
		return true;
	}

	@Override
	public void onGotFocus() {
		mTxtShopCode.requestFocus();
//		super.onGotFocus();
	}

	@Override
	public boolean onSave() {
		BeanShop shop=new BeanShop();
		shop.setCode(mTxtShopCode.getText().toUpperCase().trim());
		PosEnvSettings.getInstance().setShop(shop);
		
		SynchronizeFromServer.setListener(new IPosSyncListener() {
			@Override
			public void onCompleted() {
				PosFormUtil.closeBusyWindow();
				PosFormUtil.showInformationMessageBox(mParent,
						"Synchronization completed. System needs to be restarted.");
				System.exit(PosConstants.RESTART_EXIT_CODE);
			}
		});
		
		SynchronizeFromServer.syncFromWeb();
		PosFormUtil.showBusyWindow(mParent, "Please wait while synchronizing...");
		
		return false;
	}
	
	private IInitialSyncPanelListner listener;
	
	/**
	 * @author jojesh-13.2
	 *
	 */
	public interface IInitialSyncPanelListner{
		
		public void onShopeCodeEntered(String code);
	}

	/**
	 * @param listener the listener to set
	 */
	public void setListener(IInitialSyncPanelListner listener) {
		
		this.listener = listener;
	}

}
