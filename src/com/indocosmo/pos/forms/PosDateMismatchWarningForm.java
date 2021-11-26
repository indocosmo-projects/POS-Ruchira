/**
 * 
 */
package com.indocosmo.pos.forms;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.indocosmo.pos.common.utilities.PosResUtil;
import com.indocosmo.pos.forms.components.buttons.PosButton;
import com.indocosmo.pos.forms.components.buttons.listners.IPosButtonListner;

/**
 * @author jojesh-13.2
 *
 */
public class PosDateMismatchWarningForm extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final int PANEL_V_GAP=8;
	private static final int PANEL_H_GAP=8;
	
	private static final int FORM_HEIGHT=520;
	private static final int FORM_WIDTH=588;
	
	protected static final int PANEL_CONTENT_H_GAP = PosOrderEntryForm.PANEL_CONTENT_H_GAP;
	protected static final int PANEL_CONTENT_V_GAP = PosOrderEntryForm.PANEL_CONTENT_V_GAP;
	
	private static final int IMAGE_BUTTON_WIDTH = 150;
	private static final int IMAGE_BUTTON_HEIGHT = 60;
	
	private static final String IMAGE_BUTTON_OK = "dlg_ok.png";
	private static final String IMAGE_BUTTON_OK_TOUCH = "dlg_ok_touch.png";

	private static final String IMAGE_BUTTON_CANCEL = "dlg_cancel.png";
	private static final String IMAGE_BUTTON_CANCEL_TOUCH = "dlg_cancel_touch.png";
	
	private static final String CAUTION_IMAGE="caution_yellow.png";
	private ImageIcon cationImage;
	
	private JPanel mBottomPanel;
	protected PosButton mButtonOk;
	protected PosButton mButtonCancel;
	private boolean isCancelled=false;
	
	 

	/**
	 * @param isTableSelectionOnly
	 */
	public PosDateMismatchWarningForm (){
		
		cationImage=PosResUtil.getImageIconFromResource(CAUTION_IMAGE);
		initForm();
	}

	/**
	 * 
	 */
	private void initForm() {
		
		this.setSize(FORM_WIDTH,FORM_HEIGHT);
		this.setLayout(null);
		this.getContentPane().setBackground(Color.RED.darker().darker());
		createBottomPanel();

	}
	
	/**
	 * 
	 */
	private void createBottomPanel() {
		
		
		mBottomPanel=new JPanel();
		mBottomPanel.setOpaque(true);
		mBottomPanel.setBounds(4, getHeight()-(IMAGE_BUTTON_HEIGHT+5*3), getWidth()-4*2, IMAGE_BUTTON_HEIGHT+5*2);
		mBottomPanel.setLayout(new FlowLayout());
		this.add(mBottomPanel);
		
		int left =  PANEL_CONTENT_H_GAP * 10;
		mButtonOk = new PosButton();
		mButtonOk.setDefaultButton(true);
		mButtonOk.setText("Proceed");
		mButtonOk
				.setImage(PosResUtil.getImageIconFromResource(IMAGE_BUTTON_OK));
		mButtonOk.setTouchedImage(PosResUtil
				.getImageIconFromResource(IMAGE_BUTTON_OK_TOUCH));
		mButtonOk.setHorizontalAlignment(SwingConstants.CENTER);
		mButtonOk.setBounds(left, PANEL_CONTENT_V_GAP * 5, IMAGE_BUTTON_WIDTH,
				IMAGE_BUTTON_HEIGHT);
		mButtonOk.setOnClickListner(new IPosButtonListner() {
			
			@Override
			public void onClicked(PosButton button) {
				
				isCancelled=false;
				closeForm();
			}
		});
		mBottomPanel.add(mButtonOk);

		left = mButtonOk.getX() + mButtonOk.getWidth() + PANEL_CONTENT_V_GAP* 2;
		mButtonCancel = new PosButton();
		mButtonCancel.setCancel(true);
		mButtonCancel.setText("Cancel");
		mButtonCancel.setImage(PosResUtil
				.getImageIconFromResource(IMAGE_BUTTON_CANCEL));
		mButtonCancel.setTouchedImage(PosResUtil
				.getImageIconFromResource(IMAGE_BUTTON_CANCEL_TOUCH));
		mButtonCancel.setHorizontalAlignment(SwingConstants.CENTER);
		mButtonCancel.setBounds(left, PANEL_CONTENT_V_GAP, IMAGE_BUTTON_WIDTH,
				IMAGE_BUTTON_HEIGHT);
		mButtonCancel.setOnClickListner(new IPosButtonListner() {
			
			@Override
			public void onClicked(PosButton button) {
				
				isCancelled=true;
				closeForm();
			}
		});
		mBottomPanel.add(mButtonCancel);
		
	}

//	public static void main(String[] args) throws Exception {
//		
//		PosFormUtil.showLightBoxModal(new PosDateMismatchWarningForm());
//	}
//	
	/* (non-Javadoc)
	 * @see java.awt.Window#paint(java.awt.Graphics)
	 */
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		
		if(cationImage!=null){
			
			final int top=((mBottomPanel.getY()-PANEL_CONTENT_V_GAP)/2)-(cationImage.getIconHeight()/2);
			final int left=(getWidth()/2)-(cationImage.getIconWidth()/2);
			g.drawImage(cationImage.getImage(),left, top, left+cationImage.getIconWidth(), top+cationImage.getIconHeight(),  0, 0, cationImage.getIconWidth(), cationImage.getIconHeight(),null);
		}
	}
	
	/**
	 * 
	 */
	private void closeForm(){
		
		this.setVisible(false);
	
	}

	/**
	 * @return the isCancelled
	 */
	public boolean isCancelled() {
		return isCancelled;
	}


}
