/**
 * 
 */
package com.indocosmo.pos.forms.components.orderentry.itemedit;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import com.indocosmo.pos.common.PosEnvSettings;
import com.indocosmo.pos.common.PosLog;
import com.indocosmo.pos.common.utilities.PosCurrencyUtil;
import com.indocosmo.pos.common.utilities.PosDateUtil;
import com.indocosmo.pos.common.utilities.PosFormUtil;
import com.indocosmo.pos.common.utilities.PosOrderUtil;
import com.indocosmo.pos.common.utilities.PosSaleItemUtil;
import com.indocosmo.pos.common.utilities.PosTaxUtil;
import com.indocosmo.pos.data.beans.BeanOrderDetail;
import com.indocosmo.pos.data.beans.BeanSaleItem;
import com.indocosmo.pos.data.beans.BeanSaleItemComboContent;
import com.indocosmo.pos.data.beans.BeanSaleItemComboContentSubstitute;
import com.indocosmo.pos.data.providers.shopdb.PosSaleItemComboContentProvider;
import com.indocosmo.pos.data.providers.shopdb.PosTaxAmountObject;
import com.indocosmo.pos.data.providers.shopdb.PosTaxItemProvider.TaxCalculationMethod;
import com.indocosmo.pos.forms.PosOrderListItemEditForm;
import com.indocosmo.pos.forms.components.ButtonColumn;
import com.indocosmo.pos.forms.components.buttons.PosButton;
import com.indocosmo.pos.forms.components.buttons.listners.PosButtonListnerAdapter;
import com.indocosmo.pos.forms.components.tab.PosTab;
import com.indocosmo.pos.forms.listners.IPosFormEventsListner;
import com.indocosmo.pos.forms.messageboxes.PosMessageBoxForm.MessageBoxButtonTypes;
import com.indocosmo.pos.forms.messageboxes.listners.PosMessageBoxFormListnerAdapter;
import com.indocosmo.pos.forms.search.PosExtSearchForm;

/**
 * @author joe.12.3
 *
 */
public class PosTabOrderItemComboContentEdit extends PosTab implements IPosFormEventsListner {

	public static final String TAB_CAPTION="Combo";

	private enum ActionButtons{
		AddToSelectedItemList,
		DeleteSelectedItemList
	}

	private static final int PANEL_CONTENT_H_GAP=5;
	private static final int PANEL_CONTENT_V_GAP=3;

	private static final int SELECTED_PANEL_OPTION_BTN_HEIGHT=46;

	public static final int LAYOUT_HEIGHT=455;
	public static final int LAYOUT_WIDTH=PosOrderListItemEditForm.LAYOUT_WIDTH; //790;	
	private static final int HEADER_LABEL_HEIGHT=25;

	private static final int COMBO_CONTENT_LIST_HDR_LABEL_WIDTH=280;
	private static final int AVAILABLE_ITEMS_HDR_LABEL_WIDTH=360;
	private static final int SELECTED_ITEMS_HDR_LABEL_WIDTH=360;

	private static final int AVAILABLE_ITEMS_TABLE_WIDTH=AVAILABLE_ITEMS_HDR_LABEL_WIDTH;
	private static final int COMBO_CONTENT_LIST_TABLE_WIDTH=COMBO_CONTENT_LIST_HDR_LABEL_WIDTH;
	private static final int SELECTED_ITEMS_TABLE_WIDTH=SELECTED_ITEMS_HDR_LABEL_WIDTH;

	private static final int TABLE_HEIGHT=LAYOUT_HEIGHT-HEADER_LABEL_HEIGHT-PANEL_CONTENT_V_GAP*3;
	private static final int SCROLL_WIDTH=25;
	private static final Color PANEL_BG_COLOR=new Color(78,128,188);
	private static final int ROW_HEIGHT=50;


	private JPanel mComboContentListPanel;
	private JPanel mAvailableItemsPanel;
	private JPanel mSelectedItemsPanel;
	private BeanSaleItem mSelectedSaleItem;
	
	private static Font buttonFont=null;

