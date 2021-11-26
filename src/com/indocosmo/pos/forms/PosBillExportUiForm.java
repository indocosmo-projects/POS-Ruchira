/**
 * 
 */
package com.indocosmo.pos.forms;

import java.util.ArrayList;
import java.util.Map;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;

import com.indocosmo.pos.common.PosEnvSettings;
import com.indocosmo.pos.common.PosLog;
import com.indocosmo.pos.common.utilities.PosDateUtil;
import com.indocosmo.pos.common.utilities.PosFormUtil;
import com.indocosmo.pos.common.utilities.PosNumberUtil;
import com.indocosmo.pos.common.utilities.PosOrderUtil;
import com.indocosmo.pos.data.beans.BeanOrderHeader;
import com.indocosmo.pos.data.beans.BeanOrderHeader.PosOrderStatus;
import com.indocosmo.pos.data.beans.BeanShopShift;
import com.indocosmo.pos.data.providers.shopdb.PosShopShiftProvider;
import com.indocosmo.pos.forms.components.checkboxes.PosCheckBox;
import com.indocosmo.pos.forms.components.textfields.PosItemBrowsableField;
import com.indocosmo.pos.forms.components.textfields.PosTouchableDigitalField;
import com.indocosmo.pos.forms.components.textfields.PosTouchableFieldBase;
import com.indocosmo.pos.forms.components.textfields.PosTouchableTextField;
import com.indocosmo.pos.forms.components.textfields.listners.IPosTouchableFieldListner;
import com.indocosmo.pos.forms.components.textfields.listners.PosTouchableFieldAdapter;
import com.indocosmo.pos.reports.export.PosBillExcel;

/**
 * @author deepak
 * 
 */
public class PosBillExportUiForm extends PosBaseForm {

	private static final int PANEL_CONTENT_V_GAP = 5;
	private static final int PANEL_CONTENT_H_GAP = 8;

	private static final int LABEL_WIDTH = 120;
	private static final int LABEL_HEIGHT = 40;
	private static final int LABEL_HEIGHT_SMALL = 30;
	private static final int LABEL_WIDTH_SMALL = 60;
	private static final int LABEL_WIDTH_BIG = LABEL_WIDTH + LABEL_WIDTH_SMALL
			+ PANEL_CONTENT_H_GAP;
	private static final int LABEL_WIDTH_HOUR = 210;
	private static final int LABEL_WIDTH_MINUTE = 210;

	// private static final int TEXT_FIELD_HEIGHT = 40;
	private static final int TEXT_FIELD_WIDTH = 420;
	private static final int TEXT_FIELD_WIDTH_DAY = 120;
	private static final int TEXT_FIELD_WIDTH_MONTH = 120;
	private static final int TEXT_FIELD_WIDTH_YEAR = 180;
	private static final int TEXT_FIELD_WIDTH_HOUR = 210;
	private static final int TEXT_FIELD_WIDTH_MINUTE = 210;
	private static final int TEXT_FIELD_WIDTH_BILL = 260;
	private static final int CHECKBOX_WIDTH = 220;
	private static final int CHECKBOX_HEIGHT = 40;

	private static final int PANEL_WIDTH = LABEL_WIDTH + LABEL_WIDTH_SMALL
			+ TEXT_FIELD_WIDTH + PANEL_CONTENT_H_GAP * 4;
	private static final int PANEL_HEIGHT = LABEL_HEIGHT * 8
			+ PANEL_CONTENT_V_GAP * 13;

