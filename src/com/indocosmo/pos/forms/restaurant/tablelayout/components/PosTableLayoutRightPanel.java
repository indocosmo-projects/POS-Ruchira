/**
 * 
 */
package com.indocosmo.pos.forms.restaurant.tablelayout.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.RootPaneContainer;
import javax.swing.SortOrder;
import javax.swing.border.EtchedBorder;

import com.indocosmo.pos.common.utilities.PosFormUtil;
import com.indocosmo.pos.common.utilities.PosNumberUtil;
import com.indocosmo.pos.common.utilities.PosResUtil;
import com.indocosmo.pos.data.beans.BeanEmployees;
import com.indocosmo.pos.data.beans.BeanServingTable;
import com.indocosmo.pos.data.beans.BeanServingTableExt;
import com.indocosmo.pos.data.beans.BeanServingTableLocation;
import com.indocosmo.pos.data.providers.shopdb.PosWaiterProvider;
import com.indocosmo.pos.forms.components.buttons.PosButton;
import com.indocosmo.pos.forms.components.buttons.PosButton.ButtonStyle;
import com.indocosmo.pos.forms.components.buttons.listners.IPosButtonListner;
import com.indocosmo.pos.forms.components.textfields.PosItemBrowsableField;
import com.indocosmo.pos.forms.components.textfields.PosItemSearchableField;
import com.indocosmo.pos.forms.components.textfields.PosTouchableDigitalField;
import com.indocosmo.pos.forms.components.textfields.PosTouchableFieldBase;
import com.indocosmo.pos.forms.components.textfields.listners.IPosTouchableFieldListner;
import com.indocosmo.pos.forms.components.textfields.listners.PosTouchableFieldAdapter;
import com.indocosmo.pos.forms.messageboxes.PosMessageBoxForm.MessageBoxButtonTypes;
import com.indocosmo.pos.forms.messageboxes.PosMessageBoxForm.MessageBoxResults;

/**
 * @author jojesh
 * 
 */
