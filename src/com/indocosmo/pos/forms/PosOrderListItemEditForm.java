package com.indocosmo.pos.forms;

import java.awt.Color;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;

import com.indocosmo.pos.common.PosConstants;
import com.indocosmo.pos.common.PosEnvSettings;
import com.indocosmo.pos.common.utilities.PosAccessPermissionsUtil;
import com.indocosmo.pos.common.utilities.PosFormUtil;
import com.indocosmo.pos.common.utilities.PosResUtil;
import com.indocosmo.pos.data.beans.BeanOrderDetail;
import com.indocosmo.pos.data.beans.BeanOrderDetail.OrderDetailItemType;
import com.indocosmo.pos.data.beans.BeanSaleItem;
import com.indocosmo.pos.data.providers.shopdb.PosDiscountItemProvider;
import com.indocosmo.pos.forms.components.orderentry.itemedit.PosTabOrderItemAttributeEdit;
import com.indocosmo.pos.forms.components.orderentry.itemedit.PosTabOrderItemComboContentEdit;
import com.indocosmo.pos.forms.components.orderentry.itemedit.PosTabOrderItemDetail;
import com.indocosmo.pos.forms.components.orderentry.itemedit.PosTabOrderItemExtrasEdit;
import com.indocosmo.pos.forms.components.orderentry.itemedit.PosTabOrderItemPriceDiscountEdit;
import com.indocosmo.pos.forms.components.orderentry.itemedit.PosTabOrderItemRemarksEdit;
import com.indocosmo.pos.forms.components.tab.PosTab;
import com.indocosmo.pos.forms.components.tab.PosTabControl;
import com.indocosmo.pos.forms.listners.IPosFormEventsListner;
import com.indocosmo.pos.forms.listners.IPosItemEditFormListner;
import com.indocosmo.pos.forms.listners.adapters.PosWindowListenerAdapter;
import com.indocosmo.pos.forms.messageboxes.PosMessageBoxForm.MessageBoxButtonTypes;
import com.indocosmo.pos.forms.messageboxes.PosMessageBoxForm.MessageBoxResults;
@SuppressWarnings("serial")
public class PosOrderListItemEditForm extends PosBaseForm {

	/** Header panel width **/
	private static final int ITEM_HEADER_PANEL_HEIGHT = 50;

	/** Content panel width **/
	private static final int CONTENT_PANEL_HEIGHT = 520;

	/** Horizontal gap between the controls **/
	private static final int PANEL_CONTENT_H_GAP = PosConstants.PANEL_CONTENT_H_GAP;

	/** Vertical gap between the controls **/
	private static final int PANEL_CONTENT_V_GAP = 5;

	/** Form height **/
	private static final int LAYOUT_HEIGHT = ITEM_HEADER_PANEL_HEIGHT
			+ CONTENT_PANEL_HEIGHT;

	/** Form width **/
	public static final int LAYOUT_WIDTH = 1010;

	/** Background color item header panel **/
	private static final Color PANEL_ITEM_HEADER_BG_COLOR = PosOrderEntryForm.PANEL_BG_COLOR;
	
	private static final String  DELETED_ICON_NAME="trash-circle.png";
	private static final int DELETED_ICON_SIZE = 50;

	private JPanel mItemHeaderPanel;
	private JPanel mContentPanel;

	private JLabel mlabelItemCode;
	private JLabel mlabelItemCodevalue;
	private JLabel mlabelItemName;
	private JLabel mlabelItemNamevalue;
	private JLabel mlabelItemDeletedIcon;

	private BeanSaleItem mEditingSaleItem, mOrgSaleItem;
	private BeanOrderDetail mEditingOrderDtlItem, mOrgOrderDtlItem;

