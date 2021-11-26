package com.indocosmo.pos.forms.components.itemcontrols;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;

import com.indocosmo.pos.common.PosEnvSettings;
import com.indocosmo.pos.common.PosLog;
import com.indocosmo.pos.common.beans.settings.ui.orderentry.BeanUIOrderItemListPanelSetting;
import com.indocosmo.pos.common.utilities.PosCurrencyUtil;
import com.indocosmo.pos.common.utilities.PosFormUtil;
import com.indocosmo.pos.common.utilities.PosResUtil;
import com.indocosmo.pos.common.utilities.PosSaleItemUtil;
import com.indocosmo.pos.common.utilities.PosUomUtil;
import com.indocosmo.pos.data.beans.BeanDiscount;
import com.indocosmo.pos.data.beans.BeanDiscount.AppliedAt;
import com.indocosmo.pos.data.beans.BeanDiscount.PermittedLevel;
import com.indocosmo.pos.data.beans.BeanOrderDetail;
import com.indocosmo.pos.data.beans.BeanOrderDetail.OrderDetailItemType;
import com.indocosmo.pos.data.beans.BeanSaleItem;
import com.indocosmo.pos.data.providers.shopdb.PosDiscountItemProvider;
import com.indocosmo.pos.data.providers.shopdb.PosSaleItemProvider;
import com.indocosmo.pos.forms.components.itemcontrols.listners.IPosOrderListItemControlListner;
import com.indocosmo.pos.forms.components.orderentry.PosOrderListPanel;
import com.indocosmo.pos.forms.components.search.IPosSearchableItem;

@SuppressWarnings("serial")
public  class PosOrderListItemControl extends JPanel implements Cloneable , IPosSearchableItem{

	protected static final int PANAL_CONTENT_H_GAP=2;
	protected static final int PANAL_CONTENT_V_GAP=2;
	
	private static final String DELETED_ICON="item_deleted_ico.png";

	private static final float ITEM_NAM_FONT_SIZE=PosEnvSettings.getInstance().getUISetting().getOrderEntryUISettings().getOrderItemListPanelSetting().getItemNameFontSize();
	private static final float ITEM_PRPRTY_FONT_SIZE=PosEnvSettings.getInstance().getUISetting().getOrderEntryUISettings().getOrderItemListPanelSetting().getItemDetailsFontSize();
	public static final int LAYOUT_WIDTH=318;
	public static int LAYOUT_HEIGHT;
	private static int ITM_PRPRTY_FONT_HEIGHT;
	private static int ITM_NAM_FONT_HEIGHT;
	private static int ICON_HEIGHT=21;
	private static int ICON_WIDTH=21;

	private static final Color SELECTED_ITEM_BG_COLOR=Color.CYAN;// new Color(179, 34, 65);
	private static final Color ITEM_DISCOUNT_HIGHLIGHTED_COLOR=new Color(255,200,200);
	private static final Color BILL_DISCOUNT_HIGHLIGHTED_COLOR=new Color(200,255,200);
	private static final Color CHOICE_CONTENT_ITEM_INDI_COLOR=new Color(200, 150, 215);//Color(100, 205, 205);//new Color(208, 32, 144);
	private static final Color COMBO_CONTENT_ITEM_INDI_COLOR=new Color(205, 205, 0);

	protected static final int TOP_ROW_DTL_WIDTH=(LAYOUT_WIDTH-PANAL_CONTENT_H_GAP*2)/3;
	protected static final int TITLE_COL_WIDTH=LAYOUT_WIDTH-PANAL_CONTENT_H_GAP*2;
	protected static final int END_ROW_QUANTITY_PANEL_WIDTH=70;//(LAYOUT_WIDTH-PANAL_CONTENT_H_GAP*2)/8;
	protected static final int END_ROW_DTL_PANL_WIDTH=((LAYOUT_WIDTH-PANAL_CONTENT_H_GAP*2)-END_ROW_QUANTITY_PANEL_WIDTH)/3;



	/**
	 * Use this for debugging.
	 * Set COL_BORDER_WIDTH=1 to view the layout boundaries. 
	 * */
	private static final int COL_BORDER_WIDTH=0;
	private static final int INDICATOR_LAYOUT_WIDTH=3;
	private static final int ITEM_PADDING_HIGHT = 2;

