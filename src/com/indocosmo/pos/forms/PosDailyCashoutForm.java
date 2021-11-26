/**
 * 
 */
package com.indocosmo.pos.forms;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.RootPaneContainer;
import javax.swing.SortOrder;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import com.indocosmo.pos.common.PosEnvSettings;
import com.indocosmo.pos.common.PosLog;
import com.indocosmo.pos.common.utilities.PosAccessPermissionsUtil;
import com.indocosmo.pos.common.utilities.PosCurrencyUtil;
import com.indocosmo.pos.common.utilities.PosDateUtil;
import com.indocosmo.pos.common.utilities.PosFormUtil;
import com.indocosmo.pos.common.utilities.PosNumberUtil;
import com.indocosmo.pos.data.beans.BeanAccessLog;
import com.indocosmo.pos.data.beans.BeanCashout;
import com.indocosmo.pos.data.beans.BeanCashoutRecentItem;
import com.indocosmo.pos.data.beans.BeanUser;
import com.indocosmo.pos.data.providers.shopdb.PosAccessLogProvider;
import com.indocosmo.pos.data.providers.shopdb.PosCashOutProvider;
import com.indocosmo.pos.forms.components.buttons.PosButton;
import com.indocosmo.pos.forms.components.buttons.PosButton.ButtonStyle;
import com.indocosmo.pos.forms.components.buttons.listners.IPosButtonListner;
import com.indocosmo.pos.forms.components.search.IPosSearchableItem;
import com.indocosmo.pos.forms.components.textfields.PosTouchableFieldBase;
import com.indocosmo.pos.forms.components.textfields.PosTouchableNumericField;
import com.indocosmo.pos.forms.components.textfields.PosTouchableTextField;
import com.indocosmo.pos.forms.components.textfields.listners.IPosTouchableFieldListner;
import com.indocosmo.pos.forms.listners.IPosFormEventsListner;
import com.indocosmo.pos.forms.messageboxes.PosMessageBoxForm.MessageBoxButtonTypes;
import com.indocosmo.pos.forms.messageboxes.PosMessageBoxForm.MessageBoxResults;
import com.indocosmo.pos.forms.search.PosExtSearchForm;
import com.indocosmo.pos.forms.search.listener.IPosItemExtSearchFormListener;
import com.indocosmo.pos.forms.search.listener.adapter.PosItemExtSearchFormAdapter;
import com.indocosmo.pos.terminal.devices.PosDeviceManager;

/**
 * @author jojesh
 *
 */