	private PosTabControl mEditTab;
	private PosTabOrderItemAttributeEdit mItemAttributeEditTab;
	private PosTabOrderItemExtrasEdit mItemExtrasEditTab;
	private PosTabOrderItemComboContentEdit mItemComboContentsEditTab;
	private PosTabOrderItemPriceDiscountEdit mItemPriceDiscEditTab;
	private PosTabOrderItemRemarksEdit mItemRemarksEditTab;
	private PosTabOrderItemDetail mItemDetails;


	private IPosItemEditFormListner mOnItemEditedListner = null;
	//	private PosOrderListItemControl mPosOrderItemControl;

	public PosOrderListItemEditForm(final BeanOrderDetail orderDetailItem) throws CloneNotSupportedException {
		super("Edit item", LAYOUT_WIDTH, LAYOUT_HEIGHT);

		mOrgOrderDtlItem=orderDetailItem;
		mEditingOrderDtlItem=mOrgOrderDtlItem.clone();
		mEditingSaleItem = mEditingOrderDtlItem.getSaleItem();
		mOrgSaleItem = mOrgOrderDtlItem.getSaleItem();
		initForm();

	} 

	private void initForm(){

		setResetButtonVisible(true);
		displaySelectedItem();
		setDisplayItemValues();
		setTabContents();

		addWindowListener(mWindowListner);
	}

	private WindowListener mWindowListner = new PosWindowListenerAdapter() {

		public void windowActivated(WindowEvent e) {
			
			mEditTab.getSelectedTab().requestFocusInWindow();
		};
	};

	/**
	 * 
	 */
	private void setPanels() {

		mItemHeaderPanel = new JPanel();
		mItemHeaderPanel
		.setBounds(0, 0, LAYOUT_WIDTH, ITEM_HEADER_PANEL_HEIGHT);
		mItemHeaderPanel.setBackground(PANEL_ITEM_HEADER_BG_COLOR);
		mItemHeaderPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
		mItemHeaderPanel.setLayout(null);

		mContentPanel.add(mItemHeaderPanel);
	}

	private boolean canShowPriceTab(BeanOrderDetail item){

		boolean result=true;
		if(!PosAccessPermissionsUtil.validateAccess(this, 
				PosEnvSettings.getInstance().getCashierShiftInfo().getCashierInfo().getUserGroupId(), "edit_item",true)){

			result=false;

		}else if(item.getItemType()==OrderDetailItemType.EXTRA_ITEM){

			if(item.getSaleItem().getDiscount().getCode().equals(PosDiscountItemProvider.FREE_TOPPING_DISCOUNT))

				result=false;

		}else if(item.getItemType()==OrderDetailItemType.COMBO_CONTENT_ITEM){

			result=false;
		}

		return result;
	}

	private boolean canShowExtrasTab(BeanOrderDetail item){
		
		boolean result=true;
		if(item.getParentDtlId()!=null || item.getItemType()!=OrderDetailItemType.SALE_ITEM){
			result=false;
		}else if(item.hasSplits()){
			result=false;
		}
		return result;
	}

	private boolean canShowAtributesTab(BeanOrderDetail item){
		
		boolean result=true;
		if(item.getSaleItem().getAtrributeList()==null || item.getSaleItem().getAtrributeList().size() == 0)
			result=false;
		return result;
	}

