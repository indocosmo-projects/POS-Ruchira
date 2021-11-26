package com.indocosmo.pos.data.beans;

import java.util.ArrayList;

public final class BeanSaleItemCombo extends BeanSaleItem {

	public BeanSaleItemCombo() {
	}

	protected ArrayList<BeanSaleItem> mComboContentItemList;

	public ArrayList<BeanSaleItem> getComboContentItemList(){
		return mComboContentItemList; 
	}

	public void setComboContentItemList(ArrayList<BeanSaleItem> itemList){
		mComboContentItemList=itemList; 
	}

	public boolean hasContentItems(){
		return (mComboContentItemList!=null && mComboContentItemList.size()>0);
	}

	public void replaceContentItem(BeanSaleItem oldContent, BeanSaleItem newContent){
		final int index=removeContentItem(oldContent);
		if(index>=0)
			mComboContentItemList.add(index, newContent);
		else
			mComboContentItemList.add(newContent);
	}
	
	public void addToContentList(BeanSaleItem content){
		mComboContentItemList.add(content);
	}

	public int removeContentItem(BeanSaleItem content){
		int itemIndex=-1;
		for(int index=0; index<mComboContentItemList.size();index++) {
			if(mComboContentItemList.get(index).getCode().equals(content.getCode())) {
				itemIndex=index;
				mComboContentItemList.remove(itemIndex);
				break;
			}
		}
		return itemIndex;
	}
   
	@Override
	public BeanSaleItem clone() {
		BeanSaleItemCombo cloneObject=(BeanSaleItemCombo) super.clone();
		if(cloneObject!=null){
			/** clone the content item list as it will be edited **/
			if(mComboContentItemList!=null){
				ArrayList<BeanSaleItem> comboContentItemList=new ArrayList<BeanSaleItem> ();
				for(BeanSaleItem item:mComboContentItemList)
					comboContentItemList.add(item);
				cloneObject.setComboContentItemList(comboContentItemList);
			}
		}
		return cloneObject;
	}

}
