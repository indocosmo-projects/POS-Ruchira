package com.indocosmo.pos.forms.components.tallyexport;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.RootPaneContainer;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.indocosmo.pos.common.PosEnvSettings;
import com.indocosmo.pos.common.PosLog;
import com.indocosmo.pos.common.utilities.PosDateUtil;
import com.indocosmo.pos.common.utilities.PosFormUtil;
import com.indocosmo.pos.common.utilities.PosNumberUtil;
import com.indocosmo.pos.data.beans.BeanOrderDetail;
import com.indocosmo.pos.forms.PosOrderEntryForm;
import com.indocosmo.pos.forms.components.keypads.PosSoftKeyPad;
import com.indocosmo.pos.forms.components.keypads.PosSoftKeyPadMultiLineInput;
import com.indocosmo.pos.forms.components.tab.PosTab;
import com.indocosmo.pos.forms.components.textfields.PosTouchableDigitalField;
import com.indocosmo.pos.forms.components.textfields.listners.IPosTouchableFieldListner;
import com.indocosmo.pos.forms.listners.IPosFormEventsListner;
import com.indocosmo.pos.reports.export.PosTallyExcelExport;

@SuppressWarnings("serial")
public final class PosTabTallyDateWiseExport extends PosTab implements IPosFormEventsListner{
	
	 

	private static final int PANEL_CONTENT_V_GAP = 2;
	private static final int PANEL_CONTENT_H_GAP = 2;

	private static final int TEXT_FIELD_WIDTH_DAY = 100;
	private static final int TEXT_FIELD_WIDTH_MONTH = 110;
	private static final int TEXT_FIELD_WIDTH_YEAR = 170;
	private static final int LABEL_HEIGHT = 40;
	private static final int LABEL_HEIGHT_SMALL = 30;
	private static final int LABEL_WIDTH_SMALL = 60;
	private static final int TITLE_LABEL_HEIGHT = 40;

	private static final int TXT_AREA_HEIGHT = 234;
	private static final Border LABEL_PADDING = new EmptyBorder(2, 2, 2, 2);
	
	
	private JPanel mPanelDateSelection;
	
	private static final int PANEL_WIDTH = LABEL_WIDTH_SMALL  + TEXT_FIELD_WIDTH_DAY + TEXT_FIELD_WIDTH_MONTH + TEXT_FIELD_WIDTH_YEAR + PANEL_CONTENT_H_GAP * 8 ;
	private static final int PANEL_HEIGHT=TITLE_LABEL_HEIGHT+TXT_AREA_HEIGHT+PANEL_CONTENT_V_GAP*3;
	private static final int DATEPANEL_HEIGHT = 120;
	

	
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
	
	public PosTabTallyDateWiseExport(Object parent ){
		super(parent,"Date ");
		setMnemonicChar('d');
		initControls();	
	}
	
	private void initControls(){
		
		this.setLayout(null);
		this.setSize(PANEL_WIDTH, PANEL_HEIGHT);
		
		createOrderDateSelectionControl();
		setDefaultDate();
		 
	}
	 
