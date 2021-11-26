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

import com.indocosmo.pos.common.utilities.PosCurrencyUtil;
import com.indocosmo.pos.common.utilities.PosFormUtil;
import com.indocosmo.pos.data.beans.BeanOrderSplit;
import com.indocosmo.pos.forms.components.jtable.CheckedSelectionTableCellRender;
import com.indocosmo.pos.forms.components.jtable.NoBorderTableCellSelectionRender;

/**
 * @author jojesh-13.2
 *
 */
public class SplitSelectionTablePanel extends JPanel {

	public enum SelectionMode{
		Simple,
		Detail;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int ROW_HEIGHT = 40;
	private static final int SCROLL_WIDTH = 25;

	/*
	 * Column header positions
	 */
	private static final int LIST_TABLE_COL_SEL_BUTTON_INDEX=0;
	private static final int LIST_TABLE_COL_ITEM_NAME_INDEX=1;
	private static final int LIST_TABLE_COL_AMOUNT_INDEX=2;
	private static final int LIST_TABLE_COL_ADJUST_AMOUNT_INDEX=3;
	private static final int LIST_TABLE_COL_ACTUAL_AMOUNT_INDEX=4;
	/**
	 * This column position is dynamic based on the SelectionMode
	 * In details mode it is 5 which is default
	 */
	private int LIST_TABLE_COL_STATUS_INDEX=5;

	/*
	 * Column width
	 */
	private static final int LIST_TABLE_COL_SEL_BUTTON_WIDTH=40;
	//	private static final int LIST_TABLE_COL_ITEM_NAME_INDEX=100; //Dynamic
	private static final int LIST_TABLE_COL_AMOUNT_WIDTH=100;
	private static final int LIST_TABLE_COL_ADJUST_AMOUNT_WIDTH=100;
	private static final int LIST_TABLE_COL_ACTUAL_AMOUNT_WIDTH=100;
	private static final int LIST_TABLE_COL_STATUS_WIDTH=80;
	
	private static final String ITEM_STATUS_PAID="PAID";
	private static final String ITEM_STATUS_UNPAID="";

	private static final int LIST_TABLE_HEADER_HEIGHT=30;

	private DefaultTableModel mItemListTableModel;
	private JTable 	mItemListTable;
//	private boolean allowAdjustAmount=true; 

	private int mSelectedItemIndex=-1;
	private ArrayList<BeanOrderSplit> mSplitItems;

	private double totalAmoount;
	private double totalAdjustment;
	private double netAmount;
	private double amountToPay;
	
	private RootPaneContainer mParent;
	private SelectionMode selectionMode=SelectionMode.Detail;
	
	/**
	 * 
	 */
	public SplitSelectionTablePanel(RootPaneContainer parent, int width, int height ) {
		
		initForm(parent,width,height,SelectionMode.Detail);
		
	}

	/**
	 * @param posSplitEditItemSelectForm
	 * @param itemListPanelWidth
	 * @param itemListPanelHeight
	 * @param simple
	 */
	public SplitSelectionTablePanel(RootPaneContainer parent, int width, int height,SelectionMode selectionMode) {
	
		initForm( parent,width,height,selectionMode);
	}
	
	/**
	 * @param parent
	 * @param width
	 * @param height
	 * @param selectionMode
	 */
	private void initForm(RootPaneContainer parent, int width, int height,SelectionMode selectionMode){
		
		this.selectionMode=selectionMode;
		mParent=parent;
		this.setLayout(null);
		this.setSize(new Dimension(width,height));
		this.setPreferredSize(new Dimension(width,height));
		mItemListTable=createItemListTablePanel(this);
	}

	/**
	 * @returns the table column width
	 */
	private int getItemNameColumWidth(){

		return getWidth()
				-getAmountColumWidth()
				-LIST_TABLE_COL_SEL_BUTTON_WIDTH
				-getAdjustAmountColumnWidth()
				-getActualAmountColumnWidth()
				-SCROLL_WIDTH
				-LIST_TABLE_COL_STATUS_WIDTH
				-2;
	}
	
