package com.indocosmo.pos.forms.components.orderentry;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.RootPaneContainer;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import com.indocosmo.pos.common.PosEnvSettings;
import com.indocosmo.pos.common.utilities.PosFormUtil;
import com.indocosmo.pos.common.utilities.PosOrderUtil;
import com.indocosmo.pos.common.utilities.PosResUtil;
import com.indocosmo.pos.forms.PosOrderEntryForm;
import com.indocosmo.pos.forms.components.buttons.PosButton;
import com.indocosmo.pos.forms.components.buttons.listners.PosButtonListnerAdapter;
import com.indocosmo.pos.forms.components.itemcontrols.PosOrderListItemControl;
import com.indocosmo.pos.forms.components.keypads.PosVKeyPad;
import com.indocosmo.pos.forms.components.keypads.PosVKeyPad.IPosVKeypadListner;
import com.indocosmo.pos.forms.components.keypads.PosVKeyPadButton;
import com.indocosmo.pos.forms.components.orderentry.listners.IPosItemGridOptionPanelListner;
import com.indocosmo.pos.forms.components.search.IPosSearchableItem;
import com.indocosmo.pos.forms.search.PosExtSearchForm;
import com.indocosmo.pos.forms.search.listener.adapter.PosItemExtSearchFormAdapter;

/**
 * @author jojesh
 * Class to handle the Grid display of items
 */
@SuppressWarnings("serial")
public final class PosOrderListOptionPanel extends JPanel {

	private static final int PANEL_CONTENT_V_GAP=2;//PosOrderEntryForm.PANEL_CONTENT_V_GAP;
	private static final int PANEL_CONTENT_H_GAP=3;//PosOrderEntryForm.PANEL_CONTENT_H_GAP;

	private static final int BUTTON_HEIGHT=41;
	private static final int BUTTON_WIDTH=63;

	//	private static final int BUTTON_SEARCH_HEIGHT=BUTTON_HEIGHT;
	private static final int BUTTON_SEARCH_WIDTH=BUTTON_WIDTH;//*2+PANEL_CONTENT_H_GAP;

	//	private static final int ITEM_PANEL_HEIGHT=BUTTON_HEIGHT*2;
	//	private static final int ITEM_PANEL_MIDDLE_HEIGHT=BUTTON_HEIGHT;

	public static final int PANEL_HEIGHT=BUTTON_HEIGHT*2+24+PANEL_CONTENT_V_GAP*3;
	private static final int SEARCH_PANEL_HEIGHT=BUTTON_HEIGHT+PANEL_CONTENT_V_GAP*2;
	public static final int PANEL_WIDTH=BUTTON_WIDTH*4+PANEL_CONTENT_H_GAP*6 + PosVKeyPadButton.LAYOUT_WIDTH ;/**for replacing the vertical numeric keypad **/

	private static final String IMAGE_DOWN_NORMAL="grd_option_down.png";
	private static final String IMAGE_DOWN_TOUCH="grd_option_down_touch.png";
	private static final String IMAGE_UP_NORMAL="grd_option_up.png";
	private static final String IMAGE_UP_TOUCH="grd_option_up_touch.png";
	private static final String IMAGE_EDIT_NORMAL="grd_option_edit.png";
	private static final String IMAGE_EDIT_TOUCH="grd_option_edit_touch.png";
	private static final String IMAGE_ITEM_ADD_NORMAL="grd_option_item_add.png";
	private static final String IMAGE_ITEM_ADD_TOUCH="grd_option_item_add_touch.png";
	private static final String IMAGE_QTY_ADD_NORMAL="grd_option_qty_add.png";
	private static final String IMAGE_QTY_ADD_TOUCH="grd_option_qty_add_touch.png";
	private static final String IMAGE_QTY_SUB_NORMAL="grd_option_qty_sub.png";
	private static final String IMAGE_QTY_SUB_TOUCH="grd_option_qty_sub_touch.png";

