/**
 * 
 */
package com.indocosmo.pos.data.beans;

import java.awt.image.BufferedImage;


/**
 * @author jojesh
 *
 */
public class BeanServingTableLocation extends BeanMasterBase{
	
	private boolean autoLayout;
	private BufferedImage image;
	private boolean applyServiceCharges;
	private int basedOn;
	private double amount;
	private boolean percentage;
	private boolean applyServiceTax;
	private BeanTax serviceTax;
	private String queueNoPrefix;
	
	

	/**
	 * @return the applyServiceCharges
	 */
	public boolean isApplyServiceCharges() {
		return applyServiceCharges;
	}

	/**
	 * @param applyServiceCharges the applyServiceCharges to set
	 */
	public void setApplyServiceCharges(boolean applyServiceCharges) {
		this.applyServiceCharges = applyServiceCharges;
	}

	/**
	 * @return the basedOn
	 */
	public int getBasedOn() {
		return basedOn;
	}

	/**
	 * @param basedOn the basedOn to set
	 */
	public void setBasedOn(int basedOn) {
		this.basedOn = basedOn;
	}

	/**
	 * @return the amount
	 */
	public double getAmount() {
		return amount;
	}

	/**
	 * @param amount the amount to set
	 */
	public void setAmount(double amount) {
		this.amount = amount;
	}

	/**
	 * @return the percentage
	 */
	public boolean isPercentage() {
		return percentage;
	}

	/**
	 * @param percentage the percentage to set
	 */
	public void setPercentage(boolean percentage) {
		this.percentage = percentage;
	}
	/**

	/**
	 * @return the autoLayout
	 */
	public boolean isAutoLayout() {
		return autoLayout;
	}

	/**
	 * @param autoLayout the autoLayout to set
	 */
	public void setAutoLayout(boolean autoLayout) {
		this.autoLayout = autoLayout;
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
	 * @return the applyServiceTax
	 */
	public boolean isApplyServiceTax() {
		return applyServiceTax;
	}

	/**
	 * @param applyServiceTax the applyServiceTax to set
	 */
	public void setApplyServiceTax(boolean applyServiceTax) {
		this.applyServiceTax = applyServiceTax;
	}

	/**
	 * @return the serviceTax
	 */
	public BeanTax getServiceTax() {
		return serviceTax;
	}

	/**
	 * @param serviceTax the serviceTax to set
	 */
	public void setServiceTax(BeanTax serviceTax) {
		this.serviceTax = serviceTax;
	}

	/**
	 * @return the queueNoPrefix
	 */
	public String getQueueNoPrefix() {
		return queueNoPrefix;
	}

	/**
	 * @param queueNoPrefix the queueNoPrefix to set
	 */
	public void setQueueNoPrefix(String queueNoPrefix) {
		this.queueNoPrefix = queueNoPrefix;
	}
	
}
