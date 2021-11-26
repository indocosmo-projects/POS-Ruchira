/**
 * 
 */
package com.indocosmo.pos.forms.booking;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.indocosmo.pos.common.PosEnvSettings;
import com.indocosmo.pos.common.PosLog;
import com.indocosmo.pos.common.enums.PosOrderServiceTypes;
import com.indocosmo.pos.common.utilities.PosFormUtil;
import com.indocosmo.pos.common.utilities.PosNumberUtil;
import com.indocosmo.pos.common.utilities.PosResUtil;
import com.indocosmo.pos.data.beans.BeanCustomer;
import com.indocosmo.pos.data.beans.BeanEmployees;
import com.indocosmo.pos.data.beans.BeanServingTable;
import com.indocosmo.pos.data.providers.shopdb.PosCustomerProvider;
import com.indocosmo.pos.data.providers.shopdb.PosServiceTableProvider;
import com.indocosmo.pos.data.providers.shopdb.PosWaiterProvider;
import com.indocosmo.pos.forms.PosBaseForm;
import com.indocosmo.pos.forms.components.buttons.PosButton;
import com.indocosmo.pos.forms.components.buttons.listners.IPosButtonListner;
import com.indocosmo.pos.forms.components.textfields.PosItemBrowsableField;
import com.indocosmo.pos.forms.components.textfields.PosItemSearchableField;
import com.indocosmo.pos.forms.components.textfields.PosTouchableDigitalField;
import com.indocosmo.pos.forms.components.textfields.PosTouchableFieldBase;
import com.indocosmo.pos.forms.components.textfields.PosTouchableTextField;
import com.indocosmo.pos.forms.components.textfields.listners.IPosTouchableFieldListner;
import com.indocosmo.pos.forms.components.textfields.listners.PosTouchableFieldAdapter;

/**
 * @author jojesh
 *
 */
/**
 * @author jojesh
 *
 */
public class PosOrderBookingForm extends PosBaseForm{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int PANEL_WIDTH = 907;
	private static final int PANEL_HEIGHT = 571;
	
	private static final int PANEL_CENTER_GAP = PANEL_CONTENT_V_GAP;
	
	private static final int TITLE_LABEL_WIDTH=140;
	private static final int TITLE_LABLE_HEIGHT=PosTouchableFieldBase.LAYOUT_DEF_HEIGHT;
	private static final int TEXT_FIELD_WIDTH_DAY = 50;
	private static final int TEXT_FIELD_WIDTH_MONTH = 50;
	private static final int TEXT_FIELD_WIDTH_YEAR = 80;
	
	private static final int H_GAP_BTWN_CMPNTS=1;
	private static final int V_GAP_BTWN_CMPNTS=2;
	
	private JPanel contentPanel;
	
	private JLabel lblBookingNo;
	private PosTouchableTextField txtBookingTerCode;
	private PosTouchableTextField txtBookingNo;

	private JLabel lblBookingDateTime;
	private PosTouchableDigitalField txtBookingDay;
	private PosTouchableDigitalField txtBookingMonth;
	private PosTouchableDigitalField txtBookingYear;
	private PosTouchableDigitalField txtBookingMin;
	private PosTouchableDigitalField txtBookingHour;
	
	private JLabel labelCustomerID;
	private PosItemSearchableField txtCustomerID;
	private PosButton custAddButton;
	
	private JLabel lblCustomerName;
	private PosTouchableTextField txtCustomerName;
	
	private JLabel labelArrivalDate;
	private PosTouchableDigitalField txtArivalDay;
	private PosTouchableDigitalField txtArrivalMonth ;
	private PosTouchableDigitalField txtArrivalYear;
	
	private JLabel labelArrivalTime; 
	private PosTouchableDigitalField txtArivalMin;
	private PosTouchableDigitalField txtArrivalHour ;
	
	private JLabel lblServiceType;
	private PosItemBrowsableField txtServiceType;
	
	private JLabel lblTablePax;
	private PosItemSearchableField txtTable;
	
	private JLabel lblServedBy;
	private PosItemBrowsableField txtServedBy;
	
	private JLabel labelAdvance;
	private PosTouchableDigitalField txtAdvance; 
	
	private PosServiceTableProvider mServingTableProvider;
	private ArrayList<BeanServingTable> mServingTableList;
	
	private PosCustomerProvider mPosCustomerProvider;
	private ArrayList<BeanCustomer> mPosCustomerList;
	
	private PosWaiterProvider mPosWaiterProvider;
	private ArrayList<BeanEmployees> mPosWaiterList;
	
