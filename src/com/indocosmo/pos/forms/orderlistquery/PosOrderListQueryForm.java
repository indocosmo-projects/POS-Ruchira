/**
 * 
 */
package com.indocosmo.pos.forms.orderlistquery;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import com.indocosmo.barcode.reports.BarCodeLabelPrint;
import com.indocosmo.pos.common.PosEnvSettings;
import com.indocosmo.pos.common.PosLog;
import com.indocosmo.pos.common.enums.EnablePrintingOption;
import com.indocosmo.pos.common.enums.PosOrderServiceTypes;
import com.indocosmo.pos.common.enums.PosPrintOption;
import com.indocosmo.pos.common.exception.NoSuchOrderException;
import com.indocosmo.pos.common.utilities.PosAccessPermissionsUtil;
import com.indocosmo.pos.common.utilities.PosDateUtil;
import com.indocosmo.pos.common.utilities.PosFormUtil;
import com.indocosmo.pos.common.utilities.PosNumberUtil;
import com.indocosmo.pos.common.utilities.PosOrderUtil;
import com.indocosmo.pos.common.utilities.PosPrintingUtil;
import com.indocosmo.pos.common.utilities.PosResUtil;
import com.indocosmo.pos.data.beans.BeanOrderHeader;
import com.indocosmo.pos.data.beans.BeanOrderHeader.PosOrderStatus;
import com.indocosmo.pos.data.beans.BeanOrderStatusReport;
import com.indocosmo.pos.data.beans.BeanUser;
import com.indocosmo.pos.data.providers.shopdb.PosOrderHdrProvider;
import com.indocosmo.pos.data.providers.shopdb.PosOrderStatusReportProvider;
import com.indocosmo.pos.forms.PosBaseForm;
import com.indocosmo.pos.forms.PosOrderInfoDetForm;
import com.indocosmo.pos.forms.components.buttons.PosButton;
import com.indocosmo.pos.forms.components.buttons.listners.IPosButtonListner;
import com.indocosmo.pos.forms.components.objectbrowser.IPosBrowsableItem;
import com.indocosmo.pos.forms.components.textfields.PosItemBrowsableField;
import com.indocosmo.pos.forms.components.textfields.PosTouchableDigitalField;
import com.indocosmo.pos.forms.components.textfields.PosTouchableFieldBase;
import com.indocosmo.pos.forms.components.textfields.PosTouchableTextField;
import com.indocosmo.pos.forms.components.textfields.listners.IPosTouchableFieldListner;
import com.indocosmo.pos.forms.listners.IPosBillPrintFormListner;
import com.indocosmo.pos.forms.listners.adapters.PosBillPrintFormAdapter;
import com.indocosmo.pos.forms.messageboxes.PosMessageBoxForm.MessageBoxButtonTypes;
import com.indocosmo.pos.forms.messageboxes.PosMessageBoxForm.MessageBoxResults;
import com.indocosmo.pos.forms.orderlistquery.components.OrderListQueryTablePanel;
import com.indocosmo.pos.forms.orderlistquery.components.listeners.IOrderListQueryTablePanelListener;
import com.indocosmo.pos.forms.orderrefund.PosOrderRefundEditForm;
import com.indocosmo.pos.forms.orderrefund.listeners.IPosOrderRefundFormListner;
import com.indocosmo.pos.reports.receipts.PosReceipts;
import com.indocosmo.pos.terminal.devices.PosDeviceManager;
import com.indocosmo.pos.terminal.devices.printer.printerexceptions.PrinterException;

/**
 * @author sandhya
 *
 */
public class PosOrderListQueryForm extends PosBaseForm {

 
	private static final long serialVersionUID = 1L;
	private static final int PANEL_WIDTH = 940;
	private static final int PANEL_HEIGHT = 568;
	
	private static final int PANEL_CONTENT_V_GAP = 2;
	private static final int PANEL_CONTENT_H_GAP = 2;

	private static final int DATEPANEL_HEIGHT = 120;

	private static final int TEXT_FIELD_WIDTH_DAY = 100;
	private static final int TEXT_FIELD_WIDTH_MONTH = 110;
	private static final int TEXT_FIELD_WIDTH_YEAR = 170;
	private static final int LABEL_HEIGHT = 40;
	private static final int LABEL_HEIGHT_SMALL = 30;
	private static final int LABEL_WIDTH_SMALL = 60;
	
	private static final int LABEL_WIDTH = 130;
	private static final int TEXT_WIDTH = 359;
	
	private static final String IMAGE_BUTTON_RETRIEVE="dlg_payment.png";
	private static final String IMAGE_BUTTON_RETRIEVE_TOUCH="dlg_payment_touch.png";

	private static final Border LABEL_PADDING = new EmptyBorder(2, 2, 2, 2);
	private OrderListQueryTablePanel orderStatusRptListPanel;
	
	private JPanel mContianerPanel;
	private JPanel mPanelDateSelection;
	
	private PosTouchableDigitalField mTxtDayFrom;
	private PosTouchableDigitalField mTxtMonthFrom;
	private PosTouchableDigitalField mTxtYearFrom;
	private PosTouchableDigitalField mTxtDayTo;
	private PosTouchableDigitalField mTxtMonthTo;
	private PosTouchableDigitalField mTxtYearTo;
	
	private JLabel mlabelFrom;
	private JLabel mlabelTo;
	private JLabel mlabelDay;
	private JLabel mlabelMonth;
	private JLabel mlabelYear;
	
	private PosButton mButtonSelect;
	private PosButton mButtonPrint;
	private PosButton mButtonRefund;
	private PosButton mBtnRetrive;
	private PosItemBrowsableField mOrderStatus;
	private PosOrderStatusReportProvider  mOrderStatusProvider;
	private PosTouchableTextField mTxtInvQueNo;
	
