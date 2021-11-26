package com.indocosmo.pos.forms;

import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;

import com.indocosmo.pos.common.PosEnvSettings;
import com.indocosmo.pos.common.utilities.PosFormUtil;
import com.indocosmo.pos.common.utilities.PosResUtil;
import com.indocosmo.pos.forms.components.buttons.PosButton;
import com.indocosmo.pos.forms.components.buttons.listners.IPosButtonListner;
import com.indocosmo.pos.forms.components.buttons.listners.PosButtonListnerAdapter;
import com.indocosmo.pos.forms.components.objectbrowser.IPosBrowsableItem;
import com.indocosmo.pos.forms.components.objectbrowser.IPosObjectBrowserControl;
import com.indocosmo.pos.forms.components.objectbrowser.PosObjectBrowserItemNormal;
import com.indocosmo.pos.forms.components.objectbrowser.PosObjectBrowserItemWide;
import com.indocosmo.pos.forms.components.objectbrowser.PosObjectBrowserSaleItem;
import com.indocosmo.pos.forms.components.objectbrowser.listners.IPosObjectBrowserItemListner;
import com.indocosmo.pos.forms.listners.IPosObjectBrowserListner;

@SuppressWarnings("serial")
public class PosObjectBrowserForm extends JDialog {
	public enum ItemSize{
		Small,
		Normal,
		Wider,
		SaleItem
	}
//	private static final int FORM_WIDTH=440;
//	private static final int FORM_HEIGHT=340;
	
	private static final int RIGHT_PANEL_BUTTON_WIDTH=90;
	private static final int RIGHT_PANEL_BUTTON_HEIGHT=87;
	private static final int BORDER_WIDTH=2;
	
	private static final int PANEL_CONTENT_V_GAP=PosOrderEntryForm.PANEL_CONTENT_V_GAP;
	private static final int PANEL_CONTENT_H_GAP=PosOrderEntryForm.PANEL_CONTENT_H_GAP;
	
	private static final String IMAGE_BUTTON_SCROLL_UP="dlg_buton_up.png";
	private static final String IMAGE_BUTTON_SCROLL_UP_TOUCH="dlg_buton_up_touch.png";
		
	private static final String IMAGE_BUTTON_SCROLL_DOWN="dlg_buton_down.png";	
	private static final String IMAGE_BUTTON_SCROLL_DOWN_TOUCH="dlg_buton_down_touch.png";
	
	private static final String IMAGE_BUTTON_CANCEL="cancel_button.png";
	private static final String IMAGE_BUTTON_CANCEL_TOUCH="cancel_button_touch.png";
	
	private static final int TITLE_PANEL_HEIGHT=30;
//	private static final int TITLE_PANEL_WIDTH=FORM_WIDTH-PANEL_CONTENT_H_GAP*2;
	private static final Color TITLE_PANEL_BG_COLOR=new Color(78,128,188);
	private static final Color TITLE_PANE_FG_COLOR=Color.WHITE;	
	
	private static final int CONTENT_PANEL_MIN_HEIGHT=RIGHT_PANEL_BUTTON_HEIGHT*3+PANEL_CONTENT_V_GAP*4;
	
	private static int MIN_COLS=1;
	private static int MIN_ROWS=3; 
	
	private int mNextItemIndexToDisplay;
	private int mCurrentPage=0;
	private ItemSize mItemSize;
	private int mNoCols;
	private int mNoRows;
	private int mObjectItemHeight;
	private int mObjectItemWidth;
	private int mObjectContainerHeight;
	private int mObjectContainerWidth;
	private int mScrollPaneHeight;
	private int mScrollPaneWidth;
	private int mFormHeight;
	private int mFormWidth;
	private int mTitlePanelWidth;
	
	private IPosBrowsableItem[] mItemList;
	private ArrayList<IPosObjectBrowserControl> mItemControlList;
	private String mFormTitle;
	private String mFormTitleBg;
	private String mFormTitleFg;
	private PosButton mImageButtonScrollUp=new PosButton();
	private PosButton mImageButtonScrollDown=new PosButton();
	private JLabel mlabelTitle;
	private JPanel mTitlePanel;
	private JPanel mContentPanel;
	private JPanel mItemContainer;		
	private JPanel mContentPane;	
	private IPosObjectBrowserListner mItemListner;
	private PosButton mButtonCancel;
	private int mItemPerPage;
	private boolean autoRegisterKeyStroke=true;
	private boolean cancelled=false;
	