	public PosOrderBookingForm(){
		super("Booking",PANEL_WIDTH,PANEL_HEIGHT);
		try {
		mPosCustomerProvider=new PosCustomerProvider();
		mPosCustomerList=new ArrayList<BeanCustomer>();
		mPosCustomerList.addAll(mPosCustomerProvider.getItemList());
		
		mPosWaiterProvider = new PosWaiterProvider();
		mPosWaiterList = mPosWaiterProvider.getWaiterList();
		
		mServingTableProvider=new PosServiceTableProvider();
		mServingTableList=mServingTableProvider.getServiceTableList();
		
		initForm();
		
		} catch (Exception e) {
			PosFormUtil.showSystemError(this);
			PosLog.write(this, "PosOrderBookingForm", e);
		}
		
	}
	

	/* (non-Javadoc)
	 * @see com.indocosmo.pos.forms.PosBaseForm#setContentPanel(javax.swing.JPanel)
	 */
	@Override
	protected void setContentPanel(JPanel panel) {
		
		contentPanel=panel;
		contentPanel.setLayout(null);
		
	}
	
	private void initForm(){
		
		//Row 1
		createBookingNoField();
		createBookingDateField();
		
		//Row 2
		createCustomerSelect();
		createCustomerName();
		
		//Row 3
		createArrivalDate();
		createArrivalTime();
		
		//Row 4
		createServiceTypeFiled();
		createTablePaxFiled();
		
		//Row 5
		createServedByFiled();
		
		createAdvanceField();
		
		createRemarksFiled();
		
	}
	
	/**
	 * 
	 */
	private void createBookingNoField(){
		
		int left=PANEL_CONTENT_H_GAP;
		int top=PANEL_CONTENT_V_GAP;
		
		lblBookingNo=new JLabel();
		lblBookingNo.setOpaque(true);
		lblBookingNo.setBackground(Color.LIGHT_GRAY);
		lblBookingNo.setText("Booking No. :");
		lblBookingNo.setBorder(new EmptyBorder(2, 2, 2, 2));
		lblBookingNo.setHorizontalAlignment(SwingConstants.LEFT);		
		lblBookingNo.setBounds(left, top, TITLE_LABEL_WIDTH, TITLE_LABLE_HEIGHT);		
		lblBookingNo.setFont(PosFormUtil.getLabelFont());
		lblBookingNo.setFocusable(true);
		contentPanel.add(lblBookingNo);
		
		left=lblBookingNo.getX()+lblBookingNo.getWidth()+H_GAP_BTWN_CMPNTS;
		
		txtBookingTerCode=new PosTouchableTextField(this,97);
		txtBookingTerCode.setEditable(false);
		txtBookingTerCode.setLocation(left, top);
		txtBookingTerCode.hideResetButton(true);
		txtBookingTerCode.hideBrowseButton(true);
		txtBookingTerCode.setHorizontalTextAlignment(SwingConstants.CENTER);
		txtBookingTerCode.setText("TER01");
		contentPanel.add(txtBookingTerCode);
		
		left=txtBookingTerCode.getX()+txtBookingTerCode.getWidth()+H_GAP_BTWN_CMPNTS;
		
		JLabel lblBookingNoSep=new JLabel("-");
		lblBookingNoSep.setHorizontalAlignment(SwingConstants.CENTER);		
		lblBookingNoSep.setBounds(left, top, 10, TITLE_LABLE_HEIGHT);		
		lblBookingNoSep.setFont(PosFormUtil.getLabelFont());
		contentPanel.add(lblBookingNoSep);
		
		left=lblBookingNoSep.getX()+lblBookingNoSep.getWidth()+H_GAP_BTWN_CMPNTS;
		txtBookingNo=new PosTouchableTextField(this,192);
		txtBookingNo.setEditable(false);
		txtBookingNo.setLocation(left, top);
		txtBookingNo.hideResetButton(true);
		txtBookingNo.hideBrowseButton(true);
		txtBookingNo.setHorizontalTextAlignment(SwingConstants.CENTER);
		txtBookingNo.setText("161012101510");
		contentPanel.add(txtBookingNo);
	}
	
