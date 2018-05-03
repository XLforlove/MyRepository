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

public class SetMaker
{
	// @Field
	private String DBName;

	private String TableName;

	private String FileName;

	private TableInfo table;

	// @Constructor
	public SetMaker(String fName)
	{
		FileName = fName;
	}

	public SetMaker(String fName, String t)
	{
		FileName = fName;
		TableName = t;
		// 从数据库提取表名和字段时使用
		// TableName = TableName.toLowerCase();
		// TableName = TableName.substring(0,1).toUpperCase()
		// + TableName.substring(1);
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

		// 从数据库提取表名和字段时使用
		/*
		 * JMetaDB db = new JMetaDB(sUrl); DBName = sUrl.getDBName(); try {
		 * Vector t = db.getTables(); int n = t.size(); for (int i = 0; i < n;
		 * i++) { TableName = (String)t.get(i); this.create(); } }
		 * catch(Exception e) { e.printStackTrace(); }
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

		// 从数据库提取表名和字段时使用
		/*
		 * JMetaDB db = new JMetaDB(sUrl); DBName = sUrl.getDBName(); if
		 * (TableName == null) { System.out.println("Create failde!!");
		 * System.out.println("Tablename is missed!!"); System.exit(0); } try {
		 * this.create(); } catch(Exception e) { e.printStackTrace(); }
		 */
	}

