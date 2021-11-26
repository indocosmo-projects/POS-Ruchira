/**
 * 
 */
package com.indocosmo.pos.forms.components;

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
import javax.swing.RowFilter;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import com.indocosmo.pos.common.enums.MessageDisplayStatus;
import com.indocosmo.pos.common.utilities.PosDateUtil;
import com.indocosmo.pos.common.utilities.PosFormUtil;
import com.indocosmo.pos.common.utilities.PosStringUtil;
import com.indocosmo.pos.data.beans.BeanFlashMessage;
import com.indocosmo.pos.forms.components.jtable.CheckedSelectionTableCellRender;
import com.indocosmo.pos.forms.components.jtable.NoBorderTableCellSelectionRender;
import com.indocosmo.pos.forms.components.listners.IFlashMessageListTablePanelListener;

/**
 * @author jojesh-13.2
 *
 */
public class FlashMessageListTablePanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int ROW_HEIGHT = 30;
	private static final int SCROLL_WIDTH = 25;
	private static final int FIELD_COUNT=3;
	
	/*
	 * Column header positions
	 */
	private static final int LIST_TABLE_COL_STATUS_INDEX=0;
	private static final int LIST_TABLE_COL_DATE_INDEX=1;
	private static final int LIST_TABLE_COL_TITLE_INDEX=2;
	
	/*
	 * Column width
	 */
	private static final int LIST_TABLE_COL_STATUS_WIDTH=20;
	private static final int LIST_TABLE_COL_DATE_WIDTH=180;
	private static  int LIST_TABLE_COL_TITLE_WIDTH=250;
	
	private DefaultTableModel mFlashMsgListTableModel;
	private JTable 	mFlashMsgListTable; 


	private int mSelectedItemIndex=-1;
	private ArrayList<BeanFlashMessage> mMessageList;
	
	private final SimpleDateFormat sd =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private TableRowSorter<TableModel> mResultSorter;
	private IFlashMessageListTablePanelListener  listener;
	/**
	 * 
	 */
	public FlashMessageListTablePanel(int width, int height  ) {
		 
		initForm(width,height);
	}
	
	/**
	 * @param parent
	 * @param width
	 * @param height
	 */
	private void initForm(int width, int height ){
	
		this.setLayout(null);
		this.setSize(new Dimension(width,height));
		this.setPreferredSize(new Dimension(width,height));
		LIST_TABLE_COL_TITLE_WIDTH=width-LIST_TABLE_COL_STATUS_WIDTH-LIST_TABLE_COL_DATE_WIDTH-SCROLL_WIDTH-2;
		mFlashMsgListTable =createFlashMsgListTablePanel(this);
		
	}

  
	/**
	 * Creates the table
	 * @param tableListPanel
	 * @return
	 */
	private JTable createFlashMsgListTablePanel(JPanel tableListPanel) {

		final DefaultTableCellRenderer rightAlign=new NoBorderTableCellSelectionRender();
		final DefaultTableCellRenderer centerAlign=new NoBorderTableCellSelectionRender();
		final NoBorderTableCellSelectionRender noBorderCell=new NoBorderTableCellSelectionRender();
		rightAlign.setHorizontalAlignment(JLabel.RIGHT);
		centerAlign.setHorizontalAlignment(JLabel.CENTER);

		mFlashMsgListTableModel =  PosFormUtil.getReadonlyTableModel(); ;
		mFlashMsgListTableModel.setColumnIdentifiers(getColumnNames());
		

		final JTable messageListTable = new JTable(mFlashMsgListTableModel);
		messageListTable.setRowSorter(new TableRowSorter<TableModel>(mFlashMsgListTableModel));
		messageListTable.setFont(PosFormUtil.getTextAreaFont());
		messageListTable.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		messageListTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		
//		orderListTable.getTableHeader().setPreferredSize(new Dimension(tableListPanel.getWidth() , LIST_TABLE_HEADER_HEIGHT));
 		
		messageListTable.getTableHeader().setReorderingAllowed(false);
		messageListTable.getTableHeader().setResizingAllowed(false);
		messageListTable.getTableHeader().setFont(PosFormUtil.getTableHeadingFont());

		setColumnWidth(messageListTable);
		
		messageListTable.getColumnModel().getColumn(LIST_TABLE_COL_STATUS_INDEX).setCellRenderer(centerAlign);
		messageListTable.getColumnModel().getColumn(LIST_TABLE_COL_TITLE_INDEX).setCellRenderer(noBorderCell);
		messageListTable.getColumnModel().getColumn(LIST_TABLE_COL_DATE_INDEX).setCellRenderer(noBorderCell);
		 
		messageListTable.addMouseListener(itemListTableMouseAdapter);
		messageListTable.addKeyListener(itemListTableKeyListener);
		messageListTable.getSelectionModel().addListSelectionListener(itemListTableListSelectionListener);

	 
		JScrollPane  messageListTableScrollPane = new JScrollPane(messageListTable,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		messageListTableScrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(SCROLL_WIDTH,0));
//		messageListTableScrollPane.getHorizontalScrollBar().setPreferredSize(new Dimension(0,SCROLL_WIDTH));
		messageListTableScrollPane.setSize(new Dimension(tableListPanel.getWidth(),tableListPanel.getHeight()));
		messageListTableScrollPane.setLocation(0,0);
		messageListTableScrollPane.getViewport().setBackground(Color.WHITE);
		messageListTableScrollPane.setAutoscrolls(true);
		final int rowHeight=ROW_HEIGHT;
		messageListTable.setRowHeight(rowHeight);

		tableListPanel.add(messageListTableScrollPane);
		
		
		mResultSorter = (TableRowSorter<TableModel>) messageListTable.getRowSorter();
		return messageListTable;
	}
	
	public void filterTable(String text){
		
		mFlashMsgListTable.clearSelection();
		
		mFlashMsgListTable.getSelectionModel().removeListSelectionListener(itemListTableListSelectionListener);
		mResultSorter.setRowFilter(null);
		mResultSorter.setRowFilter(RowFilter.regexFilter("(?i)" + PosStringUtil.escapeSpecialRegexChars(text),1,2,4,5,8,9,10));
		mFlashMsgListTable.getSelectionModel().addListSelectionListener(itemListTableListSelectionListener);
	}
	
	/**
	 * @param itemListTable
	 */
	private void setColumnWidth(JTable orderListTable){
		
		orderListTable.getColumnModel().getColumn(LIST_TABLE_COL_STATUS_INDEX ).setPreferredWidth(LIST_TABLE_COL_STATUS_WIDTH );
		orderListTable.getColumnModel().getColumn(LIST_TABLE_COL_TITLE_INDEX).setPreferredWidth(LIST_TABLE_COL_TITLE_WIDTH );
		orderListTable.getColumnModel().getColumn(LIST_TABLE_COL_DATE_INDEX).setPreferredWidth(LIST_TABLE_COL_DATE_WIDTH);
		
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
			
			mSelectedItemIndex = mFlashMsgListTable
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
			int row=mFlashMsgListTable.getSelectedRow();
			if(e.getKeyCode()==KeyEvent.VK_ENTER){
				onTableRowSelected(row);
//				if(listener!=null)
//					listener.onSelectionChanged(row);
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

			startRow= mFlashMsgListTable.rowAtPoint(e.getPoint());
			startCol= mFlashMsgListTable.columnAtPoint(e.getPoint());
			super.mousePressed(e);
		}
		/* (non-Javadoc)
		 * @see java.awt.event.MouseAdapter#mouseReleased(java.awt.event.MouseEvent)
		 */
		@Override
		public void mouseReleased(MouseEvent e) {
			
			int row = mFlashMsgListTable.rowAtPoint(e.getPoint());
			int col= mFlashMsgListTable.columnAtPoint(e.getPoint());
			if(startRow==row && startCol==col && e.getClickCount()==2)
				onTableRowSelected(row );
			super.mouseReleased(e);
		}
	};
 
	
	/**
	 * Toggles the selected rows check
	 * @param row
	 */
	private void onTableRowSelected(int row){
 
		if(listener!=null)
			listener.onSelectionChanged(row);
		
	
	}

	/**
	 * @returns the column name in its order
	 */
	private String[] getColumnNames(){

	
		String[] colNames=new String[FIELD_COUNT];
		colNames[LIST_TABLE_COL_STATUS_INDEX ]=" ";
		colNames[LIST_TABLE_COL_DATE_INDEX ]="Received ";
		colNames[LIST_TABLE_COL_TITLE_INDEX]="Subject";
		
		return colNames;
	}


	/**
	 * @param splitItems
	 */
	public void setMessageList( ArrayList<BeanFlashMessage> messageList){
		
		clearRows();
		this.mMessageList=messageList ;
		setMessageList();
	}
	
	/**
	 * 
	 */
	private void setMessageList(){
		
		mFlashMsgListTable.getSelectionModel().removeListSelectionListener(itemListTableListSelectionListener);
		mResultSorter.setRowFilter(null);
		
		DefaultTableModel model=(DefaultTableModel)mFlashMsgListTable.getModel();
		
		int slNo=0;
		for(BeanFlashMessage item:mMessageList ){
			model.addRow(getRowValue(item,++slNo));
		}
		mFlashMsgListTable.getSelectionModel().addListSelectionListener(itemListTableListSelectionListener);
		
		mFlashMsgListTable.invalidate();
		
	}
	
	/**
	 * 
	 */
	private void clearRows() {
		
		final DefaultTableModel model=(DefaultTableModel) mFlashMsgListTable.getModel();
		mFlashMsgListTable.getSelectionModel().removeListSelectionListener(itemListTableListSelectionListener);
		final int rowCount=model.getRowCount();
		for(int rowIndex=0; rowIndex<rowCount; rowIndex++){
			
			model.removeRow(0);
		}
		mFlashMsgListTable.getSelectionModel().addListSelectionListener(itemListTableListSelectionListener);
	}
	
	/**
	 * 
	 */
	public void refresh(){
		
		clearRows();
		setMessageList();
	}


	/**
	 * @param splitItem
	 * @return
	 */
	private  String[] getRowValue(BeanFlashMessage message,int slNo){
		
		String[] values=new String[FIELD_COUNT];
		
		final String startText=message.getDisplayStatus()==MessageDisplayStatus.READ?"":"<html><body><B>"; 
		final String endText=message.getDisplayStatus()==MessageDisplayStatus.READ?"":"</B></body></html>"; 
		
		values[LIST_TABLE_COL_STATUS_INDEX]=startText + String.valueOf(slNo) + endText;
		values[LIST_TABLE_COL_TITLE_INDEX]= startText +  message.getTitle() + endText;
		values[LIST_TABLE_COL_DATE_INDEX]= startText + PosDateUtil.formatShortDateTime(message.getCreatedAt() ) + endText;
		return values;

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
	public BeanFlashMessage getSelectedMessage(){
		
		BeanFlashMessage selectedMessage=null;
		
		if(mSelectedItemIndex>=0)
			selectedMessage =mMessageList.get(mSelectedItemIndex);
		
		return selectedMessage;
		
	}

	/**
	 * @param listener the listener to set
	 */
	public void setListener(IFlashMessageListTablePanelListener listener) {
		this.listener = listener;
	}




}
 
