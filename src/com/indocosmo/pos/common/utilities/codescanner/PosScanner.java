/**
 * 
 */
package com.indocosmo.pos.common.utilities.codescanner;

import java.awt.KeyEventPostProcessor;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import com.indocosmo.pos.common.utilities.codescanner.handlers.PosEmptyTrayBarcodeScannerHandler;
import com.indocosmo.pos.common.utilities.codescanner.handlers.PosEmptyTrayBarcodeScannerHandler.IPosEmptyTrayBarcodeScannerActionLitener;
import com.indocosmo.pos.common.utilities.codescanner.handlers.PosItemTrayBarcodeScannerHandler;
import com.indocosmo.pos.common.utilities.codescanner.handlers.PosItemTrayBarcodeScannerHandler.IPosItemTrayBarcodeScannerActionLitener;
import com.indocosmo.pos.common.utilities.codescanner.handlers.PosMemberCardScannerHandler;
import com.indocosmo.pos.common.utilities.codescanner.handlers.PosOrderBarcodeScannerHandler;
import com.indocosmo.pos.common.utilities.codescanner.handlers.PosScannerHandler;
import com.indocosmo.pos.common.utilities.codescanner.handlers.PosUserCardScannerHandler;
import com.indocosmo.pos.common.utilities.codescanner.handlers.PosWMBarcodeScannerHandler;
import com.indocosmo.pos.common.utilities.codescanner.handlers.PosMemberCardScannerHandler.IPosMemberCodeScannerActionLitener;
import com.indocosmo.pos.common.utilities.codescanner.handlers.PosOrderBarcodeScannerHandler.IPosOrderBarcodeScannerActionLitener;
import com.indocosmo.pos.common.utilities.codescanner.handlers.PosScannerHandler.IPosScannerActionLitener;
import com.indocosmo.pos.common.utilities.codescanner.handlers.PosUserCardScannerHandler.IPosUserCodeScannerActionLitener;
import com.indocosmo.pos.common.utilities.codescanner.handlers.PosWMBarcodeScannerHandler.IPosWMBarcodeScannerActionLitener;


/**
 * @author jojesh
 *
 */
public class PosScanner implements KeyEventPostProcessor {
	
	private String mScannedValue="";
	private ArrayList<PosScannerHandler> mScanner; 
	/**
	 * @param handler
	 */
	public PosScanner(IPosScannerActionLitener handler){
		
		mScanner=new ArrayList<PosScannerHandler>();
		
		mScanner.add(new PosUserCardScannerHandler((IPosUserCodeScannerActionLitener)handler));
		mScanner.add(new PosMemberCardScannerHandler((IPosMemberCodeScannerActionLitener)handler));
		mScanner.add(new PosWMBarcodeScannerHandler((IPosWMBarcodeScannerActionLitener)handler));
		mScanner.add(new PosEmptyTrayBarcodeScannerHandler((IPosEmptyTrayBarcodeScannerActionLitener)handler));
		mScanner.add(new PosItemTrayBarcodeScannerHandler((IPosItemTrayBarcodeScannerActionLitener)handler));
		mScanner.add(new PosOrderBarcodeScannerHandler((IPosOrderBarcodeScannerActionLitener)handler));
		
		setOnCodeScanListner((IPosDefaultCodeScanListner)handler);
	}
	/* (non-Javadoc)
	 * @see java.awt.KeyEventPostProcessor#postProcessKeyEvent(java.awt.event.KeyEvent)
	 */
	@Override
	public boolean postProcessKeyEvent(KeyEvent e) {
		
		boolean isUsed=true;
		boolean isProcessed=false;
		if(e.getID() == KeyEvent.KEY_RELEASED) {
			if(e.getKeyCode()==KeyEvent.VK_ENTER){
				if(!mScannedValue.trim().equals("")){
					for(PosScannerHandler scanner:mScanner){
						if(scanner.processScannedCode(mScannedValue)){
							isProcessed=true;
							break;
						}
					}
					if(mListner!=null && !isProcessed)
						mListner.onCodeScanned(mScannedValue);
					
				}else{
					// No action performed. Pass to control to caller to handel enter key.
					if(mListner!=null)
						mListner.defaultEmptyCodeScanned();
				}						
				resetScanner();
				if(isUsed)
					e.consume();
			}
			else if(Character.isDefined(e.getKeyChar()))
				mScannedValue+=e.getKeyChar();
		}
		return true;
	}
	
	private IPosDefaultCodeScanListner mListner;
	/**
	 * @param listner
	 */
	private void setOnCodeScanListner(IPosDefaultCodeScanListner listner){
		mListner=listner;
	}
	
	/**
	 * 
	 */
	public void resetScanner(){
		mScannedValue="";
	}
	
	/**
	 * @author jojesh
	 *
	 */
	public interface IPosDefaultCodeScanListner extends IPosScannerActionLitener{
		/**
		 * @param code
		 */
		public boolean onCodeScanned(String code);
	}
	
	

}