	private static ArrayList<BeanOrderStatusReport> mOrderHeaderList = null;
	private ArrayList<IPosBrowsableItem> printButtonList;

	public PosOrderListQueryForm() {
		super("Order List", PANEL_WIDTH, PANEL_HEIGHT);

		createButtons();
		createCriteriaControl();
		setDefaults();
		mOrderStatusProvider = new PosOrderStatusReportProvider();
		createOrderStatusReportList();
		setDefaultComponent(mTxtInvQueNo);
		setDefaultButton(mBtnRetrive);
 	}
	
	 
	
	/* (non-Javadoc)
	 * @see com.indocosmo.pos.forms.PosBaseForm#setContentPanel(javax.swing.JPanel)
	 */
	@Override
	protected void setContentPanel(JPanel panel) {

		mContianerPanel = panel;
		mContianerPanel.setLayout(null);
	
	}
	
	/**
	 * 
	 */
	private void refreshButtons(){
	
		int selectedCount= 0;
		int totalCount=0;
		if (orderStatusRptListPanel.getSelectedItemList()!=null) 
			selectedCount= orderStatusRptListPanel.getSelectedItemList().size(); 
		if (mOrderHeaderList!=null)
			totalCount =mOrderHeaderList.size() ;
		
		if (selectedCount==0 && mButtonSelect.getText() =="Deselect All" )
			mButtonSelect.setText("Select All");
		
		if (selectedCount==totalCount && mButtonSelect.getText()  =="Select All" )
			mButtonSelect.setText("Deselect All");
	}

	/**
	 * 
	 */
	private void  buildPrintButton (){
		
		mButtonPrint = new PosButton();
		mButtonPrint.setText("Print");
		mButtonPrint.setImage(PosResUtil
				.getImageIconFromResource(IMAGE_BUTTON_OK));
		mButtonPrint.setTouchedImage(PosResUtil
				.getImageIconFromResource(IMAGE_BUTTON_OK_TOUCH));
		mButtonPrint.setHorizontalAlignment(SwingConstants.CENTER);
		mButtonPrint.setSize(IMAGE_BUTTON_WIDTH, IMAGE_BUTTON_HEIGHT);
		mButtonPrint.setOnClickListner( mPrintButtonListner); 
		mButtonPrint.setEnabled(false);
		addButtonsToBottomPanel(mButtonPrint,1);
		
		printButtonList=PosPrintingUtil.buildPrintOptions();
		mButtonPrint.setEnabled(printButtonList.size()>0);
	}

	/**
	 * 
	 */
	private void setDefaults() {

		String posDate = PosEnvSettings.getInstance().getPosDate();
		if(posDate!=null){
			String dates[] = posDate.split("-");
			mTxtDayFrom.setMinValue(dates[2]);
			mTxtDayTo.setMinValue(dates[2]);
			mTxtMonthFrom.setMinValue(dates[1]);
			mTxtMonthTo.setMinValue(dates[1]);
			mTxtYearFrom.setMinValue(dates[0]);
			mTxtYearTo.setMinValue(dates[0]);
		}
	}
	/**
	 * 
	 */
	private void createButtons(){
		
		PosButton buttonReset=addButtonsToBottomPanel("Reset", mResetButtonListner, 1);
		buttonReset.setMnemonic('R');
		
		buildPrintButton();
		
		mButtonRefund= addButtonsToBottomPanel("Refund", null, 0);
		mButtonRefund.setMnemonic('f');
		mButtonRefund.setImage(IMAGE_BUTTON_OK,IMAGE_BUTTON_OK_TOUCH );
		mButtonRefund.setOnClickListner(mRefundButtonListner);
		if (!PosEnvSettings.getInstance().getUISetting().getMainMenuUISettings().getMenuListPanelSettings().
				isOrderRefundButtonVisible())
			
				mButtonRefund.setVisible(false);
		
		mBtnRetrive= addButtonsToBottomPanel("Retrive", null, 0);
		mBtnRetrive.setMnemonic('v');
		mBtnRetrive.setImage(IMAGE_BUTTON_OK,IMAGE_BUTTON_OK_TOUCH );
		mBtnRetrive.setImage(PosResUtil.getImageIconFromResource(IMAGE_BUTTON_RETRIEVE));
		mBtnRetrive.setTouchedImage(PosResUtil.getImageIconFromResource(IMAGE_BUTTON_RETRIEVE_TOUCH));	
		mBtnRetrive.setOnClickListner(mRetrieveButtonListner);
		
		mButtonSelect= addButtonsToBottomPanel("Deselect All", null, 0);
		mButtonSelect.setImage(IMAGE_BUTTON_OK,IMAGE_BUTTON_OK_TOUCH );
		mButtonSelect.setOnClickListner(mSelectButtonListner);
		mButtonSelect.setVisible(false);

		setOkButtonCaption("Detail");
		setOkButtonVisible(false);
		setCancelButtonCaption("Close");
	
	}
	
	/**
	 * 
	 */
	private void createOrderStatusReportList(){
		
		int width = PANEL_WIDTH - PANEL_CONTENT_H_GAP * 8;
		int height = PANEL_HEIGHT -DATEPANEL_HEIGHT- PANEL_CONTENT_V_GAP*13;
		int top = DATEPANEL_HEIGHT + PANEL_CONTENT_V_GAP *8;
		int left =PANEL_CONTENT_H_GAP *4;
		 
	 	orderStatusRptListPanel =new  OrderListQueryTablePanel(mContianerPanel ,width ,height );
		orderStatusRptListPanel.setLocation(left ,top);
		orderStatusRptListPanel.setListener(new IOrderListQueryTablePanelListener() {
			
			@Override
			public void onSelectionChanged(int index) {
				refreshButtons();
			}
		} );
		populateOrderStatusReport();
	}
	
