/**
 * 
 */
package com.indocosmo.pos.forms.components.jtable;

import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * @author jojesh-13.2
 * To avoid border in the cell when it is selected.
 *
 */
public class NoBorderTableCellSelectionRender extends DefaultTableCellRenderer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public NoBorderTableCellSelectionRender() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see javax.swing.table.DefaultTableCellRenderer#getTableCellRendererComponent(javax.swing.JTable, java.lang.Object, boolean, boolean, int, int)
	 */
	@Override
	public Component getTableCellRendererComponent(JTable arg0, Object arg1,
			boolean arg2, boolean arg3, int arg4, int arg5) {
		// TODO Auto-generated method stub
		return super.getTableCellRendererComponent(arg0, arg1, arg2, false, arg4, arg5);
	}
	
	

}