	public PosObjectBrowserForm( ){
		
	}
	public PosObjectBrowserForm(String title,IPosBrowsableItem[] itemList){
		initVars(title,"#FFFFFF","#000000",itemList,ItemSize.Normal,5, 3);
		initControls();
	}
	
	/**
	 * @param string
	 * @param mMoreActionList
	 * @param small
	 * @param b
	 */
	public PosObjectBrowserForm(String title,
			ArrayList<? extends IPosBrowsableItem > itemList, ItemSize itemSize,
			boolean isAutoSize) {
		
		int cols=3;
		int rows=3;
		
		if(isAutoSize){
			
			if(itemList.size()<=3){
				
				cols=1;
			}else if(itemList.size()<=6){
				
				cols=2;
			}
			
		}
		
		IPosBrowsableItem[] itemArrayList=new IPosBrowsableItem[itemList.size()];
		itemList.toArray(itemArrayList);
		
		initVars(title,"#FFFFFF","#000000",itemArrayList,itemSize, cols, rows);
		initControls();
	}
	
	public PosObjectBrowserForm(String title,IPosBrowsableItem[] itemList,ItemSize itemSize,boolean isAutoSize){
		int cols=3;
		int rows=3;
		
		if(isAutoSize){
			
			//TO DO
		}
		
		initVars(title,"#FFFFFF","#000000",itemList,itemSize, cols, rows);
		initControls();
	}
	
	public PosObjectBrowserForm(String title,IPosBrowsableItem[] itemList,ItemSize itemSize){
		initVars(title,"#FFFFFF","#000000",itemList,itemSize, 3, 3);
		initControls();
	}
	
	public PosObjectBrowserForm(String title,IPosBrowsableItem[] itemList,ItemSize itemSize,int cols, int rows){
		initVars(title,"#FFFFFF","#000000",itemList,itemSize, cols, rows);
		initControls();
	}
	
	
	/**
	 * @param title
	 * @param titleFgColor 
	 * @param titleBgColor
	 * @param itemList
	 */
//	public PosObjectBrowserForm(String title, String titleBgColor,
//			String titleFgColor, IPosBrowsableItem[] itemList) {
//		initVars(title,titleBgColor,titleFgColor,itemList,ItemSize.Normal,5, 3);
//		initControls();
//		
//	}
	private void initVars(String title,String titleBgColor,String titleFgColor, IPosBrowsableItem[] itemList,ItemSize itemSize, int cols, int rows){
		mFormTitle=title;
		mFormTitleBg = titleBgColor;
		mFormTitleFg = titleFgColor;
		mItemList=getFilteredItemList(itemList);
		mItemSize=itemSize;
		switch (mItemSize) {
		case Small:
			break;
		case Normal:
			mObjectItemHeight=PosObjectBrowserItemNormal.LAYOUT_HEIGHT;
			mObjectItemWidth=PosObjectBrowserItemNormal.LAYOUT_WIDTH;
			break;
		case Wider:
			mObjectItemHeight=PosObjectBrowserItemWide.LAYOUT_HEIGHT;
			mObjectItemWidth=PosObjectBrowserItemWide.LAYOUT_WIDTH;
			break;
		case SaleItem:
			mObjectItemHeight=PosObjectBrowserSaleItem.LAYOUT_HEIGHT;
			mObjectItemWidth=PosObjectBrowserSaleItem.LAYOUT_WIDTH;
			break;
		default:
			break;
		}
		
		mNoCols=(cols<MIN_COLS)?MIN_COLS:cols;
		mNoRows=(rows<MIN_ROWS)?MIN_ROWS:rows;
		
		mItemPerPage=mNoCols*mNoRows;
		
		mObjectContainerWidth=mObjectItemWidth*mNoCols+PANEL_CONTENT_H_GAP*(mNoCols+1)+mNoCols;
		mObjectContainerHeight=mObjectItemHeight*mNoRows+PANEL_CONTENT_V_GAP*(mNoRows+1)+mNoRows;
		mObjectContainerHeight=(mObjectContainerHeight<CONTENT_PANEL_MIN_HEIGHT)?CONTENT_PANEL_MIN_HEIGHT:mObjectContainerHeight;
		mScrollPaneHeight=mObjectContainerHeight;
		mScrollPaneWidth=RIGHT_PANEL_BUTTON_WIDTH+PANEL_CONTENT_H_GAP*2;
		
		mFormHeight=TITLE_PANEL_HEIGHT+mObjectContainerHeight+PANEL_CONTENT_V_GAP*3;
		mFormWidth=mObjectContainerWidth+mScrollPaneWidth+PANEL_CONTENT_H_GAP*3+BORDER_WIDTH*2;
		
		mTitlePanelWidth=mFormWidth-PANEL_CONTENT_H_GAP*2;
	}
	
