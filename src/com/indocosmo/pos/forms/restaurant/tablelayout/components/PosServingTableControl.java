/**
 * 
 */
package com.indocosmo.pos.forms.restaurant.tablelayout.components;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.RootPaneContainer;

import com.indocosmo.pos.common.utilities.PosFormUtil;
import com.indocosmo.pos.common.utilities.PosResUtil;
import com.indocosmo.pos.common.utilities.PosStringUtil;
import com.indocosmo.pos.data.beans.BeanServingTableExt;

/**
 * @author jojesh
 *
 */
public class PosServingTableControl extends JPanel {

	private static final Color SELECTED_BORDER_COLOR=new Color(255, 0, 100, 255);
	private static final Color SELECTED_FILL_COLOR=new Color(255, 0, 0, 35);
	private static final Color DEFAULT_BORDER_COLOR=Color.ORANGE.darker();
	private static final int SELECTED_BORDER_THICKNESS=3;
	private static final int SHADOW_THICKNESS=3;

	private static final String SELECTED_ICON="table_selected.png";
	private static final String OCC_ICON="table_occupied.png";
	private Color tableInfoTextColor=Color.decode("#FFFFFF");

	private static final Color TABLE_NORMAL_FILL_COLOR=Color.WHITE;
	private static final Font INFO_FONT=PosFormUtil.getTextAreaFont().deriveFont(12f).deriveFont(Font.BOLD);
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static ImageIcon selectedIcon;
	private static ImageIcon occIcon;

	private RootPaneContainer mParent;
	private BufferedImage image;
	private boolean selected;
	private boolean markedAsAdded;
	private boolean orderCountVisible=true;
	private boolean tableCodeVisible=true;
	private boolean seatCountVisible=false;

	private BeanServingTableExt table;

