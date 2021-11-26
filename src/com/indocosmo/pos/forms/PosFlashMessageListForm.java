/**
 * 
 */
package com.indocosmo.pos.forms;

import java.awt.FlowLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;

import javax.swing.JPanel;

import com.indocosmo.pos.common.utilities.PosFormUtil;
import com.indocosmo.pos.data.beans.BeanFlashMessage;
import com.indocosmo.pos.data.providers.shopdb.PosFlashMessageProvider;
import com.indocosmo.pos.forms.components.FlashMessageListTablePanel;
import com.indocosmo.pos.forms.components.listners.IFlashMessageListTablePanelListener;
import com.indocosmo.pos.forms.listners.adapters.PosWindowListenerAdapter;

/**
 * @author deepak
 * 
 */
@SuppressWarnings("serial")
public class PosFlashMessageListForm extends PosBaseForm {

	private static final int LAYOUT_WIDTH=700;
	private static final int LAYOUT_HEIGHT=400;
	
	private static final int PANEL_CONTENT_H_GAP = 8;
	private static final int PANEL_CONTENT_V_GAP = 8;
	 
		
	private JPanel mContenPanel;
	private ArrayList<BeanFlashMessage> mMessageList;
	private PosFlashMessageProvider mPosFlashMessageProvider;
	private FlashMessageListTablePanel messageListPanel;
	
	/**
	 * 
	 */
	public PosFlashMessageListForm() {
		
		super("Messages", LAYOUT_WIDTH, LAYOUT_HEIGHT);
		// setCancelButtonVisible(false);
		
		setCancelButtonCaption("Close");
		setOkButtonVisible(false);
		addWindowListener(mWindowListner);
	}

	private WindowListener mWindowListner = new PosWindowListenerAdapter() {

		public void windowActivated(WindowEvent e) {
			
			mMessageList = mPosFlashMessageProvider.getFlashMessageList();
			messageListPanel.setMessageList(mMessageList);
			messageListPanel.refresh();
		};
	};
	
	/**
	 * 
	 */
	private void initFlashMessagesControls() {
		mPosFlashMessageProvider = new PosFlashMessageProvider();
		mMessageList = mPosFlashMessageProvider.getFlashMessageList();
		createMessageListPanel();
	 
	 
	}

	/**
	 * 
	 */
	private void createMessageListPanel(){
		
		int width = LAYOUT_WIDTH - PANEL_CONTENT_H_GAP * 2;
		int height = LAYOUT_HEIGHT - PANEL_CONTENT_V_GAP*2;
 
		messageListPanel =new  FlashMessageListTablePanel(width,height);
		messageListPanel.setLocation(PANEL_CONTENT_H_GAP ,50);
		messageListPanel.setListener(new IFlashMessageListTablePanelListener() {
			
			@Override
			public void onSelectionChanged(int index) {
				 
					PosFlashMessagesForm mFlashMessagesForm = new PosFlashMessagesForm(messageListPanel.getSelectedItemIndex());
					PosFormUtil.showLightBoxModal(PosFlashMessageListForm.this, mFlashMessagesForm);
				 
				
			}
		}); 
		messageListPanel.setMessageList(mMessageList); 
		mContenPanel.add(messageListPanel);
	}
	 
 
	 
 
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.indocosmo.pos.forms.PosBaseForm#setContentPanel(javax.swing.JPanel)
	 */
	@Override
	protected void setContentPanel(JPanel panel) {

		mContenPanel = panel;
		mContenPanel.setLayout(new FlowLayout(FlowLayout.CENTER,PANEL_CONTENT_H_GAP,PANEL_CONTENT_V_GAP));
		initFlashMessagesControls();
		
	}

}
