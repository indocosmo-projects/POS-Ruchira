/**
 * 
 */
package com.indocosmo.pos.forms;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;

import com.indocosmo.pos.common.PosEnvSettings;
import com.indocosmo.pos.common.utilities.PosAccessPermissionsUtil;
import com.indocosmo.pos.common.utilities.PosCurrencyUtil;
import com.indocosmo.pos.common.utilities.PosFormUtil;
import com.indocosmo.pos.common.utilities.PosNumberUtil;
import com.indocosmo.pos.common.utilities.PosOrderUtil;
import com.indocosmo.pos.common.utilities.PosPasswordUtil;
import com.indocosmo.pos.common.utilities.PosResUtil;
import com.indocosmo.pos.data.beans.BeanDiscount;
import com.indocosmo.pos.data.beans.BeanDiscount.PermittedLevel;
import com.indocosmo.pos.data.beans.BeanOrderDiscount;
import com.indocosmo.pos.data.beans.BeanOrderHeader;
import com.indocosmo.pos.data.beans.BeanOrderPaymentHeader;
import com.indocosmo.pos.data.beans.BeanReason;
import com.indocosmo.pos.data.beans.BeanTax;
import com.indocosmo.pos.data.providers.shopdb.PosDiscountItemProvider;
import com.indocosmo.pos.data.providers.shopdb.PosReasonItemProvider;
import com.indocosmo.pos.forms.PosObjectBrowserForm.ItemSize;
import com.indocosmo.pos.forms.components.buttons.PosButton;
import com.indocosmo.pos.forms.components.buttons.PosSelectButton;
import com.indocosmo.pos.forms.components.buttons.listners.IPosButtonListner;
import com.indocosmo.pos.forms.components.buttons.listners.IPosSelectButtonListner;
import com.indocosmo.pos.forms.components.buttons.listners.PosButtonListnerAdapter;
import com.indocosmo.pos.forms.components.itemcontrols.PosDiscountItemControl;
import com.indocosmo.pos.forms.components.keypads.listners.PosSoftKeypadAdapter;
import com.indocosmo.pos.forms.components.objectbrowser.IPosBrowsableItem;
import com.indocosmo.pos.forms.components.search.IPosSearchableItem;
import com.indocosmo.pos.forms.components.textfields.PosTouchableNumericField;
import com.indocosmo.pos.forms.components.textfields.listners.IPosTouchableFieldListner;
import com.indocosmo.pos.forms.listners.IPosObjectBrowserListner;
import com.indocosmo.pos.forms.search.PosExtSearchForm;
import com.indocosmo.pos.forms.search.listener.adapter.PosItemExtSearchFormAdapter;

/**
 * @author jojesh
 * 
 */
@SuppressWarnings("serial")
public final class PosOrderPreBillDiscountForm extends PosBaseForm {

	 
	private static final int PANEL_CONTENT_H_GAP = 8;
	private static final int PANEL_CONTENT_V_GAP =8;
	
	private static final String RESET_BUTTON_NORMAL = "ctrl_reset.png";
	private static final String RESET_BUTTON_TOUCHED = "ctrl_reset_touch.png";

	private static final int IMAGE_RIGHT_PANEL_BUTTON_WIDTH = 90;
	private static final int IMAGE_RIGHT_PANEL_BUTTON_HEIGHT = 87;

	private static final int IMAGE_RIGHT_PANEL_SEARCH_BUTTON_WIDTH = 90;
	private static final int IMAGE_RIGHT_PANEL_SEARCH_BUTTON_HEIGHT = 60;

 
	private static final String IMAGE_BUTTON_SCROLL_UP = "dlg_buton_up.png";
	private static final String IMAGE_BUTTON_SCROLL_UP_TOUCH = "dlg_buton_up_touch.png";

	private static final String IMAGE_BUTTON_SCROLL_DOWN = "dlg_buton_down.png";
	private static final String IMAGE_BUTTON_SCROLL_DOWN_TOUCH = "dlg_buton_down_touch.png";

	private static final String IMAGE_BUTTON_SEARCH = "comp_search_normal.png";
	private static final String IMAGE_BUTTON_SEARCH_TOUCH = "comp_search_touch.png";

	private static final int DISCOUNT_ROW_COUNT = 3;
	private static final int DISCOUNT_COLUMN_COUNT = 4;

