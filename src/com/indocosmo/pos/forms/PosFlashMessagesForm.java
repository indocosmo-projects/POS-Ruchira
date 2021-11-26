/**
 * 
 */
package com.indocosmo.pos.forms;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;

import com.indocosmo.pos.common.enums.MessageDisplayStatus;
import com.indocosmo.pos.common.utilities.PosFormUtil;
import com.indocosmo.pos.common.utilities.PosResUtil;
import com.indocosmo.pos.data.beans.BeanFlashMessage;
import com.indocosmo.pos.data.providers.shopdb.PosFlashMessageProvider;
import com.indocosmo.pos.forms.components.buttons.PosButton;
import com.indocosmo.pos.forms.components.buttons.listners.IPosButtonListner;

/**
 * @author deepak
 * 
 */
@SuppressWarnings("serial")
public class PosFlashMessagesForm extends PosBaseForm {

	private static final int LAYOUT_WIDTH=600;
	
	private static final int PANEL_CONTENT_H_GAP =1;
	private static final int PANEL_CONTENT_V_GAP = 1;
	
	private static final int TITLE_LABEL_HEIGHT = 40;
	private static final int TITLE_LABEL_WIDTH = LAYOUT_WIDTH-PANEL_CONTENT_V_GAP*2;

	private static final int TXT_AREA_WIDTH = TITLE_LABEL_WIDTH;
	private static final int TXT_AREA_HEIGHT = 234;

	private static final int LAYOUT_HEIGHT=TITLE_LABEL_HEIGHT+TXT_AREA_HEIGHT+PANEL_CONTENT_V_GAP*4;
		
	private JPanel mContenPanel;
	private ArrayList<BeanFlashMessage> mMessageList;
	private PosFlashMessageProvider mPosFlashMessageProvider;
	private JTextField mLabelMessageTitle;
	private JTextArea mTxtMessageBody;
	private PosButton mButtonNext;
	private PosButton mButtonPrevious;
	private int totalMessages = 0;
	private int activeMessage = 0;
	private PosFlashMessagesForm mParent = this;

	/**
	 * @param title
	 * @param cPanelwidth
	 * @param cPanelHeight
	 */
//	public PosFlashMessagesForm(String title, int cPanelwidth, int cPanelHeight) {
//		super(title, cPanelwidth, cPanelHeight);
//		setOkButtonVisible(false);
//		setCancelButtonVisible(false);
//		initFlashMessagesControls();
//		setCancelButtonCaption("Close");
//	}

	/**
	 * 
	 */
	public PosFlashMessagesForm() {
		super("Message", LAYOUT_WIDTH, LAYOUT_HEIGHT);
		// setCancelButtonVisible(false);
		
		setCancelButtonCaption("Close");
		setOkButtonVisible(false);
		initFlashMessagesControls();
	 
	}

	/**
	 * 
	 */
	public PosFlashMessagesForm(int messageIndex){
		super("Messages", LAYOUT_WIDTH, LAYOUT_HEIGHT);
		// setCancelButtonVisible(false);
		
		setCancelButtonCaption("Close");
		setOkButtonVisible(false);
		activeMessage=messageIndex;
		initFlashMessagesControls();
	}
	/**
	 * 
	 */
	private void initFlashMessagesControls() {
		mPosFlashMessageProvider = new PosFlashMessageProvider();
		mMessageList = mPosFlashMessageProvider.getFlashMessageList();
		totalMessages = mMessageList.size();

		creatTitleLabel();
		createMessageBody();
		createPreviousButton();
		createNextButton();
		
		if(activeMessage>=0 && totalMessages>0)
			setMessage(activeMessage);
		
		
	}

	/**
	 * @param i
	 */
	private void setMessage(int i) {
		mLabelMessageTitle.setText(mMessageList.get(i).getTitle());
		mTxtMessageBody.setText(mMessageList.get(i).getContent());
		
		if (mMessageList.get(i).getDisplayStatus()!=MessageDisplayStatus.READ)
			mPosFlashMessageProvider.updateDisplayStatus(mMessageList.get(i).getId());
	}

	/**
	 * 
	 */
	private void createNextButton() {
		mButtonNext = new PosButton();
		mButtonNext.setText("Next");
		mButtonNext.setImage(PosResUtil
				.getImageIconFromResource(IMAGE_BUTTON_OK));
		mButtonNext.setTouchedImage(PosResUtil
				.getImageIconFromResource(IMAGE_BUTTON_OK_TOUCH));
		mButtonNext.setHorizontalAlignment(SwingConstants.CENTER);
		mButtonNext.setSize(IMAGE_BUTTON_WIDTH, IMAGE_BUTTON_HEIGHT);
		mButtonNext.setOnClickListner(nextClickListener);
		addButtonsToBottomPanel(mButtonNext, 1);

	}

