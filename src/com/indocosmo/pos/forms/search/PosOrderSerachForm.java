package com.indocosmo.pos.forms.search;

import java.util.ArrayList;

import com.indocosmo.pos.data.beans.BeanOrderHeader;
import com.indocosmo.pos.forms.components.search.IPosSearchableItem;

@SuppressWarnings("serial")
public class PosOrderSerachForm extends PosExtSearchForm {
	/**
	 * @param searchList
	 */
	public PosOrderSerachForm(ArrayList<? extends IPosSearchableItem> searchList) {
		
		super(BeanOrderHeader.ORDER_RETRIEVE_SEARCH_COLUMN_NAMES,
				BeanOrderHeader.ORDER_RETRIEVE_SEARCH_FIELD_LIST,
				searchList);
		setEnableHorizontalScroll(true);
		this.setFieldsWidth(BeanOrderHeader.ORDER_RETRIEVE_SEARCH_FIELD_WIDTH);
		this.setFieldsFormats(BeanOrderHeader.ORDER_RETRIEVE_SEARCH_FIELD_FORMATS);
//		this.SEARCH_RESULT_PANEL_WIDTH=PosSoftKeyPadSingleLineInput.LAYOUT_WIDTH+200;
//		this.LAYOUT_WIDTH = SEARCH_RESULT_PANEL_WIDTH + 10 ;
	}

	

}