	/**
	 * 
	 */
	private void createBookingDateField(){
		
		int left=txtBookingNo.getX()+txtBookingNo.getWidth()+PANEL_CENTER_GAP;
		int top=txtBookingNo.getY();
		
		lblBookingDateTime = new JLabel();
		lblBookingDateTime.setText("Booking Date :");
		lblBookingDateTime.setBorder(new EmptyBorder(2,2,2,2));
		lblBookingDateTime.setBackground(Color.LIGHT_GRAY);
		lblBookingDateTime.setOpaque(true);
		lblBookingDateTime.setFont(PosFormUtil.getLabelFont());
		lblBookingDateTime.setHorizontalAlignment(SwingConstants.LEFT);
		lblBookingDateTime.setBounds(left, top,TITLE_LABEL_WIDTH, TITLE_LABLE_HEIGHT);	
		contentPanel.add(lblBookingDateTime);

		left = lblBookingDateTime.getX() + lblBookingDateTime.getWidth() +H_GAP_BTWN_CMPNTS;
//		top =  lblBookingDateTime.getY() + lblBookingDateTime.getHeight();

		txtBookingDay = new PosTouchableDigitalField(this,
				TEXT_FIELD_WIDTH_DAY);
		txtBookingDay.setTitle("Day");
		txtBookingDay.hideResetButton(true);
		txtBookingDay.hideBrowseButton(true);
		txtBookingDay.setEditable(false);
		txtBookingDay.setLocation(left, top);
		txtBookingDay.setText("00");
		txtBookingDay.setHorizontalTextAlignment(SwingConstants.CENTER);
		contentPanel.add(txtBookingDay);

		left = txtBookingDay.getX()+txtBookingDay.getWidth()+H_GAP_BTWN_CMPNTS;

		txtBookingMonth = new PosTouchableDigitalField(this,
				TEXT_FIELD_WIDTH_MONTH);
		txtBookingMonth.setTitle("Month");
		txtBookingMonth.hideResetButton(true);
		txtBookingMonth.hideBrowseButton(true);
		txtBookingMonth.setEditable(false);
		txtBookingMonth.setLocation(left, top);
		txtBookingMonth.setText("00");
		txtBookingMonth.setHorizontalTextAlignment(SwingConstants.CENTER);
		contentPanel.add(txtBookingMonth);


		left = txtBookingMonth.getX()+txtBookingMonth.getWidth()+H_GAP_BTWN_CMPNTS;

		txtBookingYear = new PosTouchableDigitalField(this,
				TEXT_FIELD_WIDTH_YEAR-1);
		txtBookingYear.setTitle("Year");
		txtBookingYear.hideResetButton(true);
		txtBookingYear.hideBrowseButton(true);
		txtBookingYear.setEditable(false);
		txtBookingYear.setLocation(left, top);
		txtBookingYear.setText("0000");
		txtBookingYear.setHorizontalTextAlignment(SwingConstants.CENTER);
		contentPanel.add(txtBookingYear);
		
		left = txtBookingYear.getX()+txtBookingYear.getWidth()+H_GAP_BTWN_CMPNTS;
		
		JLabel lblBookingDareTimeSep=new JLabel(":");
		lblBookingDareTimeSep.setHorizontalAlignment(SwingConstants.CENTER);		
		lblBookingDareTimeSep.setBounds(left, top, 15, TITLE_LABLE_HEIGHT);		
		lblBookingDareTimeSep.setFont(PosFormUtil.getLabelFont());
		contentPanel.add(lblBookingDareTimeSep);
		
		left = lblBookingDareTimeSep.getX()+lblBookingDareTimeSep.getWidth()+H_GAP_BTWN_CMPNTS;
		
		txtBookingMin = new PosTouchableDigitalField(this,
				TEXT_FIELD_WIDTH_DAY);
		txtBookingMin.setTitle("Day");
		txtBookingMin.hideResetButton(true);
		txtBookingMin.hideBrowseButton(true);
		txtBookingMin.setEditable(false);
		txtBookingMin.setLocation(left, top);
		txtBookingMin.setText("00");
		txtBookingMin.setHorizontalTextAlignment(SwingConstants.CENTER);
		contentPanel.add(txtBookingMin);

		left = txtBookingMin.getX()+txtBookingMin.getWidth()+H_GAP_BTWN_CMPNTS;

		txtBookingHour = new PosTouchableDigitalField(this,
				TEXT_FIELD_WIDTH_DAY);
		txtBookingHour.setTitle("Month");
		txtBookingHour.hideResetButton(true);
		txtBookingHour.hideBrowseButton(true);
		txtBookingHour.setEditable(false);
		txtBookingHour.setLocation(left, top);
		txtBookingHour.setText("00");
		txtBookingHour.setHorizontalTextAlignment(SwingConstants.CENTER);
		contentPanel.add(txtBookingHour);
		
//		setDefaultDate(txtBookingDay, txtBookingMonth, txtBookingYear,true);
	}
	
