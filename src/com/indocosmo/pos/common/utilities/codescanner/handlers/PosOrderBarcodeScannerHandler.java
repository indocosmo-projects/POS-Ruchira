/**
 * 
 */
package com.indocosmo.pos.common.utilities.codescanner.handlers;

import com.indocosmo.pos.common.utilities.codescanner.enums.PosScannerPatternType;
import com.indocosmo.pos.common.utilities.codescanner.handlers.PosOrderBarcodeScannerHandler.IPosOrderBarcodeScannerActionLitener;
import com.indocosmo.pos.common.utilities.codescanner.handlers.PosScannerHandler.IPosScannerActionLitener;

/**
 * @author jojesh-13.2
 *
 */
public class PosOrderBarcodeScannerHandler extends PosScannerHandler<IPosOrderBarcodeScannerActionLitener> {

	/**
	 * @param type
	 */
	public PosOrderBarcodeScannerHandler(IPosOrderBarcodeScannerActionLitener handler) {
		super(handler,PosScannerPatternType.ORDER);
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.pos.common.utilities.scanner.PosScannerHandler#processScannedCode(com.indocosmo.pos.common.utilities.scanner.PosScannerHandler.IPosScnannerActionLitener, java.lang.String)
	 */
	@Override
	public boolean processScannedCode(String code) {
		
		boolean isProcessed=false;
		
		if(super.processScannedCode(code)){
		
			if(listener!=null)
				isProcessed=listener.onOrderCodeScanned(getValues().get(0));
		}
		
		return isProcessed;
	}

	/**
	 * @author jojesh-13.2
	 *
	 */
	public interface IPosOrderBarcodeScannerActionLitener extends IPosScannerActionLitener{
		
		public boolean onOrderCodeScanned(String order);
		
	}
	
}
