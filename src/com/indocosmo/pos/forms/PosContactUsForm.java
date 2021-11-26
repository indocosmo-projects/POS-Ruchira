/**
 * 
 */
package com.indocosmo.pos.forms;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
import javax.swing.border.EtchedBorder;

import com.indocosmo.pos.common.PosEnvSettings;
import com.indocosmo.pos.common.PosLog;
import com.indocosmo.pos.common.utilities.PosFileUtils;
import com.indocosmo.pos.common.utilities.PosFormUtil;
import com.indocosmo.pos.common.utilities.PosResUtil;
import com.indocosmo.pos.common.utilities.PosZipUtil;
import com.indocosmo.pos.common.utilities.mail.PosSendMail;
import com.indocosmo.pos.common.utilities.mail.bean.PosMail;
import com.indocosmo.pos.forms.components.keypads.PosSoftKeyPad;
import com.indocosmo.pos.forms.messageboxes.PosMessageBoxForm.MessageBoxButtonTypes;
import com.indocosmo.pos.forms.messageboxes.listners.PosMessageBoxFormListnerAdapter;

/**
 * @author deepak
 * 
 */
@SuppressWarnings("serial")
public class PosContactUsForm extends PosBaseForm {

	private static final String CHK_BUTTON_NORMAL = "checkbox_normal.png";
	private static final String CHK_BUTTON_SELECTED = "checkbox_selected.png";

	private static final int PANEL_CONTENT_H_GAP = 2;
	private static final int PANEL_CONTENT_V_GAP = 5;

	private static final int LAYOUT_WIDTH = PosSoftKeyPad.LAYOUT_WIDTH;

	private static final int OPTION_LAYOUT_HEIGHT = 50;
	private static final int OPTION_LAYOUT_WIDTH = (LAYOUT_WIDTH - PANEL_CONTENT_V_GAP * 3) / 2;

	private static final int TITLE_LABEL_HEIGHT = 40;
	private static final int TITLE_LABEL_WIDTH = LAYOUT_WIDTH
			- PANEL_CONTENT_V_GAP * 2;

	private static final int TXT_AREA_WIDTH = TITLE_LABEL_WIDTH;
	private static final int TXT_AREA_HEIGHT = 150;

	private static final int LAYOUT_HEIGHT = TITLE_LABEL_HEIGHT
			+ TXT_AREA_HEIGHT + PosSoftKeyPad.LAYOUT_HEIGHT
			+ OPTION_LAYOUT_HEIGHT + PANEL_CONTENT_V_GAP * 5;

	private static final int TEXT_AREA_HEIGHT = 200;
	private static final int SCROLL_BAR_WIDTH = 20;

	private static final int TEXT_AREA_WIDTH = LAYOUT_WIDTH
			- PANEL_CONTENT_H_GAP;

	private static final String TMP_MAIL_FOLDER = "./tmp_mail_attachments";
	private static final String LOG_ZIP_FILE_NAME = "logs.piz";
	private static final String DB_ZIP_FILE_NAME = "dbs.piz";
	private static final String PROPERTIES_ZIP_FILE_NAME = "properties.piz";
	private static final String PRINT_PROPERTIES_ZIP_FILE_NAME = "print_properties.piz";
	private static final String LICENSE_ZIP_FILE_NAME = "license.piz";

	private JPanel mContenPanel;
	private JTextArea mLabelMessageTitle;
	private JTextArea mTxtMessageBody;
	private JTextArea mActiveField;
	private PosSoftKeyPad mPosSoftkeyPad;
	private JCheckBox mChkAttachLogs;
	private JCheckBox mChkAttachDBs;

	private ActionListener mchkActionListner;
	private FocusListener mOnFocusListener;
	protected boolean valid;

	/**
	 * @param title
	 * @param cPanelwidth
	 * @param cPanelHeight
	 * 
	 *            /**
	 * 
	 */
	public PosContactUsForm() {
		
		super("Support", LAYOUT_WIDTH, LAYOUT_HEIGHT);
		setOkButtonCaption("Send");
	}

