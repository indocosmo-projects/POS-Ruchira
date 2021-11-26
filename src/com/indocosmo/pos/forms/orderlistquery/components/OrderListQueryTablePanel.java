/**
 * 
 */
package com.indocosmo.pos.forms.orderlistquery.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import com.indocosmo.pos.common.PosEnvSettings;
import com.indocosmo.pos.common.PosLog;
import com.indocosmo.pos.common.enums.PosOrderServiceTypes;
import com.indocosmo.pos.common.enums.PosTerminalServiceType;
import com.indocosmo.pos.common.utilities.PosCurrencyUtil;
import com.indocosmo.pos.common.utilities.PosDateUtil;
import com.indocosmo.pos.common.utilities.PosFormUtil;
import com.indocosmo.pos.common.utilities.PosNumberUtil;
import com.indocosmo.pos.common.utilities.PosOrderUtil;
import com.indocosmo.pos.common.utilities.PosStringUtil;
import com.indocosmo.pos.common.utilities.PosUomUtil;
import com.indocosmo.pos.data.beans.BeanOrderHeader.PosOrderStatus;
import com.indocosmo.pos.data.beans.BeanOrderStatusReport;
import com.indocosmo.pos.data.providers.shopdb.PosUOMProvider;
import com.indocosmo.pos.forms.components.jtable.CheckedSelectionTableCellRender;
import com.indocosmo.pos.forms.components.jtable.NoBorderTableCellSelectionRender;
import com.indocosmo.pos.forms.components.search.IPosSearchableItem;
import com.indocosmo.pos.forms.orderlistquery.components.listeners.IOrderListQueryTablePanelListener;

/**
 * @author jojesh-13.2
 *
 */
public class OrderListQueryTablePanel extends JPanel {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int ROW_HEIGHT = 40;
	private static final int SCROLL_WIDTH = 25;
	private final static DefaultTableCellRenderer COL_RIGHT_RENDER = new NoBorderTableCellSelectionRender();
	private final static DefaultTableCellRenderer COL_LEFT_RENDER = new NoBorderTableCellSelectionRender();
	
		
	/*
	 * Column header positions
	 */
	//	private static final int LIST_TABLE_COL_ITEM_INDEX=0;
	private static final int LIST_TABLE_COL_SEL_BUTTON_INDEX=0; 
	private static final int LIST_TABLE_COL_SEL_BUTTON_WIDTH=0;

	

	
	private static final int LIST_TABLE_HEADER_HEIGHT=30;

	private DefaultTableModel mOrderListTableModel;
	private JTable 	mOrderListTable; 
//	private BillSplitMethod mBillSplitMethod;
	private boolean isRowsSelectable=true;
	private int mSelectedItemIndex=-1;
	private ArrayList<BeanOrderStatusReport> orderHdrList;
	
	private IOrderListQueryTablePanelListener  listener;
	private final SimpleDateFormat sd =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private TableRowSorter<TableModel> mResultSorter;
	private String[] mFieldList;
	
	/**mFieldList=PosEnvSettings.getInstance().getUISetting().getOrderListSettings().getOrderListSearchFieldList();
	 * 
	 */
	public OrderListQueryTablePanel(JPanel parent, int width, int height  ) {
			
		initForm(parent,width,height);
	}
	