	private IPosBrowsableItem[] getFilteredItemList(IPosBrowsableItem[] itemList){
		
		List<IPosBrowsableItem> tmpList=new ArrayList<IPosBrowsableItem>();
		
		for(int index=0; index<itemList.length; index++){
			
			if(itemList[index].isVisibleInUI()){
				
				tmpList.add(itemList[index]);
			}
		}
		
		return tmpList.toArray(new IPosBrowsableItem[tmpList.size()]);
		
	}
	
	private void initControls(){
		
		mContentPane = new JPanel();
		mContentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		mContentPane.setLayout(null);
		
		setContentPane(mContentPane);

		mContentPanel=new JPanel();
		setTitle();
		createItems();
		
		JPanel rightPanel=new JPanel();		
		rightPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
		rightPanel.setLayout(createLayout());
		final int rightPanelleft=mItemContainer.getX()+mItemContainer.getWidth() + PANEL_CONTENT_H_GAP;		
		final int rightPanelTop=TITLE_PANEL_HEIGHT+PANEL_CONTENT_V_GAP*2;		
		rightPanel.setBounds(rightPanelleft,rightPanelTop,  110, mItemContainer.getHeight());			
		createScrollButton(rightPanel);	
		createCancelButton(rightPanel);
		setSrcollButtonStatus();
		setSize(mFormWidth, mFormHeight);
		
	}	
	private void setTitle(){
		mTitlePanel = new JPanel();
		mTitlePanel.setBounds(PANEL_CONTENT_H_GAP, 
				PANEL_CONTENT_V_GAP, 
				mTitlePanelWidth, TITLE_PANEL_HEIGHT);
		mTitlePanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
		mTitlePanel.setBackground(TITLE_PANEL_BG_COLOR);
		mTitlePanel.setLayout(null);		
		add(mTitlePanel);
		
//		mlabelTitle=new JTextField(mFormTitle);
		mlabelTitle=PosFormUtil.setHeading(mFormTitle, mTitlePanel.getWidth(), mTitlePanel.getHeight());
		mlabelTitle.setHorizontalAlignment(SwingConstants.CENTER);		
//		mlabelTitle.setBounds(0, 0, mTitlePanel.getWidth(), mTitlePanel.getHeight());		
//		mlabelTitle.setFont(PosFormUtil.getHeadingFont());
//		setTitleColors();
//		mlabelTitle.setEditable(false);
		mlabelTitle.setFont(PosFormUtil.getTextFieldBoldFont());
		mTitlePanel.add(mlabelTitle);			
	}
	
	private void setTitleColors(){
		final String bgColor=getColorCode(mFormTitleBg);
		final String fgColor=getColorCode(mFormTitleFg);
		mlabelTitle.setForeground(new Color(Integer.parseInt(fgColor,16)));
		mlabelTitle.setBackground(new Color(Integer.parseInt(bgColor,16)));
	}
	
	
	
	private String getColorCode(String color){
		return  (color.contains("#")?color.substring(1):color).trim();
	}
	
