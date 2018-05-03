package com.sinosoft.maker;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.sql.Types;
import java.util.Vector;

import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.utility.JdbcUrl;
import com.sinosoft.utility.SysConst;

/**
 * <p>
 * ClassName:
 * </p>
 * <p>
 * Description: 生成 TablenameSchema.java 文件
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: sinosoft
 * </p>
 * @author: HST
 * @version: 1.0
 * @date: 2002-05-31
 */
public class SchemaMaker
{
	// @Field
	private String FileName;

	private String DBName;

	private String TableName;

	private TableInfo table;

	private boolean mFlag = false;

	private boolean mNoBlobCol = true;

	// @Constructor
	public SchemaMaker(String fName)
	{
		FileName = fName;
	}

	public SchemaMaker(String fName, String t)
	{
		TableName = t;
		FileName = fName;
	}

	// @Method
	public void makeAllTable(JdbcUrl sUrl)
	{
		JMetaDB mdb = new JMetaDB(sUrl);
		PDM db = new PDM(FileName);
		DBName = db.getDBName();
		try
		{
			Vector t = db.getTables();
			int n = t.size();
			for (int i = 0; i < n; i++)
			{
				table = (TableInfo) t.get(i);
				System.out.println("开始执行表" + table.getTableName());
				Vector f = db.getFields(table);
				TableName = table.getTableCode();
				mNoBlobCol = table.getNoBlobCol();
				f = mdb.getPrimaryKey(f, TableName);
				this.create(f);
			}
		}
		catch (Exception e)
		{
			// e.printStackTrace();
			System.out.println("Create failde!!");
			System.out.println(e.toString());
			System.exit(0);
		}
	}