	/**
	 * @return
	 */
	private int getAmountColumWidth(){

		return LIST_TABLE_COL_AMOUNT_WIDTH+((selectionMode==SelectionMode.Detail)?0:LIST_TABLE_COL_ACTUAL_AMOUNT_WIDTH);
	}

	/**
	 * @return
	 */
	private int getActualAmountColumnWidth() {

		return ((selectionMode==SelectionMode.Detail)?LIST_TABLE_COL_ACTUAL_AMOUNT_WIDTH:0);
	}

	/**
	 * @return
	 */
	private int getAdjustAmountColumnWidth(){

		return ((selectionMode==SelectionMode.Detail)?LIST_TABLE_COL_ADJUST_AMOUNT_WIDTH:0);
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
		itemListTable.setFont(PosFormUtil.getTableCellFont());		itemListTable.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		itemListTable.getTableHeader().setPreferredSize(new Dimension(tableListPanel.getWidth(), LIST_TABLE_HEADER_HEIGHT));
		itemListTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		itemListTable.getTableHeader().setReorderingAllowed(false);
		itemListTable.getTableHeader().setResizingAllowed(false);

		itemListTable.getTableHeader().setFont(PosFormUtil.getTableHeadingFont());
		
		setColumnSize(itemListTable);
		
		itemListTable.getColumnModel().getColumn(LIST_TABLE_COL_SEL_BUTTON_INDEX).setCellRenderer(selectionCell);
		itemListTable.getColumnModel().getColumn(LIST_TABLE_COL_ITEM_NAME_INDEX).setCellRenderer(noBorderCell);
		itemListTable.getColumnModel().getColumn(LIST_TABLE_COL_AMOUNT_INDEX).setCellRenderer(rightAlign);
		itemListTable.getColumnModel().getColumn(LIST_TABLE_COL_ADJUST_AMOUNT_INDEX).setCellRenderer(rightAlign);
		itemListTable.getColumnModel().getColumn(LIST_TABLE_COL_ACTUAL_AMOUNT_INDEX).setCellRenderer(rightAlign);
		
		itemListTable.getColumnModel().getColumn(LIST_TABLE_COL_STATUS_INDEX).setPreferredWidth(LIST_TABLE_COL_ACTUAL_AMOUNT_WIDTH);
		itemListTable.getColumnModel().getColumn(LIST_TABLE_COL_STATUS_INDEX).setCellRenderer(centerAlign);

		itemListTable.addMouseListener(new MouseAdapter() {

			int startRow;
			int startCol;
			/* (non-Javadoc)
			 * @see java.awt.event.MouseAdapter#mousePressed(java.awt.event.MouseEvent)
			 */
			@Override
			public void mousePressed(MouseEvent e) {

				startRow= itemListTable.rowAtPoint(e.getPoint());
				startCol= itemListTable.columnAtPoint(e.getPoint());
				super.mousePressed(e);
			}
			/* (non-Javadoc)
			 * @see java.awt.event.MouseAdapter#mouseReleased(java.awt.event.MouseEvent)
			 */
			@Override
			public void mouseReleased(MouseEvent e) {

				int row = itemListTable.rowAtPoint(e.getPoint());
				int col= itemListTable.columnAtPoint(e.getPoint());
				if(startRow==row && startCol==col && col==LIST_TABLE_COL_SEL_BUTTON_INDEX)
					setTableRowSelected(row);
				super.mouseReleased(e);
			}
		});

		itemListTable.addKeyListener(new KeyListener() {

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
					final int row=itemListTable.getSelectedRow();
					setTableRowSelected(row);
				}
			}
		});

		itemListTable.getSelectionModel().addListSelectionListener(splitListSelectionListener);

