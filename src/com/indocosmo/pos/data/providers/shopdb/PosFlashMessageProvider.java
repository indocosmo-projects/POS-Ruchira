/**
 * 
 */
package com.indocosmo.pos.data.providers.shopdb;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import javax.sql.rowset.CachedRowSet;

import com.indocosmo.pos.common.PosEnvSettings;
import com.indocosmo.pos.common.PosLog;
import com.indocosmo.pos.common.enums.CardTypes;
import com.indocosmo.pos.common.enums.MessageDisplayStatus;
import com.indocosmo.pos.common.utilities.PosDateUtil;
import com.indocosmo.pos.data.beans.BeanAppVersion;
import com.indocosmo.pos.data.beans.BeanFlashMessage;

/**
 * @author jojesh
 *
 */
public class PosFlashMessageProvider extends PosShopDBProviderBase{
	
//private ArrayList<PosFlashMessageObject> mFlashMessageList;
	/**
	 * 
	 */
	public PosFlashMessageProvider() {
		super("v_flash_messages");
	}
	
	public String getLatestFlashMessage(){
		
		String flashMessage =null;
		
		final String sql="SELECT * FROM flash_messages_hdr "+ 
						" WHERE display_status IN (" + MessageDisplayStatus.POS.getValue() + "," + 
													MessageDisplayStatus.BOTH.getValue() +  ") OR "+ 
								"(display_status=" + MessageDisplayStatus.READ.getValue() + " and " +  
										" from_date<='" + PosEnvSettings.getInstance().getPosDate() + "' AND " +
								        " to_date>='" + PosEnvSettings.getInstance().getPosDate() + "')";
		CachedRowSet crs =executeQuery(sql);
		if(crs!=null){
			flashMessage = buildFlashMessage(crs);
		}
		return flashMessage;
		
	}

