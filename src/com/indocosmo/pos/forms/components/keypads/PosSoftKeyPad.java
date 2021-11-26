package com.indocosmo.pos.forms.components.keypads;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.indocosmo.pos.common.utilities.PosFormUtil;
import com.indocosmo.pos.common.utilities.PosResUtil;
import com.indocosmo.pos.forms.components.buttons.PosButton;
import com.indocosmo.pos.forms.components.buttons.PosToggleButton;
import com.indocosmo.pos.forms.components.buttons.listners.IPosButtonListner;
import com.indocosmo.pos.forms.components.buttons.listners.IPosToggleButtonListner;
import com.indocosmo.pos.forms.components.buttons.listners.PosButtonListnerAdapter;

@SuppressWarnings("serial")
public class PosSoftKeyPad extends JPanel {

	private final static int PANEL_CONTENT_H_GAP=1;
	private final static int PANEL_CONTENT_V_GAP=1;

	private final static String SOFT_FUN_KEY_PAGE_IMAGE="softkb_fun_key_page.png";
	private final static String SOFT_FUN_KEY_PAGE_IMAGE_TOUCH="softkb_fun_key_page_touch.png";

	private final static String SOFT_FUN_KEY_CASE_IMAGE="softkb_fun_key_case.png";
	private final static String SOFT_FUN_KEY_CASE_IMAGE_TOUCH="softkb_fun_key_case_touch.png";

	private final static String SOFT_FUN_KEY_ENTER_IMAGE="softkb_fun_key_enter.png";
	private final static String SOFT_FUN_KEY_ENTER_IMAGE_TOUCH="softkb_fun_key_enter_touch.png";

	private final static String SOFT_FUN_KEY_CLOSE_IMAGE="softkb_fun_key_close.png";
	private final static String SOFT_FUN_KEY_CLOSE_IMAGE_TOUCH="softkb_fun_key_Close_touch.png";

	private final static String SOFT_FUN_KEY_GO_IMAGE="softkb_fun_key_go.png";
	private final static String SOFT_FUN_KEY_GO_IMAGE_TOUCH="softkb_fun_key_go_touch.png";

	private final static String SOFT_KEY_SPACE_IMAGE="softkb_key_space.png";
	private final static String SOFT_KEY_SPACE_IMAGE_TOUCH="softkb_key_space_touch.png";
	
	private final static String SOFT_LONG_KEY_SPACE_IMAGE="softkb_long_key_space.png";
	private final static String SOFT_LONG_KEY_SPACE_IMAGE_TOUCH="softkb_long_key_space_touch.png";

	private final static String SOFT_FUN_KEY_MODE_IMAGE="softkb_fun_key_mode.png";
	private final static String SOFT_FUN_KEY_MODE_IMAGE_TOUCH=" softkb_fun_key_mode_touch.png";

	private final static String SOFT_FUN_KEY_BACK_SPACE_IMAGE="softkb_fun_key_bs.png";
	private final static String SOFT_FUN_KEY_BACK_SPACE_IMAGE_TOUCH="softkb_fun_key_bs_touch.png";
	
	private final int KEY_SPACE_WIDTH=183;
	private final int LONG_KEY_SPACE_WIDTH=KEY_SPACE_WIDTH+PosSoftKey.KEY_WIDTH*2+KEY_H_GAP*2;

	private final static int MAX_CHAR_COUNT=30;

	private enum LetterCase{
		Lower,
		Upper		
	}

	private enum DisplayMode{
		Letters,
		Numeric
	}

