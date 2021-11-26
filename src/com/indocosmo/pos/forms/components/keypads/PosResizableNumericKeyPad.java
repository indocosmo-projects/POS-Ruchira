/**
 * 
 */
package com.indocosmo.pos.forms.components.keypads;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.RootPaneContainer;
import javax.swing.text.JTextComponent;

import com.indocosmo.pos.common.utilities.PosFormUtil;

/**
 * @author jojesh
 *
 */
public class PosResizableNumericKeyPad extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final int PANEL_CONTENT_V_GAP=1;
	private static final int PANEL_CONTENT_H_GAP=1;
	
	private static final int KEY_LEFT_PANEL_ROWS=4;
	private static final int KEY_LEFT_PANEL_COLS=3;
	private static final int KEY_RIGHT_PANEL_ROWS=2;
	private static final int KEY_RIGHT_PANEL_COLS=1;
	private static final int KEY_COLS=KEY_LEFT_PANEL_COLS+KEY_RIGHT_PANEL_COLS;
	private static final int KEY_ROWS=(KEY_LEFT_PANEL_ROWS>KEY_RIGHT_PANEL_ROWS)?KEY_LEFT_PANEL_ROWS:KEY_RIGHT_PANEL_ROWS;
	private static final String SPECIAL_KEY_CLEAR="C";
	private static final String SPECIAL_KEY_ALL_CLEAR="AC";
	private static final String SPECIAL_KEY_PLUS_MINUS="+/-";
	private static final String SPECIAL_KEY_DOT=".";
	private static final Color KEY_ENABLED_BG_COLOR=Color.YELLOW;
	private static final Color KEY_DISABLED_BG_COLOR=Color.LIGHT_GRAY;
	

	private static final String[] LEFT_PANEL_KEYS=new String[]{
		
		"1",
		"2",
		"3",
		"4",
		"5",
		"6",
		"7",
		"8",
		"9",
		"0",
		".",
		SPECIAL_KEY_PLUS_MINUS
	};
	
	private static final String[] RIGHT_PANEL_KEYS=new String[]{
		
		SPECIAL_KEY_CLEAR,
		SPECIAL_KEY_ALL_CLEAR
	};
	
	private HashMap<String, JButton> keys;
	private int numKeyWidth;
	private int numKeyHeight;
	private JPanel panelLeft;
	private JPanel panelRight;
	private int mWidth;
	private int mHeight;
	private RootPaneContainer mParent;
	private JTextComponent displayComponent;
	
	public PosResizableNumericKeyPad(RootPaneContainer parent,int width,int height){
		
		this.mParent=parent;
		this.mWidth=width;
		this.mHeight=height;
		
		keys=new HashMap<String, JButton>();
		
		initComponent();
		
	}

	/**
	 * 
	 */
	private void initComponent() {

		this.setSize(new Dimension(mWidth,mHeight));
		this.setPreferredSize(new Dimension(mWidth,mHeight));
		this.setLayout(null);
		
		numKeyWidth=(mWidth-(PANEL_CONTENT_H_GAP*(KEY_COLS+1)))/KEY_COLS;
		numKeyHeight=(mHeight-(PANEL_CONTENT_V_GAP*(KEY_ROWS+1)))/KEY_ROWS;
		
		createLeftPanel();
		createRightPanel();
		
	}

	/**
	 * 
	 */
	private void createRightPanel() {

		final int width=numKeyWidth*(KEY_RIGHT_PANEL_COLS)+PANEL_CONTENT_H_GAP*(KEY_RIGHT_PANEL_COLS+1);
		final int height=getHeight();
		final int top=0;
		final int left=panelLeft.getX()+panelLeft.getWidth();
		final Dimension panelSize=new Dimension(width, height);
		
		panelRight=new JPanel();
		panelRight.setSize(panelSize);
		panelRight.setPreferredSize(panelSize);
		panelRight.setLayout(new FlowLayout(FlowLayout.LEFT,PANEL_CONTENT_H_GAP,PANEL_CONTENT_V_GAP));
		panelRight.setLocation(left,top);
		this.add(panelRight);
		
		final int keyWidth= numKeyWidth;
		final int keyHeight=numKeyHeight*2+PANEL_CONTENT_V_GAP;
		final Dimension keySize=new Dimension(keyWidth, keyHeight);
		
		for(String key:RIGHT_PANEL_KEYS){
			
			JButton keyButton=new JButton();
			keyButton.setText(key);
			keyButton.setSize(keySize);
			keyButton.addActionListener(buttonActionListener);
			keyButton.setPreferredSize(keySize);
			keyButton.setFocusable(false);
			keyButton.setBackground(Color.PINK);
			panelRight.add(keyButton);
			keys.put(key, keyButton);
		}
	}
	
		/**
	 * 
	 */
	private void createLeftPanel() {

		final int width=numKeyWidth*(KEY_LEFT_PANEL_COLS)+PANEL_CONTENT_H_GAP*(KEY_LEFT_PANEL_COLS+1);
		final int height=getHeight();
		final int top=0;
		final int left=0;
		final Dimension panelSize=new Dimension(width, height);
		
		panelLeft=new JPanel();
		panelLeft.setSize(panelSize);
		panelLeft.setPreferredSize(panelSize);
		panelLeft.setLayout(new FlowLayout(FlowLayout.CENTER,PANEL_CONTENT_H_GAP,PANEL_CONTENT_V_GAP));
		panelLeft.setLocation(left,top);
		this.add(panelLeft);
		
		final int keyWidth= numKeyWidth;
		final int keyHeight=numKeyHeight;
		final Dimension keySize=new Dimension(keyWidth, keyHeight);
		
		for(String key:LEFT_PANEL_KEYS){
			
			JButton keyButton=new JButton();
			keyButton.setText(key);
			keyButton.setSize(keySize);
			keyButton.setPreferredSize(keySize);
			keyButton.addActionListener(buttonActionListener);
			keyButton.setFocusable(false);
			keyButton.setBackground(KEY_ENABLED_BG_COLOR);
			panelLeft.add(keyButton);
			keys.put(key, keyButton);
		}
		
	}

	/**
	 * @return the displayComponent
	 */
	public Component getDisplayComponent() {
		return displayComponent;
	}

	/**
	 * @param displayComponent the displayComponent to set
	 */
	public void setDisplayComponent(JTextComponent displayComponent) {
		
		this.displayComponent = displayComponent;
	}
	
	/**
	 * @param isEnabled
	 */
	public void setPlusMinusKeyEnabled(boolean isEnabled){
		
		setKeyEnabled(SPECIAL_KEY_PLUS_MINUS, isEnabled);
	}
	
	/**
	 * @param b
	 */
	public void setDecimalKeyEnabled(boolean isEnabled) {

		setKeyEnabled(SPECIAL_KEY_DOT, isEnabled);
	}

	/**
	 * @param key
	 * @param isEnabled
	 */
	private void setKeyEnabled(String key,boolean isEnabled){
		
		JButton buton= keys.get(key);
		buton.setEnabled(isEnabled);
		buton.setBackground(((isEnabled)?KEY_ENABLED_BG_COLOR:KEY_DISABLED_BG_COLOR));
	}
	
	/**
	 * 
	 */
	private ActionListener buttonActionListener=new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent act) {
				
				final JButton btn=(JButton)act.getSource();
				final String key=btn.getText();
				
				switch (key) {
				case SPECIAL_KEY_CLEAR:
					PosFormUtil.sendKeys(KeyEvent.VK_BACK_SPACE, displayComponent);
					break;
				case SPECIAL_KEY_ALL_CLEAR:
					PosFormUtil.sendKeys(KeyEvent.VK_CONTROL,KeyEvent.VK_A, displayComponent);
					PosFormUtil.sendKeys(KeyEvent.VK_DELETE, displayComponent);
					break;
				case SPECIAL_KEY_DOT:{
					
					final String text=displayComponent.getText();
					if(text.trim().length()==0){
						PosFormUtil.sendKeys(KeyEvent.VK_0, displayComponent);
					}
					if(!text.contains(SPECIAL_KEY_DOT)){
						PosFormUtil.sendKeys(KeyEvent.VK_DECIMAL, displayComponent);
					}
					break;
				}
				case SPECIAL_KEY_PLUS_MINUS:
					String text=displayComponent.getText();
					if(text.trim().length()==0){
						PosFormUtil.sendKeys(KeyEvent.VK_MINUS, displayComponent);
					}else if(text.charAt(0)=='-') {
						text=text.substring(1);
						displayComponent.setText(text);
						
					}else{
						text="-"+text;
						displayComponent.setText(text);
					}
					break;
				default:
					char ckey=key.charAt(0);
					PosFormUtil.sendKeys(ckey, displayComponent);
					break;
				}
		}
	};
	
	
	/**
	 * Sets the clear buttons size
	 * @param width
	 */
	public void setClearButtonsWidth(int width){

		final Dimension curButtonSize=keys.get(SPECIAL_KEY_ALL_CLEAR).getSize();
		final Dimension newButtonSize=new Dimension(width,(int)curButtonSize.getHeight());
		final Dimension newPanelSize=new Dimension(this.getWidth()-(int)curButtonSize.getWidth()+width, this.getHeight());
		
		this.setSize(newPanelSize);
		this.setPreferredSize(newPanelSize);
		
		keys.get(SPECIAL_KEY_ALL_CLEAR).setSize(newButtonSize);
		keys.get(SPECIAL_KEY_ALL_CLEAR).setPreferredSize(newButtonSize);
		keys.get(SPECIAL_KEY_ALL_CLEAR).validate();
		
		keys.get(SPECIAL_KEY_CLEAR).setSize(newButtonSize);
		keys.get(SPECIAL_KEY_CLEAR).setPreferredSize(newButtonSize);
		keys.get(SPECIAL_KEY_CLEAR).validate();
//		this.invalidate();
//		this.revalidate();
		this.validate();
	}
	
}