	/**
	 * 
	 */
	private void createCustomerSelect(){
		
		int left=PANEL_CONTENT_H_GAP;		
		int top=txtBookingHour.getY()+txtBookingHour.getHeight()+V_GAP_BTWN_CMPNTS;
		
		labelCustomerID=new JLabel();
		labelCustomerID.setOpaque(true);
		labelCustomerID.setBackground(Color.LIGHT_GRAY);
		labelCustomerID.setText("Customer ID :");
		labelCustomerID.setBorder(new EmptyBorder(2, 2, 2, 2));
		labelCustomerID.setHorizontalAlignment(SwingConstants.LEFT);		
		labelCustomerID.setBounds(left, top, TITLE_LABEL_WIDTH, TITLE_LABLE_HEIGHT);		
		labelCustomerID.setFont(PosFormUtil.getLabelFont());
		labelCustomerID.setFocusable(true);
		contentPanel.add(labelCustomerID);

//		mPosCustomerList=mPosCustomerProvider.getItemList();
		left=labelCustomerID.getX()+labelCustomerID.getWidth()+H_GAP_BTWN_CMPNTS;
		
		txtCustomerID=new PosItemSearchableField(this,250,mPosCustomerList);
		
//		mTxtCustomerID.setSearchItemList(mPosCustomerList);
		txtCustomerID.setHorizontalTextAlignment(JTextField.LEFT);
		txtCustomerID.setLocation(left, top);
		txtCustomerID.setFont(PosFormUtil.getTextFieldBoldFont());		
//		mTxtCustomerID.setEditable(false);
		txtCustomerID.setTitle("Customer's ID");
		txtCustomerID.setTextReadOnly(true);
		txtCustomerID.hideResetButton(true);
		txtCustomerID.setListner(new PosTouchableFieldAdapter() {
			@Override
			public void onValueSelected(Object value) {
//				mPosCustomer=(BeanCustomer)value;
//				loadUserData(mPosCustomer);
//				mTxtCustomerID.setFocus();
//				mTxtCustomerID.setSelectedAll();
			}
		});
		contentPanel.add(txtCustomerID);
		
		left=txtCustomerID.getX()+txtCustomerID.getWidth();
		
		custAddButton=new PosButton();
		custAddButton.setLocation(left, top+1);
		custAddButton.setSize(50, 40);
		custAddButton.setText("+");
		custAddButton.setOnClickListner(new IPosButtonListner() {
			
			@Override
			public void onClicked(PosButton button) {
//				showBroswerForm();
			}
		});
		
		custAddButton.setImage(PosResUtil.getImageIconFromResource("click_textfield_button.png"));
		custAddButton.setTouchedImage(PosResUtil.getImageIconFromResource("click_textfield_button_touch.png"));	
		contentPanel.add(custAddButton);
	}
	
	private void createCustomerName(){
		
		int left=custAddButton.getX()+custAddButton.getWidth()+PANEL_CENTER_GAP;
		int top=custAddButton.getY();
		
		lblCustomerName=new JLabel();
		lblCustomerName.setOpaque(true);
		lblCustomerName.setBackground(Color.LIGHT_GRAY);
		lblCustomerName.setText("Cust. Name :");
		lblCustomerName.setBorder(new EmptyBorder(2, 2, 2, 2));
		lblCustomerName.setHorizontalAlignment(SwingConstants.LEFT);		
		lblCustomerName.setBounds(left, top, TITLE_LABEL_WIDTH, TITLE_LABLE_HEIGHT);		
		lblCustomerName.setFont(PosFormUtil.getLabelFont());
		lblCustomerName.setFocusable(true);
		contentPanel.add(lblCustomerName);
		
		left=lblCustomerName.getX()+lblCustomerName.getWidth()+H_GAP_BTWN_CMPNTS;
		
		txtCustomerName=new PosTouchableTextField(this,300);
		
//		mTxtCustomerID.setSearchItemList(mPosCustomerList);
		txtCustomerName.setHorizontalTextAlignment(JTextField.LEFT);
		txtCustomerName.setLocation(left, top);
		txtCustomerName.setFont(PosFormUtil.getTextFieldBoldFont());		
		txtCustomerName.setTitle("Customer's Name");
		txtCustomerName.setTextReadOnly(true);
		txtCustomerName.hideBrowseButton(true);
		txtCustomerName.hideResetButton(true);
		txtCustomerName.setListner(new PosTouchableFieldAdapter() {
			@Override
			public void onValueSelected(Object value) {
//				mPosCustomer=(BeanCustomer)value;
//				loadUserData(mPosCustomer);
//				mTxtCustomerID.setFocus();
//				mTxtCustomerID.setSelectedAll();
			}
		});
		contentPanel.add(txtCustomerName);
		
	}
	
