/**
 * 
 */
package com.indocosmo.pos.forms.restaurant.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.RootPaneContainer;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;

import com.indocosmo.pos.common.utilities.PosFormUtil;
import com.indocosmo.pos.data.beans.BeanEmployees;
import com.indocosmo.pos.data.providers.shopdb.PosWaiterProvider;
import com.indocosmo.pos.forms.PosOrderEntryForm;
import com.indocosmo.pos.forms.components.textfields.PosItemBrowsableField;
import com.indocosmo.pos.forms.components.textfields.PosTouchableFieldBase;
import com.indocosmo.pos.forms.components.textfields.listners.PosTouchableFieldAdapter;

/**
 * @author jojesh
 * 
 */
public class PosWaiterSelectionPanel extends JPanel {

	protected static final int PANEL_CONTENT_H_GAP = PosOrderEntryForm.PANEL_CONTENT_H_GAP;
	protected static final int PANEL_CONTENT_V_GAP = PosOrderEntryForm.PANEL_CONTENT_V_GAP;
	/**
	 * 
	 */
	private int mWidth;
	private int mHeight;

	private RootPaneContainer mParent;
	private JLabel mLabelName;
	private BeanEmployees mSelectedWaiter;
	private PosItemBrowsableField mTxtWaiterCard;
	private PosWaiterProvider waiterProvider ;

	private ArrayList<BeanEmployees> mWaiterList;

	public PosWaiterSelectionPanel(RootPaneContainer parent, int width,
			int height) {
		this.mHeight = height;
		this.mWidth = width;
		this.mParent = parent;
		setLayout(new FlowLayout(FlowLayout.LEFT, PANEL_CONTENT_H_GAP,
				PANEL_CONTENT_V_GAP));
		setBorder(new EtchedBorder(EtchedBorder.LOWERED));
		setPreferredSize(new Dimension(mWidth, mHeight));
//		setSize(new Dimension(mWidth, mHeight));
		waiterProvider = new PosWaiterProvider();
		try {
			mWaiterList = waiterProvider.getWaiterList();
		} catch (SQLException e) {
			PosFormUtil.showSystemError(mParent);
			e.printStackTrace();
		}
		createWaiterSelection();
		if(mWaiterList.size()==1){
			BeanEmployees waiter = mWaiterList.get(0);
			mTxtWaiterCard.setSelectedItem(waiter);
			mSelectedWaiter = (BeanEmployees) waiter;
			if (mSelectedWaiter != null) {
				setWaiterName(mSelectedWaiter);
				mTxtWaiterCard.setText(mSelectedWaiter.getFirstName());
			} else {
				mTxtWaiterCard.reset();
			}
		}
	}

	public BeanEmployees getSelectedWaiter() {
		return mSelectedWaiter;
	}
	
	
	/***
	 * creats the waiter selection panal
	 */
	private void createWaiterSelection() {

		final int captionFiledWidth = 80;
		final int captionFiledHeight = PosTouchableFieldBase.LAYOUT_DEF_HEIGHT;

		JLabel lablCaption = new JLabel("Waiter :");
		lablCaption.setPreferredSize(new Dimension(captionFiledWidth,
				captionFiledHeight));
		setLabelFont(lablCaption);
		add(lablCaption);

		final int numberTextWidth = 350;
		mTxtWaiterCard = new PosItemBrowsableField(mParent, numberTextWidth);
		mTxtWaiterCard.setTitle("Waiters");
		mTxtWaiterCard.setBrowseItemList(mWaiterList);
		mTxtWaiterCard.setHorizontalTextAlignment(JTextField.LEFT);
		mTxtWaiterCard.setBrowseWindowSize(3, 3);
		mTxtWaiterCard.setListner(new PosTouchableFieldAdapter() {
			/*
			 * (non-Javadoc)
			 * 
			 * @see com.indocosmo.pos.components.textfields.listners.
			 * PosTouchableFieldAdapter#onReset()
			 */
			@Override
			public void onReset() {
				super.onReset();
				mLabelName.setText("");
				mSelectedWaiter = null;
			}

			/*
			 * (non-Javadoc)
			 * 
			 * @see com.indocosmo.pos.components.textfields.listners.
			 * PosTouchableFieldAdapter#onValueSelected(java.lang.Object)
			 */
			@Override
			public void onValueSelected(Object value) {
				super.onValueSelected(value);
				mSelectedWaiter = (BeanEmployees) value;
				if (mSelectedWaiter != null) {
					setWaiterName(mSelectedWaiter);
				} else {
					mTxtWaiterCard.reset();
				}

			}
		});
		add(mTxtWaiterCard);

		final int nameFiledWidth = mWidth - captionFiledWidth - numberTextWidth
				- PANEL_CONTENT_V_GAP * 4;
		final int nameFiledHeight = PosTouchableFieldBase.LAYOUT_DEF_HEIGHT;

		mLabelName = new JLabel();
		mLabelName.setPreferredSize(new Dimension(nameFiledWidth,
				nameFiledHeight));
		mLabelName.setBorder(new LineBorder(Color.DARK_GRAY));
		mLabelName.setBackground(Color.LIGHT_GRAY);
		mLabelName.setOpaque(true);
		setLabelFont(mLabelName);
		add(mLabelName);

	}
	/***
	 * sets the waiter name in label
	 * @param waiter
	 */
	private void setWaiterName(BeanEmployees waiter) {
		mLabelName.setText(" " + waiter.getFirstName() + " "
				+ waiter.getMiddleName() + " " + waiter.getLastName());
	}
	/***
	 * sets the label font
	 * @param label
	 */
	private void setLabelFont(JLabel label) {
		Font newLabelFont = new Font(label.getFont().getName(), label.getFont()
				.getStyle(), 16);
		label.setFont(newLabelFont);
	}

	/**
	 * @param waiter
	 */
	public void setSelectedWaiter(BeanEmployees waiter) {
		mSelectedWaiter = waiter;
		if (mSelectedWaiter == null)
			return;
		mTxtWaiterCard.setText(waiter.getCardNumber());
		setWaiterName(waiter);
	}
	
	/**
	 * @param cardNo
	 */
	public void setSelectedWaiter(String cardNo){
		for(BeanEmployees waiter:mWaiterList){
			if(waiter.getCardNumber().equals(cardNo)){
				mTxtWaiterCard.setSelectedItem(waiter);
				break;
			}
		}
		
	}

	/**
	 * @throws SQLException 
	 * 
	 */
	public void setDefaultWaiter() throws SQLException {
		
		setSelectedWaiter(waiterProvider.getDefaultSystemWaiter());
		
	}

}
