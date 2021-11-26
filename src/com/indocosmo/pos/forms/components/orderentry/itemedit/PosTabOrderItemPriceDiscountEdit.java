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

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
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
import com.indocosmo.pos.forms.components.buttons.PosButton;
import com.indocosmo.pos.forms.components.buttons.listners.IPosButtonListner;
import com.indocosmo.pos.forms.components.buttons.listners.PosButtonListnerAdapter;
import com.indocosmo.pos.forms.components.keypads.PosNumKeypad;
import com.indocosmo.pos.forms.components.keypads.PosNumKeypad.CancelActions;
import com.indocosmo.pos.forms.components.keypads.PosNumKeypad.IPosNumkeypadListner;
import com.indocosmo.pos.forms.components.keypads.listners.PosNumkeypadListnerAdapter;
import com.indocosmo.pos.forms.components.keypads.listners.PosSoftKeypadAdapter;
import com.indocosmo.pos.forms.components.objectbrowser.IPosBrowsableItem;
import com.indocosmo.pos.forms.components.tab.PosTab;
import com.indocosmo.pos.forms.components.textfields.PosTextField;
import com.indocosmo.pos.forms.listners.IPosFormEventsListner;
import com.indocosmo.pos.forms.listners.IPosObjectBrowserListner;
import com.indocosmo.pos.forms.messageboxes.PosMessageBoxForm.MessageBoxButtonTypes;
import com.indocosmo.pos.forms.messageboxes.PosMessageBoxForm.MessageBoxResults;

/**
 * @author jojesh
 * 
 */
