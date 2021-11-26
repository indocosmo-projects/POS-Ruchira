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

import com.indocosmo.pos.common.utilities.PosCurrencyUtil;
import com.indocosmo.pos.common.utilities.PosFormUtil;

/**
 * @author jojesh-13.2
 *
 */
public class SplitSelectionSummaryPanel extends JPanel {

	private static final int TITLE_HEIGHT=30; 
	private static final int PANEL_CONTENT_V_GAP=8;
	private static final int PANEL_CONTENT_H_GAP=8;
	private static final int PAYMENT_SUMMARY_PANEL_WIDTH=280;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private RootPaneContainer mParent;
	private JTextField valueTotalAmount;
	private JTextField valueTotalAdjustment;
	private JTextField valueBillBalance;
	private JTextField payAmoValueField;
	private JTextField valueNetAmount;
	private double totalAdjustment;

	/**
	 * 
	 */
	public SplitSelectionSummaryPanel(RootPaneContainer parent,int width,int height) {
		
		this.mParent=parent;
		
		setLayout(null);
		setSize(width, height);
		setPreferredSize(new Dimension(width, height));
//		setBorder(new EtchedBorder());
//		setBackground(Color.CYAN);
		createContents();
		
	}


	/**
	 * 
	 */
//	private void createContents() {
//		
//		JLabel title=PosFormUtil.setHeading("Splits Summary", (getWidth()/2)-2,TITLE_HEIGHT);
//		title.setLocation(2, 2);
//		this.add(title);
//		
//		final int itemDefHeight=35;
//		final int titleDefWidth=150;
//		final int valueDefWidth=(getWidth()/2)-titleDefWidth-PANEL_CONTENT_H_GAP*2;
//		
//		int top=title.getY()+title.getHeight()+PANEL_CONTENT_V_GAP;
//		int left=PANEL_CONTENT_H_GAP;
//		JLabel titleBillTotal=getTitleTextFiled("Split Count : ",left,top,titleDefWidth,itemDefHeight);
//		this.add(titleBillTotal);
//		
//		
//		left=titleBillTotal.getX()+titleBillTotal.getWidth();
//		valueBillTotal=getValueTextFiled(left,top,valueDefWidth, itemDefHeight);
//		this.add(valueBillTotal);
//		
//		top=valueBillTotal.getY()+valueBillTotal.getHeight()+PANEL_CONTENT_V_GAP;
//		left=PANEL_CONTENT_H_GAP;
//		JLabel titleBillPaid=getTitleTextFiled("Split Total : ",left,top,titleDefWidth,itemDefHeight);
//		this.add(titleBillPaid);
//		
//		
//		left=titleBillPaid.getX()+titleBillPaid.getWidth();
//		valueBillPaid=getValueTextFiled(left,top,valueDefWidth, itemDefHeight);
//		this.add(valueBillPaid);
//		
//		top=valueBillPaid.getY()+valueBillPaid.getHeight()+PANEL_CONTENT_V_GAP;
//		left=PANEL_CONTENT_H_GAP;
//		JLabel titleBillBalance=getTitleTextFiled("Unsplit Amo : ",left,top,titleDefWidth,itemDefHeight);
//		this.add(titleBillBalance);
//		
//		left=titleBillBalance.getX()+titleBillBalance.getWidth();
//		JTextField valueBillBalance=getValueTextFiled(left,top,valueDefWidth, itemDefHeight);
//		this.add(valueBillBalance);
//		
//		/**
//		 * SELECTON SUMMARY
//		 */
//		top=2;
//		left=title.getX()+title.getWidth()+3;
//		title=PosFormUtil.setHeading("Selection Summary", (getWidth()/2)-6,TITLE_HEIGHT);
//		title.setLocation(left,top);
//		this.add(title);
//		
//		top=title.getY()+title.getHeight()+PANEL_CONTENT_V_GAP;
//		left=title.getX();
//		JLabel titleSelSplitCount=getTitleTextFiled("Sel. Count : ",left,top,titleDefWidth,itemDefHeight);
//		this.add(titleSelSplitCount);
//		
//		left=titleSelSplitCount.getX()+titleSelSplitCount.getWidth();
//		JTextField valueSelectedSplitCount=getValueTextFiled(left,top,valueDefWidth, itemDefHeight);
//		this.add(valueSelectedSplitCount);
//		
//		top=valueSelectedSplitCount.getY()+valueSelectedSplitCount.getHeight()+PANEL_CONTENT_V_GAP;
//		left=title.getX();
//		JLabel titleSelSplitAmount=getTitleTextFiled("Sel. Total : ",left,top,titleDefWidth,itemDefHeight);
//		this.add(titleSelSplitAmount);
//		
//		left=titleSelSplitAmount.getX()+titleSelSplitAmount.getWidth();
//		JTextField valueSelectedSplitAmount=getValueTextFiled(left,top,valueDefWidth, itemDefHeight);
//		this.add(valueSelectedSplitAmount);
//		
//		top=valueSelectedSplitAmount.getY()+valueSelectedSplitAmount.getHeight()+PANEL_CONTENT_V_GAP;
//		left=title.getX();
//		JLabel titleSelSplitBalance=getTitleTextFiled(" Balance : ",left,top,titleDefWidth,itemDefHeight);
//		this.add(titleSelSplitBalance);
//		
//		left=titleSelSplitBalance.getX()+titleSelSplitBalance.getWidth();
//		JTextField valueSelectedSplitBalance=getValueTextFiled(left,top,valueDefWidth, itemDefHeight);
//		this.add(valueSelectedSplitBalance);
//		
//		
//	}
	
