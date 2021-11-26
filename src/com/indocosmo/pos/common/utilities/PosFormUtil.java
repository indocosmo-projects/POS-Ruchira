/**
 * 
 */
package com.indocosmo.pos.common.utilities;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.Robot;
import java.awt.Window;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.font.LineMetrics;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.RootPaneContainer;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.JTextComponent;

import com.indocosmo.pos.common.PosEnvSettings;
import com.indocosmo.pos.common.PosLog;
import com.indocosmo.pos.common.beans.BeanBillPaymentSummaryInfo;
import com.indocosmo.pos.common.beans.settings.ui.orderentry.BeanUIOrderEntrySetting;
import com.indocosmo.pos.common.enums.PosInvoicePrintFormat;
import com.indocosmo.pos.common.enums.PosOrderServiceTypes;
import com.indocosmo.pos.common.enums.PosPaymentOption;
import com.indocosmo.pos.common.enums.device.PrinterType;
import com.indocosmo.pos.data.beans.BeanFlashMessage;
import com.indocosmo.pos.data.beans.BeanOrderHeader;
import com.indocosmo.pos.data.beans.BeanPayModeBase.PaymentMode;
import com.indocosmo.pos.data.beans.BeanPaymentModes;
import com.indocosmo.pos.data.beans.BeanUser;
import com.indocosmo.pos.data.providers.shopdb.PosFlashMessageProvider;
import com.indocosmo.pos.data.providers.shopdb.PosPaymentModesProvider;
import com.indocosmo.pos.data.providers.terminaldb.PosDevReceiptPrinterConfigProvider;
import com.indocosmo.pos.forms.PosDailyCashoutForm;
import com.indocosmo.pos.forms.PosMainMenuForm;
import com.indocosmo.pos.forms.PosNumKeypadForm;
import com.indocosmo.pos.forms.PosObjectBrowserForm;
import com.indocosmo.pos.forms.PosObjectBrowserForm.ItemSize;
import com.indocosmo.pos.forms.PosOrderEntryForm;
import com.indocosmo.pos.forms.PosSfitSummaryForm;
import com.indocosmo.pos.forms.PosSoftKeyPadForm;
import com.indocosmo.pos.forms.components.keypads.PosNumKeypad.KeypadTypes;
import com.indocosmo.pos.forms.components.keypads.PosSoftKeyPad.IPosSoftKeypadListner;
import com.indocosmo.pos.forms.components.lightbox.LightBox;
import com.indocosmo.pos.forms.components.objectbrowser.IPosBrowsableItem;
import com.indocosmo.pos.forms.listners.IPosMessageBoxFormListner;
import com.indocosmo.pos.forms.listners.IPosNumKeyPadFormListner;
import com.indocosmo.pos.forms.listners.IPosObjectBrowserListner;
import com.indocosmo.pos.forms.listners.IPosPaymentMetodsFormListner;
import com.indocosmo.pos.forms.messageboxes.PosMessageBoxForm;
import com.indocosmo.pos.forms.messageboxes.PosMessageBoxForm.MessageBoxButtonTypes;
import com.indocosmo.pos.forms.messageboxes.PosMessageBoxForm.MessageBoxResults;
import com.indocosmo.pos.forms.messageboxes.PosMessageBoxForm.MessageBoxTypes;
import com.indocosmo.pos.forms.messageboxes.PosWaitMessageBoxForm;
import com.indocosmo.pos.forms.messageboxes.listners.PosNumKeyPadFormAdapter;
import com.indocosmo.pos.forms.opensessions.PosViewOpenSessions;
import com.indocosmo.pos.forms.orderlistquery.PosOrderListQueryForm;
import com.indocosmo.pos.forms.orderrefund.PosOrderRefundEditForm;
import com.indocosmo.pos.reports.base.PosPrintableReportBase;
import com.indocosmo.pos.reports.receipts.PosDefaultPaymentReceipt;
import com.indocosmo.pos.reports.receipts.PosPRDPaymentReceipt;
import com.indocosmo.pos.reports.receipts.PosReceipts;
import com.indocosmo.pos.reports.receipts.custom.jp.PosPaymentReceiptNihon;
import com.indocosmo.pos.reports.receipts.custom.nz.PosPaymentReceiptNZ;
import com.indocosmo.pos.terminal.devices.PosDeviceManager;
import com.indocosmo.pos.terminal.devices.printer.PosDeviceReceiptPrinter;
import com.sun.istack.internal.FinalArrayList;

/**
 * @author jojesh
 *
 */

public final class PosFormUtil {

	public static final Color SCROLL_BUTTON_BG_COLOR=Color.decode("#299196");
	/**
	 * 
	 */
	public PosFormUtil() {
		// TODO Auto-generated constructor stub
	}	


	public static Font getTextFieldFont(){
		return new Font(PosEnvSettings.getInstance().getUISetting().getTextAreaFont() , Font.PLAIN, 22);
	}	

	public static Font getTextFieldBoldFont(){
		return new Font(PosEnvSettings.getInstance().getUISetting().getTextAreaFont(), Font.BOLD, 20);
	}	
	public static Font getButtonFont(){
				return new Font(PosEnvSettings.getInstance().getUISetting().getLabelFont(), Font.BOLD, 12);
//		return UIManager.getDefaults().getFont("Button.font");
	}
	public static Font getButtonBoldFont(){
		return new Font(PosEnvSettings.getInstance().getUISetting().getLabelFont(), Font.BOLD, 20);
		//		return UIManager.getDefaults().getFont("Button.font");
	}
	public static Font getLabelFont(){
		return new Font(PosEnvSettings.getInstance().getUISetting().getLabelFont(), Font.PLAIN, 18);
	}
	public static Font getLabelFontSmall(){
		return new Font(PosEnvSettings.getInstance().getUISetting().getLabelFont(), Font.BOLD, 14);
	}
	