		JScrollPane  itemListTableScrollPane = new JScrollPane(itemListTable,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		itemListTableScrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(SCROLL_WIDTH,0));
		itemListTableScrollPane.setSize(new Dimension(tableListPanel.getWidth(),tableListPanel.getHeight()));
		itemListTableScrollPane.setLocation(0,0);
		itemListTableScrollPane.setOpaque(true);
		itemListTableScrollPane.getViewport().setBackground(Color.WHITE);
		
		final int rowHeight=ROW_HEIGHT;
		itemListTable.setRowHeight(rowHeight);

		tableListPanel.add(itemListTableScrollPane);


		return itemListTable;
	}
	
	/**
	 * @param itemListTable
	 */
	private void setColumnSize( JTable itemListTable){

		itemListTable.getColumnModel().getColumn(LIST_TABLE_COL_SEL_BUTTON_INDEX).setPreferredWidth(LIST_TABLE_COL_SEL_BUTTON_WIDTH);
		itemListTable.getColumnModel().getColumn(LIST_TABLE_COL_ITEM_NAME_INDEX).setPreferredWidth(getItemNameColumWidth());
		itemListTable.getColumnModel().getColumn(LIST_TABLE_COL_AMOUNT_INDEX).setPreferredWidth(getAmountColumWidth());
		itemListTable.getColumnModel().getColumn(LIST_TABLE_COL_ADJUST_AMOUNT_INDEX).setPreferredWidth(getAdjustAmountColumnWidth());
		itemListTable.getColumnModel().getColumn(LIST_TABLE_COL_ACTUAL_AMOUNT_INDEX).setPreferredWidth(getActualAmountColumnWidth());
		itemListTable.getColumnModel().getColumn(LIST_TABLE_COL_STATUS_INDEX).setPreferredWidth(LIST_TABLE_COL_STATUS_WIDTH);
		
	}
	
	/**
	 * 
	 */
	private ListSelectionListener splitListSelectionListener=new ListSelectionListener() {
		@Override
		public void valueChanged(ListSelectionEvent e) {

			final ListSelectionModel rowSM = (ListSelectionModel) e
					.getSource();
			mSelectedItemIndex = mItemListTable
					.convertRowIndexToModel(rowSM.getLeadSelectionIndex());
		}
	};

	/**
	 * Toggles the selected rows check
	 * @param row
	 */
	private void setTableRowSelected(int row){

		String selected="false";
		
		if(!mItemListTable.getValueAt(row, LIST_TABLE_COL_STATUS_INDEX).equals(ITEM_STATUS_PAID))
			selected=(mItemListTable.getValueAt(row, LIST_TABLE_COL_SEL_BUTTON_INDEX).equals("true"))?"false":"true";
		
		mItemListTable.setValueAt(selected, mItemListTable.getSelectedRow(), LIST_TABLE_COL_SEL_BUTTON_INDEX);
		updateAmounts();
	}

	/**
	 * @returns the column name in its order
	 */
	private String[] getColumnNames(){

		String[] colNames=new String[6];

		colNames[LIST_TABLE_COL_SEL_BUTTON_INDEX]="";
		colNames[LIST_TABLE_COL_ITEM_NAME_INDEX]="Bill";
		colNames[LIST_TABLE_COL_AMOUNT_INDEX]="Amount";
		colNames[LIST_TABLE_COL_ADJUST_AMOUNT_INDEX]="Adjust (+/-)";
		colNames[LIST_TABLE_COL_ACTUAL_AMOUNT_INDEX]="Actual";
		colNames[LIST_TABLE_COL_STATUS_INDEX]="Status";

		return colNames;
	}

	/**
	 * @param splitItems
	 */
	public void setSplitItemList( ArrayList<BeanOrderSplit> splitItems){

		mSplitItems=splitItems;
		setSplitItemList();
		
	}
	

	/**
	 * 
	 */
	private void setSplitItemList() {
		
		DefaultTableModel model=(DefaultTableModel)mItemListTable.getModel();
		
		for(BeanOrderSplit item:mSplitItems){
			model.addRow(getRowValue(item));
		}

		updateAmounts();
		mItemListTable.invalidate();
	}
	