	protected JLabel mLabelItemSerialNumber; 
	protected JLabel mLabelQuantityValue;
	protected JLabel mLabelPriceValue;
	protected JLabel mLabelTaxValue;
	protected JLabel mLabelTaxTitle;
	protected JLabel mLabelDiscountValue;
	protected JLabel mLabelDiscountTitle; 
	protected JLabel mLabelTotalValue;
	protected JLabel mLabelItemCode;
	protected JLabel mLabelItemName;
	protected JLabel mLabelAltItemName;
	protected JLabel mQuantityTitle;
	protected JLabel mPriceTitle;
	protected JLabel mLabelExtras;
	protected JLabel mLabelTray;
	protected JLabel mLabelRemarks;
	protected JLabel mLabelDeletedIcon;

	protected JPanel modifierIndicator;
	private static ImageIcon mImage = PosResUtil.getImageIconFromResource("indicator_modified.png");
	private static ImageIcon rImage = PosResUtil.getImageIconFromResource("indicator_remarked.png");
	private static ImageIcon tImage = PosResUtil.getImageIconFromResource("indicator_tray.png");
	protected JPanel quantitySPEC;
	protected JPanel priceSPECS;
	protected JPanel discountSPECS;
	protected JPanel taxSPECS;

	private int  mItemSerialNumber; 
	private int  mItemIndex; 

	private IPosOrderListItemControlListner mGridItemSelectedListner;
	private boolean mIsSelected=false;
	private boolean mReadOnly=false;
	private boolean mHighlighteDiscounted=false;
	protected BeanOrderDetail mOrderDetailItem;
	protected BeanSaleItem mPosSaleItem;
	protected boolean mIsComboItem=false;
	protected boolean mIsComboContentItem=false;
	protected JPanel mSubItemIndicator;
	protected JPanel mParentItemIndicator;
	private JPanel mItemPropertyContainer;
	private boolean mEdited;
	private boolean mKitchenPrinted;
	private BeanUIOrderItemListPanelSetting orderItemListPanelSetting;
	private static ImageIcon itemDeletedIco;

	protected Font mDisplayItemDetailFont=new Font(PosEnvSettings.getInstance().getUISetting().getLabelFont(), Font.PLAIN,ITEM_NAM_FONT_SIZE<13?13:(int)ITEM_NAM_FONT_SIZE);
	protected Font mDisplayItemFont=mDisplayItemDetailFont.deriveFont( Font.BOLD,ITEM_PRPRTY_FONT_SIZE<15?15f:ITEM_PRPRTY_FONT_SIZE);

	public PosOrderListItemControl() {
		super();
		initComponent();
	}

	public PosOrderListItemControl(boolean highlighteDiscounted) {
		super();
		initComponent();
		mHighlighteDiscounted=highlighteDiscounted;
	}

	private Color getItemBGColor(){
		BeanDiscount disc=mPosSaleItem.getDiscount();
			return  (mHighlighteDiscounted 
				&& !disc.getCode().equals(PosDiscountItemProvider.NONE_DISCOUNT_CODE) && 
				disc.getAppliedAt()==AppliedAt.ITEM_LEVEL)?
						(disc.getPermittedLevel()==PermittedLevel.BILL)?BILL_DISCOUNT_HIGHLIGHTED_COLOR:
							ITEM_DISCOUNT_HIGHLIGHTED_COLOR:(mIsSelected)?SELECTED_ITEM_BG_COLOR:getItemDefBgColor();

	}

	private Color getItemDefBgColor(){
		Color bgColor=Color.WHITE;
		if((mOrderDetailItem.getComboSubstitutes()!=null && mOrderDetailItem.getComboSubstitutes().size()>0) || 
				(mOrderDetailItem.getExtraItemList()!=null && mOrderDetailItem.getExtraItemList().size()>0)){
			bgColor=((!mOrderDetailItem.getSaleItem().isComboItem())?CHOICE_CONTENT_ITEM_INDI_COLOR:COMBO_CONTENT_ITEM_INDI_COLOR);
			mParentItemIndicator.setBackground(bgColor);
		}
		return bgColor;// new Color(bgColor.getRed(),bgColor.getGreen(),bgColor.getBlue(),100);
	}