	/**
	 * 
	 */
	private void createArrivalDate() {
		
		final int TEXT_FIELD_WIDTH_DAY=100;
		final int TEXT_FIELD_WIDTH_MONTH=100;
		final int TEXT_FIELD_WIDTH_YEAR=100;
		
		final int HEADING_HEIGHT=30;
		
		int left = PANEL_CONTENT_H_GAP;
		int top = labelCustomerID.getY()+labelCustomerID.getHeight()+V_GAP_BTWN_CMPNTS;
		
		labelArrivalDate = new JLabel();
		labelArrivalDate.setText("Arrival Date :");
		labelArrivalDate.setBorder(new EmptyBorder(2,2,2,2));
		labelArrivalDate.setBackground(Color.LIGHT_GRAY);
		labelArrivalDate.setOpaque(true);
		labelArrivalDate.setFont(PosFormUtil.getLabelFont());
		labelArrivalDate.setHorizontalAlignment(SwingConstants.LEFT);
		labelArrivalDate.setBounds(left, top, TITLE_LABEL_WIDTH, TITLE_LABLE_HEIGHT+HEADING_HEIGHT+V_GAP_BTWN_CMPNTS);
		contentPanel.add(labelArrivalDate);
		
		left = labelArrivalDate.getX()+labelArrivalDate.getWidth()+H_GAP_BTWN_CMPNTS;
		
		JLabel labelDay = new JLabel();
		labelDay.setText("DD");
		labelDay.setBorder(new EmptyBorder(2,2,2,2));
		labelDay.setBackground(Color.LIGHT_GRAY);
		labelDay.setOpaque(true);
		labelDay.setFont(PosFormUtil.getLabelFont());
		labelDay.setHorizontalAlignment(SwingConstants.CENTER);
		labelDay.setBounds(left, top, TEXT_FIELD_WIDTH_DAY-1, HEADING_HEIGHT);
		contentPanel.add(labelDay);
		
		
		left = labelDay.getX()+labelDay.getWidth()+H_GAP_BTWN_CMPNTS;
		
		JLabel labelMonth = new JLabel();
		labelMonth.setText("MM");
		labelMonth.setBorder(new EmptyBorder(2,2,2,2));
		labelMonth.setBackground(Color.LIGHT_GRAY);
		labelMonth.setOpaque(true);
		labelMonth.setFont(PosFormUtil.getLabelFont());
		labelMonth.setHorizontalAlignment(SwingConstants.CENTER);
		labelMonth.setBounds(left, top, TEXT_FIELD_WIDTH_MONTH-1, HEADING_HEIGHT);
		contentPanel.add(labelMonth);
		
		left = labelMonth.getX()+labelMonth.getWidth()+H_GAP_BTWN_CMPNTS;
		
		JLabel labelYear = new JLabel();
		labelYear.setText("YY");
		labelYear.setBorder(new EmptyBorder(2,2,2,2));
		labelYear.setBackground(Color.LIGHT_GRAY);
		labelYear.setOpaque(true);
		labelYear.setFont(PosFormUtil.getLabelFont());
		labelYear.setHorizontalAlignment(SwingConstants.CENTER);
		labelYear.setBounds(left, top, TEXT_FIELD_WIDTH_YEAR, HEADING_HEIGHT);
		contentPanel.add(labelYear);
		
		top = labelDay.getY()+labelDay.getHeight()+V_GAP_BTWN_CMPNTS;
		left = labelArrivalDate.getX() + labelArrivalDate.getWidth()+H_GAP_BTWN_CMPNTS ;

		txtArivalDay = new PosTouchableDigitalField(this,
				TEXT_FIELD_WIDTH_DAY);
		txtArivalDay.setTitle("Day");
		txtArivalDay.hideResetButton(true);
		txtArivalDay.setLocation(left, top);
		txtArivalDay.getTexFieldComponent().setDocument(PosNumberUtil.createDecimalDocument());
		contentPanel.add(txtArivalDay);

		left = txtArivalDay.getX() + txtArivalDay.getWidth() ;

		txtArrivalMonth = new PosTouchableDigitalField(this,
				TEXT_FIELD_WIDTH_MONTH);
		txtArrivalMonth.setTitle("Month");
		txtArrivalMonth.setLocation(left, top);
		txtArrivalMonth.hideResetButton(true);
		txtArrivalMonth.getTexFieldComponent().setDocument(PosNumberUtil.createDecimalDocument());
		contentPanel.add(txtArrivalMonth);

		left = txtArrivalMonth.getX() + txtArrivalMonth.getWidth() +H_GAP_BTWN_CMPNTS;

		txtArrivalYear = new PosTouchableDigitalField(this,
				TEXT_FIELD_WIDTH_YEAR);
		txtArrivalYear.setTitle("Year");
		txtArrivalYear.setLocation(left, top);
		txtArrivalYear.getTexFieldComponent().setDocument(PosNumberUtil.createDecimalDocument());
		txtArrivalYear.hideResetButton(true);
		txtArrivalYear.setListner(new IPosTouchableFieldListner() {

			@Override
			public void onValueSelected(Object value) {
				// TODO A0.00uto-generated method stub

			}

			@Override
			public void onReset() {
				setDefaultDate(txtArivalDay,txtArrivalMonth,txtArrivalYear,false);
			}
		});
		contentPanel.add(txtArrivalYear);
		setDefaultDate(txtArivalDay,txtArrivalMonth,txtArrivalYear,false);

	}
	