	/**
	 * 
	 */
	private void createOrderDateSelectionControl() {

		int width = PANEL_WIDTH - PANEL_CONTENT_H_GAP * 8;
		
		mPanelDateSelection = createBoxedPanel(width, DATEPANEL_HEIGHT-1 );
		mPanelDateSelection.setLocation(PANEL_CONTENT_H_GAP *4, PANEL_CONTENT_V_GAP*4);
		mContianerPanel.add(mPanelDateSelection);

		setDateHeader();
		int left =PANEL_CONTENT_H_GAP+1;
		int top = mlabelDay.getY() + mlabelDay.getHeight()+1;

		mlabelFrom = new JLabel();
		mlabelFrom.setText("From :");
		mlabelFrom.setFont(PosFormUtil.getLabelFont());
		mlabelFrom.setHorizontalAlignment(SwingConstants.LEFT);
		mlabelFrom.setBounds(left, top, LABEL_WIDTH_SMALL, LABEL_HEIGHT);
		mlabelFrom.setBorder(new EmptyBorder(1, 1, 1, 1));
		mlabelFrom.setBackground(Color.LIGHT_GRAY);
		mlabelFrom.setOpaque(true);
		mPanelDateSelection.add(mlabelFrom);

		left = mlabelFrom.getX() + mlabelFrom.getWidth() + PANEL_CONTENT_H_GAP/2;

		mTxtDayFrom = new PosTouchableDigitalField(this, TEXT_FIELD_WIDTH_DAY);
		mTxtDayFrom.setTitle("Day");
		mTxtDayFrom.hideResetButton(true);
		mTxtDayFrom.setLocation(left, top);
		mTxtDayFrom.getTexFieldComponent().setDocument(
				PosNumberUtil.createDecimalDocument());
		mTxtDayFrom.setListner(new IPosTouchableFieldListner() {
			
			@Override
			public void onValueSelected(Object value) {

				mTxtMonthFrom.requestFocus();
			}
			
			@Override
			public void onReset() {
				
			}
		});
		mPanelDateSelection.add(mTxtDayFrom);

		left = mTxtDayFrom.getX() + mTxtDayFrom.getWidth();

		mTxtMonthFrom = new PosTouchableDigitalField(this,
				TEXT_FIELD_WIDTH_MONTH);
		mTxtMonthFrom.setTitle("Month");
		mTxtMonthFrom.setLocation(left, top);
		mTxtMonthFrom.hideResetButton(true);
		mTxtMonthFrom.getTexFieldComponent().setDocument(
				PosNumberUtil.createDecimalDocument());
		mTxtMonthFrom.setListner(new IPosTouchableFieldListner() {
			
			@Override
			public void onValueSelected(Object value) {

				mTxtYearFrom.requestFocus();
			}
			
			@Override
			public void onReset() {
				
			}
		});
		mPanelDateSelection.add(mTxtMonthFrom);

		left = mTxtMonthFrom.getX() + mTxtMonthFrom.getWidth();

		mTxtYearFrom = new PosTouchableDigitalField(this, TEXT_FIELD_WIDTH_YEAR);
		mTxtYearFrom.setTitle("Year");
		mTxtYearFrom.setLocation(left, top);
		mTxtYearFrom.getTexFieldComponent().setDocument(
				PosNumberUtil.createDecimalDocument());
		mTxtYearFrom.setListner(new IPosTouchableFieldListner() {

			@Override
			public void onValueSelected(Object value) {
				// TODO A0.00uto-generated method stub
				mTxtDayTo.requestFocus();
			}

			@Override
			public void onReset() {
				mTxtDayFrom.reset();
				mTxtMonthFrom.reset();
			}
		});
		mPanelDateSelection.add(mTxtYearFrom);
		
		left = mTxtYearFrom.getX() + mTxtYearFrom.getWidth()+PANEL_CONTENT_H_GAP/2;
		final int statusFieldWidth=mPanelDateSelection.getWidth()-left-PANEL_CONTENT_H_GAP-1;
		JLabel labelEmpty = new JLabel();
		labelEmpty.setText("");
		labelEmpty.setFont(PosFormUtil.getLabelFont());
		labelEmpty.setHorizontalAlignment(SwingConstants.CENTER);
		labelEmpty.setBounds(left, PANEL_CONTENT_V_GAP,  statusFieldWidth, LABEL_HEIGHT_SMALL);
		labelEmpty.setBorder(LABEL_PADDING);
		labelEmpty.setBackground(Color.LIGHT_GRAY);
		labelEmpty.setOpaque(true);
		mPanelDateSelection.add(labelEmpty);

		left = mlabelFrom.getX();
		top = mlabelFrom.getY() + mlabelFrom.getHeight() + PANEL_CONTENT_V_GAP;

		mlabelTo = new JLabel();
		mlabelTo.setText("To :");
		mlabelTo.setFont(PosFormUtil.getLabelFont());
		mlabelTo.setHorizontalAlignment(SwingConstants.RIGHT);
		mlabelTo.setBounds(left, top, LABEL_WIDTH_SMALL, LABEL_HEIGHT);
		mlabelTo.setBorder(LABEL_PADDING);
		mlabelTo.setBackground(Color.LIGHT_GRAY);
		mlabelTo.setOpaque(true);
		mPanelDateSelection.add(mlabelTo);

		left = mlabelTo.getX() + mlabelTo.getWidth() + PANEL_CONTENT_H_GAP/2;

		mTxtDayTo = new PosTouchableDigitalField(this, TEXT_FIELD_WIDTH_DAY);
		mTxtDayTo.setTitle("Day");
		mTxtDayTo.hideResetButton(true);
		mTxtDayTo.setLocation(left, top);
		mTxtDayTo.getTexFieldComponent().setDocument(
				PosNumberUtil.createDecimalDocument());
		mTxtDayTo.setListner(new IPosTouchableFieldListner() {
			
			@Override
			public void onValueSelected(Object value) {

				mTxtMonthTo.requestFocus();
			}
			
			@Override
			public void onReset() {
				// TODO Auto-generated method stub
				
			}
		});
		mPanelDateSelection.add(mTxtDayTo);

		left = mTxtDayTo.getX() + mTxtDayTo.getWidth();

		mTxtMonthTo = new PosTouchableDigitalField(this, TEXT_FIELD_WIDTH_MONTH);
		mTxtMonthTo.setTitle("Month");
		mTxtMonthTo.setLocation(left, top);
		mTxtMonthTo.hideResetButton(true);
		mTxtMonthTo.getTexFieldComponent().setDocument(
				PosNumberUtil.createDecimalDocument());
		mTxtMonthTo.setListner(new IPosTouchableFieldListner() {
			
			@Override
			public void onValueSelected(Object value) {

				mTxtYearTo.requestFocus();
			}
			
			@Override
			public void onReset() {
				// TODO Auto-generated method stub
				
			}
		});
		mPanelDateSelection.add(mTxtMonthTo);

		left = mTxtMonthTo.getX() + mTxtMonthTo.getWidth();

		mTxtYearTo = new PosTouchableDigitalField(this, TEXT_FIELD_WIDTH_YEAR);
		mTxtYearTo.setTitle("Year");
		mTxtYearTo.setLocation(left, top);
		mTxtYearTo.getTexFieldComponent().setDocument(
				PosNumberUtil.createDecimalDocument());
		mTxtYearTo.setListner(new IPosTouchableFieldListner() {

			@Override
			public void onValueSelected(Object value) {

				mTxtInvQueNo.requestFocus();
			}

			@Override
			public void onReset() {
				mTxtDayTo.reset();
				mTxtMonthTo.reset();
			}
		});
		mPanelDateSelection.add(mTxtYearTo);
		
		 	}
	
