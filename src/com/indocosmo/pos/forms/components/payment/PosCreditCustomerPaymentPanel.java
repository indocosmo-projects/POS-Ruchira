package com.indocosmo.pos.forms.components.payment;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.indocosmo.pos.common.PosEnvSettings;
import com.indocosmo.pos.common.utilities.PosCurrencyUtil;
import com.indocosmo.pos.common.utilities.PosFormUtil;
import com.indocosmo.pos.common.utilities.PosNumberUtil;
import com.indocosmo.pos.common.utilities.PosResUtil;
import com.indocosmo.pos.data.beans.BeanCompany;
import com.indocosmo.pos.data.beans.BeanCustomer;
import com.indocosmo.pos.data.beans.BeanOrderHeader;
import com.indocosmo.pos.data.beans.BeanOrderPayment;
import com.indocosmo.pos.data.beans.BeanPayModeBase.PaymentMode;
import com.indocosmo.pos.data.providers.shopdb.PosCompanyItemProvider;
import com.indocosmo.pos.data.providers.shopdb.PosCustomerTypeProvider;
import com.indocosmo.pos.forms.PosOrderEntryForm;
import com.indocosmo.pos.forms.PosPaymentForm;
import com.indocosmo.pos.forms.components.buttons.PosButton;
import com.indocosmo.pos.forms.components.buttons.PosSelectButton;
import com.indocosmo.pos.forms.components.buttons.listners.IPosButtonListner;
import com.indocosmo.pos.forms.components.buttons.listners.IPosSelectButtonListner;
import com.indocosmo.pos.forms.components.buttons.listners.PosButtonListnerAdapter;
import com.indocosmo.pos.forms.components.itemcontrols.PosCompanyItemControl;
import com.indocosmo.pos.forms.components.itemcontrols.PosDiscountItemControl;
import com.indocosmo.pos.forms.components.search.IPosSearchableItem;
import com.indocosmo.pos.forms.components.textfields.PosTouchableDigitalField;
import com.indocosmo.pos.forms.components.textfields.listners.IPosTouchableFieldListner;
import com.indocosmo.pos.forms.search.PosExtSearchForm;
import com.indocosmo.pos.forms.search.listener.adapter.PosItemExtSearchFormAdapter;


@SuppressWarnings("serial")
public final class PosCreditCustomerPaymentPanel extends PosPaymentBasePanel{	

	private static final int IMAGE_RIGHT_PANEL_BUTTON_WIDTH=90;
	private static final int IMAGE_RIGHT_PANEL_BUTTON_HEIGHT=87;

	private static final int IMAGE_RIGHT_PANEL_SEARCH_BUTTON_WIDTH=90;
	private static final int IMAGE_RIGHT_PANEL_SEARCH_BUTTON_HEIGHT=60;

	private static final int PANEL_CONTENT_V_GAP=5;
	private static final int PANEL_CONTENT_H_GAP=PosOrderEntryForm.PANEL_CONTENT_H_GAP;

	private static final String IMAGE_BUTTON_SCROLL_UP="dlg_buton_up.png";
	private static final String IMAGE_BUTTON_SCROLL_UP_TOUCH="dlg_buton_up_touch.png";

	private static final String IMAGE_BUTTON_SCROLL_DOWN="dlg_buton_down.png";	
	private static final String IMAGE_BUTTON_SCROLL_DOWN_TOUCH="dlg_buton_down_touch.png";

	private static final String IMAGE_BUTTON_SEARCH="comp_search_normal.png";	
	private static final String IMAGE_BUTTON_SEARCH_TOUCH="comp_search_touch.png";
	
	private static final String SELECT_BUTTON_NORMAL="payment_coupon_ctrl_select_button.png";
	
	private final static String EXACT_KEY_IMAGE="exact_pay.png";
	private final static String EXACT_KEY_IMAGE_TOUCH="exact_pay_touch.png";
	
	private static ImageIcon mImageSelectNormal;

	private static final int COMPANY_ROW_COUNT=4;
	private static final int COMPANY_COLUMN_COUNT=3;

	private static final int COMPANY_LIST_PANEL_WIDTH=PosCompanyItemControl.BUTTON_WIDTH*COMPANY_COLUMN_COUNT+PANEL_CONTENT_H_GAP*3;
	private static final int COMPANY_LIST_PANEL_HEIGHT=PosCompanyItemControl.BUTTON_HEIGHT*COMPANY_ROW_COUNT+PANEL_CONTENT_V_GAP*COMPANY_ROW_COUNT+1;

