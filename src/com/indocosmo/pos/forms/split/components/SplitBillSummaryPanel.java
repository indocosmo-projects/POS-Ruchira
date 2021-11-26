/**
 * 
 */
package com.indocosmo.pos.forms.split.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.RootPaneContainer;
import javax.swing.border.EtchedBorder;

import com.indocosmo.pos.common.enums.split.BillSplitMethod;
import com.indocosmo.pos.common.utilities.PosCurrencyUtil;
import com.indocosmo.pos.common.utilities.PosFormUtil;

/**
 * @author jojesh-13.2
 *
 */
public class SplitBillSummaryPanel extends JPanel {
	
	private static final int TITLE_HEIGHT=30; 
	protected static final Color TITLE_LABEL_BG_COLOR = new Color(78, 128, 188);
	protected static final Color TITLE_LABEL_FG_COLOR = Color.WHITE;
	protected static final int PANEL_CONTENT_V_GAP=8;
	protected static final int PANEL_CONTENT_H_GAP=6;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private RootPaneContainer mParent;
	private JTextField valueBillTotal;
	private JTextField valueBillPaid;
	private JTextField valueBillBalance;
	private JTextField valueBillPartPaid;
	private JTextField valueBillTotalDisc;
	
	private double billTotal=0.0; 
	private double billPaid=0.0;
	private double billTotalDisc=0.0;
	
	private BillSplitMethod billSplitMethod;
	private double billPartRec;
	private double billPartLeft;
	