	private void createCriteriaControl()
	{
		createOrderDateSelectionControl();
		
		int left = mTxtYearFrom.getX() + mTxtYearFrom.getWidth()+PANEL_CONTENT_H_GAP/2 ;
		int top = mTxtYearFrom.getY();

		JLabel labelStatus = new JLabel();
		labelStatus.setText(PosFormUtil.getMnemonicString("Status", 'S'));
		labelStatus.setFont(PosFormUtil.getLabelFont());
		labelStatus.setHorizontalAlignment(SwingConstants.RIGHT);
		labelStatus.setBounds(left, top, LABEL_WIDTH -12, LABEL_HEIGHT+2);
		labelStatus.setBorder(new EmptyBorder(1, 1, 1, 1));
		labelStatus.setBackground(Color.LIGHT_GRAY);
		labelStatus.setOpaque(true);
		mPanelDateSelection.add(labelStatus);
	
		mOrderStatus=new PosItemBrowsableField(this,TEXT_WIDTH);
		mOrderStatus.setMnemonic('S');
		mOrderStatus.setBrowseItemList(PosOrderStatus.values());
		mOrderStatus.setTitle("Order Status");
		mOrderStatus.setBrowseWindowSize(3, 3);
		mOrderStatus.setLocation(left + labelStatus.getWidth(), top);
		mOrderStatus.setSelectedItem(PosEnvSettings.getInstance().getUISetting().getOrderListSettings().getDefaultOrderStatus());
		mOrderStatus.setFont(PosFormUtil.getTextFieldFont());	
		mOrderStatus.setListner(new IPosTouchableFieldListner() {
			
			@Override
			public void onValueSelected(Object value) {

				mTxtInvQueNo.requestFocus();
			}
			
			@Override
			public void onReset() {
				
				mOrderStatus.setSelectedItem(PosOrderStatus.Any);
				mRetrieveButtonListner.onClicked(null);
			}
		});
		mPanelDateSelection.add(mOrderStatus);
		
		top = mTxtYearTo.getY();
		
		JLabel labelInvoiceQueueNo = new JLabel();
		labelInvoiceQueueNo.setText(PosFormUtil.getMnemonicString("Filter:", 'N'));
		labelInvoiceQueueNo.setFont(PosFormUtil.getLabelFont());
		labelInvoiceQueueNo.setHorizontalAlignment(SwingConstants.RIGHT);
		labelInvoiceQueueNo.setBounds(left, top, LABEL_WIDTH -12, LABEL_HEIGHT+2);
		labelInvoiceQueueNo.setBorder(new EmptyBorder(1, 1, 1, 1));
		labelInvoiceQueueNo.setBackground(Color.LIGHT_GRAY);
		labelInvoiceQueueNo.setOpaque(true);
		mPanelDateSelection.add(labelInvoiceQueueNo);
		
		mTxtInvQueNo =new PosTouchableTextField(this,TEXT_WIDTH , LABEL_HEIGHT+2);
		mTxtInvQueNo.setMnemonic('N');
		mTxtInvQueNo.setTitle("Queue/Invoice Number, Barcode");
		mTxtInvQueNo.setLocation(mOrderStatus.getX(), top);
		mTxtInvQueNo.setFont(PosFormUtil.getTextFieldFont());
		mTxtInvQueNo.setListner(new IPosTouchableFieldListner() {
			
			@Override
			public void onValueSelected(Object value) {

			//	mRetrieveButtonListner.onClicked(null);
				orderStatusRptListPanel.filterTable(mTxtInvQueNo.getText());
			}
			
			@Override
			public void onReset() {
				
				mRetrieveButtonListner.onClicked(null);
				mTxtInvQueNo.requestFocus();
				
			}
		});
		mPanelDateSelection.add(mTxtInvQueNo);
		
	}
	


	 	/**
	 * @param width
	 * @param height
	 * @return
	 */
	private JPanel createBoxedPanel(int width, int height) {

		JPanel boxPanel = new JPanel();
		Dimension size = new Dimension(width, height);
		boxPanel.setBackground(Color.white);
		boxPanel.setPreferredSize(size);
		boxPanel.setSize(size);
		boxPanel.setBorder(new LineBorder(Color.LIGHT_GRAY));
		boxPanel.setLayout(null);

		return boxPanel;
	}
	
