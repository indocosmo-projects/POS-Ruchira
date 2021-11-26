/**
 * 
 */
package com.indocosmo.pos.forms.components.textfields;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.RootPaneContainer;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;

import com.indocosmo.pos.common.utilities.PosFormUtil;
import com.indocosmo.pos.common.utilities.PosResUtil;
import com.indocosmo.pos.forms.components.buttons.PosButton;
import com.indocosmo.pos.forms.components.buttons.listners.IPosButtonListner;
import com.indocosmo.pos.forms.components.textfields.listners.IPosTouchableFieldListner;


/**
 * @author jojesh
 *
 */
@SuppressWarnings("serial")
public abstract class PosTouchableFieldBase extends JPanel {
//
	public static final String CLICK_BUTTON_NORMAL="click_textfield_button.png";
	public static final String CLICK_BUTTON_TOUCHED="click_textfield_button_touch.png";
	
	public static final String RESET_BUTTON_NORMAL="ctrl_reset.png";
	public static final String RESET_BUTTON_TOUCHED="ctrl_reset_touch.png";
	
	private static final int PANEL_CONTENT_V_GAP=1;
	private static final int PANEL_CONTENT_H_GAP=1;

	public static final int BROWSE_BUTTON_HEIGHT=40;
	public static final int BROWSE_BUTTON_WIDTH=50;
	
	public static final int RESET_BUTTON_DEF_HEIGHT=40;
	public static final int RESET_BUTTON_DEF_WIDTH=50;
	
	private static final int TEXTFLD_DEF_WIDTH=150;
//	private static final int TITLE_DEF_WIDTH=100;

	public static final int LAYOUT_DEF_HEIGHT=BROWSE_BUTTON_HEIGHT+PANEL_CONTENT_V_GAP*2;
	public static final int LAYOUT_DEF_WIDTH=RESET_BUTTON_DEF_WIDTH+TEXTFLD_DEF_WIDTH+BROWSE_BUTTON_WIDTH+PANEL_CONTENT_H_GAP*3;

	private static ImageIcon mClickButtonIconNormal=null;
	private static ImageIcon mClickButtonIconTouched=null;
	
	private static ImageIcon mResetButtonIconNormal=null;
	private static ImageIcon mResetButtonIconTouched=null;

	protected JTextField mTextFiled;
	private PosButton mKeyPadButton;
	private PosButton mResetButton;
	protected IPosTouchableFieldListner mListner;
	protected String mTitle;
	private boolean mIsResetButtonHidden;
	private boolean mIsBrowseButtonHidden;
	private int mHeight;
	private int mWidth;
	protected RootPaneContainer mParent;
	private SelectionType mSelectionType;
	protected Object mDiffaultValue;
	protected DocumentListener mDocumentListener;
	protected Document mFieldDocument;
	 
	public enum SelectionType{
		Numeric,
		Text,
		Search,
		Browse;
		
	}
	
	/**
	 * 
	 */
	public PosTouchableFieldBase(RootPaneContainer parent) {
		mParent=parent;
		mWidth=LAYOUT_DEF_WIDTH;
		mHeight=LAYOUT_DEF_HEIGHT;
		initComponent();
	}

	/**
	 * @param width
	 */
	public PosTouchableFieldBase( RootPaneContainer parent,int width) {
		mParent=parent;
		mWidth=width;
		mHeight=LAYOUT_DEF_HEIGHT;
		initComponent();
	}

	/**
	 * @param width
	 * @param height
	 */
	public PosTouchableFieldBase( RootPaneContainer parent,int width,int height) {
		mParent=parent;
		mWidth=width;
		mHeight=height;
		initComponent();
	}

	/**
	 * 
	 */
	protected void initComponent(){
		initLayout();
		initTextField();
		initButton();
		setDocType();
		
		this.setFocusable(true);
	}

	/**
	 * 
	 */
	private void initLayout(){
		
		this.setSize(mWidth, mHeight);
		this.setPreferredSize(new Dimension(mWidth, mHeight));
		this.setLayout(new FlowLayout(FlowLayout.CENTER,PANEL_CONTENT_H_GAP,PANEL_CONTENT_V_GAP));
		
	}

