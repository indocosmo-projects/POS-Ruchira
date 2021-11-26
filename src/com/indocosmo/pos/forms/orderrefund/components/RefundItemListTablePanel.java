/**
 * 
 */
package com.indocosmo.pos.forms.orderrefund.components;

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
import com.indocosmo.pos.common.beans.settings.ui.orderlist.BeanUIOrderListSetting;
import com.indocosmo.pos.common.beans.settings.ui.orderlist.BeanUIOrderRefundSetting;
import com.indocosmo.pos.common.enums.RefundAdjustmentType;
import com.indocosmo.pos.common.utilities.PosCurrencyUtil;
import com.indocosmo.pos.common.utilities.PosFormUtil;
import com.indocosmo.pos.common.utilities.PosNumberUtil;
import com.indocosmo.pos.common.utilities.PosOrderUtil;
import com.indocosmo.pos.common.utilities.PosTaxUtil;
import com.indocosmo.pos.common.utilities.PosUomUtil;
import com.indocosmo.pos.data.beans.BeanDiscount;
import com.indocosmo.pos.data.beans.BeanOrderDetail;
import com.indocosmo.pos.data.beans.BeanOrderHeader;
import com.indocosmo.pos.data.beans.BeanOrderRefund.RefundType;
import com.indocosmo.pos.data.providers.shopdb.PosTaxAmountObject;
import com.indocosmo.pos.data.providers.shopdb.PosTaxItemProvider.TaxCalculationMethod;
import com.indocosmo.pos.data.providers.shopdb.PosUOMProvider;
import com.indocosmo.pos.forms.components.jtable.CheckedSelectionTableCellRender;
import com.indocosmo.pos.forms.components.jtable.NoBorderTableCellSelectionRender;
import com.indocosmo.pos.forms.orderrefund.components.listeners.IRefundItemListTablePanelListener;

/**
 * @author jojesh-13.2
 *
 */
public class RefundItemListTablePanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int ROW_HEIGHT = 40;
	private static final int SCROLL_WIDTH = 25;
	private static final int FIELD_COUNT=10;
	//	private static final int SCROLL_HEIGHT = 20;

	/*
	 * Column header positions
	 */
	private static final int LIST_TABLE_COL_SEL_BUTTON_INDEX=0;
	private static final int LIST_TABLE_COL_ITEM_NAME_INDEX=1;
	private static final int LIST_TABLE_COL_QTY_INDEX=2;
	private static final int LIST_TABLE_COL_AMOUNT_INDEX=3;
	private static final int LIST_TABLE_COL_DISCOUNT_INDEX=4;
	private static final int LIST_TABLE_COL_REF_QTY_INDEX=5;
	private static final int LIST_TABLE_COL_REF_AMOUNT_INDEX=6;
	private static final int LIST_TABLE_COL_STATUS_INDEX=7;
	private static final int LIST_TABLE_COL_DET_ID_INDEX=8;
	private static final int LIST_TABLE_COL_INDEX_INDEX=9;
