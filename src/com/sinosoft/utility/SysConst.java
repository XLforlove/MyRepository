package com.sinosoft.utility;

/**
 * *************************************************************** Program NAME:
 * 系统常量类 programmer: Ouyangsheng Create DATE: 2002.04.17 Create address: Beijing
 * Modify DATE: Modify address:
 * **************************************************************** 保存系统中的常量。
 * ****************************************************************
 */
public class SysConst
{
	/* 系统信息 */
	public static final int FAILURE = -1;

	public static final int SUCCESS = 0;

	public static final int NOTFOUND = 100;

	/* 系统变量 */
	public static final String EMPTY = null;

	public static final boolean CHANGECHARSET = false; // Unicode to GBK

	/* 信息分隔符 */
	public static final String PACKAGESPILTER = "|";

	public static final String RECORDSPLITER = "^";

	public static final String ENDOFPARAMETER = "^";

	public static final String EQUAL = "=";

	public static final String CONTAIN = "*";

	/* 查询显示设置 */
	public static final int MAXSCREENLINES = 10; // 每一页最大显示的行数

	public static final int MAXMEMORYPAGES = 20; // 内存中存储的最大的页数

	/* 设置信息 */
	public static final String ZERONO = "00000000000000000000"; // 对于没有号码的字段的默认值

	public static final String POOLINFO = "poolname";

	public static final String PARAMETERINFO = "parameterbuf";

	public static final String POOLTYPE = "pooltype";

	public static final String MAXSIZE = "maxsize";

	public static final String MINSIZE = "minsize";

	public static final String USERLOGPATH = "userlogpath";

	public static final String SYSLOGPATH = "syslogpath";

	public static final String COMP = "comp";

	public static final String ENCRYPT = "encrypt";

	public static final String MACFLAG = "macflag";

	public static final String SIGNFLAG = "signflag";

	public static final String SRC = "src";

	public static final String SND = "snd";

	public static final String RCV = "rcv";

	public static final String PRIOR = "prior";

	// 大批量数据查询时，使用的缓冲区大小
	public static final int FETCHCOUNT = 500;

	// 需要加密的表清单字符串
	public static final String JKTABLE = "LCCont-LDPerson-LCPol-LCAppnt-LCInsured-LAComCharge-LAWage-LPEdorApp-LCDuty-LCPrem-LCInsureAcc-LCBnf-LJAGet-LJAPay-LJSGet-LJSPay-LJTempFee-LLClaim-LCGet-";
}
