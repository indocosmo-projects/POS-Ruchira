/**
 * 
 */
package com.indocosmo.pos.forms.components.orderentry.itemedit;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.MatteBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.indocosmo.pos.common.PosEnvSettings;
import com.indocosmo.pos.common.utilities.PosCurrencyUtil;
import com.indocosmo.pos.common.utilities.PosFormUtil;
import com.indocosmo.pos.common.utilities.PosNumberUtil;
import com.indocosmo.pos.common.utilities.PosOrderUtil;
import com.indocosmo.pos.common.utilities.PosPasswordUtil;
import com.indocosmo.pos.common.utilities.PosSaleItemUtil;
import com.indocosmo.pos.common.utilities.PosTaxUtil;
import com.indocosmo.pos.common.utilities.PosUomUtil;
import com.indocosmo.pos.data.beans.BeanDiscount;
import com.indocosmo.pos.data.beans.BeanDiscount.AppliedAt;
import com.indocosmo.pos.data.beans.BeanDiscount.EditTypes;
import com.indocosmo.pos.data.beans.BeanDiscount.PermittedLevel;
import com.indocosmo.pos.data.beans.BeanOrderDetail;
import com.indocosmo.pos.data.beans.BeanReason;
import com.indocosmo.pos.data.beans.BeanSaleItem;
import com.indocosmo.pos.data.providers.shopdb.PosDiscountItemProvider;
import com.indocosmo.pos.data.providers.shopdb.PosPromotionItemProvider;
import com.indocosmo.pos.data.providers.shopdb.PosReasonItemProvider;
import com.indocosmo.pos.data.providers.shopdb.PosSaleItemDiscountProvider;
import com.indocosmo.pos.forms.PosObjectBrowserForm;
import com.indocosmo.pos.forms.PosObjectBrowserForm.ItemSize;
import com.indocosmo.pos.forms.PosOrderEntryForm;
import com.indocosmo.pos.forms.PosOrderItemQuickEditForm;
import com.indocosmo.pos.forms.components.buttons.PosButton;
import com.indocosmo.pos.forms.components.buttons.listners.IPosButtonListner;
import com.indocosmo.pos.forms.components.buttons.listners.PosButtonListnerAdapter;
import com.indocosmo.pos.forms.components.keypads.listners.PosSoftKeypadAdapter;
import com.indocosmo.pos.forms.components.objectbrowser.IPosBrowsableItem;
import com.indocosmo.pos.forms.components.tab.PosTab;
import com.indocosmo.pos.forms.components.textfields.PosTextField;
import com.indocosmo.pos.forms.components.textfields.PosTouchableNumericField;
import com.indocosmo.pos.forms.components.textfields.listners.IPosTouchableFieldListner;
import com.indocosmo.pos.forms.listners.IPosFormEventsListner;
import com.indocosmo.pos.forms.listners.IPosObjectBrowserListner;
import com.indocosmo.pos.forms.messageboxes.PosMessageBoxForm.MessageBoxButtonTypes;
import com.indocosmo.pos.forms.messageboxes.PosMessageBoxForm.MessageBoxResults;


@SuppressWarnings("serial")
public class PosTabPriceDiscountQuickEdit extends PosTab implements IPosFormEventsListner {
	
	// beans
	private BeanOrderDetail mOrderDetailItem;
	private BeanSaleItem mPosItemToEdit;
	private static BeanDiscount mNoneDiscount;
	
	// providers
	private PosSaleItemDiscountProvider mSaleItemDiscProvider;
	private PosDiscountItemProvider mDiscItemProvider;
	private static PosReasonItemProvider mReasonItemProvider;
	
	// ui parameters
	public static final String TAB_CAPTION = "Price";
	private static final int CONTENT_PANEL_HEIGHT = 330;
	private static final int LAYOUT_HEIGHT = CONTENT_PANEL_HEIGHT;
	private static final int LAYOUT_WIDTH = 700;

	private static final int PANEL_CONTENT_H_GAP = PosOrderEntryForm.PANEL_CONTENT_H_GAP;
	private static final int PANEL_CONTENT_V_GAP = PosOrderEntryForm.PANEL_CONTENT_V_GAP;
	private static final int LABEL_DISCOUNT_HEADER_WIDTH = LAYOUT_WIDTH;

	private static final int DICOUNT_BUTTON_HEIGHT = 40;
	private static final int DICOUNT_BUTTON_WIDTH = 175;

