/*
 * <p>ClassName: FieldInfo </p>
 * <p>Description: 储存表中字段名称和字段类型的信息 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @author: HST
 * @version: 1.0
 * @date: 2002-05-31
 */
package com.sinosoft.maker;

public class FieldInfo implements Cloneable
{
	// @Constructor
	public FieldInfo()
	{
		FieldProperty = "";
	}

	// @Field
	private String FieldName;

	private String FieldCode;

	private String FieldType;

	private String FieldProperty;

	private int FieldSQLType;

	private int FieldLength;

	// @Method
	public String getFieldName()
	{
		return FieldName;
	}

	public String getFieldCode()
	{
		return FieldCode;
	}

	public String getFieldType()
	{
		return FieldType;
	}

	public String getFieldProperty()
	{
		return FieldProperty;
	}

	public void setFieldName(String aFieldName)
	{
		aFieldName = aFieldName.toLowerCase();
		aFieldName = aFieldName.substring(0, 1).toUpperCase() + aFieldName.substring(1);
		FieldName = aFieldName;
	}

	public void setFieldCode(String aFieldCode)
	{
		FieldCode = aFieldCode;
	}

	public void setFieldType(String aFieldType)
	{
		FieldType = aFieldType;
	}

	public void setFieldProperty(String aFieldProperty)
	{
		FieldProperty = aFieldProperty;
		// "PK": Primary Key
	}

	protected Object clone()
	{
		try
		{
			FieldInfo f = (FieldInfo) super.clone();
			return f;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return this;
		}
	}

	public void setFieldLength(int FieldLength)
	{
		this.FieldLength = FieldLength;
	}

	public int getFieldLength()
	{
		return FieldLength;
	}

	public void setFieldSQLType(int FieldSQLType)
	{
		this.FieldSQLType = FieldSQLType;
	}

	public int getFieldSQLType()
	{
		return FieldSQLType;
	}
}