	private void createArrivalTime() {
		
		final int TEXT_FIELD_WIDTH_MIN=100;
		final int TEXT_FIELD_WIDTH_HOUR=100;
		
		final int HEADING_HEIGHT=30;
		
		int left = lblCustomerName.getX();
		int top = lblCustomerName.getY()+lblCustomerName.getHeight()+V_GAP_BTWN_CMPNTS;
		
		labelArrivalTime = new JLabel();
		labelArrivalTime.setText("Arrival Time :");
		labelArrivalTime.setBorder(new EmptyBorder(2,2,2,2));
		labelArrivalTime.setBackground(Color.LIGHT_GRAY);
		labelArrivalTime.setOpaque(true);
		labelArrivalTime.setFont(PosFormUtil.getLabelFont());
		labelArrivalTime.setHorizontalAlignment(SwingConstants.LEFT);
		labelArrivalTime.setBounds(left, top, TITLE_LABEL_WIDTH, TITLE_LABLE_HEIGHT+HEADING_HEIGHT);
		contentPanel.add(labelArrivalTime);
		
		left = labelArrivalTime.getX()+labelArrivalTime.getWidth()+H_GAP_BTWN_CMPNTS;
		
		JLabel labelMin = new JLabel();
		labelMin.setText("Min. (MM)");
		labelMin.setBorder(new EmptyBorder(2,2,2,2));
		labelMin.setBackground(Color.LIGHT_GRAY);
		labelMin.setOpaque(true);
		labelMin.setFont(PosFormUtil.getLabelFont());
		labelMin.setHorizontalAlignment(SwingConstants.CENTER);
		labelMin.setBounds(left, top, TEXT_FIELD_WIDTH_MIN-1, HEADING_HEIGHT);
		contentPanel.add(labelMin);
		
		
		left = labelMin.getX()+labelMin.getWidth()+H_GAP_BTWN_CMPNTS;
		
		JLabel labelHour = new JLabel();
		labelHour.setText("Hour (HH)");
		labelHour.setBorder(new EmptyBorder(2,2,2,2));
		labelHour.setBackground(Color.LIGHT_GRAY);
		labelHour.setOpaque(true);
		labelHour.setFont(PosFormUtil.getLabelFont());
		labelHour.setHorizontalAlignment(SwingConstants.CENTER);
		labelHour.setBounds(left, top, TEXT_FIELD_WIDTH_HOUR-1, HEADING_HEIGHT);
		contentPanel.add(labelHour);

		top = labelHour.getY()+labelHour.getHeight()+V_GAP_BTWN_CMPNTS;
		left = labelArrivalTime.getX() + labelArrivalTime.getWidth()+H_GAP_BTWN_CMPNTS ;

		txtArivalMin = new PosTouchableDigitalField(this,
				TEXT_FIELD_WIDTH_MIN);
		txtArivalMin.setTitle("Min");
		txtArivalMin.hideResetButton(true);
		txtArivalMin.setLocation(left, top);
		txtArivalMin.getTexFieldComponent().setDocument(PosNumberUtil.createDecimalDocument());
		contentPanel.add(txtArivalMin);

		left = txtArivalMin.getX() + txtArivalMin.getWidth() ;

		txtArrivalHour = new PosTouchableDigitalField(this,
				TEXT_FIELD_WIDTH_HOUR);
		txtArrivalHour.setTitle("Hour");
		txtArrivalHour.setLocation(left, top);
		txtArrivalHour.hideResetButton(true);
		txtArrivalHour.getTexFieldComponent().setDocument(PosNumberUtil.createDecimalDocument());
		txtArrivalHour.setListner(new IPosTouchableFieldListner() {

			@Override
			public void onValueSelected(Object value) {
				// TODO A0.00uto-generated method stub

			}

			@Override
			public void onReset() {
				setDefaultTime(txtArivalMin,txtArrivalHour);
			}
		});
		contentPanel.add(txtArrivalHour);
		setDefaultTime(txtArivalMin,txtArrivalHour);

	}
	