	private static final int LABEL_CUR_SYMBOL_WIDTH = 40;
	private static final int LABEL_CUR_SYMBOL_HEIGHT = LABEL_CUR_SYMBOL_WIDTH;

	private static final int LABEL_HEIGHT = 25;


	// panels
	private JPanel mAmountPanel;// main panel
	private JPanel mItemPanel;
	private JPanel mDiscountPanel;
	private JLabel mLabelPrice;

	// discount panel componenets
	private JTextArea mTxtDiscountDesc;
	private PosButton mButtonDiscountCode;
	private JLabel mLabelcurrencySymbol;
	private PosButton mButtonDiscountReason;
	private PosButton mButtonDiscountResetReason;
	private PosButton mButtonDiscountReset;
	private JTextField mCurrentCtrl;

	private ArrayList<BeanDiscount> mDiscountList;
	private ArrayList<BeanDiscount> mItemDiscountList;
	private ArrayList<BeanDiscount> mGeneralDiscountItemList;
	private ArrayList<BeanReason> mReasonList;

	// item panel
	private JLabel mLabelQty;
	private PosTouchableNumericField mTxtQty;
	private PosTouchableNumericField mTxtPrice;
	private PosTouchableNumericField mTxtDiscountValue;
	
	private String oldValue="";
 
	private PosOrderItemQuickEditForm  mParent;

	public PosTabPriceDiscountQuickEdit(PosOrderItemQuickEditForm parentForm, BeanOrderDetail beanOrderDetail) {
		
		super(parentForm, TAB_CAPTION);
		mParent=parentForm;
		mOrderDetailItem = beanOrderDetail;
		mPosItemToEdit = beanOrderDetail.getSaleItem().clone();
		mSaleItemDiscProvider = new PosSaleItemDiscountProvider();
		mDiscItemProvider = new PosDiscountItemProvider();
		mReasonItemProvider = new PosReasonItemProvider();
		mDiscItemProvider = new PosDiscountItemProvider();
		mNoneDiscount = mDiscItemProvider.getNoneDiscount();
		initControls();
	}
	
	

	public void initControls() {
		setSize(LAYOUT_WIDTH, LAYOUT_HEIGHT);
		setLayout(null);
		mAmountPanel = new JPanel();
		mAmountPanel.setBounds(0, 0, LAYOUT_WIDTH, LAYOUT_HEIGHT);
		mAmountPanel.setLayout(null);
		mAmountPanel.setBorder(new MatteBorder(0, 0, 0, 1, Color.LIGHT_GRAY));
		add(mAmountPanel);
		createDiscountPanel(); 
		createItemPanel(); 
		setValues();
		setDirty(false);
		
		
		
	
	}

	public void createDiscountPanel() {
		
		final int PANEL_HEIGHT = (DICOUNT_BUTTON_HEIGHT * 4) + (PANEL_CONTENT_V_GAP * 5) + LABEL_HEIGHT;
		mDiscountPanel = new JPanel();
		mDiscountPanel.setLayout(null);
		mDiscountPanel.setBorder(new MatteBorder(0, 0, 0, 1, Color.LIGHT_GRAY));
		mAmountPanel.add(mDiscountPanel);
		mDiscountPanel.setOpaque(true);
		final int top = 0;
		createDiscount();
		mDiscountPanel.setBounds(0, top, mAmountPanel.getWidth(), PANEL_HEIGHT);
	}