	protected void initComponent(){

		
		if(itemDeletedIco==null)
			itemDeletedIco=PosResUtil.getImageIconFromResource(DELETED_ICON);

		orderItemListPanelSetting=PosEnvSettings.getInstance().getUISetting().getOrderEntryUISettings().getOrderItemListPanelSetting();

		
		declareComponents();
		initComponentsLayout();
		addComponentsToPropertyPanel();
		
		setPreferredSize(new Dimension(LAYOUT_WIDTH,LAYOUT_HEIGHT));
		setBorder(new MatteBorder(0, 0, 1, 0, PosOrderListPanel.GRID_ITEM_BORDER_COLOR));
		addMouseListener(itemMousePressedListner);
		setLayout(null);
		
		mItemPropertyContainer.setSize(LAYOUT_WIDTH-PANAL_CONTENT_H_GAP*2,LAYOUT_HEIGHT-PANAL_CONTENT_V_GAP*2);
		mItemPropertyContainer.setLocation(PANAL_CONTENT_H_GAP, PANAL_CONTENT_V_GAP);
		mItemPropertyContainer.setOpaque(false);
		mItemPropertyContainer.setLayout(new FlowLayout(FlowLayout.LEFT,0,0));
		setSubItemIndicator();

		setParentItemIndicator();
		add(mItemPropertyContainer);

	}

	/**
	 * 
	 */
	private void addComponentsToPropertyPanel() {
		
		mItemPropertyContainer.add(mLabelItemSerialNumber);
		
		mItemPropertyContainer.add(modifierIndicator);
		
		mItemPropertyContainer.add(mLabelTotalValue);
		
		mItemPropertyContainer.add(mLabelItemName);
		
		mItemPropertyContainer.add(mLabelDeletedIcon);
		
		if(orderItemListPanelSetting.canShowQuantity())
			mItemPropertyContainer.add(quantitySPEC);

		if(orderItemListPanelSetting.canShowPrice())
			mItemPropertyContainer.add(priceSPECS);

		if(orderItemListPanelSetting.canShowDiscount())
			mItemPropertyContainer.add(discountSPECS);

		if(orderItemListPanelSetting.canShowTax())
			mItemPropertyContainer.add(taxSPECS);
		
		/**
		 * calculating the parent container dimensions.
		 */
		calculateLayoutHeight();
	}

	/**
	 * 
	 */
	private void calculateLayoutHeight() {

		LAYOUT_HEIGHT=(ITM_NAM_FONT_HEIGHT+ITEM_PADDING_HIGHT*2)+2*(ITM_PRPRTY_FONT_HEIGHT+ITEM_PADDING_HIGHT*2)+PANAL_CONTENT_V_GAP*2;
	}

