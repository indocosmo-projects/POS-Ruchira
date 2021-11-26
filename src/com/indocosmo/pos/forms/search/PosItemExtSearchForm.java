package com.indocosmo.pos.forms.search;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.InputMethodEvent;
import java.awt.event.InputMethodListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import com.indocosmo.pos.common.PosEnvSettings;
import com.indocosmo.pos.common.PosLog;
import com.indocosmo.pos.common.enums.PosOrderMedium;
import com.indocosmo.pos.common.utilities.PosCurrencyUtil;
import com.indocosmo.pos.common.utilities.PosFormUtil;
import com.indocosmo.pos.common.utilities.PosNumberUtil;
import com.indocosmo.pos.common.utilities.PosPasswordUtil;
import com.indocosmo.pos.common.utilities.PosResUtil;
import com.indocosmo.pos.common.utilities.PosSaleItemUtil;
import com.indocosmo.pos.common.utilities.PosStringUtil;
import com.indocosmo.pos.common.utilities.PosTaxUtil;
import com.indocosmo.pos.common.utilities.PosUomUtil;
import com.indocosmo.pos.data.beans.BeanDiscount;
import com.indocosmo.pos.data.beans.BeanSaleItem;
import com.indocosmo.pos.data.beans.BeanDiscount.AppliedAt;
import com.indocosmo.pos.data.beans.BeanDiscount.EditTypes;
import com.indocosmo.pos.data.beans.BeanDiscount.PermittedLevel;
import com.indocosmo.pos.data.providers.shopdb.PosDiscountItemProvider;
import com.indocosmo.pos.data.providers.shopdb.PosPromotionItemProvider;
import com.indocosmo.pos.data.providers.shopdb.PosSaleItemDiscountProvider;
import com.indocosmo.pos.forms.PosDailyCashoutForm;
import com.indocosmo.pos.forms.PosObjectBrowserForm;
import com.indocosmo.pos.forms.PosOrderEntryForm;
import com.indocosmo.pos.forms.PosRemarksEditForm;
import com.indocosmo.pos.forms.PosObjectBrowserForm.ItemSize;
import com.indocosmo.pos.forms.components.buttons.PosButton;
import com.indocosmo.pos.forms.components.buttons.PosButton.ButtonStyle;
import com.indocosmo.pos.forms.components.buttons.listners.IPosButtonListner;
import com.indocosmo.pos.forms.components.buttons.listners.PosButtonListnerAdapter;
import com.indocosmo.pos.forms.components.jtable.NoBorderTableCellSelectionRender;
import com.indocosmo.pos.forms.components.keypads.PosSoftKeyPad.IPosSoftKeypadListner;
import com.indocosmo.pos.forms.components.keypads.PosSoftKeyPadInputBase;
import com.indocosmo.pos.forms.components.keypads.PosSoftKeyPadSingleLineInput;
import com.indocosmo.pos.forms.components.objectbrowser.IPosBrowsableItem;
import com.indocosmo.pos.forms.components.search.IPosSearchableItem;
import com.indocosmo.pos.forms.components.textfields.PosItemBrowsableField;
import com.indocosmo.pos.forms.components.textfields.PosTextField;
import com.indocosmo.pos.forms.components.textfields.PosTouchableFieldBase;
import com.indocosmo.pos.forms.components.textfields.PosTouchableNumericField;
import com.indocosmo.pos.forms.components.textfields.PosTouchableTextField;
import com.indocosmo.pos.forms.components.textfields.listners.IPosTouchableFieldListner;
import com.indocosmo.pos.forms.components.textfields.listners.PosTouchableFieldAdapter;
import com.indocosmo.pos.forms.listners.IPosFormEventsListner;
import com.indocosmo.pos.forms.listners.IPosObjectBrowserListner;
import com.indocosmo.pos.forms.messageboxes.PosMessageBoxForm.MessageBoxButtonTypes;
import com.indocosmo.pos.forms.messageboxes.PosMessageBoxForm.MessageBoxResults;
import com.indocosmo.pos.forms.search.listener.IPosItemExtSearchFormListener;
import com.indocosmo.pos.reports.base.PosPrintableBase.TextAlign;

@SuppressWarnings("serial")
public class PosItemExtSearchForm extends JDialog {

	private static final int PANEL_CONTENT_H_GAP = 5;
	private static final int PANEL_CONTENT_V_GAP = 2;

	private final int RESULT_ROW_HEIGHT = 35;
	private static final int SCROLL_WIDTH = 20;
	private static final int SCROLL_HEIGHT = 20;
	private static final int SEARCH_RESULT_PANEL_WIDTH = PosSoftKeyPadSingleLineInput.LAYOUT_WIDTH;
	private static final int SEARCH_RESULT_PANEL_HEIGHT = 270;
	private static final int SEARCH_RESULT_PANEL_COL_BORDER_WIDTH = 1;
	private static final int MESSAGE_PANEL_HEIGHT = 20;
	private static final int TITLE_HEIGHT=30;
	private static final Color PANEL_ITEM_HEADER_BG_COLOR = PosOrderEntryForm.PANEL_BG_COLOR;

	private static final int ITEM_DTL_PANEL_WIDTH=340;
	private static final int ITEM_DTL_LABEL_WIDTH=100;
	private static final int ITEM_DTL_HEIGHT = PosTouchableFieldBase.BROWSE_BUTTON_HEIGHT;

	public static final int LAYOUT_HEIGHT = PosSoftKeyPadSingleLineInput.LAYOUT_HEIGHT + TITLE_HEIGHT
			+ SEARCH_RESULT_PANEL_HEIGHT + PANEL_CONTENT_V_GAP * 4;
	public static final int LAYOUT_WIDTH = SEARCH_RESULT_PANEL_WIDTH
			+ PANEL_CONTENT_H_GAP * 2  +ITEM_DTL_PANEL_WIDTH;
	protected static final Color LABEL_BG_COLOR = new Color(78, 128, 188);
	private final static DefaultTableCellRenderer COL_RIGHT_RENDER = new NoBorderTableCellSelectionRender();
	private final static DefaultTableCellRenderer COL_LEFT_RENDER = new NoBorderTableCellSelectionRender();
	
	
	protected static final int IMAGE_BUTTON_WIDTH = 100;
	protected static final int IMAGE_BUTTON_HEIGHT = 40;

