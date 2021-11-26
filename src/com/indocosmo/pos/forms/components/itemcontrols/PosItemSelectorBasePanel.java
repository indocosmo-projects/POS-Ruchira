package com.indocosmo.pos.forms.components.itemcontrols;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import com.indocosmo.pos.forms.components.itemcontrols.listners.IPosItemSelectorListner;

public abstract class PosItemSelectorBasePanel extends JPanel {

	protected static final Color SELECTED_ITEM_SELECTED_BG_COLOR=Color.CYAN;// new Color(179, 34, 65);
	protected static final Color SELECTED_ITEM_PRE_SELECTED_BG_COLOR=new Color(255,200,200);// new Color(179, 34, 65);
	protected static final Color SELECTED_ITEM_BORDER_COLOR=Color.ORANGE;
	protected static final Color ITEM_BORDER_COLOR=Color.GRAY;

	protected final static int BORDER_WIDTH=3;
	protected boolean mIsSelected=false;

	public PosItemSelectorBasePanel(final int layoutWidth,final int layoutHeight) {
		setPreferredSize(new Dimension(layoutWidth,layoutHeight));
		setSize(layoutWidth,layoutHeight);
		initComponent();
	}

	protected  void initComponent(){
		setBackground(Color.WHITE);
		addMouseListener(mItemMousePressedListner);
		setBorder(new LineBorder(ITEM_BORDER_COLOR,BORDER_WIDTH));
		setVisible(false);
	}

	private MouseListener mItemMousePressedListner=new MouseAdapter() {
		@Override
		public void mousePressed(MouseEvent e) {
			onItemSelected(e);
		}
	};

	public void setSelected(boolean selected){
		if(mIsSelected==selected || !isEnabled())return;
		mIsSelected=selected;
		if(mIsSelected){
			setBackground(SELECTED_ITEM_PRE_SELECTED_BG_COLOR);
			setBorder(new LineBorder(SELECTED_ITEM_BORDER_COLOR,3));
			if(mListner!=null)
				if(mListner.onSelected(this)){
					setBackground(SELECTED_ITEM_SELECTED_BG_COLOR);
				}else{
					setBackground(Color.WHITE);
					setSelected(false);
				}
		}
		else{
			setBackground(Color.WHITE);
			setBorder(new LineBorder(ITEM_BORDER_COLOR,BORDER_WIDTH));
			repaint();
		}
	}	

	protected  void onItemSelected(MouseEvent e){
		if(!isEnabled()) return;
		setSelected(true);
		if(e.getClickCount()==2 && !e.isConsumed())
			if(mListner!=null)
				mListner.onDoubleClick(this);
	}

	public boolean isSelected(){
		return mIsSelected;
	}

	@Override
	public void setEnabled(boolean enabled) {
		setBackground((enabled)?Color.WHITE:Color.GRAY);
		super.setEnabled(enabled);
	}

	private IPosItemSelectorListner mListner;

	public void setListner(IPosItemSelectorListner listner){
		mListner=listner;
	}
}
