/**
 * 
 */
package com.indocosmo.pos.forms.opensessions.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import com.indocosmo.pos.data.beans.BeanLoginSessions;
import com.indocosmo.pos.forms.components.jtable.CheckedSelectionTableCellRender;
import com.indocosmo.pos.forms.components.jtable.NoBorderTableCellSelectionRender;
import com.indocosmo.pos.forms.orderlistquery.components.listeners.IOrderListQueryTablePanelListener;
import com.indocosmo.pos.common.utilities.PosFormUtil;
/**
 * @author jojesh-13.2
 *
 */
public class OpenSessionsTablePanel extends JPanel {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int ROW_HEIGHT = 40;
	private static final int SCROLL_WIDTH = 25;
	private static final int FIELD_COUNT=5;
	
	/*
	 * Column header positions
	 */
	//	private static final int LIST_TABLE_COL_ITEM_INDEX=0;
	private static final int LIST_TABLE_COL_SEL_BUTTON_INDEX=0;
	private static final int LIST_TABLE_COL_CARD_NO_INDEX=1;
	private static final int LIST_TABLE_COL_USER_NAME_INDEX=2;
	private static final int LIST_TABLE_COL_STATION_NAME_INDEX=3;
	private static final int LIST_TABLE_COL_STARTED_AT_INDEX=4;
	
	/*
	 * Column width
	 */
	private static final int LIST_TABLE_COL_SEL_BUTTON_WIDTH=40;
	private static final int LIST_TABLE_COL_CARD_NO_WIDTH=90;
	private static final int LIST_TABLE_COL_USER_NAME_WIDTH=220;
		private static final int LIST_TABLE_COL_STARTED_AT_WIDTH=200;
	
	private static final int LIST_TABLE_HEADER_HEIGHT=30;

	private DefaultTableModel mOpenSessionsListTableModel;
	private JTable 	mOpenSessionsListTable; 
//	private BillSplitMethod mBillSplitMethod;
	private boolean isRowsSelectable=true;
	private int mSelectedItemIndex=-1;
	private ArrayList<BeanLoginSessions> mOpenSessionsList;
	
	private IOrderListQueryTablePanelListener  listener;
	private final SimpleDateFormat sd =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

//	final ArrayList<BeanLoginSessions> sessions=sessionProvider.getAllOpenSessions(PosEnvSettings.getInstance().getTillOpenCashierShiftInfo().getID());
	
