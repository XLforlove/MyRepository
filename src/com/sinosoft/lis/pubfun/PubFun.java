package com.sinosoft.lis.pubfun;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Vector;

import com.sinosoft.utility.JdbcUrl;
import com.sinosoft.utility.Schema;
import com.sinosoft.utility.StrTool;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description:业务系统的公共业务处理函数 该类包含所有业务处理中的公共函数，和以前系统中的funpub.4gl
 * 文件相对应。在这个类中，所有的函数都采用Static的类型，所有需要的数据都是 通过参数传入的，在本类中不采用通过属性传递数据的方法。
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * @author YT
 * @version 1.0
 */
public class PubFun
{
	public PubFun()
	{}

	public static void main(String[] args)
	{
		// PubFun pubFun1t = new PubFun();
		String tStr = "1111.001";
		String tLimit;
		tLimit = PubFun.getInt(tStr);
		// tStr=PubFun1.CreateMaxNo("ProposalNo",tLimit) ;
		System.out.println(tLimit);
		System.out.println(PubFun.calInterval("2008-3-1", "2008-3-30", "D"));
	}

	/**
	 * 计算日期的函数 author: HST 参照日期指当按照年月进行日期的计算的时候，参考的日期，如下例，结果返回2002-03-31
	 * <p>
	 * <b>Example: </b>
	 * <p>
	 * <p>
	 * FDate tD=new FDate();
	 * <p>
	 * <p>
	 * Date baseDate =new Date();
	 * <p>
	 * <p>
	 * baseDate=tD.getDate("2000-02-29");
	 * <p>
	 * <p>
	 * Date comDate =new Date();
	 * <p>
	 * <p>
	 * comDate=tD.getDate("1999-12-31");
	 * <p>
	 * <p>
	 * int inteval=1;
	 * <p>
	 * <p>
	 * String tUnit="M";
	 * <p>
	 * <p>
	 * Date tDate =new Date();
	 * <p>
	 * <p>
	 * tDate=PubFun.calDate(baseDate,inteval,tUnit,comDate);
	 * <p>
	 * <p>
	 * System.out.println(tDate.toString());
	 * <p>
	 * <p>
	 * 未测试,请业务处理人员增加
	 * <p>
	 * @param baseDate 起始日期
	 * @param interval 时间间隔
	 * @param unit 时间间隔单位
	 * @param compareDate 参照日期
	 * @return Date类型变量
	 */
	public static Date calDate(Date baseDate, int interval, String unit, Date compareDate)
	{
		Date returnDate = null;

		GregorianCalendar mCalendar = new GregorianCalendar();
		mCalendar.setTime(baseDate);
		if (unit.equals("Y"))
		{
			mCalendar.add(Calendar.YEAR, interval);
		}
		if (unit.equals("M"))
		{
			mCalendar.add(Calendar.MONTH, interval);
		}
		if (unit.equals("D"))
		{
			mCalendar.add(Calendar.DATE, interval);
		}

		if (compareDate != null)
		{
			GregorianCalendar cCalendar = new GregorianCalendar();
			cCalendar.setTime(compareDate);

			int mYears = mCalendar.get(Calendar.YEAR);
			int mMonths = mCalendar.get(Calendar.MONTH);
			int cMonths = cCalendar.get(Calendar.MONTH);
			int cDays = cCalendar.get(Calendar.DATE);

			if (unit.equals("Y"))
			{
				cCalendar.set(mYears, cMonths, cDays);
				if (cCalendar.before(mCalendar))
				{
					mCalendar.set(mYears + 1, cMonths, cDays);
					returnDate = mCalendar.getTime();
				}
				else
				{
					returnDate = cCalendar.getTime();
				}
			}
			if (unit.equals("M"))
			{
				cCalendar.set(mYears, mMonths, cDays);
				if (cCalendar.before(mCalendar))
				{
					mCalendar.set(mYears, mMonths + 1, cDays);
					returnDate = mCalendar.getTime();
				}
				else
				{
					returnDate = cCalendar.getTime();
				}
			}
			if (unit.equals("D"))
			{
				returnDate = mCalendar.getTime();
			}
		}
		else
		{
			returnDate = mCalendar.getTime();
		}

		return returnDate;
	}

