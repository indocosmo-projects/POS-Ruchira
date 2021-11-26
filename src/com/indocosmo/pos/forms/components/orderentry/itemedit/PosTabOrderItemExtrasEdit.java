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
import com.indocosmo.pos.common.utilities.PosNumberUtil;
import com.indocosmo.pos.common.utilities.PosOrderUtil;
import com.indocosmo.pos.common.utilities.PosSaleItemUtil;
import com.indocosmo.pos.common.utilities.PosTaxUtil;
import com.indocosmo.pos.data.beans.BeanChoice;
import com.indocosmo.pos.data.beans.BeanDiscount;
import com.indocosmo.pos.data.beans.BeanOrderDetail;
import com.indocosmo.pos.data.beans.BeanOrderDetail.OrderDetailItemType;
import com.indocosmo.pos.data.beans.BeanSaleItem;
import com.indocosmo.pos.data.beans.BeanSaleItemChoice;
import com.indocosmo.pos.data.providers.shopdb.PosChoiceItemProvider;
import com.indocosmo.pos.data.providers.shopdb.PosDiscountItemProvider;
import com.indocosmo.pos.data.providers.shopdb.PosSaleItemChoiceProvider;
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
public class PosTabOrderItemExtrasEdit extends PosTab implements IPosFormEventsListner {

	public static final String TAB_CAPTION="Extras";

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
	
	private static final int CHOICE_LIST_HDR_LABEL_WIDTH=280;
	private static final int AVAILABLE_ITEMS_HDR_LABEL_WIDTH=360;
	private static final int SELECTED_ITEMS_HDR_LABEL_WIDTH=360;
	
	private static final int AVAILABLE_ITEMS_TABLE_WIDTH=AVAILABLE_ITEMS_HDR_LABEL_WIDTH;
	private static final int CHOICE_LIST_TABLE_WIDTH=CHOICE_LIST_HDR_LABEL_WIDTH;
	private static final int SELECTED_ITEMS_TABLE_WIDTH=SELECTED_ITEMS_HDR_LABEL_WIDTH;
	
	private static final int TABLE_HEIGHT=LAYOUT_HEIGHT-HEADER_LABEL_HEIGHT-PANEL_CONTENT_V_GAP*3;
	private static final int SCROLL_WIDTH=25;
	private static final Color PANEL_BG_COLOR=new Color(78,128,188);
	private static final int ROW_HEIGHT=50;
	
	
	
	private JPanel mChoiceListPanel;
	private JPanel mAvailableItemsPanel;
	private JPanel mSelectedItemsPanel;
	private BeanSaleItem mSelectedSaleItem;
	private DefaultTableCellRenderer integerCoulmnRenderer;
	private static Font buttonFont=null;
	
	private JTable mChoiceListTable;
	private DefaultTableModel mChoiceListTableModel;
	
	private JTable mAvailableItemsTable;
	private DefaultTableModel mAvailableItemsTableModel;
	
	private JTable mSelectedItemsTable;
	
	private BeanOrderDetail mOrderDetailItem;
	
	private DefaultTableModel mSelectedItemsTableModel;
	
	private PosButton mBtnShowAll;
	
	private PosButton mBtnDeleteAll;
	
	//All saleItemChoice for the given sale item
	private ArrayList<BeanSaleItemChoice> mSaleItemChoiceList;
	//Selected subsaleitems (extras) for the given sale item	
	private HashMap<String, ArrayList<BeanOrderDetail>> mSelectedExtrasListMap;
	
	//Current selected sale items choice in the Available Choice panel list; 	
	private BeanSaleItemChoice mActiveSaleItemChoice;
	//All the subsaleitems from the active saleitem choise. these are displayed in the available item list
	private ArrayList<BeanSaleItem> mActiveAvailableItemlist;
	//All the selected subitems for the active saleitemchoice
	private ArrayList<BeanOrderDetail> mActiveSelectedItemList;
	
	private ArrayList<BeanChoice> mGlobalChoiceList;
	
	private BeanDiscount mNonDiscount;
	private BeanDiscount mFreeTopDiscount;
	
