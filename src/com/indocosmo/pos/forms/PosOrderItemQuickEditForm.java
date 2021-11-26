/**
 * 
 */
package com.indocosmo.pos.forms;

import java.awt.Color;
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
import com.indocosmo.pos.forms.components.orderentry.itemedit.PosTabPriceDiscountQuickEdit;
import com.indocosmo.pos.forms.components.orderentry.itemedit.PosTabOrderItemDetail;
import com.indocosmo.pos.forms.components.orderentry.itemedit.PosTabOrderItemExtrasEdit;
import com.indocosmo.pos.forms.components.orderentry.itemedit.PosTabOrderItemRemarksQuickEdit;
import com.indocosmo.pos.forms.components.tab.PosTab;
import com.indocosmo.pos.forms.components.tab.PosTabControl;
import com.indocosmo.pos.forms.listners.IPosFormEventsListner;
import com.indocosmo.pos.forms.listners.IPosItemEditFormListner;
import com.indocosmo.pos.forms.messageboxes.PosMessageBoxForm.MessageBoxButtonTypes;
import com.indocosmo.pos.forms.messageboxes.PosMessageBoxForm.MessageBoxResults;

/**
 * @author LAP-L530
 *
 */
@SuppressWarnings("serial")
public class PosOrderItemQuickEditForm extends PosBaseForm {

	
	/** Header panel width **/
	private static final int ITEM_HEADER_PANEL_HEIGHT = 0;//50

	/** Content panel width **/
	private static final int CONTENT_PANEL_HEIGHT = 390;//520;

	/** Horizontal gap between the controls **/
	private static final int PANEL_CONTENT_H_GAP = PosConstants.PANEL_CONTENT_H_GAP;

	/** Vertical gap between the controls **/
	private static final int PANEL_CONTENT_V_GAP = 5;

	/** Form height **/
	private static final int LAYOUT_HEIGHT = ITEM_HEADER_PANEL_HEIGHT + CONTENT_PANEL_HEIGHT;

	/** Form width **/
	//modified by gibin
	public static final int LAYOUT_WIDTH = 700;//600

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

	private BeanSaleItem mEditingSaleItem;
	private BeanOrderDetail mEditingOrderDtlItem, mOrgOrderDtlItem;

	private PosTabControl mEditTab;
	private PosTabPriceDiscountQuickEdit mItemPriceDiscEditTab;
	private PosTabOrderItemRemarksQuickEdit mItemRemarksEditTab;
	private IPosItemEditFormListner mOnItemEditedListner = null;
	/**
	 * @param title
	 * @param cPanelwidth
	 * @param cPanelHeight
	 * @param showTitle
	 */
	public PosOrderItemQuickEditForm(final BeanOrderDetail orderDetailItem) throws CloneNotSupportedException {
		super("Edit Item", LAYOUT_WIDTH, LAYOUT_HEIGHT, false);
		mOrgOrderDtlItem=orderDetailItem;
		mEditingOrderDtlItem=orderDetailItem;//mOrgOrderDtlItem.clone();
		mEditingSaleItem = mEditingOrderDtlItem.getSaleItem();
		mOrgOrderDtlItem.getSaleItem();
		initForm();
		setDefaultComponent(mItemPriceDiscEditTab.getDefaultComponent());
	}
	
	private void initForm(){

		setResetButtonVisible(true);
		displaySelectedItem();
		setDisplayItemValues();
		setTabContents();

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
	private void setTabContents() {
		
		ArrayList<PosTab> tabs = new ArrayList<PosTab>();
		
		if(canShowPriceTab(mEditingOrderDtlItem)){
			mItemPriceDiscEditTab = new PosTabPriceDiscountQuickEdit(this,mEditingOrderDtlItem);
			tabs.add(mItemPriceDiscEditTab);
		}

		mItemRemarksEditTab = new PosTabOrderItemRemarksQuickEdit(this,mEditingOrderDtlItem);
		tabs.add(mItemRemarksEditTab);

		new PosTabOrderItemDetail(this, mEditingOrderDtlItem);

		mEditTab = new PosTabControl(LAYOUT_WIDTH, CONTENT_PANEL_HEIGHT
				- PANEL_CONTENT_V_GAP);
				
		mEditTab.setLocation(0,0);
		mEditTab.setTabs(tabs);
		mContentPanel.add(mEditTab);
	}

	private void setPanels() {

		mItemHeaderPanel = new JPanel();
		mItemHeaderPanel
		.setBounds(0, 0, LAYOUT_WIDTH, ITEM_HEADER_PANEL_HEIGHT);
		mItemHeaderPanel.setBackground(PANEL_ITEM_HEADER_BG_COLOR);
		mItemHeaderPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
		mItemHeaderPanel.setLayout(null);
	
	}
	
	/* (non-Javadoc)
	 * @see com.indocosmo.pos.forms.PosBaseForm#setContentPanel(javax.swing.JPanel)
	 */
	@Override
	protected void setContentPanel(JPanel panel) {
		
		mContentPanel = panel;
		setPanels();
	}

	/**
	 * @param parent
	 */
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
 
	/* (non-Javadoc)
	 * @see com.indocosmo.pos.forms.PosBaseForm#onOkButtonClicked()
	 */
	@Override
	public boolean onOkButtonClicked() {
		// TODO Auto-generated method stub
		
		if (	
				(mItemPriceDiscEditTab != null && !mItemPriceDiscEditTab
				.onOkButtonClicked())
				||
				 
				(mItemRemarksEditTab != null && !mItemRemarksEditTab
				.onOkButtonClicked())

			)
			return false;
		
		notifyEdited();
		return super.onOkButtonClicked();
	} 
	
	private void notifyEdited() {
		if (mOnItemEditedListner != null)
			 
			mOnItemEditedListner.onItemEdited(mEditingOrderDtlItem, mOrgOrderDtlItem);
	}
	
	public void onResetButtonClicked() {
		if(PosFormUtil.showQuestionMessageBox(this, MessageBoxButtonTypes.YesNo, "Do you want discard the changes in " + 
				mEditTab.getSelectedTab().getTabCaption() + " tab?" , null)==MessageBoxResults.Yes){

			((IPosFormEventsListner) mEditTab.getSelectedTab())
			.onResetButtonClicked();
		}
	}
	public void setOnItemEditedListner(IPosItemEditFormListner listner) {
		this.mOnItemEditedListner = listner;
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
