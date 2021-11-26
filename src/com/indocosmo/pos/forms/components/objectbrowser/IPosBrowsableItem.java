package com.indocosmo.pos.forms.components.objectbrowser;

import javax.swing.KeyStroke;

public interface IPosBrowsableItem {
	
	public Object getItemCode();
	
	public String getDisplayText();

	public boolean isVisibleInUI();
	
	public KeyStroke getKeyStroke();
	
	
}
