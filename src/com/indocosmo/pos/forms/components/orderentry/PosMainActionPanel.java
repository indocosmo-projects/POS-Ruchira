package com.indocosmo.pos.forms.components.orderentry;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.RootPaneContainer;
import javax.swing.SwingConstants;

import com.indocosmo.pos.common.PosEnvSettings;
import com.indocosmo.pos.common.PosLog;
import com.indocosmo.pos.common.enums.PosOrderServiceTypes;
import com.indocosmo.pos.common.utilities.PosFormUtil;
import com.indocosmo.pos.common.utilities.PosResUtil;
import com.indocosmo.pos.data.beans.BeanOrderHeader;
import com.indocosmo.pos.data.beans.BeanServingTable;
import com.indocosmo.pos.forms.PosObjectBrowserForm;
import com.indocosmo.pos.forms.PosObjectBrowserForm.ItemSize;
import com.indocosmo.pos.forms.components.buttons.PosButton;
import com.indocosmo.pos.forms.components.buttons.PosIconButton;
import com.indocosmo.pos.forms.components.buttons.listners.IPosButtonListner;
import com.indocosmo.pos.forms.components.buttons.listners.PosButtonListnerAdapter;
import com.indocosmo.pos.forms.components.objectbrowser.IPosBrowsableItem;
import com.indocosmo.pos.forms.components.orderentry.listners.IPosActionPanelRestaurantListner;
import com.indocosmo.pos.forms.listners.IPosObjectBrowserListner;

@SuppressWarnings("serial")
public final class PosMainActionPanel extends PosActionPanelBase {

	public enum RestaurantOrderEntryMenuActions {

		SERVICE_SELECTION,TABLE_SELECTION, WAITER_SELECTION;
	}

	private PosIconButton mButtonTableWaiter;
	private PosOrderServiceSelection mOrderServiceSelectionPanel;
	/***
	 * Default constructor
	 */
	public PosMainActionPanel(RootPaneContainer parent) {
		super(parent);
		//Comments added by Udhayan for DayStart module problem On 23-Jul-2021
		//PosLog.debug("createTopPanles================ After super(parent)");
		//PosLog.debug("mServiceType value================)"+mServiceType);
		setServiceTypeIfno(mServiceType,null);
		//PosLog.debug("createTopPanles================ After setServiceTypeIfno  ");
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
		final int noWideButtons = 4;
		final int noNormButtons = 1;
		return WIDE_BUTTON_WIDTH * noWideButtons +(NORM_BUTTON_WIDTH*noNormButtons) + PANEL_CONTENT_H_GAP 
				* (noWideButtons + noNormButtons+1) + PosOrderServiceSelection.LAYOUT_WIDTH;
	}
	/***
	 * creates the order group and buttons
	 */
	private void createOrderButtons() {
		
		createServiceSelection();
		
		JPanel orderButtonsPanel = new JPanel();
		orderButtonsPanel.setLayout(new FlowLayout(FlowLayout.CENTER,
				PANEL_CONTENT_H_GAP, PANEL_CONTENT_V_GAP));
		orderButtonsPanel.setPreferredSize(new Dimension(WIDE_BUTTON_WIDTH+NORM_BUTTON_WIDTH
				+ PANEL_CONTENT_H_GAP * 2, LAYOUT_HEIGHT));
		orderButtonsPanel.setOpaque(false);
		add(orderButtonsPanel);
		
		JPanel grpPanle =new JPanel();
		grpPanle.setLayout(null);
		grpPanle.setOpaque(false);
		grpPanle.setPreferredSize(new Dimension(WIDE_BUTTON_WIDTH
				, (WIDE_BUTTON_HEIGHT*2)+PANEL_CONTENT_V_GAP));
		orderButtonsPanel.add(grpPanle);
		
		createSaveButton(grpPanle,true);
		mButtonSave.setLocation(0, 0);
		createRetrieveButton(grpPanle,true);
		mButtonOpen.setLocation(
				0, 
				mButtonSave.getY()+mButtonSave.getHeight()+PANEL_CONTENT_V_GAP);
		/** table button is temporary disabled for future implementation **/
		createTableButton(orderButtonsPanel);
		/**/
		createInfoButton(orderButtonsPanel,false);

	}
	/***
	 * create Service Selection buttons
	 */
	private void createServiceSelection() {
		mOrderServiceSelectionPanel = new PosOrderServiceSelection(
				this);
		mOrderServiceSelectionPanel.setListner(new IPosOrderServiceSelectionListner() {

			@Override
			public void onServiceRequested(PosOrderServiceTypes service) {
				if (mPosActionPanelListner != null)
					((IPosActionPanelRestaurantListner) mPosActionPanelListner)
							.onPosActionButtonClicked(
									OrderEntryMenuActions.ORDER_NEW,
									service);
				
			}

			@Override
			public void onServiceChanged(PosOrderServiceTypes service) {
				if (mPosActionPanelListner != null)
					((IPosActionPanelRestaurantListner) mPosActionPanelListner)
							.onPosActionButtonClicked(
									RestaurantOrderEntryMenuActions.SERVICE_SELECTION,
									service);
				
			}
		});
		this.add(mOrderServiceSelectionPanel);
	}
	/***
	 * Create the Table/Waiter button
	 * @param panel
	 */
	private void createTableButton(JPanel panel) {
		
		mButtonTableWaiter = createActionPanelButton("Table Waiter", true,KeyEvent.VK_W,'T');
		mButtonTableWaiter.setOnClickListner(new PosButtonListnerAdapter() {
			@Override
			public void onClicked(PosButton button) {
				if (mPosActionPanelListner != null)
					((IPosActionPanelRestaurantListner) mPosActionPanelListner)
							.onPosActionButtonClicked(
									RestaurantOrderEntryMenuActions.TABLE_SELECTION,
									null);
			};
		});
		//panel.add(mButtonTableWaiter);
	}

