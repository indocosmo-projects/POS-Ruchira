package com.indocosmo.pos.data.providers.shopdb;

import java.sql.Connection;
import java.sql.SQLException;

import com.indocosmo.pos.common.utilities.PosDBUtil;
import com.indocosmo.pos.data.providers.PosDatabaseProvider;

public abstract class PosShopDBProviderBase extends PosDatabaseProvider {

	protected static Connection mConnection;
	
	public PosShopDBProviderBase() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PosShopDBProviderBase(String idField, String table) {
		super(idField, table);
		// TODO Auto-generated constructor stub
	}

	public PosShopDBProviderBase(String table) {
		super(table);
		// TODO Auto-generated constructor stub
	}
 
	@Override
	protected void initProvider(){
		mConnection=PosDBUtil.getInstance().getShopDBConnection();

	}
	
	@Override
	protected Connection getConnection() {
		return mConnection;
	}

	 
	/**
	 * @param DateTo
	 * @return
	 * @throws SQLException
	 */
	public int purgeData(String dateTo) throws SQLException{ 
		return 0;
	}

}
