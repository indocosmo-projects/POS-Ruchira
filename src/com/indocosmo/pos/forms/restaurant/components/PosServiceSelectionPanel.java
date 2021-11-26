/**
 * 
 */
package com.indocosmo.pos.forms.restaurant.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

import com.indocosmo.pos.common.utilities.PosResUtil;
import com.indocosmo.pos.forms.PosOrderEntryForm;
import com.indocosmo.pos.forms.components.buttons.PosSelectButton;
import com.indocosmo.pos.forms.components.buttons.listners.IPosSelectButtonListner;

/**
 * @author jojesh
 *
 */
@SuppressWarnings("serial")
public class PosServiceSelectionPanel extends JPanel {

	private static final String IMAGE_BUTTON_NORMAL="service_selection.png";
	private static final String IMAGE_BUTTON_TOUCHED="service_selection_touch.png";
	private static final String IMAGE_BUTTON_SELECTED="service_selection_selected.png";
	
	private static final int BUTTON_WIDTH=250;
	private static final int BUTTON_HEIGHT=42;
	
	private static final int PANEL_CONTENT_V_GAP=PosOrderEntryForm.PANEL_CONTENT_V_GAP;
	private static final int PANEL_CONTENT_H_GAP=PosOrderEntryForm.PANEL_CONTENT_H_GAP;
	
	private static ImageIcon mImageIconNormal=null;
	private static ImageIcon mImageIconTouched=null;
	private static ImageIcon mImageIconSelected=null;
	
	private PosSelectButton mButtonTakeAway;
	private PosSelectButton mButtonTable;
	
	private ServiceTypes mSelectedService;
	
	private int mHeight;
	private int mWidth;
	
	public enum ServiceTypes{
		TAKEAWAY,
		TABLE
	}
	
	public PosServiceSelectionPanel(int width,int height) {
		super();

		this.mHeight=height;
		this.mWidth=width;
		
		final int hGap=(mWidth-BUTTON_WIDTH*2)/3;
		setLayout(new FlowLayout(FlowLayout.CENTER, hGap, PANEL_CONTENT_V_GAP));
		setBorder(new EtchedBorder(EtchedBorder.LOWERED));
		setPreferredSize(new Dimension(mWidth,mHeight));

		loadItemImages();
		createServiceSelection();
		setSelectedService(ServiceTypes.TABLE);
	}
	
	protected void loadItemImages(){
		if(mImageIconNormal==null)
			mImageIconNormal=PosResUtil.getImageIconFromResource(IMAGE_BUTTON_NORMAL);
		if(mImageIconTouched==null)
			mImageIconTouched=PosResUtil.getImageIconFromResource(IMAGE_BUTTON_TOUCHED);
		if(mImageIconSelected==null)
			mImageIconSelected=PosResUtil.getImageIconFromResource(IMAGE_BUTTON_SELECTED);
	}
	
	private void createServiceSelection() {
		
		final int buttonHeight=BUTTON_HEIGHT;
		final int buttonWidth=BUTTON_WIDTH;
		
		mButtonTable=new PosSelectButton("Table Service");
		mButtonTable.setPreferredSize(new Dimension(buttonWidth,buttonHeight));
		mButtonTable.setSelectedTextColor(Color.WHITE);
		setImages(mButtonTable);
		add(mButtonTable);
			
		mButtonTakeAway=new PosSelectButton("Take Away");
		mButtonTakeAway.setPreferredSize(new Dimension(buttonWidth,buttonHeight));
		mButtonTakeAway.setSelectedTextColor(Color.WHITE);
		setImages(mButtonTakeAway);
		add(mButtonTakeAway);
		
		mButtonTakeAway.setOnSelectListner(new IPosSelectButtonListner() {
			
			@Override
			public void onSelected(PosSelectButton button) {
				mButtonTable.setSelected(false);
				mSelectedService=ServiceTypes.TAKEAWAY;
				if(mListener!=null)
					mListener.onServiceSelected(mSelectedService);
			}
		});
		
		mButtonTable.setOnSelectListner(new IPosSelectButtonListner() {
			
			@Override
			public void onSelected(PosSelectButton button) {
				mButtonTakeAway.setSelected(false);
				mSelectedService=ServiceTypes.TABLE;
				if(mListener!=null)
					mListener.onServiceSelected(mSelectedService);
			}
		});
		
	}
	
	public void setSelectedService(ServiceTypes type){
		if(type==ServiceTypes.TABLE)
			mButtonTable.setSelected(true);
		else
			mButtonTakeAway.setSelected(false);
	}
	
	public ServiceTypes getSelectedService(){
		return mSelectedService;
	}
	
	private void setImages(PosSelectButton button){
		button.setImage(mImageIconNormal);
		button.setTouchedImage(mImageIconTouched);
		button.setSelectedImage(mImageIconSelected);
	}
	
	
	
	private IPosServiceSectionListner mListener;
	
	public void setListner(IPosServiceSectionListner listner){
		this.mListener=listner;
	}
	
	public interface IPosServiceSectionListner{
		public void onServiceSelected(ServiceTypes type);
	}
}