	private JPanel mContentPanel;
	private int left;
	private int top;
	private JLabel mlabelOrderId;
	private JLabel mlabelFrom;
//	private PosItemSearchableField mTxtOrderIdFrom;
	private JLabel mlabelTo;
//	private PosItemSearchableField mTxtOrderIdTo;
	private JLabel mlabelOrderDate;
	private PosTouchableDigitalField mTxtDayFrom;
	private PosTouchableDigitalField mTxtMonthFrom;
	private PosTouchableDigitalField mTxtYearFrom;
	private PosTouchableDigitalField mTxtDayTo;
	private PosTouchableDigitalField mTxtMonthTo;
	private PosTouchableDigitalField mTxtYearTo;
	private JLabel mlabelOrderTime;
//	private PosTouchableNumericDigitalField mTxtHourFrom;
//	private PosTouchableNumericDigitalField mTxtMinuteFrom;
//	private PosTouchableNumericDigitalField mTxtHourTo;
//	private PosTouchableNumericDigitalField mTxtMinuteTo;
	private JLabel mlabelCashierShift;
	private PosItemBrowsableField mTxtCashierShift;
	private JLabel mlabelOrderStatus;
	private PosItemBrowsableField mTxtOrderStatus;
	private PosCheckBox mHeadersOnly;
	private JLabel mlabelDay;
	private JLabel mlabelMonth;
	private JLabel mlabelYear;
//	private JLabel mlabelHour;
//	private JLabel mlabelMinute;
	private ArrayList<BeanOrderHeader> mOrderList;
	private PosOrderStatus mOrderStatus;
	private JLabel mlabelBillName;
	private PosTouchableTextField mTxtBillName;
	private PosCheckBox mOpenFile;
	private JDialog mParent = this;
	private PosTouchableTextField mTxtOrderIdFrom;
	private PosTouchableTextField mTxtOrderIdTo;

	public PosBillExportUiForm() {
		super("Order Details Export", PANEL_WIDTH, PANEL_HEIGHT);
		loadOrderHeaders();
		initExportUiControls();
	}

	private void loadOrderHeaders() {
		try {
//			mOrderList = (new PosOrderHdrProvider()).getOrderHeaders();
		} catch (Exception e) {
			PosFormUtil
					.showErrorMessageBox(this,
							"Failed to load the order details. Please contact Administrator");
			PosLog.write(this, "loadOrderHeaders", e);
		}
	}

	/**
	 * 
	 */
	private void initExportUiControls() {
		createOrderNumberSelectionControl();
		createOrderDateSelectionControl();
//		createOrderTimeSelectionControl();
		createCashierShiftSelectionControl();
		createOrderStatusSelectionControl();
		createHeaderSlectionControl();
		createBillDestinationTextField();
		createFileOpenSelectionControl();
		setDefaults();
	}

	/**
	 * 
	 */
	private void setDefaults() {

		String posDate = PosEnvSettings.getInstance().getPosDate();
		if(posDate!=null){
			String dates[] = posDate.split("-");
			mTxtDayFrom.setText(dates[2]);
			mTxtDayTo.setText(dates[2]);
			mTxtMonthFrom.setText(dates[1]);
			mTxtMonthTo.setText(dates[1]);
			mTxtYearFrom.setText(dates[0]);
			mTxtYearTo.setText(dates[0]);
		}
	}