	/**
	 * 
	 */
	private void setDateHeader() {

		int left = PANEL_CONTENT_H_GAP   ;
		int top = PANEL_CONTENT_V_GAP   ;

		mlabelDay = new JLabel();
		mlabelDay.setText("Day(DD)");
		mlabelDay.setFont(PosFormUtil.getLabelFont());
		mlabelDay.setHorizontalAlignment(SwingConstants.RIGHT);
		mlabelDay.setBounds(left, top, TEXT_FIELD_WIDTH_DAY+60, LABEL_HEIGHT_SMALL);
		mlabelDay.setBackground(Color.LIGHT_GRAY);
		mlabelDay.setOpaque(true);
		mlabelDay.setBorder(LABEL_PADDING);
		mPanelDateSelection.add(mlabelDay);

		left = mlabelDay.getX() + mlabelDay.getWidth() + PANEL_CONTENT_H_GAP/2;

		mlabelMonth = new JLabel();
		mlabelMonth.setText("Month(MM)");
		mlabelMonth.setFont(PosFormUtil.getLabelFont());
		mlabelMonth.setHorizontalAlignment(SwingConstants.CENTER);
		mlabelMonth.setBounds(left, top, TEXT_FIELD_WIDTH_MONTH, LABEL_HEIGHT_SMALL);
		mlabelMonth.setBackground(Color.LIGHT_GRAY);
		mlabelMonth.setOpaque(true);
		mlabelMonth.setBorder(LABEL_PADDING);
		mPanelDateSelection.add(mlabelMonth);

		left = mlabelMonth.getX() + mlabelMonth.getWidth()
				+ PANEL_CONTENT_H_GAP/2;

		mlabelYear = new JLabel();
		mlabelYear.setText("Year(YYYY)");
		mlabelYear.setFont(PosFormUtil.getLabelFont());
		mlabelYear.setHorizontalAlignment(SwingConstants.CENTER);
		mlabelYear.setBounds(left, top, TEXT_FIELD_WIDTH_YEAR-1,
				LABEL_HEIGHT_SMALL);
		mlabelYear.setBackground(Color.LIGHT_GRAY);
		mlabelYear.setOpaque(true);
		mlabelYear.setBorder(LABEL_PADDING);
		mPanelDateSelection.add(mlabelYear);
	}
	/**
	 * @param orderDateFrom
	 * @param orderDateTo
	 * @return
	 * @throws Exception
	 */
	private boolean validateSearchCriteria(String orderDateFrom, String orderDateTo) throws Exception{
		
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
		
		if(errorControl!=null){
			errorControl.requestFocus();
			throw new Exception(errMessage);
		}
		
		return true;
	}
	
	/**
	 * 
	 */
	
	private void populateOrderStatusReport()
	{
		
		String orderDateFrom;
		String orderDateTo;
		String where="";

		PosOrderStatus selectedOrderStatus=(PosOrderStatus)mOrderStatus.getSelectedValue();
		
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
				
				try {
					if (validateSearchCriteria(orderDateFrom, orderDateTo)) {
						
					 
							switch (selectedOrderStatus) {
							case Any:
								where=  " (status in (1, 6) AND order_date<='"+ orderDateTo + "'"   +  ") OR " +
									"(status in (3,4) AND  closing_date between '" 	+ orderDateFrom + "' and '" + orderDateTo + "'"  + ") OR "  +
									" (status =5 AND  closing_date between '" + orderDateFrom + "' and '" + orderDateTo + "'"  + ") ";
								break;
							case Closed:
							case Refunded:
								where= " (closing_date between '"
										+ orderDateFrom + "' and '" + orderDateTo + "') AND " +
										" STATUS ="+ selectedOrderStatus.getCode() ;
								break;
							case Void:
								where= " (closing_date between '"
										+ orderDateFrom + "' and '" + orderDateTo + "') AND " +
										" STATUS ="+selectedOrderStatus.getCode();
								break;
							case Open:
							case Partial:
								where=" status=" + selectedOrderStatus.getCode() + " AND " +
										" order_date<='"+ orderDateTo + "' ";
										break;
							default:
								break;
							}
//						}
						
						showOrderListSearch(where);
					}
				} catch (Exception e) {

					PosLog.write(this, "mRetrieveButtonListner.onclick", e);
					PosFormUtil.showErrorMessageBox(PosOrderListQueryForm.this, e.getMessage());
				}
	}
	
//	
	/**
	 * 
	 */
	private IPosButtonListner mRetrieveButtonListner = new IPosButtonListner() {
		@Override
		public void onClicked(PosButton button) {

			mTxtInvQueNo.setText("");
			populateOrderStatusReport();
		}
	};

	/**
	 * 
	 */
	private IPosButtonListner mResetButtonListner = new IPosButtonListner() {
		@Override
		public void onClicked(PosButton button) {

			setDefaults();
			mOrderStatus.setSelectedItem(PosOrderStatus.Any);
			mTxtInvQueNo.setText("");
			populateOrderStatusReport();
			mTxtInvQueNo.requestFocus();
			
		}
	};
	/**
	 * 
	 */
	private IPosButtonListner mPrintButtonListner = new IPosButtonListner() {
		@Override
		public void  onClicked(PosButton button) {
			
		 PosPrintingUtil.showPrintOptions(PosOrderListQueryForm.this, billPrintListner, printButtonList);
		}
	};
	
	 
	/**
	 * 
	 */
	private void printList(){
		
		ArrayList<BeanOrderStatusReport> orderHdrSelectedList= orderStatusRptListPanel.getSelectedItemList();
		String orderId;
		if(orderHdrSelectedList==null || orderHdrSelectedList.size()==0) {
			orderId= orderStatusRptListPanel.getSelectedOrderId();
			if (orderId.trim()==""){
				PosFormUtil.showInformationMessageBox(this, "No order(s) selected. Please select order(s) to print.");
				return ;
			}
		
		}
			
			
		
			
		if(orderHdrSelectedList==null || orderHdrSelectedList.size()==0) {
			
			orderId= orderStatusRptListPanel.getSelectedOrderId();
			if (orderId.trim()!=""){
				
				printOrder(orderId);
				return;
			} 
		}
		 
		for(BeanOrderStatusReport orderStatusRpt : orderHdrSelectedList )
		{
			 
			if(!printOrder(orderStatusRpt.getOrderId()))
				return;
		}
	}
	
