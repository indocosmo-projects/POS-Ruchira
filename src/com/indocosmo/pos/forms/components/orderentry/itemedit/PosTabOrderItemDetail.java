/**
 * 
 */
package com.indocosmo.pos.forms.components.orderentry.itemedit;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.indocosmo.pos.common.utilities.PosCurrencyUtil;
import com.indocosmo.pos.common.utilities.PosFormUtil;
import com.indocosmo.pos.data.beans.BeanOrderDetail;
import com.indocosmo.pos.data.beans.BeanSaleItem;
import com.indocosmo.pos.forms.components.tab.PosTab;
import com.indocosmo.pos.forms.components.textfields.PosTextField;
import com.indocosmo.pos.forms.listners.IPosFormEventsListner;

/**
 * @author anand
 *
 */
@SuppressWarnings("serial")
public final class PosTabOrderItemDetail extends PosTab implements IPosFormEventsListner{

	private static final int TEXT_FIELD_HEIGHT = 35;
	private static final int TEXT_FIELD_WIDTH = 360;
	private static final int LABEL_HEIGHT = 35;
	private static final int LABEL_WIDTH = 200;
	private JPanel mainContainer;
	private BeanOrderDetail mOrderDetailItem;
	private BeanSaleItem mSaleItem;
	private BufferedImage mImage;

	private PosTextField itemImage;

	private JLabel itemNameLabel;
	private PosTextField itemNameText;

	private JLabel itemCodeLabel;
	private PosTextField itemCodeText;

	private JLabel itemAltNameLabel;
	private PosTextField itemAltNameText;
	
	private JLabel itemClassLabel;
	private PosTextField itemClassText;

	private JLabel itemPriceLabel;
	private PosTextField itemPriceText;