	private static final int DISCOUNT_LIST_PANEL_WIDTH = PosDiscountItemControl.BUTTON_WIDTH
			* DISCOUNT_COLUMN_COUNT + PANEL_CONTENT_H_GAP * 3;
	private static final int DISCOUNT_LIST_PANEL_HEIGHT = PosDiscountItemControl.BUTTON_HEIGHT
			* DISCOUNT_ROW_COUNT + PANEL_CONTENT_V_GAP * DISCOUNT_ROW_COUNT  ;

	private static final int DISCOUNT_DET_PANEL_HEIGHT =129;
			
	private static final int FORM_HEIGHT =   412; 
	private static final int FORM_WIDTH = 730;
	
	
	
	private int mCurrentPage = 0;
	private int mItemsPerPage = DISCOUNT_ROW_COUNT * DISCOUNT_COLUMN_COUNT;

 
 

	private JLabel mLabelName;
	private JTextField mTxtName;

	private JLabel mlabelDiscount;
	private PosTouchableNumericField mTxtDiscount;
	private JLabel mLabelDiscountSymbol;

	private JLabel mLabelAmount;
	private JTextField mTxtAmount;

	private BeanDiscount mSelectedDiscount;
	private PosButton mBtnReasonSelect;
	private PosButton mBtnReasonReset;
	private JTextArea mTxtReason;
	
	private PosOrderEntryForm mParent;

	 private BeanOrderHeader mOrderHeader;

//	private boolean mPrintRecipt = false;
	private double mTaxBeforeBillDiscount;
	private double mRoundAdjustMentAmount = 0;
	
	private static PosReasonItemProvider mReasonItemProvider;
	private ArrayList<BeanDiscount> mPosDiscountItemList;
	private ArrayList<PosDiscountItemControl> mDiscountItemControlList;
	private ArrayList<BeanReason> mReasonList;

	private PosButton mImageButtonScrollUp;
	private PosButton mImageButtonScrollDown;
	private PosButton mImageButtonSearch;

	private JPanel mDiscountItemContainer;
	private PosButton mButtonCancel;
	// private JTextField mTxtCompanyInfo;
	private PosDiscountItemControl mSelectedDiscountItemControl;

	private PosDiscountItemProvider mDiscountProvider;
	
	private JPanel mContentPanel;

	private double mBillTaxAmount;
	private boolean  mIsDirty ;
	
	/**
	 * @param title
	 * @param cPanelwidth
	 * @param cPanelHeight
	 */
	public PosOrderPreBillDiscountForm(PosOrderEntryForm parent ) {
		
		super("Bill Discount", FORM_WIDTH,FORM_HEIGHT);	
		setCancelButtonCaption("Close");
		setResetButtonVisible(true);

		mParent =(PosOrderEntryForm) parent;
	 	mReasonItemProvider = new PosReasonItemProvider();
	 	mDiscountProvider = new PosDiscountItemProvider();
	 	mOrderHeader = mParent.getOrderObject();
	 	
	 	createUI();
	 	mSelectedDiscount=mOrderHeader.getPreBillDiscount();
	 	displayCurPageItems();
	 	
//	 	if(mOrderHeader.getPreBillDiscount() !=null || mOrderHeader.getPreBillDiscount() !=mDiscountProvider.getNoneDiscount())
	 		
		reset();
		mIsDirty=false;
	}


    public boolean isDirty(){
    	return mIsDirty;
    }
	/**
	 * 
	 */
	private void createUI() {
 

		PosButton removeButton= addButtonsToBottomPanel("Remove", removeBtnListner, 2);
		removeButton.setImage(IMAGE_BUTTON_RESET,IMAGE_BUTTON_RESET_TOUCH );
		removeButton.setMnemonic('m');

		setDetailControls();
		setDiscountDetails();
		
		removeButton.setVisible(!hasPartialPayments());
		setOkButtonVisible(!hasPartialPayments());
		setResetButtonVisible(!hasPartialPayments());
	}
	IPosButtonListner  removeBtnListner=new IPosButtonListner() {

		@Override
		public void onClicked(PosButton button) {
			if (mSelectedDiscountItemControl != null)
				mSelectedDiscountItemControl.setSelected(false);
			mSelectedDiscountItemControl=null;
			mSelectedDiscount = mDiscountProvider.getNoneDiscount();
			mBillTaxAmount=0;
			setValues();
			
		}
	};
 