	/**
	 * 
	 */
	private void initFlashMessagesControls() {

		creatTitleLabel();
		createMessageBody();
		createOptionLayout();
		mActiveField = mLabelMessageTitle;
		mActiveField.requestFocus();

	}

	/**
	 * 
	 */
	private void creatTitleLabel() {

		mLabelMessageTitle = new JTextArea();
		mLabelMessageTitle.setBorder(new EtchedBorder(1));
		mLabelMessageTitle.setFont(PosFormUtil.getHeadingFont());
		mLabelMessageTitle.setPreferredSize(new Dimension(TITLE_LABEL_WIDTH,
				TITLE_LABEL_HEIGHT));
		mLabelMessageTitle.addFocusListener(mOnFocusListener);
		mContenPanel.add(mLabelMessageTitle);
	}

	/**
	 * 
	 */
	private void createMessageBody() {

		mTxtMessageBody = new JTextArea(5, 30);
		mTxtMessageBody.setLineWrap(true);
		mTxtMessageBody.setFont(PosFormUtil.getTextFieldBoldFont());

		mTxtMessageBody.addFocusListener(mOnFocusListener);

		JScrollPane scrolPane = new JScrollPane(mTxtMessageBody);
		scrolPane.getVerticalScrollBar().setPreferredSize(
				new Dimension(SCROLL_BAR_WIDTH, 0));
		scrolPane
				.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrolPane.setBounds(PANEL_CONTENT_H_GAP, 2, TEXT_AREA_WIDTH,
				TEXT_AREA_HEIGHT);
		// scrolPane.setBorder(new LineBorder(new Color(134, 184, 232)));
		scrolPane.setPreferredSize(new Dimension(TXT_AREA_WIDTH,
				TXT_AREA_HEIGHT));
		mContenPanel.add(scrolPane);

		final int left = LAYOUT_WIDTH / 2 - PosSoftKeyPad.LAYOUT_WIDTH / 2;
		final int top = scrolPane.getY() + scrolPane.getHeight()
				+ PANEL_CONTENT_V_GAP;
		mPosSoftkeyPad = new PosSoftKeyPad(mTxtMessageBody, true);
		mPosSoftkeyPad.setLocation(left, top);
		mPosSoftkeyPad.setActionButtonsDisabled(true);
		mContenPanel.add(mPosSoftkeyPad);
	}

	private void createOptionLayout() {

		mChkAttachLogs = new JCheckBox();
		mChkAttachLogs.setHorizontalAlignment(JTextField.LEFT);
		mChkAttachLogs.setPreferredSize(new Dimension(OPTION_LAYOUT_WIDTH,
				OPTION_LAYOUT_HEIGHT));
		mChkAttachLogs.setFont(PosFormUtil.getTextFieldBoldFont());
		mChkAttachLogs.setText("Attach Log files.");
		mChkAttachLogs.setIcon(PosResUtil
				.getImageIconFromResource(CHK_BUTTON_NORMAL));
		mChkAttachLogs.setSelectedIcon(PosResUtil
				.getImageIconFromResource(CHK_BUTTON_SELECTED));
		mChkAttachLogs.setSelected(true);
		mChkAttachLogs.addActionListener(mchkActionListner);
		mContenPanel.add(mChkAttachLogs);

		mChkAttachDBs = new JCheckBox();
		mChkAttachDBs.setHorizontalAlignment(JTextField.LEFT);
		mChkAttachDBs.setPreferredSize(new Dimension(OPTION_LAYOUT_WIDTH,
				OPTION_LAYOUT_HEIGHT));
		mChkAttachDBs.setFont(PosFormUtil.getTextFieldBoldFont());
		mChkAttachDBs.setText("Attach Database files.");
		mChkAttachDBs.setIcon(PosResUtil
				.getImageIconFromResource(CHK_BUTTON_NORMAL));
		mChkAttachDBs.setSelectedIcon(PosResUtil
				.getImageIconFromResource(CHK_BUTTON_SELECTED));
		mChkAttachDBs.setSelected(false);
		mChkAttachDBs.addActionListener(mchkActionListner);
		
		mContenPanel.add(mChkAttachDBs);
		mChkAttachDBs.setSelected(true);
		
//		if (PosDBUtil.getInstance().getDatabaseType()
//				.equals(PosDbType.SQLITE)) {
//			
//			mChkAttachDBs.setSelected(true);
//		}
	}