	public static Font getTableCellFont(){
		return new Font(PosEnvSettings.getInstance().getUISetting().getLabelFont(), Font.PLAIN, 18);
	}
	public static Font getTableHeadingFont(){
		return new Font(PosEnvSettings.getInstance().getUISetting().getLabelFont(), Font.BOLD, 14);
	}
	
	public static Font getSubHeadingFont(){
		return new Font(PosEnvSettings.getInstance().getUISetting().getWindowTitleFont(), Font.BOLD, 14);
	}
	public static Font getHeadingFont(){
		return new Font(PosEnvSettings.getInstance().getUISetting().getWindowTitleFont(), Font.BOLD, 24);
	}
	public static Font getDigitalFont(){
		return PosResUtil.getFontFromResource("digital.ttf").deriveFont(Font.BOLD, 18f);
	}
	public static Font getTextAreaFont(){
		return new Font(PosEnvSettings.getInstance().getUISetting().getTextAreaFont(), Font.PLAIN, 15);
	}
 

	public static Font getMessageBoxTextFont(){
		return new Font(PosEnvSettings.getInstance().getUISetting().getLabelFont(),Font.PLAIN,20);
	}	
	public static Font getLoginLabelFont(){
		return new Font(PosEnvSettings.getInstance().getUISetting().getLabelFont(),Font.PLAIN,20);
	}	
	public static Font getMainmenuItemFont(){
		//		return new Font("sansserif", Font.BOLD, 16);
		return UIManager.getDefaults().getFont("Button.font");
	}

	public static Font getTokenFont(){
		return new Font(getLabelFont().getName(), Font.BOLD, 50);
	}

	public static Font getItemButtonFont(){
		return new Font(getButtonFont().getName(), Font.BOLD, 11);
	}


	public static Font getObjectBrowserItemFont(){
		return new Font(getButtonFont().getName(), Font.BOLD, 11);
	}

	public static Font getLabelHeaderFont(){
		return new Font(getLabelFont().getName(), Font.BOLD, 30);
	}

	public static void setCheckboxDefaultFont(JCheckBox checkBox){		
		 Font newCheckBoxFont=new Font(getLabelFont().getName(),getLabelFont().getStyle(),18);  
		 checkBox.setFont(newCheckBoxFont);  		  
	}
	public static float getFontTextHeight(String text,JComponent container){
		
		LineMetrics matrix = container.getFontMetrics(container.getFont()).getLineMetrics(text,container.getGraphics());
		return matrix.getHeight();
		
	}

	/**
	 * Use this function to show the numerical keypad.
	 * @param textArea the target text area where the value should be populated. 
	 */
	public static void showNumKeyPad(final JTextComponent target,final IPosNumKeyPadFormListner listner){
		showNumKeyPad("Pos-System",null,target,  listner,null,null,null,null,true);
	}

	public static void showNumKeyPad(String title, JDialog dialog,  final JTextComponent target,final IPosNumKeyPadFormListner listner){
		showNumKeyPad(title,dialog,target,listner,null,null,null,null,true);
	}
	
	public static void showNumKeyPad(String title, JDialog dialog,  final JTextComponent target,final IPosNumKeyPadFormListner listner, Double minValue, String minValueError, Double maxValue, String maxValueError,boolean canCancel){
		showNumKeyPad( title,  dialog,    target,  listner,  minValue,  minValueError,  maxValue,  maxValueError, canCancel,null);
	}
	
	public static void showAcceptPasswordUI(JDialog parent, IPosNumKeyPadFormListner listner){
		showNumKeyPad("Password ?",parent, null,listner, null, null,null,null,true,KeypadTypes.Password);
	}
	
	public static void showAcceptCardNoUI(JDialog parent, IPosNumKeyPadFormListner listner){
		showNumKeyPad("Card Number",parent, null,listner, null, null,null,null,true,KeypadTypes.Digital);
	}
	
	public static void showAcceptValueUI(String title, JDialog parent, IPosNumKeyPadFormListner listner){
		showNumKeyPad(title,parent, null,listner, null, null,null,null,true,KeypadTypes.Numeric);
	}
	
	public static void showNumKeyPad(String title, JDialog dialog,  final JTextComponent target,final IPosNumKeyPadFormListner listner, Double minValue, String minValueError, Double maxValue,String maxValueError,boolean canCancel,KeypadTypes keyType){
		
		showNumKeyPad( title,  dialog,    target,  listner,  minValue,  minValueError,  maxValue, maxValueError, 1d, canCancel, keyType);
		
	}
		
		
	public static void showNumKeyPad(String title, JDialog dialog,  final JTextComponent target,final IPosNumKeyPadFormListner listner, Double minValue, String minValueError, Double maxValue,String maxValueError, Double defValue,boolean canCancel,KeypadTypes keyType){
		
		final PosNumKeypadForm posNumberKeypad;
		posNumberKeypad=new PosNumKeypadForm(keyType);
		posNumberKeypad.setMaxValue(maxValue,maxValueError);
		posNumberKeypad.setMinValue(minValue,minValueError);
		posNumberKeypad.setDefaltValue(String.valueOf(defValue));
		posNumberKeypad.setTitle(title);
		posNumberKeypad.setCancelEnabled(canCancel);
		posNumberKeypad.setOnValueChanged(new PosNumKeyPadFormAdapter() {			
			@Override
			public void onValueChanged(String value) {	
				String oldVAlue=null;
				posNumberKeypad.setVisible(false);
				if(target!=null){
					oldVAlue=target.getText();
					target.setText(value);
				}
				if(listner!=null){
					if(target!=null)
						listner.onValueChanged(target,oldVAlue);
					listner.onValueChanged(value);
				}
				posNumberKeypad.dispose();
			}
		});
		if(dialog==null)
			showModal(posNumberKeypad);
		else
			showLightBoxModal(dialog, posNumberKeypad);
	}


