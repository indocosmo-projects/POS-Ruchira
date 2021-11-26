/**
 * 
 */
package com.indocosmo.pos.forms;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SortOrder;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;

import com.indocosmo.pos.common.PosEnvSettings;
import com.indocosmo.pos.common.enums.PosOrderServiceTypes;
import com.indocosmo.pos.common.enums.PosReportsType;
import com.indocosmo.pos.common.utilities.PosDateUtil;
import com.indocosmo.pos.common.utilities.PosFormUtil;
import com.indocosmo.pos.common.utilities.PosNumberUtil;
import com.indocosmo.pos.common.utilities.PosResUtil;
import com.indocosmo.pos.data.beans.BeanOrderCustomer;
import com.indocosmo.pos.data.beans.BeanReportVoidRefundItem;
import com.indocosmo.pos.data.providers.shopdb.PosVoidRefundItemReportProvider;
import com.indocosmo.pos.forms.components.checkboxes.PosCheckBox;
import com.indocosmo.pos.forms.components.radiobuttons.PosRadioButton;
import com.indocosmo.pos.forms.components.textfields.PosItemSearchableField;
import com.indocosmo.pos.forms.components.textfields.PosTouchableDigitalField;
import com.indocosmo.pos.forms.components.textfields.listners.IPosTouchableFieldListner;
import com.indocosmo.pos.reports.miscellaneous.PosRefundItemListReport;
import com.indocosmo.pos.reports.miscellaneous.PosVoidItemListReport;
import com.indocosmo.pos.terminal.devices.PosDeviceManager;
import com.indocosmo.pos.terminal.devices.printer.PosDeviceReceiptPrinter;
import com.indocosmo.pos.terminal.devices.printer.printerexceptions.PrinterException;

/**
 * @author sandhya
 *
 */
@SuppressWarnings("serial")
public class PosVoidRefundItemReportForm extends PosBaseForm{

	private static final int PANEL_CONTENT_H_GAP =2;
	private static final int PANEL_CONTENT_V_GAP = 2;
	private static final int LABEL_HEIGHT = 40;
	private static final int LABEL_HEIGHT_SMALL = 30;
	private static final Border LABEL_PADDING = new EmptyBorder(2, 2, 2, 2);
 
	private static final String CHK_BUTTON_NORMAL="checkbox_normal.png";
	private static final String CHK_BUTTON_SELECTED="checkbox_selected.png";
	
	private static final int TEXT_FIELD_WIDTH_DAY = 120;
	private static final int TEXT_FIELD_WIDTH_MONTH = 120;
	private static final int TEXT_FIELD_WIDTH_YEAR = 190;
	private static final int LABEL_WIDTH = 80;
	private static final int CRITERIA_PANEL_HEIGHT = LABEL_HEIGHT  ;
	private static final int DATEPANEL_HEIGHT = LABEL_HEIGHT + LABEL_HEIGHT_SMALL   + PANEL_CONTENT_V_GAP *4;
	private static  int PANEL_HEIGHT = DATEPANEL_HEIGHT + PANEL_CONTENT_V_GAP * 9 +  CRITERIA_PANEL_HEIGHT ;
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
	private PosCheckBox mChkShowSummaryOnly;
	PosReportsType reportType;
	 