	public void createDiscount() {
		int left = PANEL_CONTENT_H_GAP;
		int top = PANEL_CONTENT_V_GAP;

		JLabel labelDiscount = PosFormUtil.setHeading("Discount", LABEL_DISCOUNT_HEADER_WIDTH);
		mDiscountPanel.add(labelDiscount);

		top = labelDiscount.getY() + labelDiscount.getHeight() + PANEL_CONTENT_V_GAP;
		mButtonDiscountCode = new PosButton();
		mButtonDiscountCode.setBounds(left, top, DICOUNT_BUTTON_WIDTH, DICOUNT_BUTTON_HEIGHT);
		mButtonDiscountCode.setImage("item_edit_discount_select.png");
		mButtonDiscountCode.setTouchedImage("item_edit_discount_select_touch.png");
		mButtonDiscountCode.setOnClickListner(discountCodeListener);
		mDiscountPanel.add(mButtonDiscountCode);

		left = mButtonDiscountCode.getX() + mButtonDiscountCode.getWidth() + PANEL_CONTENT_H_GAP;
		top = mButtonDiscountCode.getY();
		final int NUMBERFIELD_WIDTH = LABEL_DISCOUNT_HEADER_WIDTH - mButtonDiscountCode.getWidth()
				- LABEL_CUR_SYMBOL_WIDTH - (PANEL_CONTENT_V_GAP * 5);
		
		mTxtDiscountValue = new PosTouchableNumericField(null, NUMBERFIELD_WIDTH);
		mTxtDiscountValue.setLocation(left, top);
		mTxtDiscountValue.setEditable(false);
		mTxtDiscountValue.setFont(PosFormUtil.getTextFieldBoldFont());
		mTxtDiscountValue.setListner(new IPosTouchableFieldListner() 
		{

			@Override
			public void onValueSelected(Object value) {
				onDiscountChanged();
				mTxtQty.setSelectedAll();
				mTxtQty.requestFocus();
			}

			@Override
			public void onReset() {
				onDiscountChanged();

			}
		});
//		mTxtDiscountValue.addFocusListener(focusListener);
		mDiscountPanel.add(mTxtDiscountValue);

		left = mTxtDiscountValue.getX() + mTxtDiscountValue.getWidth() + PANEL_CONTENT_H_GAP;
		mLabelcurrencySymbol = new JLabel();
		mLabelcurrencySymbol.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
		mLabelcurrencySymbol.setHorizontalAlignment(SwingConstants.CENTER);
		mLabelcurrencySymbol.setBounds(left, top, LABEL_CUR_SYMBOL_WIDTH, LABEL_CUR_SYMBOL_HEIGHT);
		mLabelcurrencySymbol.setFont(PosFormUtil.getTextFieldBoldFont());
		mDiscountPanel.add(mLabelcurrencySymbol);

		left = PANEL_CONTENT_H_GAP;
		top = mTxtDiscountValue.getY() + mTxtDiscountValue.getHeight() + PANEL_CONTENT_V_GAP;
		mButtonDiscountReason = new PosButton();
		mButtonDiscountReason.setText("Select Reason");
		mButtonDiscountReason.setBounds(left, top, DICOUNT_BUTTON_WIDTH, DICOUNT_BUTTON_HEIGHT);
		mButtonDiscountReason.setImage("item_edit_discount_select.png");
		mButtonDiscountReason.setTouchedImage("item_edit_discount_select_touch.png");
		mButtonDiscountReason.setOnClickListner(reasonsListener);
		mDiscountPanel.add(mButtonDiscountReason);

		left = PANEL_CONTENT_H_GAP;
		top = mButtonDiscountReason.getY() + mButtonDiscountReason.getHeight() + PANEL_CONTENT_V_GAP;
		mButtonDiscountResetReason = new PosButton();
		mButtonDiscountResetReason.setText("Clear Reason");
		mButtonDiscountResetReason.setBounds(left, top, DICOUNT_BUTTON_WIDTH, DICOUNT_BUTTON_HEIGHT);
		mButtonDiscountResetReason.setImage("item_edit_discount_reson_clear.png");
		mButtonDiscountResetReason.setTouchedImage("item_edit_discount_reson_clear_touch.png");
		mButtonDiscountResetReason.setOnClickListner(new IPosButtonListner() {

			@Override
			public void onClicked(PosButton button) {
				mTxtDiscountDesc.setText("");
			}
		});
		mDiscountPanel.add(mButtonDiscountResetReason);

		left = PANEL_CONTENT_H_GAP;
		top = mButtonDiscountResetReason.getY() + mButtonDiscountResetReason.getHeight() + PANEL_CONTENT_V_GAP;
		mButtonDiscountReset = new PosButton();
		mButtonDiscountReset.setText("Remove Discount");
		mButtonDiscountReset.setMnemonic('u');
		mButtonDiscountReset.setBounds(left, top, DICOUNT_BUTTON_WIDTH, DICOUNT_BUTTON_HEIGHT);
		mButtonDiscountReset.setImage("item_edit_discount_reset.png");
		mButtonDiscountReset.setTouchedImage("item_edit_discount_reset_touch.png");
		mButtonDiscountReset.setOnClickListner(new IPosButtonListner() {
		@Override
		public void onClicked(PosButton button) {

				if (PosFormUtil.showQuestionMessageBox(mParent, MessageBoxButtonTypes.YesNo,
						"This will reset the discount settings. Do you want to continue?",
						null) == MessageBoxResults.Yes) {
					BeanDiscount discount = mDiscItemProvider.getNoneDiscount();
					applyDiscount(discount);
					mTxtQty.selectAll();
					mTxtQty.requestFocus();
				}
			}

		});
		
		mDiscountPanel.add(mButtonDiscountReset);
		final int scrollHeight = (mButtonDiscountReason.getHeight() * 3) + (PANEL_CONTENT_V_GAP * 2);
		top = mButtonDiscountReason.getY();
		left = mButtonDiscountReason.getX() + mButtonDiscountReason.getWidth() + PANEL_CONTENT_H_GAP;
		int DISCOUNT_DESC_WIDTH = LABEL_DISCOUNT_HEADER_WIDTH - mButtonDiscountCode.getWidth()
				- (PANEL_CONTENT_V_GAP * 4);
		mTxtDiscountDesc = new JTextArea();
		mTxtDiscountDesc.setLineWrap(true);
		mTxtDiscountDesc.setWrapStyleWord(false);
		mTxtDiscountDesc.setFont(PosFormUtil.getTextFieldFont());
		mTxtDiscountDesc.addMouseListener(onDiscDescriptionfocus);
		mTxtDiscountDesc.setBounds(left, top, DISCOUNT_DESC_WIDTH, scrollHeight);
		mTxtDiscountDesc.setEditable(false);
		mTxtDiscountDesc.getDocument().addDocumentListener(mDirtyDocumentListener);
		JScrollPane scrollPane = new JScrollPane(mTxtDiscountDesc);
		scrollPane.setBounds(left, top, DISCOUNT_DESC_WIDTH, scrollHeight);
		mDiscountPanel.add(scrollPane);

	}

