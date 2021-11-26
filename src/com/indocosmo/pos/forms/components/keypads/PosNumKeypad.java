/**
 * 
 */
package com.indocosmo.pos.forms.components.keypads;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.text.Document;

import com.indocosmo.pos.common.utilities.PosFormUtil;
import com.indocosmo.pos.common.utilities.PosNumberUtil;
import com.indocosmo.pos.common.utilities.PosResUtil;
import com.indocosmo.pos.forms.components.buttons.PosButton;
import com.indocosmo.pos.forms.components.buttons.listners.IPosButtonListner;
import com.indocosmo.pos.forms.components.buttons.listners.PosButtonListnerAdapter;

@SuppressWarnings("serial")
public final class PosNumKeypad extends JPanel{
	
	public enum CancelActions{
		Cancel,
		Clear
	}
	
	public enum KeypadTypes{
		Digital,
		Numeric,
		Password;
	}
	
	private static final int PANEL_CONTENT_V_GAP=3;//PosOrderEntryForm.PANEL_CONTENT_V_GAP;
	private static final int PANEL_CONTENT_H_GAP=8;//PosOrderEntryForm.PANEL_CONTENT_H_GAP;

	private static final int IMAGE_BUTTON_WIDTH=90;
	private static final int IMAGE_BUTTON_HEIGHT=87;
	
	private static final int BORDER_THICKNES=1; 
	
	public static final int LAYOUT_WIDTH=IMAGE_BUTTON_WIDTH*4+PANEL_CONTENT_H_GAP*5+BORDER_THICKNES*2;
	public static final int LAYOUT_HEIGHT=420;
	
	private static final int KEY_PAD_CONTAINER_WIDTH=IMAGE_BUTTON_WIDTH*3+PANEL_CONTENT_H_GAP*3;
	
	private static final int TEXTFIELD_WIDTH=LAYOUT_WIDTH-PANEL_CONTENT_H_GAP*2;
	private static final int TEXTFIELD_HEIGHT=30;	

	private static final int MAX_INPUT_LENGTH = 20;
	
	private static final int RIGHT_PANEL_WIDTH=IMAGE_BUTTON_WIDTH+PANEL_CONTENT_H_GAP*2;

	private static final String IMAGE_BUTTON_KEYPAD="dlg_buton.png";
	private static final String IMAGE_BUTTON_KEYPAD_TOUCH="dlg_buton_touch.png";		
	
	private static final String IMAGE_BUTTON_CANCEL="cancel_button.png";
	private static final String IMAGE_BUTTON_CANCEL_TOUCH="cancel_button_touch.png";
	
	private static final String IMAGE_ACCEPT_BUTTON="num_key_pad_accept.png";
	private static final String IMAGE_ACCEPT_BUTTON_TOUCH="num_key_pad_accept_hover.png";	
	
	private JPanel mNumericKeypadContainer;		
	private boolean mHideAcceptButton;
	private JPanel mDisplayTextPanel;
	private JTextField mTxtDisplayTextField;
	private IPosNumkeypadListner mNumkeypadAcceptListner;
	private IPosNumkeypadListner mNumkeypadCancelListner;
	private PosButton mButtonCancel;
	private String defaultValue;
	private KeypadTypes mKeypadType=KeypadTypes.Numeric;
	private boolean resetOnAccept=true;
	
	public void setDisplayTextField(JTextField txtDisplayTextField){
		mTxtDisplayTextField=txtDisplayTextField;
//		mTxtDisplayTextField.setDocument(PosNumberUtil.createDecimalDocument());
		mTxtDisplayTextField.selectAll();
	}
	public JTextField getDisplayTxtField() {
		return mTxtDisplayTextField;
	}	
	
	public PosNumKeypad(){
		mHideAcceptButton=false;
		initComponent();	
//		mTxtDisplayTextField.setFocusable(false);
	}
	
	public PosNumKeypad(JTextField txtDisplayTextField){
		mTxtDisplayTextField=txtDisplayTextField;
		mHideAcceptButton=true;
		initComponent();	
		mTxtDisplayTextField.setFocusable(true);
	}
	
	public PosNumKeypad(KeypadTypes keyType,JTextField txtDisplayTextField) {
		mTxtDisplayTextField=txtDisplayTextField;
		mHideAcceptButton=false;
		this.mKeypadType = keyType;
		mTxtDisplayTextField.setFocusable(true);
		initComponent();
	}

