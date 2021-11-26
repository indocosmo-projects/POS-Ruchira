/**
 * 
 */
package com.indocosmo.pos.forms.components.textfields;

import java.util.ArrayList;

import javax.swing.RootPaneContainer;

import com.indocosmo.pos.common.utilities.PosFormUtil;
import com.indocosmo.pos.forms.PosObjectBrowserForm;
import com.indocosmo.pos.forms.PosObjectBrowserForm.ItemSize;
import com.indocosmo.pos.forms.components.objectbrowser.IPosBrowsableItem;
import com.indocosmo.pos.forms.listners.IPosObjectBrowserListner;

/**
 * @author jojesh
 *
 */
@SuppressWarnings("serial")
public class PosItemBrowsableField extends PosTouchableFieldBase {

	private PosObjectBrowserForm mBrowseForm;
	private IPosBrowsableItem[]  mBrowseItemList;
	private IPosBrowsableItem mSelectedItem; 
	private int mBrowserWindowRows=3;
	private int mBroweWindowColumns=1;

	public PosItemBrowsableField(RootPaneContainer parent, int width, int height) {
		super(parent, width, height);
		// TODO Auto-generated constructor stub
	}

	public PosItemBrowsableField(RootPaneContainer parent, int width) {
		super(parent, width);
		// TODO Auto-generated constructor stub
	}

	public PosItemBrowsableField(RootPaneContainer parent) {
		super(parent);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void initComponent() {
		super.initComponent();
		setTextReadOnly(true);
	}
	
	@Override
	protected void showBroswerForm() {
		showItemBrowseForm();
	}
		
	/**
	 * 
	 */
	private void showItemBrowseForm(){
		if(mBrowseItemList==null)return;
		if(mBrowseForm==null){
			mBrowseForm=new PosObjectBrowserForm(mTitle, mBrowseItemList,ItemSize.Wider,mBroweWindowColumns,mBrowserWindowRows);
			mBrowseForm.setListner(new IPosObjectBrowserListner() {
				@Override
				public void onItemSelected(IPosBrowsableItem item) {
					mSelectedItem=item;
					mTextFiled.setText(item.getDisplayText());
					onValueSelected(mSelectedItem);
				}

				@Override
				public void onCancel() {
					// TODO Auto-generated method stub

				}
			});		
		}
		//	mSearchForm.setTitle(mTitle);
		PosFormUtil.showLightBoxModal(mParent,mBrowseForm);
	}
	
//	@Override
//	protected void onResetButtonClicked() {
//		mSelectedItem=null;
//		super.onResetButtonClicked();
//	}
	
	/* (non-Javadoc)
	 * @see com.indocosmo.pos.forms.components.textfields.PosTouchableFieldBase#reset()
	 */
	@Override
	public void reset() {
		mSelectedItem=null;
		super.reset();
	}
	public IPosBrowsableItem getSelectedValue() {
		return mSelectedItem;
	}
	
	public void setSelectedItem(IPosBrowsableItem value) {
		this.mSelectedItem=value;
		if(mSelectedItem!=null){
			setText(mSelectedItem.getDisplayText());
			onValueSelected(mSelectedItem);
		}
		else
			setText("");
	}
	
	public void setBrowseWindowSize(int cols,int rows){
		mBrowserWindowRows=rows;
		mBroweWindowColumns=cols;
	}
	
	public void setBrowseItemList(IPosBrowsableItem[] browseItemList) {
		this.mBrowseItemList = browseItemList;
	}
	
	/**
	 * @param mSearchItemList the mSearchItemList to set
	 */
	public void setBrowseItemList(ArrayList<? extends IPosBrowsableItem> browseItemList) {
		IPosBrowsableItem[] itemList=new IPosBrowsableItem[browseItemList.size()];
		browseItemList.toArray(itemList);
		this.mBrowseItemList = itemList;
	}
	
	public IPosBrowsableItem[] getBrowseItemList(){
		return this.mBrowseItemList ;
	}
	
	/* (non-Javadoc)
	 * @see com.indocosmo.pos.components.textfields.PosTouchableFieldBase#setEditable(boolean)
	 */
	@Override
	public void setEditable(boolean editable) {
		super.setEditable(editable);
		mTextFiled.setEditable(false);
	}
	
	
	
}
