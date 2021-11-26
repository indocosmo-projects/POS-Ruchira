/**
 * 
 */
package com.indocosmo.pos.forms;

import java.awt.FlowLayout;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;

import com.indocosmo.pos.common.utilities.PosResUtil;
import com.indocosmo.pos.data.beans.BeanCoupon;
import com.indocosmo.pos.data.providers.shopdb.PosCouponItemProvider;
import com.indocosmo.pos.forms.components.buttons.PosButton;
import com.indocosmo.pos.forms.components.buttons.listners.IPosButtonListner;
import com.indocosmo.pos.forms.components.buttons.listners.PosButtonListnerAdapter;
import com.indocosmo.pos.forms.components.itemcontrols.PosCouponItemControl;
import com.indocosmo.pos.forms.components.itemcontrols.listners.IPosCouponItemListner;
import com.indocosmo.pos.forms.listners.IPosCouponBrowseFormListner;

/**
 * @author jojesh
 *
 */
@SuppressWarnings("serial")
public final class PosCouponBrowseForm extends JDialog {
	
	private static final int FORM_WIDTH=440;
	private static final int FORM_HEIGHT=340;
	
	private static final int IMAGE_RIGHT_PANEL_BUTTON_WIDTH=90;
	private static final int IMAGE_RIGHT_PANEL_BUTTON_HEIGHT=87;
	private static final int LABEL_TITLE_HEIGHT=20;

	private static final int PANEL_CONTENT_V_GAP=PosOrderEntryForm.PANEL_CONTENT_V_GAP;
	private static final int PANEL_CONTENT_H_GAP=PosOrderEntryForm.PANEL_CONTENT_H_GAP;
	
	private static final String IMAGE_BUTTON_SCROLL_UP="dlg_buton_up.png";
	private static final String IMAGE_BUTTON_SCROLL_UP_TOUCH="dlg_buton_up_touch.png";
		
	private static final String IMAGE_BUTTON_SCROLL_DOWN="dlg_buton_down.png";	
	private static final String IMAGE_BUTTON_SCROLL_DOWN_TOUCH="dlg_buton_down_touch.png";
	
	private static final String IMAGE_BUTTON_CANCEL="cancel_button.png";
	private static final String IMAGE_BUTTON_CANCEL_TOUCH="cancel_button_touch.png";

	
	private int mNextItemIndexToDisplay;
	private int mCurrentPage=0;
	
	private ArrayList<BeanCoupon> mPosCouponBrowseItemList;
	private ArrayList<PosCouponItemControl> mCouponItemControlList;
	
	private PosButton mImageButtonScrollUp=new PosButton();
	private PosButton mImageButtonScrollDown=new PosButton();
	
	private JPanel mCouponBrowseContainer;		
	private JPanel mContentPane;	
	private IPosCouponBrowseFormListner mPosCouponFormListner;
	PosButton mButtonCancel;
	
	public PosCouponBrowseForm(){
		initControls();
		loadCoupon();
	}
	
	private void initControls(){
//		Dimension dim =PosUtil.getScreenSize();
//		int left = (dim.width-FORM_WIDTH)/2;
//		int top = (dim.height-FORM_HEIGHT)/2;
//		setUndecorated(true);
//		setAlwaysOnTop(true);		
//		setBounds(left,top,FORM_WIDTH, FORM_HEIGHT);
		setSize(FORM_WIDTH, FORM_HEIGHT);
		mContentPane = new JPanel();
		mContentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		mContentPane.setLayout(null);		
		setContentPane(mContentPane);
		setTitle();
		loadCouponItems();
		createCouponItems();
		
		JPanel rightPanel=new JPanel();		
		rightPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
		rightPanel.setLayout(createLayout());
		final int rightPanelleft=mCouponBrowseContainer.getX()+mCouponBrowseContainer.getWidth() + PANEL_CONTENT_H_GAP;		
		final int rightPanelTop=LABEL_TITLE_HEIGHT+PANEL_CONTENT_V_GAP*2;		
		rightPanel.setBounds(rightPanelleft,rightPanelTop,  110, mCouponBrowseContainer.getHeight());			
		createScrollButton(rightPanel);	
		createCancelButton(rightPanel);
		setSrcollButtonStatus();
		
	}	
	private void setTitle(){
		JLabel labelTitle=new JLabel();		
		labelTitle.setText("Coupon Browse Items");
		labelTitle.setHorizontalAlignment(SwingConstants.CENTER);		
		labelTitle.setBounds(PANEL_CONTENT_H_GAP, PANEL_CONTENT_V_GAP, FORM_WIDTH, LABEL_TITLE_HEIGHT);		
		labelTitle.setFont(new Font("Dialog", Font.BOLD, 18));		
		add(labelTitle);		
		}
	
	private void loadCouponItems(){
		PosCouponItemProvider couponItemProvider=new PosCouponItemProvider();
		mPosCouponBrowseItemList=couponItemProvider.getCouponBrowseItemList();
//		mPosCouponBrowseItemList.add(0,new PosCoupon("NONE", "None"));
	}
	
