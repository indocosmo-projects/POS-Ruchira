/**
 * 
 */
package com.indocosmo.pos.forms.split.components;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;
import javax.swing.RootPaneContainer;

import com.indocosmo.pos.common.utilities.PosFormUtil;
import com.indocosmo.pos.forms.components.radiobuttons.PosRadioButton;
import com.indocosmo.pos.forms.messageboxes.PosMessageBoxForm.MessageBoxButtonTypes;
import com.indocosmo.pos.forms.messageboxes.PosMessageBoxForm.MessageBoxResults;

/**
 * @author jojesh-13.2
 *
 */
public class TableSeatSplitMethodPanel extends SplitMethodPanel {


	/**
	 * @author jojesh-13.2
	 *
	 */
	public enum TableSplitBasedOn{

		None,
		Table,
		Seat;
	}

	private static final long serialVersionUID = 1L;
	private static final int RADIO_HEIGHT = 40;

	private TableSplitBasedOn selectedTableSplitBasedOn;
	private PosRadioButton rdoTableBased;
	private PosRadioButton rdoSeatBased;

	/**
	 * 
	 */
	public TableSeatSplitMethodPanel(RootPaneContainer parent ,int width,int height){
		super(parent);

		setLayout(null);
		setSize(width, height);
		setPreferredSize(new Dimension(width, height));
		setVisible(false);
		createContents();

		setSelectedTableSplitBasedOn(TableSplitBasedOn.Seat);
	}

	/**
	 * 
	 */
	private void createContents(){

		final int vGap=30;
		final int hGap=10;
		final int radioButtonWidth=getWidth()-hGap*2;

		int left=hGap;
		int top=vGap;

		rdoTableBased = new PosRadioButton();
		rdoTableBased.setFocusable(false);
		rdoTableBased.setTag(TableSplitBasedOn.Table);
		rdoTableBased.setHorizontalAlignment(JTextField.LEFT);
		rdoTableBased.setBounds(left, top, radioButtonWidth, RADIO_HEIGHT);
		rdoTableBased.setFont(PosFormUtil.getTextFieldBoldFont());
		rdoTableBased.setText("Based on Table.");
		rdoTableBased.setSelected(true);
		rdoTableBased.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				final TableSplitBasedOn newBasedOn= (TableSplitBasedOn)((PosRadioButton)e.getSource()).getTag();

				if(newBasedOn==selectedTableSplitBasedOn) {
					rdoTableBased.setSelected(true);
					return;

				}

				if(canAlter(newBasedOn)){

					rdoSeatBased.setSelected(false);
					selectedTableSplitBasedOn=(TableSplitBasedOn)((PosRadioButton)e.getSource()).getTag();

				}else
					rdoTableBased.setSelected(false);

			}
		});
		this.add(rdoTableBased);

		top+=vGap+RADIO_HEIGHT;
		left=hGap;

		rdoSeatBased = new PosRadioButton();
		rdoSeatBased.setFocusable(false);
		rdoSeatBased.setTag(TableSplitBasedOn.Seat);
		rdoSeatBased.setHorizontalAlignment(JTextField.LEFT);
		rdoSeatBased.setBounds(left, top, radioButtonWidth, RADIO_HEIGHT);
		rdoSeatBased.setFont(PosFormUtil.getTextFieldBoldFont());
		rdoSeatBased.setText("Based on Seat.");
		rdoSeatBased.setSelected(false);
		rdoSeatBased.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				final TableSplitBasedOn newBasedOn= (TableSplitBasedOn)((PosRadioButton)e.getSource()).getTag();

				if(newBasedOn==selectedTableSplitBasedOn) {
					rdoSeatBased.setSelected(true);
					return;

				}

				if(canAlter(newBasedOn)){

					rdoTableBased.setSelected(false);
					selectedTableSplitBasedOn=(TableSplitBasedOn)((PosRadioButton)e.getSource()).getTag();
				}else
					rdoSeatBased.setSelected(false);

			}
		});
		this.add(rdoSeatBased);

	}

	/**
	 * @param newBasedOn
	 * @return
	 */
	private boolean canAlter(TableSplitBasedOn newBasedOn){

		boolean result=false;

		if(listener.hasSplit()){

			if(PosFormUtil.showQuestionMessageBox(mParent, MessageBoxButtonTypes.YesNo, "This will reset current splitiing. Do you want to continue?", null)==MessageBoxResults.Yes){

				listener.onResetRequested();
				result=true;

			}
		}else
			result=true;

		return result;
	}


	/**
	 * @return
	 */
	public boolean isInputValid(){

		boolean valid=true;

		return valid;
	}

	/**
	 * @return the selectedTableSplitBasedOn
	 */
	public TableSplitBasedOn getSelectedTableSplitBasedOn() {

		return selectedTableSplitBasedOn;
	}

	/**
	 * @param selectedTableSplitBasedOn the selectedTableSplitBasedOn to set
	 */
	public void setSelectedTableSplitBasedOn(
			TableSplitBasedOn selectedTableSplitBasedOn) {

		if(this.selectedTableSplitBasedOn == selectedTableSplitBasedOn) 
			return;

		this.selectedTableSplitBasedOn = selectedTableSplitBasedOn;

		rdoSeatBased.setSelected(selectedTableSplitBasedOn==TableSplitBasedOn.Seat);
		rdoTableBased.setSelected(selectedTableSplitBasedOn==TableSplitBasedOn.Table);

	}

	/* (non-Javadoc)
	 * @see com.indocosmo.pos.forms.split.components.SplitMethodPanel#reset()
	 */
	@Override
	void reset() {

		setSelectedTableSplitBasedOn(TableSplitBasedOn.Seat);

	}


}