	private int mCurrentPage=0;
	private int mItemsPerPage=COMPANY_ROW_COUNT*COMPANY_COLUMN_COUNT;

	private ArrayList<? extends BeanCustomer> mPosCompanyeItemList;
	private ArrayList<PosCompanyItemControl> mCompanyItemControlList;	
	private Map<String, PosCompanyItemControl> mCompanyItemControlMap;
	
	private PosButton mImageButtonScrollUp;	
	private PosButton mImageButtonScrollDown;
	private PosButton mImageButtonSearch;

	private JPanel mCompanyItemContainer;
	private PosButton mButtonCancel;	
//	private JTextField mTxtCompanyInfo;	
	private PosCompanyItemControl mSelectedCompanyItemControl;

	private JLabel mLabelCode;
	private JTextField mTxtCode;

	private JLabel mLabelName;
	private JTextField mTxtName;

	private JLabel mLabelAccount;
	private JTextField mTxtAccount;

	private JLabel mLabelAmount;
	private PosTouchableDigitalField mTxtAmount;
	
	private JTextField mTxtCustType;
	private JTextField mTxtGSTin;
	private JTextField mTxtCUSTNo;
	private JTextField mTxtCUSTCardNo;


	private int mCompanyId;
	private String mCompanyName;

	private BeanCompany mSelectedCompany;
	private BeanOrderHeader mOrderHeader;
	private int mWidth,mHeight;
	private JTextField mTxtExact;

	public PosCreditCustomerPaymentPanel(PosPaymentForm parent,int width, int height) {
		super(parent,PaymentMode.Company.getDisplayText(),PaymentMode.Company);
//		setKeyStroke(KeyStroke.getKeyStroke(KeyEvent.VK_F12, 0));
		setMnemonicChar('A');
		mWidth=width;
		mHeight=height;
		initLayout();
	}
//	
//	public PosCompanyPaymentPanel(PosOrderHeaderObject orderHeader,PosPaymentForm parent,int width, int height) {
//		super(parent,"Company",PaymentMode.Company);
//		mOrderHeader=orderHeader;
//		mWidth=width;
//		mHeight=height;
//	}

	private void initLayout(){
		
		setBounds(0,0,mWidth,mHeight);
		setLayout(null);
		setDetailControls();
		setCompanyDetails();
		displayCurPageItems();
		reset();
		
	}
	
