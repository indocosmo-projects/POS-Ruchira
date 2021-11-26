/**
 * 
 */
package com.indocosmo.pos.forms.split.components;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.RootPaneContainer;
import javax.swing.border.EtchedBorder;

import com.indocosmo.pos.common.utilities.PosCurrencyUtil;
import com.indocosmo.pos.common.utilities.PosFormUtil;
import com.indocosmo.pos.common.utilities.PosNumberUtil;

/**
 * @author jojesh-13.2
 *
 */
public class EditSplitSummaryPanel extends JPanel {
	
	private static final int TITLE_HEIGHT=30; 
	protected static final Color TITLE_LABEL_BG_COLOR = new Color(78, 128, 188);
	protected static final Color TITLE_LABEL_FG_COLOR = Color.WHITE;
	protected static final int PANEL_CONTENT_V_GAP=8;
	protected static final int PANEL_CONTENT_H_GAP=8;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private RootPaneContainer mParent;
	private JTextField valueItemCount;
	private JTextField valueTotalAmount;
	

	/**
	 * 
	 */
	public EditSplitSummaryPanel(RootPaneContainer parent,int width,int height) {
		
		this.mParent=parent;
		
		setLayout(null);
		setSize(width, height);
		setPreferredSize(new Dimension(width, height));
		setBorder(new EtchedBorder());
//		setBackground(Color.CYAN);
		createContents();
		
	}


