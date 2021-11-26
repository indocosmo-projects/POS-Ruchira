package com.indocosmo.pos.forms.messageboxes;

import java.awt.FlowLayout;
import java.awt.event.KeyEvent;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;

import com.indocosmo.pos.common.utilities.PosFormUtil;
import com.indocosmo.pos.common.utilities.PosResUtil;
import com.indocosmo.pos.forms.components.buttons.PosButton;
import com.indocosmo.pos.forms.components.buttons.listners.IPosButtonListner;
import com.indocosmo.pos.forms.components.buttons.listners.PosButtonListnerAdapter;
import com.indocosmo.pos.forms.listners.IPosMessageBoxFormCancelListener;
import com.indocosmo.pos.forms.listners.IPosMessageBoxFormListner;

@SuppressWarnings("serial")
public class PosMessageBoxForm extends JDialog {

	private static final String POSITIVE_BUTTON_IMAGE = "msg_positive.png";
	private static final String POSITIVE_BUTTON_IMAGE_TOUCH = "msg_positive_touch.png";
	private static final String NEGATIVE_BUTTON_IMAGE = "msg_nagative.png";
	private static final String NEGATIVE_BUTTON_IMAGE_TOUCH = "msg_nagative_touch.png";

	private static final int ICON_HEIGHT = 60;
	private static final int ICON_WIDTH = 60;
	private static final String ICON_QUESTION_SRC = "msg_question.png";
	private static final String ICON_INFORMATION_SRC = "msg_information.png";
	private static final String ICON_ERROR_SRC = "msg_error.png";

	private static int PANEL_CONTENT_H_GAP = 8;
	private static int PANEL_CONTENT_V_GAP = 8;

	private static int MESSAGE_PANEL_HEIGHT = 100;

	private static int BUTTON_HEIGHT = 60;
	private static int BUTTON_WIDTH = 150;

	private static int BUTTON_PANEL_HEIGHT = BUTTON_HEIGHT
			+ PANEL_CONTENT_V_GAP * 2;

	private static final int FORM_WIDTH = 550;
	private static int FORM_HEIGHT = PANEL_CONTENT_V_GAP * 3
			+ MESSAGE_PANEL_HEIGHT + BUTTON_PANEL_HEIGHT;

	private static final int BUTTON_PANEL_WIDTH = FORM_WIDTH
			- PANEL_CONTENT_H_GAP * 2;
	private static final int MESSAGE_PANEL_WIDTH = FORM_WIDTH
			- PANEL_CONTENT_H_GAP * 2;

	public enum MessageBoxButtonTypes {
		YesNo, YesNoCancel, OkCancel, OkOnly,CancelOnly;
	}
	
	public enum MessageBoxResults{
		Yes,
		No,
		Ok,
		Cancel,
	}

	public enum MessageBoxTypes {
		Information, Question, Error;
	}
	private JPanel mContentPane;
	private JPanel mMessagePanel;
	private JPanel mButtonPanel;

	private JLabel mMessageLabel;
	private JLabel mMessageIconLabel;

	private PosButton mButtonPositiveAction;
	private PosButton mButtonNegatieveAction;
	private PosButton mButtonNoAction;

	private IPosMessageBoxFormListner mMessageBoxListner;
	private IPosMessageBoxFormCancelListener mMessageBoxCancelListner;
	
	private MessageBoxButtonTypes mMessageButtonType;
	private MessageBoxTypes mMessageBoxType;
	private MessageBoxResults mMessageBoxResult;

	// private MessageBoxTypes mMessageBoxType;

	public PosMessageBoxForm() {
		mMessageButtonType = mMessageButtonType.YesNo;
		// mMessageBoxType=MessageBoxTypes.YesNo;
		initControls();
	}

	public PosMessageBoxForm(MessageBoxButtonTypes buttonType) {
		mMessageButtonType = buttonType;
		initControls();
	}

	private void initControls() {
		setSize(FORM_WIDTH, FORM_HEIGHT);
		mContentPane = new JPanel();
		mContentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		mContentPane.setLayout(null);
		setContentPane(mContentPane);
		createPanels();
		setButtonTypes();
	}

	

