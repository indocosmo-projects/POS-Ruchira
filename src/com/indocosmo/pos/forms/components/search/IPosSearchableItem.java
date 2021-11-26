package com.indocosmo.pos.forms.components.search;

import com.indocosmo.pos.forms.components.objectbrowser.IPosBrowsableItem;


public interface IPosSearchableItem extends IPosBrowsableItem{

	/***
	 * the list of function names which returns the value for each column
	 * @return
	 */
	abstract public String[] getFieldList();
	/***
	 * the list of title for each column
	 * @return
	 */
	abstract public String[] getFieldTitleList();
	/***
	 * The list of size for each column
	 * @return
	 */
	abstract public int[] getFieldWidthList();
	
	/**
	 * @return
	 */
	abstract public String[] getFieldFormatList();
	
	
}