	/*
	 * 
	 */
	private boolean printOrder(String orderId) {
		
		try{
			
			final PosOrderHdrProvider orderHeaderProvider = new PosOrderHdrProvider();
			final BeanOrderHeader orderHeader = orderHeaderProvider.getOrderData(orderId);
			
			if(orderHeader==null)
				throw new NoSuchOrderException("No such order. Please check order number.");
			
			final boolean forceToPrint=(PosEnvSettings.getInstance().getPrintSettings().getReceiptPrintingAtPayment()!=EnablePrintingOption.ASK);
			
			if (orderHeader.getStatus().equals(PosOrderStatus.Refunded) || orderHeader.getStatus().equals(PosOrderStatus.Closed)) {
//				PosReceipts.printReceipt(PosOrderListQueryForm.this, orderHeader, false, null, false, useAltLang,true);
				PosFormUtil.showPrintConfirmMessage(PosOrderListQueryForm.this, forceToPrint, orderHeader, false, null, false);
			}else{
//				PosReceipts.printReceipt(PosOrderListQueryForm.this,orderHeader, false, null,true, useAltLang,true);
				PosFormUtil.showPrintConfirmMessage(PosOrderListQueryForm.this, forceToPrint, orderHeader, true, null, true);
			}
			
			if (orderHeader.getStatus().equals(PosOrderStatus.Refunded)){

				boolean useAltLangToPrint=false;
				if(PosDeviceManager.getInstance().hasReceiptPrinter()){

					if(PosDeviceManager.getInstance().getReceiptPrinter()!=null ){

						if(PosDeviceManager.getInstance().getReceiptPrinter().isUseAltLanguage()){
							final MessageBoxResults res= PosFormUtil.showQuestionMessageBox(this,
									 MessageBoxButtonTypes.YesNo ,
									"Do you want to print receipt using alternatieve language?",null);

								switch(res){
			
								case Yes:
									useAltLangToPrint=true;
									break;
								 	case No:
									useAltLangToPrint=false;
									break;
								default:
									break;
			
									}
							}
					}
					
				}
				
				PosOrderUtil.printRefundReceipt(this, orderHeader,useAltLangToPrint);
			}
			
		} catch (PrinterException e) {
			
			PosFormUtil.showErrorMessageBox(this,e.getMessage());
			return false;
		} catch (Exception e) {
			
			PosLog.write(this, "Print ", e);
			PosFormUtil.showErrorMessageBox(this,
					"Failed to print. Please check the log for details");
			return false;
		}
		return true;
	}
 

	/*
	 * 
	 */
	private boolean validatePrint( ArrayList<BeanOrderStatusReport> orderHdrSelectedList,PosPrintOption printOption){
		
	 	if(orderHdrSelectedList==null || orderHdrSelectedList.size()==0) {
			
			String orderId= orderStatusRptListPanel.getSelectedOrderId();
			
			if (orderId.trim()==""){
			
				PosFormUtil.showInformationMessageBox(this, "No order(s) selected. Please select order(s) to print.");
				return false;
			}else {
				if(printOption==PosPrintOption.RESHITO){
					if (!(orderStatusRptListPanel.getSelectedOrderStatus().equals(PosOrderStatus.Refunded)  ||
							  orderStatusRptListPanel.getSelectedOrderStatus().equals(PosOrderStatus.Closed))){
						
						PosFormUtil.showErrorMessageBox(this, "There is no payment done for this order. Reshito can be printed only for  closed orders.");
						return false;
					}
				}
			}
			if(printOption==PosPrintOption.ITEMLABEL  && orderStatusRptListPanel.getSelectedOrder().getOrderServiceType()!=PosOrderServiceTypes.SALES_ORDER) {
				
				PosFormUtil.showErrorMessageBox(this, "This feature is available for only sales order service.");
				return false;
			}
		}else if(printOption==PosPrintOption.RESHITO &&  orderHdrSelectedList.size()==1   ) {
		
			final BeanOrderStatusReport orderStatusReport=orderHdrSelectedList.get(0);
			
			if (!(orderStatusReport.getStatus().equals(PosOrderStatus.Refunded)  ||
					orderStatusReport.getStatus().equals(PosOrderStatus.Closed))){
				
				PosFormUtil.showErrorMessageBox(this, "There is no payment done for this order. Reshito can be printed only for  closed orders.");
				return false;
			}
		}else if((printOption==PosPrintOption.ITEMLABEL || 
				printOption==PosPrintOption.BARCODELABEL ||
				printOption==PosPrintOption.KITCHENRECEIPT)  &&  orderHdrSelectedList.size()>1   ) {
			
			PosFormUtil.showErrorMessageBox(this, "Only one order can be print at a time.");
			return false;
		}else if(printOption==PosPrintOption.ITEMLABEL &&  orderHdrSelectedList.size()==1   ) {
		
			final BeanOrderStatusReport orderStatusReport=orderHdrSelectedList.get(0);
			
			if(printOption==PosPrintOption.ITEMLABEL  && orderStatusReport.getOrderServiceType()!=PosOrderServiceTypes.SALES_ORDER) {
				
				PosFormUtil.showErrorMessageBox(this, "This feature is available for only sales order service.");
				return false;
			}
			
		}
		return true;
		
	}
	
