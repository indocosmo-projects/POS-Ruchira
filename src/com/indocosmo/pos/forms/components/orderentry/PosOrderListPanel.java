package com.indocosmo.pos.forms.components.orderentry;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SortOrder;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.text.JTextComponent;

import com.indocosmo.pos.common.PosEnvSettings;
import com.indocosmo.pos.common.PosLog;
import com.indocosmo.pos.common.beans.settings.ui.orderentry.BeanUIOrderEntrySetting;
import com.indocosmo.pos.common.enums.ItemEditMode;
import com.indocosmo.pos.common.utilities.PosAccessPermissionsUtil;
import com.indocosmo.pos.common.utilities.PosCurrencyUtil;
import com.indocosmo.pos.common.utilities.PosDateUtil;
import com.indocosmo.pos.common.utilities.PosFormUtil;
import com.indocosmo.pos.common.utilities.PosOrderUtil;
import com.indocosmo.pos.common.utilities.PosSaleItemUtil;
import com.indocosmo.pos.common.utilities.PosStringUtil;
import com.indocosmo.pos.common.utilities.PosTaxUtil;
import com.indocosmo.pos.common.utilities.PosUomUtil;
import com.indocosmo.pos.data.beans.BeanCustomerType;
import com.indocosmo.pos.data.beans.BeanDiscount;
import com.indocosmo.pos.data.beans.BeanOrderDetail;
import com.indocosmo.pos.data.beans.BeanOrderDetail.OrderDetailItemType;
import com.indocosmo.pos.data.beans.BeanOrderHeader;
import com.indocosmo.pos.data.beans.BeanSaleItem;
import com.indocosmo.pos.data.beans.BeanSaleItemComboContent;
import com.indocosmo.pos.data.beans.BeanSaleItemComboContentSubstitute;
import com.indocosmo.pos.data.providers.shopdb.PosCustomerTypeProvider;
import com.indocosmo.pos.data.providers.shopdb.PosDiscountItemProvider;
import com.indocosmo.pos.data.providers.shopdb.PosSaleItemComboContentProvider;
import com.indocosmo.pos.data.providers.shopdb.PosTaxItemProvider.TaxCalculationMethod;
import com.indocosmo.pos.data.providers.shopdb.PosUOMProvider;
import com.indocosmo.pos.forms.PosOrderEntryForm;
import com.indocosmo.pos.forms.PosOrderEntryForm.PosOrderEntryMode;
import com.indocosmo.pos.forms.PosOrderItemQuickEditForm;
import com.indocosmo.pos.forms.PosOrderListItemEditForm;
import com.indocosmo.pos.forms.components.itemcontrols.PosOrderListItemControl;
import com.indocosmo.pos.forms.components.itemcontrols.listners.IPosOrderListItemControlListner;
import com.indocosmo.pos.forms.components.keypads.PosNumKeypad.KeypadTypes;
import com.indocosmo.pos.forms.components.orderentry.itemedit.PosTabOrderItemAttributeEdit;
import com.indocosmo.pos.forms.components.orderentry.itemedit.PosTabOrderItemComboContentEdit;
import com.indocosmo.pos.forms.components.orderentry.itemedit.PosTabOrderItemExtrasEdit;
import com.indocosmo.pos.forms.components.orderentry.itemedit.PosTabOrderItemPriceDiscountEdit;
import com.indocosmo.pos.forms.components.orderentry.listners.IPosItemGridOptionPanelListner;
import com.indocosmo.pos.forms.components.orderentry.listners.IPosOrderListPanelListner;
import com.indocosmo.pos.forms.components.search.IPosSearchableItem;
import com.indocosmo.pos.forms.listners.IPosItemEditFormListner;
import com.indocosmo.pos.forms.listners.IPosNumKeyPadFormListner;
import com.indocosmo.pos.forms.listners.adapters.PosItemEditFormAdapter;
import com.indocosmo.pos.forms.messageboxes.PosMessageBoxForm.MessageBoxButtonTypes;
import com.indocosmo.pos.forms.messageboxes.PosMessageBoxForm.MessageBoxResults;
import com.indocosmo.pos.forms.messageboxes.listners.PosMessageBoxFormListnerAdapter;
import com.indocosmo.pos.forms.search.PosExtSearchForm;
import com.indocosmo.pos.forms.search.listener.adapter.PosItemExtSearchFormAdapter;
import com.indocosmo.pos.terminal.devices.PosDeviceManager;
import com.indocosmo.pos.terminal.devices.poledisplay.PosDevicePoleDisplay;

@SuppressWarnings("serial")
public final class PosOrderListPanel extends JPanel implements IPosItemGridOptionPanelListner
{

	private static final int PANEL_CONTENT_V_GAP = 1;
	private static final int PANEL_CONTENT_H_GAP = 1;
	public static final int LAYOUT_WIDTH = PosOrderListItemControl.LAYOUT_WIDTH+PANEL_CONTENT_V_GAP*2;
	private static final Color PANEL_BG_COLOR = Color.WHITE;
	private static int ITEM_TOTAL_PANEL_HEIGHT = 72;
	public static final Color GRID_ITEM_BORDER_COLOR = Color.LIGHT_GRAY;

	private int mHeight;
	private JPanel mItemListPanelContainer;
	private JPanel mItemListPanel;
	private JPanel mItemTotalPanel;
	private PosOrderListItemControl mSelectedListItemControl=new PosOrderListItemControl();
	private int mSelectedItemIndex;
	private PosOrderListOptionPanel mitemGridControl;
	private JLabel mLabelTotalCountValue;
	private JLabel mLabelTotalDiscountValue;
	private JLabel mLabelTotalTaxValue;
	private JLabel mLabelTotalAmount;

	// Bill amounts
	private double mBillGrandTotalAmount;
	private double mBillItemTotalAmount;
	private double mBillDiscountAmount;
	private double mBillTaxAmount;
	private double mBillTax1Amount;
	private double mBillTax2Amount;
	private double mBillTax3Amount;
	private double mBillGSTAmount;
	private double mBillServiceTaxAmount;
	private double mBillItemCount;

	private int mItemsPerPage;
	private int mDisplayItemsPerPage;
	private boolean mReadOnly = false;
	private PosOrderEntryForm mParent;
	private boolean mHighlighteDiscounted = false;
	private boolean mHideTotalPanel=true;

	/**
	 * Hashmap for grid item controls.
	 * Key DetailItem id
	 * */
	private HashMap<String,PosOrderListItemControl> mPosGridItemsCtrlIDHashMap;
	private ArrayList<PosOrderListItemControl> mPosGridItems;
	private ArrayList<BeanOrderDetail> mPosOrderDetailItems;

	private BeanCustomerType mSelectedCustomerType;
	private BeanCustomerType mDefCustomerType;
	private PosDevicePoleDisplay mPosPoleDisplay;
	private PosDiscountItemProvider discountProvider;


	/**
	 * @param height
	 */
	public PosOrderListPanel(int height) {
		mHeight = height;
		initComponent();
	}

	/**
	 * @param height
	 * @param hideTotalPanel
	 */
	public PosOrderListPanel(int height,boolean hideTotalPanel) {
		mHeight = height;
		mHideTotalPanel=hideTotalPanel;
		initComponent();
	}


	/**
	 * 
	 */
	private void initComponent() {

		if(mHideTotalPanel)
			ITEM_TOTAL_PANEL_HEIGHT=0;

		mPosPoleDisplay = PosDevicePoleDisplay.getInstance();
		discountProvider=new PosDiscountItemProvider();
		mDefCustomerType = new PosCustomerTypeProvider()
		.getDefaultCustomerType();
		mSelectedCustomerType = mDefCustomerType;
		setSize(LAYOUT_WIDTH, mHeight);
		setPreferredSize(new Dimension(LAYOUT_WIDTH, mHeight));
		setBackground(PANEL_BG_COLOR);
		setBorder(new LineBorder(GRID_ITEM_BORDER_COLOR,1));
		setLayout(new FlowLayout(FlowLayout.CENTER, PANEL_CONTENT_H_GAP, PANEL_CONTENT_V_GAP));
		createPanels();
		mItemsPerPage = Math.round(mItemListPanelContainer.getHeight()
				/ PosOrderListItemControl.LAYOUT_HEIGHT);
		mDisplayItemsPerPage=(( mItemListPanelContainer.getHeight()
				% PosOrderListItemControl.LAYOUT_HEIGHT)>PosOrderListItemControl.LAYOUT_HEIGHT/2)?mItemsPerPage+1:mItemsPerPage;
		mPosGridItems = new ArrayList<PosOrderListItemControl>();
		mPosGridItemsCtrlIDHashMap=new HashMap<String,PosOrderListItemControl>();
	}

