/**
 * 
 */
package com.indocosmo.pos.forms.split.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.RootPaneContainer;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import com.indocosmo.pos.common.PosEnvSettings;
import com.indocosmo.pos.common.utilities.PosCurrencyUtil;
import com.indocosmo.pos.common.utilities.PosFormUtil;
import com.indocosmo.pos.data.beans.BeanOrderDetail;
import com.indocosmo.pos.data.beans.BeanOrderSplitDetail;
import com.indocosmo.pos.forms.components.jtable.CheckedSelectionTableCellRender;
import com.indocosmo.pos.forms.components.jtable.NoBorderTableCellSelectionRender;
import com.indocosmo.pos.forms.split.listners.IPosSplitListItemPanelListener;

/**
 * @author jojesh-13.2
 *
 */
public class SplitItemListTablePanel extends JPanel {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int ROW_HEIGHT = 40;
	private static final int SCROLL_WIDTH = 25;
	private static final int FIELD_COUNT=5;
	//	private static final int SCROLL_HEIGHT = 20;

	/*
	 * Column header positions
	 */
	//	private static final int LIST_TABLE_COL_ITEM_INDEX=0;
	private static final int LIST_TABLE_COL_SEL_BUTTON_INDEX=0;
	private static final int LIST_TABLE_COL_ITEM_NAME_INDEX=1;
	private static final int LIST_TABLE_COL_TABLE_SEAT_INDEX=2;
	private static final int LIST_TABLE_COL_BILL_NAME_INDEX=3;
	private static final int LIST_TABLE_COL_AMOUNT_INDEX=4;

	/*
	 * Column width
	 */
	private static final int LIST_TABLE_COL_INDEX_WIDTH=0;
	private static final int LIST_TABLE_COL_SEL_BUTTON_WIDTH=40;
	private static final int LIST_TABLE_COL_TABLE_SEAT_WIDTH=100;
	private static final int LIST_TABLE_COL_BILL_NAME_WIDTH=150;
	
	private static final int LIST_TABLE_COL_AMOUNT_WIDTH=150;
	
	
	private static final int LIST_TABLE_HEADER_HEIGHT=30;

	private DefaultTableModel mItemListTableModel;
	private JTable 	mItemListTable; 
//	private BillSplitMethod mBillSplitMethod;
	private boolean isRowsSelectable=false;
	private int mSelectedItemIndex=-1;
	private ArrayList<BeanOrderSplitDetail> splitItems;
	
	private boolean isBillNameInfoVisible=true;
	private boolean isTableSeatInfoVisible=true;
	
	private IPosSplitListItemPanelListener listener;
	
	/**
	 * 
	 */
	public SplitItemListTablePanel(RootPaneContainer parent, int width, int height ,boolean showBillName,boolean showTableSeatInfo) {

		isTableSeatInfoVisible=showTableSeatInfo;
		isBillNameInfoVisible=showBillName;
		initForm(parent,width,height);
		
	}
	
	/**
	 * 
	 */
	public SplitItemListTablePanel(RootPaneContainer parent, int width, int height ,boolean showBillName) {

		isBillNameInfoVisible=showBillName;
		initForm(parent,width,height);
		
	}

	/**
	 * 
	 */
	public SplitItemListTablePanel(RootPaneContainer parent, int width, int height ) {

		initForm(parent,width,height);
		
	}
	
	/**
	 * @param parent
	 * @param width
	 * @param height
	 */
	private void initForm(RootPaneContainer parent, int width, int height ){
	
		this.setLayout(null);
		this.setSize(new Dimension(width,height));
		this.setPreferredSize(new Dimension(width,height));
		mItemListTable=createItemListTablePanel(this);
		
	}