	private void initListner() {

		mchkActionListner = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// setOkEnabled(mChkAttachLogs.isSelected() ||
				// mChkAttachDBs.isSelected());
			}
		};

		mOnFocusListener = new FocusListener() {

			@Override
			public void focusLost(FocusEvent e) {
				if (mActiveField != null)
					mActiveField.setBackground(Color.WHITE);
				mActiveField = null;
			}

			@Override
			public void focusGained(FocusEvent e) {
				mActiveField = (JTextArea) e.getSource();
				if (mActiveField != null) {
					mPosSoftkeyPad.setTextArea(mActiveField);
				}

			}
		};
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.indocosmo.pos.forms.PosBaseForm#setContentPanel(javax.swing.JPanel)
	 */
	@Override
	protected void setContentPanel(JPanel panel) {
		initListner();
		mContenPanel = panel;
		mContenPanel.setLayout(new FlowLayout(FlowLayout.CENTER,
				PANEL_CONTENT_H_GAP, PANEL_CONTENT_V_GAP));
		initFlashMessagesControls();
		
//		if(PosEnvSettings.getInstance().getShopDBType().equals(PosDbType.MYSQL)){
//			
//			mChkAttachDBs.setSelected(false);
//			mChkAttachDBs.setEnabled(false);
//		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.indocosmo.pos.forms.PosBaseForm#onOkButtonClicked()
	 */
	@Override
	public boolean onOkButtonClicked() {

		final PosContactUsForm me = this;

		if (!validateContents())
			return false;

		SwingWorker<Boolean, String> swt = new SwingWorker<Boolean, String>() {
			boolean result = false;

			@Override
			protected Boolean doInBackground() throws Exception {

				try {

					File tmpFolder = new File(TMP_MAIL_FOLDER);

					PosMail mail = new PosMail();
					mail.setSubject(mLabelMessageTitle.getText());
					mail.setBody("Shop :"
							+ PosEnvSettings.getInstance().getShop().getName()
							+ "\nTerminal :"
							+ PosEnvSettings.getInstance().getStation()
									.getName() + "\n\n"
							+ mTxtMessageBody.getText());

					PosFileUtils.getInstance().createFolder(tmpFolder);
					String pathToZipFolder = prepareZipFiles(TMP_MAIL_FOLDER);

					if (mChkAttachLogs.isSelected())
						mail.addAttachement(pathToZipFolder + "/"
								+ LOG_ZIP_FILE_NAME);

					if (mChkAttachDBs.isSelected())
						mail.addAttachement(pathToZipFolder + "/"
								+ DB_ZIP_FILE_NAME);
					
					mail.addAttachement(pathToZipFolder + "/"
								+ PROPERTIES_ZIP_FILE_NAME);
			
					mail.addAttachement(pathToZipFolder + "/"
							+ LICENSE_ZIP_FILE_NAME );
					
					PosSendMail.send(mail);
					
					
					// PosFormUtil.closeBusyWindow();
					// PosFormUtil.showInformationMessageBox(me,
					// "Mail has been sent successfully.");
					tmpFolder.delete();
					result = true;
				} catch (Exception e) {
					PosLog.write(this, "onOkButtonClicked", e);
					result = false;
				}
				return result;
			}

			@Override
			protected void done() {
				PosFormUtil.closeBusyWindow();
				if (result) {
					PosFormUtil.showInformationMessageBox(me,
							"Mail has been sent successfully.");
					me.closeWindow();
				} else
					PosFormUtil
							.showErrorMessageBox(me,
									"Failed to the send mail. Please try later or contact the administrator.");
			}
		};
		swt.execute();
		PosFormUtil.showBusyWindow(this, "Please wait while sending mail...");
		return false;
	}

	private boolean validateContents() {

		valid = true;

		if (mLabelMessageTitle.getText().trim().length() <= 0) {
			PosFormUtil.showErrorMessageBox(this, "Please fill subject line.");
			mLabelMessageTitle.requestFocus();
			return false;
		}

		if (mLabelMessageTitle.getText().trim().length() <= 0
				|| mTxtMessageBody.getText().trim().length() <= 0) {
			PosFormUtil.showErrorMessageBox(this, "Please fill your feedback.");
			return false;
		}

		if (!mChkAttachLogs.isSelected() && !mChkAttachDBs.isSelected()) {
			PosFormUtil.showQuestionMessageBox(this, MessageBoxButtonTypes.YesNo,
					"Send this message without any attachments?",
					new PosMessageBoxFormListnerAdapter() {
						/*
						 * (non-Javadoc)
						 * 
						 * @see com.indocosmo.pos.forms.messageboxes.listners.
						 * PosMessageBoxFormListnerAdapter#onYesButtonPressed()
						 */
						@Override
						public void onYesButtonPressed() {
							valid = true;
						}

						/*
						 * (non-Javadoc)
						 * 
						 * @see com.indocosmo.pos.forms.messageboxes.listners.
						 * PosMessageBoxFormListnerAdapter#onCancelClicked()
						 */
						@Override
						public void onNoButtonPressed() {
							valid = false;
							super.onNoButtonPressed();
						}
					});
		}

		return valid;

	}

	private String prepareZipFiles(String toFile) throws IOException {

		if (mChkAttachLogs.isSelected()) {
			prepareLogsToMail(toFile);
		}

		if (mChkAttachDBs.isSelected()) {
			prepareDbsToMail(toFile);
		}

		preparePropertiesFileToMail(toFile);
		prepareLicenseFileToMail(toFile);
		
//		if (mChkAttachLogs.isSelected() || mChkAttachDBs.isSelected()) {
			
//		}
		return toFile;

	}

	private void prepareLogsToMail(String toFolder) throws IOException {

		String logFolder = PosEnvSettings.getInstance().getLogPath();

		PosZipUtil.zip(logFolder, toFolder + "/" + LOG_ZIP_FILE_NAME);

	}

	private static void prepareDbsToMail(String toFolder) throws IOException {

		String sqliteShopDbFolder = PosEnvSettings.getInstance().getDbFolder();
		String[] srcFiles = {
				sqliteShopDbFolder + "/"
						+ PosEnvSettings.getInstance().getShopDBFile(),
				sqliteShopDbFolder + "/"
						+ PosEnvSettings.getInstance().getTerminalDBFile() };
		PosZipUtil.zip(srcFiles, toFolder + "/" + DB_ZIP_FILE_NAME);
	}

	private void preparePropertiesFileToMail(String toFolder)
			throws IOException {

		String propertiesFile[] ={ "./" + PosEnvSettings.PROPERTY_FILE,"./" + PosEnvSettings.UI_PROPERTY_FILE,"./" + PosEnvSettings.PRINT_PROPERTY_FILE};
		PosZipUtil.zip(propertiesFile, toFolder + "/"
				+ PROPERTIES_ZIP_FILE_NAME);
		
		
	}
	private void prepareLicenseFileToMail(String toFolder)
			throws IOException {

		String licenseFile[] ={ "./" + PosEnvSettings.LICENCE_FILE};
		PosZipUtil.zip(licenseFile, toFolder + "/"
				+ LICENSE_ZIP_FILE_NAME);
		
		
	}
}