	public PosServingTableControl(RootPaneContainer parent){

		this.mParent=parent;
		setOpaque(false);

		addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent e) {
				if(e.getClickCount()==2 && !e.isConsumed())
				 	onDoubleClick( );

			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseClicked(MouseEvent e) {

				if(selected) return;

				setSelected(true);
				//				if(listener!=null)
				//					listener.onServingTableSelected(PosServingTableControl.this);
			}
		});
		setFont(INFO_FONT);
	}

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#paint(java.awt.Graphics)
	 */
	@Override
	public void paint(Graphics g) {

		final Graphics2D g2 = (Graphics2D) g;

		/*
		 * Save graphic object
		 */
		final Color color= g2.getColor();
		final Stroke stroke= g2.getStroke();
		
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		if(image==null)
			drawBox(g2);
		else
			drawImage(g2);

		drawTableInfo(g2);

		if(markedAsAdded)
			drawAddedIcon(g2);
		else if(table.getOrderCount()>0)
			drawOccupiedIcon(g2);

		if(isSelected())
			drawSelection(g2);

		/*
		 * Reset the graphic object
		 */
		g2.setStroke(stroke);
		g2.setColor(color);

		super.paint(g);

	}


	/**
	 * @param g2
	 */
	private void drawSelection(Graphics2D g2) {

		final Color borderColor=SELECTED_BORDER_COLOR;
		final Color fillColor=SELECTED_FILL_COLOR;
		final Stroke newStroke=new BasicStroke(SELECTED_BORDER_THICKNESS);

		g2.setStroke(newStroke);
		g2.setColor(borderColor);
		g2.drawRect(1, 1, getWidth()-SELECTED_BORDER_THICKNESS, getHeight()-SELECTED_BORDER_THICKNESS);
		g2.setColor(fillColor);
		g2.fillRect(1, 1, getWidth()-SELECTED_BORDER_THICKNESS, getHeight()-SELECTED_BORDER_THICKNESS);

	}

	/**
	 * @param g2
	 */
	private void drawAddedIcon(Graphics2D g2) {

		if(selectedIcon==null)
			selectedIcon=PosResUtil.getImageIconFromResource(SELECTED_ICON);

		g2.drawImage(selectedIcon.getImage(),this.getWidth()-selectedIcon.getIconWidth(),0,this.getWidth(),selectedIcon.getIconHeight(),
				0,0,selectedIcon.getIconWidth(),selectedIcon.getIconHeight(),null);

	}

	/**
	 * @param g2
	 */
	private void drawTableInfo(Graphics2D g2) {
		
		String tblInfo="";
		
		if(isTableCodeVisible())
			tblInfo+=table.getCode();
		
		if(isSeatCountVisible())
			tblInfo+="("+table.getSeatCount()+")";
		
		g2.setColor(tableInfoTextColor );
		PosStringUtil.drawTextCentered(g2, tblInfo, new Rectangle(0, 0, getWidth(), getHeight()));
	}

	/**
	 * 
	 */
	private void drawBox(Graphics2D g2) {

		g2.setColor(Color.GRAY);
		g2.fillRect(SHADOW_THICKNESS, SHADOW_THICKNESS, getWidth(), getHeight());
		g2.setColor(TABLE_NORMAL_FILL_COLOR);
		g2.fillRect(0, 0, getWidth()-SHADOW_THICKNESS, getHeight()-SHADOW_THICKNESS);

		final Color borderColor=DEFAULT_BORDER_COLOR;
		final Stroke newStroke=new BasicStroke(SELECTED_BORDER_THICKNESS);

		g2.setStroke(newStroke);
		g2.setColor(borderColor);
		g2.drawRect(1, 1, getWidth()-SELECTED_BORDER_THICKNESS-SHADOW_THICKNESS, getHeight()-SELECTED_BORDER_THICKNESS-SHADOW_THICKNESS);

	}

	/**
	 * 
	 */
	private void drawImage(Graphics2D g2) {

		if(selectedIcon==null)
			selectedIcon=PosResUtil.getImageIconFromResource(SELECTED_ICON);

		g2.drawImage(image,0,0,this.getWidth(),this.getHeight(),0,0,image.getWidth(),image.getHeight(),null);

	}

	/**
	 * @param g2
	 */
	private void drawOccupiedIcon(Graphics2D g2) {

		if(occIcon==null)
			occIcon=PosResUtil.getImageIconFromResource(OCC_ICON);

		g2.drawImage(occIcon.getImage(),this.getWidth()-occIcon.getIconWidth(),0,this.getWidth(),occIcon.getIconHeight(),
				0,0,occIcon.getIconWidth(),occIcon.getIconHeight(),null);

		g2.setColor(Color.WHITE);

		if(orderCountVisible){

			final String text=String.valueOf(table.getOrderCount());
			final Rectangle rec=new Rectangle(this.getWidth()-occIcon.getIconWidth(),0,occIcon.getIconWidth(),occIcon.getIconHeight());
			final Font oldFnt=g2.getFont();
			final Font fnt=g2.getFont().deriveFont(Font.PLAIN, 9f);

			g2.setFont(fnt);
			PosStringUtil.drawTextCentered(g2, text, rec);
			g2.setFont(oldFnt);

		}
	}

	/**
	 * @return the image
	 */
	public BufferedImage getImage() {
		return image;
	}

	/**
	 * @param image the image to set
	 */
	public void setImage(BufferedImage image) {
		this.image = image;
	}

	/**
	 * @return the selected
	 */
	public boolean isSelected() {
		return selected;
	}

	/**
	 * @param selected the selected to set
	 */
	public void setSelected(boolean selected) {
		this.selected = selected;
		repaint();

		if(selected){
			if(listener!=null)
				listener.onServingTableSelected(PosServingTableControl.this);
		}
	}
	
	public void onDoubleClick( ){
		
		this.selected = true;
		repaint();

		if(listener!=null)
			listener.onServingTableDoubleClicked(PosServingTableControl.this);
	}

	/**
	 * @return the table
	 */
	public BeanServingTableExt getTable() {
		return table;
	}

	/**
	 * @param table the table to set
	 */
	public void setTable(BeanServingTableExt table) {
		this.table = table;
		setLayout();
	}

	/**
	 * @return the markedAsAdded
	 */
	public boolean isMarkedAsAdded() {
		return markedAsAdded;
	}

	/**
	 * @param markedAsAdded the markedAsAdded to set
	 */
	public void setMarkedAsAdded(boolean markedAsAdded) {
		this.markedAsAdded = markedAsAdded;
		repaint();
	}

	private IPosServingTableControlListener listener;

	public interface IPosServingTableControlListener{

		public void onServingTableSelected(PosServingTableControl source);
		/**
		 * @param source
		 */
		public void onServingTableDoubleClicked(PosServingTableControl source);
	}

	/**
	 * @return the listener
	 */
	public IPosServingTableControlListener getListener() {
		return listener;
	}

	/**
	 * @param listener the listener to set
	 */
	public void setListener(IPosServingTableControlListener listener) {
		this.listener = listener;
	}

	/**
	 * 
	 */
	private void setLayout(){
		setImage(table.getImage());
		setLocation(table.getColumn(), table.getRow());
		setSize(table.getLayoutWidth(), table.getLayoutHeight());
		setPreferredSize(new Dimension(table.getLayoutWidth(), table.getLayoutHeight()));
	}

	/**
	 * @return the showOrderCount
	 */
	public boolean isOrderCountVisible() {
		return orderCountVisible;
	}

	/**
	 * @param showOrderCount the showOrderCount to set
	 */
	public void setOrderCountVisible(boolean visible) {
		this.orderCountVisible = visible;
	}

	/**
	 * @return the seatCountVisible
	 */
	public boolean isSeatCountVisible() {
		return seatCountVisible;
	}

	/**
	 * @param seatCountVisible the seatCountVisible to set
	 */
	public void setSeatCountVisible(boolean seatCountVisible) {
		this.seatCountVisible = seatCountVisible;
	}

	/**
	 * @return the tableCodeVisible
	 */
	public boolean isTableCodeVisible() {
		return tableCodeVisible;
	}

	/**
	 * @param tableCodeVisible the tableCodeVisible to set
	 */
	public void setTableCodeVisible(boolean tableCodeVisible) {
		this.tableCodeVisible = tableCodeVisible;
	}

	/**
	 * @param infoTextColor
	 */
	public void setTableInfoTextColor(Color infoTextColor) {
		
		this.tableInfoTextColor=infoTextColor;		
	}

}
