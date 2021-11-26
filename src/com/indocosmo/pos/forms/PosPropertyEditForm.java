/**
 * 
 */
package com.indocosmo.pos.forms;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.Document;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;

import com.indocosmo.pos.common.PosConstants;
import com.indocosmo.pos.common.PosEnvSettings;
import com.indocosmo.pos.common.PosLog;
import com.indocosmo.pos.common.enums.PosPropertyFileType;
import com.indocosmo.pos.common.utilities.PosFormUtil;
import com.indocosmo.pos.common.utilities.PosPropertyFileUtil;
import com.indocosmo.pos.forms.messageboxes.PosMessageBoxForm.MessageBoxButtonTypes;
import com.indocosmo.pos.forms.messageboxes.PosMessageBoxForm.MessageBoxResults;

/**
 * 
 * @author anand
 *
 */
@SuppressWarnings("serial")
public class PosPropertyEditForm extends PosBaseForm {

	private static String PROPERTY_FILE;
	private static final int CONTENT_PANEL_HEIGHT =  550;
	private static final int CONTENT_PANEL_WIDTH = 850;
	private static final int FORM_WIDTH=CONTENT_PANEL_WIDTH+PANEL_CONTENT_H_GAP*2;
	private static final int FORM_HEIGHT=CONTENT_PANEL_HEIGHT+PANEL_CONTENT_V_GAP*2;
	private static final Dimension V_SCROLL_BAR_DIMNSN = new Dimension(17,0);
	private static final Dimension H_SCROLL_BAR_DIMNSN = new Dimension(0,17);
	private UndoManager undoOrRedo;
	private String propertyFileContents;
	private JTextArea propertyFileTxt;
	private JScrollPane propertyFileScroll;
	private boolean isContentAvailable=false;
	private boolean isBottomPanelSet=false;
	
	
//	private PosSoftKeyPad mSoftKeypad;
	private static final boolean isEncrypted = PosEnvSettings.getInstance().isUIPropertyFileEncrypted();

	/**
	 * @param title
	 * @param cPanelwidth
	 * @param cPanelHeight
	 */
	public PosPropertyEditForm(PosPropertyFileType property) {
		
		super(property.getTitle(),FORM_WIDTH,FORM_HEIGHT);
		isBottomPanelSet=true;
		PROPERTY_FILE=property.getFileName();
		isContentAvailable=setPropertyFileTxt();
		initControl();

	}
	/**
	 * Sets the form Buttons and flags.
	 */
	private void initControl(){

		setOkButtonCaption("Save");
		setResetButtonVisible(true);
		setResetEnable(false);
		setResetlButtonCaption("Revert");
		setCancelButtonCaption("Close");
	}
	/* (non-Javadoc)
	 * @see com.indocosmo.pos.forms.PosBaseForm#setContentPanel(javax.swing.JPanel)
	 */
	@Override
	protected void setContentPanel(JPanel contentPanel) {

		propertyFileTxt = new JTextArea();
		propertyFileTxt.setBorder(new EmptyBorder(2, 2, 2, 2));
		/**
		 * setting undo and redo functionality in the JTextArea propertyFileTxt.
		 */
		undoOrRedo=new UndoManager();
		InputMap propertyFileTxtIM = propertyFileTxt.getInputMap(JComponent.WHEN_FOCUSED);
		ActionMap propertyFileTxtAM = propertyFileTxt.getActionMap();
		final Document propertyFileDocument=propertyFileTxt.getDocument();
		propertyFileDocument.addDocumentListener(new DocumentListener() {

			@Override
			public void removeUpdate(DocumentEvent e) {
				if(isBottomPanelSet)
					setResetEnable(isContentAvailable);
			}
			@Override
			public void insertUpdate(DocumentEvent e) {
				if(isBottomPanelSet)
					setResetEnable(isContentAvailable);
			}
			@Override
			public void changedUpdate(DocumentEvent e) {
				if(isBottomPanelSet)
					setResetEnable(isContentAvailable);
			}
		});
		propertyFileDocument.addUndoableEditListener(new UndoableEditListener() {

			@Override
			public void undoableEditHappened(UndoableEditEvent e) {
				try{
					if(isContentAvailable)
						undoOrRedo.addEdit(e.getEdit());
				}
				catch(Exception f){
					PosLog.write(this, "undOrRedo.addEdit(", f);
				}
			}
		});
		propertyFileTxtIM.put(KeyStroke.getKeyStroke(KeyEvent.VK_Z,Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()), "undo");
		propertyFileTxtIM.put(KeyStroke.getKeyStroke(KeyEvent.VK_Y,Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()), "redo");

		propertyFileTxtAM.put("undo",new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try{ 
					if(undoOrRedo.canUndo())
						undoOrRedo.undo();
				}catch(CannotUndoException u){
					PosLog.write(this, "undOrRedo.undo(", u);
				}

			}
		});

		propertyFileTxtAM.put("redo", new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try{
					if(undoOrRedo.canRedo())
						undoOrRedo.redo();
				}catch(CannotRedoException r){
					PosLog.write(this, "undOrRedo.redo()",r);
				}

			}
		});

		contentPanel.setBackground(new Color(6, 38, 76));
		propertyFileScroll = new JScrollPane(propertyFileTxt);
		propertyFileScroll.setBounds(0,0, contentPanel.getWidth(), contentPanel.getHeight());
		propertyFileScroll.getVerticalScrollBar().setPreferredSize(V_SCROLL_BAR_DIMNSN);
		propertyFileScroll.getHorizontalScrollBar().setPreferredSize(H_SCROLL_BAR_DIMNSN);
		contentPanel.add(propertyFileScroll);