	private static final String IMAGE_DELETE_NORMAL="grd_option_delete_red.png";
	private static final String IMAGE_DELETE_TOUCH="grd_option_delete_red_touch.png";

	private static final String IMAGE_SEARCH_NORMAL="grd_option_search.png";
	private static final String IMAGE_SEARCH_TOUCH="grd_option_search_touch.png";

//	private static final String IMAGE_SEARCH_NEXT_NORMAL="grd_option_search_next.png";
//	private static final String IMAGE_SEARCH_NEXT_TOUCH="grd_option_search_next_touch.png";
	
//	private static final String IMAGE_EDIT_NORMAL="grd_edit_normal.png";
//	private static final String IMAGE_EDIT_TOUCH="grd_edit_touch.png";



	//	private static final Color PANEL_BG_COLOR=new Color(0,255,0);
	private static final Color PANEL_BG_COLOR=PosOrderEntryForm.PANEL_BG_COLOR;
//	private int mLeft, mTop;
	private boolean mIsSearchOnly;
	private IPosItemGridOptionPanelListner mGridOptionListner;
	private PosOrderListPanel mOrderListPanel;
	private PosOrderListItemControl mGridItem;
	private PosButton mButtonSearch;
	private PosButton mEditItemButton;
	private String mSearchText="";
	private RootPaneContainer mParent;
	private JPanel mSmallButtonContainer;
	
	private JLabel mDisplyOfItems;


	public PosOrderListOptionPanel(RootPaneContainer Parent) {
		mIsSearchOnly=false;
//		mLeft=left;
//		mTop=top;
		mParent=Parent;
		initComponent();
	}

	public void setOrderListPanel(PosOrderListPanel listPanel){
		mOrderListPanel=listPanel;
		setGridOptionListner(listPanel);
	}

	public PosOrderListOptionPanel(boolean isSearchOnly) {
		mIsSearchOnly=isSearchOnly;
//		mLeft=left;
//		mTop=top;
		initComponent();
	}

	private void initComponent() {
		int width=PANEL_WIDTH;
		int height=(mIsSearchOnly)?SEARCH_PANEL_HEIGHT:PANEL_HEIGHT;
		setPreferredSize(new Dimension(width, height));
		setSize(width, height);
		setPreferredSize(new Dimension(width, height));
		setBackground(PANEL_BG_COLOR);
//				setBackground(Color.GREEN);
		setBorder(new LineBorder(Color.LIGHT_GRAY,1));
		setLayout(null);

		createButtons();

	}

	private FlowLayout createLayout(){
		FlowLayout flowLayout=new FlowLayout();
		flowLayout.setVgap(PANEL_CONTENT_V_GAP);
		flowLayout.setHgap(PANEL_CONTENT_H_GAP);
		flowLayout.setAlignment(FlowLayout.CENTER);
		return flowLayout;
	}

	private void createButtons(){
		final int width=getWidth()-PosVKeyPadButton.LAYOUT_WIDTH-PANEL_CONTENT_H_GAP;
		final int height=BUTTON_HEIGHT*2+PANEL_CONTENT_V_GAP*3;
		
		mSmallButtonContainer=new JPanel();
		mSmallButtonContainer.setOpaque(false);
		mSmallButtonContainer.setLayout(createLayout());
		mSmallButtonContainer.setPreferredSize(new Dimension(width, height));
		mSmallButtonContainer.setBounds(0,0,width, height);
//		mSmallButtonContainer.setBackground(Color.RED);
		add(mSmallButtonContainer);
	
		createUpButton();
		createAddButton();
		createSearchButton();
		createPlusButton();
		createDownButton();
		createDeleteButton();
		createRepeatSearchButtons();
		createMinusButton();
		createVKeyPad();
		createPreviousButton();
		createNextButton();
//		createEditButton();
		createDisplayofItems();
	}
	