	private void createItemPanel() {
		mItemPanel = new JPanel();
		mItemPanel.setLayout(null);
		mItemPanel.setBorder(new MatteBorder(0, 0, 0, 1, Color.LIGHT_GRAY));
		mAmountPanel.add(mItemPanel);
		mItemPanel.setOpaque(true);
		final int top = mDiscountPanel.getY() + mDiscountPanel.getHeight() + PANEL_CONTENT_V_GAP;
		final int height = mAmountPanel.getHeight() - mDiscountPanel.getHeight() - (PANEL_CONTENT_V_GAP * 2);
		mItemPanel.setBounds(0, top, mAmountPanel.getWidth(), height);
		createPriceDetails();
		
		
	}

	private void createPriceDetails() {
		int left = PANEL_CONTENT_H_GAP;
		int top = PANEL_CONTENT_V_GAP;
		int LABEL_QTY_WIDTH = 70;
		JLabel title = PosFormUtil.setHeading("Amount & Quantity", LAYOUT_WIDTH - PANEL_CONTENT_V_GAP * 2);
		title.setLocation(left, top);
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setBounds(0, 0, mItemPanel.getWidth(), title.getHeight());
		mItemPanel.add(title);

		top = title.getY() + title.getHeight();
		mLabelQty = new JLabel();
		mLabelQty.setText("Quantity");
		mLabelQty.setBounds(left, top + (PANEL_CONTENT_V_GAP * 3), LABEL_QTY_WIDTH, LABEL_HEIGHT);
		mLabelQty.setFont(PosFormUtil.getLabelFont());
		mLabelQty.setOpaque(true);
		//mLabelQty.setBackground(Color.LIGHT_GRAY);
		mItemPanel.add(mLabelQty);

		mTxtQty = new PosTouchableNumericField(null);
		mTxtQty.setLocation(mLabelQty.getX() + mLabelQty.getWidth(), top +( PANEL_CONTENT_V_GAP * 2));
		mTxtQty.setFont(PosFormUtil.getTextFieldBoldFont());
		mTxtQty.getTexFieldComponent().setRequestFocusEnabled(true);
//		mTxtQty.getTexFieldComponent().addActionListener(new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				System.out.println("KeyEvent "+e.getActionCommand()+" itemedit "+mPosItemToEdit.isOpen());
//				setDirty(true);	
//				onQtyChanged();
//			}
//		});
		
		
		//mTxtQty.getTexFieldComponent().requestFocus();
		
		 mTxtQty.setListner(new IPosTouchableFieldListner() {
		 @Override
		 public void onValueSelected(Object value) {
		 	if (onQtyChanged()){
		 		if(mOrderDetailItem.getSaleItem().isOpen()){
		 			mTxtPrice.setSelectedAll();
		 			mTxtPrice.requestFocus();
		 		}else if(mParent.onOkButtonClicked())
		 			mParent.closeWindow();
		 	}
		 	
		 }
		 		
		 @Override
		 public void onReset() {
			 onQtyChanged();
		 }
		 });
//		 mTxtQty.addFocusListener(focusListener);
		mItemPanel.add(mTxtQty);

		left = mLabelQty.getX() + mLabelQty.getWidth() + PANEL_CONTENT_H_GAP;
		top = mLabelQty.getY();

		mLabelPrice = new JLabel();
		mLabelPrice.setText("Price");
		mLabelPrice.setHorizontalAlignment(SwingConstants.LEFT);
		mLabelPrice.setVerticalAlignment(SwingConstants.CENTER);
		mLabelPrice.setBounds((title.getWidth() / 2) + PANEL_CONTENT_V_GAP, top, LABEL_QTY_WIDTH, LABEL_HEIGHT);
		mLabelPrice.setFont(PosFormUtil.getLabelFont());
		mItemPanel.add(mLabelPrice);

		mTxtPrice = new PosTouchableNumericField(null);
		mTxtPrice.setLocation(mLabelPrice.getX() + mLabelPrice.getWidth(), top - PANEL_CONTENT_H_GAP);
		mTxtPrice.setFont(PosFormUtil.getTextFieldBoldFont());
		mTxtPrice.setEditable(false);
//		mTxtPrice.getTexFieldComponent().addActionListener(new ActionListener() {
//
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				onPriceChanged();
//				System.out.println(" Price Key event "+e.getActionCommand());
//				
//			}
//		});
		mTxtPrice.setListner(new IPosTouchableFieldListner() {
			
			@Override
			public void onValueSelected(Object value) {
				if (onPriceChanged() && mParent.onOkButtonClicked())
		 			mParent.closeWindow();
			}
			
			@Override
			public void onReset() {
				onPriceChanged();
				
			}
		});
//		mTxtPrice.addFocusListener(focusListener);
		mTxtPrice.setFocusable(true);
		mItemPanel.add(mTxtPrice);

	}
	// <<===================================== ui ends here	=================================================>>//

	

