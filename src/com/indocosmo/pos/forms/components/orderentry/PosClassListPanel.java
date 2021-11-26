/**
 * 
 */
package com.indocosmo.pos.forms.components.orderentry;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import com.indocosmo.pos.common.PosEnvSettings;
import com.indocosmo.pos.common.beans.settings.ui.orderentry.BeanUIClassItemListPanelSetting;
import com.indocosmo.pos.common.utilities.PosFormUtil;
import com.indocosmo.pos.common.utilities.PosResUtil;
import com.indocosmo.pos.data.beans.BeanItemClassBase;
import com.indocosmo.pos.forms.PosOrderEntryForm;
import com.indocosmo.pos.forms.components.buttons.PosButton;
import com.indocosmo.pos.forms.components.buttons.PosButton.ButtonStyle;
import com.indocosmo.pos.forms.components.buttons.PosSelectButton;
import com.indocosmo.pos.forms.components.buttons.listners.IPosButtonListner;
import com.indocosmo.pos.forms.components.buttons.listners.IPosSelectButtonListner;
import com.indocosmo.pos.forms.components.buttons.listners.PosButtonListnerAdapter;
import com.indocosmo.pos.forms.components.itemcontrols.PosClassItemControl;
import com.indocosmo.pos.forms.components.orderentry.listners.IPosClassItemListListner;

/**
 * @author jojesh
 *
 */
@SuppressWarnings("serial")
public final class PosClassListPanel extends JPanel{
	
	private enum ScrollButtonsTypes{
		UP,
		DOWN
	}

	public static final int PANEL_CONTENT_H_GAP=4;
	private static final int SCROLL_PANEL_H_GAP=4;
	private static final int SCROLL_PANEL_V_GAP=2;
	private static final int CLASS_PANEL_H_GAP=4;
	private static final int CLASS_PANEL_V_GAP=2;
	
	private static final int PANEL_WIDTH=PosOrderEntryForm.RIGHT_PANEL_WIDTH-PosOrderEntryForm.PANEL_V_GAP*2;
	
	private static final int SCROLL_BTN_HEIGHT=50;
	private static final int SCROLL_BTN_WIDTH=PosClassItemControl.CLASS_ITEM_BTN_WIDTH;
	
	private static final String SCROLL_UP_IMAGE_NORMAL="class_item_scroll_up.png";
	private static final String SCROLL_UP_IMAGE_TOUCH="class_item_scroll_up_touch.png";
	private static final String SCROLL_DOWN_IMAGE_NORMAL="class_item_scroll_down.png";
	private static final String SCROLL_DOWN_IMAGE_TOUCH="class_item_scroll_down_touch.png";
	
	public static final Color PANEL_BG_COLOR=PosOrderEntryForm.PANEL_BG_COLOR;
	
	private int mHeight;
	private Boolean mIsMainClassPanel=true; 
	private ArrayList<BeanItemClassBase> mClassList;
	private IPosClassItemListListner mClassItemListListner ;
	private PosButton mScrollUpButton,mScrollDownButton;
	private static ImageIcon mImageUpButtonNormal,mImageUpButtonTouch,mImageDownButtonNormal,mImageDownButtonTouch;
	private JPanel mClassItemPanel;
	private ArrayList<PosClassItemControl> mClassItemControlList;
	private PosClassItemControl mSelectedClassItemControl;
	private BeanUIClassItemListPanelSetting classItemListPanelSettings;
	private int mCurPage=0;
	private int mItermsPerPage=0;
	
	public PosClassListPanel(Boolean isMainClassPanel,int height) {
		mHeight=height;
		classItemListPanelSettings=PosEnvSettings.getInstance().getUISetting().getOrderEntryUISettings().getClassItemListPanelSetting();
		mIsMainClassPanel=isMainClassPanel;
		initComponent();
	}
	
	public void setClassList(ArrayList<BeanItemClassBase> classList) {
		mCurPage=0;
		this.mClassList = classList;
		resetSelection();
		bindControls();
	}
	