	private JTable mComboContentListTable;
	private DefaultTableModel mComboContentListTableModel;

	private JTable mAvailableItemsTable;
	private DefaultTableModel mAvailableItemsTableModel;

	private JTable mSelectedItemsTable;
	private DefaultTableModel mSelectedItemsTableModel;
	
	private PosButton mBtnDeleteAll;

	private BeanOrderDetail mOrderDetailItem;

	//All ComboContents for the given sale item
	private ArrayList<BeanSaleItemComboContent> mComboContentList;
	// Selected sub-saleitems (substitutes) for the given sale item	
	// key combo content code
	private HashMap<String, ArrayList<BeanOrderDetail>> mSelectedItemsListMap;
	//Current selected combo content in the Available combo content list; 	
	private BeanSaleItemComboContent mActiveComboContent;
	//All the substitutes from the active  combo content. these are displayed in the available item list
	private ArrayList<BeanSaleItemComboContentSubstitute> mActiveAvailableItemlist;
	//All the selected substitutes for the active combo content
	private ArrayList<BeanOrderDetail> mActiveSelectedItemList;
	private DefaultTableCellRenderer integerCoulmnRenderer;


	//public  PosTabSaleItemExtrasEdit(Object parent, PosOrderDetailsForm itemControl,BeanSaleItem saleItem){
	public  PosTabOrderItemComboContentEdit(Object parent, BeanOrderDetail orderDetailItem){
		super(parent,TAB_CAPTION);
		setMnemonicChar('b');
		mOrderDetailItem=orderDetailItem;
		mSelectedSaleItem=mOrderDetailItem.getSaleItem();
		initComponent();
	}

	private void initComponent(){
		setLayout(new FlowLayout(FlowLayout.LEFT,1,1));
		setSize(LAYOUT_WIDTH, LAYOUT_HEIGHT);
		mSelectedItemsListMap=getSelectedItemList(mOrderDetailItem);
		initControls();
	}
	/**
	 * Take a copy of the current selected list for resetting if needed
	 * */
	private HashMap<String, ArrayList<BeanOrderDetail>> getSelectedItemList(BeanOrderDetail orderDtlItem) {

		HashMap<String, ArrayList<BeanOrderDetail>> newHashMaplist= new HashMap<String, ArrayList<BeanOrderDetail>>();
		HashMap<String, ArrayList<BeanOrderDetail>> curHashMaplist=mOrderDetailItem.getComboSubstitutes();
		try{
			if(curHashMaplist!=null){
				for(String key:curHashMaplist.keySet()){
					ArrayList<BeanOrderDetail> subItemList=curHashMaplist.get(key);
					ArrayList<BeanOrderDetail> newItemList=new ArrayList<BeanOrderDetail> ();
					if(subItemList!=null){
						for(BeanOrderDetail dtlItem:subItemList){
							newItemList.add(dtlItem.clone());
						}
					}
					newHashMaplist.put(key,newItemList);
				}
			}
		}catch (Exception e){
			PosLog.write(this, "getSelectedItemList", e);
			PosFormUtil.showErrorMessageBox(mParent, "Failed to load the item list. Please contact administrator.");
		}
		return newHashMaplist;
	}

	private void initControls(){

		integerCoulmnRenderer = new DefaultTableCellRenderer();
		integerCoulmnRenderer.setHorizontalAlignment(JLabel.RIGHT);
		if(buttonFont==null)
			buttonFont=getFont().deriveFont(Font.BOLD,18f);
		
		createComboContentList();
		createAvaialbleItemsList();
		createSelectedItemsList();

		if(mComboContentListTable.getRowCount()>0)
			mComboContentListTable.setRowSelectionInterval(0, 0);
		
		setDirty(false);
	}

