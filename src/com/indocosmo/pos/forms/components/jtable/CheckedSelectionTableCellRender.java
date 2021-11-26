/**
 * 
 */
package com.indocosmo.pos.forms.components.jtable;

import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import com.indocosmo.pos.common.utilities.PosResUtil;

/**
 * @author jojesh-13.2
 * Class to add check box behavior in JTable
 */
public class CheckedSelectionTableCellRender extends JLabel implements TableCellRenderer{

	private static final long serialVersionUID = 1L;
	private static final String CHK_IMAGE_UNCHECKED = "table_cell_normal.png";
	private static final String CHK_IMAGE_CHECKED = "table_cell_selected_transparant.png";

	private static ImageIcon CHECKED_IMAGE;
	private static ImageIcon UNCHECKED_IMAGE;

	private Boolean isSeclected=true;

	public CheckedSelectionTableCellRender(){

		loadImages();
	}

	/**
	 * Loads the image.
	 * TO avoid resource usage load onece
	 */
	private void loadImages(){

		if(CHECKED_IMAGE==null)
			CHECKED_IMAGE=PosResUtil.getImageIconFromResource(CHK_IMAGE_CHECKED);
		if(UNCHECKED_IMAGE==null)
			UNCHECKED_IMAGE=PosResUtil.getImageIconFromResource(CHK_IMAGE_UNCHECKED);
	}

	/* (non-Javadoc)
	 * @see javax.swing.table.TableCellRenderer#getTableCellRendererComponent(javax.swing.JTable, java.lang.Object, boolean, boolean, int, int)
	 */
	@Override
	public Component getTableCellRendererComponent(JTable arg0, Object arg1,
			boolean arg2, boolean arg3, int arg4, int arg5) {

		isSeclected=arg1.toString().equals("true");
		ImageIcon icon=(isSeclected)?CHECKED_IMAGE:UNCHECKED_IMAGE;
		this.setIcon(icon);

		return this;
	}

	/**
	 * @return the isSeclected
	 */
	public boolean isSeclected() {
		return isSeclected;
	}

	/**
	 * @param isSeclected the isSeclected to set
	 */
	public void setSeclected(boolean isSeclected) {
		this.isSeclected = isSeclected;
	}



}