	private void createItems(){		
		
		boolean addKeyStroke=((autoRegisterKeyStroke && mItemPerPage<=12) || !autoRegisterKeyStroke);
		
		mNextItemIndexToDisplay=0;
		mItemContainer=new JPanel();
		mItemContainer.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
		mItemContainer.setLayout(createLayout());

		final int top=TITLE_PANEL_HEIGHT+PANEL_CONTENT_V_GAP*2;
		
		mItemContainer.setBounds(PANEL_CONTENT_H_GAP,top,  mObjectContainerWidth, mObjectContainerHeight);	
		mItemControlList=new ArrayList<IPosObjectBrowserControl>();
		mCurrentPage++;
		for(int index=0; index<mItemPerPage; index++){
			
			IPosObjectBrowserControl itemControl=null;
			switch (mItemSize) {
			case Small:
				break;
			case Normal:
				itemControl=new PosObjectBrowserItemNormal();
				break;
			case Wider:
				itemControl=new PosObjectBrowserItemWide();
				break;
			case SaleItem:
				itemControl=new PosObjectBrowserSaleItem();
				addKeyStroke=false;
				break;
			default:
				break;
			}
			PosButton btnControl=(PosButton)itemControl;
			itemControl.setListner(itemSelectListner);
			btnControl.setAutoMnemonicEnabled(false);
			btnControl.setFont(PosFormUtil.getButtonFont());
			if(mNextItemIndexToDisplay<mItemList.length){
				itemControl.setItem(mItemList[mNextItemIndexToDisplay]);
				mItemControlList.add(itemControl);
				if(addKeyStroke)
					addKeyStroke(btnControl,mItemList[mNextItemIndexToDisplay],index);
				mNextItemIndexToDisplay++;
				
			}
			else
				((Component)itemControl).setVisible(false);
			
			mItemContainer.add((Component)itemControl);
		}
		add(mItemContainer);
	}
	
	/**
	 * @param btnControl
	 * @param item
	 * @param index
	 */
	private void addKeyStroke(PosButton btnControl, IPosBrowsableItem item,int index){
	
		KeyStroke stroke=null;
		if(autoRegisterKeyStroke)
			stroke=KeyStroke.getKeyStroke(112+index,0);
		else if(item.getKeyStroke()!=null)
			stroke=item.getKeyStroke();
		btnControl.registerKeyStroke(stroke);
		if(PosEnvSettings.getInstance().getUISetting().showKeyStrokeCharOnButtons())
			btnControl.setText( "(" + PosFormUtil.getFunctionKeyChar(stroke.getKeyCode()) + ") " + btnControl.getText());
	}
	
	private void createCancelButton(JPanel rightPanel)
	{	
		mButtonCancel=new PosButton();
		mButtonCancel.setCancel(true);
		mButtonCancel.setText("Cancel");
		mButtonCancel.setImage(PosResUtil.getImageIconFromResource(IMAGE_BUTTON_CANCEL));
		mButtonCancel.setTouchedImage(PosResUtil.getImageIconFromResource(IMAGE_BUTTON_CANCEL_TOUCH));	
		mButtonCancel.setHorizontalAlignment(SwingConstants.CENTER);		
		mButtonCancel.setBounds(0, 0, RIGHT_PANEL_BUTTON_WIDTH,RIGHT_PANEL_BUTTON_HEIGHT);
		mButtonCancel.setOnClickListner(imgCancelButtonListner);
		rightPanel.add(mButtonCancel);
		add(rightPanel);
	}
	
	public void setCancel(Boolean canCancel){
		mButtonCancel.setEnabled(canCancel);
	}
	