//	public void updateSplitItemsStatus(){
//		
//		DefaultTableModel model=(DefaultTableModel)mItemListTable.getModel();
//		
//		for(int rowIndex=0; rowIndex<model.getRowCount(); rowIndex++){
//			
//			final BeanOrderSplit item=mSplitItems.get(rowIndex);
//			model.setValueAt(item.isPayed()?"PAID":"", rowIndex, LIST_TABLE_COL_STATUS_INDEX);
//		}
//	}

	/**
	 * @param splitItem
	 * @return
	 */
	private  String[] getRowValue(BeanOrderSplit splitItem){

		return new String[]{
				"false",
				splitItem.getDescription(),
				PosCurrencyUtil.format(splitItem.getAmount()),
				PosCurrencyUtil.format(splitItem.getAdjustAmount()),
				PosCurrencyUtil.format(splitItem.getActualAmount()),
				splitItem.isPayed()?ITEM_STATUS_PAID:ITEM_STATUS_UNPAID
		};
	}

	/**
	 * 
	 */
	public void clearSelections(){

		for(int row=0; row<mItemListTable.getRowCount();row++)
			mItemListTable.setValueAt("false", row, LIST_TABLE_COL_SEL_BUTTON_INDEX);

		mItemListTable.getSelectionModel().clearSelection();
		mItemListTable.getColumnModel().getSelectionModel().clearSelection();
		updateAmounts();

	}

	/**
	 * @param allowAdjustAmount
	 */
	public void setAllowAdjustAmount(boolean allowAdjustAmount) {

		selectionMode=((allowAdjustAmount)?SelectionMode.Detail:SelectionMode.Simple);

		setColumnSize(mItemListTable);
		
		if(!allowAdjustAmount){
			
			mItemListTable.removeColumn(mItemListTable.getColumnModel().getColumn(LIST_TABLE_COL_ACTUAL_AMOUNT_INDEX));
			mItemListTable.removeColumn(mItemListTable.getColumnModel().getColumn(LIST_TABLE_COL_ADJUST_AMOUNT_INDEX));
			/**
			 * Re-adjust the position of the status column
			 */
			LIST_TABLE_COL_STATUS_INDEX-=2;
		}

	}

	/**
	 * @return
	 */
	public int getSelectedItemIndex(){

		return mSelectedItemIndex;
	}


	/**
	 * @return
	 */
	public BeanOrderSplit getCurrentSelectedItem(){

		BeanOrderSplit split=null;

		if(mSelectedItemIndex>=0)
			split=mSplitItems.get(mSelectedItemIndex);

		return split;
	}

	/**
	 * @param amount
	 */
	public void setSelectedItemAdjustment(double amount){

		final BeanOrderSplit split=getCurrentSelectedItem();
		
			if(split.getAmount()+amount<=0){
				
				PosFormUtil.showErrorMessageBox(this.mParent,"Adjustment amount is not valid. Please make sure that the amount after adjustment is greater than zero.");
				
			}else{
				split.setAdjustAmount(amount);
				setSelectedItemObject(split);
			}

	}
	

	/**
	 * @param amount
	 */
	public double getSelectedItemAdjustment(){

		return getCurrentSelectedItem().getAdjustAmount();
	}

	/**
	 * @param split
	 */
	private void setSelectedItemObject(BeanOrderSplit split){

		DefaultTableModel model=(DefaultTableModel) mItemListTable.getModel();

		model.setValueAt(split.getDescription(), mSelectedItemIndex, LIST_TABLE_COL_ITEM_NAME_INDEX);
		model.setValueAt(PosCurrencyUtil.format(split.getAmount()), mSelectedItemIndex, LIST_TABLE_COL_AMOUNT_INDEX);
		if(selectionMode==SelectionMode.Detail){
			
			model.setValueAt(PosCurrencyUtil.format(split.getAdjustAmount()), mSelectedItemIndex, LIST_TABLE_COL_ADJUST_AMOUNT_INDEX);
			model.setValueAt(PosCurrencyUtil.format(split.getActualAmount()), mSelectedItemIndex, LIST_TABLE_COL_ACTUAL_AMOUNT_INDEX);
		}
		model.setValueAt(split.isPayed()?ITEM_STATUS_PAID:ITEM_STATUS_UNPAID, mSelectedItemIndex, LIST_TABLE_COL_STATUS_INDEX);
		updateAmounts();

	}


	/**
	 * 
	 */
	private void updateAmounts(){

		amountToPay=0.0;
		totalAdjustment=0.0;
		totalAmoount=0.0;
		netAmount=0.0;

		final DefaultTableModel model=(DefaultTableModel) mItemListTable.getModel();
		for(int rowIndex=0; rowIndex<model.getRowCount(); rowIndex++){

			totalAdjustment+=Double.valueOf(model.getValueAt(rowIndex, LIST_TABLE_COL_ADJUST_AMOUNT_INDEX).toString());
			totalAmoount+=Double.valueOf(model.getValueAt(rowIndex, LIST_TABLE_COL_AMOUNT_INDEX).toString());


			if(Boolean.valueOf(model.getValueAt(rowIndex, LIST_TABLE_COL_SEL_BUTTON_INDEX).toString())){
				amountToPay+=Double.valueOf(model.getValueAt(rowIndex, LIST_TABLE_COL_ACTUAL_AMOUNT_INDEX).toString());
			}
		}

		netAmount=totalAmoount+totalAdjustment;

		if(listener!=null)
			listener.onItemChanged();
	}


	/**
	 * @return the totalAmoount
	 */
	public double getTotalAmoount() {
		return totalAmoount;
	}

	//	/**
	//	 * @param totalAmoount the totalAmoount to set
	//	 */
	//	public void setTotalAmoount(double totalAmoount) {
	//		this.totalAmoount = totalAmoount;
	//	}

	/**
	 * @return the totalAdjustment
	 */
	public double getTotalAdjustment() {
		return totalAdjustment;
	}

	//	/**
	//	 * @param totalAdjustment the totalAdjustment to set
	//	 */
	//	public void setTotalAdjustment(double totalAdjustment) {
	//		this.totalAdjustment = totalAdjustment;
	//	}

	/**
	 * @return the netAmount
	 */
	public double getNetAmount() {
		return netAmount;
	}

	/**
	 * @return the amountToPay
	 */
	public double getAmountToPay() {
		return amountToPay;
	}

	/**
	 * @return
	 */
	public  ArrayList<BeanOrderSplit> getSelectedItems(){

		ArrayList<BeanOrderSplit> selectedItems=null;

		final DefaultTableModel model=(DefaultTableModel) mItemListTable.getModel();
		for(int rowIndex=0; rowIndex<model.getRowCount(); rowIndex++){

				if(Boolean.valueOf(model.getValueAt(rowIndex, LIST_TABLE_COL_SEL_BUTTON_INDEX).toString())){
					
					if(selectedItems==null)selectedItems=new ArrayList<BeanOrderSplit>();
					selectedItems.add(mSplitItems.get(rowIndex));
				}

		}
		
		return selectedItems;
	}
	
	
	public ISplitSelectionListener listener;
	public interface ISplitSelectionListener{
		
		public void onItemChanged();
	}
	

	/**
	 * @param listener the listener to set
	 */
	public void setListener(ISplitSelectionListener listener) {
		this.listener = listener;
	}
	
	public void updateSplitItems() {
		
		clearRows();
		setSplitItemList();
	}


	/**
	 * 
	 */
	private void clearRows() {
		
		final DefaultTableModel model=(DefaultTableModel) mItemListTable.getModel();
		mItemListTable.getSelectionModel().removeListSelectionListener(splitListSelectionListener);
		final int rowCount=model.getRowCount();
		for(int rowIndex=0; rowIndex<rowCount; rowIndex++){
			
			model.removeRow(0);
		}
		mItemListTable.getSelectionModel().addListSelectionListener(splitListSelectionListener);
		
	}


}