	protected static final String IMAGE_BUTTON_OK = "dlg_ok.png";
	protected static final String IMAGE_BUTTON_OK_TOUCH = "dlg_ok_touch.png";

	protected static final String IMAGE_BUTTON_CANCEL = "dlg_cancel.png";
	protected static final String IMAGE_BUTTON_CANCEL_TOUCH = "dlg_cancel_touch.png";
	private static final int  BROWSE_BUTTON_WIDTH=50;
	private static final int LABEL_CUR_SYMBOL_WIDTH = 40;
	private PosSoftKeyPadInputBase mSoftKeyPad;
	private JPanel mResultPanel;
	private JTable mSearchResultTable;
	private TableRowSorter<TableModel> mResultSorter;
	private IPosSearchableItem mSelectedItem = null;
	private IPosSearchableItem[] mActualSearchList; //The original search list which is not filtered.
	private IPosSearchableItem[] mDisplayedSearchList; //Could be different if item has duplicates and mFilterDuplicate=true;
	private String[] mColumnNames;
	private String[] mFieldList;
	private String[] mFieldFormatList;
	private boolean mIsResultTableCreated = false;
	private boolean mAllowRowSelection=false;
	private boolean mCloseOnAccept=true;
	private boolean mFilterDuplicate=false;
	private String mWindowTitle="Select item";
	private JLabel mTitleLabel;
	private boolean showHiddenItems=false;
	
	
	private JLabel mlabelItemName;
	private PosTouchableNumericField mTxtQty;
	private PosTouchableNumericField mTxtPrice;
	private PosTouchableNumericField mTxtDiscountAmount;
	private PosItemBrowsableField mFieldDiscount;
	private JTextField mTxtDiscount;
	private JTextArea mTxtRemarks;
	BeanSaleItem mPosItemToEdit;
	private ArrayList<BeanDiscount> mItemDiscountList;
	private ArrayList<BeanDiscount> mGeneralDiscountItemList;
	private ArrayList<BeanDiscount> mDiscountList;
	private PosButton btnDiscRetrieve;
	private PosButton btnDiscReset;
	private JLabel mLabelCurrencySymbol;
	public PosItemExtSearchForm(
			ArrayList<? extends IPosSearchableItem> searchList) {
		
		setSearchParams(searchList.get(0).getFieldTitleList(),
				searchList.get(0).getFieldList(),searchList,false);
		setFieldsFormats(searchList.get(0).getFieldFormatList());
		initWindow(searchList);
		setFieldsWidth(searchList.get(0).getFieldWidthList());
	}

	public PosItemExtSearchForm(String[] colNames, String[] fieldList,
			ArrayList<? extends IPosSearchableItem> searchList) {
		setSearchParams(colNames,fieldList,searchList,false);
		initWindow(searchList);
	}

	private void setSearchParams(String[] colNames, String[] fieldList,
			ArrayList<? extends IPosSearchableItem> searchList,boolean filterDupliocate){
		mColumnNames = colNames;
		mFieldList = fieldList;
	}

	private void setWindowListner(){
		this.addWindowListener(new WindowAdapter() {
			/* (non-Javadoc)
			 * @see java.awt.event.WindowAdapter#windowOpened(java.awt.event.WindowEvent)
			 */
			@Override
			public void windowOpened(WindowEvent e) {
				populateSearchTable();
				super.windowOpened(e);
			}
		});
	}

	/**
	 * @param searchList
	 */
	private void initWindow(ArrayList<? extends IPosSearchableItem> searchList) {
		IPosSearchableItem[] itemList = new IPosSearchableItem[searchList
		                                                       .size()];
		searchList.toArray(itemList);

		initWindow(itemList);
		if (itemList == null || itemList.length <= 0) {
			mSoftKeyPad.setEnabled(false);
		}
	}