	private void setDetailControls(){
		
		int left=3;
		int top=20;
		int labelWidth=130;
		int height=40;
		int txtWidth=450;
		int exactButtonWidth=110;
		
		final int panel_width=labelWidth+txtWidth+PANEL_CONTENT_H_GAP*3;
		
		JPanel detailsControlPanel=new JPanel();
//		detailsControlPanel.setBounds(0, 0, getWidth(), getHeight()-COMPANY_LIST_PANEL_HEIGHT-PANEL_CONTENT_V_GAP);
		detailsControlPanel.setBounds((getWidth()-panel_width)/2, 0, panel_width, getHeight()-PANEL_CONTENT_V_GAP);
		detailsControlPanel.setLayout(null);
		//		detailsControlPanel.setBackground(Color.CYAN);
		add(detailsControlPanel);



		mLabelCode=new JLabel("Code : ");
		mLabelCode.setBorder(new EmptyBorder(2, 2, 2, 2));
		mLabelCode.setOpaque(true);
		mLabelCode.setBackground(Color.LIGHT_GRAY);
		mLabelCode.setBounds(left, top, labelWidth, height);
		mLabelCode.setFont(PosFormUtil.getLabelFont());
		detailsControlPanel.add(mLabelCode);

		left=mLabelCode.getX()+mLabelCode.getWidth()+left/3;
		mTxtCode=new JTextField();
		mTxtCode.setBounds(left, top, txtWidth, height);
		mTxtCode.setFont(PosFormUtil.getTextFieldFont());
		mTxtCode.setEditable(false);
		mTxtCode.setHorizontalAlignment(SwingConstants.LEFT);
		detailsControlPanel.add(mTxtCode);

		//		left=0;
		//		top=mLabelCode.getY()+mLabelCode.getHeight()+PANEL_CONTENT_V_GAP;
//		left=mTxtCode.getX()+mTxtCode.getWidth()+3;
		
		left=3;
		top=mTxtCode.getY()+mTxtCode.getHeight()+2;
		
		mLabelName=new JLabel("Name :");
		mLabelName.setBorder(new EmptyBorder(2, 2, 2, 2));
		mLabelName.setOpaque(true);
		mLabelName.setBackground(Color.LIGHT_GRAY);
		mLabelName.setBounds(left, top, labelWidth, height);
		mLabelName.setFont(PosFormUtil.getLabelFont());
		detailsControlPanel.add(mLabelName);
		
		left=mLabelName.getX()+mLabelName.getWidth()+1;
		mTxtName=new JTextField();
		mTxtName.setBounds(left, top, txtWidth, height);
		mTxtName.setFont(PosFormUtil.getTextFieldFont());
		mTxtName.setEditable(false);
		mTxtName.setHorizontalAlignment(SwingConstants.LEFT);
		detailsControlPanel.add(mTxtName);
		
		left=3;
		top=mTxtName.getY()+mTxtName.getHeight()+2;
		
		final JLabel labelCustType=new JLabel("Cust. Type : ");
		labelCustType.setBorder(new EmptyBorder(2, 2, 2, 2));
		labelCustType.setOpaque(true);
		labelCustType.setBackground(Color.LIGHT_GRAY);
		labelCustType.setBounds(left, top, labelWidth, height);
		labelCustType.setFont(PosFormUtil.getLabelFont());
		detailsControlPanel.add(labelCustType);
		
		left=labelCustType.getX()+labelCustType.getWidth()+1;
		
		mTxtCustType=new JTextField();
		mTxtCustType.setBounds(left, top, txtWidth, height);
		mTxtCustType.setFont(PosFormUtil.getTextFieldFont());
		mTxtCustType.setEditable(false);
		mTxtCustType.setHorizontalAlignment(SwingConstants.LEFT);
		detailsControlPanel.add(mTxtCustType);
		
		left=3;
		top=mTxtCustType.getY()+mTxtCustType.getHeight()+2;
		
		mLabelAccount=new JLabel("Account : ");
		mLabelAccount.setBorder(new EmptyBorder(2, 2, 2, 2));
		mLabelAccount.setOpaque(true);
		mLabelAccount.setBackground(Color.LIGHT_GRAY);
		mLabelAccount.setBounds(left, top, labelWidth, height);
		mLabelAccount.setFont(PosFormUtil.getLabelFont());
		detailsControlPanel.add(mLabelAccount);
		
		left=mLabelAccount.getX()+mLabelAccount.getWidth()+1;
		mTxtAccount=new JTextField();
		mTxtAccount.setBounds(left, top, txtWidth, height);
		mTxtAccount.setFont(PosFormUtil.getTextFieldFont());
		mTxtAccount.setEditable(false);
		mTxtAccount.setHorizontalAlignment(SwingConstants.LEFT);
		detailsControlPanel.add(mTxtAccount);
		
		left=3;
		top=mTxtAccount.getY()+mTxtAccount.getHeight()+2;
		
		final JLabel labelGSTin=new JLabel("GSTin : ");
		labelGSTin.setBorder(new EmptyBorder(2, 2, 2, 2));
		labelGSTin.setOpaque(true);
		labelGSTin.setBackground(Color.LIGHT_GRAY);
		labelGSTin.setBounds(left, top, labelWidth, height);
		labelGSTin.setFont(PosFormUtil.getLabelFont());
		detailsControlPanel.add(labelGSTin);
		
		left=labelGSTin.getX()+labelGSTin.getWidth()+1;
		
		mTxtGSTin=new JTextField();
		mTxtGSTin.setBounds(left, top, txtWidth, height);
		mTxtGSTin.setFont(PosFormUtil.getTextFieldFont());
		mTxtGSTin.setEditable(false);
		mTxtGSTin.setHorizontalAlignment(SwingConstants.LEFT);
		detailsControlPanel.add(mTxtGSTin);
		
		left=3;
		top=mTxtGSTin.getY()+mTxtGSTin.getHeight()+2;
		
		final JLabel labelCUSTNo=new JLabel("Cust No. : ");
		labelCUSTNo.setBorder(new EmptyBorder(2, 2, 2, 2));
		labelCUSTNo.setOpaque(true);
		labelCUSTNo.setBackground(Color.LIGHT_GRAY);
		labelCUSTNo.setBounds(left, top, labelWidth, height);
		labelCUSTNo.setFont(PosFormUtil.getLabelFont());
		detailsControlPanel.add(labelCUSTNo);
		
		left=labelCUSTNo.getX()+labelCUSTNo.getWidth()+1;
		
		mTxtCUSTNo=new JTextField();
		mTxtCUSTNo.setBounds(left, top, txtWidth, height);
		mTxtCUSTNo.setFont(PosFormUtil.getTextFieldFont());
		mTxtCUSTNo.setEditable(false);
		mTxtCUSTNo.setHorizontalAlignment(SwingConstants.LEFT);
		detailsControlPanel.add(mTxtCUSTNo);
		
		left=3;
		top=mTxtCUSTNo.getY()+mTxtCUSTNo.getHeight()+2;
		
		final JLabel labelCUSTCardNo=new JLabel("Card No. : ");
		labelCUSTCardNo.setBorder(new EmptyBorder(2, 2, 2, 2));
		labelCUSTCardNo.setOpaque(true);
		labelCUSTCardNo.setBackground(Color.LIGHT_GRAY);
		labelCUSTCardNo.setBounds(left, top, labelWidth, height);
		labelCUSTCardNo.setFont(PosFormUtil.getLabelFont());
		detailsControlPanel.add(labelCUSTCardNo);
		
		left=labelCUSTCardNo.getX()+labelCUSTCardNo.getWidth()+1;
		
		mTxtCUSTCardNo=new JTextField();
		mTxtCUSTCardNo.setBounds(left, top, txtWidth, height);
		mTxtCUSTCardNo.setFont(PosFormUtil.getTextFieldFont());
		mTxtCUSTCardNo.setEditable(false);
		mTxtCUSTCardNo.setHorizontalAlignment(SwingConstants.LEFT);
		detailsControlPanel.add(mTxtCUSTCardNo);
		
		left=3;
		top=mTxtCUSTCardNo.getY()+mTxtCUSTCardNo.getHeight()+2;

		mLabelAmount=new JLabel();
		mLabelAmount.setText(PosFormUtil.getMnemonicString("Amount : ", 'A'));
		mLabelAmount.setBorder(new EmptyBorder(2, 2, 2, 2));
		mLabelAmount.setOpaque(true);
		mLabelAmount.setBackground(Color.LIGHT_GRAY);
		mLabelAmount.setBounds(left, top, labelWidth, height);
		mLabelAmount.setFont(PosFormUtil.getLabelFont());
		detailsControlPanel.add(mLabelAmount);

		left=mLabelAmount.getX()+mLabelAmount.getWidth()+1;
		mTxtAmount=new PosTouchableDigitalField(mParent,txtWidth-exactButtonWidth);
		mTxtAmount.setMnemonic('A');
		mTxtAmount.setHorizontalTextAlignment(JTextField.RIGHT);
		mTxtAmount.hideResetButton(true);
		mTxtAmount.setLocation(left, top);
		mTxtAmount.setFont(PosFormUtil.getTextFieldFont());
		mTxtAmount.getTexFieldComponent().setDocument(PosNumberUtil.createDecimalDocument());
		mTxtAmount.setListner(new IPosTouchableFieldListner() {
			
			@Override
			public void onValueSelected(Object value) {
				onTenderAmountChanged(String.valueOf(getExactAmount()));
			}
			
			@Override
			public void onReset() {
				// TODO Auto-generated method stub
				
			}
		});
		mTxtAmount.setFiledDocumentListner(new DocumentListener() {

			@Override
			public void removeUpdate(DocumentEvent e) {
				onTenderAmountChanged(mTxtAmount.getText());}

			@Override
			public void insertUpdate(DocumentEvent e) {
				onTenderAmountChanged(mTxtAmount.getText());
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				onTenderAmountChanged(mTxtAmount.getText());}
		});
		
		detailsControlPanel.add(mTxtAmount);
		
		left=mTxtAmount.getX()+mTxtAmount.getWidth()+1;
		
		PosButton button=new PosButton("Exact");
		button.setMnemonic('x');
		button.setBounds(left, top+1, exactButtonWidth, height);
		button.setImage(EXACT_KEY_IMAGE);
		button.setTouchedImage(EXACT_KEY_IMAGE_TOUCH);
		button.setTag(getBillTotal());
		button.setOnClickListner(mExactAmountButton);
		detailsControlPanel.add(button);
				
	}
	