	public static String FormatString(String inputString) {
		String FormatString = "0";
		if (inputString.indexOf(".") < 0) {
			FormatString = inputString + ".0";
			return FormatString;
		} else {
			return inputString;
		}
	}

	// price adjustment
	private void setAdjustmentOnUnit(BeanDiscount adjDiscount) {
		adjDiscount.setPrice(0);
		mPosItemToEdit.setDiscount(adjDiscount);
		final double curUnitPrice = PosSaleItemUtil.getItemFixedPrice(mPosItemToEdit);
		final double newUnitPrice = PosNumberUtil.getValueFormUIComponenet(mTxtPrice.getTexFieldComponent());
		final double discAmo = curUnitPrice - newUnitPrice;
		adjDiscount.setPrice(discAmo);
		// adjDiscount.setVariants(discAmo);
		mPosItemToEdit.setDiscount(adjDiscount);
		setValues();
	}


	private void setDiscount() {
 
		mTxtDiscountValue.setText(PosCurrencyUtil.format(PosCurrencyUtil
				.roundTo(mPosItemToEdit.getDiscount().getPrice() + mPosItemToEdit.getDiscount().getVariants())));

		mButtonDiscountCode.setText(mPosItemToEdit.getDiscount().getName());
		mTxtDiscountDesc.setText(mPosItemToEdit.getDiscount().getDescription());

		mLabelcurrencySymbol.setText(
				(mPosItemToEdit.getDiscount().isPercentage()) ? "%" : PosEnvSettings.getInstance().getCurrencySymbol());
		 
	}
	private void setValues() {

		mTxtQty.setText(PosUomUtil.format(mPosItemToEdit.getQuantity(), mPosItemToEdit.getUom()));
		mTxtQty.getTexFieldComponent().setSelectedTextColor(Color.DARK_GRAY);
		
		mTxtPrice.setText(PosCurrencyUtil.format(PosCurrencyUtil.roundTo(PosSaleItemUtil.getItemFixedPrice(mPosItemToEdit))));
		mTxtPrice.setEditable(mPosItemToEdit.isOpen());

		setDiscount();
		setEnableControls();
	}

