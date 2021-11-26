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
import com.indocosmo.pos.data.beans.BeanDataSync;
import com.indocosmo.pos.data.providers.shopdb.PosServerSyncStatusProvider;
import com.indocosmo.pos.forms.components.jtable.CheckedSelectionTableCellRender;
import com.indocosmo.pos.forms.components.jtable.NoBorderTableCellSelectionRender;

/**
 * @author LAP-L530
 *
 */
public class PosSyncStatusInfoPanel extends JPanel {
	
	private static final int PANEL_CONTENT_GAP=2;
	
	private int mHeight;
	private int mWidth;
	PosServerSyncStatusProvider posServerSyncStatusProvider = null;
	Object[] object = null; 
	DefaultTableModel dtm=null;
	DefaultTableModel mSyncStatusTableModel;
	JLabel headingLabel;
	JTable mSyncStatusTable;
	
	/**
	 * @param mHeight
	 * @param mWidth
	 */
	public PosSyncStatusInfoPanel(int mHeight, int mWidth) {
		super();
		this.mHeight = mHeight;
		this.mWidth = mWidth-PANEL_CONTENT_GAP*2;
		initControls();
	}
	
	private void initControls(){
		this.setSize(mWidth,mHeight );
//		this.setLocation(0, 367);
		this.setOpaque(false);
		this.setLayout(new FlowLayout(FlowLayout.CENTER,PANEL_CONTENT_GAP,PANEL_CONTENT_GAP));
		
		headingLabel=new JLabel("Sync. Status",SwingConstants.CENTER);
		headingLabel.setVerticalAlignment(SwingConstants.CENTER);
		headingLabel.setBounds(new Rectangle(0, 90,mWidth, 75));
		//headingLabel.setLocation(10, 30);
		headingLabel.setSize(new Dimension(mWidth,20));
		headingLabel.setPreferredSize(new Dimension(this.getWidth(),30));
		headingLabel.setBackground(new Color(78,128,188));
		headingLabel.setForeground(Color.WHITE);
		headingLabel.setOpaque(true);
		headingLabel.setFont(PosFormUtil.getSubHeadingFont());
		this.add(headingLabel);
		
		final DefaultTableCellRenderer rightAlign=new NoBorderTableCellSelectionRender();
		final DefaultTableCellRenderer centerAlign=new NoBorderTableCellSelectionRender();
		final DefaultTableCellRenderer leftAlign=new NoBorderTableCellSelectionRender();
		final CheckedSelectionTableCellRender selectionCell=new CheckedSelectionTableCellRender();
		final NoBorderTableCellSelectionRender noBorderCell=new NoBorderTableCellSelectionRender();
		rightAlign.setHorizontalAlignment(JLabel.RIGHT);
		centerAlign.setHorizontalAlignment(JLabel.CENTER);
		leftAlign.setHorizontalAlignment(JLabel.LEFT);
		
		mSyncStatusTableModel =  PosFormUtil.getReadonlyTableModel();
		mSyncStatusTableModel.setColumnIdentifiers(new String[] {"Process ","Error","Pending "});
		
		JTable mSyncStatusTable = new JTable(mSyncStatusTableModel);
		mSyncStatusTable.setRowSorter(new TableRowSorter<TableModel>(mSyncStatusTableModel));
		mSyncStatusTable.setFont(PosFormUtil.getTableCellFont());
		mSyncStatusTable.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		mSyncStatusTable.getTableHeader().setResizingAllowed(true);
		mSyncStatusTable.getTableHeader().setReorderingAllowed(false);
		mSyncStatusTable.setRequestFocusEnabled(false);
		mSyncStatusTable.setFocusable(false);
		mSyncStatusTable.setFocusTraversalPolicyProvider(false);
		mSyncStatusTable.setRowSelectionAllowed(false);
		mSyncStatusTable.getTableHeader().setFont(PosFormUtil.getTableHeadingFont());
		mSyncStatusTable.getColumnModel().getColumn(0).setCellRenderer(leftAlign);
		mSyncStatusTable.getColumnModel().getColumn(1).setCellRenderer(centerAlign);
		mSyncStatusTable.getColumnModel().getColumn(2).setCellRenderer(centerAlign);
		mSyncStatusTable.getColumnModel().getColumn(1).setMaxWidth(100);
		mSyncStatusTable.getColumnModel().getColumn(2).setMaxWidth(100);
		
		JScrollPane  orderListTableScrollPane = new JScrollPane(mSyncStatusTable,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		orderListTableScrollPane.setPreferredSize(new Dimension(mWidth, mHeight-40));
		orderListTableScrollPane.getViewport().setBackground(Color.WHITE);
		orderListTableScrollPane.setAutoscrolls(true);
		mSyncStatusTable.setRowHeight(35);
		posServerSyncStatusProvider = new PosServerSyncStatusProvider(null);
		object=new Object[3];
		this.add(orderListTableScrollPane);
		
		java.util.Timer timer = new java.util.Timer();
		timer.scheduleAtFixedRate(new schduledTask(), 1000, 10000);
	}
	
	class schduledTask extends TimerTask
	{
		@Override
		public void run() {
			mSyncStatusTableModel.setRowCount(0);
			ArrayList<BeanDataSync> beanDataSyncs = null;
			try {
				beanDataSyncs = posServerSyncStatusProvider.getSyncRows();
			} catch (Exception e) {
				e.printStackTrace();
			}
			for (int i = 0; i < beanDataSyncs.size(); i++) {
				object[0] = beanDataSyncs.get(i).getRemarks();
				object[1] = beanDataSyncs.get(i).getErrorRecords();
				object[2] = beanDataSyncs.get(i).getPendingRecords();
				mSyncStatusTableModel.addRow(object);
			}
		}
	}

}