	/**
	 * 通过起始日期和终止日期计算以时间间隔单位为计量标准的时间间隔 author: HST
	 * <p>
	 * <b>Example: </b>
	 * <p>
	 * <p>
	 * 参照calInterval(String cstartDate, String cendDate, String
	 * unit)，前两个变量改为日期型即可
	 * <p>
	 * @param startDate 起始日期，Date变量
	 * @param endDate 终止日期，Date变量
	 * @param unit 时间间隔单位，可用值("Y"--年 "M"--月 "D"--日)
	 * @return 时间间隔,整形变量int
	 */
	public static int calInterval(Date startDate, Date endDate, String unit)
	{
		int interval = 0;

		GregorianCalendar sCalendar = new GregorianCalendar();
		sCalendar.setTime(startDate);
		int sYears = sCalendar.get(Calendar.YEAR);
		int sMonths = sCalendar.get(Calendar.MONTH);
		int sDays = sCalendar.get(Calendar.DAY_OF_MONTH);
		int sDaysOfYear = sCalendar.get(Calendar.DAY_OF_YEAR);
		System.out.println("sDaysOfYear is " + sDaysOfYear);

		GregorianCalendar eCalendar = new GregorianCalendar();
		eCalendar.setTime(endDate);
		int eYears = eCalendar.get(Calendar.YEAR);
		int eMonths = eCalendar.get(Calendar.MONTH);
		int eDays = eCalendar.get(Calendar.DAY_OF_MONTH);
		int eDaysOfYear = eCalendar.get(Calendar.DAY_OF_YEAR);
		System.out.println("eDaysOfYear is " + eDaysOfYear);

		if (unit.equals("Y"))
		{
			interval = eYears - sYears;
			if (eDaysOfYear < sDaysOfYear)
			{
				interval--;
			}
		}
		if (unit.equals("M"))
		{
			interval = eYears - sYears;
			interval = interval * 12;

			interval = eMonths - sMonths + interval;
			if (eDays < sDays)
			{
				interval--;
			}
		}
		if (unit.equals("D"))
		{
			interval = eYears - sYears;
			interval = interval * 365;

			interval = eDaysOfYear - sDaysOfYear + interval;

			// 处理润年
			int n = 0;
			eYears--;
			if (eYears > sYears)
			{
				int i = sYears % 4;
				if (i == 0)
				{
					sYears++;
					n++;
				}
				int j = (eYears) % 4;
				if (j == 0)
				{
					eYears--;
					n++;
				}
				n += (eYears - sYears) / 4;
			}
			if (eYears == sYears)
			{
				int i = sYears % 4;
				if (i == 0)
				{
					n++;
				}
			}
			interval += n;
		}
		return interval;
	}

