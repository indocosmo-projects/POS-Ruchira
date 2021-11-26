package com.indocosmo.pos.forms.components.orderentry;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.KeyEvent;

import javax.swing.JPanel;
import javax.swing.RootPaneContainer;

import com.indocosmo.pos.common.enums.PosOrderServiceTypes;
import com.indocosmo.pos.data.beans.BeanOrderHeader;
import com.indocosmo.pos.forms.components.buttons.PosButton;
import com.indocosmo.pos.forms.components.buttons.listners.IPosButtonListner;

@SuppressWarnings("serial")
public final class PosActionPanelCounter extends PosActionPanelBase{

	
	/**
	 * @param parent
	 */
	public PosActionPanelCounter(RootPaneContainer parent) {
		super(parent);
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indocosmo.pos.components.orderentry.PosActionPanelBase#
	 * createActionButtons()
	 */
	@Override
	protected void createActionButtons() {
		
		createOrderButtons();
		addSeperater();
		createMiscButtons();

	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.indocosmo.pos.components.orderentry.PosActionPanelBase#getLayoutWidth
	 * ()
	 */
	@Override
	protected int getLayoutWidth() {
		final int noWidColumns = 3;
		final int noNormColumns = 4;
		final int noSeperator=2;
		return WIDE_BUTTON_WIDTH * noWidColumns+NORM_BUTTON_WIDTH*noNormColumns + SEPERATER_WIDTH*noSeperator+ PANEL_CONTENT_H_GAP 
				* (noWidColumns+noNormColumns + 1) ;
	}
	/***
	 * creates the order group and buttons
	 */
	private void createOrderButtons() {
		JPanel orderPanel = new JPanel();
		orderPanel.setLayout(new FlowLayout(FlowLayout.CENTER,
				PANEL_CONTENT_H_GAP, 0));
		orderPanel.setPreferredSize(new Dimension(NORM_BUTTON_WIDTH*2+ WIDE_BUTTON_WIDTH+PANEL_CONTENT_H_GAP*3 
				, LAYOUT_HEIGHT));
		orderPanel.setOpaque(false);
		add(orderPanel);
		
		createNewButton(orderPanel,false);
		
		JPanel grpPanel = new JPanel();
		grpPanel.setLayout(new FlowLayout(FlowLayout.CENTER,
				0, PANEL_CONTENT_V_GAP));
		grpPanel.setPreferredSize(new Dimension(WIDE_BUTTON_WIDTH 
				, LAYOUT_HEIGHT));
		grpPanel.setOpaque(false);
		orderPanel.add(grpPanel);
		
		createSaveButton(grpPanel,true);
		createRetrieveButton(grpPanel,true);
		
		createInfoButton(orderPanel,false);

	}



	/**
	 * @param orderButtonsPanel
	 * @param b
	 */
	private void createNewButton(JPanel panel, boolean b) {
		mButtonNew= createActionPanelButton("New",KeyEvent.VK_N,'N');
		mButtonNew.setOnClickListner(new IPosButtonListner() {
			@Override
			public void onClicked(PosButton button) {
				if (mPosActionPanelListner != null)
					mPosActionPanelListner.onPosActionButtonClicked(
							OrderEntryMenuActions.ORDER_NEW, PosOrderServiceTypes.TAKE_AWAY);
			}
		});
		panel.add(mButtonNew);
		
	}

	/***
	 * Creates the Misc buttons
	 */
	private void createMiscButtons() {

		JPanel miscPanel = new JPanel();
		miscPanel.setLayout(new FlowLayout(FlowLayout.CENTER,
				PANEL_CONTENT_H_GAP, 0));
		miscPanel.setPreferredSize(new Dimension(WIDE_BUTTON_WIDTH +NORM_BUTTON_WIDTH*2+PANEL_CONTENT_H_GAP*3
				, LAYOUT_HEIGHT));
		miscPanel.setOpaque(false);
		add(miscPanel);
		
		createCustomerInfoButton(miscPanel,false);
		
		JPanel grpPanel = new JPanel();
		grpPanel.setLayout(new FlowLayout(FlowLayout.CENTER,
				0, PANEL_CONTENT_V_GAP));
		grpPanel.setPreferredSize(new Dimension(WIDE_BUTTON_WIDTH 
				, LAYOUT_HEIGHT));
		grpPanel.setOpaque(false);
		
		miscPanel.add(grpPanel);
		createMenuButton(grpPanel,true);
		createPosMessageButton(grpPanel, true);
		createMoreButton(miscPanel,false);
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.pos.components.orderentry.PosActionPanelBase#setForNewService(com.indocosmo.pos.enums.PosOrderServiceTypes, java.lang.Object)
	 */
	@Override
	public void setServiceTypeIfno(PosOrderServiceTypes type, Object data) {

	}
	
	/* (non-Javadoc)
	 * @see com.indocosmo.pos.components.orderentry.PosActionPanelBase#reset()
	 */
	@Override
	public void reset() {
		super.reset();
		mButtonOrderInfo.setEnabled(false);
		
	}
	
	/* (non-Javadoc)
	 * @see com.indocosmo.pos.components.orderentry.PosActionPanelBase#setForNewOrder(com.indocosmo.pos.dataobjects.PosOrderHeaderObject)
	 */
	@Override
	public void onNewOrderStarted(BeanOrderHeader order) {
		super.onNewOrderStarted(order);
		mButtonOrderInfo.setEnabled(true);
	}


}
