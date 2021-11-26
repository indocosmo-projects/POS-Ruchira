/**
 * 
 */
package com.indocosmo.pos.forms.components.terminal;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.TimerTask;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import com.indocosmo.pos.common.utilities.PosFormUtil;
import com.indocosmo.pos.common.utilities.PosUomUtil;
import com.indocosmo.pos.data.beans.BeanDataSync;
import com.indocosmo.pos.data.beans.BeanStockItem;
import com.indocosmo.pos.data.providers.shopdb.PosServerSyncStatusProvider;
import com.indocosmo.pos.data.providers.shopdb.PosStockInHdrProvider;
import com.indocosmo.pos.data.providers.shopdb.PosUOMProvider;
import com.indocosmo.pos.forms.components.jtable.CheckedSelectionTableCellRender;
import com.indocosmo.pos.forms.components.jtable.NoBorderTableCellSelectionRender;

/**
 * @author LAP-L530
 *
 */
public class PosStockInfoPanel extends JPanel {
	
	private static final int FIELD_COUNT=4;
	private static final int PANEL_CONTENT_GAP=2;
	private int mHeight;
	private int mWidth;
	PosStockInHdrProvider stockInHdrProvider = null;
	JLabel headingLabel;
	JTable mSyncStatusTable;
	DefaultTableModel mStockInTableModel;
	/**
	 * @param mHeight
	 * @param mWidth
	 */
	public PosStockInfoPanel(int mHeight, int mWidth) {
		super();
		this.mHeight = mHeight;
		this.mWidth = mWidth-PANEL_CONTENT_GAP*2;
		initControls();
	}
	
	
	private void initControls(){
		this.setSize(mWidth,mHeight );
		this.setPreferredSize(new Dimension(mWidth,mHeight ));
		this.setLayout(new FlowLayout(FlowLayout.CENTER,PANEL_CONTENT_GAP,PANEL_CONTENT_GAP));
	
//		this.setLocation(0, 367);
		this.setOpaque(false);
		
		headingLabel=new JLabel("Stock",SwingConstants.CENTER);
		headingLabel.setVerticalAlignment(SwingConstants.CENTER);
		headingLabel.setBounds(new Rectangle(0, 0,mWidth-PANEL_CONTENT_GAP, 75));
//		headingLabel.setLocation(100, 100);
//		headingLabel.setSize(new Dimension(mWidth,20));
		headingLabel.setPreferredSize(new Dimension(this.getWidth()-PANEL_CONTENT_GAP,30));
		headingLabel.setBackground(new Color(78,128,188));
		headingLabel.setForeground(Color.WHITE);
		headingLabel.setOpaque(true);
		headingLabel.setFont(PosFormUtil.getSubHeadingFont());
		this.add(headingLabel);
		
		final DefaultTableCellRenderer rightAlign=new NoBorderTableCellSelectionRender();
		final DefaultTableCellRenderer centerAlign=new NoBorderTableCellSelectionRender();
		final DefaultTableCellRenderer leftAlign=new NoBorderTableCellSelectionRender();
		rightAlign.setHorizontalAlignment(JLabel.RIGHT);
		centerAlign.setHorizontalAlignment(JLabel.CENTER);
		leftAlign.setHorizontalAlignment(JLabel.LEFT);
		
		
		mStockInTableModel =  PosFormUtil.getReadonlyTableModel();
		mStockInTableModel.setColumnIdentifiers(new String[] {"Item ","Opening","Sales ","Current"});
		
		JTable stockInfoTable = new JTable(mStockInTableModel);
		stockInfoTable.setRowSorter(new TableRowSorter<TableModel>(mStockInTableModel));
		stockInfoTable.setFont(PosFormUtil.getTableCellFont());
		stockInfoTable.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		stockInfoTable.getTableHeader().setResizingAllowed(true);
		stockInfoTable.getTableHeader().setReorderingAllowed(false);
		stockInfoTable.setRequestFocusEnabled(false);
		stockInfoTable.setFocusable(false);
		stockInfoTable.setFocusTraversalPolicyProvider(false);
		stockInfoTable.setRowSelectionAllowed(false);
		stockInfoTable.getTableHeader().setFont(PosFormUtil.getTableHeadingFont());
		stockInfoTable.getColumnModel().getColumn(0).setCellRenderer(leftAlign);
		stockInfoTable.getColumnModel().getColumn(1).setCellRenderer(centerAlign);
		stockInfoTable.getColumnModel().getColumn(2).setCellRenderer(centerAlign);
		stockInfoTable.getColumnModel().getColumn(3).setCellRenderer(centerAlign);
		stockInfoTable.getColumnModel().getColumn(1).setMaxWidth(80);
		stockInfoTable.getColumnModel().getColumn(2).setMaxWidth(80);
		stockInfoTable.getColumnModel().getColumn(3).setMaxWidth(80);
		
		JScrollPane  orderListTableScrollPane = new JScrollPane(stockInfoTable,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		orderListTableScrollPane.setPreferredSize(new Dimension(mWidth, mHeight-40));
		orderListTableScrollPane.getViewport().setBackground(Color.WHITE);
		orderListTableScrollPane.setAutoscrolls(true);
		stockInfoTable.setRowHeight(35);
		stockInHdrProvider = new PosStockInHdrProvider();
		this.add(orderListTableScrollPane);
		
		java.util.Timer timer = new java.util.Timer();
		timer.scheduleAtFixedRate(new schduledTask(), 1000, 10000);
	}
	
	class schduledTask extends TimerTask
	{
		@Override
		public void run() {
			mStockInTableModel.setRowCount(0);
			ArrayList<BeanStockItem> stockItems = null;
			try {
				stockItems = stockInHdrProvider.getStockItems();;
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			String[] values=new String[FIELD_COUNT];
			
			for (int i = 0; i < stockItems.size(); i++) {
				values[0] = stockItems.get(i).getName();
				values[1] = PosUomUtil.format(stockItems.get(i).getOpeningStock(),PosUOMProvider.getInstance().getMaxDecUom());
				values[2] =PosUomUtil.format(stockItems.get(i).getSalesQty(),PosUOMProvider.getInstance().getMaxDecUom());  
				values[3] = PosUomUtil.format(stockItems.get(i).getCurrentStock(),PosUOMProvider.getInstance().getMaxDecUom());  
				mStockInTableModel.addRow(values);
			}
		}
	}

}