	/**
	 * 通过起始日期和终止日期计算以时间间隔单位为计量标准的时间间隔 author: HST
	 * <p>
	 * <b>Example: </b>
	 * <p>
	 * <p>
	 * calInterval("2002-10-8", "2012-10-1", "Y") returns 10
	 * <p>
	 * @param startDate 起始日期，(String,格式："YYYY-MM-DD")
	 * @param endDate 终止日期，(String,格式："YYYY-MM-DD")
	 * @param unit 时间间隔单位，可用值("Y"--年 "M"--月 "D"--日)
	 * @return 时间间隔,整形变量int
	 */
	public static int calInterval(String cstartDate, String cendDate, String unit)
	{
		FDate fDate = new FDate();
		Date startDate = fDate.getDate(cstartDate);
		Date endDate = fDate.getDate(cendDate);
		if (fDate.mErrors.needDealError())
		{
			return 0;
		}

		int interval = 0;

		GregorianCalendar sCalendar = new GregorianCalendar();
		sCalendar.setTime(startDate);
		int sYears = sCalendar.get(Calendar.YEAR);
		int sMonths = sCalendar.get(Calendar.MONTH);
		int sDays = sCalendar.get(Calendar.DAY_OF_MONTH);
		int sDaysOfYear = sCalendar.get(Calendar.DAY_OF_YEAR);
		System.out.println("sDaysOfYear is " + sDaysOfYear);

		GregorianCalendar eCalendar = new GregorianCalendar();
		eCalendar.setTime(endDate);
		int eYears = eCalendar.get(Calendar.YEAR);
		int eMonths = eCalendar.get(Calendar.MONTH);
		int eDays = eCalendar.get(Calendar.DAY_OF_MONTH);
		int eDaysOfYear = eCalendar.get(Calendar.DAY_OF_YEAR);
		System.out.println("eDaysOfYear is " + eDaysOfYear);

		if (StrTool.cTrim(unit).equals("Y"))
		{
			interval = eYears - sYears;
			if (eDaysOfYear < sDaysOfYear)
			{
				interval--;
			}
		}
		if (StrTool.cTrim(unit).equals("M"))
		{
			interval = eYears - sYears;
			interval = interval * 12;

			interval = eMonths - sMonths + interval;
			if (eDays < sDays)
			{
				interval--;
			}
		}
		if (StrTool.cTrim(unit).equals("D"))
		{
			interval = eYears - sYears;
			interval = interval * 365;

			interval = eDaysOfYear - sDaysOfYear + interval;

			// 处理润年
			int n = 0;
			eYears--;
			if (eYears > sYears)
			{
				int i = sYears % 4;
				if (i == 0)
				{
					sYears++;
					n++;
				}
				int j = (eYears) % 4;
				if (j == 0)
				{
					eYears--;
					n++;
				}
				n += (eYears - sYears) / 4;
			}
			if (eYears == sYears)
			{
				int i = sYears % 4;
				if (i == 0)
				{
					n++;
				}
			}
			interval += n;
		}
		return interval;
	}

	/**
	 * 得到默认的JDBCUrl
	 * @return JDBCUrl
	 */
	public static JdbcUrl getDefaultUrl()
	{
		JdbcUrl tUrl = new JdbcUrl();
		return tUrl;
	}

	/**
	 * 将字符串补数,将sourString的<br>
	 * 后面</br>用cChar补足cLen长度的字符串,如果字符串超长，则不做处理
	 * <p>
	 * <b>Example: </b>
	 * <p>
	 * <p>
	 * RCh("Minim", "0", 10) returns "Minim00000"
	 * <p>
	 * @param sourString 源字符串
	 * @param cChar 补数用的字符
	 * @param cLen 字符串的目标长度
	 * @return 字符串
	 */
	public static String RCh(String sourString, String cChar, int cLen)
	{
		int tLen = sourString.length();
		int i, iMax;
		String tReturn = "";
		if (tLen >= cLen)
		{
			return sourString;
		}
		iMax = cLen - tLen;
		for (i = 0; i < iMax; i++)
		{
			tReturn = tReturn + cChar;
		}
		tReturn = sourString.trim() + tReturn.trim();
		return tReturn;
	}

	/**
	 * 将字符串补数,将sourString的<br>
	 * 前面</br>用cChar补足cLen长度的字符串,如果字符串超长，则不做处理
	 * <p>
	 * <b>Example: </b>
	 * <p>
	 * <p>
	 * LCh("Minim", "0", 10) returns "00000Minim"
	 * <p>
	 * @param sourString 源字符串
	 * @param cChar 补数用的字符
	 * @param cLen 字符串的目标长度
	 * @return 字符串
	 */
	public static String LCh(String sourString, String cChar, int cLen)
	{
		int tLen = sourString.length();
		int i, iMax;
		String tReturn = "";
		if (tLen >= cLen)
		{
			return sourString;
		}
		iMax = cLen - tLen;
		for (i = 0; i < iMax; i++)
		{
			tReturn = cChar + tReturn;
		}
		tReturn = tReturn.trim() + sourString.trim();
		return tReturn;
	}

	/**
	 * 得到当前系统日期 author: YT
	 * @return 当前日期的格式字符串,日期格式为"yyyy-MM-dd"
	 */
	public static String getCurrentDate()
	{
		String pattern = "yyyy-MM-dd";
		SimpleDateFormat df = new SimpleDateFormat(pattern);
		Date today = new Date();
		String tString = df.format(today);
		return tString;
	}

