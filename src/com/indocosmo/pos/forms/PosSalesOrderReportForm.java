/**
 * 
 */
package com.indocosmo.pos.forms;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.indocosmo.pos.common.PosEnvSettings;
import com.indocosmo.pos.common.enums.PosOrderServiceTypes;
import com.indocosmo.pos.common.enums.PosReportsType;
import com.indocosmo.pos.common.utilities.PosDateUtil;
import com.indocosmo.pos.common.utilities.PosFormUtil;
import com.indocosmo.pos.common.utilities.PosNumberUtil;
import com.indocosmo.pos.common.utilities.PosResUtil;
import com.indocosmo.pos.forms.components.checkboxes.PosCheckBox;
import com.indocosmo.pos.forms.components.objectbrowser.IPosBrowsableItem;
import com.indocosmo.pos.forms.components.radiobuttons.PosRadioButton;
import com.indocosmo.pos.forms.components.textfields.PosTouchableDigitalField;
import com.indocosmo.pos.forms.components.textfields.listners.IPosTouchableFieldListner;
import com.indocosmo.pos.reports.base.PosPrintableReportBase;
import com.indocosmo.pos.reports.salesorder.PosSalesOrderAdvanceReport;
import com.indocosmo.pos.reports.salesorder.PosSalesOrderBalanceReport;
import com.indocosmo.pos.reports.salesorder.PosSalesOrderItemWiseDetReport;
import com.indocosmo.pos.reports.salesorder.PosSalesOrderItemWiseReport;
import com.indocosmo.pos.reports.salesorder.PosSalesOrderReportBase;
import com.indocosmo.pos.terminal.devices.PosDeviceManager;
import com.indocosmo.pos.terminal.devices.printer.PosDeviceReceiptPrinter;
import com.indocosmo.pos.terminal.devices.printer.printerexceptions.PrinterException;

/**
 * @author sandhya
 *
 */
@SuppressWarnings("serial")
public class PosSalesOrderReportForm extends PosBaseForm{

	private static final int PANEL_CONTENT_H_GAP =2;
	private static final int PANEL_CONTENT_V_GAP = 2;
	private static final int LABEL_HEIGHT = 40;
	private static final int LABEL_HEIGHT_SMALL = 30;
	private static final Border LABEL_PADDING = new EmptyBorder(2, 2, 2, 2);
 
	private static final String CHK_BUTTON_NORMAL="checkbox_normal.png";
	private static final String CHK_BUTTON_SELECTED="checkbox_selected.png";
	private static final int CHECKBOX_SMALL_WIDTH = 160;
	private static final int CHECKBOX_HEIGHT = 40;
	
	private static final int TEXT_FIELD_WIDTH_DAY = 120;
	private static final int TEXT_FIELD_WIDTH_MONTH = 120;
	private static final int TEXT_FIELD_WIDTH_YEAR = 190;
	private static final int LABEL_WIDTH = 80;
	private static final int CRITERIA_PANEL_HEIGHT = LABEL_HEIGHT  + PANEL_CONTENT_V_GAP * 3 ;
	private static final int DATEPANEL_HEIGHT = LABEL_HEIGHT + LABEL_HEIGHT_SMALL   + PANEL_CONTENT_V_GAP *6;
	private static  int PANEL_HEIGHT = DATEPANEL_HEIGHT + PANEL_CONTENT_V_GAP * 9 + 
			((PosOrderServiceTypes.HOME_DELIVERY.isVisibleInUI() && PosOrderServiceTypes.HOME_DELIVERY.isVisibleInUI())?CRITERIA_PANEL_HEIGHT:0);
	private static final int PANEL_WIDTH = LABEL_WIDTH + TEXT_FIELD_WIDTH_DAY + TEXT_FIELD_WIDTH_MONTH + TEXT_FIELD_WIDTH_YEAR + PANEL_CONTENT_H_GAP * 10;
	private JPanel mContentPanel;
	private JPanel mPanelDateSelection;
	private JLabel mlabelDate;
	private JLabel mlabelDay;
	private JLabel mlabelMonth;
	private JLabel mlabelYear;

	private PosTouchableDigitalField mTxtDay;
	private PosTouchableDigitalField mTxtMonth;
	private PosTouchableDigitalField mTxtYear;
	