	private boolean hasPartialPayments(){
		
		boolean result=false;
		if(mOrderHeader.getOrderPaymentHeaders()!=null){
			for(BeanOrderPaymentHeader payHdr:mOrderHeader.getOrderPaymentHeaders()){
				
				if( !payHdr.isAdvance()){
					result=true;
					break;
				}
			}
		}
		return result;
		
	}
	/**
	 * 
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indocosmo.pos.forms.PosBaseForm#onOkButtonClicked()
	 */
	@Override
	public boolean onOkButtonClicked() {
			
		if(! validateDiscountPrice(PosNumberUtil.parseDoubleSafely(String.valueOf(mTxtDiscount.getText()))))
			return false;
		
		if (mSelectedDiscount!=null){ 
				
				try {
						if (mSelectedDiscount!=mOrderHeader.getPreBillDiscount()){
							mOrderHeader.setPreBillDiscount(mSelectedDiscount);
							mIsDirty=true;
						}
				} catch (Exception e) {	 
					displayCurPageItems();
					e.printStackTrace();
				}
				
				
			}	
			return true;
		  
		}
 

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indocosmo.pos.forms.PosBaseForm#onResetButtonClicked()
	 */
	@Override
	public void onResetButtonClicked() {
		super.onResetButtonClicked();
		reset();
	}

	 
 

	/**
	 * @return
	 */
 
	/**
	 * @return
	 */
	public double getTaxBeforeBillDiscount() {
	
		double tax = 0;
//		if (mOrderBill != null) {
//			tax = mOrderBill.getBillTax();
//		}
		
		tax=mTaxBeforeBillDiscount;

		return tax;
	}

	/**
	 * @return
	 */
	public double getRoundingAdjustment() {
		return mRoundAdjustMentAmount;
	}

	/**
	 * @param adjAmount
	 */
 
 
  
	  
 
	private void setDetailControls() {
		 			
		JPanel detailsControlPanel = new JPanel();
		detailsControlPanel.setBounds(PANEL_CONTENT_H_GAP , PANEL_CONTENT_V_GAP,  FORM_WIDTH - PANEL_CONTENT_H_GAP  *2,  DISCOUNT_DET_PANEL_HEIGHT);
		detailsControlPanel.setLayout(null);
		
//		detailsControlPanel.setBackground(LABEL_FG_COLOR);
		mContentPanel.add(detailsControlPanel);

		int left = PANEL_CONTENT_H_GAP/4+1;
		int top = PANEL_CONTENT_V_GAP/5+2;
		int width = 105;
		int height = 40;
		int txtWidth = 200;
		int symbolWidth = 50;

		mLabelName = new JLabel("Name :");
		mLabelName.setBorder(new EmptyBorder(2, 2, 2, 2));
		mLabelName.setOpaque(true);
		mLabelName.setBackground(Color.LIGHT_GRAY);
		mLabelName.setBounds(left, top, width, height);
		mLabelName.setFont(PosFormUtil.getLabelFont());
		detailsControlPanel.add(mLabelName);

		mTxtName = new JTextField();
		left = mLabelName.getX() + mLabelName.getWidth()+1;
		mTxtName.setBounds(left, top, txtWidth + 183, height);
		mTxtName.setFont(PosFormUtil.getTextFieldFont());
		mTxtName.setEditable(false);
		mTxtName.setHorizontalAlignment(SwingConstants.LEFT);
		detailsControlPanel.add(mTxtName);

		left = PANEL_CONTENT_H_GAP/4+1;
		top = mTxtName.getY() + mTxtName.getHeight() + PANEL_CONTENT_V_GAP/5+1;
		mlabelDiscount = new JLabel();
		mlabelDiscount.setText(PosFormUtil.getMnemonicString("Discount : ", 'n'));
		mlabelDiscount.setBorder(new EmptyBorder(2, 2, 2, 2));
		mlabelDiscount.setOpaque(true);
		mlabelDiscount.setBackground(Color.LIGHT_GRAY);
		mlabelDiscount.setBounds(left, top+1, width, height);
		mlabelDiscount.setFont(PosFormUtil.getLabelFont());
		detailsControlPanel.add(mlabelDiscount);

		left = mlabelDiscount.getX() + mlabelDiscount.getWidth();
		mTxtDiscount = new PosTouchableNumericField(PosOrderPreBillDiscountForm.this,txtWidth+4 - symbolWidth
				- 1);
//		mTxtDiscount.setBounds(left, top, txtWidth - symbolWidth
//				- PANEL_CONTENT_H_GAP, height);
		mTxtDiscount.setLocation(left, top);
		mTxtDiscount.setFont(PosFormUtil.getTextFieldFont());
		mTxtDiscount.setEditable(false);
		mTxtDiscount.hideResetButton(true);
		mTxtDiscount.setMnemonic('n');
 
		mTxtDiscount.setListner(new IPosTouchableFieldListner() {
			
			@Override
			public void onValueSelected(Object value) {

				validateDiscountPrice(PosNumberUtil.parseDoubleSafely(String.valueOf(value)));
			}
			
			@Override
			public void onReset() {
				// TODO Auto-generated method stub
				
			}
		});
		
		mTxtDiscount.addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent e) {
				
//				validateDiscountPrice(PosNumberUtil.parseDoubleSafely(String.valueOf(mTxtDiscount.getText())));
			}
			
