/**
 * 
 */
package com.indocosmo.pos.forms;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import com.indocosmo.pos.common.PosEnvSettings;
import com.indocosmo.pos.common.PosLog;
import com.indocosmo.pos.common.utilities.PosDateUtil;
import com.indocosmo.pos.common.utilities.PosFormUtil;
import com.indocosmo.pos.common.utilities.PosNumberUtil;
import com.indocosmo.pos.data.providers.shopdb.PosTallyInvoiceExportProvider;
import com.indocosmo.pos.forms.components.orderentry.itemedit.PosTabOrderItemAttributeEdit;
import com.indocosmo.pos.forms.components.orderentry.itemedit.PosTabOrderItemComboContentEdit;
import com.indocosmo.pos.forms.components.orderentry.itemedit.PosTabOrderItemDetail;
import com.indocosmo.pos.forms.components.orderentry.itemedit.PosTabOrderItemExtrasEdit;
import com.indocosmo.pos.forms.components.orderentry.itemedit.PosTabOrderItemPriceDiscountEdit;
import com.indocosmo.pos.forms.components.orderentry.itemedit.PosTabOrderItemRemarksEdit;
import com.indocosmo.pos.forms.components.tab.PosTab;
import com.indocosmo.pos.forms.components.tab.PosTabControl;
import com.indocosmo.pos.forms.components.tallyexport.PosTabTallyDateWiseExport;
import com.indocosmo.pos.forms.components.tallyexport.PosTabTallyInvoiceNoWiseExport;
import com.indocosmo.pos.forms.components.textfields.PosTouchableDigitalField;
import com.indocosmo.pos.forms.components.textfields.listners.IPosTouchableFieldListner;
import com.indocosmo.pos.reports.export.PosTallyExcelExport;

/**
 * @author jojesh-13.2
 *
 */
public class PosTallyInvoiceExportForm extends PosBaseForm {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	


	/** Content panel HEIGHT & width **/
	private static final int CONTENT_PANEL_HEIGHT = 200;
	private static final int CONTENT_PANEL_WIDTH=465;
	 
	
	private JPanel mContentPanel;
	private PosTabControl mEditTab;
	PosTabTallyDateWiseExport mExportDateWise;
	PosTabTallyInvoiceNoWiseExport mExportInvNoWise;
	
	/*
	*
	 */
	public PosTallyInvoiceExportForm(){
		
		super("Tally Invoice Export", CONTENT_PANEL_WIDTH, CONTENT_PANEL_HEIGHT );
		
	}


	/* (non-Javadoc)
	 * @see com.indocosmo.pos.forms.PosBaseForm#setContentPanel(javax.swing.JPanel)
	 */
	@Override
	protected void setContentPanel(JPanel panel) {

		mContentPanel=panel;
		setTabContents();
		 
	}

	private void setTabContents() {
		
		ArrayList<PosTab> tabs = new ArrayList<PosTab>();

		mExportDateWise = new PosTabTallyDateWiseExport(this );
		tabs.add(mExportDateWise);
 
		mExportInvNoWise=new PosTabTallyInvoiceNoWiseExport(this);
		tabs.add(mExportInvNoWise);
		
		mEditTab = new PosTabControl(CONTENT_PANEL_WIDTH , CONTENT_PANEL_HEIGHT
				 );
		 
		mEditTab.setTabs(tabs);
		mContentPanel.add(mEditTab);
	}
/* (non-Javadoc)
 * @see com.indocosmo.pos.forms.PosBaseForm#onOkButtonClicked()
 */
@Override
public boolean onOkButtonClicked() {
	if (mEditTab.getSelectedTab().equals(mExportInvNoWise))
		return mExportInvNoWise.onOkButtonClicked();
	else
		return mExportDateWise.onOkButtonClicked();
 }
	 
}