	/**
	 * 
	 */
//	private void createContents() {
//		
//		JLabel title=PosFormUtil.setHeading("Bill details", getWidth()-6,TITLE_HEIGHT);
//		title.setLocation(2, 2);
//		this.add(title);
//		
//		final int itemDefHeight=PosTouchableFieldBase.LAYOUT_DEF_HEIGHT;
//		final int titleDefWidth=100;
//		final int valueDefWidth=getWidth()-titleDefWidth-PANEL_CONTENT_H_GAP*2;
//		
//		int top=title.getY()+title.getHeight()+PANEL_CONTENT_V_GAP;
//		int left=PANEL_CONTENT_H_GAP;
//		
//		JLabel titleBillTotal=getTitleTextFiled("Total",left,top,titleDefWidth,itemDefHeight);
//		this.add(titleBillTotal);
//		
//		
//		left=titleBillTotal.getX()+titleBillTotal.getWidth();
//		valueBillTotal=getValueTextFiled(left,top,valueDefWidth, itemDefHeight);
//		this.add(valueBillTotal);
//		
//		top=valueBillTotal.getY()+valueBillTotal.getHeight()+PANEL_CONTENT_V_GAP;
//		left=PANEL_CONTENT_H_GAP;
//		JLabel titleBillPaid=getTitleTextFiled("Paid : ",left,top,titleDefWidth,itemDefHeight);
//		this.add(titleBillPaid);
//		
//		
//		left=titleBillPaid.getX()+titleBillPaid.getWidth();
//		valueBillPaid=getValueTextFiled(left,top,valueDefWidth, itemDefHeight);
//		this.add(valueBillPaid);
//		
//		top=valueBillPaid.getY()+valueBillPaid.getHeight()+PANEL_CONTENT_V_GAP;
//		left=PANEL_CONTENT_H_GAP;
//		JLabel titleBillBalance=getTitleTextFiled("Balance : ",left,top,titleDefWidth,itemDefHeight);
//		this.add(titleBillBalance);
//		
//		left=titleBillBalance.getX()+titleBillBalance.getWidth();
//		JTextField valueBillBalance=getValueTextFiled(left,top,valueDefWidth, itemDefHeight);
//		this.add(valueBillBalance);
//		
//	}
	
	
		private void createContents() {
			
			final JLabel title=PosFormUtil.setHeading("Split Details", getWidth()-6,TITLE_HEIGHT);
			title.setLocation(2, 2);
			this.add(title);
			
			final int itemDefHeight=30;
			final int titleDefWidth=(getWidth()-PANEL_CONTENT_V_GAP*3)/2;
			final int valueDefWidth=(getWidth()-PANEL_CONTENT_V_GAP*3)/2;
			
			int top=title.getY()+title.getHeight()+PANEL_CONTENT_V_GAP;
			int left=PANEL_CONTENT_H_GAP;
			
			final JLabel titleTotalItems=getTitleTextFiled("Total Items",left,top,titleDefWidth,itemDefHeight);
			this.add(titleTotalItems);
			
//			top=valueBillTotal.getY()+valueBillTotal.getHeight()+PANEL_CONTENT_V_GAP;
			left=titleTotalItems.getX()+titleTotalItems.getWidth()+PANEL_CONTENT_H_GAP;;
			final JLabel titleToalAmount=getTitleTextFiled("Amount",left,top,titleDefWidth,itemDefHeight);
			this.add(titleToalAmount);
			
////			top=valueBillTotal.getY()+valueBillTotal.getHeight()+PANEL_CONTENT_V_GAP;
//			left=titleToalAmount.getX()+titleToalAmount.getWidth()+PANEL_CONTENT_H_GAP;
//			final JLabel titlePartPaid=getTitleTextFiled("Part.",left,top,titleDefWidth,itemDefHeight);
//			this.add(titlePartPaid);
//			
			
			left=titleTotalItems.getX();
			top=titleTotalItems.getY()+titleTotalItems.getHeight();
			valueItemCount=getValueTextFiled(left,top,valueDefWidth, itemDefHeight);
			this.add(valueItemCount);
			
			
			left=titleToalAmount.getX();
			top=titleToalAmount.getY()+titleToalAmount.getHeight();
			valueTotalAmount=getValueTextFiled(left,top,valueDefWidth, itemDefHeight);
			this.add(valueTotalAmount);
			
//			left=titlePartPaid.getX();
//			top=titlePartPaid.getY()+titlePartPaid.getHeight();
//			valueBillPartPaid=getValueTextFiled(left,top,valueDefWidth, itemDefHeight);
//			valueBillPartPaid.setForeground(Color.RED);
//			this.add(valueBillPartPaid);
////			
////			left=titleBillPaid.getX()+titleBillPaid.getWidth();
////			valueBillPaid=getValueTextFiled(left,top,valueDefWidth, itemDefHeight);
////			this.add(valueBillPaid);
////			
//			top=valueBillPaid.getY()+valueBillPaid.getHeight()+PANEL_CONTENT_V_GAP;
//			left=PANEL_CONTENT_H_GAP;
//			JLabel titleBillBalance=getTitleTextFiled("Balance",left,top,getWidth()-PANEL_CONTENT_V_GAP*2,itemDefHeight);
//			this.add(titleBillBalance);
//			
//			top=titleBillBalance.getY()+titleBillBalance.getHeight();
//			left=titleBillBalance.getX();
//			valueBillBalance=getValueTextFiled(left,top,getWidth()-PANEL_CONTENT_V_GAP*2, getHeight()-top-PANEL_CONTENT_V_GAP);
//			valueBillBalance.setForeground(Color.RED);
//			Font fnt=valueBillBalance.getFont().deriveFont(Font.BOLD,40.0f);
//			valueBillBalance.setFont(fnt);
//			this.add(valueBillBalance);
//			
		}
	
	
	/**
	 * @param left
	 * @param top
	 * @param width
	 * @param height
	 * @return
	 */
	private JTextField getValueTextFiled(int left, int top, int width,int height){
		
		final JTextField valueTextField=new JTextField("00.00");
		valueTextField.setSize(width, height);
		valueTextField.setFont(PosFormUtil.getLabelFont());
		valueTextField.setLocation(left,top);
		valueTextField.setHorizontalAlignment(JLabel.CENTER);
//		valueBillBalance.setBorder(new LineBorder(Color.CYAN));
		valueTextField.setEditable(false);
		
		return valueTextField;
		
	}
	
	private JLabel getTitleTextFiled(String title,int left, int top, int width,int height){
		
		final JLabel titleField=new JLabel(title);
		titleField.setSize(width, height);
		titleField.setFont(PosFormUtil.getLabelFont());
		titleField.setLocation(left,top);
		titleField.setOpaque(true);
		titleField.setBackground(Color.LIGHT_GRAY);
		titleField.setHorizontalAlignment(JLabel.CENTER);
		
		return titleField;
		
	}


	/**
	 * @param billTotal
	 */
	public void setItemCount(double itemCOunt) {
		
		valueItemCount.setText(PosNumberUtil.format(itemCOunt));
		
	}
	
	/**
	 * @param BillPaid
	 */
	public void setTotalAmount(double amo) {
		
		valueTotalAmount.setText(PosCurrencyUtil.format(amo));
		
	}

}