public class PosTableLayoutRightPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected static final int PANEL_CONTENT_H_GAP = 4;
	protected static final int PANEL_CONTENT_V_GAP = 8;

	private static final String IMG_TABLE_ADD_NORMAL="add_table.png";
	private static final String IMG_TABLE_ADD_TOUCH="add_table_touch.png";
	private static final String IMG_TABLE_DELETE_NORMAL="delete_table.png";
	private static final String IMG_TABLE_DELETE_TOUCH="delete_table_touch.png";

	private static final String IMG_OK_BUTTON_NORMAL="table_dlg_ok.png";
	private static final String IMG_OK_BUTTON_TOUCH="table_dlg_ok_touch.png";

	private static final String IMG_CANCEL_BUTTON_NORMAL="table_dlg_cancel.png";
	private static final String IMG_CANCEL_BUTTON_TOUCH="table_dlg_cancel_touch.png";
	
	private static final String IMG_RESET_BUTTON_NORMAL="table_dlg_reset.png";
	private static final String IMG_RESET_BUTTON_TOUCH="table_dlg_reset_touch.png";
	private static final int BUTTON_WIDTH=253;//getWidth()-7;
	private static final int BUTTON_HEIGHT=60;

	private ImageIcon imgAddTableNormal;
	private ImageIcon imgAddTableTouch;
	private ImageIcon imgDeleteTableNormal;
	private ImageIcon imgDeleteTableTouch;

	private ImageIcon imgOkButtonNormal;
	private ImageIcon imgOkButtonTouch;

	private ImageIcon imgCancelButtonNormal;
	private ImageIcon imgCancelButtonTouch;
	
	private ImageIcon imgResetButtonNormal;
	private ImageIcon imgResetButtonTouch;


	/**
	 * 
	 */
	private int mWidth;
	private int mHeight;

	private RootPaneContainer mParent;

	private BeanEmployees mSelectedWaiter;
	private PosItemBrowsableField mTxtWaiterCard;
	private PosWaiterProvider waiterProvider ;
	private BeanServingTableExt selectedTable;
	private int selectedPax;
	private ArrayList<BeanServingTableLocation> tableLocations;
	private ArrayList<BeanEmployees> mWaiterList;
	private PosItemBrowsableField mTxtTableLocation;

	private JTextField mtxtOders;
	private JTextField mtxtTblName;
	private PosItemSearchableField mtxtTblCode;
	private PosTouchableDigitalField mtxtTblSeatsCount;
	private PosTouchableDigitalField mtxtPax;
	private Font infoFont=PosFormUtil.getTextFieldFont();//.deriveFont(20f).deriveFont(Font.BOLD);
	private boolean tableSelectionOnly=false;


	private PosButton btnCustomer;
	public PosTableLayoutRightPanel(RootPaneContainer parent, int width,int height, boolean tableSelectionOnly) {

		this.mHeight = height;
		this.mWidth = width;
		this.mParent = parent;
		this.tableSelectionOnly=tableSelectionOnly;
		initComponents();
	}

	public PosTableLayoutRightPanel(RootPaneContainer parent, int width,int height) {

		this.mHeight = height;
		this.mWidth = width;
		this.mParent = parent;

		initComponents();
	}

	/**
	 * 
	 */
	private void initComponents() {

		loadImages();

		setLayout(null);
		setBorder(new EtchedBorder(EtchedBorder.LOWERED));
		setPreferredSize(new Dimension(mWidth, mHeight));

		setSize(new Dimension(mWidth, mHeight));

		JPanel tableInfoPanel=new JPanel();
		tableInfoPanel.setBounds(2, 2, getWidth()-4, 300);
		tableInfoPanel.setLayout(new FlowLayout(FlowLayout.LEFT,1,1));
		this.add(tableInfoPanel);

		createTableInfo(tableInfoPanel);

		JPanel oderInfoPanel=new JPanel();
		oderInfoPanel.setBounds(tableInfoPanel.getX(), tableInfoPanel.getY()+tableInfoPanel.getHeight()+2
				, getWidth()-4, BUTTON_HEIGHT*4);
		oderInfoPanel.setLayout(new FlowLayout(FlowLayout.LEFT,1,1));
		if(!tableSelectionOnly)
			this.add(oderInfoPanel);

		createOrderInfo(oderInfoPanel);
//		createCustomerInfo(oderInfoPanel);
		createDialogButtons();

		setWaiters();
	}

	/**
	 * 
	 */
	private void loadImages() {

		imgAddTableNormal=PosResUtil.getImageIconFromResource(IMG_TABLE_ADD_NORMAL);
		imgAddTableTouch=PosResUtil.getImageIconFromResource(IMG_TABLE_ADD_TOUCH);

		imgDeleteTableNormal=PosResUtil.getImageIconFromResource(IMG_TABLE_DELETE_NORMAL);
		imgDeleteTableTouch=PosResUtil.getImageIconFromResource(IMG_TABLE_DELETE_TOUCH);

		imgOkButtonNormal=PosResUtil.getImageIconFromResource(IMG_OK_BUTTON_NORMAL);
		imgOkButtonTouch=PosResUtil.getImageIconFromResource(IMG_OK_BUTTON_TOUCH);

		imgCancelButtonNormal=PosResUtil.getImageIconFromResource(IMG_CANCEL_BUTTON_NORMAL);
		imgCancelButtonTouch=PosResUtil.getImageIconFromResource(IMG_CANCEL_BUTTON_TOUCH);
		
		imgResetButtonNormal=PosResUtil.getImageIconFromResource(IMG_RESET_BUTTON_NORMAL);
		imgResetButtonTouch=PosResUtil.getImageIconFromResource(IMG_RESET_BUTTON_TOUCH);

	}

	/**
	 * 
	 */
	private void createDialogButtons() {



		int left=2;
		int top=getHeight()-4-BUTTON_HEIGHT*3;

		JPanel butonPanel=new JPanel();
		butonPanel.setBounds(left, top, getWidth()-4, BUTTON_HEIGHT*5);
		butonPanel.setLayout(new FlowLayout(FlowLayout.LEFT,1,1));
		this.add(butonPanel);

		final Dimension btnSize=new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT);

		final PosButton btnOk=new PosButton();
		btnOk.setText("OK");
		btnOk.registerKeyStroke(KeyEvent.VK_ENTER, KeyEvent.CTRL_DOWN_MASK);
		btnOk.setDefaultButton(true);
		btnOk.setPreferredSize(btnSize);
		btnOk.setButtonStyle(ButtonStyle.IMAGE);
		btnOk.setImage(imgOkButtonNormal, imgOkButtonTouch);
		btnOk.setOnClickListner(new IPosButtonListner() {

			@Override
			public void onClicked(PosButton button) {

				if(listener!=null )
					listener.onOkButtonClicked();
			}
		});
		butonPanel.add(btnOk);

		final PosButton btnCancel=new PosButton();
		btnCancel.setText("Cancel");
		btnCancel.setCancel(true);
		btnCancel.setPreferredSize(btnSize);
		btnCancel.setButtonStyle(ButtonStyle.IMAGE);
		btnCancel.setImage(imgCancelButtonNormal, imgCancelButtonTouch);
		btnCancel.setOnClickListner(new IPosButtonListner() {

			@Override
			public void onClicked(PosButton button) {

				if(listener!=null)
					listener.onCancelButtonClicked();

			}
		});
		butonPanel.add(btnCancel);

		final PosButton btnReset=new PosButton();
		btnReset.setText("Reset");
		btnReset.setPreferredSize(btnSize);
		btnReset.setButtonStyle(ButtonStyle.IMAGE);
		btnReset.setImage(imgResetButtonNormal, imgResetButtonTouch);
		btnReset.setOnClickListner(new IPosButtonListner() {

			@Override
			public void onClicked(PosButton button) {
				
				if(PosFormUtil.showQuestionMessageBox(mParent, MessageBoxButtonTypes.YesNo, "Do you really want to reset?", null)==MessageBoxResults.Yes){
				
					if(listener!=null)
						listener.onResetButtonClicked();
				}

			}
		});
		butonPanel.add(btnReset);

	}

	/**
	 * @param infoPanel 
	 * 
	 */
	private void createTableInfo(JPanel infoPanel) {

		JLabel lblHeading=PosFormUtil.setHeading("Table Information.", infoPanel.getWidth()-2, 30);
		infoPanel.add(lblHeading);

		final int TITLE_WIDTH=75;
		final int TITLE_HEIGHT=PosTouchableFieldBase.LAYOUT_DEF_HEIGHT;
		final int VALUE_FIELD_WIDTH=infoPanel.getWidth()-TITLE_WIDTH-4;
		final int VALUE_FIELD_HEIGHT=TITLE_HEIGHT;

		final Dimension titleSize=new Dimension(TITLE_WIDTH, TITLE_HEIGHT);
		final Dimension valueSize=new Dimension(VALUE_FIELD_WIDTH, VALUE_FIELD_HEIGHT);

		JLabel lblTitle=new JLabel(PosFormUtil.getMnemonicString("&nbsp;Loc :",'L'));
		lblTitle.setOpaque(true);
		lblTitle.setBackground(Color.LIGHT_GRAY);
		lblTitle.setPreferredSize(titleSize);
		setLabelFont(lblTitle);
		infoPanel.add(lblTitle);

		mTxtTableLocation = new PosItemBrowsableField(mParent, VALUE_FIELD_WIDTH);
		mTxtTableLocation.setMnemonic('L');
		mTxtTableLocation.setTitle("Select Location.");
		mTxtTableLocation.setHorizontalTextAlignment(JTextField.LEFT);
		mTxtTableLocation.setBrowseWindowSize(3, 3);
		mTxtTableLocation.hideResetButton(true);
		mTxtTableLocation.setListner(new PosTouchableFieldAdapter() {
			/*
			 * (non-Javadoc)
			 * 
			 * @see com.indocosmo.pos.components.textfields.listners.
			 * PosTouchableFieldAdapter#onReset()
			 */
			@Override
			public void onReset() {
				super.onReset();

			}

			/*
			 * (non-Javadoc)
			 * 
			 * @see com.indocosmo.pos.components.textfields.listners.
			 * PosTouchableFieldAdapter#onValueSelected(java.lang.Object)
			 */
			@Override
			public void onValueSelected(Object value) {

				super.onValueSelected(value);
				final BeanServingTableLocation location=(BeanServingTableLocation) value;
				
				if(listener!=null && mTxtTableLocation.getText()!=location.getName() ){
					resetSelectedTableInfo();
					listener.onTableLocationChanged(location);
				}
			}
		});
		infoPanel.add(mTxtTableLocation);


		/*
		 * Code
		 * 
		 */

		lblTitle=new JLabel(PosFormUtil.getMnemonicString("&nbspTable :",'T'));
		lblTitle.setOpaque(true);
		lblTitle.setBackground(Color.LIGHT_GRAY);
		lblTitle.setPreferredSize(titleSize);
		setLabelFont(lblTitle);
		infoPanel.add(lblTitle);

		mtxtTblCode=new PosItemSearchableField(mParent,(int)valueSize.getWidth());
		mtxtTblCode.setPreferredSize(valueSize);
		mtxtTblCode.hideResetButton(true);
		mtxtTblCode.setFont(infoFont);
		mtxtTblCode.setMnemonic('T');
		mtxtTblCode.setTitle("Select Table.");
		mtxtTblCode.setSorting(0, SortOrder.ASCENDING);
		mtxtTblCode.setListner(new IPosTouchableFieldListner() {
			
			@Override
			public void onValueSelected(Object value) {

				final BeanServingTable table=(BeanServingTable)value;
				
				if(listener!=null)
					listener.onTableChanged(table);
			}
			
			@Override
			public void onReset() {
				// TODO Auto-generated method stub
				
			}
		});
		
		infoPanel.add(mtxtTblCode);

		/*
		 * Name
		 * 
		 */

		lblTitle=new JLabel(" Name :");
		lblTitle.setOpaque(true);
		lblTitle.setBackground(Color.LIGHT_GRAY);
		lblTitle.setPreferredSize(titleSize);
		setLabelFont(lblTitle);
		infoPanel.add(lblTitle);

		mtxtTblName=new JTextField();
		mtxtTblName.setPreferredSize(valueSize);
		mtxtTblName.setEditable(false);
		mtxtTblName.setFont(infoFont);
		infoPanel.add(mtxtTblName);

		/*
		 * Seats
		 * 
		 */

		lblTitle=new JLabel(PosFormUtil.getMnemonicString("&nbsp;Seats :",'S'));
		lblTitle.setOpaque(true);
		lblTitle.setBackground(Color.LIGHT_GRAY);
		lblTitle.setPreferredSize(titleSize);
		setLabelFont(lblTitle);
		infoPanel.add(lblTitle);

		mtxtTblSeatsCount=new PosTouchableDigitalField(this.mParent,VALUE_FIELD_WIDTH);
		mtxtTblSeatsCount.setPreferredSize(valueSize);
		mtxtTblSeatsCount.setTitle("Number of seats?");
		mtxtTblSeatsCount.setMnemonic('S');
		mtxtTblSeatsCount.hideResetButton(true);
		mtxtTblSeatsCount.setTextReadOnly(true);
		mtxtTblSeatsCount.setHorizontalTextAlignment(JTextField.RIGHT);
		mtxtTblSeatsCount.setFont(PosFormUtil.getTextAreaFont());
		mtxtTblSeatsCount.setListner(new IPosTouchableFieldListner() {

			@Override
			public void onValueSelected(Object value) {

				selectedTable.setSeatCount(Integer.valueOf(value.toString()));

				if(listener!=null)
					listener.onTableSeatCountChanged(selectedTable);

			}

			@Override
			public void onReset() {

			}
		});
		infoPanel.add(mtxtTblSeatsCount);

		lblTitle=new JLabel(" Order :");
		lblTitle.setOpaque(true);
		lblTitle.setBackground(Color.LIGHT_GRAY);
		lblTitle.setPreferredSize(titleSize);
		setLabelFont(lblTitle);
		infoPanel.add(lblTitle);

		mtxtOders=new JTextField();
		mtxtOders.setPreferredSize(valueSize);
		mtxtOders.setEditable(false);
		mtxtOders.setHorizontalAlignment(JTextField.RIGHT);
		mtxtOders.setFont(infoFont);
		infoPanel.add(mtxtOders);


		final int BUTTON_WIDTH=(infoPanel.getWidth()/2)-2;
		final int BUTTON_HEIGHT=50;
		final Dimension bnSize =new Dimension( BUTTON_WIDTH, BUTTON_HEIGHT);

		final PosButton btnAddTable=new PosButton();
		btnAddTable.setText("Add");
		btnAddTable.setPreferredSize(bnSize);
		btnAddTable.setButtonStyle(ButtonStyle.IMAGE);
		btnAddTable.setImage(imgAddTableNormal, imgAddTableTouch);
		btnAddTable.setOnClickListner(new IPosButtonListner() {

			@Override
			public void onClicked(PosButton button) {

				if(selectedTable==null){

					PosFormUtil.showErrorMessageBox(mParent, "No table selected. Please select table.");
					return;
				}

				if(listener!=null)
					listener.onTableAdded(selectedTable);

			}
		});
		infoPanel.add(btnAddTable);


		final PosButton btnRemoveTable=new PosButton();
		btnRemoveTable.setText("Remove");
		btnRemoveTable.setMnemonic('m');
		btnRemoveTable.setPreferredSize(bnSize);
		btnRemoveTable.setButtonStyle(ButtonStyle.IMAGE);
		btnRemoveTable.setImage(imgDeleteTableNormal, imgDeleteTableTouch);
		btnRemoveTable.setOnClickListner(new IPosButtonListner() {

			@Override
			public void onClicked(PosButton button) {

				if(selectedTable==null){

					PosFormUtil.showErrorMessageBox(mParent, "No table selected. Please select table.");
					return;
				}

				if(listener!=null)
					listener.onTableRemoved(selectedTable);
			}
		});
		infoPanel.add(btnRemoveTable);


	}

	public BeanEmployees getSelectedWaiter() {
		return mSelectedWaiter;
	}

	public void selectWaiter() {
//		mTxtWaiterCard.showItemBrowseForm();
		mTxtWaiterCard.getBrowseButton().doClick();
	}

	/***
	 * creats the waiter selection panel
	 * @param oderInfoPanel 
	 */
	private void createOrderInfo(JPanel oderInfoPanel) {

		waiterProvider = new PosWaiterProvider();

		try {
			mWaiterList = waiterProvider.getWaiterList();
			 
		} catch (SQLException e) {
			PosFormUtil.showSystemError(mParent);
			e.printStackTrace();
		}
		JLabel lblHeading=PosFormUtil.setHeading("Order Info", oderInfoPanel.getWidth()-2, 30);
		oderInfoPanel.add(lblHeading);

		final int TITLE_WIDTH=80;
		final int TITLE_HEIGHT=PosTouchableFieldBase.LAYOUT_DEF_HEIGHT;
		final int VALUE_FIELD_WIDTH=oderInfoPanel.getWidth()-TITLE_WIDTH-4;
		final int VALUE_FIELD_HEIGHT=TITLE_HEIGHT;

		final Dimension titleSize=new Dimension(TITLE_WIDTH, TITLE_HEIGHT);
		final Dimension valueSize=new Dimension(VALUE_FIELD_WIDTH, VALUE_FIELD_HEIGHT);

		JLabel lblTitle=new JLabel(PosFormUtil.getMnemonicString("&nbsp;Waiter :",'W'));
		lblTitle.setOpaque(true);
		lblTitle.setBackground(Color.LIGHT_GRAY);
		lblTitle.setPreferredSize(titleSize);
		setLabelFont(lblTitle);
		oderInfoPanel.add(lblTitle);

		//		final int numberTextWidth = oderInfoPanel.getWidth()-PANEL_CONTENT_H_GAP*2;

		int columnCount=3;
		int rowCount=3;
		
		if (mWaiterList.size()>=20) {
			columnCount=5;
			rowCount=5;
		}else if (mWaiterList.size()>=16) {
			columnCount=5;
			rowCount=4;
		}else if (mWaiterList.size()>=12) {
			columnCount=4;
			rowCount=4;
		}else if (mWaiterList.size()>=9) {
			columnCount=4;
			rowCount=3;
		}
		
		mTxtWaiterCard = new PosItemBrowsableField(mParent, VALUE_FIELD_WIDTH);
		mTxtWaiterCard.setTitle("Waiter selection");
		mTxtWaiterCard.setMnemonic('W');
		mTxtWaiterCard.setHorizontalTextAlignment(JTextField.LEFT);
		mTxtWaiterCard.setBrowseWindowSize(columnCount, rowCount);
		mTxtWaiterCard.setPreferredSize(valueSize);
		mTxtWaiterCard.hideResetButton(true);
		mTxtWaiterCard.setListner(new PosTouchableFieldAdapter() {
			/*
			 * (non-Javadoc)
			 * 
			 * @see com.indocosmo.pos.components.textfields.listners.
			 * PosTouchableFieldAdapter#onReset()
			 */
			@Override
			public void onReset() {
				super.onReset();
				mSelectedWaiter = null;
			}

			/*
			 * (non-Javadoc)
			 * 
			 * @see com.indocosmo.pos.components.textfields.listners.
			 * PosTouchableFieldAdapter#onValueSelected(java.lang.Object)
			 */
			@Override
			public void onValueSelected(Object value) {
				super.onValueSelected(value);
				mSelectedWaiter = (BeanEmployees) value;
				if (mSelectedWaiter != null) {
				} else {
					mTxtWaiterCard.reset();
				}

			}
		});
		oderInfoPanel.add(mTxtWaiterCard);

		lblTitle=new JLabel(PosFormUtil.getMnemonicString("&nbsp;PAX :",'P'));
		lblTitle.setOpaque(true);
		lblTitle.setBackground(Color.LIGHT_GRAY);
		lblTitle.setPreferredSize(titleSize);
		setLabelFont(lblTitle);
		oderInfoPanel.add(lblTitle);

		mtxtPax=new PosTouchableDigitalField(this.mParent,VALUE_FIELD_WIDTH);
		mtxtPax.setPreferredSize(valueSize);
		mtxtPax.setTitle("PAX?");
		mtxtPax.setMnemonic('P');
		mtxtPax.hideResetButton(true);
		mtxtPax.setTextReadOnly(true);
		mtxtPax.setFont(infoFont);
		mtxtPax.setHorizontalTextAlignment(JTextField.RIGHT);
		mtxtPax.setListner(new IPosTouchableFieldListner() {

			@Override
			public void onValueSelected(Object value) {

				selectedPax=Integer.valueOf(value.toString());
			}

			@Override
			public void onReset() {
				// TODO Auto-generated method stub

			}
		});
		oderInfoPanel.add(mtxtPax);


	}

	/**
	 * 
	 */
	private void setWaiters(){

		 
		try {
		 
			mTxtWaiterCard.setBrowseItemList(mWaiterList);
			if(mWaiterList.size()==1){
				BeanEmployees waiter = mWaiterList.get(0);
				mTxtWaiterCard.setSelectedItem(waiter);
				mSelectedWaiter = (BeanEmployees) waiter;
				if (mSelectedWaiter != null) {
					mTxtWaiterCard.setText(mSelectedWaiter.getFirstName());
				} else {
					mTxtWaiterCard.reset();
				}
			}
		} catch (Exception e) {
			PosFormUtil.showSystemError(mParent);
			e.printStackTrace();
		}
	}

	
	
	/***
	 * create the Customer selection controls
	 * @param container - jPanel oderInfoPanel 
	 */
	private void createCustomerInfo(JPanel oderInfoPanel) {
		
		final Dimension btnSize=new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT);

		btnCustomer=new PosButton();
		btnCustomer.setText("Customer");
		btnCustomer.setPreferredSize(btnSize);
		btnCustomer.setButtonStyle(ButtonStyle.IMAGE);
		btnCustomer.setImage(imgOkButtonNormal , imgOkButtonTouch);
		btnCustomer.setOnClickListner(new IPosButtonListner() {

			@Override
			public void onClicked(PosButton button) {
				

			}
		});
		oderInfoPanel.add(btnCustomer);
	}
	/**
	 * 
	 */
	public void setLocationData(ArrayList<BeanServingTableLocation> locations){

		mTxtTableLocation.setBrowseItemList(locations);
		tableLocations=locations;

		if(locations!=null && locations.size()>0)
			mTxtTableLocation.setSelectedItem(locations.get(0));
	}
	
	/***
	 * sets the label font
	 * @param label
	 */
	private void setLabelFont(JLabel label) {

		Font newLabelFont = new Font(label.getFont().getName(), label.getFont()
				.getStyle(), 16);
		label.setFont(newLabelFont);
	}

	/**
	 * @param waiter
	 */
	public void setSelectedWaiter(BeanEmployees waiter) {

		mSelectedWaiter = waiter;
		if (mSelectedWaiter == null)
			return;
		mTxtWaiterCard.setText(waiter.getCardNumber());
	}

	/**
	 * @param cardNo
	 */
	public void setSelectedWaiter(String cardNo){

		for(BeanEmployees waiter:mWaiterList){
			if(waiter.getCardNumber().equals(cardNo)){
				mTxtWaiterCard.setSelectedItem(waiter);
				break;
			}
		}

	}

	/**
	 * @throws SQLException 
	 * 
	 */
	public void setDefaultWaiter() throws Exception {

		setSelectedWaiter(waiterProvider.getDefaultSystemWaiter());

	}

	/**
	 * @param table
	 */
	public void setSelectedTable(BeanServingTableExt table) {

		this.selectedTable=table;
		populateSelectedTableInfo();

	}

	/**
	 * @param id
	 */
	public void setSelectedTableLocation(int id) {

		if(tableLocations!=null && tableLocations.size()>0){
			for(BeanServingTableLocation loc:tableLocations){

				if(loc.getId()==id){
					mTxtTableLocation.setSelectedItem(loc);
				}
			}
		}
	}

	/**
	 * 
	 */
	private void populateSelectedTableInfo(){

		this.mtxtTblCode.setText(selectedTable.getCode());
		this.mtxtTblName.setText(selectedTable.getName());
		this.mtxtOders.setText(String.valueOf(selectedTable.getOrderCount()));
		this.mtxtTblSeatsCount.setText(String.valueOf(selectedTable.getSeatCount()));
	}

	
	/**
	 * 
	 */
	public void resetSelectedTableInfo(){

		selectedTable=null;
		this.mtxtTblCode.setText("");
		this.mtxtTblName.setText("");
		this.mtxtOders.setText("");
		this.mtxtTblSeatsCount.setText("");
	}
	/**
	 * @return
	 */
	public int getCovers() {

		return (int) PosNumberUtil.parseDoubleSafely(mtxtPax.getText());
	}

	/**
	 * @return the selectedPax
	 */
	public int getSelectedPax() {
		return selectedPax;
	}

	/**
	 * @param selectedPax the selectedPax to set
	 */
	public void setSelectedPax(int selectedPax) {
		this.selectedPax = selectedPax;
	}



	/**
	 * 
	 */
	private IPosTableLayoutRightPanelListener listener;

	/**
	 * @author jojesh
	 *
	 */
	public interface IPosTableLayoutRightPanelListener{

		/**
		 * @param location
		 */
		public void onTableLocationChanged(BeanServingTableLocation location);
		
		/**
		 * @param table
		 */
		public void onTableChanged(BeanServingTable table);

		/**
		 * 
		 */
		public void onOkButtonClicked();

		/**
		 * 
		 */
		public void onCancelButtonClicked();

		/**
		 * 
		 */
		public void onResetButtonClicked();

		/**
		 * @param selectedTable
		 */
		public void onTableAdded(BeanServingTableExt selectedTable);
		/**
		 * @param selectedTable
		 */
		public void onTableRemoved(BeanServingTableExt selectedTable);

		/**
		 * @param selectedTable
		 */
		public void onTableSeatCountChanged(BeanServingTableExt selectedTable);


	}

	/**
	 * @param listener the listener to set
	 */
	public void setListener(IPosTableLayoutRightPanelListener listener) {
		this.listener = listener;
	}

	/**
	 * @param availableTableList
	 */
	public void setTableList(Map<String, BeanServingTableExt> availableTableList) {

		mtxtTblCode.setSearchFieldList(new ArrayList<BeanServingTableExt>(availableTableList.values()));
		
	}

}