	private void createCouponItems(){		
		mNextItemIndexToDisplay=0;
		mCouponBrowseContainer=new JPanel();
		mCouponBrowseContainer.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
		mCouponBrowseContainer.setLayout(createLayout());
		
		final int width=PosCouponItemControl.BUTTON_WIDTH*3+PANEL_CONTENT_H_GAP*4;
		final int height=PosCouponItemControl.BUTTON_HEIGHT*3+PANEL_CONTENT_V_GAP*4;
		final int top=LABEL_TITLE_HEIGHT+PANEL_CONTENT_V_GAP*2;
		
		mCouponBrowseContainer.setBounds(PANEL_CONTENT_H_GAP,top,  width, height);	
		mCouponItemControlList=new ArrayList<PosCouponItemControl>();
		mCurrentPage++;
		for(int index=0; index<9; index++){
			PosCouponItemControl itemControl=new PosCouponItemControl();
			itemControl.setItemSelectedListner(itemSelectListner);
			if(mNextItemIndexToDisplay<mPosCouponBrowseItemList.size()){
				itemControl.setCouponBrowseItem(mPosCouponBrowseItemList.get(mNextItemIndexToDisplay));
				
				mNextItemIndexToDisplay++;
				mCouponItemControlList.add(itemControl);
			}
			else
				itemControl.setVisible(false);
			mCouponBrowseContainer.add(itemControl);
		}
		add(mCouponBrowseContainer);
	}
	
	private void createCancelButton(JPanel rightPanel)
	{	
		mButtonCancel=new PosButton();		
		mButtonCancel.setText("Cancel");
		mButtonCancel.setImage(PosResUtil.getImageIconFromResource(IMAGE_BUTTON_CANCEL));
		mButtonCancel.setTouchedImage(PosResUtil.getImageIconFromResource(IMAGE_BUTTON_CANCEL_TOUCH));	
		mButtonCancel.setHorizontalAlignment(SwingConstants.CENTER);		
		mButtonCancel.setBounds(0, 0, IMAGE_RIGHT_PANEL_BUTTON_WIDTH,IMAGE_RIGHT_PANEL_BUTTON_HEIGHT);
		mButtonCancel.setOnClickListner(imgCancelButtonListner);
		rightPanel.add(mButtonCancel);
		add(rightPanel);
	}
	
	public void setCancel(Boolean canCancel){
		mButtonCancel.setEnabled(canCancel);
	}
	
	private void createScrollButton(JPanel rightPanel)
	{			
		mImageButtonScrollUp.setText("");
		mImageButtonScrollUp.setImage(PosResUtil.getImageIconFromResource(IMAGE_BUTTON_SCROLL_UP));
		mImageButtonScrollUp.setTouchedImage(PosResUtil.getImageIconFromResource(IMAGE_BUTTON_SCROLL_UP_TOUCH));		
		mImageButtonScrollUp.setHorizontalAlignment(SwingConstants.CENTER);		
		mImageButtonScrollUp.setBounds(0, 0, IMAGE_RIGHT_PANEL_BUTTON_WIDTH,IMAGE_RIGHT_PANEL_BUTTON_HEIGHT);
		mImageButtonScrollUp.setOnClickListner(imgScrollUpButtonListner);		
		rightPanel.add(mImageButtonScrollUp);
		add(rightPanel);		
		
		mImageButtonScrollDown.setText("");
		mImageButtonScrollDown.setImage(PosResUtil.getImageIconFromResource(IMAGE_BUTTON_SCROLL_DOWN));
		mImageButtonScrollDown.setTouchedImage(PosResUtil.getImageIconFromResource(IMAGE_BUTTON_SCROLL_DOWN_TOUCH));		
		mImageButtonScrollDown.setHorizontalAlignment(SwingConstants.CENTER);		
		mImageButtonScrollDown.setBounds(0, 0, IMAGE_RIGHT_PANEL_BUTTON_WIDTH,IMAGE_RIGHT_PANEL_BUTTON_HEIGHT);
		mImageButtonScrollDown.setOnClickListner(imgScrollDownButtonListner);
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
		int itemIndex=mCurrentPage*9-9;
		PosCouponItemControl itemControl=null;
		for(int controlIndex=0; controlIndex<9; controlIndex++){
			itemControl=mCouponItemControlList.get(controlIndex);
			if(itemIndex<mPosCouponBrowseItemList.size()){
				itemControl.setCouponBrowseItem(mPosCouponBrowseItemList.get(itemIndex));
				itemControl.setVisible(true);
				itemIndex++;
			}
			else
				itemControl.setVisible(false);
		}
	}
	
	private void setSrcollButtonStatus(){
		mImageButtonScrollDown.setEnabled(true);
		mImageButtonScrollUp.setEnabled(true);
		if(mCurrentPage*9>=mPosCouponBrowseItemList.size()) mImageButtonScrollDown.setEnabled(false);
		if(mCurrentPage<=1) mImageButtonScrollUp.setEnabled(false);
	}
	
	private  IPosButtonListner imgScrollUpButtonListner= new PosButtonListnerAdapter() {
		@Override
		public void onClicked(PosButton button) {
			previousButtonClick();
		}
	};	
	
	private  IPosButtonListner imgScrollDownButtonListner= new PosButtonListnerAdapter() {
		@Override
		public void onClicked(PosButton button) {			
			nextButtonClick();
		}
	};
	
	private  IPosButtonListner imgCancelButtonListner= new PosButtonListnerAdapter() {
		@Override
		public void onClicked(PosButton button) {			
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
	
	private IPosCouponItemListner itemSelectListner=new IPosCouponItemListner() {
		@Override
		public void onSelected(BeanCoupon couponBrowseItem) {
			if(mPosCouponFormListner!=null)
				mPosCouponFormListner.onSelected(couponBrowseItem);
			setVisible(false);
			dispose();
		}
	};
	
	private void loadCoupon(){
		PosCouponItemProvider smProvider=new PosCouponItemProvider();
		mPosCouponBrowseItemList=smProvider.getCouponBrowseItemList();
	}
	
	public void setCouponitemSelectedListner(
		IPosCouponBrowseFormListner posCouponFormListner) {
		this.mPosCouponFormListner = posCouponFormListner;
	}
	

	
}