	//public  PosTabSaleItemExtrasEdit(Object parent, PosOrderDetailsForm itemControl,BeanSaleItem saleItem){
	public  PosTabOrderItemExtrasEdit(Object parent, BeanOrderDetail orderDetailItem){
		super(parent,TAB_CAPTION);
		setMnemonicChar('x');
		mOrderDetailItem=orderDetailItem;
		mSelectedSaleItem=mOrderDetailItem.getSaleItem();
		PosDiscountItemProvider dicProvider=new PosDiscountItemProvider();
		mNonDiscount=dicProvider.getNoneDiscount();
		mFreeTopDiscount=dicProvider.getFreeToppingDiscount();
		initComponent();
	}
	
	private void initComponent(){
		setLayout(new FlowLayout(FlowLayout.LEFT,1,1));
		setSize(LAYOUT_WIDTH, LAYOUT_HEIGHT);
		mSelectedExtrasListMap=getSelectedItemList(mOrderDetailItem);
		initControls();
	}
	
	/**
	 * Take a copy of the current selected list for resetting if needed
	 * */
	private HashMap<String, ArrayList<BeanOrderDetail>> getSelectedItemList(BeanOrderDetail orderDtlItem){

		HashMap<String, ArrayList<BeanOrderDetail>> newHashMaplist= new HashMap<String, ArrayList<BeanOrderDetail>>();
		HashMap<String, ArrayList<BeanOrderDetail>> curHashMaplist=mOrderDetailItem.getExtraItemList();
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
		
		createChoiceListList();
		createAvaialbleItemsList();
		createSelectedItemsList();
		
		if(mChoiceListTable.getRowCount()>0)
			mChoiceListTable.setRowSelectionInterval(0, 0);
		
		setDirty(false);
	}
	
