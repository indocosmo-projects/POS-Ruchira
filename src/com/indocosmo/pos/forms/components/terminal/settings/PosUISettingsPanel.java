/**
 * 
 */
package com.indocosmo.pos.forms.components.terminal.settings;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JDialog;
import javax.swing.JTextField;

import com.indocosmo.pos.common.PosLog;
import com.indocosmo.pos.common.utilities.PosFormUtil;
import com.indocosmo.pos.data.beans.terminal.BeanUISettings;
import com.indocosmo.pos.data.providers.terminaldb.PosUISettingsProvider;
import com.indocosmo.pos.data.providers.terminaldb.PosUISettingsProvider.PosUISettingFields;
import com.indocosmo.pos.forms.PosOrderEntryForm;
import com.indocosmo.pos.forms.components.checkboxes.PosCheckBox;

/**
 * @author deepak
 *
 */
public class PosUISettingsPanel extends PosTermnalSettingsBase{


	
	private static final int PANEL_CONTENT_H_GAP=PosOrderEntryForm.PANEL_CONTENT_H_GAP;
	private static final int PANEL_CONTENT_V_GAP=PosOrderEntryForm.PANEL_CONTENT_V_GAP;
	private static final int CHECKBOX_HEIGHT = 40;
	private static final int CHECKBOX_WIDTH = 400;	
	
	private JDialog mParent;
	private PosCheckBox mShowLightBox;
	private PosCheckBox mUseColoredItemButtons;
	private PosCheckBox mShowAlternativeTitles;
	private PosCheckBox mShowSaleItemDetails;
	private PosCheckBox mShowDetailsInBillSummary;
	private PosCheckBox mHideClassPanel;
	BeanUISettings uiSettings;
	private PosCheckBox mUseSingleLineBillGrid;
	PosUISettingsProvider uiSettingProvider;
	private ArrayList<PosCheckBox> mCheckBoxList;
	/**
	 * 
	 */
	public PosUISettingsPanel(JDialog parent) {
		super(parent,"UI");
		mParent=parent;
		setSize(LAYOUT_WIDTH,LAYOUT_HEIGHT);
		setLayout(null);
		setOpaque(true);
		mCheckBoxList=new ArrayList<PosCheckBox>();
		setUIsettingsDetails();
	}
	/**
	 * 
	 */
	private void setUIsettingsDetails() {
		
		setUISettingsControls();
		setUISettings();
	}
	
	
	/**
	 *Create the check boxes.
	 */
	private void setUISettingsControls() {
		int left=PANEL_CONTENT_H_GAP;
		int top=PANEL_CONTENT_V_GAP;
		
		mShowLightBox = new PosCheckBox();
		mShowLightBox.setTag(PosUISettingFields.SHOW_LIGHT_BOX);
		mShowLightBox.setHorizontalAlignment(JTextField.LEFT);
		mShowLightBox.setBounds(left, top, CHECKBOX_WIDTH, CHECKBOX_HEIGHT);
		mShowLightBox.setFont(PosFormUtil.getTextFieldBoldFont());
		PosFormUtil.setCheckboxDefaultFont(mShowLightBox);
		mShowLightBox.setText("  Show Light Box");
		add(mShowLightBox);
		mCheckBoxList.add(mShowLightBox);
		
		top = mShowLightBox.getY()+mShowLightBox.getHeight()+PANEL_CONTENT_V_GAP;
		
		mUseColoredItemButtons = new PosCheckBox();
		mUseColoredItemButtons.setTag(PosUISettingFields.USE_COLORED_BUTTONS);
		mUseColoredItemButtons.setHorizontalAlignment(JTextField.LEFT);
		mUseColoredItemButtons.setBounds(left, top, CHECKBOX_WIDTH, CHECKBOX_HEIGHT);
		PosFormUtil.setCheckboxDefaultFont(mUseColoredItemButtons);
		mUseColoredItemButtons.setText("  Use Colored Item Buttons");
		add(mUseColoredItemButtons);
		mCheckBoxList.add(mUseColoredItemButtons);
		
		top = mUseColoredItemButtons.getY()+mUseColoredItemButtons.getHeight()+PANEL_CONTENT_V_GAP;
		
		mShowAlternativeTitles = new PosCheckBox();
		
		mShowAlternativeTitles.setHorizontalAlignment(JTextField.LEFT);
		mShowAlternativeTitles.setTag(PosUISettingFields.SHOW_ALTER_NAMES);
		mShowAlternativeTitles.setBounds(left, top, CHECKBOX_WIDTH, CHECKBOX_HEIGHT);
		PosFormUtil.setCheckboxDefaultFont(mShowAlternativeTitles);
		mShowAlternativeTitles.setText("  Show Alternative Titles");
		add(mShowAlternativeTitles);
		mCheckBoxList.add(mShowAlternativeTitles);
		
		top = mShowAlternativeTitles.getY()+mShowAlternativeTitles.getHeight()+PANEL_CONTENT_V_GAP;
		
		mShowSaleItemDetails = new PosCheckBox();
		mShowSaleItemDetails.setTag(PosUISettingFields.SHOW_SALEITEM_DETAILS);
		mShowSaleItemDetails.setHorizontalAlignment(JTextField.LEFT);
		mShowSaleItemDetails.setBounds(left, top, CHECKBOX_WIDTH, CHECKBOX_HEIGHT);
		PosFormUtil.setCheckboxDefaultFont(mShowSaleItemDetails);
		mShowSaleItemDetails.setText("  Show Sale Item Details");
		add(mShowSaleItemDetails);
		mCheckBoxList.add(mShowSaleItemDetails);
		
		top = mShowSaleItemDetails.getY()+mShowSaleItemDetails.getHeight()+PANEL_CONTENT_V_GAP;
		
		mUseSingleLineBillGrid = new PosCheckBox();
		mUseSingleLineBillGrid.setTag(PosUISettingFields.USE_SINGLE_ROW_GRID);
		mUseSingleLineBillGrid.setHorizontalAlignment(JTextField.LEFT);
		mUseSingleLineBillGrid.setBounds(left, top, CHECKBOX_WIDTH, CHECKBOX_HEIGHT);
		PosFormUtil.setCheckboxDefaultFont(mUseSingleLineBillGrid);
		mUseSingleLineBillGrid.setText("  Use Single Line Bill Grid");
		add(mUseSingleLineBillGrid);
		mCheckBoxList.add(mUseSingleLineBillGrid);
		
		top = mUseSingleLineBillGrid.getY()+mUseSingleLineBillGrid.getHeight()+PANEL_CONTENT_V_GAP;
		
		mShowDetailsInBillSummary = new PosCheckBox();
		mShowDetailsInBillSummary.setTag(PosUISettingFields.SHOW_DETAILS_IN_BILL_SUMMARY);
		mShowDetailsInBillSummary.setHorizontalAlignment(JTextField.LEFT);
		mShowDetailsInBillSummary.setBounds(left, top, CHECKBOX_WIDTH, CHECKBOX_HEIGHT);
		PosFormUtil.setCheckboxDefaultFont(mShowDetailsInBillSummary);
		mShowDetailsInBillSummary.setText("  Show Details In Bill Summary");
		add(mShowDetailsInBillSummary);
		mCheckBoxList.add(mShowDetailsInBillSummary);
		
		top = mShowDetailsInBillSummary.getY()+mShowDetailsInBillSummary.getHeight()+PANEL_CONTENT_V_GAP;
		
		mHideClassPanel = new PosCheckBox();
		mHideClassPanel.setTag(PosUISettingFields.HIDE_CLASS_PANEL);
		mHideClassPanel.setHorizontalAlignment(JTextField.LEFT);
		mHideClassPanel.setBounds(left, top, CHECKBOX_WIDTH, CHECKBOX_HEIGHT);
		PosFormUtil.setCheckboxDefaultFont(mHideClassPanel);
		mHideClassPanel.setText("  Hide Super Class Panel");
		add(mHideClassPanel);
		mCheckBoxList.add(mHideClassPanel);
		
	}
	
