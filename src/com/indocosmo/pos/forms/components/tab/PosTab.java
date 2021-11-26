package com.indocosmo.pos.forms.components.tab;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

@SuppressWarnings("serial")
public abstract class PosTab extends JPanel  {

	private String mTabCaption;
	protected Object mParent;
	protected KeyStroke keyStroke;
	protected Character mnemonicChar;
	private JComponent defaultComponent;
	protected boolean mIsDirty=false;
	
	private boolean isReadOnly=false;

	/**
	 * @return the isReadOnly
	 */
	public boolean isReadOnly() {
		return isReadOnly;
	}

	/**
	 * @param isReadOnly the isReadOnly to set
	 */
	public void setReadOnly(boolean isReadOnly) {
		this.isReadOnly = isReadOnly;
	}

	/**
	 * @return the isDirty
	 */
	public boolean isDirty() {
		return mIsDirty;
	}

	/**
	 * @param isDirty the isDirty to set
	 */
	public void setDirty(boolean isDirty) {
		this.mIsDirty = isDirty;
	}

	/**
	 * @return the posParentForm
	 */
	public Object getPosParent() {
		return mParent;
	}

	/**
	 * @param posParentForm the posParentForm to set
	 */
	public void setPosParent(Object posParent) {
		this.mParent = posParent;
	}

	public void setDefaultComponent(JComponent component){
		 defaultComponent=component;
	}
//	public PosTab(String caption){
//		mTabCaption=caption;
//	}
	
	public PosTab(Object parentForm,String caption){
		mTabCaption=caption;
		mParent=parentForm;
		
		addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent e) {
				// TODO Auto-generated method stub
				 
			}
			
			@Override
			public void focusGained(FocusEvent e) {
				
				onGotFocus();
			}
		});
		
		
	}
	
	public String getTabCaption() {
		return mTabCaption;
	}

	public boolean onValidating(){
		return true;
	}
	
	public void onGotFocus(){
		
		if(defaultComponent!=null)
			defaultComponent.requestFocusInWindow();
	}

	/**
	 * @return the keyStroke
	 */
	public KeyStroke getKeyStroke() {
		return keyStroke;
	}

	/**
	 * @param keyStroke the keyStroke to set
	 */
	public void setKeyStroke(KeyStroke keyStroke) {
		this.keyStroke = keyStroke;
	}

	/**
	 * @return the mnemonicChar
	 */
	public Character getMnemonicChar() {
		return mnemonicChar;
	}

	/**
	 * @param mnemonicChar the mnemonicChar to set
	 */
	public void setMnemonicChar(Character mnemonicChar) {
		this.mnemonicChar = mnemonicChar;
	}
	
	
	

}
