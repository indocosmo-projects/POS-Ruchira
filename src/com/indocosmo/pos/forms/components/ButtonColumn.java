package com.indocosmo.pos.forms.components;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.AbstractCellEditor;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

@SuppressWarnings("serial")
public class ButtonColumn extends AbstractCellEditor 
implements TableCellRenderer, TableCellEditor, ActionListener, MouseListener
{

	private JTable mTable;
	private Action mAction;
	private Object mTag;
	private Font buttonFont;
	private JButton mRenderButton;
	private JButton mEditButton;
	private Object mEditorValue;
	private boolean mIsButtonColumnEditor;
	
	private Color buttonBGColor=UIManager.getColor("Button.background");
	private Color buttonFGColor;


	public ButtonColumn(JTable table, Action action, int column)
	{
		this.mTable = table;
		this.mAction = action;

		mRenderButton = new JButton();
		
		mEditButton = new JButton();
		mEditButton.setFocusPainted( false );
		
		mEditButton.addActionListener( this );

		TableColumnModel columnModel = mTable.getColumnModel();
		columnModel.getColumn(column).setCellRenderer( this );
		columnModel.getColumn(column).setCellEditor( this );
		mTable.addMouseListener( this );
	}

	@Override
	public Component getTableCellEditorComponent(
			JTable table, Object value, boolean isSelected, int row, int column)
	{
		if (value == null)
		{
			mEditButton.setText( "" );
			mEditButton.setIcon( null );
		}
		else if (value instanceof Icon)
		{
			mEditButton.setText( "" );
			mEditButton.setIcon( (Icon)value );
		}
		else
		{
			mEditButton.setText( value.toString() );
			mEditButton.setIcon( null );
		}

		this.mEditorValue = value;
		return mEditButton;
	}

	@Override
	public Object getCellEditorValue()
	{
		return mEditorValue;
	}

	//
	//  Implement TableCellRenderer interface
	//
	public Component getTableCellRendererComponent(
			JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
	{
		if (isSelected)
		{
			mRenderButton.setForeground(buttonFGColor);
//			mRenderButton.setBackground(table.getSelectionBackground());
			mRenderButton.setBackground(buttonBGColor);
		}
		else
		{
			mRenderButton.setForeground(buttonFGColor);
//			mRenderButton.setBackground(UIManager.getColor("Button.background"));
			mRenderButton.setBackground(buttonBGColor);
		}

		if (value == null)
		{
			mRenderButton.setText( "" );
			mRenderButton.setIcon( null );
		}
		else if (value instanceof Icon)
		{
			mRenderButton.setText( "" );
			mRenderButton.setIcon( (Icon)value );
		}
		else
		{
			mRenderButton.setText( value.toString() );
			mRenderButton.setIcon( null );
		}

		return mRenderButton;
	}


	public void actionPerformed(ActionEvent e)
	{
		int row = mTable.convertRowIndexToModel( mTable.getEditingRow() );
		fireEditingStopped();
		ActionEvent event = new ActionEvent(
				this,
				ActionEvent.ACTION_PERFORMED,
				"" + row);
		if(mAction!=null)
			mAction.actionPerformed(event);
	}


	public void mousePressed(MouseEvent e)
	{
		if (mTable.isEditing()
				&&  mTable.getCellEditor() == this)
			mIsButtonColumnEditor = true;
	}

	public void mouseReleased(MouseEvent e)
	{
		if (mIsButtonColumnEditor
				&&  mTable.isEditing())
			mTable.getCellEditor().stopCellEditing();

		mIsButtonColumnEditor = false;
	}
	
	public void setTag(Object tag){
		mTag=tag;
	}
	
	public Object getTag(){
		return mTag;
	}

	public void mouseClicked(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}

	/**
	 * @return the buttonFont
	 */
	public Font getButtonFont() {
		return buttonFont;
	}

	/**
	 * @param buttonFont the buttonFont to set
	 */
	public void setFont(Font buttonFont) {
		this.buttonFont = buttonFont;
		mEditButton.setFont(buttonFont);
		mRenderButton.setFont(buttonFont);
		
	}
	
	/**
	 * @return the buttonBGColor
	 */
	public Color getButtonBGColor() {
		return buttonBGColor;
	}

	/**
	 * @param buttonBGColor the buttonBGColor to set
	 */
	public void setBackgroundColor(Color buttonBGColor) {
		this.buttonBGColor = buttonBGColor;
		mRenderButton.setBackground(buttonBGColor);
		mEditButton.setBackground(buttonBGColor);
	}

	/**
	 * @return the buttonFGColor
	 */
	public Color setForegroundColor() {
		return buttonFGColor;
	}

	/**
	 * @param buttonFGColor the buttonFGColor to set
	 */
	public void setForegroundColor(Color buttonFGColor) {
		this.buttonFGColor = buttonFGColor;
		mRenderButton.setForeground(buttonFGColor);
		mEditButton.setForeground(buttonFGColor);
	}



}
