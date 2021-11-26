/**
 * 
 */
package com.indocosmo.pos.common.utilities.codescanner.handlers;

import com.indocosmo.pos.common.utilities.codescanner.bean.BeanWeighingBarcodeDtl;
import com.indocosmo.pos.common.utilities.codescanner.enums.PosScannerPatternType;
import com.indocosmo.pos.common.utilities.codescanner.handlers.PosItemTrayBarcodeScannerHandler.IPosItemTrayBarcodeScannerActionLitener;
import com.indocosmo.pos.common.utilities.codescanner.handlers.PosScannerHandler.IPosScannerActionLitener;
 

/**
 * @author jojesh-13.2
 *
 */
public class PosItemTrayBarcodeScannerHandler extends PosWeighingBarcodeScannerHandler<IPosItemTrayBarcodeScannerActionLitener> {



	/**
	 * @param actionHandler
	 */
	public PosItemTrayBarcodeScannerHandler(
			IPosItemTrayBarcodeScannerActionLitener actionHandler) {
		super(actionHandler,PosScannerPatternType.ITEMTRAY);
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.pos.common.utilities.scanner.PosScannerHandler#processScannedCode(com.indocosmo.pos.common.utilities.scanner.PosScannerHandler.IPosScnannerActionLitener, java.lang.String)
	 */
	@Override
	public boolean processScannedCode(String code) {
		
		boolean isProcessed=false;
		
		if(super.processScannedCode(code)){
		
			if(listener!=null && getWeighingBarcodeDetail()!=null)
				isProcessed=listener.onItemTrayBarcodeScanned(getWeighingBarcodeDetail());
		}
		
		return isProcessed;
	}

	/**
	 * @author jojesh-13.2
	 */
	public interface IPosItemTrayBarcodeScannerActionLitener extends IPosScannerActionLitener{
		
		public boolean onItemTrayBarcodeScanned(BeanWeighingBarcodeDtl dtl);
		
	}
	
}