	/**
	 * 得到当前系统时间 author: YT
	 * @return 当前时间的格式字符串，时间格式为"HH:mm:ss"
	 */
	public static String getCurrentTime()
	{
		String pattern = "HH:mm:ss";
		SimpleDateFormat df = new SimpleDateFormat(pattern);
		Date today = new Date();
		String tString = df.format(today);
		return tString;
	}

	/**
	 * 得到流水号前导 author: YT
	 * @param comCode 机构代码
	 * @return 流水号的前导字符串
	 */
	public static String getNoLimit(String comCode)
	{
		comCode = comCode.trim();
		int tLen = comCode.length();
		if (tLen > 6)
		{
			comCode = comCode.substring(0, 6);
		}
		if (tLen < 6)
		{
			comCode = RCh(comCode, "0", 6);
		}
		String tString = "";
		tString = comCode + getCurrentDate().substring(0, 4);
		return tString;
	}

	/**
	 * 该函数得到c_Str中的第c_i个以c_Split分割的字符串
	 * @param c_Str 目标字符串
	 * @param c_i 位置
	 * @param c_Split 分割符
	 * @return 如果发生异常，则返回空
	 */
	public static String getStr(String c_Str, int c_i, String c_Split)
	{
		String t_Str1 = "", t_Str2 = "", t_strOld = "";
		int i = 0, i_Start = 0;
		t_Str1 = c_Str;
		t_Str2 = c_Split;
		i = 0;
		try
		{
			while (i < c_i)
			{
				i_Start = t_Str1.indexOf(t_Str2, 0);
				if (i_Start >= 0)
				{
					i = i + 1;
					t_strOld = t_Str1;
					t_Str1 = t_Str1.substring(i_Start + t_Str2.length(), t_Str1.length());
				}
				else
				{
					if (i != c_i - 1)
					{
						t_Str1 = "";
					}
					break;
				}
			}

			if (i_Start >= 0)
			{
				t_Str1 = t_strOld.substring(0, i_Start);
			}
		}
		catch (Exception ex)
		{
			t_Str1 = "";
		}
		return t_Str1;
	}

	/**
	 * 把数字金额转换为中文大写金额 author: HST
	 * @param money 数字金额(float)
	 * @return 中文大写金额(String)
	 */
	public static String getChnMoney(double money)
	{
		String ChnMoney = "";
		String s0 = "";

		if (money == 0.0)
		{
			ChnMoney = "零元整";
			return ChnMoney;
		}

		if (money < 0)
		{
			s0 = "负";
			money = money * (-1);
		}

		money = money * 100;

		// long iMoney = Math.round( money ); // 四舍五入
		Double D = new Double(money);
		long iMoney = D.longValue(); // 直接截取
		String sMoney = String.valueOf(iMoney);

		int nLen = sMoney.length();
		String sInteger = sMoney.substring(0, nLen - 2);

		String sDot = sMoney.substring(nLen - 2, nLen);
		String sFormatStr = PubFun.formatStr(sInteger);

		String s1 = PubFun.getChnM(sFormatStr.substring(0, 4), "亿");

		String s2 = PubFun.getChnM(sFormatStr.substring(4, 8), "万");

		String s3 = PubFun.getChnM(sFormatStr.substring(8, 12), "");

		String s4 = PubFun.getDotM(sDot);

		if (s1.length() > 0 && s1.substring(0, 1).equals("0"))
		{
			s1 = s1.substring(1, s1.length());
		}
		if (s1.length() > 0 && s1.substring(s1.length() - 1, s1.length()).equals("0") && s2.length() > 0
				&& s2.substring(0, 1).equals("0"))
		{
			s1 = s1.substring(0, s1.length() - 1);
		}
		if (s2.length() > 0 && s2.substring(s2.length() - 1, s2.length()).equals("0") && s3.length() > 0
				&& s3.substring(0, 1).equals("0"))
		{
			s2 = s2.substring(0, s2.length() - 1);
		}
		if (s4.equals("00"))
		{
			s4 = "";
			if (s3.length() > 0 && s3.substring(s3.length() - 1, s3.length()).equals("0"))
			{
				s3 = s3.substring(0, s3.length() - 1);
			}
		}
		if (s3.length() > 0 && s3.substring(s3.length() - 1, s3.length()).equals("0") && s4.length() > 0
				&& s4.substring(0, 1).equals("0"))
		{
			s3 = s3.substring(0, s3.length() - 1);
		}
		if (s4.length() > 0 && s4.substring(s4.length() - 1, s4.length()).equals("0"))
		{
			s4 = s4.substring(0, s4.length() - 1);
		}
		if (s3.equals("0"))
		{
			s3 = "";
			s4 = "0" + s4;
		}

		ChnMoney = s0 + s1 + s2 + s3 + "元" + s4;
		if (ChnMoney.substring(0, 1).equals("0"))
		{
			ChnMoney = ChnMoney.substring(1, ChnMoney.length());
		}
		for (int i = 0; i < ChnMoney.length(); i++)
		{
			if (ChnMoney.substring(i, i + 1).equals("0"))
			{
				ChnMoney = ChnMoney.substring(0, i) + "零" + ChnMoney.substring(i + 1, ChnMoney.length());
			}
		}

		if (sDot.substring(1, 2).equals("0"))
		{
			ChnMoney += "整";
		}

		return ChnMoney;
	}

