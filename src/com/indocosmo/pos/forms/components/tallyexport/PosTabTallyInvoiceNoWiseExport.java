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
public final class PosTabTallyInvoiceNoWiseExport extends PosTab implements IPosFormEventsListner{
	
	 

	private static final int PANEL_CONTENT_V_GAP = 2;
	private static final int PANEL_CONTENT_H_GAP = 2;

	private static final int TEXT_FIELD_WIDTH = 320;
	private static final int LABEL_HEIGHT = 40;
	private static final int LABEL_WIDTH_SMALL = 67;

	private static final Border LABEL_PADDING = new EmptyBorder(2, 2, 2, 2);
	
	
	private JPanel mPanelDateSelection;
	
	private static final int PANEL_WIDTH = LABEL_WIDTH_SMALL *2+ TEXT_FIELD_WIDTH  ;
	private static final int PANEL_HEIGHT=LABEL_HEIGHT *3  +PANEL_CONTENT_V_GAP ;
	 
	

	
	private PosTouchableDigitalField mTxtInvoiceNoFrom;
	private PosTouchableDigitalField mTxtInvoiceNoTo;
	
	private JLabel mlabelFrom;
	private JLabel mlabelTo;
	
	public PosTabTallyInvoiceNoWiseExport(Object parent ){
		super(parent,"Invoice No ");
		setMnemonicChar('n');
		initControls();	
	}
	
	private void initControls(){
		
		this.setLayout(null);
		this.setSize(PANEL_WIDTH, PANEL_HEIGHT);
		
		createSelectionControl();
		 
	}
	 
	/**
	 * 
	 */
	private void createSelectionControl() {

		int width = PANEL_WIDTH - PANEL_CONTENT_H_GAP * 5;
		
		mPanelDateSelection = createBoxedPanel(width, PANEL_HEIGHT-1 );
		mPanelDateSelection.setLocation(PANEL_CONTENT_H_GAP *4, PANEL_CONTENT_V_GAP*4);
		add(mPanelDateSelection);

		int left = PANEL_CONTENT_H_GAP  *10;
		int top = PANEL_CONTENT_V_GAP  *10  ;
	
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

		mTxtInvoiceNoFrom = new PosTouchableDigitalField((RootPaneContainer)getPosParent(), TEXT_FIELD_WIDTH);
		mTxtInvoiceNoFrom.setTitle("Invoice No");
		mTxtInvoiceNoFrom.hideResetButton(true);
		mTxtInvoiceNoFrom.setLocation(left, top);
		mTxtInvoiceNoFrom.getTexFieldComponent().setDocument(
				PosNumberUtil.createDecimalDocument());
		mTxtInvoiceNoFrom.setListner(new IPosTouchableFieldListner() {
			
			@Override
			public void onValueSelected(Object value) {

				mTxtInvoiceNoTo.requestFocus();
				mTxtInvoiceNoTo.setSelectedAll();
			}
			
			@Override
			public void onReset() {
				
			}
		});
		mPanelDateSelection.add(mTxtInvoiceNoFrom);

		
		left = mlabelFrom.getX();
		top = mlabelFrom.getY() + mlabelFrom.getHeight() + PANEL_CONTENT_V_GAP;

		mlabelTo = new JLabel();
		mlabelTo.setText("To :");
		mlabelTo.setFont(PosFormUtil.getLabelFont());
		mlabelTo.setHorizontalAlignment(SwingConstants.LEFT);
		mlabelTo.setBounds(left, top, LABEL_WIDTH_SMALL, LABEL_HEIGHT);
		mlabelTo.setBorder(LABEL_PADDING);
		mlabelTo.setBackground(Color.LIGHT_GRAY);
		mlabelTo.setOpaque(true);
		mPanelDateSelection.add(mlabelTo);

		
		left = mlabelTo.getX() + mlabelTo.getWidth() + PANEL_CONTENT_H_GAP/2;
		mTxtInvoiceNoTo = new PosTouchableDigitalField((RootPaneContainer)getPosParent(),
				TEXT_FIELD_WIDTH);
		mTxtInvoiceNoTo.setTitle("Invoice No");
		mTxtInvoiceNoTo.setLocation(left, top);
		mTxtInvoiceNoTo.hideResetButton(true);
		mTxtInvoiceNoTo.getTexFieldComponent().setDocument(
				PosNumberUtil.createDecimalDocument());
		mTxtInvoiceNoTo.setListner(new IPosTouchableFieldListner() {
			
			@Override
			public void onValueSelected(Object value) {

			 
			}
			
			@Override
			public void onReset() {
				// TODO Auto-generated method stub
				
			}
		});
		mPanelDateSelection.add(mTxtInvoiceNoTo);
 
	}

 	/**
 * @param width
 * @param height
 * @return
 */
	private JPanel createBoxedPanel(int width, int height) {
	
		JPanel boxPanel = new JPanel();
		Dimension size = new Dimension(width, height);
//		boxPanel.setBackground(Color.white);
		boxPanel.setPreferredSize(size);
		boxPanel.setSize(size);
//		boxPanel.setBorder(new LineBorder(Color.LIGHT_GRAY));
		boxPanel.setLayout(null);
	
		return boxPanel;
	}

  
	
	/* (non-Javadoc)
	 * @see com.indocosmo.pos.forms.PosBaseForm#onOkButtonClicked()
	 */
	@Override
	public boolean onOkButtonClicked() {
		 
		try{
			if (!validateControls()) 
				return false; 
			PosTallyExcelExport tallyExport=new PosTallyExcelExport();
			tallyExport.exportInvoiceListInvoiceNoWise(mTxtInvoiceNoFrom.getText(), mTxtInvoiceNoTo.getText(), false);
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
	private boolean validateControls(){
		
		boolean valid=true;

		if ((mTxtInvoiceNoFrom.getText().trim().length() == 0) ) {
			PosFormUtil.showErrorMessageBox(mParent,"Please enter a valid Invoice No");
			mTxtInvoiceNoFrom.requestFocus();
			valid = false;
		} else if ( mTxtInvoiceNoTo.getText().trim().length() == 0 || Integer.parseInt(mTxtInvoiceNoTo.getText().trim())==0) {
			PosFormUtil.showErrorMessageBox(mParent,"Please enter a valid  Invoice No");
			mTxtInvoiceNoTo.requestFocus();
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
