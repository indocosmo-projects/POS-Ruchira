/**
 * 
 */
package com.indocosmo.pos.forms.restaurant.tablelayout.components;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LayoutManager;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JPanel;
import javax.swing.RootPaneContainer;

import com.indocosmo.pos.common.PosLog;
import com.indocosmo.pos.common.utilities.PosFormUtil;
import com.indocosmo.pos.data.beans.BeanOrderServingTable;
import com.indocosmo.pos.data.beans.BeanServingTableExt;
import com.indocosmo.pos.data.beans.BeanServingTableLocation;
import com.indocosmo.pos.data.providers.shopdb.PosServiceTableExtProvider;
import com.indocosmo.pos.forms.restaurant.tablelayout.components.PosServingTableControl.IPosServingTableControlListener;

/**
 * @author jojesh
 *
 */
public class PosServingTableLayoutListPanel extends JPanel implements IPosServingTableControlListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final Color BG_COLOR=Color.LIGHT_GRAY;

	private static final int PANEL_CONTENT_H_GAP=1;
	private static final int PANEL_CONTENT_V_GAP=2;
	private static final Color INFO_TEXT_DEF_COLOR=Color.BLACK;

	private RootPaneContainer mParent;

	private Map<String, BeanServingTableExt> availableTableList;
	private Map<String, BeanOrderServingTable> orderTableList;
	private Map<String, BeanOrderServingTable> selectedTables;

	private Map<String, PosServingTableControl> tableControlList;
	private PosServingTableControl selecetedTableControl;

	private boolean isSizeInfoVisible=false;
	private BeanServingTableLocation tableLocation;
	
	private boolean orderCountVisible=true;
	private boolean tableCodeVisible=true;
	private boolean seatCountVisible=false;
	private Color infoTextColor;

	public PosServingTableLayoutListPanel(RootPaneContainer parent,boolean isAutoLayout){

		this.mParent=parent;
		setOpaque(true);
		setBackground(BG_COLOR);
		setLayout(getLayoutManager(isAutoLayout));
		tableControlList=new HashMap<String, PosServingTableControl>();
	}

	/**
	 * @param isAutoLayout
	 * @return
	 */
	private LayoutManager getLayoutManager(boolean isAutoLayout){

		return (isAutoLayout)?new FlowLayout(FlowLayout.LEFT):null;
	}
	
	/**
	 * 
	 */
	private void setSelectedTableList(){
		
		this.selectedTables=new HashMap<String, BeanOrderServingTable>();

		if(orderTableList!=null){

			try {

				for(BeanOrderServingTable table:orderTableList.values())
					selectedTables.put(table.getCode(),(BeanOrderServingTable)table.clone());

			} catch (CloneNotSupportedException e) {

				PosLog.write(this, "reset", e);
				PosFormUtil.showErrorMessageBox(this.mParent, "Failed to set the table list. Please check log for deails.");
			}
		}

	}

	/**
	 * 
	 */
	private void setControls(){

		this.removeAll();
		
		if(availableTableList!=null){

			for(BeanServingTableExt table:availableTableList.values()){

				final PosServingTableControl control=new PosServingTableControl(mParent);
				control.setTable(table);
				control.setListener(this);
				control.setOrderCountVisible(orderCountVisible);
				control.setTableCodeVisible(tableCodeVisible);
				control.setSeatCountVisible(seatCountVisible);
				control.setTableInfoTextColor((tableLocation.isAutoLayout())? INFO_TEXT_DEF_COLOR:infoTextColor);
				tableControlList.put(table.getCode(), control);

				if(selectedTables!=null && selectedTables.containsKey(table.getCode())){
					
					if(!selectedTables.get(table.getCode()).isVoid()){
						
						control.setMarkedAsAdded(true);
						table.setSeatCount(selectedTables.get(table.getCode()).getSeatCount());
					}
				}
				
				this.add(control);
			}
		}
		
		this.repaint();
	}
	
	

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#paint(java.awt.Graphics)
	 */
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		
		final Graphics2D g2 = (Graphics2D) g;
		
		if(isSizeInfoVisible)
			drawSize(g2);
		
		drawBg(g2);
	}

	/**
	 * @param g
	 */
	private void drawBg(Graphics2D g2) {
		
		if(tableLocation!=null && tableLocation.getImage()!=null)

		g2.drawImage(tableLocation.getImage(),0,0,this.getWidth(),this.getHeight(),0,0,tableLocation.getImage().getWidth(),tableLocation.getImage().getHeight(),null);
	}

	/**
	 * 
	 */
	private void drawSize(Graphics g) {

		final int x=10;
		final int y=getHeight()-20;
		final String info="SIZE : "+this.getWidth()+" x "+this.getHeight();

		g.drawChars(info.toCharArray(), 0, info.length(), x, y);

	}

	/**
	 * @return the availableTableList
	 */
	public Map<String, BeanServingTableExt> getAvailableTableList() {

		return availableTableList;
	}

	/**
	 * @param availableTableList the availableTableList to set
	 */
	private void setAvailableTableList(
			Map<String, BeanServingTableExt> availableTableList) {

		this.availableTableList = availableTableList;
		setControls();
	}

	/**
	 * @return the orderTableList
	 */
	public Map<String, BeanOrderServingTable> getOrderTableList() {

		return orderTableList;
	}

	/**
	 * @param orderTableList the orderTableList to set
	 */
	public void setOrderTableList(Map<String, BeanOrderServingTable> orderTableList) {

		this.orderTableList = orderTableList;
		setSelectedTableList();
		setControls();
	}
	
	/**
	 * @return
	 */
	public boolean isSizeInfoVisible() {

		return isSizeInfoVisible;
	}

	/**
	 * @param b
	 */
	public void setSizeInfoVisible(boolean visible) {

		isSizeInfoVisible=visible;
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.pos.forms.restaurant.tablelayout.components.PosServingTableControl.IPosServingTableControlListener#onSelected(com.indocosmo.pos.forms.restaurant.tablelayout.components.PosServingTableControl)
	 */
	@Override
	public void onServingTableSelected(PosServingTableControl source) {

		if(selecetedTableControl==source) return;

		if(selecetedTableControl!=null){

			selecetedTableControl.setSelected(false);
		}

		selecetedTableControl=source;

		if(listener!=null)
			listener.onServingTableSelected(selecetedTableControl.getTable());

	}

	/* (non-Javadoc)
	 * @see com.indocosmo.pos.forms.restaurant.tablelayout.components.PosServingTableControl.IPosServingTableControlListener#onServingTableDoubleClicked(com.indocosmo.pos.forms.restaurant.tablelayout.components.PosServingTableControl)
	 */
	@Override
	public void onServingTableDoubleClicked(PosServingTableControl source) {

		if(selecetedTableControl!=null){

			selecetedTableControl.setSelected(false);
		}

		selecetedTableControl=source;

		if(listener!=null)
			listener.onServingTableDoubleClicked(selecetedTableControl.getTable());
		
	}

	/**
	 * @param location
	 */
//	public void setTableLocation(BeanServingTableLocation location) {
//
//		this.tableLocation=location;
//		setLayout(getLayoutManager(this.tableLocation.isAutoLayout()));
//
//		try {
//
//			final PosServiceTableExtProvider pvdr=new PosServiceTableExtProvider();
//			setAvailableTableList(pvdr.getServiceTableExtMapList(this.tableLocation.getId()));
//
//		} catch (Exception e) {
//
//			PosLog.write(this, "setTableLocation", e);
//			PosFormUtil.showErrorMessageBox(mParent,"Failed to fetch tables. Please check log for more details.");
//		}
//	}
	
	/**
	 * @param location
	 * @param availableTableList
	 */
	public void setTableLocation(BeanServingTableLocation location,Map<String, BeanServingTableExt> availableTableList ) {

		this.tableLocation=location;
		setLayout(getLayoutManager(this.tableLocation.isAutoLayout()));

			setAvailableTableList(availableTableList);

	}


	public Map<String, BeanOrderServingTable> getSelectedTables(){

		return selectedTables;

	}

	/**
	 * 
	 */
	private IPosServingTableListListener listener;

	


	/**
	 * @author jojesh
	 *
	 */
	public interface IPosServingTableListListener{

		public void onServingTableSelected(BeanServingTableExt table);
		
		public void onServingTableDoubleClicked(BeanServingTableExt table);
	}

	/**
	 * @param listener the listener to set
	 */
	public void setListener(IPosServingTableListListener listener) {
		this.listener = listener;
	}

	/**
	 * @param table
	 * @throws Exception 
	 */
	public void addTableToSelectionList(BeanServingTableExt table) throws Exception {

		if(selectedTables==null)
			selectedTables=new HashMap<String, BeanOrderServingTable>();

		if(!selectedTables.containsKey(table.getCode()))
			selectedTables.put(table.getCode(), new BeanOrderServingTable(table));

		else if(selectedTables.get(table.getCode()).isVoid())
			selectedTables.get(table.getCode()).setVoid(false);

		tableControlList.get(table.getCode()).setMarkedAsAdded(true);
	}

	/**
	 * @param table
	 */
	public void removeTableFromSelectionList(BeanServingTableExt table) {

		if(selectedTables!=null && selectedTables.containsKey(table.getCode())){

			selectedTables.remove(table.getCode());
			tableControlList.get(table.getCode()).setMarkedAsAdded(false);
		}

	}
	
	/**
	 * @return
	 */
	public String getSelectedTableCode(){

		return (selecetedTableControl!=null)?selecetedTableControl.getTable().getCode():null;

	}

	/**
	 * @param table
	 */
	public void updateServingTable(BeanServingTableExt table) {
		
		if(selectedTables!=null && selectedTables.containsKey(table.getCode())){

			selectedTables.get(table.getCode()).setSeatCount(table.getSeatCount());
		}
		
	}

	/**
	 * @param code
	 */
	public void setSelectedTable(String code) {
		
		if(tableControlList!=null && tableControlList.size()>0){
			
			if(tableControlList.containsKey(code))
					tableControlList.get(code).setSelected(true);
		}
		
	}

	/**
	 * @param orderCountVisible
	 */
	public void setOrderCountVisible(boolean orderCountVisible) {

		this.orderCountVisible=orderCountVisible;
	}

	/**
	 * @return the tableCodeVisible
	 */
	public boolean isTableCodeVisible() {
		return tableCodeVisible;
	}

	/**
	 * @param tableCodeVisible the tableCodeVisible to set
	 */
	public void setTableCodeVisible(boolean tableCodeVisible) {
		this.tableCodeVisible = tableCodeVisible;
	}

	/**
	 * @return the seatCountVisible
	 */
	public boolean isSeatCountVisible() {
		return seatCountVisible;
	}

	/**
	 * @param seatCountVisible the seatCountVisible to set
	 */
	public void setSeatCountVisible(boolean seatCountVisible) {
		this.seatCountVisible = seatCountVisible;
	}

	/**
	 * @param infoTextColor the infoTextColor to set
	 */
	public void setInfoTextColor(String infoTextColor) {
		this.infoTextColor = Color.decode(infoTextColor);
	}

	/**
	 * 
	 */
	public void reset() {
		
		selecetedTableControl=null;
	}

}