	/**
	 * 
	 */
	public OpenSessionsTablePanel(JPanel parent, int width, int height  ) {
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
		mOpenSessionsListTable =createOpenSessionsListTablePanel(this);
		
	}

	 
	/**
	 * Creates the table
	 * @param tableListPanel
	 * @return
	 */
	private JTable createOpenSessionsListTablePanel(JPanel tableListPanel) {

		final DefaultTableCellRenderer rightAlign=new NoBorderTableCellSelectionRender();
		final DefaultTableCellRenderer centerAlign=new NoBorderTableCellSelectionRender();
		final CheckedSelectionTableCellRender selectionCell=new CheckedSelectionTableCellRender();
		final NoBorderTableCellSelectionRender noBorderCell=new NoBorderTableCellSelectionRender();
		rightAlign.setHorizontalAlignment(JLabel.RIGHT);
		centerAlign.setHorizontalAlignment(JLabel.CENTER);

		mOpenSessionsListTableModel =  PosFormUtil.getReadonlyTableModel(); ;
		mOpenSessionsListTableModel.setColumnIdentifiers(getColumnNames());

		final JTable sessionsListTable = new JTable(mOpenSessionsListTableModel);
		sessionsListTable.setRowSorter(new TableRowSorter<TableModel>(mOpenSessionsListTableModel));
		sessionsListTable.setFont(getFont().deriveFont(18f));
		sessionsListTable.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		sessionsListTable.getTableHeader().setPreferredSize(new Dimension(tableListPanel.getWidth() , LIST_TABLE_HEADER_HEIGHT));
		 
		sessionsListTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		sessionsListTable.getTableHeader().setReorderingAllowed(false);
		sessionsListTable.getTableHeader().setResizingAllowed(false);
		

		setColumnWidth(sessionsListTable);
		
		sessionsListTable.getColumnModel().getColumn(LIST_TABLE_COL_SEL_BUTTON_INDEX).setCellRenderer(selectionCell);
		sessionsListTable.getColumnModel().getColumn(LIST_TABLE_COL_CARD_NO_INDEX).setCellRenderer(noBorderCell);
		sessionsListTable.getColumnModel().getColumn(LIST_TABLE_COL_USER_NAME_INDEX).setCellRenderer(noBorderCell);
		sessionsListTable.getColumnModel().getColumn(LIST_TABLE_COL_STATION_NAME_INDEX).setCellRenderer(noBorderCell);
		sessionsListTable.getColumnModel().getColumn(LIST_TABLE_COL_STARTED_AT_INDEX).setCellRenderer(noBorderCell);
		
 
		sessionsListTable.addMouseListener(itemListTableMouseAdapter);
		sessionsListTable.addKeyListener(itemListTableKeyListener);
		sessionsListTable.getSelectionModel().addListSelectionListener(itemListTableListSelectionListener);

	 
		JScrollPane  openSessionsListTableScrollPane = new JScrollPane(sessionsListTable,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		openSessionsListTableScrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(SCROLL_WIDTH,0));
		openSessionsListTableScrollPane.setSize(new Dimension(tableListPanel.getWidth(),tableListPanel.getHeight()));
		openSessionsListTableScrollPane.setLocation(0,0);
		openSessionsListTableScrollPane.getViewport().setBackground(Color.WHITE);
		
		final int rowHeight=ROW_HEIGHT;
		sessionsListTable.setRowHeight(rowHeight);

		tableListPanel.add(openSessionsListTableScrollPane);
		
		return sessionsListTable;
	}
	
	
	/**
	 * @returns the table column width
	 */
	private int getStationNameColumWidth(){

		return getWidth()-
				-LIST_TABLE_COL_SEL_BUTTON_WIDTH
				-LIST_TABLE_COL_CARD_NO_WIDTH
				- LIST_TABLE_COL_USER_NAME_WIDTH
				- LIST_TABLE_COL_STARTED_AT_WIDTH 
				 -SCROLL_WIDTH
				-80;

	}

	
	/**
	 * @param itemListTable
	 */
	private void setColumnWidth(JTable orderListTable){

		orderListTable.getColumnModel().getColumn(LIST_TABLE_COL_SEL_BUTTON_INDEX).setPreferredWidth(LIST_TABLE_COL_SEL_BUTTON_WIDTH);
		orderListTable.getColumnModel().getColumn(LIST_TABLE_COL_CARD_NO_INDEX).setPreferredWidth(LIST_TABLE_COL_CARD_NO_WIDTH );
		orderListTable.getColumnModel().getColumn(LIST_TABLE_COL_USER_NAME_INDEX ).setPreferredWidth(LIST_TABLE_COL_USER_NAME_WIDTH );
		orderListTable.getColumnModel().getColumn(LIST_TABLE_COL_STATION_NAME_INDEX).setPreferredWidth(getStationNameColumWidth());
		orderListTable.getColumnModel().getColumn(LIST_TABLE_COL_STARTED_AT_INDEX).setPreferredWidth(LIST_TABLE_COL_STARTED_AT_WIDTH);
		
	}
	
 
	/**
	 * 
	 */
	private ListSelectionListener itemListTableListSelectionListener=new ListSelectionListener() {
		@Override
		public void valueChanged(ListSelectionEvent e) {

			final ListSelectionModel rowSM = (ListSelectionModel) e
					.getSource();
			mSelectedItemIndex = mOpenSessionsListTable
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
			int row=mOpenSessionsListTable.getSelectedRow();
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

			startRow= mOpenSessionsListTable.rowAtPoint(e.getPoint());
			startCol= mOpenSessionsListTable.columnAtPoint(e.getPoint());
			super.mousePressed(e);
		}
		/* (non-Javadoc)
		 * @see java.awt.event.MouseAdapter#mouseReleased(java.awt.event.MouseEvent)
		 */
		@Override
		public void mouseReleased(MouseEvent e) {

			int row = mOpenSessionsListTable.rowAtPoint(e.getPoint());
			int col= mOpenSessionsListTable.columnAtPoint(e.getPoint());
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
			final boolean isSelected=!mOpenSessionsList.get(row).isSelected();
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
			final boolean isSelected=!mOpenSessionsList.get(row).isSelected();
			if(col==0)
				setTableRowSelected(row,isSelected);
		}
		
	
	}
	
	/**
	 * @param row
	 * @param isSelected
	 */
	private void setTableRowSelected(int row, boolean isSelected){
		
		mOpenSessionsList.get(row).setSelected(isSelected);

		String selected=(isSelected?"true":"false");
		mOpenSessionsListTable.setValueAt(selected, row, LIST_TABLE_COL_SEL_BUTTON_INDEX);
		
		
	
	 
		
	}
	
	/**
	 * @param isSelected
	 */
	public void setListSelected( boolean isSelected){

		final DefaultTableModel model=(DefaultTableModel) mOpenSessionsListTable.getModel();
		String selected=(isSelected?"true":"false");
		
		for(int rowIndex=0; rowIndex<model.getRowCount(); rowIndex++){
			
			mOpenSessionsListTable.setValueAt(selected, rowIndex , LIST_TABLE_COL_SEL_BUTTON_INDEX);

		}
	}