	/**
	 * @returns the table column width
	 */
	private int getItemNameColumWidth(){

		return getWidth()-
				-LIST_TABLE_COL_INDEX_WIDTH
				-LIST_TABLE_COL_AMOUNT_WIDTH
				-getBillNameColumWidth()
				-getTableSeatColumWidth()
				-LIST_TABLE_COL_SEL_BUTTON_WIDTH
				-SCROLL_WIDTH
				-2;

	}
	/**
	 * Creates the table
	 * @param tableListPanel
	 * @return
	 */
	private JTable createItemListTablePanel(JPanel tableListPanel) {

		final DefaultTableCellRenderer rightAlign=new NoBorderTableCellSelectionRender();
		final DefaultTableCellRenderer centerAlign=new NoBorderTableCellSelectionRender();
		final CheckedSelectionTableCellRender selectionCell=new CheckedSelectionTableCellRender();
		final NoBorderTableCellSelectionRender noBorderCell=new NoBorderTableCellSelectionRender();
		rightAlign.setHorizontalAlignment(JLabel.RIGHT);
		centerAlign.setHorizontalAlignment(JLabel.CENTER);

		mItemListTableModel =  PosFormUtil.getReadonlyTableModel(); ;
		mItemListTableModel.setColumnIdentifiers(getColumnNames());

		final JTable itemListTable = new JTable(mItemListTableModel);
		itemListTable.setRowSorter(new TableRowSorter<TableModel>(mItemListTableModel));
//		itemListTable.setFont(getFont().deriveFont(18f));
		itemListTable.setFont(PosFormUtil.getTableCellFont());
		itemListTable.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		itemListTable.getTableHeader().setPreferredSize(new Dimension(tableListPanel.getWidth(), LIST_TABLE_HEADER_HEIGHT));
		itemListTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		itemListTable.getTableHeader().setReorderingAllowed(false);
		itemListTable.getTableHeader().setResizingAllowed(false);
		itemListTable.getTableHeader().setFont(PosFormUtil.getTableHeadingFont());

		setColumnWidth(itemListTable);
		
		itemListTable.getColumnModel().getColumn(LIST_TABLE_COL_SEL_BUTTON_INDEX).setCellRenderer(selectionCell);
		itemListTable.getColumnModel().getColumn(LIST_TABLE_COL_ITEM_NAME_INDEX).setCellRenderer(noBorderCell);
		itemListTable.getColumnModel().getColumn(LIST_TABLE_COL_AMOUNT_INDEX).setCellRenderer(rightAlign);
		itemListTable.getColumnModel().getColumn(LIST_TABLE_COL_TABLE_SEAT_INDEX).setCellRenderer(centerAlign);
		itemListTable.getColumnModel().getColumn(LIST_TABLE_COL_BILL_NAME_INDEX).setCellRenderer(noBorderCell);
		
		itemListTable.addMouseListener(itemListTableMouseAdapter);
		itemListTable.addKeyListener(itemListTableKeyListener);
		itemListTable.getSelectionModel().addListSelectionListener(itemListTableListSelectionListener);

		JScrollPane  itemListTableScrollPane = new JScrollPane(itemListTable,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		itemListTableScrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(SCROLL_WIDTH,0));
		itemListTableScrollPane.setSize(new Dimension(tableListPanel.getWidth(),tableListPanel.getHeight()));
		itemListTableScrollPane.setLocation(0,0);
		itemListTableScrollPane.getViewport().setBackground(Color.WHITE);
		
		final int rowHeight=ROW_HEIGHT;
		itemListTable.setRowHeight(rowHeight);

		tableListPanel.add(itemListTableScrollPane);
		
		if(!isBillNameInfoVisible)
			itemListTable.removeColumn(itemListTable.getColumnModel().getColumn(LIST_TABLE_COL_BILL_NAME_INDEX));
		
		if(!isTableSeatInfoVisible)
			itemListTable.removeColumn(itemListTable.getColumnModel().getColumn(LIST_TABLE_COL_TABLE_SEAT_INDEX));
		
		
		return itemListTable;
	}
	
	/**
	 * @param itemListTable
	 */
	private void setColumnWidth(JTable itemListTable){

		itemListTable.getColumnModel().getColumn(LIST_TABLE_COL_SEL_BUTTON_INDEX).setPreferredWidth(LIST_TABLE_COL_SEL_BUTTON_WIDTH);
		itemListTable.getColumnModel().getColumn(LIST_TABLE_COL_ITEM_NAME_INDEX).setPreferredWidth(getItemNameColumWidth());
		itemListTable.getColumnModel().getColumn(LIST_TABLE_COL_TABLE_SEAT_INDEX).setPreferredWidth(getTableSeatColumWidth());
		itemListTable.getColumnModel().getColumn(LIST_TABLE_COL_AMOUNT_INDEX).setPreferredWidth(LIST_TABLE_COL_AMOUNT_WIDTH);
		itemListTable.getColumnModel().getColumn(LIST_TABLE_COL_BILL_NAME_INDEX).setPreferredWidth(getBillNameColumWidth());

	}
	
	/**
	 * @return
	 */
	private int getBillNameColumWidth() {
	
		return isBillNameInfoVisible?LIST_TABLE_COL_BILL_NAME_WIDTH:0;
	}
	
