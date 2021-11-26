/**
 * 
 */
package com.indocosmo.pos.common.utilities.codescanner.handlers;

import com.indocosmo.pos.common.utilities.codescanner.bean.BeanWeighingBarcodeDtl;
import com.indocosmo.pos.common.utilities.codescanner.enums.PosScannerPatternType;
import com.indocosmo.pos.common.utilities.codescanner.handlers.PosScannerHandler.IPosScannerActionLitener;
import com.indocosmo.pos.common.utilities.codescanner.handlers.PosWMBarcodeScannerHandler.IPosWMBarcodeScannerActionLitener;

/**
 * @author jojesh-13.2
 *
 */
public class PosWMBarcodeScannerHandler extends PosWeighingBarcodeScannerHandler <IPosWMBarcodeScannerActionLitener> {



	/**
	 * @param actionHandler
	 */
	public PosWMBarcodeScannerHandler(
			IPosWMBarcodeScannerActionLitener actionHandler) {
		super(actionHandler,PosScannerPatternType.WEIGHING);
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.pos.common.utilities.scanner.PosScannerHandler#processScannedCode(com.indocosmo.pos.common.utilities.scanner.PosScannerHandler.IPosScnannerActionLitener, java.lang.String)
	 */
	@Override
	public boolean processScannedCode(String code) {
		
		boolean isProcessed=false;
		
		if(super.processScannedCode(code)){
			
			if(listener!=null && getWeighingBarcodeDetail()!=null)
				isProcessed=listener.onWMBarcodeScanned(getWeighingBarcodeDetail());
		}
		
		return isProcessed;
	}

	/**
	 * @author jojesh-13.2
	 *
	 */
	public interface IPosWMBarcodeScannerActionLitener extends IPosScannerActionLitener{
		
		public boolean onWMBarcodeScanned(BeanWeighingBarcodeDtl dtl);
		
	}
	
}