	/**
	 * 
	 */
	private void createContents(){
		
		createPaymentSummaryPanel();
		createSplitSummaryPanel();
	}
	
	/**
	 * 
	 */
	private void createPaymentSummaryPanel(){
		
		int left=getWidth()-PAYMENT_SUMMARY_PANEL_WIDTH;
		int top=0;
		
		JPanel paymentSummaryPanel=new JPanel();
		paymentSummaryPanel.setLayout(null);
		paymentSummaryPanel.setLocation(left, top);
		paymentSummaryPanel.setSize(PAYMENT_SUMMARY_PANEL_WIDTH, getHeight());
		paymentSummaryPanel.setBorder(new EtchedBorder());
		add(paymentSummaryPanel);
	
		JLabel title=PosFormUtil.setHeading("Payment Summary", PAYMENT_SUMMARY_PANEL_WIDTH-5,TITLE_HEIGHT);
		title.setLocation(2, 2);
		paymentSummaryPanel.add(title);
		
		left=PANEL_CONTENT_H_GAP;
		top=PANEL_CONTENT_V_GAP+title.getY()+title.getHeight();
		JLabel payAmoTitle=getTitleTextFiled("To Pay", left, top, PAYMENT_SUMMARY_PANEL_WIDTH-PANEL_CONTENT_H_GAP*2,30);
		payAmoTitle.setHorizontalAlignment(JLabel.CENTER);
		payAmoTitle.setBackground(Color.LIGHT_GRAY);
		payAmoTitle.setOpaque(true);
		paymentSummaryPanel.add(payAmoTitle);
		
		left=PANEL_CONTENT_H_GAP;
		top=payAmoTitle.getY()+payAmoTitle.getHeight();
		
		payAmoValueField=getValueTextFiled(left, top, 
				payAmoTitle.getWidth(), 
				paymentSummaryPanel.getHeight()-payAmoTitle.getY()-payAmoTitle.getHeight()-PANEL_CONTENT_V_GAP
				);
		payAmoValueField.setForeground(Color.RED);
		Font fnt=payAmoValueField.getFont().deriveFont(Font.BOLD,40.0f);
		payAmoValueField.setHorizontalAlignment(JLabel.CENTER);
		payAmoValueField.setFont(fnt);
		paymentSummaryPanel.add(payAmoValueField);
		
	}
	