	public static void showSoftKeyPad(RootPaneContainer parent, JTextComponent target, final IPosSoftKeypadListner listner){
		
		PosSoftKeyPadForm keyPad=new PosSoftKeyPadForm(target);
		keyPad.setListner(listner);
		showLightBoxModal(parent,keyPad);
	}

	public static void showModal(JDialog dialog){
		show(dialog,true);
	}

	private static void show(JDialog dialog,boolean showModal){
		
		final Dimension dim =PosUtil.getScreenSize(PosEnvSettings.getInstance().getPrimaryScreen());
		final int left = (dim.width-dialog.getWidth())/2;
		final int top = (dim.height-dialog.getHeight())/2;
		
		dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		dialog.setModal(showModal);
		
		((JPanel)dialog.getContentPane()).setBorder(
				BorderFactory.createMatteBorder(4, 4, 5, 4,  new Color(134, 184, 232)));
		dialog.setUndecorated(true);		
		dialog.setAlwaysOnTop(true);
		dialog.setLocation(left,top);
		dialog.setVisible(true);
	}

	public static void show(JDialog dialog){
		show(dialog,false);
	}

	public static void showLightBoxModal(JDialog dialog){
		JFrame parent=null;
		showLightBoxModal(parent,dialog);
	}


	public static void showLightBoxModal(Object parent,JDialog dialog){
		showLightBoxModal((RootPaneContainer) parent,dialog);
	}
	
	public static void showLightBoxModal(RootPaneContainer parent,JDialog dialog){
		
		LightBox lightBox=createLightBox(parent);
		setWindow(dialog);
		dialog.setVisible(true);
		if(lightBox!=null){
			lightBox.removeLightBoxEffect(parent);
			parent.getContentPane().validate();
			parent.getContentPane().repaint();
		}
		
	}

	private static LightBox createLightBox(RootPaneContainer container){
		LightBox lightBox=null;
		if(container!=null && PosEnvSettings.getInstance().getUISetting().showLightBox()){

			boolean isExist=false;
			for(Component comp:container.getLayeredPane().getComponents()){
				if(comp.getClass()==LightBox.class){
					isExist=true;
					break;
				}
				if(!isExist){
					lightBox=new LightBox();
					lightBox.createLightBoxEffect(container);
				}
			}
		}
		return lightBox;
	}
	
	/**
	 * @param dialog
	 */
	public static void setWindow(JDialog dialog){
		setWindow(dialog,true);
	}
	

	/**
	 * @param dialog
	 * @param screen
	 */
	public static void setWindow(JDialog dialog, int screen){
		setWindow(dialog,true,screen);
	}
	
	/**
	 * @param dialog
	 * @param isModal
	 */
	public static void setWindow(JDialog dialog,boolean isModal){
		
		final int screen=PosEnvSettings.getInstance().getPrimaryScreen();
		setWindow(dialog, isModal, screen);
	}
	
	/**
	 * @param dialog
	 * @param isModal
	 * @param displayScreen
	 */
	public static void setWindow(JDialog dialog,boolean isModal, int displayScreen){
		
		final Dimension dim =PosUtil.getScreenSize(displayScreen);
		final int left = (dim.width-dialog.getWidth())/2;
		final int top = (dim.height-dialog.getHeight())/2;
		final Point scrrenLoc=PosUtil.transformScreenPoints(new Point(left,top),displayScreen);
		
		dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		dialog.setModal(isModal);
		
		((JPanel)dialog.getContentPane()).setBorder(
				BorderFactory.createMatteBorder(4, 4, 5, 4,  new Color(134, 184, 232)));
		dialog.setUndecorated(true);		
		dialog.setAlwaysOnTop(true);
		dialog.setLocation(scrrenLoc.x,scrrenLoc.y);
		dialog.setPreferredSize(new Dimension(dialog.getWidth(),dialog.getHeight()));
		dialog.pack();
		
		applyWindowRenderingHack(dialog);
		
	}

	private static PosMessageBoxForm createMessageBox(MessageBoxTypes boxType, MessageBoxButtonTypes buttonType, String message, IPosMessageBoxFormListner listner){
		
		PosMessageBoxForm msgForm=new PosMessageBoxForm(buttonType);
		msgForm.setMessageBoxIcon(boxType);
		msgForm.setMessage(message);
		msgForm.setListner(listner);
		return msgForm;
	}

	public static MessageBoxResults showQuestionMessageBox(Object parent, MessageBoxButtonTypes type, String message, IPosMessageBoxFormListner listner){
		return showQuestionMessageBox((RootPaneContainer) parent,  type,  message,  listner);
	}
	 
	public static MessageBoxResults showQuestionMessageBox(RootPaneContainer parent, MessageBoxButtonTypes type, String message, IPosMessageBoxFormListner listner){
		PosMessageBoxForm msgForm=createMessageBox(MessageBoxTypes.Question, type,  message,  listner);
		showLightBoxModal(parent,msgForm);
		return msgForm.getResult();
	}

