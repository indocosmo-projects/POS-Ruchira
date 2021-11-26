/**
 * 
 */
package com.indocosmo.pos.common.utilities.codescanner.handlers;

import com.indocosmo.pos.common.utilities.codescanner.bean.BeanWeighingBarcodeDtl;
import com.indocosmo.pos.common.utilities.codescanner.enums.PosScannerPatternType;
import com.indocosmo.pos.common.utilities.codescanner.handlers.PosEmptyTrayBarcodeScannerHandler.IPosEmptyTrayBarcodeScannerActionLitener;
import com.indocosmo.pos.common.utilities.codescanner.handlers.PosScannerHandler.IPosScannerActionLitener;

/**
 * @author jojesh-13.2
 *
 */
public class PosEmptyTrayBarcodeScannerHandler extends PosWeighingBarcodeScannerHandler<IPosEmptyTrayBarcodeScannerActionLitener> {



	/**
	 * @param actionHandler
	 */
	public PosEmptyTrayBarcodeScannerHandler(
			IPosEmptyTrayBarcodeScannerActionLitener actionHandler) {
		super(actionHandler,PosScannerPatternType.TRAY);
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.pos.common.utilities.scanner.PosScannerHandler#processScannedCode(com.indocosmo.pos.common.utilities.scanner.PosScannerHandler.IPosScnannerActionLitener, java.lang.String)
	 */
	@Override
	public boolean processScannedCode(String code) {
		
		boolean isProcessed=false;
		
		if(super.processScannedCode(code)){
		
			if(listener!=null && getWeighingBarcodeDetail()!=null)
				isProcessed=listener.onEmptyTrayBarcodeScanned(getWeighingBarcodeDetail());
		}
		
		return isProcessed;
	}

	/**
	 * @author jojesh-13.2
	 */
	public interface IPosEmptyTrayBarcodeScannerActionLitener extends IPosScannerActionLitener{
		
		public boolean onEmptyTrayBarcodeScanned(BeanWeighingBarcodeDtl dtl);
		
	}
	
}
