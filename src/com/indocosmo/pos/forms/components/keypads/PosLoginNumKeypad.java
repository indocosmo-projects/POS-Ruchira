/**
 * 
 */
package com.indocosmo.pos.forms.components.keypads;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.indocosmo.pos.common.utilities.PosFormUtil;
import com.indocosmo.pos.common.utilities.PosResUtil;
import com.indocosmo.pos.forms.PosOrderEntryForm;
import com.indocosmo.pos.forms.components.buttons.PosButton;
import com.indocosmo.pos.forms.components.buttons.listners.IPosButtonListner;
import com.indocosmo.pos.forms.components.buttons.listners.PosButtonListnerAdapter;


/**
 * @author jojesh
 *
 */
public final class PosLoginNumKeypad extends JPanel {
	
	
	private static final int FORM_WIDTH=330;
	private static final int FORM_HEIGHT=360;
	
	private static final int IMAGE_BUTTON_WIDTH=70;
	private static final int IMAGE_BUTTON_HEIGHT=70;
		
	private static final int PANEL_CONTENT_V_GAP=PosOrderEntryForm.PANEL_CONTENT_V_GAP;
	private static final int PANEL_CONTENT_H_GAP=PosOrderEntryForm.PANEL_CONTENT_H_GAP;
		
	private JPanel mNumericKeypadContainer;		
	private IPosNumLoginkeypadListner mNumLoginkeypadListner;	
	private IPosNumLoginkeypadListner mNumLoginClearButtonListner;
	
	private static final String IMAGE_BUTTON_KEYPAD="dlg_login_keypad.png";
	private static final String IMAGE_BUTTON_KEYPAD_TOUCH="dlg_login_keypad_touch.png";	
	
	private static final String IMAGE_BUTTON_CLEAR="num_key_pad_clear.png";
	private static final String IMAGE_BUTTON_CLEAR_TOUCH="num_key_pad_clear_touch.png";


	public PosLoginNumKeypad(){
		initComponent();		
	}
	

	private void initComponent() {
		setPreferredSize(new Dimension(260, FORM_HEIGHT-PANEL_CONTENT_V_GAP*4));
		createNumericKeypadItems();
		setLayout(null);		
//		setBackground(Color.RED);	
		
	}		
		
	private void createNumericKeypadItems(){
		String[] key = {
				"7", "8", "9",
				"4","5","6",
				"1", "2", "3", 
				"0","Clear"
		};	
		
		int width=FORM_WIDTH-PANEL_CONTENT_H_GAP*10;
		final int top=PANEL_CONTENT_V_GAP;	
		int height=FORM_HEIGHT-PANEL_CONTENT_V_GAP*6;
		
		mNumericKeypadContainer=new JPanel();
		mNumericKeypadContainer.setBounds(PANEL_CONTENT_H_GAP,top,  width, height);		
//		mNumericKeypadContainer.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
		mNumericKeypadContainer.setLayout(createLayout());	
		
		for(int index=0; index<key.length; index++){
			if(key[index]!="Clear"){
				PosButton KeypadButton = new PosButton(key[index]);		
				KeypadButton.setBounds(0,0,IMAGE_BUTTON_WIDTH, IMAGE_BUTTON_HEIGHT);		
				KeypadButton.setHorizontalAlignment(SwingConstants.CENTER);			
				KeypadButton.setVerticalTextPosition(SwingConstants.CENTER);
				KeypadButton.setHorizontalTextPosition(SwingConstants.CENTER);				
				KeypadButton.setImage(PosResUtil.getImageIconFromResource(IMAGE_BUTTON_KEYPAD));	
				KeypadButton.setTouchedImage(PosResUtil.getImageIconFromResource(IMAGE_BUTTON_KEYPAD_TOUCH));
				KeypadButton.setFont(PosFormUtil.getButtonBoldFont());
				KeypadButton.setOnClickListner(mOnNumButtonClickListner);				
				mNumericKeypadContainer.add(KeypadButton);
			}
			else
			{
				PosButton KeypadButton = new PosButton(key[index]);		
				KeypadButton.setBounds(0,0,IMAGE_BUTTON_WIDTH*2+PANEL_CONTENT_V_GAP, IMAGE_BUTTON_HEIGHT);		
				KeypadButton.setHorizontalAlignment(SwingConstants.CENTER);			
				KeypadButton.setVerticalTextPosition(SwingConstants.CENTER);
				KeypadButton.setHorizontalTextPosition(SwingConstants.CENTER);				
				KeypadButton.setImage(PosResUtil.getImageIconFromResource(IMAGE_BUTTON_CLEAR));	
				KeypadButton.setTouchedImage(PosResUtil.getImageIconFromResource(IMAGE_BUTTON_CLEAR_TOUCH));
//				KeypadButton.setBackground(Color.RED);
//				KeypadButton.setOpaque(true);
				KeypadButton.setOnClickListner(mOnClearButtonClickListner);
				KeypadButton.setFont(PosFormUtil.getButtonBoldFont());				
				mNumericKeypadContainer.add(KeypadButton);
			}
		}
		add(mNumericKeypadContainer);
	}
		
	
	private FlowLayout createLayout(){
		FlowLayout flowLayout=new FlowLayout();
		flowLayout.setVgap(PANEL_CONTENT_V_GAP);
		flowLayout.setHgap(PANEL_CONTENT_H_GAP);
		flowLayout.setAlignment(FlowLayout.LEFT);
		return flowLayout;
	}
	
	private IPosButtonListner mOnNumButtonClickListner=new PosButtonListnerAdapter() {
		@Override
		public void onClicked(PosButton button) {
			if(mNumLoginkeypadListner!=null)
				mNumLoginkeypadListner.onClickButton(button.getText());	
//			JOptionPane.showMessageDialog(null, button.getText());
		};
	};
	
	private IPosButtonListner mOnClearButtonClickListner=new PosButtonListnerAdapter() {
		@Override
		public void onClicked(PosButton button) {
			if(mNumLoginClearButtonListner!=null)
				mNumLoginClearButtonListner.onClearButton();	
//			JOptionPane.showMessageDialog(null, "Clear");
		};
	};
//	
	
	public void setOnClicButtonkListner(IPosNumLoginkeypadListner numLoginkeypadListner) {
		this.mNumLoginkeypadListner = numLoginkeypadListner;
	}
	
	public void setOnClearButtonkListner(IPosNumLoginkeypadListner numLoginkeypadListner) {
		this.mNumLoginClearButtonListner = numLoginkeypadListner;
	}
	
	
	public interface IPosNumLoginkeypadListner{
		public void onClickButton(String value);
		public void onClearButton();
	}
	
}


