package com.sinosoft.utility;

/**
 * <p>
 * ClassName: Schema 接口
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: sinosoft
 * </p>
 * @author: HST
 * @version: 1.0
 * @date: 2002-06-17
 */
public interface Schema
{
	public static int TYPE_NOFOUND = -1; //

	public static int TYPE_MIN = -1; // the minimum value of type

	public static int TYPE_STRING = 0; // java.lang.String

	public static int TYPE_DATE = 1; // java.util.Date

	public static int TYPE_FLOAT = 2; // float

	public static int TYPE_INT = 3; // int

	public static int TYPE_DOUBLE = 4; // double

	public static int TYPE_MAX = 5; // the maximum value of type
	
	public static int TYPE_CHAR = 6; // char

	public String[] getPK();

	// 按名字或索引返回值
	public String getV(String FCode);

	public String getV(int nIndex);

	// 按名字或索引返回字段的类型，如Schema.TYPE_STRING
	public int getFieldType(String strFieldName);

	public int getFieldType(int nFieldIndex);

	// 得到字段数
	public int getFieldCount();

	// 名字和索引互查
	public int getFieldIndex(String strFieldName);

	public String getFieldName(int nFieldIndex);

	// 按照名字设置
	public boolean setV(String strFieldName, String strFieldValue);
}