	/**
	 * 
	 */
	private void createDisplayofItems() {
		final int top=mSmallButtonContainer.getX()+mSmallButtonContainer.getHeight();
		final int left=PANEL_CONTENT_H_GAP;
		final int height=getHeight()-top;
		final int width=getWidth()-PANEL_CONTENT_H_GAP*2;
		mDisplyOfItems=new JLabel("Displaying Items 1 To 10 of 100");
		mDisplyOfItems.setBounds(left,top, width, height);
		mDisplyOfItems.setForeground(Color.WHITE);
//		mDisplyOfItems.setFont(PosFormUtil.getLabelFont().deriveFont(Font.BOLD));
		//getItemLabelFont().deriveFont((float)uiSettings.getItemDetailFontSize());
		mDisplyOfItems.setFont(new Font(PosEnvSettings.getInstance().getUISetting().getLabelFont(), Font.BOLD, 15));
		mDisplyOfItems.setBackground(new Color(78,128,188));
//		mDisplyOfItems.setBackground(Color.BLUE);
		mDisplyOfItems.setOpaque(true);
		mDisplyOfItems.setHorizontalAlignment(SwingConstants.CENTER);
		add(mDisplyOfItems);
		setDisplyOfItems(0, 0, 0);
	}

	private PosVKeyPad mVKeyPad ;
	private void createVKeyPad() {
		final int top = PANEL_CONTENT_V_GAP;
		final int left = mSmallButtonContainer.getX() + mSmallButtonContainer.getWidth();// +PANEL_V_GAP;
		final int height = mSmallButtonContainer.getHeight()-PANEL_CONTENT_V_GAP*2;// PosUtil.getScreenHeight()-PosBottomToolbarPanel.LAYOUT_HEIGHT-top-PosOrderEntryForm.PANEL_V_GAP*2;
		// mGridPanel.getHeight();
		mVKeyPad = new PosVKeyPad(left, top, height, true);
		mVKeyPad.setParent(mParent);
		// mGridPanel.add(mVKeyPad);
		mVKeyPad.setListner(new IPosVKeypadListner() {
			@Override
			public void onValueSlected(double value) {
				if(!checkForActionAlloweded()) return ;
				if(!PosOrderUtil.getStatusString(mGridItem.getOrderDetailItem()).equals("N")){
					PosFormUtil.showInformationMessageBox(mParent, "This item has been already printed to kitchen, Please add as new item.");
					return ;
				}
				mOrderListPanel.onQuantityChanged((double) value);
			}
		});
		add(mVKeyPad);
	}

	PosButton mPageUpButton;
	private void createUpButton(){
		mPageUpButton=new PosButton();
		mPageUpButton.setBounds(0, 0, BUTTON_WIDTH, BUTTON_HEIGHT);
		mPageUpButton.setImage(PosResUtil.getImageIconFromResource(IMAGE_UP_NORMAL));
		mPageUpButton.setTouchedImage(PosResUtil.getImageIconFromResource(IMAGE_UP_TOUCH));
		mPageUpButton.setOnClickListner(new PosButtonListnerAdapter() {
			@Override
			public void onClicked(PosButton button) {
				if(!checkForActionAlloweded()) return ;
				if(mGridOptionListner!=null)
					mGridOptionListner.onUpClicked();
			}
		});
		mPageUpButton.registerKeyStroke(KeyEvent.VK_PAGE_UP);
		mSmallButtonContainer.add(mPageUpButton);
	}

	PosButton mPageDownButton;
	private void createDownButton(){
		mPageDownButton=new PosButton();
		mPageDownButton.setBounds(0, 0, BUTTON_WIDTH, BUTTON_HEIGHT);
		mPageDownButton.setImage(PosResUtil.getImageIconFromResource(IMAGE_DOWN_NORMAL));
		mPageDownButton.setTouchedImage(PosResUtil.getImageIconFromResource(IMAGE_DOWN_TOUCH));
		mPageDownButton.setOnClickListner(new PosButtonListnerAdapter() {
			@Override
			public void onClicked(PosButton button) {
				if(!checkForActionAlloweded()) return ;
				if(mGridOptionListner!=null)
					mGridOptionListner.onDownClicked();
			}
		});
		mPageDownButton.registerKeyStroke(KeyEvent.VK_PAGE_DOWN);
		mSmallButtonContainer.add(mPageDownButton);
	}
	
