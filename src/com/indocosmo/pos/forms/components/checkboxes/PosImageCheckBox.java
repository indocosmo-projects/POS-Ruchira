/**
 * 
 */
package com.indocosmo.pos.forms.components.checkboxes;

import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JCheckBox;

import com.indocosmo.pos.common.utilities.PosFormUtil;
import com.indocosmo.pos.common.utilities.PosResUtil;

/**
 * @author jojesh-13.2
 *
 */
public class PosImageCheckBox extends PosCheckBox {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final int LAYOUT_HEIGHT=45;
	private static final String CHK_BUTTON_NORMAL="check_box_normal.png";
	private static final String CHK_BUTTON_SELECTED="check_box_selected.png";
	
	private static ImageIcon ICON_NORMAL;
	private static ImageIcon ICON_TOUCHED;
	/**
	 * @param string
	 */
	public PosImageCheckBox(String string,int width) {
		super(string);
		initControls();
		setWidth(width);
	}

	/**
	 * 
	 */
	public PosImageCheckBox() {
		// TODO Auto-generated constructor stub
	}

	private void loadImages(){
		if(ICON_NORMAL==null)
			ICON_NORMAL=PosResUtil.getImageIconFromResource(CHK_BUTTON_NORMAL);
		if(ICON_TOUCHED==null)
			ICON_TOUCHED=PosResUtil.getImageIconFromResource(CHK_BUTTON_SELECTED);
	}
	
	/**
	 * 
	 */
	private void initControls(){
		setOpaque(false);
//		setLayout(new FlowLayout(FlowLayout.LEFT,1, 10));
//		setBackground(Color.RED);
		loadImages();
		setHorizontalAlignment(JCheckBox.LEFT);
		setVerticalAlignment(JCheckBox.TOP);
		setFont(PosFormUtil.getLabelFont());	
		setIcon(ICON_NORMAL);
		setSelectedIcon(ICON_TOUCHED);
		setFocusPainted(false);
		setSelected(true);
	}
	
	/**
	 * @param width
	 */
	public void setWidth(int width){
		setSize(new Dimension(width,LAYOUT_HEIGHT));
		setPreferredSize(new Dimension(width,LAYOUT_HEIGHT));
	}
}