	/**
	 * @param orderHeader
	 */
	private void printBillReshito( ) {
		
		ArrayList<BeanOrderStatusReport> orderHdrSelectedList= orderStatusRptListPanel.getSelectedItemList();
		
		if (!validatePrint(orderHdrSelectedList,PosPrintOption.RESHITO))
			return ;
			
		if(orderHdrSelectedList==null || orderHdrSelectedList.size()==0) {
			
			final String orderId= orderStatusRptListPanel.getSelectedOrderId();
	 		printBillReshito(orderId);
			return;
		} 
		 
		for(BeanOrderStatusReport orderStatusRpt : orderHdrSelectedList )
		{
			 
			if(!printBillReshito(orderStatusRpt.getOrderId()))
				return;
		}
	}
 
	/*
	 * 
	 */
	private boolean printBillReshito(String orderId) {
		
		try{
			
			BeanOrderHeader orderHdr=new PosOrderHdrProvider().getOrderHeader(orderId);
			if (orderHdr.getStatus().equals(PosOrderStatus.Refunded) || orderHdr.getStatus().equals(PosOrderStatus.Closed)){
				
				PosReceipts.printBillReshito(orderHdr,false);
			}

		} catch (PrinterException e) {
			
			PosFormUtil.showErrorMessageBox(this,e.getMessage());
			return false;
		} catch (Exception e) {
			
			PosLog.write(this, "Print Reshito", e);
			PosFormUtil.showErrorMessageBox(this, "Failed to print. Please check the log for details");
			return false;
		}
		return true;
	}
	/**
	 * 
	 */
	private IPosButtonListner mRefundButtonListner=new IPosButtonListner() {
		
		@Override
		public void onClicked(PosButton button) {
			
			String orderId= orderStatusRptListPanel.getSelectedOrderId();
			if(orderId==null || orderId.trim()==""){
				
				PosFormUtil.showErrorMessageBox(PosOrderListQueryForm.this, "No order is selected. Please select an order.");
			}
			if (orderStatusRptListPanel.getSelectedOrderStatus().equals(PosOrderStatus.Closed) ||
				orderStatusRptListPanel.getSelectedOrderStatus().equals(PosOrderStatus.Refunded)  ){

				BeanUser user=getActiveUser();
				if(!PosAccessPermissionsUtil.validateAccess(PosOrderListQueryForm.this,user.getUserGroupId(),"order_refund"))
					user=PosAccessPermissionsUtil.validateAccess(PosOrderListQueryForm.this,false, "order_refund");
				
				if(user!=null){
					
					final PosOrderRefundEditForm form = new PosOrderRefundEditForm(orderId);
					form.setActiveUser(user);
					form.setListner(refundListener);
					PosFormUtil.showLightBoxModal(PosOrderListQueryForm.this,form);
				}
				
			}else
				PosFormUtil.showErrorMessageBox(PosOrderListQueryForm.this, "Only paid order can be refunded. ");
		}
	};
	/*
	 * 
	 */
	private IPosOrderRefundFormListner refundListener=new IPosOrderRefundFormListner() {
		
		@Override
		public void onRefundDone(Object sender, BeanOrderHeader item) {

			orderStatusRptListPanel.getSelectedOrder().setStatus(PosOrderStatus.Refunded);
//			orderStatusRptListPanel.setSelectedOrderStatus(PosOrderStatus.Refunded);
//			orderStatusRptListPanel.SetOrderHdrList(mOrderHeaderList );
			orderStatusRptListPanel.refresh();
		}
	};
	
	/**
	 * 
	 */
	private IPosButtonListner mSelectButtonListner = new IPosButtonListner() {
		@Override
		public void onClicked(PosButton button) {
			
			if (button.getText()=="Select All"){
				orderStatusRptListPanel.setListSelected(true);
				button.setText("Deselect All");
			}
			else{
				orderStatusRptListPanel.setListSelected(false);
				button.setText("Select All");
			}
			}
					 
	};
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indocosmo.pos.forms.PosBaseForm#onOkButtonClicked()
	 */
	@Override
	public boolean onOkButtonClicked() {
		
		String orderId= orderStatusRptListPanel.getSelectedOrderId();
		if (orderId.trim()!=""){
			PosOrderInfoDetForm orderDetForm = new PosOrderInfoDetForm(orderId);
			PosFormUtil.showLightBoxModal(PosOrderListQueryForm.this, orderDetForm);
		}
		return false;
	}

	 
	/**
	 * @param wherefalse
	 */
	private void showOrderListSearch(String where) {
		
		try {
			mOrderHeaderList = null;
			mOrderHeaderList = mOrderStatusProvider.getOrderHeaders(where);
			
			orderStatusRptListPanel.SetOrderHdrList(mOrderHeaderList );
			mContianerPanel.add(orderStatusRptListPanel);
			if (mOrderHeaderList.size() ==0){
				mButtonSelect.setEnabled(false);
				setOkEnabled(false);
				mButtonRefund.setEnabled(false);
				mButtonPrint.setEnabled(false);
			}
			else{
				orderStatusRptListPanel.setRowSelected(true,0);
				mButtonSelect.setEnabled(true);
				setOkEnabled(true);
				mButtonRefund.setEnabled(true);
				mButtonPrint.setEnabled(true);
				mButtonSelect.setText("Deselect All");
				orderStatusRptListPanel.requestFocus();
				
			}
 
		} catch (Exception e) {
			
			PosLog.write(this, "showOrderListSearch", e);
			PosFormUtil.showErrorMessageBox(this,
					"Failed get order list. Please check the log for details");
		}

	}
	
