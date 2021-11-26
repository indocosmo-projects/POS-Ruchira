package com.indocosmo.pos.forms.components.terminal.settings;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.RootPaneContainer;
import javax.swing.border.LineBorder;

import com.indocosmo.pos.data.beans.terminal.BeanTerminalSetting;
import com.indocosmo.pos.forms.PosOrderEntryForm;
import com.indocosmo.pos.forms.components.tab.PosTab;

@SuppressWarnings("serial")
public abstract class PosTermnalSettingsBase extends PosTab {

	public static final int PANEL_CONTENT_H_GAP = PosOrderEntryForm.PANEL_CONTENT_H_GAP;
	public static final int PANEL_CONTENT_V_GAP = 5;

	public static final int LAYOUT_HEIGHT = 448;
	public static final int LAYOUT_WIDTH = 800;

	public PosTermnalSettingsBase(RootPaneContainer parent, String caption) {
		super(parent,caption);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Override this function to save the panel content.
	 * 
	 * @return
	 */
	public abstract boolean onSave();

	/**
	 * Sets the small help info panel at the bottom
	 * 
	 * @param info
	 */
	protected void setInfoPanel(final String info) {
		setInfoPanel(info, 50);
	}

	/**
	 * Sets the small help info panel at the bottom
	 * 
	 * @param info
	 */
	protected void setInfoPanel(final String info, final int height) {

		final int lblInfoHeight = height;
		final int lblInfoWidth = getWidth() - PANEL_CONTENT_H_GAP ;
		final int lblInfoLeft = 0;
		final int lblInfoTop = getHeight() - lblInfoHeight
				- PANEL_CONTENT_V_GAP * 2;

		final JLabel lableInfo = new JLabel(info);
		lableInfo.setBounds(lblInfoLeft, lblInfoTop, lblInfoWidth,
				lblInfoHeight);
		lableInfo.setBackground(Color.LIGHT_GRAY);
		lableInfo.setOpaque(true);
		lableInfo.setBorder(new LineBorder(Color.GRAY));
		add(lableInfo);

	}
	
	protected BeanTerminalSetting mTerminalSettings;
	/**
	 * @param terminalSettings
	 */
	public void setTerminalSettings(BeanTerminalSetting terminalSettings) {
		
		this.mTerminalSettings=terminalSettings;
	
	}

}
