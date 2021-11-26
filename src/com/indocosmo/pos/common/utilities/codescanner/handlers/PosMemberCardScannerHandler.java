/**
 * 
 */
package com.indocosmo.pos.common.utilities.codescanner.handlers;

import com.indocosmo.pos.common.utilities.codescanner.enums.PosScannerPatternType;
import com.indocosmo.pos.common.utilities.codescanner.handlers.PosMemberCardScannerHandler.IPosMemberCodeScannerActionLitener;
import com.indocosmo.pos.common.utilities.codescanner.handlers.PosScannerHandler.IPosScannerActionLitener;

/**
 * @author jojesh-13.2
 *
 */
public class PosMemberCardScannerHandler extends PosScannerHandler<IPosMemberCodeScannerActionLitener> {

	/**
	 * @param type
	 */
	public PosMemberCardScannerHandler(IPosMemberCodeScannerActionLitener handler) {
		super(handler,PosScannerPatternType.MEMBER);
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.pos.common.utilities.scanner.PosScannerHandler#processScannedCode(com.indocosmo.pos.common.utilities.scanner.PosScannerHandler.IPosScnannerActionLitener, java.lang.String)
	 */
	@Override
	public boolean processScannedCode(String code) {
		
		boolean isProcessed=false;
		
		if(super.processScannedCode(code)){
		
			if(listener!=null)
				isProcessed=listener.onMemberCardScanned(getValues().get(1));
		}
		
		return isProcessed;
	}

	/**
	 * @author jojesh-13.2
	 *
	 */
	public interface IPosMemberCodeScannerActionLitener extends IPosScannerActionLitener{
		
		public boolean onMemberCardScanned(String cardNumber);
		
	}
	
}