	private PosRadioButton mBalancePendingSelection;
	private PosRadioButton mBalanceCollectedSelection;
	
	private PosCheckBox mCheckHomeDelivery;
	private PosCheckBox mCheckSalesOrder;
	
	PosReportsType reportType;
	 
	/**
	 * 
	 */
	public PosSalesOrderReportForm(PosReportsType reportType) {
		
		super("Sales Order Report",PANEL_WIDTH,  reportType==PosReportsType.DAILYBALANCE? (PANEL_HEIGHT + CRITERIA_PANEL_HEIGHT + PANEL_CONTENT_V_GAP):PANEL_HEIGHT);
		
		this.reportType = reportType;
		setTitle(reportType.getDisplayText());
		createUI();
	}
	/**
	 * 
	 */
	private void createUI() {

		createDateSelectionControl();
		createServiceType();
		createCriteriaControls();
		setOkButtonCaption("Print");
		mButtonOk.setMnemonic('P');
		setCancelButtonCaption("Close");
		 
		setDefaultDate();
	}
   
	/**
	 * 
	 */
	private void createCriteriaControls() {

		int width = PANEL_WIDTH - PANEL_CONTENT_H_GAP * 7;
		
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(width, CRITERIA_PANEL_HEIGHT-1));
		panel.setBorder(new LineBorder(Color.LIGHT_GRAY));
		panel.setLayout(null);
		if(reportType==PosReportsType.DAILYBALANCE)
			mContentPanel.add(panel);
		
		
		int left =PANEL_CONTENT_H_GAP+1;
		int top =PANEL_CONTENT_V_GAP;

		JLabel label = new JLabel();
		label.setText("Report :");
		label.setFont(PosFormUtil.getLabelFont());
		label.setHorizontalAlignment(SwingConstants.LEFT);
		label.setBounds(left, top, LABEL_WIDTH, LABEL_HEIGHT);
		label.setBorder(new EmptyBorder(1, 1, 1, 1));
		label.setBackground(Color.LIGHT_GRAY);
		label.setOpaque(true);
		panel.add(label);

		
		width=width-LABEL_WIDTH-PANEL_CONTENT_H_GAP*2;
		JPanel balanceTypePanel = new JPanel();
		balanceTypePanel.setBounds(left + LABEL_WIDTH , top,width, CRITERIA_PANEL_HEIGHT-PANEL_CONTENT_V_GAP*2);
		balanceTypePanel.setLayout(new FlowLayout(FlowLayout.CENTER,50,3));
		panel.add(balanceTypePanel);
		
		mBalanceCollectedSelection = new PosRadioButton();
		mBalanceCollectedSelection.setPreferredSize(new Dimension(CHECKBOX_SMALL_WIDTH, CHECKBOX_HEIGHT)); 
		mBalanceCollectedSelection.setFocusable(false);
		mBalanceCollectedSelection.setTag("Collected");
		mBalanceCollectedSelection.setHorizontalAlignment(JTextField.LEFT);
		mBalanceCollectedSelection.setFont(PosFormUtil.getTextFieldBoldFont());
		mBalanceCollectedSelection.setText("Collected");
		mBalanceCollectedSelection.setMnemonic('l');
		 
		balanceTypePanel.add(mBalanceCollectedSelection);
		
		
		mBalancePendingSelection = new PosRadioButton();
		mBalancePendingSelection.setPreferredSize(new Dimension(CHECKBOX_SMALL_WIDTH, CHECKBOX_HEIGHT)); 
		mBalancePendingSelection.setFocusable(false);
		mBalancePendingSelection.setTag("Pending");
		mBalancePendingSelection.setHorizontalAlignment(JTextField.LEFT);
		mBalancePendingSelection.setFont(PosFormUtil.getTextFieldBoldFont());
		mBalancePendingSelection.setText("Pending");
		mBalancePendingSelection.setMnemonic('e');
		balanceTypePanel.add(mBalancePendingSelection);

		final ButtonGroup reporttype = new ButtonGroup();
		reporttype.add(mBalanceCollectedSelection);
		reporttype.add(mBalancePendingSelection);
		
