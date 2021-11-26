/**
 * 
 */
package com.indocosmo.pos.forms.components;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import com.indocosmo.pos.common.PosEnvSettings;
import com.indocosmo.pos.common.utilities.PosCurrencyUtil;
import com.indocosmo.pos.common.utilities.PosFormUtil;
import com.indocosmo.pos.common.utilities.PosNumberUtil;
import com.indocosmo.pos.common.utilities.PosOrderUtil;
import com.indocosmo.pos.common.utilities.PosSaleItemUtil;
import com.indocosmo.pos.common.utilities.PosUomUtil;
import com.indocosmo.pos.data.beans.BeanOrderDetail;
import com.indocosmo.pos.data.beans.BeanSaleItem;
import com.indocosmo.pos.data.providers.shopdb.PosSaleItemProvider;
import com.indocosmo.pos.data.providers.shopdb.PosTaxItemProvider.TaxCalculationMethod;
import com.indocosmo.pos.forms.components.jtable.NoBorderTableCellSelectionRender;

/**
 * @author jojesh-13.2
 *
 */
public class PosOrderInfoDetTablePanel extends JPanel {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int ROW_HEIGHT = 40;
	private static final int SCROLL_WIDTH = 25;
	private static final int FIELD_COUNT=6;
	
	/*
	 * Column header positions
	 */
	//	private static final int LIST_TABLE_COL_ITEM_INDEX=0;
	private static final int LIST_TABLE_COL_ITEM_NAME_INDEX=0;
	private static final int LIST_TABLE_COL_ITEM_PRICE_INDEX=1;
	private static final int LIST_TABLE_COL_ITEM_QTY_INDEX=2;
	private static final int LIST_TABLE_COL_ITEM_DISC_INDEX=3;
	private static final int LIST_TABLE_COL_ITEM_TAX_INDEX=4;
	private static final int LIST_TABLE_COL_PRICE_INDEX=5;
	
	/*
	 * Column width
	 */
	//private static final int  LIST_TABLE_COL_ITEM_NAME_WIDTH=350;
	private static final int LIST_TABLE_COL_ITEM_PRICE_WIDTH=100;
	private static final int LIST_TABLE_COL_ITEM_QTY_WIDTH=70;
	private static final int LIST_TABLE_COL_ITEM_DISC_WIDTH=75;
	private static final int LIST_TABLE_COL_ITEM_TAX_WIDTH=75;
	private static final int LIST_TABLE_COL_PRICE_WIDTH=105;

	private static final int LIST_TABLE_HEADER_HEIGHT=30;

	private DefaultTableModel mOrderDetailTableModel;
	private JTable 	mOrderDetailTable; 
	private JScrollPane  orderListTableScrollPane; 
	private ArrayList<BeanOrderDetail > orderDtlList;
	
	/**
	 * 
	 */
	public PosOrderInfoDetTablePanel(JPanel parent, int width, int height  ) {
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
		mOrderDetailTable =createOrderListTablePanel(this);
		this.setFont(PosFormUtil.getLabelFont());
		
	}
	
	/**
	 * @returns the table column width OF Item Name
	 */
	private int getItemColumWidth(){

		return getWidth()
				-LIST_TABLE_COL_ITEM_PRICE_WIDTH
				-LIST_TABLE_COL_ITEM_QTY_WIDTH 
				-LIST_TABLE_COL_ITEM_DISC_WIDTH
				-LIST_TABLE_COL_ITEM_TAX_WIDTH
				- LIST_TABLE_COL_PRICE_WIDTH 
				 -SCROLL_WIDTH
				-2;

	}
	