	/**
	 * 
	 */
	private void declareComponents() {
		// TODO Auto-generated method stub
		
		mItemPropertyContainer=new JPanel();
		
		/** Serial number **/
		mLabelItemSerialNumber = new JLabel("1");

		/** Item code **/
		mLabelItemCode = new JLabel("0001");

		/** Item name **/
		mLabelItemName = new JLabel("MAGGI NOODLES");
		
		mLabelDeletedIcon=new JLabel();

		/** Total Value  **/
		mLabelTotalValue = new JLabel("");

		/** Quantity  **/
		mQuantityTitle = new JLabel("Q:");
		mLabelQuantityValue = new JLabel("100.00");
		mLabelQuantityValue.setBorder(new LineBorder(Color.RED, COL_BORDER_WIDTH));

		/** Alternative name  **/
		mLabelAltItemName = new JLabel("MAGGI NOODLES");

		/** Item sale price **/
		mPriceTitle = new JLabel("P:");

		mLabelPriceValue = new JLabel("00.00");

		/** Discount  Amount **/
		mLabelDiscountTitle = new JLabel("D:");

		mLabelDiscountValue = new JLabel("00.00");

		/** Tax amount  **/
		mLabelTaxTitle = new JLabel("T:");

		mLabelTaxValue = new JLabel("00.00");

		/** Modifiers indicator**/

		mLabelExtras = new JLabel(mImage);
		mLabelRemarks = new JLabel(rImage);
		mLabelTray = new JLabel(tImage);

	}
	protected void initComponentsLayout() {

		mLabelItemSerialNumber.setFont(mDisplayItemDetailFont);
		ITM_PRPRTY_FONT_HEIGHT=(int)PosFormUtil.getFontTextHeight("Gg", mLabelItemSerialNumber);
		mLabelItemSerialNumber.setPreferredSize(new Dimension(TOP_ROW_DTL_WIDTH,ITM_PRPRTY_FONT_HEIGHT+ITEM_PADDING_HIGHT*2));
		mLabelItemSerialNumber.setBorder(new LineBorder(Color.RED, COL_BORDER_WIDTH));
		

		/** Modifier **/
		modifierIndicator = new JPanel();
		modifierIndicator.setPreferredSize(new Dimension(TOP_ROW_DTL_WIDTH,ITM_PRPRTY_FONT_HEIGHT+ITEM_PADDING_HIGHT*2));
		modifierIndicator.setLayout(new FlowLayout(FlowLayout.CENTER,0,0));
		modifierIndicator.setOpaque(false);

		mLabelExtras.setPreferredSize(new Dimension(ITM_PRPRTY_FONT_HEIGHT+ITEM_PADDING_HIGHT*2,
				ITM_PRPRTY_FONT_HEIGHT+ITEM_PADDING_HIGHT*2));
		mLabelRemarks.setPreferredSize(new Dimension(ITM_PRPRTY_FONT_HEIGHT+ITEM_PADDING_HIGHT*2,
				ITM_PRPRTY_FONT_HEIGHT+ITEM_PADDING_HIGHT*2));
		mLabelTray.setPreferredSize(new Dimension(ITM_PRPRTY_FONT_HEIGHT+ITEM_PADDING_HIGHT*2,
				ITM_PRPRTY_FONT_HEIGHT+ITEM_PADDING_HIGHT*2));


		mLabelRemarks.setVisible(false);
		mLabelTray.setVisible(false);
		
		modifierIndicator.add(mLabelTray);
		modifierIndicator.add(mLabelExtras);
		modifierIndicator.add(mLabelRemarks);
		

		mLabelTotalValue.setFont(mDisplayItemDetailFont.deriveFont(Font.BOLD));
		mLabelTotalValue.setHorizontalAlignment(SwingConstants.RIGHT);
		mLabelTotalValue.setPreferredSize(new Dimension(TOP_ROW_DTL_WIDTH,ITM_PRPRTY_FONT_HEIGHT+ITEM_PADDING_HIGHT*2));
		mLabelTotalValue.setBorder(new LineBorder(Color.RED, COL_BORDER_WIDTH));
		


		mLabelItemName.setFont(mDisplayItemFont);
		ITM_NAM_FONT_HEIGHT=(int)PosFormUtil.getFontTextHeight("Gg", mLabelItemName);
		mLabelItemName.setHorizontalAlignment(SwingConstants.LEFT);
		mLabelItemName.setPreferredSize(new Dimension(TITLE_COL_WIDTH-ICON_WIDTH ,ITM_NAM_FONT_HEIGHT+ITEM_PADDING_HIGHT*2));
		mLabelItemName.setBorder(new LineBorder(Color.RED, COL_BORDER_WIDTH));
		
		mLabelDeletedIcon.setPreferredSize(new Dimension(ICON_WIDTH ,ICON_HEIGHT));
//		mLabelDeletedIcon.setIcon(null);

		/** Quantity  **/
		quantitySPEC = new JPanel();
		quantitySPEC.setOpaque(false);
		quantitySPEC.setPreferredSize(new Dimension(END_ROW_QUANTITY_PANEL_WIDTH,ITM_PRPRTY_FONT_HEIGHT+ITEM_PADDING_HIGHT*2));
		quantitySPEC.setLayout(new FlowLayout(FlowLayout.LEFT,0,0));

		mQuantityTitle.setFont(mDisplayItemDetailFont);
		quantitySPEC.add(mQuantityTitle);

		mLabelQuantityValue.setFont(mDisplayItemDetailFont);
		quantitySPEC.setBorder(new LineBorder(Color.GREEN, COL_BORDER_WIDTH));
		quantitySPEC.add(mLabelQuantityValue);

		

		/** Alternative name  **/
		mLabelAltItemName.setFont(mDisplayItemDetailFont);
		mLabelAltItemName.setHorizontalAlignment(SwingConstants.LEFT);
		mLabelAltItemName.setPreferredSize(new Dimension(TITLE_COL_WIDTH ,ITM_NAM_FONT_HEIGHT+ITEM_PADDING_HIGHT*2));
		mLabelAltItemName.setBorder(new LineBorder(Color.RED, COL_BORDER_WIDTH));

		/** Item sale price **/
		priceSPECS = new JPanel();
		priceSPECS.setOpaque(false);
		priceSPECS.setBackground(Color.BLUE);
		priceSPECS.setPreferredSize(new Dimension(END_ROW_DTL_PANL_WIDTH, ITM_PRPRTY_FONT_HEIGHT+ITEM_PADDING_HIGHT*2));
		priceSPECS.setLayout(new FlowLayout(FlowLayout.CENTER,0,0));

		mPriceTitle.setFont(mDisplayItemDetailFont);
		priceSPECS.add(mPriceTitle);

		mLabelPriceValue.setFont(mDisplayItemDetailFont);
		priceSPECS.add(mLabelPriceValue);
		priceSPECS.setBorder(new LineBorder(Color.GREEN, COL_BORDER_WIDTH));

		

		/** Discount  Amount **/
		discountSPECS = new JPanel();
		discountSPECS.setOpaque(false);
		discountSPECS.setPreferredSize(new Dimension(END_ROW_DTL_PANL_WIDTH, ITM_PRPRTY_FONT_HEIGHT+ITEM_PADDING_HIGHT*2));
		discountSPECS.setLayout(new FlowLayout(FlowLayout.CENTER,0,0));

		mLabelDiscountTitle.setFont(mDisplayItemDetailFont);
		discountSPECS.add(mLabelDiscountTitle);

		mLabelDiscountValue.setFont(mDisplayItemDetailFont);
		discountSPECS.add(mLabelDiscountValue);
		discountSPECS.setBorder(new LineBorder(Color.GREEN, COL_BORDER_WIDTH));

		

		/** Tax amount  **/
		taxSPECS = new JPanel();
		taxSPECS.setOpaque(false);
		taxSPECS.setPreferredSize(new Dimension(END_ROW_DTL_PANL_WIDTH, ITM_PRPRTY_FONT_HEIGHT+ITEM_PADDING_HIGHT*2));
		taxSPECS.setLayout(new FlowLayout(FlowLayout.CENTER,0,0));

		mLabelTaxTitle.setFont(mDisplayItemDetailFont);
		taxSPECS.add(mLabelTaxTitle);

		mLabelTaxValue.setFont(mDisplayItemDetailFont);
		taxSPECS.add(mLabelTaxValue);
		taxSPECS.setBorder(new LineBorder(Color.GREEN, COL_BORDER_WIDTH));

		

	}