	public void create()
	{
		PrintWriter out = null;
		String Path = ".\\src\\com\\sinosoft\\lis\\vschema\\";
		String ClassName = TableName + "Set";
		FileName = ClassName + ".java";
		String SchemaName = TableName + "Schema";

		try
		{
			System.out.println("Creating file ......");
			System.out.println("File Name : " + FileName);

			out = new PrintWriter(new FileWriter(new File(Path + FileName)), true);

			// 文件头信息
			out.println("/**");
			out.println(" * Copyright (c) 2002 sinosoft  Co. Ltd.");
			out.println(" * All right reserved.");
			out.println(" */");
			out.println("");
			// @Package
			out.println("package com.sinosoft.lis.vschema;");
			out.println("");
			// @Import
			out.println("import com.sinosoft.lis.schema." + SchemaName + ";");
			out.println("import com.sinosoft.utility.*;");
			out.println("");
			// 类信息
			out.println("/**");
			out.println(" * <p>ClassName: " + ClassName + " </p>");
			out.println(" * <p>Description: " + SchemaName + "Set类文件 </p>");
			out.println(" * <p>Copyright: Copyright (c) 2002</p>");
			out.println(" * <p>Company: sinosoft </p>");
			out.println(" * @Database: " + DBName);
			out.println(" * @CreateDate：" + getDate());
			out.println(" */");
			// @Begin
			out.println("public class " + ClassName + " extends SchemaSet");
			out.println("{");

			// 生成方法
			out.println("	// @Method");
			// 生成 add 方法
			out.println("	public boolean add(" + SchemaName + " aSchema)");
			out.println("	{");
			out.println("		return super.add(aSchema);");
			out.println("	}");
			out.println("");
			out.println("	public boolean add(" + ClassName + " aSet)");
			out.println("	{");
			out.println("		return super.add(aSet);");
			out.println("	}");
			out.println("");

			// 生成 remove 方法
			out.println("	public boolean remove(" + SchemaName + " aSchema)");
			out.println("	{");
			out.println("		return super.remove(aSchema);");
			out.println("	}");
			out.println("");

			// 生成 get 方法
			out.println("	public " + SchemaName + " get(int index)");
			out.println("	{");
			out.println("		" + SchemaName + " tSchema = (" + SchemaName + ")super.getObj(index);");
			out.println("		return tSchema;");
			out.println("	}");
			out.println("");

			// 生成 set 方法
			out.println("	public boolean set(int index, " + SchemaName + " aSchema)");
			out.println("	{");
			out.println("		return super.set(index,aSchema);");
			out.println("	}");
			out.println("");
			out.println("	public boolean set(" + ClassName + " aSet)");
			out.println("	{");
			out.println("		return super.set(aSet);");
			out.println("	}");
			out.println("");

			// 生成 encode 方法
			out.println("	/**");
			out.println("	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#Prp" + TableName + "描述/A>表字段");
			// out.println(" * @param: 无");
			out.println("	* @return: String 返回打包后字符串");
			out.println("	**/");
			out.println("	public String encode()");
			out.println("	{");
			// out.println(" String strReturn = \"\";");
			out.println("		StringBuffer strReturn = new StringBuffer(\"\");");
			out.println("		int n = this.size();");
			out.println("		for (int i = 1; i <= n; i++)");
			out.println("		{");
			out.println("			" + SchemaName + " aSchema = this.get(i);");
			// out.println(" strReturn += aSchema.encode();");
			out.println("			strReturn.append(aSchema.encode());");
			// out.println(" if( i != n ) strReturn +=
			// SysConst.RECORDSPLITER;");
			out.println("			if( i != n ) strReturn.append(SysConst.RECORDSPLITER);");
			out.println("		}");
			out.println("");
			out.println("		return strReturn.toString();");
			out.println("	}");
			out.println("");

			// 生成 decode 方法
			out.println("	/**");
			out.println("	* 数据解包");
			out.println("	* @param: str String 打包后字符串");
			out.println("	* @return: boolean");
			out.println("	**/");
			out.println("	public boolean decode( String str )");
			out.println("	{");
			out.println("		int nBeginPos = 0;");
			out.println("		int nEndPos = str.indexOf('^');");
			out.println("		this.clear();");
			out.println("");
			out.println("		while( nEndPos != -1 )");
			out.println("		{");
			out.println("			" + SchemaName + " aSchema = new " + SchemaName + "();");
			// out.println(" if(!aSchema.decode(str.substring(nBeginPos,
			// nEndPos)))");
			// out.println(" {");
			// out.println(" // @@错误处理");
			// out.println(" this.mErrors.copyAllErrors( aSchema.mErrors );");
			// out.println(" return false;");
			// out.println(" }");
			// out.println(" this.add(aSchema);");
			// out.println(" nBeginPos = nEndPos + 1;");
			// out.println(" nEndPos = str.indexOf('^', nEndPos + 1);");
			out.println("			if(aSchema.decode(str.substring(nBeginPos, nEndPos)))");
			out.println("			{");
			out.println("			this.add(aSchema);");
			out.println("			nBeginPos = nEndPos + 1;");
			out.println("			nEndPos = str.indexOf('^', nEndPos + 1);");
			out.println("			}");
			out.println("			else");
			out.println("			{");
			out.println("				// @@错误处理");
			out.println("				this.mErrors.copyAllErrors( aSchema.mErrors );");
			out.println("				return false;");
			out.println("			}");
			out.println("		}");
			out.println("		" + SchemaName + " tSchema = new " + SchemaName + "();");
			// out.println(" if(!tSchema.decode(str.substring(nBeginPos)))");
			// out.println(" {");
			// out.println(" // @@错误处理");
			// out.println(" this.mErrors.copyAllErrors( tSchema.mErrors );");
			// out.println(" return false;");
			// out.println(" }");
			// out.println(" this.add(tSchema);");
			// out.println("");
			// out.println(" return true;");
			out.println("		if(tSchema.decode(str.substring(nBeginPos)))");
			out.println("		{");
			out.println("		this.add(tSchema);");
			out.println("		return true;");
			out.println("		}");
			out.println("		else");
			out.println("		{");
			out.println("			// @@错误处理");
			out.println("			this.mErrors.copyAllErrors( tSchema.mErrors );");
			out.println("			return false;");
			out.println("		}");
			out.println("	}");
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
	private static String getDate()
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