	/**
	 * 
	 */
	public SplitBillSummaryPanel(RootPaneContainer parent,int width,int height) {
		
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
			
			final JLabel title=PosFormUtil.setHeading("Bill Details", getWidth()-6,TITLE_HEIGHT);
			title.setLocation(2, 2);
			this.add(title);
			
			final int itemDefHeight=30;
			final int titleDefWidth=(getWidth()-PANEL_CONTENT_V_GAP*4)/5;
			final int valueDefWidth=(getWidth()-PANEL_CONTENT_V_GAP*4)/5;
			
			int top=title.getY()+title.getHeight()+PANEL_CONTENT_V_GAP;
			int left=PANEL_CONTENT_H_GAP;
			
			final JLabel titleBillTotal=getTitleTextFiled("Total",left,top,titleDefWidth+PANEL_CONTENT_H_GAP,itemDefHeight);
			this.add(titleBillTotal);
			
//			top=valueBillTotal.getY()+valueBillTotal.getHeight()+PANEL_CONTENT_V_GAP;
			left=titleBillTotal.getX()+titleBillTotal.getWidth()+PANEL_CONTENT_H_GAP;;
			final JLabel titleBillPaid=getTitleTextFiled("Paid",left,top,titleDefWidth,itemDefHeight);
			this.add(titleBillPaid);
			
//			top=valueBillTotal.getY()+valueBillTotal.getHeight()+PANEL_CONTENT_V_GAP;
			left=titleBillPaid.getX()+titleBillPaid.getWidth()+PANEL_CONTENT_H_GAP;
			final JLabel titlePartPaid=getTitleTextFiled("Part.[Left]",left,top,titleDefWidth*2,itemDefHeight);
			this.add(titlePartPaid);
			
			left=titlePartPaid.getX()+titlePartPaid.getWidth()+PANEL_CONTENT_H_GAP;
			final JLabel titleTotalDisc=getTitleTextFiled("Disc.",left,top,titleDefWidth-PANEL_CONTENT_H_GAP,itemDefHeight);
			this.add(titleTotalDisc);
			
			left=titleBillTotal.getX();
			top=titleBillTotal.getY()+titleBillTotal.getHeight();
			valueBillTotal=getValueTextFiled(left,top,valueDefWidth +PANEL_CONTENT_H_GAP, itemDefHeight);
			this.add(valueBillTotal);
			
			
			left=titleBillPaid.getX();
			top=titleBillPaid.getY()+titleBillPaid.getHeight();
			valueBillPaid=getValueTextFiled(left,top,valueDefWidth, itemDefHeight);
			valueBillPaid.setForeground(Color.RED);
			this.add(valueBillPaid);
			
			left=titlePartPaid.getX();
			top=titlePartPaid.getY()+titlePartPaid.getHeight();
			valueBillPartPaid=getValueTextFiled(left,top,valueDefWidth*2, itemDefHeight);
			valueBillPartPaid.setForeground(Color.RED);
			this.add(valueBillPartPaid);
			
			left=titleTotalDisc.getX();
			top=titleTotalDisc.getY()+titleTotalDisc.getHeight();
			valueBillTotalDisc=getValueTextFiled(left,top,valueDefWidth-PANEL_CONTENT_H_GAP , itemDefHeight);
			valueBillTotalDisc.setForeground(Color.RED);
			this.add(valueBillTotalDisc);
			
			
//			
//			left=titleBillPaid.getX()+titleBillPaid.getWidth();
//			valueBillPaid=getValueTextFiled(left,top,valueDefWidth, itemDefHeight);
//			this.add(valueBillPaid);
//			
			top=valueBillPaid.getY()+valueBillPaid.getHeight()+PANEL_CONTENT_V_GAP;
			left=PANEL_CONTENT_H_GAP;
			JLabel titleBillBalance=getTitleTextFiled("Balance",left,top,getWidth()-PANEL_CONTENT_V_GAP*2,itemDefHeight);
			this.add(titleBillBalance);
			
			top=titleBillBalance.getY()+titleBillBalance.getHeight();
			left=titleBillBalance.getX();
			valueBillBalance=getValueTextFiled(left,top,getWidth()-PANEL_CONTENT_V_GAP*2, getHeight()-top-PANEL_CONTENT_V_GAP);
			valueBillBalance.setForeground(Color.RED);
			Font fnt=valueBillBalance.getFont().deriveFont(Font.BOLD,40.0f);
			valueBillBalance.setFont(fnt);
			this.add(valueBillBalance);
			
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
	public void setBillTotal(double billTotal) {
		
		this.billTotal=billTotal;
		setBalance();
		valueBillTotal.setText(PosCurrencyUtil.format(billTotal));
		
	}
	
	/**
	 * 
	 */
	private void setBalance() {

		final double balance=getBalanceAmount();
		valueBillBalance.setText(PosCurrencyUtil.format(balance));
	}

	/**
	 * @param total discount
	 */
	public void setTotalDiscount(double discount) {
		
		this.billTotalDisc=discount;
		valueBillTotalDisc.setText(PosCurrencyUtil.format(discount));
		setBalance();
	}


	/**
	 * @param BillPaid
	 */
	public void setBillPaid(double paid) {
		
		this.billPaid=paid;
		setBalance();
		valueBillPaid.setText(PosCurrencyUtil.format(paid));
		
	}

	/**
	 * @param BillPaidWithout Discount
	 */
	public void setBillPaidWithOutDiscount(double paid) {
		
		setBalance();
		valueBillPaid.setText(PosCurrencyUtil.format(paid));
		
	}
	/**
	 * @param BillPaid
	 */
	public void setBillPart(double rec,double left) {
		
		this.billPartRec=rec;
		this.billPartLeft=left;
		setBalance();
		valueBillPartPaid.setText(PosCurrencyUtil.format(billPartRec)+"["+PosCurrencyUtil.format(billPartLeft)+"]");
		
	}
	
	/**
	 * @param method
	 */
	public void setBillSplitMethod(BillSplitMethod billSplitMethod){
		
		this.billSplitMethod=billSplitMethod;
		setBalance();
	}
	
	/**
	 * @return
	 */
	public double getBalanceAmount(){
		
		final double balance=billTotal-billPaid-billTotalDisc;
		
		return balance;
	}
	

}
