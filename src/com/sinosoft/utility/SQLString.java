/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.utility;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * ClassName: SQLString
 * </p>
 * <p>
 * Description: 根据传入的schema对象生成相应的数据库操作sql
 * </p>
 * @author HST、ZhuXF
 * @version 1.1
 * @modify 2007-10-17
 * @depict 人生五十載，去事恍如夢幻，普天之下，豈有長生不滅者！
 */
public class SQLString
{
	// @Constructor
	public SQLString(String t)
	{
		TableName = t;
	}

	// @Field
	private String TableName;

	private StringBuffer WherePart;

	private StringBuffer PKWherePart;

	private StringBuffer UpdPart;

	private StringBuffer InsPart;

	private StringBuffer mSql = new StringBuffer(256);;

	private static final String mark = "'";

	private String[][] arr = null;

	/**
	 * 获得数据库操作sql
	 * @param flag int
	 * @param s Schema
	 */
	public void setSQL(int flag, Schema s)
	{
		arr = null;// 重设
		switch (flag)
		{
			case 1:
				InsPart = new StringBuffer(256);
				mSql = new StringBuffer(256);
				// insert
				this.setInsPart(s);
				if (InsPart.equals(""))
				{
					mSql.setLength(0);
				}
				else
				{
					mSql.append("insert into ");
					mSql.append(TableName);
					mSql.append(" ");
					mSql.append(InsPart);
				}
				break;
			case 2:
				PKWherePart = new StringBuffer(256);
				UpdPart = new StringBuffer(256);
				mSql = new StringBuffer(256);
				// update (by Primary Key)
				this.setUpdPart(s);
				this.setPKWherePart(s);
				if (UpdPart.equals(""))
				{
					mSql.setLength(0);
				}
				else
				{
					mSql.append("update ");
					mSql.append(TableName);
					mSql.append(" ");
					mSql.append(UpdPart);
					mSql.append(" ");
					mSql.append(PKWherePart);
				}
				break;
			case 3:
				WherePart = new StringBuffer(256);
				mSql = new StringBuffer(256);
				// delete
				this.setWherePart(s);
				mSql.append("delete from ");
				mSql.append(TableName);
				mSql.append(" ");
				mSql.append(WherePart);
				break;
			case 4:
				PKWherePart = new StringBuffer(256);
				mSql = new StringBuffer(256);
				// delete (by Primary Key)
				this.setPKWherePart(s);
				mSql.append("delete from ");
				mSql.append(TableName);
				mSql.append(" ");
				mSql.append(PKWherePart);
				break;
			case 5:
				WherePart = new StringBuffer(256);
				mSql = new StringBuffer(256);
				// select
				this.setWherePart(s);
				mSql.append("select * from ");
				mSql.append(TableName);
				mSql.append(" ");
				mSql.append(WherePart);
				break;
			case 6:
				PKWherePart = new StringBuffer(256);
				mSql = new StringBuffer(256);
				// select (by Primary Key)
				this.setPKWherePart(s);
				mSql.append("select * from ");
				mSql.append(TableName);
				mSql.append(" ");
				mSql.append(PKWherePart);
				break;
			case 7:
				WherePart = new StringBuffer(256);
				mSql = new StringBuffer(256);
				// select Count
				this.setWherePart(s);
				mSql.append("select count(1) from ");
				mSql.append(TableName);
				mSql.append(" ");
				mSql.append(WherePart);
				break;
			case 8:
				WherePart = new StringBuffer(256);
				mSql = new StringBuffer(256);
				// select Count
				this.setWherePartBind(s);
				mSql.append("select * from ");
				mSql.append(TableName);
				mSql.append(" ");
				mSql.append(WherePart);
				break;
		}
	}

	public String getSQL()
	{
		return mSql.toString();
	}

	public String getSQL(int sqlFlag, Schema s)
	{
		if (sqlFlag == 5 || sqlFlag == 6)
		{
			this.setSQL(sqlFlag, s);
		}
		else
		{
			mSql.setLength(0);
		}
		return mSql.toString();
	}