	public ArrayList<BeanFlashMessage> getFlashMessageList() {
		
//		loadFlashMessages();
		
		return loadFlashMessages();
	}
	/**
	 * @return 
	 * 
	 */
	private ArrayList<BeanFlashMessage> loadFlashMessages() {
		ArrayList<BeanFlashMessage> mFlashMessageList=null;
		CachedRowSet crs = getData();
		if(crs!=null){
			mFlashMessageList = new ArrayList<BeanFlashMessage>();
			try {
				while(crs.next()){
					mFlashMessageList.add(createFlashMessageObject(crs));
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return mFlashMessageList;
	}

	/**
	 * @param crs 
	 * @return
	 */
	private BeanFlashMessage createFlashMessageObject(CachedRowSet crs) {
		
		BeanFlashMessage flashMessage = new BeanFlashMessage();
		try {
			flashMessage.setId(crs.getInt("id"));
			flashMessage.setTitle(crs.getString("title"));
			flashMessage.setContent(crs.getString("content"));
			flashMessage.setFromDate(crs.getString("from_date"));
			flashMessage.setType(MessageType.get(crs.getInt("type")));
			flashMessage.setDisplayStatus(MessageDisplayStatus.get(crs.getInt("display_status")));
			flashMessage.setCreatedAt(crs.getString("created_at"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
		return flashMessage;
	}

	/**
	 * @param crs
	 */
	private String buildFlashMessage(CachedRowSet crs) {
		StringBuilder flashMessages = new StringBuilder();
		
		try {
			while(crs.next()){
				flashMessages.append(crs.getString("content"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return flashMessages.toString();
	}
	
	public int getMessageCount(){
		return loadFlashMessages().size();
	}

	
	/**
	 * @param crs
	 */
	public void updateDisplayStatus(int messageId) {
		
		final String sql="UPDATE flash_messages_hdr SET display_status=3 WHERE id=" + String.valueOf(messageId);
		
		try {
			executeNonQuery(sql);
 		} catch (SQLException e) {
			PosLog.write(this,"updateDisplayStatus", e);
		}
 	}
	/*
	 * 
	 */
	public ArrayList<BeanFlashMessage>   getNotificationMessage(){
		
		 BeanFlashMessage flashMsg=null;
		 ArrayList<BeanFlashMessage> flashMsgList=null;
			
		final String sql="SELECT id, title,CASE type 	WHEN 1 THEN CONCAT(\"<b>\",content,\"</b>\")  WHEN 2 THEN CONCAT(\"<font color='red'><b>\",content,\"</font>\")END AS content,from_date, type,display_status,created_at from flash_messages_hdr WHERE " + 
		        "(type="+ MessageType.Sync.getValue() + " OR type="+ MessageType.License.getValue() + ") "+ 
				" AND display_status=" + MessageDisplayStatus.POS.getValue() + " AND is_deleted=0 AND " +
				 " cast( from_date  AS date) <= cast(now() AS date) AND " + 
				" cast(to_date AS date) >= cast(now() AS date)" ;
		
		
		CachedRowSet crs = executeQuery(sql);
		if(crs!=null){
			try {
				
				while(crs.next()){
					
					flashMsg=createFlashMessageObject(crs);
					
					if(flashMsgList==null)
						flashMsgList=new ArrayList<BeanFlashMessage>();
					flashMsgList.add(flashMsg);
					
				}
				
			} catch (SQLException e) {

				e.printStackTrace();
			}
		}
		return flashMsgList;
	}
	/*
	 * 
	 */
	public void addSyncNotificationMessage() throws Exception{
		
		final int id=1;
		final String title="Sync Updates";
		final String content="There are some critical updates available on server. Please run synchronization to get the updates.";
		
		BeanFlashMessage flashMessage = new BeanFlashMessage();
	 
		flashMessage.setId(id);
		flashMessage.setTitle(title);
		flashMessage.setContent(content);
		flashMessage.setType(MessageType.Sync);
		
		addFlashMessage(flashMessage);
	}	
	/*
	 * 
	 */
	public void addLicenseNotificationMessage(Date licenseTillDt) throws Exception{
		
		final int id=2;
		final String title="Licese Validity";
		final String content="Your subscription will expire on " + PosDateUtil.format(PosDateUtil.DATE_FORMAT_DDMMYY, licenseTillDt)  +
				             ". Please contact System Administrator...";
		
//		Please contact administrator. .
		BeanFlashMessage flashMessage = new BeanFlashMessage();
	 
		flashMessage.setId(id);
		flashMessage.setTitle(title);
		flashMessage.setContent(content);
		flashMessage.setType(MessageType.License);
		
		addFlashMessage(flashMessage);
	}	

	private void addFlashMessage(BeanFlashMessage flashMessage) throws SQLException{
		
		PreparedStatement ps=null;
    	final String where="type="+ flashMessage.getType().getValue() +  " AND is_deleted=0";
    	    
    	final boolean isExistMsg=isExist("flash_messages_hdr" , where);
    	if(isExistMsg)
    		ps=getUpdPreparedStatment();
		else
			ps=getInsPreparedStatment();
    	
    	ps.setString(1, PosDateUtil.getDate());
    	ps.setString(2, PosDateUtil.getNextDate(PosDateUtil.getDate(), 1));
    	ps.setInt(3, MessageDisplayStatus.POS.getValue());
    	ps.setString(4, PosDateUtil.getDateTime());
    	ps.setString(5, flashMessage.getTitle());
    	ps.setString(6, flashMessage.getContent());
    	ps.setInt(7, flashMessage.getType().getValue());
    	
    	if(!isExistMsg){
    		
	    	ps.setInt(8,flashMessage.getId());
	    	ps.setInt(9, 0);
	    	ps.setInt(10, 0);
    	} 	
    	ps.execute();
    	
	}
	
	/**
	 * @return 
	 * @throws SQLException
	 */
	private PreparedStatement getInsPreparedStatment() throws SQLException{

		final String insert_sql="INSERT INTO flash_messages_hdr (" +
							"`from_date`, `to_date`,`display_status`, `created_at`,`title`,`content`,`type`,`id`, " + 
							"`is_deleted`, `is_synchable`) " + 
							" VALUES (?,?,?,?,?,?,?,?,?,?)";
		return getConnection().prepareStatement(insert_sql);
	}

	/**
	 * @throws SQLException
	 */
	private PreparedStatement getUpdPreparedStatment() throws SQLException {

	 
		final String update_sql = "update flash_messages_hdr set " +
				"   from_date=?, to_date=?,  display_status=?,created_at=? ,title=?,content=? " + 
				" WHERE  `is_deleted`=0 AND type=?";
		return getConnection().prepareStatement(update_sql);

	}
	/*
	 * 
	 */
	public void updateSyncNotificationMessageStatus() throws Exception{
		 	 
    	final String update_sql = "update flash_messages_hdr set  display_status="  + 
    			MessageDisplayStatus.READ.getValue() + 
				" WHERE  `is_deleted`=0 AND type=" + MessageType.Sync.getValue() ;
		  executeNonQuery(update_sql);
     
	}	
	

public enum MessageType{
	Custom(0),
	Sync(1),
	License(2);
	
	private static final Map<Integer,MessageType> mLookup 
	= new HashMap<Integer,MessageType>();

	static {
		for(MessageType rc : EnumSet.allOf(MessageType.class))
			mLookup.put(rc.getValue(), rc);
	}

	private int mValue;
	
	private MessageType(int value) {
		this.mValue = value;
	}

	public int getValue() { return mValue; }
	
	public static MessageType get(int value) { 
		return mLookup.get(value); 
	}
}
}


