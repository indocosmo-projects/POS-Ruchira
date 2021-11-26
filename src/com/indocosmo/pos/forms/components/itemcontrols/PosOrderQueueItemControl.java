package com.indocosmo.pos.forms.components.itemcontrols;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import com.indocosmo.pos.common.utilities.PosFormUtil;
import com.indocosmo.pos.data.beans.BeanOrderQHeader;
import com.indocosmo.pos.forms.components.itemcontrols.listners.IPosOrderQueueItemControlListner;

@SuppressWarnings("serial")
public final class PosOrderQueueItemControl extends JPanel{

	
	private final static int PANEL_CONTENT_H_GAP=8;
	private final static int PANEL_CONTENT_V_GAP=-1;
	
	private final static int ORDERNO_PANEL_HEIGHT=60;
	private final static int ORDERNO_PANEL_WIDTH=60;
	
	private final static int ORDERDTL_PANEL_HEIGHT=0;
	private final static int ORDERDTL_PANEL_WIDTH=ORDERNO_PANEL_WIDTH;
	
	public final static int LAYOUT_WIDTH=ORDERNO_PANEL_WIDTH;
	public final static int LAYOUT_HEIGHT=ORDERNO_PANEL_HEIGHT+ORDERDTL_PANEL_HEIGHT;
	
	private static final Color SELECTED_ITEM_BG_COLOR=Color.CYAN;// new Color(179, 34, 65);
	private static final Color SELECTED_ITEM_BORDER_COLOR=Color.ORANGE;
	private static final Color ITEM_BORDER_COLOR=Color.GRAY;
	
	private JPanel mOrderNumDispPanel;
	private JPanel mOrderDtlDispPanel;
	private BeanOrderQHeader mItem;
//	private int mIndex;
	private boolean mIsSelected=false;
	
	private JLabel mTockenNumberLabel;
	private Object mTag;
	/**
	 * Create the dialog.
	 */
	public PosOrderQueueItemControl() {
		setPreferredSize(new Dimension(LAYOUT_WIDTH,LAYOUT_HEIGHT));
		setLayout(null);
		setBackground(Color.WHITE);
		addMouseListener(itemMousePressedListner);
		initComponent();
	}
	
	private  void initComponent() {
		setBorder(new LineBorder(ITEM_BORDER_COLOR,3));
		mOrderNumDispPanel=new JPanel();
		mOrderNumDispPanel.setBounds(0, 0, ORDERNO_PANEL_WIDTH, ORDERNO_PANEL_HEIGHT);
		mOrderNumDispPanel.setBorder(new LineBorder(Color.BLACK));
		mOrderNumDispPanel.setOpaque(false);
		mOrderNumDispPanel.setLayout(null);
		add(mOrderNumDispPanel);
		
		mOrderDtlDispPanel=new JPanel();
		final int top=mOrderNumDispPanel.getY()+mOrderNumDispPanel.getHeight()+PANEL_CONTENT_V_GAP;
		mOrderDtlDispPanel.setBounds(0,top, ORDERDTL_PANEL_WIDTH, ORDERDTL_PANEL_HEIGHT);
		mOrderDtlDispPanel.setBorder(new LineBorder(Color.BLACK));
		mOrderDtlDispPanel.setOpaque(false);
		add(mOrderDtlDispPanel);
		
		mTockenNumberLabel=new JLabel();
		mTockenNumberLabel.setBounds(0,0,ORDERNO_PANEL_WIDTH-1, ORDERNO_PANEL_HEIGHT-1);
		mTockenNumberLabel.setFont(PosFormUtil.getTokenFont().deriveFont(20f));
		mTockenNumberLabel.setHorizontalAlignment(SwingConstants.CENTER);
		mTockenNumberLabel.setVerticalAlignment(SwingConstants.CENTER);
		mOrderNumDispPanel.add(mTockenNumberLabel);
	}


	public void validateComponent() {
		// TODO Auto-generated method stub
	}
	
	private MouseListener itemMousePressedListner=new MouseAdapter() {
		@Override
		public void mousePressed(MouseEvent e) {
//			if(!isEnabled()) return;
			setSelected(true);
			if(e.getClickCount()==2 && !e.isConsumed())
				if(mOrderQueueItemListner!=null)
					mOrderQueueItemListner.onDoubleClick(PosOrderQueueItemControl.this);
		}
	};
	
	public void setSelected(boolean selected){
		if(mIsSelected==selected || !isEnabled())return;
		mIsSelected=selected;
		if(mIsSelected){
			setBackground(SELECTED_ITEM_BG_COLOR);
			setBorder(new LineBorder(SELECTED_ITEM_BORDER_COLOR,3));
			if(mOrderQueueItemListner!=null)
				mOrderQueueItemListner.onSelected(this);
		}
		else{
			setBackground(Color.WHITE);
			setBorder(new LineBorder(ITEM_BORDER_COLOR,3));
		}	
	}
	
	
	public void setOrderQueItem(BeanOrderQHeader item){
		mItem=item;
		if(mItem!=null){
			mTockenNumberLabel.setText(String.valueOf(mItem.getOrderQueueNo()));
			setVisible(true);
		}
		else
			setVisible(false);
	}
	
	public void setText(String text){
		mTockenNumberLabel.setText(text);
		mTockenNumberLabel.setFont(PosFormUtil.getTokenFont().deriveFont(30f));
	}
	
	public Object getTag() {
		return mTag;
	}

	public void setTag(Object tag) {
		this.mTag = tag;
	}
	
	@Override
	public void setEnabled(boolean enabled) {
		setBackground((enabled)?Color.WHITE:Color.GRAY);
		super.setEnabled(enabled);
	}
	
	public BeanOrderQHeader getOrderQueItem(){
		return mItem;
	}

	
	private IPosOrderQueueItemControlListner mOrderQueueItemListner;
	
	public void setListner(IPosOrderQueueItemControlListner listner){
		mOrderQueueItemListner=listner;
	}
	
}