	private void setEnableControls() {

		BeanDiscount discount = mPosItemToEdit.getDiscount();
		mTxtPrice.setEditable(discount.getEditType() == EditTypes.UnitPrice || mPosItemToEdit.isOpen());
		
		
		final boolean isDiscountEditable = !(discount.getCode().equals(PosPromotionItemProvider.DEF_PROMO_CODE)
				|| discount.getCode().equals(PosDiscountItemProvider.CUSTOMER_DISCOUNT));

		final boolean isDiscountDescEditable = !(discount.getCode().equals(PosDiscountItemProvider.NONE_DISCOUNT_CODE)
				|| discount.getCode().equals(PosPromotionItemProvider.DEF_PROMO_CODE));

		mTxtDiscountDesc.setEditable(isDiscountDescEditable && isDiscountEditable);
		mButtonDiscountResetReason.setEnabled(isDiscountDescEditable && isDiscountEditable);
		mButtonDiscountReason.setEnabled(isDiscountDescEditable && isDiscountEditable);
		mButtonDiscountCode.setEnabled(isDiscountEditable);
		mButtonDiscountReset.setEnabled(isDiscountEditable);
		mTxtDiscountValue.setEditable(discount.isOverridable());
		
		/**
		 * Do not allow to change quantity form here if it is added as an extra item
		 */
		if (!PosOrderUtil.getStatusString(mOrderDetailItem).trim().equals("N")
				|| (mOrderDetailItem.getParentDtlId() != null
						&& mOrderDetailItem.getParentDtlId().trim().length() > 0)) {
			mTxtQty.setEditable(false);
		}

	}

	// dirty listener
	private DocumentListener mDirtyDocumentListener = new DocumentListener() {

		@Override
		public void removeUpdate(DocumentEvent arg0) {
			setDirty(true);
			
		}

		@Override
		public void insertUpdate(DocumentEvent arg0) {
			setDirty(true);
			onPriceChanged();
		}

		@Override
		public void changedUpdate(DocumentEvent arg0) {
			setDirty(true);
			onPriceChanged();
		

		}
	};

	private void setDiscountAppliedLevel(BeanDiscount discount) {
		try {
			if (!discount.getCode().equals(PosDiscountItemProvider.NONE_DISCOUNT_CODE))
				discount.setAppliedAt(AppliedAt.ITEM_LEVEL);
		} catch (Exception e) {
				e.printStackTrace();
		}
	}

	private MouseListener onDiscDescriptionfocus = new MouseAdapter() {
		public void mouseClicked(java.awt.event.MouseEvent e) {
			if (mTxtDiscountDesc.isEditable())
				PosFormUtil.showSoftKeyPad((JDialog) mParent, mTxtDiscountDesc, new PosSoftKeypadAdapter() {
					@Override
					public void onAccepted(String text) {
						mPosItemToEdit.getDiscount().setDescription(text);
						setDiscountAppliedLevel(mPosItemToEdit.getDiscount());
					}
				});
		};
	};

	/**
	 * Listener for showing the discount browser.
	 */
	private IPosButtonListner discountCodeListener = new PosButtonListnerAdapter() {
		@Override
		public void onClicked(PosButton button) {
			if (mPosItemToEdit.getDiscount() != null && mPosItemToEdit.getDiscount().isPromotion()
					&& mPosItemToEdit.getDiscount().getCode() != PosPromotionItemProvider.DEF_PROMO_CODE) {

				PosFormUtil.showInformationMessageBox(getPosParent(), "Since Promotion exists, no Discount available.");
				return;
			}
			// mCurrentCtrl = null;
			if (mDiscountList == null) {
				
				mItemDiscountList = mSaleItemDiscProvider.getSaleItemDiscountList(mPosItemToEdit.getId());
				mGeneralDiscountItemList = mDiscItemProvider.getGeneralDiscounts(PermittedLevel.ITEM);
				mDiscountList = new ArrayList<BeanDiscount>();
				mDiscountList.addAll(mGeneralDiscountItemList);
				mDiscountList.addAll(mItemDiscountList);
			}
			IPosBrowsableItem[] itemList = new IPosBrowsableItem[mDiscountList.size()];
			mDiscountList.toArray(itemList);
			PosObjectBrowserForm objectBrowser = new PosObjectBrowserForm("Discounts", itemList, ItemSize.Wider, 4, 3);
			objectBrowser.setListner(new IPosObjectBrowserListner() {
				@Override
				public void onItemSelected(IPosBrowsableItem item) {
					BeanDiscount discount = (BeanDiscount) item;
					if (validateDiscount(discount)) {
						applyDiscount(discount);
						if(mTxtDiscountValue.isEditable()){
							mTxtDiscountValue.requestFocus();
							mTxtDiscountValue.selectAll();			
						}
					}
				}

				@Override
				public void onCancel() {
					
				}
			});
			PosFormUtil.showLightBoxModal(mParent, objectBrowser);
		}
	};