	/**
	 * 
	 */
	private IPosButtonListner mExactAmountButton=new IPosButtonListner() {
		@Override
		public void onClicked(PosButton button) {
						
			setExactAmount();
			
		}
	};
	
	/**
	 * 
	 */
	public void setExactAmount(){
	

		final double amount=getExactAmount();
		mTxtAmount.setText(PosCurrencyUtil.format(amount));
		mTxtAmount.selectAll();
		mTxtAmount.requestFocus();
		onTenderAmountChanged(String.valueOf(amount));
		
	}

	private double getExactAmount(){
		double exactAmount = mParent.getBillTotalAmount()+mParent.getRoundingAdjustment()-mParent.getTenderedAmount()+getTenderAmount();
		return (exactAmount>0)?exactAmount:0;
		
		
	}
	
	private void setCompanyDetails(){	
//		initControls();
		loadCompanyItemList();
		createCompanyItems();
		
	}

//	private void initControls(){		
//		loadCompanyItem();
//		createCompanyItems();			
//	}	

	private void loadCompanyItemList(){
		PosCompanyItemProvider companyItemProvider=new PosCompanyItemProvider();
		mPosCompanyeItemList=companyItemProvider.getItemList();
	}
	
//	private void loadCompany(){
//		PosCompanyItemProvider smProvider=new PosCompanyItemProvider();
//		mPosCompanyeItemList=smProvider.getCompanyItemList();
//	}