	/**
	 * Creates the table
	 * @param tableListPanel
	 * @return
	 */
	private JTable createOrderListTablePanel(JPanel tableListPanel) {

		final DefaultTableCellRenderer rightAlign=new NoBorderTableCellSelectionRender();
		final DefaultTableCellRenderer centerAlign=new NoBorderTableCellSelectionRender();
		//final CheckedSelectionTableCellRender selectionCell=new CheckedSelectionTableCellRender();
		final NoBorderTableCellSelectionRender noBorderCell=new NoBorderTableCellSelectionRender();
		rightAlign.setHorizontalAlignment(JLabel.RIGHT);
		centerAlign.setHorizontalAlignment(JLabel.CENTER);

		mOrderDetailTableModel   =  PosFormUtil.getReadonlyTableModel(); ;
		mOrderDetailTableModel.setColumnIdentifiers(getColumnNames());

		final JTable orderDetTable = new JTable(mOrderDetailTableModel);
		orderDetTable.setRowSorter(new TableRowSorter<TableModel>(mOrderDetailTableModel));
//		orderDetTable.setFont(getFont().deriveFont(18f));
		orderDetTable.setFont(PosFormUtil.getTableCellFont());
		orderDetTable.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		orderDetTable.getTableHeader().setPreferredSize(new Dimension(tableListPanel.getWidth(), LIST_TABLE_HEADER_HEIGHT));
		 
		orderDetTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		orderDetTable.getTableHeader().setReorderingAllowed(false);
		orderDetTable.getTableHeader().setResizingAllowed(false);
		orderDetTable.getTableHeader().setFont(PosFormUtil.getTableHeadingFont());

		setColumnWidth(orderDetTable);
		
		orderDetTable.getColumnModel().getColumn(LIST_TABLE_COL_ITEM_NAME_INDEX).setCellRenderer(noBorderCell);
		orderDetTable.getColumnModel().getColumn(LIST_TABLE_COL_ITEM_PRICE_INDEX).setCellRenderer(rightAlign);
		orderDetTable.getColumnModel().getColumn(LIST_TABLE_COL_ITEM_QTY_INDEX).setCellRenderer(rightAlign);
		orderDetTable.getColumnModel().getColumn(LIST_TABLE_COL_ITEM_DISC_INDEX).setCellRenderer(rightAlign);
		orderDetTable.getColumnModel().getColumn(LIST_TABLE_COL_ITEM_TAX_INDEX).setCellRenderer(rightAlign);
		orderDetTable.getColumnModel().getColumn(LIST_TABLE_COL_PRICE_INDEX).setCellRenderer(rightAlign);
		 
			 
		orderListTableScrollPane = new JScrollPane(orderDetTable,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		orderListTableScrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(SCROLL_WIDTH,0));
		orderListTableScrollPane.setSize(new Dimension(tableListPanel.getWidth(),tableListPanel.getHeight()));
		orderListTableScrollPane.setLocation(0,0);
		orderListTableScrollPane.getViewport().setBackground(Color.WHITE);
		
		final int rowHeight=ROW_HEIGHT;
		orderDetTable.setRowHeight(rowHeight);

		tableListPanel.add(orderListTableScrollPane);
		
		return orderDetTable;
	}
	
	/**
	 * @param itemListTable
	 */
	private void setColumnWidth(JTable orderDetTable){

		orderDetTable.getColumnModel().getColumn(LIST_TABLE_COL_ITEM_NAME_INDEX ).setPreferredWidth(getItemColumWidth()  );
		orderDetTable.getColumnModel().getColumn(LIST_TABLE_COL_ITEM_PRICE_INDEX).setPreferredWidth(LIST_TABLE_COL_ITEM_PRICE_WIDTH );
		orderDetTable.getColumnModel().getColumn(LIST_TABLE_COL_ITEM_QTY_INDEX).setPreferredWidth(LIST_TABLE_COL_ITEM_QTY_WIDTH );
		orderDetTable.getColumnModel().getColumn(LIST_TABLE_COL_ITEM_DISC_INDEX).setPreferredWidth(LIST_TABLE_COL_ITEM_DISC_WIDTH );
		orderDetTable.getColumnModel().getColumn(LIST_TABLE_COL_ITEM_TAX_INDEX).setPreferredWidth(LIST_TABLE_COL_ITEM_TAX_WIDTH );
		orderDetTable.getColumnModel().getColumn(LIST_TABLE_COL_PRICE_INDEX).setPreferredWidth(LIST_TABLE_COL_PRICE_WIDTH);
 
			
	}
	
   	/**
	 * @returns the column name in its order
	 */
	private String[] getColumnNames(){

		String[] colNames=new String[FIELD_COUNT];
		
		colNames[LIST_TABLE_COL_ITEM_NAME_INDEX  ]="Item Name";
		colNames[LIST_TABLE_COL_ITEM_PRICE_INDEX]="Price";
		colNames[LIST_TABLE_COL_ITEM_QTY_INDEX]="Quantity";
		colNames[LIST_TABLE_COL_ITEM_DISC_INDEX]="Discount";
		colNames[LIST_TABLE_COL_ITEM_TAX_INDEX]="Tax";
		colNames[LIST_TABLE_COL_PRICE_INDEX]="Total Amount";
		
		return colNames;
	}


	/**
	 * @param splitItems
	 */
	public void SetOrderDetailList( ArrayList<BeanOrderDetail > orderDtls){
		
		clearRows(); //orderDtlList
//		
		orderDtlList=new ArrayList<BeanOrderDetail>();
		
		for(BeanOrderDetail dtlItem:orderDtls){
			
			if(!dtlItem.isVoid())
				orderDtlList.add(dtlItem);
			
			/**Check ordered item has sub items**/
			if(dtlItem.hasSubItems()){

				/**If has combo contents print them**/
				if(dtlItem.isComboContentsSelected()){

					for(ArrayList<BeanOrderDetail> subItemList:dtlItem.getComboSubstitutes().values()){

						for(BeanOrderDetail item:subItemList){

							if(!item.isVoid())

								orderDtlList.add(item);
						}
					}
				}

				/**If has extras print them**/
				if(dtlItem.isExtraItemsSelected()){

					for(ArrayList<BeanOrderDetail> subItemList:dtlItem.getExtraItemList().values()){

						for(BeanOrderDetail item:subItemList){

							if(!item.isVoid())

								orderDtlList.add(item);
						}
					}
				}
			}
		 		
		}
		SetOrderDetailList();
//		mOrderListTable.invalidate();

	}
	
