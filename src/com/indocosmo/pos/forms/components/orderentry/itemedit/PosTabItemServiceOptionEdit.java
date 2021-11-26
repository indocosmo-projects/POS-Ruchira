/**
 * 
 */
package com.indocosmo.pos.forms.components.orderentry.itemedit;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.RootPaneContainer;
import javax.swing.SwingConstants;

import com.indocosmo.pos.common.enums.PosOrderServiceTypes;
import com.indocosmo.pos.common.utilities.PosFormUtil;
import com.indocosmo.pos.data.beans.BeanEmployees;
import com.indocosmo.pos.data.beans.BeanOrderDetail;
import com.indocosmo.pos.data.beans.BeanServingTable;
import com.indocosmo.pos.data.providers.shopdb.PosServiceTableProvider;
import com.indocosmo.pos.data.providers.shopdb.PosWaiterProvider;
import com.indocosmo.pos.forms.components.tab.PosTab;
import com.indocosmo.pos.forms.components.textfields.PosItemBrowsableField;
import com.indocosmo.pos.forms.components.textfields.PosTouchableFieldBase;
import com.indocosmo.pos.forms.components.textfields.PosTouchableNumericField;
import com.indocosmo.pos.forms.components.textfields.listners.IPosTouchableFieldListner;
import com.indocosmo.pos.forms.components.textfields.listners.PosTouchableFieldAdapter;
import com.indocosmo.pos.forms.listners.IPosFormEventsListner;

/**
 * @author jojesh
 * 
 */
