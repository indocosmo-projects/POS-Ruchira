package com.indocosmo.pos.forms.search;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
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
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import com.indocosmo.pos.common.PosLog;
import com.indocosmo.pos.common.utilities.PosFormUtil;
import com.indocosmo.pos.common.utilities.PosNumberUtil;
import com.indocosmo.pos.common.utilities.PosStringUtil;
import com.indocosmo.pos.forms.components.jtable.NoBorderTableCellSelectionRender;
import com.indocosmo.pos.forms.components.keypads.PosSoftKeyPad.IPosSoftKeypadListner;
import com.indocosmo.pos.forms.components.keypads.PosSoftKeyPadInputBase;
import com.indocosmo.pos.forms.components.keypads.PosSoftKeyPadSingleLineInput;
import com.indocosmo.pos.forms.components.search.IPosSearchableItem;
import com.indocosmo.pos.forms.search.listener.IPosItemExtSearchFormListener;

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
	private static final int TITLE_HEIGHT=30;

	public static final int LAYOUT_HEIGHT = PosSoftKeyPadSingleLineInput.LAYOUT_HEIGHT + TITLE_HEIGHT
			+ SEARCH_RESULT_PANEL_HEIGHT + PANEL_CONTENT_V_GAP * 4;
	public static final int LAYOUT_WIDTH = SEARCH_RESULT_PANEL_WIDTH
			+ PANEL_CONTENT_H_GAP * 2;
	private final static DefaultTableCellRenderer COL_RIGHT_RENDER = new NoBorderTableCellSelectionRender();
	private final static DefaultTableCellRenderer COL_LEFT_RENDER = new NoBorderTableCellSelectionRender();
	private PosSoftKeyPadInputBase mSoftKeyPad;
	private JPanel mResultPanel;
	private JTable mSearchResultTable;
	private TableRowSorter<TableModel> mResultSorter;
	private IPosSearchableItem mSelectedItem = null;
	private IPosSearchableItem[] mActualSearchList;
	private IPosSearchableItem[] mFilteredSearchList; //The original search list which is not filtered.
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

		mActualSearchList=searchList;
		
		COL_RIGHT_RENDER.setHorizontalAlignment(JLabel.RIGHT);
		COL_RIGHT_RENDER.setHorizontalTextPosition(JLabel.LEFT);

//		mActualSearchList =getFilteredItemList(searchList);
		
		setSize(LAYOUT_WIDTH, LAYOUT_HEIGHT);
		setLayout(null);
		mSoftKeyPad = new PosSoftKeyPadSingleLineInput();
		mSoftKeyPad.showActionActrols(false);
		mSoftKeyPad.setListner(softKeypadListener);

		//		PosTextField mTitleLabel=new PosTextField();
		//		mTitleLabel.setText(mWindowTitle);
		mTitleLabel=PosFormUtil.setHeading(mWindowTitle, this.getWidth()-PANEL_CONTENT_H_GAP*2, TITLE_HEIGHT);
		mTitleLabel.setBackground(PosFormUtil.SECTION_HEADING_PANEL_BG_COLOR);
		mTitleLabel.setLocation(PANEL_CONTENT_H_GAP, PANEL_CONTENT_V_GAP+3);
		mTitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		mTitleLabel.setFont(PosFormUtil.getTextFieldBoldFont());
		add(mTitleLabel);

		final int leftResultPanel =PANEL_CONTENT_H_GAP;
		final int topResultPanel =mTitleLabel.getY()+mTitleLabel.getHeight() -3;
		mResultPanel = new JPanel();
		mResultPanel.setSize(SEARCH_RESULT_PANEL_WIDTH,
				SEARCH_RESULT_PANEL_HEIGHT);
		mResultPanel.setLocation(leftResultPanel, topResultPanel);
		//		mResultPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
		mResultPanel.setLayout(new FlowLayout());
		add(mResultPanel);
//		if (mActualSearchList != null && mActualSearchList.length > 0)
//			createResultTable();
//		else
//			createNoItemWarning();

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
		
		mFilteredSearchList =getFilteredItemList(mActualSearchList);
		if(mSearchResultTable==null){
			
			if (mFilteredSearchList != null && mFilteredSearchList.length > 0)
				createResultTable();
			else
				createNoItemWarning();
		};

		DefaultTableModel model=(DefaultTableModel)mSearchResultTable.getModel();

		if (mFilteredSearchList.length > 0) {
			mDisplayedSearchList=(mFilterDuplicate)?getFilteredList():mFilteredSearchList;
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

		for(IPosSearchableItem item:mFilteredSearchList){
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
//		searchTable.setFont(getFont().deriveFont(18f));
		searchTable.setFont(PosFormUtil.getTableCellFont());
		searchTable.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		searchTable.getTableHeader().setReorderingAllowed(false);
		searchTable.getTableHeader().setResizingAllowed(false);
		searchTable.getTableHeader().setFont(PosFormUtil.getTableHeadingFont());
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
		final Class<?> itemClass = mFilteredSearchList[0].getClass();
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
				
				if(!mAllowRowSelection || mSelectedItem==null) return;

				if (mListner != null) {
					if(mListner.onAccepted(PosItemExtSearchForm.this,mSelectedItem))
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

				if(mListner.onAccepted(PosItemExtSearchForm.this,mSelectedItem)){

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

			if(e.getKeyCode()==KeyEvent.VK_ENTER || e.getKeyCode()==KeyEvent.VK_TAB || e.getKeyCode()==KeyEvent.VK_UP || e.getKeyCode()==KeyEvent.VK_DOWN){
				e.consume();
				mSearchResultTable.getSelectionModel().clearSelection();
				mSearchResultTable.changeSelection(0, 0, false, false);
				mSearchResultTable.requestFocus();
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
}