	private final static int CHAR_KEYS_PER_PAGE=27;
	private final static int UPPER_KEYS_START=0;
	private final static int LOWER_KEYS_START=UPPER_KEYS_START+CHAR_KEYS_PER_PAGE;
	private final static int NUM_KEYS_START=LOWER_KEYS_START+CHAR_KEYS_PER_PAGE;
	private final static int KEY_H_GAP=3;
	private final static int KEY_V_GAP=3;
	private final static int KEYS_ON_ROW=10;
	private final static int KEY_LAYER_COUNT=4;
	private final static int TEXT_AREA_HEIGHT=30;
	private final static int KEY_LAYER_WIDTH=KEYS_ON_ROW*(KEY_H_GAP+PosSoftKey.KEY_WIDTH)+KEY_H_GAP;
	private final static int KEY_LAYER_HEIGHT=PosSoftKey.KEY_HEIGHT+KEY_V_GAP;
	private final static int KEYS_PANEL_HEIGHT=KEY_LAYER_HEIGHT*KEY_LAYER_COUNT+KEY_V_GAP;
	private final static int KEYS_PANEL_WIDTH=KEY_LAYER_WIDTH;
	private final static int TEXT_AREA_WIDTH=KEYS_PANEL_WIDTH;

	public final static int LAYOUT_WIDTH=KEYS_PANEL_WIDTH+PANEL_CONTENT_H_GAP*2;
//	public final static int LAYOUT_HEIGHT=PANEL_CONTENT_V_GAP*3+TEXT_AREA_HEIGHT+KEYS_PANEL_HEIGHT;
	public final static int LAYOUT_HEIGHT=PANEL_CONTENT_V_GAP*2+KEYS_PANEL_HEIGHT;

	private final static String DISPLAY_CHARS="QWERTYUIOPASDFGHJKL ZXCVBNMqwertyuiopasdfghjkl zxcvbnm1234567890@#%&*/-+()?!“':;,~=_<>{}[]|$₤₤¥¢Ẅ§^'°¿¡\\«»®©";

	private LetterCase mCurLetterCase=LetterCase.Lower;
	private DisplayMode mCurDisplayMode=DisplayMode.Letters;	
	private ArrayList<PosSoftKey> mDisplayKeyList;
	private PosToggleButton mKeyChangeCase;
	private PosButton mKeyChangePages;
	private PosButton mKeyBackSpace;
	private PosToggleButton mKeySwichAlphaNum;
	private PosButton mKeySpace;
	private PosButton mKeyEnter;
	private PosButton mKeyGo;
	private PosButton mKeyCancel;	
	private PosSoftKey mSoftKey;	
	private int mCurPageIndex,mPageCount;	
	private JPanel mKeysPanel;
	private JTextArea mTextArea;
	private IPosSoftKeypadListner mKeypadFormListner;
	private Boolean mIsMultiLine=true;


	private KeyListener mKeyListener;
	private DocumentListener mDocListener;
	
	/**
	 * Create the dialog.
	 */
	public PosSoftKeyPad(JTextArea textArea) {
		mTextArea=textArea;
		initControl();
	}

	public PosSoftKeyPad(JTextArea textArea, Boolean multiline) {
		mTextArea=textArea;
		mIsMultiLine=multiline;
		initControl();
	}
	