	/**
	 * 
	 */
	private void createOrderNumberSelectionControl() {

		left = PANEL_CONTENT_H_GAP;
		top = PANEL_CONTENT_V_GAP;

		mlabelOrderId = new JLabel();
		mlabelOrderId.setText("Order No");
		mlabelOrderId.setFont(PosFormUtil.getLabelFont());
		mlabelOrderId.setHorizontalAlignment(SwingConstants.LEFT);
		mlabelOrderId.setBounds(left, top, LABEL_WIDTH, LABEL_HEIGHT);
		mContentPanel.add(mlabelOrderId);

		left = mlabelOrderId.getX() + mlabelOrderId.getWidth()
				+ PANEL_CONTENT_H_GAP;

		mlabelFrom = new JLabel();
		mlabelFrom.setText("From :");
		mlabelFrom.setFont(PosFormUtil.getLabelFont());
		mlabelFrom.setHorizontalAlignment(SwingConstants.RIGHT);
		mlabelFrom.setBounds(left, top, LABEL_WIDTH_SMALL, LABEL_HEIGHT);
		mContentPanel.add(mlabelFrom);

		left = mlabelFrom.getX() + mlabelFrom.getWidth() + PANEL_CONTENT_H_GAP;

		mTxtOrderIdFrom= new PosTouchableTextField(this,TEXT_FIELD_WIDTH);
		mTxtOrderIdFrom.hideResetButton(true);
		mTxtOrderIdFrom.setHorizontalTextAlignment(JTextField.LEFT);
		mTxtOrderIdFrom.setFont(PosFormUtil.getTextFieldFont());
		mTxtOrderIdFrom.setLocation(left, top);
		mContentPanel.add(mTxtOrderIdFrom);

		left = mlabelOrderId.getX() + mlabelOrderId.getWidth()
				+ PANEL_CONTENT_H_GAP;
		top = PANEL_CONTENT_V_GAP + mlabelFrom.getY() + mlabelFrom.getHeight();

		mlabelTo = new JLabel();
		mlabelTo.setText("To :");
		mlabelTo.setFont(PosFormUtil.getLabelFont());
		mlabelTo.setHorizontalAlignment(SwingConstants.RIGHT);
		mlabelTo.setBounds(left, top, LABEL_WIDTH_SMALL, LABEL_HEIGHT);
		mContentPanel.add(mlabelTo);

		left = mlabelTo.getX() + mlabelTo.getWidth() + PANEL_CONTENT_H_GAP;

		mTxtOrderIdTo= new PosTouchableTextField(this,TEXT_FIELD_WIDTH);
		mTxtOrderIdTo.hideResetButton(true);
		mTxtOrderIdTo.setHorizontalTextAlignment(JTextField.LEFT);
		mTxtOrderIdTo.setFont(PosFormUtil.getTextFieldFont());
		mTxtOrderIdTo.setLocation(left, top);
		mContentPanel.add(mTxtOrderIdTo);
	}

