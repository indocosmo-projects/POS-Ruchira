package com.indocosmo.pos.common.utilities;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.swing.text.JTextComponent;

import com.indocosmo.pos.common.PosLog;
import com.indocosmo.pos.data.beans.BeanCashierShift;
import com.indocosmo.pos.forms.PosSoftKeyPadForm;
import com.indocosmo.pos.forms.components.keypads.listners.PosSoftKeypadAdapter;
import com.indocosmo.pos.forms.listners.IPosNumKeyPadFormListner;

public final class PosPasswordUtil {
	
	private static boolean mValid;
	private static boolean mDiscountValid;

	public static synchronized String encrypt(String plaintext)  {
		return encrypt(plaintext,"MD5", "UTF-8");
	}

	public static synchronized String encrypt(String plaintext,
			String algorithm, String encoding) {
		MessageDigest msgDigest = null;
		String hashValue = null;
		try {
			msgDigest = MessageDigest.getInstance(algorithm);
			msgDigest.reset();
			msgDigest.update(plaintext.getBytes(encoding));
			byte rawByte[] = msgDigest.digest();
			BigInteger bigInt = new BigInteger(1,rawByte);
			hashValue = bigInt.toString(16);
			while(hashValue.length() < 32 ){
				hashValue = "0"+hashValue;
				}
		} catch (NoSuchAlgorithmException e) {
			PosLog.write("PosPasswordUtil", "encrypt", "No Such Algorithm Exists");
		} catch (UnsupportedEncodingException e) {
			PosLog.write("PosPasswordUtil", "encrypt", "The Encoding Is Not Supported");
		}
		return hashValue;
	}
	
	/**
	 * Compare the given passwords.
	 * @param pwdToCheck the password entered by the user. Which needs to be encrypted before comparing.
	 * @param password 
	 * @return
	 */
	public static boolean comparePassword(String pwdToCheck, String password){
		return (password==null || password.equals(""))?true:encrypt(pwdToCheck).equals(password);
	}
	
	public static boolean getValidatePassword(final BeanCashierShift cashierShift){
		
		mValid= false;
		PosFormUtil.showAcceptPasswordUI(null, new IPosNumKeyPadFormListner() {

			@Override
			public void onValueChanged(String value) {
				final String mPassword=encrypt(value);
				if(!cashierShift.getCashierInfo().getPassword().equals(mPassword)){
					PosFormUtil.showErrorMessageBox(null,"Invalid user id or password.");
					mValid =false;
				}else
					mValid=true;
			}

			@Override
			public void onValueChanged(JTextComponent target, String oldValue) {
				// TODO Auto-generated method stub

			}
		});
		return mValid;
	}
//	private static String mValidPassword;
//	public static String getPassword(){
//		
//		mValidPassword="";
//		PosFormUtil.showNumKeyPad("Password ?",null,null,new IPosNumKeyPadFormListner() {
//
//			@Override
//			public void onValueChanged(String value) {
//				mValidPassword=encrypt(value);
//			}
//
//			@Override
//			public void onValueChanged(JTextComponent target, String oldValue) {
//				// TODO Auto-generated method stub
//
//			}
//		},null,null,null,null,true,true);
//		
//		return mValidPassword;
//	}
	
	public static boolean getValidateDiscountPassword(final String disPassword){
		mDiscountValid=false;
		PosSoftKeyPadForm mSoftKeyPadForm=new PosSoftKeyPadForm();
		mSoftKeyPadForm.setListner(new PosSoftKeypadAdapter() {
			@Override
			public void onAccepted(String password) {
				
				if(password!=null&&!password.isEmpty()){
					mDiscountValid = password.equals(disPassword);
				}
			}
		});
		mSoftKeyPadForm.setTitle("Enter password");
		PosFormUtil.showLightBoxModal(null,mSoftKeyPadForm);
		return mDiscountValid;
	}
}