	private void createCompanyItems(){	
		mCompanyItemContainer=new JPanel();
		mCompanyItemContainer.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
		mCompanyItemContainer.setLayout(createLayout());		

		final int top=getHeight()-COMPANY_LIST_PANEL_HEIGHT-2;
		final int left=-2;//mTxtCompanyInfo.getX();

		mCompanyItemContainer.setBounds(left,top,  COMPANY_LIST_PANEL_WIDTH, COMPANY_LIST_PANEL_HEIGHT);	
		mCompanyItemControlList=new ArrayList<PosCompanyItemControl>();
		mCompanyItemControlMap=new HashMap<String, PosCompanyItemControl>();
		mCurrentPage++;
		
		for(int index=0; index<mItemsPerPage; index++){
			PosCompanyItemControl itemControl=new PosCompanyItemControl();
			itemControl.setOnSelectListner(posPaymentSelectedListner);
			addKeyStroke(itemControl, index);
			mCompanyItemControlList.add(itemControl);
			mCompanyItemContainer.add(itemControl);
		}
//		add(mCompanyItemContainer);
//		mCompanyItemContainer.setVisible(false);/*Here Hide the Visibility */
		createScrollButton();			
		setSrcollButtonStatus();

	}

	public void setCancel(Boolean canCancel){
		mButtonCancel.setEnabled(canCancel);
	}

	private void createScrollButton()
	{		
		int top=mCompanyItemContainer.getY();
		int left=mCompanyItemContainer.getX()+mCompanyItemContainer.getWidth()-2;
		JPanel scrollPanel= new JPanel();
		scrollPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
		scrollPanel.setBounds(left,top,  IMAGE_RIGHT_PANEL_BUTTON_WIDTH+PANEL_CONTENT_H_GAP*2, mCompanyItemContainer.getHeight());	
		scrollPanel.setLayout(new FlowLayout(FlowLayout.LEFT,3,5));
//		add(scrollPanel);

		mImageButtonScrollUp=new PosButton();
		mImageButtonScrollUp.setText("");
		mImageButtonScrollUp.setImage(PosResUtil.getImageIconFromResource(IMAGE_BUTTON_SCROLL_UP));
		mImageButtonScrollUp.setTouchedImage(PosResUtil.getImageIconFromResource(IMAGE_BUTTON_SCROLL_UP_TOUCH));		
		mImageButtonScrollUp.setHorizontalAlignment(SwingConstants.CENTER);		
		mImageButtonScrollUp.setBounds(left, top, IMAGE_RIGHT_PANEL_BUTTON_WIDTH,IMAGE_RIGHT_PANEL_BUTTON_HEIGHT);
		mImageButtonScrollUp.registerKeyStroke(KeyEvent.VK_PAGE_UP);
		mImageButtonScrollUp.setOnClickListner(imgScrollUpButtonListner);				
//		scrollPanel.add(mImageButtonScrollUp);		

		top=mImageButtonScrollUp.getY()+mImageButtonScrollUp.getHeight()+ PANEL_CONTENT_V_GAP;

		mImageButtonScrollDown=new PosButton();
		mImageButtonScrollDown.setText("");
		mImageButtonScrollDown.setImage(PosResUtil.getImageIconFromResource(IMAGE_BUTTON_SCROLL_DOWN));
		mImageButtonScrollDown.setTouchedImage(PosResUtil.getImageIconFromResource(IMAGE_BUTTON_SCROLL_DOWN_TOUCH));		
		mImageButtonScrollDown.setHorizontalAlignment(SwingConstants.CENTER);		
		mImageButtonScrollDown.setBounds(left, top, IMAGE_RIGHT_PANEL_BUTTON_WIDTH,IMAGE_RIGHT_PANEL_BUTTON_HEIGHT);
		mImageButtonScrollDown.registerKeyStroke(KeyEvent.VK_PAGE_DOWN);
		
		mImageButtonScrollDown.setOnClickListner(imgScrollDownButtonListner);
//		scrollPanel.add(mImageButtonScrollDown);

		mImageButtonSearch=new PosButton();
		mImageButtonSearch.setText("Search ");
		mImageButtonSearch.setImage(PosResUtil.getImageIconFromResource(IMAGE_BUTTON_SEARCH));
		mImageButtonSearch.setTouchedImage(PosResUtil.getImageIconFromResource(IMAGE_BUTTON_SEARCH_TOUCH));		
		mImageButtonSearch.setHorizontalAlignment(SwingConstants.CENTER);		
		mImageButtonSearch.setBounds(left, top, IMAGE_RIGHT_PANEL_SEARCH_BUTTON_WIDTH,IMAGE_RIGHT_PANEL_SEARCH_BUTTON_HEIGHT);
		mImageButtonSearch.setOnClickListner(imgSearchButtonListner);
//		scrollPanel.add(mImageButtonSearch);
		
//		scrollPanel.setVisible(false);/*Here Hide the Visibility */
		
	}

