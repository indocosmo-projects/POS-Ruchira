/**
 * 
 */
package com.indocosmo.pos.forms.split.components;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.RootPaneContainer;

import com.indocosmo.pos.common.enums.split.SplitBasedOn;
import com.indocosmo.pos.forms.split.listners.ISimpleSplitMethodPanelListener;
import com.indocosmo.pos.forms.split.listners.ISplitMethodPanelListener;
import com.indocosmo.pos.forms.split.listners.ISplitMethodSelectionListener;
import com.indocosmo.pos.forms.split.listners.ISplitOperationPanelListener;

/**
 * @author jojesh
 *
 */
public class SplitOperationPanel extends JPanel implements ISplitMethodPanelListener, ISimpleSplitMethodPanelListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final int PANEL_CONTENT_V_GAP=1;
	private static final int PANEL_CONTENT_H_GAP=2;

	private int SPLIT_METHOD_SELECTION_PANEL_HEIGHT=50;

	private SplitMethodSelectionPanel mSplitMethodeSelectionPanel;
	
	private CustomSplitMethodPanel mAdvancedSplitePanel;
	private SimpleSplitMethodPanelBase mSimpleSplitePanel;
	private TableSeatSplitMethodPanel mTableSeatSplitMethodPanel; 

	private RootPaneContainer mParent;
	private int mWidth;
	private int mHeight;
	private SplitBasedOn selectedSplitBasedOn;
	private ISplitOperationPanelListener listener;

	/**
	 * @param parent
	 * @param width
	 * @param height
	 */
	public SplitOperationPanel(RootPaneContainer parent, int width, int height){

		this.mParent=parent;
		this.mWidth=width;
		this.mHeight=height;
		initComponent();

	}

	/**
	 * 
	 */
	private void initComponent() {

		setLayout(null);
		setSize(new Dimension(mWidth,mHeight));
		setPreferredSize(getSize());
		setFocusable(true);
		createSplitMethodSelectionPanel();
		createSplitOptionsPanels();

	}

	/**
	 * 
	 */
	private void createSplitOptionsPanels() {

		createAdvanceSplitMethodPanel();
		createSeperator();
		createSimpleSplitMethodPanel();
		createTableSeatSplitMethodPanel();
	}

	/**
	 * 
	 */
	private void createSeperator() {

		final int left=PANEL_CONTENT_H_GAP;
		final int top=mSplitMethodeSelectionPanel.getY()+mSplitMethodeSelectionPanel.getHeight();

		JPanel sepLine=new JPanel();
		sepLine.setBounds(left, top, getWidth()-PANEL_CONTENT_H_GAP*2,PANEL_CONTENT_V_GAP);
		sepLine.setBackground(Color.GRAY);
		add(sepLine);

	}

	/**
	 * 
	 */
	private void createSplitMethodSelectionPanel() {
		
		final int width=mWidth-PANEL_CONTENT_H_GAP*2;
		

		int left=PANEL_CONTENT_H_GAP;
		int top=PANEL_CONTENT_V_GAP;

//		mSplitMethodeSelectionPanel=new SplitMethodSelectionPanelPopup(mParent, 
//				width,
//				SPLIT_METHOD_SELECTION_PANEL_HEIGHT);
		mSplitMethodeSelectionPanel=new SplitMethodSelectionPanelButtons(mParent, 
				width,
				SPLIT_METHOD_SELECTION_PANEL_HEIGHT);
		mSplitMethodeSelectionPanel.setLocation(left,top);
		mSplitMethodeSelectionPanel.setListener(new ISplitMethodSelectionListener() {

			@Override
			public boolean onSplitMethodSelected(SplitBasedOn value) {
				// TODO Auto-generated method stub
				return onSplitMethodChanged(value);
			}
		});
		add(mSplitMethodeSelectionPanel);

	}
	
	private void createTableSeatSplitMethodPanel(){
				
		final int width=mWidth-PANEL_CONTENT_H_GAP*2;
		final int height=mHeight-SPLIT_METHOD_SELECTION_PANEL_HEIGHT-PANEL_CONTENT_V_GAP*4;
		
		int left=PANEL_CONTENT_H_GAP;
		int top=SPLIT_METHOD_SELECTION_PANEL_HEIGHT+PANEL_CONTENT_V_GAP*2;

		mTableSeatSplitMethodPanel=new TableSeatSplitMethodPanel(mParent,width,height);
		mTableSeatSplitMethodPanel.setLocation(left, top);
		mTableSeatSplitMethodPanel.setListener(this);
		add(mTableSeatSplitMethodPanel);
	}

	/**
	 * 
	 */
	private void createSimpleSplitMethodPanel(){

		final int width=mWidth-PANEL_CONTENT_H_GAP*2;
		final int height=mHeight-SPLIT_METHOD_SELECTION_PANEL_HEIGHT-PANEL_CONTENT_V_GAP*4;
		
		int left=PANEL_CONTENT_H_GAP;
		int top=SPLIT_METHOD_SELECTION_PANEL_HEIGHT+PANEL_CONTENT_V_GAP*2;

		mSimpleSplitePanel=new SimpleSplitMethodPanelExt(mParent,
				width,
				height);
		mSimpleSplitePanel.setListener(this);
		mSimpleSplitePanel.setLocation(left, top);
		add(mSimpleSplitePanel);
	}

	/**
	 * 
	 */
	private void createAdvanceSplitMethodPanel(){

		final int width=mWidth-PANEL_CONTENT_H_GAP*2;
		final int height=mHeight-SPLIT_METHOD_SELECTION_PANEL_HEIGHT-PANEL_CONTENT_V_GAP*4;
		
		int left=PANEL_CONTENT_V_GAP;
		int top=mSplitMethodeSelectionPanel.getY()+mSplitMethodeSelectionPanel.getHeight()+PANEL_CONTENT_V_GAP;

		mAdvancedSplitePanel=new CustomSplitMethodPanel(mParent,width,height);
		mAdvancedSplitePanel.setLocation(left, top);
		mAdvancedSplitePanel.setListener(this);
		add(mAdvancedSplitePanel);
	}

	/**
	 * @param method
	 * @return
	 */
	protected boolean onSplitMethodChanged(SplitBasedOn method) {

		//		if(this.selectedSplitBasedOn!=null && this.selectedSplitBasedOn!=method){

		if(!listener.onSplitMethodChanged(method)){

			return false;
		}

		selectedSplitBasedOn=method;
		switch (method) {
		case Amount:
		case Percentage:
		case Count:
			
			mSimpleSplitePanel.setSplitBasedOn(method);
			mSimpleSplitePanel.setVisible(true);
			mTableSeatSplitMethodPanel.setVisible(false);
			mAdvancedSplitePanel.setVisible(false);
			
			break;
		case Custom:
			
			mAdvancedSplitePanel.setSplitBasedOn(method);
			mAdvancedSplitePanel.setVisible(true);
			mTableSeatSplitMethodPanel.setVisible(false);
			mSimpleSplitePanel.setVisible(false);
			break;
		case Seat:
			
			mTableSeatSplitMethodPanel.setSplitBasedOn(method);
			mTableSeatSplitMethodPanel.setVisible(true);
			mAdvancedSplitePanel.setVisible(false);
			mSimpleSplitePanel.setVisible(false);
			
			break;
		}

		return true;

	}

	/**
	 * @return the listener
	 */
	public ISplitOperationPanelListener getListener() {
		return listener;
	}

	/**
	 * @param listener the listener to set
	 */
	public void setListener(ISplitOperationPanelListener listener) {
		this.listener = listener;
	}

	/**
	 * @return
	 */
	public double getSplitValue(){

//		Object value=null;
//
//		if(selectedSplitBasedOn.getBillSplitMethod()!=BillSplitMethod.Advance){
//			value=mSimpleSplitePanel.getSplitValue();
//		}

		return mSimpleSplitePanel.getSplitValue();
	}

	/**
	 * @return
	 */
	public boolean isSplitEqually(){

		return mAdvancedSplitePanel.isSplitEqually();
	}

	/**
	 * @return the selectedSplitBasedOn
	 */
	public SplitBasedOn getSplitBasedOn() {
		return selectedSplitBasedOn;
	}

	/**
	 * @param selectedSplitBasedOn the selectedSplitBasedOn to set
	 */
	public void setSplitBasedOn(SplitBasedOn splitBasedOn) {

//		this.selectedSplitBasedOn = splitBasedOn;
		this.mSplitMethodeSelectionPanel.setSplitBasedOn(splitBasedOn);
	}

