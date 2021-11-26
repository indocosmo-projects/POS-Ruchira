/**
 * 
 */
package com.indocosmo.pos.data.providers.shopdb;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.sql.rowset.CachedRowSet;

import com.indocosmo.pos.common.PosLog;
import com.indocosmo.pos.data.beans.BeanChoice;


/**
 * @author joe.12.3
 *
 */
public class PosChoiceItemProvider extends PosShopDBProviderBase {
	
	/**
	 * choice id code mapping for faster access.
	 * <ChoiceId,ChoiceCode>
	 * */
	private HashMap<Integer, String> choicesIdCodeMapping;
	/**
	 * All the choices are added to choiceList hashmap when it is created.
	 * <ChoiceCode,ChoiceObject>
	 * */
	private HashMap<String, BeanChoice> allChoiceList;
	/**
	 * All the choices that are global
	 * */
	private ArrayList<BeanChoice> globalChoiceList;
	
	private PosSaleItemProvider saleItemProvider;
	
	private static PosChoiceItemProvider singleInstance;

	/**
	 * 
	 */
	private PosChoiceItemProvider() {
		super("v_choices");
		allChoiceList=new HashMap<String, BeanChoice>();
		choicesIdCodeMapping=new HashMap<Integer,String>();
		saleItemProvider=new PosSaleItemProvider();
	}

	public static PosChoiceItemProvider getInstance(){

		if(singleInstance==null)
			singleInstance=new PosChoiceItemProvider();

		return singleInstance;
	}

	public BeanChoice getChoice(int choiceID) throws Exception{
		BeanChoice item=null;

		if(choicesIdCodeMapping.containsKey(choiceID))
			item=allChoiceList.get(choicesIdCodeMapping.get(choiceID));
		else
		{
			final String cond="id="+choiceID;
			item=createItem(cond);
		}

		return item;		
	}
	
	public BeanChoice getChoice(String code) throws Exception{
		BeanChoice item=null;

		if(allChoiceList.containsKey(code))
			item=allChoiceList.get(choicesIdCodeMapping.get(code));
		else
		{
			final String cond="code'="+code+"'";
			item=createItem(cond);
		}

		return item;		
	}
	
	public ArrayList<BeanChoice>  getGlobalChoices() throws Exception{
		if(globalChoiceList==null){
			String whereCond="is_global=1";
			CachedRowSet res=getData(whereCond);
			if(res!=null){
				globalChoiceList=new ArrayList<BeanChoice> ();
				try{
					while(res.next()){
						BeanChoice item=createItemFromCrs(res);
						globalChoiceList.add(item);
					}
					res.close();
				}catch (Exception e) {
					PosLog.write(this,"getGlobalChoices",e.getMessage());
					res.close();
					throw e;
				}
			}
		}
			
	return globalChoiceList;
	}

	/**
	 * @param cond
	 * @return
	 * @throws Exception 
	 */
	private BeanChoice createItem(String cond) throws Exception {

		BeanChoice item=null;
		CachedRowSet res=getData(cond);
		if(res!=null){
			try {
				if(res.next()) {
					item = createItemFromCrs(res);
				}
				res.close();
			} catch (SQLException e) {
				PosLog.write(this,"createItem",e.getMessage());
				res.close();
				throw e;
			}
		}
		return item;
	}
	
	private void addToCache(BeanChoice item){
		choicesIdCodeMapping.put(item.getId(), item.getCode());
		allChoiceList.put(item.getCode(), item);
	}

	/**
	 * @param res
	 * @return
	 * @throws Exception 
	 */
	public BeanChoice createItemFromCrs(CachedRowSet res) throws Exception {
		BeanChoice item=new BeanChoice();
		item.setCode(res.getString("code"));
		item.setDescription(res.getString("description"));
		item.setId(res.getInt("id"));
		item.setName(res.getString("name"));
		item.setExtra(res.getBoolean("is_global"));
		item.setSaleItems(saleItemProvider.getSaleItemsByChoiceId(item.getId()));
		addToCache(item);
		return item;
	}

	
	


}