	private void previousButtonClick(){
		mCurrentPage--;
		displayCurPageItems();
		setSrcollButtonStatus();
	}

	private void nextButtonClick(){	
		mCurrentPage++;
		displayCurPageItems();
		setSrcollButtonStatus();
	}

	/**
	 * @param btnControl
	 * @param item
	 * @param index
	 */
	private void addKeyStroke(PosCompanyItemControl itemControl ,int index){
	
		itemControl.setAutoMnemonicEnabled(false);
		final KeyStroke stroke =KeyStroke.getKeyStroke(112+index,0);
		itemControl.registerKeyStroke(stroke);
	}
	private void displayCurPageItems(){
		if(mSelectedCompanyItemControl!=null)
			mSelectedCompanyItemControl.setSelected(false);
		mSelectedCompanyItemControl=null;
		
		int itemIndex=mCurrentPage*mItemsPerPage-mItemsPerPage;
		PosCompanyItemControl itemControl=null;
		PosCompanyItemControl tempCompany=null;
		for(int controlIndex=0; controlIndex<mItemsPerPage; controlIndex++){
			itemControl=mCompanyItemControlList.get(controlIndex);
			if(itemIndex<mPosCompanyeItemList.size()){
				itemControl.setCompanyItem((BeanCompany)mPosCompanyeItemList.get(itemIndex));
				itemControl.setVisible(true);
				itemIndex++;
				if(mSelectedCompany!=null && mSelectedCompany.getCode().equals(itemControl.getCompanyItem().getCode()))
					tempCompany=itemControl;
			}
			else
				itemControl.setVisible(false);
		}
		if(tempCompany!=null)
			tempCompany.setSelected(true);
	}

	private void setSrcollButtonStatus(){
		mImageButtonScrollDown.setEnabled(true);
		mImageButtonScrollUp.setEnabled(true);
		if(mCurrentPage*mItemsPerPage>=mPosCompanyeItemList.size()) mImageButtonScrollDown.setEnabled(false);
		if(mCurrentPage<=1) mImageButtonScrollUp.setEnabled(false);
	}	

	private  IPosButtonListner imgScrollUpButtonListner= new PosButtonListnerAdapter() {
		@Override
		public void onClicked(PosButton button) {
			previousButtonClick();           
		}
	};	

	private  IPosButtonListner imgScrollDownButtonListner= new PosButtonListnerAdapter() {
		@Override
		public void onClicked(PosButton button) {			
			nextButtonClick();
		}
	};	

	private  IPosButtonListner imgSearchButtonListner= new PosButtonListnerAdapter() {
		@Override
		public void onClicked(PosButton button) {			
			showSearchForm();
		}
	};	