	protected void setSubItemIndicator(){
		mSubItemIndicator=new JPanel();
		mSubItemIndicator.setOpaque(true);
		mSubItemIndicator.setSize(new Dimension(INDICATOR_LAYOUT_WIDTH,LAYOUT_HEIGHT));
		mSubItemIndicator.setPreferredSize(new Dimension(INDICATOR_LAYOUT_WIDTH,LAYOUT_HEIGHT));
		mSubItemIndicator.setLocation(LAYOUT_WIDTH-INDICATOR_LAYOUT_WIDTH,0);
		mSubItemIndicator.setVisible(false);
		add(mSubItemIndicator);
	}

	protected void setParentItemIndicator(){
		mParentItemIndicator=new JPanel();
		mParentItemIndicator.setBackground(new Color(208, 32, 144));
		mParentItemIndicator.setOpaque(true);
		mParentItemIndicator.setSize(new Dimension(LAYOUT_WIDTH,INDICATOR_LAYOUT_WIDTH));
		mParentItemIndicator.setPreferredSize(new Dimension(LAYOUT_WIDTH,INDICATOR_LAYOUT_WIDTH));
		mParentItemIndicator.setLocation(0,LAYOUT_HEIGHT-INDICATOR_LAYOUT_WIDTH);
		mParentItemIndicator.setVisible(false);
		add(mParentItemIndicator);
	}

	private void setTotal(){
		mLabelTotalValue.setText(getFormatedText(PosCurrencyUtil.format(PosSaleItemUtil.getGrandTotal(mPosSaleItem))));
	}

	public void setSelected(Boolean selected){
		//		if(mReadOnly) return;
		if(mIsSelected==selected)return;
		mIsSelected=selected;
		setBackground((selected)?SELECTED_ITEM_BG_COLOR:getItemBGColor());
		if(mIsSelected)
			if(mGridItemSelectedListner!=null)
				mGridItemSelectedListner.onSelected(this);
	}