	/**
	 * 
	 */
	private void createOrderDateSelectionControl() {

		int width = PANEL_WIDTH - PANEL_CONTENT_H_GAP * 5;
		
		mPanelDateSelection = createBoxedPanel(width, DATEPANEL_HEIGHT-1 );
		mPanelDateSelection.setLocation(PANEL_CONTENT_H_GAP *4, PANEL_CONTENT_V_GAP*4);
		add(mPanelDateSelection);

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

		mTxtDayFrom = new PosTouchableDigitalField((RootPaneContainer)getPosParent(), TEXT_FIELD_WIDTH_DAY);
		mTxtDayFrom.setTitle("Day");
		mTxtDayFrom.hideResetButton(true);
		mTxtDayFrom.setLocation(left, top);
		mTxtDayFrom.getTexFieldComponent().setDocument(
				PosNumberUtil.createDecimalDocument());
		mTxtDayFrom.setListner(new IPosTouchableFieldListner() {
			
			@Override
			public void onValueSelected(Object value) {

				mTxtMonthFrom.requestFocus();
				mTxtMonthFrom.setSelectedAll();
			}
			
			@Override
			public void onReset() {
				
			}
		});
		mPanelDateSelection.add(mTxtDayFrom);

		left = mTxtDayFrom.getX() + mTxtDayFrom.getWidth();

		mTxtMonthFrom = new PosTouchableDigitalField((RootPaneContainer)getPosParent(),
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
				mTxtYearFrom.setSelectedAll();
			}
			
			@Override
			public void onReset() {
				// TODO Auto-generated method stub
				
			}
		});
		mPanelDateSelection.add(mTxtMonthFrom);

		left = mTxtMonthFrom.getX() + mTxtMonthFrom.getWidth();

		mTxtYearFrom = new PosTouchableDigitalField((RootPaneContainer)getPosParent(), TEXT_FIELD_WIDTH_YEAR);
		mTxtYearFrom.setTitle("Year");
		mTxtYearFrom.setLocation(left, top);
		mTxtYearFrom.getTexFieldComponent().setDocument(
				PosNumberUtil.createDecimalDocument());
		mTxtYearFrom.setListner(new IPosTouchableFieldListner() {

			@Override
			public void onValueSelected(Object value) {
				// TODO A0.00uto-generated method stub
				mTxtDayTo.requestFocus();
				mTxtDayTo.setSelectedAll();
			}

			@Override
			public void onReset() {
				String posDate = PosEnvSettings.getInstance().getPosDate();
				if(posDate != null){
					String dates[] = posDate.split("-");
					mTxtDayFrom.setText(dates[2]);
					mTxtMonthFrom.setText(dates[1]);
					mTxtYearFrom.setText(dates[0]);
				}
				mTxtDayFrom.requestFocus();
				mTxtDayFrom.setSelectedAll();
				 
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

		mTxtDayTo = new PosTouchableDigitalField((RootPaneContainer)getPosParent(), TEXT_FIELD_WIDTH_DAY);
		mTxtDayTo.setTitle("Day");
		mTxtDayTo.hideResetButton(true);
		mTxtDayTo.setLocation(left, top);
		mTxtDayTo.getTexFieldComponent().setDocument(
				PosNumberUtil.createDecimalDocument());
		mTxtDayTo.setListner(new IPosTouchableFieldListner() {
			
			@Override
			public void onValueSelected(Object value) {

				mTxtMonthTo.requestFocus();
				mTxtMonthTo.setSelectedAll();
			}
			
			@Override
			public void onReset() {
				// TODO Auto-generated method stub
				
			}
		});
		mPanelDateSelection.add(mTxtDayTo);

		left = mTxtDayTo.getX() + mTxtDayTo.getWidth();

		mTxtMonthTo = new PosTouchableDigitalField((RootPaneContainer)getPosParent(), TEXT_FIELD_WIDTH_MONTH);
		mTxtMonthTo.setTitle("Month");
		mTxtMonthTo.setLocation(left, top);
		mTxtMonthTo.hideResetButton(true);
		mTxtMonthTo.getTexFieldComponent().setDocument(
				PosNumberUtil.createDecimalDocument());
		mTxtMonthTo.setListner(new IPosTouchableFieldListner() {
			
			@Override
			public void onValueSelected(Object value) {

				mTxtYearTo.requestFocus();
				mTxtYearTo.setSelectedAll();
			}
			
			@Override
			public void onReset() {
				// TODO Auto-generated method stub
				
			}
		});
		mPanelDateSelection.add(mTxtMonthTo);

		left = mTxtMonthTo.getX() + mTxtMonthTo.getWidth();

		mTxtYearTo = new PosTouchableDigitalField((RootPaneContainer)getPosParent(), TEXT_FIELD_WIDTH_YEAR);
		mTxtYearTo.setTitle("Year");
		mTxtYearTo.setLocation(left, top);
		mTxtYearTo.getTexFieldComponent().setDocument(
				PosNumberUtil.createDecimalDocument());
		mTxtYearTo.setListner(new IPosTouchableFieldListner() {

			@Override
			public void onValueSelected(Object value) {

			}

			@Override
			public void onReset() {
			 
				String posDate = PosEnvSettings.getInstance().getPosDate();
				if(posDate != null){
					String dates[] = posDate.split("-");
				 
					mTxtDayTo.setText(dates[2]);
					mTxtMonthTo.setText(dates[1]);
					mTxtYearTo.setText(dates[0]);
				}
				mTxtDayTo.requestFocus();
				mTxtDayTo.selectAll();
			}
		});
		mPanelDateSelection.add(mTxtYearTo);
		
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
	 * 
	 */
	private void setDefaultDate() {

		String posDate = PosEnvSettings.getInstance().getPosDate();
		if(posDate != null){
			String dates[] = posDate.split("-");
			mTxtDayFrom.setText(dates[2]);
			mTxtMonthFrom.setText(dates[1]);
			mTxtYearFrom.setText(dates[0]);
			
			mTxtDayTo.setText(dates[2]);
			mTxtMonthTo.setText(dates[1]);
			mTxtYearTo.setText(dates[0]);
			
			mTxtDayFrom.requestFocus();
			mTxtDayFrom.setSelectedAll();
		}
	}
	
	/* (non-Javadoc)
	 * @see com.indocosmo.pos.forms.PosBaseForm#onOkButtonClicked()
	 */
	@Override
	public boolean onOkButtonClicked() {
		 
		try{
			if (!validateDate()) 
				return false;
			String dateFrom =PosDateUtil.buildStringDate(mTxtYearFrom.getText()
									.trim(), mTxtMonthFrom.getText().trim(), mTxtDayFrom.getText()
									.trim(), PosDateUtil.DATE_SEPERATOR) ;
			
			String dateTo =PosDateUtil.buildStringDate(mTxtYearTo.getText()
					.trim(), mTxtMonthTo.getText().trim(), mTxtDayTo.getText()
					.trim(), PosDateUtil.DATE_SEPERATOR) ;
			
			if (dateFrom!=null && (!PosDateUtil.validateDate(dateFrom))) {
				PosFormUtil.showErrorMessageBox(mParent,"From Date is not in proper format.");
				mTxtDayFrom.requestFocus();
				return false;
			} else if (dateTo!=null && (!PosDateUtil.validateDate(dateTo))) {
				PosFormUtil.showErrorMessageBox(mParent,"To Date is not in proper format.");
				mTxtDayTo.requestFocus();
				return false;
			} 
			PosTallyExcelExport tallyExport=new PosTallyExcelExport();
			tallyExport.exportInvoiceListDateWise( dateFrom, dateTo , false);
			PosFormUtil.showInformationMessageBox(mParent, "Data exported successfully. ");
			
		}catch(Exception ex){
			PosLog.write(this, "exportExcel", ex);
			PosFormUtil.showErrorMessageBox(mParent , ex.getMessage());
			
			
		}
		return false;
		
	}
	
	/*
	 * 
	 */
	private boolean validateDate(){
		
		boolean valid=true;

		if ((mTxtDayFrom.getText().trim().length() == 0)
				|| (mTxtDayFrom.getText().trim().length() > 2)) {
			PosFormUtil.showErrorMessageBox(this,"Invalid day.");
			mTxtDayFrom.requestFocus();
			valid = false;
		} else if ((mTxtMonthFrom.getText().trim().length() == 0)
				|| (mTxtMonthFrom.getText().trim().length() > 2)) {
			PosFormUtil.showErrorMessageBox(this,"Invalid month.");
			mTxtMonthFrom.requestFocus();
			valid = false;
		} else if ((mTxtYearFrom.getText().trim().length()) != 4) {
			PosFormUtil.showErrorMessageBox(this,"Invalid year.");
			mTxtYearFrom.requestFocus();
			valid = false;
		} else if ((mTxtDayTo.getText().trim().length() == 0)
				|| (mTxtDayTo.getText().trim().length() > 2)) {
			PosFormUtil.showErrorMessageBox(this,"Invalid day.");
			mTxtDayTo.requestFocus();
			valid = false;
		} else if ((mTxtMonthTo.getText().trim().length() == 0)
				|| (mTxtMonthTo.getText().trim().length() > 2)) {
			PosFormUtil.showErrorMessageBox(this,"Invalid month.");
			mTxtMonthTo.requestFocus();
			valid = false;
		} else if ((mTxtYearTo.getText().trim().length()) != 4) {
			PosFormUtil.showErrorMessageBox(this,"Invalid year.");
			mTxtYearTo.requestFocus();
			valid = false;
		}
		return valid;
	}
	@Override
	public boolean onCancelButtonClicked() {
		return false;
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.pos.forms.listners.IPosFormEventsListner#onResetButtonClicked()
	 */
	@Override
	public void onResetButtonClicked() {
		// TODO Auto-generated method stub
		
	}
 

}
