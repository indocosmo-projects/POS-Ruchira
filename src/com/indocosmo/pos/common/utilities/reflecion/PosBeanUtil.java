/**
 * 
 */
package com.indocosmo.pos.common.utilities.reflecion;

import java.lang.reflect.Method;

import com.indocosmo.pos.common.PosLog;

/**
 * @author jojesh
 *
 */
public class PosBeanUtil {

	public static void copyProperties(Object source, Object destination) throws Exception{

		Method m[] = source.getClass().getMethods();
		for(int i=0;i<m.length;i++) {
			try {
				
				String name = m[i].getName();
				if(name.startsWith("get") || name.startsWith("is")) {
				
					Class rtype = m[i].getReturnType();
					String setter = name.replaceFirst("^(get|is)","set");
					Class s = destination.getClass();
					Method method = s.getMethod(setter,rtype);
					Object[] args = new Object[1];
					args[0] = m[i].invoke(source);
					method.invoke(destination,args[0]);
				}
				
			} catch(NoSuchMethodException nse){
				
				PosLog.debug(nse);
			}
		}
	}
}