	PosButton mNextButton;
	private void createNextButton(){
		mNextButton=new PosButton();
		mNextButton.setBounds(0, 0, 0, 0);
//		mNextButton.setImage(PosResUtil.getImageIconFromResource(IMAGE_UP_NORMAL));
//		mNextButton.setTouchedImage(PosResUtil.getImageIconFromResource(IMAGE_UP_TOUCH));
//		mNextButton.setVisible(false);
		mNextButton.setOnClickListner(new PosButtonListnerAdapter() {
			@Override
			public void onClicked(PosButton button) {
				if(!checkForActionAlloweded()) return ;
				if(mGridOptionListner!=null)
					mGridOptionListner.onNexClicked();
			}
		});
		mNextButton.registerKeyStroke(KeyEvent.VK_DOWN);
		add(mNextButton);
	}

	PosButton mPreviousButton;
	private void createPreviousButton(){
		mPreviousButton=new PosButton();
		mPreviousButton.setBounds(0, 0, 0, 0);
//		mNextButton.setImage(PosResUtil.getImageIconFromResource(IMAGE_UP_NORMAL));
//		mNextButton.setTouchedImage(PosResUtil.getImageIconFromResource(IMAGE_UP_TOUCH));
		mPreviousButton.setOnClickListner(new PosButtonListnerAdapter() {
			@Override
			public void onClicked(PosButton button) {
				if(!checkForActionAlloweded()) return ;
				if(mGridOptionListner!=null)
					mGridOptionListner.onPreviousClicked();
			}
		});
		mPreviousButton.registerKeyStroke(KeyEvent.VK_UP);
		add(mPreviousButton);
	}


	PosButton mAddItemButton;
	private void createAddButton(){
		mAddItemButton=new PosButton();
		mAddItemButton.setBounds(0, 0, BUTTON_WIDTH, BUTTON_HEIGHT);
		mAddItemButton.setImage(PosResUtil.getImageIconFromResource(IMAGE_ITEM_ADD_NORMAL));
		mAddItemButton.setTouchedImage(PosResUtil.getImageIconFromResource(IMAGE_ITEM_ADD_TOUCH));
		mAddItemButton.setOnClickListner(new PosButtonListnerAdapter() {
			@Override
			public void onClicked(PosButton button) {
				if(mGridOptionListner!=null)
					mGridOptionListner.onAddClicked();

			}
		});
		mAddItemButton.registerKeyStroke(KeyEvent.VK_A, KeyEvent.CTRL_DOWN_MASK);
		mSmallButtonContainer.add(mAddItemButton);
	}

	PosButton mDeleteItemButton;
	private void createDeleteButton(){
		mDeleteItemButton=new PosButton();
		mDeleteItemButton.setBounds(0, 0, BUTTON_WIDTH, BUTTON_HEIGHT);
		mDeleteItemButton.setImage(PosResUtil.getImageIconFromResource(IMAGE_DELETE_NORMAL));
		mDeleteItemButton.setTouchedImage(PosResUtil.getImageIconFromResource(IMAGE_DELETE_TOUCH));
		mDeleteItemButton.setOnClickListner(new PosButtonListnerAdapter() {
			@Override
			public void onClicked(PosButton button) {
				if(!checkForActionAlloweded()) return ;
				if(mGridOptionListner!=null)
					mGridOptionListner.onDeleteClicked();
			}
		});
		mDeleteItemButton.setEnabled(PosEnvSettings.getInstance().getUISetting().getOrderEntryUISettings().isEnableItemDelete());
		mDeleteItemButton.registerKeyStroke(KeyEvent.VK_DELETE);
		mSmallButtonContainer.add(mDeleteItemButton);
	}