	/**
	 * 
	 */
	private void createPanels() {

		final int containerHeight = getHeight() - ITEM_TOTAL_PANEL_HEIGHT-3;
		//				- PANEL_CONTENT_V_GAP;
		mItemListPanelContainer = new JPanel();
		mItemListPanelContainer.setSize(new Dimension(PosOrderListItemControl.LAYOUT_WIDTH,
				containerHeight));
		mItemListPanelContainer.setPreferredSize(new Dimension(PosOrderListItemControl.LAYOUT_WIDTH,
				containerHeight));
		mItemListPanelContainer.setLayout(null);
		mItemListPanelContainer.setBackground(PANEL_BG_COLOR);
		this.add(mItemListPanelContainer);
		mItemListPanel = new JPanel();
		mItemListPanel.setBounds(0, 0, PosOrderListItemControl.LAYOUT_WIDTH, 0);
		mItemListPanel.setBackground(PANEL_BG_COLOR);
		mItemListPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 1, 0));
		mItemListPanelContainer.add(mItemListPanel);

		createTotalPanel();
	}

	/**
	 * 
	 */
	private void createTotalPanel() {
		mItemTotalPanel = new JPanel();
		mItemTotalPanel.setPreferredSize(new Dimension(getWidth(),
				ITEM_TOTAL_PANEL_HEIGHT));
		mItemTotalPanel.setBorder(new MatteBorder(1, 1, 1, 1,
				PosOrderListPanel.GRID_ITEM_BORDER_COLOR));
		mItemTotalPanel.setBackground(PANEL_BG_COLOR);
		FlowLayout fl = new FlowLayout(FlowLayout.LEFT);
		fl.setHgap(0);
		mItemTotalPanel.setLayout(fl);

		add(mItemTotalPanel);

		JLabel totalTitle = new JLabel("Amount : ");
		totalTitle.setFont(new Font(PosEnvSettings.getInstance().getUISetting().getLabelFont(), Font.BOLD, 15));
		totalTitle.setSize(new Dimension(80, 30));
		totalTitle.setHorizontalAlignment(SwingConstants.LEFT);
		totalTitle.setVerticalAlignment(SwingConstants.CENTER);
		mItemTotalPanel.add(totalTitle);

		mLabelTotalAmount = new JLabel("00.00");
		mLabelTotalAmount.setFont(new Font(PosEnvSettings.getInstance().getUISetting().getLabelFont(), Font.BOLD, 30));
		mLabelTotalAmount.setPreferredSize(new Dimension(232, 30));
		mLabelTotalAmount.setHorizontalAlignment(SwingConstants.LEFT);
		mLabelTotalAmount.setVerticalAlignment(SwingConstants.CENTER);
		mItemTotalPanel.add(mLabelTotalAmount);

		JLabel countTitle = new JLabel("Q:");
		countTitle.setFont(new Font(PosEnvSettings.getInstance().getUISetting().getLabelFont(), Font.BOLD, 15));
		countTitle.setPreferredSize(new Dimension(22, 20));
		countTitle.setHorizontalAlignment(SwingConstants.LEFT);
		countTitle.setVerticalAlignment(SwingConstants.CENTER);
		mItemTotalPanel.add(countTitle);

		mLabelTotalCountValue = new JLabel("000.00");
		mLabelTotalCountValue.setFont(new Font(PosEnvSettings.getInstance().getUISetting().getLabelFont(), Font.BOLD, 15));
		mLabelTotalCountValue.setPreferredSize(new Dimension(55, 25));
		mLabelTotalCountValue.setHorizontalAlignment(SwingConstants.LEFT);
		mLabelTotalCountValue.setVerticalAlignment(SwingConstants.CENTER);
		mItemTotalPanel.add(mLabelTotalCountValue);

		JLabel discTitle = new JLabel(" D:");
		discTitle.setFont(new Font(PosEnvSettings.getInstance().getUISetting().getLabelFont(), Font.BOLD, 15));
		discTitle.setPreferredSize(new Dimension(29, 20));
		discTitle.setHorizontalAlignment(SwingConstants.RIGHT);
		discTitle.setVerticalAlignment(SwingConstants.CENTER);
//		if(PosUISettings.getInstance().showDetailsInBillSummary())
		mItemTotalPanel.add(discTitle);

		mLabelTotalDiscountValue = new JLabel("0000.00");
		mLabelTotalDiscountValue.setFont(new Font(PosEnvSettings.getInstance().getUISetting().getLabelFont(), Font.BOLD, 15));
		mLabelTotalDiscountValue.setPreferredSize(new Dimension(65, 25));
		mLabelTotalDiscountValue.setHorizontalAlignment(SwingConstants.LEFT);
		mLabelTotalDiscountValue.setVerticalAlignment(SwingConstants.CENTER);
//		if(PosUISettings.getInstance().showDetailsInBillSummary())
		mItemTotalPanel.add(mLabelTotalDiscountValue);

		JLabel taxTitle = new JLabel(" T:");
		taxTitle.setFont(new Font(PosEnvSettings.getInstance().getUISetting().getLabelFont(), Font.BOLD, 15));
		taxTitle.setPreferredSize(new Dimension(27, 20));
		taxTitle.setHorizontalAlignment(SwingConstants.RIGHT);
		taxTitle.setVerticalAlignment(SwingConstants.CENTER);
//		if(PosUISettings.getInstance().showDetailsInBillSummary())
		mItemTotalPanel.add(taxTitle);

		mLabelTotalTaxValue = new JLabel("000.00");
		mLabelTotalTaxValue.setFont(new Font(PosEnvSettings.getInstance().getUISetting().getLabelFont(), Font.BOLD, 15));
		mLabelTotalTaxValue.setPreferredSize(new Dimension(100, 25));
		mLabelTotalTaxValue.setHorizontalAlignment(SwingConstants.LEFT);
		mLabelTotalTaxValue.setVerticalAlignment(SwingConstants.CENTER);
//		if(PosUISettings.getInstance().showDetailsInBillSummary())
		mItemTotalPanel.add(mLabelTotalTaxValue);

	}

	/**
	 * 
	 */
	public void validateComponent() {
		invalidate();
		repaint();
	}

	/**
	 * 
	 */
	private IPosOrderListItemControlListner gridItemListner = new IPosOrderListItemControlListner() {
		@Override
		public void onSelected(PosOrderListItemControl gridItem) {
			if (mSelectedListItemControl != null)
				mSelectedListItemControl.setSelected(false);
			mSelectedItemIndex = (gridItem.getItemIndex()) - 1;
			mSelectedListItemControl = gridItem;
			adjustBounds(gridItem, false);
			if (mListner != null)
				mListner.onItemSelected(gridItem);
		}

		@Override
		public void onDoubleClick(PosOrderListItemControl gridItem) {
			onEditClicked();
		}
	};

	/**
	 * @param itemCode
	 * @throws Exception
	 */
	private void addPosItem(String itemCode) throws Exception {
		ArrayList<BeanSaleItem> availablePositems = mParent
				.getAvailablePositems();
		final int index = findIndexoOf(itemCode, availablePositems, 0);
		if (index >= 0) {
			BeanSaleItem item = availablePositems.get(index);
			addPosItem(item);
		}
	}

	/**
	 * @param orderItem
	 * @return
	 */
	public boolean addOrderDetailItem(final BeanOrderDetail orderItem){
		return addOrderDetailItem(-1,orderItem);
	}


	/**
	 * @param at
	 * @param orderItem
	 * @return
	 */
	public boolean addOrderDetailItem(int at,final BeanOrderDetail orderItem) {

		final BeanUIOrderEntrySetting orderEntrySettings =PosEnvSettings.getInstance().getUISetting().getOrderEntryUISettings();
				 
		final boolean showItemDetEditScreen= PosOrderUtil.canShowItemEditUI(orderItem);
		
//		final boolean showItemDetEditScreen= ((orderItem.getSaleItem().isComboItem() && orderEntrySettings.canShowEditWindowForItemsWithComboOnAdd()) 
//				|| (orderItem.getSaleItem().hasChoices() && orderEntrySettings.canShowEditWindowForItemsWithChoiceOnAdd()) 
//				|| (orderItem.getSaleItem().getAtrributeList()!=null 
//						&& orderItem.getSaleItem().getAtrributeList().size()>0 
//						&& orderEntrySettings.canShowEditWindowForItemsWithModifiersOnAdd()));
	
		final boolean isOpenItem=orderItem.getSaleItem().isOpen() && 
				 PosEnvSettings.getInstance().getUISetting().getOrderEntryUISettings().canShowEditWindowForOpenItemsOnAdd();
		
//		final BeanUIOrderEntrySetting orderEntrySettings=PosEnvSettings.getInstance().getUISetting().getOrderEntryUISettings();
		
		if(orderItem.isNewItem() && !orderItem.isAddedToOrder()){
			
			
			// weighing item, will take qty from weighing machine,
			// if fails to read from weighing machine, show an external qty window for manual entry
			// if the item is an open item or it has attributes/choice, it will not show that external qty window, 
			// instead of that it will show item edit screen or quick edit extended window based on ui settings  
			
			if (orderItem.getSaleItem().isRequireWeighing()){
				
				
				final double quantity=getWeightFromWeighingMachine(!isOpenItem && !showItemDetEditScreen && 
						!orderEntrySettings.canShowQuickEdit());
				
				if(quantity<=0) 
					return false;

				orderItem.getSaleItem().setQuantity(quantity);
				PosTaxUtil.calculateTax(orderItem.getSaleItem());
				
			}
			
			if(isOpenItem){

				if(orderEntrySettings.getQuickEditMode()==ItemEditMode.Extended)
					showItemEditExtendedWindow(PosTabOrderItemPriceDiscountEdit.TAB_CAPTION , orderItem);
				
			}else if (orderEntrySettings.canShowQuickEdit() &&  !showItemDetEditScreen ){
				
				if(orderEntrySettings.getQuickEditMode()==ItemEditMode.Simple && orderItem.getParentDtlId()==null  ){
				 	
					final double quantity=getQtyFromKeyPadWindow(true);
					if(quantity<=0) return false;
					orderItem.getSaleItem().setQuantity(quantity);
					PosTaxUtil.calculateTax(orderItem.getSaleItem());
				
				} else if( orderEntrySettings.getQuickEditMode()==ItemEditMode.Extended && orderItem.getParentDtlId()==null ){
					
					showItemEditExtendedWindow(PosTabOrderItemPriceDiscountEdit.TAB_CAPTION , orderItem);
				}
			}
			
		}
			
		PosOrderListItemControl posItemControl=null;
		if(orderItem.getItemType()==OrderDetailItemType.SALE_ITEM ){
			final int insertAt = (at >= 0) ? at : mPosOrderDetailItems.size();
			mPosOrderDetailItems.add(insertAt,orderItem);
		}

		if (mSelectedCustomerType != mDefCustomerType)
			PosSaleItemUtil.resetItemPrice4CustType(orderItem, mSelectedCustomerType);
		posItemControl = addOrderItemToGridList(at, orderItem);
		//		posItemControl.setKitchenPrintStatus(orderItem.isPrintedToKitchen());
		posItemControl.setSelected(true);
		setTotalPanel();
		if (mListner != null)
			mListner.onItemAdded(posItemControl);

		//		}else
		//			posItemControl=mPosGridItemsHashMap.get(orderItem.getId());

		//Add if any extra items
		if(orderItem.getExtraItemList()!=null && orderItem.getExtraItemList().size()>0){
			setExtraItems(orderItem);
		}

		if(orderItem.getComboSubstitutes()!=null && orderItem.getComboSubstitutes().size()>0){
			setComboSubstitutes(orderItem);
		}
		//		}

		orderItem.setAddedToOrder(true);
		return true;

	}
	
	/**
	 * Gets the weight from WM
	 * @return
	 */
	public double getWeightFromWeighingMachine(){
		
		return getWeightFromWeighingMachine(true);
	}
	
	/**
	 * Gets the weight from WM
	 * @return
	 */
	private double wmQuantity=-1;
	public double getWeightFromWeighingMachine(boolean showExternalQtyEntryWindow){
		
		boolean isCancelled=false;
		
		wmQuantity=0;

		if(PosDeviceManager.getInstance().hasWeighingMachine()){
			
			boolean retry=true;
			while(retry){

//				if(PosDeviceManager.getInstance().getWeighingMachine()!=null && PosDeviceManager.getInstance().getWeighingMachine().isDeviceInitialized()){
				if(PosDeviceManager.getInstance().getWeighingMachine()!=null){

					try {

						wmQuantity=PosDeviceManager.getInstance().getWeighingMachine().requestForWeight();

					} catch (Exception e) {

						PosFormUtil.showErrorMessageBox(mParent.getActiveWindow(), "Not able to get weight from weighing machine. Please check machine or contact administrator.");
						PosLog.write(this,"addOrderDetailItem", e);
					}
				}

				if(wmQuantity<=0){

//					retry=(PosFormUtil.showQuestionMessageBox(mParent.getActiveWindow(), 
//							MessageBoxButtonTypes.YesNoCancel, 
//							"No weight received from weighing machine. Do you want to retry?", null)==MessageBoxResults.Yes);
					
					switch (PosFormUtil.showQuestionMessageBox(mParent.getActiveWindow(), 
							MessageBoxButtonTypes.YesNoCancel, 
							"No weight received from weighing machine. Do you want to retry?", null)) {
					case Yes:
						retry=true;
						isCancelled=false;
						break;
					case No:
						retry=false;
						isCancelled=false;
						break;
					case Cancel:
						retry=false;
						isCancelled=true;
						break;
					default:
						break;
					
					};
					
					
				}else
					break;
			}
		}
		
		
		if(wmQuantity<=0 && !isCancelled){

			if (showExternalQtyEntryWindow)
				wmQuantity=getQtyFromKeyPadWindow();
			else
				wmQuantity=1;

		}
		
		return wmQuantity;
	}
	
	/**
	 * @return
	 */
	private double qtyEntered=0;
	private double getQtyFromKeyPadWindow(){
		return getQtyFromKeyPadWindow(true);
	}
	private double getQtyFromKeyPadWindow(boolean canCancel){
		
		qtyEntered=0;
		PosFormUtil.showNumKeyPad("Quantity",(JDialog)mParent.getActiveWindow(),null,new IPosNumKeyPadFormListner() {

			@Override
			public void onValueChanged(String value) {
				
				qtyEntered=Double.parseDouble(value);
			}

			@Override
			public void onValueChanged(JTextComponent target, String oldValue) {
				// TODO Auto-generated method stub

			}
		},0d,"A valid quantity should be entered.",null,null,1d,canCancel,KeypadTypes.Numeric);
		
		return qtyEntered;
	}
	/**
	 * @return
	 */
	private boolean canAddNewItem(){

		boolean isAllowed=true;

		if(!mParent.validateNewOrder()) 
			isAllowed=false;
		else if(mReadOnly)
			isAllowed=checkAuthenticationForBilledOrders();	

		return isAllowed;
	}

	/**
	 * @param saleItem
	 * @throws Exception
	 */
	public void addPosItem(BeanSaleItem saleItem) throws Exception {

		if (!canAddNewItem()) return;
		
		final PosOrderListItemControl itemCtrl=getGroupItemControlFromList(mPosGridItems, saleItem.getCode());
		if(itemCtrl!=null){
			
			boolean isConfimed=true;
			if(PosEnvSettings.getInstance().getUISetting().getOrderEntryUISettings().getOrderItemListPanelSetting().isConfirmDuplicateItems()){

				isConfimed=(PosFormUtil.showQuestionMessageBox(mParent.getActiveWindow(), MessageBoxButtonTypes.YesNo, "Item ["+saleItem.getName()+"] has been added already. "
						+  (PosEnvSettings.getInstance().getUISetting().getOrderEntryUISettings().getOrderItemListPanelSetting().isGroupSameItems()? 
								"Do you want to add the quantity to the existing item ?" 
								:"Do you want to add the item ?")
						
						, null)==MessageBoxResults.Yes);

			}

			if(isConfimed){

				if(PosEnvSettings.getInstance().getUISetting().getOrderEntryUISettings().getOrderItemListPanelSetting().isGroupSameItems()){

					final BeanSaleItem orgSaleItem= itemCtrl.getPosSaleItem();
					final double newQty=orgSaleItem.getQuantity() + (saleItem.isRequireWeighing()? getWeightFromWeighingMachine() : saleItem.getQuantity());
					itemCtrl.setSelected(true);
					onQuantityChanged(newQty);
					return;
				}

			}else
				return;

		}

		BeanOrderDetail detailItem =PosOrderUtil.createOrderDetailObject(mCurPosOrderItem,saleItem);
		BeanSaleItem item = detailItem.getSaleItem();
		setPromotionToItem(item);

		PosSaleItemUtil.resetSaleItemPriceForService(item, mParent.getSelectedServiceType());
		PosSaleItemUtil.resetSaleItemTax(detailItem, mParent.getSelectedServiceType());
		
		if(saleItem.isOpen() || saleItem.getFixedPrice()==0)
			detailItem.setServingTable(mParent.getSelectedServingTable());
		else
			PosOrderUtil.setServiceTable(detailItem, mParent.getSelectedServingTable());

		detailItem.setServedBy(mParent.getSelectedWaiter());
		detailItem.setServiceType(mParent.getSelectedServiceType());
		

		if(detailItem.getSaleItem().isComboItem())
			setDefaultComboSubstitutions(detailItem);
		
		if(addOrderDetailItem(detailItem)){

			final BeanUIOrderEntrySetting orderEntrySettings=PosEnvSettings.getInstance().getUISetting().getOrderEntryUISettings();
			
			final boolean showItemDetEditScreen=PosOrderUtil.canShowItemEditUI(detailItem);
			
//			final boolean showItemDetEditScreen= (detailItem.getSaleItem().isComboItem() ||
//					detailItem.hasSubItems() || detailItem.getSaleItem().hasChoices() ||
//					(detailItem.getSaleItem().getAtrributeList()!=null 
//					&& detailItem.getSaleItem().getAtrributeList().size()>0));
			
			final boolean isOpenItem=detailItem.getSaleItem().isOpen() && 
					 PosEnvSettings.getInstance().getUISetting().getOrderEntryUISettings().canShowEditWindowForOpenItemsOnAdd();
		
			if(isOpenItem && orderEntrySettings.getQuickEditMode()!=ItemEditMode.Extended)
				onEditItem(PosTabOrderItemPriceDiscountEdit.TAB_CAPTION); 
			else if(showItemDetEditScreen){
				
				 if(detailItem.getSaleItem().isComboItem())
						onEditItem(PosTabOrderItemComboContentEdit.TAB_CAPTION);
				 else if(mSelectedListItemControl.getPosSaleItem().hasChoices())
					onEditItem(PosTabOrderItemExtrasEdit.TAB_CAPTION);
				  else if(detailItem.getSaleItem().getAtrributeList()!=null 
					&& detailItem.getSaleItem().getAtrributeList().size()>0)
					  onEditItem(PosTabOrderItemAttributeEdit.TAB_CAPTON);
				  else
					onEditItem(PosTabOrderItemPriceDiscountEdit.TAB_CAPTION);
			} 
				
		}
		
	}

	/**
	 * @param orderItem
	 * @throws Exception
	 */
	private void setDefaultComboSubstitutions(BeanOrderDetail orderItem) throws Exception {
		//Extract the default selected items
		try {
			ArrayList<BeanSaleItemComboContent> ccItems = PosSaleItemComboContentProvider.getInstance().getComboContentList(orderItem.getSaleItem().getId());
			HashMap<String,ArrayList<BeanOrderDetail>> selectedItemsListMap=new HashMap<String, ArrayList<BeanOrderDetail>>();
			for(BeanSaleItemComboContent ccItem:ccItems){
				if(ccItem.getContentItems()!=null){
					ArrayList<BeanOrderDetail> list=new ArrayList<BeanOrderDetail>();
					for(BeanSaleItemComboContentSubstitute ccsItem:ccItem.getContentItems()){
						if(ccsItem.isDefault()){
							/**
							 * If the quantity is more than one then add more detail item
							 * This is to flatten the items. ie keep independent order sale items.
							 */
							for(int cnt=0; cnt<ccsItem.getQuantity();cnt++){
								BeanOrderDetail dtlItem=PosOrderUtil.createOrderDetailItemForCombo(orderItem, ccItem, ccsItem);
								list.add(dtlItem);
							}
						}
					}
					selectedItemsListMap.put(ccItem.getCode(), list);
				}
			}
			orderItem.setComboSubstitutes(selectedItemsListMap);

		} catch (SQLException e) {
			PosLog.write(this, "setDefaultComboSubstitutions", e);
			throw e;
		}

	}


	/**
	 * @param saleItem
	 */
	private void setPromotionToItem(BeanSaleItem saleItem){
		
		try {
			
			PosSaleItemUtil.setPromotionToItem(saleItem);
			PosTaxUtil.calculateTax(saleItem);
		} catch (Exception e) {
			PosFormUtil.showErrorMessageBox(mParent,"Failed to load promotion. Please contact administrator");
		}
	}

	/**
	 * @param saleItem
	 */
	private void sendSaleItemToPoleDisplay(BeanSaleItem saleItem) {
		mPosPoleDisplay.disPlayItemDtl(saleItem);
	}

	/**
	 * @param item
	 * @return
	 */
	private PosOrderListItemControl createOrderListControlFromSaleItem(
			BeanSaleItem item) {
		PosOrderListItemControl posItemControl = null;
		posItemControl = new PosOrderListItemControl(mHighlighteDiscounted);
		return posItemControl;
	}

	/**
	 * @param posItemControl
	 */
	private void setItemSerialNo(PosOrderListItemControl posItemControl) {
		
		posItemControl.setItemSerialNumber(posItemControl.getItemIndex());
	}

	/**
	 * @param at
	 * @param item
	 * @return
	 */
	private PosOrderListItemControl addOrderItemToGridList(int at,
			BeanOrderDetail item) {
		
		PosOrderListItemControl posItemControl = createOrderListControlFromSaleItem(item
				.getSaleItem());
		int totalItems = (at >= 0) ? at : mItemListPanel.getComponentCount();
		posItemControl.setItemIndex(++totalItems);
		posItemControl.setReadOnly(mReadOnly);
		posItemControl.setOrderDetailItem(item);
		setItemSerialNo(posItemControl);
		addOrderListControlToGrid(at, posItemControl);
		if(item.isNewItem() || !item.isVoid() )
			setBillVars(item.getSaleItem(), true);
		return posItemControl;
	}


	/**
	 * @param at
	 * @param posItemControl
	 */
	private void addOrderListControlToGrid(int at,
			PosOrderListItemControl posItemControl) {
		final int insertAt = (at >= 0) ? at : mItemListPanel
				.getComponentCount();
		posItemControl.setListner(gridItemListner);
		mPosGridItems.add(insertAt, posItemControl);
		mPosGridItemsCtrlIDHashMap.put(posItemControl.getOrderDetailItem().getId(), posItemControl);
		mItemListPanel.add(posItemControl, insertAt);
		final int height = (mPosGridItems.size())
				* (PosOrderListItemControl.LAYOUT_HEIGHT);
		mItemListPanel.setSize(mItemListPanel.getWidth(), height);
	}

	/**
	 * @param index
	 */
	private void selectItem(int index) {
		
		if(mPosGridItems!=null && mPosGridItems.size()>0)
			mPosGridItems.get(index).setSelected(true);
	}

	/**
	 * 
	 */
	private void setTotalPanel() {
		
		setTotalPanel(true, true);
	}

	/**
	 * @param updatePDItem
	 * @param updatePDTotal
	 */
	private void setTotalPanel(boolean updatePDItem, boolean updatePDTotal) {

		mLabelTotalCountValue.setText(PosUomUtil.format(
				mBillItemCount,PosUOMProvider.getInstance().getMaxDecUom()));
		mLabelTotalAmount.setText(PosCurrencyUtil.format(mBillGrandTotalAmount));
		mLabelTotalDiscountValue.setText(PosCurrencyUtil.format(mBillDiscountAmount));
		mLabelTotalTaxValue.setText(PosCurrencyUtil.format(mBillTaxAmount));
		validateComponent();
		updatePoleDisplay(updatePDItem, updatePDTotal);
	}

	/**
	 * @param updatePDItem
	 * @param updatePDTotal
	 */
	private void updatePoleDisplay(boolean updatePDItem, boolean updatePDTotal) {
		
		if (updatePDItem && mSelectedListItemControl != null&& mSelectedListItemControl.getOrderDetailItem().getItemType()==OrderDetailItemType.SALE_ITEM )
			sendSaleItemToPoleDisplay(mSelectedListItemControl.getPosSaleItem());
		if (updatePDTotal)
			mPosPoleDisplay.disPlayBillTotal(mBillGrandTotalAmount);
	}

	/**
	 * 
	 */
	private void adjustBounds() {
		
		adjustBounds(false);
	}

	/**
	 * @param arrangeOnTop
	 */
	private void adjustBounds(Boolean arrangeOnTop) {
		
		adjustBounds(mSelectedListItemControl, arrangeOnTop);
	}

	/**
	 * @param posItem
	 * @param arrangeOnTop
	 */
	private void adjustBounds(PosOrderListItemControl posItem,
			Boolean arrangeOnTop) {
		
		final int itemIndex = posItem.getItemIndex() - 1;
		final int itemTop = itemIndex * (PosOrderListItemControl.LAYOUT_HEIGHT);
		final int viewPortHeight = mItemListPanelContainer.getHeight();
		final int containerTop = mItemListPanel.getY();
		final int itemCurTop = itemTop + containerTop;
		int diff = 0;
		if (arrangeOnTop) {
			diff = mSelectedListItemControl.getY() + containerTop;
			mItemListPanel.setLocation(mItemListPanel.getX(), containerTop
					- diff);
		} else {
			if(mPosGridItems.size()<=mItemsPerPage){
				mItemListPanel.setLocation(mItemListPanel.getX(), 0);
			}else 

				if (itemCurTop + PosOrderListItemControl.LAYOUT_HEIGHT> viewPortHeight) {
					diff = (itemCurTop + PosOrderListItemControl.LAYOUT_HEIGHT)
							- viewPortHeight;
					//				mItemListPanel.setLocation(mItemListPanel.getX(), containerTop
					//						- diff + 1 - viewPortHeight
					//						+ PosOrderListItemControl.LAYOUT_HEIGHT);

					mItemListPanel.setLocation(mItemListPanel.getX(), containerTop
							- diff + 1 );


				} else if (itemCurTop < 0) {
					diff = -itemCurTop;
					mItemListPanel.setLocation(mItemListPanel.getX(), containerTop
							+ diff);
				}
		}
		setDisplayingItemsCount();
	}

	/**
	 * 
	 */
	public void resetGrid() {
		
		mSelectedCustomerType = mDefCustomerType;
		mPosGridItems.clear();
		mPosGridItemsCtrlIDHashMap.clear();
		mPosOrderDetailItems=null;
		mItemListPanel.removeAll();
		mItemListPanel.setSize(mItemListPanel.getWidth(), 0);
		mItemListPanel.revalidate();
		mItemListPanel.repaint();
		mSelectedItemIndex = 0;
		mSelectedListItemControl = null;
		resetBillVars();
		setTotalPanel(false, false);
		setDisplayingItemsCount();
	}

	/**
	 * @param itemGridControl
	 */
	public void setItemGridControlPanel(PosOrderListOptionPanel itemGridControl) {
		
		this.mitemGridControl = itemGridControl;
		mitemGridControl.setOrderListPanel(this);
	}

	/**
	 * @return
	 */
	private boolean canDoScroll(){
		//		return ( mPosGridItems!=null && mPosGridItems.size()>mItemsPerPage);
		return ( mPosGridItems!=null && mPosGridItems.size()>0);
	}

	@Override
	public void onUpClicked() {

		if(!canDoScroll()) return ;
		mSelectedItemIndex -= mItemsPerPage;
		mSelectedItemIndex = (mSelectedItemIndex < 0) ? 0 : mSelectedItemIndex;
		selectItem(mSelectedItemIndex);
		adjustBounds(true);

	}

	@Override
	public void onDownClicked() {

		if(!canDoScroll()) return ;
		mSelectedItemIndex += mItemsPerPage;
		mSelectedItemIndex = (mSelectedItemIndex >= mItemListPanel
				.getComponentCount()) ? mItemListPanel.getComponentCount() - 1
						: mSelectedItemIndex;
				selectItem(mSelectedItemIndex);
				adjustBounds(true);
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.pos.forms.components.orderentry.listners.IPosItemGridOptionPanelListner#onNexClicked()
	 */
	@Override
	public void onNexClicked() {

		if(!canDoScroll()) return ;
		mSelectedItemIndex += 1;
		mSelectedItemIndex = (mSelectedItemIndex >= mItemListPanel
				.getComponentCount()) ? mItemListPanel.getComponentCount() - 1
						: mSelectedItemIndex;
				selectItem(mSelectedItemIndex);
				//				adjustBounds(true);
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.pos.forms.components.orderentry.listners.IPosItemGridOptionPanelListner#onPreviousClicked()
	 */
	@Override
	public void onPreviousClicked() {

		if(!canDoScroll()) return ;
		mSelectedItemIndex -= 1;
		mSelectedItemIndex = (mSelectedItemIndex < 0) ? 0 : mSelectedItemIndex;
		selectItem(mSelectedItemIndex);
		//		adjustBounds(true);

	}

	@Override
	public void onAddClicked() {

//		final PosItemExtSearchForm  serachForm=new PosItemExtSearchForm(PosOrderEntryForm.getInstance().getAvailablePositems());
		final PosExtSearchForm  serachForm=new PosExtSearchForm(PosOrderEntryForm.getInstance().getAvailablePositems());
		serachForm.setSorting(1, SortOrder.ASCENDING);
		serachForm.setCloseOnAccept(false);
		serachForm.setListner(new PosItemExtSearchFormAdapter() {

			@Override
			public boolean onAccepted(Object sender,IPosSearchableItem item) {
				try {

					//					addPosItem(String.valueOf(item.getItemCode()));
					BeanSaleItem saleItem=(BeanSaleItem)item;
					if(saleItem.isGroupItem())
						mParent.showGroupItemObjectBrowser(serachForm,(BeanSaleItem)item);
					else
						addPosItem(String.valueOf(item.getItemCode()));
//						addPosItem(saleItem);
						

					return true;
				} catch (Exception e) {

					PosLog.write(this, "onAddClicked:onAccepted", e);
					PosFormUtil.showErrorMessageBox(sender, "Failed to add item. Please contact Administrator.");
					return false;
				}

			}
			/* (non-Javadoc)
			 * @see com.indocosmo.pos.forms.search.listener.adapter.PosItemExtSearchFormAdapter#onDetailClicked(java.lang.Object, com.indocosmo.pos.forms.components.search.IPosSearchableItem)
			 */
			@Override
			public void onDetailClicked(Object sender,
					IPosSearchableItem item) {
				BeanSaleItem saleItem=(BeanSaleItem)item;
				onEditClicked();
			}
		});

		mParent.setActiveWindow(serachForm);
		PosFormUtil.showLightBoxModal(mParent, serachForm);
	}

	@Override
	public void onEditClicked() {

		/**
		 * If modifiers exist set the modifier tab as default
		 */
		onEditItem(
				((mSelectedListItemControl != null 
				&& mSelectedListItemControl.getOrderDetailItem().getSaleItem().getAtrributeList()!=null 
				&& mSelectedListItemControl.getOrderDetailItem().getSaleItem().getAtrributeList().size()>0)?
						PosTabOrderItemAttributeEdit.TAB_CAPTON:
							null)  
				);
	}

	/**
	 * @param tabCaption
	 */
	private void onEditItem(String tabCaption)  {

		 
		if (mSelectedListItemControl == null)
			return;

		//		if(!PosAccessPermissionsUtil.validateAccess(mParent, 
		//				PosEnvSettings.getInstance().getCashierShiftInfo().getCashierInfo().getUserGroupId(), "edit_item"))
		//			return;

		try {
			
			PosOrderListItemEditForm posItemEditForm;
			mSelectedListItemControl.getOrderDetailItem().setKitchenDirty(false);
			posItemEditForm = new PosOrderListItemEditForm(
					mSelectedListItemControl.getOrderDetailItem());
			
			posItemEditForm.setOnItemEditedListner(onItemEditedListner);
			//			if(tabCaption==null)
			//				posItemEditForm.setOkEnabled(!mReadOnly);
			if(!mSelectedListItemControl.getOrderDetailItem().isNewItem() && !checkAuthenticationForParkedOrders() )
				posItemEditForm.setReadOnly(true);
			else if(mReadOnly && !checkAuthenticationForBilledOrders())
				posItemEditForm.setReadOnly(true);	
			else
				posItemEditForm.setReadOnly(mReadOnly || mSelectedListItemControl.isReadOnly() || mSelectedListItemControl.getOrderDetailItem().hasSplits());
			
			posItemEditForm.setSelectedTab(tabCaption);
			PosFormUtil.showLightBoxModal(mParent, posItemEditForm);
			
			
		} catch (Exception e) {
			PosLog.write(this, "onEditITem", e);
			PosFormUtil.showSystemError(mParent);
		}

	}
 
	
	private void showItemEditExtendedWindow(String tabCaption,BeanOrderDetail orderDtl){
		try{
			
			PosOrderItemQuickEditForm posItemEditForm;
			posItemEditForm = new PosOrderItemQuickEditForm(orderDtl);
			
//			posItemEditForm.setOnItemEditedListner(onItemEditedListner);
			//			if(tabCaption==null)
			//				posItemEditForm.setOkEnabled(!mReadOnly);
			 if(mReadOnly && !checkAuthenticationForBilledOrders())
				posItemEditForm.setReadOnly(true);	
		 	posItemEditForm.setSelectedTab(tabCaption);
			PosFormUtil.showLightBoxModal(mParent, posItemEditForm);
		
		} catch (Exception e) {
			PosLog.write(this, "onEditITem", e);
			PosFormUtil.showSystemError(mParent);
		}
	}

	/**
	 * @param parentOrderItem
	 */
	private void setExtraItems(BeanOrderDetail parentOrderItem){
		
		HashMap<String, ArrayList<BeanOrderDetail>> itemList=parentOrderItem.getExtraItemList();
		int curIndx=mPosGridItemsCtrlIDHashMap.get(parentOrderItem.getId()).getItemIndex();
		mPosGridItemsCtrlIDHashMap.get(parentOrderItem.getId()).setSelected(false);
		for(String key:itemList.keySet()){
			for(BeanOrderDetail item:itemList.get(key)){
				if(addOrderDetailItem(curIndx,item))
					curIndx++;
			}
		}
		resetListLayout(curIndx);
		mPosGridItemsCtrlIDHashMap.get(parentOrderItem.getId()).setSelected(true);
	}

	/**
	 * @param parentOrderItem
	 * @param filterVoidItems
	 * @param removeControls
	 */
	private void deleteExtras(BeanOrderDetail parentOrderItem, boolean filterVoidItems,boolean removeControls){
		

		HashMap<String, ArrayList<BeanOrderDetail>> itemList=parentOrderItem.getExtraItemList();
		mPosGridItemsCtrlIDHashMap.get(parentOrderItem.getId()).setSelected(false);
		for(String key:itemList.keySet()){
			for(BeanOrderDetail Item:itemList.get(key)){

				deleteOrderDetailItem(Item,false,!(filterVoidItems && Item.isVoid()),removeControls);
			}
		}
		mPosGridItemsCtrlIDHashMap.get(parentOrderItem.getId()).setSelected(false);
	}

	/**
	 * @param parentOrderItem
	 */
	private void setComboSubstitutes(BeanOrderDetail parentOrderItem){

		HashMap<String, ArrayList<BeanOrderDetail>> itemList=parentOrderItem.getComboSubstitutes();
		int curIndx=mPosGridItemsCtrlIDHashMap.get(parentOrderItem.getId()).getItemIndex();
		mPosGridItemsCtrlIDHashMap.get(parentOrderItem.getId()).setSelected(false);
		for(String key:itemList.keySet()){
			for(BeanOrderDetail item:itemList.get(key)){
				if(addOrderDetailItem(curIndx,item))
					curIndx++;
			}
		}
		resetListLayout(curIndx);
		mPosGridItemsCtrlIDHashMap.get(parentOrderItem.getId()).setSelected(true);
	}

	/**
	 * @param parentOrderItem
	 */
	private void deleteComboSubstitutes(BeanOrderDetail parentOrderItem){

		HashMap<String, ArrayList<BeanOrderDetail>> itemList=parentOrderItem.getComboSubstitutes();
		mPosGridItemsCtrlIDHashMap.get(parentOrderItem.getId()).setSelected(false);
		for(String key:itemList.keySet()){
			for(BeanOrderDetail Item:itemList.get(key)){
				deleteOrderDetailItem(Item);
			}
		}
		mPosGridItemsCtrlIDHashMap.get(parentOrderItem.getId()).setSelected(false);
	}
	/**
	 * When the main combo item's quantity or discount is changed,
	 * it should applied to combo contents too.
	 * */
	private void updateComboContentsQuantiyAndDiscount(BeanOrderDetail orderItem){

		final double qty=orderItem.getSaleItem().getQuantity();
		final BeanDiscount discount=orderItem.getSaleItem().getDiscount();
		if(orderItem.getComboSubstitutes()!=null && orderItem.getComboSubstitutes().size()>0){

			for(String key:orderItem.getComboSubstitutes().keySet()){

				for(BeanOrderDetail item:orderItem.getComboSubstitutes().get(key)){

					if(item.getSaleItem().getQuantity()!=qty){

						item.getSaleItem().setQuantity(qty);
						item.setDirty(true);
					}

					if(discount.isPercentage() && item.getSaleItem().getFixedPrice()>0){

						item.getSaleItem().setDiscount(discount);
						item.setDirty(true);
					}else if(!item.getSaleItem().getDiscount().getCode().equals(discountProvider.getNoneDiscount().getCode())){

						item.getSaleItem().setDiscount(discountProvider.getNoneDiscount());
						item.setDirty(true);
					}

					if(item.isDirty()){

						PosTaxUtil.calculateTax(item.getSaleItem());
						updateDirtyItem(item);
					}
				}
			}
		}
	}

	/**
	 * 
	 */
	private IPosItemEditFormListner onItemEditedListner = new PosItemEditFormAdapter() {
		@Override
		public void onItemEdited(BeanOrderDetail newItem,
				BeanOrderDetail oldItem) {

			if(!newItem.isDirty()) return;

			updateDirtyItem(newItem);

			/**
			 * Update the arrays with new item.
			 * */
			updateOrderItemArrays(newItem);

			/**
			 * Remove the old extra items
			 * */
			if(oldItem.isExtraItemsSelected())
				deleteExtras(oldItem,true,true);
			/**
			 * Add the news extra items
			 * */
			if(newItem.isExtraItemsSelected())
				setExtraItems(newItem);


			/**
			 * remove old combocontents
			 * */
			if(oldItem.isComboContentsSelected()){
				deleteComboSubstitutes(oldItem);
			}
			/**
			 * Add the new combo contents 
			 * */
			if(newItem.isComboContentsSelected()){
				//				if(newItem.getSaleItem().getQuantity()!=oldItem.getSaleItem().getQuantity())
				updateComboContentsQuantiyAndDiscount(newItem);
				setComboSubstitutes(newItem);

			}
			/**
			 * Update the bills vars with newitem.
			 */
			setBillVars(oldItem.getSaleItem(), false);
			setBillVars(newItem.getSaleItem(), true);
			setTotalPanel();
			/**
			 * SPLIT TO CHECK NEEDED?

			if(mParent.IsPartiallyPaidOrder()){
				mParent.resetBillDiscount();
			}
			 */
			if (mListner != null)
				mListner.onItemEdited(mSelectedListItemControl);
			mSelectedListItemControl.onEdited();
		}



	};
	
	

//	/**
//	 * 
//	 */
//	private IPosItemEditFormListner onItemQuickEditedListner = new PosItemEditFormAdapter() {
//		@Override
//		public void onItemEdited(BeanOrderDetail newItem,
//				BeanOrderDetail oldItem) {
//
//			if(!newItem.isDirty()) return;
//
//			updateDirtyItem(newItem);
//
//			/**
//			 * Update the bills vars with newitem.
//			 */
//			setBillVars(oldItem.getSaleItem(), false);
//			setBillVars(newItem.getSaleItem(), true);
//			setTotalPanel();
//			
//			
//			if (mListner != null)
//				mListner.onItemEdited(mSelectedListItemControl);
//			mSelectedListItemControl.onEdited();
//		}
//
//
//
//	};
	
	/**
	 * When the item is edited the item becomes dirty.
	 * So update the printing and other properties here and reset to isDirty to false
	 * */
	private void updateDirtyItem(BeanOrderDetail item) {

		if(!item.isDirty()) return;

		if(item.isKitchenDirty())
			item.setPrintedToKitchen(false);
		PosOrderListItemControl itemCtrl= mPosGridItemsCtrlIDHashMap.get(item.getId());
		if(itemCtrl!=null)
			itemCtrl.refresh();

		/**
		 * Set parent items kitchen print status true if the sub item's print status is
		 * */
		PosOrderListItemControl parentitem=getParentControl(item);
		if(parentitem!=null){
			parentitem.getOrderDetailItem().setPrintedToKitchen(false);
			parentitem.refresh();
		}

		/**
		 * reset the dirty property to false
		 * */
		item.setDirty(false);

	}

	/**
	 * @param withItem
	 */
	private void updateOrderItemArrays(BeanOrderDetail withItem){

		ArrayList<BeanOrderDetail> itemListToUpdate=null;
		mSelectedListItemControl.setOrderDetailItem(withItem);

		if(withItem.getItemType()==OrderDetailItemType.SALE_ITEM){
			itemListToUpdate=mPosOrderDetailItems;
		}else {

			BeanOrderDetail parentOrderITem=getParentOrderDetailItem(withItem);

			if(withItem.getItemType()==OrderDetailItemType.EXTRA_ITEM)
				itemListToUpdate=parentOrderITem.getExtraItemList().get(withItem.getSaleItemChoice().getChoice().getCode());
			else if(withItem.getItemType()==OrderDetailItemType.COMBO_CONTENT_ITEM)
				itemListToUpdate=parentOrderITem.getComboSubstitutes().get(withItem.getComboContentItem().getCode());

		}

		updateOrderItemArrayListContent(itemListToUpdate,withItem);
	}

	/**
	 * @param orderItem
	 * @return
	 */
	private PosOrderListItemControl getParentControl(BeanOrderDetail orderItem){
		
		PosOrderListItemControl pCtrl=null;
		if(orderItem.getParentDtlId()!=null)
			pCtrl=mPosGridItemsCtrlIDHashMap.get(orderItem.getParentDtlId());
		return pCtrl;
	}

	/**
	 * @param orderItem
	 * @return
	 */
	private BeanOrderDetail getParentOrderDetailItem(BeanOrderDetail orderItem){
		
		BeanOrderDetail pItem=null;
		PosOrderListItemControl pCtrl=getParentControl(orderItem);
		if(pCtrl!=null)
			pItem=pCtrl.getOrderDetailItem();
		return pItem;
	}

	/**
	 * @param itemList
	 * @param withItem
	 */
	private void updateOrderItemArrayListContent(ArrayList<BeanOrderDetail> itemList,BeanOrderDetail withItem){
		
		if(itemList!=null){
			for(int index=0;index<itemList.size();index++){
				if(itemList.get(index).getId()==withItem.getId()){
					itemList.set(index, withItem);
					break;
				}
			}
		}
	}

	/**
	 * @return
	 */
	private boolean canDeleteItem(){

		boolean isAllowed=true;

		if(mSelectedListItemControl == null) 
			isAllowed=false;
		else if(mSelectedListItemControl.isVoid()) 
			isAllowed=false;
		else if(mReadOnly)
			isAllowed=checkAuthenticationForBilledOrders();		
		else if (mSelectedListItemControl.getOrderDetailItem().getParentDtlId()!=null ){

			PosFormUtil.showInformationMessageBox(mParent, "You can not delete this item. Edit the parent item and remove this item from list.");
			isAllowed=false;

		}else if(mSelectedListItemControl.getOrderDetailItem().hasSplits()){
			PosFormUtil.showInformationMessageBox(mParent, "There is partial payment for this item. Use refund option.");
			isAllowed=false;
		}else if (!mSelectedListItemControl.getOrderDetailItem().isNewItem())
			isAllowed=checkAuthenticationForParkedOrders();		
		

		return isAllowed;
	}


	@Override
	public void onDeleteClicked() {

		if(!canDeleteItem())
			return;

		String msg="";
		if(mParent.IsPartiallyPaidOrder()){
			msg="Partial payment has been received, ";
		}else if(mParent.IsParked()&&mSelectedListItemControl.isKitchenPrinted()){
			msg="Receipt is printed or the Item has been printed to Kitchen, ";
		}

		if(PosEnvSettings.getInstance().getUISetting().getOrderEntryUISettings().getOrderItemListPanelSetting().isConfirmItemDeletionRequired()){

			PosFormUtil.showQuestionMessageBox(
					mParent,
					MessageBoxButtonTypes.YesNo,msg+
					"Do you want to delete the item ["
					+ mSelectedListItemControl.getItemName() + "].",
					new PosMessageBoxFormListnerAdapter() {
						@Override
						public void onYesButtonPressed() {
							BeanOrderDetail item=mSelectedListItemControl.getOrderDetailItem();
							deleteOrderDetailItem(item);

							//						if(mPosOrderDetailItems.contains(item))
							//							setOrderItemDirty(mSelectedListItemControl.getOrderDetailItem(),true);
						}
					});
		}else{

			BeanOrderDetail item=mSelectedListItemControl.getOrderDetailItem();
			deleteOrderDetailItem(item);
		}

	}


	/**
	 * @param orderDetailItem
	 */
	private void deleteOrderDetailItem(BeanOrderDetail orderDetailItem){
		
		deleteOrderDetailItem(orderDetailItem,false);
	}

	/**
	 * @param orderDetailItem
	 * @param childrenOnly
	 */
	private void deleteOrderDetailItem(BeanOrderDetail orderDetailItem,boolean childrenOnly){
		

		deleteOrderDetailItem(orderDetailItem,childrenOnly,true,true);
	}


	/**
	 * @param orderDetailItem
	 * @param childrenOnly
	 * @param updateAmount
	 * @param removeControls
	 */
	private void deleteOrderDetailItem(BeanOrderDetail orderDetailItem,boolean childrenOnly,boolean updateAmount,boolean removeControls){

		//Check for extra items
		if(orderDetailItem.isExtraItemsSelected()){
			deleteExtras(orderDetailItem,true,orderDetailItem.isNewItem());

		}

		//Check for combocontent items
		if(orderDetailItem.isComboContentsSelected()){
			deleteComboSubstitutes(orderDetailItem);
		}

		if(childrenOnly) {
			mPosGridItemsCtrlIDHashMap.get(orderDetailItem.getId()).setSelected(true);
			return;
		}

		if (orderDetailItem.isNewItem()){
			mPosOrderDetailItems.remove(orderDetailItem);
		}else{
			orderDetailItem.setVoid(true);
			orderDetailItem.setPrintedToKitchen(false);
			orderDetailItem.setVoidBy(PosEnvSettings.getInstance().getCashierShiftInfo()
					.getCashierInfo());
			orderDetailItem.setVoidAt(PosEnvSettings.getInstance().getPosDate());
			orderDetailItem.setVoidTime(PosDateUtil.getDateTime());
			
		}
		/**
		 * SPLIT TO CHECK NEEDED?

		if(mParent.IsPartiallyPaidOrder()){
			mParent.resetBillDiscount();
		}
		 */
		if (PosDeviceManager.getInstance().hasPoleDisplay())
			mPosPoleDisplay.displayMessage(PosStringUtil
					.truncateString(orderDetailItem
							.getSaleItem().getName(),
							mPosPoleDisplay.getLineLength() - 9)
							+ " removed.");
		if(updateAmount)
			setBillVars(orderDetailItem.getSaleItem(), false);

		PosOrderListItemControl listItemControl=mPosGridItemsCtrlIDHashMap.get(orderDetailItem.getId());
		if(listItemControl!=null ){

			BeanOrderDetail dtlItem=listItemControl.getOrderDetailItem();
			if(removeControls){
				if(dtlItem.isNewItem() || (dtlItem.getParentDtlId()!=null && dtlItem.getParentDtlId().trim().length()>0)){

					deleteOrderListControl(listItemControl);
					resetListLayout(listItemControl.getItemIndex()-1);
				}
			}

			listItemControl.refresh();

		}

		setTotalPanel(false, true);
		if (mListner != null)
			mListner.onItemDeleted(listItemControl);
	}

	/**
	 * @param ctrl
	 */
	private void deleteOrderListControl(PosOrderListItemControl ctrl) {
		
		mItemListPanel.remove(ctrl);
		mPosGridItems.remove(ctrl);
		mPosGridItemsCtrlIDHashMap.remove(ctrl.getOrderDetailItem().getId());
		
	}

	/**
	 * @param item
	 * @param isAdding
	 */
	private void setBillVars(BeanSaleItem item, Boolean isAdding){
		
		setBillVars( item,  isAdding, false);
	}
	/**
	 * @param item
	 * @param isAdding
	 * @param updateSubItems
	 */
	private void resetBillVars() {
		
		mBillItemCount = 0;
		mBillDiscountAmount = 0;
		mBillGrandTotalAmount = 0;
		mBillItemTotalAmount = 0;
		mBillTaxAmount = 0;
		mBillTax1Amount = 0;
		mBillTax2Amount = 0;
		mBillTax3Amount = 0;
		mBillGSTAmount = 0;
		mBillServiceTaxAmount = 0;
	}

	/**
	 * @param item
	 * @param isAdding
	 * @param updateSubItems
	 */
	private void setBillVars(BeanSaleItem item, Boolean isAdding, boolean updateSubItems) {
		
		mBillGrandTotalAmount += PosCurrencyUtil.roundTo(PosSaleItemUtil.getGrandTotal(item)) * (isAdding ? 1 : -1) ;
		
		mBillItemTotalAmount += PosCurrencyUtil.roundTo(PosSaleItemUtil.getTotalItemPrice(item)) * (isAdding ? 1 : -1)+
				(item.getTaxCalculationMethod()==TaxCalculationMethod.InclusiveOfTax?0:
					PosCurrencyUtil.roundTo(PosSaleItemUtil.getTotalTaxAmount(item,true)) * (isAdding ? 1 : -1))	;
		
		mBillDiscountAmount += PosCurrencyUtil.roundTo(PosSaleItemUtil.getTotalDiscountAmount(item)) * (isAdding ? 1 : -1);

		mBillTaxAmount += PosCurrencyUtil.roundTo(PosSaleItemUtil.getTotalTaxAmount(item,true)) * (isAdding ? 1 : -1);
		mBillItemCount += PosUomUtil.roundTo(PosSaleItemUtil.getItemQuantity(item),item.getUom()) * (isAdding ? 1 : -1);
		mBillTax1Amount += PosCurrencyUtil.roundTo(PosSaleItemUtil.getTotalT1Amount(item)) * (isAdding ? 1 : -1);
		mBillTax2Amount +=  PosCurrencyUtil.roundTo(PosSaleItemUtil.getTotalT2Amount(item)) * (isAdding ? 1 : -1);
		mBillTax3Amount += PosCurrencyUtil.roundTo (PosSaleItemUtil.getTotalT3Amount(item)) * (isAdding ? 1 : -1);
		mBillGSTAmount +=  PosCurrencyUtil.roundTo(PosSaleItemUtil.getTotalGSTAmount(item)) * (isAdding ? 1 : -1);
		mBillServiceTaxAmount += PosCurrencyUtil.roundTo(PosSaleItemUtil.getTotalServiceTaxAmount(item))
				* (isAdding ? 1 : -1);

		
		mBillGrandTotalAmount =  PosCurrencyUtil.roundTo(mBillGrandTotalAmount);
		mBillTaxAmount =  PosCurrencyUtil.roundTo(mBillTaxAmount);
//		mBillTax1Amount =  PosCurrencyUtil.roundTo(mBillTax1Amount);
//		mBillTax2Amount =  PosCurrencyUtil.roundTo(mBillTax2Amount);
//		mBillTax3Amount =  PosCurrencyUtil.roundTo(mBillTax3Amount);
//		mBillGSTAmount =  PosCurrencyUtil.roundTo(mBillGSTAmount);
//		mBillServiceTaxAmount =  PosCurrencyUtil.roundTo(mBillServiceTaxAmount);
		if(updateSubItems){

			/**
			 * If the item is combo,
			 * then reset the sub items too.
			 * */

			if(mSelectedListItemControl.getPosSaleItem().isComboItem()){
				final HashMap<String, ArrayList<BeanOrderDetail>> ccItems= mSelectedListItemControl.getOrderDetailItem().getComboSubstitutes();
				if (ccItems!=null){
					for(String key:ccItems.keySet()){
						for(BeanOrderDetail ccItem:ccItems.get(key)){
							setBillVars(ccItem.getSaleItem(), isAdding);
						}
					}
				}
			}
		}

		PosOrderUtil.updateOrderHdrFromGrid(mCurPosOrderItem, this);

	}

	/**
	 * @param from
	 */
	private void resetListLayout(int from) {
		
		//		int itemCount = mItemListPanel.getComponentCount();
		int itemCount = mPosGridItems.size();
		PosOrderListItemControl item=null;

		if (from < itemCount) {
			for (int index = from; index < itemCount; index++) {
				item = mPosGridItems.get(index);
				item.setItemIndex(index + 1);
				setItemSerialNo(item);
			}
		}

		from += (from >= itemCount) ? -1 : 0;
		item = (from >= 0 && from < itemCount) ? mPosGridItems
				.get(from) : null;
				//				final int height = (mItemListPanel.getComponentCount())
				//						* (PosOrderListItemControl.LAYOUT_HEIGHT + 1);
				final int height = (itemCount)
						* (PosOrderListItemControl.LAYOUT_HEIGHT);
				from = -1;

				if (item != null)
					item.setSelected(true);
				else
					mSelectedListItemControl=null;

				mItemListPanel.setSize(mItemListPanel.getWidth(), height);
				mItemListPanel.revalidate();
				mItemListPanel.repaint();
				setDisplayingItemsCount();

	}

	/**
	 * @return the mBillGrandTotalAmount
	 */
	public double getBillTotal() {
		return mBillGrandTotalAmount;
	}

	/**
	 * @return the mBillItemTotalAmount
	 */
	public double getBillItemTotal() {
		return mBillItemTotalAmount;
	}

	/**
	 * @return the mBillDiscountAmount
	 */
	public double getBillDiscount() {
		return mBillDiscountAmount;
	}

	/**
	 * @return the mBillTax
	 */
	public double getBillTax() {
		return mBillTaxAmount;
	}

	/**
	 * @return the mBillTax
	 */
	public double getBillTax1() {
		return mBillTax1Amount;
	}

	/**
	 * @return the mBillTax
	 */
	public double getBillTax2() {
		return mBillTax2Amount;
	}

	/**
	 * @return the mBillTax
	 */
	public double getBillTax3() {
		return mBillTax3Amount;
	}

	/**
	 * @return the mBillTax
	 */
	public double getBillGST() {
		return mBillGSTAmount;
	}

	/**
	 * @return the mBillTax
	 */
	public double getBillServiceTax() {
		return mBillServiceTaxAmount;
	}

	/**
	 * @return the mBillItemCount
	 */
	public double getBillItemCount() {
		return mBillItemCount;
	}

	/**
	 * @return
	 */
	public ArrayList<BeanOrderDetail> getItemList() {
		return mPosOrderDetailItems;
	}

	/**
	 * @param parent
	 */
	public void setParent(PosOrderEntryForm parent) {
		mParent = parent;
	}

	/**
	 * @return
	 */
	public boolean isReadOnly() {
		return mReadOnly;
	}

	/**
	 * @param readOnly
	 */
	public void setReadOnly(boolean readOnly) {
		this.mReadOnly = readOnly;
	}

	/**
	 * 
	 */
	public IPosOrderListPanelListner mListner;

	/**
	 * @param listner
	 */
	public void setListner(IPosOrderListPanelListner listner) {
		mListner = listner;
	}

	/**
	 * @param item
	 * @param value
	 * @return
	 */
	private boolean canChangeQuantity(BeanOrderDetail item,double value){
		
		boolean result= true;
		String message="";

		if(mReadOnly){
			result=checkAuthenticationForBilledOrders();
		}else if(mSelectedListItemControl==null) 
			result=false;
		else if(mSelectedListItemControl.isReadOnly()) 
			result=false;
		else if (!(mSelectedListItemControl.getOrderDetailItem().getSaleItem().getQuantity()!=value) )
			result=false;
		else if(item.getItemType()==OrderDetailItemType.EXTRA_ITEM){

			message="Quantity can not be changed for this item. Edit parent item and use Extras tab to change quantity.";
			result=false;

		}else if(item.getItemType()==OrderDetailItemType.COMBO_CONTENT_ITEM){

			message="Quantity can not be changed for this item. Edit parent item and use Combo tab to change quantity";
			result=false;

		}else if(item.getItemType()==OrderDetailItemType.SALE_ITEM && item.isExtraItemsSelected()){
			message= "Quantity can not be changed for this item.";
			result=false;
		}else if(item.hasSplits()){
			message= "This item has partial payments. Quantitiy can not be changed ";
			result=false;
		}else if (!mSelectedListItemControl.getOrderDetailItem().isNewItem())
			result=checkAuthenticationForParkedOrders();		
		

		if(!result && message.trim().length()>0)
			PosFormUtil.showErrorMessageBox(mParent,message);

		return result;
	}

	
	/*
	 * 
	 */
	private boolean checkAuthenticationForBilledOrders(){

		boolean result;
		if (PosAccessPermissionsUtil.checkEditAuthenticationOfBilledOrder(mParent, mCurPosOrderItem, true)==MessageBoxResults.Yes)
		{
			result=true;
			mReadOnly=false;
			setItemControlsReadOnly(mReadOnly);
			mCurPosOrderItem.setReadOnly(mReadOnly);
		}else
			result=false;

		return result;
	}
	
	
	/*
	 * 
	 */
	private boolean checkAuthenticationForParkedOrders(){
		
		boolean result=true;
		
		if(PosEnvSettings.getInstance().getUISetting().isParkedItemEditAuthenticationRequired())
			if(PosAccessPermissionsUtil.checkEditAuthentication(mParent,mCurPosOrderItem,true,"parked_order_edit")==MessageBoxResults.Yes){
				mCurPosOrderItem.setReadOnly(mReadOnly);
			}else
				result=false;
	
		return result;
	}
	/**
	 * @param isReadOnly
	 */
	private void setItemControlsReadOnly(boolean isReadOnly){

		for(PosOrderListItemControl ctrl: mPosGridItems){

			ctrl.setReadOnly(isReadOnly);
		}
	}


	/* (non-Javadoc)
	 * @see com.indocosmo.pos.forms.components.orderentry.listners.IPosItemGridOptionPanelListner#onQuantityChanged(double)
	 */
	@Override
	public void onQuantityChanged(double value) {

		if(!canChangeQuantity(mSelectedListItemControl.getOrderDetailItem(),value))
			return;


		BeanOrderDetail orderItem=mSelectedListItemControl.getOrderDetailItem();
		orderItem.setDirty(true);

		setBillVars(mSelectedListItemControl.getPosSaleItem(), false,true);

		mSelectedListItemControl.getPosSaleItem().setQuantity(value);
		PosTaxUtil.calculateTax(mSelectedListItemControl.getPosSaleItem());

		/**
		 * If the order item is a combo, 
		 * update the combo content's quantity too.
		 * */
		if(mSelectedListItemControl.getPosSaleItem().isComboItem()){
			updateComboContentsQuantiyAndDiscount(mSelectedListItemControl.getOrderDetailItem());
		}
		updateDirtyItem(orderItem);

		mSelectedListItemControl.setDisplayLabels();
		setBillVars(mSelectedListItemControl.getPosSaleItem(), true,true);
		setTotalPanel();
		mSelectedListItemControl.onEdited();
		/**
		 * SPLIT TO CHECK NEEDED?

		if(mParent.IsPartiallyPaidOrder()){
			mParent.resetBillDiscount();
		}
		 */
		if (mListner != null)
			mListner.onItemEdited(mSelectedListItemControl);
	}

	/**
	 * @param code
	 * @param posSaleItems
	 * @param searchStartIndex
	 * @return
	 */
	private int findIndexoOf(String code,
			ArrayList<BeanSaleItem> posSaleItems, int searchStartIndex) {
		
		boolean isFound = false;
		for (int index = 0; index < posSaleItems.size(); index++, searchStartIndex++) {
			searchStartIndex = (searchStartIndex < posSaleItems.size()) ? searchStartIndex
					: 0;
			if (posSaleItems.get(searchStartIndex).getCode().equals(code)) {
				isFound = true;
				break;
			}
		}
		return ((isFound) ? searchStartIndex : -1);
	}

	/**
	 * @param code
	 * @param searchStartIndex
	 * @return
	 */
	private int findIndexoOf(String code, int searchStartIndex) {
		
		boolean isFound = false;
		for (int index = 0; index < mPosGridItems.size(); index++, searchStartIndex++) {
			searchStartIndex = (searchStartIndex < mPosGridItems.size()) ? searchStartIndex
					: 0;
			if (mPosGridItems.get(searchStartIndex).getPosSaleItem().getCode()
					.equals(code)) {
				isFound = true;
				break;
			}
		}
		return ((isFound) ? searchStartIndex : -1);
	}

	@Override
	public void onSearchClicked(String itemCode) {
		
		if(itemCode.length()==0 && mPosGridItems.size()<=1) return;
		final int index = findIndexoOf(
				itemCode,
				(mSelectedListItemControl != null) ? mPosGridItems.indexOf(mSelectedListItemControl) + 1
						: 0);
		if (index >= 0)
			mPosGridItems.get(index).setSelected(true);
		adjustBounds();

	}

	/**
	 * @param highlighted
	 */
	public void setHighlighteDiscounted(boolean highlighted) {
		
		mHighlighteDiscounted = highlighted;
		for (PosOrderListItemControl ctrl : mPosGridItems)
			ctrl.setHighlighteDiscounted(mHighlighteDiscounted);
	}

	/**
	 * @return
	 */
	public boolean getHighlighteDiscounted() {
		return mHighlighteDiscounted;
	}


	/**
	 * @param custType
	 */
	public void setCustomerType(BeanCustomerType custType) {
		
		if (mSelectedCustomerType==null || !mSelectedCustomerType.getCode().equals(custType.getCode())) {
			mSelectedCustomerType = custType;
			resetBillVars();
			for (PosOrderListItemControl ctrl : mPosGridItems) {

				BeanSaleItem item = ctrl.getPosSaleItem();
				BeanSaleItem clonedItem = item.clone();
				PosSaleItemUtil.resetItemPrice4CustType(ctrl.getOrderDetailItem(),custType);
				// force to reset the values
				ctrl.setDisplayLabels();
//				setBillVars(clonedItem, false);
//				setBillVars(item, true);
				if(!ctrl.getOrderDetailItem().isVoid())
					setBillVars(item, true);

			}
			setTotalPanel();
		}
	}

	/**
	 * @param mPosOrderDetailItems
	 *            the mPosOrderDetailItems to set
	 */
	private void setOrderDetailItems(
			ArrayList<BeanOrderDetail> posOrderDetailItems) {
		resetGrid();
		this.mPosOrderDetailItems = new ArrayList<BeanOrderDetail>();
		mCurPosOrderItem.setOrderDetailItems(mPosOrderDetailItems);
		for (BeanOrderDetail item : posOrderDetailItems) {
			addOrderDetailItem(item);
		}
		if(mPosGridItems!=null && mPosGridItems.size()>0)
			mPosGridItems.get(0).setSelected(true);
	}

	private BeanOrderHeader mCurPosOrderItem;

	/**
	 * @param item
	 */
	public void setPosOrderEntryItem(BeanOrderHeader item) {

		mCurPosOrderItem = item;
		if(item!=null){
			if(!isReadOnly())
				setReadOnly(item.isReadOnly());
			setOrderDetailItems(item.getOrderDetailItems());
			setCustomerType(mCurPosOrderItem.getCustomer().getCustType());

		}else

			resetGrid();
	}

	/**
	 * @return
	 */
	public BeanOrderHeader getPosOrderEntryItem() {

		return mCurPosOrderItem;
	}

	private PosOrderEntryMode mOrderEntryMode;

	/**
	 * @param retrieve
	 */
	public void setOrderEntrMode(PosOrderEntryMode mode) {

		mOrderEntryMode = mode;
	}

	/**
	 * 
	 */
	public void setPrintedToKitchen(){

		for (int index = 0; index < mPosGridItems.size(); index++) {

			mPosGridItems.get(index).refresh();
		}
	}

	/**
	 * @return
	 */
	public ArrayList<PosOrderListItemControl> getGridItems(){
		return mPosGridItems;
	}

	/**
	 * 
	 */
	private void setDisplayingItemsCount(){

		if(mitemGridControl==null) return;
		int fromIndex=0;
		int toIndex=0;
		int totalItem=mPosGridItems.size();

		if(totalItem>0){
			final int topItemDisplyY=(mItemListPanel.getY()<0?-1*mItemListPanel.getY():0)+5;
			fromIndex=topItemDisplyY/(PosOrderListItemControl.LAYOUT_HEIGHT);
			fromIndex=mPosGridItems.get(fromIndex).getItemIndex();
			toIndex=((fromIndex+mDisplayItemsPerPage)>=totalItem)?totalItem:(fromIndex+mDisplayItemsPerPage);
		}

		mitemGridControl.setDisplyOfItems(fromIndex,toIndex,totalItem);

	}


	/**
	 * @return
	 */
	public PosOrderListItemControl getSelectedItemControl(){

		return mSelectedListItemControl;
	}
	
	/**
	 * @param list
	 * @param saleItemCode
	 * @return
	 */
	private PosOrderListItemControl getGroupItemControlFromList(ArrayList<PosOrderListItemControl> list, String saleItemCode){
		
		PosOrderListItemControl itemCtrl=null;
		for(PosOrderListItemControl ctrl:list){
			
			if(ctrl.getSaleItemCode().equals(saleItemCode) && !ctrl.isVoid() &&  
					PosOrderUtil.getStatusString(ctrl.getOrderDetailItem()).trim().equals("N")){
				if(ctrl.getOrderDetailItem().getRemarks()==null || ctrl.getOrderDetailItem().getRemarks().trim().length()==0){
					 if (!ctrl.getOrderDetailItem().hasSubItems() && !ctrl.getOrderDetailItem().getSaleItem().hasSelectedAttributes())  {
						 if(ctrl.getOrderDetailItem().getParentDtlId()==null || ctrl.getOrderDetailItem().getParentDtlId().trim()=="" )
						 itemCtrl=ctrl;
						 break;
					 }
				}
				
			}
		}
		
		return itemCtrl;
	}
}