	/**
	 * 
	 */
	private void createPreviousButton() {
		mButtonPrevious = new PosButton();
		mButtonPrevious.setText("Previous");
		mButtonPrevious.setImage(PosResUtil
				.getImageIconFromResource(IMAGE_BUTTON_OK));
		mButtonPrevious.setTouchedImage(PosResUtil
				.getImageIconFromResource(IMAGE_BUTTON_OK_TOUCH));
		mButtonPrevious.setHorizontalAlignment(SwingConstants.CENTER);
		mButtonPrevious.setSize(IMAGE_BUTTON_WIDTH, IMAGE_BUTTON_HEIGHT);
		mButtonPrevious.setOnClickListner(previousClickListener);
		addButtonsToBottomPanel(mButtonPrevious, 0);

	}

	/**
	 * 
	 */
	private void creatTitleLabel() {

		mLabelMessageTitle = new JTextField();
		mLabelMessageTitle.setFont(PosFormUtil.getButtonBoldFont());
		mLabelMessageTitle.setForeground(Color.BLACK);
		mLabelMessageTitle.setHorizontalAlignment(SwingConstants.LEFT);
		mLabelMessageTitle.setPreferredSize(new Dimension( TITLE_LABEL_WIDTH,
				TITLE_LABEL_HEIGHT));
//		mLabelMessageTitle.setBorder(BorderFactory
//				.createLineBorder(Color.black));
		mLabelMessageTitle.setBorder(new LineBorder(Color.LIGHT_GRAY));
//		mLabelMessageTitle.setBackground(Color.LIGHT_GRAY);
		mLabelMessageTitle.setEditable(false);
		mContenPanel.add(mLabelMessageTitle);
	}

	/**
	 * 
	 */
	private void createMessageBody() {
		mTxtMessageBody = new JTextArea(5,30);
		mTxtMessageBody.setLineWrap(true);
		mTxtMessageBody.setWrapStyleWord(false);
		mTxtMessageBody.setFont(PosFormUtil.getTextAreaFont());
//		mTxtMessageBody.setForeground(new Color(28,143,2));
//		mTxtMessageBody.setBackground(Color.BLACK);
		
		JScrollPane scrolPane=new JScrollPane(mTxtMessageBody);
		scrolPane.getVerticalScrollBar().setPreferredSize(new Dimension(20,0));
		scrolPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrolPane.setPreferredSize(new Dimension( TXT_AREA_WIDTH,
				TXT_AREA_HEIGHT));
		scrolPane.setBorder(new LineBorder(new Color(134, 184, 232)));
		mContenPanel.add(scrolPane);
	}

	/**
	 * @param posFlashMessageObject
	 */
//	private void createMessage(PosFlashMessageObject posFlashMessageObject) {
//
//		PosButton messageButton = new PosButton(
//				posFlashMessageObject.getTitle());
//		messageButton.setSize(350, 100);
//		mContenPanel.add(messageButton);
//	}

	private IPosButtonListner nextClickListener = new IPosButtonListner() {

		@Override
		public void onClicked(PosButton button) {

			activeMessage++;
			if (activeMessage < totalMessages) {
				setMessage(activeMessage);
			} else {
				activeMessage--;
				PosFormUtil.showErrorMessageBox(mParent, "No more mesages");
			}
		}
	};

	private IPosButtonListner previousClickListener = new IPosButtonListner() {

		@Override
		public void onClicked(PosButton button) {

			activeMessage--;
			if (activeMessage >=0) {
				setMessage(activeMessage);
			} else {
				activeMessage++;
				PosFormUtil.showErrorMessageBox(mParent, "No more mesages");
			}
		}
	};

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.indocosmo.pos.forms.PosBaseForm#setContentPanel(javax.swing.JPanel)
	 */
	@Override
	protected void setContentPanel(JPanel panel) {

		mContenPanel = panel;
		mContenPanel.setLayout(new FlowLayout(FlowLayout.CENTER,PANEL_CONTENT_H_GAP,0));
//		mContenPanel.setBackground(Color.WHITE);
		mContenPanel.setBorder(null);
	}

}