	private void createRepeatSearchButtons(){
		mEditItemButton=new PosButton();
		mEditItemButton.setPreferredSize(new Dimension((int)(BUTTON_WIDTH),BUTTON_HEIGHT));
		mEditItemButton.setImage(PosResUtil.getImageIconFromResource(IMAGE_EDIT_NORMAL));
		mEditItemButton.setTouchedImage(PosResUtil.getImageIconFromResource(IMAGE_EDIT_TOUCH));
		mEditItemButton.setOnClickListner(new PosButtonListnerAdapter() {
			@Override
			public void onClicked(PosButton button) {
				if(!checkForActionAlloweded()) return ;
				if(mGridOptionListner!=null)
					mGridOptionListner.onEditClicked();
			}
		});
		mEditItemButton.registerKeyStroke(KeyEvent.VK_ENTER,KeyEvent.CTRL_DOWN_MASK);
		mSmallButtonContainer.add(mEditItemButton);
	}
	
	private void createSearchButton(){
		
		mButtonSearch=new PosButton();
		mButtonSearch.setPreferredSize(new Dimension((int)(BUTTON_SEARCH_WIDTH),BUTTON_HEIGHT));
		mButtonSearch.setImage(PosResUtil.getImageIconFromResource(IMAGE_SEARCH_NORMAL));
		mButtonSearch.setTouchedImage(PosResUtil.getImageIconFromResource(IMAGE_SEARCH_TOUCH));

		mButtonSearch.setOnClickListner(new PosButtonListnerAdapter() {
			@Override
			public void onClicked(PosButton button) {
				if(!checkForActionAlloweded()) return ;
				ArrayList<PosOrderListItemControl> listItems=mOrderListPanel.getGridItems();
				
				PosExtSearchForm serachForm=new PosExtSearchForm(listItems);
				serachForm.seFilterDuplicate(true);
				serachForm.setSorting(1);
				serachForm.setListner(new PosItemExtSearchFormAdapter() {
					
					@Override
					public boolean onAccepted(Object sender, IPosSearchableItem item) {
						mSearchText=String.valueOf(item.getItemCode());
						doSearch();
						
						return true;
					}
				});
				PosFormUtil.showLightBoxModal(mParent,serachForm);
			}
		});
		mButtonSearch.registerKeyStroke(KeyEvent.VK_F3);
		mSmallButtonContainer.add(mButtonSearch);
	}
	
	private boolean checkForActionAlloweded(){
		
		if(mOrderListPanel==null 
				|| mOrderListPanel.getItemList() ==null 
				|| mOrderListPanel.getItemList().size()<=0) 
			return false;
		else 
			return true;
	}

	PosButton mQtyPlusButton;
	PosButton mQtyMinusButton;
	private void createPlusButton(){
		
		mQtyPlusButton=new PosButton();
		mQtyPlusButton.setPreferredSize(new Dimension(BUTTON_WIDTH,BUTTON_HEIGHT));
		mQtyPlusButton.setImage(PosResUtil.getImageIconFromResource(IMAGE_QTY_ADD_NORMAL));
		mQtyPlusButton.setTouchedImage(PosResUtil.getImageIconFromResource(IMAGE_QTY_ADD_TOUCH));
		mQtyPlusButton.setOnClickListner(new PosButtonListnerAdapter() {
			@Override
			public void onClicked(PosButton button) {
				if(!checkForActionAlloweded()) return ;
				setQtyText(true);
			}
		});
		mQtyPlusButton.registerKeyStroke(KeyEvent.VK_ADD);
		mSmallButtonContainer.add(mQtyPlusButton);
	}
	