	/**
	 * 
	 */
	private void createOrderDateSelectionControl() {

		setDateHeader();

		left = PANEL_CONTENT_H_GAP;
		top = PANEL_CONTENT_V_GAP + mlabelDay.getY() + mlabelDay.getHeight();

		mlabelOrderDate = new JLabel();
		mlabelOrderDate.setText("Order Date");
		mlabelOrderDate.setFont(PosFormUtil.getLabelFont());
		mlabelOrderDate.setHorizontalAlignment(SwingConstants.LEFT);
		mlabelOrderDate.setBounds(left, top, LABEL_WIDTH, LABEL_HEIGHT);
		mContentPanel.add(mlabelOrderDate);

		left = mlabelOrderId.getX() + mlabelOrderId.getWidth()
				+ PANEL_CONTENT_H_GAP;

		mlabelFrom = new JLabel();
		mlabelFrom.setText("From :");
		mlabelFrom.setFont(PosFormUtil.getLabelFont());
		mlabelFrom.setHorizontalAlignment(SwingConstants.LEFT);
		mlabelFrom.setBounds(left, top, LABEL_WIDTH_SMALL, LABEL_HEIGHT);
		mContentPanel.add(mlabelFrom);

		left = mlabelFrom.getX() + mlabelFrom.getWidth() + PANEL_CONTENT_H_GAP;

		mTxtDayFrom = new PosTouchableDigitalField(this,
				TEXT_FIELD_WIDTH_DAY);
		mTxtDayFrom.setTitle("Day");
		mTxtDayFrom.hideResetButton(true);
		mTxtDayFrom.setLocation(left, top);
		mTxtDayFrom.getTexFieldComponent().setDocument(PosNumberUtil.createDecimalDocument());
		mContentPanel.add(mTxtDayFrom);

		left = mTxtDayFrom.getX() + mTxtDayFrom.getWidth();

		mTxtMonthFrom = new PosTouchableDigitalField(this,
				TEXT_FIELD_WIDTH_MONTH);
		mTxtMonthFrom.setTitle("Month");
		mTxtMonthFrom.setLocation(left, top);
		mTxtMonthFrom.hideResetButton(true);
		mTxtMonthFrom.getTexFieldComponent().setDocument(PosNumberUtil.createDecimalDocument());
		mContentPanel.add(mTxtMonthFrom);

		left = mTxtMonthFrom.getX() + mTxtMonthFrom.getWidth();

		mTxtYearFrom = new PosTouchableDigitalField(this,
				TEXT_FIELD_WIDTH_YEAR);
		mTxtYearFrom.setTitle("Year");
		mTxtYearFrom.setLocation(left, top);
		mTxtYearFrom.getTexFieldComponent().setDocument(PosNumberUtil.createDecimalDocument());
		mTxtYearFrom.setListner(new IPosTouchableFieldListner() {

			@Override
			public void onValueSelected(Object value) {
				// TODO A0.00uto-generated method stub

			}

			@Override
			public void onReset() {
				mTxtDayFrom.reset();
				mTxtMonthFrom.reset();
			}
		});
		mContentPanel.add(mTxtYearFrom);

		left = mlabelOrderDate.getX() + mlabelOrderDate.getWidth()
				+ PANEL_CONTENT_H_GAP;
		top = mlabelFrom.getY() + mlabelFrom.getHeight() + PANEL_CONTENT_V_GAP;

		mlabelTo = new JLabel();
		mlabelTo.setText("To :");
		mlabelTo.setFont(PosFormUtil.getLabelFont());
		mlabelTo.setHorizontalAlignment(SwingConstants.RIGHT);
		mlabelTo.setBounds(left, top, LABEL_WIDTH_SMALL, LABEL_HEIGHT);
		mContentPanel.add(mlabelTo);

		left = mlabelTo.getX() + mlabelTo.getWidth() + PANEL_CONTENT_H_GAP;

		mTxtDayTo = new PosTouchableDigitalField(this,
				TEXT_FIELD_WIDTH_DAY);
		mTxtDayTo.setTitle("Day");
		mTxtDayTo.hideResetButton(true);
		mTxtDayTo.setLocation(left, top);
		mTxtDayTo.getTexFieldComponent().setDocument(PosNumberUtil.createDecimalDocument());
		mContentPanel.add(mTxtDayTo);

		left = mTxtDayTo.getX() + mTxtDayTo.getWidth();

		mTxtMonthTo = new PosTouchableDigitalField(this,
				TEXT_FIELD_WIDTH_MONTH);
		mTxtMonthTo.setTitle("Month");
		mTxtMonthTo.setLocation(left, top);
		mTxtMonthTo.hideResetButton(true);
		mTxtMonthTo.getTexFieldComponent().setDocument(PosNumberUtil.createDecimalDocument());
		mContentPanel.add(mTxtMonthTo);

		left = mTxtMonthTo.getX() + mTxtMonthTo.getWidth();

		mTxtYearTo = new PosTouchableDigitalField(this,
				TEXT_FIELD_WIDTH_YEAR);
		mTxtYearTo.setTitle("Year");
		mTxtYearTo.setLocation(left, top);
		mTxtYearTo.getTexFieldComponent().setDocument(PosNumberUtil.createDecimalDocument());
		mTxtYearTo.setListner(new IPosTouchableFieldListner() {

			@Override
			public void onValueSelected(Object value) {
				// TODO A0.00uto-generated method stub

			}

			@Override
			public void onReset() {
				mTxtDayTo.reset();
				mTxtMonthTo.reset();
			}
		});
		mContentPanel.add(mTxtYearTo);

	}

