package com.indocosmo.pos.data.providers.shopdb;

import java.util.ArrayList;

import com.indocosmo.pos.data.beans.BeanSalesMode;

public final class PosSaleModeItemProvider {

	private static ArrayList<BeanSalesMode> mPosSalesModeItemList=null;
	
	public PosSaleModeItemProvider(){
		loadSalesMode();
	}
	
	private void loadSalesMode(){
		if(mPosSalesModeItemList!=null) return;
		mPosSalesModeItemList=new ArrayList<BeanSalesMode>();
		mPosSalesModeItemList.add(new BeanSalesMode(1,"0001","Normal"));
		mPosSalesModeItemList.add(new BeanSalesMode(2,"0002","Happy Hours"));
		mPosSalesModeItemList.add(new BeanSalesMode(3,"0003","Evening Sales"));
		mPosSalesModeItemList.add(new BeanSalesMode(4,"0004","Promotion Sales"));
		mPosSalesModeItemList.add(new BeanSalesMode(5,"0005","Onam Sales"));
		mPosSalesModeItemList.add(new BeanSalesMode(6,"0006","X-mas Sales"));
		mPosSalesModeItemList.add(new BeanSalesMode(7,"0007","New Year Sales"));
		mPosSalesModeItemList.add(new BeanSalesMode(8,"0008","Deepavali Sales"));
		mPosSalesModeItemList.add(new BeanSalesMode(9,"0009","Easter Sales"));
		mPosSalesModeItemList.add(new BeanSalesMode(10,"0010","Vishu Sales"));
		mPosSalesModeItemList.add(new BeanSalesMode(11,"0011","Pooja Sales"));
	}
	
	public ArrayList<BeanSalesMode> getSaleModeItemList(){
		loadSalesMode();
		return mPosSalesModeItemList;
	}
	
	public BeanSalesMode getDefaultSalesMode(){
		return mPosSalesModeItemList.get(0);
	}
		
}

