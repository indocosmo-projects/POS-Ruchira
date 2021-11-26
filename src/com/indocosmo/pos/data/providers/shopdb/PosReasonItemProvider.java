package com.indocosmo.pos.data.providers.shopdb;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.rowset.CachedRowSet;

import com.indocosmo.pos.common.PosLog;
import com.indocosmo.pos.data.beans.BeanReason;
import com.indocosmo.pos.data.beans.BeanReason.ReasonContext;

public class PosReasonItemProvider extends PosShopDBProviderBase {

private ArrayList<BeanReason> mPosReasonItemList;
	
	public PosReasonItemProvider(){
		mTablename="reasons";	
		mPosReasonItemList=new ArrayList<BeanReason>();
	}	
	
	private void loadReasonItems(ReasonContext context){
		CachedRowSet res=null;
		if(context==null)
			res=getData();
		else
			res=getData("context="+String.valueOf(context.getValue()));
		mPosReasonItemList.clear();
		try {
			while (res.next()) {
				mPosReasonItemList.add(createReasonItem(res)); 
			}
			res.close();
		} catch (SQLException e) {
			PosLog.write(this,"loadReasonItems",e);
		}
	}
	
	private BeanReason createReasonItem(CachedRowSet res) throws SQLException{
		BeanReason item=new BeanReason();
		item.setId(res.getInt("id"));
		item.setCode(res.getString("code"));
		item.setName(res.getString("name"));
		item.setContext(ReasonContext.get(res.getInt("context")));
		item.setDescription(res.getString("description"));
		return item;
	}
	
	public ArrayList<BeanReason> getReasonItemList(){
		loadReasonItems(null);
		return mPosReasonItemList;
	}
	
	public ArrayList<BeanReason> getReasonItemList(ReasonContext context){
		loadReasonItems(context);
		return mPosReasonItemList;
	}

}