	private void bindControls(){
		int startIndex=mCurPage*mItermsPerPage;
		for(int index=0; index<mClassItemControlList.size(); index++){
			PosClassItemControl posClassItemControl=mClassItemControlList.get(index);
			if(mClassList!=null && mClassList.size()>0 && startIndex<mClassList.size()){
				posClassItemControl.setClassItem(mClassList.get(startIndex++));
			}
			else
				posClassItemControl.setClassItem(null);
		}
		setScrollButtonStatus();
		resetSelection();
		mClassItemControlList.get(0).setSelected(true);
	}
	
	private void setScrollButtonStatus(){
		final int totalDisplayeItems=(mCurPage+1)*mItermsPerPage;
		mScrollDownButton.setEnabled(!(totalDisplayeItems>=mClassList.size()));
		mScrollUpButton.setEnabled(!(totalDisplayeItems<=mItermsPerPage));
		
	}
 
	
	private void initComponent() {
			int width=(!classItemListPanelSettings.showMainClassPanel())?
				PANEL_WIDTH:
					PANEL_WIDTH/2;
		setLayout(null);
		setPreferredSize(new Dimension(width, mHeight));
		setBounds(0, 0, width, mHeight);
		createClassListPanel();
		createScrollButtons();
	}
	
	private int getScrollButtonPanelHeight(){
		
		final int height=(!classItemListPanelSettings.showMainClassPanel())?
				SCROLL_BTN_HEIGHT+SCROLL_PANEL_V_GAP*2:
				SCROLL_BTN_HEIGHT*2+SCROLL_PANEL_V_GAP*3;
		return height;
	}
	
	private void createClassListPanel(){
		int left=0;
		int top=0;
		
		final int height=getHeight()-getScrollButtonPanelHeight();
		final int width=getWidth();
		
		mClassItemPanel=new JPanel();
		mClassItemPanel.setLayout(createLayout());
		mClassItemPanel.setPreferredSize(new Dimension(width, height));
		mClassItemPanel.setBounds(left, top, width, height);
		mClassItemPanel.setOpaque(false);
		createClassItemButtonlist();
		add(mClassItemPanel);
	}
	
	private FlowLayout createLayout(){
		FlowLayout flowLayout=new FlowLayout();
		flowLayout.setVgap(CLASS_PANEL_V_GAP);
		flowLayout.setHgap(CLASS_PANEL_H_GAP);
		flowLayout.setAlignment(FlowLayout.LEFT);
		return flowLayout;
	}
	
	private void createClassItemButtonlist(){
		mClassItemControlList=new ArrayList<PosClassItemControl>();
		final int noRowButtons=(int)mClassItemPanel.getHeight()/(PosClassItemControl.CLASS_ITEM_BTN_HEIGHT+CLASS_PANEL_V_GAP);
		final int noColButtons=(int)mClassItemPanel.getWidth()/(PosClassItemControl.CLASS_ITEM_BTN_WIDTH+CLASS_PANEL_H_GAP);
		mItermsPerPage=noRowButtons*noColButtons;
		for(int index=0;index<mItermsPerPage;index++){
			PosClassItemControl button=createClassItemButton();
			button.setButtonStyle(ButtonStyle.COLORED);
			mClassItemControlList.add(button);
			mClassItemPanel.add(button);
		}
	}
	
	private PosClassItemControl createClassItemButton(){
		PosClassItemControl button=new PosClassItemControl(mIsMainClassPanel);
		button.setOnSelectListner(posClassItemSelectedListner);
		return button;
	}
	 
