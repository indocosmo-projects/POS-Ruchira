package com.indocosmo.pos.data.beans;

import java.sql.PreparedStatement;

public final class BeanOrderDetailPS {
	private PreparedStatement mSaleItemPS;
	private PreparedStatement mComboItemPS;
	/**
	 * @return the mSaleItemPS
	 */
	public PreparedStatement getSaleItemPS() {
		return mSaleItemPS;
	}
	/**
	 * @param mSaleItemPS the mSaleItemPS to set
	 */
	public void setSaleItemPS(PreparedStatement ps) {
		this.mSaleItemPS = ps;
	}
	/**
	 * @return the mComboItemPS
	 */
	public PreparedStatement getComboItemPS() {
		return mComboItemPS;
	}
	/**
	 * @param mComboItemPS the mComboItemPS to set
	 */
	public void setComboItemPS(PreparedStatement ps) {
		this.mComboItemPS = ps;
	}
}