	/**
	 * 
	 */
	private void initTextField(){
	
		final int width=mWidth-PANEL_CONTENT_H_GAP*4-(BROWSE_BUTTON_WIDTH+RESET_BUTTON_DEF_WIDTH);
		final int height=mHeight-PANEL_CONTENT_V_GAP*2;
		createTextField();
		mTextFiled.setSize(width, height);
		mTextFiled.setPreferredSize(new Dimension(width, height));
		mTextFiled.setFont(PosFormUtil.getTextFieldFont());	
		mTextFiled.setHorizontalAlignment(((mSelectionType==SelectionType.Numeric)?JTextField.RIGHT:JTextField.LEFT));
		mTextFiled.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(mTextFiled.isEditable())
					onValueSelected(mTextFiled.getText());
					
			}
		});
		
		mTextFiled.addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent e) {
//				mTextFiled.setBackground(Color.RED);
			}
			
			@Override
			public void focusGained(FocusEvent e) {
				
//				mTextFiled.setBackground(Color.GREEN);
			}
		});
		final Action keyPadAction = new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showBroswerForm();
		}};
		
		final String keyPadShortCut = "alt ENTER";
		final KeyStroke keyPadKeyStroke = KeyStroke.getKeyStroke(keyPadShortCut);
		mTextFiled.getInputMap().put(keyPadKeyStroke, keyPadShortCut);
		mTextFiled.getActionMap().put(keyPadShortCut, keyPadAction);
		
		final Action valueRestAction = new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				onResetButtonClicked();
		}};
		
		final String valueResetShortCut = "alt DELETE";
		final KeyStroke valueResetStroke = KeyStroke.getKeyStroke(valueResetShortCut);
		mTextFiled.getInputMap().put(valueResetStroke, valueResetShortCut);
		mTextFiled.getActionMap().put(valueResetShortCut, valueRestAction);
		
		this.add(mTextFiled);
	}
	
	protected void createTextField(){
		mTextFiled=new JTextField();
	}

	/**
	 * 
	 */
	private void initButton(){
		
		final int width=BROWSE_BUTTON_WIDTH;
		final int height=BROWSE_BUTTON_HEIGHT;
		mKeyPadButton=new PosButton();
		mKeyPadButton.setFocusable(false);
		mKeyPadButton.setSize(width, height);
		mKeyPadButton.setPreferredSize(new Dimension(width, height));
		mKeyPadButton.setText("...");
		mKeyPadButton.setOnClickListner(new IPosButtonListner() {
			
			@Override
			public void onClicked(PosButton button) {
				showBroswerForm();
			}
		});
		
		loadItemImages();
		mKeyPadButton.setImage(mClickButtonIconNormal);
		mKeyPadButton.setTouchedImage(mClickButtonIconTouched);	
		this.add(mKeyPadButton);
		
		final int resetWidth=RESET_BUTTON_DEF_WIDTH;
		final int resetHeight=RESET_BUTTON_DEF_HEIGHT;
		mResetButton=new PosButton();
		mResetButton.setSize(resetWidth, resetHeight);
		mResetButton.setPreferredSize(new Dimension(width, height));
		mResetButton.setFocusable(false);
		mResetButton.setOnClickListner(new IPosButtonListner() {
			@Override
			public void onClicked(PosButton button) {
				onResetButtonClicked();
			}
		});
		loadItemImages();
		mResetButton.setImage(mResetButtonIconNormal);
		mResetButton.setTouchedImage(mResetButtonIconTouched);	
		this.add(mResetButton);
	}
	
	protected void onResetButtonClicked(){
		reset();
	}
	
	public void reset(){
		mTextFiled.setText("");
		mTextFiled.requestFocus();	
		if(mListner!=null)
			mListner.onReset();
		
	}
	
	public void setDefaultValue(Object value){
		mDiffaultValue=value;
	}
	
	public void hideResetButton(boolean hidden){
		mIsResetButtonHidden=hidden;
		mResetButton.setVisible(!hidden);
		resetTextField();
	}
	public void hideBrowseButton(boolean hidden){
		mIsBrowseButtonHidden=hidden;
		mKeyPadButton.setVisible(!hidden);
		resetTextField();
	}
	
	private void resetTextField(){
		
		final int width=mWidth-PANEL_CONTENT_H_GAP*2-(((mIsBrowseButtonHidden)?0:(BROWSE_BUTTON_WIDTH+PANEL_CONTENT_H_GAP))+((mIsResetButtonHidden)?0:(RESET_BUTTON_DEF_WIDTH+PANEL_CONTENT_H_GAP)));
		final int height=mHeight-PANEL_CONTENT_V_GAP*2;
		mTextFiled.setSize(width, height);
		mTextFiled.setPreferredSize(new Dimension(width, height));
		mTextFiled.revalidate();
	}
	
	/**
	 * 
	 */
	private void loadItemImages(){
		if(mClickButtonIconNormal==null)
			mClickButtonIconNormal=PosResUtil.getImageIconFromResource(CLICK_BUTTON_NORMAL);
		if(mClickButtonIconTouched==null)
			mClickButtonIconTouched=PosResUtil.getImageIconFromResource(CLICK_BUTTON_TOUCHED);
		if(mResetButtonIconNormal==null)
			mResetButtonIconNormal=PosResUtil.getImageIconFromResource(RESET_BUTTON_NORMAL);
		if(mResetButtonIconTouched==null)
			mResetButtonIconTouched=PosResUtil.getImageIconFromResource(RESET_BUTTON_TOUCHED);
	}
	
	/**
	 * @param text
	 */
	public void setText(String text){
		
		mTextFiled.setText(text);
		if(text!=null && text.trim().length()>0)
			mTextFiled.setCaretPosition(0);

	}
	
	public void setTitle(String text){
		mTitle=text;
	}
	
	/**
	 * @return
	 */
	public String getText(){
		return mTextFiled.getText();
	}
	
	public void setTextFont(Font font){
		mTextFiled.setFont(font);
	}
	
	public void setHorizontalTextAlignment(int align){
		mTextFiled.setHorizontalAlignment(align);
	}
	
	
	
	public JTextField getTexFieldComponent(){
		return mTextFiled;
	}
	
	public void setEditable(boolean editable){
		
		mTextFiled.setFocusable(editable);
		mTextFiled.setEditable(editable);
		mKeyPadButton.setEnabled(editable);
		mResetButton.setEnabled(editable);
	}
	
	/**
	 * @return
	 */
	public boolean isEditable(){
		
		return mTextFiled.isEditable();
	}
	
	/* (non-Javadoc)
		 * @see javax.swing.JComponent#setEnabled(boolean)
		 */
	@Override
	public void setEnabled(boolean enabled) {
		setEditable(enabled);
	}

	/**
	 * @param mListner the mListner to set
	 */
	public void setListner(IPosTouchableFieldListner listner) {
		this.mListner = listner;
	}
	
	@Override
	public void requestFocus() {
		if(mTextFiled.isEditable())
			mTextFiled.requestFocus();
		else
			mKeyPadButton.requestFocus();
	}
	
	@Override
	public boolean requestFocus(boolean temporary) {
		return mTextFiled.requestFocus(temporary);
	}
	
	/* (non-Javadoc)
	 * @see java.awt.Component#setFocusable(boolean)
	 */
	@Override
	public void setFocusable(boolean focusable) {
		// TODO Auto-generated method stub
		mTextFiled.setFocusable(focusable);
	}
	
	public void setSelectedAll(){
		mTextFiled.selectAll();
	}
	
	public void setTextReadOnly(boolean isReadOnly){
		mTextFiled.setEditable(!isReadOnly);
		mKeyPadButton.setEnabled(true);
		mResetButton.setEnabled(true);
	}
	
	public void selectAll(){
		mTextFiled.selectAll();
	}
	
	public void doAction(){
		showBroswerForm();
	}
	
	protected abstract void showBroswerForm();
	
	protected  void onValueSelected(Object value){
		
		mTextFiled.requestFocus();
		mTextFiled.setCaretPosition(0);
//		mTextFiled.setCaretPosition(0);
		if(mListner!=null)
			mListner.onValueSelected(value);
	}
	
	public void setFiledDocumentListner(DocumentListener documentListener){
		mDocumentListener=documentListener;
		mTextFiled.getDocument().addDocumentListener(mDocumentListener);
			
	}
	
	
	public void setFiledDocument(Document document){
		mFieldDocument=document;
		mTextFiled.setDocument(mFieldDocument);
	}
	
	/* (non-Javadoc)
		 * @see javax.swing.JComponent#setBackground(java.awt.Color)
		 */
	@Override
	public void setBackground(Color bg) {
		super.setBackground(bg);
		if(mTextFiled!=null)
			mTextFiled.setBackground(bg);
	}
	
	protected void setDocType(){
		
	}
	
	/* (non-Javadoc)
	 * @see java.awt.Component#addFocusListener(java.awt.event.FocusListener)
	 */
	@Override
	public synchronized void addFocusListener(FocusListener l) {
		mTextFiled.addFocusListener(l);
	}

	/**
	 * @return the mKeyPadButton
	 */
	public PosButton getBrowseButton() {
		return mKeyPadButton;
	}

	/**
	 * @return the mResetButton
	 */
	public PosButton getResetButton() {
		return mResetButton;
	}
	
	/**
	 * @param mnemonic
	 */
	public void setMnemonic(char mnemonic){
		
		if(mKeyPadButton!=null)
			mKeyPadButton.setMnemonic(mnemonic);
	}

}
