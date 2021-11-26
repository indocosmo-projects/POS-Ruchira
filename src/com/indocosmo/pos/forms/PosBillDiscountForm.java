package com.indocosmo.pos.forms;

import javax.swing.JDialog;


/**
 * This class is deprecated.
 * **/

@SuppressWarnings("serial")
public class PosBillDiscountForm  extends JDialog {

//	private static final int PANEL_CONTENT_H_GAP=5;//PosOrderEntryForm.PANEL_CONTENT_H_GAP;
//	private static final int PANEL_CONTENT_V_GAP=5;
//
//	private static final int TITLE_PANEL_HEIGHT=60;
//	private static final int CONTENT_PANEL_HEIGHT=550; 
//
//	private static final int IMAGE_BOTTOM_BUTTON_WIDTH=150;
//	private static final int IMAGE_BOTTOM_BUTTON_HEIGHT=60;
//
//	private static final int BOTTOM_PANEL_HEIGHT=IMAGE_BOTTOM_BUTTON_HEIGHT+8*2;
//
//	private static final int BUTTON_DISC_ADD_DEL_HEIGHT=41;
//	private static final int BUTTON_DISC_ADD_DEL_WIDTH=134;
//
//	private static final int FORM_HEIGHT=TITLE_PANEL_HEIGHT+CONTENT_PANEL_HEIGHT+BOTTOM_PANEL_HEIGHT+PANEL_CONTENT_V_GAP*3;
//	public static final int FORM_WIDTH=900;
//
//	private static final int TITLE_LABEL_WIDTH=FORM_WIDTH-PANEL_CONTENT_V_GAP*2;
//	private static final int TITLE_LABEL_HEIGHT=TITLE_PANEL_HEIGHT;
//
//	private static final Color PANEL_BG_COLOR=new Color(78,128,188);
//	private static final Color LABEL_FG_COLOR=Color.WHITE;
//
//	private static final String IMAGE_BUTTON_OK="dlg_ok.png";
//	private static final String IMAGE_BUTTON_OK_TOUCH="dlg_ok_touch.png";
//
//	private static final String IMAGE_BUTTON_CANCEL="dlg_cancel.png";
//	private static final String IMAGE_BUTTON_CANCEL_TOUCH="dlg_cancel_touch.png";	
//	private static final String IMAGE_DISC_ADD_NORMAL="bill_discount_apply.png";
//	private static final String IMAGE_DISC_ADD_TOUCH="bill_discount_apply_touch.png";
//	private static final String IMAGE_DISC_SYMBOL_NORMAL="discount_symbol.png";
//	private static final String IMAGE_DISC_SYMBOL_TOUCH="discount_symbol_touch.png";
//
//	private static final int TEXT_FIELD_WIDTH=175;
//	private static final int TEXT_FIELD_HEIGHT=40;
//	private static final int LABEL_CUR_SYMBOL_WIDTH=50;
//	private static final int LABEL_CUR_SYMBOL_HEIGHT=40;
//
//	private PosButton mButtonOk;
//	private PosButton mButtonCancel;
//
//	private JPanel mContentPane;
//	//	private JPanel mItemHeaderPanel;
//	private JPanel mTitlePanel;
//	private JPanel mContentPanel;
//	private JPanel mBottomPanel;
//
//	private JLabel mlabelTitle;	
//	private JTable mDiscountTable;
//	private DefaultTableModel mAvailableDiscountTableModel;
//	private JScrollPane mDiscountScrollPane;
//	private JLabel mLabelDiscountCode;
//	private PosButton mButtonDiscountCode;
//	private JLabel mLabelDiscountValue;
//	private JTextField mTxtDiscountValue;
////	private JLabel mLabelcurrencySymbol;
//	private PosButton mBtnCurrencySymbol;
//	private PosButton mButtonDiscountDesc;
//	private JTextArea mTxtDiscountDesc;
//	private JTextField mTxtDiscountMsg;
//
//	private PosDiscountItemProvider mDiscProvider;
//	private static PosReasonItemProvider mReasonItemProvider;
//	private ArrayList<String> mAppliedTableDiscountCodeList;
//	private ArrayList<BeanDiscount> mAppliedTableDiscountList;
//	private ArrayList<BeanDiscount> mBillDiscountList;
//	private ArrayList<BeanReason> mReasonList;
//	private BeanDiscount mSelectedDiscountItem;
//
//
//	private PosOrderListPanel mItemGridPanel;
//	private PosOrderListOptionPanel mGridOptionPanel;
//
//	public PosBillDiscountForm(PosOrderEntryForm parent){
//		mDiscProvider=new PosDiscountItemProvider();
//		mAppliedTableDiscountCodeList=new ArrayList<String>();
//		mAppliedTableDiscountList=new ArrayList<BeanDiscount>();
//		mSelectedDiscountItem=mDiscProvider.getNoneDiscount();
//		mReasonItemProvider=new PosReasonItemProvider();
//		initControls();
//		setValues();
//		bindTableData();
//	}
//
//
//	private void initControls() {
//		setSize(FORM_WIDTH, FORM_HEIGHT);
//
//		mContentPane = new JPanel();
//		//		mContentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
//		mContentPane.setLayout(null);
//		setContentPane(mContentPane);
//
//		mTitlePanel = new JPanel();
//		mTitlePanel.setBounds(PANEL_CONTENT_H_GAP, 
//				PANEL_CONTENT_V_GAP, 
//				FORM_WIDTH-PANEL_CONTENT_H_GAP*2, TITLE_PANEL_HEIGHT);
//		mTitlePanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
//		mTitlePanel.setBackground(PANEL_BG_COLOR);
//		mTitlePanel.setLayout(null);
//		add(mTitlePanel);	
//
//		mContentPanel = new JPanel();
//		mContentPanel.setBounds(PANEL_CONTENT_H_GAP, 
//				mTitlePanel.getY()+ mTitlePanel.getHeight()+PANEL_CONTENT_V_GAP, 
//				FORM_WIDTH-PANEL_CONTENT_H_GAP*2, CONTENT_PANEL_HEIGHT);
//		mContentPanel.setLayout(null);
//		mContentPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
//		add(mContentPanel);	
//
//
//		mBottomPanel = new JPanel();
//		mBottomPanel.setBounds(PANEL_CONTENT_H_GAP, 
//				mContentPanel.getY()+ mContentPanel.getHeight()-1, 
//				FORM_WIDTH-PANEL_CONTENT_H_GAP*2, BOTTOM_PANEL_HEIGHT);
//		mBottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER,PANEL_CONTENT_H_GAP,PANEL_CONTENT_V_GAP));
//		mBottomPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
//		add(mBottomPanel);
//
//		setTitle();		
//		setBottomPanel();
//		setItemGridPanel();
//		createDiscountTable();
//		createDiscount();
//	}
//
//	private void bindTableData(){
//		for(BeanOrderDetail item : mOrderEntryObject.getOrderDetailItems()){
//			BeanDiscount discItem=item.getSaleItem().getDiscount();
//			addToTable(discItem);
//		}
//	}
//
//	private void setTitle(){
//		mlabelTitle=new JLabel();		
//		mlabelTitle.setHorizontalAlignment(SwingConstants.CENTER);	
//		mlabelTitle.setVerticalAlignment(SwingConstants.CENTER);	
//		mlabelTitle.setText("Bill Discounts");
//		mlabelTitle.setBounds(0, 0, TITLE_LABEL_WIDTH, TITLE_LABEL_HEIGHT);		
//		mlabelTitle.setFont(PosFormUtil.getLabelHeaderFont());
//		mlabelTitle.setOpaque(false);
//		mlabelTitle.setForeground(LABEL_FG_COLOR);
//		mTitlePanel.add(mlabelTitle);		
//	}
//
//
//	private void setBottomPanel(){
//		mButtonOk=new PosButton();		
//		mButtonOk.setText("OK");
//		mButtonOk.setImage(PosResUtil.getImageIconFromResource(IMAGE_BUTTON_OK));
//		mButtonOk.setTouchedImage(PosResUtil.getImageIconFromResource(IMAGE_BUTTON_OK_TOUCH));	
//		mButtonOk.setHorizontalAlignment(SwingConstants.CENTER);		
//		mButtonOk.setSize(IMAGE_BOTTOM_BUTTON_WIDTH, IMAGE_BOTTOM_BUTTON_HEIGHT );
//		mButtonOk.setOnClickListner(okButtonListner);
//		mBottomPanel.add(mButtonOk);
//
//		mButtonCancel=new PosButton();		
//		mButtonCancel.setText("Cancel");
//		mButtonCancel.setImage(PosResUtil.getImageIconFromResource(IMAGE_BUTTON_CANCEL));
//		mButtonCancel.setTouchedImage(PosResUtil.getImageIconFromResource(IMAGE_BUTTON_CANCEL_TOUCH));	
//		mButtonCancel.setHorizontalAlignment(SwingConstants.CENTER);		
//		mButtonCancel.setSize(IMAGE_BOTTOM_BUTTON_WIDTH, IMAGE_BOTTOM_BUTTON_HEIGHT );
//		mButtonCancel.setOnClickListner(cancelButtonListner);
//		mBottomPanel.add(mButtonCancel);		
//	}
//
////	private ArrayList<PosSaleItemObject> mSaleItems;
//	private BeanOrderHeader mOrderEntryObject;
//	private void setItemGridPanel(){
//		final int left=PANEL_CONTENT_H_GAP;
//		int top=PANEL_CONTENT_V_GAP;
//		mGridOptionPanel=new PosOrderListOptionPanel(true);
//		mGridOptionPanel.setLocation(left, top);
//		mGridOptionPanel.setOpaque(false);
////		mGridOptionPanel.setBorder(new EtchedBorder());
//		mContentPanel.add(mGridOptionPanel);
//
////		mSaleItems=PosOrderEntryForm.getInstance().getBillGrid().getItemList();
//		mOrderEntryObject=PosOrderEntryForm.getInstance().getBillGrid().getPosOrderEntryItem();
//		top=mGridOptionPanel.getY()+mGridOptionPanel.getHeight();
//		mItemGridPanel=new PosOrderListPanel(mContentPanel.getHeight()-top-PANEL_CONTENT_V_GAP,true);
//		mItemGridPanel.setLocation(left, top);
//		mItemGridPanel.setReadOnly(true);
//		mItemGridPanel.setItemGridControlPanel(mGridOptionPanel);
//		mItemGridPanel.setPosOrderEntryItem(mOrderEntryObject);
//		mContentPanel.add(mItemGridPanel);
//	}
//
//
//	private static final int SCROLL_WIDTH=20;
//
//	private void createDiscountTable(){
//
//		final int tableWidth=mContentPanel.getWidth()-mItemGridPanel.getWidth()-PANEL_CONTENT_H_GAP*3;
//		final int tableHeight=212;
//		final int codeColumnWidth=100;
//		final int priceColumnWidth=50;
//		final int nameColumnWidth=150;
//		final int deleteColumnWidth=50;
//		final int descColumnWidth=tableWidth-codeColumnWidth-deleteColumnWidth-priceColumnWidth-nameColumnWidth-SCROLL_WIDTH-3;
//
//		int left=mItemGridPanel.getWidth()+mItemGridPanel.getX()+PANEL_CONTENT_H_GAP;
//		int top=PANEL_CONTENT_V_GAP;
//		JLabel labelDiscount=new JLabel();
//		labelDiscount.setText("Applied Bill Discounts");
//		labelDiscount.setHorizontalAlignment(SwingConstants.CENTER);
//		labelDiscount.setVerticalAlignment(SwingConstants.CENTER);
//		labelDiscount.setBounds(left, top, tableWidth, 25);
//		labelDiscount.setFont(PosFormUtil.getLabelFont());
//		labelDiscount.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
//		labelDiscount.setBackground(PANEL_BG_COLOR);
//		labelDiscount.setOpaque(true);
//		mContentPanel.add(labelDiscount);
//
//		top+=labelDiscount.getHeight();
//		mAvailableDiscountTableModel =  PosFormUtil.getReadonlyTableModel();
//		mAvailableDiscountTableModel.setColumnIdentifiers(new String[] {"Code","Name","Desc","Price",""});
//
//		mDiscountTable = new JTable(mAvailableDiscountTableModel);
//		final int headerHeight=25;
//		mDiscountTable.getTableHeader().setPreferredSize(new Dimension(tableWidth, headerHeight));
//		mDiscountTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
//		mDiscountTable.getColumnModel().getColumn(0).setPreferredWidth(codeColumnWidth);
//		mDiscountTable.getColumnModel().getColumn(1).setPreferredWidth(nameColumnWidth);
//		mDiscountTable.getColumnModel().getColumn(2).setPreferredWidth(descColumnWidth);
//		mDiscountTable.getColumnModel().getColumn(3).setPreferredWidth(priceColumnWidth);
//		mDiscountTable.getColumnModel().getColumn(4).setPreferredWidth(deleteColumnWidth);
//		new ButtonColumn(mDiscountTable, mdDleteAction , 4);
//		mDiscountScrollPane = new JScrollPane(mDiscountTable,
//				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
//				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
//		mDiscountScrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(SCROLL_WIDTH,0));
//		mDiscountScrollPane.setSize(new Dimension(tableWidth,tableHeight));
//		mDiscountScrollPane.setLocation(left,top);
//		//		final int headerHeight=mDiscountTable.getTableHeader().getHeight();
//		final int rowHeight=(tableHeight-headerHeight-(1))/PosConstants.BILL_DISCOUNT_TABLE_NO_RECORDS;
//		mDiscountTable.setRowHeight(rowHeight);
//		mContentPanel.add(mDiscountScrollPane);
//	}
//
//	private Action mdDleteAction = new AbstractAction()
//	{
//		public void actionPerformed(ActionEvent e)
//		{
//			final JTable table =mDiscountTable;// (JTable)e.getSource();
//			final int modelRow = Integer.valueOf( e.getActionCommand());
//			PosFormUtil.showQuestionMessageBox(PosBillDiscountForm.this, MessageBoxButtonTypes.YesNo, "Are You sure do you want to delete the discount ?.", new PosMessageBoxFormListnerAdapter() {
//				@Override
//				public void onYesButtonPressed() {
//					((DefaultTableModel)table.getModel()).removeRow(modelRow);
//					mItemGridPanel.removeBillDiscounts(mAppliedTableDiscountList.get(modelRow));
//					mAppliedTableDiscountList.remove(modelRow);
//					mAppliedTableDiscountCodeList.remove(modelRow);
//				}
//
//			});
//
//		}
//	};
//
//	private void addToTable(BeanDiscount discItem){
//		addToTable(discItem, false);
//	}
//	private void addToTable(BeanDiscount discItem, boolean showDupMessage){
//		if(mAppliedTableDiscountCodeList.contains(discItem.getCode())){
//			if(showDupMessage)
//				PosFormUtil.showInformationMessageBox(this, "This discounts has been already applied to bill items. Please remove it first.");
//			return;
//		}
//		if(!discItem.getCode().equals(PosDiscountItemProvider.NONE_DISCOUNT_CODE) && 
//				discItem.getAppliedAt()!=AppliedAt.ITEM_LEVEL){
//			try {
//				discItem.setAppliedAt(AppliedAt.BILL_LEVEL);
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			mAppliedTableDiscountCodeList.add(discItem.getCode());
//			mAppliedTableDiscountList.add(discItem);
//			final String symbol=(discItem.isPercentage())?"%": PosEnvSettings.getInstance().getCurrencySymbol();
//			mAvailableDiscountTableModel.addRow(new String[]{discItem.getCode(),discItem.getName(),discItem.getDescription(),String.valueOf(discItem.getPrice())+symbol,"X"});
//			mSelectedDiscountItem=mDiscProvider.getNoneDiscount();
//			setValues();
//		}
//	}
//
//	private void createDiscount(){	
//		int left=mDiscountScrollPane.getX();
//		int top=mDiscountScrollPane.getY()+mDiscountScrollPane.getHeight()+PANEL_CONTENT_V_GAP;
//		int height=mContentPanel.getHeight()-top-PANEL_CONTENT_V_GAP;
//		int labelWidthDescription=mDiscountScrollPane.getWidth();
//		final int LABEL_HEIGHT=25;
//		final int TEXT_FIELD_HEIGHT=40;
//
//		JPanel discountPanel=new JPanel();
//		discountPanel.setBounds(left,top,mDiscountScrollPane.getWidth(),height);
//		discountPanel.setLayout(null);
//		//		discountPanel.setBackground(Color.RED);
//		mContentPanel.add(discountPanel);
//
//		JLabel labelDiscount=new JLabel();
//		labelDiscount.setText("Discount Details");
//		labelDiscount.setHorizontalAlignment(SwingConstants.CENTER);
//		labelDiscount.setVerticalAlignment(SwingConstants.CENTER);
//		labelDiscount.setBounds(0, 0, labelWidthDescription, LABEL_HEIGHT);
//		labelDiscount.setFont(PosFormUtil.getLabelFont());
//		labelDiscount.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
//		labelDiscount.setBackground(PANEL_BG_COLOR);
//		labelDiscount.setOpaque(true);
//		discountPanel.add(labelDiscount);
//
//		left=0;
//		top=labelDiscount.getY()+labelDiscount.getHeight()+PANEL_CONTENT_V_GAP;
//		mLabelDiscountCode=new JLabel();
//		mLabelDiscountCode.setText("Name");
//		mLabelDiscountCode.setHorizontalAlignment(SwingConstants.LEFT);
//		mLabelDiscountCode.setVerticalAlignment(SwingConstants.CENTER);
//		mLabelDiscountCode.setBounds(left, top, TEXT_FIELD_WIDTH, LABEL_HEIGHT);
//		mLabelDiscountCode.setFont(PosFormUtil.getLabelFont());
//		mLabelDiscountCode.setOpaque(true);
//		discountPanel.add(mLabelDiscountCode);	
//
//		top=mLabelDiscountCode.getY()+mLabelDiscountCode.getHeight();
//		mButtonDiscountCode=new PosButton("Select...");	
//		mButtonDiscountCode.setBounds(left, top, TEXT_FIELD_WIDTH, TEXT_FIELD_HEIGHT);
//		mButtonDiscountCode.setImage("item_edit_discount_select.png");
//		mButtonDiscountCode.setTouchedImage("item_edit_discount_select_touch.png");
//		//		mButtonDiscountCode.addFocusListener(discountCodeListener);	
//		mButtonDiscountCode.setOnClickListner(discountCodeListener);
//		discountPanel.add(mButtonDiscountCode);
//
//		left=mLabelDiscountCode.getX()+mLabelDiscountCode.getWidth()+PANEL_CONTENT_H_GAP;
//		top=mLabelDiscountCode.getY();//mButtonDiscountCode.getY()+mButtonDiscountCode.getHeight()+PANEL_CONTENT_V_GAP;
//		mLabelDiscountValue=new JLabel();
//		mLabelDiscountValue.setText("Amount");
//		mLabelDiscountValue.setHorizontalAlignment(SwingConstants.LEFT);	
//		mLabelDiscountValue.setVerticalAlignment(SwingConstants.CENTER);
//		mLabelDiscountValue.setBounds(left, top, TEXT_FIELD_WIDTH, LABEL_HEIGHT);	
//		//		mLabelDiscountValue.setBackground(Color.GREEN);
//		mLabelDiscountValue.setOpaque(true);
//		mLabelDiscountValue.setFont(PosFormUtil.getLabelFont());
//		discountPanel.add(mLabelDiscountValue);
//
//		left=mButtonDiscountCode.getX()+mButtonDiscountCode.getWidth()+PANEL_CONTENT_H_GAP;
//		top=mButtonDiscountCode.getY();
//		mTxtDiscountValue=new PosTextField();	
//		mTxtDiscountValue.setHorizontalAlignment(PosTextField.RIGHT);
//		mTxtDiscountValue.setBounds(left, top, discountPanel.getWidth()-mButtonDiscountCode.getWidth()- LABEL_CUR_SYMBOL_WIDTH-BUTTON_DISC_ADD_DEL_WIDTH-PANEL_CONTENT_H_GAP*3 , TEXT_FIELD_HEIGHT);	
//		mTxtDiscountValue.setFont(PosFormUtil.getTextFieldBoldFont());	
//		mTxtDiscountValue.addMouseListener(priceEditListner);
//		discountPanel.add(mTxtDiscountValue);
//		
//		left=mTxtDiscountValue.getX()+mTxtDiscountValue.getWidth()+PANEL_CONTENT_H_GAP;
//		mBtnCurrencySymbol=new PosButton();
//		mBtnCurrencySymbol.setHorizontalAlignment(SwingConstants.CENTER);		
//		mBtnCurrencySymbol.setBounds(left, top, LABEL_CUR_SYMBOL_WIDTH, LABEL_CUR_SYMBOL_HEIGHT);		
//		mBtnCurrencySymbol.setFont(PosFormUtil.getButtonFont().deriveFont(15f));
//		mBtnCurrencySymbol.setForeground(Color.WHITE);
//		mBtnCurrencySymbol.setImage(IMAGE_DISC_SYMBOL_NORMAL);
//		mBtnCurrencySymbol.setTouchedImage(IMAGE_DISC_SYMBOL_TOUCH);
//		mBtnCurrencySymbol.setImage(IMAGE_DISC_SYMBOL_NORMAL);
//		mBtnCurrencySymbol.setDisabledIcon(PosResUtil.getImageIconFromResource(IMAGE_DISC_SYMBOL_NORMAL));
//		mBtnCurrencySymbol.setOnClickListner(new PosButtonListnerAdapter() {
//			@Override
//			public void onClicked(PosButton button) {
//				mBtnCurrencySymbol.setText(mBtnCurrencySymbol.getText().equals("$")?"%":"$");
//				mSelectedDiscountItem.setPercentagable(mBtnCurrencySymbol.getText().equals("%"));
//			}
//		});
//		discountPanel.add(mBtnCurrencySymbol);
//
//		left=mBtnCurrencySymbol.getX()+mBtnCurrencySymbol.getWidth()+PANEL_CONTENT_H_GAP;
//		PosButton addDiscButton=new PosButton("Apply");
//		addDiscButton.setBounds(left, top, BUTTON_DISC_ADD_DEL_WIDTH, BUTTON_DISC_ADD_DEL_HEIGHT);	
//		addDiscButton.setImage(IMAGE_DISC_ADD_NORMAL);
//		addDiscButton.setTouchedImage(IMAGE_DISC_ADD_TOUCH);
//		addDiscButton.setOnClickListner(btnAddDiscountListner);
//		discountPanel.add(addDiscButton);
//
//
//		left=0;
//		top=mTxtDiscountValue.getY()+mTxtDiscountValue.getHeight()+PANEL_CONTENT_V_GAP;
//		mButtonDiscountDesc=new PosButton();
//		mButtonDiscountDesc.setText("Reason");
//		mButtonDiscountDesc.setBounds(left, top, TEXT_FIELD_WIDTH, TEXT_FIELD_HEIGHT);		
//		mButtonDiscountDesc.setImage("item_edit_discount_select.png");
//		mButtonDiscountDesc.setTouchedImage("item_edit_discount_select_touch.png");
//		mButtonDiscountDesc.setOnClickListner(reasonsListener);
//		discountPanel.add(mButtonDiscountDesc);
//
//		top=mButtonDiscountDesc.getY()+mButtonDiscountDesc.getHeight();
//		left=0;
//		mTxtDiscountDesc=new JTextArea();	
//		mTxtDiscountDesc.setLineWrap(true);
//		mTxtDiscountDesc.setWrapStyleWord(false);
//		mTxtDiscountDesc.setFont(PosFormUtil.getTextFieldFont());	
//		mTxtDiscountDesc.addMouseListener(onDiscDescriptionfocus);
//		JScrollPane scrollPane = new JScrollPane(mTxtDiscountDesc);
//		scrollPane.setBounds(left, top, labelWidthDescription, 120);
//		discountPanel.add(scrollPane);
//
//
//		top=scrollPane.getY()+scrollPane.getHeight();
//		left=0;
//		mTxtDiscountMsg=new JTextField("Warning:    All discounts that are not used will be removed up on save.");	
//		mTxtDiscountMsg.setEditable(false);
//		mTxtDiscountMsg.setForeground(Color.RED);
//		mTxtDiscountMsg.setFont(new Font("Arial",Font.PLAIN,14));
//		mTxtDiscountMsg.setBounds(left, top, labelWidthDescription, discountPanel.getHeight()-top);
//		discountPanel.add(mTxtDiscountMsg);
//	}
//
//	private MouseListener priceEditListner=new MouseAdapter() {
//		@Override
//		public void mouseClicked(MouseEvent e) {
//			if(mTxtDiscountValue.isEditable())
//				PosFormUtil.showNumKeyPad("Discount ?",PosBillDiscountForm.this, mTxtDiscountValue, new IPosNumKeyPadFormListner() {
//					@Override
//					public void onValueChanged(String value) {
//						mSelectedDiscountItem.setPrice(Double.parseDouble(value));
//					}
//					@Override
//					public void onValueChanged(JTextComponent target, String oldValue) {
//						// TODO Auto-generated method stub
//					}
//				});
//		}
//	};
//
//	/**
//	 * Listener for mouse click in the description area.
//	 */
//	private MouseListener onDiscDescriptionfocus=new MouseAdapter() {
//		public void mouseClicked(java.awt.event.MouseEvent e) {
//			if(mTxtDiscountDesc.isEditable())
//				PosFormUtil.showSoftKeyPad(PosBillDiscountForm.this,mTxtDiscountDesc, new PosSoftKeypadAdapter() {
//					@Override
//					public void onAccepted(String text) {
//						mSelectedDiscountItem.setDescription(text);
//					}
//				});
//		};
//	};
//
//
//	/**
//	 * Listener to show the reason browser.
//	 */
//	private IPosButtonListner reasonsListener=new PosButtonListnerAdapter() {
//		@Override
//		public void onClicked(PosButton button) {
//			if(mReasonList==null)
//				mReasonList=mReasonItemProvider.getReasonItemList();
//			IPosBrowsableItem[] itemList=new IPosBrowsableItem[mReasonList.size()];
//			mReasonList.toArray(itemList);
//			PosObjectBrowserForm objectBrowser=new PosObjectBrowserForm("Reasons",itemList,ItemSize.Wider);
//			objectBrowser.setListner(new IPosObjectBrowserListner() {
//				@Override
//				public void onItemSelected(IPosBrowsableItem item) {
//					BeanReason reason=(BeanReason) item;
//					mSelectedDiscountItem.setDescription(reason.getDescription());
//					setValues();
//				}
//				@Override
//				public void onCancel() {
//					// TODO Auto-generated method stub
//
//				}
//			});
//			PosFormUtil.showLightBoxModal(PosBillDiscountForm.this,objectBrowser);
//		}
//	}; 
//
//	private IPosButtonListner btnAddDiscountListner=new PosButtonListnerAdapter() {
//		@Override
//		public void onClicked(PosButton button) {
//			try {
//				mSelectedDiscountItem.setAppliedAt(AppliedAt.BILL_LEVEL);
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			mItemGridPanel.overrideBillDiscounts(mSelectedDiscountItem);
//			addToTable(mSelectedDiscountItem,true);
//		}
//	};
//
//	/**
//	 * Listener for showing the discount browser.
//	 */
//	private IPosButtonListner discountCodeListener=new PosButtonListnerAdapter() {
//		@Override
//		public void onClicked(PosButton button) {
//			if(mBillDiscountList==null){
//				mBillDiscountList=mDiscProvider.getDiscounts(PermittedLevel.BILL);
//			}
//			IPosBrowsableItem[] itemList=new IPosBrowsableItem[mBillDiscountList.size()];
//			mBillDiscountList.toArray(itemList);
//			PosObjectBrowserForm objectBrowser=new PosObjectBrowserForm("Discounts",itemList,ItemSize.Wider);
//			objectBrowser.setListner(new IPosObjectBrowserListner() {
//				@Override
//				public void onItemSelected(IPosBrowsableItem item) {
//					mSelectedDiscountItem=(BeanDiscount) item;
//					setValues();
//				}
//				@Override
//				public void onCancel() {
//				}
//			});
//			PosFormUtil.showLightBoxModal(PosBillDiscountForm.this,objectBrowser);
//		}
//	}; 
//
//	private  IPosButtonListner okButtonListner= new PosButtonListnerAdapter() {
//		@Override
//		public void onClicked(PosButton button) {		
//			setVisible(false);
//			dispose();
//			if(mPosBillDiscountFormListner!=null)
//				mPosBillDiscountFormListner.onOkPressed(mItemGridPanel.getItemList());
//		}
//	};	
//
//	private  IPosButtonListner cancelButtonListner= new PosButtonListnerAdapter() {
//		@Override
//		public void onClicked(PosButton button) {			
//			setVisible(false);
//			dispose();			
//		}
//	};	
//
//	/**
//	 * Sets the values of each controls in the UI
//	 */
//	private void setValues(){
//		mTxtDiscountValue.setText(String.valueOf(mSelectedDiscountItem.getPrice()));
//		mButtonDiscountCode.setText(!(mSelectedDiscountItem.getCode().equals(PosDiscountItemProvider.NONE_DISCOUNT_CODE))?mSelectedDiscountItem.getName():"Select...");
//		mTxtDiscountDesc.setText(!(mSelectedDiscountItem.getCode().equals(PosDiscountItemProvider.NONE_DISCOUNT_CODE))?mSelectedDiscountItem.getDescription():"");
//		mTxtDiscountDesc.setEditable(mSelectedDiscountItem.isOverridable());
//		mButtonDiscountDesc.setEnabled(mSelectedDiscountItem.isOverridable());
////		mLabelcurrencySymbol.setText((mSelectedDiscountItem.isPercentage())?"%":PosEnvSettings.getInstance().getCurrencySymbol());
//		mBtnCurrencySymbol.setText((mSelectedDiscountItem.isPercentage())?"%":PosEnvSettings.getInstance().getCurrencySymbol());
//		mBtnCurrencySymbol.setEnabled(mSelectedDiscountItem.isOverridable());
//		mTxtDiscountValue.setEditable(mSelectedDiscountItem.isOverridable());
//	}
//
//	public void validateComponent() {
//		// TODO Auto-generated method stub
//
//	}
//
//	private IPosBillDiscountFormListner mPosBillDiscountFormListner;
//	public void setListner(IPosBillDiscountFormListner listner){
//		mPosBillDiscountFormListner=listner;
//	}
	



}