public class PosDailyCashoutForm extends PosBaseForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int LAYOUT_HEIGHT =350;
	private static final int LAYOUT_WIDTH = 600;
	
	private static final int PANEL_CONTENT_H_GAP=2;
	private static final int PANEL_CONTENT_V_GAP=2;
	
	private PosButton mButtonDelete;
	private PosButton mButtonCashBox;
	private JPanel mContentPanel;
	
	private JTextField txtPosUser;
	private JTextField txtPosDate;
	private PosTouchableTextField txtTitle;
	private PosTouchableNumericField txtAmount;
	private JTextArea txtRemarks;
	private BeanCashout mCashoutItem;
	private ArrayList<BeanCashoutRecentItem> recentRemarks;
	private ArrayList<BeanCashoutRecentItem> recentTitles;
	
	
	private PosCashOutProvider mCashOutProvider;
	
	public PosDailyCashoutForm(RootPaneContainer parent ){
		super("Daily Expenses",LAYOUT_WIDTH,LAYOUT_HEIGHT);
		
		setParent((Container)parent);
		
		mButtonDelete=addButtonsToBottomPanel("Delete", mDeleteButtonListener, 0);
		mButtonDelete.setImage("dlg_reset.png","dlg_reset_touch.png");
		
		 createCashBoxButton();
		
		mCashOutProvider=new PosCashOutProvider();
		setOkButtonCaption("Save");
		setCancelButtonCaption("Close");
		createFields();
		reset();
		setDefaultComponent(txtTitle);
		
	}
	
	/**
	 * 
	 */
	private void createCashBoxButton(){
		
		mButtonCashBox = new PosButton();
		mButtonCashBox.setText("Cash Box");
		mButtonCashBox.setMnemonic('B');
		mButtonCashBox.setImage("exp_cash_box.png","exp_cash_box_touch.png");
		mButtonCashBox.setHorizontalAlignment(SwingConstants.CENTER);
		mButtonCashBox.setBounds(0, PANEL_CONTENT_V_GAP * 5, 100,
				60);
		mButtonCashBox.setOnClickListner(openCashBoxButtonListener);
		
		if(PosDeviceManager.getInstance().hasReceiptPrinter() && PosDeviceManager.getInstance().getReceiptPrinter()!=null && PosDeviceManager.getInstance().getReceiptPrinter().hasCashBox()){
			if(PosDeviceManager.getInstance().getReceiptPrinter().canOpenManually()){
				mButtonCashBox.setEnabled(true);				
			}
		}else if (PosDeviceManager.getInstance().hasCashBox() && PosDeviceManager.getInstance().getCashBox().canOpenManually()){
			mButtonCashBox.setEnabled(true);		
		}
		
		addButtonsToBottomPanel(mButtonCashBox,0);
	}
	
	/**
	 * 
	 */
	private IPosButtonListner openCashBoxButtonListener = new IPosButtonListner() {
		
//		private PosAccessLogProvider accesslogProvider;

		@Override
		public void onClicked(PosButton button) {
//			PosUserAuthenticateForm loginForm=new PosUserAuthenticateForm("Open cash drawer? ");
//			PosFormUtil.showLightBoxModal(PosDailyCashoutForm.this,loginForm);
//			BeanUser user=loginForm.getUser();
//			if(user!=null&&PosAccessPermissionsUtil.validateAccess(PosDailyCashoutForm.this,user.getUserGroupId(), "open_cash_drawer")){
//				BeanAccessLog accessLog= new BeanAccessLog();
//				accessLog.setFunctionName("open_cash_drawer");
//				accessLog.setUserName(user.getName());
//				accessLog.setAccessTime(PosDateUtil.getDateTime());
//				accesslogProvider = new PosAccessLogProvider();
//				accesslogProvider.updateAccessLog(accessLog);
//				
//				try {
					PosDeviceManager.getInstance().getReceiptPrinter().openCashBoxManually();
//				} catch (Exception e) {
//					PosLog.write(this, "openCashBoxButtonListener", e);
//				}
//			}
			
		}
	};
	

	/**
	 * 
	 */
	private void createFields() {
		
		final int captionWidth=150;
		final int captionHeight=PosTouchableFieldBase.LAYOUT_DEF_HEIGHT;
		final Dimension captionSize=new Dimension(captionWidth,captionHeight);
		
		final int valueWidth=mContentPanel.getWidth()-captionWidth-PANEL_CONTENT_H_GAP*5;
		final int valueHeight=PosTouchableFieldBase.LAYOUT_DEF_HEIGHT;
		final Dimension valeSize=new Dimension( valueWidth ,valueHeight);
		final int retriveButtonWidth=100;
	
		JLabel lblTitle=new JLabel();
		
		lblTitle.setOpaque(true);
		lblTitle.setBackground(Color.LIGHT_GRAY);
		lblTitle.setText("Date :");
		lblTitle.setBorder(new EmptyBorder(2, 2, 2, 2));
		lblTitle.setHorizontalAlignment(SwingConstants.LEFT);		
		lblTitle.setPreferredSize(captionSize); 	
		lblTitle.setFont(PosFormUtil.getLabelFont());
		lblTitle.setFocusable(true);
		mContentPanel.add(lblTitle);
		
		txtPosDate=new JTextField();
		txtPosDate.setHorizontalAlignment(JTextField.LEFT);
		txtPosDate.setEditable(false);
		txtPosDate.setFont(PosFormUtil.getTextFieldFont());
		txtPosDate.setPreferredSize(new Dimension(valueWidth-retriveButtonWidth-PANEL_CONTENT_H_GAP,valueHeight));
		mContentPanel.add(txtPosDate);
		
		final PosButton btnRetrieve=new PosButton("Search...");
		btnRetrieve.setMnemonic('e');
		btnRetrieve.setPreferredSize(new Dimension(retriveButtonWidth,valueHeight));
		btnRetrieve.setButtonStyle(ButtonStyle.IMAGE);
		btnRetrieve.setImage("cashout_search.png", "cashout_search_touch.png");
		btnRetrieve.setOnClickListner(new IPosButtonListner() {
			
			@Override
			public void onClicked(PosButton button) {
			
				try {
					final ArrayList<BeanCashout> itemList=mCashOutProvider.getCashOutList(PosEnvSettings.getInstance().getPosDate(),PosEnvSettings.getInstance().getCashierShiftInfo().getShiftItem().getId());
					if(itemList!=null && itemList.size()>0){
						
						PosExtSearchForm form=new PosExtSearchForm(itemList);
						form.setListner(new IPosItemExtSearchFormListener() {
							
							@Override
							public boolean onCancel(Object sender) {

								return true;
							}
							
							@Override
							public boolean onAccepted(Object sender, IPosSearchableItem item) {				
								
								reset();
								mCashoutItem=(BeanCashout)item;
								setItem(mCashoutItem);
								return true;
							}

							@Override
							public void onDetailClicked(Object sender,
									IPosSearchableItem item) {
								// TODO Auto-generated method stub
								
							}
						});
						PosFormUtil.showLightBoxModal(PosDailyCashoutForm.this, form);
					}
				} catch (Exception e) {
					
					PosLog.write(PosDailyCashoutForm.this, "btnRetrieve.onClicked", e);
					PosFormUtil.showErrorMessageBox(PosDailyCashoutForm.this, "Failed to load items. Please check log.");
				}
				
			}
		});
		mContentPanel.add(btnRetrieve);

		lblTitle=new JLabel();
		lblTitle.setOpaque(true);
		lblTitle.setBackground(Color.LIGHT_GRAY);
		lblTitle.setText("User :");
		lblTitle.setBorder(new EmptyBorder(2, 2, 2, 2));
		lblTitle.setHorizontalAlignment(SwingConstants.LEFT);		
		lblTitle.setPreferredSize(captionSize); 	
		lblTitle.setFont(PosFormUtil.getLabelFont());
		lblTitle.setFocusable(true);
		mContentPanel.add(lblTitle);
		
		txtPosUser=new JTextField();
		txtPosUser.setHorizontalAlignment(JTextField.LEFT);
		txtPosUser.setEditable(false);
		txtPosUser.setFont(PosFormUtil.getTextFieldFont());
		txtPosUser.setPreferredSize(valeSize);
		mContentPanel.add(txtPosUser);
		
		lblTitle=new JLabel();
		lblTitle.setOpaque(true);
		lblTitle.setBackground(Color.LIGHT_GRAY);
		lblTitle.setText(PosFormUtil.getMnemonicString("Title :",'t'));
		lblTitle.setBorder(new EmptyBorder(2, 2, 2, 2));
		lblTitle.setHorizontalAlignment(SwingConstants.LEFT);		
		lblTitle.setPreferredSize(captionSize); 	
		lblTitle.setFont(PosFormUtil.getLabelFont());
		lblTitle.setFocusable(true);
		mContentPanel.add(lblTitle);
		
		final int buttonWidth=50;
		
		txtTitle=new PosTouchableTextField(this,valeSize.width-buttonWidth-PANEL_CONTENT_H_GAP);
		txtTitle.setTitle("Title");
		txtTitle.setMnemonic('t');
		txtTitle.setFont(PosFormUtil.getTextFieldFont());
		mContentPanel.add(txtTitle);
		
		final PosButton btnTitleRetrieve=new PosButton("...");
		btnTitleRetrieve.setMnemonic('l');
		btnTitleRetrieve.setPreferredSize(new Dimension(buttonWidth, valeSize.height));
		btnTitleRetrieve.setButtonStyle(ButtonStyle.IMAGE);
		btnTitleRetrieve.setImage("cashout_search.png", "cashout_search_touch.png");
		btnTitleRetrieve.setOnClickListner( new IPosButtonListner() {
			
			@Override
			public void onClicked(PosButton button) {
				
//				if(recentCashouts==null){
					
					try{
						
						recentTitles=mCashOutProvider.getRecentCashoutTitle(); 
					}catch(Exception e){
						
						PosLog.write(PosDailyCashoutForm.this, "recentItemList.onClicked", e);
					}
//				}
				
				if(recentTitles!=null && recentTitles.size()>0){
					
					final PosExtSearchForm serachForm=new PosExtSearchForm(recentTitles);
					serachForm.setWindowTitle("Recent item list");
					serachForm.setSorting(0, SortOrder.ASCENDING);
					serachForm.setListner(new PosItemExtSearchFormAdapter() {

						@Override
						public boolean onAccepted(Object sender, IPosSearchableItem item) {
							
//							mCashoutItem=null;
							final BeanCashoutRecentItem selItem=(BeanCashoutRecentItem)item;
							//txtAmount.setText(PosCurrencyUtil.format(selItem.getAmount()));
							txtTitle.setText(selItem.getRemarks());
							
							return true;
						}
						
					});
					
					PosFormUtil.showLightBoxModal(getParent(),serachForm);
				}
			}
		});

		mContentPanel.add(btnTitleRetrieve);
		
		lblTitle=new JLabel();
		lblTitle.setOpaque(true);
		lblTitle.setBackground(Color.LIGHT_GRAY);
		lblTitle.setText(PosFormUtil.getMnemonicString("Amount", 'A'));
		lblTitle.setBorder(new EmptyBorder(2, 2, 2, 2));
		lblTitle.setHorizontalAlignment(SwingConstants.LEFT);		
		lblTitle.setPreferredSize(captionSize); 	
		lblTitle.setFont(PosFormUtil.getLabelFont());
		lblTitle.setFocusable(true);
		mContentPanel.add(lblTitle);
		
		txtAmount=new PosTouchableNumericField(this,(int)valeSize.getWidth());
		txtAmount.setMnemonic('A');
		txtAmount.setHorizontalTextAlignment(SwingConstants.RIGHT);
		txtAmount.setTitle("Amount");
		txtAmount.setListner(new IPosTouchableFieldListner() {
			
			@Override
			public void onValueSelected(Object value) {

				txtRemarks.requestFocus();
				
			}
			
			@Override
			public void onReset() {
				// TODO Auto-generated method stub
				
			}
		});
		mContentPanel.add(txtAmount);
		
		lblTitle=new JLabel();
		lblTitle.setOpaque(true);
		lblTitle.setBackground(Color.LIGHT_GRAY);
		lblTitle.setText(PosFormUtil.getMnemonicString("Remarks", 'm'));
		lblTitle.setBorder(new EmptyBorder(2, 2, 2, 2));
		lblTitle.setHorizontalAlignment(SwingConstants.LEFT);		
		lblTitle.setPreferredSize(captionSize); 	
		lblTitle.setFont(PosFormUtil.getLabelFont());
		lblTitle.setFocusable(true);
		mContentPanel.add(lblTitle);
		
		final PosButton remarksEditButton=new PosButton("...");
		remarksEditButton.setButtonStyle(ButtonStyle.IMAGE);
		remarksEditButton.setMnemonic('m');
		remarksEditButton.setPreferredSize(new Dimension(PosTouchableFieldBase.BROWSE_BUTTON_WIDTH,PosTouchableFieldBase.BROWSE_BUTTON_HEIGHT));
		remarksEditButton.setImage(PosTouchableFieldBase.CLICK_BUTTON_NORMAL, PosTouchableFieldBase.CLICK_BUTTON_TOUCHED);
		remarksEditButton.setOnClickListner(new IPosButtonListner() {
			
			@Override
			public void onClicked(PosButton button) {
				
				final PosRemarksEditForm form=new PosRemarksEditForm();
				form.setRemarks(txtRemarks.getText());
				form.setListner(new IPosFormEventsListner() {
					
					@Override
					public void onResetButtonClicked() {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public boolean onOkButtonClicked() {
						txtRemarks.setText(form.getRemarks());
						return true;
					}
					
					@Override
					public boolean onCancelButtonClicked() {
						// TODO Auto-generated method stub
						return false;
					}
				});
				PosFormUtil.showLightBoxModal(PosDailyCashoutForm.this,form);
				
			}
		});
		mContentPanel.add(remarksEditButton);
		
		final PosButton remarksResetButton=new PosButton();
		remarksResetButton.setButtonStyle(ButtonStyle.IMAGE);
		remarksResetButton.setPreferredSize(new Dimension(PosTouchableFieldBase.RESET_BUTTON_DEF_WIDTH,PosTouchableFieldBase.RESET_BUTTON_DEF_HEIGHT));
		remarksResetButton.setImage(PosTouchableFieldBase.RESET_BUTTON_NORMAL, PosTouchableFieldBase.RESET_BUTTON_TOUCHED);
		remarksResetButton.setOnClickListner(new IPosButtonListner() {
			
			@Override
			public void onClicked(PosButton button) {
				
				if(PosFormUtil.showQuestionMessageBox(PosDailyCashoutForm.this, MessageBoxButtonTypes.YesNo, "Are you sure, do you want to clear the remarks?",null)==MessageBoxResults.Yes){
					
					txtRemarks.setText("");
				}
			}
		});
		mContentPanel.add(remarksResetButton);
		
		final PosButton recentItemList=new PosButton();
		recentItemList.setText("Previous...");
		recentItemList.setButtonStyle(ButtonStyle.IMAGE);
		recentItemList.setPreferredSize(new Dimension(150,40));
//		recentItemList.setImage(PosTouchableFieldBase.RESET_BUTTON_NORMAL, PosTouchableFieldBase.RESET_BUTTON_TOUCHED);
		recentItemList.setImage("oreder_view_remarks.png", "oreder_view_remarks_touch.png");
		recentItemList.setOnClickListner(new IPosButtonListner() {
			
			@Override
			public void onClicked(PosButton button) {
				
//				if(recentCashouts==null){
					
					try{
						
						recentRemarks=mCashOutProvider.getRecentCashoutRemarks(); 
					}catch(Exception e){
						
						PosLog.write(PosDailyCashoutForm.this, "recentItemList.onClicked", e);
					}
//				}
				
				if(recentRemarks!=null  && recentRemarks.size()>0){
					
					final PosExtSearchForm serachForm=new PosExtSearchForm(recentRemarks);
					serachForm.setWindowTitle("Recent item list");
					serachForm.setSorting(0, SortOrder.ASCENDING);
					serachForm.setListner(new PosItemExtSearchFormAdapter() {

						@Override
						public boolean onAccepted(Object sender, IPosSearchableItem item) {
							
//							mCashoutItem=null;
							final BeanCashoutRecentItem selItem=(BeanCashoutRecentItem)item;
							//txtAmount.setText(PosCurrencyUtil.format(selItem.getAmount()));
							txtRemarks.setText(selItem.getRemarks());
							
							return true;
						}
						
					});
					
					PosFormUtil.showLightBoxModal(getParent(),serachForm);
				}
			}
		});
		mContentPanel.add(recentItemList);

		txtRemarks=new JTextArea(5,30);
		txtRemarks.setLineWrap(true);
		txtRemarks.setFont(PosFormUtil.getTextFieldBoldFont());
		mContentPanel.add(txtRemarks);
		
		JScrollPane scrolPane=new JScrollPane(txtRemarks);
		scrolPane.getVerticalScrollBar().setPreferredSize(new Dimension(20,0));
		scrolPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrolPane.setPreferredSize(new Dimension(mContentPanel.getWidth()-PANEL_CONTENT_H_GAP*4,115));
		scrolPane.setBorder(new LineBorder(new Color(134, 184, 232)));
		mContentPanel.add(scrolPane);
	
	}


	/**
	 * @param cashoutItem
	 */
	private void setItem(BeanCashout cashoutItem) {
		
		txtAmount.setText(PosCurrencyUtil.format(cashoutItem.getAmount()));
		txtTitle.setText(cashoutItem.getTitle());
		txtRemarks.setText(cashoutItem.getRemarks());
		txtPosDate.setText(cashoutItem.getCashOutDisplayTime());
		txtPosUser.setText(cashoutItem.getUserName());
		
	}
	
	private IPosButtonListner mDeleteButtonListener=new IPosButtonListner() {
		
		@Override
		public void onClicked(PosButton button) {
			
			if(mCashoutItem!=null){
				
				if(PosFormUtil.showQuestionMessageBox(PosDailyCashoutForm.this, MessageBoxButtonTypes.YesNo, "Do you want to delete this item?", null)==MessageBoxResults.Yes){
					
					try{
						
						mCashoutItem.setUpdatedBy(getActiveUser().getId());
						mCashOutProvider.deleteData(mCashoutItem);
						if(PosFormUtil.showQuestionMessageBox(PosDailyCashoutForm.this, MessageBoxButtonTypes.YesNo, "Item has been deleted. Do you want to exit?", null)==MessageBoxResults.Yes)
							closeWindow();
						else
							reset();
						
					}catch(Exception e){
						
						PosLog.write(PosDailyCashoutForm.this, "mDeleteButtonListener.onClicked", e);
						PosFormUtil.showErrorMessageBox(PosDailyCashoutForm.this, "Failed to delete the item. Please check log.");
					}
				}
					
			}
			
		}
	};

	/* (non-Javadoc)
	 * @see com.indocosmo.pos.forms.PosBaseForm#setContentPanel(javax.swing.JPanel)
	 */
	@Override
	protected void setContentPanel(JPanel panel) {
		
		mContentPanel=panel;
		mContentPanel.setLayout(new FlowLayout(FlowLayout.LEFT, PANEL_CONTENT_H_GAP,PANEL_CONTENT_V_GAP));
	}
	
	/* (non-Javadoc)
	 * @see com.indocosmo.pos.forms.PosBaseForm#onOkButtonClicked()
	 */
	@Override
	public boolean onOkButtonClicked() {
		
		boolean isValid=validateInputs();
	
		if(isValid) {
			try {
				
				if(mCashoutItem==null){

					mCashoutItem=new BeanCashout();
					mCashoutItem.setCashOutDate(PosEnvSettings.getInstance().getPosDate());
					mCashoutItem.setCashOutTime(PosDateUtil.getDateTime());
					mCashoutItem.setUser(getActiveUser());
					mCashoutItem.setStation(PosEnvSettings.getInstance().getStation());
					mCashoutItem.setCreatedBy(getActiveUser().getId());
					mCashoutItem.setShiftId(PosEnvSettings.getInstance().getCashierShiftInfo().getShiftItem().getId());
				}

				mCashoutItem.setAmount(PosNumberUtil.parseDoubleSafely(txtAmount.getText()));
				mCashoutItem.setTitle(txtTitle.getText());
				mCashoutItem.setRemarks(txtRemarks.getText());
				mCashoutItem.setUpdatedBy(getActiveUser().getId());
				mCashOutProvider.save(mCashoutItem);
				
				if(PosFormUtil.showQuestionMessageBox(this, MessageBoxButtonTypes.YesNo, "Details has been saved. Do you want to add more?", null)==MessageBoxResults.Yes){
					isValid=false;
					reset();
				}
				 
				
			} catch (Exception e) {
				
				PosLog.write(this, "onOkButtonClicked", e);
				PosFormUtil.showErrorMessageBox(this, "Failed to save the details. Please check log.");
				isValid=false;
			}
		}
		
		return isValid;
	}

	/**
	 * 
	 */
	private void reset() {
		
		mCashoutItem=null;
		txtAmount.setText("");
		txtRemarks.setText("");
		txtTitle.setText("");
		
		final String date=PosEnvSettings.getInstance().getPosDate();
		txtPosDate.setText(date);
		
		final String userName=(getActiveUser()!=null)?getActiveUser().getName():"";
		txtPosUser.setText(userName);
		
	}

	/**
	 * @return
	 */
	private boolean validateInputs() {
		
		boolean isValid=true;
		
		double amount=PosNumberUtil.parseDoubleSafely(txtAmount.getText());
		if(amount<=0){
			
			isValid=false;
			PosFormUtil.showErrorMessageBox(this, "Invalid amount enterd. Please input valid amount.");
			txtAmount.requestFocus();
		}else if(txtTitle.getText().trim().isEmpty()){
			
			isValid=false;
			PosFormUtil.showErrorMessageBox(this, "No valid title enterd. Please input title.");
			txtTitle.requestFocus();
			
		}else if(txtRemarks.getText().trim().isEmpty()){
			
			isValid=false;
			PosFormUtil.showErrorMessageBox(this, "No valid remarks enterd. Please input remarks.");
			txtRemarks.requestFocus();
		}
		
		return isValid;
	}

	
	/* (non-Javadoc)
	 * @see com.indocosmo.pos.forms.PosBaseForm#setActiveUser(com.indocosmo.pos.data.beans.BeanUser)
	 */
	@Override
	public void setActiveUser(BeanUser activeUser) {
		super.setActiveUser(activeUser);
		txtPosUser.setText(getActiveUser().getName());
	}

}