	private void createScrollButtons(){
		int top=mClassItemPanel.getY()+mClassItemPanel.getHeight()+1;
		int height=SCROLL_BTN_HEIGHT;
		loadImages();
		
		JPanel scrollButtonPanel =new JPanel();
		scrollButtonPanel.setLayout(new FlowLayout(FlowLayout.CENTER,SCROLL_PANEL_H_GAP,SCROLL_PANEL_V_GAP));
		scrollButtonPanel.setBounds(0, top, getWidth(), getScrollButtonPanelHeight());
		scrollButtonPanel.setOpaque(false);
		add(scrollButtonPanel);
		
		mScrollUpButton=new PosButton();
		mScrollUpButton.registerKeyStroke(KeyEvent.VK_UP,KeyEvent.CTRL_DOWN_MASK);
		mScrollUpButton.setButtonStyle(ButtonStyle.BOTH);
		mScrollUpButton.setBackgroundColor(PosFormUtil.SCROLL_BUTTON_BG_COLOR);
		mScrollUpButton.setPreferredSize(new Dimension(SCROLL_BTN_WIDTH, SCROLL_BTN_HEIGHT));
		mScrollUpButton.setImage(mImageUpButtonNormal);
		mScrollUpButton.setTag(ScrollButtonsTypes.UP);
		mScrollUpButton.setOnClickListner(scrollButtonClickListner);
		scrollButtonPanel.add(mScrollUpButton);
		
		top=getHeight()-height-PosOrderEntryForm.PANEL_CONTENT_V_GAP;
		mScrollDownButton=new PosButton();
		mScrollDownButton.registerKeyStroke(KeyEvent.VK_DOWN,KeyEvent.CTRL_DOWN_MASK);
		mScrollDownButton.setButtonStyle(ButtonStyle.BOTH);
		mScrollDownButton.setBackgroundColor(PosFormUtil.SCROLL_BUTTON_BG_COLOR);
		mScrollDownButton.setPreferredSize(new Dimension(SCROLL_BTN_WIDTH, SCROLL_BTN_HEIGHT));
		mScrollDownButton.setImage(mImageDownButtonNormal);
		mScrollDownButton.setTag(ScrollButtonsTypes.DOWN);
		mScrollDownButton.setOnClickListner(scrollButtonClickListner);
		scrollButtonPanel.add(mScrollDownButton);
	}
	
	private IPosButtonListner scrollButtonClickListner =new PosButtonListnerAdapter() {
		@Override
		public void onClicked(PosButton button) {
			PosButton scrollButton=(PosButton)button;
			ScrollButtonsTypes tag=(ScrollButtonsTypes)scrollButton.getTag();
			switch(tag){
				case UP:
					mCurPage--;
					break;
				case DOWN:
					mCurPage++;
					break;
			}
			bindControls();
		}
	};
	
	private void loadImages(){
		if(mImageDownButtonNormal==null)
			mImageDownButtonNormal=PosResUtil.getImageIconFromResource(SCROLL_DOWN_IMAGE_NORMAL);
		if(mImageDownButtonTouch==null)
			mImageDownButtonTouch=PosResUtil.getImageIconFromResource(SCROLL_DOWN_IMAGE_TOUCH);
		if(mImageUpButtonNormal==null)
			mImageUpButtonNormal=PosResUtil.getImageIconFromResource(SCROLL_UP_IMAGE_NORMAL);
		if(mImageUpButtonTouch==null)
			mImageUpButtonTouch=PosResUtil.getImageIconFromResource(SCROLL_UP_IMAGE_TOUCH);
	}
	
	public void setClassItemClickListner(
			IPosClassItemListListner classItemListListner) {
		this.mClassItemListListner = classItemListListner;
	}
	
	public void resetSelection(){
		if(mSelectedClassItemControl!=null)
			mSelectedClassItemControl.setSelected(false);
		mSelectedClassItemControl=null;
	}
	
	private IPosSelectButtonListner posClassItemSelectedListner=new IPosSelectButtonListner() {
		@Override
		public void onSelected(PosSelectButton button) {
			PosClassItemControl senderButton=(PosClassItemControl)button;
			if(mSelectedClassItemControl==senderButton) return;
			if(mSelectedClassItemControl!=null)
				mSelectedClassItemControl.setSelected(false);
			mSelectedClassItemControl=senderButton;
			if(mClassItemListListner!=null)
				mClassItemListListner.onClassItemSelected(mSelectedClassItemControl.getClassItem());
		};
	}; 

}