	public static MessageBoxResults showQuestionMessageBox(RootPaneContainer parent, MessageBoxButtonTypes type, String message, IPosMessageBoxFormListner listner,String noActionText){
		PosMessageBoxForm msgForm=createMessageBox(MessageBoxTypes.Question, type,  message,  listner);
		msgForm.setButtonNoActionName(noActionText);
		showLightBoxModal(parent,msgForm);
		return msgForm.getResult();
	}
	public static void showSystemError(Object parent){
		showSystemError((RootPaneContainer) parent);
	}
	public static void showSystemError(RootPaneContainer parent){
		PosFormUtil.showErrorMessageBox(parent, "System error!!! Please contact Administrator");
	}
	public static MessageBoxResults showErrorMessageBox(Object parent,String message){
		return showErrorMessageBox ((RootPaneContainer) parent,message);
	}
	public static MessageBoxResults showErrorMessageBox(RootPaneContainer parent,String message){
		PosMessageBoxForm msgForm=createMessageBox(MessageBoxTypes.Error, MessageBoxButtonTypes.CancelOnly,  message,  null);
		showLightBoxModal(parent,msgForm);
		return msgForm.getResult();
	}
	
	public static MessageBoxResults showInformationMessageBox(Object parent,String message){
		return showInformationMessageBox((RootPaneContainer) parent,message);
	}
	
	public static MessageBoxResults showInformationMessageBox(RootPaneContainer parent,String message){
		PosMessageBoxForm msgForm=createMessageBox( MessageBoxTypes.Information,MessageBoxButtonTypes.OkOnly,  message,  null);
		showLightBoxModal(parent,msgForm);
		return msgForm.getResult();
	}
	
	public static PosMessageBoxForm showCancelOnlyMessageBox(RootPaneContainer parent, String message, IPosMessageBoxFormListner listner){
		PosMessageBoxForm msgForm=createMessageBox(MessageBoxTypes.Information, MessageBoxButtonTypes.CancelOnly,  message,  listner);
		return msgForm;
	}

	private  static PosWaitMessageBoxForm mPosWaitMessageBoxForm;
	public static void showBusyWindow(Container parent,String message){
		if(mPosWaitMessageBoxForm==null)
			mPosWaitMessageBoxForm=new PosWaitMessageBoxForm();
		mPosWaitMessageBoxForm.setMessage(message);
		PosFormUtil.showLightBoxModal(parent, mPosWaitMessageBoxForm);
	}

	public static void closeBusyWindow(){
		if(mPosWaitMessageBoxForm==null)
			return;
		mPosWaitMessageBoxForm.setVisible(false);
		mPosWaitMessageBoxForm.dispose();
		mPosWaitMessageBoxForm=null;
	}

	public static void showBusyWindow(boolean show){
		if(mPosWaitMessageBoxForm==null)
			return;
		mPosWaitMessageBoxForm.setVisible(false);
	}
	
	public static void setBusyWindowMessage(String message){
		if(mPosWaitMessageBoxForm==null)
			return;
		mPosWaitMessageBoxForm.setMessage(message);
	}

	public static void showObjectBrowser(String title,IPosBrowsableItem[] itemList, IPosObjectBrowserListner listner){
		PosObjectBrowserForm objBrowserForm=new PosObjectBrowserForm(title, itemList);
		objBrowserForm.setListner(listner);
		showLightBoxModal(objBrowserForm);
	}
	
	public static void showPosError(String message){
//		PosFormUtil.showMessageBox(null,MessageButtonType.OkOnly,message);
	}

	public static void sendKeys(int keycode,Component componenet){

		if(componenet==null) return;
		
		try {
			Robot rb=new Robot();
			componenet.requestFocus();
			rb.keyPress(keycode);
			rb.keyRelease(keycode);
		} catch (AWTException e) {
			e.printStackTrace();
			return;
		}
	}
	
	/**
	 * @param str
	 * @param chr
	 * @return
	 */
	public static String getMnemonicString(String str, Character chr){
		
		String mnemonicString=str;
		
		if(chr!=null && chr!=0 && str!=null && str.length()>0){
			
			if (!str.contains(chr.toString())){
				if (str.contains(":"))
					str=str.replace(":", "(" + chr.toString() + ")" + " :");
				else
					str=str + chr.toString();
			}
				
				
			mnemonicString= "<html>" + str.replace(String.valueOf(chr), "<u>"+chr+"</u>") + "</html>";
		}
		
		return mnemonicString;
	}
	
	public static void sendKeys(int modifier,int keycode,Component componenet){

		if(componenet==null) return;
		
		try {
			Robot rb=new Robot();
			componenet.requestFocus();
			rb.keyPress(modifier);
			rb.keyPress(keycode);
			rb.keyRelease(keycode);
			rb.keyRelease(modifier);
		} catch (AWTException e) {
			e.printStackTrace();
			return;
		}
	}
	
	public static final Color SECTION_HEADING_PANEL_BG_COLOR=new Color(78,128,188);
	public static final Color SECTION_HEADING_PANEL_FG_COLOR=Color.WHITE;
	private static final int SECTION_HEADING_HEIGHT=25;
	