	public void createChoiceListList(){
		
		mChoiceListPanel=new JPanel();
		mChoiceListPanel.setPreferredSize(new Dimension(CHOICE_LIST_HDR_LABEL_WIDTH,LAYOUT_HEIGHT));
		mChoiceListPanel.setLayout(null);
		add(mChoiceListPanel);
		
		JLabel labelItem=new JLabel();
		labelItem.setText("Choice List");
		labelItem.setForeground(Color.WHITE);
		labelItem.setHorizontalAlignment(SwingConstants.CENTER);
		labelItem.setVerticalAlignment(SwingConstants.CENTER);
		labelItem.setSize(CHOICE_LIST_HDR_LABEL_WIDTH, HEADER_LABEL_HEIGHT);
		labelItem.setLocation(0, 0);
		labelItem.setFont(PosFormUtil.getLabelFont());
		labelItem.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
		labelItem.setBackground(PANEL_BG_COLOR);
		labelItem.setOpaque(true);
		mChoiceListPanel.add(labelItem);
		
		int left=0;
		int top=HEADER_LABEL_HEIGHT+PANEL_CONTENT_V_GAP;
		
		mChoiceListTableModel =  PosFormUtil.getReadonlyTableModel(); ;
		mChoiceListTableModel.setColumnIdentifiers(new String[] {"Name","Free"});
		mChoiceListTable = new JTable(mChoiceListTableModel);
		mChoiceListTable.setFont(getFont().deriveFont(Font.BOLD,13f));
		
		final int colFreeItems=50;
		final int colNameWidth=CHOICE_LIST_TABLE_WIDTH-colFreeItems-SCROLL_WIDTH-2;
		final int headerHeight=25;
//		final Font boldfFont = new Font("abcd",Font.BOLD,15);
		final DefaultTableCellRenderer integerCoulmnRenderer = new DefaultTableCellRenderer();
//		final DefaultTableCellRenderer nameCoulmnRendered = new DefaultTableCellRenderer();
		integerCoulmnRenderer.setHorizontalAlignment(JLabel.RIGHT);
//		integerCoulmnRenderer.setFont(boldfFont);
//		integerCoulmnRenderer.setBackground(Color.blue);//(boldfFont);
		
		mChoiceListTable.getTableHeader().setPreferredSize(new Dimension(CHOICE_LIST_TABLE_WIDTH, headerHeight));
		mChoiceListTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		mChoiceListTable.getColumnModel().getColumn(0).setPreferredWidth(colNameWidth);
		mChoiceListTable.getColumnModel().getColumn(1).setPreferredWidth(colFreeItems);
		mChoiceListTable.getColumnModel().getColumn(1).setCellRenderer(integerCoulmnRenderer);
		
		
		JScrollPane  choiceListScrollPane = new JScrollPane(mChoiceListTable,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		choiceListScrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(SCROLL_WIDTH,0));
		choiceListScrollPane.setSize(new Dimension(CHOICE_LIST_TABLE_WIDTH,this.getHeight()-top));
		choiceListScrollPane.setLocation(left,top);
		choiceListScrollPane.getViewport().setBackground(Color.WHITE);
		final int rowHeight=ROW_HEIGHT;
		mChoiceListTable.setRowHeight(rowHeight);
		mChoiceListPanel.add(choiceListScrollPane);
		
		final ListSelectionModel selectionModel=mChoiceListTable.getSelectionModel();
		selectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		selectionModel.addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				final int selectedRow=mChoiceListTable.getSelectedRow();
				if(selectedRow>=0){
					mActiveSaleItemChoice=mSaleItemChoiceList.get(selectedRow);
					mActiveAvailableItemlist=mActiveSaleItemChoice.getChoice().getSaleItems();
					mActiveSelectedItemList=mSelectedExtrasListMap.get(mActiveSaleItemChoice.getChoice().getCode());
					populateAvailableItemList();
					populateSelectedItemList();
				}
			}
		});
		populateChoiceList();
	}
	
	private void populateChoiceList(){
		PosSaleItemChoiceProvider provider=PosSaleItemChoiceProvider.getInstance();
		try {
			mSaleItemChoiceList=new ArrayList<BeanSaleItemChoice>();
			mSaleItemChoiceList.addAll(provider.getSaleItemChoices(mSelectedSaleItem.getCode()));
//			mGlobalChoiceList=PosChoiceItemProvider.getInstance().getGlobalChoices();
			mGlobalChoiceList=getFilteredGlobalChoiceList();
			if(mGlobalChoiceList!=null){
				for(BeanChoice choiceItem:mGlobalChoiceList){
					BeanSaleItemChoice item=new BeanSaleItemChoice();
					item.setChoice(choiceItem);
					item.setFreeItemCount(0);
					item.setSaleItemCode(mSelectedSaleItem.getCode());
					item.setSaleItemId(mSelectedSaleItem.getId());
					mSaleItemChoiceList.add(item);
				}
			}
				
			
		} catch (Exception e) {
			PosLog.write(this, "populateChoiceList", e);
			PosFormUtil.showSystemError(getPosParent());
			e.printStackTrace();
		}
		if(mSaleItemChoiceList==null) return;
		for(BeanSaleItemChoice item:mSaleItemChoiceList){
			mChoiceListTableModel.addRow(new String[]{
					item.getChoice().getName(),PosNumberUtil.format(String.valueOf(item.getFreeItemCount()))
			});
		}

	}

	/**
	 * @return
	 * @throws Exception 
	 */
	private ArrayList<BeanChoice> getFilteredGlobalChoiceList() throws Exception {
		
		ArrayList<BeanChoice> globalChoiceList=PosChoiceItemProvider.getInstance().getGlobalChoices();
		ArrayList<BeanChoice> filteredChoiceList=new ArrayList<BeanChoice>();
		
		for(BeanChoice gChoiceItem:globalChoiceList){
			BeanChoice newChoice=gChoiceItem.clone();
			ArrayList<BeanSaleItem> filterdSaleItemList=new ArrayList<BeanSaleItem>();
			for(BeanSaleItem saleItem:newChoice.getSaleItems()){
				
				if(!isDuplicateItem(saleItem)){
					filterdSaleItemList.add(saleItem);
				}
			}
			if(filterdSaleItemList.size()>0){
				newChoice.setSaleItems(filterdSaleItemList);
				filteredChoiceList.add(newChoice);
			}
		}
		
		return filteredChoiceList;
	}

	/**
	 * @param gChSaleItem
	 * @return
	 */
	private boolean isDuplicateItem(BeanSaleItem gChSaleItem) {
		boolean result=false;
		for(BeanSaleItemChoice saleItemChoice:mSaleItemChoiceList){
			for(BeanSaleItem saleItem:saleItemChoice.getChoice().getSaleItems()){
				if(saleItem.getCode().equals(gChSaleItem.getCode())){
					return true;
				}
			}
		}
		return result;
	}

	public void createAvaialbleItemsList(){
		mAvailableItemsPanel=new JPanel();
		mAvailableItemsPanel.setPreferredSize(new Dimension(AVAILABLE_ITEMS_HDR_LABEL_WIDTH,LAYOUT_HEIGHT));
//		mAvailableItemsPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
		mAvailableItemsPanel.setLayout(null);
		add(mAvailableItemsPanel);
		
		JLabel labelItem=new JLabel();
		labelItem.setText("Available items");
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
//		mAvailableItemsTableModel =  PosFormUtil.getReadonlyTableModel();
		mAvailableItemsTableModel.setColumnIdentifiers(new String[] {"Name","Price",""});
//		mAvailableItemsTableModel.setColumnIdentifiers(new String[] {"Name","Price Diff"});
		mAvailableItemsTable = new JTable(mAvailableItemsTableModel);
		mAvailableItemsTable.setFont(getFont().deriveFont(Font.BOLD,13f));
		
		final int headerHeight=25;
		final int colButtonPlusWidth=50;
		final int colPriceDiffWidth=75;
		final int colNameWidth=AVAILABLE_ITEMS_TABLE_WIDTH-colPriceDiffWidth-colButtonPlusWidth-SCROLL_WIDTH-2;
//		final int colNameWidth=AVAILABLE_ITEMS_TABLE_WIDTH-colPriceDiffWidth-SCROLL_WIDTH-2;
		
		mAvailableItemsTable.getTableHeader().setPreferredSize(new Dimension(AVAILABLE_ITEMS_TABLE_WIDTH, headerHeight));
		mAvailableItemsTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		mAvailableItemsTable.getColumnModel().getColumn(0).setPreferredWidth(colNameWidth);
		mAvailableItemsTable.getColumnModel().getColumn(1).setPreferredWidth(colPriceDiffWidth);
		mAvailableItemsTable.getColumnModel().getColumn(1).setCellRenderer(integerCoulmnRenderer);
		mAvailableItemsTable.getColumnModel().getColumn(2).setPreferredWidth(colButtonPlusWidth);
		
		final ButtonColumn btnColum=new ButtonColumn(mAvailableItemsTable, mButtonAction,2);
		btnColum.setTag(ActionButtons.AddToSelectedItemList);
		btnColum.setBackgroundColor(Color.decode("#56a988"));
		btnColum.setForegroundColor(Color.WHITE);
		btnColum.setFont(buttonFont);
		
		
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
		avaiableItemsScrollPane.getViewport().setBackground(Color.WHITE);
		final int rowHeight=ROW_HEIGHT;
		mAvailableItemsTable.setRowHeight(rowHeight);
		mAvailableItemsPanel.add(avaiableItemsScrollPane);
		
	}
	
	private void populateAvailableItemList(){
		clearTableRows(mAvailableItemsTableModel);
		if(mActiveAvailableItemlist==null) return;
		for(BeanSaleItem item:mActiveAvailableItemlist){
			mAvailableItemsTableModel.addRow(new String[]{
					item.getName(),PosCurrencyUtil.format(PosSaleItemUtil.getGrandTotal(item)),"+"
			});
			
//			mAvailableItemsTableModel.addRow(new Object[]{
//					item.getName(),PosNumberUtil.formatNumber(String.valueOf(item.getFixedPrice())),PosResUtil.getImageIconFromResource("add_item.png")
//			});
//			
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
		mSelectedItemsTableModel = PosFormUtil.getReadonlyTableModel(btnColIndex);
		mSelectedItemsTableModel.setColumnIdentifiers(new String[] {"Name","Price","",""});
		mSelectedItemsTable = new JTable(mSelectedItemsTableModel);
		mSelectedItemsTable.setFont(getFont().deriveFont(Font.BOLD,13f));
		
		final int headerHeight=25;
		final int colIdWidth=0;
		final int colButtonDellWidth=50;
//		final int colQuantityWidth=50;
		final int colPriceDiffWidth=75;
		final int colNameWidth=SELECTED_ITEMS_TABLE_WIDTH-colPriceDiffWidth-colButtonDellWidth-SCROLL_WIDTH-2-colIdWidth;
//		final int colNameWidth=SELECTED_ITEMS_TABLE_WIDTH-colPriceDiffWidth-SCROLL_WIDTH-2;
		
		mSelectedItemsTable.getTableHeader().setPreferredSize(new Dimension(SELECTED_ITEMS_TABLE_WIDTH, headerHeight));
		mSelectedItemsTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		mSelectedItemsTable.getColumnModel().getColumn(0).setPreferredWidth(colNameWidth);
		mSelectedItemsTable.getColumnModel().getColumn(1).setPreferredWidth(colPriceDiffWidth);
		mSelectedItemsTable.getColumnModel().getColumn(2).setPreferredWidth(colButtonDellWidth);
		mSelectedItemsTable.getColumnModel().getColumn(1).setCellRenderer(integerCoulmnRenderer);
		mSelectedItemsTable.getColumnModel().getColumn(3).setPreferredWidth(colIdWidth);
		
		ButtonColumn btnColumn = new ButtonColumn(mSelectedItemsTable, mButtonAction,2);
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
		mSubstituteScrollPane.getViewport().setBackground(Color.WHITE);
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
				clearCurrentChoiceSelection();
				super.onClicked(button);
			}

		});
		mSelectedItemsPanel.add(mBtnDeleteAll);
		
		left+=btnWidth+PANEL_CONTENT_H_GAP;
		mBtnShowAll=new PosButton("Show All...");
		mBtnShowAll.setSize(btnWidth, SELECTED_PANEL_OPTION_BTN_HEIGHT);
		mBtnShowAll.setLocation(btnWidth+PANEL_CONTENT_H_GAP, optionBtnTop);
		mBtnShowAll.setImage("extras_delete_all_normal.png");
		mBtnShowAll
				.setTouchedImage("extras_delete_all_touch.png");
		mBtnShowAll.setOnClickListner(new PosButtonListnerAdapter() {
			/* (non-Javadoc)
			 * @see com.indocosmo.pos.forms.components.buttons.listners.PosButtonListnerAdapter#onClicked(com.indocosmo.pos.forms.components.buttons.PosButton)
			 */
			@Override
			public void onClicked(PosButton button) {
				searchSelectedItems();
				super.onClicked(button);
			}

		});
		mSelectedItemsPanel.add(mBtnShowAll);
	}


	private void clearCurrentChoiceSelection() {
		
		if(mActiveSaleItemChoice==null) return;		
		PosFormUtil.showQuestionMessageBox(
				getPosParent(),
				MessageBoxButtonTypes.YesNo,
				"Do you want to clear the selection for Choice ["+mActiveSaleItemChoice.getChoice().getName() +"]",
				
				new PosMessageBoxFormListnerAdapter() {
					@Override
					public void onYesButtonPressed() {
						mSelectedExtrasListMap.get(mActiveSaleItemChoice.getChoice().getCode()).clear();
						populateSelectedItemList();
					}
				});
	}
	
	private ArrayList<BeanOrderDetail> getAllSelectedItems(){
		
		return getAllSelectedItems(false);
	}
	
	private ArrayList<BeanOrderDetail> getAllSelectedItems(boolean filterDeleted){
		
		ArrayList<BeanOrderDetail> items=new ArrayList<BeanOrderDetail>();
		for(String key:mSelectedExtrasListMap.keySet()){
			
			for(BeanOrderDetail item:mSelectedExtrasListMap.get(key)){
				
				if(!(filterDeleted && item.isVoid()))
					items.add(item);
			}
		}
		
		return items;
	}
	
	private void searchSelectedItems(){
		
		ArrayList<BeanOrderDetail> items=getAllSelectedItems(true);
		
		if(items==null || items.size() <=0){
			PosFormUtil.showInformationMessageBox(mParent, "No extra items were selected!!!");
			return;
		}
		
		final 
		
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
		
		if(mActiveSelectedItemList==null){
			mActiveSelectedItemList=new ArrayList<BeanOrderDetail>();
			mSelectedExtrasListMap.put(mActiveSaleItemChoice.getChoice().getCode(), mActiveSelectedItemList);
		}else if(mActiveSaleItemChoice.getMaxItems()!=-1 && getNonVoidItemCount(mActiveSelectedItemList)>=mActiveSaleItemChoice.getMaxItems()){
			PosFormUtil.showInformationMessageBox(getPosParent(), "You can not add more items. Remove one or more selected items.");
			return;
		}

		try {
			
			BeanSaleItem cItem=null;
			cItem = (BeanSaleItem) mActiveAvailableItemlist.get(modelRowIndex);
			BeanOrderDetail dtlItem=PosOrderUtil.createOrderDetailSubItem(mOrderDetailItem,cItem,OrderDetailItemType.EXTRA_ITEM);
			dtlItem.setSaleItemChoice(mActiveSaleItemChoice);
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
	
		
	private void populateSelectedItemList(){
		clearTableRows(mSelectedItemsTableModel);
		if(mActiveSelectedItemList!=null && mActiveSelectedItemList.size()>0){
			final double freeItemCount=mActiveSaleItemChoice.getFreeItemCount();
			for(int i=0;i<mActiveSelectedItemList.size();i++){
				final BeanSaleItem item=(BeanSaleItem)mActiveSelectedItemList.get(i).getSaleItem();
				if(i<freeItemCount)
					item.setDiscount(mFreeTopDiscount);
				else
					item.setDiscount(mNonDiscount);

				//	Re-calculate the tax.since discount is applied
				
				PosTaxUtil.calculateTax(item);
				if(!mActiveSelectedItemList.get(i).isVoid())
					mSelectedItemsTableModel.addRow(new String[]{
							item.getName(),PosCurrencyUtil.format(PosSaleItemUtil.getGrandTotal(item)),"x",mActiveSelectedItemList.get(i).getId()});
			}
		}
	}
	
	@SuppressWarnings("serial")
	private Action mButtonAction = new AbstractAction()
	{
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
//	
//	private void deleteFromSelectedItems(int index){
//
//		if(mActiveSelectedItemList.get(index).isNewItem()){
//			mActiveSelectedItemList.get(index).setVoid(true);
//			mActiveSelectedItemList.get(index).setPrintedToKitchen(false);
//		}else
//				mActiveSelectedItemList.remove(index);
//		this.setDirty(true);
//		populateSelectedItemList();
//
//	}
	
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
				
			}else{
				mActiveSelectedItemList.remove(selectedItem);
				if(mActiveSelectedItemList.size()==0){
					mSelectedExtrasListMap.remove(mActiveSaleItemChoice.getChoice().getCode());
					mActiveSelectedItemList=null;
				}
			}
			this.setDirty(true);
			populateSelectedItemList();
		}

	}
	
