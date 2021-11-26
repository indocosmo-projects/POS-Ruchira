package com.indocosmo.pos.forms.components.buttons;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;

import com.indocosmo.pos.common.utilities.PosFormUtil;
import com.indocosmo.pos.common.utilities.PosResUtil;
import com.indocosmo.pos.forms.components.buttons.listners.IPosButtonListner;

@SuppressWarnings("serial")
public class PosButton extends JButton {

	public static final Color FOREGROUND_COLOR=Color.WHITE;
	
	public enum ButtonStyle{
		COLORED,
		IMAGE,
		BOTH;
		
	}

	protected ImageIcon mImage=null;
	protected ImageIcon mTouchedImage=null;
	protected Color normalBGColor=null;
	protected Color touchedBGColor=null;
	protected ButtonStyle buttonStyle=ButtonStyle.IMAGE;
	protected String text;
	protected Boolean mIsTextWrap=false;
	private Object mTag;
	private Character mnemonicChar;
	private boolean autoMnemonicEnabled=true;

	/*
	 * New Interface for listner
	 */
	protected IPosButtonListner mOnClickListner;


	public PosButton(String text) {
		super(text);
		initComponent();
	}

	public PosButton(Icon image) {
		super(image);
		initComponent();
	}
	
	final public void setImage(String normalImageFile, String touchedImageFile){
		setImage(PosResUtil.getImageIconFromResource(normalImageFile),PosResUtil.getImageIconFromResource(touchedImageFile));
	}

	final public void setImage(String filename){
		setImage(PosResUtil.getImageIconFromResource(filename));
	}
	
	final public void setImage(ImageIcon normalImage, ImageIcon touchImage){
		setImage(normalImage);
		setTouchedImage(touchImage);
	}

	final public void setImage(ImageIcon ico){
		mImage=ico;
		setLookNFeel();
	}

	final public void setTouchedImage(String filename){
		setTouchedImage(PosResUtil.getImageIconFromResource(filename));
	}

	final public void setTouchedImage(ImageIcon ico){
		mTouchedImage =ico;
	}
	
	protected void onClicked(){
			if(mOnClickListner!=null)
				mOnClickListner.onClicked(this);
	}
	
	public void setButtonStyle(ButtonStyle btnstyle){
		buttonStyle=btnstyle;
	}
	
	public void setBackgroundColor(Color color){
		touchedBGColor=color.darker().darker();
		setBackgroundColor(color, touchedBGColor);
	}
	
	public void setBackgroundColor(Color noramlColor, Color pressedColor){
		normalBGColor=noramlColor;
		touchedBGColor=pressedColor;
		setLookNFeel();
	}
	
	protected Color getNormalBGColor(){
		return isEnabled()?this.normalBGColor:Color.LIGHT_GRAY;
	}
	
	protected Color getTouchedBGColor(){
		return isEnabled()?this.touchedBGColor:Color.LIGHT_GRAY;
	}
	
	protected ButtonStyle getButtonStyle(){
		return this.buttonStyle; 
	}
	private void initComponent() {
		setListeners();
		setLookNFeel();
		setLayout(null);
		setFont(PosFormUtil.getButtonFont());
	}
	
	protected void setLookNFeel(){
		setForeground(FOREGROUND_COLOR);
		setFocusable(false);
		setVerticalTextPosition(SwingConstants.CENTER);
		setHorizontalTextPosition(SwingConstants.CENTER);
		switch (buttonStyle) {
		case IMAGE:
					setImageButoonLookNStyle();
			break;
		case COLORED:
					setColoredButtonLookNFeel();
			break;
		case BOTH:
					setImageButoonLookNStyle();
					setColoredButtonLookNFeel();
			break;
		}
		mIsTextWrap=false;
		setFont(PosFormUtil.getButtonFont());
	}
	

	protected void setImageButoonLookNStyle(){
		if(mImage!=null)
			setIcon(mImage);
		setOpaque(false);
		setContentAreaFilled(false);
		setBorderPainted(false);	
	}
	protected void setColoredButtonLookNFeel(){
		setOpaque(true);
		setBackground(getNormalBGColor());
	}

	public void setOnClickListner(IPosButtonListner listner) {
		this.mOnClickListner = listner;
	}
	
	public PosButton() {
		initComponent();
	}
	
	public final Object getTag() {
		return mTag;
	}

	public final void setTag(Object tag) {
		this.mTag = tag;
	}

