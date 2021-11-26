/**
 * 
 */
package com.indocosmo.pos.common.utilities.codescanner.handlers;

import java.util.List;

import com.indocosmo.pos.common.utilities.codescanner.bean.BeanWeighingBarcodeDtl;
import com.indocosmo.pos.common.utilities.codescanner.bean.BeanWeighingBarcodeDtl.MeasuringType;
import com.indocosmo.pos.common.utilities.codescanner.enums.PosScannerPatternType;
import com.indocosmo.pos.common.utilities.codescanner.handlers.PosScannerHandler.IPosScannerActionLitener;
import com.indocosmo.pos.common.utilities.codescanner.handlers.PosWeighingBarcodeScannerHandler.IPosWMBarcodeScannerActionLitener;

/**
 * @author jojesh-13.2
 *
 */
public abstract class PosWeighingBarcodeScannerHandler<T extends IPosScannerActionLitener> extends PosScannerHandler<T> {

	private final static int WM_COUNT_VALUE=0;
	private final static int WM_WEIGHING_VALUE=1;

	/**
	 * @param actionHandler
	 */
	public PosWeighingBarcodeScannerHandler(
			T actionHandler,PosScannerPatternType type) {
		super(actionHandler,type);
	}
	
	private BeanWeighingBarcodeDtl weighingBarcodeDtl;
	/**
	 * @return
	 */
	protected BeanWeighingBarcodeDtl getWeighingBarcodeDetail(){
		
		return weighingBarcodeDtl;
	}

	/* (non-Javadoc)
	 * @see com.indocosmo.pos.common.utilities.scanner.PosScannerHandler#processScannedCode(com.indocosmo.pos.common.utilities.scanner.PosScannerHandler.IPosScnannerActionLitener, java.lang.String)
	 */

	@Override
	public boolean processScannedCode(String code) {
		
		boolean isProcessed=false;
		weighingBarcodeDtl=null;
		if(super.processScannedCode(code)){
		
			weighingBarcodeDtl=new BeanWeighingBarcodeDtl();
			List<String> values=getValues();
			final int measuringType=((values.get(2)==null || values.get(2).trim().equals(""))?WM_WEIGHING_VALUE:Integer.parseInt(values.get(2)));
			weighingBarcodeDtl.setMeasuringType(( measuringType==WM_COUNT_VALUE)?MeasuringType.Count:MeasuringType.Weighing);
			weighingBarcodeDtl.setCode(values.get(1));
			final double value=Double.parseDouble(values.get(3));
			weighingBarcodeDtl.setValue((weighingBarcodeDtl.getMeasuringType()== MeasuringType.Weighing?value/1000:value));
			isProcessed=true;
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
