package com.indocosmo.pos.forms.components.keypads;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.KeyEvent;

import javax.swing.JPanel;
import javax.swing.text.JTextComponent;

import com.indocosmo.pos.common.utilities.PosFormUtil;
import com.indocosmo.pos.common.utilities.PosNumberUtil;
import com.indocosmo.pos.forms.PosNumKeypadForm;
import com.indocosmo.pos.forms.PosOrderEntryForm;
import com.indocosmo.pos.forms.components.buttons.PosButton;
import com.indocosmo.pos.forms.components.buttons.listners.IPosButtonListner;
import com.indocosmo.pos.forms.listners.IPosNumKeyPadFormListner;

/**
 * This class will create the vertical key pad near to the Ordered item Grid. This can be used to
 * change the quantity of items ordered. This also includes a toggle button which will enable the
 * 2 digits entry like tv remote.
 * @author jojesh
 *
 */
@SuppressWarnings("serial")
public final class PosVKeyPad extends JPanel {

	private static final Color PANEL_BG_COLOR=PosOrderEntryForm.PANEL_BG_COLOR;
	private final static int PANEL_CONTENT_H_GAP=3;
	private final static int PANEL_CONTENT_V_GAP=1;
	private final static String[] KEY_TITLES={"1","2","3","4","5","6","7","8","9","--"};
//	private final static String[] KEY_TITLES={"123"};
	private int mLeft,mTop,mHeight,mWidth;
	private boolean mIsSimpleKeypad;
	private Object mParent;
	
	private static final String MULTI_NORMAL_IMAGE="vkb_key_123.png";
	private static final String MULTI_THOUCH_IMAGE="vkb_key_123_touch.png";
	
	public PosVKeyPad(int left,int top,int height,boolean isSimple){		
		mLeft=left;
		mTop=top;
		mHeight=height;
		mIsSimpleKeypad=isSimple;
		iniComponent();
	}

	/**
	 * Initializes the component.
	 */
	private void iniComponent(){
		
		setLocation(mLeft,mTop);
//		setBackground(PANEL_BG_COLOR);
//		setBackground(Color.BLUE);
//		setOpaque(true);
		setOpaque(false);
		if(mIsSimpleKeypad)
			crateButtons4SimpleKeypad();
		else
			createButtons();
	}
	private PosButton m123Button;
	private void crateButtons4SimpleKeypad() {
		mWidth=PosVKeyPadButton.LAYOUT_WIDTH; 
		setSize(mWidth, mHeight);
		setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		m123Button=new PosButton();
		m123Button.setPreferredSize(new Dimension(mWidth,mHeight));
		m123Button.setOnClickListner(new IPosButtonListner() {
			@Override
			public void onClicked(PosButton button) {
				showNumKeyPad();
			}
		});
		m123Button.registerKeyStroke(KeyEvent.VK_F4);
		m123Button.setImage(MULTI_NORMAL_IMAGE);
		m123Button.setTouchedImage(MULTI_THOUCH_IMAGE);

		add(m123Button);
	}

	private void createButtons(){
		mWidth=PosVKeyPadButton.LAYOUT_WIDTH+PANEL_CONTENT_H_GAP*2; 
		setSize(mWidth, mHeight);
		final int vGap=(mHeight-(KEY_TITLES.length*PosVKeyPadButton.LAYOUT_HEIGHT))/KEY_TITLES.length;
		setLayout(new FlowLayout(FlowLayout.CENTER,PANEL_CONTENT_H_GAP,vGap));
		int noButtons=mHeight/(PosVKeyPadButton.LAYOUT_HEIGHT+PANEL_CONTENT_V_GAP);
		noButtons=(noButtons>KEY_TITLES.length)?KEY_TITLES.length:noButtons;
		for(int index=0;index<noButtons;index++){
			PosVKeyPadButton vkButton=new PosVKeyPadButton();
			vkButton.setText(KEY_TITLES[index]);
			vkButton.setTag(KEY_TITLES[index]);
			vkButton.setOnClickListner(new IPosButtonListner() {
				@Override
				public void onClicked(PosButton button) {
					final String value=String.valueOf(button.getTag());
					if(value=="--")
						showNumKeyPad();
					else
						if(mVKeypadListner!=null)
							mVKeypadListner.onValueSlected(Integer.parseInt(value));
				}
			});
			add(vkButton);
		}
	}

	private void showNumKeyPad(){
		PosNumKeypadForm numKeyPad=new PosNumKeypadForm();
		numKeyPad.setMinValue(0d, "A valid quantity should be entered.");
		numKeyPad.setTitle("Enter quantity.");
		numKeyPad.setFiledDocument(PosNumberUtil.createDecimalDocument(false));
		numKeyPad.setOnValueChanged(new IPosNumKeyPadFormListner() {

			@Override
			public void onValueChanged(String value) {
				final double selValue=Double.parseDouble(value);
				if(mVKeypadListner!=null)
					mVKeypadListner.onValueSlected(selValue);
			}

			@Override
			public void onValueChanged(JTextComponent target, String oldValue) {
			}
		});
		PosFormUtil.showLightBoxModal(mParent,numKeyPad);
	}
	
	public void setParent(Object parent){
		mParent=parent;
	}

	private IPosVKeypadListner mVKeypadListner;
	public void setListner(IPosVKeypadListner listner){
		mVKeypadListner=listner;
	}
	public interface IPosVKeypadListner{
		public void onValueSlected(double value);
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.JComponent#setEnabled(boolean)
	 */
	@Override
	public void setEnabled(boolean enabled) {
		m123Button.setEnabled(enabled);
		super.setEnabled(enabled);
	}

}