/* (non-Javadoc)
 * @see javax.swing.JComponent#requestFocus()
 */
	@Override
	public void requestFocus() {

		mSimpleSplitePanel.requestFocus();
	}

	/**
	 * @return the mTableSeatSplitMethodPanel
	 */
	public TableSeatSplitMethodPanel getTableSeatSplitMethodPanel() {
		
		return mTableSeatSplitMethodPanel;
	}

	/**
	 * 
	 */
	public void reset() {
		
		mAdvancedSplitePanel.reset();
		mSimpleSplitePanel.reset();
		mTableSeatSplitMethodPanel.reset(); 
	}


	/**
	 * @param basedOn
	 * @param enabled
	 */
	public void setSplitMethodEnabled(SplitBasedOn basedOn, boolean enabled){
		
		mSplitMethodeSelectionPanel.setSplitMethodEnabled(basedOn, enabled);
		
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.pos.forms.split.listners.ISplitMethodPanelListener#onResetRequested()
	 */
	@Override
	public void onResetRequested() {
		
		listener.onResetRequested();
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.pos.forms.split.listners.ISplitMethodPanelListener#hasSplit()
	 */
	@Override
	public boolean hasSplit() {

		return listener.hasSplit();
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.pos.forms.split.listners.ISimpleSplitMethodPanelListener#onExactAmountRequested(java.lang.Object)
	 */
	@Override
	public double onExactAmountRequested(Object sender) {
		
		double exactAmo=0.00;
		
		if(listener!=null)
			exactAmo=listener.onExactAmountRequested();
		
		return exactAmo;
	}

}