	protected void setListeners(){
		addMouseListener(new MouseAdapter() {
			
			@Override
			public void mousePressed(MouseEvent e){
				
				switch (buttonStyle) {
				case IMAGE:
					setIcon(mTouchedImage!=null?mTouchedImage:mImage);
					break;
				case COLORED:
					setBackground(getTouchedBGColor());
					break;
				case BOTH:
					setIcon(mTouchedImage!=null?mTouchedImage:mImage);
					setBackground(getTouchedBGColor());
					break;
				}
				e.consume();
			}
			@Override
			public void mouseReleased(MouseEvent e){
				
				switch (buttonStyle) {
				case IMAGE:
					setIcon(mImage);
					break;
				case COLORED:
					setBackground(getNormalBGColor());
					break;
				case BOTH:
					setIcon(mImage);
					setBackground(getNormalBGColor());
					break;
				}
				e.consume();
			}
		});
		addActionListener(new PosImageButtonActionListner());
	}
	
	protected void onActionPerformed(){
		onClicked();
	}

	public void validateComponent() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setText(String text) {
		
		this.text=text;
		if(mIsTextWrap!=null && mIsTextWrap)
			text=getHTMLFormatedString(text);
		super.setText(text);
		if(this.text!=null && this.text.length()>0 && mnemonicChar==null && this.text.charAt(0)!='.' && autoMnemonicEnabled)
			setMnemonic(this.text.charAt(0));
	}
	
	/**
	 * @param text
	 * @return
	 */
	protected String getHTMLFormatedString(String text){
	
		
		return (mnemonicChar!=null)?
				PosFormUtil.getMnemonicString(text, mnemonicChar):
					"<html>"+text+"</html>";
	}
	
	/**
	 * @return
	 */
	protected String getHTMLFormatedString(){
		
		return getHTMLFormatedString(this.text);
	}
	
	/**
	 * @param text
	 * @param mnemonicChar
	 */
	public void setText(String text, char mnemonicChar){
		
		this.mnemonicChar=mnemonicChar;
		super.setMnemonic(mnemonicChar);
		this.text=text;
//		if(mIsTextWrap!=null && mIsTextWrap)
//			text=getHTMLFormatedString(text);;
		setText(text);
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.AbstractButton#setMnemonic(char)
	 */
	@Override
	public void setMnemonic(char mnemonic) {
	
		setText(text,mnemonic);
	}

	@Override
	public void setBounds(int x, int y, int width, int height) {
		super.setBounds(x, y, width, height);
		setPreferredSize(new Dimension(width, height));
	}

	@Override
	public void setSize(int width, int height) {
		super.setSize(width, height);
		setPreferredSize(new Dimension(width, height));
	}

	public Boolean getTextWrap() {
		return mIsTextWrap;
	}

	public void setTextWrap(Boolean isTextWrap) {
		this.mIsTextWrap = isTextWrap;
	}
	
	protected class PosImageButtonActionListner implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			onActionPerformed();
		}
	}
	
	public void setCancel(boolean cancel){
		if(cancel)
			registerKeyStroke(KeyEvent.VK_ESCAPE);
	}
	
	public void setDefaultButton(boolean defaultButton){
		if(defaultButton)
			registerKeyStroke(KeyEvent.VK_ENTER);
	}
	
	public void registerKeyStroke(int keyStroke, int modifier){
		
		registerKeyStroke(KeyStroke.getKeyStroke(keyStroke, modifier));
	}

	/**
	 * eg. "control alt f"
	 * @param keyStroke
	 */
	public void registerKeyStroke(String keyStroke){

		registerKeyStroke(KeyStroke.getKeyStroke(keyStroke));
	}

	public void registerKeyStroke(KeyStroke keyStroke){
		
		if(keyStroke==null) return;

		this.registerKeyboardAction(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				onKeyStrokeActivated();

			}
		}, keyStroke, JComponent.WHEN_IN_FOCUSED_WINDOW);
	}
	
	protected void onKeyStrokeActivated(){
		onClicked();
	}

	public void registerKeyStroke(int keyStroke){

		registerKeyStroke(keyStroke,0);
	}
	/* (non-Javadoc)
	 * @see javax.swing.AbstractButton#setEnabled(boolean)
	 */
	@Override
	public void setEnabled(boolean b) {
		// TODO Auto-generated method stub
		super.setEnabled(b);
		if(buttonStyle==ButtonStyle.BOTH||buttonStyle==ButtonStyle.COLORED)
			setBackground(getNormalBGColor());
	}

	/**
	 * @return the autoMnemonicenabled
	 */
	public boolean isAutoMnemonicenabled() {
		return autoMnemonicEnabled;
	}

	/**
	 * @param autoMnemonicenabled the autoMnemonicenabled to set
	 */
	public void setAutoMnemonicEnabled(boolean autoMnemonicenabled) {
		this.autoMnemonicEnabled = autoMnemonicenabled;
	}
}
