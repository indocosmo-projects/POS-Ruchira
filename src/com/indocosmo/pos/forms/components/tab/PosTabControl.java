package com.indocosmo.pos.forms.components.tab;

import java.awt.FlowLayout;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.EtchedBorder;

import com.indocosmo.pos.common.utilities.PosResUtil;
import com.indocosmo.pos.forms.components.buttons.PosSelectButton;
import com.indocosmo.pos.forms.components.buttons.listners.IPosSelectButtonListner;
import com.indocosmo.pos.forms.components.tab.listner.IPosTabControlListner;

@SuppressWarnings("serial")
public class PosTabControl extends JPanel {
	
	private static final String IMAGE_BUTTON_NORMAL="tab_button_normal.png";
	private static final String IMAGE_BUTTON_TOUCHED="tab_button_touch.png";
	private static final String IMAGE_BUTTON_SELECTED="tab_button_selected.png";
	
	public static final int BUTTON_WIDTH=110;
	public static final int BUTTON_HEIGHT=52;	
	
	private static ImageIcon mImageIconNormal=null;
	private static ImageIcon mImageIconTouched=null;
	private static ImageIcon mImageIconSelected=null;
	
	private static final int TAB_BUTTON_GAP=3;
	private static final int TAB_COTIANER_H_GAP=3;
	private static final int TAB_COTIANER_V_GAP=3;
	
	private ArrayList<PosSelectButton> mTabButtons;
	private ArrayList<PosTab> mTabPanels;
	private int mNumberOfTabs;
	private int mHeight;
	private int mWidth;
	private int mSelectedTabIndex=-1;
	private JPanel mTabContainer;
	private JPanel mTabButtonContainer;
	
	public PosTabControl(int width, int height){
		mWidth=width;
		mHeight=height;
		initComponent();
	}
	
	public PosTabControl(ArrayList<PosTab> tabs, int width, int height, IPosTabControlListner listner){
		mTabControlListner=listner;
		mTabPanels=tabs;
		mWidth=width;
		mHeight=height;
//		setBackground(Color.RED);
		initComponent();
		createTabs();
	}
	
	public void setTabs(ArrayList<PosTab> tabs) {
		mTabPanels=tabs;
		createTabs();
	}
	
	public ArrayList<PosTab> getTabs(){
		return mTabPanels;
	}
	
	public void addTab(PosTab tab) {
		if(mTabPanels==null)
			mTabPanels=new ArrayList<PosTab>();
		mTabPanels.add(tab);
		createTabs();
	}
	
	public void setSelectedTab(final PosTab tab){
		setSelectedTab(mTabPanels.indexOf(tab));
	}
	