	public void createComboContentList(){

		mComboContentListPanel=new JPanel();
		mComboContentListPanel.setPreferredSize(new Dimension(COMBO_CONTENT_LIST_HDR_LABEL_WIDTH,LAYOUT_HEIGHT));
		mComboContentListPanel.setLayout(null);
		add(mComboContentListPanel);

		JLabel labelItem=new JLabel();
		labelItem.setText("Combo Contents");
		labelItem.setForeground(Color.WHITE);
		labelItem.setHorizontalAlignment(SwingConstants.CENTER);
		labelItem.setVerticalAlignment(SwingConstants.CENTER);
		labelItem.setSize(COMBO_CONTENT_LIST_HDR_LABEL_WIDTH, HEADER_LABEL_HEIGHT);
		labelItem.setLocation(0, 0);
		labelItem.setFont(PosFormUtil.getLabelFont());
		labelItem.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
		labelItem.setBackground(PANEL_BG_COLOR);
		labelItem.setOpaque(true);
		mComboContentListPanel.add(labelItem);



		int left=0;
		int top=HEADER_LABEL_HEIGHT+PANEL_CONTENT_V_GAP;

		mComboContentListTableModel =  PosFormUtil.getReadonlyTableModel();
//		mComboContentListTableModel.setColumnIdentifiers(new String[] {"Name","Qty"});
		mComboContentListTableModel.setColumnIdentifiers(new String[] {"Name"});
		mComboContentListTable = new JTable(mComboContentListTableModel);
		mComboContentListTable.setFont(getFont().deriveFont(Font.BOLD,13f));

//		final int colQty=50;
//		final int colNameWidth=COMBO_CONTENT_LIST_TABLE_WIDTH-colQty-SCROLL_WIDTH-2;
		final int colNameWidth=COMBO_CONTENT_LIST_TABLE_WIDTH-SCROLL_WIDTH-2;
		final int headerHeight=25;

		mComboContentListTable.getTableHeader().setPreferredSize(new Dimension(COMBO_CONTENT_LIST_TABLE_WIDTH, headerHeight));
		mComboContentListTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		mComboContentListTable.getColumnModel().getColumn(0).setPreferredWidth(colNameWidth);
//		mComboContentListTable.getColumnModel().getColumn(1).setPreferredWidth(colQty);
//		mComboContentListTable.getColumnModel().getColumn(1).setCellRenderer(integerCoulmnRenderer);

		JScrollPane  comboContentListScrollPane = new JScrollPane(mComboContentListTable,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		comboContentListScrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(SCROLL_WIDTH,0));
		comboContentListScrollPane.setSize(new Dimension(COMBO_CONTENT_LIST_TABLE_WIDTH,this.getHeight()-top));
		comboContentListScrollPane.setLocation(left,top);

		final int rowHeight=ROW_HEIGHT;
		mComboContentListTable.setRowHeight(rowHeight);
		mComboContentListPanel.add(comboContentListScrollPane);

		final ListSelectionModel selectionModel=mComboContentListTable.getSelectionModel();
		selectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		selectionModel.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				final int selectedRow=mComboContentListTable.getSelectedRow();
				if(selectedRow>=0){
					mActiveComboContent=mComboContentList.get(selectedRow);
					mActiveAvailableItemlist=mActiveComboContent.getContentItems();
					mActiveSelectedItemList=mSelectedItemsListMap.get(mActiveComboContent.getCode());
					if(mActiveSelectedItemList==null){
						mActiveSelectedItemList=new ArrayList<BeanOrderDetail>();
						mSelectedItemsListMap.put(mActiveComboContent.getCode(), mActiveSelectedItemList);
					}
					populateAvailableItemList();
					populateSelectedItemList();
				}
			}
		});
		populateComboContentList();
	}


	private void populateComboContentList(){
		PosSaleItemComboContentProvider provider=PosSaleItemComboContentProvider.getInstance();
		try {
			mComboContentList=new ArrayList<BeanSaleItemComboContent>();
			mComboContentList.addAll(provider.getComboContentList(mSelectedSaleItem.getCode()));

		} catch (Exception e) {
			PosLog.write(this, "populateComboContentList", e);
			PosFormUtil.showSystemError(getPosParent());
			e.printStackTrace();
		}
		if(mComboContentList!=null){
			for(BeanSaleItemComboContent ccItem:mComboContentList){
//				mComboContentListTableModel.addRow(new String[]{
//						ccItem.getName(),PosNumberUtil.formatNumber(String.valueOf(ccItem.getMaxItems()))
				mComboContentListTableModel.addRow(new String[]{
						ccItem.getName()
				});

			}
		}

	}

	public void createAvaialbleItemsList(){

		mAvailableItemsPanel=new JPanel();
		mAvailableItemsPanel.setPreferredSize(new Dimension(AVAILABLE_ITEMS_HDR_LABEL_WIDTH,LAYOUT_HEIGHT));
		//		mAvailableItemsPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
		mAvailableItemsPanel.setLayout(null);
		add(mAvailableItemsPanel);

		JLabel labelItem=new JLabel();
		labelItem.setText("Substitutions");
		labelItem.setForeground(Color.WHITE);
		labelItem.setHorizontalAlignment(SwingConstants.CENTER);
		labelItem.setVerticalAlignment(SwingConstants.CENTER);
		labelItem.setSize(AVAILABLE_ITEMS_HDR_LABEL_WIDTH, HEADER_LABEL_HEIGHT);
		labelItem.setLocation(0, 0);
		labelItem.setFont(PosFormUtil.getLabelFont());
		labelItem.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
		labelItem.setBackground(PANEL_BG_COLOR);
		labelItem.setOpaque(true);
		mAvailableItemsPanel.add(labelItem);

		int left=0;
		int top=HEADER_LABEL_HEIGHT+PANEL_CONTENT_V_GAP;
		final int[] btnColIndex={2};
		mAvailableItemsTableModel =  PosFormUtil.getReadonlyTableModel(btnColIndex);
		mAvailableItemsTableModel.setColumnIdentifiers(new String[] {"Name","Price",""});
		mAvailableItemsTable = new JTable(mAvailableItemsTableModel);
		mAvailableItemsTable.setFont(getFont().deriveFont(Font.BOLD,13f));

		final int headerHeight=25;
		final int colButtonPlusWidth=50;
		final int colPriceDiffWidth=75;
		final int colNameWidth=AVAILABLE_ITEMS_TABLE_WIDTH-colPriceDiffWidth-colButtonPlusWidth-SCROLL_WIDTH-2;

		mAvailableItemsTable.getTableHeader().setPreferredSize(new Dimension(AVAILABLE_ITEMS_TABLE_WIDTH, headerHeight));
		mAvailableItemsTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		mAvailableItemsTable.getColumnModel().getColumn(0).setPreferredWidth(colNameWidth);
		mAvailableItemsTable.getColumnModel().getColumn(1).setPreferredWidth(colPriceDiffWidth);
		mAvailableItemsTable.getColumnModel().getColumn(1).setCellRenderer(integerCoulmnRenderer);
		mAvailableItemsTable.getColumnModel().getColumn(2).setPreferredWidth(colButtonPlusWidth);

		ButtonColumn btnColum=new ButtonColumn(mAvailableItemsTable, mButtonAction,2);
		btnColum.setTag(ActionButtons.AddToSelectedItemList);
		btnColum.setBackgroundColor(Color.decode("#56a988"));
		btnColum.setForegroundColor(Color.WHITE);
		btnColum.setFont(buttonFont);
//
//		mAvailableItemsTable.addMouseListener(new MouseAdapter() {
//			@Override
//			public void mousePressed(MouseEvent e) {
//
//				if(e.getClickCount()==2 && !e.isConsumed() && !isReadOnly()){
//					final int modelRowIndex=mAvailableItemsTable.getSelectedRow();
//					addToSelectedItemList(modelRowIndex);
//					e.consume();
//				}
//			}
//		});

		JScrollPane avaiableItemsScrollPane = new JScrollPane(mAvailableItemsTable,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		avaiableItemsScrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(SCROLL_WIDTH,0));
		avaiableItemsScrollPane.setSize(new Dimension(AVAILABLE_ITEMS_TABLE_WIDTH,this.getHeight()-top));
		avaiableItemsScrollPane.setLocation(left,top);

		final int rowHeight=ROW_HEIGHT;
		mAvailableItemsTable.setRowHeight(rowHeight);
		mAvailableItemsPanel.add(avaiableItemsScrollPane);

	}

	private void populateAvailableItemList(){
		clearTableRows(mAvailableItemsTableModel);
		if(mActiveAvailableItemlist==null) return;

		for(BeanSaleItemComboContentSubstitute item:mActiveAvailableItemlist){
			
			double taxAmt=0;
			if (item.getSaleItem().getTax()!=null && 
					item.getSaleItem().getTaxCalculationMethod().equals(TaxCalculationMethod.ExclusiveOfTax)){
				
				final PosTaxAmountObject taxAmtObj=PosTaxUtil.calculateTaxes(item.getSaleItem().getTax(), item.getPriceDifferance());
				taxAmt=taxAmtObj.getTaxOneAmount()+ taxAmtObj.getTaxTwoAmount() + taxAmtObj.getTaxThreeAmount() + 
					   taxAmtObj.getServiceTaxAmount()+taxAmtObj.getGSTAmount();
			}
			 
			mAvailableItemsTableModel.addRow(new String[]{
					item.getSaleItem().getName(),PosCurrencyUtil.format(item.getPriceDifferance()+taxAmt),"+"
			});
		}
	}

	/**
	 * @param mAvailableItemsTableModel2
	 */
	private void clearTableRows(DefaultTableModel tableModel) {
		final int rowCount=tableModel.getRowCount();
		for(int i=0; i<rowCount;i++)
			tableModel.removeRow(0);
	}

	public void createSelectedItemsList(){

		mSelectedItemsPanel=new JPanel();
		mSelectedItemsPanel.setPreferredSize(new Dimension(SELECTED_ITEMS_HDR_LABEL_WIDTH,LAYOUT_HEIGHT));
		//		mSelectedItemsPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
		mSelectedItemsPanel.setLayout(null);
		add(mSelectedItemsPanel);

		JLabel labelItem=new JLabel();
		labelItem.setText("Selected Items");
		labelItem.setForeground(Color.WHITE);
		labelItem.setHorizontalAlignment(SwingConstants.CENTER);
		labelItem.setVerticalAlignment(SwingConstants.CENTER);
		labelItem.setSize(SELECTED_ITEMS_HDR_LABEL_WIDTH, HEADER_LABEL_HEIGHT);
		labelItem.setLocation(0, 0);
		labelItem.setFont(PosFormUtil.getLabelFont());
		labelItem.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
		labelItem.setBackground(PANEL_BG_COLOR);
		labelItem.setOpaque(true);
		mSelectedItemsPanel.add(labelItem);

		int left=0;
		int top=HEADER_LABEL_HEIGHT+PANEL_CONTENT_V_GAP;

		final int[] btnColIndex={2};
		mSelectedItemsTableModel =  PosFormUtil.getReadonlyTableModel(btnColIndex); ;
		mSelectedItemsTableModel.setColumnIdentifiers(new String[] {"Name","Price","",""});
		mSelectedItemsTable = new JTable(mSelectedItemsTableModel);
		mSelectedItemsTable.setFont(getFont().deriveFont(Font.BOLD,13f));

		final int headerHeight=25;
		final int colIdWidth=0;
		final int colButtonDellWidth=50;
		final int colQuantityWidth=50;
		final int colPriceDiffWidth=75;
		final int colNameWidth=SELECTED_ITEMS_TABLE_WIDTH-colPriceDiffWidth-colQuantityWidth-SCROLL_WIDTH-2-colIdWidth;

		mSelectedItemsTable.getTableHeader().setPreferredSize(new Dimension(SELECTED_ITEMS_TABLE_WIDTH, headerHeight));
		mSelectedItemsTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		mSelectedItemsTable.getColumnModel().getColumn(0).setPreferredWidth(colNameWidth);
		mSelectedItemsTable.getColumnModel().getColumn(1).setPreferredWidth(colPriceDiffWidth);
		mSelectedItemsTable.getColumnModel().getColumn(1).setCellRenderer(integerCoulmnRenderer);
		mSelectedItemsTable.getColumnModel().getColumn(2).setPreferredWidth(colButtonDellWidth);
		mSelectedItemsTable.getColumnModel().getColumn(3).setPreferredWidth(colIdWidth);

		ButtonColumn btnColumn =new ButtonColumn(mSelectedItemsTable, mButtonAction,2);
		btnColumn.setTag(ActionButtons.DeleteSelectedItemList);
		btnColumn.setBackgroundColor(Color.decode("#8a011a"));
		btnColumn.setForegroundColor(Color.WHITE);
		btnColumn.setFont(buttonFont);

//		mSelectedItemsTable.addMouseListener(new MouseAdapter() {
//			@Override
//			public void mousePressed(MouseEvent e) {
//
//				if(e.getClickCount()==2 && !e.isConsumed() && !isReadOnly()){
//					final int modelRowIndex=mSelectedItemsTable.getSelectedRow();
//					deleteFromSelectedItems(modelRowIndex);
//					e.consume();
//				}
//			}
//		});

		final int tableHeight=this.getHeight()-top-SELECTED_PANEL_OPTION_BTN_HEIGHT;
		JScrollPane mSubstituteScrollPane = new JScrollPane(mSelectedItemsTable,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		mSubstituteScrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(SCROLL_WIDTH,0));
		mSubstituteScrollPane.setSize(new Dimension(SELECTED_ITEMS_TABLE_WIDTH,tableHeight));
		mSubstituteScrollPane.setLocation(left,top);

		final int rowHeight=ROW_HEIGHT;
		mSelectedItemsTable.setRowHeight(rowHeight);
		mSelectedItemsPanel.add(mSubstituteScrollPane);

		final int btnWidth=(SELECTED_ITEMS_TABLE_WIDTH-PANEL_CONTENT_H_GAP) /2;
		final int optionBtnTop=top+tableHeight+PANEL_CONTENT_V_GAP;

		mBtnDeleteAll=new PosButton("Clear");
		mBtnDeleteAll.setSize(btnWidth, SELECTED_PANEL_OPTION_BTN_HEIGHT);
		mBtnDeleteAll.setLocation(left, optionBtnTop);
		mBtnDeleteAll.setImage("extras_delete_all_normal.png");
		mBtnDeleteAll
		.setTouchedImage("extras_delete_all_touch.png");
		mBtnDeleteAll.setOnClickListner(new PosButtonListnerAdapter() {
			/* (non-Javadoc)
			 * @see com.indocosmo.pos.forms.components.buttons.listners.PosButtonListnerAdapter#onClicked(com.indocosmo.pos.forms.components.buttons.PosButton)
			 */
			@Override
			public void onClicked(PosButton button) {
				clearSelectedContents();
				super.onClicked(button);
			}

		});
		mSelectedItemsPanel.add(mBtnDeleteAll);

		left+=btnWidth+PANEL_CONTENT_H_GAP;
		final PosButton btnShowAll=new PosButton("Show All...");
		btnShowAll.setSize(btnWidth, SELECTED_PANEL_OPTION_BTN_HEIGHT);
		btnShowAll.setLocation(btnWidth+PANEL_CONTENT_H_GAP, optionBtnTop);
		btnShowAll.setImage("extras_delete_all_normal.png");
		btnShowAll
		.setTouchedImage("extras_delete_all_touch.png");
		btnShowAll.setOnClickListner(new PosButtonListnerAdapter() {
			/* (non-Javadoc)
			 * @see com.indocosmo.pos.forms.components.buttons.listners.PosButtonListnerAdapter#onClicked(com.indocosmo.pos.forms.components.buttons.PosButton)
			 */
			@Override
			public void onClicked(PosButton button) {
				searchSelectedItems();
				super.onClicked(button);
			}

		});
		mSelectedItemsPanel.add(btnShowAll);
	}


	private void clearSelectedContents() {

		PosFormUtil.showQuestionMessageBox(
				getPosParent(),
				MessageBoxButtonTypes.YesNo,
				"Do you want to clear the selection for the content item ["+mActiveComboContent.getName() +"]",

				new PosMessageBoxFormListnerAdapter() {
					@Override
					public void onYesButtonPressed() {
						mSelectedItemsListMap.get(mActiveComboContent.getCode()).clear();
						populateSelectedItemList();
					}
				});
	}
	
	private ArrayList<BeanOrderDetail> getAllSelectedItems(){
		
		return getAllSelectedItems(false);
	}