	public static JLabel setHeading(String heading,int width){
		return setHeading( heading, width,SECTION_HEADING_HEIGHT);
	}
	public static JLabel setHeading(String heading,int width, int height){
		JLabel headingLabel=new JLabel(heading,SwingConstants.CENTER);
		headingLabel.setVerticalAlignment(SwingConstants.CENTER);
		headingLabel.setSize(new Dimension(width,height));
		headingLabel.setPreferredSize(new Dimension(width,height));
		headingLabel.setBackground(SECTION_HEADING_PANEL_BG_COLOR);
		headingLabel.setForeground(SECTION_HEADING_PANEL_FG_COLOR);
		headingLabel.setOpaque(true);
		headingLabel.setFont(PosFormUtil.getSubHeadingFont());
		return headingLabel;
	}
	
	public static DefaultTableModel getReadonlyTableModel(){
		return	 new DefaultTableModel(){
			
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			/* (non-Javadoc)
			 * @see javax.swing.table.DefaultTableModel#isCellEditable(int, int)
			 */
			@Override
			public boolean isCellEditable(int row, int column) {
				// TODO Auto-generated method stub
				return false;
			}
		};
	}
	
	public static DefaultTableModel getReadonlyTableModel(final int[] skipCol){
		return	 new DefaultTableModel(){

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			/* (non-Javadoc)
			 * @see javax.swing.table.DefaultTableModel#isCellEditable(int, int)
			 */
			@Override
			public boolean isCellEditable(int row, int column) {
				boolean isEditable=false;
				for(int i=0; i<skipCol.length;i++){
					if(skipCol[i]==column){
						isEditable=true;
						break;
					}
				}
				return isEditable;
			}
		};
	}
	
	public static final WindowListener WINDOW_RENDERING_HACK_LISTENER=new WindowListener() {

		@Override
		public void windowOpened(WindowEvent e) {
			
			hack(e.getWindow());
		}

		@Override
		public void windowIconified(WindowEvent e) {}

		@Override
		public void windowDeiconified(WindowEvent e) {}

		@Override
		public void windowDeactivated(WindowEvent e) {}

		@Override
		public void windowClosing(WindowEvent e) {}

		@Override
		public void windowClosed(WindowEvent e) {}

		@Override
		public void windowActivated(WindowEvent e) {
			hack(e.getWindow());
		}

		/**
		 * @param wnd
		 */
		public void hack(Window wnd){
			wnd.revalidate();
			wnd.repaint();
		}
	};
	
	/**
	 * @param wnd
	 */
	public static void applyWindowRenderingHack(Window wnd){

		if(!PosEnvSettings.getInstance().getUISetting().isApplyWindowRenderingHack()) return;

		wnd.removeWindowListener(WINDOW_RENDERING_HACK_LISTENER);
		wnd.addWindowListener(WINDOW_RENDERING_HACK_LISTENER);
	}
	
	/**
	 * 
	 */
	public static void showOrderListForm(RootPaneContainer parent, boolean validateCurUser) {

		final BeanUser user=PosAccessPermissionsUtil.validateAccess(parent, validateCurUser, "order_details_export");
		
		if(user!=null){
			
			final PosOrderListQueryForm form = new PosOrderListQueryForm();
			form.setActiveUser(user);
			PosFormUtil.showLightBoxModal(parent,form);
		}
	}
	
	/**
	 * 
	 */
	public static void showOpenSessiosForm(RootPaneContainer parent, boolean validateCurUser) {
			
			final PosViewOpenSessions form = new PosViewOpenSessions();
			PosFormUtil.showLightBoxModal(parent,form);
	}
	
	/**
	 * 
	 */
	public static void showOrderRefundForm(RootPaneContainer parent, boolean validateCurUser,String orderId) {
		
		final BeanUser user=PosAccessPermissionsUtil.validateAccess(parent, validateCurUser, "order_refund");

		if(user!=null){
			
//			final PosOrderRefundForm form = new PosOrderRefundForm(orderId);
			final PosOrderRefundEditForm form = new PosOrderRefundEditForm(orderId);
			form.setActiveUser(user);
			PosFormUtil.showLightBoxModal(parent,form);
		}
	}
	
