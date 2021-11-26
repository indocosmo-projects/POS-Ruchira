/**
 * 
 */
package com.indocosmo.pos.data.beans;

import com.indocosmo.pos.common.enums.MessageDisplayStatus;
import com.indocosmo.pos.data.providers.shopdb.PosFlashMessageProvider.MessageType;

/**
 * @author Deepak
 *
 */
public class BeanFlashMessage {
	
	private String mFlashMessage;
	private int id;
	private String mTitle;
	private String mContent;
	private String mFromDate;
	private MessageDisplayStatus mDisplayStatus;
	private MessageType mType;
	private String mCreatedAt;
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the mTitle
	 */
	public String getTitle() {
		return mTitle;
	}

	/**
	 * @param mTitle the mTitle to set
	 */
	public void setTitle(String mTitle) {
		this.mTitle = mTitle;
	}

	/**
	 * @return the mContent
	 */
	public String getContent() {
		return mContent;
	}

	/**
	 * @param mContent the mContent to set
	 */
	public void setContent(String mContent) {
		this.mContent = mContent;
	}

	/**
	 * @return the mFlashMessage
	 */
	public String getFlashMessage() {
		return mFlashMessage;
	}

	/**
	 * @param mFlashMessage the mFlashMessage to set
	 */
	public void setFlashMessage(String mFlashMessage) {
		this.mFlashMessage = mFlashMessage;
	}

	/**
	 * @return the mFromDate
	 */
	public String getFromDate() {
		return mFromDate;
	}

	/**
	 * @param mFromDate the mFromDate to set
	 */
	public void setFromDate(String mFromDate) {
		this.mFromDate = mFromDate;
	}

	/**
	 * @return the mDisplayStatus
	 */
	public MessageDisplayStatus getDisplayStatus() {
		return mDisplayStatus;
	}

	/**
	 * @param mDisplayStatus the mDisplayStatus to set
	 */
	public void setDisplayStatus(MessageDisplayStatus mDisplayStatus) {
		this.mDisplayStatus = mDisplayStatus;
	}

	/**
	 * @return the mCreatedAt
	 */
	public String getCreatedAt() {
		return mCreatedAt;
	}

	/**
	 * @param mCreatedAt the mCreatedAt to set
	 */
	public void setCreatedAt(String mCreatedAt) {
		this.mCreatedAt = mCreatedAt;
	}

	/**
	 * @return the type
	 */
	public MessageType getType() {
		return mType;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(MessageType type) {
		this.mType = type;
	}

}
