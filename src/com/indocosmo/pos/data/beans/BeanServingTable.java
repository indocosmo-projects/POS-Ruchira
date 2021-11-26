/**
 * 
 */
package com.indocosmo.pos.data.beans;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * @author jojesh
 *
 */
public class BeanServingTable extends BeanMasterBase {

	private int seatNo;
	private boolean is_system;
	private int seatCount;
	private BeanServingTableLocation location;
	private int layoutWidth;
	private int layoutHeight;
	private BufferedImage image;
	private int column;
	private int row;
//	private int servingTableLocationId;
	private ArrayList<BeanServingSeat> seats;

		
	/**
	 * @return the layoutWidth
	 */
	public int getLayoutWidth() {
		return layoutWidth;
	}

	/**
	 * @param layoutWidth the layoutWidth to set
	 */
	public void setLayoutWidth(int layoutWidth) {
		this.layoutWidth = layoutWidth;
	}

	/**
	 * @return the layoutHeight
	 */
	public int getLayoutHeight() {
		return layoutHeight;
	}

	/**
	 * @param layoutHeight the layoutHeight to set
	 */
	public void setLayoutHeight(int layoutHeight) {
		this.layoutHeight = layoutHeight;
	}

	/**
	 * 
	 */
	public BeanServingTable() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the covers
	 */
	public int getSeatCount() {
		return seatCount;
	}

	/**
	 * @param seats the covers to set
	 */
	public void setSeatCount(int seats) {
		this.seatCount = seats;
	}

	/**
	 * @return the is_system
	 */
	public boolean isSystem() {
		return is_system;
	}

	/**
	 * @param isystem the is_system to set
	 */
	public void isSystem(boolean is_system) {
		this.is_system = is_system;
	}

	/**
	 * @return
	 */
	public int getSelectedSeatNo() {
		 
		return seatNo;
	}
	
	public void setSelectedSeatNo(int seatNo){
		this.seatNo=seatNo;
	}

	/**
	 * @return the column
	 */
	public int getColumn() {
		return column;
	}

	/**
	 * @param column the column to set
	 */
	public void setColumn(int column) {
		this.column = column;
	}

	/**
	 * @return the row
	 */
	public int getRow() {
		return row;
	}

	/**
	 * @param row the row to set
	 */
	public void setRow(int row) {
		this.row = row;
	}

	/**
	 * @return the servingTableLocationId
	 */
//	public int getServingTableLocationId() {
//		return servingTableLocationId;
//	}

	/**
	 * @param servingTableLocationId the servingTableLocationId to set
	 */
//	public void setServingTableLocationId(int servingTableLocationId) {
//		this.servingTableLocationId = servingTableLocationId;
//	}

	/**
	 * @return the seats
	 */
	public ArrayList<BeanServingSeat> getSeats() {
		return seats;
	}

	/**
	 * @param seats the seats to set
	 */
	public void setSeats(ArrayList<BeanServingSeat> seats) {
		this.seats = seats;
	}

	@Override
    public BeanServingTable clone() throws CloneNotSupportedException {
    	
    	ArrayList<BeanServingSeat> servingSeatsCloned= null;
    	
    	if(seats!=null){
    		
    		servingSeatsCloned=new ArrayList<BeanServingSeat>();
    		
    		for(BeanServingSeat seat:seats){
    			
    			servingSeatsCloned.add(seat.clone());
    		}
    		
    	}
    	
    	BeanServingTable cloneObject = null;
        cloneObject = (BeanServingTable) super.clone();
        cloneObject.setSeats(servingSeatsCloned);
        
        return cloneObject;
    }

	/**
	 * @return the image
	 */
	public BufferedImage getImage() {
		return image;
	}

	/**
	 * @param image the image to set
	 */
	public void setImage(BufferedImage image) {
		this.image = image;
	}

/**
	 * @return the location
	 */
	public BeanServingTableLocation getLocation() {
		return location;
	}

	/**
	 * @param location the location to set
	 */
	public void setLocation(BeanServingTableLocation location) {
		this.location = location;
	}
	
	public static String[] SEARCH_FIELD_TITLE_LIST={"Code","Name","Seats"};
	public static String[] SEARCH_FIELD_LIST={"getCode","getName","getSeatCount"};
	public static int[] SEARCH_FIELD_WIDTH_LIST={100,350};
	
	/* (non-Javadoc)
	 * @see com.indocosmo.pos.components.search.IPosSearchableItem#getFiledList()
	 */
	@Override
	public String[] getFieldList() {
		return SEARCH_FIELD_LIST;
	}
	/* (non-Javadoc)
	 * @see com.indocosmo.pos.components.search.IPosSearchableItem#getFiledTitleList()
	 */
	@Override
	public String[] getFieldTitleList() {

		return SEARCH_FIELD_TITLE_LIST;
	}
	/* (non-Javadoc)
	 * @see com.indocosmo.pos.components.search.IPosSearchableItem#getFiledWidthList()
	 */
	@Override
	public int[] getFieldWidthList() {
		return SEARCH_FIELD_WIDTH_LIST;
	}
}
