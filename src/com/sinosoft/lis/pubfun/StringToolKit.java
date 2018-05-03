package com.sinosoft.lis.pubfun;

public class StringToolKit
{

	public StringToolKit()
	{}

	public static String replaceStr(String strExpression, String strFind, String strReplaceWith)
	{
		String strReturn = null;
		String strTemp = null;
		int intIndex;
		strTemp = strExpression;
//		int i = 0;
		while ((intIndex = strTemp.indexOf(strFind)) > -1)
		{
			strReturn = strReturn + strTemp.substring(0, intIndex) + strReplaceWith;
			strTemp = strTemp.substring(intIndex + strFind.length(), strTemp.length());
		}
		strReturn = strReturn + strTemp;
		return strReturn;
	}
}