	/**
	 * @param isPassword
	 */
	public PosNumKeypad(KeypadTypes keyType) {
		mHideAcceptButton=false;
		this.mKeypadType = keyType;
		initComponent();	
	}

	private void initComponent() {
		setBounds(0,0,LAYOUT_WIDTH, LAYOUT_HEIGHT);
		setPreferredSize(new Dimension(LAYOUT_WIDTH, LAYOUT_HEIGHT));
		createDisplayTextField();
		createNumericKeypadItems();
		setLayout(null);
		setBorder(new EtchedBorder(EtchedBorder.LOWERED));
		JPanel rightPanel=new JPanel();		
		rightPanel.setLayout(createLayout());
		final int rightPanel_X=KEY_PAD_CONTAINER_WIDTH ;//+ PANEL_CONTENT_H_GAP;		
		final int rightPanel_Y=(mDisplayTextPanel!=null)?mDisplayTextPanel.getHeight()+PANEL_CONTENT_V_GAP*2:0;	
		
		rightPanel.setBounds(rightPanel_X,rightPanel_Y,  RIGHT_PANEL_WIDTH, mNumericKeypadContainer.getHeight());
		createRightPanelButton(rightPanel);		
		
	}	
	
	public void canHideAcceptButton(boolean hide){
		mHideAcceptButton=hide;
	}
	