@SuppressWarnings("serial")
public final class PosTabOrderItemPriceDiscountEdit extends PosTab implements
		IPosFormEventsListner {
	
	public static final String TAB_CAPTION="Price";

	// - private static final int LABEL_WIDTH=80;
	private static final int LABEL_HEIGHT = 25;

	// private static final String RESET_BUTTON_NORMAL = "ctrl_reset.png";
	// private static final String RESET_BUTTON_TOUCHED =
	// "ctrl_reset_touch.png";
	//
	// private static final int RESET_BUTTON_DEF_HEIGHT = 40;
	// private static final int RESET_BUTTON_DEF_WIDTH = 50;

	private static final int DICOUNT_BUTTON_HEIGHT = 40;
	private static final int DICOUNT_BUTTON_WIDTH = 175;

	private static final int PANEL_CONTENT_H_GAP = PosOrderEntryForm.PANEL_CONTENT_H_GAP;
	private static final int PANEL_CONTENT_V_GAP = PosOrderEntryForm.PANEL_CONTENT_V_GAP;

	private static final int CONTENT_PANEL_HEIGHT = 455;// PosNumKeypad.LAYOUT_HEIGHT
	// + PANEL_CONTENT_V_GAP;
	private static final int LAYOUT_HEIGHT = CONTENT_PANEL_HEIGHT;
	private static final int LAYOUT_WIDTH = PosTabOrderItemAttributeEdit.LAYOUT_WIDTH;

	private static final int LABEL_DISCOUNT_HEADER_WIDTH = LAYOUT_WIDTH
			- PosNumKeypad.LAYOUT_WIDTH - PANEL_CONTENT_V_GAP * 2;

	private static final int LABEL_DISCOUNT_DESCRIPTION_WIDTH = LAYOUT_WIDTH
			- PosNumKeypad.LAYOUT_WIDTH - DICOUNT_BUTTON_WIDTH
			- PANEL_CONTENT_V_GAP * 3;

	private static final int TEXT_FIELD_HEIGHT = 35;
	private static final int TEXT_FIELD_WIDTH = (LABEL_DISCOUNT_HEADER_WIDTH - PANEL_CONTENT_H_GAP) / 2;

	private static final int LABEL_CUR_SYMBOL_WIDTH = 40;
	private static final int LABEL_CUR_SYMBOL_HEIGHT = LABEL_CUR_SYMBOL_WIDTH;

//	private static final Color PANEL_BG_COLOR = new Color(78, 128, 188);

	// private BeanSaleItem mPosItem;
	private BeanOrderDetail mOrderDetailItem;
	private BeanSaleItem mPosItemToEdit;;
	// private IPosItemEditFormListner mOnItemEditedListner=null;

//	private JLabel mLabelDiscountCode;
	private PosButton mButtonDiscountCode;
	private PosButton mButtonDiscountReset;
	
//	private JLabel mLabelDiscountValue;
	private PosTextField mTxtDiscountValue;
	private JLabel mLabelcurrencySymbol;

	private PosButton mButtonDiscountResetReason;
	private PosButton mButtonDiscountReason;
	private JTextArea mTxtDiscountDesc;

	private JLabel mLabelQty;
	private PosTextField mTxtQty;

	private JLabel mLabelTax;
	private PosTextField mTxtTaxValue;

	private JLabel mLabelDiscountAmount;
	private PosTextField mTxtDiscountAmount;

	private JLabel mLabelTotal = new JLabel();
	private PosTextField mTxtTotalValue;

	private PosTextField mCurrentCtrl;

	private JPanel mAmountPanel;
	private JPanel mItemPanel;
	private JPanel mDiscountPanel;
	private JLabel mLabelPrice;
	private PosTextField mTxtPrice;

	private PosSaleItemDiscountProvider mSaleItemDiscProvider;
	private PosDiscountItemProvider mDiscItemProvider;
	private static PosReasonItemProvider mReasonItemProvider;
	private ArrayList<BeanDiscount> mItemDiscountList;
	private ArrayList<BeanDiscount> mGeneralDiscountItemList;
	private ArrayList<BeanDiscount> mDiscountList;
	private ArrayList<BeanReason> mReasonList;

	private static BeanDiscount mNoneDiscount;

	// private PosDiscountObject mAdjustMentDiscount;

	public PosTabOrderItemPriceDiscountEdit(Object parent,
			BeanOrderDetail orderDetailItem) {
		super(parent, TAB_CAPTION);
		mOrderDetailItem = orderDetailItem;
		mPosItemToEdit = mOrderDetailItem.getSaleItem().clone();
		mSaleItemDiscProvider = new PosSaleItemDiscountProvider();
		mDiscItemProvider = new PosDiscountItemProvider();
		mReasonItemProvider = new PosReasonItemProvider();
		mNoneDiscount = mDiscItemProvider.getNoneDiscount();
		initControls();
		
	}
	
	/**
	 * Create the panel and initializes the controls
	 */
	private void initControls() {
		setSize(LAYOUT_WIDTH, LAYOUT_HEIGHT);

		setLayout(null);
		mAmountPanel = new JPanel();
		mAmountPanel.setBounds(0, 0, LAYOUT_WIDTH - PosNumKeypad.LAYOUT_WIDTH,
				LAYOUT_HEIGHT);
		mAmountPanel.setLayout(null);
		mAmountPanel.setBorder(new MatteBorder(0, 0, 0, 1, Color.LIGHT_GRAY));
		add(mAmountPanel);
		createDiscountPanel();
		createItemPanel();
		createKeypadControl();
		setEnableControls();
		setValues();
		setDirty(false);
			
		setDefaultFocus();	
		mTxtQty.requestFocus();

	}

	/**
	 * 
	 */
	private void setEnableControls() {
		
		BeanDiscount discount = mPosItemToEdit.getDiscount();
		mTxtTotalValue
				.setEditable(discount.getEditType() == EditTypes.NetPrice);
		mTxtPrice.setEditable(discount.getEditType() == EditTypes.UnitPrice
				|| mPosItemToEdit.isOpen());
		
		final boolean isDiscountEditable = !( discount
				.getCode().equals(PosPromotionItemProvider.DEF_PROMO_CODE) || discount
				.getCode().equals(PosDiscountItemProvider.CUSTOMER_DISCOUNT));

		final boolean isDiscountDescEditable = !(discount.getCode().equals(
				PosDiscountItemProvider.NONE_DISCOUNT_CODE) || discount
				.getCode().equals(PosPromotionItemProvider.DEF_PROMO_CODE));
		
		
		mTxtDiscountDesc.setEditable(isDiscountDescEditable && isDiscountEditable);
		mButtonDiscountResetReason.setEnabled(isDiscountDescEditable && isDiscountEditable);
		mButtonDiscountReason.setEnabled(isDiscountDescEditable && isDiscountEditable);
		
		mButtonDiscountCode.setEnabled(isDiscountEditable);
		mButtonDiscountReset.setEnabled(isDiscountEditable);
		mTxtDiscountValue.setEditable(discount.isOverridable());
		
		/**
		 * Do not allow to change quantity form here if it is added as an extra item
		 */
		if(!PosOrderUtil.getStatusString(mOrderDetailItem).trim().equals("N") || (mOrderDetailItem.getParentDtlId()!=null&& mOrderDetailItem.getParentDtlId().trim().length()>0)){
			mTxtQty.setEditable(false);
		}
		
	}

	private ActionListener mValueEnteredAction = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			onValueChanged(mCurrentCtrl.getText());
		}
	};

	/**
	 * 
	 */
	private void createItemPanel() {
		mItemPanel = new JPanel();
		mItemPanel.setLayout(null);
		mItemPanel.setBorder(new MatteBorder(0, 0, 0, 1, Color.LIGHT_GRAY));
		mAmountPanel.add(mItemPanel);
//		mItemPanel.setBackground(Color.GREEN);
		mItemPanel.setOpaque(true);
		createPriceDetails();
		final int top = mDiscountPanel.getY() + mDiscountPanel.getHeight();
		final int height = mTxtTotalValue.getY() + mTxtTotalValue.getHeight();
		mItemPanel.setBounds(0, top, LAYOUT_WIDTH - PosNumKeypad.LAYOUT_WIDTH,
				height);

	}

	/**
	 * 
	 */
	private void createDiscountPanel() {
		
		mDiscountPanel = new JPanel();
		mDiscountPanel.setLayout(null);
		mDiscountPanel.setBorder(new MatteBorder(0, 0, 0, 1, Color.LIGHT_GRAY));
		mAmountPanel.add(mDiscountPanel);
//		mDiscountPanel.setBackground(Color.RED);
		mDiscountPanel.setOpaque(true);
		createDiscount();
		final int top = 0;// mItemPanel.getY()+mItemPanel.getHeight();
		final int height = mTxtDiscountDesc.getY()
				+ mTxtDiscountDesc.getHeight();
		mDiscountPanel.setBounds(0, top, LAYOUT_WIDTH
				- PosNumKeypad.LAYOUT_WIDTH, height);

	}

	/**
	 * Sets the values of each controls in the UI
	 */
	private void setValues() {

		mTxtQty.setText(PosUomUtil.format(mPosItemToEdit.getQuantity(),mPosItemToEdit.getUom()));
		mTxtQty.setTag(mTxtQty.getText());
		mTxtPrice.setText(PosCurrencyUtil.format(PosCurrencyUtil
				.roundTo(PosSaleItemUtil.getItemFixedPrice(mPosItemToEdit))));
		// mTxtPrice.setText(PosNumberUtil.formatNumber(PosNumberUtil.roundTo(mPosItemClone.getUnitPrice())));
		mTxtPrice.setTag(mTxtPrice.getText());
		mTxtPrice.setEditable(mPosItemToEdit.isOpen());
		// mTxtQty.setEditable(!mPosItemToEdit.hasChoices());

		mTxtDiscountValue.setText(PosCurrencyUtil.format(PosCurrencyUtil
				.roundTo(mPosItemToEdit.getDiscount().getPrice()
						+ mPosItemToEdit.getDiscount().getVariants())));
		mTxtDiscountValue.setTag(mTxtDiscountValue.getText());
		mButtonDiscountCode.setText(mPosItemToEdit.getDiscount().getName());
		mTxtDiscountDesc.setText(mPosItemToEdit.getDiscount().getDescription());
		mTxtDiscountAmount.setText(PosCurrencyUtil.format(PosSaleItemUtil
				.getTotalDiscountAmount(mPosItemToEdit)));

		if (mTxtTaxValue != null)
			mTxtTaxValue.setText(PosCurrencyUtil.format(PosSaleItemUtil
					.getTotalTaxAmount(mPosItemToEdit, true)));
		mTxtTotalValue.setText(PosCurrencyUtil.format(PosSaleItemUtil
				.getGrandTotal(mPosItemToEdit)));
		mTxtTotalValue.setTag(mTxtTotalValue.getText());
		mLabelcurrencySymbol.setText((mPosItemToEdit.getDiscount()
				.isPercentage()) ? "%" : PosEnvSettings.getInstance()
				.getCurrencySymbol());
		setEnableControls();
	}

	private DocumentListener mDirtyDocumentListener = new DocumentListener() {

		@Override
		public void removeUpdate(DocumentEvent arg0) {
			setDirty(true);

		}

		@Override
		public void insertUpdate(DocumentEvent arg0) {
			setDirty(true);

		}

		@Override
		public void changedUpdate(DocumentEvent arg0) {
			setDirty(true);

		}
	};

	/**
	 * Create the quantity elements
	 */
	private void createPriceDetails() {

		int left = PANEL_CONTENT_H_GAP;
		int top = PANEL_CONTENT_V_GAP;
		top = PANEL_CONTENT_V_GAP;// labelItem.getY() + labelItem.getHeight() +
									// PANEL_CONTENT_V_GAP;
		JLabel title = PosFormUtil.setHeading("Amount & Quantity", 
				LAYOUT_WIDTH - PosNumKeypad.LAYOUT_WIDTH-PANEL_CONTENT_V_GAP*2);
		title.setLocation(left, top);
		mItemPanel.add(title);
		
		top = title.getY() + title.getHeight();
		mLabelQty =new JLabel();
		mLabelQty.setText("Quantity");
		mLabelQty.setHorizontalAlignment(SwingConstants.LEFT);
		mLabelQty.setVerticalAlignment(SwingConstants.CENTER);
		mLabelQty.setBounds(left, top, TEXT_FIELD_WIDTH, LABEL_HEIGHT);
		mLabelQty.setFont(PosFormUtil.getLabelFont());
		mLabelQty.setLocation(left, top);
		mItemPanel.add(mLabelQty);

		top = mLabelQty.getY() + mLabelQty.getHeight();
		mTxtQty = new PosTextField();
		mTxtQty.setHorizontalAlignment(PosTextField.RIGHT);
		mTxtQty.setBounds(left, top, TEXT_FIELD_WIDTH, TEXT_FIELD_HEIGHT);
		mTxtQty.setFont(PosFormUtil.getTextFieldBoldFont());
		mTxtQty.addFocusListener(focusListener);
		mTxtQty.addActionListener(mValueEnteredAction);
		mTxtQty.setDocument(PosNumberUtil.createDecimalDocument());
		mTxtQty.getDocument().addDocumentListener(mDirtyDocumentListener);
		mItemPanel.add(mTxtQty);

		left = mLabelQty.getX() + mLabelQty.getWidth() + PANEL_CONTENT_H_GAP;
		top = mLabelQty.getY();

		mLabelPrice = new JLabel();
		mLabelPrice.setText("Price");
		mLabelPrice.setHorizontalAlignment(SwingConstants.LEFT);
		mLabelPrice.setVerticalAlignment(SwingConstants.CENTER);
		mLabelPrice.setBounds(left, top, TEXT_FIELD_WIDTH, LABEL_HEIGHT);
		mLabelPrice.setFont(PosFormUtil.getLabelFont());
		mItemPanel.add(mLabelPrice);

		top = mLabelPrice.getY() + mLabelPrice.getHeight();
		mTxtPrice = new PosTextField();
		mTxtPrice.setHorizontalAlignment(PosTextField.RIGHT);
		mTxtPrice.setBounds(left, top, TEXT_FIELD_WIDTH, TEXT_FIELD_HEIGHT);
		mTxtPrice.setFont(PosFormUtil.getTextFieldBoldFont());
		mTxtPrice.addFocusListener(focusListener);
		mTxtPrice.setEditable(false);
		mTxtPrice.addActionListener(mValueEnteredAction);
		mTxtPrice.setDocument(PosNumberUtil.createDecimalDocument());
		mTxtPrice.getDocument().addDocumentListener(mDirtyDocumentListener);
		mTxtPrice.setFocusable(true);
		mItemPanel.add(mTxtPrice);

		top = mTxtPrice.getY() + mTxtPrice.getHeight() + PANEL_CONTENT_V_GAP;
		left = PANEL_CONTENT_H_GAP;

		mLabelDiscountAmount = new JLabel();
		mLabelDiscountAmount.setText("Total Discount :");
		mLabelDiscountAmount.setOpaque(true);
		mLabelDiscountAmount.setBackground(Color.lightGray);
		mLabelDiscountAmount.setHorizontalAlignment(SwingConstants.LEFT);
		mLabelDiscountAmount.setVerticalAlignment(SwingConstants.CENTER);
		mLabelDiscountAmount.setBounds(left, top, TEXT_FIELD_WIDTH,
				TEXT_FIELD_HEIGHT);
		mLabelDiscountAmount.setFont(PosFormUtil.getLabelFont());
		mItemPanel.add(mLabelDiscountAmount);

		left = mLabelDiscountAmount.getX() + mLabelDiscountAmount.getWidth()
				+ PANEL_CONTENT_V_GAP;
		mTxtDiscountAmount = new PosTextField();
		mTxtDiscountAmount.setEditable(false);
		mTxtDiscountAmount.setHorizontalAlignment(PosTextField.RIGHT);
		mTxtDiscountAmount.setBounds(left, top, TEXT_FIELD_WIDTH,
				TEXT_FIELD_HEIGHT);
		mTxtDiscountAmount.setFont(PosFormUtil.getTextFieldBoldFont());
		mTxtDiscountAmount.addFocusListener(focusListener);
		mTxtDiscountAmount.setDocument(PosNumberUtil.createDecimalDocument());
		mTxtDiscountAmount.getDocument().addDocumentListener(
				mDirtyDocumentListener);
		mItemPanel.add(mTxtDiscountAmount);

		top = mTxtDiscountAmount.getY() + mTxtDiscountAmount.getHeight()
				+ PANEL_CONTENT_V_GAP;
		left = PANEL_CONTENT_H_GAP;

		mLabelTax = new JLabel();
		mLabelTax.setText("Item Tax Amount :");
		mLabelTax.setOpaque(true);
		mLabelTax.setBackground(Color.LIGHT_GRAY);
		mLabelTax.setHorizontalAlignment(SwingConstants.LEFT);
		mLabelTax.setVerticalAlignment(SwingConstants.CENTER);
		mLabelTax.setBounds(left, top, TEXT_FIELD_WIDTH, TEXT_FIELD_HEIGHT);
		mLabelTax.setFont(PosFormUtil.getLabelFont());
		mItemPanel.add(mLabelTax);

		left = mLabelTax.getX() + mLabelTax.getWidth() + PANEL_CONTENT_V_GAP;
		mTxtTaxValue = new PosTextField();
		mTxtTaxValue.setEditable(false);
		mTxtTaxValue.setHorizontalAlignment(PosTextField.RIGHT);
		mTxtTaxValue.setBounds(left, top, TEXT_FIELD_WIDTH, TEXT_FIELD_HEIGHT);
		mTxtTaxValue.setFont(PosFormUtil.getTextFieldBoldFont());
		mTxtTaxValue.addFocusListener(focusListener);
		mTxtTaxValue.getDocument().addDocumentListener(mDirtyDocumentListener);
		mItemPanel.add(mTxtTaxValue);

		left = PANEL_CONTENT_H_GAP;
		top = mLabelTax.getY() + mLabelTax.getHeight() + PANEL_CONTENT_V_GAP;
		// }

		mLabelTotal.setText("Item Total Amount :");
		mLabelTotal.setOpaque(true);
		mLabelTotal.setBackground(Color.LIGHT_GRAY);
		mLabelTotal.setHorizontalAlignment(SwingConstants.LEFT);
		mLabelTotal.setVerticalAlignment(SwingConstants.CENTER);
		mLabelTotal.setBounds(left, top, TEXT_FIELD_WIDTH, TEXT_FIELD_HEIGHT);
		mLabelTotal.setFont(PosFormUtil.getLabelFont());
		mItemPanel.add(mLabelTotal);

		left = mLabelTotal.getX() + mLabelTotal.getWidth()
				+ PANEL_CONTENT_V_GAP;
		mTxtTotalValue = new PosTextField();
		mTxtTotalValue.setHorizontalAlignment(PosTextField.RIGHT);
		mTxtTotalValue
				.setBounds(left, top, TEXT_FIELD_WIDTH, TEXT_FIELD_HEIGHT);
		mTxtTotalValue.setFont(PosFormUtil.getTextFieldBoldFont());
		mTxtTotalValue.addFocusListener(focusListener);
		mTxtTotalValue.setEditable(false);
		mTxtTotalValue.addActionListener(mValueEnteredAction);
		mTxtTotalValue.setDocument(PosNumberUtil.createDecimalDocument());
		mTxtTotalValue.getDocument()
				.addDocumentListener(mDirtyDocumentListener);
		mItemPanel.add(mTxtTotalValue);
	}

	/**
	 * Create the discount elements
	 */
	private void createDiscount() {
		
		int left = PANEL_CONTENT_H_GAP;
		int top = PANEL_CONTENT_V_GAP;
		JLabel labelDiscount = PosFormUtil.setHeading("Discount", LABEL_DISCOUNT_HEADER_WIDTH);
		labelDiscount.setLocation(left, top);
		mDiscountPanel.add(labelDiscount);

		top = labelDiscount.getY() + labelDiscount.getHeight()
				+ PANEL_CONTENT_V_GAP;
		mButtonDiscountCode = new PosButton();
		mButtonDiscountCode.setBounds(left, top, DICOUNT_BUTTON_WIDTH,
				DICOUNT_BUTTON_HEIGHT);
		mButtonDiscountCode.setImage("item_edit_discount_select.png");
		mButtonDiscountCode
				.setTouchedImage("item_edit_discount_select_touch.png");
		mButtonDiscountCode.setOnClickListner(discountCodeListener);
		mDiscountPanel.add(mButtonDiscountCode);


		left = mButtonDiscountCode.getX() + mButtonDiscountCode.getWidth()
				+ PANEL_CONTENT_H_GAP;
		top = mButtonDiscountCode.getY();
		mTxtDiscountValue = new PosTextField();
		mTxtDiscountValue.setHorizontalAlignment(PosTextField.RIGHT);
		mTxtDiscountValue.setBounds(left, top, LABEL_DISCOUNT_DESCRIPTION_WIDTH
				- LABEL_CUR_SYMBOL_WIDTH - PANEL_CONTENT_H_GAP,
				mButtonDiscountCode.getHeight());
		mTxtDiscountValue.setEditable(false);
		mTxtDiscountValue.setFont(PosFormUtil.getTextFieldBoldFont());
		mTxtDiscountValue.addFocusListener(focusListener);
		mTxtDiscountValue.addActionListener(mValueEnteredAction);
		mTxtDiscountValue.setDocument(PosNumberUtil.createDecimalDocument());
		mTxtDiscountValue.getDocument().addDocumentListener(
				mDirtyDocumentListener);
		mDiscountPanel.add(mTxtDiscountValue);

		left = mTxtDiscountValue.getX() + mTxtDiscountValue.getWidth()
				+ PANEL_CONTENT_H_GAP;
		mLabelcurrencySymbol = new JLabel();
		mLabelcurrencySymbol.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
		mLabelcurrencySymbol.setHorizontalAlignment(SwingConstants.CENTER);
		mLabelcurrencySymbol.setBounds(left, top, LABEL_CUR_SYMBOL_WIDTH,
				LABEL_CUR_SYMBOL_HEIGHT);
		mLabelcurrencySymbol.setFont(PosFormUtil.getTextFieldBoldFont());
		mDiscountPanel.add(mLabelcurrencySymbol);

		// left = PANEL_CONTENT_H_GAP;
		// top = mTxtDiscountValue.getY() + mTxtDiscountValue.getHeight()
		// + PANEL_CONTENT_V_GAP;
		// mButtonDiscountDesc = new PosButton();
		// mButtonDiscountDesc.setText("Reason");
		// mButtonDiscountDesc.setBounds(left, top, 175, 40);
		// mButtonDiscountDesc.setImage("item_edit_discount_select.png");
		// mButtonDiscountDesc
		// .setTouchedImage("item_edit_discount_select_touch.png");
		// mButtonDiscountDesc.setOnClickListner(reasonsListener);
		// mDiscountPanel.add(mButtonDiscountDesc);
		//
		// left = mButtonDiscountDesc.getX() + mButtonDiscountDesc.getWidth() +
		// 2;
		// top = mTxtDiscountValue.getY() + mTxtDiscountValue.getHeight()
		// + PANEL_CONTENT_V_GAP;
		// mButtonDiscountReset = new PosButton();
		// mButtonDiscountReset.setBounds(left, top, RESET_BUTTON_DEF_WIDTH,
		// RESET_BUTTON_DEF_HEIGHT);
		// mButtonDiscountReset.setImage(RESET_BUTTON_NORMAL);
		// mButtonDiscountReset.setTouchedImage(RESET_BUTTON_TOUCHED);
		// mButtonDiscountReset.setOnClickListner(new IPosButtonListner() {
		//
		// @Override
		// public void onClicked(PosButton button) {
		// mTxtDiscountDesc.setText("");
		// }
		// });
		// mDiscountPanel.add(mButtonDiscountReset);

		left = PANEL_CONTENT_H_GAP;
		top = mTxtDiscountValue.getY() + mTxtDiscountValue.getHeight()
				+ PANEL_CONTENT_V_GAP;
		mButtonDiscountReason = new PosButton();
		mButtonDiscountReason.setText("Select Reason");
		mButtonDiscountReason.setBounds(left, top, DICOUNT_BUTTON_WIDTH,
				DICOUNT_BUTTON_HEIGHT);
		mButtonDiscountReason.setImage("item_edit_discount_select.png");
		mButtonDiscountReason
				.setTouchedImage("item_edit_discount_select_touch.png");
		mButtonDiscountReason.setOnClickListner(reasonsListener);
		mDiscountPanel.add(mButtonDiscountReason);

		left = PANEL_CONTENT_H_GAP;
		top = mButtonDiscountReason.getY() + mButtonDiscountReason.getHeight()
				+ PANEL_CONTENT_V_GAP;
		mButtonDiscountResetReason = new PosButton();
		mButtonDiscountResetReason.setText("Clear Reason");
		mButtonDiscountResetReason.setBounds(left, top, DICOUNT_BUTTON_WIDTH,
				DICOUNT_BUTTON_HEIGHT);
		mButtonDiscountResetReason.setImage("item_edit_discount_reson_clear.png");
		mButtonDiscountResetReason
				.setTouchedImage("item_edit_discount_reson_clear_touch.png");
		mButtonDiscountResetReason.setOnClickListner(new IPosButtonListner() {

			@Override
			public void onClicked(PosButton button) {
				mTxtDiscountDesc.setText("");
			}
		});
		mDiscountPanel.add(mButtonDiscountResetReason);
		
		left = PANEL_CONTENT_H_GAP;
		top = mButtonDiscountResetReason.getY() + mButtonDiscountResetReason.getHeight()
				+ PANEL_CONTENT_V_GAP;
		mButtonDiscountReset = new PosButton();
		mButtonDiscountReset.setText("Remove Discount");
		mButtonDiscountReset.setMnemonic('u');
		mButtonDiscountReset.setBounds(left, top, DICOUNT_BUTTON_WIDTH,
				DICOUNT_BUTTON_HEIGHT);
		mButtonDiscountReset.setImage("item_edit_discount_reset.png");
		mButtonDiscountReset
				.setTouchedImage("item_edit_discount_reset_touch.png");
		mButtonDiscountReset.setOnClickListner(new IPosButtonListner() {

			@Override
			public void onClicked(PosButton button) {
				
				if(PosFormUtil.showQuestionMessageBox(mParent, 
						MessageBoxButtonTypes.YesNo, 
						"This will reset the discount settings. Do you want to continue?", 
						null)==MessageBoxResults.Yes){
					BeanDiscount discount=mDiscItemProvider.getNoneDiscount();
					applyDiscount(discount);
				}
			}
			
		});
		
		mDiscountPanel.add(mButtonDiscountReset);

		final int scrollHeight = 140;// mButtonDiscountDesc.getHeight()+mButtonDiscountReset.getHeight()+PANEL_CONTENT_V_GAP;
		top = mButtonDiscountReason.getY();
		left = mButtonDiscountReason.getX() + mButtonDiscountReason.getWidth()
				+ PANEL_CONTENT_H_GAP;
		mTxtDiscountDesc = new JTextArea();
		mTxtDiscountDesc.setLineWrap(true);
		mTxtDiscountDesc.setWrapStyleWord(false);
		mTxtDiscountDesc.setFont(PosFormUtil.getTextFieldFont());
		mTxtDiscountDesc.addMouseListener(onDiscDescriptionfocus);
		mTxtDiscountDesc.setBounds(left, top, LABEL_DISCOUNT_DESCRIPTION_WIDTH,
				scrollHeight);
		mTxtDiscountDesc.setEditable(false);
		mTxtDiscountDesc.getDocument().addDocumentListener(
				mDirtyDocumentListener);
		JScrollPane scrollPane = new JScrollPane(mTxtDiscountDesc);
		scrollPane.setBounds(left, top, LABEL_DISCOUNT_DESCRIPTION_WIDTH,
				scrollHeight);
		mDiscountPanel.add(scrollPane);
	}

	/**
	 * Creates the number keypad object and attach to the UI
	 */
	private PosNumKeypad mPosNumKeypad;

	private void createKeypadControl() {
		mPosNumKeypad = new PosNumKeypad();
		mPosNumKeypad.setFiledDocument(PosNumberUtil
				.createDecimalDocument(false));
		final int left = LAYOUT_WIDTH - PosNumKeypad.LAYOUT_WIDTH;
		// final int top=30;
		final int top = PANEL_CONTENT_V_GAP;
		mPosNumKeypad.setLocation(left, top);
		mPosNumKeypad.setBorder(null);
		mPosNumKeypad.setOnAcceptListner(posNumkeypadListner);
		mPosNumKeypad.setCancelActions(CancelActions.Clear);
		add(mPosNumKeypad);
	}

	/**
	 * Listener for mouse click in the description area.
	 */
	private MouseListener onDiscDescriptionfocus = new MouseAdapter() {
		public void mouseClicked(java.awt.event.MouseEvent e) {
			if (mTxtDiscountDesc.isEditable())
				PosFormUtil.showSoftKeyPad((JDialog)mParent, mTxtDiscountDesc,
						new PosSoftKeypadAdapter() {
							@Override
							public void onAccepted(String text) {
								mPosItemToEdit.getDiscount().setDescription(
										text);
								setDiscountAppliedLevel(mPosItemToEdit
										.getDiscount());
							}
						});
		};
	};

	/**
	 * This function will set the applied at flag to ITEM. This will be used
	 * later to check from which screen the discount is applied. Discount can be
	 * applied from ITEM editing or BILL editing.
	 * 
	 * @param discount
	 */
	private void setDiscountAppliedLevel(BeanDiscount discount) {
		try {
			if (!discount.getCode().equals(
					PosDiscountItemProvider.NONE_DISCOUNT_CODE))
				discount.setAppliedAt(AppliedAt.ITEM_LEVEL);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Focus listener to set the active editing control
	 */
	private FocusListener focusListener = new FocusAdapter() {
		@Override
		public void focusGained(FocusEvent e) {
			PosTextField source = (PosTextField) e.getSource();
			if (!source.isEditable())
				return;
			if (mCurrentCtrl != null) {
				String oldValue = String.valueOf(mCurrentCtrl.getTag());
				if (oldValue.equals(mCurrentCtrl.getText()))
					mCurrentCtrl.setBackground(Color.WHITE);
				else
					mCurrentCtrl.setBackground(Color.GREEN);
			}
			// mPosNumKeypad.setDisplayTextField(source);
			mCurrentCtrl = source;
			mCurrentCtrl.setBackground(Color.RED);
		}
	};

	/**
	 * Listener to show the reason browser.
	 */
	private IPosButtonListner reasonsListener = new PosButtonListnerAdapter() {
		@Override
		public void onClicked(PosButton button) {
			mCurrentCtrl = null;
			if (mReasonList == null)
				mReasonList = mReasonItemProvider.getReasonItemList();
			IPosBrowsableItem[] itemList = new IPosBrowsableItem[mReasonList
					.size()];
			mReasonList.toArray(itemList);
			PosObjectBrowserForm objectBrowser = new PosObjectBrowserForm(
					"Reasons", itemList, ItemSize.Wider, 5, 3);
			objectBrowser.setListner(new IPosObjectBrowserListner() {
				@Override
				public void onItemSelected(IPosBrowsableItem item) {
					BeanReason reason = (BeanReason) item;
					mPosItemToEdit.getDiscount().setDescription(
							reason.getDescription());
					setDiscountAppliedLevel(mPosItemToEdit.getDiscount());
					setValues();
				}

				@Override
				public void onCancel() {
					// TODO Auto-generated method stub

				}
			});
			PosFormUtil.showLightBoxModal(mParent, objectBrowser);
		}
	};

	/**
	 * Listener for showing the discount browser.
	 */
	private IPosButtonListner discountCodeListener = new PosButtonListnerAdapter() {
		@Override
		public void onClicked(PosButton button) {
			if (mPosItemToEdit.getDiscount() != null
					&& mPosItemToEdit.getDiscount().isPromotion()
					&& mPosItemToEdit.getDiscount().getCode() != PosPromotionItemProvider.DEF_PROMO_CODE) {

				PosFormUtil.showInformationMessageBox(getPosParent(),
						"Since Promotion exists, no Discount available.");
				return;
			}
			mCurrentCtrl = null;
			if (mDiscountList == null) {
				mItemDiscountList = mSaleItemDiscProvider
						.getSaleItemDiscountList(mPosItemToEdit.getId());
				mGeneralDiscountItemList = mDiscItemProvider
						.getGeneralDiscounts(PermittedLevel.ITEM);
				mDiscountList = new ArrayList<BeanDiscount>();
				mDiscountList.addAll(mGeneralDiscountItemList);
				mDiscountList.addAll(mItemDiscountList);
			}
			IPosBrowsableItem[] itemList = new IPosBrowsableItem[mDiscountList
					.size()];
			mDiscountList.toArray(itemList);
			PosObjectBrowserForm objectBrowser = new PosObjectBrowserForm(
					"Discounts", itemList, ItemSize.Wider, 4, 3);
			objectBrowser.setListner(new IPosObjectBrowserListner() {
				@Override
				public void onItemSelected(IPosBrowsableItem item) {
					BeanDiscount discount = (BeanDiscount) item;
					if (validateDiscount(discount)) {
						applyDiscount(discount);
					}
				}

				@Override
				public void onCancel() {
				}
			});
			PosFormUtil.showLightBoxModal(mParent, objectBrowser);
		}
	};
	
	private void applyDiscount(BeanDiscount discount){
		
		mPosItemToEdit.setDiscount(discount.clone());
		setDiscountAppliedLevel(mPosItemToEdit.getDiscount());
		setDirty(true);
		resetItem();
		
	}

	private void resetItem() {
		setDirty(false);
		PosTaxUtil.calculateTax(mPosItemToEdit);
		setValues();
	}

	private boolean validateDiscount(BeanDiscount discount) {
		boolean isValid = true;
		String disPassword =  (discount.getDiscountPassword()!=null)?discount.getDiscountPassword().trim():null;
		if (disPassword != null && !disPassword.isEmpty()) {
			if (!PosPasswordUtil.getValidateDiscountPassword(disPassword)) {
				PosFormUtil.showErrorMessageBox(mParent, "Wrong password.");
				return false;
			}
		}
		if (mPosItemToEdit.getQuantity() < discount.getRequiredQuantity()) {
			isValid = false;
			PosFormUtil
					.showErrorMessageBox(getPosParent(),
							"Item does not have enough quantity to apply this discount.");
		} else if (discount.getEditType() == EditTypes.UnitPrice
				&& mPosItemToEdit.isOpen() && isValid) {
			isValid = false;
			PosFormUtil
					.showErrorMessageBox(
							getPosParent(),
							"The selected discount is not applicable to this sale item. Item is open. Please adjust price.");
		}
		// else
		// if(discount.getPrice()>PosSaleItemUtil.getItemFixedPrice(mPosItemClone)&&!discount.isPercentage()){
		// isValid=false;
		// PosFormUtil.showErrorMessageBox(null,"The selected discount is not applicable to this sale item. Discount amount is greater than item price.");
		// }
		return isValid;
	}

	/**
	 * Listener for the number keypad. When value is edited it should change the
	 * background color.
	 */
	private IPosNumkeypadListner posNumkeypadListner = new PosNumkeypadListnerAdapter() {
		@Override
		public void onAcceptButton(String value) {
			onValueChanged(value);
		}
	};
	
	public boolean onValidating() {
		
		boolean result=true;
		
		double value = PosNumberUtil.parseDoubleSafely(mTxtPrice.getText());
		
		if (value <= 0) {
			PosFormUtil.showErrorMessageBox(getPosParent(),
					"Amount should be greater than 0.");
			result=false;
			
			mCurrentCtrl=mTxtPrice;
		}
		
		if(!mPosItemToEdit.getDiscount().isPercentage() && 
			  PosNumberUtil.parseDoubleSafely(mTxtDiscountValue.getText())>value){
			
			PosFormUtil.showErrorMessageBox(getPosParent(),
					"Discount price should not be greater than item price.");
			mCurrentCtrl=mTxtDiscountValue;
			result=false;
			
		}		
		
		
		return result;
	};

	private void onValueChanged(String value) {
		if (mCurrentCtrl == null)
			return;
		if (mCurrentCtrl == mTxtQty) {
			double newQty = PosNumberUtil.parseDoubleSafely(value);
			if (!mTxtQty.isEditable() && PosNumberUtil.parseDoubleSafely(mTxtQty.getText())!=newQty ){
				PosFormUtil.showErrorMessageBox(getPosParent(),
						"This item has been already printed to kitchen, Please add as new item.");
				newQty = mPosItemToEdit.getQuantity();
				return;
			}else if (newQty <= 0) {
				PosFormUtil.showErrorMessageBox(getPosParent(),
						"Qunatity should be greater than 0.");
				newQty = mPosItemToEdit.getQuantity();
			}
			mCurrentCtrl.setText(PosUomUtil.format(newQty,mPosItemToEdit.getUom()));
			mPosItemToEdit.setQuantity(newQty);
		} else {
			if ( Double.parseDouble(value)== Double.parseDouble(String.valueOf(mCurrentCtrl.getTag())))
				return;
			mCurrentCtrl.setText(FormatString(value));
			final double newValue = Math.abs(Double.parseDouble(value));
			if (mCurrentCtrl == mTxtPrice) {
				final double oldValue= Math.abs(Double.parseDouble(mTxtPrice.getTag().toString()));
			 

				if(oldValue!=newValue)
				{
					mPosItemToEdit.setCustomerPrice(0);
					if (mPosItemToEdit.isOpen()) {
						mPosItemToEdit.setFixedPrice(newValue);
						PosTaxUtil.calculateTax(mPosItemToEdit);
					} else {
						setAdjustmentOnUnit(mPosItemToEdit.getDiscount());
					}
				}

			} else if (mCurrentCtrl == mTxtDiscountValue) {
				// if(!mPosItemClone.getDiscount().isPercentage()&&newValue>PosSaleItemUtil.getItemFixedPrice(mPosItemClone)){
				// PosFormUtil.showErrorMessageBox(null," The entered discount is not applicable to this item.Discount amount is greater than item price.");
				// }else{
				mPosItemToEdit.getDiscount().setPrice(newValue);
				setDiscountAppliedLevel(mPosItemToEdit.getDiscount());
				// }
			} else if (mCurrentCtrl == mTxtTotalValue) {
				if (newValue > PosSaleItemUtil
						.getItemFixedPrice(mPosItemToEdit)
						* mPosItemToEdit.getQuantity()) {
					PosFormUtil
							.showErrorMessageBox(getPosParent(),
									" Can not give a price greater than the total amount.");
				} else {
					setAdjustmentOnTotal(mPosItemToEdit.getDiscount(), newValue);
					setDiscountAppliedLevel(mPosItemToEdit.getDiscount());
				}

			}
		}
		mCurrentCtrl.setBackground(Color.GREEN);
		resetItem();
	}

	private void setAdjustmentOnTotal(BeanDiscount adjDiscount, double newTotal) {
		mPosItemToEdit.setDiscount(mNoneDiscount);
		PosTaxUtil.calculateTax(mPosItemToEdit);
		final double discAmo = PosSaleItemUtil.getGrandTotal(mPosItemToEdit)
				- newTotal;
		adjDiscount.setPrice(discAmo / mPosItemToEdit.getQuantity());
		// adjDiscount.setVariants(discAmo/mPosItemClone.getQuantity());
		mPosItemToEdit.setDiscount(adjDiscount);
		PosTaxUtil.calculateTax(mPosItemToEdit);
	}

	private void setAdjustmentOnUnit(BeanDiscount adjDiscount) {
		adjDiscount.setPrice(0);
		mPosItemToEdit.setDiscount(adjDiscount);
		final double curUnitPrice = PosSaleItemUtil
				.getItemFixedPrice(mPosItemToEdit);
		final double newUnitPrice = PosNumberUtil
				.getValueFormUIComponenet(mTxtPrice);
		final double discAmo = curUnitPrice - newUnitPrice;
		adjDiscount.setPrice(discAmo);
		// adjDiscount.setVariants(discAmo);
		mPosItemToEdit.setDiscount(adjDiscount);
		setValues();
	}

	public static String FormatString(String inputString) {
		String FormatString = "0";
		if (inputString.indexOf(".") < 0) {
			FormatString = inputString + ".0";
			return FormatString;
		} else {
			return inputString;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.indocosmo.pos.forms.listners.IPosFormEventsListner#onOkButtonClicked
	 * ()
	 */
	@Override
	public boolean onOkButtonClicked() {
		
		if(!onValidating()) return false;
		
		if (isDirty()) {
			
			if (mCurrentCtrl !=null)  
					onValueChanged(mCurrentCtrl.getText());
			
			mOrderDetailItem.getSaleItem().setDiscount(
					mPosItemToEdit.getDiscount());
			mOrderDetailItem.getSaleItem().setFixedPrice(
					mPosItemToEdit.getFixedPrice()); // use the fixed price
			// to set the price
			// since it is
			// needed to
			// calculates the
			// taxes
			// mPosItem.setUnitPrice(mPosItemClone.getUnitPrice());
			// TAXDISC130203
			mOrderDetailItem.getSaleItem().setCustomerPrice(mPosItemToEdit.getCustomerPrice());
			mOrderDetailItem.getSaleItem().setQuantity(
					mPosItemToEdit.getQuantity());
			PosTaxUtil.calculateTax(mOrderDetailItem.getSaleItem());
			mOrderDetailItem.setDirty(true);
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.indocosmo.pos.forms.listners.IPosFormEventsListner#onCancelButtonClicked
	 * ()
	 */
	@Override
	public boolean onCancelButtonClicked() {
		// TODO Auto-generated method stub
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.indocosmo.pos.forms.listners.IPosFormEventsListner#onResetButtonClicked
	 * ()
	 */
	@Override
	public void onResetButtonClicked() {
		reset();
		setValues();
		setDirty(false);
	}

	private void setDefaultFocus() {
		
		if (mCurrentCtrl == null){
		
			if(mPosItemToEdit.isOpen())
				mCurrentCtrl =mTxtPrice;
			else if (mPosItemToEdit.getDiscount().isOverridable())
				mCurrentCtrl =mTxtDiscountValue;
			else
				mCurrentCtrl=mTxtQty;
			
		}
//			mCurrentCtrl = (mPosItemToEdit.getDiscount().isOverridable()) ? mTxtDiscountValue
//					: 
		
		mCurrentCtrl.requestFocus();
		mCurrentCtrl.selectAll();
	}

	private void reset() {
		mTxtPrice.setBackground(Color.WHITE);
		mTxtDiscountValue.setBackground(Color.WHITE);
		mTxtQty.setBackground(Color.WHITE);
		mTxtTotalValue.setBackground(Color.WHITE);
		mPosItemToEdit = mOrderDetailItem.getSaleItem().clone();
		setDefaultFocus();
	}

	@Override
	public void onGotFocus() {
		
		setDefaultFocus();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.indocosmo.pos.forms.listners.IPosFormMethodes#setReadOnly(boolean)
	 */
	@Override
	public void setReadOnly(boolean isReadOnly) {
		mButtonDiscountCode.setEnabled(!isReadOnly);
		mTxtDiscountValue.setEditable(!isReadOnly);
		mButtonDiscountResetReason.setEnabled(!isReadOnly);
		mButtonDiscountReason.setEnabled(!isReadOnly);
		mButtonDiscountReset.setEnabled(!isReadOnly);
		mTxtDiscountDesc.setEditable(!isReadOnly);
		mTxtQty.setEditable(PosOrderUtil.getStatusString(mOrderDetailItem).trim().equals("N") && !isReadOnly);
		mTxtTaxValue.setEditable(!isReadOnly);
		mTxtDiscountAmount.setEditable(!isReadOnly);
		mTxtTotalValue.setEditable(!isReadOnly);
		mTxtPrice.setEditable(!isReadOnly);
	}

	public double getEnteredQty() {
		final double qty = PosNumberUtil.parseDoubleSafely(mTxtQty.getText());
		return qty;
	}
}