			@Override
			public void focusGained(FocusEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		detailsControlPanel.add(mTxtDiscount);

		left = mTxtDiscount.getX() + mTxtDiscount.getWidth()+1;
		mLabelDiscountSymbol = new JLabel();
		mLabelDiscountSymbol.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
		mLabelDiscountSymbol.setHorizontalAlignment(SwingConstants.CENTER);
		mLabelDiscountSymbol.setBounds(left, top+1, symbolWidth, 41);
		mLabelDiscountSymbol.setFont(PosFormUtil.getLabelFont());
		detailsControlPanel.add(mLabelDiscountSymbol);

		left = PANEL_CONTENT_H_GAP/4+1;
		top = mTxtDiscount.getY() + mTxtDiscount.getHeight()
				+ PANEL_CONTENT_V_GAP/5+1;
		mLabelAmount = new JLabel("Amount : ");
		mLabelAmount.setBorder(new EmptyBorder(2, 2, 2, 2));
		mLabelAmount.setOpaque(true);
		mLabelAmount.setBackground(Color.LIGHT_GRAY);
		mLabelAmount.setBounds(left, top, width, height);
		mLabelAmount.setFont(PosFormUtil.getLabelFont());
		detailsControlPanel.add(mLabelAmount);

		mTxtAmount = new JTextField();
		left = mLabelAmount.getX() + mLabelAmount.getWidth()+1;
		mTxtAmount.setBounds(left, top, txtWidth+2, height);
		mTxtAmount.setFont(PosFormUtil.getTextFieldFont());
		mTxtAmount.setEditable(false);
		mTxtAmount.setHorizontalAlignment(SwingConstants.RIGHT);
		mTxtAmount.setDocument(PosNumberUtil.createDecimalDocument());
		 
		detailsControlPanel.add(mTxtAmount);

		top = PANEL_CONTENT_V_GAP/5+2;
		left = mTxtName.getX() + mTxtName.getWidth() +1;

		mBtnReasonSelect = new PosButton("Reason");
		mBtnReasonSelect.setMnemonic('e');
		mBtnReasonSelect.setBounds(left, top, 170, height);
		mBtnReasonSelect.setImage("item_edit_discount_select.png");
		mBtnReasonSelect.setTouchedImage("item_edit_discount_select_touch.png");
		mBtnReasonSelect.setOnClickListner(reasonsListener);
		detailsControlPanel.add(mBtnReasonSelect);

		left = mBtnReasonSelect.getX() + mBtnReasonSelect.getWidth()+1;
		mBtnReasonReset = new PosButton();
		mBtnReasonReset.setBounds(left, top, symbolWidth, height);
		mBtnReasonReset.setImage(RESET_BUTTON_NORMAL);
		mBtnReasonReset.setTouchedImage(RESET_BUTTON_TOUCHED);
		mBtnReasonReset.setOnClickListner(new IPosButtonListner() {

			@Override
			public void onClicked(PosButton button) {
				mTxtReason.setText("");
			}
		});
		detailsControlPanel.add(mBtnReasonReset);

		final int scrollHeight = 85;
		top = mBtnReasonSelect.getY() + mBtnReasonSelect.getHeight()
				+ PANEL_CONTENT_V_GAP/5+1;
		left = mLabelDiscountSymbol.getX() + mLabelDiscountSymbol.getWidth()
				+ PANEL_CONTENT_H_GAP/4+1;
		mTxtReason = new JTextArea();
		mTxtReason.setLineWrap(true);
		mTxtReason.setWrapStyleWord(false);
		mTxtReason.setFont(PosFormUtil.getTextFieldFont());
		mTxtReason.addMouseListener(onDiscDescriptionfocus);
		mTxtReason.setBounds(left, top, txtWidth * 2, scrollHeight);
		mTxtReason.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(mTxtReason);
		scrollPane.setBounds(left, top, txtWidth * 2, scrollHeight);
		detailsControlPanel.add(scrollPane);

	}
/*
 * 
 */
	private boolean validateDiscountPrice(double discount){
		boolean result=false;
//		double discount =PosNumberUtil.parseDoubleSafely(String.valueOf(mTxtDiscount.getText()));
		if(discount<0){
			PosFormUtil.showErrorMessageBox(null,"Discount amount should be greater than 0.");
			
			if (mTxtDiscount.isEditable())
				mTxtDiscount.requestFocus();
//			setValues();
			
		}else{
			mSelectedDiscount.setPrice(discount);
			setValues();
			result=true;
		}
		return result;
	}
	/**
	 * Listener for mouse click in the description area.
	 */
	private MouseListener onDiscDescriptionfocus = new MouseAdapter() {
		public void mouseClicked(java.awt.event.MouseEvent e) {
			if (mTxtReason.isEditable())
				PosFormUtil.showSoftKeyPad(PosOrderPreBillDiscountForm.this,mTxtReason,
						new PosSoftKeypadAdapter() {
							@Override
							public void onAccepted(String text) {
								mSelectedDiscount.setDescription(text);
							}
						});
		};
	};

