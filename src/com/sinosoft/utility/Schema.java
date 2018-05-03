package com.sinosoft.utility;

/**
 * <p>
 * ClassName: Schema �ӿ�
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

	// �����ֻ���������ֵ
	public String getV(String FCode);

	public String getV(int nIndex);

	// �����ֻ����������ֶε����ͣ���Schema.TYPE_STRING
	public int getFieldType(String strFieldName);

	public int getFieldType(int nFieldIndex);

	// �õ��ֶ���
	public int getFieldCount();

	// ���ֺ���������
	public int getFieldIndex(String strFieldName);

	public String getFieldName(int nFieldIndex);

	// ������������
	public boolean setV(String strFieldName, String strFieldValue);
}