//	
//	private ArrayList<BeanOrderDetail> getAllSelectedItems(boolean filterDeleted){
//		
//		ArrayList<BeanOrderDetail> items=new ArrayList<BeanOrderDetail>();
//		for(String key:mSelectedExtrasListMap.keySet()){
//			
//			for(BeanOrderDetail item:mSelectedExtrasListMap.get(key)){
//				
//				if(!(filterDeleted && item.isVoid()))
//					items.add(item);
//			}
//		}
//		
//		return items;
//	}

	private ArrayList<BeanOrderDetail> getAllSelectedItems(boolean filterDeleted){
		
		ArrayList<BeanOrderDetail> items=new ArrayList<BeanOrderDetail>();
		for(String key:mSelectedItemsListMap.keySet()){
			
			for(BeanOrderDetail item:mSelectedItemsListMap.get(key)){
				
				if(!(filterDeleted && item.isVoid()))
					items.add(item);
			}
		}

		return items;
	}

	private void searchSelectedItems(){

		ArrayList<BeanOrderDetail> items=getAllSelectedItems(true);

		if(items==null || items.size() <=0){
			PosFormUtil.showInformationMessageBox(mParent, "No items were selected!!!");
			return;
		}

		PosExtSearchForm serachForm=new PosExtSearchForm(items);
		serachForm.setAllowRowSelection(false);
//		serachForm.setListner(new IPosItemExtSearchFormListener() {
//
//			@Override
//			public void onCancel() {
//				// TODO Auto-generated method stub
//
//			}
//
//			@Override
//			public void onAccepted(IPosSearchableItem item) {
//
//			}
//		});
		PosFormUtil.showLightBoxModal(getPosParent(),serachForm);
	}

	private void addToSelectedItemList(int modelRowIndex){
		
		final double allowedQty=mActiveAvailableItemlist.get(modelRowIndex).getQuantity();
		
		final BeanOrderDetail dtlItem=PosOrderUtil.createOrderDetailItemForCombo(mOrderDetailItem, mActiveComboContent,mActiveAvailableItemlist.get(modelRowIndex));

		if(allowedQty!=0 && getNonVoidItemCount(mActiveSelectedItemList,dtlItem)>=allowedQty){
			PosFormUtil.showInformationMessageBox(getPosParent(), "You can not add more items. Remove one or more selected items.");
			return;
		}

		try {
//			BeanOrderDetail dtlItem=PosOrderUtil.createOrderDetailItemForCombo(mOrderDetailItem, mActiveComboContent,mActiveAvailableItemlist.get(modelRowIndex));
			mActiveSelectedItemList.add(dtlItem);
			populateSelectedItemList();
			mSelectedItemsTable.setRowSelectionInterval(getNonVoidItemCount(mActiveSelectedItemList)-1, getNonVoidItemCount(mActiveSelectedItemList)-1);
			this.setDirty(true);
		} catch (Exception e) {
			PosLog.write(this, "addToSelectedItemList", e);
			PosFormUtil.showSystemError(getPosParent());
		}
	}

	private int getNonVoidItemCount(ArrayList<BeanOrderDetail> itemList){
		int cnt=0;
		if(itemList!=null){
			for(BeanOrderDetail item:itemList){
				if(!item.isVoid())
					cnt++;
			}
		}
		return cnt;
	}
	/**
	 * @param itemList
	 * @param selItem
	 * @return
	 */
	private int getNonVoidItemCount(ArrayList<BeanOrderDetail> itemList,BeanOrderDetail selItem){
		int cnt=0;
		if(itemList!=null){
			for(BeanOrderDetail item:itemList){
				if(!item.isVoid() && item.getSaleItem().getCode().equals(selItem.getSaleItem().getCode()))
					cnt++;
			}
		}
		return cnt;
	}

	private void populateSelectedItemList(){
		clearTableRows(mSelectedItemsTableModel);
		for(int i=0;i<mActiveSelectedItemList.size();i++){
			final BeanSaleItem item=mActiveSelectedItemList.get(i).getSaleItem();
			if(!mActiveSelectedItemList.get(i).isVoid())
				mSelectedItemsTableModel.addRow(new String[]{
						item.getName(),PosCurrencyUtil.format(PosSaleItemUtil.getGrandTotal(item)),"x",mActiveSelectedItemList.get(i).getId()});
		}
	}

	private Action mButtonAction = new AbstractAction()
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent e)
		{
			ButtonColumn btCol=(ButtonColumn)e.getSource();
			ActionButtons ab=(ActionButtons )btCol.getTag();
			final int modelRowIndex = Integer.valueOf( e.getActionCommand());
			switch (ab) {
			case AddToSelectedItemList:
				addToSelectedItemList(modelRowIndex);
				break;
			case DeleteSelectedItemList:
				deleteFromSelectedItems(modelRowIndex);
				break;

			default:
				break;
			}

		}
	};
	/**
	 * Since items displayed on the table and actual items list differs in count due to void items,
	 * find the actual item by item id
	 * @param rowindex
	 * @return
	 */
	private BeanOrderDetail getSelectedItemByRowIndex(int rowindex){
		BeanOrderDetail selectedItem=null;
		final String selId=(String)mSelectedItemsTable.getModel().getValueAt(rowindex, 3);
		for(BeanOrderDetail item: mActiveSelectedItemList){
			
			if(item.getId().equals(selId)){
				selectedItem=item;
			}
		}
		return selectedItem;
	}

	private void deleteFromSelectedItems(int index){
		
		BeanOrderDetail selectedItem =getSelectedItemByRowIndex(index);
		if(selectedItem!=null){
			if(!selectedItem.isNewItem()){
				selectedItem.setVoid(true);
				selectedItem.setPrintedToKitchen(false);
				selectedItem.setVoidBy(PosEnvSettings.getInstance().getCashierShiftInfo()
						.getCashierInfo());
				selectedItem.setVoidAt(PosEnvSettings.getInstance().getPosDate());
				selectedItem.setVoidTime(PosDateUtil.getDateTime());
			}else
				mActiveSelectedItemList.remove(selectedItem);
			this.setDirty(true);
			populateSelectedItemList();
		}

	}


	@Override
	public boolean onValidating() {
		return true;
	}

	@Override
	public boolean onOkButtonClicked() {
		
		boolean result=true;
		if(isDirty()){
			result= onValidating();
			if(result){
				mOrderDetailItem.setComboSubstitutes(mSelectedItemsListMap);
				mOrderDetailItem.setDirty(isDirty());
				mOrderDetailItem.setKitchenDirty(isDirty());
			}
		}
		
		return result;
	}

	@Override
	public boolean onCancelButtonClicked() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onResetButtonClicked() {
		mSelectedItemsListMap=getSelectedItemList(mOrderDetailItem);
		this.setDirty(false);
		mComboContentListTable.clearSelection();
		if(mComboContentListTable.getRowCount()>0)
			mComboContentListTable.setRowSelectionInterval(0, 0);
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.pos.forms.listners.IPosFormMethodes#setReadOnly(boolean)
	 */
	@Override
	public void setReadOnly(boolean isReadOnly) {
		mSelectedItemsTable.setEnabled(!isReadOnly);
		mAvailableItemsTable.setEnabled(!isReadOnly);
//		mComboContentListTable.setEnabled(!isReadOnly);
		mBtnDeleteAll.setEnabled(!isReadOnly);
	}

}