	private void initListeners(){
		
		mDocListener=new DocumentListener() {

			@Override
			public void removeUpdate(DocumentEvent e) {
				onTextChanged();
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				onTextChanged();

			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				onTextChanged();

			}
		};
		
		mKeyListener=new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				int keyCode=e.getKeyCode();
				if(keyCode==KeyEvent.VK_CAPS_LOCK || keyCode==KeyEvent.VK_NUM_LOCK ||keyCode==KeyEvent.VK_ESCAPE){
					Toolkit toolkit = Toolkit.getDefaultToolkit();
					switch(keyCode){
					case KeyEvent.VK_NUM_LOCK:
						boolean isNumLockOn = toolkit.getLockingKeyState(KeyEvent.VK_NUM_LOCK);
						mKeySwichAlphaNum.setSelected(isNumLockOn);
						break;
					case KeyEvent.VK_CAPS_LOCK:
						boolean isCapsLockOn = toolkit.getLockingKeyState(KeyEvent.VK_CAPS_LOCK);
						mKeyChangeCase.setSelected(isCapsLockOn);
						mKeySwichAlphaNum.setSelected(false);
						break;
					case KeyEvent.VK_ESCAPE:
						if(mKeypadFormListner!=null)
							mKeypadFormListner.onCancel();
						break;
					}
					
					e.consume();
				}
				else
					super.keyReleased(e);
			}
			@Override
			public void keyPressed(KeyEvent e) {

			}
		};
	}


	private void initControl(){
		
		initListeners();
		
		setBackground(new Color(6,38,76));
		setSize(LAYOUT_WIDTH, LAYOUT_HEIGHT);
		setPreferredSize( new Dimension(LAYOUT_WIDTH, LAYOUT_HEIGHT));
		setLayout(new FlowLayout(FlowLayout.CENTER,PANEL_CONTENT_H_GAP,PANEL_CONTENT_V_GAP));
//		mTextArea=mIsMultiLine?new JTextArea():new JTextArea(new MaxLengthTextDoc(MAX_CHAR_COUNT));
//		mTextArea.setLineWrap(mIsMultiLine);
//		mTextArea.setBounds(PANEL_CONTENT_H_GAP,PANEL_CONTENT_V_GAP,TEXT_AREA_WIDTH,TEXT_AREA_HEIGHT);
//		mTextArea.setBorder(new LineBorder(new Color(134, 184, 232)));
//		mTextArea.setFont(PosFormUtil.getTextFieldBoldFont());
//		mTextArea.addKeyListener(new KeyAdapter() {
//			@Override
//			public void keyPressed(KeyEvent e) {
//				if(!mIsMultiLine){
//					if(e.getKeyCode()==KeyEvent.VK_ENTER ){
//						go();
//						e.consume();
//					}	
//				}
//			}
//		});



//		add(mTextArea);
		setTextArea(mTextArea);
		mDisplayKeyList=new ArrayList<PosSoftKey>();
//		int top=mTextArea.getY()+TEXT_AREA_HEIGHT+PANEL_CONTENT_V_GAP;
		mKeysPanel=new JPanel();
		mKeysPanel.setBackground(new Color(6,38,76));
//		mKeysPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
//		mKeysPanel.setBounds(PANEL_CONTENT_H_GAP,top,KEYS_PANEL_WIDTH,KEYS_PANEL_HEIGHT);
		mKeysPanel.setPreferredSize(new Dimension(KEYS_PANEL_WIDTH,KEYS_PANEL_HEIGHT));

		mKeysPanel.setLayout(null);
		add(mKeysPanel);

		JPanel row=createFirstLayer();
		row.setLocation(0, 0);
		mKeysPanel.add(row);

		row=createSecondLayer();
		row.setLocation(0, KEY_LAYER_HEIGHT);
		mKeysPanel.add(row);

		row=createThirdLayer();
		row.setLocation(0, KEY_LAYER_HEIGHT*2);
		mKeysPanel.add(row);

		row=createFourthLayer();
		row.setLocation(0, KEY_LAYER_HEIGHT*3);
		mKeysPanel.add(row);

		PopulateKeys();
		setKeyState();
	}
	
	public void setTextArea(JTextArea textArea){
		
		mTextArea=textArea;
		setTextArea();

	}
	
	private void setTextArea(){
		 
		if(mTextArea!=null){
			mTextArea.getDocument().removeDocumentListener(mDocListener);
			mTextArea.getDocument().addDocumentListener(mDocListener);
			
			mTextArea.removeKeyListener(mKeyListener);
			mTextArea.addKeyListener(mKeyListener);
		}
	}
	
	private void setKeyState(){
		Toolkit toolkit = Toolkit.getDefaultToolkit();
//		boolean isNumLockOn = toolkit.getLockingKeyState(KeyEvent.VK_NUM_LOCK);
//		mKeySwichAlphaNum.setSelected(isNumLockOn);
		boolean isCapsLockOn = toolkit.getLockingKeyState(KeyEvent.VK_CAPS_LOCK);
		mKeyChangeCase.setSelected(isCapsLockOn);
	}

	private void onTextChanged(){
		if(mKeypadFormListner!=null)
			mKeypadFormListner.onTextChanged(mTextArea.getText());
	}

	private JPanel createFirstLayer(){
		JPanel keyLayer=new JPanel();
		keyLayer.setLayout(new FlowLayout(FlowLayout.CENTER,KEY_H_GAP,KEY_V_GAP));
		keyLayer.setSize(KEY_LAYER_WIDTH, KEY_LAYER_HEIGHT);
		keyLayer.setOpaque(false);
		for(int keyIndex=0;keyIndex<KEYS_ON_ROW;keyIndex++){
			mSoftKey=new PosSoftKey(String.valueOf(keyIndex));
			mSoftKey.setOnClickListner(softKeyListner);
			keyLayer.add(mSoftKey);
			mDisplayKeyList.add(mSoftKey);
		}
		return keyLayer;
	}

	private JPanel createSecondLayer(){
		JPanel keyLayer=new JPanel();
		keyLayer.setLayout(new FlowLayout(FlowLayout.CENTER,KEY_H_GAP,KEY_V_GAP));
		keyLayer.setSize(KEY_LAYER_WIDTH, KEY_LAYER_HEIGHT);
		keyLayer.setOpaque(false);
		for(int keyIndex=0;keyIndex<KEYS_ON_ROW;keyIndex++){
			mSoftKey=new PosSoftKey(String.valueOf(keyIndex));
			mSoftKey.setOnClickListner(softKeyListner);
			keyLayer.add(mSoftKey);
			mDisplayKeyList.add(mSoftKey);
		}
		return keyLayer;
	}

	private JPanel createThirdLayer(){
		JPanel keyLayer=new JPanel();
		keyLayer.setLayout(new FlowLayout(FlowLayout.CENTER,0,KEY_V_GAP));
		keyLayer.setSize(KEY_LAYER_WIDTH, KEY_LAYER_HEIGHT);
		keyLayer.setOpaque(false);

		final int KEY_CHANGE_CASE_WIDTH=97;
		mKeyChangeCase=new PosToggleButton();
		mKeyChangeCase.setPreferredSize(new Dimension(KEY_CHANGE_CASE_WIDTH, PosSoftKey.KEY_HEIGHT));
		mKeyChangeCase.setOnToggleListner(caseChangeListner);
		mKeyChangeCase.setImage(PosResUtil.getImageIconFromResource(SOFT_FUN_KEY_CASE_IMAGE));
		mKeyChangeCase.setTouchedImage(PosResUtil.getImageIconFromResource(SOFT_FUN_KEY_CASE_IMAGE_TOUCH));
		keyLayer.add(mKeyChangeCase);

		final int KEY_CHANGE_PAGES_WIDTH=97;
		mKeyChangePages=new PosButton("1/2");
		mKeyChangePages.setPreferredSize(new Dimension(KEY_CHANGE_PAGES_WIDTH, PosSoftKey.KEY_HEIGHT));
		mKeyChangePages.setVisible(false);
		mKeyChangePages.setOnClickListner(pageChangeListner);
		mKeyChangePages.setImage(PosResUtil.getImageIconFromResource(SOFT_FUN_KEY_PAGE_IMAGE));
		mKeyChangePages.setTouchedImage(PosResUtil.getImageIconFromResource(SOFT_FUN_KEY_PAGE_IMAGE_TOUCH));
		keyLayer.add(mKeyChangePages);


		JPanel keyInnerLayer=new JPanel();
		keyInnerLayer.setLayout(new FlowLayout(FlowLayout.CENTER,KEY_H_GAP,0));
		keyInnerLayer.setSize(PosSoftKey.KEY_WIDTH*7, KEY_LAYER_HEIGHT);
		keyInnerLayer.setOpaque(false);
		keyLayer.add(keyInnerLayer);

		for(int keyIndex=0;keyIndex<KEYS_ON_ROW-3;keyIndex++){
			mSoftKey=new PosSoftKey(String.valueOf(keyIndex));
			mSoftKey.setOnClickListner(softKeyListner);
			keyInnerLayer.add(mSoftKey);
			mDisplayKeyList.add(mSoftKey);
		}

		final int KEY_CHANGE_BACK_SPACE_WIDTH=97;
		mKeyBackSpace=new PosButton();
		mKeyBackSpace.setPreferredSize(new Dimension(KEY_CHANGE_BACK_SPACE_WIDTH, PosSoftKey.KEY_HEIGHT));
		mKeyBackSpace.setOnClickListner(funKeyListner);
		mKeyBackSpace.setTag(KeyEvent.VK_BACK_SPACE);
		mKeyBackSpace.setImage(PosResUtil.getImageIconFromResource(SOFT_FUN_KEY_BACK_SPACE_IMAGE));
		mKeyBackSpace.setTouchedImage(PosResUtil.getImageIconFromResource(SOFT_FUN_KEY_BACK_SPACE_IMAGE_TOUCH));
		keyLayer.add(mKeyBackSpace);
		return keyLayer;
	}

	private JPanel createFourthLayer(){
		JPanel keyLayer=new JPanel();
		keyLayer.setLayout(new FlowLayout(FlowLayout.CENTER,KEY_H_GAP,KEY_V_GAP));
		keyLayer.setSize(KEY_LAYER_WIDTH, KEY_LAYER_HEIGHT);
		keyLayer.setOpaque(false);

		mKeyCancel=new PosButton();
		mKeyCancel.setPreferredSize(new Dimension((int)((PosSoftKey.KEY_WIDTH)), PosSoftKey.KEY_HEIGHT));
		mKeyCancel.setImage(PosResUtil.getImageIconFromResource(SOFT_FUN_KEY_CLOSE_IMAGE));
		mKeyCancel.setTouchedImage(PosResUtil.getImageIconFromResource(SOFT_FUN_KEY_CLOSE_IMAGE_TOUCH));
		mKeyCancel.setOnClickListner(new PosButtonListnerAdapter() {
			@Override
			public void onClicked(PosButton button) {
				if(mKeypadFormListner!=null)
					mKeypadFormListner.onCancel();
			}
		});
		keyLayer.add(mKeyCancel);

		final int KEY_SWITCH_ALPHA_NUM_WIDTH=136;
		mKeySwichAlphaNum=new PosToggleButton("123");
		mKeySwichAlphaNum.setPreferredSize(new Dimension(KEY_SWITCH_ALPHA_NUM_WIDTH, PosSoftKey.KEY_HEIGHT));
		mKeySwichAlphaNum.setOnToggleListner(displayModeChangeListner);
		mKeySwichAlphaNum.setImage(PosResUtil.getImageIconFromResource(SOFT_FUN_KEY_MODE_IMAGE));
		mKeySwichAlphaNum.setTouchedImage(PosResUtil.getImageIconFromResource(SOFT_FUN_KEY_MODE_IMAGE_TOUCH));
		keyLayer.add(mKeySwichAlphaNum);

		
		mKeySpace=new PosButton();
		mKeySpace.setPreferredSize(new Dimension(KEY_SPACE_WIDTH, PosSoftKey.KEY_HEIGHT));
		mKeySpace.setTag(" ");
		mKeySpace.setOnClickListner(softKeyListner);
		mKeySpace.setImage(PosResUtil.getImageIconFromResource(SOFT_KEY_SPACE_IMAGE));
		mKeySpace.setTouchedImage(PosResUtil.getImageIconFromResource(SOFT_KEY_SPACE_IMAGE_TOUCH));
		keyLayer.add(mKeySpace);

		mSoftKey=new PosSoftKey(".");
		keyLayer.add(mSoftKey);
		mSoftKey.setOnClickListner(softKeyListner);
		mSoftKey.setTag(".");
		mDisplayKeyList.add(mSoftKey);

		final int KEY_ENTER_WIDTH=136;
		mKeyEnter=new PosButton();
		mKeyEnter.setEnabled(mIsMultiLine);
		mKeyEnter.setPreferredSize(new Dimension(KEY_ENTER_WIDTH, PosSoftKey.KEY_HEIGHT));
		mKeyEnter.setTag(KeyEvent.VK_ENTER);
		mKeyEnter.setOnClickListner(funKeyListner);
		mKeyEnter.setImage(PosResUtil.getImageIconFromResource(SOFT_FUN_KEY_ENTER_IMAGE));
		mKeyEnter.setTouchedImage(PosResUtil.getImageIconFromResource(SOFT_FUN_KEY_ENTER_IMAGE_TOUCH));
		keyLayer.add(mKeyEnter);

		mKeyGo=new PosButton();
		mKeyGo.setPreferredSize(new Dimension(PosSoftKey.KEY_WIDTH, PosSoftKey.KEY_HEIGHT));
		mKeyGo.setImage(PosResUtil.getImageIconFromResource(SOFT_FUN_KEY_GO_IMAGE));
		mKeyGo.setTouchedImage(PosResUtil.getImageIconFromResource(SOFT_FUN_KEY_GO_IMAGE_TOUCH));
		mKeyGo.setOnClickListner(new PosButtonListnerAdapter() {
			@Override
			public void onClicked(PosButton button) {
				go();
			}
		});
		keyLayer.add(mKeyGo);

		return keyLayer;
	}
	
	private void go(){
//		if(mKeypadFormListner!=null &&  !mTextArea.getText().equals("")){
		if(mKeypadFormListner!=null){ // empty the contents of the textfield
			mKeypadFormListner.onAccepted(mTextArea.getText());
		}
	}

	private void PopulateKeys(){
		char [] keys=DISPLAY_CHARS.toCharArray();
		final int startIndex=(mCurDisplayMode==DisplayMode.Letters)?
				((mCurLetterCase==LetterCase.Lower)?LOWER_KEYS_START:UPPER_KEYS_START):
					NUM_KEYS_START+mCurPageIndex*CHAR_KEYS_PER_PAGE;
				for(int index=0; index<27; index++){
					mDisplayKeyList.get(index).setText(String.valueOf(keys[startIndex+index]));
					mDisplayKeyList.get(index).setTag(keys[startIndex+index]);
				}
				setButtonText();
	}


	private IPosToggleButtonListner caseChangeListner=new IPosToggleButtonListner(){
		@Override
		public void onToggle(PosToggleButton button, Boolean selected) {
			mCurLetterCase=(mCurLetterCase==LetterCase.Lower)?LetterCase.Upper:LetterCase.Lower;
			PopulateKeys();
		}
	};

	private IPosButtonListner pageChangeListner=new PosButtonListnerAdapter(){
		@Override
		public void onClicked(PosButton button) {
			mCurPageIndex=(mCurPageIndex<mPageCount-1)?++mCurPageIndex:0;
			mKeyChangePages.setText(String.valueOf(mCurPageIndex+1)+"/"+String.valueOf(mPageCount));
			PopulateKeys();
		}
	};

	private IPosToggleButtonListner displayModeChangeListner=new IPosToggleButtonListner(){	
		@Override
		public void onToggle(PosToggleButton button, Boolean selected) {
			mCurDisplayMode=(mCurDisplayMode==DisplayMode.Letters)?DisplayMode.Numeric:DisplayMode.Letters;
			mPageCount=(mCurDisplayMode==DisplayMode.Letters)?1:(DISPLAY_CHARS.length()-NUM_KEYS_START)/CHAR_KEYS_PER_PAGE;
			PopulateKeys();
		}
	};

	private void setButtonText(){
		mKeyChangeCase.setVisible((mCurDisplayMode==DisplayMode.Letters));
		mKeyChangePages.setVisible((mCurDisplayMode==DisplayMode.Numeric));
		mKeySwichAlphaNum.setText((mCurDisplayMode==DisplayMode.Numeric)?
				((mCurLetterCase==LetterCase.Lower)?"abc":"ABC"):"123");
		mTextArea.requestFocus();
	}

	private IPosButtonListner softKeyListner=new PosButtonListnerAdapter() {
		@Override
		public void onClicked(PosButton button) {
			sendChars(String.valueOf(((PosButton)button).getTag()));
		}
	};

	private IPosButtonListner funKeyListner=new PosButtonListnerAdapter() {
		@Override
		public void onClicked(PosButton button) {
			PosFormUtil.sendKeys((int)((PosButton)button).getTag(),mTextArea);
		}
	};

	private void sendChars(String ch){
		String seletedText=mTextArea.getSelectedText();
		if(seletedText!=null && seletedText.length()>0){
			final int selStartIndex=mTextArea.getSelectionStart();
			final String selText=mTextArea.getText().substring(selStartIndex,mTextArea.getSelectionEnd());
			final StringBuffer text=new StringBuffer(mTextArea.getText());
			//			mTextArea.setText(text.replaceFirst(selText, ""));
			final String textModified=text.replace(selStartIndex,mTextArea.getSelectionEnd(),"").toString();
			mTextArea.setText(textModified);
			mTextArea.setCaretPosition(selStartIndex);
		}
		mTextArea.insert(String.valueOf(ch), mTextArea.getCaretPosition());
		mTextArea.requestFocus();
	}



	public void setKeypadListner(IPosSoftKeypadListner listner) {
		this.mKeypadFormListner = listner;
	}

	public void setText(String text){
		mTextArea.setText(text);
		mTextArea.selectAll();
	}

	public void setMultiline(Boolean multiline){
		mIsMultiLine=multiline;
		mTextArea.setLineWrap(multiline);
		mKeyEnter.setEnabled(multiline);
		if(!mIsMultiLine) {
			mTextArea.setColumns(30);
			mTextArea.setRows(1);
		}
	}

	public void setFocus(){
		mTextArea.requestFocus();
	}

	public interface IPosSoftKeypadListner{
		public void onAccepted(String text);
		public void onAccepted(int index);
		public void onCancel();
		public void onTextChanged(String text);
		/**
		 * @param e
		 */
		public void onKeyPressed(KeyEvent e);
	}

//	private class MaxLengthTextDoc extends PlainDocument {
//		private int mMaxChars;
//
//		public MaxLengthTextDoc(int maxchars){
//			mMaxChars=maxchars;
//		}
//
//		@Override
//		public void insertString(int offs, String str, AttributeSet a)
//				throws BadLocationException {
//			if(str != null && (getLength() + str.length() <=mMaxChars)){
//				super.insertString(offs, str, a);
//			}
//		}
//	}

	public void setActionButtonsDisabled(boolean isDisabled){
		mKeyGo.setVisible(!isDisabled);
		mKeyCancel.setVisible(!isDisabled);
		mKeySpace.setPreferredSize(new Dimension(LONG_KEY_SPACE_WIDTH, PosSoftKey.KEY_HEIGHT));
		mKeySpace.setImage(PosResUtil.getImageIconFromResource(SOFT_LONG_KEY_SPACE_IMAGE));
		mKeySpace.setTouchedImage(PosResUtil.getImageIconFromResource(SOFT_LONG_KEY_SPACE_IMAGE_TOUCH));
		mKeySpace.revalidate();
	}

}