	/**
	 * Listener to show the reason browser.
	 */
	private IPosButtonListner reasonsListener = new PosButtonListnerAdapter() {
		@Override
		public void onClicked(PosButton button) {
			// mCurrentCtrl=null;
			if (mReasonList == null)
				mReasonList = mReasonItemProvider.getReasonItemList();
			IPosBrowsableItem[] itemList = new IPosBrowsableItem[mReasonList
					.size()];
			mReasonList.toArray(itemList);
			PosObjectBrowserForm objectBrowser = new PosObjectBrowserForm(
					"Reasons", itemList, ItemSize.Wider);
			objectBrowser.setListner(new IPosObjectBrowserListner() {
				@Override
				public void onItemSelected(IPosBrowsableItem item) {
					BeanReason reason = (BeanReason) item;
					mSelectedDiscount.setDescription(reason.getDescription());
					setValues();
				}

				@Override
				public void onCancel() {
					// TODO Auto-generated method stub
				}
			});
			PosFormUtil.showLightBoxModal(PosOrderPreBillDiscountForm.this,objectBrowser);
		}
	};
	private void setValues() {
		 
		mTxtName.setText(mSelectedDiscount.getName());
		mTxtReason.setText(mSelectedDiscount.getDescription());
		mTxtDiscount.setText(PosCurrencyUtil.format(mSelectedDiscount.getPrice()));
		mLabelDiscountSymbol.setText((mSelectedDiscount.isPercentage()) ? "%"
				: PosEnvSettings.getInstance().getCurrencySymbol());
		mTxtAmount.setText(PosCurrencyUtil.format(getBillDiscountAmount()));
		//onTenderAmountChanged(String.valueOf(getBillDiscountAmount()));
		setEnableControls();
	}

	public double getBillDiscountAmount() {
		double amount = 0;
		final double totalBillAmount = PosOrderUtil.getTotalAmount(mOrderHeader);
		if (mSelectedDiscount != null
				&& !mSelectedDiscount.getCode().equals(
						PosDiscountItemProvider.NONE_DISCOUNT_CODE)) {
			amount = ((mSelectedDiscount.isPercentage()) ? totalBillAmount
					* mSelectedDiscount.getPrice() / 100 : mSelectedDiscount
					.getPrice());
			
			double billTax =mOrderHeader.getBillTaxAmount();
			if(billTax>0){
				double txRate = billTax/(totalBillAmount-billTax);
				
				mBillTaxAmount = amount*txRate/(1+txRate);
				BeanTax billTax1 =new BeanTax();
			}
		}
//		PosTaxObject billTax=PosEnvSettings.getInstance().getBillParams().getTax();
//		if(billTax!=null)
//			mBillTaxAmount= PosTaxUtil.getTaxAmountFromTotal(billTax, amount);
		
		return amount;
	}
 
	 
	private void setEnableControls() {
		final boolean enableReason = (mSelectedDiscount != null && !mSelectedDiscount
				.getCode().equals(PosDiscountItemProvider.NONE_DISCOUNT_CODE) && !hasPartialPayments());
		mBtnReasonSelect.setEnabled(enableReason);
		mBtnReasonReset.setEnabled(enableReason);
		mTxtReason.setEditable(enableReason);
		mTxtDiscount.setEditable(mSelectedDiscount != null
				&& mSelectedDiscount.isOverridable() && !hasPartialPayments());
	}
 