/*
	 * Column width
	 */
	private static final int LIST_TABLE_COL_SEL_BUTTON_WIDTH=40;
	private static final int LIST_TABLE_COL_QTY_WIDTH=60;
	private static final int LIST_TABLE_COL_AMOUNT_WIDTH=110;
	private static final int LIST_TABLE_COL_DISCOUNT_WIDTH=90;
	private static final int LIST_TABLE_COL_REF_QTY_WIDTH=60;
	private static final int LIST_TABLE_COL_REF_AMOUNT_WIDTH=113;
	private static final int LIST_TABLE_COL_STATUS_WIDTH=100;
	private static final int LIST_TABLE_COL_DET_ID_WIDTH=0;
	private static final int LIST_TABLE_COL_INDEX_WIDTH=0;
	
	private static final int LIST_TABLE_HEADER_HEIGHT=30;

	private DefaultTableModel mItemListTableModel;
	private JTable 	mItemListTable; 
	private boolean isRowsSelectable=false;
	private int mSelectedItemIndex=-1;
	private ArrayList<BeanOrderDetail> mOrderDetItems;
	private BeanOrderHeader mOrderHdr;
	  
	private IRefundItemListTablePanelListener  listener;
	RootPaneContainer mParent;
	/**
	 * 
	 */
	public RefundItemListTablePanel(RootPaneContainer parent, int width, int height ) {

		initForm(parent,width,height);
	}
	
	/**
	 * @param parent
	 * @param width
	 * @param height
	 */
	private void initForm(RootPaneContainer parent, int width, int height ){
	
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

		return getWidth()-
				-LIST_TABLE_COL_INDEX_WIDTH
				-LIST_TABLE_COL_STATUS_WIDTH
				-LIST_TABLE_COL_AMOUNT_WIDTH
				-LIST_TABLE_COL_DISCOUNT_WIDTH
				-LIST_TABLE_COL_REF_QTY_WIDTH
				-LIST_TABLE_COL_REF_AMOUNT_WIDTH
				-LIST_TABLE_COL_QTY_WIDTH
				-LIST_TABLE_COL_SEL_BUTTON_WIDTH
				-SCROLL_WIDTH
				-2;

	}
	
	/* (non-Javadoc)
	 * @see javax.swing.JComponent#setEnabled(boolean)
	 */
	@Override
	public void setEnabled(boolean enabled) {
		// TODO Auto-generated method stub
		super.setEnabled(enabled);
		
		if(mItemListTable!=null)
			mItemListTable.setEnabled(enabled);
				
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

		final JTable itemListTable = new JTable(mItemListTableModel) ;
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
		itemListTable.getColumnModel().getColumn(LIST_TABLE_COL_QTY_INDEX).setCellRenderer(centerAlign);
		itemListTable.getColumnModel().getColumn(LIST_TABLE_COL_AMOUNT_INDEX).setCellRenderer(rightAlign);
		itemListTable.getColumnModel().getColumn(LIST_TABLE_COL_DISCOUNT_INDEX).setCellRenderer(rightAlign);
		itemListTable.getColumnModel().getColumn(LIST_TABLE_COL_REF_QTY_INDEX).setCellRenderer(rightAlign);
		itemListTable.getColumnModel().getColumn(LIST_TABLE_COL_REF_AMOUNT_INDEX).setCellRenderer(rightAlign);
		itemListTable.getColumnModel().getColumn(LIST_TABLE_COL_STATUS_INDEX).setCellRenderer(noBorderCell);
		itemListTable.getColumnModel().getColumn(LIST_TABLE_COL_DET_ID_INDEX).setCellRenderer(noBorderCell);
		itemListTable.getColumnModel().getColumn(LIST_TABLE_COL_INDEX_INDEX).setCellRenderer(noBorderCell);
		
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
		return itemListTable;
	}
	
	/**
	 * @param itemListTable
	 */
	private void setColumnWidth(JTable itemListTable){

		itemListTable.getColumnModel().getColumn(LIST_TABLE_COL_SEL_BUTTON_INDEX).setPreferredWidth(LIST_TABLE_COL_SEL_BUTTON_WIDTH);
		itemListTable.getColumnModel().getColumn(LIST_TABLE_COL_ITEM_NAME_INDEX).setPreferredWidth(getItemNameColumWidth());
		itemListTable.getColumnModel().getColumn(LIST_TABLE_COL_QTY_INDEX).setPreferredWidth(LIST_TABLE_COL_QTY_WIDTH);
		itemListTable.getColumnModel().getColumn(LIST_TABLE_COL_AMOUNT_INDEX).setPreferredWidth(LIST_TABLE_COL_AMOUNT_WIDTH);
		itemListTable.getColumnModel().getColumn(LIST_TABLE_COL_DISCOUNT_INDEX).setPreferredWidth(LIST_TABLE_COL_DISCOUNT_WIDTH);
		itemListTable.getColumnModel().getColumn(LIST_TABLE_COL_REF_QTY_INDEX).setPreferredWidth(LIST_TABLE_COL_REF_QTY_WIDTH);
		itemListTable.getColumnModel().getColumn(LIST_TABLE_COL_REF_AMOUNT_INDEX).setPreferredWidth(LIST_TABLE_COL_REF_AMOUNT_WIDTH);
		itemListTable.getColumnModel().getColumn(LIST_TABLE_COL_STATUS_INDEX).setPreferredWidth( LIST_TABLE_COL_STATUS_WIDTH);
		
		itemListTable.getColumnModel().getColumn(LIST_TABLE_COL_DET_ID_INDEX).setMinWidth(LIST_TABLE_COL_DET_ID_WIDTH);
		itemListTable.getColumnModel().getColumn(LIST_TABLE_COL_DET_ID_INDEX).setPreferredWidth(LIST_TABLE_COL_DET_ID_WIDTH );
		itemListTable.getColumnModel().getColumn(LIST_TABLE_COL_DET_ID_INDEX).setMaxWidth(LIST_TABLE_COL_DET_ID_WIDTH);
		
		itemListTable.getColumnModel().getColumn(LIST_TABLE_COL_INDEX_INDEX).setMinWidth(LIST_TABLE_COL_INDEX_WIDTH);
		itemListTable.getColumnModel().getColumn(LIST_TABLE_COL_INDEX_INDEX).setPreferredWidth(LIST_TABLE_COL_INDEX_WIDTH );
		itemListTable.getColumnModel().getColumn(LIST_TABLE_COL_INDEX_INDEX).setMaxWidth(LIST_TABLE_COL_INDEX_WIDTH);

		
	}
	
	 

	/**
	 * 
	 */
	private ListSelectionListener itemListTableListSelectionListener=new ListSelectionListener() {
		@Override
		public void valueChanged(ListSelectionEvent e) {

			if(!mItemListTable.isEnabled())
				return;
			
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
			
			if(!mItemListTable.isEnabled())
				return;
			
			if(e.getKeyCode()==KeyEvent.VK_SPACE || e.getKeyCode()==KeyEvent.VK_ENTER){
				
				onTableRowSelected(mItemListTable.getSelectedRow(),mItemListTable.getSelectedColumn());
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

			if(!mItemListTable.isEnabled())
				return;
			
			startRow= mItemListTable.rowAtPoint(e.getPoint());
			startCol= mItemListTable.columnAtPoint(e.getPoint());
			super.mousePressed(e);
		}
		/* (non-Javadoc)
		 * @see java.awt.event.MouseAdapter#mouseReleased(java.awt.event.MouseEvent)
		 */
		@Override
		public void mouseReleased(MouseEvent e) {

			if(!mItemListTable.isEnabled())
				return;
			
			int row = mItemListTable.rowAtPoint(e.getPoint());
			int col= mItemListTable.columnAtPoint(e.getPoint());
			if(startRow==row && startCol==col)
				onTableRowSelected(row,col);
			super.mouseReleased(e);
		}
	};
	

	/**
	 * Toggles the selected rows check
	 * @param row
	 */
	private void onTableRowSelected(int row,int column){

		
		final int actualRowIndex=Integer.parseInt(String.valueOf( mItemListTable.getValueAt(row, LIST_TABLE_COL_INDEX_INDEX)));
		
		if(mOrderDetItems.get(actualRowIndex).isRefunded()) 
			return;
		
		//deselection happens only if 1st column 
		if (mOrderDetItems.get(actualRowIndex).isDirty() && column!=LIST_TABLE_COL_SEL_BUTTON_INDEX)
			return;
		
		if(isRowsSelectable) 
		{
			final boolean isSelected=!mOrderDetItems.get(actualRowIndex).isDirty();
			setTableRowSelected(row,isSelected);
		}
		if(listener!=null )
			listener.onSelectionChanged(row);
		
	}
	
	/*
	 * 
	 */
	private double getItemRefundableAmt(int row){
		
		final double itemTotalAfterBillDiscount= PosNumberUtil.parseDoubleSafely(String.valueOf(mItemListTable.getValueAt(row, LIST_TABLE_COL_AMOUNT_INDEX)))+ 
				PosNumberUtil.parseDoubleSafely(String.valueOf(mItemListTable.getValueAt(row, LIST_TABLE_COL_DISCOUNT_INDEX)));
		
		return itemTotalAfterBillDiscount;
	}
	 /**
	 * @param row
	 * @param isSelected
	 */
	private void setTableRowSelected(int row, boolean isSelected){
		
		
		final int actualRowIndex=Integer.parseInt(String.valueOf( mItemListTable.getValueAt(row, LIST_TABLE_COL_INDEX_INDEX)));
		mOrderDetItems.get(actualRowIndex).setDirty(isSelected);
 
		final double refundAmt=  getItemRefundableAmt(row);
		
		String selected=(isSelected?"true":"false");
		mItemListTable.setValueAt(selected, row, LIST_TABLE_COL_SEL_BUTTON_INDEX);
//		mOrderDetItems.get(actualRowIndex).setRefundAmount(isSelected?
//				mOrderDetItems.get(actualRowIndex).getSaleItem ().getItemTotal():0);
		
		mOrderDetItems.get(actualRowIndex).setRefundAmount(isSelected?refundAmt:0) ;
		final double qty=PosNumberUtil.parseDoubleSafely(String.valueOf(mItemListTable.getValueAt(actualRowIndex, LIST_TABLE_COL_QTY_INDEX)));
		mOrderDetItems.get(actualRowIndex).setRefundQuantity(qty);
		
		mItemListTable.setValueAt(PosCurrencyUtil.format(refundAmt), row, LIST_TABLE_COL_REF_AMOUNT_INDEX);
		mItemListTable.setValueAt(PosCurrencyUtil.format(qty), row, LIST_TABLE_COL_REF_QTY_INDEX);
	}

	/**
	 * @returns the column name in its order
	 */
	private String[] getColumnNames(){

		String[] colNames=new String[FIELD_COUNT];

		colNames[LIST_TABLE_COL_DET_ID_INDEX]="";
		colNames[LIST_TABLE_COL_STATUS_INDEX]="Refund St.";
		colNames[LIST_TABLE_COL_REF_AMOUNT_INDEX]="Refund Amount";
		colNames[LIST_TABLE_COL_REF_QTY_INDEX]="Ref. Qty";
		colNames[LIST_TABLE_COL_DISCOUNT_INDEX]="Discount";
		colNames[LIST_TABLE_COL_AMOUNT_INDEX]="Amount";
		colNames[LIST_TABLE_COL_QTY_INDEX]="Qty.";
		colNames[LIST_TABLE_COL_ITEM_NAME_INDEX]="Item Name";
		colNames[LIST_TABLE_COL_SEL_BUTTON_INDEX]="";

		return colNames;
	}


	/**
	 * @param splitItems
	 */
	public void SetOrderItemList(BeanOrderHeader orderHdr){
		
		mOrderHdr=orderHdr;
		clearRows();
		mOrderDetItems=new ArrayList<BeanOrderDetail>();
		
		for(BeanOrderDetail dtlItem:orderHdr.getOrderDetailItems()){
			
			if(!dtlItem.isVoid())
				mOrderDetItems.add(dtlItem);
			
			/**Check ordered item has sub items**/
			if(dtlItem.hasSubItems()){

				/**If has combo contents print them**/
				if(dtlItem.isComboContentsSelected()){

					for(ArrayList<BeanOrderDetail> subItemList:dtlItem.getComboSubstitutes().values()){

						for(BeanOrderDetail item:subItemList){

							if(!item.isVoid())

								mOrderDetItems.add(item);
						}
					}
				}

				/**If has extras print them**/
				if(dtlItem.isExtraItemsSelected()){

					for(ArrayList<BeanOrderDetail> subItemList:dtlItem.getExtraItemList().values()){

						for(BeanOrderDetail item:subItemList){

							if(!item.isVoid())

								mOrderDetItems.add(item);
						}
					}
				}
			}
		 		
		}
		SetOrderItemList();

	}
	
	/**
	 * 
	 */
	private void SetOrderItemList(){
		
		DefaultTableModel model=(DefaultTableModel)mItemListTable.getModel();
		
		for(BeanOrderDetail dtlItem:mOrderDetItems){
			
			 model.addRow(getRowValue(dtlItem));
			 mItemListTable.setRowHeight(mItemListTable.getRowCount()-1,ROW_HEIGHT );
					 		
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
		SetOrderItemList();
	}


	/**
	 * @param splitItem
	 * @return
	 */
	private  String[] getRowValue(BeanOrderDetail orderItem){
		
		double amount ;
		double itemDiscount=0;
		 
		final double rate=PosOrderUtil.getItemFixedPrice(orderItem);
  
		 
			if(orderItem.getSaleItem().getTaxCalculationMethod()==TaxCalculationMethod.ExclusiveOfTax){
				final PosTaxAmountObject taxAmount=PosTaxUtil.calculateTaxes(orderItem.getSaleItem().getTax(), rate*orderItem.getSaleItem().getQuantity()); 
				amount= PosCurrencyUtil.roundTo(rate*orderItem.getSaleItem().getQuantity() + taxAmount.getTaxOneAmount() + taxAmount.getTaxTwoAmount() + taxAmount.getTaxThreeAmount());
				itemDiscount=	PosOrderUtil.getTotalDiscountAmountForExclusiveRate(orderItem,amount/orderItem.getSaleItem().getQuantity());
			} else{
				itemDiscount=PosOrderUtil.getTotalDiscountAmount(orderItem);
				 amount=rate*orderItem.getSaleItem().getQuantity();
			}
		 final double itemTotalBeforeBillDisc=amount-itemDiscount;
		
		 final double billDisc=(mOrderHdr.getBillDiscountPercentage()>0)? (itemTotalBeforeBillDisc*mOrderHdr.getBillDiscountPercentage()/100):0;
		 final double itemTotal= itemTotalBeforeBillDisc-billDisc;
		final double totalDisc=(itemDiscount+billDisc)>0?-1*(itemDiscount+billDisc):0;
		
		
		String[] values=new String[FIELD_COUNT];
		values[LIST_TABLE_COL_SEL_BUTTON_INDEX]="false";
		values[LIST_TABLE_COL_ITEM_NAME_INDEX]=orderItem.getSaleItem().getName();
		values[LIST_TABLE_COL_QTY_INDEX]=PosUomUtil.format(orderItem.getSaleItem().getQuantity(),orderItem.getSaleItem().getUom());
		values[LIST_TABLE_COL_AMOUNT_INDEX]=PosCurrencyUtil.format(amount);
		values[LIST_TABLE_COL_DISCOUNT_INDEX]=PosCurrencyUtil.format(totalDisc);
		
		if (orderItem.isRefunded()){
			
			values[LIST_TABLE_COL_REF_QTY_INDEX]=PosUomUtil.format(orderItem.getRefundQuantity(),orderItem.getSaleItem().getUom());
			values[LIST_TABLE_COL_REF_AMOUNT_INDEX]= PosCurrencyUtil.format(orderItem.getRefundAmount());		
			values[LIST_TABLE_COL_STATUS_INDEX]= "Refunded"  ;
		}else{
			
			values[LIST_TABLE_COL_REF_QTY_INDEX]=PosUomUtil.format(orderItem.getSaleItem().getQuantity(),orderItem.getSaleItem().getUom());
			values[LIST_TABLE_COL_REF_AMOUNT_INDEX]= PosCurrencyUtil.format(itemTotal);
		}		
		
		values[LIST_TABLE_COL_DET_ID_INDEX]=orderItem.getId();
		values[LIST_TABLE_COL_INDEX_INDEX]=String.valueOf( mItemListTable.getRowCount());
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

		int actualRowIndex;
		
		for(int row=0; row<mItemListTable.getRowCount();row++)
		{
			actualRowIndex=Integer.parseInt(String.valueOf( mItemListTable.getValueAt(row, LIST_TABLE_COL_INDEX_INDEX)));
			if (mOrderDetItems.get(actualRowIndex).isDirty()){
			
				mItemListTable.setValueAt("false", row, LIST_TABLE_COL_SEL_BUTTON_INDEX);
				mItemListTable.setValueAt(mItemListTable.getValueAt(row, LIST_TABLE_COL_QTY_INDEX), row, LIST_TABLE_COL_REF_QTY_INDEX);	
				mItemListTable.setValueAt(PosCurrencyUtil.format(getItemRefundableAmt(actualRowIndex)), row, LIST_TABLE_COL_REF_AMOUNT_INDEX);	
				mOrderDetItems.get(actualRowIndex).setDirty(false);
				mOrderDetItems.get(actualRowIndex).setRefundAmount(0);
				mOrderDetItems.get(actualRowIndex).setRefundQuantity(0);
			 
			}
		}
		
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
	public ArrayList<BeanOrderDetail> getSelectedItemList(){
		
		ArrayList<BeanOrderDetail>  selectedItems=null;
		
		final DefaultTableModel model=(DefaultTableModel) mItemListTable.getModel();
		for(int rowIndex=0; rowIndex<model.getRowCount(); rowIndex++){

				if(Boolean.valueOf(model.getValueAt(rowIndex, LIST_TABLE_COL_SEL_BUTTON_INDEX).toString())){
					
					if(selectedItems==null)
						selectedItems=new ArrayList<BeanOrderDetail>();
					
					selectedItems.add(mOrderDetItems.get(rowIndex));
				}

		}
		
		return selectedItems;
		
	}

	/**
	 * Returns the items to be refunded 
	 * @return
	 */
	public ArrayList<BeanOrderDetail> getRefundableItemList(){
		
		ArrayList<BeanOrderDetail>  pendingItems=null;
		
		final DefaultTableModel model=(DefaultTableModel) mItemListTable.getModel();
		for(int rowIndex=0; rowIndex<model.getRowCount(); rowIndex++){

			
				if( !mOrderDetItems.get(rowIndex).isRefunded()){
					
					if(pendingItems==null)
						pendingItems=new ArrayList<BeanOrderDetail>();
					mOrderDetItems.get(rowIndex).setRefundAmount(getItemRefundableAmt(rowIndex));
					final double qty=PosNumberUtil.parseDoubleSafely(String.valueOf(mItemListTable.getValueAt(rowIndex, LIST_TABLE_COL_QTY_INDEX)));
					mOrderDetItems.get(rowIndex).setRefundQuantity(qty);
					pendingItems.add(mOrderDetItems.get(rowIndex));
				}

		}
		
		return pendingItems;
	}
	
	/**
	 * @param listener the listener to set
	 */
	public void setListener(IRefundItemListTablePanelListener listener) {
		this.listener = listener;
	}

	/**
	 * @return
	 */

	 public BeanOrderDetail getSelectedItem(){
	 if (mSelectedItemIndex>=0){
		 final int actualRowIndex=Integer.parseInt(String.valueOf( mItemListTable.getValueAt(mSelectedItemIndex, LIST_TABLE_COL_INDEX_INDEX)));
			
		 return mOrderDetItems.get(actualRowIndex);
	 }
	 else
		 return null;
	 
		 
	 }
	/**
	 * @param amount
	 */
	public void setSelectedItemAdjustmentAmount(double amount){

		final int actualRowIndex=Integer.parseInt(String.valueOf( mItemListTable.getValueAt(mSelectedItemIndex, LIST_TABLE_COL_INDEX_INDEX)));
		final double actualItemTotal=  getItemRefundableAmt(actualRowIndex);
			if(actualItemTotal<amount) 
				
				PosFormUtil.showErrorMessageBox(this.mParent,"Refund amount should be less than actual amount.");
			else if(amount<=0)	
				PosFormUtil.showErrorMessageBox(this.mParent,"Refund amount should be greater than zero.");
			
			 else{
				
				mItemListTable.setValueAt(PosCurrencyUtil.format(amount), mSelectedItemIndex, LIST_TABLE_COL_REF_AMOUNT_INDEX);
				mOrderDetItems.get(actualRowIndex).setRefundAmount(amount);
				if(listener!=null )
					listener.onSelectionChanged(mSelectedItemIndex);
			}

	}
	/**
	 * @param amount
	 */
	public void setSelectedItemAdjustmentQty(double qty){

		final int actualRowIndex=Integer.parseInt(String.valueOf(mItemListTable.getValueAt(mSelectedItemIndex, LIST_TABLE_COL_INDEX_INDEX)));
		final double actualQty=PosNumberUtil.parseDoubleSafely(String.valueOf(mItemListTable.getValueAt(actualRowIndex, LIST_TABLE_COL_QTY_INDEX)));
		
			if(actualQty<qty)
				PosFormUtil.showErrorMessageBox(this.mParent,"Refund quantity should be less than actual quanity.");
			else if(qty<=0)	
				PosFormUtil.showErrorMessageBox(this.mParent,"Refund quantity should be greater than zero.");
			else{
				
				mItemListTable.setValueAt(PosUomUtil.format(qty,PosUOMProvider.getInstance().getMaxDecUom()), mSelectedItemIndex, LIST_TABLE_COL_REF_QTY_INDEX);
				
				final double actualItemTotal=  getItemRefundableAmt(actualRowIndex);
				final double refundItemTotal=(actualItemTotal/actualQty)*qty;
				mItemListTable.setValueAt(PosCurrencyUtil.format(refundItemTotal) , mSelectedItemIndex, LIST_TABLE_COL_REF_AMOUNT_INDEX);
				
				mOrderDetItems.get(actualRowIndex).setRefundQuantity(qty);
				mOrderDetItems.get(actualRowIndex).setRefundAmount(refundItemTotal);
				
				 	
				
				if(listener!=null )
					listener.onSelectionChanged(mSelectedItemIndex);
			}

	}
}