	/**
	 * Set the check boxes from the values from database.
	 * 
	 */
	private void setUISettings() {
		
		try {
			uiSettingProvider = new PosUISettingsProvider();
			uiSettings = uiSettingProvider.getUISettings();
			for(PosCheckBox chkBox:mCheckBoxList)
				chkBox.setSelected( uiSettings.getSettingMaps().get((PosUISettingFields)chkBox.getTag()));
				
		} catch (SQLException e) {
			PosLog.write(this, "setUISettings", e);
			PosFormUtil.showErrorMessageBox(mParent, "Error in UI settings. Please contact Administrator.");
		}
		
	}
		
	
	/* (non-Javadoc)
	 * @see com.indocosmo.pos.components.terminal.settings.PosTermnalSettingsBase#onSave()
	 */
	@Override
	public boolean onSave() {
		if(uiSettings == null){
			uiSettings= new BeanUISettings();
		}
		Map<PosUISettingFields, Boolean> uiSettingMap = new HashMap<PosUISettingFields, Boolean>();
		for(PosCheckBox chkBox:mCheckBoxList)
			uiSettingMap.put((PosUISettingFields)chkBox.getTag(), chkBox.isSelected());
		
		uiSettings.setSettingMaps(uiSettingMap);
		uiSettingProvider.saveSettings(uiSettings);
		
		return true;
	}

}