	/**
	 * 
	 */
	public PosVoidRefundItemReportForm(PosReportsType reportType) {
		
		super("Report",PANEL_WIDTH,   PANEL_HEIGHT);
		this.reportType = reportType;
		
		setTitle(reportType.getDisplayText());
		createUI();
	}
	/**
	 * 
	 */
	private void createUI() {

		createDateSelectionControl();
		createCustomerCriterialControls();
		setOkButtonCaption("Print");
		mButtonOk.setMnemonic('P');
		setCancelButtonCaption("Close");
		 
		setDefaultDate();
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

	/**
	 * 
	 */
	private void createCustomerCriterialControls() {

		int width = PANEL_WIDTH - PANEL_CONTENT_H_GAP * 7;
		
		JPanel panel = createBoxedPanel(width, CRITERIA_PANEL_HEIGHT );
//		panel.setBackground(Color.LIGHT_GRAY);
		mContentPanel.add(panel);
	 
		mChkShowSummaryOnly = new PosCheckBox();
		mChkShowSummaryOnly.setFocusable(false);
		mChkShowSummaryOnly.setOpaque(true);
		mChkShowSummaryOnly.setHorizontalAlignment(JTextField.LEFT);
		mChkShowSummaryOnly.setBounds(PANEL_CONTENT_H_GAP , PANEL_CONTENT_V_GAP, width-  PANEL_CONTENT_H_GAP *2  , LABEL_HEIGHT-PANEL_CONTENT_V_GAP*2);
//		mChkShowSummaryOnly.setBackground(Color.LIGHT_GRAY);
		mChkShowSummaryOnly.setIcon(PosResUtil.getImageIconFromResource(CHK_BUTTON_NORMAL));
		mChkShowSummaryOnly.setSelectedIcon(PosResUtil.getImageIconFromResource(CHK_BUTTON_SELECTED));
		mChkShowSummaryOnly.setFont(PosFormUtil.getLabelFont());
		mChkShowSummaryOnly.setText("Show Summary Only");
		mChkShowSummaryOnly.setMnemonic('S');
		mChkShowSummaryOnly.setEnabled(true);
		mChkShowSummaryOnly.setSelected(false);
		panel.add(mChkShowSummaryOnly );   
 
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
		String reportDate;
		 
		reportDate = (mTxtDay.getText().trim().length()!=0 || mTxtMonth.getText().trim().length()!=0
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
		} else if (reportDate!=null && (!PosDateUtil.validateDate(reportDate))) {
			PosFormUtil.showErrorMessageBox(this,"Date is not in proper format.");
			mTxtDay.requestFocus();
			valid = false;
		} 

		if(valid){
	 
			doPrintReport(reportDate );
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
	private void doPrintReport(final String reportDate  ) {

		try{
			/** Change here for custom print format **/
	
			if(!PosDeviceManager.getInstance().hasReceiptPrinter()) 
				throw new PrinterException("No Receipt printer configured. Please configure printer.");
			
			  PosDeviceReceiptPrinter printer=PosDeviceManager.getInstance().getPrinter((new PosRefundItemListReport()).getPrinterType());
			
			if(printer==null) 
				throw new PrinterException("Receipt printer not available. Please check printer");
		
			 ArrayList<BeanReportVoidRefundItem> itemList;
			 PosVoidRefundItemReportProvider reportPvdr=new PosVoidRefundItemReportProvider();
			 if(reportType==PosReportsType.REFUND_REPORT)
				 itemList=reportPvdr.getRefundList(reportDate);
			 else	
				 itemList=reportPvdr.getVoidItemList(reportDate);

			 if(itemList==null || itemList.size()==0){
				 PosFormUtil.showErrorMessageBox(this, "No data to print");
				 return;
			 }
			
			if(reportType==PosReportsType.REFUND_REPORT){
				PosRefundItemListReport report=new PosRefundItemListReport();
				printer=PosDeviceManager.getInstance().getPrinter(report.getPrinterType());
				report.setReportDate(reportDate); 
				report.setSummaryOnly(mChkShowSummaryOnly.isSelected());
				report.setRefundItemList(itemList);
				printer.print(report);
			}else{
					
				PosVoidItemListReport report=new PosVoidItemListReport();
				printer=PosDeviceManager.getInstance().getPrinter(report.getPrinterType());
				report.setReportDate(reportDate); 
				report.setSummaryOnly(mChkShowSummaryOnly.isSelected());
				report.setVoidItemList(itemList);
				printer.print(report);
			}
			
		} catch (Exception e) {
			
			final String printExceptionName = java.awt.print.PrinterException.class.getName() + ":";
			PosFormUtil.showErrorMessageBox(this, e.getMessage().replace(printExceptionName, ""));
		}

	}
	  
}
