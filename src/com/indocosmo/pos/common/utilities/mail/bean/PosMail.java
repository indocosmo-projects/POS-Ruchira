/**
 * 
 */
package com.indocosmo.pos.common.utilities.mail.bean;

import java.util.ArrayList;

/**
 * @author jojesh-13.2
 *
 */
public class PosMail {

	String subject;
	String body;
	ArrayList<String> attachement;
	
	public PosMail(){
		attachement=new ArrayList<String>();
	}
	/**
	 * @return the subject
	 */
	public String getSubject() {
		return subject;
	}
	/**
	 * @param subject the subject to set
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}
	/**
	 * @return the body
	 */
	public String getBody() {
		return body;
	}
	/**
	 * @param body the body to set
	 */
	public void setBody(String body) {
		this.body = body;
	}
	/**
	 * @return the attachement
	 */
	public ArrayList<String> getAttachement() {
		return attachement;
	}
	/**
	 * @param attachement the attachement to set
	 */
	public void addAttachement(String attachement) {
		this.attachement.add(attachement);
	}
	
	public void setAttachement(ArrayList<String> attachement) {
		this.attachement = attachement;
	}
	
	
	
	public boolean hasAttachment(){
		return (attachement!=null && attachement.size()>0);
	}
	
}