	/***
	 * Creates the Misc buttons
	 */
	private void createMiscButtons() {
		JPanel otherButtonsPanel = new JPanel();
		otherButtonsPanel.setLayout(new FlowLayout(FlowLayout.CENTER,
				PANEL_CONTENT_H_GAP, PANEL_CONTENT_V_GAP));
		otherButtonsPanel.setPreferredSize(new Dimension(WIDE_BUTTON_WIDTH * 2
				+ PANEL_CONTENT_H_GAP * 2, LAYOUT_HEIGHT));
		otherButtonsPanel.setOpaque(false);
		add(otherButtonsPanel);
		createCustomerInfoButton(otherButtonsPanel,true);
		createMenuButton(otherButtonsPanel,true);
		createPosMessageButton(otherButtonsPanel, true);
		createMoreButton(otherButtonsPanel,true);
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.pos.components.orderentry.PosActionPanelBase#setForNewService(com.indocosmo.pos.enums.PosOrderServiceTypes, java.lang.Object)
	 */
	@Override
	public void setServiceTypeIfno(PosOrderServiceTypes type, Object data) {
		//Comments added by Udhayan for DayStart module problem On 23-Jul-2021
		//PosLog.debug("createTopPanles================ inside setServiceTypeIfno");
	//	PosLog.debug("type================" +type+"data====="+data);
		if(mServiceType!=type){
			
			reset();
			//PosLog.debug("createTopPanles================ after reset");
			mServiceType = type;
			//PosLog.debug("createTopPanles================ after mServiceType");
		}
		mOrderServiceSelectionPanel.setSelectedServiceType(type,data);
		//PosLog.debug("createTopPanles================ after mOrderServiceSelectionPanel");
	}
	
	/* (non-Javadoc)
	 * @see com.indocosmo.pos.components.orderentry.PosActionPanelBase#reset()
	 */
	@Override
	public void reset() {
		super.reset();
		mButtonTableWaiter.setEnabled(false);
		mButtonOrderInfo.setEnabled(false);
		mOrderServiceSelectionPanel.reset();
	}
	
	/* (non-Javadoc)
	 * @see com.indocosmo.pos.components.orderentry.PosActionPanelBase#setForNewOrder(com.indocosmo.pos.dataobjects.PosOrderHeaderObject)
	 */
	@Override
	public void onNewOrderStarted(BeanOrderHeader order) {
		super.onNewOrderStarted(order);
		mButtonTableWaiter.setEnabled(mServiceType == PosOrderServiceTypes.TABLE_SERVICE);
	}

	/***
	 * Sub class for service selection buttons
	 * 
	 * @author jojesh
	 * 
	 */
	private class PosOrderServiceSelection extends JPanel {
		
		private PosIconButton mButtonNew;
		private PosIconButton mButtonService;

		private static final int BUTTON_WIDTH = 100;
		private static final int BUTTON_HEIGHT = NORM_BUTTON_HEIGHT;
		
		public static final int LAYOUT_WIDTH = BUTTON_WIDTH+NORM_BUTTON_WIDTH
				+ PANEL_CONTENT_H_GAP*2;
		private final int LAYOUT_HEIGHT = PosActionPanelBase.LAYOUT_HEIGHT;

		protected static final String IMAGE_SERVICE_BUTTON = "action_panel_service.png";
		protected static final String IMAGE_SERVICE_BUTTON_TOUCHED = "action_panel_service_touch.png";
		
		protected static final String IMAGE_NEW_BUTTON = "action_panel_button_new.png";
		protected static final String IMAGE_NEW_BUTTON_TOUCHED = "action_panel_button_new_touch.png";

		private ImageIcon mImageButtonService;
		private ImageIcon mImageButtonServiceTouch;
		private ImageIcon mImageButtonNew;
		private ImageIcon mImageButtonNewTouch;
		
		private PosOrderServiceTypes mSelectedServiceType=PosEnvSettings.getInstance().getUISetting().getOrderEntryUISettings().getDefaultServiceType();
		private JPanel mRootPanel;
		private Object mSelectedServiceData;
		/***
		 * Constructor
		 * 
		 * @param panel
		 */
		public PosOrderServiceSelection(JPanel panel) {
			this.mRootPanel = panel;

			this.setLayout(new FlowLayout(FlowLayout.CENTER,
					PANEL_CONTENT_H_GAP, PANEL_CONTENT_V_GAP));
			this.setPreferredSize(new Dimension(LAYOUT_WIDTH, LAYOUT_HEIGHT));
			this.setOpaque(false);

			loadButtonImages();
			createServiceSelectionActionButtons();
			setSelectedServiceType(PosEnvSettings.getInstance().getUISetting().getOrderEntryUISettings().getDefaultServiceType());
		}
		
		/**
		 * @param type
		 * @param data
		 */
		public void setSelectedServiceType(PosOrderServiceTypes type,
				Object data) {
			this.mSelectedServiceType=type;
			this.mSelectedServiceData=data;
			String caption="NONE";
			switch (mSelectedServiceType) {
			case TABLE_SERVICE:
				BeanServingTable table=(mSelectedServiceData!=null)?(BeanServingTable)this.mSelectedServiceData:null;
				caption=PosEnvSettings.getInstance().getUISetting().getServiceTableTitle()+" <br> [ "+(table==null?"NONE":table.getCode())+" ]</br>";
				break;
			case HOME_DELIVERY:
				caption=PosEnvSettings.getInstance().getUISetting().getServiceHomeDelTitle();
				break;
			case TAKE_AWAY:
				caption=PosEnvSettings.getInstance().getUISetting().getServiceTakeAwayTitle();
				break;
			case WHOLE_SALE:
				caption=PosEnvSettings.getInstance().getUISetting().getServiceWholeSaleTitle();
				break;
			case SALES_ORDER:
				caption=PosEnvSettings.getInstance().getUISetting().getServiceSalesOrderTitle();
				break;
			default:
				caption="NOT SET";
				break;

			}
			mButtonService.setText("<center>"+caption+"</center>");
		}

		public void reset(){
			setSelectedServiceType(mServiceType);
		}
		
		public void setSelectedServiceType(PosOrderServiceTypes service){
			this.setSelectedServiceType(service,null);
		}

		/***
		 * Creates the various service selection buttons
		 */
		private void createServiceSelectionActionButtons() {
			mButtonService = createServiceSelectionActionButton("TABLE <br>[ NONE ]");
			
			mButtonService.setOnClickListner(new IPosButtonListner() {

				@Override
				public void onClicked(PosButton button) {
					PosObjectBrowserForm form=new PosObjectBrowserForm("Services", PosOrderServiceTypes.values(),ItemSize.Wider,1,3);
					form.setListner(new IPosObjectBrowserListner() {
						
						@Override
						public void onItemSelected(IPosBrowsableItem item) {
							PosOrderServiceTypes service=(PosOrderServiceTypes)item;
							if(mSelectedServiceType!=service){
								if(mListner!=null)
									mListner.onServiceChanged(service);
							}
						}
						
						@Override
						public void onCancel() {
							// TODO Auto-generated method stub
							
						}
					});
					PosFormUtil.showLightBoxModal(mParent,form);
				}
			});
			this.add(mButtonService);

			mButtonNew= createActionPanelButton("New",KeyEvent.VK_N,'N');
			mButtonNew.registerKeyStroke(KeyEvent.VK_N,KeyEvent.CTRL_DOWN_MASK);
			mButtonNew.setImage(mImageButtonNew);
			mButtonNew.setTouchedImage(mImageButtonNewTouch);
			mButtonNew.setOnClickListner(new IPosButtonListner() {
				@Override
				public void onClicked(PosButton button) {
					if (mListner != null)
						mListner.onServiceRequested(mSelectedServiceType);
				}
			});
			this.add(mButtonNew);
		}

		/***
		 * Creates the service selections buttons
		 * 
		 * @param caption
		 * @return
		 */
		protected PosIconButton createServiceSelectionActionButton(
				String caption) {
			PosIconButton buton = new PosIconButton(caption);
			buton.setAutoMnemonicEnabled(false);
			buton.setSize(BUTTON_WIDTH, BUTTON_HEIGHT);
			buton.setImage(mImageButtonService);
			buton.setTouchedImage(mImageButtonServiceTouch);
			buton.setHorizontalAlignment(SwingConstants.CENTER);
			buton.setVerticalTextPosition(SwingConstants.CENTER);
			buton.setHorizontalTextPosition(SwingConstants.CENTER);
			buton.registerKeyStroke(KeyEvent.VK_S, InputEvent.ALT_MASK | InputEvent.CTRL_MASK);
			return buton;
		}

		/***
		 * Loads image buttons
		 */
		private void loadButtonImages() {
			mImageButtonService = PosResUtil
					.getImageIconFromResource(IMAGE_SERVICE_BUTTON);
			mImageButtonServiceTouch = PosResUtil
					.getImageIconFromResource(IMAGE_SERVICE_BUTTON_TOUCHED);
			mImageButtonNew = PosResUtil
					.getImageIconFromResource(IMAGE_NEW_BUTTON);
			mImageButtonNewTouch = PosResUtil
					.getImageIconFromResource(IMAGE_NEW_BUTTON_TOUCHED);
		}

		/***
		 * Listener
		 */
		private IPosOrderServiceSelectionListner mListner;

		/***
		 * Setter for listener
		 * 
		 * @param listner
		 */
		public void setListner(IPosOrderServiceSelectionListner listner) {
			this.mListner = listner;
		}

	}

	/***
	 * Listener for order service selection
	 * 
	 * @author jojesh
	 * 
	 */
	private interface IPosOrderServiceSelectionListner {
		public void onServiceRequested(final PosOrderServiceTypes service);
		public void onServiceChanged(final PosOrderServiceTypes service);
	}


}