	/**
	 * @param parent
	 * @param validateCurUser
	 */
	public static void showShiftSummaryForm(RootPaneContainer parent, boolean validateCurUser) {
		
		final BeanUser user=PosAccessPermissionsUtil.validateAccess(parent, validateCurUser, "shift_summary");

		if(user!=null){
			
			final PosSfitSummaryForm form = new PosSfitSummaryForm();
			form.setActiveUser(user);
			PosFormUtil.showLightBoxModal(parent,form);
		}
	}
	/**
	 * 
	 */
	public static void showCashOutForm(RootPaneContainer parent, boolean validateCurUser) {
		
		final BeanUser user=PosAccessPermissionsUtil.validateAccess(parent, validateCurUser, "daily_cashout");

		if(user!=null){
			
			final PosDailyCashoutForm form=new PosDailyCashoutForm(parent);
			form.setActiveUser(user);
			PosFormUtil.showLightBoxModal(parent,form);
			
		}
	}
	 
 
	/**
	 * @param parent. Parent Form
	 * @param forceToPrint. Allow to cancel the printing false  
	 * @param orderHeader. Order header to print
	 * @param openCashBox.  
	 * @param paymentSummaryInfo
	 * @param isBillPrinting BIll printing or receipt printing
	 * @param printMultiple Allow to print multiple copes as configure in printer settings
	 * @return whether printed
	 * @throws Exception
	 */
	public static boolean showPrintConfirmMessage(
			final RootPaneContainer parent, 
			final boolean forceToPrint,
			final BeanOrderHeader orderHeader,
			final Boolean openCashBox, 
			final BeanBillPaymentSummaryInfo paymentSummaryInfo,  
			final boolean isBillPrinting) throws Exception{

		boolean isCancelled=false;
		
		if(!PosDeviceManager.getInstance().hasReceiptPrinter())
			return false;
		

		final boolean useAltLangToPrint=PosEnvSettings.getInstance().getPrintSettings().isLanguageSwitchingAllowed()?
					PosDeviceManager.getInstance().canUseAltLanguage(PrinterType.Receipt80):false;
			
			if (!forceToPrint && PosEnvSettings.getInstance().getPrintSettings().getInvoiceFormat(orderHeader.getOrderServiceType())==
					PosInvoicePrintFormat.BOTH){
				final PosObjectBrowserForm form=new PosObjectBrowserForm("Print Menu", PosInvoicePrintFormat.values() , ItemSize.Wider,1,3);
				form.setListner(new IPosObjectBrowserListner() {
		
					@Override
					public void onItemSelected(IPosBrowsableItem item) {
						
						boolean altLangToPrint;
						try{
							switch ((PosInvoicePrintFormat)item){
							
								case EIGHTY_MM:
		
									 altLangToPrint=useAltLangToPrint &&(PosFormUtil.showQuestionMessageBox(parent, MessageBoxButtonTypes.YesNo  ,
												"Do you want to print receipt using alternatieve language?",null)==MessageBoxResults.Yes);
									PosReceipts.printReceipt80MM(parent, orderHeader,openCashBox,paymentSummaryInfo,isBillPrinting,altLangToPrint);
									break;
								case A4:
									PosReceipts.printReceiptA4(parent, orderHeader,openCashBox,paymentSummaryInfo,isBillPrinting,useAltLangToPrint);
									break;
								case BOTH:
									altLangToPrint=useAltLangToPrint &&(PosFormUtil.showQuestionMessageBox(parent, MessageBoxButtonTypes.YesNo  ,
												"Do you want to print receipt using alternatieve language?",null)==MessageBoxResults.Yes);
									PosReceipts.printReceipt80MM(parent, orderHeader,openCashBox,paymentSummaryInfo,isBillPrinting,altLangToPrint);
									if(PosFormUtil.showQuestionMessageBox(parent,
												MessageBoxButtonTypes.YesNo,
												"Do you want to print receipt in A4 size?",null)==MessageBoxResults.Yes)	
									
										PosReceipts.printReceiptA4(parent, orderHeader,openCashBox,paymentSummaryInfo,isBillPrinting,useAltLangToPrint);
							
									break;
								
							}
						}catch(Exception ex){
							if(openCashBox)
								PosDeviceManager.getInstance().getPrinter(PrinterType.Receipt80).openCashBox();

						}
					}
		
					@Override
					public void onCancel() {
						if(openCashBox)
							PosDeviceManager.getInstance().getPrinter(PrinterType.Receipt80).openCashBox();
		
					}
				});
				PosFormUtil.showLightBoxModal(parent,form);
			}else{
				
				MessageBoxResults res=MessageBoxResults.Yes;
				if(forceToPrint && useAltLangToPrint)
					res=PosFormUtil.showQuestionMessageBox(parent,
					MessageBoxButtonTypes.YesNo,
							"Do you want to print receipt using alternatieve language?",null);
				else if(!forceToPrint && useAltLangToPrint)
					res=PosFormUtil.showQuestionMessageBox(parent,
					MessageBoxButtonTypes.YesNoCancel,
							"Do you want to print receipt using alternatieve language?",null);
				else if(!forceToPrint)
					res=PosFormUtil.showQuestionMessageBox(parent,
								MessageBoxButtonTypes.YesNo,
									"Do you want to print receipt?",null);
				
				boolean altLangToPrint=useAltLangToPrint;
				switch(res){

				case Yes:
//					altLangToPrint=true;
					isCancelled=false;
					break;
				case Cancel:
					altLangToPrint=false;
					isCancelled=true;
					break;
				case No:
					altLangToPrint=false;
					isCancelled=(!useAltLangToPrint);
					break;
				default:
					break;

				}
				
				if(!isCancelled){
					
				 
					switch (PosEnvSettings.getInstance().getPrintSettings().getInvoiceFormat(orderHeader.getOrderServiceType())){
					
						case EIGHTY_MM:
							PosReceipts.printReceipt80MM(parent, orderHeader,openCashBox,paymentSummaryInfo,isBillPrinting,altLangToPrint);
							break;
						case A4:
							PosReceipts.printReceiptA4(parent, orderHeader,openCashBox,paymentSummaryInfo,isBillPrinting,useAltLangToPrint);
							break;
						case BOTH:
							PosReceipts.printReceipt80MM(parent, orderHeader,openCashBox,paymentSummaryInfo,isBillPrinting,altLangToPrint);
							PosReceipts.printReceiptA4(parent, orderHeader,openCashBox,paymentSummaryInfo,isBillPrinting,useAltLangToPrint);
					
							break;
					}
					 
				}else
					if(openCashBox)
						PosDeviceManager.getInstance().getPrinter(PrinterType.Receipt80).openCashBox();


			} 
		return !isCancelled;
	}

	
	/**
	 * @param parent. Parent Form
	 * @param forceToPrint. Allow to cancel the printing false  
	 * @param orderHeader. Order header to print
	 * @param openCashBox.  
	 * @param paymentSummaryInfo
	 * @param isBillPrinting BIll printing or receipt printing
	 * @param printMultiple Allow to print multiple copes as configure in printer settings
	 * @return whether printed
	 * @throws Exception
	 */
	public static boolean showRefundPrintConfirmMessage(
			RootPaneContainer parent, 
			boolean forceToPrint,
			BeanOrderHeader orderHeader,
			Boolean openCashBox) throws Exception{

		boolean isCancelled=false;
		boolean useAltLangToPrint=PosDeviceManager.getInstance().getReceiptPrinter().isUseAltLanguage();

		if(PosDeviceManager.getInstance().hasReceiptPrinter()){

			if(PosDeviceManager.getInstance().getReceiptPrinter()!=null ){

				if(!(PosDeviceManager.getInstance().getReceiptPrinter().isUseAltLanguage() && PosEnvSettings.getInstance().getPrintSettings().isLanguageSwitchingAllowed()) ){

					if(!forceToPrint){

						if(PosFormUtil.showQuestionMessageBox(parent,
								MessageBoxButtonTypes.YesNo,
								"Do you want to print receipt?",
								null)==MessageBoxResults.No){

							isCancelled=true;
						}
					}

				}else{

					final MessageBoxResults res=PosFormUtil.showQuestionMessageBox(parent,
								((forceToPrint)?MessageBoxButtonTypes.YesNo:MessageBoxButtonTypes.YesNoCancel),
								"Do you want to print receipt using alternatieve language?",null);

					switch(res){

					case Yes:
						useAltLangToPrint=true;
						isCancelled=false;
						break;
					case Cancel:
						useAltLangToPrint=false;
						isCancelled=true;
						break;
					case No:
						useAltLangToPrint=false;
						isCancelled=false;
						break;
					default:
						break;

					}
				}
			}
			
		}else
			isCancelled=true;
		
		if(!isCancelled){
			
			PosReceipts.printRefundReceipt(orderHeader, openCashBox, useAltLangToPrint);
		}else
			if(openCashBox)
				PosDeviceManager.getInstance().getPrinter(PrinterType.Receipt80).openCashBox();


		return !isCancelled;
	}

	
	/*
	 * 
	 */
	public static boolean canRound(PaymentMode payMode){
	
		final PosPaymentModesProvider posPaymentModesProvider = PosPaymentModesProvider.getInstance();
		BeanPaymentModes paymentModes = posPaymentModesProvider.getPaymentModes();
		
		boolean result=true;
		
		switch(payMode){
		 case Cash:
			 result=paymentModes.isCanCashRound();
			 break;
		 case Card:
			 result= paymentModes.isCanCardRound();
			 break;
		 case Company:
			 result= paymentModes.isCanCompanyRound();
			 break;
		 case Coupon:
			 result=paymentModes.isCanVoucherRound();
			 break;
		  default:
				 break;
		}
		return result;
		
	 
	}
	