	private JLabel itemDescriptionLabel;
	private JTextArea itemDescriptionText;
	/**
	 * 
	 * @param parentForm
	 * @param ordrDetailItem
	 */
	public PosTabOrderItemDetail(Object parentForm, BeanOrderDetail ordrDetailItem) {

		super(parentForm, "Details");
		mOrderDetailItem=ordrDetailItem;
		initControl();
		populateLabels();
	}
	/**
	 * setting values to the textFields
	 */
	private void populateLabels() {
		
		itemNameText.setText(mSaleItem.getName());
		itemPriceText.setText(PosCurrencyUtil.format(mSaleItem.getFixedPrice()));
		itemAltNameText.setText(mSaleItem.getAlternativeName());
		itemClassText.setText(mSaleItem.getSubClass().getName());
		itemDescriptionText.setText(mSaleItem.getDescription());
		itemCodeText.setText(mSaleItem.getCode());

		try{

			mImage= mSaleItem.getSaleItemImage();
			if(mImage!=null){
//				itemImage.setOpaque(false);
				repaint();
			}
			else{
				itemImage.setText("Image Not Exsist");
//				itemImage.setVisible(true);
			}
		}
		catch(NullPointerException N){
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	/* (non-Javadoc)
	 * @see javax.swing.JComponent#paint(java.awt.Graphics)
	 */
	@Override
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		super.paint(g);
		if(mImage!=null){
			final int startX=itemImage.getX()+mainContainer.getX();
			final int startY=itemImage.getY()+mainContainer.getY();
			g.drawImage(mImage, startX+2, startY+2, startX+itemImage.getWidth()-2, startY+itemImage.getHeight()-2, 0, 0, mImage.getWidth(), mImage.getHeight(), null);
		}
	}
	/**
	 * Initializing controls
	 */
	private void initControl() {

		setLayout(new FlowLayout(FlowLayout.CENTER,0,15));
		mSaleItem=mOrderDetailItem.getSaleItem();

		mainContainer = new JPanel();
		mainContainer.setPreferredSize(new Dimension(782,460));
		mainContainer.setLayout(null);
		mainContainer.setOpaque(false);
		mainContainer.setBackground(Color.blue);
		add(mainContainer);

		itemCodeLabel = new JLabel("Item Code :");
		itemCodeLabel.setSize(LABEL_WIDTH, LABEL_HEIGHT);
		itemCodeLabel.setBackground(Color.lightGray);
		itemCodeLabel.setOpaque(true);
		itemCodeLabel.setLocation(10, 10);
		itemCodeLabel.setFont(PosFormUtil.getLabelFont());

		itemCodeText= new PosTextField();
		itemCodeText.setFont(PosFormUtil.getTextFieldBoldFont());
		itemCodeText.setEditable(false);
		itemCodeText.setSize(TEXT_FIELD_WIDTH, TEXT_FIELD_HEIGHT);
		itemCodeText.setLocation(213, 10);
		mainContainer.add(itemCodeLabel);
		mainContainer.add(itemCodeText);

		itemNameLabel = new JLabel("Name :");
		itemNameLabel.setBackground(Color.lightGray);
		itemNameLabel.setOpaque(true);
		itemNameLabel.setHorizontalTextPosition(JLabel.LEFT);
		itemNameLabel.setSize(LABEL_WIDTH,LABEL_HEIGHT);
		itemNameLabel.setLocation(10, 50);
		itemNameLabel.setFont(PosFormUtil.getLabelFont());

		itemNameText=new PosTextField();
		itemNameText.setFont(PosFormUtil.getTextFieldBoldFont());
		itemNameText.setEditable(false);
		itemNameText.setSize(TEXT_FIELD_WIDTH, TEXT_FIELD_HEIGHT);
		itemNameText.setLocation(213, 50);
		mainContainer.add(itemNameLabel);
		mainContainer.add(itemNameText);

		itemImage = new PosTextField();
		itemImage.setHorizontalAlignment(JLabel.CENTER);
		itemImage.setSize(195,195);
		itemImage.setOpaque(false);
//		itemImage.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
		itemImage.setEditable(false);
		itemImage.setLocation(578, 10);
		mainContainer.add(itemImage);

		itemAltNameLabel = new JLabel("Alternate Name :");
		itemAltNameLabel.setBackground(Color.lightGray);
		itemAltNameLabel.setOpaque(true);
		itemAltNameLabel.setSize(LABEL_WIDTH, LABEL_HEIGHT);
		itemAltNameLabel.setLocation(10, 90);
		itemAltNameLabel.setFont(PosFormUtil.getLabelFont());

		itemAltNameText= new PosTextField();
		itemAltNameText.setFont(PosFormUtil.getTextFieldBoldFont());
		itemAltNameText.setEditable(false);
		itemAltNameText.setSize(TEXT_FIELD_WIDTH, TEXT_FIELD_HEIGHT);
		itemAltNameText.setLocation(213, 90);
		mainContainer.add(itemAltNameLabel);
		mainContainer.add(itemAltNameText);
		
		itemClassLabel = new JLabel("Item Class :");
		itemClassLabel.setBackground(Color.lightGray);
		itemClassLabel.setOpaque(true);
		itemClassLabel.setSize(LABEL_WIDTH, LABEL_HEIGHT);
		itemClassLabel.setLocation(10, 130);
		itemClassLabel.setFont(PosFormUtil.getLabelFont());

		itemClassText= new PosTextField();
		itemClassText.setFont(PosFormUtil.getTextFieldBoldFont());
		itemClassText.setEditable(false);
		itemClassText.setSize(TEXT_FIELD_WIDTH, TEXT_FIELD_HEIGHT);
		itemClassText.setLocation(213, 130);
		mainContainer.add(itemClassLabel);
		mainContainer.add(itemClassText);

		itemPriceLabel = new JLabel("Price :");
		itemPriceLabel.setBackground(Color.lightGray);
		itemPriceLabel.setOpaque(true);
		itemPriceLabel.setSize(LABEL_WIDTH, LABEL_HEIGHT);
		itemPriceLabel.setLocation(10,170);
		itemPriceLabel.setFont(PosFormUtil.getLabelFont());

		itemPriceText= new PosTextField();
		itemPriceText.setHorizontalAlignment(JTextField.RIGHT);
		itemPriceText.setFont(PosFormUtil.getTextFieldBoldFont());
		itemPriceText.setEditable(false);
		itemPriceText.setSize(TEXT_FIELD_WIDTH, TEXT_FIELD_HEIGHT);
		itemPriceText.setLocation(213, 170);
		mainContainer.add(itemPriceLabel);
		mainContainer.add(itemPriceText);


		itemDescriptionLabel = new JLabel("Description");
		itemDescriptionLabel.setSize(LABEL_WIDTH, LABEL_HEIGHT);
		itemDescriptionLabel.setFont(PosFormUtil.getLabelFont());
		itemDescriptionLabel.setLocation(10, 210);
		mainContainer.add(itemDescriptionLabel);

		itemDescriptionText= new JTextArea();
		itemDescriptionText.setFont(new Font("sansserif", Font.PLAIN, 15));
		itemDescriptionText.setOpaque(false);
		itemDescriptionText.setEditable(false);
		itemDescriptionText.setLineWrap(true);
		itemDescriptionText.setWrapStyleWord(true);
		itemDescriptionText.setSize(763,180);
		itemDescriptionText.setLocation(10, 240);
		itemDescriptionText.setBorder(itemCodeText.getBorder());
		mainContainer.add(itemDescriptionText);
	}
	@Override
	public boolean onOkButtonClicked() {
		return false;
	}
	@Override
	public boolean onCancelButtonClicked() {
		return false;
	}

	@Override
	public void onResetButtonClicked() {

	}
}