	private void createDisplayTextField(){
		if (mTxtDisplayTextField == null) {
			mDisplayTextPanel = new JPanel();
			mDisplayTextPanel.setLayout(null);
			mDisplayTextPanel.setBounds(BORDER_THICKNES, BORDER_THICKNES,
					TEXTFIELD_WIDTH + PANEL_CONTENT_H_GAP, TEXTFIELD_HEIGHT
							+ PANEL_CONTENT_V_GAP * 2);
			add(mDisplayTextPanel);
			mTxtDisplayTextField = ((mKeypadType == KeypadTypes.Password) ? new JPasswordField("", 12)
					: new  JTextField("",12));
			mTxtDisplayTextField.setHorizontalAlignment(JTextField.RIGHT);
			mTxtDisplayTextField.setBounds(PANEL_CONTENT_H_GAP,
					PANEL_CONTENT_V_GAP, TEXTFIELD_WIDTH, TEXTFIELD_HEIGHT);
			mTxtDisplayTextField.setFont(PosFormUtil.getTextFieldBoldFont());
			mDisplayTextPanel.add(mTxtDisplayTextField);

		}
		mTxtDisplayTextField.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mOnAcceptListner.onClicked(null);
			}
		});
		mTxtDisplayTextField.setDocument(PosNumberUtil.createDecimalDocument());
	}
	
	private void createNumericKeypadItems(){
		String[] key = {
				"7", "8", "9",
				"4","5","6",
				"1", "2", "3", 
				"0",".","+/-"
		};	
		
		final int width=KEY_PAD_CONTAINER_WIDTH;//mDisplayTextPanel.getWidth()-PANEL_CONTENT_H_GAP-RIGHT_PANEL_WIDTH;
		final int top=(mDisplayTextPanel!=null)?mDisplayTextPanel.getHeight()+PANEL_CONTENT_V_GAP*2:0;	
		int height=4*IMAGE_BUTTON_HEIGHT+PANEL_CONTENT_V_GAP*6;// LAYOUT_HEIGHT-mDisplayTextPanel.getHeight()-PANEL_CONTENT_V_GAP*3;
		
		mNumericKeypadContainer=new JPanel();
		mNumericKeypadContainer.setBounds(BORDER_THICKNES,top,  width, height);		
		mNumericKeypadContainer.setLayout(createLayout());	
		
		for(int index=0; index<key.length; index++){
			PosButton KeypadButton = new PosButton(key[index]);		
			KeypadButton.setSize(IMAGE_BUTTON_WIDTH, IMAGE_BUTTON_HEIGHT);		
			KeypadButton.setHorizontalAlignment(SwingConstants.CENTER);			
			KeypadButton.setVerticalTextPosition(SwingConstants.CENTER);
			KeypadButton.setHorizontalTextPosition(SwingConstants.CENTER);				
			KeypadButton.setImage(PosResUtil.getImageIconFromResource(IMAGE_BUTTON_KEYPAD));	
			KeypadButton.setTouchedImage(PosResUtil.getImageIconFromResource(IMAGE_BUTTON_KEYPAD_TOUCH));
			KeypadButton.setFont(PosFormUtil.getButtonBoldFont());
			KeypadButton.setOnClickListner(new PosButtonListnerAdapter() {
				@Override
				public void onClicked(PosButton button) {
					String text=button.getText();
					if(text=="+/-")
						processSignChange();						 
					else if(text==".")
						addDecimalPoint();
					else
						addDigitToDisplay(text);	
					requestFocus();
				}
			});
			
			mNumericKeypadContainer.add(KeypadButton);
		}
		add(mNumericKeypadContainer);
	}
	
	public void setCancelEnabled(boolean enable){
		mButtonCancel.setEnabled(enable);
	}
	
	private void createRightPanelButton(JPanel rightPanel)
	{			
		PosButton buttonDelete=new PosButton();		
		buttonDelete.setText("Del");
		buttonDelete.setMnemonic(0);
		buttonDelete.setImage(PosResUtil.getImageIconFromResource(IMAGE_BUTTON_KEYPAD));
		buttonDelete.setTouchedImage(PosResUtil.getImageIconFromResource(IMAGE_BUTTON_KEYPAD_TOUCH));		
		buttonDelete.setHorizontalAlignment(SwingConstants.CENTER);		
		buttonDelete.setBounds(0, 0, IMAGE_BUTTON_WIDTH, IMAGE_BUTTON_HEIGHT);	
		buttonDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	delButtonPressed();
            	requestFocus();
            }
        });
		
		rightPanel.add(buttonDelete);
		add(rightPanel);		
		
		PosButton buttonAccept=new PosButton();
		buttonAccept.setAutoMnemonicEnabled(false);
		buttonAccept.setText("Accept");
		buttonAccept.setTouchedImage(PosResUtil.getImageIconFromResource(IMAGE_BUTTON_KEYPAD_TOUCH));		
		buttonAccept.setHorizontalAlignment(SwingConstants.CENTER);		
		buttonAccept.setBounds(0, 0, IMAGE_BUTTON_WIDTH, IMAGE_BUTTON_HEIGHT*2+PANEL_CONTENT_V_GAP);	
		buttonAccept.setImage(PosResUtil.getImageIconFromResource(IMAGE_ACCEPT_BUTTON));	
		buttonAccept.setTouchedImage(PosResUtil.getImageIconFromResource(IMAGE_ACCEPT_BUTTON_TOUCH));
		buttonAccept.setOnClickListner(mOnAcceptListner);
		buttonAccept.setEnabled(!mHideAcceptButton);
		rightPanel.add(buttonAccept);
		add(rightPanel);
		
		mButtonCancel=new PosButton();
		mButtonCancel.setAutoMnemonicEnabled(false);
		mButtonCancel.setText("Cancel");
		mButtonCancel.setCancel(true);
		mButtonCancel.setImage(PosResUtil.getImageIconFromResource(IMAGE_BUTTON_CANCEL));
		mButtonCancel.setTouchedImage(PosResUtil.getImageIconFromResource(IMAGE_BUTTON_CANCEL_TOUCH));	
		mButtonCancel.setHorizontalAlignment(SwingConstants.CENTER);		
		mButtonCancel.setBounds(0, 0, IMAGE_BUTTON_WIDTH, IMAGE_BUTTON_HEIGHT);		
		mButtonCancel.setOnClickListner(mCancelButtonListner);
		rightPanel.add(mButtonCancel);
		add(rightPanel);
	}
	
	private void addDigitToDisplay(String digit) {
		String selTExt=mTxtDisplayTextField.getSelectedText();
		if(selTExt!=null && selTExt.length()>0)
			setDisplayString("");
		String inputString = getDisplayString();
		if(mKeypadType == KeypadTypes.Numeric){
			if (inputString.indexOf("0") == 0) {
				inputString = inputString.substring(1);
			}
			if ((!inputString.equals("0") || Integer.parseInt(digit) > 0)
					&& inputString.length() < MAX_INPUT_LENGTH) {
				setDisplayString(inputString + digit);
			}
		}else{
			setDisplayString(inputString + digit);
		}
	}

	private void addDecimalPoint() {
		String inputString = getDisplayString();
		if (inputString.indexOf(".") < 0)
			setDisplayString(new String(inputString + "."));
	}

	private void delButtonPressed() {
		if (getDisplayString().length() <=1){
			if(mKeypadType == KeypadTypes.Numeric)
				setDisplayString("0");
			else	
				setDisplayString(" ");
		}else{
		setDisplayString(getDisplayString().substring(0,
				getDisplayString().length() - 1));

		}
	}
	private void processSignChange() {
		String input = getDisplayString();

		if (input.length() > 0 && !input.equals("0")) {
			if (input.indexOf("-") == 0)
				setDisplayString(input.substring(1));
			else
				setDisplayString("-" + input);
		}
	}

	private  void setDisplayString(String s) {
		if(mTxtDisplayTextField.isEditable() && mTxtDisplayTextField.isEnabled()){
			mTxtDisplayTextField.setText(s);
		}
	}
	
	public void setValue(String value) {
		setDisplayString(value);
		mTxtDisplayTextField.selectAll();
	}

	private String getDisplayString() {
		return mTxtDisplayTextField.getText();
	}		
	
	private FlowLayout createLayout(){
		FlowLayout flowLayout=new FlowLayout();
		flowLayout.setVgap(PANEL_CONTENT_V_GAP+1);
		flowLayout.setHgap(PANEL_CONTENT_H_GAP);
		flowLayout.setAlignment(FlowLayout.LEFT);
		return flowLayout;
	}
	
	private IPosButtonListner mOnAcceptListner=new PosButtonListnerAdapter() {
		@Override
		public void onClicked(PosButton button) {
			if(mNumkeypadAcceptListner!=null  && validateInput())
				mNumkeypadAcceptListner.onAcceptButton(mTxtDisplayTextField.getText());
			if(mKeypadType==KeypadTypes.Numeric && resetOnAccept)
				setDisplayString("0");
		};
	};
	
	private boolean validateInput(){
		boolean valid=true;
		if(mKeypadType!=KeypadTypes.Password && mTxtDisplayTextField.getText().trim().length()<=0){
			valid=false;
			PosFormUtil.showErrorMessageBox(null,"Please enter a valid input.");
		}
		mTxtDisplayTextField.requestFocus();
		return valid;
	}
	
	private  IPosButtonListner mCancelButtonListner= new PosButtonListnerAdapter() {
		@Override
		public void onClicked(PosButton button) {			
			if(mNumkeypadCancelListner!=null)
				mNumkeypadCancelListner.onCancelButton();
			setDisplayString("0");
		}
	};
	
	public void setOnAcceptListner(IPosNumkeypadListner numkeypadListner) {
		this.mNumkeypadAcceptListner = numkeypadListner;
	}
	
	public void setOnCancelListner(
			IPosNumkeypadListner numkeypadCancelListner) {
		this.mNumkeypadCancelListner = numkeypadCancelListner;
	}
	
	public void setCancelActions(CancelActions action){
		switch(action){
			case Cancel:
				break;
			case Clear:
				mButtonCancel.setText("Clear");
		}
		
	}
	
	public interface IPosNumkeypadListner{
		public void onAcceptButton(String value);
		public void onCancelButton();
	}

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#requestFocus()
	 */
	@Override
	public void requestFocus() {
		mTxtDisplayTextField.requestFocus();
		
	}

	public void setFiledDocument(Document document){
		mTxtDisplayTextField.setDocument(document);
	}
	/**
	 * @return the resetOnAccept
	 */
	public boolean isResetOnAccept() {
		return resetOnAccept;
	}
	/**
	 * @param resetOnAccept the resetOnAccept to set
	 */
	public void setResetOnAccept(boolean resetOnAccept) {
		this.resetOnAccept = resetOnAccept;
	}
	/**
	 * @return the defaultValue
	 */
	public String getDefaultValue() {
		return defaultValue;
	}
	/**
	 * @param defaultValue the defaultValue to set
	 */
	public void setDefaultValue(String defaultValue) {
		mTxtDisplayTextField.setText(defaultValue);
		mTxtDisplayTextField.selectAll();
		this.defaultValue = defaultValue;
	}
}