	public void onEdited(){
		mEdited = true;
		Color editedColor=new Color(255,150,150);
		setBackground(editedColor);
	}

	public void setItemSerialNumber(int value){
		mItemSerialNumber=value;
		mLabelItemSerialNumber.setText(String.valueOf(value));
	}

	public int getItemSerialNumber(){
		return mItemSerialNumber;
	}

	public int getItemIndex(){
		return mItemIndex;
	}

	public void setItemIndex(int value){
		mItemIndex=value;
	}

	public void setItemQuantity() {
		
		String text=PosUomUtil.format(mPosSaleItem.getQuantity(),mPosSaleItem.getUom());
		this.mLabelQuantityValue.setText(getFormatedText(text));
	}

	public double getItemQuantity() {
		return mPosSaleItem.getQuantity();
	}

	protected void setItemPrice() {
		
		final double price=PosSaleItemUtil.getItemFixedPrice(mPosSaleItem);
		String text=PosCurrencyUtil.format(price);
		
		this.mLabelPriceValue.setText(getFormatedText(text));

	}

	public double getItemPrice() {
		return PosSaleItemUtil.getTotalItemPrice(mPosSaleItem);
	}

	private void setItemTax() {
		
		//String text=PosCurrencyUtil.format(PosSaleItemUtil.getTotalTaxAmount(mPosSaleItem,true));
		final String text=PosCurrencyUtil.format( getItemTax(),true);
		this.mLabelTaxValue.setText(getFormatedText(text));
	}

	public double getItemTax() {
		return 	PosSaleItemUtil.getTotalTaxAmount(mPosSaleItem,true);
	}

	public double getItemTax1() {
		return 	PosSaleItemUtil.getTotalT1Amount(mPosSaleItem);
	}

	public double getItemTax2() {
		return 	PosSaleItemUtil.getTotalT2Amount(mPosSaleItem);
	}

	public double getItemTax3() {
		return PosSaleItemUtil.getTotalT3Amount(mPosSaleItem);
	}

	public double getItemServiceTax() {
		return 	PosSaleItemUtil.getTotalServiceTaxAmount(mPosSaleItem);
	}

	public double getItemGSTAmount() {
		return PosSaleItemUtil.getTotalGSTAmount(mPosSaleItem);
	}

	public void setItemDiscount() {

		String text=PosCurrencyUtil.format(PosSaleItemUtil.getTotalDiscountAmount(mPosSaleItem));
		
		this.mLabelDiscountValue.setText(getFormatedText(text));
		setBackground(getItemBGColor());
	}

	public void setItemDiscount(BeanDiscount discountItem) {
		mPosSaleItem.setDiscount(discountItem);
		setDisplayLabels();
	}

	public double getItemDiscount() {
		return PosSaleItemUtil.getTotalDiscountAmount(mPosSaleItem);
	}

	public double getItemTotal() {
		return PosSaleItemUtil.getGrandTotal(mPosSaleItem);
	}

	

	public void setItemCode() {
		
		String text=mPosSaleItem.getCode();
		mLabelItemCode.setText(getFormatedText(text));
	}

	public String getItemName() {
		return mPosSaleItem.getName();
	}

	public void setItemName(String name){
		
		mLabelItemName.setText(getFormatedText(name));

	}
	
	private String getFormatedText(String text){
		
		if(mOrderDetailItem.isVoid())
			text="<html><body style=\"white-space: nowrap;\"><strike>"+text+"</strike></body></html>";
		else
			text="<html><body style=\"white-space: nowrap;\">"+text+"</body></html>";
		
		return text;
		
	}

	private void setKitchenPrintIndicator(){
		Boolean printed=mOrderDetailItem.isPrintedToKitchen();
		mLabelItemName.setForeground((printed)?Color.BLACK:Color.RED);
	}


	public void setItemName() {
//		setItemName(" "+((orderItemListPanelSetting.useAlternativeTitle())?
//				mPosSaleItem.getAlternativeName():
		setItemName(PosSaleItemUtil.getItemNameToDisplay(mPosSaleItem, orderItemListPanelSetting.useAlternativeTitle()));
		mLabelAltItemName.setText(mPosSaleItem.getAlternativeName());
	}

	public void setListner(IPosOrderListItemControlListner listner){
		mGridItemSelectedListner=listner;
	}