	private void setDiscountDetails() {
		initDiscountControls();
		mPosDiscountItemList = mDiscountProvider
				.getDiscounts(PermittedLevel.BILL);
		for(int index=mPosDiscountItemList.size()-1;index>=0;index--){
			if(!mPosDiscountItemList.get(index).isVisibleInUI()){
				mPosDiscountItemList.remove(index);
			}
		}
	}

	private void initDiscountControls() {
		loadDiscountItem();
		createDiscountItems();
	}

	private void loadDiscountItem() {
		PosDiscountItemProvider discountItemProvider = new PosDiscountItemProvider();
		mPosDiscountItemList = discountItemProvider.getDiscounts();
	}

	private void createDiscountItems() {
		
		mDiscountItemContainer = new JPanel();
		mDiscountItemContainer
				.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
		mDiscountItemContainer.setLayout(createLayout());

		final int top =  (DISCOUNT_DET_PANEL_HEIGHT + PANEL_CONTENT_V_GAP *2  );
		final int left = 0;// mTxtCompanyInfo.getX();

		mDiscountItemContainer.setBounds(left, top, DISCOUNT_LIST_PANEL_WIDTH,
				DISCOUNT_LIST_PANEL_HEIGHT);
		mDiscountItemControlList = new ArrayList<PosDiscountItemControl>();
		mCurrentPage++;
		for (int index = 0; index < mItemsPerPage; index++) {
			PosDiscountItemControl itemControl = new PosDiscountItemControl();
			itemControl.setOnSelectListner(posPaymentSelectedListner);
 			addKeyStroke (itemControl,index);
			mDiscountItemControlList.add(itemControl);
			mDiscountItemContainer.add(itemControl);
		}
		mContentPanel.add(mDiscountItemContainer);
		

		createScrollButton();
		setSrcollButtonStatus();

	}

	private FlowLayout createLayout() {
		FlowLayout flowLayout = new FlowLayout();
		flowLayout.setVgap(3);
		flowLayout.setHgap(3);
		flowLayout.setAlignment(FlowLayout.LEFT);
		return flowLayout;
	}


	private IPosSelectButtonListner posPaymentSelectedListner = new IPosSelectButtonListner() {
		@Override
		public void onSelected(PosSelectButton button) {
//			if(mParent.getTenderedAmount()>0)
//			{
//				PosFormUtil.showErrorMessageBox(null, " Please Reset all payments to apply discount");
//				
//			}else{
				if (mSelectedDiscountItemControl != null)
					mSelectedDiscountItemControl.setSelected(false);
				mSelectedDiscountItemControl = (PosDiscountItemControl) button;
				if(validateDiscount(mSelectedDiscountItemControl.getDiscountItem())){
					final String reason=mSelectedDiscount.getDescription();
					 
					//for overridable , if amount changed
					final double price=(mSelectedDiscount.getCode() == mSelectedDiscountItemControl.getDiscountItem().getCode())?
							PosNumberUtil.parseDoubleSafely(String.valueOf(mTxtDiscount.getText())):
						mSelectedDiscountItemControl.getDiscountItem().getPrice();
					
					mSelectedDiscount = mSelectedDiscountItemControl.getDiscountItem()
							.clone();
					mSelectedDiscount.setDescription(reason);
					mSelectedDiscount.setPrice(price);
					setValues();
				}else
					reset();
//			}
			
		}
	};
	private boolean validateDiscount(BeanDiscount discount){
		
		boolean isValid=true;
		String disPassword = (discount.getDiscountPassword()!=null)?discount.getDiscountPassword().trim():null;
		if (disPassword != null && !disPassword.isEmpty()) {
			if (!PosPasswordUtil.getValidateDiscountPassword(disPassword)) {
				PosFormUtil.showErrorMessageBox(PosOrderPreBillDiscountForm.this, "wrong password.");
//				mSelectedDiscountItemControl.setSelected(false);
				isValid = false;
				
			}
		}
		if(isValid){
			if (!PosAccessPermissionsUtil.validateAccess(PosOrderPreBillDiscountForm.this, PosEnvSettings
					.getInstance().getCashierShiftInfo().getCashierInfo()
					.getUserGroupId(), "bill_discount")) {
//				mSelectedDiscountItemControl.setSelected(false);
				isValid = false;
			} 
			
//			else if (!mParent.canApplyDiscount()) {
//				PosFormUtil.showErrorMessageBox(mParent,
//						" Please Reset all payments to apply discount");
//				isValid = false;
//			} 
		}
		return isValid;
	}