	private static String getDotM(String sIn)
	{
		String sMoney = "";
		if (!sIn.substring(0, 1).equals("0"))
		{
			sMoney += getNum(sIn.substring(0, 1)) + "角";
		}
		else
		{
			sMoney += "0";
		}
		if (!sIn.substring(1, 2).equals("0"))
		{
			sMoney += getNum(sIn.substring(1, 2)) + "分";
		}
		else
		{
			sMoney += "0";
		}

		return sMoney;
	}

	private static String getChnM(String strUnit, String digit)
	{
		String sMoney = "";
		boolean flag = false;

		if (strUnit.equals("0000"))
		{
			sMoney += "0";
			return sMoney;
		}
		if (!strUnit.substring(0, 1).equals("0"))
		{
			sMoney += getNum(strUnit.substring(0, 1)) + "仟";
		}
		else
		{
			sMoney += "0";
			flag = true;
		}
		if (!strUnit.substring(1, 2).equals("0"))
		{
			sMoney += getNum(strUnit.substring(1, 2)) + "佰";
			flag = false;
		}
		else
		{
			if (flag == false)
			{
				sMoney += "0";
				flag = true;
			}
		}
		if (!strUnit.substring(2, 3).equals("0"))
		{
			sMoney += getNum(strUnit.substring(2, 3)) + "拾";
			flag = false;
		}
		else
		{
			if (flag == false)
			{
				sMoney += "0";
				flag = true;
			}
		}
		if (!strUnit.substring(3, 4).equals("0"))
		{
			sMoney += getNum(strUnit.substring(3, 4));
		}
		else
		{
			if (flag == false)
			{
				sMoney += "0";
				flag = true;
			}
		}

		if (sMoney.substring(sMoney.length() - 1, sMoney.length()).equals("0"))
		{
			sMoney = sMoney.substring(0, sMoney.length() - 1) + digit.trim() + "0";
		}
		else
		{
			sMoney += digit.trim();
		}
		return sMoney;
	}

	private static String formatStr(String sIn)
	{
		int n = sIn.length();
		String sOut = sIn;
		// int i = n % 4;

		for (int k = 1; k <= 12 - n; k++)
		{
			sOut = "0" + sOut;
		}
		return sOut;
	}

	private static String getNum(String value)
	{
		String sNum = "";
		Integer I = new Integer(value);
		int iValue = I.intValue();
		switch (iValue)
		{
			case 0:
				sNum = "零";
				break;
			case 1:
				sNum = "壹";
				break;
			case 2:
				sNum = "贰";
				break;
			case 3:
				sNum = "叁";
				break;
			case 4:
				sNum = "肆";
				break;
			case 5:
				sNum = "伍";
				break;
			case 6:
				sNum = "陆";
				break;
			case 7:
				sNum = "柒";
				break;
			case 8:
				sNum = "捌";
				break;
			case 9:
				sNum = "玖";
				break;
		}
		return sNum;
	}