		mBalanceCollectedSelection.setSelected(true);
		
	}

	/**
	 * 
	 */
	private void setDefaultDate() {

		String posDate = PosEnvSettings.getInstance().getPosDate();
		if(posDate != null){
			String dates[] = posDate.split("-");
			mTxtDay.setText(dates[2]);
			mTxtMonth.setText(dates[1]);
			mTxtYear.setText(dates[0]);
			mTxtDay.requestFocus();
			mTxtDay.setSelectedAll();
		}
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
	private void createDateSelectionControl() {

		int width = PANEL_WIDTH - PANEL_CONTENT_H_GAP * 7;
		
		mPanelDateSelection = createBoxedPanel(width, DATEPANEL_HEIGHT-1 );
		mContentPanel.add(mPanelDateSelection);
		
		setDateHeader();
		int left =PANEL_CONTENT_H_GAP+1;
		int top = mlabelDay.getY() + mlabelDay.getHeight()+1;

		mlabelDate = new JLabel();
		mlabelDate.setText("Date :");
		mlabelDate.setFont(PosFormUtil.getLabelFont());
		mlabelDate.setHorizontalAlignment(SwingConstants.LEFT);
		mlabelDate.setBounds(left, top, LABEL_WIDTH, LABEL_HEIGHT);
		mlabelDate.setBorder(new EmptyBorder(1, 1, 1, 1));
		mlabelDate.setBackground(Color.LIGHT_GRAY);
		mlabelDate.setOpaque(true);
		mPanelDateSelection.add(mlabelDate);

		left = mlabelDate.getX() + mlabelDate.getWidth() + PANEL_CONTENT_H_GAP/2;

		mTxtDay = new PosTouchableDigitalField(this, TEXT_FIELD_WIDTH_DAY);
		mTxtDay.setTitle("Day");
		mTxtDay.hideResetButton(true);
		mTxtDay.setLocation(left, top);
		mTxtDay.getTexFieldComponent().setDocument(
				PosNumberUtil.createDecimalDocument());
		mTxtDay.setListner(new IPosTouchableFieldListner() {
			
			@Override
			public void onValueSelected(Object value) {

				mTxtMonth.requestFocus();
				mTxtMonth.setSelectedAll();
			}
			
			@Override
			public void onReset() {
				
			}
		});
		mPanelDateSelection.add(mTxtDay);

		left = mTxtDay.getX() + mTxtDay.getWidth();

		mTxtMonth = new PosTouchableDigitalField(this,
				TEXT_FIELD_WIDTH_MONTH);
		mTxtMonth.setTitle("Month");
		mTxtMonth.setLocation(left, top);
		mTxtMonth.hideResetButton(true);
		mTxtMonth.getTexFieldComponent().setDocument(
				PosNumberUtil.createDecimalDocument());
		mTxtMonth.setListner(new IPosTouchableFieldListner() {
			
			@Override
			public void onValueSelected(Object value) {

				mTxtYear.requestFocus();
				mTxtYear.setSelectedAll();
			}
			
			@Override
			public void onReset() {
				// TODO Auto-generated method stub
				
			}
		});
		mPanelDateSelection.add(mTxtMonth);

		left = mTxtMonth.getX() + mTxtMonth.getWidth();

		mTxtYear = new PosTouchableDigitalField(this, TEXT_FIELD_WIDTH_YEAR);
		mTxtYear.setTitle("Year");
		mTxtYear.setLocation(left, top);
		mTxtYear.getTexFieldComponent().setDocument(
				PosNumberUtil.createDecimalDocument());
		mTxtYear.setListner(new IPosTouchableFieldListner() {

			@Override
			public void onValueSelected(Object value) {
				// TODO A0.00uto-generated method stub
			 
			}

			@Override
			public void onReset() {
				mTxtDay.reset();
				mTxtMonth.reset();
				setDefaultDate();
				
			}
		});
		mPanelDateSelection.add(mTxtYear);
		 
		
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
		mlabelDay.setBounds(left, top, TEXT_FIELD_WIDTH_DAY+LABEL_WIDTH, LABEL_HEIGHT_SMALL);
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

	/* (non-Javadoc)
	 * @see com.indocosmo.pos.forms.PosBaseForm#setContentPanel(javax.swing.JPanel)
	 */
	@Override
	protected void setContentPanel(JPanel panel) {

		mContentPanel = panel;
		mContentPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.pos.forms.PosBaseForm#onOkButtonClicked()
	 */
	@Override
	public boolean onOkButtonClicked() {

		boolean valid=true;
		String deliveryDate;
		 
		deliveryDate = (mTxtDay.getText().trim().length()!=0 || mTxtMonth.getText().trim().length()!=0
				|| mTxtYear.getText().trim().length()!=0)?PosDateUtil
						.buildStringDate(mTxtYear.getText()
								.trim(), mTxtMonth.getText().trim(), mTxtDay.getText()
								.trim(), PosDateUtil.DATE_SEPERATOR):null;
		 
					
			 

		if ((mTxtDay.getText().trim().length() == 0)
				|| (mTxtDay.getText().trim().length() > 2)) {
			PosFormUtil.showErrorMessageBox(this,"Invalid day.");
			mTxtDay.requestFocus();
			valid = false;
		} else if ((mTxtMonth.getText().trim().length() == 0)
				|| (mTxtMonth.getText().trim().length() > 2)) {
			PosFormUtil.showErrorMessageBox(this,"Invalid month.");
			mTxtMonth.requestFocus();
			valid = false;
		} else if ((mTxtYear.getText().trim().length()) != 4) {
			PosFormUtil.showErrorMessageBox(this,"Invalid year.");
			mTxtYear.requestFocus();
			valid = false;
		} else if (deliveryDate!=null && (!PosDateUtil.validateDate(deliveryDate))) {
			PosFormUtil.showErrorMessageBox(this,"Date is not in proper format.");
			mTxtDay.requestFocus();
			valid = false;
		} 

		if(valid){
			ArrayList<PosOrderServiceTypes> selectedServices=new ArrayList<PosOrderServiceTypes>();
			if(mCheckHomeDelivery.isSelected())
				selectedServices.add(PosOrderServiceTypes.HOME_DELIVERY);
			
			if(mCheckSalesOrder.isSelected())
				selectedServices.add(PosOrderServiceTypes.SALES_ORDER);
			if(selectedServices.size()==0){
				PosFormUtil.showErrorMessageBox(this,"Select  Service");
				valid = false;
			}
			doPrintReport(deliveryDate,selectedServices );
		}
		return false; 

	}
	 
	 
	/**
	 * @param where
	 * @param timeCriteria 
	 * @param orderTo 
	 * @param selected
	 * @param text
	 * @param salesReportOnly 
	 */
	private void doPrintReport(final String deliveryDate,
			ArrayList<PosOrderServiceTypes> selectedServices ) {

		try{
			/** Change here for custom print format **/
	
			if(!PosDeviceManager.getInstance().hasReceiptPrinter()) 
				throw new PrinterException("Normal printer not configured. Please configure printer.");
	
			PosSalesOrderReportBase report=null;
			
			switch(reportType){
			case DAILYADVANCE:
				report=new PosSalesOrderAdvanceReport();
				break;
			case DAILYBALANCE:
				report=new PosSalesOrderBalanceReport();
				((PosSalesOrderBalanceReport)report).setPendingBalanceReport(mBalancePendingSelection.isSelected()); 
				break;
			case ITEMWISE_STATEMENT:
				report=new PosSalesOrderItemWiseReport();
				break;
			case ITEMWISE_DET_STATEMENT:
				report=new PosSalesOrderItemWiseDetReport();
				break;
			}	
			report.setReportDate(deliveryDate); 
			report.setSelectedServices(selectedServices);
			
			final PosDeviceReceiptPrinter printer=PosDeviceManager.getInstance().getPrinter(report.getPrinterType());
	
			/**
			 * Check if printer exist 
			 */
			if(printer==null) 
				throw new PrinterException("Normal printer not configured. Please configure printer.");
			 
			printer.print(report);
			
		} catch (Exception e) {
			final String printExceptionName = java.awt.print.PrinterException.class.getName() + ":";
			PosFormUtil.showErrorMessageBox(this, e.getMessage().replace(printExceptionName, ""));
		}

	}
	/**
	 * @return the reportType
	 */
	public PosReportsType getReportType() {
		return reportType;
	}
	/**
	 * @param reportType the reportType to set
	 */
	public void setReportType(PosReportsType reportType) {
		this.reportType = reportType;
		setTitle(reportType.getDisplayText());
		

	}
	/**
	 * 
	 */
	private void createServiceType() {

		

		int width = PANEL_WIDTH - PANEL_CONTENT_H_GAP * 7;
		
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(width, CRITERIA_PANEL_HEIGHT-1));
		panel.setBorder(new LineBorder(Color.LIGHT_GRAY));
		panel.setLayout(null);
		if (PosOrderServiceTypes.HOME_DELIVERY.isVisibleInUI() && PosOrderServiceTypes.SALES_ORDER.isVisibleInUI()) 
	 		mContentPanel.add(panel);
		
		int left =PANEL_CONTENT_H_GAP+1;
		int top =PANEL_CONTENT_V_GAP;

		JLabel label = new JLabel();
		label.setText("Service :");
		label.setFont(PosFormUtil.getLabelFont());
		label.setHorizontalAlignment(SwingConstants.LEFT);
		label.setBounds(left, top, LABEL_WIDTH, LABEL_HEIGHT);
		label.setBorder(new EmptyBorder(1, 1, 1, 1));
		label.setBackground(Color.LIGHT_GRAY);
		label.setOpaque(true);
		panel.add(label);

		width=width-LABEL_WIDTH-PANEL_CONTENT_H_GAP*2;
		JPanel serviceTypePanel = new JPanel();
		serviceTypePanel.setBounds(left + LABEL_WIDTH , top,width, CRITERIA_PANEL_HEIGHT-PANEL_CONTENT_V_GAP*2);
		serviceTypePanel.setLayout(new FlowLayout(FlowLayout.CENTER,50,3));
		panel.add(serviceTypePanel);
 
		mCheckHomeDelivery = new PosCheckBox();
		mCheckHomeDelivery.setFocusable(false);
		mCheckHomeDelivery.setOpaque(true);
		mCheckHomeDelivery.setTag(PosOrderServiceTypes.HOME_DELIVERY.getDisplayText());
		mCheckHomeDelivery.setHorizontalAlignment(JTextField.LEFT);
		mCheckHomeDelivery.setPreferredSize(new Dimension(CHECKBOX_SMALL_WIDTH, CHECKBOX_HEIGHT)); 
		mCheckHomeDelivery.setIcon(PosResUtil.getImageIconFromResource(CHK_BUTTON_NORMAL));
		mCheckHomeDelivery.setSelectedIcon(PosResUtil.getImageIconFromResource(CHK_BUTTON_SELECTED));
		mCheckHomeDelivery.setFont(PosFormUtil.getLabelFont());
		mCheckHomeDelivery.setText( PosOrderServiceTypes.HOME_DELIVERY.getDisplayText() );
		mCheckHomeDelivery.setMnemonic('H');
		if (PosOrderServiceTypes.HOME_DELIVERY.isVisibleInUI()) 
			serviceTypePanel.add(mCheckHomeDelivery );   //servicePanel.add(mCheckHomeDelivery);
	  
		mCheckSalesOrder = new PosCheckBox();
		mCheckSalesOrder.setFocusable(false);
		mCheckSalesOrder.setTag(PosOrderServiceTypes.SALES_ORDER.getDisplayText());
		mCheckSalesOrder.setHorizontalAlignment(JTextField.LEFT);
		mCheckSalesOrder.setPreferredSize(new Dimension(CHECKBOX_SMALL_WIDTH, CHECKBOX_HEIGHT)); 
		mCheckSalesOrder.setIcon(PosResUtil.getImageIconFromResource(CHK_BUTTON_NORMAL));
		mCheckSalesOrder.setSelectedIcon(PosResUtil.getImageIconFromResource(CHK_BUTTON_SELECTED));
		mCheckSalesOrder.setFont(PosFormUtil.getLabelFont());
		mCheckSalesOrder.setText(PosOrderServiceTypes.SALES_ORDER.getDisplayText());
		mCheckSalesOrder.setMnemonic('S');
		if (PosOrderServiceTypes.SALES_ORDER.isVisibleInUI()){
			serviceTypePanel.add(mCheckSalesOrder);
			mCheckSalesOrder.setSelected(true);
		}else if (PosOrderServiceTypes.HOME_DELIVERY.isVisibleInUI()) 
			mCheckHomeDelivery.setSelected(true);
	}
}