	/**
	 * 
	 */
	private void setDateHeader() {

		left = PANEL_CONTENT_H_GAP * 3 + LABEL_WIDTH + LABEL_WIDTH_SMALL;
		top = PANEL_CONTENT_V_GAP + mlabelTo.getY() + mlabelTo.getHeight();

		mlabelDay = new JLabel();
		mlabelDay.setText("Day(DD)");
		mlabelDay.setFont(PosFormUtil.getLabelFont());
		mlabelDay.setHorizontalAlignment(SwingConstants.CENTER);
		mlabelDay.setBounds(left, top, LABEL_WIDTH, LABEL_HEIGHT_SMALL);
		mContentPanel.add(mlabelDay);

		left = mlabelDay.getX() + mlabelDay.getWidth() + PANEL_CONTENT_H_GAP;

		mlabelMonth = new JLabel();
		mlabelMonth.setText("Month(MM)");
		mlabelMonth.setFont(PosFormUtil.getLabelFont());
		mlabelMonth.setHorizontalAlignment(SwingConstants.CENTER);
		mlabelMonth.setBounds(left, top, LABEL_WIDTH, LABEL_HEIGHT_SMALL);
		mContentPanel.add(mlabelMonth);

		left = mlabelMonth.getX() + mlabelMonth.getWidth()
				+ PANEL_CONTENT_H_GAP;

		mlabelYear = new JLabel();
		mlabelYear.setText("Year(YYYY)");
		mlabelYear.setFont(PosFormUtil.getLabelFont());
		mlabelYear.setHorizontalAlignment(SwingConstants.CENTER);
		mlabelYear.setBounds(left, top, TEXT_FIELD_WIDTH_YEAR, LABEL_HEIGHT_SMALL);
		mContentPanel.add(mlabelYear);
	}

	/**
	 * mTxtOrderIdFrom.setValueColumnIndex(2);
	 */
	private void createCashierShiftSelectionControl() {

		left = PANEL_CONTENT_H_GAP;
		top = mTxtDayTo.getY() + mTxtDayTo.getHeight() + PANEL_CONTENT_V_GAP;

		mlabelCashierShift = new JLabel();
		mlabelCashierShift.setText("Cashier Shift :");
		mlabelCashierShift.setFont(PosFormUtil.getLabelFont());
		mlabelCashierShift.setHorizontalAlignment(SwingConstants.LEFT);
		mlabelCashierShift.setBounds(left, top, LABEL_WIDTH_BIG, LABEL_HEIGHT);
		mContentPanel.add(mlabelCashierShift);

		left = mlabelCashierShift.getX() + mlabelCashierShift.getWidth()
				+ PANEL_CONTENT_H_GAP;
		
		PosShopShiftProvider shiftProvider=new PosShopShiftProvider();
		Map<Integer,BeanShopShift> shopShifts=shiftProvider.getShopShifts();
		shiftProvider=null;

		mTxtCashierShift = new PosItemBrowsableField(this, TEXT_FIELD_WIDTH);
		mTxtCashierShift.setBrowseItemList(new ArrayList<BeanShopShift>(shopShifts.values()));
		mTxtCashierShift.setTitle("Cashier Shift");
		mTxtCashierShift.setLocation(left, top);
		mContentPanel.add(mTxtCashierShift);
	}

	/**
	 * 
	 */
	private void createOrderStatusSelectionControl() {

		left = PANEL_CONTENT_H_GAP;
		top = mlabelCashierShift.getY() + mlabelCashierShift.getHeight()
				+ PANEL_CONTENT_V_GAP;

		mlabelOrderStatus = new JLabel();
		mlabelOrderStatus.setText("Order Status :");
		mlabelOrderStatus.setFont(PosFormUtil.getLabelFont());
		mlabelOrderStatus.setHorizontalAlignment(SwingConstants.LEFT);
		mlabelOrderStatus.setBounds(left, top, LABEL_WIDTH_BIG, LABEL_HEIGHT);
		mContentPanel.add(mlabelOrderStatus);

		left = mlabelOrderStatus.getX() + mlabelOrderStatus.getWidth()
				+ PANEL_CONTENT_H_GAP;

		mTxtOrderStatus = new PosItemBrowsableField(this, TEXT_FIELD_WIDTH);
		mTxtOrderStatus.setBrowseItemList(PosOrderStatus.values());
		mTxtOrderStatus.setTitle("Order Status");
		mTxtOrderStatus.setLocation(left, top);
		mTxtOrderStatus.setListner(new PosTouchableFieldAdapter() {

			/*
			 * (non-Javadoc)
			 * 
			 * @see com.indocosmo.pos.components.textfields.listners.
			 * PosTouchableFieldAdapter#onValueSelected(java.lang.Object)
			 */
			@Override
			public void onValueSelected(Object value) {
				// TODO Auto-generated method stub
				mOrderStatus = (PosOrderStatus) value;
			}
		});
		mContentPanel.add(mTxtOrderStatus);

	}