	/**
	 * @param parent
	 * @param width
	 * @param height
	 */
	private void initForm(JPanel parent, int width, int height ){
	
		this.setLayout(null);
		this.setSize(new Dimension(width,height));
		this.setPreferredSize(new Dimension(width,height));
		
		COL_RIGHT_RENDER.setHorizontalAlignment(JLabel.RIGHT);
		COL_RIGHT_RENDER.setHorizontalTextPosition(JLabel.LEFT);
		 mFieldList=PosEnvSettings.getInstance().getUISetting().getOrderListSettings().getOrderListSearchFieldList();
		mOrderListTable =createOrderListTablePanel(this);
		
	}

  
	/**
	 * Creates the table
	 * @param tableListPanel
	 * @return
	 */
	private JTable createOrderListTablePanel(JPanel tableListPanel) {

		final DefaultTableCellRenderer rightAlign=new NoBorderTableCellSelectionRender();
		final DefaultTableCellRenderer centerAlign=new NoBorderTableCellSelectionRender();
		final CheckedSelectionTableCellRender selectionCell=new CheckedSelectionTableCellRender();
		final NoBorderTableCellSelectionRender noBorderCell=new NoBorderTableCellSelectionRender();
		rightAlign.setHorizontalAlignment(JLabel.RIGHT);
		centerAlign.setHorizontalAlignment(JLabel.CENTER);

		mOrderListTableModel =  PosFormUtil.getReadonlyTableModel(); ;
		mOrderListTableModel.setColumnIdentifiers(getColumnNames());
		

		final JTable orderListTable = new JTable(mOrderListTableModel);
		orderListTable.setRowSorter(new TableRowSorter<TableModel>(mOrderListTableModel));
		//orderListTable.setFont(getFont().deriveFont(18f));
		orderListTable.setFont(PosFormUtil.getTableCellFont());
		orderListTable.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		orderListTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		
//		orderListTable.getTableHeader().setPreferredSize(new Dimension(tableListPanel.getWidth() , LIST_TABLE_HEADER_HEIGHT));
 		
		orderListTable.getTableHeader().setReorderingAllowed(false);
		orderListTable.getTableHeader().setResizingAllowed(true);
		orderListTable.getTableHeader().setFont(PosFormUtil.getTableHeadingFont());

		setColumnWidth(orderListTable);
		setColAlignment(orderListTable);
		
//		orderListTable.getColumnModel().getColumn(LIST_TABLE_COL_SEL_BUTTON_INDEX).setCellRenderer(selectionCell);
//		orderListTable.getColumnModel().getColumn(LIST_TABLE_COL_ORDER_NO_INDEX).setCellRenderer(noBorderCell);
//		orderListTable.getColumnModel().getColumn(LIST_TABLE_COL_INV_QUE_NO_INDEX).setCellRenderer(noBorderCell);
//		orderListTable.getColumnModel().getColumn(LIST_TABLE_COL_ORDER_DATE_INDEX).setCellRenderer(centerAlign);
//		orderListTable.getColumnModel().getColumn(LIST_TABLE_COL_ITEM_QTY_INDEX).setCellRenderer(rightAlign);
//		orderListTable.getColumnModel().getColumn(LIST_TABLE_COL_AMOUNT_INDEX).setCellRenderer(rightAlign);
//		orderListTable.getColumnModel().getColumn(LIST_TABLE_COL_SERVEDBY_INDEX).setCellRenderer(noBorderCell);
//		orderListTable.getColumnModel().getColumn(LIST_TABLE_COL_STATUS_INDEX).setCellRenderer(noBorderCell);
//		orderListTable.getColumnModel().getColumn(LIST_TABLE_COL_TABLENO_INDEX).setCellRenderer(centerAlign);
//		orderListTable.getColumnModel().getColumn(LIST_TABLE_COL_CUST_NAME_INDEX).setCellRenderer(noBorderCell);
//		orderListTable.getColumnModel().getColumn(LIST_TABLE_COL_CUST_PHONE_INDEX).setCellRenderer(noBorderCell); 
		 
		orderListTable.addMouseListener(itemListTableMouseAdapter);
		orderListTable.addKeyListener(itemListTableKeyListener);
		orderListTable.getSelectionModel().addListSelectionListener(itemListTableListSelectionListener);

	 
		JScrollPane  orderListTableScrollPane = new JScrollPane(orderListTable,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		orderListTableScrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(SCROLL_WIDTH,0));
		orderListTableScrollPane.getHorizontalScrollBar().setPreferredSize(new Dimension(0,SCROLL_WIDTH));
		orderListTableScrollPane.setSize(new Dimension(tableListPanel.getWidth(),tableListPanel.getHeight()));
		orderListTableScrollPane.setLocation(0,0);
		orderListTableScrollPane.getViewport().setBackground(Color.WHITE);
		orderListTableScrollPane.setAutoscrolls(true);
		final int rowHeight=ROW_HEIGHT;
		orderListTable.setRowHeight(rowHeight);

		tableListPanel.add(orderListTableScrollPane);
		
		
		mResultSorter = (TableRowSorter<TableModel>) orderListTable.getRowSorter();
		return orderListTable;
	}
	
