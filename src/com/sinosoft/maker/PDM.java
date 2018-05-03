/*
 * <p>ClassName: PDM </p>
 * <p>Description: 获得PDM中的表信息和字段信息 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @author: HST
 * @version: 1.0
 * @date: 2002-06-17
 */
package com.sinosoft.maker;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.Vector;

public class PDM
{
	// @Field
	private String DBName;

	private String fileName;

	private Vector content;

	private Vector fields;

	private Vector tables;

	// @Constructor
	public PDM(String fName)
	{
		fileName = fName;
	}

	// @Method
	private void readPDM()
	{
		try
		{
			content = null;
			content = new Vector();
//			BufferedReader in = new BufferedReader(new FileReader(fileName));
			
			BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "utf-8"));

			String s = in.readLine();
			while (s != null)
			{
				content.addElement(s);
				s = in.readLine();
			}
			in.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.exit(0);
		}
	}

	public String getDBName()
	{
		String s = null;
		this.readPDM();
		int n = content.size();
		for (int i = 0; i < n; i++)
		{
			s = (String) content.get(i);

			if ((s.length() > 17) && (s.substring(0, 8).equals("<a:Code>")))
			{
				int j = s.indexOf("</a:Code>");
				DBName = s.substring(8, j);
				break;
			}
		}
		return DBName;
	}

	public TableInfo getOneTable(String tName) throws Exception
	{
		String s = null;
		String tableLine = null;
		boolean flag = false;
		TableInfo t = new TableInfo();
		tableLine = "<a:Code>" + tName + "</a:Code>";

		this.readPDM();
		int m = tName.length();
		int n = content.size();
		for (int i = 0; i < n; i++)
		{
			s = (String) content.get(i);
			// Table Name
			if ((s.length() >= 17 + m) && s.substring(0, 17 + m).equals(tableLine) && (!flag))
			{
				s = (String) content.get(i - 1);
				int j = s.indexOf("</a:Name>");
				t.setTableName(s.substring(8, j));
				t.setTableCode(tName);
				System.out.println("TableCode: " + t.getTableCode());
				System.out.println("TableName: " + t.getTableName());
				flag = true;
				continue;
			}
			// Column Start Rows
			if ((s.length() == 11) && s.substring(0, 11).equals("<c:Columns>") && flag)
			{
				t.setColumnStart(i);
				continue;
			}
			// Column End Rows
			if ((s.length() == 12) && s.substring(0, 12).equals("</c:Columns>") && flag)
			{
				t.setColumnEnd(i);
				flag = false;
				break;
			}
		} // end of for
		if (t.getTableName() == null)
		{
			Exception e = new Exception("Table name not found!!");
			throw e;
		}
		return t;
	}

	public Vector getTables()
	{
		tables = new Vector();
		boolean flag = false;
		String s = null;
		TableInfo t = new TableInfo();
		this.readPDM();
		int n = content.size();
		for (int i = 0; i < n; i++)
		{
			s = (String) content.get(i);

			// Table Start
			if ((s.length() > 12) && s.substring(0, 12).equals("<o:Table Id=") && (!flag))
			{
				flag = true;
				continue;
			}
			// Table Name
			if ((s.length() > 17) && s.substring(0, 8).equals("<a:Name>") && flag)
			{
				int j = s.indexOf("</a:Name>");
				t.setTableName(s.substring(8, j));
				continue;
			}
			// Table Code
			if ((s.length() > 17) && s.substring(0, 8).equals("<a:Code>") && flag)
			{
				int j = s.indexOf("</a:Code>");
				t.setTableCode(s.substring(8, j));
				flag = false;
				continue;
			}
			// Column Start Rows
			if ((s.length() == 11) && s.substring(0, 11).equals("<c:Columns>") && (!flag))
			{
				t.setColumnStart(i);
				continue;
			}
			// Column End Rows
			if ((s.length() == 12) && s.substring(0, 12).equals("</c:Columns>") && (!flag))
			{
				t.setColumnEnd(i);
				TableInfo t1 = (TableInfo) t.clone();
				tables.addElement(t1);
				flag = false;
				continue;
			}
		} // end of for
		return tables;
	}

	public Vector getFields(TableInfo table)
	{
		fields = new Vector();
		boolean flag = false;
		String s = null;
		int startRow = table.getColumnStart();
		int endRow = table.getColumnEnd();
		FieldInfo f = new FieldInfo();
		this.readPDM();
		for (int i = startRow; i <= endRow; i++)
		{
			s = (String) content.get(i);

			// Field Start
			if ((s.length() > 13) && s.substring(0, 13).equals("<o:Column Id=") && (!flag))
			{
				flag = true;
				continue;
			}
			// Field Name
			if ((s.length() > 17) && s.substring(0, 8).equals("<a:Name>") && flag)
			{
				int j = s.indexOf("</a:Name>");
				f.setFieldName(s.substring(8, j));
				// System.out.println("FieldName: " + f.getFieldName());
				continue;
			}
			// Field Code
			if ((s.length() > 17) && s.substring(0, 8).equals("<a:Code>") && flag)
			{
				int j = s.indexOf("</a:Code>");
				f.setFieldCode(s.substring(8, j));
				// System.out.println("FieldCode: " + f.getFieldCode());
				continue;
			}
			// Field Type
			if ((s.length() > 25) && s.substring(0, 12).equals("<a:DataType>") && flag)
			{
				int j = s.indexOf("</a:DataType>");
				String FieldType = s.substring(12, j);
				FieldType = changeType(FieldType);
				// 如果发现表中存在blob字段，则在tableinfo中设置属性
				if (table.getNoBlobCol() && "InputStream".equals(FieldType))
				{
					table.setNoBlobCol();
				}
				f.setFieldType(FieldType);
				// System.out.println("FieldType: " + f.getFieldType());
				FieldInfo f1 = (FieldInfo) f.clone();
				fields.addElement(f1);
				flag = false;
				continue;
			}
		} // end of for
		return fields;
	}

	private static String changeType(String oldType)
	{
		String strReturn = "";
		if (oldType.toLowerCase().indexOf("int") >= 0)
		{
			strReturn = "int";
		}
		if (oldType.toLowerCase().indexOf("dec") >= 0)
		{
			int i = oldType.toLowerCase().indexOf(",");
			if (i >= 0 && oldType.toLowerCase().substring(i + 1, i + 2).equals("0"))
			{
				strReturn = "int";
			}
			else
			{
				strReturn = "double";
			}
		}
		if (oldType.toLowerCase().indexOf("num") >= 0)
		{
			int i = oldType.toLowerCase().indexOf(",");
			if (i >= 0 && oldType.toLowerCase().substring(i + 1, i + 2).equals("0"))
			{
				strReturn = "int";
			}
			else
			{
				strReturn = "double";
			}
		}
		if (oldType.toLowerCase().indexOf("char") >= 0)
		{
			strReturn = "String";
		}
		if (oldType.toLowerCase().indexOf("date") >= 0)
		{
			strReturn = "Date";
		}
		if (oldType.toLowerCase().indexOf("datetime") >= 0)
		{
			strReturn = "String";
		}
		if (oldType.toLowerCase().indexOf("float") >= 0)
		{
			strReturn = "double";
		}
		if (oldType.toLowerCase().indexOf("serial") >= 0)
		{
			strReturn = "int";
		}
		if (oldType.toLowerCase().indexOf("raw") >= 0)
		{
			strReturn = "int";
		}
		if (oldType.toLowerCase().indexOf("byte") >= 0)
		{
			strReturn = "int";
		}
		if (oldType.toLowerCase().indexOf("blob") >= 0)
		{
			strReturn = "InputStream";
		}
		return strReturn;
	}
}