	public void setOrderDetailItem(BeanOrderDetail detailItem){
		mOrderDetailItem=detailItem;
		setPosItem(detailItem.getSaleItem());
		refresh();
		doModifyIfExtraItem();
	}
	/**
	 * 
	 */
	private void setIndicators() {
		
		setSubItemIdicatorVisible();
		setParentItemIndicatorVisible();
		setKitchenPrintIndicator();
		setModifierIndicator();
	}
	private void doModifyIfExtraItem(){
		if(mOrderDetailItem.getItemType()==OrderDetailItemType.EXTRA_ITEM){
			quantitySPEC.setVisible(true);
			taxSPECS.setVisible(true);
		}
	}

	private void setSubItemIdicatorVisible(){
		mSubItemIndicator.setVisible(false);
		if(mOrderDetailItem.getParentDtlId()!=null){
			mSubItemIndicator.setVisible(true);
			Color bgColor=((mOrderDetailItem.getItemType()==OrderDetailItemType.EXTRA_ITEM)?CHOICE_CONTENT_ITEM_INDI_COLOR:COMBO_CONTENT_ITEM_INDI_COLOR);
			mSubItemIndicator.setBackground(bgColor);
		}
	}

	private void setParentItemIndicatorVisible(){
		mParentItemIndicator.setVisible(false);
		if((mOrderDetailItem.getComboSubstitutes()!=null && mOrderDetailItem.getComboSubstitutes().size()>0) || 
				(mOrderDetailItem.getExtraItemList()!=null && mOrderDetailItem.getExtraItemList().size()>0)){
			mParentItemIndicator.setVisible(true);
			//			Color bgColor=((!mOrderDetailItem.getSaleItem().isComboItem())?CHOICE_CONTENT_ITEM_INDI_COLOR:COMBO_CONTENT_ITEM_INDI_COLOR)
			mParentItemIndicator.setBackground(getItemDefBgColor());
		}
	}

	private void setPosItem(BeanSaleItem item){
		mPosSaleItem=item;
		if(mOrderDetailItem!=null)
			mOrderDetailItem.setSaleItem(mPosSaleItem);

	}

	public void setDisplayLabels(){

		setItemName();
		setItemCode();
		setItemQuantity();
		setItemPrice();
		setItemTax();
		setItemDiscount();
		setTotal();

	}

	public void refresh(){
		
		setIndicators();
		setDisplayLabels();
		
		mLabelDeletedIcon.setIcon((mOrderDetailItem.isVoid()?itemDeletedIco:null));
	}

	/**
	 * 
	 */
	public void setModifierIndicator() {

		mLabelExtras.setVisible(false);
		mLabelRemarks.setVisible(false);
		mLabelTray.setVisible(false);
		if(mOrderDetailItem.getRemarks()!=null&&mOrderDetailItem.getRemarks().trim().length()>0){
			//			mLabelRemarks.setIcon(mImage);
			//			mLabelRemarks.setText("hello");
			mLabelRemarks.setVisible(true);
			//			mLabelRemarks.setOpaque(true);
			//			setEnabled(true);
			//			modifierIndicator.add(mLabelRemarks);
		}
		
		if(mOrderDetailItem.getTrayCode()!=null && !mOrderDetailItem.getTrayCode().trim().isEmpty()){
			
			mLabelTray.setVisible(true);
		}
		
		for(int i=0;i<5;i++){
			if(mPosSaleItem.getAttribSelectedOption(i)!=null&&mPosSaleItem.getAttribSelectedOption(i).trim().length()!=0&&!mPosSaleItem.getAttribSelectedOption(i).equalsIgnoreCase(PosSaleItemProvider.DEFAULT_ATTRIBUTE_OPTION)){
				mLabelExtras.setVisible(true);
				//				modifierIndicator.add(mLabelExtras);
				//				modifierIndicator.setVisible(true);
				//				mLabelModifiers.setVisible(true);
				break;
			}
			//			mLabelExtras.setIcon(rImage);
			//			mLabelExtras.setText("hello");
			//			mLabelExtras.setVisible(true);
			//			mLabelExtras.setOpaque(false);
		}
	}

	public void validateComponent() {
		// TODO Auto-generated method stub
	}

	private MouseListener itemMousePressedListner=new MouseAdapter() {
		@Override
		public void mousePressed(MouseEvent e) {
			setSelected(true);
			//			if(e.getClickCount()==2 && !e.isConsumed() && !mReadOnly )
			if(e.getClickCount()==2 && !e.isConsumed())
				if(mGridItemSelectedListner!=null)
					mGridItemSelectedListner.onDoubleClick(PosOrderListItemControl.this);
		}
	};