	/*
	 * 
	 */
	private IPosBillPrintFormListner billPrintListner =new PosBillPrintFormAdapter(){
		
		@Override
		public void onRePrintClicked(Object sender) {
			printList();
		}
		
		@Override
		public void onRePrintKitchenReceiptClicked(Object sender){
			printKitchenReceipt();
			 
		}
		
		@Override
		public void onRePrintBarcodeClicked(Object sender) {
			printBarcode();
		}
		
		@Override
		public void onRePrintReshitoClicked(Object sender) {
			printBillReshito();
		}
		
		public void onRePrintItemLabelClicked(Object sender) {
			printItemLabel();
		}
		
	};
 
	 
	/**
	 * print selected list 
	 */
	private void printKitchenReceipt() {
		
		ArrayList<BeanOrderStatusReport> orderHdrSelectedList= orderStatusRptListPanel.getSelectedItemList();
		
		if (!validatePrint(orderHdrSelectedList,PosPrintOption.KITCHENRECEIPT))
			return ;
			
		if(orderHdrSelectedList==null || orderHdrSelectedList.size()==0) {
			
			final String orderId= orderStatusRptListPanel.getSelectedOrderId();
			printKitchenReceipt(orderId);
			return;
		} 
		 
		for(BeanOrderStatusReport orderStatusRpt : orderHdrSelectedList )
		{
			 
			if(!printKitchenReceipt(orderStatusRpt.getOrderId()))
				return;
		}
	}
 
	/*
	 * 
	 */
	private boolean printKitchenReceipt(String orderId) {
		
		
		try {
				BeanOrderHeader orderHeader=new PosOrderHdrProvider().getOrderData(orderId);
					
				if( orderHeader.getOrderDetailItems()==null || orderHeader.getOrderDetailItems().size()==0)
					return false;

				if (!PosOrderUtil.hasPrintableItems(orderHeader,  true)){
					
					PosFormUtil.showInformationMessageBox(this, "No Items to print." );
					return false;
				}
				try {
	
					if (orderHeader.getStatus()==PosOrderStatus.Open || orderHeader.getStatus()==PosOrderStatus.Partial){
						
						final MessageBoxResults msgBoxResult=PosFormUtil.showQuestionMessageBox(this, MessageBoxButtonTypes.YesNoCancel,"Print new/modified item[s] only?", null);
						if(msgBoxResult!=MessageBoxResults.Cancel){
							
							if(msgBoxResult==MessageBoxResults.Yes && !PosOrderUtil.hasNotPrintedToKitchenItems(orderHeader)){
								
								PosFormUtil.showInformationMessageBox(this, "Already printed to kitchen(s)." );
							
							}else{
								
								final PosOrderHdrProvider pvdr=new PosOrderHdrProvider();
								PosReceipts.printReceiptToKitchen(orderHeader, msgBoxResult==MessageBoxResults.No);
								PosOrderUtil.setAsPrintedToKitchen(orderHeader);
								pvdr.saveOrder(orderHeader);
	
							 
							}
						}
					}else
						PosReceipts.printReceiptToKitchen(orderHeader, true);
						
				} catch (Exception e) {
					PosLog.write(this, "printKitchen Recipt", e);
					PosFormUtil.showErrorMessageBox(this, "Error in printing!!"); 
					return false;
				}
					
		} catch (Exception e) {
			PosLog.write(this, "Kitchen Print", e);
			PosFormUtil.showErrorMessageBox(this,"Failed to print. Please contact administrator.");
			return false;
		}
		return true;
	}
	
	/**
	 * print selected list 
	 */
	private void printBarcode() {
		
		ArrayList<BeanOrderStatusReport> orderHdrSelectedList= orderStatusRptListPanel.getSelectedItemList();
		
		if (!validatePrint(orderHdrSelectedList,PosPrintOption.BARCODELABEL))
			return ;
			
		if(orderHdrSelectedList==null || orderHdrSelectedList.size()==0) {
			
			final String orderId= orderStatusRptListPanel.getSelectedOrderId();
			printBarcode(orderId);
			return;
		} 
		 
		for(BeanOrderStatusReport orderStatusRpt : orderHdrSelectedList )
		{
			if(!printBarcode(orderStatusRpt.getOrderId()))
				return;
		}
	}
 
	/*
	 * 
	 */
	private boolean printBarcode(String orderId) {
		
		try{
			
			BeanOrderHeader orderHdr=new PosOrderHdrProvider().getOrderData(orderId);
			final BarCodeLabelPrint bcLabelPrint=new BarCodeLabelPrint();
			bcLabelPrint.print(orderHdr);

		 } catch (Exception e) {
				PosLog.write(this, "Print Barcode", e);
				PosFormUtil.showErrorMessageBox(this,"Failed to print. Please contact administrator.");
				return false;
		}
		return true;
	}
	
	
	
	/**
	 * print selected list 
	 */
	private void printItemLabel() {
		
		ArrayList<BeanOrderStatusReport> orderHdrSelectedList= orderStatusRptListPanel.getSelectedItemList();
		
		if (!validatePrint(orderHdrSelectedList,PosPrintOption.ITEMLABEL))
			return ;
			
		if(orderHdrSelectedList==null || orderHdrSelectedList.size()==0) {
			
			final String orderId= orderStatusRptListPanel.getSelectedOrderId();
			printItemLabel(orderId);
			return;
		} 
		 
		for(BeanOrderStatusReport orderStatusRpt : orderHdrSelectedList )
		{
			if(!printItemLabel(orderStatusRpt.getOrderId()))
				return;
		}
	}
 
	/*
	 * 
	 */
	private boolean printItemLabel(String orderId) {
		
		try{
			
			BeanOrderHeader orderHdr=new PosOrderHdrProvider().getOrderData(orderId);
			PosReceipts.printItemLabels(orderHdr);

		 } catch (Exception e) {
				PosLog.write(this, "Print Item Label", e);
				PosFormUtil.showErrorMessageBox(this,"Failed to print. Please contact administrator.");
				return false;
		}
		return true;
	}
}