	/**
	 * 
	 */
	private void createServiceTypeFiled(){
		
		int left=PANEL_CONTENT_H_GAP;
		int top=labelArrivalDate.getY()+labelArrivalDate.getHeight()+V_GAP_BTWN_CMPNTS;
		
		lblServiceType=new JLabel();
		lblServiceType.setOpaque(true);
		lblServiceType.setBackground(Color.LIGHT_GRAY);
		lblServiceType.setText("Service :");
		lblServiceType.setBorder(new EmptyBorder(2, 2, 2, 2));
		lblServiceType.setHorizontalAlignment(SwingConstants.LEFT);		
		lblServiceType.setBounds(left, top, TITLE_LABEL_WIDTH, TITLE_LABLE_HEIGHT);		
		lblServiceType.setFont(PosFormUtil.getLabelFont());
		lblServiceType.setFocusable(true);
		contentPanel.add(lblServiceType);
		
		left=lblServiceType.getX()+lblServiceType.getWidth()+H_GAP_BTWN_CMPNTS;
		
		txtServiceType=new PosItemBrowsableField(this,301);
		txtServiceType.setBrowseItemList(PosOrderServiceTypes.values());
		txtServiceType.setSelectedItem(PosOrderServiceTypes.TABLE_SERVICE);
		txtServiceType.setLocation(left, top);
		txtServiceType.hideResetButton(true);
		txtServiceType.setHorizontalTextAlignment(SwingConstants.CENTER);
		contentPanel.add(txtServiceType);
	}
	
	/**
	 * 
	 */
	private void createTablePaxFiled(){
		
		int left=labelArrivalTime.getX();
		int top=labelArrivalTime.getY()+labelArrivalTime.getHeight()+V_GAP_BTWN_CMPNTS;
		
		lblTablePax=new JLabel();
		lblTablePax.setOpaque(true);
		lblTablePax.setBackground(Color.LIGHT_GRAY);
		lblTablePax.setText("Table/Pax :");
		lblTablePax.setBorder(new EmptyBorder(2, 2, 2, 2));
		lblTablePax.setHorizontalAlignment(SwingConstants.LEFT);		
		lblTablePax.setBounds(left, top, TITLE_LABEL_WIDTH, TITLE_LABLE_HEIGHT);		
		lblTablePax.setFont(PosFormUtil.getLabelFont());
		lblTablePax.setFocusable(true);
		contentPanel.add(lblTablePax);
		
		left=lblTablePax.getX()+lblTablePax.getWidth()+H_GAP_BTWN_CMPNTS;
		
		txtTable=new PosItemSearchableField(this,200,mServingTableList);
		txtTable.setLocation(left, top);
		txtTable.setHorizontalTextAlignment(SwingConstants.CENTER);
		txtTable.hideResetButton(true);
		contentPanel.add(txtTable);
		
		left=txtTable.getX()+txtTable.getWidth();
		
		PosTouchableDigitalField txtPax = new PosTouchableDigitalField(this,
				100);
		txtPax.setTitle("PAX");
		txtPax.setLocation(left, top);
		txtPax.hideResetButton(true);
		txtPax.getTexFieldComponent().setDocument(PosNumberUtil.createDecimalDocument());
		contentPanel.add(txtPax);
	}
	
	/**
	 * 
	 */
	private void createServedByFiled(){
		
		int left=PANEL_CONTENT_H_GAP;
		int top=lblServiceType.getY()+lblServiceType.getHeight()+V_GAP_BTWN_CMPNTS;
		
		lblServedBy=new JLabel();
		lblServedBy.setOpaque(true);
		lblServedBy.setBackground(Color.LIGHT_GRAY);
		lblServedBy.setText("Served By :");
		lblServedBy.setBorder(new EmptyBorder(2, 2, 2, 2));
		lblServedBy.setHorizontalAlignment(SwingConstants.LEFT);		
		lblServedBy.setBounds(left, top, TITLE_LABEL_WIDTH, TITLE_LABLE_HEIGHT);		
		lblServedBy.setFont(PosFormUtil.getLabelFont());
		
		lblServedBy.setFocusable(true);
		contentPanel.add(lblServedBy);
		
		left=lblServedBy.getX()+lblServedBy.getWidth()+H_GAP_BTWN_CMPNTS;
		
		txtServedBy=new PosItemBrowsableField(this,301);
		txtServedBy.setLocation(left, top);
		txtServedBy.setBrowseWindowSize(3, 3);
		txtServedBy.setBrowseItemList(mPosWaiterList);
		txtServedBy.setHorizontalTextAlignment(SwingConstants.CENTER);
		contentPanel.add(txtServedBy);
	}
	