	public void setSelectedTab(final int tabIndex){
		
		final int index=((tabIndex>=mTabButtons.size() || tabIndex<=0)?0:tabIndex);
		mTabButtons.get(index).setSelected(true);
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				mTabPanels.get(index).onGotFocus();
			}
		});
	}
	
	public void setSelectedTab(final String tabCaption){
		int tabIndex=-1;
		for(int index=0; index<mTabButtons.size();index++){
			if(mTabButtons.get(index).getText()==tabCaption){
				tabIndex=index;
				break;
			}
		}
		if(tabIndex==-1)return;
		setSelectedTab(tabIndex);
	}
	
	
	
	public void setTabCaption(final int tabIndex,String caption){
	if (tabIndex>0 && tabIndex<mTabButtons.size())
		mTabButtons.get(tabIndex).setText(caption);
		
	}
	private void initComponent(){
		
		final int minWidth=(BUTTON_WIDTH+TAB_BUTTON_GAP)*mNumberOfTabs;
		mWidth=(mWidth<minWidth)?minWidth:mWidth;
		setSize(mWidth, mHeight);
		setLayout(null);
		
		mTabButtonContainer=new JPanel();
		mTabButtonContainer.setLayout(null);
		mTabButtonContainer.setBounds(0, 0, mWidth, BUTTON_HEIGHT);
		mTabButtonContainer.setLayout(new FlowLayout(FlowLayout.LEFT, 2, 0));
		add(mTabButtonContainer);
		
		mTabContainer=new JPanel();
		mTabContainer.setLayout(null);
		mTabContainer.setBounds(0, BUTTON_HEIGHT, mWidth, mHeight-BUTTON_HEIGHT);
		mTabContainer.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
		mTabContainer.setOpaque(false);
		add(mTabContainer);

		mTabButtons=new ArrayList<PosSelectButton>();
		loadItemImages();
	}
	
	private void createTabs(){
		
		mNumberOfTabs=mTabPanels.size();
		
		for(int index=0; index<mNumberOfTabs; index++){
			final int left=index*(BUTTON_WIDTH+TAB_BUTTON_GAP);
			final int top=0;
			PosSelectButton tabButton=new PosSelectButton();
			tabButton.setBounds(left, top,BUTTON_WIDTH,BUTTON_HEIGHT);
			tabButton.setText(mTabPanels.get(index).getTabCaption());
			if(mTabPanels.get(index).getMnemonicChar()!=null)
				tabButton.setMnemonic(mTabPanels.get(index).getMnemonicChar());
			tabButton.setTag(index);
			tabButton.setOnSelectListner(mButonListner);
			tabButton.setImage(mImageIconNormal);
			tabButton.setTouchedImage(mImageIconTouched);
			tabButton.setSelectedImage(mImageIconSelected);
			mTabButtonContainer.add(tabButton);
			mTabButtons.add(tabButton);
			JPanel tabPanel=(JPanel)mTabPanels.get(index);
			tabPanel.setLocation(TAB_COTIANER_H_GAP, TAB_COTIANER_V_GAP);
			tabPanel.setSize(mTabContainer.getWidth()-TAB_COTIANER_H_GAP*2, mTabContainer.getHeight()-TAB_COTIANER_V_GAP*2);
			tabPanel.setVisible(false);
			mTabContainer.add(tabPanel);

		}
		invalidate();
		repaint();
		
		if(mTabButtons!=null && mTabButtons.size()>0)
			mTabButtons.get(0).setSelected(true);
	}
	
	private IPosSelectButtonListner mButonListner=new IPosSelectButtonListner() {
		@Override
		public void onSelected(PosSelectButton button) {
			final int tabIndex=(int) button.getTag();
			if(mSelectedTabIndex>=0)
				setTabs(mSelectedTabIndex,false);
			mSelectedTabIndex=tabIndex;
			setTabs(mSelectedTabIndex,true);
			if(mTabControlListner!=null)
				mTabControlListner.onTabSelected(mSelectedTabIndex);
		}
	};
	
	private PosTab mSelectedTab;
	
	private void setTabs(int index, boolean isSelected){
		
		mTabButtons.get(index).setSelected(isSelected);
		final PosTab tab=(PosTab)mTabPanels.get(index);  
		tab.setVisible(isSelected);
		if(isSelected){
			tab.onGotFocus();
			mSelectedTab=tab;
		}
		
	}
	
	public PosTab getSelectedTab(){
		return mSelectedTab;
	}
	
	private void loadItemImages(){
		if(mImageIconNormal==null)
			mImageIconNormal=PosResUtil.getImageIconFromResource(IMAGE_BUTTON_NORMAL);
		if(mImageIconTouched==null)
			mImageIconTouched=PosResUtil.getImageIconFromResource(IMAGE_BUTTON_TOUCHED);
		if(mImageIconSelected==null)
			mImageIconSelected=PosResUtil.getImageIconFromResource(IMAGE_BUTTON_SELECTED);
	}
	
	private IPosTabControlListner mTabControlListner;
	
	public void setListner(IPosTabControlListner listner){
		mTabControlListner=listner;
	}
	
	public void setTabVisibility(PosTab tab,boolean visible){
		
		if(mTabPanels.contains(tab)){
			final int index= mTabPanels.indexOf(tab);
			setTabVisibility(index,visible);
		}
	}
	
	public void setTabVisibility(int index,boolean visible){
			mTabButtons.get(index).setVisible(visible);
//			mTabPanels.get(index).setVisible(visible);
	}
}