	private void createMinusButton(){
		mQtyMinusButton=new PosButton();
		mQtyMinusButton.setPreferredSize(new Dimension(BUTTON_WIDTH,BUTTON_HEIGHT));
		mQtyMinusButton.setImage(PosResUtil.getImageIconFromResource(IMAGE_QTY_SUB_NORMAL));
		mQtyMinusButton.setTouchedImage(PosResUtil.getImageIconFromResource(IMAGE_QTY_SUB_TOUCH));
		mQtyMinusButton.setOnClickListner(new PosButtonListnerAdapter() {
			@Override
			public void onClicked(PosButton button) {
				if(!checkForActionAlloweded()) return ;
				setQtyText(false);
			}
		});
		mQtyMinusButton.registerKeyStroke(KeyEvent.VK_SUBTRACT);
		mSmallButtonContainer.add(mQtyMinusButton);
	}

	private void doSearch(){
		if(mGridOptionListner!=null)
			mGridOptionListner.onSearchClicked(mSearchText);
		mEditItemButton.requestFocus();
	}

	private void setQtyText(Boolean isIncr){
		double quantity=mGridItem.getItemQuantity();
		if(!PosOrderUtil.getStatusString(mGridItem.getOrderDetailItem()).equals("N")){
			 PosFormUtil.showInformationMessageBox(mParent, "This item has been already printed to kitchen, Please add as new item.");
		}else{
			quantity+=(isIncr)?1:(quantity>1)?-1:0;
			if(mGridOptionListner!=null)
				mGridOptionListner.onQuantityChanged(quantity);
		}
	}

	public void validateComponent() {
		// TODO Auto-generated method stub
	}

	public void setListItem(PosOrderListItemControl item){
		mGridItem=item;
		//		if(mGridItem!=null && !mGridItem.getItemCode().equals(mButtonSearch.getText()))
		//			mButtonSearch.setText("Search...");
	}

	public void setGridOptionListner(
			IPosItemGridOptionPanelListner gridOptionListner) {
		this.mGridOptionListner = gridOptionListner;
	}

	public void setReadOnly(boolean isReadOnly){
		setAddButtonEnabled(!isReadOnly);
		setQtyButtonsEnabled(!isReadOnly);
		setNumKeyPadButtonEnabled(!isReadOnly);
		setDeleteButtonEnabled(!isReadOnly);
		setSearchButtonsEnabled(!isReadOnly);
		setEditButtonEnabled(!isReadOnly);
	}
	
	public void setAddButtonEnabled(boolean enabled){
		mAddItemButton.setEnabled(enabled);
	}
	
	
	public void setEditButtonEnabled(boolean enabled){
		mEditItemButton.setEnabled(enabled);
	}
	
	public void setQtyButtonsEnabled(boolean enabled){
		mQtyPlusButton.setEnabled(enabled);
		mQtyMinusButton.setEnabled(enabled);
	}
	
	public void setSearchButtonsEnabled(boolean enabled){
		mButtonSearch.setEnabled(enabled);
		mEditItemButton.setEnabled(enabled);
	}

	public void setScrollButtonsEnabled(boolean enabled){
		mPageDownButton.setEnabled(enabled);
		mPageUpButton.setEnabled(enabled);
	}
	
	public void setNumKeyPadButtonEnabled(boolean enabled){
		mVKeyPad.setEnabled(enabled);
	}
	
	public void setDeleteButtonEnabled(boolean enabled){
		mDeleteItemButton.setEnabled(enabled);
	}

	/**
	 * @param fromIndex
	 * @param toIndex
	 * @param totalItem
	 */
	public void setDisplyOfItems(int fromIndex, int toIndex, int totalItem) {
		
		final String displayString=(totalItem<=0)?" No items to display.":
			" Displaying item(s) from "+String.valueOf(fromIndex)+" to "+String.valueOf(toIndex)+" of "+String.valueOf(totalItem);
		mDisplyOfItems.setText(displayString);
		
	}
	
}
