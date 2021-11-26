/**
 * 
 */
package com.indocosmo.pos.forms.split.components;

import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.RootPaneContainer;

import com.indocosmo.pos.common.enums.split.SplitBasedOn;
import com.indocosmo.pos.common.utilities.PosFormUtil;
import com.indocosmo.pos.forms.components.buttons.PosButton;
import com.indocosmo.pos.forms.components.buttons.listners.IPosButtonListner;
import com.indocosmo.pos.forms.components.textfields.PosTouchableFieldBase;
import com.indocosmo.pos.forms.components.textfields.PosTouchableNumericField;
import com.indocosmo.pos.forms.components.textfields.listners.IPosTouchableFieldListner;

/**
 * @author jojesh-13.2
 *
 */
public class SimpleSplitMethodPanel extends SimpleSplitMethodPanelBase {

	private PosTouchableNumericField noOfSplitField;
	private JLabel noSplitTitle;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private SimpleSplitMethodPanel(RootPaneContainer parent ,int width,int height){
		super(parent,width,height);

	}

	/**
	 * 
	 */
	
	/* (non-Javadoc)
	 * @see com.indocosmo.pos.forms.split.components.SimpleSplitMethodPanelBase#createContents()
	 */
	@Override
	protected void createContents() {

		final int itemDefHeight=PosTouchableFieldBase.LAYOUT_DEF_HEIGHT;
		final String CLICK_BUTTON_NORMAL="click_textfield_button.png";
		final String CLICK_BUTTON_TOUCHED="click_textfield_button_touch.png";
		final int btnOptionWidth=50;
		final int btnOptionHeight=40;

		int top =PANEL_CONTENT_V_GAP*2;
		int left=PANEL_CONTENT_H_GAP;
		
		top =+PANEL_CONTENT_V_GAP+btnOptionHeight;
				//(getHeight()/2)-itemDefHeight/2;
		left=PANEL_CONTENT_H_GAP;

		noSplitTitle=new JLabel("");
		noSplitTitle.setFont(PosFormUtil.getLabelFont());
		noSplitTitle.setBounds(left,top,120,itemDefHeight);
		noSplitTitle.setHorizontalAlignment(JLabel.RIGHT);
		add(noSplitTitle);

		int width=getWidth()- noSplitTitle.getWidth()-PANEL_CONTENT_H_GAP*2;
		left=noSplitTitle.getX()+noSplitTitle.getWidth();
		noOfSplitField=new PosTouchableNumericField(mParent,width);
		noOfSplitField.setTextReadOnly(true);
		noOfSplitField.setText("1");
		noOfSplitField.setLocation(left, top);
		noOfSplitField.setTitle("No. of splits");
		noOfSplitField.setMinValue(1);
		noOfSplitField.setListner(new IPosTouchableFieldListner() {

			@Override
			public void onValueSelected(Object value) {

			}

			@Override
			public void onReset() {
				noOfSplitField.setText("1");				
			}
		});
		add(noOfSplitField);

		top=noOfSplitField.getY()-btnOptionHeight;
		left=noOfSplitField.getX()+noOfSplitField.getTexFieldComponent().getWidth()+2;

		PosButton plusButton=new PosButton();
		plusButton.setImage(CLICK_BUTTON_NORMAL);
		plusButton.setTouchedImage(CLICK_BUTTON_TOUCHED);
		plusButton.setBounds(left,top,btnOptionWidth, btnOptionHeight);
		plusButton.setPreferredSize(new Dimension(btnOptionWidth, btnOptionHeight));
		plusButton.setText("+");
		plusButton.setOnClickListner(new IPosButtonListner() {

			@Override
			public void onClicked(PosButton button) {
				double splitValue=getSplitValue()+1;
				noOfSplitField.setText(String.valueOf(splitValue));
			}
		});
		add(plusButton);

		top=noOfSplitField.getY()+btnOptionHeight;
		left=noOfSplitField.getX()+noOfSplitField.getTexFieldComponent().getWidth()+2;

		PosButton minusButton=new PosButton();
		minusButton.setImage(CLICK_BUTTON_NORMAL);
		minusButton.setTouchedImage(CLICK_BUTTON_TOUCHED);
		minusButton.setBounds(left,top,btnOptionWidth, btnOptionHeight);
		minusButton.setPreferredSize(new Dimension(btnOptionWidth, btnOptionHeight));
		minusButton.setText("-");
		minusButton.setOnClickListner(new IPosButtonListner() {

			@Override
			public void onClicked(PosButton button) {
				double splitValue=getSplitValue()-1;
				noOfSplitField.setText(String.valueOf(((splitValue>0)?splitValue:1)));
			}
		});
		add(minusButton);


	}
	/* (non-Javadoc)
	 * @see com.indocosmo.pos.forms.split.components.SimpleSplitMethodPanelBase#getSplitValue()
	 */
	@Override
	public double getSplitValue() {

		double splitValue=0.0;
		try{
			
			splitValue= Double.parseDouble(noOfSplitField.getText());
				
		}catch(Exception e){
			
		}

		return splitValue;
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.pos.forms.split.components.SplitMethodPanel#isSplitValid()
	 */
	@Override
	public boolean isSplitValid() {

		boolean valid=(getSplitValue()>0);
		if(!valid)
			PosFormUtil.showErrorMessageBox(mParent, "Invalid split count. Please select a valid split.");

		return valid;
	}
	
	/* (non-Javadoc)
	 * @see com.indocosmo.pos.forms.components.splits.SplitMethodPanel#setSplitBasedOn(com.indocosmo.pos.common.enums.split.SplitBasedOn)
	 */
	@Override
	public void setSplitBasedOn(SplitBasedOn splitBasedOn) {
		// TODO Auto-generated method stub
		super.setSplitBasedOn(splitBasedOn);
		noSplitTitle.setText(splitBasedOn.getDisplayText()+" : ");
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.pos.forms.split.components.SplitMethodPanel#reset()
	 */
	@Override
	void reset() {
		// TODO Auto-generated method stub
		
	}
}