	/**
	 * @return
	 */
	private int getTableSeatColumWidth() {

		return isTableSeatInfoVisible?LIST_TABLE_COL_TABLE_SEAT_WIDTH:0;
	}

	/**
	 * 
	 */
	private ListSelectionListener itemListTableListSelectionListener=new ListSelectionListener() {
		@Override
		public void valueChanged(ListSelectionEvent e) {

			final ListSelectionModel rowSM = (ListSelectionModel) e
					.getSource();
			mSelectedItemIndex = mItemListTable
					.convertRowIndexToModel(rowSM.getLeadSelectionIndex());
		}
	};
	
	/**
	 * 
	 */
	private KeyListener itemListTableKeyListener=new KeyListener() {

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
		}

		@Override
		public void keyPressed(KeyEvent e) {

			if(e.getKeyCode()==KeyEvent.VK_SPACE || e.getKeyCode()==KeyEvent.VK_ENTER){
				final int row=mItemListTable.getSelectedRow();
				onTableRowSelected(row);
			}
		}
	};
	
	private MouseAdapter itemListTableMouseAdapter=new MouseAdapter() {
		int startRow;
		int startCol;
		/* (non-Javadoc)
		 * @see java.awt.event.MouseAdapter#mousePressed(java.awt.event.MouseEvent)
		 */
		@Override
		public void mousePressed(MouseEvent e) {

			startRow= mItemListTable.rowAtPoint(e.getPoint());
			startCol= mItemListTable.columnAtPoint(e.getPoint());
			super.mousePressed(e);
		}
		/* (non-Javadoc)
		 * @see java.awt.event.MouseAdapter#mouseReleased(java.awt.event.MouseEvent)
		 */
		@Override
		public void mouseReleased(MouseEvent e) {

			int row = mItemListTable.rowAtPoint(e.getPoint());
			int col= mItemListTable.columnAtPoint(e.getPoint());
			if(startRow==row && startCol==col)
				onTableRowSelected(row);
			super.mouseReleased(e);
		}
	};
	

	/**
	 * Toggles the selected rows check
	 * @param row
	 */
	private void onTableRowSelected(int row){

		
		if(!isRowsSelectable) return;
		
		/**
		 * Dont allow to select/deselect child items like extras combos 
		 */
		final BeanOrderDetail orderDtlItem=splitItems.get(row).getOrderDetailtem();
		final String parentID=orderDtlItem.getParentDtlId();
		if(parentID!=null && !parentID.equals("")) return;
		
		final boolean isSelected=!splitItems.get(row).isSelected();
		setTableRowSelected(row,isSelected);
		
//		/**
//		 * If the item is splitted selected skip. 
//		 */
//		if(splitItems.get(row).getSplitName()!=null && !splitItems.get(row).getSplitName().isEmpty()) return;
		
		

		if(orderDtlItem.hasSubItems())
			autoSelectSubItems(orderDtlItem.getId(),isSelected,row);
	}
	
	/**
	 * @param row
	 * @param isSelected
	 */
	private void setTableRowSelected(int row, boolean isSelected){
		
		splitItems.get(row).setSelected(isSelected);

		String selected=(isSelected?"true":"false");
		mItemListTable.setValueAt(selected, row, LIST_TABLE_COL_SEL_BUTTON_INDEX);

		if(listener!=null)
			listener.onSelectionChanged(row);
		
	}
	
	/**
	 * Automatically select the subitems 
	 * @param parentItemId
	 * @param isSelected
	 * @param startPosition
	 */
	private void autoSelectSubItems(String parentItemId,boolean isSelected, int startPosition){
		
		for(int index=startPosition; index<splitItems.size();index++){
			
			if(splitItems.get(index).getOrderDetailtem().getParentDtlId()==parentItemId){
				setTableRowSelected(index,isSelected);
			}
		}
		
	}

	/**
	 * @returns the column name in its order
	 */
	private String[] getColumnNames(){

		String[] colNames=new String[FIELD_COUNT];

		colNames[LIST_TABLE_COL_AMOUNT_INDEX]="Amount";
		colNames[LIST_TABLE_COL_BILL_NAME_INDEX]="Bill Name";
		colNames[LIST_TABLE_COL_TABLE_SEAT_INDEX]="Table/Seat";
		colNames[LIST_TABLE_COL_ITEM_NAME_INDEX]="Item Name";
		colNames[LIST_TABLE_COL_SEL_BUTTON_INDEX]="";

		return colNames;
	}


	/**
	 * @param splitItems
	 */
	public void SetSplitItemList( ArrayList<BeanOrderSplitDetail> splitItems){
		
		clearRows();
		this.splitItems=splitItems;
		setSplitItemList();

	}
	
	/**
	 * 
	 */
	private void setSplitItemList(){
		
		DefaultTableModel model=(DefaultTableModel)mItemListTable.getModel();
		
		for(BeanOrderSplitDetail item:splitItems){
			
			int rowHeight=ROW_HEIGHT;
			model.addRow(getRowValue(item));
			
			if(PosEnvSettings.getInstance().getUISetting().getOrderEntryUISettings().getSplitUISettings().isItemChoicesVisible() 
					&& PosEnvSettings.getInstance().getUISetting().getOrderEntryUISettings().getSplitUISettings().isItemMoidifiersVisible() 
					&& item.getOrderDetailtem().getSaleItem().hasSelectedAttributes() 
					&& item.getOrderDetailtem().getExtraItemList()!=null && item.getOrderDetailtem().getExtraItemList().size()>0)
				
				rowHeight+=20;

			mItemListTable.setRowHeight(mItemListTable.getRowCount()-1,rowHeight );
		}

		mItemListTable.invalidate();
		
	}
	
	/**
	 * 
	 */
	private void clearRows() {
		
		final DefaultTableModel model=(DefaultTableModel) mItemListTable.getModel();
		mItemListTable.getSelectionModel().removeListSelectionListener(itemListTableListSelectionListener);
		final int rowCount=model.getRowCount();
		for(int rowIndex=0; rowIndex<rowCount; rowIndex++){
			
			model.removeRow(0);
		}
		mItemListTable.getSelectionModel().addListSelectionListener(itemListTableListSelectionListener);
	}
	
	/**
	 * 
	 */
	public void refresh(){
		
		clearRows();
		setSplitItemList();
	}


	/**
	 * @param splitItem
	 * @return
	 */
	private  String[] getRowValue(BeanOrderSplitDetail splitItem){
		
		String[] values=new String[FIELD_COUNT];
		values[LIST_TABLE_COL_SEL_BUTTON_INDEX]=String.valueOf(splitItem.isSelected());
		values[LIST_TABLE_COL_ITEM_NAME_INDEX]=splitItem.getItemName();
		values[LIST_TABLE_COL_BILL_NAME_INDEX]=splitItem.getSplitName();
		values[LIST_TABLE_COL_TABLE_SEAT_INDEX]=splitItem.getTableSeat();
		values[LIST_TABLE_COL_AMOUNT_INDEX]=PosCurrencyUtil.format(splitItem.getPrice());
		
		
		return values;

	}

	/**
	 * @param selectable
	 */
	public void setRowsSelectable(boolean selectable){
		
		if(isRowsSelectable!=selectable)
			clearSelections();
		
		isRowsSelectable=selectable;
		mItemListTable.setRowSelectionAllowed(isRowsSelectable);
		
	}

	/**
	 * 
	 */
	public void clearSelections(){

		for(int row=0; row<mItemListTable.getRowCount();row++)
			mItemListTable.setValueAt("false", row, LIST_TABLE_COL_SEL_BUTTON_INDEX);
		
		mItemListTable.getSelectionModel().clearSelection();
		mItemListTable.getColumnModel().getSelectionModel().clearSelection();

	}
	
	/**
	 * @return
	 */
	public int getSelectedItemIndex(){
		
		return mSelectedItemIndex;
	}
	
	/**
	 * Returns the selected items
	 * @return
	 */
	public ArrayList<BeanOrderSplitDetail> getSelectedItemList(){
		
		ArrayList<BeanOrderSplitDetail>  selectedItems=null;
		
		final DefaultTableModel model=(DefaultTableModel) mItemListTable.getModel();
		for(int rowIndex=0; rowIndex<model.getRowCount(); rowIndex++){

				if(Boolean.valueOf(model.getValueAt(rowIndex, LIST_TABLE_COL_SEL_BUTTON_INDEX).toString())){
					
					if(selectedItems==null)
						selectedItems=new ArrayList<BeanOrderSplitDetail>();
					
					selectedItems.add(splitItems.get(rowIndex));
				}

		}
		
		return selectedItems;
		
	}

	/**
	 * @param listener the listener to set
	 */
	public void setListener(IPosSplitListItemPanelListener listener) {
		this.listener = listener;
	}


}