	public BeanSaleItem getPosSaleItem(){
		return mPosSaleItem;
	}

	public BeanOrderDetail getOrderDetailItem(){
		return mOrderDetailItem;
	}

	public boolean isReadOnly() {
		
		return mReadOnly || isVoid();
	}

	public void setHighlighteDiscounted(boolean highlighted){
		mHighlighteDiscounted=highlighted;
		setBackground(getItemBGColor());
	}

	public boolean getHighlighteDiscounted(){
		return mHighlighteDiscounted;
	}

	public void setReadOnly(boolean readOnly) {
		this.mReadOnly = readOnly;
	}

	public PosOrderListItemControl clone() {
		PosOrderListItemControl cloneObject=null;
		try {
			cloneObject=(PosOrderListItemControl) super.clone();
		} catch (CloneNotSupportedException e) {
			PosLog.write(this,"clone",e);
		}
		return cloneObject;
	}

	/**
	 * @return the mKitchenPrinted
	 */
	public boolean isKitchenPrinted() {
		return mOrderDetailItem.isPrintedToKitchen();
	}

	/**
	 * @param mKitchenPrinted the mKitchenPrinted to set
	 */
	public void setKitchenPrinted(boolean mKitchenPrinted) {
		this.mKitchenPrinted = mKitchenPrinted;
	}

	public static String[] SEARCH_FIELD_TITLE_LIST={"Code","Name"};
	public static String[] SEARCH_FIELD_LIST={"getItemCode","getDisplayText"};
	public static int[] SEARCH_FIELD_WIDTH_LIST={100};

	public String getSaleItemCode(){
		return mPosSaleItem.getCode();
	}

//	public String getSaleItemName(){
//		return mPosSaleItem.getName();
//	}

	/* (non-Javadoc)
	 * @see com.indocosmo.pos.components.objectbrowser.IPosBrowsableItem#getDisplayText()
	 */
	@Override
	public String getDisplayText() {
		return mPosSaleItem.getName();
	}
	/* (non-Javadoc)
	 * @see com.indocosmo.pos.components.search.IPosSearchableItem#getFiledList()
	 */
	@Override
	public String[] getFieldList() {
		return SEARCH_FIELD_LIST;
	}
	/* (non-Javadoc)
	 * @see com.indocosmo.pos.components.search.IPosSearchableItem#getFiledTitleList()
	 */
	@Override
	public String[] getFieldTitleList() {

		return SEARCH_FIELD_TITLE_LIST;
	}
	/* (non-Javadoc)
	 * @see com.indocosmo.pos.components.search.IPosSearchableItem#getFiledWidthList()
	 */
	@Override
	public int[] getFieldWidthList() {
		return SEARCH_FIELD_WIDTH_LIST;
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.pos.forms.components.objectbrowser.IPosBrowsableItem#isVisibleInUI()
	 */
	@Override
	public boolean isVisibleInUI() {
		// TODO Auto-generated method stub
		return true;
	}
	
	/* (non-Javadoc)
	 * @see com.indocosmo.pos.forms.components.objectbrowser.IPosBrowsableItem#getItemCode()
	 */
	@Override
	public Object getItemCode() {
		return mPosSaleItem.getCode();
	}

	/**
	 * @return
	 */
	public boolean isVoid() {
		
		return mOrderDetailItem.isVoid();
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.pos.forms.components.search.IPosSearchableItem#getFiledFormatList()
	 */
	@Override
	public String[] getFieldFormatList() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.pos.forms.components.objectbrowser.IPosBrowsableItem#getKeyStroke()
	 */
	@Override
	public KeyStroke getKeyStroke() {
		// TODO Auto-generated method stub
		return null;
	}
	

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#processKeyEvent(java.awt.event.KeyEvent)
	 */
	@Override
	protected void processKeyEvent(KeyEvent e) {
	
		if(e.getKeyCode()==KeyEvent.VK_ENTER)
			if(mIsSelected){
				if(mGridItemSelectedListner!=null)
					mGridItemSelectedListner.onDoubleClick(PosOrderListItemControl.this);
				e.consume();
			}
		else
		super.processKeyEvent(e);
	}
}