	/**
	 * 通过 Primary Key 组成 WherePart
	 * @param s Schema
	 */
	public void setPKWherePart(Schema s)
	{
		String schemaName = s.toString();
		PKWherePart.append("where");

		if (schemaName.indexOf("LFComToISCComSchema") != -1)
		{
			String[] pk = s.getPK();
			String strFieldName = "";
			StringBuffer strFieldValue = null;

			strFieldName = pk[0];
			strFieldValue = new StringBuffer(100);
			int nFieldType = s.getFieldType(strFieldName);
			switch (nFieldType)
			{
				case Schema.TYPE_STRING:
				case Schema.TYPE_DATE:
					strFieldValue.append(mark);
					strFieldValue.append(s.getV(strFieldName));
					strFieldValue.append(mark);
					break;
				case Schema.TYPE_DOUBLE:
				case Schema.TYPE_FLOAT:
				case Schema.TYPE_INT:
					strFieldValue.append(s.getV(strFieldName));
					break;
				default:
					System.out.println("出现异常数据类型");
					break;
			}
			PKWherePart.append(" ");
			PKWherePart.append(strFieldName);
			PKWherePart.append("=");
			PKWherePart.append(strFieldValue);
		}
		else
		{
			String[] pk = s.getPK();
			int n = pk.length;

			String strFieldName = "";
			StringBuffer strFieldValue = null;
			for (int i = 0; i < n; i++)
			{
				strFieldName = pk[i];
				strFieldValue = new StringBuffer(100);
				int nFieldType = s.getFieldType(strFieldName);
				switch (nFieldType)
				{
					case Schema.TYPE_STRING:
					case Schema.TYPE_DATE:
						strFieldValue.append(mark);
						strFieldValue.append(s.getV(strFieldName));
						strFieldValue.append(mark);
						break;
					case Schema.TYPE_DOUBLE:
					case Schema.TYPE_FLOAT:
					case Schema.TYPE_INT:
						strFieldValue.append(s.getV(strFieldName));
						break;
					default:
						System.out.println("出现异常数据类型");
						break;
				}

				if (i != 0)
				{
					PKWherePart.append(" and");
				}
				PKWherePart.append(" ");
				PKWherePart.append(strFieldName);
				PKWherePart.append("=");
				PKWherePart.append(strFieldValue);
			}
		}
	}

	public String getPKWherePart()
	{
		return PKWherePart.toString();
	}

	/**
	 * 通过 Schema 对象组成 WherePart
	 * @param s Schema
	 */
	public void setWherePart(Schema s)
	{
		WherePart.append("where");

		int nFieldCount = s.getFieldCount();
		int j = 0;

		String strFieldName = "";
		StringBuffer strFieldValue = null;
		for (int i = 0; i < nFieldCount; i++)
		{
			strFieldName = s.getFieldName(i);
			strFieldValue = new StringBuffer(100);

			int nFieldType = s.getFieldType(i);
			boolean bFlag = false;

			switch (nFieldType)
			{
				case Schema.TYPE_STRING:
				case Schema.TYPE_DATE:
					if (s.getV(i).equals("null"))
					{
						strFieldValue.append(s.getV(i));
					}
					else
					{
						strFieldValue.append(mark);
						strFieldValue.append(s.getV(i));
						strFieldValue.append(mark);
						bFlag = true;
					}
					break;

				case Schema.TYPE_DOUBLE:
					if (!s.getV(i).equals("0.0"))
					{
						strFieldValue.append(s.getV(i));
						bFlag = true;
					}
					break;

				case Schema.TYPE_FLOAT:
					if (!s.getV(i).equals("0.0"))
					{
						strFieldValue.append(s.getV(i));
						bFlag = true;
					}
					break;

				case Schema.TYPE_INT:
					if (!s.getV(i).equals("0"))
					{
						strFieldValue.append(s.getV(i));
						bFlag = true;
					}
					break;

				default:
					System.out.println("出现异常数据类型");
					bFlag = false;
					break;
			}

			if (bFlag)
			{
				j++;
				if (j != 1)
				{
					WherePart.append(" and");
				}
				WherePart.append(" ");
				WherePart.append(strFieldName);
				WherePart.append(" = ");
				WherePart.append(strFieldValue);
			}
		}
		if (j == 0)
		{
			WherePart.setLength(0);
		}
	}