	/**
	 * 
	 */
	private void createSplitSummaryPanel(){
		
		int left=0 ;
		int top=0;
		
		JPanel splitSummaryPanel=new JPanel();
		splitSummaryPanel.setLayout(null);
		splitSummaryPanel.setLocation(left, top);
		splitSummaryPanel.setSize(getWidth()-PAYMENT_SUMMARY_PANEL_WIDTH+2, getHeight());
		splitSummaryPanel.setBorder(new EtchedBorder());
		add(splitSummaryPanel);
	
		JLabel title=PosFormUtil.setHeading("Split Summary", splitSummaryPanel.getWidth()-5,TITLE_HEIGHT);
		title.setLocation(2, 2);
		splitSummaryPanel.add(title);
		
		
		final int itemDefHeight=35;
		final int titleDefWidth=150;
		final int valueDefWidth=splitSummaryPanel.getWidth()-titleDefWidth-PANEL_CONTENT_H_GAP*2;
		
		top=title.getY()+title.getHeight()+PANEL_CONTENT_V_GAP;
		left=PANEL_CONTENT_H_GAP;
		JLabel titleBillTotal=getTitleTextFiled("Amount : ",left,top,titleDefWidth,itemDefHeight);
		splitSummaryPanel.add(titleBillTotal);
		
		
		left=titleBillTotal.getX()+titleBillTotal.getWidth();
		valueTotalAmount=getValueTextFiled(left,top,valueDefWidth, itemDefHeight);
		splitSummaryPanel.add(valueTotalAmount);
		
		top=valueTotalAmount.getY()+valueTotalAmount.getHeight()+PANEL_CONTENT_V_GAP;
		left=PANEL_CONTENT_H_GAP;
		JLabel titleBillAdj=getTitleTextFiled("Variance : ",left,top,titleDefWidth,itemDefHeight);
		splitSummaryPanel.add(titleBillAdj);
		
		
		left=titleBillAdj.getX()+titleBillAdj.getWidth();
		valueTotalAdjustment=getValueTextFiled(left,top,valueDefWidth, itemDefHeight);
		splitSummaryPanel.add(valueTotalAdjustment);
		
		top=valueTotalAdjustment.getY()+valueTotalAdjustment.getHeight()+PANEL_CONTENT_V_GAP;
		left=PANEL_CONTENT_H_GAP;
		JLabel titleBillBalance=getTitleTextFiled("Net. : ",left,top,titleDefWidth,itemDefHeight);
		splitSummaryPanel.add(titleBillBalance);
		
		left=titleBillBalance.getX()+titleBillBalance.getWidth();
		valueNetAmount=getValueTextFiled(left,top,valueDefWidth, itemDefHeight);
		splitSummaryPanel.add(valueNetAmount);
		
	}
	
	
	/**
	 * @param left
	 * @param top
	 * @param width
	 * @param height
	 * @return
	 */
	private JTextField getValueTextFiled(int left, int top, int width,int height){
		
		JTextField valueTextField=new JTextField("00.00");
		valueTextField.setSize(width, height);
		valueTextField.setFont(PosFormUtil.getLabelFont());
		valueTextField.setLocation(left,top);
		valueTextField.setHorizontalAlignment(JLabel.RIGHT);
//		valueBillBalance.setBorder(new LineBorder(Color.CYAN));
		valueTextField.setEditable(false);
		
		return valueTextField;
		
	}
	
	/**
	 * @param title
	 * @param left
	 * @param top
	 * @param width
	 * @param height
	 * @return
	 */
	private JLabel getTitleTextFiled(String title,int left, int top, int width,int height){
		
		JLabel titleField=new JLabel(title);
		titleField.setSize(width, height);
		titleField.setFont(PosFormUtil.getLabelFont());
		titleField.setLocation(left,top);
		titleField.setHorizontalAlignment(JLabel.RIGHT);
		
		return titleField;
		
	}


	/**
	 * @param billTotal
	 */
//	public void setBillTotal(double billTotal) {
//		
//		valueBillTotal.setText(PosNumberUtil.formatNumber(billTotal));
//		
//	}


	/**
	 * @param amount
	 */
	public void setAmountToPay(double amount){
		
		payAmoValueField.setText(PosCurrencyUtil.format(amount));
	}


	/**
	 * @param totalAdjustment
	 */
	public void setTotalAdjustment(double amount) {
		
		this.totalAdjustment=amount;
		valueTotalAdjustment.setText(PosCurrencyUtil.format(amount));
	}


	/**
	 * @param totalAmoount
	 */
	public void setTotalAmount(double amount) {
		
		valueTotalAmount.setText(PosCurrencyUtil.format(amount));
	}


	/**
	 * @param netAmount
	 */
	public void setNetAmount(double amount) {
		
		valueNetAmount.setText(PosCurrencyUtil.format(amount));
	}


	/**
	 * @return the totalAdjustment
	 */
	public double getTotalAdjustment() {
		return totalAdjustment;
	}
	


}