	private static HashMap<Integer, String> functionKeyMap;
	/**
	 * @param keyCode
	 * @return
	 */
	public static String getFunctionKeyChar(int keyCode){
		
		String keyChar="";
		if(functionKeyMap==null){
			functionKeyMap=new HashMap<Integer, String>();
			int key=111;
			for(int index=1; index<=12; index++){
			
				key++;
				functionKeyMap.put(key, "F"+index);
				
			}
		}
		
		if(functionKeyMap.containsKey(keyCode))
			keyChar=functionKeyMap.get(keyCode);
		
		return keyChar;
		
	}


	/*
	 * 
	 */
	public static void showPaymentOptions(final RootPaneContainer parent, final IPosPaymentMetodsFormListner payMethodListner,
			 BeanOrderHeader orderHdr){
		try {

		  	final  BeanUIOrderEntrySetting uiOrderEtrySettings=
					  PosEnvSettings.getInstance().getUISetting().getOrderEntryUISettings();
					
				  	PosPaymentOption paymentOption= uiOrderEtrySettings.getPaymentOption();
				  	
				 	switch (paymentOption){
				 	case SPLIT:
				 		paymentOption=(uiOrderEtrySettings.getPaymentPanelSetting().isSplitPayButtonVisibile() && 
				 				orderHdr.getOrderServiceType()!=PosOrderServiceTypes.SALES_ORDER)?PosPaymentOption.SPLIT:PosPaymentOption.STANDARD;
					case STANDARD:
					case QUICKCASH:
				 	case QUICKCARD:
				 	case ONLINE:
				 		payMethodListner.onClick(uiOrderEtrySettings.getPaymentOption());
						break;
					case ASK:
						showPaymentOptions4Ask(parent,payMethodListner,orderHdr);
					}

		} catch (Exception e) {

			PosLog.write(parent ,"paymentButtonListner.orderClicked", e);
			PosFormUtil.showSystemError(parent);
		}
	}
	