	/**
	 * 
	 */
	private void SetOrderDetailList(){
		
		DefaultTableModel model=(DefaultTableModel)mOrderDetailTable.getModel();
	
		for (BeanOrderDetail item: orderDtlList){
			
			if(!item.isVoid()){
				model.addRow(getRowValue(item));
				int rowHeight=ROW_HEIGHT;

				mOrderDetailTable.setRowHeight(mOrderDetailTable.getRowCount()-1,rowHeight );
			}
		}
		mOrderDetailTable.invalidate();
		
	}

	/**
	 * @param  
	 * @return
	 */
	private  String[] getRowValue(BeanOrderDetail  orderDtl){
		
		return getRowValue(orderDtl, false);

	}
	 
	

	/**
	 * @param  
	 * @return
	 */
	private  String[] getRowValue(BeanOrderDetail  orderDtl,boolean isRefdunded){
		
		final double totalTaxAmt=PosSaleItemUtil.getTotalTaxAmount(orderDtl.getSaleItem(),true);
		
		final String styleStartText=isRefdunded?"<html><body><strike style='color:red'>":"";
		final String styleEndText=isRefdunded?"</strike></body></html>":"";
	
//		final String styleStartText=orderDtl.isRefunded()?"<html><body><strike style='color:red'><span style='color:black'>":"";
//		final String styleEndText=orderDtl.isRefunded()?"</span></strike></body></html>":"";
		
//		final String styleStartText=orderDtl.isRefunded()?"<html><body><span style='color: red; text-decoration: line-through'><span style='color: black'>":"";
//		final String styleEndText=orderDtl.isRefunded()?"</span></span></body></html>":"";
		String[] values=new String[FIELD_COUNT];
		values[LIST_TABLE_COL_ITEM_NAME_INDEX]= styleStartText + getDescriptiveItemName(orderDtl) + styleEndText;
		values[LIST_TABLE_COL_ITEM_PRICE_INDEX] =PosCurrencyUtil.format(PosSaleItemUtil.getItemFixedPrice(orderDtl.getSaleItem()));
		values[LIST_TABLE_COL_ITEM_QTY_INDEX] =PosUomUtil.format(orderDtl.getSaleItem().getQuantity(),orderDtl.getSaleItem().getUom());
		values[LIST_TABLE_COL_ITEM_DISC_INDEX] =PosCurrencyUtil.format(PosSaleItemUtil.getTotalDiscountAmount(orderDtl.getSaleItem()));
		values[LIST_TABLE_COL_ITEM_TAX_INDEX] = "<html><body><font size=4>"+ 
		(orderDtl.getSaleItem().getTaxCalculationMethod() ==TaxCalculationMethod.ExclusiveOfTax && totalTaxAmt!=0? " + ":"") + "</font>" + 
		PosCurrencyUtil.format(totalTaxAmt) +"</body></html>";
		values[LIST_TABLE_COL_PRICE_INDEX ]=PosCurrencyUtil.format(PosOrderUtil.getGrandTotal(orderDtl));
		return values;

	}
	
	/**
	 * @return item name and sub items  
	 */
	private String getDescriptiveItemName( BeanOrderDetail orderDetailItem){

		String itemName="<html><body>"+orderDetailItem.getSaleItem().getName();
		String modifire="";
		 		final BeanSaleItem beanSaleItem=orderDetailItem.getSaleItem();

		if(beanSaleItem.hasSelectedAttributes()){
			
			for(int i=0;i<5;i++){

				if(beanSaleItem.getAttribSelectedOption(i)!=null && 
						beanSaleItem.getAttribSelectedOption(i).trim().length()!=0 && 
						!beanSaleItem.getAttribSelectedOption(i).trim().equalsIgnoreCase(PosSaleItemProvider.DEFAULT_ATTRIBUTE_OPTION)){
					modifire+="[ "+beanSaleItem.getAttributeName(i)+" : "+beanSaleItem.getAttribSelectedOption(i)+" ], ";
				}
			}
			
			if(!modifire.isEmpty())	
				modifire=modifire.substring(0,modifire.length()-2);
		}

		if(!modifire.isEmpty() && PosEnvSettings.getInstance().getUISetting().getOrderEntryUISettings().getSplitUISettings().isItemMoidifiersVisible())	
			itemName+="<br><font size=\"4\" color=\"red\">Modifiers " +modifire+"</font></br>";

		return itemName+"</body></html>";
	}

	/**
	 * 
	 */
	private void clearRows() {
		
		final DefaultTableModel model=(DefaultTableModel) mOrderDetailTable.getModel();
	 	final int rowCount=model.getRowCount();
		for(int rowIndex=0; rowIndex<rowCount; rowIndex++){
			
			model.removeRow(0);
		}
	}
	
	/**
	 * 
	 */
	public void refresh(){
		
		clearRows();
		SetOrderDetailList();
	}

	public void resizePanel(Dimension s){
		
		this.setPreferredSize(s);
		orderListTableScrollPane.setSize(s);
		orderListTableScrollPane.setPreferredSize(s);
		orderListTableScrollPane.invalidate();
		this.invalidate();
	}

	

}