public class PosTabItemServiceOptionEdit extends PosTab implements
		IPosFormEventsListner {

	protected static final int PANEL_CONTENT_H_GAP = 2;
	protected static final int PANEL_CONTENT_V_GAP = 2;
	private static final int ITEM_PANEL_H_GAP = 1;

	private BeanOrderDetail mOrderDetailObject;
	private PosOrderServiceTypes mSelectedServiceType;
	private BeanServingTable mSelectedServiceTable;
	private BeanEmployees mSelectedServedBy;
	private int mSelectedSeatNumber;

	private PosItemBrowsableField mFieldServedBy;
	private PosItemBrowsableField mFieldService;
	private PosItemBrowsableField mFieldTable;
	private PosTouchableNumericField mSeatNumber;
	private JPanel mServiceTablePanel;

	private static final int ITEM_TITEL_WIDTH = 135;
	private static final int ITEM_TEXT_WIDTH = 300;
	private static final int ITEM_HEIGHT = PosTouchableFieldBase.LAYOUT_DEF_HEIGHT;

	private static final int ITEM_PANEL_HIEGHT = ITEM_HEIGHT;
	private static final int ITEM_PANEL_WIDTH = (ITEM_TITEL_WIDTH + ITEM_TEXT_WIDTH)
			+ ITEM_PANEL_H_GAP * 3;

	private ArrayList<BeanEmployees> mPosWaiterList;
	private ArrayList<BeanServingTable> mPosServiceTableList;

	private PosWaiterProvider mPosWaiterProvider;
	private PosServiceTableProvider mServiceTableProvider;
	

	private RootPaneContainer mParent;

	private boolean isDirty;

	/**
	 * @param caption
	 */
	public PosTabItemServiceOptionEdit(RootPaneContainer parent,
			BeanOrderDetail dtlObject) {
		super(parent,"Service");
		this.mOrderDetailObject = dtlObject;
		this.mParent = parent;
		this.setLayout(new FlowLayout(FlowLayout.LEFT));

		try {
			mPosWaiterProvider = new PosWaiterProvider();
			mServiceTableProvider = new PosServiceTableProvider();

			mPosWaiterList = mPosWaiterProvider.getWaiterList();
			mPosServiceTableList = mServiceTableProvider.getServiceTableList();
			initControls();
		} catch (Exception e) {
			PosFormUtil.showSystemError(mParent);
			e.printStackTrace();
		}
	}

	private void initControls() {

		mFieldService = createServiceField(this);
		mFieldServedBy = createServedByField(this);
		mFieldTable = createTableField(this);
		mSeatNumber= createSeatField(this);
		
		onResetButtonClicked();
	}

	/***
	 * 
	 * @param panel
	 * @return
	 */
	private PosItemBrowsableField createServedByField(JPanel panel) {

		JPanel itemPanel = creatFieldPanelWithTitle("Served By :");
		panel.add(itemPanel);

		final PosItemBrowsableField field = new PosItemBrowsableField(mParent,
				ITEM_TEXT_WIDTH);
		field.setBrowseItemList(mPosWaiterList);
		field.setBrowseWindowSize(3, 3);
		field.setHorizontalTextAlignment(SwingConstants.LEFT);
		field.setFont(PosFormUtil.getTextFieldBoldFont());
		field.setTitle("Served By");
		field.hideResetButton(true);
		field.setListner(new PosTouchableFieldAdapter() {

			@Override
			public void onValueSelected(Object value) {
				mSelectedServedBy = (BeanEmployees) value;
				isDirty = true;
			}

			/*
			 * (non-Javadoc)
			 * 
			 * @see com.indocosmo.pos.components.textfields.listners.
			 * PosTouchableFieldAdapter#onReset()
			 */
			@Override
			public void onReset() {
				super.onReset();
				mSelectedServedBy = null;
				isDirty = true;
			}
		});

		itemPanel.add(field);
		return field;
	}

	/***
	 * 
	 * @param panel
	 * @return
	 */
	private PosItemBrowsableField createServiceField(JPanel panel) {

		JPanel itemPanel = creatFieldPanelWithTitle("Service :");
		panel.add(itemPanel);

		final PosItemBrowsableField field = new PosItemBrowsableField(mParent,
				ITEM_TEXT_WIDTH);
		field.setBrowseItemList(PosOrderServiceTypes.values());
		field.setTitle("Services");
		field.hideResetButton(true);
		field.setFont(PosFormUtil.getTextFieldFont());
		field.setListner(new PosTouchableFieldAdapter() {

			@Override
			public void onValueSelected(Object value) {
				if (mSelectedServiceType == (PosOrderServiceTypes) value)
					return;
				mSelectedServiceType = (PosOrderServiceTypes) value;
//				mFieldTable.reset();
//				mFieldServedBy.reset();
				try {
					switch (mSelectedServiceType) {
					case HOME_DELIVERY:
						mSelectedServiceTable = mServiceTableProvider
								.getTableByServiceType(mSelectedServiceType);
						break;
					case TABLE_SERVICE:
						mSelectedServiceTable = null;
						break;
					case TAKE_AWAY:
						mSelectedServiceTable = mServiceTableProvider
								.getTableByServiceType(mSelectedServiceType);
						mSelectedServedBy = mOrderDetailObject.getServedBy();
						break;
					default:
						break;
					}
					setupUI();
				} catch (Exception e) {
					PosFormUtil.showSystemError(mParent);
				}
				isDirty = true;
			}

			/*
			 * (non-Javadoc)
			 * 
			 * @see com.indocosmo.pos.components.textfields.listners.
			 * PosTouchableFieldAdapter#onReset()
			 */
			@Override
			public void onReset() {
				mSelectedServiceType = null;
				isDirty = true;
			}
		});
		itemPanel.add(field);
		return field;
	}

	/***
	 * 
	 * @param panel
	 * @return
	 */
	private PosItemBrowsableField createTableField(JPanel panel) {

		mServiceTablePanel = creatFieldPanelWithTitle("Table :");
		panel.add(mServiceTablePanel);

		final PosItemBrowsableField field = new PosItemBrowsableField(mParent,
				ITEM_TEXT_WIDTH);
		field.setBrowseWindowSize(3, 3);
		field.setBrowseItemList(mPosServiceTableList);
		field.setTitle("Tables");
		field.hideResetButton(true);
		field.setFont(PosFormUtil.getTextFieldFont());
		field.setListner(new IPosTouchableFieldListner() {

			@Override
			public void onValueSelected(Object value) {
				mSelectedServiceTable = (BeanServingTable) value;
				isDirty = true;
			}

			@Override
			public void onReset() {
				mSelectedServiceTable = null;
			}
		});
		mServiceTablePanel.add(field);
		return field;
	}
	
	/***
	 * 
	 * @param panel
	 * @return
	 */
	private PosTouchableNumericField createSeatField(JPanel panel) {

		mServiceTablePanel = creatFieldPanelWithTitle("Seat :");
		panel.add(mServiceTablePanel);

		final PosTouchableNumericField field = new PosTouchableNumericField(mParent,
				ITEM_TEXT_WIDTH);
		field.setTitle("Tables");
		field.hideResetButton(true);
		field.setFont(PosFormUtil.getTextFieldFont());
		field.setTextReadOnly(true);
		field.setListner(new IPosTouchableFieldListner() {

			@Override
			public void onValueSelected(Object value) {
				mSelectedSeatNumber=Integer.valueOf(value.toString());
				isDirty = true;
			}

			@Override
			public void onReset() {

			}
		});
		mServiceTablePanel.add(field);
		return field;
	}


	/***
	 * 
	 * @param title
	 * @return
	 */
	private JPanel creatFieldPanelWithTitle(String title) {
		JPanel itemPanel = new JPanel();
		itemPanel
				.setLayout(new FlowLayout(FlowLayout.LEFT, ITEM_PANEL_H_GAP, 0));
		itemPanel.setPreferredSize(new Dimension(ITEM_PANEL_WIDTH,
				ITEM_PANEL_HIEGHT));

		JLabel lable = new JLabel(title);
		lable.setPreferredSize(new Dimension(ITEM_TITEL_WIDTH, ITEM_HEIGHT));
		lable.setFont(PosFormUtil.getLabelFont());
		lable.setOpaque(true);
		lable.setBackground(Color.LIGHT_GRAY);
		itemPanel.add(lable);

		return itemPanel;
	}

	/***
	 * set the control with data
	 */
	private void setupUI() {

		mFieldServedBy.setSelectedItem(mSelectedServedBy);
		mFieldTable.setSelectedItem(mSelectedServiceTable);
		mFieldService.setSelectedItem(mSelectedServiceType);
		mSeatNumber.setText(String.valueOf(mSelectedSeatNumber));
		
		mFieldService.setEnabled(false);
		mFieldTable.setEnabled(false);
		mFieldServedBy.setEnabled(false);
		mSeatNumber.setEnabled(false);

		switch (mSelectedServiceType) {
		case HOME_DELIVERY:
			mServiceTablePanel.setVisible(false);
			mFieldServedBy.setVisible(false);
			mFieldTable.setVisible(false);
			mSeatNumber.setVisible(false);
			break;
		case TABLE_SERVICE:
			mFieldTable.setEnabled(true);
			mFieldServedBy.setEnabled(true);
			mSeatNumber.setEnabled(true);
			break;
		case TAKE_AWAY:
			mServiceTablePanel.setVisible(false);
			mSeatNumber.setVisible(false);
			mFieldServedBy.setEnabled(false);
			break;
		default:
			break;
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.indocosmo.pos.forms.listners.IPosFormEventsListner#onOkButtonClicked
	 * ()
	 */
	@Override
	public boolean onOkButtonClicked() {

		mOrderDetailObject.setServiceType(mSelectedServiceType);
		mOrderDetailObject.setServedBy(mSelectedServedBy);
		mOrderDetailObject.setServingTable(mSelectedServiceTable);
		mOrderDetailObject.setServingSeat(mSelectedSeatNumber);
		
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.indocosmo.pos.forms.listners.IPosFormEventsListner#onCancelButtonClicked
	 * ()
	 */
	@Override
	public boolean onCancelButtonClicked() {
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.indocosmo.pos.forms.listners.IPosFormEventsListner#onResetButtonClicked
	 * ()
	 */
	@Override
	public void onResetButtonClicked() {
		mSelectedServiceType = mOrderDetailObject.getServiceType();
		mSelectedServedBy = mOrderDetailObject.getServedBy();
		mSelectedServiceTable = mOrderDetailObject.getServingTable();
		
		setupUI();
	}

}