	public void filterTable(String text){
		
		mOrderListTable.clearSelection();
		
		clearSelections();		
		mOrderListTable.getSelectionModel().removeListSelectionListener(itemListTableListSelectionListener);
		mResultSorter.setRowFilter(null);
		mResultSorter.setRowFilter(RowFilter.regexFilter("(?i)" + PosStringUtil.escapeSpecialRegexChars(text),1,2,4,5,8,9,10));
		mOrderListTable.getSelectionModel().addListSelectionListener(itemListTableListSelectionListener);
	}
 

	
 
	/**
	 * 
	 */
	private ListSelectionListener itemListTableListSelectionListener=new ListSelectionListener() {
		@Override
		public void valueChanged(ListSelectionEvent e) {

			
			final ListSelectionModel rowSM = (ListSelectionModel) e
					.getSource();
			if(rowSM.getLeadSelectionIndex()==-1) return;
			
			mSelectedItemIndex = mOrderListTable
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
			int row=mOrderListTable.getSelectedRow();
			if(e.getKeyCode()==KeyEvent.VK_SPACE || e.getKeyCode()==KeyEvent.VK_ENTER){
				onTableRowSelected(row);
				if(listener!=null)
					listener.onSelectionChanged(row);
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

			startRow= mOrderListTable.rowAtPoint(e.getPoint());
			startCol= mOrderListTable.columnAtPoint(e.getPoint());
			super.mousePressed(e);
		}
		/* (non-Javadoc)
		 * @see java.awt.event.MouseAdapter#mouseReleased(java.awt.event.MouseEvent)
		 */
		@Override
		public void mouseReleased(MouseEvent e) {

			int row = mOrderListTable.rowAtPoint(e.getPoint());
			int col= mOrderListTable.columnAtPoint(e.getPoint());
			if(startRow==row && startCol==col)
				onTableRowSelected(row,col);
			if(listener!=null)
				listener.onSelectionChanged(row);
			super.mouseReleased(e);
		}
	};

	

	/**
	 * Toggles the selected rows check
	 * @param row
	 */
	private void onTableRowSelected(int row){
		 
		if(isRowsSelectable) 
		{
			final boolean isSelected=!orderHdrList.get(row).isSelected();
			setTableRowSelected(row,isSelected);
		}
		if(listener!=null)
			listener.onSelectionChanged(row);
		
	
	}


	/**
	 * Toggles the selected rows check
	 * @param row
	 */
	private void onTableRowSelected(int row,int col){
		 
		if(isRowsSelectable) 
		{
			final boolean isSelected=!orderHdrList.get(row).isSelected();
			if(col==0)
				setTableRowSelected(row,isSelected);
		}
		
	}
	
	/**
	 * @param row
	 * @param isSelected
	 */
	private void setTableRowSelected(int row, boolean isSelected){
		
		orderHdrList.get(row).setSelected(isSelected);

		String selected=(isSelected?"true":"false");
		mOrderListTable.setValueAt(selected, row, LIST_TABLE_COL_SEL_BUTTON_INDEX);
	}
	
	/**
	 * @param isSelected
	 */
	public void setListSelected( boolean isSelected){

		final DefaultTableModel model=(DefaultTableModel) mOrderListTable.getModel();
		String selected=(isSelected?"true":"false");
		
		for(int rowIndex=0; rowIndex<model.getRowCount(); rowIndex++){
			
			mOrderListTable.setValueAt(selected, rowIndex , LIST_TABLE_COL_SEL_BUTTON_INDEX);

		}
	}

	/**
	 * @param isSelected
	 * @param rowIndex
	 */
	public void setRowSelected( boolean isSelected ,int rowIndex){
		String selected=(isSelected?"true":"false");
		
			
		mOrderListTable.getSelectionModel().setSelectionInterval(0,rowIndex);
	}
	
	/***
	 * sets the column alignment
	 * 
	 * @param table
	 * @param colIndex
	 */
	private void setColAlignment(JTable table) {
	
		final BeanOrderStatusReport orderHdr=new BeanOrderStatusReport();
		final Class<?> itemClass = orderHdr.getClass(); 
		try {
			for (int colIndex = 0; colIndex < mFieldList.length; colIndex++) {
				DefaultTableCellRenderer cr = COL_LEFT_RENDER;
				Method method = itemClass.getMethod(mFieldList[colIndex],
						new Class[] {});
				Class<?> cls = method.getReturnType();
				if (cls.equals(Double.TYPE) || cls.equals(Float.TYPE)
						|| cls.equals(Integer.TYPE))
					cr = COL_RIGHT_RENDER;
				table.getColumnModel().getColumn(colIndex+1).setCellRenderer(cr);
			}
		} catch (NoSuchMethodException e) {

		} catch (SecurityException e) {

		}

	}
	/**
	 * @param itemListTable
	 */
	private void setColumnWidth(JTable orderListTable){

 
		orderListTable.getColumnModel().getColumn(LIST_TABLE_COL_SEL_BUTTON_INDEX).setMinWidth(LIST_TABLE_COL_SEL_BUTTON_WIDTH);
		orderListTable.getColumnModel().getColumn(LIST_TABLE_COL_SEL_BUTTON_INDEX).setPreferredWidth(LIST_TABLE_COL_SEL_BUTTON_WIDTH );
		orderListTable.getColumnModel().getColumn(LIST_TABLE_COL_SEL_BUTTON_INDEX).setMinWidth(LIST_TABLE_COL_SEL_BUTTON_WIDTH);
 
		int i=1;
		for(int width: PosEnvSettings.getInstance().getUISetting().getOrderListSettings().getOrderListSearchFieldWidth()){
			orderListTable.getColumnModel().getColumn(i).setPreferredWidth(width );
			++i;
		} 
		 
	}
	/**
	 * @returns the column name in its order
	 */
	private String[] getColumnNames(){

		
//		String[] colNames=new String[FIELD_COUNT];
//		colNames[LIST_TABLE_COL_ORDER_NO_INDEX]="Ref. No.";
//		colNames[LIST_TABLE_COL_INV_QUE_NO_INDEX ]="Inv./[Que. No.]";
//		colNames[LIST_TABLE_COL_STATUS_INDEX ]="Status";
//		colNames[LIST_TABLE_COL_SERVEDBY_INDEX ]="By";
//		colNames[LIST_TABLE_COL_AMOUNT_INDEX]="Amount";
//		colNames[LIST_TABLE_COL_ITEM_QTY_INDEX]="Item #";
//		colNames[LIST_TABLE_COL_ORDER_DATE_INDEX ]="Date";
//		colNames[LIST_TABLE_COL_TABLENO_INDEX ]="Table/Service";
//		colNames[LIST_TABLE_COL_CUST_NAME_INDEX ]="Customer ";
//		colNames[LIST_TABLE_COL_CUST_PHONE_INDEX ]="Phone No.";
//		colNames[LIST_TABLE_COL_SEL_BUTTON_INDEX]="";
//		
		String[] colNames=new String[PosEnvSettings.getInstance().getUISetting().getOrderListSettings().getOrderListSearchColumnNames().length+1];
		int i=1;
		for(String colName: PosEnvSettings.getInstance().getUISetting().getOrderListSettings().getOrderListSearchColumnNames()){
			colNames[i]=colName;
			++i;
		}
					
		return colNames;
	}


	/**
	 * @param splitItems
	 */
	public void SetOrderHdrList( ArrayList<BeanOrderStatusReport> orderHdrs){
		
		clearRows();
		this.orderHdrList=orderHdrs ;
		SetOrderHdrList();
//		mOrderListTable.invalidate();

	}
	
	/**
	 * 
	 */
	private void SetOrderHdrList(){
		
		mOrderListTable.getSelectionModel().removeListSelectionListener(itemListTableListSelectionListener);
		mResultSorter.setRowFilter(null);
		
		DefaultTableModel model=(DefaultTableModel)mOrderListTable.getModel();
		 
		for(BeanOrderStatusReport item:orderHdrList ){
			try{
				model.addRow(getRowValue(item, orderHdrList.get(0).getClass()));
			} catch (Exception e) {
				PosLog.write(this, "getTableModel", e);
				PosFormUtil.showErrorMessageBox(this,
						"Failed to get search data. Contact Administrator.");
			}
		}
		mOrderListTable.getSelectionModel().addListSelectionListener(itemListTableListSelectionListener);
		
		mOrderListTable.invalidate();
		
	}
	
	/**
	 * 
	 */
	private void clearRows() {
		
		final DefaultTableModel model=(DefaultTableModel) mOrderListTable.getModel();
		mOrderListTable.getSelectionModel().removeListSelectionListener(itemListTableListSelectionListener);
		final int rowCount=model.getRowCount();
		for(int rowIndex=0; rowIndex<rowCount; rowIndex++){
			
			model.removeRow(0);
		}
		mOrderListTable.getSelectionModel().addListSelectionListener(itemListTableListSelectionListener);
	}
	
	/**
	 * 
	 */
	public void refresh(){
		
		clearRows();
		SetOrderHdrList();
	}

 
	/**
	 * @param splitItem
	 * @return
	 */
	
	private  String[] getRowValue(BeanOrderStatusReport item, Class<?> itemClass) throws Exception{
		
		String[] mFieldFormatList=PosEnvSettings.getInstance().getUISetting().getOrderListSettings().getOrderListSearchFieldFormats();
		
		String[] values = new String[mFieldList.length+1];
		
		
		values[LIST_TABLE_COL_SEL_BUTTON_INDEX]=String.valueOf(item.isSelected());
		
		for (int index = 0; index < mFieldList.length; index++) {
			
			final String format=(mFieldFormatList!=null && index<mFieldFormatList.length)?mFieldFormatList[index]:"";
			final Method method = itemClass.getMethod(mFieldList[index], new Class[] {});
			
			if (method.getReturnType().equals(Double.TYPE))
				values[index+1] = PosNumberUtil.format(Double.parseDouble(method.invoke(item, new Object[] {}).toString()),format);
			else
				values[index+1] =	String.valueOf(method.invoke(item, new Object[] {}));
		}
		
		return values;
	}

	/**
	 * @param selectable
	 */
	public void setRowsSelectable(boolean selectable){
		
		if(isRowsSelectable!=selectable)
			clearSelections();
		
		isRowsSelectable=selectable;
		mOrderListTable.setRowSelectionAllowed(isRowsSelectable);
		
	}

	/**
	 * 
	 */
	public void clearSelections(){

		for(int row=0; row<mOrderListTable.getRowCount();row++)
			mOrderListTable.setValueAt("false", row, LIST_TABLE_COL_SEL_BUTTON_INDEX);
		
		mOrderListTable.getSelectionModel().clearSelection();
		mOrderListTable.getColumnModel().getSelectionModel().clearSelection();

		mSelectedItemIndex=-1;
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
	public ArrayList<BeanOrderStatusReport> getSelectedItemList(){
		
		ArrayList<BeanOrderStatusReport>  selectedItems=null;
		
		final DefaultTableModel model=(DefaultTableModel) mOrderListTable.getModel();
		for(int rowIndex=0; rowIndex<model.getRowCount(); rowIndex++){

				if(Boolean.valueOf(model.getValueAt(rowIndex, LIST_TABLE_COL_SEL_BUTTON_INDEX).toString())){
					
					if(selectedItems==null)
						selectedItems=new ArrayList<BeanOrderStatusReport>();
					
					selectedItems.add(orderHdrList.get(rowIndex));
				}

		}
		
		return selectedItems;
		
	}

	/**
	 * @param listener the listener to set
	 */
	public void setListener(IOrderListQueryTablePanelListener listener) {
		this.listener = listener;
	}

	 public String getSelectedOrderId(){
		 if (mSelectedItemIndex>=0)
			 return orderHdrList.get(mSelectedItemIndex).getOrderId();
		 else
			 return "";
		 
	 }
	  
	 public PosOrderStatus getSelectedOrderStatus(){
		 if (mSelectedItemIndex>=0)
			 return orderHdrList.get(mSelectedItemIndex).getStatus();
		 else
			 return null;
		 
	 }
	 
//	 public void setSelectedOrderStatus(PosOrderStatus orderStatus){
//		 if (mSelectedItemIndex>=0){
//			 orderHdrList.get(mSelectedItemIndex).setStatus(orderStatus);
//			 mOrderListTable.setValueAt(orderStatus, mOrderListTable.getSelectedRow(), LIST_TABLE_COL_STATUS_INDEX);
//		 }
// 
//	 }
	  
	 public BeanOrderStatusReport getSelectedOrder(){
		 if (mSelectedItemIndex>=0)
			 return orderHdrList.get(mSelectedItemIndex) ;
		 else
			 return null;
		 
	 }
	

}