	/**
	 * @param isSelected
	 * @param rowIndex
	 */
	public void setRowSelected( boolean isSelected ,int rowIndex){
		String selected=(isSelected?"true":"false");
		mOpenSessionsListTable.getSelectionModel().setSelectionInterval(0,rowIndex);
	}
	/**
	 * @returns the column name in its order
	 */
	private String[] getColumnNames(){

		String[] colNames=new String[FIELD_COUNT];
		
		colNames[LIST_TABLE_COL_STARTED_AT_INDEX]="Started At";
		colNames[LIST_TABLE_COL_STATION_NAME_INDEX]="Station Name";
		colNames[LIST_TABLE_COL_USER_NAME_INDEX ]="User Name";
		colNames[LIST_TABLE_COL_CARD_NO_INDEX ]="Card No";
		colNames[LIST_TABLE_COL_SEL_BUTTON_INDEX]="";

		return colNames;
	}


	/**
	 * @param splitItems
	 */
	public void SetOpenSessionList( ArrayList<BeanLoginSessions> openSessions){
		
		clearRows();
		this.mOpenSessionsList=openSessions;
		SetOpenSessionList();

	}
	
	/**
	 * 
	 */
	private void SetOpenSessionList(){
		
		if(mOpenSessionsList==null) return;
		
		DefaultTableModel model=(DefaultTableModel)mOpenSessionsListTable.getModel();
		
		for(BeanLoginSessions item:mOpenSessionsList  ){
			model.addRow(getRowValue(item));
		}

		mOpenSessionsListTable.invalidate();
		
	}
	
	/**
	 * 
	 */
	private void clearRows() {
		
		final DefaultTableModel model=(DefaultTableModel) mOpenSessionsListTable.getModel();
		mOpenSessionsListTable.getSelectionModel().removeListSelectionListener(itemListTableListSelectionListener);
		final int rowCount=model.getRowCount();
		for(int rowIndex=0; rowIndex<rowCount; rowIndex++){
			
			model.removeRow(0);
		}
		mOpenSessionsListTable.getSelectionModel().addListSelectionListener(itemListTableListSelectionListener);
	}
	
	/**
	 * 
	 */
	public void refresh(){
		
		clearRows();
		SetOpenSessionList();
	}


	/**
	 * @param splitItem
	 * @return
	 */
	private  String[] getRowValue(BeanLoginSessions openSession){
		String[] values=new String[FIELD_COUNT];
		values[LIST_TABLE_COL_SEL_BUTTON_INDEX]=String.valueOf(openSession.isSelected());
		values[LIST_TABLE_COL_CARD_NO_INDEX]=openSession.getCardNo();
		values[LIST_TABLE_COL_USER_NAME_INDEX]=openSession.getUserName();
		values[LIST_TABLE_COL_STATION_NAME_INDEX]=openSession.getStationName();
		values[LIST_TABLE_COL_STARTED_AT_INDEX]=openSession.getStartAt();
		return values;

	}

	/**
	 * @param selectable
	 */
	public void setRowsSelectable(boolean selectable){
		
		if(isRowsSelectable!=selectable)
			clearSelections();
		
		isRowsSelectable=selectable;
		mOpenSessionsListTable.setRowSelectionAllowed(isRowsSelectable);
		
	}

	/**
	 * 
	 */
	public void clearSelections(){

		for(int row=0; row<mOpenSessionsListTable.getRowCount();row++)
			mOpenSessionsListTable.setValueAt("false", row, LIST_TABLE_COL_SEL_BUTTON_INDEX);
		
		mOpenSessionsListTable.getSelectionModel().clearSelection();
		mOpenSessionsListTable.getColumnModel().getSelectionModel().clearSelection();

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
	public ArrayList<BeanLoginSessions> getSelectedSessionList(){
		
		ArrayList<BeanLoginSessions>  selectedSessions=null;
		
		final DefaultTableModel model=(DefaultTableModel) mOpenSessionsListTable.getModel();
		for(int rowIndex=0; rowIndex<model.getRowCount(); rowIndex++){

				if(Boolean.valueOf(model.getValueAt(rowIndex, LIST_TABLE_COL_SEL_BUTTON_INDEX).toString())){
					
					if(selectedSessions==null)
						selectedSessions=new ArrayList<BeanLoginSessions>();
					
					selectedSessions.add(mOpenSessionsList.get(rowIndex));
				}

		}
		
		return selectedSessions;
		
	}

	/**
	 * @param listener the listener to set
	 */
	public void setListener(IOrderListQueryTablePanelListener listener) {
		this.listener = listener;
	}

}