	/**
	 * 
	 */
	private void createAdvanceField(){
		
		int left=PANEL_CONTENT_H_GAP;		
		int top=lblServedBy.getY()+lblServedBy.getHeight()+V_GAP_BTWN_CMPNTS;
		
		labelAdvance=new JLabel();
		labelAdvance.setOpaque(true);
		labelAdvance.setBackground(Color.LIGHT_GRAY);
		labelAdvance.setText("Advance :");
		labelAdvance.setBorder(new EmptyBorder(2, 2, 2, 2));
		labelAdvance.setHorizontalAlignment(SwingConstants.LEFT);		
		labelAdvance.setBounds(left, top, TITLE_LABEL_WIDTH, TITLE_LABLE_HEIGHT);		
		labelAdvance.setFont(PosFormUtil.getLabelFont());
		labelAdvance.setFocusable(true);
		contentPanel.add(labelAdvance);
		
		left = labelAdvance.getX() + labelAdvance.getWidth()+H_GAP_BTWN_CMPNTS ;

		txtAdvance = new PosTouchableDigitalField(this,
				301);
		txtAdvance.setTitle("Advance :");
		txtAdvance.hideResetButton(true);
		txtAdvance.setLocation(left, top);
		
		txtAdvance.getTexFieldComponent().setDocument(PosNumberUtil.createDecimalDocument());
		contentPanel.add(txtAdvance);
		
	}
	
	/**
	 * 
	 */
	private void createRemarksFiled(){
		
		int left=PANEL_CONTENT_H_GAP;
		int top=labelAdvance.getY()+labelAdvance.getHeight()+V_GAP_BTWN_CMPNTS;
		
		JLabel lblRemarks;
		lblRemarks=new JLabel();
		lblRemarks.setOpaque(true);
		lblRemarks.setBackground(Color.LIGHT_GRAY);
		lblRemarks.setText("Remarks :");
		lblRemarks.setBorder(new EmptyBorder(2, 2, 2, 2));
		lblRemarks.setHorizontalAlignment(SwingConstants.LEFT);		
		lblRemarks.setBounds(left, top, TITLE_LABEL_WIDTH, 30);		
		lblRemarks.setFont(PosFormUtil.getLabelFont());
		lblRemarks.setFocusable(true);
		contentPanel.add(lblRemarks);
		
		top=lblRemarks.getY()+lblRemarks.getHeight()+V_GAP_BTWN_CMPNTS;
		JTextArea txtRemarks=new JTextArea();
		txtRemarks=new JTextArea(5,30);
		txtRemarks.setLineWrap(true);
		txtRemarks.setFont(PosFormUtil.getTextFieldBoldFont());
		txtRemarks.getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent arg0) {
//				setDirty(true);
			}
			
			@Override
			public void insertUpdate(DocumentEvent arg0) {
//				setDirty(true);
				
			}
			
			@Override
			public void changedUpdate(DocumentEvent arg0) {
//				setDirty(true);
				
			}
		});
		
		JScrollPane scrolPane=new JScrollPane(txtRemarks);
		scrolPane.getVerticalScrollBar().setPreferredSize(new Dimension(20,0));
		scrolPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrolPane.setBounds(left,top,contentPanel.getWidth()-PANEL_CONTENT_H_GAP*2, 225);
		scrolPane.setBorder(new LineBorder(new Color(134, 184, 232)));
		contentPanel.add(scrolPane);
	}
	
	/**
	 * 
	 */
	private void setDefaultDate(PosTouchableFieldBase txtDay, PosTouchableFieldBase txtMonth,PosTouchableFieldBase txtYear,boolean isLong) {

		String posDate = PosEnvSettings.getInstance().getPosDate();
		if(posDate != null){
			String dates[] = posDate.split("-");
			txtDay.setText(dates[2]);
			txtMonth.setText(dates[1]);
			txtYear.setText((isLong)?dates[0]:dates[0].substring(2));
		}
	}
	
	private void setDefaultTime(PosTouchableFieldBase txtMin, PosTouchableFieldBase txtHour) {

		String posDate = PosEnvSettings.getInstance().getPosDate();
//		if(posDate != null){
//			String dates[] = posDate.split("-");
//			txtDay.setText(dates[2]);
//			txtMonth.setText(dates[1]);
//			txtYear.setText(dates[0]);
//		}
	}
	

}