	private void initWindow(IPosSearchableItem[] searchList) {

		setWindowListner();

		COL_RIGHT_RENDER.setHorizontalAlignment(JLabel.RIGHT);
		COL_RIGHT_RENDER.setHorizontalTextPosition(JLabel.LEFT);

		mActualSearchList =getFilteredItemList(searchList);
		setSize(LAYOUT_WIDTH, LAYOUT_HEIGHT);
		setLayout(null);
		mSoftKeyPad = new PosSoftKeyPadSingleLineInput();
		mSoftKeyPad.showActionActrols(false);
		mSoftKeyPad.setListner(softKeypadListener);

		mTitleLabel=PosFormUtil.setHeading(mWindowTitle, this.getWidth()-ITEM_DTL_PANEL_WIDTH-PANEL_CONTENT_H_GAP*2, TITLE_HEIGHT);
		mTitleLabel.setBackground(PosFormUtil.SECTION_HEADING_PANEL_BG_COLOR);
		mTitleLabel.setLocation(PANEL_CONTENT_H_GAP, PANEL_CONTENT_V_GAP+3);
		mTitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		mTitleLabel.setFont(PosFormUtil.getTextFieldBoldFont());
		add(mTitleLabel);

		createItemDetailPanel();
		final int leftResultPanel =PANEL_CONTENT_H_GAP;
		final int topResultPanel =mTitleLabel.getY()+mTitleLabel.getHeight() -3;
		mResultPanel = new JPanel();
		mResultPanel.setSize(SEARCH_RESULT_PANEL_WIDTH,
				SEARCH_RESULT_PANEL_HEIGHT);
		mResultPanel.setLocation(leftResultPanel, topResultPanel);
		//		mResultPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
		mResultPanel.setLayout(new FlowLayout());
		add(mResultPanel);
		if (mActualSearchList != null && mActualSearchList.length > 0)
			createResultTable();
		else
			createNoItemWarning();

		final int leftSoftKeyPad = PANEL_CONTENT_H_GAP;
		final int topSoftKeyPad = mResultPanel.getY()+mResultPanel.getHeight()
				+ PANEL_CONTENT_V_GAP ;
		mSoftKeyPad.setLocation(leftSoftKeyPad, topSoftKeyPad);
		add(mSoftKeyPad);

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				super.windowOpened(e);
				mSoftKeyPad.setFocus();
			}
		});
		
		setSorting(0);
	}
	
	private IPosSearchableItem[] getFilteredItemList(IPosSearchableItem[] itemList){
		
		List<IPosSearchableItem> tmpList=new ArrayList<IPosSearchableItem>();
		
		for(int index=0; index<itemList.length; index++){
			
			if(itemList[index].isVisibleInUI() || isShowHiddenItems()){
				
				tmpList.add(itemList[index]);
			}
		}
		
		return tmpList.toArray(new IPosSearchableItem[tmpList.size()]);
		
	}

	private void createNoItemWarning() {
		JLabel warning = new JLabel("There is no item to display!!!",
				SwingConstants.CENTER);
		warning.setPreferredSize(new Dimension(SEARCH_RESULT_PANEL_WIDTH
				- PANEL_CONTENT_H_GAP * 2, SEARCH_RESULT_PANEL_HEIGHT
				- PANEL_CONTENT_V_GAP * 2));
		warning.setHorizontalTextPosition(SwingConstants.CENTER);
		warning.setVerticalTextPosition(SwingConstants.CENTER);
		mResultPanel.add(warning);
	}

	private DefaultTableModel getTableModel() {

		DefaultTableModel model =  PosFormUtil.getReadonlyTableModel();
		model.setColumnIdentifiers(mColumnNames);

		return model;
	}

	private void populateSearchTable(){
		
		if(mSearchResultTable==null) return;

		DefaultTableModel model=(DefaultTableModel)mSearchResultTable.getModel();

		if (mActualSearchList.length > 0) {
			mDisplayedSearchList=(mFilterDuplicate)?getFilteredList():mActualSearchList;
			final Class<?> itemClass = mDisplayedSearchList[0].getClass();
			try {
				for (IPosSearchableItem item : mDisplayedSearchList) {
					if(item.isVisibleInUI() || showHiddenItems) // hide none displyable items
						model.addRow(getRowValues(item, itemClass));
				}
			} catch (Exception e) {
				PosLog.write(this, "getTableModel", e);
				PosFormUtil.showErrorMessageBox(this,
						"Failed to get search data. Contact Administrator.");
			}
		}
	}

	/**
	 * @return
	 */
	private IPosSearchableItem[] getFilteredList() {

		ArrayList<Object> itemCodes=new ArrayList<Object>();
		ArrayList<IPosSearchableItem> newList=new ArrayList<IPosSearchableItem>();

		for(IPosSearchableItem item:mActualSearchList){
			if(! itemCodes.contains(item.getItemCode())){
				newList.add(item);
				itemCodes.add(item.getItemCode());
			}
		}

		IPosSearchableItem[] newListArray=new IPosSearchableItem[newList.size()];
		newList.toArray(newListArray);
		return newListArray;
	}

	/**
	 * @param item
	 * @param itemClass
	 * @return
	 * @throws Exception
	 */
	private String[] getRowValues(Object item, Class<?> itemClass)
			throws Exception {
		String[] values = new String[mFieldList.length];
		for (int index = 0; index < mFieldList.length; index++) {
			
			final String format=(mFieldFormatList!=null && index<mFieldFormatList.length)?mFieldFormatList[index]:"";
			final Method method = itemClass.getMethod(mFieldList[index], new Class[] {});
			
			if (method.getReturnType().equals(Double.TYPE))
				values[index] = PosNumberUtil.format(Double.parseDouble(method.invoke(item, new Object[] {}).toString()),format);
			else
				values[index] =	String.valueOf(method.invoke(item, new Object[] {}));
		}
		
		return values;
	}

	/**
	 * @return
	 */
	protected JTable getSearchTable() {
		DefaultTableModel model = getTableModel();
		JTable searchTable = new JTable(model);
		searchTable.setRowSorter(new TableRowSorter<TableModel>(model));
		searchTable.setRowHeight(RESULT_ROW_HEIGHT);
		searchTable.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
		searchTable.setFont(getFont().deriveFont(18f));
		searchTable.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		searchTable.getTableHeader().setReorderingAllowed(false);
		searchTable.getTableHeader().setResizingAllowed(false);
		setColAlignment(searchTable);
		return searchTable;
	}

	/***
	 * sets the column alignment
	 * 
	 * @param table
	 * @param colIndex
	 */
	private void setColAlignment(JTable table) {
		final Class<?> itemClass = mActualSearchList[0].getClass();
		try {
			for (int colIndex = 0; colIndex < mFieldList.length; colIndex++) {
				DefaultTableCellRenderer cr = COL_LEFT_RENDER;
				Method method = itemClass.getMethod(mFieldList[colIndex],
						new Class[] {});
				Class<?> cls = method.getReturnType();
				if (cls.equals(Double.TYPE) || cls.equals(Float.TYPE)
						|| cls.equals(Integer.TYPE))
					cr = COL_RIGHT_RENDER;
				table.getColumnModel().getColumn(colIndex).setCellRenderer(cr);
			}
		} catch (NoSuchMethodException e) {

		} catch (SecurityException e) {

		}

	}

	/* (non-Javadoc)
	 * @see java.awt.Window#dispose()
	 */
	@Override
	public void dispose() {
		setVisible(false);
		super.dispose();
	}

	/**
	 * 
	 */
	@SuppressWarnings("unchecked")
	protected void createResultTable() {

		mSearchResultTable = getSearchTable();
		mResultSorter = (TableRowSorter<TableModel>) mSearchResultTable
				.getRowSorter();
		ListSelectionModel tableSelModel = mSearchResultTable
				.getSelectionModel();
		tableSelModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tableSelModel.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {

				mSelectedItem=null;
				final ListSelectionModel rowSM = (ListSelectionModel) e
						.getSource();
				if(rowSM.getLeadSelectionIndex()>=0 && rowSM.getLeadSelectionIndex()<mSearchResultTable.getRowCount()){
					final int selectedIndex = mSearchResultTable
							.convertRowIndexToModel(rowSM.getLeadSelectionIndex());
					mSelectedItem = mDisplayedSearchList[selectedIndex];
				}
				 if(mSelectedItem!=null)
					 setSelectedItemDetails((BeanSaleItem)mSelectedItem);

				if(!mAllowRowSelection || mSelectedItem==null) return;
				
				if (mListner != null) {
					if(mListner.onAccepted(PosItemExtSearchForm.this,mPosItemToEdit))
						dispose();
				}else
					dispose();
			}
		});



		mSearchResultTable.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyPressed(final KeyEvent e) {

				if(e.getKeyCode()==KeyEvent.VK_ENTER){
					e.consume();
					softKeypadListener.onAccepted("");
				}else if(e.getKeyCode()==KeyEvent.VK_KP_UP || e.getKeyCode()==KeyEvent.VK_KP_DOWN || 
						e.getKeyCode()==KeyEvent.VK_UP || e.getKeyCode()==KeyEvent.VK_DOWN ){

					//Nothing to do. Just default behavior.

				}else if(e.getKeyCode()==KeyEvent.VK_ESCAPE){
					e.consume();
					softKeypadListener.onCancel();
				}else if(e.getKeyCode()==KeyEvent.VK_TAB){
					mTxtQty.requestFocus();
					mTxtQty.setSelectedAll();
				}else {

					mSearchResultTable.getSelectionModel().clearSelection();
					mSoftKeyPad.reset();
					PosFormUtil.sendKeys(e.getKeyChar(),mSoftKeyPad.getTextComponent());
					e.consume();
				}

			}
		});
		
		mSearchResultTable.addMouseListener(new MouseAdapter() {
			/* (non-Javadoc)
			 * @see java.awt.event.MouseAdapter#mousePressed(java.awt.event.MouseEvent)
			 */
			@Override
			public void mousePressed(MouseEvent e) {
				super.mousePressed(e);
				if(e.getClickCount()==2){
					e.consume();
					softKeypadListener.onAccepted("");
				}
			}
		});

		JScrollPane pane = new JScrollPane(mSearchResultTable,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		pane.getHorizontalScrollBar().setPreferredSize(
				new Dimension(0, SCROLL_HEIGHT));
		pane.getVerticalScrollBar().setPreferredSize(
				new Dimension(SCROLL_WIDTH, 0));
		pane.setPreferredSize(new Dimension(mResultPanel.getWidth(),
				mResultPanel.getHeight() - 8));
		pane.getViewport().setBackground(Color.WHITE);
		pane.setLocation(0, 0);

		mResultPanel.add(pane);
		mIsResultTableCreated = true;
	}

	/**
	 * @param width
	 */
	public void setFieldsWidth(int[] width) {
		
		if (!mIsResultTableCreated)
			return;
		int index = 0;
		for (; index < width.length; index++)
			if (index < mFieldList.length) {
				mSearchResultTable.getColumnModel().getColumn(index)
				.setMaxWidth(width[index]);
				mSearchResultTable.getColumnModel().getColumn(index)
				.setMinWidth(width[index]);
				mSearchResultTable.getColumnModel().getColumn(index)
				.setPreferredWidth(width[index]);
			} else
				break;
		mSearchResultTable.invalidate();
	}
	
	/**
	 * @param oRDER_RETRIEVE_SEARCH_FIELD_WIDTH
	 */
	public void setFieldsFormats(String[] fieldFormats) {

		this.mFieldFormatList=fieldFormats;
				
	}

	private IPosSoftKeypadListner softKeypadListener=new IPosSoftKeypadListner() {
		@Override
		public void onCancel() {
			//			setVisible(false);
			if (mListner != null){
				if(mListner.onCancel(PosItemExtSearchForm.this))
					dispose();
			}else
				dispose();
		}

		@Override
		public void onAccepted(String text) {
			
			if (mSelectedItem == null)
				return;

			if (mListner != null){

				if(mListner.onAccepted(PosItemExtSearchForm.this,mPosItemToEdit)){

					if(mCloseOnAccept){
						dispose();
					}

				}
			}else{
				
				if(mCloseOnAccept){
					dispose();
				}
			}

		}
		
		@Override
		public void onTextChanged(String text) {
			
			mResultSorter.setRowFilter(RowFilter.regexFilter("(?i)" + PosStringUtil.escapeSpecialRegexChars(text)));
				
		}

		@Override
		public void onAccepted(int index) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onKeyPressed(KeyEvent e) {

			if(e.getKeyCode()==KeyEvent.VK_ENTER || e.getKeyCode()==KeyEvent.VK_UP || e.getKeyCode()==KeyEvent.VK_DOWN){
				e.consume();
				mSearchResultTable.getSelectionModel().clearSelection();
				mSearchResultTable.changeSelection(0, 0, false, false);
				mSearchResultTable.requestFocus();
			}else if( e.getKeyCode()==KeyEvent.VK_TAB ){
				e.consume();
				mSearchResultTable.getSelectionModel().clearSelection();
				mSearchResultTable.changeSelection(0, 0, false, false);
				mTxtQty.setSelectedAll();
				mTxtQty.requestFocus();
			}
		}
	};
	
	/**
	 * 0 based column number
	 * @param columnNo
	 */
	public void setSorting(int columnNo){
		
		setSorting(columnNo,SortOrder.DESCENDING);
	}
	
	/**
	 * @param columnNo
	 * @param sort
	 */
	public void setSorting(int columnNo, SortOrder sort){
		
		List<RowSorter.SortKey> sortKeys = new ArrayList<>(25);
		sortKeys.add(new RowSorter.SortKey(columnNo, sort));
		if(mResultSorter!=null && sortKeys!=null && sortKeys.size()>0){
			mResultSorter.setSortKeys(sortKeys);
			mResultSorter.sort();
		}
	}

	public void setListner(IPosItemExtSearchFormListener listner) {
		mListner = listner;
//		mSoftKeyPad.setListner(softKeypadListener);

	}

	public void seFilterDuplicate(boolean filter){
		mFilterDuplicate=filter;
	}

	/**
	 * @return the mAllowRowSelection
	 */
	public boolean isAllowRowSelection() {
		return mAllowRowSelection;
	}

	/**
	 * @param mAllowRowSelection the mAllowRowSelection to set
	 */
	public void setAllowRowSelection(boolean allowRowSelection) {
		this.mAllowRowSelection = allowRowSelection;
	}

	private IPosItemExtSearchFormListener mListner;

	/**
	 * @return the closeOnAccept
	 */
	public boolean isCloseOnAccept() {
		return mCloseOnAccept;
	}

	/**
	 * @param closeOnAccept the closeOnAccept to set
	 */
	public void setCloseOnAccept(boolean closeOnAccept) {
		this.mCloseOnAccept = closeOnAccept;
	}

	/**
	 * @return the windowTitle
	 */
	public String getWindowTitle() {
		return mWindowTitle;
	}

	/**
	 * @param windowTitle the windowTitle to set
	 */
	public void setWindowTitle(String windowTitle) {
		this.mWindowTitle = windowTitle;
		mTitleLabel.setText(mWindowTitle);
	}

	/**
	 * @return the showHiddenItems
	 */
	public boolean isShowHiddenItems() {
		return showHiddenItems;
	}

	/**
	 * @param showHiddenItems the showHiddenItems to set
	 */
	public void setShowHiddenItems(boolean showHiddenItems) {
		this.showHiddenItems = showHiddenItems;
	}
	
 
	/**
	 * 
	 */
	private void createItemDetailPanel(){
		
		final int width=ITEM_DTL_PANEL_WIDTH -PANEL_CONTENT_H_GAP/2;
//		mTitleLabel.setLocation(PANEL_CONTENT_H_GAP, PANEL_CONTENT_V_GAP+3);
		JPanel itemDetPanel=new JPanel();
		itemDetPanel.setSize(new Dimension(ITEM_DTL_PANEL_WIDTH, LAYOUT_HEIGHT-PANEL_CONTENT_V_GAP*4));
		itemDetPanel.setLocation(mTitleLabel.getWidth()+PANEL_CONTENT_H_GAP, PANEL_CONTENT_V_GAP+2);
//		itemDetPanel.setBackground(Color.red);
		itemDetPanel.setLayout(null);
		add(itemDetPanel);
		//		PosTextField mTitleLabel=new PosTextField();
		//		mTitleLabel.setText(mWindowTitle);
		JLabel itemDetTitleLabel=PosFormUtil.setHeading(" ",width , TITLE_HEIGHT);
		itemDetTitleLabel.setBackground(PosFormUtil.SECTION_HEADING_PANEL_BG_COLOR);
		itemDetTitleLabel.setLocation(PANEL_CONTENT_H_GAP/2, PANEL_CONTENT_V_GAP/2);
		itemDetTitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		itemDetTitleLabel.setFont(PosFormUtil.getTextFieldBoldFont());
		itemDetPanel.add(itemDetTitleLabel);
		
		final int left=PANEL_CONTENT_H_GAP/2;
		int top=itemDetTitleLabel.getX() + TITLE_HEIGHT+ PANEL_CONTENT_V_GAP;
		 
		JPanel itemNamePanel = new JPanel();
		itemNamePanel
		.setBounds(left, top, width, TITLE_HEIGHT+PANEL_CONTENT_V_GAP*2);
		itemNamePanel.setBackground(PANEL_ITEM_HEADER_BG_COLOR);
		itemNamePanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
		itemNamePanel.setLayout(null);

		itemDetPanel.add(itemNamePanel);

		mlabelItemName = new JLabel();
		mlabelItemName.setText("");
		mlabelItemName.setHorizontalAlignment(SwingConstants.LEFT);
		mlabelItemName.setBounds(0, 0, width, TITLE_HEIGHT);
		mlabelItemName.setFont(PosFormUtil.getLoginLabelFont());
		itemNamePanel.add(mlabelItemName);
		 
		top =itemNamePanel.getY() + itemNamePanel.getHeight()+PANEL_CONTENT_V_GAP+1;
		mTxtQty=createField(itemDetPanel, "Quantity :",'Q',left,top);
		mTxtQty.setListner(new IPosTouchableFieldListner() {
			
			@Override
			public void onValueSelected(Object value) {
					
				if (onQtyChanged()) {
					if(mTxtPrice.isEnabled()){
						mTxtPrice.requestFocus();
						mTxtPrice.setSelectedAll();
					}else
						mTxtDiscount.requestFocus();
				}
				
			}
			
			@Override
			public void onReset() {
				mTxtQty.setText("1");
				mTxtQty.requestFocus();
				mTxtQty.setSelectedAll();
			}
		}); 
		
		
		top +=ITEM_DTL_HEIGHT+PANEL_CONTENT_V_GAP*2;
		mTxtPrice=createField(itemDetPanel, "Price :",'P',left,top);
		mTxtPrice.setListner(new IPosTouchableFieldListner() {
			
			@Override
			public void onValueSelected(Object value) {
				mTxtDiscount.requestFocus();	
			}
			
			@Override
			public void onReset() {
				// TODO Auto-generated method stub
				
			}
		});
		top +=ITEM_DTL_HEIGHT+PANEL_CONTENT_V_GAP*2;
		 createDiscountField(itemDetPanel, "Discount ",'D', left, top);
		
		top +=ITEM_DTL_HEIGHT+PANEL_CONTENT_V_GAP*2;
		createDiscountValueField(itemDetPanel,  "Discount :",'i',left,top);
		
		top +=ITEM_DTL_HEIGHT+PANEL_CONTENT_V_GAP*2;
		createRemarksField(itemDetPanel,"Remarks :",'R',left,top);
		
		
		top = LAYOUT_HEIGHT-IMAGE_BUTTON_HEIGHT-PANEL_CONTENT_V_GAP*6;
		createItemDetailButton(itemDetPanel, left,top);
	
		setEnableControls();
	}
	
	/***
	 * 
	 * @param title
	 * @return
	 */
	private void createItemDetailButton(JPanel panel ,int left,int top) {
		
		final int width=ITEM_DTL_PANEL_WIDTH -PANEL_CONTENT_H_GAP/2;
		JPanel itemPanel = new JPanel();
		itemPanel
		.setBounds(left, top, width, ITEM_DTL_HEIGHT+PANEL_CONTENT_V_GAP);
		itemPanel
		.setLayout(new FlowLayout(FlowLayout.LEFT,PANEL_CONTENT_H_GAP*2, PANEL_CONTENT_V_GAP));
//		itemPanel.setBorder(new EtchedBorder());
		panel.add(itemPanel);
		
		 PosButton mButtonItemDetail = new PosButton("Detail");
		 mButtonItemDetail.setMnemonic('e');
		mButtonItemDetail.setImage(PosResUtil
				.getImageIconFromResource(IMAGE_BUTTON_OK));
		mButtonItemDetail.setTouchedImage(PosResUtil
				.getImageIconFromResource(IMAGE_BUTTON_OK_TOUCH));
		mButtonItemDetail.setHorizontalAlignment(SwingConstants.CENTER);
		mButtonItemDetail.setSize(IMAGE_BUTTON_WIDTH, IMAGE_BUTTON_HEIGHT);
		mButtonItemDetail.setOnClickListner(new IPosButtonListner() {
			
			@Override
			public void onClicked(PosButton button) {

				if (mSelectedItem == null)
					return;

				if (mListner != null){

					if(mListner.onAccepted(PosItemExtSearchForm.this,mPosItemToEdit)){
						 mListner.onDetailClicked(PosItemExtSearchForm.this,mPosItemToEdit);
//						 dispose();

					}
					
				}else{
						dispose();
				}				
			}
		});
		itemPanel.add(mButtonItemDetail);
		
 
		 PosButton mButtonItemAdd = new PosButton("Add");
		 mButtonItemAdd.setMnemonic('A');
		 mButtonItemAdd.setImage(PosResUtil
					.getImageIconFromResource(IMAGE_BUTTON_OK));
		 mButtonItemAdd.setTouchedImage(PosResUtil
					.getImageIconFromResource(IMAGE_BUTTON_OK_TOUCH));
		 mButtonItemAdd.setHorizontalAlignment(SwingConstants.CENTER);
		 mButtonItemAdd.setSize(IMAGE_BUTTON_WIDTH, IMAGE_BUTTON_HEIGHT);
		 mButtonItemAdd.setOnClickListner(new IPosButtonListner() {
			
			@Override
			public void onClicked(PosButton button) {
				if (applyChanges()){
					softKeypadListener.onAccepted("");	
					mSearchResultTable.getSelectionModel().clearSelection();
					mSoftKeyPad.reset();
					clearItemDetails();
					mSearchResultTable.requestFocus();
				}
				
			}
		});
		itemPanel.add(mButtonItemAdd);
		
		 PosButton mButtonItemCancel = new PosButton("Cancel");
		 mButtonItemCancel.setMnemonic('C');
		 mButtonItemCancel.setImage(PosResUtil
					.getImageIconFromResource(IMAGE_BUTTON_CANCEL));
		 mButtonItemCancel.setTouchedImage(PosResUtil
					.getImageIconFromResource(IMAGE_BUTTON_CANCEL_TOUCH));
		 mButtonItemCancel.setHorizontalAlignment(SwingConstants.CENTER);
		 mButtonItemCancel.setSize(IMAGE_BUTTON_WIDTH, IMAGE_BUTTON_HEIGHT);
		 mButtonItemCancel.setOnClickListner(new IPosButtonListner() {
				
				@Override
				public void onClicked(PosButton button) {
					softKeypadListener.onCancel();				
				}
			});
		 itemPanel.add(mButtonItemCancel);
		
	 
	}
	
	/***
	 * 
	 * @param title
	 * @return
	 */
	private JPanel creatFieldPanelWithTitle(String title ,char mnemonic,int left,int top,int labelWidth) {
		
		final int width=ITEM_DTL_PANEL_WIDTH -PANEL_CONTENT_H_GAP/2;
		JPanel itemPanel = new JPanel();
		itemPanel.setBounds(left, top, width, ITEM_DTL_HEIGHT+PANEL_CONTENT_V_GAP);
		itemPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 1, 0));

		JLabel label = new JLabel(PosFormUtil.getMnemonicString(title,mnemonic));
		label.setPreferredSize(new Dimension(labelWidth, ITEM_DTL_HEIGHT));
		label.setBorder(new EmptyBorder(2, 2, 2, 2));
		label.setFont(PosFormUtil.getLabelFont());
		label.setOpaque(true);
		label.setBackground(Color.LIGHT_GRAY);
		itemPanel.add(label);

		return itemPanel;
	}

	/**
	 * 
	 * @param panel
	 * @param title
	 * @return
	 */
	private PosTouchableNumericField createField(JPanel panel, String title,char mnemonic,int left,int top) {
		JPanel itemPanel = creatFieldPanelWithTitle(title,mnemonic,left,top,ITEM_DTL_LABEL_WIDTH);
		panel.add(itemPanel);

		final int width=ITEM_DTL_PANEL_WIDTH -ITEM_DTL_LABEL_WIDTH -PANEL_CONTENT_H_GAP ;
		PosTouchableNumericField text = new PosTouchableNumericField(this,width);
		text.setTitle(title);
		text.setMnemonic(mnemonic);
		text.setFont(PosFormUtil.getTextFieldFont());
		itemPanel.add(text);
		 
		return text;
	}
	
	 
	/**
	 * 
	 * @param panel
	 * @param title
	 * @return
	 */
	private void createDiscountValueField(JPanel panel, String title,char mnemonic,int left,int top) {
		JPanel itemPanel = creatFieldPanelWithTitle(title,mnemonic,left,top,ITEM_DTL_LABEL_WIDTH);
		panel.add(itemPanel);

		 
		mLabelCurrencySymbol = new JLabel();
		mLabelCurrencySymbol.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
		mLabelCurrencySymbol.setHorizontalAlignment(SwingConstants.CENTER);
		mLabelCurrencySymbol.setPreferredSize(new Dimension(LABEL_CUR_SYMBOL_WIDTH,
				ITEM_DTL_HEIGHT));
		itemPanel.add(mLabelCurrencySymbol);
		
		final int width=ITEM_DTL_PANEL_WIDTH -ITEM_DTL_LABEL_WIDTH - LABEL_CUR_SYMBOL_WIDTH -PANEL_CONTENT_H_GAP-1 ;
		mTxtDiscountAmount = new PosTouchableNumericField(this,width);
		mTxtDiscountAmount.setTitle(title);
		mTxtDiscountAmount.setMnemonic(mnemonic);
		mTxtDiscountAmount.setFont(PosFormUtil.getTextFieldFont());
		itemPanel.add(mTxtDiscountAmount);
		 
		 
	}
	/***
	 * 
	 * @param panel
	 * @return
	 */
	private void createDiscountField(JPanel panel, String title,char mnemonic ,int left,int top) {
			
		int width=ITEM_DTL_PANEL_WIDTH -PANEL_CONTENT_H_GAP/2;
	 
		JPanel itemPanel = new JPanel();
		itemPanel
		.setBounds(left, top, width, ITEM_DTL_HEIGHT+PANEL_CONTENT_V_GAP);
		itemPanel
		.setLayout(new FlowLayout(FlowLayout.LEFT, 1, 0));
		 
		 panel.add(itemPanel);
		 
		width=width-BROWSE_BUTTON_WIDTH*2-PANEL_CONTENT_H_GAP*1  ;
		
		mTxtDiscount = new JTextField("");
		mTxtDiscount.setPreferredSize(new Dimension(width , ITEM_DTL_HEIGHT));
		mTxtDiscount.setFont(PosFormUtil.getLabelFont());
		mTxtDiscount.setEditable(false); 
		itemPanel.add(mTxtDiscount);

		btnDiscRetrieve=new PosButton("...");
		btnDiscRetrieve.setMnemonic(mnemonic);
		btnDiscRetrieve.setPreferredSize(new Dimension(BROWSE_BUTTON_WIDTH ,ITEM_DTL_HEIGHT));
		btnDiscRetrieve.setButtonStyle(ButtonStyle.IMAGE);
		btnDiscRetrieve.setImage(PosTouchableFieldBase.CLICK_BUTTON_NORMAL, PosTouchableFieldBase.CLICK_BUTTON_TOUCHED);
		btnDiscRetrieve.setOnClickListner(discountCodeListener);
		itemPanel.add(btnDiscRetrieve);
	  
		
		btnDiscReset=new PosButton("");
		btnDiscReset.setPreferredSize(new Dimension(BROWSE_BUTTON_WIDTH ,ITEM_DTL_HEIGHT));
		btnDiscReset.setButtonStyle(ButtonStyle.IMAGE);
		btnDiscReset.setImage(PosTouchableFieldBase.RESET_BUTTON_NORMAL, PosTouchableFieldBase.RESET_BUTTON_TOUCHED);//PosTouchableFieldBase.RESET_BUTTON_NORMAL,PosTouchableFieldBase.RESET_BUTTON_TOUCHED);
		btnDiscReset.setOnClickListner(new IPosButtonListner() {
			
			@Override
			public void onClicked(PosButton button) {
			
				 
			}
		});
		itemPanel.add(btnDiscReset);
	   
	}
	
	
	private void createRemarksField(JPanel panel, String title,char mnemonic,int left,int top) {
		
		int width=ITEM_DTL_PANEL_WIDTH -PANEL_CONTENT_H_GAP/2;
	 
		JPanel itemPanel = creatFieldPanelWithTitle(title,mnemonic,left,top,
				width- PosTouchableFieldBase.BROWSE_BUTTON_WIDTH*2-PANEL_CONTENT_V_GAP*3);
		panel.add(itemPanel);
		
		final PosButton remarksEditButton=new PosButton("...");
		remarksEditButton.setButtonStyle(ButtonStyle.IMAGE);
		remarksEditButton.setMnemonic(mnemonic);
		remarksEditButton.setPreferredSize(new Dimension(PosTouchableFieldBase.BROWSE_BUTTON_WIDTH,PosTouchableFieldBase.BROWSE_BUTTON_HEIGHT));
		remarksEditButton.setImage(PosTouchableFieldBase.CLICK_BUTTON_NORMAL, PosTouchableFieldBase.CLICK_BUTTON_TOUCHED);
		remarksEditButton.setOnClickListner(new IPosButtonListner() {
			
			@Override
			public void onClicked(PosButton button) {
				
				final PosRemarksEditForm form=new PosRemarksEditForm();
				form.setRemarks(mTxtRemarks.getText());
				form.setListner(new IPosFormEventsListner() {
					
					@Override
					public void onResetButtonClicked() {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public boolean onOkButtonClicked() {
						mTxtRemarks.setText(form.getRemarks());
						return true;
					}
					
					@Override
					public boolean onCancelButtonClicked() {
						// TODO Auto-generated method stub
						return false;
					}
				});
				PosFormUtil.showLightBoxModal(PosItemExtSearchForm.this,form);
				
			}
		});
		itemPanel.add(remarksEditButton);
		
		final PosButton remarksResetButton=new PosButton();
		remarksResetButton.setButtonStyle(ButtonStyle.IMAGE);
		remarksResetButton.setPreferredSize(new Dimension(PosTouchableFieldBase.RESET_BUTTON_DEF_WIDTH,PosTouchableFieldBase.RESET_BUTTON_DEF_HEIGHT));
		remarksResetButton.setImage(PosTouchableFieldBase.RESET_BUTTON_NORMAL, PosTouchableFieldBase.RESET_BUTTON_TOUCHED);
		remarksResetButton.setOnClickListner(new IPosButtonListner() {
			
			@Override
			public void onClicked(PosButton button) {
				
				if(PosFormUtil.showQuestionMessageBox(PosItemExtSearchForm.this, MessageBoxButtonTypes.YesNo, "Are you sure, do you want to clear the remarks?",null)==MessageBoxResults.Yes){
					
					mTxtRemarks.setText("");
				}
			}
		});
		itemPanel.add(remarksResetButton);
		
		JPanel mRemakTextPanel = new JPanel();
		mRemakTextPanel.setBounds(left, top + itemPanel.getHeight() + PANEL_CONTENT_V_GAP,
				width-2 , ITEM_DTL_HEIGHT*6-PANEL_CONTENT_V_GAP );
		mRemakTextPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
		mRemakTextPanel.setLayout(null);		
		panel.add(mRemakTextPanel);
		
		mTxtRemarks=new JTextArea(10,19);
		mTxtRemarks.setLineWrap(true);
		mTxtRemarks.setFont(PosFormUtil.getTextFieldBoldFont());
		mTxtRemarks.setBounds(PANEL_CONTENT_V_GAP, PANEL_CONTENT_V_GAP, width-PANEL_CONTENT_H_GAP-2 , ITEM_DTL_HEIGHT*6-PANEL_CONTENT_V_GAP*3); 
		mRemakTextPanel.add(mTxtRemarks);
		
	}
	/*
	 * 
	 */
	private void setSelectedItemDetails(BeanSaleItem saleItem){
		mPosItemToEdit=saleItem.clone();
		mlabelItemName.setText(saleItem.getName());
		mTxtQty.setText(PosUomUtil.format(saleItem.getQuantity(),saleItem.getUom()));
		mTxtPrice.setText(PosCurrencyUtil.format(saleItem.getFixedPrice()));
		setEnableControls();
	}
	
	/**
	 * Listener for showing the discount browser.
	 */
	private IPosButtonListner discountCodeListener = new PosButtonListnerAdapter() {
		@Override
		public void onClicked(PosButton button) {
			
			if (mPosItemToEdit==null)
				return;
			if (mPosItemToEdit.getDiscount() != null
					&& mPosItemToEdit.getDiscount().isPromotion()
					&& mPosItemToEdit.getDiscount().getCode() != PosPromotionItemProvider.DEF_PROMO_CODE) {

				PosFormUtil.showInformationMessageBox(this,
						"Since Promotion exists, no Discount available.");
				return;
			}

			if (mDiscountList == null) {
				  PosSaleItemDiscountProvider mSaleItemDiscProvider=new PosSaleItemDiscountProvider();
				  PosDiscountItemProvider mDiscItemProvider=new PosDiscountItemProvider();
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
						if(mTxtDiscountAmount.isEnabled()){
							mTxtDiscountAmount.requestFocus();
							mTxtDiscountAmount.selectAll();
						}else{
							mTxtRemarks.requestFocus();
							mTxtRemarks.selectAll();
						}
							
					}
				}

				@Override
				public void onCancel() {
				}
			});
			PosFormUtil.showLightBoxModal(PosItemExtSearchForm.this, objectBrowser);
		}
	};
	
	private void applyDiscount(BeanDiscount discount){
		mPosItemToEdit.setFixedPrice(PosNumberUtil.parseDoubleSafely(mTxtPrice.getText()));
		mPosItemToEdit.setDiscount(discount.clone());
		setDiscountAppliedLevel(mPosItemToEdit.getDiscount());
		 resetItem();
		
	}
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


	private boolean validateDiscount(BeanDiscount discount) {
		boolean isValid = true;
		String disPassword =  (discount.getDiscountPassword()!=null)?discount.getDiscountPassword().trim():null;
		if (disPassword != null && !disPassword.isEmpty()) {
			if (!PosPasswordUtil.getValidateDiscountPassword(disPassword)) {
				PosFormUtil.showErrorMessageBox(this, "Wrong password.");
				return false;
			}
		}
		if (mPosItemToEdit.getQuantity() < discount.getRequiredQuantity()) {
			isValid = false;
			PosFormUtil
					.showErrorMessageBox(this,
							"Item does not have enough quantity to apply this discount.");
		} else if (discount.getEditType() == EditTypes.UnitPrice
				&& mPosItemToEdit.isOpen() && isValid) {
			isValid = false;
			PosFormUtil
					.showErrorMessageBox(
							this,
							"The selected discount is not applicable to this sale item. Item is open. Please adjust price.");
		}
		// else
		// if(discount.getPrice()>PosSaleItemUtil.getItemFixedPrice(mPosItemClone)&&!discount.isPercentage()){
		// isValid=false;
		// PosFormUtil.showErrorMessageBox(null,"The selected discount is not applicable to this sale item. Discount amount is greater than item price.");
		// }
		return isValid;
	}
	
	
	private void resetItem() {
		PosTaxUtil.calculateTax(mPosItemToEdit);
		
		// set values 
		mTxtDiscount.setText(mPosItemToEdit.getDiscount().getName());
//		mTxtDiscountAmount.setText(PosCurrencyUtil.format(PosSaleItemUtil
//				.getTotalDiscountAmount(mPosItemToEdit)));
		mTxtDiscountAmount.setText(PosCurrencyUtil.format(PosCurrencyUtil
				.roundTo(mPosItemToEdit.getDiscount().getPrice()
						+ mPosItemToEdit.getDiscount().getVariants())));
		
		mLabelCurrencySymbol.setText((mPosItemToEdit.getDiscount()
				.isPercentage()) ? "%" : PosEnvSettings.getInstance()
				.getCurrencySymbol());
		mTxtDiscountAmount.setEditable(mPosItemToEdit.getDiscount().isOverridable());
		
	}
 
	/*
	 * 
	 */
	 private boolean onQtyChanged(){
		 boolean result=true;
		 
			final double newQty=PosNumberUtil.parseDoubleSafely(mTxtQty.getText());
			if (newQty <= 0) {
				PosFormUtil.showErrorMessageBox(PosItemExtSearchForm.this,
						"Qunatity should be greater than 0.");
				mTxtQty.requestFocus();
				mTxtQty.setSelectedAll();
				result=false;
			}else{
				mTxtQty.setText(PosUomUtil.format(newQty,mPosItemToEdit.getUom()));
				mPosItemToEdit.setQuantity(newQty);
				
				PosTaxUtil.calculateTax(mPosItemToEdit);
			}
			return result;
			 
						 
	 }
	 
	 /*
	  * 
	  */
	 private void setEnableControls(){
		 
		if(mPosItemToEdit==null){
			mTxtPrice.setEnabled(false);
			mTxtDiscountAmount.setEnabled(false);
			btnDiscRetrieve.setEnabled(false);
			btnDiscReset.setEnabled(false);
			
		} else if(mPosItemToEdit.isOpen())
			mTxtPrice.setEnabled(true);
		else if (mPosItemToEdit.getDiscount()!=null && 
				 mPosItemToEdit.getDiscount()!=new PosDiscountItemProvider().getNoneDiscount() && 
				 mPosItemToEdit.getDiscount().isOverridable()
				 ){
			mTxtDiscountAmount.setEnabled(true);
		}else{
			btnDiscRetrieve.setEnabled(true);
			btnDiscReset.setEnabled(true);
		}
	}
	 
	 /*
	  * Clear Item Details Controls
	  */
	 private void clearItemDetails(){
		 
		mPosItemToEdit=null;
		mlabelItemName.setText("");
		mTxtQty.setText("");
		mTxtPrice.setText("");
		mTxtDiscount.setText("");
		mTxtDiscountAmount.setText("");
		mTxtRemarks.setText("");
		setEnableControls();  
	}
		/*
		 * 
		 */
	 private boolean applyChanges(){
		 boolean result=true;
		 
			final double newQty=PosNumberUtil.parseDoubleSafely(mTxtQty.getText());
			if (newQty <= 0) {
				PosFormUtil.showErrorMessageBox(PosItemExtSearchForm.this,
						"Qunatity should be greater than 0.");
				mTxtQty.requestFocus();
				mTxtQty.setSelectedAll();
				result=false;
			}else{
				 
				mPosItemToEdit.setQuantity(newQty);
				mPosItemToEdit.setFixedPrice(PosNumberUtil.parseDoubleSafely(mTxtPrice.getText()));
//				mPosItemToEdit.
				PosTaxUtil.calculateTax(mPosItemToEdit);
			}
			return result;
			 
						 
	 }
}