	private void createPanels() {
		mMessagePanel = new JPanel();
		mMessagePanel.setBounds(PANEL_CONTENT_H_GAP, PANEL_CONTENT_V_GAP,
				MESSAGE_PANEL_WIDTH, MESSAGE_PANEL_HEIGHT);
		mMessagePanel.setLayout(null);
		mMessagePanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
		add(mMessagePanel);

		int top = (mMessagePanel.getHeight() / 2) - (ICON_HEIGHT / 2);
		mMessageIconLabel = new JLabel();
		mMessageIconLabel.setBounds(PANEL_CONTENT_H_GAP, top, ICON_WIDTH,
				ICON_HEIGHT);
//		mMessageIconLabel
//				.setIcon(PosResUtil
//						.getImageIconFromResource((mMessageBoxType == MessageBoxTypes.Information) ? ICON_INFORMATION_SRC
//								: (mMessageBoxType == MessageBoxTypes.Error) ? ICON_ERROR_SRC : ICON_QUESTION_SRC));
		mMessagePanel.add(mMessageIconLabel);

		int left = mMessageIconLabel.getX() + mMessageIconLabel.getWidth()
				+ PANEL_CONTENT_H_GAP;
		int width = MESSAGE_PANEL_WIDTH - ICON_WIDTH - PANEL_CONTENT_H_GAP * 3;
		mMessageLabel = new JLabel();
		mMessageLabel.setBounds(left, PANEL_CONTENT_V_GAP, width,
				MESSAGE_PANEL_HEIGHT - PANEL_CONTENT_V_GAP * 2);
		mMessageLabel.setFont(PosFormUtil.getMessageBoxTextFont());
		mMessagePanel.add(mMessageLabel);

		mButtonPanel = new JPanel();
		mButtonPanel.setBounds(PANEL_CONTENT_H_GAP, mMessagePanel.getY()
				+ mMessagePanel.getHeight() + PANEL_CONTENT_V_GAP,
				BUTTON_PANEL_WIDTH, BUTTON_PANEL_HEIGHT);
		mButtonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		mButtonPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
		add(mButtonPanel);

		mButtonPositiveAction = new PosButton();
		mButtonPositiveAction.setBounds(0, 0, BUTTON_WIDTH, BUTTON_HEIGHT);
		mButtonPositiveAction.setOnClickListner(onButtonPositiveClick);
		mButtonPositiveAction.setImage(PosResUtil
				.getImageIconFromResource(POSITIVE_BUTTON_IMAGE));
		mButtonPositiveAction.setTouchedImage(PosResUtil
				.getImageIconFromResource(POSITIVE_BUTTON_IMAGE_TOUCH));
		mButtonPanel.add(mButtonPositiveAction);

		mButtonNegatieveAction = new PosButton();
		mButtonNegatieveAction.setBounds(0, 0, BUTTON_WIDTH, BUTTON_HEIGHT);
		mButtonNegatieveAction.setImage(PosResUtil
				.getImageIconFromResource(NEGATIVE_BUTTON_IMAGE));
		mButtonNegatieveAction.setTouchedImage(PosResUtil
				.getImageIconFromResource(NEGATIVE_BUTTON_IMAGE_TOUCH));
		mButtonNegatieveAction.setOnClickListner(onButtonNegativeClick);
		mButtonPanel.add(mButtonNegatieveAction);

		mButtonNoAction = new PosButton();

		mButtonNoAction.setBounds(0, 0, BUTTON_WIDTH, BUTTON_HEIGHT);
		mButtonNoAction.setImage(PosResUtil
				.getImageIconFromResource(NEGATIVE_BUTTON_IMAGE));
		mButtonNoAction.setTouchedImage(PosResUtil
				.getImageIconFromResource(NEGATIVE_BUTTON_IMAGE_TOUCH));
		mButtonNoAction.setOnClickListner(onButtonNoActionClick);
		mButtonNoAction.setVisible(false);
		mButtonPanel.add(mButtonNoAction);

	}

	
	private IPosButtonListner onButtonPositiveClick = new PosButtonListnerAdapter() {
		@Override
		public void onClicked(PosButton button) {
			mMessageBoxResult=(MessageBoxResults)button.getTag();
			if(mMessageBoxCancelListner!= null){
				switch (mMessageButtonType) {
				case CancelOnly:
					mMessageBoxCancelListner.onCancelButtonPressed();	
					break;
				default:
					break;
				}
			}else{
				setVisible(false);
				if (mMessageBoxListner != null||mMessageBoxCancelListner!= null) {
					;
					switch (mMessageButtonType) {
					case CancelOnly:
						mMessageBoxCancelListner.onCancelButtonPressed();	
						break;
					case OkOnly:
					case OkCancel:
						mMessageBoxListner.onOkButtonPressed();
						break;
					case YesNo:
					case YesNoCancel:
						mMessageBoxListner.onYesButtonPressed();
						break;
					default:
						break;
					}
				}
				dispose();
			}
			
			
		}
	};

	private IPosButtonListner onButtonNoActionClick = new PosButtonListnerAdapter() {
		@Override
		public void onClicked(PosButton button) {
			mMessageBoxResult=(MessageBoxResults)button.getTag();
			setVisible(false);
			if (mMessageBoxListner != null) {
				switch (mMessageButtonType) {
				case YesNoCancel:
					mMessageBoxListner.onCancelButtonPressed();
					break;
				default:
					break;
				}
			}
			dispose();
		}
	};