	/**
	 * 
	 */
	private void createHeaderSlectionControl() {

		left = PANEL_CONTENT_H_GAP;
		top = mlabelOrderStatus.getY() + mlabelOrderStatus.getHeight()
				+ PANEL_CONTENT_V_GAP;

		mHeadersOnly = new PosCheckBox();
		mHeadersOnly.setTag("Headers Only");
		mHeadersOnly.setHorizontalAlignment(JTextField.LEFT);
		mHeadersOnly.setBounds(left, top, CHECKBOX_WIDTH, CHECKBOX_HEIGHT);
		mHeadersOnly.setFont(PosFormUtil.getTextFieldBoldFont());
		mHeadersOnly.setText("Headers Only");
		mContentPanel.add(mHeadersOnly);
	}

	private void createBillDestinationTextField(){
		
		left = mHeadersOnly.getX()+mHeadersOnly.getWidth()+PANEL_CONTENT_H_GAP;
		top = mlabelOrderStatus.getY() + mlabelOrderStatus.getHeight()
				+ PANEL_CONTENT_V_GAP;
		
		mlabelBillName = new JLabel();
		mlabelBillName.setText("File Name");
		mlabelBillName.setFont(PosFormUtil.getLabelFont());
		mlabelBillName.setHorizontalAlignment(SwingConstants.RIGHT);
		mlabelBillName.setBounds(left, top, LABEL_WIDTH, LABEL_HEIGHT);
		mContentPanel.add(mlabelBillName);

		left = mlabelBillName.getX()+mlabelBillName.getWidth()+PANEL_CONTENT_H_GAP;
		
		mTxtBillName = new PosTouchableTextField(this,TEXT_FIELD_WIDTH_BILL);
		mTxtBillName.setTitle("File Name");
		mTxtBillName.setLocation(left, top);
		mContentPanel.add(mTxtBillName);
		
	}
	
