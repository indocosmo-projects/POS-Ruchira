/**
 * 
 */
package com.indocosmo.pos.data.beans;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * @author jojesh
 *
 */
public class BeanItemPromotion extends BeanDiscount implements  Cloneable{

	public enum WeekDay{
		SUN("sun"),
		MON("mon"),
		TUE("tue"),
		WED("wed"),
		THU("thu"),
		FRI("fri"),
		SAT("sat");

		private static final Map<String,WeekDay> mLookup=new HashMap<String,WeekDay>();

		static {
			for(WeekDay wd:EnumSet.allOf(WeekDay.class))
				mLookup.put(wd.getCode(), wd);
		}

		private String mCode;
		private WeekDay(String code){
			mCode=code;
		}

		public String getCode(){
			return mCode;
		}

		public WeekDay get(String code){
			return mLookup.get(code.toLowerCase());
		}
	}

	private String mTimeFrom;
	private String mTimeTo;
	private ArrayList<WeekDay> mWeekDays=null;

	/**
	 * 
	 */
	public BeanItemPromotion() {
		setIsPromotion(true);
	}

	public String getTimeFrom() {
		return mTimeFrom;
	}

	public void setTimeFrom(String timeFrom) {
		this.mTimeFrom = timeFrom;
	}

	public String getTimeTo() {
		return mTimeTo;
	}

	public void setTimeTo(String timeTo) {
		this.mTimeTo = timeTo;
	}

	public ArrayList<WeekDay> getWeekDays() {
		return mWeekDays;
	}

	public void setWeekDays(ArrayList<WeekDay> weekDays) {
		this.mWeekDays = weekDays;
	}

	public BeanItemPromotion clone() {
		BeanItemPromotion cloneObject=null;
		//		try {
		cloneObject=(BeanItemPromotion) super.clone();
		//		} catch (CloneNotSupportedException e) {
		//			PosLog.write(this,"clone",e); 
		//		}
		return cloneObject;
	}
}
