/**
 * 
 */
package com.indocosmo.pos.terminal.devices.eftpos;

/**
 * @author jojesh
 *
 */
public final class PosDeviceEFTConstants {
	
	public static final int FS = 0x1C;
	public static final int DLE = 0x10;
	public static final int STX = 0x02;
	public static final int ETX = 0x03;
	public static final int ENQ = 0x05;
	public static final int ACK = 0x06;
	public static final int NAK = 0x15;
	public static final int COMMA = 0x2C;
	
	public static final int MAX_BYTE_ARRAY_LENGTH=10*1024;
	public static final int MAX_RETRY_COUNT=3;
	public static final int MAX_RESPONSE_WAIT_TIME=3000;
	
	public static final int DSP_DISP_MSG_PART=3;
	
	public static final int SIG_DISP_MSG_PART=3;
	public static final int ERR_DISP_MSG_PART=3;
	
	public static final int EFT_MSG_ID_PART=0;
	public static final int EFT_MSG_TYPE_PART=1;
	public static final int EFT_MERCHANT_ID_PART=2;
	
	public static final int PUR_CARD_AMO_PART=3;
	public static final int PUR_CASH_AMO_PART=4;
	public static final int PUR_STAT_PART=5;
	public static final int PUR_DISP_MSG_PART=6;
	public static final int PUR_AUTH_PART=7;
	public static final int PUR_TXN_REF_NO_PART=8;
	public static final int PUR_CARD_NO_PART=9;
	public static final int PUR_ACC_PART=10;
	public static final int PUR_OPT_DATAT_1_PART=11;
	public static final int PUR_OPT_DATAT_2_PART=12;
	
	public static final int MAN_CARD_AMO_PART=3;
	public static final int MAN_STAT_PART=4;
	public static final int MAN_DISP_MSG_PART=5;
	public static final int MAN_BANK_REF_PART=6;
	public static final int MAN_TXN_REF_NO_PART=7;
	public static final int MAN_CARD_NO_PART=8;
	public static final int MAN_ACC_PART=9;
	public static final int MAN_OPT_DATAT_1_PART=10;
	public static final int MAN_OPT_DATAT_2_PART=11;
	
	public static final int LOGON_STAT_PART=3;
	public static final int LOGON_DISP_MSG_PART=4;
	public static final int LOGON_OPT_DATAT_1_PART=5;
	
	public static final int POL_STAT_PART=3;
//	public static final int POL_DISP_MSG_PART=4;
//	public static final int POL_SRNO_PART=5;
}