	private void createScrollButton(JPanel rightPanel)
	{			
		mImageButtonScrollUp.setImage(PosResUtil.getImageIconFromResource(IMAGE_BUTTON_SCROLL_UP));
		mImageButtonScrollUp.registerKeyStroke(KeyEvent.VK_PAGE_UP);
		mImageButtonScrollUp.setTouchedImage(PosResUtil.getImageIconFromResource(IMAGE_BUTTON_SCROLL_UP_TOUCH));		
		mImageButtonScrollUp.setHorizontalAlignment(SwingConstants.CENTER);		
		mImageButtonScrollUp.setBounds(0, 0, RIGHT_PANEL_BUTTON_WIDTH,RIGHT_PANEL_BUTTON_HEIGHT);
		mImageButtonScrollUp.setOnClickListner(scrollUpButtonListner);		
		rightPanel.add(mImageButtonScrollUp);
		add(rightPanel);		
		
		mImageButtonScrollDown.setImage(PosResUtil.getImageIconFromResource(IMAGE_BUTTON_SCROLL_DOWN));
		mImageButtonScrollDown.registerKeyStroke(KeyEvent.VK_PAGE_DOWN);
		mImageButtonScrollDown.setTouchedImage(PosResUtil.getImageIconFromResource(IMAGE_BUTTON_SCROLL_DOWN_TOUCH));		
		mImageButtonScrollDown.setHorizontalAlignment(SwingConstants.CENTER);		
		mImageButtonScrollDown.setBounds(0, 0, RIGHT_PANEL_BUTTON_WIDTH,RIGHT_PANEL_BUTTON_HEIGHT);
		mImageButtonScrollDown.setOnClickListner(scrollDownButtonListner);
		rightPanel.add(mImageButtonScrollDown);
		add(rightPanel);
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
	
	private void displayCurPageItems(){
		int itemIndex=mCurrentPage*mItemPerPage-mItemPerPage;
		IPosObjectBrowserControl itemControl=null;
		for(int controlIndex=0; controlIndex<mItemPerPage; controlIndex++){
			itemControl=mItemControlList.get(controlIndex);
			if(itemIndex<mItemList.length){
				itemControl.setItem(mItemList[itemIndex]);
				((Component)itemControl).setVisible(true);
				itemIndex++;
			}
			else
				((Component)itemControl).setVisible(false);
		}
	}
	
	private void setSrcollButtonStatus(){
		mImageButtonScrollDown.setEnabled(true);
		mImageButtonScrollUp.setEnabled(true);
		if(mCurrentPage*mItemPerPage>=mItemList.length) mImageButtonScrollDown.setEnabled(false);
		if(mCurrentPage<=1) mImageButtonScrollUp.setEnabled(false);
	}
	
	private  IPosButtonListner scrollUpButtonListner= new PosButtonListnerAdapter() {
		@Override
		public void onClicked(PosButton button) {
			previousButtonClick();
		}
	};	
	
	private  IPosButtonListner scrollDownButtonListner= new PosButtonListnerAdapter() {
		@Override
		public void onClicked(PosButton button) {			
			nextButtonClick();
		}
	};
	
	private  IPosButtonListner imgCancelButtonListner= new PosButtonListnerAdapter() {
		@Override
		public void onClicked(PosButton button) {
			cancelled=true;
			if(mItemListner!=null)
				mItemListner.onCancel();
			setVisible(false);
			dispose();			
		}
	};
	
	private FlowLayout createLayout(){
		FlowLayout flowLayout=new FlowLayout();
		flowLayout.setVgap(PANEL_CONTENT_V_GAP);
		flowLayout.setHgap(PANEL_CONTENT_H_GAP);
		flowLayout.setAlignment(FlowLayout.LEFT);
		return flowLayout;
	}
	
	private IPosObjectBrowserItemListner itemSelectListner=new IPosObjectBrowserItemListner() {

		@Override
		public void onSelected(IPosBrowsableItem item) {
			setVisible(false);
			if(mItemListner!=null)
				mItemListner.onItemSelected(item);
			dispose();
		}
	};
	
	public void setListner(
			IPosObjectBrowserListner listner) {
		this.mItemListner = listner;
	}
	/**
	 * @param foreground
	 * @param background
	 */
	public void setTitleColors(String foreground, String background) {
		mFormTitleBg=background;
		mFormTitleFg=foreground;
		setTitleColors();
	}
	/**
	 * @return the autoRegisterKeyStroke
	 */
	public boolean isAutoRegisterKeyStroke() {
		return autoRegisterKeyStroke;
	}
	/**
	 * @param autoRegisterKeyStroke the autoRegisterKeyStroke to set
	 */
	public void setAutoRegisterKeyStroke(boolean autoRegisterKeyStroke) {
		this.autoRegisterKeyStroke = autoRegisterKeyStroke;
	}
	
	/**
	 * @return the cancelled
	 */
	public boolean isCancelled() {
		return cancelled;
	}
	/**
	 * @param cancelled the cancelled to set
	 */
	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}
}
