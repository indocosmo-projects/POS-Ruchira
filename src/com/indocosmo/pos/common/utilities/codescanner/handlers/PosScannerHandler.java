/**
 * 
 */
package com.indocosmo.pos.common.utilities.codescanner.handlers;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.indocosmo.pos.common.utilities.codescanner.enums.PosScannerPatternType;
import com.indocosmo.pos.common.utilities.codescanner.handlers.PosScannerHandler.IPosScannerActionLitener;
import com.indocosmo.pos.data.providers.shopdb.PosScannerPatternProvider;


/**
 * @author jojesh-13.2
 *
 */
public abstract class PosScannerHandler<L extends IPosScannerActionLitener> {

	protected PosScannerPatternType scannerType;
	protected Pattern pattern;
	protected Matcher patternMatcher;
	protected List<String> values;
	protected L listener;
	
	public PosScannerHandler(L actionHandler){
		
	}

	/**
	 * @param type
	 */
	protected PosScannerHandler(L actionHandler, PosScannerPatternType type){

		listener=actionHandler;
		scannerType=type;
		pattern = Pattern.compile(getPattern());
	}

	/**
	 * Performs the action based on value passed
	 * @param value: The scanned value
	 * @return
	 */
	public boolean processScannedCode(String code){

		values=null;

   
		if(isCodeMatched(code)){

			if(isCodeMatched(code)){

				values=new ArrayList<String>();
				for(int index=0;index<=patternMatcher.groupCount();index++)
					values.add(patternMatcher.group(index));
			}
		}

		return (values!=null && values.size()>0);
	}

	/**
	 * @param pattern
	 * @return
	 */
	protected boolean isCodeMatched(String code){

		patternMatcher = pattern.matcher(code);

		return patternMatcher.find();
	}

	/**
	 * @param code
	 * @return
	 */
	protected List<String> getValues(){

		return values;
	}

	/**
	 * @return
	 */
	protected String getPattern(){

		return PosScannerPatternProvider.getInstance().getPattern(scannerType);
	}

	/**
	 *Action Listener for scanner handler
	 *
	 */
	public interface IPosScannerActionLitener{
			
		public boolean defaultEmptyCodeScanned();
	}

	/**
	 * @param listener the listener to set
	 */
	public void setListener(L listener) {
		this.listener = listener;
	}
}