	private void applyDiscount(BeanDiscount discount) {

		mPosItemToEdit.setDiscount(discount.clone());
		setDiscountAppliedLevel(mPosItemToEdit.getDiscount());
		setDirty(true);
		setDiscount(); 
		setEnableControls() ;

	}

//	private void resetItem() {
////		setDirty(false);
//		PosTaxUtil.calculateTax(mPosItemToEdit);
//		setValues();
//	}

	private boolean validateDiscount(BeanDiscount discount) {
		boolean isValid = true;
		String disPassword = (discount.getDiscountPassword() != null) ? discount.getDiscountPassword().trim() : null;
		if (disPassword != null && !disPassword.isEmpty()) {
			if (!PosPasswordUtil.getValidateDiscountPassword(disPassword)) {
				PosFormUtil.showErrorMessageBox(mParent, "Wrong password.");
				return false;
			}
		}
		if (mPosItemToEdit.getQuantity() < discount.getRequiredQuantity()) {
			isValid = false;
			PosFormUtil.showErrorMessageBox(getPosParent(),
					"Item does not have enough quantity to apply this discount.");
		} else if (discount.getEditType() == EditTypes.UnitPrice && mPosItemToEdit.isOpen() && isValid) {
			isValid = false;
			PosFormUtil.showErrorMessageBox(getPosParent(),
					"The selected discount is not applicable to this sale item. Item is open. Please adjust price.");
		}
	
		return isValid;
	}

	/**
	 * Listener to show the reason browser.
	 */
	private IPosButtonListner reasonsListener = new PosButtonListnerAdapter() {
		@Override
		public void onClicked(PosButton button) {
			mCurrentCtrl = null;
			if (mReasonList == null)
				mReasonList = mReasonItemProvider.getReasonItemList();
			IPosBrowsableItem[] itemList = new IPosBrowsableItem[mReasonList.size()];
			mReasonList.toArray(itemList);
			PosObjectBrowserForm objectBrowser = new PosObjectBrowserForm("Reasons", itemList, ItemSize.Wider, 5, 3);
			objectBrowser.setListner(new IPosObjectBrowserListner() {
				@Override
				public void onItemSelected(IPosBrowsableItem item) {
					BeanReason reason = (BeanReason) item;
					mPosItemToEdit.getDiscount().setDescription(reason.getDescription());
					setDiscountAppliedLevel(mPosItemToEdit.getDiscount());
					setValues();
				}
				@Override
				public void onCancel() {
					

				}
			});
			PosFormUtil.showLightBoxModal(mParent, objectBrowser);
		}
	};

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.indocosmo.pos.forms.listners.IPosFormEventsListner#onOkButtonClicked()
	 */
	@Override
	public boolean onOkButtonClicked() {
		
		if (!onValidating())
			return false;
		
//	 	
//		if(mOrderDetailItem.getSaleItem().getDiscount().getPrice()!= Double.parseDouble(mTxtDiscountValue.getText()))
//		{
////			applyDiscount(mPosItemToEdit.getDiscount());
//			onDiscountChanged();
//			setDirty(true);
//		}
		
		if(!onQtyChanged())
			return false;
	
		if(!onPriceChanged())
			return false;
		
	 
		onDiscountChanged();

		if (isDirty()) {
			mOrderDetailItem.getSaleItem().setDiscount(mPosItemToEdit.getDiscount());
			mOrderDetailItem.getSaleItem().setFixedPrice(mPosItemToEdit.getFixedPrice());
			mOrderDetailItem.getSaleItem().setCustomerPrice(0);
			mOrderDetailItem.getSaleItem().setQuantity(mPosItemToEdit.getQuantity());
			PosTaxUtil.calculateTax(mOrderDetailItem.getSaleItem());
			mOrderDetailItem.setDirty(true);
		}
	 
	
		return true;
	}