//	/**
//	 * Removes the empty choices
//	 */
//	private void prepareSelectedItemListMap(){
//		
//		ArrayList<String> nullChoices=new ArrayList<String>();
//		for(String choiceCode:mSelectedExtrasListMap.keySet()){
//			if(mSelectedExtrasListMap.get(choiceCode).size()==0)
//				nullChoices.add(choiceCode);
//				
//		}
//		if(nullChoices.size()>0){
//			for(String key:nullChoices){
//				mSelectedExtrasListMap.remove(key);
//			}
//		}
//	}
	

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
				mOrderDetailItem.setExtraItemList(mSelectedExtrasListMap);
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
		mSelectedExtrasListMap=getSelectedItemList(mOrderDetailItem);
		this.setDirty(false);
		mChoiceListTable.clearSelection();
		if(mChoiceListTable.getRowCount()>0)
			mChoiceListTable.setRowSelectionInterval(0, 0);
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.pos.forms.listners.IPosFormMethodes#setReadOnly(boolean)
	 */
	@Override
	public void setReadOnly(boolean isReadOnly) {
		mSelectedItemsTable.setEnabled(!isReadOnly);
		mAvailableItemsTable.setEnabled(!isReadOnly);
//		mChoiceListTable.setEnabled(!isReadOnly);
		mBtnDeleteAll.setEnabled(!isReadOnly);
	}

	/**
	 * @return the mSelectedExtrasListMap
	 */
	public HashMap<String, ArrayList<BeanOrderDetail>> getSelectedExtrasListMap() {
//		prepareSelectedItemListMap();
		return mSelectedExtrasListMap;
	}
	
	/**
	 * Return whether choices populated
	 * @return
	 */
	public boolean hasChoiceItems(){
		
		return (mSaleItemChoiceList!=null && mSaleItemChoiceList.size()>0);
	}
	
}
