/*
 * <p>ClassName: TableInfo </p>
 * <p>Description: 储存表的信息 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @author: HST
 * @version: 1.0
 * @date: 2002-06-14
 */
package com.sinosoft.maker;

public class TableInfo implements Cloneable
{
	// @Constructor
	public TableInfo()
	{}

	// @Field
	private String tableName;

	private String tableCode;

	private int columnStart;

	private int columnEnd;
	
	// 是否存在blob字段
	private boolean NoBlobCol = true;

	// @Method
	public String getTableName()
	{
		return tableName;
	}

	public String getTableCode()
	{
		return tableCode;
	}

	public int getColumnStart()
	{
		return columnStart;
	}

	public int getColumnEnd()
	{
		return columnEnd;
	}

	// 获取表中是否存在blob字段的标识
	public boolean getNoBlobCol()
	{
		return NoBlobCol;
	}
	
	public void setTableName(String aTableName)
	{
		tableName = aTableName;
	}

	public void setTableCode(String aTableCode)
	{
		tableCode = aTableCode;
	}

	public void setColumnStart(int aColumnStart)
	{
		columnStart = aColumnStart;
	}

	// 设置表中是否存在blob字段
	public void setNoBlobCol()
	{
		NoBlobCol = false;
	}
	
	public void setColumnEnd(int aColumnEnd)
	{
		columnEnd = aColumnEnd;
	}

	protected Object clone()
	{
		try
		{
			TableInfo t = (TableInfo) super.clone();
			return t;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return this;
		}
	}
}