//		final int top = propertyFileScroll.getY()+propertyFileScroll.getHeight();
//		final int left = contentPanel.getWidth()/2-PosSoftKeyPad.LAYOUT_WIDTH/2-1;
//		mSoftKeypad = new PosSoftKeyPad(propertyFileTxt,false);
//		mSoftKeypad.setLocation(left, top);
//		mSoftKeypad.setActionButtonsDisabled(true);
//		contentPanel.add(mSoftKeypad);
	}

	/**
	 * @throws IOException 
	 * 
	 */
	private boolean setPropertyFileTxt(){

		try{

			StringBuilder sb = new StringBuilder();
			BufferedReader br;
			if(isEncrypted){
				final InputStream propertyFileStream = PosPropertyFileUtil.decryptPropertyFile(PROPERTY_FILE);
				br = new BufferedReader(new InputStreamReader(propertyFileStream));
				propertyFileStream.close();
			}
			else{
				final File propertyFile = new File(PROPERTY_FILE);
				br = new BufferedReader(new FileReader(propertyFile));
			}
			String line = br.readLine();
			while(line!=null){

				sb.append(line);
				sb.append("\n");
				line = br.readLine();
			}
			br.close();
			propertyFileContents=sb.toString();
			propertyFileTxt.setText(propertyFileContents);

			/**
			 * 
			 */
			//setting the scroll bar initially to the top position.
			javax.swing.SwingUtilities.invokeLater(new Runnable() {
				public void run() { 
					propertyFileScroll.getVerticalScrollBar().setValue(0);
					propertyFileScroll.getHorizontalScrollBar().setValue(0);
					propertyFileTxt.setCaretPosition(0);
				}
			});

			return true;

		}catch(Exception e){

			PosLog.write(this, "setPrprtyFileTxt", e);
			PosFormUtil.showErrorMessageBox(this,"Failed To Fetch Property File");
			return false;
		}

	}

	private boolean doPropertySave(){

		try{
			if(isEncrypted)
				PosPropertyFileUtil.encryptPropertyFile(PROPERTY_FILE,propertyFileTxt.getText());
			else{
				
				final byte[] propertyFileBytes = propertyFileTxt.getText().getBytes();
				final FileOutputStream fout = new FileOutputStream(new File(PROPERTY_FILE));
				fout.write(propertyFileBytes);
				fout.close();
			}
			return true;
		}catch(Exception e){
			PosLog.write(this, "doPropertySave", e);
			return false;
		}

	}

	/* (non-Javadoc)
	 * @see com.indocosmo.pos.forms.PosBaseForm#onOkButtonClicked()
	 */
	@Override
	public boolean onOkButtonClicked() {

		if(doPropertySave()){
			
			if(PosFormUtil.showQuestionMessageBox(this, MessageBoxButtonTypes.YesNo, "System's settings has been updated. Do you want to restart?",null)==MessageBoxResults.Yes)
				System.exit(PosConstants.RESTART_EXIT_CODE);
		}
		else
			PosFormUtil.showInformationMessageBox(this, "Error saving system's confiurations. Please contact the administrator.");
		
		closeWindow();
		return super.onOkButtonClicked();
	}
	/* (non-Javadoc)
	 * @see com.indocosmo.pos.forms.PosBaseForm#onResetButtonClicked()
	 */
	@Override
	public void onResetButtonClicked() {

		final int scroll_H_Value = propertyFileScroll.getHorizontalScrollBar().getValue();
		final int scroll_V_Value = propertyFileScroll.getVerticalScrollBar().getValue();
		final int curCaretPos=propertyFileTxt.getCaretPosition();
		propertyFileTxt.setText(propertyFileContents);
		//Setting the scroll bar to the previous position after reset.
		javax.swing.SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {

				propertyFileScroll.getVerticalScrollBar().setValue(scroll_V_Value);
				propertyFileScroll.getHorizontalScrollBar().setValue(scroll_H_Value);
				propertyFileTxt.setCaretPosition(curCaretPos);
			}
		});
		setResetEnable(false);
	}

	/* (non-Javadoc)
	 * @see java.awt.Dialog#setVisible(boolean)
	 */
	@Override
	public void setVisible(boolean b) {

		if(isContentAvailable)
			super.setVisible(b);
		else
			super.setVisible(false);
	}

}