	/*
	 * 如果一个字符串数字中小数点后全为零，则去掉小数点及零
	 */
	public static String getInt(String Value)
	{
		String result = "";
		boolean mflag = true;
		int m = 0;
		m = Value.lastIndexOf(".");
		if (m == -1)
		{
			result = Value;
		}
		else
		{
			for (int i = m + 1; i <= Value.length() - 1; i++)
			{
				if (Value.charAt(i) != '0')
				{
					result = Value;
					mflag = false;
					break;
				}
			}
			if (mflag == true)
			{
				result = Value.substring(0, m);
			}
		}
		return result;
	}

	// 得到近似值
	public static float getApproximation(float aValue)
	{
		if (java.lang.Math.abs(aValue) <= 0.01)
		{
			aValue = 0;
		}
		return aValue;
	}

	/**
	 * 根据输入标记为间隔符号，拆分字符串
	 * @param strMain
	 * @param strDelimiters
	 * @return 数组，失败返回NULL
	 */
	public static String[] split(String strMain, String strDelimiters)
	{
		int i;
		int intIndex = 0; // 记录分隔符位置，以取出子串
		Vector vResult = new Vector(); // 存储子串的数组
		String strSub = ""; // 存放子串的中间变量

		strMain = strMain.trim();

		// 若主字符串比分隔符串还要短的话,则返回空字符串
		if (strMain.length() <= strDelimiters.length())
		{
			System.out.println("分隔符串长度大于等于主字符串长度，不能进行拆分！");
			return null;
		}

		// 取出第一个分隔符在主串中的位置
		intIndex = strMain.indexOf(strDelimiters);

		// 在主串中找不到分隔符
		if (intIndex == -1)
		{
			String[] arrResult =
			{ strMain };
			return arrResult;
		}

		// 分割主串到数组中
		while (intIndex != -1)
		{
			strSub = strMain.substring(0, intIndex);
			if (intIndex != 0)
			{
				vResult.add(strSub);
			}
			else
			{
				// break;
				vResult.add("");
			}

			strMain = strMain.substring(intIndex + strDelimiters.length()).trim();
			intIndex = strMain.indexOf(strDelimiters);
		}

		// 如果最末不是分隔符，取最后的字符串
		if (!strMain.equals("") && strMain != null)
		{
			vResult.add(strMain);
		}

		String[] arrResult = new String[vResult.size()];
		for (i = 0; i < vResult.size(); i++)
		{
			arrResult[i] = (String) vResult.get(i);
		}

		return arrResult;
	}

	/**
	 * 设置PreparedStatement 绑定的参数 <br>
	 * String[][] arrs = new String[1][3];<br>
	 * tArr[0][0] = String.valueOf(Schema.TYPE_CHAR); 数据类型<br>
	 * tArr[0][1] = "sex"; 参数值<br>
	 * tArr[0][2] = "6"; 当字段为Schema.TYPE_CHAR类型时须设置字段长度<br>
	 * @throws SQLException
	 */
	public static void setPrepParam(PreparedStatement pstmt, String[][] arrs) throws SQLException
	{

		if (arrs != null && arrs.length > 0 && pstmt != null)
		{
			int tSize = arrs.length;
			int type = -1;
			for (int i = 0; i < tSize; i++)
			{
				if (arrs[i][0] == null)
				{
					System.out.println("SQLType 不存在！");
				}
				else
				{
					type = Integer.parseInt(arrs[i][0]);

					switch (type)
					{
						case Schema.TYPE_STRING:
							pstmt.setString(i + 1, arrs[i][1]);
							break;
						// PrepareStatement统一用DOUBLE设置数字类型
						case Schema.TYPE_DOUBLE:
							pstmt.setDouble(i + 1, Double.valueOf(arrs[i][1]).longValue());
							break;
						case Schema.TYPE_DATE:
							pstmt.setDate(i + 1, ((java.sql.Date.valueOf(arrs[i][1]))));
							break;
						case Schema.TYPE_CHAR:
							pstmt.setString(i + 1, StrTool.space(arrs[i][1], Integer.parseInt(arrs[i][2])));
						default:
							System.out.println("SQLType " + type + "  不存在！");
							break;
					};
				}
			}
		}
	}
}