	/**
	 * 通过 Schema 对象组成 绑定参数形式的WherePart
	 * @param s Schema
	 */
	public void setWherePartBind(Schema s)
	{
		WherePart.append("where");

		int nFieldCount = s.getFieldCount();
		int j = 0;
		List l = new ArrayList();
		String strFieldName = "";
		// 对表的所有字段循环
		for (int i = 0; i < nFieldCount; i++)
		{
			strFieldName = s.getFieldName(i);
			int nFieldType = s.getFieldType(i);
			boolean bFlag = false;
			String[] sArr = new String[3];
			switch (nFieldType)
			{
				case Schema.TYPE_STRING:
				case Schema.TYPE_DATE:
					if (!s.getV(i).equals("null"))
					{
						// String、Date在PrepareStatement设置的方式不同
						sArr[0] = String.valueOf(nFieldType);
						sArr[1] = s.getV(i);
						bFlag = true;
					}
					break;
				case Schema.TYPE_DOUBLE:
				case Schema.TYPE_FLOAT:
					if (!s.getV(i).equals("0.0"))
					{
						// FLOAT在PrepareStatement查询时可设置为DOUBLE
						sArr[0] = String.valueOf(Schema.TYPE_DOUBLE);
						sArr[1] = s.getV(i);
						bFlag = true;
					}
					break;

				case Schema.TYPE_INT:
					if (!s.getV(i).equals("0"))
					{
						// INT在PrepareStatement查询时可设置为DOUBLE
						sArr[0] = String.valueOf(Schema.TYPE_DOUBLE);
						sArr[1] = s.getV(i);
						bFlag = true;
					}
					break;
				default:
					System.out.println("出现异常数据类型");
					bFlag = false;
					break;
			}
			// 字段存在非初始值设置查询条件
			if (bFlag)
			{
				j++;
				if (j != 1)
				{
					WherePart.append(" and");
				}
				WherePart.append(" ");
				WherePart.append(strFieldName);
				WherePart.append(" = ?");
				// 添加绑定参数数组
				l.add(sArr);
			}
		}
		// 不存在查询条件
		if (j == 0)
		{
			WherePart.setLength(0);
		}
		else
		{
			arr = new String[j][2];
			for (int i = 0; i < j; i++)
			{
				// 设置绑定查询条件参数值
				arr[i] = (String[]) l.get(i);
			}
		}
	}

	public String getWherePart()
	{
		return WherePart.toString();
	}

	/**
	 * 通过 Schema 对象组成 UpdPart
	 * @param s Schema
	 */
	public void setUpdPart(Schema s)
	{
		UpdPart.append("set ");

		int nFieldCount = s.getFieldCount();

		String strFieldName = "";
		StringBuffer strFieldValue = null;
		for (int i = 0; i < nFieldCount; i++)
		{
			strFieldName = s.getFieldName(i);
			strFieldValue = new StringBuffer(100);

			int nFieldType = s.getFieldType(i);

			switch (nFieldType)
			{
				case Schema.TYPE_STRING:
				case Schema.TYPE_DATE:
					if (s.getV(i).equals("null"))
					{
						strFieldValue.append(s.getV(i));
					}
					else
					{
						strFieldValue.append(mark);
						strFieldValue.append(s.getV(i));
						strFieldValue.append(mark);
					}
					break;
				case Schema.TYPE_DOUBLE:
				case Schema.TYPE_FLOAT:
				case Schema.TYPE_INT:
					strFieldValue.append(s.getV(i));
					// 不管初始值是0，还是输入0，一律插入数据库。
					break;
				default:
					System.out.println("出现异常数据类型");
					break;
			}
			if (i != 0)
			{
				UpdPart.append(",");
			}
			UpdPart.append(strFieldName);
			UpdPart.append("=");
			UpdPart.append(strFieldValue);
		}
	}

	public String getUpdPart()
	{
		return UpdPart.toString();
	}

	/**
	 * 通过 Schema 对象组成 InsPart
	 * @param s Schema
	 */
	public void setInsPart(Schema s)
	{
		StringBuffer ColPart = new StringBuffer(256);
		ColPart.append("( ");
		StringBuffer ValPart = new StringBuffer(256);
		ValPart.append("values ( ");

		int nFieldCount = s.getFieldCount();
		int j = 0;

		String strFieldName = "";
		StringBuffer strFieldValue = null;
		for (int i = 0; i < nFieldCount; i++)
		{
			strFieldName = s.getFieldName(i);
			strFieldValue = new StringBuffer(100);

			int nFieldType = s.getFieldType(i);
			boolean bFlag = false;
			switch (nFieldType)
			{
				case Schema.TYPE_STRING:
				case Schema.TYPE_DATE:
					if (s.getV(i).equals("null"))
					{
						strFieldValue.append(s.getV(i));
					}
					else
					{
						strFieldValue.append(mark);
						strFieldValue.append(s.getV(i));
						strFieldValue.append(mark);
						bFlag = true;
					}
					break;
				case Schema.TYPE_DOUBLE:
				case Schema.TYPE_FLOAT:
				case Schema.TYPE_INT:
					strFieldValue.append(s.getV(i));
					bFlag = true;
					break;
				default:
					bFlag = false; // 不支持的数据类型
					break;
			}

			if (bFlag)
			{
				j++;
				if (j != 1)
				{
					ColPart.append(",");
					ValPart.append(",");
				}
				ColPart.append(strFieldName);
				ValPart.append(strFieldValue);
			}
		}
		ColPart.append(" )");
		ValPart.append(" )");

		InsPart.append(ColPart);
		InsPart.append(" ");
		InsPart.append(ValPart);
		if (j == 0)
		{
			InsPart.setLength(0);
		}
	}

	public String getInsPart()
	{
		return InsPart.toString();
	}

	/**
	 * 得到绑定参数
	 * @return String[][]
	 */
	public String[][] getArr()
	{
		return arr;
	}
}