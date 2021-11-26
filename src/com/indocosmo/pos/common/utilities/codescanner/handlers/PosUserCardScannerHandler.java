/**
 * 
 */
package com.indocosmo.pos.common.utilities.codescanner.handlers;

import com.indocosmo.pos.common.utilities.codescanner.enums.PosScannerPatternType;
import com.indocosmo.pos.common.utilities.codescanner.handlers.PosScannerHandler.IPosScannerActionLitener;
import com.indocosmo.pos.common.utilities.codescanner.handlers.PosUserCardScannerHandler.IPosUserCodeScannerActionLitener;

/**
 * @author jojesh-13.2
 *
 */
public class PosUserCardScannerHandler extends PosScannerHandler<IPosUserCodeScannerActionLitener> {


	/**
	 * @param actionHandler
	 */
	public PosUserCardScannerHandler(
			IPosUserCodeScannerActionLitener actionHandler) {
		super(actionHandler,PosScannerPatternType.USER);
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.pos.common.utilities.scanner.PosScannerHandler#processScannedCode(com.indocosmo.pos.common.utilities.scanner.PosScannerHandler.IPosScnannerActionLitener, java.lang.String)
	 */
	@Override
	public boolean processScannedCode(String code) {
		
		boolean isProcessed=false;
		
		if(super.processScannedCode(code)){
		
			if(listener!=null)
				isProcessed=listener.onUserCardScanned(getValues().get(1));
		}
		
		return isProcessed;
	}

	/**
	 * @author jojesh-13.2
	 *
	 */
	public interface IPosUserCodeScannerActionLitener extends IPosScannerActionLitener{
		
		public boolean onUserCardScanned(String cardNumber);
		
	}
	
}
