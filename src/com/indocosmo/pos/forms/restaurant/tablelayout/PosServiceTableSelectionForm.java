/**
 * 
 */
package com.indocosmo.pos.forms.restaurant.tablelayout;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Map;

import javax.swing.JDialog;

import com.indocosmo.pos.common.PosEnvSettings;
import com.indocosmo.pos.common.PosLog;
import com.indocosmo.pos.common.beans.settings.ui.orderentry.neworder.BeanUITableSelectionSettings;
import com.indocosmo.pos.common.utilities.PosFormUtil;
import com.indocosmo.pos.common.utilities.PosOrderUtil;
import com.indocosmo.pos.data.beans.BeanEmployees;
import com.indocosmo.pos.data.beans.BeanOrderHeader;
import com.indocosmo.pos.data.beans.BeanOrderServingTable;
import com.indocosmo.pos.data.beans.BeanServingTable;
import com.indocosmo.pos.data.beans.BeanServingTableExt;
import com.indocosmo.pos.data.beans.BeanServingTableLocation;
import com.indocosmo.pos.data.providers.shopdb.PosServiceTableExtProvider;
import com.indocosmo.pos.data.providers.shopdb.PosServiceTableLocationProvider;
import com.indocosmo.pos.forms.components.buttons.PosButton;
import com.indocosmo.pos.forms.components.buttons.listners.IPosButtonListner;
import com.indocosmo.pos.forms.restaurant.tablelayout.components.PosServingTableLayoutListPanel;
import com.indocosmo.pos.forms.restaurant.tablelayout.components.PosServingTableLayoutListPanel.IPosServingTableListListener;
import com.indocosmo.pos.forms.restaurant.tablelayout.components.PosTableLayoutRightPanel;
import com.indocosmo.pos.forms.restaurant.tablelayout.components.PosTableLayoutRightPanel.IPosTableLayoutRightPanelListener;

/**
 * @author jojesh
 *
 */