	private IPosButtonListner onButtonNegativeClick = new PosButtonListnerAdapter() {
		@Override
		public void onClicked(PosButton button) {
			mMessageBoxResult=(MessageBoxResults)button.getTag();
			setVisible(false);
			if (mMessageBoxListner != null){
				switch (mMessageButtonType) {
				case OkCancel:
					mMessageBoxListner.onCancelButtonPressed();
					break;
				case YesNo:
				case YesNoCancel:
					mMessageBoxListner.onNoButtonPressed();
					break;
				default:
					break;
				}
			}
			dispose();
		}
	};

	private void setButtonTypes() {
		switch (mMessageButtonType) {
		
		case CancelOnly:
			mButtonPositiveAction.setVisible(false);
			
			mButtonNegatieveAction.setText("Cancel");
			mButtonNegatieveAction.registerKeyStroke(KeyEvent.VK_C);
			mButtonNegatieveAction.setTag(MessageBoxResults.Cancel);
			mButtonNegatieveAction.setDefaultButton(true);
			mButtonNegatieveAction.setCancel(true);
			break;
		case OkOnly:
			mButtonPositiveAction.setText("OK");
			mButtonPositiveAction.registerKeyStroke(KeyEvent.VK_O);
			mButtonPositiveAction.setDefaultButton(true);
			mButtonPositiveAction.setCancel(true);
			
			mButtonNegatieveAction.setVisible(false);
			
//			mButtonNegatieveAction.setDefaultButton(true);
		case OkCancel:
			mButtonNegatieveAction.setText("Cancel");
			mButtonNegatieveAction.registerKeyStroke(KeyEvent.VK_C);
			mButtonNegatieveAction.setTag(MessageBoxResults.Cancel);
			mButtonNegatieveAction.setCancel(true);
			
			mButtonPositiveAction.setText("OK");
			mButtonPositiveAction.registerKeyStroke(KeyEvent.VK_O);
			mButtonPositiveAction.setDefaultButton(true);
			mButtonPositiveAction.setTag(MessageBoxResults.Ok);
			break;
		case YesNo:
			mButtonNegatieveAction.setText("No");
			mButtonNegatieveAction.registerKeyStroke(KeyEvent.VK_N);
			mButtonNegatieveAction.setTag(MessageBoxResults.No);
			mButtonNegatieveAction.setCancel(true);
			
			mButtonPositiveAction.setText("Yes");
			mButtonPositiveAction.registerKeyStroke(KeyEvent.VK_Y);
			mButtonPositiveAction.setDefaultButton(true);
			mButtonPositiveAction.setTag(MessageBoxResults.Yes);
			break;
		case YesNoCancel:
			mButtonNegatieveAction.setText("No");
			mButtonNegatieveAction.registerKeyStroke(KeyEvent.VK_N);
			mButtonNegatieveAction.setTag(MessageBoxResults.No);
			
			mButtonPositiveAction.setText("Yes");
			mButtonPositiveAction.registerKeyStroke(KeyEvent.VK_Y);
			mButtonPositiveAction.setDefaultButton(true);
			mButtonPositiveAction.setTag(MessageBoxResults.Yes);
			
			mButtonNoAction.setText("Cancel");
			mButtonNoAction.registerKeyStroke(KeyEvent.VK_C);
			mButtonNoAction.setCancel(true);
			mButtonNoAction.setTag(MessageBoxResults.Cancel);
			mButtonNoAction.setVisible(true);
			break;
		default:
			break;
		}
		mButtonPositiveAction.setDefaultButton(true);
	}

	public void setListner(IPosMessageBoxFormListner listner) {
		this.mMessageBoxListner = listner;
	}
	
	public void setCancelOnlyListner(IPosMessageBoxFormCancelListener listner) {
		this.mMessageBoxCancelListner = listner;
	}
	
	public void setButtonNoActionName(String name){
		mButtonNoAction.setText(name);
	}

	public void setMessage(String message) {
		mMessageLabel.setText("<html>" + message + "</html>");
	}
	public void setMessageBoxIcon(MessageBoxTypes type){
		mMessageBoxType =type;
		mMessageIconLabel
		.setIcon(PosResUtil
				.getImageIconFromResource((mMessageBoxType == MessageBoxTypes.Information) ? ICON_INFORMATION_SRC
						: (mMessageBoxType == MessageBoxTypes.Error) ? ICON_ERROR_SRC : ICON_QUESTION_SRC));
		
	}
	
	public MessageBoxResults getResult(){
		return mMessageBoxResult;
	}
	
}