	private void createFileOpenSelectionControl(){
		
		left = PANEL_CONTENT_H_GAP;
		top = mHeadersOnly.getY() + mHeadersOnly.getHeight()
				+ PANEL_CONTENT_V_GAP;

		mOpenFile = new PosCheckBox();
		mOpenFile.setTag("Open File when created");
		mOpenFile.setHorizontalAlignment(JTextField.LEFT);
		mOpenFile.setBounds(left, top, CHECKBOX_WIDTH, CHECKBOX_HEIGHT);
		mOpenFile.setFont(PosFormUtil.getTextFieldBoldFont());
		mOpenFile.setText("Open File");
//		mContentPanel.add(mOpenFile);
		
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.indocosmo.pos.forms.PosBaseForm#setContentPanel(javax.swing.JPanel)
	 */
	@Override
	protected void setContentPanel(JPanel panel) {
		mContentPanel = panel;
		mContentPanel.setLayout(null);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indocosmo.pos.forms.PosBaseForm#onOkButtonClicked()
	 */
	@Override
	public boolean onOkButtonClicked() {

		boolean valid = true;
		String orderNoFrom;
		String orderNoTo;
		String orderDateFrom;
		String orderDateTo;
		String orderTimeFrom = null;
		String orderTimeTo = null;
		int cashierShift;
		String orderStatus;
		String billFileName;
		String where;
		String string = "";
		String criteria="";
		orderDateFrom = (mTxtDayFrom.getText().trim().length()!=0 || mTxtMonthFrom.getText().trim().length()!=0
				|| mTxtYearFrom.getText().trim().length()!=0)?PosDateUtil
				.buildStringDate(mTxtYearFrom.getText()
				.trim(), mTxtMonthFrom.getText().trim(), mTxtDayFrom.getText()
				.trim(), PosDateUtil.DATE_SEPERATOR):null;
		orderDateTo = (mTxtDayTo.getText().trim().length()!=0 || mTxtMonthTo.getText().trim().length()!=0
				|| mTxtYearTo.getText().trim().length()!=0)?PosDateUtil
				.buildStringDate(mTxtYearTo.getText().trim(),
				mTxtMonthTo.getText().trim(), mTxtDayTo.getText().trim(),
				PosDateUtil.DATE_SEPERATOR):null;
		billFileName = mTxtBillName.getText().trim()+".xls";

		try {

			if (validateSearchCriteria(orderDateFrom, orderDateTo,
					orderTimeFrom, orderTimeTo)) {

				if (mTxtOrderIdFrom.getText().length() != 0 || mTxtOrderIdTo.getText().length() != 0) {
					
					orderNoFrom = PosOrderUtil.getFormatedOrderId(mTxtOrderIdFrom.getText());
					orderNoTo = PosOrderUtil.getFormatedOrderId(mTxtOrderIdTo.getText());
					
					string = string
							+ ((mTxtOrderIdFrom.getText().length() != 0) ? ((mTxtOrderIdTo.getText().length() != 0) ? (" order_id between '"
									+ orderNoFrom + "' and '" + orderNoTo + "'")
									: (" order_id ='" + orderNoFrom + "'"))
									: (" order_id ='" + orderNoTo + "'"))
							+ " and ";
					criteria =criteria+((mTxtOrderIdFrom.getText().length() != 0) ? ((mTxtOrderIdTo.getText().length() != 0) ? (" Order Id From '"
							+ mTxtOrderIdFrom.getText() + "' To '" + mTxtOrderIdTo.getText() + "'")
							: (" Order Id ='" + mTxtOrderIdFrom.getText() + "'"))
							: (" Order Id ='" + mTxtOrderIdTo.getText() + "'"))
					+ " , ";

				}

				if (orderDateFrom != null || orderDateTo != null) {
					string = string
							+ ((orderDateFrom != null) ? ((orderDateTo != null) ? (" order_date between '"
									+ orderDateFrom + "' and '" + orderDateTo + "'")
									: (" order_date ='" + orderDateFrom + "'"))
									: (" order_date ='" + orderDateTo + "'"))
							+ " and ";
					criteria =criteria+((orderDateFrom != null) ? ((orderDateTo != null) ? (" Order Date From '"
							+ orderDateFrom + "' To '" + orderDateTo + "'")
							: (" Order Date ='" + orderDateFrom + "'"))
							: (" order Date ='" + orderDateTo + "'"))
					+ " , ";

				}

				if (orderTimeFrom != null || orderTimeTo != null) {
				}

				if (mTxtCashierShift.getText().length() != 0) {
					cashierShift = ((BeanShopShift)mTxtCashierShift.getSelectedValue()).getId();
					string = string
							+ (" shift_id ='" + cashierShift + "' and ");
					criteria =criteria+(" Shift Name ='" + mTxtCashierShift.getText() + "' , ");
				}

				if (mTxtOrderStatus.getText().length() != 0) {
					orderStatus = String.valueOf(mOrderStatus.getCode());
					string = string + (" status ='" + orderStatus + "' and ");
					criteria =criteria+(" Status ='" + mTxtOrderStatus.getText() + "' , ");
				}

				where = (string.length() != 0) ? string.substring(0,
						string.length() - 4) : string;
				criteria = (criteria.length() != 0) ? criteria.substring(0,
						criteria.length() - 2) : criteria;
				doCreateBill(mParent,where,criteria,billFileName, mHeadersOnly.isSelected(),mOpenFile.isSelected());
			}
		} catch (Exception e) {
			PosFormUtil.showErrorMessageBox(this, e.getMessage());
			valid = false;
		}
		
		return valid;
	}

	/**
	 * @param c 
	 * @param headerOnly 
	 * @param billFileName 
	 * @param criteria 
	 * @param where 
	 * @param mParent2 
	 * 
	 */
	private void doCreateBill(JDialog mParent2, String where, final String criteria, final String billFileName, final boolean headerOnly, final boolean OpenFile) {
		final String where1 =where;
		final PosBillExcel billExcel = new PosBillExcel();
		SwingWorker<Boolean, String> swt = new SwingWorker<Boolean, String>() {
			@Override
			protected Boolean doInBackground() throws Exception {
				try {
					billExcel.createBill(mParent,where1,criteria,billFileName, headerOnly,OpenFile);
				} catch (Exception e) {
					PosFormUtil.closeBusyWindow();
					throw new Exception(
							"Problem in creating file. Please check the log for details.");
				}
				return true;
			}
			@Override
			protected void done() {
				PosFormUtil.closeBusyWindow();
				PosFormUtil.showInformationMessageBox(null, "Excel file has been generated in the export folder.");
			}
		};
		swt.execute();
		PosFormUtil.showBusyWindow(this,
				"Please wait ...");
		
	}

	private boolean validateSearchCriteria(String orderDateFrom, String orderDateTo, 
			String orderTimeFrom, String orderTimeTo) throws Exception{
		String errMessage=null;
		PosTouchableFieldBase errorControl=null;
		
		
		if ((mTxtDayFrom.getText().trim().length() != 0)
				&& (mTxtDayFrom.getText().trim().length() > 2)) {
			errMessage = "Invalid day.";
			errorControl = mTxtDayFrom;
		} else if ((mTxtMonthFrom.getText().trim().length() != 0)
				&& (mTxtMonthFrom.getText().trim().length() > 2)) {
			errMessage = "Invalid month.";
			errorControl = mTxtMonthFrom;
		} else if ((mTxtYearFrom.getText().trim().length() != 0)
				&& (mTxtYearFrom.getText().trim().length()) != 4) {
			errMessage = "Invalid year.";
			errorControl = mTxtYearFrom;
		} else if (orderDateFrom!=null && (!PosDateUtil.validateDate(orderDateFrom))) {
				errMessage = "Date is not in proper format.";
				errorControl = mTxtDayFrom;
		}else if ((mTxtDayTo.getText().trim().length() != 0)
				&& (mTxtDayTo.getText().trim().length() > 2)) {
			errMessage = "Invalid day.";
			errorControl = mTxtDayTo;
		} else if ((mTxtMonthTo.getText().trim().length() != 0)
				&& (mTxtMonthTo.getText().trim().length() > 2)) {
			errMessage = "Invalid month.";
			errorControl = mTxtMonthTo;
		} else if ((mTxtYearTo.getText().trim().length() != 0)
				&& (mTxtYearTo.getText().trim().length()) != 4) {
			errMessage = "Invalid year.";
			errorControl = mTxtYearTo;
		} else if (orderDateTo!=null && (!PosDateUtil.validateDate(orderDateTo))) {
				errMessage = "Date is not in proper format.";
				errorControl = mTxtDayTo;
		}
		else if(mTxtBillName.getText().trim().length() == 0){
			errMessage = "Please enter the bill name.";
			errorControl = mTxtBillName;
		}
		if(errorControl!=null){
			errorControl.requestFocus();
			throw new Exception(errMessage);
		}
		
		return true;
	}
}
