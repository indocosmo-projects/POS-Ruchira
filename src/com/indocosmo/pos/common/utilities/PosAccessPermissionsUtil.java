/**
 * 
 */
package com.indocosmo.pos.common.utilities;

import javax.swing.RootPaneContainer;

import com.indocosmo.pos.common.PosEnvSettings;
import com.indocosmo.pos.data.beans.BeanAccessLog;
import com.indocosmo.pos.data.beans.BeanOrderHeader;
import com.indocosmo.pos.data.beans.BeanUser;
import com.indocosmo.pos.data.providers.shopdb.PosAccessLogProvider;
import com.indocosmo.pos.data.providers.shopdb.PosSystemFunctionsProvider;
import com.indocosmo.pos.data.providers.shopdb.PosUserGroupFunctionsProvider;
import com.indocosmo.pos.forms.PosOrderRetrieveForm;
import com.indocosmo.pos.forms.PosUserAuthenticateForm;
import com.indocosmo.pos.forms.components.buttons.PosButton;
import com.indocosmo.pos.forms.components.buttons.listners.IPosButtonListner;
import com.indocosmo.pos.forms.components.buttons.listners.PosButtonListnerAdapter;
import com.indocosmo.pos.forms.listners.IPosMessageBoxFormListner;
import com.indocosmo.pos.forms.messageboxes.PosMessageBoxForm.MessageBoxButtonTypes;
import com.indocosmo.pos.forms.messageboxes.PosMessageBoxForm.MessageBoxResults;
import com.indocosmo.pos.forms.messageboxes.listners.PosMessageBoxFormListnerAdapter;

/**
 * @author deepak
 *
 */
public final class PosAccessPermissionsUtil {
	
	/**
	 * @param parent
	 * @param UserGroupId
	 * @param functionCode
	 * @return
	 */
	public static boolean validateAccess(RootPaneContainer parent,int UserGroupId, String functionCode){
//		boolean valid = true;
//		PosSystemFunctionsProvider SystemFunctionsProvider = new PosSystemFunctionsProvider();
//		PosUserGroupFunctionsProvider userGroupFunctionsProvider = new PosUserGroupFunctionsProvider();
//		int functionID = SystemFunctionsProvider.getSystemFunctionIdByCode(functionCode);
//		if(!userGroupFunctionsProvider.isValid(UserGroupId, functionID)){
//			valid = false;
//			PosFormUtil.showErrorMessageBox(parent, "Sorry, you do not have permission to use this function.");
//		}
//		return valid;
		return validateAccess(parent,UserGroupId,functionCode, false);
		
	}
	
	/**
	 * @param parent
	 * @param UserGroupId
	 * @param functionCode
	 * @param isSilent
	 * @return
	 */
	public static boolean validateAccess(RootPaneContainer parent,int UserGroupId, String functionCode, boolean isSilent){
		
		boolean valid = true;
		PosSystemFunctionsProvider SystemFunctionsProvider = new PosSystemFunctionsProvider();
		PosUserGroupFunctionsProvider userGroupFunctionsProvider = new PosUserGroupFunctionsProvider();
		int functionID = SystemFunctionsProvider.getSystemFunctionIdByCode(functionCode);
		if(!userGroupFunctionsProvider.isValid(UserGroupId, functionID)){
			valid = false;
			if(!isSilent)
				PosFormUtil.showErrorMessageBox(parent, "Sorry, you do not have permission to use this function.");
		}
		return valid;
	}
	
	/**
	 * @param parent
	 * @param validateCurentUser
	 * @param module
	 * @return
	 */
	public static BeanUser validateAccess(RootPaneContainer parent, boolean validateCurentUser, String module){
		
		boolean isAllowed=false;
		
		BeanUser curUser=null;

		/*
		 * Validate the current user
		 */
		if(validateCurentUser){
			
			curUser=PosEnvSettings.getInstance()
					.getCashierShiftInfo().getCashierInfo();
			if(validateCurentUser && curUser!=null&&PosAccessPermissionsUtil.validateAccess(parent,curUser.getUserGroupId(), module,true)){

				isAllowed=true;
			}
		}
		/*
		 * if current user is not permitted get new user
		 */
		if(!isAllowed){

			PosUserAuthenticateForm loginForm=new PosUserAuthenticateForm("Authenticate");
			PosFormUtil.showLightBoxModal(parent,loginForm);
			curUser=loginForm.getUser();
			if(curUser!=null&&PosAccessPermissionsUtil.validateAccess(parent,curUser.getUserGroupId(), module)){
				
				BeanAccessLog accessLog= new BeanAccessLog();
				accessLog.setFunctionName(module);
				accessLog.setUserName(curUser.getName());
				accessLog.setAccessTime(PosDateUtil.getDateTime());
				final PosAccessLogProvider accesslogProvider = new PosAccessLogProvider();
				accesslogProvider.updateAccessLog(accessLog);
				isAllowed=true;
			}
		}
		
		return (isAllowed)?curUser:null;
	} 
	

	/**
	 * @param parent
	 * @param orderItem
	 * @param askForPermission
	 * @return
	 */
public static MessageBoxResults checkEditAuthenticationOfBilledOrder( RootPaneContainer parent,BeanOrderHeader orderItem, boolean askForPermission){
		
	MessageBoxResults result=MessageBoxResults.Yes; 
		String module="printed_bill_edit";
		
		if(!PosEnvSettings.getInstance().getUISetting().isBilledItemEditAuthenticationRequired() 
				|| orderItem.getTotalPrintCount()==0)
			result= MessageBoxResults.Yes;
		else
			result=checkEditAuthentication(parent,orderItem,askForPermission,module);
		return result;
}
	/**
	 * @param parent
	 * @param orderItem
	 * @param askForPermission
	 * @return
	 */
	public static MessageBoxResults checkEditAuthentication ( RootPaneContainer parent,BeanOrderHeader orderItem, boolean askForPermission,String module){
		
	
		MessageBoxResults result;
		final BeanUser curUser=PosEnvSettings.getInstance().getCashierShiftInfo().getCashierInfo();
	 	if(curUser==null)
			result=MessageBoxResults.No;
		else if(PosAccessPermissionsUtil.validateAccess(parent,curUser.getUserGroupId(), module,true))
			result=MessageBoxResults.Yes;
		else if (!askForPermission ){
			PosFormUtil.showErrorMessageBox(parent, "You don't have the enough right to edit this order.");
			result=MessageBoxResults.No;
		}
		else{
			 
			if (PosAccessPermissionsUtil.validateAccess(parent, false, module)!=null)
				result=MessageBoxResults.Yes;
			else
				result=MessageBoxResults.No;
		}

			
	
		return result;
	}
 
}
