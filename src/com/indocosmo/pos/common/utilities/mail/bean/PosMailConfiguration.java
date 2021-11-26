/**
 * 
 */
package com.indocosmo.pos.common.utilities.mail.bean;

import java.util.Properties;

/**
 * @author jojesh-13.2
 *
 */
public class PosMailConfiguration {


	Properties porperty;

	String clientId;
	String fromAddress;
	String replyToAddress;
	String password;
	String toAddress;
	String ccAddress;
	String bccAdress;
	String user;
	
	
	/**
	 * @return the clientId
	 */
	public String getClientId() {
		return clientId;
	}
	/**
	 * @param clientId the clientId to set
	 */
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	/**
	 * @return the porperty
	 */
	public Properties getPorperty() {
		return porperty;
	}
	/**
	 * @param porperty the porperty to set
	 */
	public void setPorperty(Properties porperty) {
		this.porperty = porperty;
	}
	/**
	 * @return the fromAddress
	 */
	public String getFromAddress() {
		return fromAddress;
	}
	/**
	 * @param fromAddress the fromAddress to set
	 */
	public void setFromAddress(String fromAddress) {
		this.fromAddress = fromAddress;
	}
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * @return the bccAdress
	 */
	public String getBccAdress() {
		return bccAdress;
	}
	/**
	 * @param bccAdress the bccAdress to set
	 */
	public void setBccAdress(String bccAdress) {
		this.bccAdress = bccAdress;
	}
	/**
	 * @return the ccAddress
	 */
	public String getCcAddress() {
		return ccAddress;
	}
	/**
	 * @param ccAddress the ccAddress to set
	 */
	public void setCcAddress(String ccAddress) {
		this.ccAddress = ccAddress;
	}
	/**
	 * @return the toAddress
	 */
	public String getToAddress() {
		return toAddress;
	}
	/**
	 * @param toAddress the toAddress to set
	 */
	public void setToAddress(String toAddress) {
		this.toAddress = toAddress;
	}
	/**
	 * @return the user
	 */
	public String getUser() {
		return user;
	}
	/**
	 * @param user the user to set
	 */
	public void setUser(String user) {
		this.user = user;
	}
	/**
	 * @return the replyToAddress
	 */
	public String getReplyToAddress() {
		return replyToAddress;
	}
	/**
	 * @param replyToAddress the replyToAddress to set
	 */
	public void setReplyToAddress(String replyToAddress) {
		this.replyToAddress = replyToAddress;
	} 
	
	
	
}