	@Override
	public boolean onCancelButtonClicked() {
		// TODO Auto-generated method stub
		return true;
	}
	
	@Override
	public void onResetButtonClicked() {
		
		reset();
		setDirty(false);
		setValues();
	}
	private void reset() {
		
		mTxtPrice.setBackground(Color.WHITE);
		mTxtDiscountValue.setBackground(Color.WHITE);
		mTxtDiscountValue.setBackground(Color.WHITE);
		mTxtQty.setBackground(Color.WHITE);
		mPosItemToEdit = mOrderDetailItem.getSaleItem().clone();
		
	}
	@Override
	public void onGotFocus() {

	}
	@Override
	public void setReadOnly(boolean isReadOnly) {
		
		mButtonDiscountCode.setEnabled(!isReadOnly);
		mTxtDiscountValue.setEditable(!isReadOnly);
		mButtonDiscountResetReason.setEnabled(!isReadOnly);
		mButtonDiscountReason.setEnabled(!isReadOnly);
		mButtonDiscountReset.setEnabled(!isReadOnly);
		mTxtDiscountDesc.setEditable(!isReadOnly);
		mTxtQty.setEditable(PosOrderUtil.getStatusString(mOrderDetailItem).trim().equals("N") && !isReadOnly);

	}
	public double getEnteredQty() {
		final double qty = PosNumberUtil.parseDoubleSafely(mTxtQty.getText());
		return qty;
	}
 
	public boolean onQtyChanged() {
		
		double newQty = PosNumberUtil.parseDoubleSafely(mTxtQty.getText());
		
		if (mPosItemToEdit.getQuantity()==newQty && newQty>0  )
			return true;

		boolean result=true;
		setDirty(true);
		if (!mTxtQty.isEditable() && PosNumberUtil.parseDoubleSafely(mTxtQty.getText()) != newQty) {
			PosFormUtil.showErrorMessageBox(getPosParent(),
					"This item has been already printed to kitchen, Please add as new item.");
			newQty = mPosItemToEdit.getQuantity();
			result=false;
		} else if (newQty <= 0) {
			PosFormUtil.showErrorMessageBox(getPosParent(), "Qunatity should be greater than 0");
			newQty = mPosItemToEdit.getQuantity();
			mTxtQty.selectAll();
			mTxtQty.requestFocus();
			result=false;
		}else{
			
			mPosItemToEdit.setQuantity(newQty);
			
			if(mPosItemToEdit.isOpen())
			{
				mTxtPrice.requestFocus();
				mTxtPrice.selectAll();
			}
		}
		
		return result;
	}
	
	public void onDiscountChanged() {
		

		final double newValue = Math.abs(Double.parseDouble(mTxtDiscountValue.getText()));
		
		if (mPosItemToEdit.getDiscount().getPrice() !=newValue){
			
			mPosItemToEdit.getDiscount().setPrice(PosNumberUtil.parseDoubleSafely(mTxtDiscountValue.getText()));
			setDiscountAppliedLevel(mPosItemToEdit.getDiscount());
			setDirty(true);
		}
	}
	
	public boolean onPriceChanged() {
		
		final double newValue = Math.abs(Double.parseDouble(mTxtPrice.getText()));
		
		if (PosCurrencyUtil.roundTo(PosSaleItemUtil.getItemFixedPrice(mPosItemToEdit)) == newValue && newValue>0 )
			return true;
		
		boolean result=true;

		setDirty(true);
		mPosItemToEdit.setCustomerPrice(0);
		if (mPosItemToEdit.isOpen()) 
			mPosItemToEdit.setFixedPrice(newValue);
		
//		if (mPosItemToEdit.isOpen()) {
//			mPosItemToEdit.setFixedPrice(newValue);
////			PosTaxUtil.calculateTax(mPosItemToEdit);
//		} else {
//			setAdjustmentOnUnit(mPosItemToEdit.getDiscount());
//		}

		return result;
		 
	}
	public JComponent getDefaultComponent()
	{
		mTxtQty.selectAll();
		System.out.println(" "+mTxtQty.getTexFieldComponent().getCaretPosition());
		return mTxtQty;
	}

	


}