	private void setTabContents() {
		
		ArrayList<PosTab> tabs = new ArrayList<PosTab>();

		if(canShowPriceTab(mEditingOrderDtlItem)){
			mItemPriceDiscEditTab = new PosTabOrderItemPriceDiscountEdit(this,
					mEditingOrderDtlItem);
			tabs.add(mItemPriceDiscEditTab);
		}

		if(mEditingOrderDtlItem.getSaleItem().isComboItem()){
			mItemComboContentsEditTab=new PosTabOrderItemComboContentEdit(this, mEditingOrderDtlItem);
			tabs.add(mItemComboContentsEditTab);
		}

		if (canShowExtrasTab(mEditingOrderDtlItem)){
			mItemExtrasEditTab=new PosTabOrderItemExtrasEdit(this,mEditingOrderDtlItem);
			if(mItemExtrasEditTab.hasChoiceItems())
				tabs.add(mItemExtrasEditTab);
		}

		if (canShowAtributesTab(mEditingOrderDtlItem)) {
			mItemAttributeEditTab = new PosTabOrderItemAttributeEdit(this,mEditingOrderDtlItem);
			tabs.add(mItemAttributeEditTab);
		}

		mItemRemarksEditTab = new PosTabOrderItemRemarksEdit(this,mEditingOrderDtlItem);
		tabs.add(mItemRemarksEditTab);

		//		if (PosEnvSettings.getInstance().getStation().getType() == PosTerminalServiceType.Restaurant) {
		//			mitemServiceOptionEditPanel = new PosItemServiceOptionEditPanel(this,
		//					mPosOrderItemControl.getOrderDetailItem());
		//			tabs.add(mitemServiceOptionEditPanel);
		//		}
		mItemDetails = new PosTabOrderItemDetail(this, mEditingOrderDtlItem);
		tabs.add(mItemDetails);

		mEditTab = new PosTabControl(LAYOUT_WIDTH, CONTENT_PANEL_HEIGHT
				- PANEL_CONTENT_V_GAP);
		mEditTab.setLocation(0,
				mItemHeaderPanel.getY() + mItemHeaderPanel.getHeight()
				+ PANEL_CONTENT_V_GAP);
		mEditTab.setTabs(tabs);
		mContentPanel.add(mEditTab);
		mEditTab.setSelectedTab(mItemPriceDiscEditTab);
	}

	private void displaySelectedItem() {
		mlabelItemCode = new JLabel();
		int top = PANEL_CONTENT_V_GAP * 2;
		mlabelItemCode.setText("Item Code:");
		mlabelItemCode.setHorizontalAlignment(SwingConstants.LEFT);
		mlabelItemCode.setBounds(PANEL_CONTENT_H_GAP, top, 130, 30);
		mlabelItemCode.setFont(PosFormUtil.getLoginLabelFont());
		mItemHeaderPanel.add(mlabelItemCode);

		int left = mlabelItemCode.getX() + mlabelItemCode.getWidth();
		mlabelItemCodevalue = new JLabel();
		mlabelItemCodevalue.setHorizontalAlignment(SwingConstants.LEFT);
		mlabelItemCodevalue.setBounds(left, top, 120, 30);
		mlabelItemCodevalue.setFont(PosFormUtil.getLoginLabelFont());
		mItemHeaderPanel.add(mlabelItemCodevalue);

		left = mlabelItemCodevalue.getX() + mlabelItemCodevalue.getWidth()
				+ PANEL_CONTENT_H_GAP * 2;
		mlabelItemName = new JLabel();
		mlabelItemName.setText("Item Name:");
		mlabelItemName.setHorizontalAlignment(SwingConstants.LEFT);
		mlabelItemName.setBounds(left, top, 150, 30);
		mlabelItemName.setFont(PosFormUtil.getLoginLabelFont());
		mItemHeaderPanel.add(mlabelItemName);

		left = mlabelItemName.getX() + mlabelItemName.getWidth();
		mlabelItemNamevalue = new JLabel();
		mlabelItemNamevalue.setHorizontalAlignment(SwingConstants.LEFT);
		mlabelItemNamevalue.setBounds(left, top, mItemHeaderPanel.getWidth()-DELETED_ICON_SIZE
				- left - PANEL_CONTENT_H_GAP, 30);
		mlabelItemNamevalue.setFont(PosFormUtil.getLoginLabelFont());
		mItemHeaderPanel.add(mlabelItemNamevalue);
		
		left = mItemHeaderPanel.getWidth()-DELETED_ICON_SIZE;
		mlabelItemDeletedIcon = new JLabel();
		mlabelItemDeletedIcon.setBounds(left, 0, DELETED_ICON_SIZE, DELETED_ICON_SIZE);
		mItemHeaderPanel.add(mlabelItemDeletedIcon);
		
		mlabelItemDeletedIcon.setIcon((mOrgOrderDtlItem!=null && mOrgOrderDtlItem.isVoid())?PosResUtil.getImageIconFromResource(DELETED_ICON_NAME):null);

	}

