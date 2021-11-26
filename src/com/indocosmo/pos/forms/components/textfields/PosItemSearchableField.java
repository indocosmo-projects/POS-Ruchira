/**
 * 
 */
package com.indocosmo.pos.forms.components.textfields;

import java.util.ArrayList;

import javax.swing.RootPaneContainer;
import javax.swing.SortOrder;

import com.indocosmo.pos.common.utilities.PosFormUtil;
import com.indocosmo.pos.forms.components.search.IPosSearchableItem;
import com.indocosmo.pos.forms.search.PosExtSearchForm;
import com.indocosmo.pos.forms.search.listener.adapter.PosItemExtSearchFormAdapter;

/**
 * @author jojesh
 *
 */
@SuppressWarnings("serial")
public class PosItemSearchableField extends PosTouchableFieldBase {

	private PosExtSearchForm mSearchForm; 

	private int mValueColumIndex=0;
	private IPosSearchableItem mSelectedItem; 
	private ArrayList<? extends IPosSearchableItem> mSearchFiledList;

	private int sortColumn=0;
	private SortOrder sortOrder=SortOrder.DESCENDING;
	private boolean showHiddenItems=false;

	public PosItemSearchableField(RootPaneContainer parent, int width) {
		
		super(parent,width);
		
	}
	
	public PosItemSearchableField(RootPaneContainer parent, int width,ArrayList<? extends IPosSearchableItem> searchList) {
		
		super(parent,width);
		mSearchFiledList=searchList;
	}
	
	/**
	 * @param searchList
	 */
	public void setSearchFieldList(ArrayList<? extends IPosSearchableItem> searchList){
		
		mSearchFiledList=searchList;
	}
	
	@Override
	protected void initComponent() {
		super.initComponent();
		setTextReadOnly(true);
	}

	@Override
	protected void showBroswerForm() {
		showItemSearchForm();
	}
	
	public void setValueColumnIndex(int index){
		mValueColumIndex=index;
	}
		
	/**
	 * 
	 */
	private void showItemSearchForm(){

		if(mSearchFiledList==null)return;
			mSearchForm=new PosExtSearchForm(mSearchFiledList);
			mSearchForm.setSorting(sortColumn, sortOrder);
			mSearchForm.setListner(new PosItemExtSearchFormAdapter() {
				
				@Override
				public boolean onAccepted(Object sender, IPosSearchableItem item) {
					mTextFiled.setText(item.getDisplayText());
					onValueSelected(item);
					return true;
				}
			});
		mSearchForm.setWindowTitle(mTitle);
		mSearchForm.setShowHiddenItems(showHiddenItems);
		PosFormUtil.showLightBoxModal(mParent,mSearchForm);
	}
	
	
	@Override
	protected void onResetButtonClicked() {
		mSelectedItem=null;
		super.onResetButtonClicked();
	}
	
	public IPosSearchableItem getSelectedItem() {
		return mSelectedItem;
	}
	
	public void setSelectedItem(IPosSearchableItem item) {
		this.mSelectedItem=item;
		if(mSelectedItem!=null)
			setText(mSelectedItem.getDisplayText());
		else
			setText("");
	}
	
	/* (non-Javadoc)
	 * @see com.indocosmo.pos.components.textfields.PosTouchableFieldBase#setEditable(boolean)
	 */
	@Override
	public void setEditable(boolean editable) {
		super.setEditable(editable);
		mTextFiled.setEditable(false);
	}

	/**
	 * @return the sortColumn
	 */
	public int getSortColumn() {
		return sortColumn;
	}

	/**
	 * @param sortColumn the sortColumn to set
	 */
	public void setSortColumn(int sortColumn) {
		this.sortColumn = sortColumn;
	}
	
	
	/**
	 * @return the sortOrder
	 */
	public SortOrder getSortOrder() {
		return sortOrder;
	}

	/**
	 * @param sortOrder the sortOrder to set
	 */
	public void setSortOrder(SortOrder sortOrder) {
		this.sortOrder = sortOrder;
	}
	
	
	public void setSorting(int column, SortOrder order){
		
		setSortColumn(column);
		setSortOrder(order);
	}

	/**
	 * @return the showHiddenItems
	 */
	public boolean isShowHiddenItems() {
		return showHiddenItems;
	}

	/**
	 * @param showHiddenItems the showHiddenItems to set
	 */
	public void setShowHiddenItems(boolean showHiddenItems) {
		this.showHiddenItems = showHiddenItems;
	}
	
	/**
	 * @return the mSearchItemList
	 */
//	public ArrayList<? extends Object>  getSearchItemList() {
//		return mSearchItemList;
//	}

	/**
	 * @param mSearchItemList the mSearchItemList to set
	 */
//	private ArrayList<? extends Object>  mSearchItemList;
//	public void setSearchItemList(ArrayList<? extends Object>  searchItemList) {
//		
//		this.mSearchItemList = searchItemList;
//	}
	
//	/**
//	 * @param mSearchItemList the mSearchItemList to set
//	 */
//	public void setSearchItemList(IPosSearchableItem[] searchItemList) {
//		this.mSearchItemList = searchItemList;
//	}
}