	private void showSearchForm(){
		PosExtSearchForm serachForm=new PosExtSearchForm(mPosCompanyeItemList);
//		serachForm.setListner(new PosSoftKeypadAdapter(){
//			@Override
//			public void onAccepted(int index) {
//				int page=(index/mItemsPerPage);
//				page=((index%mItemsPerPage>0)?page+1:page); 
//				mCurrentPage=(page>0)?page:page+1;
//				displayCurPageItems();
//				setSrcollButtonStatus();
//				//				}
//				final int firstIndex=(mCurrentPage-1)*mItemsPerPage;
//				//					final int lastIndex=mCurrentPage*mItemsPerPage;
//				mCompanyItemControlList.get(index-firstIndex).setSelected(true);
//
//			}
//		});
		serachForm.setListner(new PosItemExtSearchFormAdapter() {
			
			@Override
			public boolean onAccepted(Object sender,IPosSearchableItem item) {
//				addPosItem(String.valueOf(item.getItemCode()));
				
				selectCompanyControl((BeanCompany)item);
				return true;
			}
		});
		PosFormUtil.showLightBoxModal(mParent,serachForm);
	}
	
	/**
	 * @param item
	 */
	private void selectCompanyControl(BeanCompany item){
		
		int arrayItemIndex=mPosCompanyeItemList.indexOf(item);
		int pageItemIndex=arrayItemIndex+1;
		int page=(pageItemIndex/mItemsPerPage);
		page=((pageItemIndex%mItemsPerPage>0)?page+1:page); 
		mCurrentPage=(page>0)?page:page+1;
		displayCurPageItems();
		setSrcollButtonStatus();

		final int firstIndex=(mCurrentPage-1)*mItemsPerPage;
		mCompanyItemControlList.get(arrayItemIndex-firstIndex).setSelected(true);
		
	}

	private FlowLayout createLayout(){
		FlowLayout flowLayout=new FlowLayout();
		flowLayout.setVgap(3);
		flowLayout.setHgap(3);
		flowLayout.setAlignment(FlowLayout.LEFT);
		return flowLayout;
	}


	private IPosSelectButtonListner posPaymentSelectedListner=new IPosSelectButtonListner() {
		@Override
		public void onSelected(PosSelectButton button) {		
			if(mSelectedCompanyItemControl!=null)
				mSelectedCompanyItemControl.setSelected(false);
			mSelectedCompanyItemControl=(PosCompanyItemControl)button;	
			setSelectedCompany(mSelectedCompanyItemControl.getCompanyItem());
			onTenderAmountChanged(String.valueOf(mParent.getBillTotalAmount()));
			mCompanyId=mSelectedCompany.getId();
			mCompanyName=mSelectedCompanyItemControl.getCompanyItem().getName();
		}
	};
	
	
	private void setSelectedCompany(BeanCompany company){
		
		mSelectedCompany=company;
		mTxtAccount.setText(mSelectedCompany.getAccountCode());
		
		if (PosEnvSettings.getInstance().isEnabledHMSIntegration() && 
				mOrderHeader.getCustomer().getCustType().getCode().equals(PosCustomerTypeProvider.ROOM_TYPE_CODE)){
			mTxtName.setText(mOrderHeader.getOrderCustomer().getName());
		}else{
			mTxtName.setText(mSelectedCompany.getName());
		}
		mTxtCustType.setText(mSelectedCompany.getCustType().getName());
		mTxtGSTin.setText(mSelectedCompany.getTinNo());
		mTxtCUSTNo.setText(mSelectedCompany.getCSTNo());
		mTxtCUSTCardNo.setText(mSelectedCompany.getCardNumber());
		mTxtCode.setText(mSelectedCompany.getCode());
		mTxtAmount.setText(PosCurrencyUtil.format(mParent.getBillTotalAmount()+mParent.getRoundingAdjustment()));
		setExactAmount();
		
	}


	/**
	 * @return the mCompanyId
	 */
	public final int getCompanyId() {
		return mSelectedCompany.getId();
	}

	/**
	 * @param mCompanyId the mCompanyId to set
	 */
//	public final void setCompanyId(int companyId) {
//		this.mCompanyId = companyId;
//	}

	/**
	 * @return the mCompanyName
	 */
	public final String getCompanyName() {
		return mSelectedCompany.getName();
	}

	/**
	 * @param mCompanyName the mCompanyName to set
	 */
//	public final void setCompanyName(String companyName) {
//		this.mCompanyName = companyName;
//	}	

	public BeanCompany getCompanyItem(){
		return mSelectedCompany;
	}

	//	public double getAmountPaid(){
	//		return Double.parseDouble(mTxtAmount.getText());
	//	}