	/**
	 * 
	 */
	public static void showPaymentOptions4Ask(final RootPaneContainer parent, final IPosPaymentMetodsFormListner payMethodListner,
			BeanOrderHeader orderHdr){
		
		ArrayList<IPosBrowsableItem> paymentOptions =new ArrayList<IPosBrowsableItem>();
		
		paymentOptions.add(PosPaymentOption.STANDARD);
		
		if(PosEnvSettings.getInstance().getUISetting().getOrderEntryUISettings().getPaymentPanelSetting().isSplitPayButtonVisibile() && 
				orderHdr.getOrderServiceType()!=PosOrderServiceTypes.SALES_ORDER) 
			paymentOptions.add(PosPaymentOption.SPLIT);	
		
		if(PosEnvSettings.getInstance().getUISetting().getOrderEntryUISettings().getPaymentPanelSetting().isOnlinePayButtonVisible()) 
			paymentOptions.add(PosPaymentOption.ONLINE);	
		
		if(PosEnvSettings.getInstance().getUISetting().getQuickPayMode()!=null){
			for(String payMode :PosEnvSettings.getInstance().getUISetting().getQuickPayMode()){
				
					if(Integer.parseInt(payMode)==1)
						paymentOptions.add(PosPaymentOption.QUICKCASH);
					else if(Integer.parseInt(payMode)==2)
						paymentOptions.add(PosPaymentOption.QUICKCARD);
					else if (orderHdr.getCustomer().isIsArCompany())
						paymentOptions.add(PosPaymentOption.QUICKCREDIT);
					 
	
			}
		}
		
//		final PosObjectBrowserForm form=new PosObjectBrowserForm("Quick Payment Mode", quickPayModes , ItemSize.Wider,1,2);
		
		PosObjectBrowserForm form=new PosObjectBrowserForm("Payment Options", paymentOptions,ItemSize.Wider,true);
		form.setListner(new IPosObjectBrowserListner() {
			
			@Override
			public void onItemSelected(IPosBrowsableItem item) {
					
				try {
					
					payMethodListner.onClick((PosPaymentOption)item); 
						
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					PosLog.write(parent, "showPaymentOptions", e);
					e.printStackTrace();
				}
				
			}
			
			@Override
			public void onCancel() {
				// TODO Auto-generated method stub
				
			}
		});
		PosFormUtil.showLightBoxModal(parent,form);
 
	}
 
	
	
	/**
	 * @param parent. Parent Form
	 * @param forceToPrint. Allow to cancel the printing false  
	 * @param orderHeader. Order header to print
	 * @param openCashBox.  
	 * @param paymentSummaryInfo
	 * @param isBillPrinting BIll printing or receipt printing
	 * @param printMultiple Allow to print multiple copes as configure in printer settings
	 * @return whether printed
	 * @throws Exception
	 */
 
	public static boolean showSalesOrderPrintConfirmMessage(
			RootPaneContainer parent, 
			boolean forceToPrint,
			BeanOrderHeader orderHeader,
			Boolean openCashBox) throws Exception{

		boolean isCancelled=false;
		boolean useAltLangToPrint=false;

		if(PosDeviceManager.getInstance().hasReceiptPrinter()){
			useAltLangToPrint = PosDeviceManager.getInstance().canUseAltLanguage(PrinterType.Normal);
			if(PosDeviceManager.getInstance().getReceiptPrinter()!=null ){


					if(!forceToPrint){

						if(PosFormUtil.showQuestionMessageBox(parent,
								MessageBoxButtonTypes.YesNo,
								"Do you want to print Sales Order Receipt?",
								null)==MessageBoxResults.No){

							isCancelled=true;
						}
					}

			}
			
		}else
			isCancelled=true;
		
		if(!isCancelled){
			
			PosReceipts.printSOReceipt(orderHeader, openCashBox, useAltLangToPrint); 
		}else
			if(openCashBox)
				PosDeviceManager.getInstance().getReceiptPrinter().openCashBox();


		return !isCancelled;
	}
	
	/**
	 * @param parent. Parent Form
	 * @param forceToPrint. Allow to cancel the printing false  
	 * @param orderHeader. Order header to print
	 * @param openCashBox.  
	 * @param paymentSummaryInfo
	 * @param isBillPrinting BIll printing or receipt printing
	 * @param printMultiple Allow to print multiple copes as configure in printer settings
	 * @return whether printed
	 * @throws Exception
	 */
 
	public static boolean showSOInvoicePrintConfirmMessage(
			RootPaneContainer parent, 
			boolean forceToPrint,
			BeanOrderHeader orderHeader,
			Boolean openCashBox) throws Exception{

		boolean isCancelled=false;
		boolean useAltLangToPrint=false;
		 
		 
				
			 
		if(PosDeviceManager.getInstance().hasReceiptPrinter()){
			useAltLangToPrint = PosDeviceManager.getInstance().canUseAltLanguage(PrinterType.Normal);
			if(PosDeviceManager.getInstance().getReceiptPrinter()!=null ){


					if(!forceToPrint){

						if(PosFormUtil.showQuestionMessageBox(parent,
								MessageBoxButtonTypes.YesNo,
								"Do you want to print the receipt in Sales Order Invoice format?",
								null)==MessageBoxResults.No){

							isCancelled=true;
						}
					}

			}
			
		}else
			isCancelled=true;
		
		if(!isCancelled){
			
			PosReceipts.printSOInvoice(orderHeader, openCashBox,  useAltLangToPrint);  
		}else
			if(openCashBox)
				PosDeviceManager.getInstance().getReceiptPrinter().openCashBox();


		return !isCancelled;
	}
	/*
	 * 
	 */
	public static void checkForScrollMessges() {

		PosFlashMessageProvider msgProvider = new PosFlashMessageProvider();
		ArrayList<BeanFlashMessage> messageList = msgProvider.getNotificationMessage();
		String message = "" ;
		
		if(messageList != null && messageList.size() >0) {
			
			for(BeanFlashMessage flashMsg:messageList){
				
				message+= (message.trim().equals("")?"": "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
				message+=  flashMsg.getContent() ;
			}
		}
		message =message.trim().length()==0?"":"<html><body>" + message  + "</body></html>" ;
		PosMainMenuForm.SET_MESSAGE(message);
		PosOrderEntryForm.SET_MESSAGE(message);
		

	}


}