	public void setCancel(Boolean canCancel) {
		mButtonCancel.setEnabled(canCancel);
	}

	private void createScrollButton() {
		
		int top = mDiscountItemContainer.getY();
		int left = mDiscountItemContainer.getX()
				+ mDiscountItemContainer.getWidth() - 2;
		JPanel scrollPanel = new JPanel();
		scrollPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
		scrollPanel.setBounds(left, top, IMAGE_RIGHT_PANEL_BUTTON_WIDTH
				+ PANEL_CONTENT_H_GAP  +4, mDiscountItemContainer.getHeight());
		scrollPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 3, 5));
		mContentPanel.add(scrollPanel);

		mImageButtonScrollUp = new PosButton();
		mImageButtonScrollUp.setText("");
		mImageButtonScrollUp.setImage(PosResUtil
				.getImageIconFromResource(IMAGE_BUTTON_SCROLL_UP));
		mImageButtonScrollUp.setTouchedImage(PosResUtil
				.getImageIconFromResource(IMAGE_BUTTON_SCROLL_UP_TOUCH));
		mImageButtonScrollUp.setHorizontalAlignment(SwingConstants.CENTER);
		mImageButtonScrollUp
				.setBounds(left, top, IMAGE_RIGHT_PANEL_BUTTON_WIDTH,
						IMAGE_RIGHT_PANEL_BUTTON_HEIGHT);
		mImageButtonScrollUp.registerKeyStroke(KeyEvent.VK_PAGE_UP);
		mImageButtonScrollUp.setOnClickListner(imgScrollUpButtonListner);
		scrollPanel.add(mImageButtonScrollUp);

		top = mImageButtonScrollUp.getY() + mImageButtonScrollUp.getHeight()
				+ PANEL_CONTENT_V_GAP;

		mImageButtonScrollDown = new PosButton();
		mImageButtonScrollDown.setText("");
		mImageButtonScrollDown.setImage(PosResUtil
				.getImageIconFromResource(IMAGE_BUTTON_SCROLL_DOWN));
		mImageButtonScrollDown.setTouchedImage(PosResUtil
				.getImageIconFromResource(IMAGE_BUTTON_SCROLL_DOWN_TOUCH));
		mImageButtonScrollDown.setHorizontalAlignment(SwingConstants.CENTER);
		mImageButtonScrollDown
				.setBounds(left, top, IMAGE_RIGHT_PANEL_BUTTON_WIDTH,
						IMAGE_RIGHT_PANEL_BUTTON_HEIGHT);
		mImageButtonScrollDown.registerKeyStroke(KeyEvent.VK_PAGE_DOWN);
		
		mImageButtonScrollDown.setOnClickListner(imgScrollDownButtonListner);
		scrollPanel.add(mImageButtonScrollDown);

		mImageButtonSearch = new PosButton();
		mImageButtonSearch.setText("Search ");
		mImageButtonSearch.setImage(PosResUtil
				.getImageIconFromResource(IMAGE_BUTTON_SEARCH));
		mImageButtonSearch.setTouchedImage(PosResUtil
				.getImageIconFromResource(IMAGE_BUTTON_SEARCH_TOUCH));
		mImageButtonSearch.setHorizontalAlignment(SwingConstants.CENTER);
		mImageButtonSearch.setBounds(left, top,
				IMAGE_RIGHT_PANEL_SEARCH_BUTTON_WIDTH,
				IMAGE_RIGHT_PANEL_SEARCH_BUTTON_HEIGHT);
		mImageButtonSearch.setOnClickListner(imgSearchButtonListner);
		scrollPanel.add(mImageButtonSearch);
	}

	private void previousButtonClick() {
		mCurrentPage--;
		displayCurPageItems();
		setSrcollButtonStatus();
	}

	private void nextButtonClick() {
		mCurrentPage++;
		displayCurPageItems();
		setSrcollButtonStatus();
	}

	private void displayCurPageItems() {
		if (mSelectedDiscountItemControl != null)
			mSelectedDiscountItemControl.setSelected(false);
		mSelectedDiscountItemControl = null;

		int itemIndex = mCurrentPage * mItemsPerPage - mItemsPerPage;
		PosDiscountItemControl itemControl = null;
		PosDiscountItemControl tempCompany = null;
		for (int controlIndex = 0; controlIndex < mItemsPerPage; controlIndex++) {
			itemControl = mDiscountItemControlList.get(controlIndex);
			if (itemIndex < mPosDiscountItemList.size()) {
				itemControl
						.setDiscountItem(mPosDiscountItemList.get(itemIndex));
				itemControl.setVisible(true);	
				itemIndex++;
				if (mSelectedDiscount != null
						&& mSelectedDiscount.getCode().equals(
								itemControl.getDiscountItem().getCode()))
					tempCompany = itemControl;
			} else
				itemControl.setVisible(false);
		}
		if (tempCompany != null)
			tempCompany.setSelected(true);
	}

	
	/**
	 * @param btnControl
	 * @param item
	 * @param index
	 */
	private void addKeyStroke(PosDiscountItemControl itemControl ,int index){
	
		itemControl.setAutoMnemonicEnabled(false);
		final KeyStroke stroke =KeyStroke.getKeyStroke(112+index,0);
		itemControl.registerKeyStroke(stroke);
	}
	
	private void setSrcollButtonStatus() {
		mImageButtonScrollDown.setEnabled(true);
		mImageButtonScrollUp.setEnabled(true);
		if (mCurrentPage * mItemsPerPage >= mPosDiscountItemList.size())
			mImageButtonScrollDown.setEnabled(false);
		if (mCurrentPage <= 1)
			mImageButtonScrollUp.setEnabled(false);
	}

	private IPosButtonListner imgScrollUpButtonListner = new PosButtonListnerAdapter() {
		@Override
		public void onClicked(PosButton button) {
			previousButtonClick();
		}
	};

	private IPosButtonListner imgScrollDownButtonListner = new PosButtonListnerAdapter() {
		@Override
		public void onClicked(PosButton button) {
			nextButtonClick();
		}
	};

	private IPosButtonListner imgSearchButtonListner = new PosButtonListnerAdapter() {
		@Override
		public void onClicked(PosButton button) {
			showSearchForm();
		}
	};
	

	private void showSearchForm() {
		
		if(mPosDiscountItemList==null || mPosDiscountItemList.size()==0) return;
		PosExtSearchForm serachForm=new PosExtSearchForm(mPosDiscountItemList);
		serachForm.setListner(new PosItemExtSearchFormAdapter() {
			
			@Override
			public boolean onAccepted(Object sender,IPosSearchableItem item) {
//				addPosItem(String.valueOf(item.getItemCode()));
				int index=mPosDiscountItemList.indexOf(item);
				int page = (index / mItemsPerPage);
				page = ((index % mItemsPerPage > 0) ? page + 1 : page);
				mCurrentPage = (page > 0) ? page : page + 1;
				displayCurPageItems();
				setSrcollButtonStatus();
				final int firstIndex = (mCurrentPage - 1) * mItemsPerPage;
				mDiscountItemControlList.get(index - firstIndex).setSelected(
						true);
				
				return true;			
			}
		});
		PosFormUtil.showLightBoxModal(PosOrderPreBillDiscountForm.this, serachForm);
	}
 
	private void reset() {
		
		if(mOrderHeader.getPreBillDiscount() !=null && mOrderHeader.getPreBillDiscount() !=mDiscountProvider.getNoneDiscount()){
			mSelectedDiscount=mOrderHeader.getPreBillDiscount();
			displayCurPageItems();
			mSelectedDiscount.setPrice(mOrderHeader.getPreBillDiscount().getPrice());
		}else{
			mSelectedDiscount = mDiscountProvider.getNoneDiscount();
			if (mSelectedDiscountItemControl != null)
				mSelectedDiscountItemControl.setSelected(false);
			mSelectedDiscountItemControl=null;
		}
		mBillTaxAmount=0;
		setValues();
	}



	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.indocosmo.pos.forms.PosBaseForm#setContentPanel(javax.swing.JPanel)
	 */
	@Override
	protected void setContentPanel(JPanel panel) {
//		panel.setLayout(new FlowLayout(FlowLayout.LEFT,PANEL_CONTENT_H_GAP ,
//				PANEL_CONTENT_V_GAP ));
		
		mContentPanel = panel;
	}


}