public final class PosServiceTableSelectionForm extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int PANEL_V_GAP=8;
	private static final int PANEL_H_GAP=8;
	
	private static final int TABLE_LIST_PANE_HEIGHT=PosEnvSettings.getInstance().getUISetting().getOrderEntryUISettings().getNewOrderUISetting().getTableSelectionUISettings().getLayoutHeight();
	private static final int TABLE_LIST_PANE_WIDTH=PosEnvSettings.getInstance().getUISetting().getOrderEntryUISettings().getNewOrderUISetting().getTableSelectionUISettings().getLayoutWidth();
	private static final int FORM_HEIGHT=TABLE_LIST_PANE_HEIGHT+PANEL_V_GAP*2;
	private static final int RIGHT_PANE_WIDTH = 260;
	private static final int FORM_WIDTH=TABLE_LIST_PANE_WIDTH+RIGHT_PANE_WIDTH+PANEL_H_GAP*3;

	private PosServingTableLayoutListPanel tableListPanel;
	private PosTableLayoutRightPanel rightPanel;
	private boolean isCancelled;
	private boolean isTableSelectionOnly=false;
	private Map<String, BeanOrderServingTable> orderTables;
	private BeanUITableSelectionSettings tableSelecionUISetings=PosEnvSettings.getInstance().getUISetting().getOrderEntryUISettings().getNewOrderUISetting().getTableSelectionUISettings();
	private BeanOrderHeader orderHeader;
	
	/**
	 * @param isTableSelectionOnly
	 */
	public PosServiceTableSelectionForm (boolean isTableSelectionOnly){
		
		this.isTableSelectionOnly=isTableSelectionOnly;
		
		initForm();
	}

	/**
	 * 
	 */
	public PosServiceTableSelectionForm (){

		initForm();

	}

	/**
	 * 
	 */
	private void initForm() {
		
		this.setSize(FORM_WIDTH,FORM_HEIGHT);
		this.setLayout(null);

		createTableListPanel();
		createRightPanel();
		createShowSizeHideButton();

		setLocationData();
	}

	/**
	 * 
	 */
	private void createShowSizeHideButton() {

		PosButton btnShowSize=new PosButton();
		btnShowSize.setBounds(0, 0, 0, 0);
		btnShowSize.registerKeyStroke(KeyEvent.VK_U, InputEvent.ALT_MASK | InputEvent.SHIFT_MASK);
		btnShowSize.setOnClickListner(new IPosButtonListner() {

			@Override
			public void onClicked(PosButton button) {

				tableListPanel.setSizeInfoVisible(!tableListPanel.isSizeInfoVisible());
				tableListPanel.repaint();

			}
		});
		this.add(btnShowSize);
	}

	/**
	 * 
	 */
	private void createRightPanel() {

		final int left=tableListPanel.getX()+tableListPanel.getWidth()+ PANEL_H_GAP;
		final int top=PANEL_V_GAP;
		final int width=RIGHT_PANE_WIDTH;
		final int height=TABLE_LIST_PANE_HEIGHT;

		rightPanel=new PosTableLayoutRightPanel(this,width ,height,isTableSelectionOnly);
		rightPanel.setLocation(left,top);
		rightPanel.setOpaque(true);
		rightPanel.setListener(rightPanelListener);
		this.add(rightPanel);
	}
	
	/**
	 * 
	 */
	private IPosTableLayoutRightPanelListener rightPanelListener=new IPosTableLayoutRightPanelListener() {

		@Override
		public void onTableLocationChanged(BeanServingTableLocation location) {

			try {

				final PosServiceTableExtProvider pvdr=new PosServiceTableExtProvider();
				final Map<String, BeanServingTableExt> availableTableList =pvdr.getServiceTableExtMapList(location.getId());
				tableListPanel.setTableLocation(location,availableTableList);
				rightPanel.setTableList(availableTableList);

			} catch (Exception e) {

				PosLog.write(this, "setTableLocation", e);
				PosFormUtil.showErrorMessageBox(this,"Failed to fetch tables. Please check log for more details.");
			}
			
		}
		
		@Override
		public void onTableChanged(BeanServingTable table) {

			tableListPanel.setSelectedTable(table.getCode());
			
		}

		@Override
		public void onTableAdded(BeanServingTableExt table) {

			try {
				
				if(!tableSelecionUISetings.allowMultipleOrders()){
					
					if(table.getOrderCount()>0){
						
						PosFormUtil.showErrorMessageBox(PosServiceTableSelectionForm.this,"This table has order. Please choose different table.");
						return;
					}
				}
				
				tableListPanel.addTableToSelectionList(table);
				
				if(tableSelecionUISetings.isSingleTouchSelectionEnabled() && !isTableSelectionOnly){
					
					onOkButtonClicked();
				}
				
			} catch (Exception e) {

				PosLog.write(PosServiceTableSelectionForm.this, "onTableAdded", e);
				PosFormUtil.showErrorMessageBox(PosServiceTableSelectionForm.this, "Failed to add table. Please check log for more details");
			}

		}

		@Override
		public void onTableRemoved(BeanServingTableExt table) {
			
			if(orderHeader!=null ){
				
				final int itemCount=PosOrderUtil.getItemCountInTable(orderHeader,table);
				if(itemCount>0){
					
					PosFormUtil.showErrorMessageBox(PosServiceTableSelectionForm.this, "This table has items. Can not remove this table.");
					return;
				}
			}

			tableListPanel.removeTableFromSelectionList(table);

		}

		@Override
		public void onCancelButtonClicked() {
			
			isCancelled=true;
			setVisible(false);
			if(listener!=null)
				listener.onCancelButtonClicked();

		}

		@Override
		public void onOkButtonClicked() {

			onAccepted();

		}

		@Override
		public void onTableSeatCountChanged(BeanServingTableExt table) {
			
			tableListPanel.updateServingTable(table);
		}

		@Override
		public void onResetButtonClicked() {
		
			reset();
		}
		
	};

	/**
	 * 
	 */
	private void onAccepted(){
		
		if(!validateData()) return;
		isCancelled=false;
		setVisible(false);
		if(listener!=null)
			listener.onOkButtonClicked();
		
	}

	/**
	 * @return
	 */
	protected boolean validateData() {

		boolean isValid=true;
		
		if(getOrderTables()==null || getOrderTables().size()<=0){
			
			PosFormUtil.showErrorMessageBox(this, "No table(s) selected. Please select table(s).");
			isValid=false;
		}else if(getSelectedWaiter()==null){
			
			
			rightPanel.selectWaiter();
			if(getSelectedWaiter()==null) {
				PosFormUtil.showErrorMessageBox(this, "No waiter selected. Please select waiter.");
				isValid=false;
			}
			
			
		}
		return isValid;
	}

	/**
	 * 
	 */
	private void setLocationData(){

		try {

			final PosServiceTableLocationProvider pvdr=new PosServiceTableLocationProvider();
			ArrayList<BeanServingTableLocation> locations;
			locations = pvdr.getServiceTableLocationList();
			rightPanel.setLocationData(locations);

		} catch (Exception e) {

			PosLog.write(this, "setLocationData", e);
			PosFormUtil.showErrorMessageBox(this, "Failed to load table locations. Please check log for more details");
		}
	}
	
	/**
	 * 
	 */
	public void reset(){
		rightPanel.resetSelectedTableInfo();
		tableListPanel.reset();
		setOrderTables(orderTables);
		setLocationData();
		
	}

	/**
	 * 
	 */
	private void createTableListPanel() {

		final int left=PANEL_H_GAP;
		final int top=PANEL_V_GAP;
		final int width=TABLE_LIST_PANE_WIDTH;
		final int height=TABLE_LIST_PANE_HEIGHT;

		tableListPanel=new PosServingTableLayoutListPanel(this,false);
		tableListPanel.setBounds(left,top,width ,height);
		tableListPanel.setListener(new IPosServingTableListListener() {

			@Override
			public void onServingTableSelected(BeanServingTableExt table) {

				rightPanel.setSelectedTable(table);
				
				if(tableSelecionUISetings.isSingleTouchSelectionEnabled() && !isTableSelectionOnly){
					
					rightPanelListener.onTableAdded(table);
				}
			}

			@Override
			public void onServingTableDoubleClicked(BeanServingTableExt table) {
				
				rightPanel.setSelectedTable(table);
				rightPanelListener.onTableAdded(table);
			}
		});
		tableListPanel.setInfoTextColor(tableSelecionUISetings.getInfoFontColor());
		tableListPanel.setOrderCountVisible(tableSelecionUISetings.isOrderCountVisible());
		tableListPanel.setTableCodeVisible(tableSelecionUISetings.isTableCodeVisible());
		tableListPanel.setSeatCountVisible(tableSelecionUISetings.isSeatCountVisible());
		this.add(tableListPanel);

	}
	
	/**
	 * @return
	 */
	public Map<String, BeanOrderServingTable> getOrderTables(){
		
		return tableListPanel.getSelectedTables();
	}

	/**
	 * @return
	 */
	public void setOrderTables(Map<String, BeanOrderServingTable> orderTables){
		
		this.orderTables=orderTables;	
		 tableListPanel.setOrderTableList(orderTables);
	}

	/**
	 * @return
	 */
	public boolean isCancelled() {

		return this.isCancelled;
	}

	/**
	 * 
	 */
	private IPosServiceTableSelectionFormListener listener;
	

	/**
	 * @author jojesh
	 *
	 */
	public interface IPosServiceTableSelectionFormListener{

		/**
		 * 
		 */
		public void onOkButtonClicked();

		/**
		 * 
		 */
		public void onCancelButtonClicked();
		
		/**
		 * 
		 */
		public void onResetClicked();
	}

	/**
	 * @param listener the listener to set
	 */
	public void setListener(IPosServiceTableSelectionFormListener listener) {
		
		this.listener = listener;
	}

	/**
	 * @return
	 */
	public String getSelectedServiceTableCode() {
		
		return tableListPanel.getSelectedTableCode();
	}

	/**
	 * @return
	 */
	public int getSelectedCovers() {
		
		return rightPanel.getCovers();
	}

	/**
	 * @return
	 */
	public BeanEmployees getSelectedWaiter() {

		return rightPanel.getSelectedWaiter();
	}

	/**
	 * @return
	 */
	public int getPax() {
		
		return rightPanel.getSelectedPax();
	}
	
	/**
	 * @param mSelectedServedBy
	 */
	public void setSelectedWaiter(BeanEmployees employee) {
		
		if(employee!=null)
			setSelectedWaiter(employee.getCardNumber());
	}

	/**
	 * @param cardNumber
	 */
	public void setSelectedWaiter(String cardNumber) {
		
		rightPanel.setSelectedWaiter(cardNumber);
		
	}

	/**
	 * @param code
	 */
	public void setSelectedTable(String code) {

		tableListPanel.setSelectedTable(code);
	}

	/**
	 * @param i
	 */
	public void setSelectedTableLocation(int id) {
		
		rightPanel.setSelectedTableLocation(id);
		
	}

	/**
	 * @param mPosOrderEntryItem
	 */
	public void setOrderHeader(BeanOrderHeader orderHeader) {
		
		this.orderHeader=orderHeader;
		
	}



}
