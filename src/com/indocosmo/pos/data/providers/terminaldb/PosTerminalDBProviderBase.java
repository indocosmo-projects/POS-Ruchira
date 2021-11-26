package com.indocosmo.pos.data.providers.terminaldb;

import java.sql.Connection;

import com.indocosmo.pos.common.utilities.PosDBUtil;
import com.indocosmo.pos.data.providers.PosDatabaseProvider;

public abstract class PosTerminalDBProviderBase extends PosDatabaseProvider {

	protected static Connection mConnection;
	
	public PosTerminalDBProviderBase() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PosTerminalDBProviderBase(String idField, String table) {
		super(idField, table);
		// TODO Auto-generated constructor stub
	}

	public PosTerminalDBProviderBase(String table) {
		super(table);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void initProvider(){
		mConnection=PosDBUtil.getInstance().getTerminalDBConnection();

	}

	@Override
	protected Connection getConnection() {
		return mConnection;
	}

	

}
