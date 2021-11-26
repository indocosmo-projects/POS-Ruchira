package com.indocosmo.pos.forms.components.buttons;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import com.indocosmo.pos.common.utilities.PosResUtil;
import com.indocosmo.pos.forms.components.buttons.listners.IPosSelectButtonListner;

@SuppressWarnings("serial")
public class PosSelectButton extends PosButton {

	protected ImageIcon mSelectedImage;
	protected Boolean mIsSelected=false;
	protected Color mSelectedTextColor=Color.BLACK;
	protected Color mSelectedBGColor=Color.CYAN;
	protected Color mNormalTextColor=Color.WHITE;
	
	private IPosSelectButtonListner mOnSelectListner;

	/**
	 * @param text
	 */
	public PosSelectButton(String text) {
		super(text);

	}

	/**
	 * @param image
	 */
	public PosSelectButton(Icon image) {
		super(image);

	}

	/**
	 * 
	 */
	public PosSelectButton() {

	}
	
	/**
	 * @return the Selected Text Color
	 */
	public Color getSelectedTextColor() {
		return mSelectedTextColor;
	}

	/**
	 * @param Color of Selected Text Color to set
	 */
	public void setSelectedTextColor(Color color) {
		this.mSelectedTextColor = color;
	}
	/**
	 * @return
	 */
	public Color getSelectedBGColor(){
		return this.mSelectedBGColor;
	}
	/**
	 * @param color
	 */
	public void setSelectedBackgroundColor(Color color){
		mSelectedBGColor=color;
	}
	

	/**
	 * @param noramlColor
	 * @param pressedColor
	 * @param selectedColor
	 */
	public void setBackgroundColor(Color noramlColor, Color pressedColor, Color selectedColor) {
		
		setBackgroundColor(noramlColor, pressedColor);
		setSelectedBackgroundColor(selectedColor);
	}

	/**
	 * @return the Normal Text Color
	 */
	public Color getNormalTextColor() {
		return mNormalTextColor;
	}

	/**
	 * @param Color of Normal Text Color to set
	 */
	public void setNormalTextColor(Color normalTextColor) {
		this.mNormalTextColor = normalTextColor;
	}
	
	final public void setSelectedImage(ImageIcon ico){
		mSelectedImage=ico;
	}
	
	
	final public void setSelectedImage(String filename){
		setSelectedImage(PosResUtil.getImageIconFromResource(filename));
	}
	
	final public boolean isSelected() {
		return mIsSelected;
	}
	
	protected void onSelected(){
	if(mIsSelected)
		if(mOnSelectListner!=null)
			mOnSelectListner.onSelected(this);
	}
	
	@Override
	public void setSelected(boolean isSelected) {
		if(mIsSelected==isSelected) return;
		mIsSelected = isSelected;
		ImageIcon ico=(mIsSelected)?getSelectedImage():mImage;
		setForeground(((mIsSelected)?mSelectedTextColor:mNormalTextColor));
		setBackground(((mIsSelected)?mSelectedBGColor:normalBGColor));
		if(ico!=null&&this.buttonStyle!=ButtonStyle.COLORED)
			setIcon(ico);
		onSelected();
	}
	
	public void setOnSelectListner(IPosSelectButtonListner listner) {
		this.mOnSelectListner = listner;
	}
	
	@Override
	protected void setListeners() {
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e){
				if(mTouchedImage!=null && buttonStyle!=ButtonStyle.COLORED)
					setIcon(mTouchedImage);
				if(normalBGColor!=null)
					setBackground(isSelected()?mSelectedBGColor:getTouchedBGColor());
			}
			@Override
			public void mouseReleased(MouseEvent e){
				ImageIcon icon=(isSelected())?getSelectedImage():mImage; 
				if(icon!=null && buttonStyle!=ButtonStyle.COLORED)
					setIcon(icon);
				if(touchedBGColor!=null)
					setBackground(isSelected()?mSelectedBGColor:getNormalBGColor());
			}
		});
		addActionListener(new PosImageButtonActionListner());
	}
	
	private ImageIcon getSelectedImage(){
		return (mSelectedImage==null)?mTouchedImage:mSelectedImage;
	}
	
	@Override
	protected void onActionPerformed(){
		setSelected(true);
	}
	
	/* (non-Javadoc)
	 * @see com.indocosmo.pos.forms.components.buttons.PosButton#onKeyStrokeActivated()
	 */
	@Override
	protected void onKeyStrokeActivated() {
		 onActionPerformed();
	}

}