	private void setDisplayItemValues() {
		
		mlabelItemCodevalue.setText(mEditingSaleItem.getCode());
		mlabelItemNamevalue.setText(mEditingSaleItem.getName());
	}

	private void notifyEdited() {
		if (mOnItemEditedListner != null)
			//			if (mPosOrderItemControl instanceof PosOrderListComboContentControl)
			//				mOnItemEditedListner.onComboContentItemEdited(mEditingSaleItem,
			//						mSubstitutesEditPanel.getNewSubstitutes());
			//			else
			mOnItemEditedListner.onItemEdited(mEditingOrderDtlItem, mOrgOrderDtlItem);
	}

	public void setOnItemEditedListner(IPosItemEditFormListner listner) {
		this.mOnItemEditedListner = listner;
	}

	@Override
	protected void setContentPanel(JPanel panel) {
		mContentPanel = panel;
		mContentPanel.setBorder(null);
		setPanels();
	}

	@Override
	public boolean onOkButtonClicked() {

		// Validate the qty against the extras.
		// Qty should be one when there are extras.
		if (	(mItemPriceDiscEditTab != null && mItemPriceDiscEditTab.getEnteredQty()>1) && 
				(mItemExtrasEditTab != null && mItemExtrasEditTab.getSelectedExtrasListMap()!=null && mItemExtrasEditTab.getSelectedExtrasListMap().size()>0)){

			for(ArrayList<BeanOrderDetail> extraItems:mItemExtrasEditTab.getSelectedExtrasListMap().values()){

				for(BeanOrderDetail item:extraItems){
					if(!item.isVoid()){
						PosFormUtil.showErrorMessageBox(this, "Item's quantity should be 1 when item contains extras.");
						return false;
					}
				}
			}

		}

		if (	
				(mItemPriceDiscEditTab != null && !mItemPriceDiscEditTab
				.onOkButtonClicked())
				||
				(mItemComboContentsEditTab != null && !mItemComboContentsEditTab
				.onOkButtonClicked())
				||
				(mItemExtrasEditTab != null && !mItemExtrasEditTab
				.onOkButtonClicked())
				||
				(mItemRemarksEditTab != null && !mItemRemarksEditTab
				.onOkButtonClicked())
				||
				(mItemAttributeEditTab != null && !mItemAttributeEditTab
				.onOkButtonClicked())

				)
			return false;
		notifyEdited();
		mItemAttributeEditTab = null;
		return super.onOkButtonClicked();
	}

	@Override
	public void onResetButtonClicked() {
		if(PosFormUtil.showQuestionMessageBox(this, MessageBoxButtonTypes.YesNo, "Do you want discard the changes in " + 
				mEditTab.getSelectedTab().getTabCaption() + " tab?" , null)==MessageBoxResults.Yes){

			((IPosFormEventsListner) mEditTab.getSelectedTab())
			.onResetButtonClicked();
		}
	}

	public void setSelectedTab(String tabText){
		mEditTab.setSelectedTab(tabText);
	}

	public interface IPosOrderItemEditFormEventListner extends IPosFormEventsListner{

		public void onResetButtonClicked(final BeanOrderDetail orgItem);

	}

	public void setReadOnly(boolean isReadOnly){
		if(isReadOnly){
			setOkEnabled(!isReadOnly);
			setResetEnable(!isReadOnly);
			for(PosTab tab:mEditTab.getTabs()){
				tab.setReadOnly(isReadOnly);
			}
		}
	}
}
