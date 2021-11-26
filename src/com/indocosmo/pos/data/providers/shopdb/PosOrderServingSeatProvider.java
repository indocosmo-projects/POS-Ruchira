/**
 * 
 */
package com.indocosmo.pos.data.providers.shopdb;

import java.sql.SQLException;
/**
 * @author jojesh-13.2
 *
 */
public class PosOrderServingSeatProvider extends PosShopDBProviderBase {

//
//    /**
//     * Sets the table name.
//     */
//    private final String mTableName = "order_serving_seats";
//    private PosEnvSettings mPosEnvSettings;
//    private BeanOrderServedSeat mOrderedSeat;
//
//    /**
//     * Instantiates a sevice table databse.
//     *
//     * @param p_context the p_context
//     */
//    public PosOrderServingSeatProvider() {
//        super(mTableName);
//
//        // TODO Auto-generated constructor stub
//    }
//
//    /**
//     * @return the serviceTableList
//     * @throws Exception
//     */
//    public Cursor getServiceTableExt(String order_id) {
//    	
//        String where = "order_id = '" + order_id + "'";
//        Cursor res = getData(where);
//        return res;
//    }
//
//    private String getUpdateStatement() {
//
//        String update_sql = "update " + mTableName +
//                " set order_id=?, " +
//                " table_id=?, " +
//                " seat_no=?, " +
//                " created_by=?, " +
//                " created_at=?, " +
//                " updated_by=?, " +
//                " updated_at=?, " +
//                " is_void=? " +
//                " where  order_id=? and table_id=? and seat_no=?";
//        return update_sql;
//    }
//
//
//    private String getInsertStatement() {
//    	
//        String insert_sql = "insert into " + mTableName + " (" +
//                " order_id, " +
//                " table_id, " +
//                " seat_no, " +
//                " created_by, " +
//                " created_at, " +
//                " updated_by, " +
//                " updated_at, " +
//                " is_void " +
//                ") " +
//                " values (?,?,?,?,?,?,?,?)";
//        return insert_sql;
//    }
//
//    public void saveDetails(SQLiteDatabase db, BeanOrderHeader orderHeader, int tableId, LinkedHashMap<Integer, BeanOrderServedSeat> mBeanOrderServedSeat) throws SQLException {
//
//        if (mPosEnvSettings == null)
//            mPosEnvSettings = PosEnvSettings.getInstance();
//        SQLiteStatement prep;
//        SQLiteStatement mUpdatePrepare = db.compileStatement(getUpdateStatement());
//
//        SQLiteStatement mInsertPrepare = db.compileStatement(getInsertStatement());
//        if (mBeanOrderServedSeat != null) {
//
//            Iterator it = mBeanOrderServedSeat.entrySet().iterator();
//
//            while (it.hasNext()) {
//                Map.Entry pair = (Map.Entry) it.next();
//
//                mOrderedSeat = (BeanOrderServedSeat) pair.getValue();
//
//                boolean isExist = isExist("order_id='" + orderHeader.getOrderId() + "' and table_id=" + tableId + " and seat_no=" + mOrderedSeat.getSeatNo());
//                if (isExist) {
//                    /*We need to update data if exist*/
//                    prep = mUpdatePrepare;
//                } else {
//                    /*We need to insert data if not exist*/
//                    prep = mInsertPrepare;
//                }
//
//                prep.bindString(1, orderHeader.getOrderId());
//                prep.bindLong(2, tableId);
//                prep.bindLong(3, mOrderedSeat.getSeatNo());
//
//                try {
//                    prep.bindLong(4, mPosEnvSettings.getCashierShiftInfo().getCashierInfo().getId());
//
//                } catch (Exception e) {
//                    // TODO: handle exception
//                    e.printStackTrace();
//                    PosLog.write(getContext(), this, "Provider", e);
//                    Log.e("POS", "For Test...Required mPosEnvSettings.getCashierShiftInfo()");
//                    //For Test
//                    prep.bindLong(4, 1);
//                }
//                prep.bindString(5, orderHeader.getOrderDate());
//                try {
//                    prep.bindLong(6, mPosEnvSettings.getCashierShiftInfo().getCashierInfo().getId());
//                } catch (Exception e) {
//                    // TODO: handle exception
//                    e.printStackTrace();
//                    PosLog.write(getContext(), this, "Provider", e);
//                    Log.e("POS", "For Test...Required mPosEnvSettings.getCashierShiftInfo()");
//                    //For Test
//                    prep.bindLong(6, 1);
//                }
//                prep.bindString(7, orderHeader.getOrderDate());
//                prep.bindLong(8, getIntFromBoolean(mOrderedSeat.isVoid()));
//
//                 /*If item exist set additional where condition parameter for update query*/
//                if (isExist) {
//                    prep.bindString(9, orderHeader.getOrderId());
//                    prep.bindLong(10, tableId);
//                    prep.bindLong(11, mOrderedSeat.getSeatNo());
//                    /*Update data*/
//                    Log.e("POS", "POS seat updated = " + prep.executeUpdateDelete());
//                } else /*Insert data*/ {
//
//                    Log.e("POS", "POS seat insert = " + prep.executeInsert());
//                }
//                prep.clearBindings();
//
//            }
//        }
//
//
//    }
//
//    public LinkedHashMap<Integer, BeanOrderServedSeat> getServiceSeatList(String order_id, int table_id) {
//        String sql = "SELECT seat_no " +
//                "FROM " + mTableName +
//                " WHERE table_id = " + table_id + " and order_id = '" + order_id + "' and is_void is not 1 ";
//        Cursor res = getDataFromSql(sql);
//
//        LinkedHashMap<Integer, BeanOrderServedSeat> seatlist = new LinkedHashMap<Integer, BeanOrderServedSeat>();
//
//        try {
//            BeanOrderServedSeat mBeanOrderServedSeat = null;
//            if (res != null) {
//                while (res.moveToNext()) {
//                    mBeanOrderServedSeat = new BeanOrderServedSeat();
//                    mBeanOrderServedSeat.setSeatNo(res.getInt(getColumIndex(res, "seat_no")));
//                    mBeanOrderServedSeat.setExisting(true);//As item added
//
//                    seatlist.put(res.getInt(getColumIndex(res, "seat_no")), mBeanOrderServedSeat);
//
//                }
//                res.close();
//            }
//        } catch (Exception e) {
//            Log.e("loadItems", e.getMessage());
//
//            throw e;
//        }
//        return seatlist;
//    }
//
//
//    @Override
//    protected void initProvider() {
//
//    }

	/* (non-Javadoc)
	 * @see com.indocosmo.pos.data.providers.PosDatabaseProvider#deleteData(java.lang.String)
	 */
	@Override
	public int deleteData(String where) throws SQLException {
	
		final String sql= "delete from order_serving_seats where "+where;
		
		return executeNonQuery(sql);
		
	}
	
 
}