	@Override
	public boolean onValidating() {
		
//		||mTxtAccount.getText().trim().equals("")
		
		if(mTxtAmount.getText().trim().equals("")
				||mTxtAmount.getText().trim().equals("")
				||mTxtCode.getText().trim().equals("")
				||mTxtName.getText().trim().equals("")
				){
			PosFormUtil.showErrorMessageBox(mParent, "Company details are not valid.");
			return false;
		}
		return true;
	}

	@Override
	public double getTenderAmount() {
		double amount=(!mTxtAmount.getText().equals(""))?Double.parseDouble(mTxtAmount.getText()):0;
		return	amount;

	}
	
	public void setTenderAmount(double amount){
		mTxtAmount.setText(PosCurrencyUtil.format(amount));
		mTxtAmount.selectAll();
		mTxtAmount.requestFocus();
	}

	@Override
	public void reset() {
		

		if(mParent.getOrderHeader()!=null && 
				mParent.getOrderHeader().getCustomer().getCustType().getCode().equals(PosCustomerTypeProvider.ROOM_TYPE_CODE)){
			
			mLabelCode.setText("Room No. :");
			
		}
		if(mSelectedCompanyItemControl!=null)
			mSelectedCompanyItemControl.setSelected(false);
		mSelectedCompany=null;
		mSelectedCompanyItemControl=null;
		mTxtAccount.setText("");
		mTxtCode.setText("");
//		mTxtCompanyInfo.setText("");
		mTxtName.setText("");
		mTxtCustType.setText("");
		mTxtGSTin.setText("");
		mTxtCUSTNo.setText("");
		mTxtCUSTCardNo.setText("");
		
		mTxtAmount.setText(PosCurrencyUtil.format(0));
		mTxtAmount.selectAll();
		mTxtAmount.requestFocus();
		
		mOrderHeader=mParent.getOrderHeader();
		
	
		if(mOrderHeader!=null && mOrderHeader.getCustomer().isIsArCompany())
			selectCompanyControl((BeanCompany)mOrderHeader.getCustomer());
		mTxtAmount.requestFocus();
//		mParent.mCantApplyDiscount=false;
		
	}

	@Override
	public void onGotFocus() {
		mParent.getCardPaymentPanel().reset();
//		if(mParent.getBalanceAmount()<0){
//			mParent.setRoundingAdjustment(0);
//		}
		double roundedAmount=0;
		double roundingAdjustment ;
		mTxtAmount.reset();
		if(mParent.getBalanceAmount()<0){
 
		
			if (PosFormUtil.canRound(PaymentMode.Company)) {
			
				roundedAmount = PosCurrencyUtil.nRound(mParent.getBalanceAmount()+mParent.getRoundingAdjustment());
				roundingAdjustment = roundedAmount - (mParent.getBalanceAmount()+mParent.getRoundingAdjustment());
			}else{
				roundedAmount = mParent.getBalanceAmount()-(-1 * mParent.getRoundingAdjustment());
		
		
				roundingAdjustment =0;
			}
			
			mParent.setRoundingAdjustment((roundingAdjustment==0)?0:-roundingAdjustment);
		}
		mTxtAmount.setText(PosCurrencyUtil.format(roundedAmount<0?(roundedAmount*-1):0));
	}

	public BeanOrderPayment getPayment(
			BeanOrderHeader orderHeader) {
		PosCreditCustomerPaymentPanel posCompanyPayment = this;
		BeanOrderPayment orderPayment = new BeanOrderPayment();
		orderPayment.setOrderId(orderHeader.getOrderId());
		orderPayment.setPaymentMode(PaymentMode.Company);
		orderPayment.setPaidAmount(posCompanyPayment.getTenderAmount());
		orderPayment.setCompanyId(posCompanyPayment.getCompanyId());
		orderPayment.setCompanyName(posCompanyPayment.getCompanyName());
		return orderPayment;
	}
	
	/*
	 * 
	 */
	
	public void clear() {
		
		if(mSelectedCompanyItemControl!=null)
			mSelectedCompanyItemControl.setSelected(false);
		mSelectedCompany=null;
		mSelectedCompanyItemControl=null;
		mTxtAccount.setText("");
		mTxtCode.setText("");
//		mTxtCompanyInfo.setText("");
		mTxtName.setText("");
		mTxtCustType.setText("");
		mTxtGSTin.setText("");
		mTxtCUSTNo.setText("");
		mTxtCUSTCardNo.setText("");
		
		mTxtAmount.setText(PosCurrencyUtil.format(0));
		mTxtAmount.selectAll();
		mTxtAmount.requestFocus();
		
		mOrderHeader=mParent.getOrderHeader();
		 
		
		
	}

}