	public void makeOneTable(JdbcUrl sUrl)
	{
		JMetaDB mdb = new JMetaDB(sUrl);
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
			Vector f = db.getFields(table);
			f = mdb.getPrimaryKey(f, TableName);
			this.create(f);
		}
		catch (Exception e)
		{
			// e.printStackTrace();
			System.out.println("Create failde!!");
			System.out.println(e.toString());
			System.exit(0);
		}
	}

	public void create(Vector fields)
	{
		PrintWriter out = null;
		String Path = ".\\src\\com\\sinosoft\\lis\\schema\\";
		String ClassName = TableName + "Schema";
		String DBOperName = TableName + "DB";
		FileName = ClassName + ".java";

		try
		{
			System.out.println("Creating file ......");
			System.out.println("File Name : " + FileName);

			out = new PrintWriter(new FileWriter(new File(Path + FileName)), true);

			// 文件头信息
			out.println("/**");
			out.println(" * Copyright (c) " + PubFun.getCurrentDate().substring(0, 4) + " sinosoft  Co. Ltd.");
			out.println(" * All right reserved.");
			out.println(" */");
			out.println("");
			// @Package
			out.println("package com.sinosoft.lis.schema;");
			out.println("");
			// @Import
			out.println("import java.sql.*;");
			out.println("import java.util.Date;");
			out.println("import com.sinosoft.lis.pubfun.FDate;");
			out.println("import com.sinosoft.utility.*;");
			out.println("import com.sinosoft.lis.db." + DBOperName + ";");
			// 判断是否存在blob信息，有则增加引用
			if (!mNoBlobCol)
			{
				out.println("import java.io.InputStream;");
			}
			out.println("");
			// 类信息
			out.println("/**");
			out.println(" * <p>Title: Web业务系统</p>");
			out.println(" * <p>ClassName: " + ClassName + " </p>");
			out.println(" * <p>Description: DB层 Schema 类文件 </p>");
			out.println(" * @Database: " + DBName);
			out.println(" * @CreateDate：" + PubFun.getCurrentDate());
			out.println(" */");
			// @Begin
			out.println("public class " + ClassName + " implements Schema, Cloneable");
			out.println("{");

			// 生成数据成员定义
			out.println("	// @Field");
			int n = fields.size();
			for (int i = 0; i < n; i++)
			{
				FieldInfo f = (FieldInfo) fields.get(i);
				String FieldName = f.getFieldName();
				String FieldCode = f.getFieldCode();
				String FieldType = f.getFieldType();
				if (FieldType.equals("Date"))
				{
					mFlag = true;
				}
				out.println("	/** " + FieldName + " */");
				out.println("	private " + FieldType + " " + FieldCode + ";");
				// System.out.println(FieldType + " " + FieldCode);
			}
			out.println("");
			out.println("	public static final int FIELDNUM = " + n + ";	// 数据库表的字段个数");
			out.println("");
			out.println("	private static String[] PK;				// 主键");
			if (mFlag)
			{
				out.println("");
				out.println("	private FDate fDate = new FDate();		// 处理日期");
			}
			out.println("");
			out.println("	public CErrors mErrors;			// 错误信息");
			out.println("");

			// 生成构建器
			out.println("	// @Constructor");
			out.println("	public " + ClassName + "()");
			out.println("	{");
			out.println("		mErrors = new CErrors();");
			out.println("");
			int k = 0;
			for (int i = 0; i < n; i++)
			{
				FieldInfo f = (FieldInfo) fields.get(i);
				String FieldProperty = f.getFieldProperty();
				if (FieldProperty.equals("PK"))
				{
					k++;
				}
			}
			out.println("		String[] pk = new String[" + k + "];");
			k = 0;
			for (int i = 0; i < n; i++)
			{
				FieldInfo f = (FieldInfo) fields.get(i);
				String FieldCode = f.getFieldCode();
				String FieldProperty = f.getFieldProperty();
				if (FieldProperty.equals("PK"))
				{
					out.println("		pk[" + k + "] = \"" + FieldCode + "\";");
					k++;
				}
			}
			out.println("");
			out.println("		PK = pk;");
			out.println("	}");
			out.println("");

			// 生成clone方法
			out.println("            /**");
			out.println("             * Schema克隆");
			out.println("             * @return Object");
			out.println("             * @throws CloneNotSupportedException");
			out.println("             */");
			out.println("            public Object clone()");
			out.println("                    throws CloneNotSupportedException");
			out.println("            {");
			out.println("                " + ClassName + " cloned = (" + ClassName + ")super.clone();");
			if (mFlag)
			{
				out.println("                cloned.fDate = (FDate) fDate.clone();");
			}
			out.println("                cloned.mErrors = (CErrors) mErrors.clone();");
			out.println("                return cloned;");
			out.println("            }");
			out.println("");

			// 生成 getPK 方法
			out.println("	// @Method");
			out.println("	public String[] getPK()");
			out.println("	{");
			out.println("		return PK;");
			out.println("	}");
			out.println("");

			// 生成 set、get 字段的方法
			for (int i = 0; i < n; i++)
			{
				FieldInfo f = (FieldInfo) fields.get(i);
				String FieldCode = f.getFieldCode();
				String FieldType = f.getFieldType();
				// get方法
				if (FieldType.equals("Date"))
				{
					// 特殊处理
					out.println("	public String get" + FieldCode + "()");
					out.println("	{");
					out.println("		if( " + FieldCode + " != null )");
					out.println("			return fDate.getString(" + FieldCode + ");");
					out.println("		else");
					out.println("			return null;");
					out.println("	}");
				}
				else if (FieldType.equals("InputStream"))
				{
					// 特殊处理
					out.println("	public InputStream get" + FieldCode + "()");
					out.println("	{");
					out.println("		return " + FieldCode + ";");
					out.println("	}");
				}
				else
				{
					out.println("	public " + FieldType + " get" + FieldCode + "()");
					out.println("	{");
					if (FieldType.equals("String"))
					{
						if (SysConst.CHANGECHARSET)
						{
							out.println("		if (SysConst.CHANGECHARSET && " + FieldCode + " != null && !" + FieldCode
									+ ".equals(\"\"))");
							out.println("		{");
							out.println("			" + FieldCode + " = StrTool.unicodeToGBK(" + FieldCode + ");");
							out.println("		}");
						}
					}
					out.println("		return " + FieldCode + ";");
					out.println("	}");
				}
				// set方法
				if (FieldType.equals("String"))
				{
					out.println("	public void set" + FieldCode + "(" + FieldType + " a" + FieldCode + ")");
					out.println("	{");
					// 由于系统改造为varchar2的类型，也就面临一个可能传入数据存在空格的风险，需要在这里处理掉
					out.println("		" + FieldCode + " = StrTool.cTrim(a" + FieldCode + ");");
					out.println("	}");
				}
				else
				{
					out.println("	public void set" + FieldCode + "(" + FieldType + " a" + FieldCode + ")");
					out.println("	{");
					out.println("		" + FieldCode + " = a" + FieldCode + ";");
					out.println("	}");

					// 非String类型的字段赋值的时候需要有特殊的分支支持
					out.println("	public void set" + FieldCode + "(String a" + FieldCode + ")");
					out.println("	{");
					if (FieldType.equals("float"))
					{
						out.println("		if (a" + FieldCode + " != null && !a" + FieldCode + ".equals(\"\"))");
						out.println("		{");
						out.println("			Float tFloat = new Float(a" + FieldCode + ");");
						out.println("			float f = tFloat.floatValue();");
						out.println("			" + FieldCode + " = f;");
						out.println("		}");
					}
					else if (FieldType.equals("double"))
					{
						// add by yt 2003-6-20
						out.println("		if (a" + FieldCode + " != null && !a" + FieldCode + ".equals(\"\"))");
						out.println("		{");
						out.println("			Double tDouble = new Double(a" + FieldCode + ");");
						out.println("			double d = tDouble.doubleValue();");
						out.println("			" + FieldCode + " = d;");
						out.println("		}");
					}
					else if (FieldType.equals("int"))
					{
						out.println("		if (a" + FieldCode + " != null && !a" + FieldCode + ".equals(\"\"))");
						out.println("		{");
						out.println("			Integer tInteger = new Integer(a" + FieldCode + ");");
						out.println("			int i = tInteger.intValue();");
						out.println("			" + FieldCode + " = i;");
						out.println("		}");
					}
					else if (FieldType.equals("Date"))
					{
						out.println("		if (a" + FieldCode + " != null && !a" + FieldCode + ".equals(\"\") )");
						out.println("		{");
						out.println("			" + FieldCode + " = fDate.getDate( a" + FieldCode + " );");
						out.println("		}");
						out.println("		else");
						out.println("			" + FieldCode + " = null;");
					}
					out.println("	}");
					out.println("");
				}
			}

			// 生成 setSchema 方法
			out.println("");
			out.println("	/**");
			out.println("	* 使用另外一个 " + ClassName + " 对象给 Schema 赋值");
			out.println("	* @param: a" + ClassName + " " + ClassName);
			// out.println(" * @return: 无");
			out.println("	**/");
			out.println("	public void setSchema(" + ClassName + " a" + ClassName + ")");
			out.println("	{");
			for (int i = 0; i < n; i++)
			{
				FieldInfo f = (FieldInfo) fields.get(i);
				String FieldCode = f.getFieldCode();
				String FieldType = f.getFieldType();
				if (FieldType.equals("Date"))
				{
					out.println("		this." + FieldCode + " = fDate.getDate( a" + ClassName + ".get" + FieldCode + "());");
				}
				else
				{
					out.println("		this." + FieldCode + " = a" + ClassName + ".get" + FieldCode + "();");
				}
			}
			out.println("	}");
			out.println("");
			out.println("	/**");
			out.println("	* 使用 ResultSet 中的第 i 行给 Schema 赋值");
			out.println("	* @param: rs ResultSet");
			out.println("	* @param: i int");
			out.println("	* @return: boolean");
			out.println("	**/");
			out.println("	public boolean setSchema(ResultSet rs,int i)");
			out.println("	{");
			out.println("		try");
			out.println("		{");
			out.println("			//rs.absolute(i);		// 非滚动游标");
			int index = 0;
			for (int i = 0; i < n; i++)
			{
				index = i + 1;
				// 采用字段名方式返回数据信息
				// 2009-4-3 修改为按字段顺序返回数据信息，高性能，但是要求pdm的字段顺序和数据库中的字段顺序完全一致
				FieldInfo f = (FieldInfo) fields.get(i);
				String FieldCode = f.getFieldCode();
				String FieldType = f.getFieldType();
				if (FieldType.equals("float"))
				{
					out.println("			this." + FieldCode + " = rs.getFloat(" + index + ");");
				}
				else if (FieldType.equals("double"))
				{
					// add by yt 2003-6-20
					out.println("			this." + FieldCode + " = rs.getDouble(" + index + ");");
				}
				else if (FieldType.equals("int"))
				{
					out.println("			this." + FieldCode + " = rs.getInt(" + index + ");");
				}
				else if (FieldType.equals("String"))
				{
					out.println("			if( rs.getString(" + index + ") == null )");
					out.println("				this." + FieldCode + " = null;");
					out.println("			else");
					out.println("				this." + FieldCode + " = rs.getString(" + index + ").trim();");
					out.println("");
				}
				else if (FieldType.equals("Date"))
				{
					out.println("			this." + FieldCode + " = rs.getDate(" + index + ");");
				}
				if (FieldType.equals("InputStream"))
				{
					out.println("			this." + FieldCode + " = rs.getBinaryStream(" + index + ");");
				}
			}

			// end of for
			out.println("		}");
			out.println("		catch(SQLException sqle)");
			out.println("		{");
			out.println("			System.out.println(\"数据库中的" + TableName
					+ "表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables\");");
			out.println("			// @@错误处理");
			out.println("			CError tError = new CError();");
			out.println("			tError.moduleName = \"" + ClassName + "\";");
			out.println("			tError.functionName = \"setSchema\";");
			out.println("			tError.errorMessage = sqle.toString();");
			out.println("			this.mErrors .addOneError(tError);");
			out.println("			return false;");
			out.println("		}");
			out.println("		return true;");
			out.println("	}");

			out.println("");
			out.println("	public " + ClassName + " getSchema()");
			out.println("	{");
			out.println("		" + ClassName + " a" + ClassName + " = new " + ClassName + "();");
			out.println("		a" + ClassName + ".setSchema(this);");
			out.println("		return a" + ClassName + ";");
			out.println("	}");
			out.println("");
			out.println("	public " + DBOperName + " getDB()");
			out.println("	{");
			out.println("		" + DBOperName + " aDBOper = new " + DBOperName + "();");
			out.println("		aDBOper.setSchema(this);");
			out.println("		return aDBOper;");
			out.println("	}");
			out.println("");
			out.println("");

			// 生成 encode 方法
			out.println("	/**");
			out.println("	* 数据打包，按 XML 格式打包");
			out.println("	* @return: String 返回打包后字符串");
			out.println("	**/");
			out.println("	public String encode()");
			out.println("	{");
			out.println("		StringBuffer strReturn = new StringBuffer(256);");
			boolean b = false;
			for (int i = 0; i < n; i++)
			{
				FieldInfo f = (FieldInfo) fields.get(i);
				String FieldCode = f.getFieldCode();
				String FieldType = f.getFieldType();
				if (i == (n - 1))
				{
					b = true;
				}
				if (i == 0)
				{
					out.println("strReturn.append(" + getEnCodeStringLine(b, FieldCode, FieldType));
				}
				else
				{
					out.println("strReturn.append(" + getEnCodeStringLine(b, FieldCode, FieldType));
				}
			}
			out.println("		return strReturn.toString();");
			out.println("	}");
			out.println("");

			// 生成 decode 函数
			out.println("	/**");
			out.println("	* 数据解包");
			out.println("	* @param: strMessage String 包含一条纪录数据的字符串");
			out.println("	* @return: boolean");
			out.println("	**/");
			out.println("	public boolean decode(String strMessage)");
			out.println("	{");
			out.println("		try");
			out.println("		{");
			out.println("			// 仅仅需要对传入的strMessage进行一次转换");
			out.println("        	strMessage = StrTool.GBKToUnicode(strMessage);");
			for (int i = 0; i < n; i++)
			{
				FieldInfo f = (FieldInfo) fields.get(i);
				String FieldCode = f.getFieldCode();
				String FieldType = f.getFieldType();
				out.println("			" + getDeCodeStringLine(i, FieldCode, FieldType));
			}
			out.println("		}");
			out.println("		catch(NumberFormatException ex)");
			out.println("		{");
			out.println("			// @@错误处理");
			out.println("			CError tError = new CError();");
			out.println("			tError.moduleName = \"" + ClassName + "\";");
			out.println("			tError.functionName = \"decode\";");
			out.println("			tError.errorMessage = ex.toString();");
			out.println("			this.mErrors .addOneError(tError);");
			out.println("");
			out.println("			return false;");
			out.println("		}");
			out.println("		return true;");
			out.println("	}");
			out.println("");

			// 生成 getV 函数
			out.println("	/**");
			out.println("	* 取得对应传入参数的String形式的字段值");
			out.println("	* @param: FCode String 希望取得的字段名");
			out.println("	* @return: String");
			out.println("	* 如果没有对应的字段，返回\"\"");
			out.println("	* 如果字段值为空，返回\"null\"");
			out.println("	**/");
			out.println("	public String getV(String FCode)");
			out.println("	{");
			out.println("		String strReturn = \"\";");
			for (int i = 0; i < n; i++)
			{
				FieldInfo f = (FieldInfo) fields.get(i);
				String FieldCode = f.getFieldCode();
				String FieldType = f.getFieldType();
				if (i == 0)
				{
					out.println("		if (FCode.equalsIgnoreCase(\"" + FieldCode + "\"))");
				}
				else
				{
					out.println("		else if (FCode.equalsIgnoreCase(\"" + FieldCode + "\"))");
				}
				out.println("		{");
				if (FieldType.equals("Date"))
				{
					out.println("			strReturn = StrTool.cTrim(this.get" + FieldCode + "());");
				}
				else if (FieldType.equals("String"))
				{
					out.println("			strReturn = StrTool.cTrim(" + FieldCode + ");");
				}
				else
				{
					out.println("			strReturn = String.valueOf(" + FieldCode + ");");
				}
				out.println("		}");
			}
			out.println("		if (strReturn.equals(\"\"))");
			out.println("		{");
			out.println("			strReturn = \"null\";");
			out.println("		}");
			out.println("");
			out.println("		return strReturn;");
			out.println("	}");
			out.println("");

			// 生成 getV 方法
			out.println();
			out.println("	/**");
			out.println("	* 取得Schema中指定索引值所对应的字段值");
			out.println("	* @param: nFieldIndex int 指定的字段索引值");
			out.println("	* @return: String");
			out.println("	* 如果没有对应的字段，返回\"\"");
			out.println("	* 如果字段值为空，返回\"null\"");
			out.println("	**/");
			out.println("	public String getV(int nFieldIndex)");
			out.println("	{");
			out.println("		String strFieldValue = \"\";");
			out.println("		switch(nFieldIndex) {");
			for (int i = 0; i < n; i++)
			{
				FieldInfo f = (FieldInfo) fields.get(i);

				String FieldType = f.getFieldType();
				String FieldCode = f.getFieldCode();

				out.println("			case " + String.valueOf(i) + ":");
				if (FieldType.equals("Date"))
				{
					out.println("				strFieldValue = StrTool.cTrim(this.get" + FieldCode + "());");
				}
				else if (FieldType.equals("String"))
				{
					out.println("				strFieldValue = StrTool.cTrim(" + FieldCode + ");");
				}
				else
				{
					out.println("				strFieldValue = String.valueOf(" + FieldCode + ");");
				}
				out.println("				break;");
			}
			out.println("			default:");
			out.println("				strFieldValue = \"\";");
			out.println("		};");
			out.println("		if( strFieldValue.equals(\"\") ) {");
			out.println("			strFieldValue = \"null\";");
			out.println("		}");
			out.println("		return strFieldValue;");
			out.println("	}");

			// 生成 setV 函数
			out.println();
			out.println("	/**");
			out.println("	* 设置对应传入参数的String形式的字段值");
			out.println("	* @param: FCode String 需要赋值的对象");
			out.println("	* @param: FValue String 要赋的值");
			out.println("	* @return: boolean");
			out.println("	**/");
			out.println("	public boolean setV(String FCode ,String FValue)");
			out.println("	{");
			out.println("		if( StrTool.cTrim( FCode ).equals( \"\" ))");
			out.println("			return false;");
			out.println("");
			for (int i = 0; i < n; i++)
			{
				FieldInfo f = (FieldInfo) fields.get(i);
				String FieldCode = f.getFieldCode();
				String FieldType = f.getFieldType();
				if (i == 0)
				{
					out.println("		if (FCode.equalsIgnoreCase(\"" + FieldCode + "\"))");
				}
				else
				{
					out.println("		else if (FCode.equalsIgnoreCase(\"" + FieldCode + "\"))");
				}
				out.println("		{");
				if (FieldType.equals("Date"))
				{
					out.println("			if( FValue != null && !FValue.equals(\"\") )");
					out.println("			{");
					out.println("				" + FieldCode + " = fDate.getDate( FValue );");
					out.println("			}");
					out.println("			else");
					out.println("				" + FieldCode + " = null;");
				}
				else if (FieldType.equals("float"))
				{
					out.println("			if( FValue != null && !FValue.equals(\"\"))");
					out.println("			{");
					out.println("				Float tFloat = new Float( FValue );");
					out.println("				float f = tFloat.floatValue();");
					out.println("				" + FieldCode + " = f;");
					out.println("			}");
				}
				else if (FieldType.equals("double"))
				{
					// add by yt 2003-6-20
					out.println("			if( FValue != null && !FValue.equals(\"\"))");
					out.println("			{");
					out.println("				Double tDouble = new Double( FValue );");
					out.println("				double d = tDouble.doubleValue();");
					out.println("				" + FieldCode + " = d;");
					out.println("			}");
				}
				else if (FieldType.equals("int"))
				{
					out.println("			if( FValue != null && !FValue.equals(\"\"))");
					out.println("			{");
					out.println("				Integer tInteger = new Integer( FValue );");
					out.println("				int i = tInteger.intValue();");
					out.println("				" + FieldCode + " = i;");
					out.println("			}");
				}
				else if (FieldType.equals("String"))
				{
					out.println("			if( FValue != null)");
					out.println("			{");
					out.println("				" + FieldCode + " = FValue.trim();");
					out.println("			}");
					out.println("			else");
					out.println("				" + FieldCode + " = null;");
				}
				out.println("		}");
			}
			out.println("		return true;");
			out.println("	}");
			out.println("");

			// 生成 equals 方法
			out.println("	public boolean equals(Object otherObject)");
			out.println("	{");
			out.println("		if (this == otherObject) return true;");
			out.println("		if (otherObject == null) return false;");
			out.println("		if (getClass() != otherObject.getClass()) return false;");
			out.println("		" + ClassName + " other = (" + ClassName + ")otherObject;");
			out.println("		return");
			for (int i = 0; i < n; i++)
			{
				FieldInfo f = (FieldInfo) fields.get(i);
				String FieldCode = f.getFieldCode();
				String FieldType = f.getFieldType();
				String relation = "";
				String endChar = "";
				if (i != 0)
				{
					relation = "&& ";
				}
				if (i == n - 1)
				{
					endChar = ";";
				}
				if (FieldType.equals("Date"))
				{
					out.println("			" + relation + "fDate.getString(" + FieldCode + ")" + ".equals(other.get"
							+ FieldCode + "())" + endChar);
				}
				else if (FieldType.equals("String"))
				{
					out.println("			" + relation + FieldCode + ".equals(other.get" + FieldCode + "())" + endChar);
				}
				else if (FieldType.equals("float") || FieldType.equals("int") || FieldType.equals("double"))
				{
					// modify by yt 2003-6-20
					out.println("			" + relation + FieldCode + " == other.get" + FieldCode + "()" + endChar);
				}
				else if (FieldType.equals("InputStream"))
				{
					out.println("			" + endChar);
				}
			} // end of for
			out.println("	}");

			// 生成 getFieldCount 方法
			out.println();
			out.println("	/**");
			out.println("	* 取得Schema拥有字段的数量");
			out.println("       * @return: int");
			out.println("	**/");
			out.println("	public int getFieldCount()");
			out.println("	{");
			out.println(" 		return FIELDNUM;");
			out.println("	}");

			// 生成 int getFieldIndex(String strFieldName) 方法
			out.println();
			out.println("	/**");
			out.println("	* 取得Schema中指定字段名所对应的索引值");
			out.println("	* 如果没有对应的字段，返回-1");
			out.println("       * @param: strFieldName String");
			out.println("       * @return: int");
			out.println("	**/");
			out.println("	public int getFieldIndex(String strFieldName)");
			out.println("	{");
			for (int i = 0; i < n; i++)
			{
				FieldInfo f = (FieldInfo) fields.get(i);

				if (i == 0)
				{
					out.println("		if( strFieldName.equals(\"" + f.getFieldCode() + "\") ) {");
				}
				else
				{
					out.println("		else if( strFieldName.equals(\"" + f.getFieldCode() + "\") ) {");
				}
				out.println("			return " + String.valueOf(i) + ";");
				out.println("		}");
			}
			out.println("		return -1;");
			out.println("	}");

			// 生成 String getFieldName(int nFieldIndex) 方法
			out.println();
			out.println("	/**");
			out.println("	* 取得Schema中指定索引值所对应的字段名");
			out.println("	* 如果没有对应的字段，返回\"\"");
			out.println("       * @param: nFieldIndex int");
			out.println("       * @return: String");
			out.println("	**/");
			out.println("	public String getFieldName(int nFieldIndex)");
			out.println("	{");
			out.println("		String strFieldName = \"\";");
			out.println("		switch(nFieldIndex) {");
			for (int i = 0; i < n; i++)
			{
				FieldInfo f = (FieldInfo) fields.get(i);

				out.println("			case " + String.valueOf(i) + ":");
				out.println("				strFieldName = \"" + f.getFieldCode() + "\";");
				out.println("				break;");
			}
			out.println("			default:");
			out.println("				strFieldName = \"\";");
			out.println("		};");
			out.println("		return strFieldName;");
			out.println("	}");

			// 生成 int getFieldType(String strFieldName) 方法
			out.println();
			out.println("	/**");
			out.println("	* 取得Schema中指定字段名所对应的字段类型");
			out.println("	* 如果没有对应的字段，返回Schema.TYPE_NOFOUND");
			out.println("       * @param: strFieldName String");
			out.println("       * @return: int");
			out.println("	**/");
			out.println("	public int getFieldType(String strFieldName)");
			out.println("	{");
			for (int i = 0; i < n; i++)
			{
				FieldInfo f = (FieldInfo) fields.get(i);

				String FieldCode = f.getFieldCode();
				String FieldType = f.getFieldType(); // java对象的属性
				String strFieldType = "Schema.TYPE_NOFOUND";

				if (i == 0)
				{
					out.println("		if( strFieldName.equals(\"" + FieldCode + "\") ) {");
				}
				else
				{
					out.println("		else if( strFieldName.equals(\"" + FieldCode + "\") ) {");
				}

				if (FieldType.equals("String"))
				{
					strFieldType = "Schema.TYPE_STRING";

				}
				else if (FieldType.equals("Date"))
				{
					strFieldType = "Schema.TYPE_DATE";

				}
				else if (FieldType.equals("int"))
				{
					strFieldType = "Schema.TYPE_INT";

				}
				else if (FieldType.equals("float"))
				{
					strFieldType = "Schema.TYPE_FLOAT";

				}
				else if (FieldType.equals("double"))
				{
					strFieldType = "Schema.TYPE_DOUBLE";

				}
				else
				{
					strFieldType = "Schema.TYPE_NOFOUND";
				}

				out.println("			return " + strFieldType + ";");
				out.println("		}");
			}
			out.println("		return Schema.TYPE_NOFOUND;");
			out.println("	}");

			// 生成 int getFieldType(int nFieldIndex) 方法
			out.println();
			out.println("	/**");
			out.println("	* 取得Schema中指定索引值所对应的字段类型");
			out.println("	* 如果没有对应的字段，返回Schema.TYPE_NOFOUND");
			out.println("       * @param: nFieldIndex int");
			out.println("       * @return: int");
			out.println("	**/");
			out.println("	public int getFieldType(int nFieldIndex)");
			out.println("	{");
			out.println("		int nFieldType = Schema.TYPE_NOFOUND;");
			out.println("		switch(nFieldIndex) {");
			for (int i = 0; i < n; i++)
			{
				FieldInfo f = (FieldInfo) fields.get(i);
				String FieldType = f.getFieldType();
				String strFieldType = "Schema.TYPE_NOFOUND";

				out.println("			case " + String.valueOf(i) + ":");

				if (FieldType.equals("String"))
				{
					strFieldType = "Schema.TYPE_STRING";
				}
				else if (FieldType.equals("Date"))
				{
					strFieldType = "Schema.TYPE_DATE";
				}
				else if (FieldType.equals("int"))
				{
					strFieldType = "Schema.TYPE_INT";
				}
				else if (FieldType.equals("float"))
				{
					strFieldType = "Schema.TYPE_FLOAT";
				}
				else if (FieldType.equals("double"))
				{
					strFieldType = "Schema.TYPE_DOUBLE";
				}
				else
				{
					strFieldType = "Schema.TYPE_NOFOUND";
				}

				out.println("				nFieldType = " + strFieldType + ";");
				out.println("				break;");
			}
			out.println("			default:");
			out.println("				nFieldType = Schema.TYPE_NOFOUND;");
			out.println("		};");
			out.println("		return nFieldType;");
			out.println("	}");

			// 生成 int[] getFieldSQLType(int nFieldIndex) 方法
			out.println();
			out.println("	/**");
			out.println("	* 取得Schema中指定索引值所对应的字段数据库类型");
			out.println("	* 如果没有对应的字段，返回Types.VARCHAR");
			out.println("   * @param: nFieldIndex int");
			out.println("   * @return: int[] ,{Types.CHA),12}; ");
			out.println("	* Types.CHAR 为特殊类型需提供字段长度；其他可不设置字段长度。");
			out.println("	**/");
			out.println("	public int[] getFieldSQLType(int nFieldIndex)");
			out.println("	{");
			out.println("		int[] fieldSQLType = new int[2];");
			out.println("		switch(nFieldIndex) {");
			for (int i = 0; i < n; i++)
			{
				FieldInfo f = (FieldInfo) fields.get(i);
				int FieldSQLType = f.getFieldSQLType();
				String strFieldType = "Types.NULL";

				out.println("			case " + String.valueOf(i) + ":");
				switch (FieldSQLType)
				{
					case Types.VARCHAR:
						strFieldType = "Types.VARCHAR";
						break;
					case Types.DATE:
						strFieldType = "Types.DATE";
						break;
					case Types.DOUBLE:
					case Types.FLOAT:
					case Types.NUMERIC:
						strFieldType = "Types.DOUBLE";
						break;
					case Types.INTEGER:
						strFieldType = "Types.INTEGER";
						break;
					case Types.CHAR:
						strFieldType = "Types.CHAR";
						out.println("				fieldSQLType[1] = " + f.getFieldLength() + ";");
						break;
					case Types.BLOB:
						strFieldType = "Types.BLOB";
						break;
					default:
						break;

				};

				out.println("				fieldSQLType[0] = " + strFieldType + ";");
				out.println("				break;");
			}
			out.println("			default:");
			out.println("				fieldSQLType[0] = Types.NULL;");
			out.println("		};");
			out.println("		return fieldSQLType;");
			out.println("	}");

			// 生成结尾
			out.println("}");

			System.out.println("Create file success!!");
			mFlag = false;
		} // end of try
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}

	// 返回encode的对每一个字段的编码串值
	private static String getEnCodeStringLine(boolean b, String strColName, String strColType)
	{
		String str = ""; // 一行代码编码串

		if (strColType.equals("String") || strColType.equals("Date"))
		{
			if (strColType.equals("Date"))
			{
				strColName = "fDate.getString( " + strColName + " )";
			}

			if (b)
			{
				str = "StrTool.cTrim(" + strColName + "));";
			}
			else
			{
				str = "StrTool.cTrim(" + strColName + ")); strReturn.append(SysConst.PACKAGESPILTER);";
			}
		}
		else
		{
			if (b)
			{
				if (strColType.equals("InputStream"))
				{
					str = " 1 );";
				}
				else
				{
					str = " ChgData.chgData(" + strColName + "));";
				}
			}
			else
			{
				if (strColType.equals("InputStream"))
				{
					str = " 1 );strReturn.append(SysConst.PACKAGESPILTER);";
				}
				else
				{
					str = " ChgData.chgData(" + strColName + "));strReturn.append(SysConst.PACKAGESPILTER);";
				}
			}
		}
		return str;
	}

	// 返回 decode的对每一个字段的编码串值
	private static String getDeCodeStringLine(int i, String strColName, String strColType)
	{
		String str = ""; // 一行代码编码串

		if (strColType.equals("String"))
		{
			str = strColName + " = StrTool.getStr(strMessage, " + (i + 1) + ", SysConst.PACKAGESPILTER );";
		}
		else if (strColType.equals("int"))
		{
			str = strColName + "= new Integer(ChgData.chgNumericStr(StrTool.getStr(" + "strMessage," + (i + 1)
					+ ",SysConst.PACKAGESPILTER))).intValue();";
		}
		else if (strColType.equals("float"))
		{
			str = strColName + " = new Float(ChgData.chgNumericStr(StrTool.getStr(" + "strMessage," + (i + 1)
					+ ",SysConst.PACKAGESPILTER))).floatValue();";
		}
		else if (strColType.equals("double"))
		{
			// add by yt 2003-6-20
			str = strColName + " = new Double(ChgData.chgNumericStr(StrTool.getStr(" + "strMessage," + (i + 1)
					+ ",SysConst.PACKAGESPILTER))).doubleValue();";
		}
		else if (strColType.equals("Date"))
		{
			str = strColName + " = fDate.getDate(StrTool.getStr(strMessage, " + (i + 1) + ",SysConst.PACKAGESPILTER));";
		}
		return str;
	}
}