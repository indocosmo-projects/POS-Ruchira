/**
 * 
 */
package com.indocosmo.pos.forms.split.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.RootPaneContainer;
import javax.swing.border.LineBorder;

import com.indocosmo.pos.common.enums.split.SplitBasedOn;
import com.indocosmo.pos.common.utilities.PosCurrencyUtil;
import com.indocosmo.pos.common.utilities.PosFormUtil;
import com.indocosmo.pos.forms.components.keypads.PosResizableNumericKeyPad;
import com.indocosmo.pos.forms.split.listners.ISimpleSplitMethodPanelListener;

/**
 * @author jojesh-13.2
 *
 */
public class SimpleSplitMethodPanelExt extends SimpleSplitMethodPanelBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final int TITLE_HEIGHT=30;
	private static final int EXACT_BUTTON_HEIGHT=46;
	
	private static final Color EXACT_BUTTON_ENABLED_BG_COLOR=Color.decode("#8C71DA");
	private static final Color EXACT_BUTTON_DISABLED_BG_COLOR=Color.LIGHT_GRAY;

	private JTextField txtValue;
	private PosResizableNumericKeyPad keyPad;
	private JLabel splitValueTitle;
	private JButton exactBtton;
	private boolean exactButtonVisible;

	public SimpleSplitMethodPanelExt(RootPaneContainer parent ,int width,int height){
		super(parent,width,height);

	}

	/* (non-Javadoc)
	 * @see com.indocosmo.pos.forms.split.components.SimpleSplitMethodPanelBase#createContents()
	 */
	@Override
	protected void createContents() {

		final int keypadHeight=getHeight();
		final int keypadWidth=(int)(getWidth()*(.65));
		final int clearButtonWidth=60;
		
		keyPad=new PosResizableNumericKeyPad(mParent, keypadWidth, keypadHeight);
		
		keyPad.setPlusMinusKeyEnabled(false);
		keyPad.setClearButtonsWidth(clearButtonWidth);
		keyPad.setLocation(getWidth()-keyPad.getWidth(),0);
		add(keyPad);

		splitValueTitle=PosFormUtil.setHeading("Amount", getWidth()-keyPad.getWidth(),TITLE_HEIGHT);
		splitValueTitle.setLocation(0, 0);
		this.add(splitValueTitle);

		
		final Font fnt=PosFormUtil.getLabelFont().deriveFont(Font.BOLD,35.0f);

		txtValue=new JTextField();
		txtValue.setHorizontalAlignment(JLabel.CENTER);
		txtValue.setForeground(Color.RED);
		txtValue.setFont(fnt);
		txtValue.setBorder(new LineBorder(new Color(78,128,188), 1));
//		txtValue.setSize(txtWidth,txtHeight);
//		txtValue.setPreferredSize(new Dimension(txtWidth,txtHeight));
		txtValue.setLocation(0,TITLE_HEIGHT);
		add(txtValue);

		exactBtton=new JButton("Set to Balance");
		exactBtton.setFocusable(false);
//		exactBtton.setSize(new Dimension(txtValue.getWidth(),EXACT_BUTTON_HEIGHT-2));
//		exactBtton.setLocation(txtValue.getX(), txtValue.getY()+txtValue.getHeight()+1);
		exactBtton.setForeground(Color.WHITE);
		exactBtton.setBackground(EXACT_BUTTON_ENABLED_BG_COLOR);
		exactBtton.setFont(PosFormUtil.getButtonFont());
		exactBtton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				if(listener!=null){

					double exactAmo=((ISimpleSplitMethodPanelListener)listener).onExactAmountRequested(SimpleSplitMethodPanelExt.this);
					if(exactAmo>0){
						txtValue.setText(PosCurrencyUtil.format(exactAmo));
					}

				}
			}
		});
		add(exactBtton);

		setExactButtonVisibiltiy();
		keyPad.setDisplayComponent(txtValue);

	}

	/* (non-Javadoc)
	 * @see com.indocosmo.pos.forms.split.components.SimpleSplitMethodPanelBase#getSplitValue()
	 */
	@Override
	public double getSplitValue() {

		double splitValue=0.0;
		try{

			splitValue= Double.parseDouble(txtValue.getText());

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
			PosFormUtil.showErrorMessageBox(mParent, "Invalid split value. Please select a valid split.");

		return valid;
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.pos.forms.components.splits.SplitMethodPanel#setSplitBasedOn(com.indocosmo.pos.common.enums.split.SplitBasedOn)
	 */
	@Override
	public void setSplitBasedOn(SplitBasedOn splitBasedOn) {
		// TODO Auto-generated method stub

		super.setSplitBasedOn(splitBasedOn);
		keyPad.setDecimalKeyEnabled((splitBasedOn==SplitBasedOn.Amount));
		splitValueTitle.setText(splitBasedOn.getDisplayText());
		txtValue.setText("");
		setExactButtonVisible(splitBasedOn==SplitBasedOn.Amount);
		requestFocus();

	}

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#requestFocus()
	 */
	@Override
	public void requestFocus() {

		txtValue.requestFocus();
	}

	/**
	 * @return the exactButtonVisible
	 */
	public boolean isExactButtonVisible() {
		return exactButtonVisible;
	}

	/**
	 * @param exactButtonVisible the exactButtonVisible to set
	 */
	public void setExactButtonVisible(boolean exactButtonVisible) {
		this.exactButtonVisible = exactButtonVisible;
		
		setExactButtonVisibiltiy();
	}
	
	/**
	 * 
	 */
	public void setExactButtonVisibiltiy(){
		
		exactBtton.setEnabled(exactButtonVisible);
		exactBtton.setBackground(((exactButtonVisible)?EXACT_BUTTON_ENABLED_BG_COLOR:EXACT_BUTTON_DISABLED_BG_COLOR));
		
		if(exactButtonVisible){
			
			final int txtHeight=getHeight()-TITLE_HEIGHT-EXACT_BUTTON_HEIGHT;
			final int txtWidth=getWidth()-keyPad.getWidth();
			
			txtValue.setSize(txtWidth,txtHeight);
			txtValue.setPreferredSize(new Dimension(txtWidth,txtHeight));
			exactBtton.setSize(new Dimension(txtValue.getWidth(),EXACT_BUTTON_HEIGHT-2));
			exactBtton.setLocation(txtValue.getX(), txtValue.getY()+txtValue.getHeight()+1);
			
		}else{
			
			final int txtHeight=getHeight()-TITLE_HEIGHT-1;
			final int txtWidth=getWidth()-keyPad.getWidth();
			txtValue.setSize(txtWidth,txtHeight);
			txtValue.setPreferredSize(new Dimension(txtWidth,txtHeight));
			exactBtton.setSize(new Dimension(0,0));
			exactBtton.setLocation(0, 0);
		}
		
		exactBtton.setVisible(exactButtonVisible);
		txtValue.invalidate();
		exactBtton.invalidate();
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.pos.forms.split.components.SplitMethodPanel#reset()
	 */
	@Override
	void reset() {
	
		txtValue.setText("");
		
	}
}
