/**
 * 
 */
package com.indocosmo.pos.forms;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;

import com.indocosmo.pos.common.PosEnvSettings;
import com.indocosmo.pos.common.utilities.PosResUtil;
import com.indocosmo.pos.common.utilities.PosUtil;
import com.indocosmo.pos.data.beans.BeanSalesMode;
import com.indocosmo.pos.data.providers.shopdb.PosSaleModeItemProvider;
import com.indocosmo.pos.forms.components.buttons.PosButton;
import com.indocosmo.pos.forms.components.buttons.listners.IPosButtonListner;
import com.indocosmo.pos.forms.components.buttons.listners.PosButtonListnerAdapter;
import com.indocosmo.pos.forms.components.itemcontrols.PosSalesModeItemControl;
import com.indocosmo.pos.forms.components.itemcontrols.listners.IPosSalesModeItemControlListner;
import com.indocosmo.pos.forms.listners.IPosSalesModeFormListner;

/**
 * @author jojesh
 *
 */
@SuppressWarnings("serial")
@Deprecated
public final class PosSalesModeForm extends JDialog {
	
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
	
	private ArrayList<BeanSalesMode> mPosSalesModeItemList;
	private ArrayList<PosSalesModeItemControl> mSalesModeItemControlList;
	
	private PosButton mImageButtonScrollUp=new PosButton();
	private PosButton mImageButtonScrollDown=new PosButton();
	
	private JPanel mSalesModeContainer;		
	private JPanel mContentPane;	
	private IPosSalesModeFormListner mPosSalesModeFormListner;
	PosButton mButtonCancel;
	
	
	public PosSalesModeForm(){
		initControls();
		loadSalesModes();
	}
	
	private void initControls(){
		Dimension dim =PosUtil.getScreenSize(PosEnvSettings.getInstance().getPrimaryScreen());
		int left = (dim.width-FORM_WIDTH)/2;
		int top = (dim.height-FORM_HEIGHT)/2;
		setUndecorated(true);
		setAlwaysOnTop(true);		
		setBounds(left,top,FORM_WIDTH, FORM_HEIGHT);
		mContentPane = new JPanel();
		mContentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		mContentPane.setLayout(null);		
		setContentPane(mContentPane);
		setTitle();
		loadSalesModeItems();
		createSalesModeItems();
		
		JPanel rightPanel=new JPanel();		
		rightPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
		rightPanel.setLayout(createLayout());
		final int rightPanelleft=mSalesModeContainer.getX()+mSalesModeContainer.getWidth() + PANEL_CONTENT_H_GAP;		
		final int rightPanelTop=LABEL_TITLE_HEIGHT+PANEL_CONTENT_V_GAP*2;		
		rightPanel.setBounds(rightPanelleft,rightPanelTop,  110, mSalesModeContainer.getHeight());			
		createScrollButton(rightPanel);	
		createCancelButton(rightPanel);
		setSrcollButtonStatus();
		
	}	
	private void setTitle(){
		JLabel labelTitle=new JLabel();		
		labelTitle.setText("Tariff Categories");
		labelTitle.setHorizontalAlignment(SwingConstants.CENTER);		
		labelTitle.setBounds(PANEL_CONTENT_H_GAP, PANEL_CONTENT_V_GAP, FORM_WIDTH, LABEL_TITLE_HEIGHT);		
		labelTitle.setFont(new Font("Dialog", Font.BOLD, 18));		
		add(labelTitle);		
		}
	
	private void loadSalesModeItems(){
		PosSaleModeItemProvider salesModeItemProvider=new PosSaleModeItemProvider();
		mPosSalesModeItemList=salesModeItemProvider.getSaleModeItemList();
	}
	
	private void createSalesModeItems(){		
		mNextItemIndexToDisplay=0;
		mSalesModeContainer=new JPanel();
		mSalesModeContainer.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
		mSalesModeContainer.setLayout(createLayout());
		
		final int width=PosSalesModeItemControl.BUTTON_WIDTH*3+PANEL_CONTENT_H_GAP*4;
		final int height=PosSalesModeItemControl.BUTTON_HEIGHT*3+PANEL_CONTENT_V_GAP*4;
		final int top=LABEL_TITLE_HEIGHT+PANEL_CONTENT_V_GAP*2;
		
		mSalesModeContainer.setBounds(PANEL_CONTENT_H_GAP,top,  width, height);	
		mSalesModeItemControlList=new ArrayList<PosSalesModeItemControl>();
		mCurrentPage++;
		for(int index=0; index<9; index++){
			PosSalesModeItemControl itemControl=new PosSalesModeItemControl();
			itemControl.setItemSelectedListner(itemSelectListner);
			if(mNextItemIndexToDisplay<mPosSalesModeItemList.size()){
				itemControl.setSalesModeItem(mPosSalesModeItemList.get(mNextItemIndexToDisplay));
				mNextItemIndexToDisplay++;
				mSalesModeItemControlList.add(itemControl);
			}
			else
				itemControl.setVisible(false);
			mSalesModeContainer.add(itemControl);
		}
		add(mSalesModeContainer);
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
		PosSalesModeItemControl itemControl=null;
		for(int controlIndex=0; controlIndex<9; controlIndex++){
			itemControl=mSalesModeItemControlList.get(controlIndex);
			if(itemIndex<mPosSalesModeItemList.size()){
				itemControl.setSalesModeItem(mPosSalesModeItemList.get(itemIndex));
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
		if(mCurrentPage*9>=mPosSalesModeItemList.size()) mImageButtonScrollDown.setEnabled(false);
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
			if(mPosSalesModeFormListner!=null)
				mPosSalesModeFormListner.onCancel();
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
	
	private IPosSalesModeItemControlListner itemSelectListner=new IPosSalesModeItemControlListner() {
		@Override
		public void onSelected(BeanSalesMode salesModeItem) {
			if(mPosSalesModeFormListner!=null)
				mPosSalesModeFormListner.onSalesModeSelected(salesModeItem);
			setVisible(false);
			dispose();
		}
	};
	private void loadSalesModes(){
		PosSaleModeItemProvider smProvider=new PosSaleModeItemProvider();
		mPosSalesModeItemList=smProvider.getSaleModeItemList();
	}
	
	public void setSalesModeSelectedListner(
		IPosSalesModeFormListner posSalesModeFormListner) {
		this.mPosSalesModeFormListner = posSalesModeFormListner;
	}
	
	
	
	
}
