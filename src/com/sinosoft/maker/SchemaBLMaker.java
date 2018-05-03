/*
 * <p>ClassName: SetMaker </p>
 * <p>Description: 生成 TablenameSet.java 文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @author: HST
 * @version: 1.0
 * @date: 2002-06-17
 */
package com.sinosoft.maker;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Vector;

import com.sinosoft.utility.JdbcUrl;

public class SchemaBLMaker
{
	// @Field
	private String DBName;

	private String TableName;

	private String FileName;

	private TableInfo table;

	// @Constructor
	public SchemaBLMaker(String fName)
	{
		FileName = fName;
	}

	public SchemaBLMaker(String fName, String t)
	{
		FileName = fName;
		TableName = t;
		/*
		 * 从数据库提取表名和字段时使用 TableName = TableName.toLowerCase(); TableName =
		 * TableName.substring(0,1).toUpperCase() + TableName.substring(1);
		 */
	}

	// @Method
	public void makeAllTable(JdbcUrl sUrl)
	{
//		JMetaDB mdb = new JMetaDB(sUrl);
		PDM db = new PDM(FileName);
		DBName = db.getDBName();
		try
		{
			Vector t = db.getTables();
			int n = t.size();
			for (int i = 0; i < n; i++)
			{
				table = (TableInfo) t.get(i);
				TableName = table.getTableCode();
				this.create();
			}
		}
		catch (Exception e)
		{
			// e.printStackTrace();
			System.out.println("Create failde!!");
			System.out.println(e.toString());
			System.exit(0);
		}

		/*
		 * 从数据库提取表名和字段时使用 JMetaDB db = new JMetaDB(sUrl); DBName =
		 * sUrl.getDBName(); try { Vector t = db.getTables(); int n = t.size();
		 * for (int i = 0; i < n; i++) { TableName = (String)t.get(i);
		 * this.create(); } } catch(Exception e) { e.printStackTrace(); }
		 */
	}

	public void makeOneTable(JdbcUrl sUrl)
	{
//		JMetaDB mdb = new JMetaDB(sUrl);
		PDM db = new PDM(FileName);
		DBName = db.getDBName();
		System.out.println("DBName: " + DBName);

		if (TableName == null)
		{
			System.out.println("Create failde!!");
			System.out.println("Tablename is missed!!");
			System.exit(0);
		}
		try
		{
			table = db.getOneTable(TableName);
			this.create();
		}
		catch (Exception e)
		{
			// e.printStackTrace();
			System.out.println("Create failde!!");
			System.out.println(e.toString());
			System.exit(0);
		}

		/*
		 * 从数据库提取表名和字段时使用 JMetaDB db = new JMetaDB(sUrl); DBName =
		 * sUrl.getDBName(); if (TableName == null) { System.out.println("Create
		 * failde!!"); System.out.println("Tablename is missed!!");
		 * System.exit(0); } try { this.create(); } catch(Exception e) {
		 * e.printStackTrace(); }
		 */
	}

	public void create()
	{
		PrintWriter out = null;
		String Path = ".\\com\\sinosoft\\lis\\bl\\";
		String ClassName = TableName + "BL";
		String FileName = ClassName + ".java";
		String SchemaName = TableName + "Schema";

		try
		{
			System.out.println("Creating file ......");
			System.out.println("File Name : " + FileName);

			out = new PrintWriter(new FileWriter(new File(Path + FileName)), true);

			// 生成文件头信息
			out.println("/*");
			out.println(" * <p>ClassName: " + ClassName + " </p>");
			out.println(" * <p>Description: " + SchemaName + "BL类文件 </p>");
			out.println(" * <p>Copyright: Copyright (c) 2002</p>");
			out.println(" * <p>Company: sinosoft </p>");
			out.println(" * @Database: " + DBName);
			out.println(" * @CreateDate：" + getDate());
			out.println(" */");
			// @Package
			out.println("package com.sinosoft.lis.bl;");
			out.println("");
			// @Import
			out.println("import com.sinosoft.lis.vbl.*;");
			out.println("import com.sinosoft.lis.db.*;");
			out.println("import com.sinosoft.lis.vdb.*;");
			out.println("import com.sinosoft.lis.schema.*;");
			out.println("import com.sinosoft.lis.vschema.*;");
			out.println("import com.sinosoft.utility.*;");
			out.println("");
			// @Begin
			out.println("public class " + ClassName + " extends " + SchemaName);
			out.println("{");

			// 生成构建器
			out.println("	// @Constructor");
			out.println("	public " + ClassName + "() {}");
			out.println("");

			// 生成结尾
			out.println("}");

			System.out.println("Create file success!!");
		} // end of try
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}

	// 得到当前日期
	private String getDate()
	{
		String strReturn = "";
		int intYear = Calendar.getInstance().get(Calendar.YEAR);
		int intMonth = Calendar.getInstance().get(Calendar.MONTH) + 1;
		int intDate = Calendar.getInstance().get(Calendar.DATE);
		strReturn = "" + intYear;

		if (intMonth < 10)
		{
			strReturn += "-" + "0" + intMonth;
		}
		else
		{
			strReturn += "-" + intMonth;
		}

		if (intDate < 10)
		{
			strReturn += "-" + "0" + intDate;
		}
		else
		{
			strReturn += "-" + intDate;
		}
		return strReturn;
	}
}