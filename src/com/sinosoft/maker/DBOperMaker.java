package com.sinosoft.maker;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.sql.Types;
import java.util.Calendar;
import java.util.Vector;

import com.sinosoft.utility.JdbcUrl;
import com.sinosoft.utility.SysConst;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * ClassName: DBOperMaker
 * </p>
 * <p>
 * Description: 生成DB文件
 * </p>
 * @author HST、ZhuXF
 * @version 1.2
 * @date: 2002-06-17
 * @modify 2007-8-27
 * @depict 人生五十載，去事恍如夢幻，普天之下，豈有長生不滅者！
 */
public class DBOperMaker
{
	// @Field
	private String DBName;

	private String TableName;

	private String FileName;

	private TableInfo table;

	private boolean mNoBlobCol = true;

	private boolean EXISTSCHAR = false;// 是否存在CHAR类型的字段

	// @Constructor
	public DBOperMaker(String fName)
	{
		FileName = fName;
	}

	public DBOperMaker(String fName, String t)
	{
		FileName = fName;
		TableName = t;
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
				TableName = table.getTableCode();
				table = db.getOneTable(TableName);
				Vector f = db.getFields(table);
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
		String Path = ".\\src\\com\\sinosoft\\lis\\db\\";
		String ClassName = TableName + "DB";
		FileName = ClassName + ".java";
		String SchemaName = TableName + "Schema";
		String SetName = TableName + "Set";

		// 是否加密标志
		boolean tJKFlag = false;
		try
		{
			System.out.println("Creating file ......");
			System.out.println("File Name : " + FileName);

			out = new PrintWriter(new FileWriter(new File(Path + FileName)), true);

			// 判断是否存在CHAR类型的字段
			int n = fields.size();
			for (int i = 0; i < n; i++)
			{
				FieldInfo f = (FieldInfo) fields.get(i);
				int fileSQLType = f.getFieldSQLType();
				if (Types.CHAR == fileSQLType)
				{
					EXISTSCHAR = true;
					break;
				}
			}

			// 文件头信息
			out.println("/**");
			out.println(" * Copyright (c) 2002 sinosoft  Co. Ltd.");
			out.println(" * All right reserved.");
			out.println(" */");
			out.println("");
			// @Package
			out.println("package com.sinosoft.lis.db;");
			out.println("");
			// @Import
			out.println("import java.sql.*;");
			out.println("import com.sinosoft.lis.pubfun.PubFun;");
			out.println("import com.sinosoft.lis.schema." + SchemaName + ";");
			out.println("import com.sinosoft.lis.vschema." + SetName + ";");
			out.println("import com.sinosoft.utility.*;");
			if (SysConst.JKTABLE.indexOf(TableName + "-") >= 0)
			{
				tJKFlag = true;
				out.println("import com.sinosoft.lis.JKEncrypt.LJKEncrypt;");// 11-13
			}
			out.println("");
			// 类信息
			out.println("/**");
			out.println(" * <p>ClassName: " + ClassName + " </p>");
			out.println(" * <p>Description: DB层数据库操作类文件 </p>");
			out.println(" * <p>Copyright: Copyright (c) 2002</p>");
			out.println(" * <p>Company: sinosoft </p>");
			out.println(" * @Database: " + DBName);
			out.println(" * @CreateDate：" + getDate());
			out.println(" */");
			// @Begin
			out.println("public class " + ClassName + " extends " + SchemaName);
			out.println("{");

			// 生成数据成员定义
			out.println("	// @Field");
			out.println("	private Connection con;");
			out.println("	private DBOper db;");
			out.println("	/**");
			out.println("	* flag = true: 传入Connection");
			out.println("	* flag = false: 不传入Connection");
			out.println("	**/");
			out.println("	private boolean mflag = false;");
			out.println("");
			out.println("	public CErrors mErrors = new CErrors();		// 错误信息");
			out.println("");
			out.println("	/**");
			out.println("	 * 为批量操作而准备的语句和游标对象");
			out.println("	 */");
			out.println("	private ResultSet mResultSet = null;");
			out.println("	private Statement mStatement = null;");

			// 生成构建器
			out.println("	// @Constructor");
			out.println("	public " + ClassName + "( Connection tConnection )");
			out.println("	{");
			out.println("		con = tConnection;");
			out.println("		db = new DBOper( con, \"" + TableName + "\" );");
			out.println("		mflag = true;");
			out.println("	}");
			out.println("");
			out.println("	public " + ClassName + "()");
			out.println("	{");
			out.println("		con = null;");
			out.println("		db = new DBOper( \"" + TableName + "\" );");
			out.println("		mflag = false;");
			out.println("	}");
			out.println("");

			// 生成方法
			// 2005-11-14 Kevin
			out.println("	// @Method");
			for (int i = 1; i <= 4; i++)
			{
				String oper = "";
				switch (i)
				{
					case 1: // insert
						oper = "insert";
						break;
					case 2: // update
						oper = "update";
						break;
					case 3: // deleteSQL
						oper = "deleteSQL";
						break;
					case 4: // delete
						oper = "delete";
						break;
				} // end of switch

				if (i == 1 || i == 2 || i == 4)
				{
					// 2005-11-14 Kevin
					// 修改了delete和update的生成方法，所以，在此处直接退出。
					continue;
				}

				// 生成 insert、update、deleteSQL, delete 方法
				out.println("	public boolean " + oper + "()");
				out.println("	{");
				out.println("		" + SchemaName + " tSchema = this.getSchema();");
				out.println("		if (db." + oper + "(tSchema))");
				out.println("		{");
				out.println("		     return true;");
				out.println("		}");
				out.println("		else");
				out.println("		{");
				out.println("			// @@错误处理");
				out.println("			this.mErrors.copyAllErrors(db.mErrors);");
				out.println("			CError tError = new CError();");
				out.println("			tError.moduleName = \"" + ClassName + "\";");
				out.println("			tError.functionName = \"" + oper + "\";");
				out.println("			tError.errorMessage = \"操作失败!\";");
				out.println("			this.mErrors .addOneError(tError);");
				out.println("			return false;");
				out.println("		}");
				out.println("	}");
				out.println("");
			} // end of for

			// 2005-11-11 Kevin
			// 生成按主键操作的WHERE子句，这个子句在下面的多个部分都会用到。
			// 生成UPDATE操作所需要的所有字段的字句。例如，ContNo = ? , PolNo = ?
			StringBuffer sbPKWhereClause = new StringBuffer(50);
			StringBuffer sbInsertColumnClause = new StringBuffer(300);
			StringBuffer sbUpdateColumnClause = new StringBuffer(300);

			for (int i = 0; i < fields.size(); i++)
			{
				FieldInfo f = (FieldInfo) fields.get(i);
				String FieldProperty = f.getFieldProperty();

				sbInsertColumnClause.append(" , ?");
				sbUpdateColumnClause.append(" , ").append(f.getFieldCode()).append(" = ?");

				if (FieldProperty.equals("PK"))
				{
					sbPKWhereClause.append(" AND ").append(f.getFieldCode()).append(" = ?");
				}
			}
			sbPKWhereClause = sbPKWhereClause.delete(0, 4);
			sbInsertColumnClause = sbInsertColumnClause.delete(0, 2);
			sbUpdateColumnClause = sbUpdateColumnClause.delete(0, 2);

			int nFieldIndex = 0;
			int nParamIndex = 1; // 这个变量在下面的程序段中会经常用到，所以在此处统一定义。

			// 生成 getCount 方法
			out.println("	public int getCount()");
			out.println("	{");
			out.println("		" + SchemaName + " tSchema = this.getSchema();");
			out.println();
			out.println("		int tCount = db.getCount(tSchema);");
			out.println("		if (tCount < 0)");
			out.println("		{");
			out.println("			// @@错误处理");
			out.println("			this.mErrors.copyAllErrors(db.mErrors);");
			out.println("			CError tError = new CError();");
			out.println("			tError.moduleName = \"" + ClassName + "\";");
			out.println("			tError.functionName = \"getCount\";");
			out.println("			tError.errorMessage = \"操作失败!\";");
			out.println("			this.mErrors .addOneError(tError);");
			out.println("");
			out.println("			return -1;");
			out.println("		}");
			out.println("");
			out.println("		return tCount;");
			out.println("	}");
			out.println("");

			// 2005-11-14 Kevin
			// 修改delete方法，使用preparedStatement方法。
			out.println("	public boolean delete()");
			out.println("	{");
			out.println("		PreparedStatement pstmt = null;");
			out.println();
			out.println("		if( !mflag ) {");
			out.println("			con = DBConnPool.getConnection();");
			out.println("		}");
			out.println();
			out.println("		try");
			out.println("		{");
			out.println("			pstmt = con.prepareStatement(\"DELETE FROM " + TableName + " WHERE " + sbPKWhereClause
					+ "\");");

			// 生成设置参数的部分。
			nParamIndex = 1;
			for (nFieldIndex = 0; nFieldIndex < fields.size(); nFieldIndex++)
			{
				FieldInfo f = (FieldInfo) fields.get(nFieldIndex);
				String FieldProperty = f.getFieldProperty();
				if (FieldProperty.equals("PK"))
				{
					genSetParamString(f, nParamIndex++, "pstmt", out, "			", true);
				}
			}

			out.println("			pstmt.executeUpdate();");
			out.println("			pstmt.close();");
			out.println("		} catch (Exception ex) {");
			out.println("			ex.printStackTrace();");
			out.println("			// @@错误处理");
			out.println("			this.mErrors.copyAllErrors(db.mErrors);");
			out.println("			CError tError = new CError();");
			out.println("			tError.moduleName = \"" + ClassName + "\";");
			out.println("			tError.functionName = \"delete()\";");
			out.println("			tError.errorMessage = ex.toString();");
			out.println("			this.mErrors .addOneError(tError);");
			out.println();
			out.println("		// only for debug purpose");
			out.println("		SQLString sqlObj = new SQLString(\"" + TableName + "\");");
			out.println("		sqlObj.setSQL(4, this);");
			out.println("		sqlObj.getSQL();");

			String tSql = "";
			boolean tFlag = false;
			int tPKCount = 0;
			// 如果有blob字段信息，则无需输出脚本，因为此类表不走下面的delete、insert和update操作
			if (mNoBlobCol)
			{
//				out.println("//真实的执行语句");
//				tSql = "\"True Error Sql is : DELETE FROM " + TableName + " WHERE ";
//				tFlag = false;
//				tPKCount = 0;
//				for (nFieldIndex = 0; nFieldIndex < fields.size(); nFieldIndex++)
//				{
//					FieldInfo f = (FieldInfo) fields.get(nFieldIndex);
//					String FieldProperty = f.getFieldProperty();
//					if (FieldProperty.equals("PK"))
//					{
//						if (tFlag)
//						{
//							tSql += " + \" and ";
//						}
//						tPKCount++;
//						tSql += genSetValueString(f);
//						tFlag = true;
//					}
//				}
//				// 如果没有主键的情况下
//				if (tPKCount == 0)
//				{
//					tSql += "1=1 \"";
//				}
//				out.println("System.out.println(String.valueOf(" + tSql + ").replaceAll(\"'null'\", \"null\"));");
			}

			out.println();
			out.println("			try {");
			out.println("				pstmt.close();");
			out.println("			} catch (Exception e){}");
			out.println();
			out.println("			if( !mflag ) {");
			out.println("				try {");
			out.println("					con.close();");
			out.println("				} catch (Exception e){}");
			out.println("			}");
			out.println();
			out.println("			return false;");
			out.println("		}");
			out.println();
			out.println("		if( !mflag ) {");
			out.println("			try {");
			out.println("				con.close();");
			out.println("			} catch (Exception e){}");
			out.println("		}");
			out.println();
			out.println("		return true;");
			out.println("	}");
			out.println("");
			// 2005-11-14 Kevin
			// 修改delete函数的生成方法

			// 2005-11-14 Kevin
			// 修改update方法，使用preparedStatement方法。
			out.println("	public boolean update()");
			out.println("	{");
			out.println("		PreparedStatement pstmt = null;");
			out.println();
			out.println("		if( !mflag ) {");
			out.println("			con = DBConnPool.getConnection();");
			out.println("		}");
			out.println();
			out.println();
			out.println("		try");
			out.println("		{");
			out.println("			pstmt = con.prepareStatement(\"UPDATE " + TableName + " SET " + sbUpdateColumnClause
					+ " WHERE " + sbPKWhereClause + "\");");

			if (tJKFlag)
			{
				out.println("           //**********************加密代码*************************");// 11-13
				out.println("           LJKEncrypt tLJKEncrypt = new LJKEncrypt();");// 11-13
				out.println("           this.setSchema(tLJKEncrypt." + TableName + "toEncrypt(con, this));");// 11-13
				out.println("           //******************************************************");// 11-13
			}

			// 生成设置数据的部分。
			nParamIndex = 1;
			for (nFieldIndex = 0; nFieldIndex < fields.size(); nFieldIndex++)
			{
				FieldInfo f = (FieldInfo) fields.get(nFieldIndex);
				genSetParamString(f, nParamIndex++, "pstmt", out, "			", false);
			}
			out.println("			// set where condition");
			// 继续生成设置条件的部分。
			for (nFieldIndex = 0; nFieldIndex < fields.size(); nFieldIndex++)
			{
				FieldInfo f = (FieldInfo) fields.get(nFieldIndex);
				String FieldProperty = f.getFieldProperty();
				if (FieldProperty.equals("PK"))
				{
					genSetParamString(f, nParamIndex++, "pstmt", out, "			", true);
				}
			}

			out.println("			pstmt.executeUpdate();");
			out.println("			pstmt.close();");
			out.println("		} catch (Exception ex) {");
			out.println("			ex.printStackTrace();");
			out.println("			// @@错误处理");
			out.println("			this.mErrors.copyAllErrors(db.mErrors);");
			out.println("			CError tError = new CError();");
			out.println("			tError.moduleName = \"" + ClassName + "\";");
			out.println("			tError.functionName = \"update()\";");
			out.println("			tError.errorMessage = ex.toString();");
			out.println("			this.mErrors .addOneError(tError);");
			out.println();
			out.println("		// only for debug purpose");
			out.println("		SQLString sqlObj = new SQLString(\"" + TableName + "\");");
			out.println("		sqlObj.setSQL(2, this);");
			out.println("		sqlObj.getSQL();");

			if (mNoBlobCol)
			{
//				out.println("//真实的执行语句");
//				tSql = "\"True Error Sql is : UPDATE " + TableName + " SET ";
//				tFlag = false;
//				for (nFieldIndex = 0; nFieldIndex < fields.size(); nFieldIndex++)
//				{
//					FieldInfo f = (FieldInfo) fields.get(nFieldIndex);
//					if (tFlag)
//					{
//						tSql += " + \" , ";
//					}
//					tSql += genSetValueString(f);
//					tFlag = true;
//				}
//				tSql += "+\" WHERE ";
//				tFlag = false;
//				tPKCount = 0;
//				// 设置更新条件
//				for (nFieldIndex = 0; nFieldIndex < fields.size(); nFieldIndex++)
//				{
//					FieldInfo f = (FieldInfo) fields.get(nFieldIndex);
//					String FieldProperty = f.getFieldProperty();
//					if (FieldProperty.equals("PK"))
//					{
//						if (tFlag)
//						{
//							tSql += " + \" and ";
//						}
//						tPKCount++;
//						tSql += genSetValueString(f);
//						tFlag = true;
//					}
//				}
//				// 如果没有主键的情况下
//				if (tPKCount == 0)
//				{
//					tSql += "1=1 \"";
//				}
//				out.println("System.out.println(String.valueOf(" + tSql + ").replaceAll(\"'null'\", \"null\"));");
				// out.println("System.out.println(" + tSql + ");");
			}

			out.println();
			out.println("			try {");
			out.println("				pstmt.close();");
			out.println("			} catch (Exception e){}");
			out.println();
			out.println("			if( !mflag ) {");
			out.println("				try {");
			out.println("					con.close();");
			out.println("				} catch (Exception e){}");
			out.println("			}");
			out.println();
			out.println("			return false;");
			out.println("		}");
			out.println();
			out.println("		if( !mflag ) {");
			out.println("			try {");
			out.println("				con.close();");
			out.println("			} catch (Exception e){}");
			out.println("		}");
			out.println();
			out.println("		return true;");
			out.println("	}");
			out.println("");
			// 2005-11-14 Kevin
			// 修改update函数的生成方法

			// 2005-11-14 Kevin
			// 修改insert方法，使用preparedStatement方法。
			out.println("	public boolean insert()");
			out.println("	{");
			out.println("		PreparedStatement pstmt = null;");
			out.println();
			out.println("		if( !mflag ) {");
			out.println("			con = DBConnPool.getConnection();");
			out.println("		}");
			out.println();
			out.println();
			out.println("		try");
			out.println("		{");
			out.println("			pstmt = con.prepareStatement(\"INSERT INTO " + TableName + " VALUES("
					+ sbInsertColumnClause + ")\");");
			if (tJKFlag)
			{
				out.println("           //**********************加密代码*************************");// 11-13
				out.println("           LJKEncrypt tLJKEncrypt = new LJKEncrypt();");// 11-13
				out.println("           this.setSchema(tLJKEncrypt." + TableName + "toEncrypt(con, this));");// 11-13
				out.println("           //******************************************************");// 11-13
			}

			// 生成设置数据的部分。
			nParamIndex = 1;
			for (nFieldIndex = 0; nFieldIndex < fields.size(); nFieldIndex++)
			{
				FieldInfo f = (FieldInfo) fields.get(nFieldIndex);
				genSetParamString(f, nParamIndex++, "pstmt", out, "			", false);
			}
			out.println("			// execute sql");
			out.println("			pstmt.executeUpdate();");
			out.println("			pstmt.close();");
			out.println("		} catch (Exception ex) {");
			out.println("			ex.printStackTrace();");
			out.println("			// @@错误处理");
			out.println("			this.mErrors.copyAllErrors(db.mErrors);");
			out.println("			CError tError = new CError();");
			out.println("			tError.moduleName = \"" + ClassName + "\";");
			out.println("			tError.functionName = \"insert()\";");
			out.println("			tError.errorMessage = ex.toString();");
			out.println("			this.mErrors .addOneError(tError);");
			out.println();
			out.println("		// only for debug purpose");
			out.println("		SQLString sqlObj = new SQLString(\"" + TableName + "\");");
			out.println("		sqlObj.setSQL(1, this);");
			out.println("		sqlObj.getSQL();");

			if (mNoBlobCol)
			{
//				out.println("//真实的执行语句");
//				tSql = "\"True Error Sql is : INSERT INTO " + TableName + " VALUES(";
//				tFlag = false;
//				for (nFieldIndex = 0; nFieldIndex < fields.size(); nFieldIndex++)
//				{
//					if (tFlag)
//					{
//						tSql += "+\",";
//					}
//					FieldInfo f = (FieldInfo) fields.get(nFieldIndex);
//					int nFieldType = f.getFieldSQLType();
//					switch (nFieldType)
//					{
//						case Types.CHAR:
//						case Types.VARCHAR:
//						case Types.DATE:
//							tSql += "'\"+" + "this.get" + f.getFieldCode() + "() + \"'\"";
//							break;
//						case Types.INTEGER:
//						case Types.NUMERIC:
//							tSql += "\"+" + "this.get" + f.getFieldCode() + "()";
//							break;
//						default:
//							System.out.println("unsupported field type");
//					}
//					tFlag = true;
//				}
//				tSql += "+\")\"";
//				// out.println("System.out.println(" + tSql + ");");
//				out.println("System.out.println(String.valueOf(" + tSql + ").replaceAll(\"'null'\", \"null\"));");
			}

			out.println();
			out.println("			try {");
			out.println("				pstmt.close();");
			out.println("			} catch (Exception e){}");
			out.println();
			out.println("			if( !mflag ) {");
			out.println("				try {");
			out.println("					con.close();");
			out.println("				} catch (Exception e){}");
			out.println("			}");
			out.println();
			out.println("			return false;");
			out.println("		}");
			out.println();
			out.println("		if( !mflag ) {");
			out.println("			try {");
			out.println("				con.close();");
			out.println("			} catch (Exception e){}");
			out.println("		}");
			out.println();
			out.println("		return true;");
			out.println("	}");
			out.println("");
			// 2005-11-14 Kevin
			// 修改insert函数的生成方法

			// 生成 getInfo方法
			out.println("	public boolean getInfo()");
			out.println("	{");
			out.println("		PreparedStatement pstmt = null;");
			out.println("		ResultSet rs = null;");
			out.println();
			out.println("		if( !mflag ) {");
			out.println("			con = DBConnPool.getConnection();");
			out.println("		}");
			out.println();
			out.println("		try");
			out.println("		{");
			out.println("			pstmt = con.prepareStatement(\"SELECT * FROM " + TableName + " WHERE " + sbPKWhereClause
					+ "\", ");
			out.println("				ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);");
			nParamIndex = 1;
			for (nFieldIndex = 0; nFieldIndex < fields.size(); nFieldIndex++)
			{
				FieldInfo f = (FieldInfo) fields.get(nFieldIndex);
				String FieldProperty = f.getFieldProperty();
				if (FieldProperty.equals("PK"))
				{
					genSetParamString(f, nParamIndex++, "pstmt", out, "			", true);
				}
			}

			out.println("			rs = pstmt.executeQuery();");
			out.println("			int i = 0;");
			out.println("			while (rs.next())");
			out.println("			{");
			out.println("				i++;");
			out.println("				if (!this.setSchema(rs,i))");
			out.println("				{");
			out.println("					// @@错误处理");
			out.println("					CError tError = new CError();");
			out.println("					tError.moduleName = \"" + ClassName + "\";");
			out.println("					tError.functionName = \"getInfo\";");
			out.println("					tError.errorMessage = \"取数失败!\";");
			out.println("					this.mErrors .addOneError(tError);");
			out.println("");
			out.println("					try{ rs.close(); } catch( Exception ex ) {}");
			out.println("					try{ pstmt.close(); } catch( Exception ex1 ) {}");
			out.println("");
			out.println("					if (!mflag)");
			out.println("					{");
			out.println("						try");
			out.println("						{");
			out.println("							con.close();");
			out.println("						}");
			out.println("						catch(Exception et){}");
			out.println("					}");
			out.println("					return false;");
			out.println("				}");
			out.println("				break;");
			out.println("			}");
			out.println("			try{ rs.close(); } catch( Exception ex2 ) {}");
			out.println("			try{ pstmt.close(); } catch( Exception ex3 ) {}");
			out.println("");
			out.println("			if( i == 0 )");
			out.println("			{");
			out.println("				if (!mflag)");
			out.println("				{");
			out.println("					try");
			out.println("					{");
			out.println("						con.close();");
			out.println("					}");
			out.println("					catch(Exception et){}");
			out.println("				}");
			out.println("				return false;");
			out.println("			}");
			out.println("		}");
			out.println("		catch(Exception e)");
			out.println("	    {");
			out.println("			e.printStackTrace();");
			out.println("			// @@错误处理");
			out.println("			CError tError = new CError();");
			out.println("			tError.moduleName = \"" + ClassName + "\";");
			out.println("			tError.functionName = \"getInfo\";");
			out.println("			tError.errorMessage = e.toString();");
			out.println("			this.mErrors .addOneError(tError);");
			out.println("");
			out.println("			try{ rs.close(); } catch( Exception ex ) {}");
			out.println("			try{ pstmt.close(); } catch( Exception ex1 ) {}");
			out.println("");
			out.println("			if (!mflag)");
			out.println("			{");
			out.println("				try");
			out.println("				{");
			out.println("					con.close();");
			out.println("				}");
			out.println("				catch(Exception et){}");
			out.println("			}");
			out.println("			return false;");
			out.println("	    }");
			out.println("	    // 断开数据库连接");
			out.println("		if (!mflag)");
			out.println("		{");
			out.println("			try");
			out.println("			{");
			out.println("				con.close();");
			out.println("			}");
			out.println("			catch(Exception e){}");
			out.println("		}");
			out.println("");
			out.println("		return true;");
			out.println("	}");
			out.println("");

			// 生成 query 方法
			out.println("	public " + SetName + " query()");
			out.println("	{");
			// 数据库表存在CHAR类型的字段
			EXISTSCHAR = true;
			if (EXISTSCHAR)
			{
				out.println("		Statement stmt = null;");
			}
			else
			{
				out.println("		PreparedStatement pstmt = null;");
			}
			out.println("		ResultSet rs = null;");
			out.println("		" + SetName + " a" + SetName + " = new " + SetName + "();");
			out.println();
			out.println("	  if( !mflag ) {");
			out.println("		  con = DBConnPool.getConnection();");
			out.println("		}");
			out.println();
			out.println("		try");
			out.println("		{");
			out.println("			SQLString sqlObj = new SQLString(\"" + TableName + "\");");
			out.println("			" + SchemaName + " aSchema = this.getSchema();");

			if (EXISTSCHAR)
			{
				out.println("			sqlObj.setSQL(5,aSchema);");
				out.println("			String sql = sqlObj.getSQL();");
				out.println("			stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);");
				out.println("			rs = stmt.executeQuery(sql);");

			}
			else
			{
				out.println("			sqlObj.setSQL(8, aSchema);//采用绑定参数形式的SQL语句");// 采用绑定参数形式的SQL语句
				out.println("			String sql = sqlObj.getSQL();");
				out.println("			String[][] arr = sqlObj.getArr();//参数对应值和类型");// 参数对应值和类型
				out.println("			pstmt = con.prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);");
				out.println("			PubFun.setPrepParam(pstmt, arr);//根据类型设置绑定参数值");// 根据类型设置绑定参数值
				out.println("			rs = pstmt.executeQuery();");
			}

			out.println("			int i = 0;");
			out.println("			while (rs.next())");
			out.println("			{");
			out.println("				i++;");
			out.println("				" + SchemaName + " s1 = new " + SchemaName + "();");
			out.println("				s1.setSchema(rs,i);");
			out.println("				a" + SetName + ".add(s1);");

			out.println("			}");
			out.println("			try{ rs.close(); } catch( Exception ex ) {}");

			if (EXISTSCHAR)
			{
				out.println("			try{ stmt.close(); } catch( Exception ex1 ) {}");
			}
			else
			{
				out.println("			try{ pstmt.close(); } catch( Exception ex1 ) {}");
			}

			out.println("		}");
			out.println("		catch(Exception e)");
			out.println("	    {");
			out.println("			e.printStackTrace();");
			out.println("			// @@错误处理");
			out.println("			CError tError = new CError();");
			out.println("			tError.moduleName = \"" + ClassName + "\";");
			out.println("			tError.functionName = \"query\";");
			out.println("			tError.errorMessage = e.toString();");
			out.println("			this.mErrors .addOneError(tError);");
			out.println("");
			out.println("			try{ rs.close(); } catch( Exception ex2 ) {}");

			if (EXISTSCHAR)
			{
				out.println("			try{ stmt.close(); } catch( Exception ex1 ) {}");
			}
			else
			{
				out.println("			try{ pstmt.close(); } catch( Exception ex1 ) {}");
			}

			out.println("");
			out.println("			if (!mflag)");
			out.println("			{");
			out.println("				try");
			out.println("				{");
			out.println("					con.close();");
			out.println("				}");
			out.println("				catch(Exception et){}");
			out.println("			}");
			out.println("	    }");
			out.println("");
			out.println("		if (!mflag)");
			out.println("		{");
			out.println("			try");
			out.println("			{");
			out.println("				con.close();");
			out.println("			}");
			out.println("			catch(Exception e){}");
			out.println("		}");
			out.println("");
			out.println("		return a" + SetName + ";");
			out.println("	}");
			out.println("");

			// 生成 executeQuery 方法
			out.println("	public " + SetName + " executeQuery(String sql)");
			out.println("	{");
			out.println("		Statement stmt = null;");
			out.println("		ResultSet rs = null;");
			out.println("		" + SetName + " a" + SetName + " = new " + SetName + "();");
			out.println();
			out.println("	  if( !mflag ) {");
			out.println("		  con = DBConnPool.getConnection();");
			out.println("		}");
			out.println();
			out.println("		try");
			out.println("		{");
			out.println("			stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);");
			out.println("");
			out.println("			rs = stmt.executeQuery(StrTool.GBKToUnicode(sql));");
			out.println("			int i = 0;");
			out.println("			while (rs.next())");
			out.println("			{");
			out.println("				i++;");
			out.println("				" + SchemaName + " s1 = new " + SchemaName + "();");
			out.println("				if (!s1.setSchema(rs,i))");
			out.println("				{");
			out.println("					// @@错误处理");
			out.println("					CError tError = new CError();");
			out.println("					tError.moduleName = \"" + ClassName + "\";");
			out.println("					tError.functionName = \"executeQuery\";");
			out.println("					tError.errorMessage = \"sql语句有误，请查看表名及字段名信息!\";");
			out.println("					this.mErrors .addOneError(tError);");
			out.println("				}");
			out.println("				a" + SetName + ".add(s1);");

			out.println("			}");
			out.println("			try{ rs.close(); } catch( Exception ex ) {}");
			out.println("			try{ stmt.close(); } catch( Exception ex1 ) {}");
			out.println("		}");
			out.println("		catch(Exception e)");
			out.println("	    {");
			out.println("			e.printStackTrace();");
			out.println("			// @@错误处理");
			out.println("			CError tError = new CError();");
			out.println("			tError.moduleName = \"" + ClassName + "\";");
			out.println("			tError.functionName = \"executeQuery\";");
			out.println("			tError.errorMessage = e.toString();");
			out.println("			this.mErrors .addOneError(tError);");
			out.println("");
			out.println("			try{ rs.close(); } catch( Exception ex2 ) {}");
			out.println("			try{ stmt.close(); } catch( Exception ex3 ) {}");
			out.println("");
			out.println("			if (!mflag)");
			out.println("			{");
			out.println("				try");
			out.println("				{");
			out.println("					con.close();");
			out.println("				}");
			out.println("				catch(Exception et){}");
			out.println("			}");
			out.println("	    }");
			out.println("");
			out.println("		if (!mflag)");
			out.println("		{");
			out.println("			try");
			out.println("			{");
			out.println("				con.close();");
			out.println("			}");
			out.println("			catch(Exception e){}");
			out.println("		}");
			out.println("");
			out.println("		return a" + SetName + ";");
			out.println("	}");
			out.println("");

			// 生成 query(start, count) 方法
			out.println("	public " + SetName + " query(int nStart, int nCount)");
			out.println("	{");

			if (EXISTSCHAR)// 根据是否存在CHAR类型走不同分支
			{
				out.println("		Statement stmt = null;");
			}
			else
			{
				out.println("		PreparedStatement pstmt = null;");
			}

			out.println("		ResultSet rs = null;");
			out.println("		" + SetName + " a" + SetName + " = new " + SetName + "();");
			out.println();
			out.println("	  if( !mflag ) {");
			out.println("		  con = DBConnPool.getConnection();");
			out.println("		}");
			out.println();
			out.println("		try");
			out.println("		{");
			out.println("			SQLString sqlObj = new SQLString(\"" + TableName + "\");");
			out.println("			" + SchemaName + " aSchema = this.getSchema();");

			if (EXISTSCHAR)// 数据库表存在CHAR类型的字段
			{
				out.println("			sqlObj.setSQL(5,aSchema);");
				out.println("			String sql = sqlObj.getSQL();");
				out.println("			stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);");
				out.println("			rs = stmt.executeQuery(sql);");
			}
			else
			{
				out.println("			sqlObj.setSQL(8, aSchema);");// 采用绑定参数形式的SQL语句
				out.println("			String sql = sqlObj.getSQL();");
				out.println("			String[][] arr = sqlObj.getArr();");// 参数对应值和类型
				out.println("			pstmt = con.prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);");
				out.println("			PubFun.setPrepParam(pstmt, arr);");// 根据类型设置绑定参数值
				out.println("			rs = pstmt.executeQuery();");
			}

			out.println("			int i = 0;");
			out.println("			while (rs.next())");
			out.println("			{");
			out.println("				i++;");
			out.println();

			// 小于起始值，继续
			out.println("				if( i < nStart ) {");
			out.println("					continue;");
			out.println("				}");
			out.println();

			// 大于结束值，退出
			out.println("				if( i >= nStart + nCount ) {");
			out.println("					break;");
			out.println("				}");
			out.println();

			out.println("				" + SchemaName + " s1 = new " + SchemaName + "();");
			out.println("				s1.setSchema(rs,i);");
			out.println("				a" + SetName + ".add(s1);");
			out.println("			}");
			out.println("			try{ rs.close(); } catch( Exception ex ) {}");

			if (EXISTSCHAR)
			{
				out.println("			try{ stmt.close(); } catch( Exception ex1 ) {}");
			}
			else
			{
				out.println("			try{ pstmt.close(); } catch( Exception ex1 ) {}");
			}

			out.println("		}");
			out.println("		catch(Exception e)");
			out.println("	    {");
			out.println("			e.printStackTrace();");
			out.println("			// @@错误处理");
			out.println("			CError tError = new CError();");
			out.println("			tError.moduleName = \"" + ClassName + "\";");
			out.println("			tError.functionName = \"query\";");
			out.println("			tError.errorMessage = e.toString();");
			out.println("			this.mErrors .addOneError(tError);");
			out.println("");
			out.println("			try{ rs.close(); } catch( Exception ex2 ) {}");

			if (EXISTSCHAR)
			{
				out.println("			try{ stmt.close(); } catch( Exception ex1 ) {}");
			}
			else
			{
				out.println("			try{ pstmt.close(); } catch( Exception ex1 ) {}");
			}

			out.println("");
			out.println("			if (!mflag)");
			out.println("			{");
			out.println("				try");
			out.println("				{");
			out.println("					con.close();");
			out.println("				}");
			out.println("				catch(Exception et){}");
			out.println("			}");
			out.println("	    }");
			out.println("");
			out.println("		if (!mflag)");
			out.println("		{");
			out.println("			try");
			out.println("			{");
			out.println("				con.close();");
			out.println("			}");
			out.println("			catch(Exception e){}");
			out.println("		}");
			out.println("");
			out.println("		return a" + SetName + ";");
			out.println("	}");
			out.println("");

			// 生成 executeQuery(sql, start, count) 方法
			out.println("	public " + SetName + " executeQuery(String sql, int nStart, int nCount)");
			out.println("	{");
			out.println("		Statement stmt = null;");
			out.println("		ResultSet rs = null;");
			out.println("		" + SetName + " a" + SetName + " = new " + SetName + "();");
			out.println();
			out.println("	  if( !mflag ) {");
			out.println("		  con = DBConnPool.getConnection();");
			out.println("		}");
			out.println();
			out.println("		try");
			out.println("		{");
			out.println("			stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);");
			out.println("");
			out.println("			rs = stmt.executeQuery(StrTool.GBKToUnicode(sql));");
			out.println("			int i = 0;");
			out.println("			while (rs.next())");
			out.println("			{");
			out.println("				i++;");
			out.println();

			// 小于起始值，继续
			out.println("				if( i < nStart ) {");
			out.println("					continue;");
			out.println("				}");
			out.println();

			// 大于结束值，退出
			out.println("				if( i >= nStart + nCount ) {");
			out.println("					break;");
			out.println("				}");
			out.println();

			out.println("				" + SchemaName + " s1 = new " + SchemaName + "();");
			out.println("				if (!s1.setSchema(rs,i))");
			out.println("				{");
			out.println("					// @@错误处理");
			out.println("					CError tError = new CError();");
			out.println("					tError.moduleName = \"" + ClassName + "\";");
			out.println("					tError.functionName = \"executeQuery\";");
			out.println("					tError.errorMessage = \"sql语句有误，请查看表名及字段名信息!\";");
			out.println("					this.mErrors .addOneError(tError);");
			out.println("				}");
			out.println("				a" + SetName + ".add(s1);");
			out.println("			}");
			out.println("			try{ rs.close(); } catch( Exception ex ) {}");
			out.println("			try{ stmt.close(); } catch( Exception ex1 ) {}");
			out.println("		}");
			out.println("		catch(Exception e)");
			out.println("	    {");
			out.println("			e.printStackTrace();");
			out.println("			// @@错误处理");
			out.println("			CError tError = new CError();");
			out.println("			tError.moduleName = \"" + ClassName + "\";");
			out.println("			tError.functionName = \"executeQuery\";");
			out.println("			tError.errorMessage = e.toString();");
			out.println("			this.mErrors .addOneError(tError);");
			out.println("");
			out.println("			try{ rs.close(); } catch( Exception ex2 ) {}");
			out.println("			try{ stmt.close(); } catch( Exception ex3 ) {}");
			out.println("");
			out.println("			if (!mflag)");
			out.println("			{");
			out.println("				try");
			out.println("				{");
			out.println("					con.close();");
			out.println("				}");
			out.println("				catch(Exception et){}");
			out.println("			}");
			out.println("	    }");
			out.println("");
			out.println("		if (!mflag)");
			out.println("		{");
			out.println("			try");
			out.println("			{");
			out.println("				con.close();");
			out.println("			}");
			out.println("			catch(Exception e){}");
			out.println("		}");
			out.println("");
			out.println("		return a" + SetName + ";");
			out.println("	}");
			out.println("");

			// 生成按条件update的方法
			out.println("	public boolean update(String strWherePart)");
			out.println("	{");
			out.println("		Statement stmt = null;");
			out.println();
			out.println("	  if( !mflag ) {");
			out.println("		  con = DBConnPool.getConnection();");
			out.println("		}");
			out.println();
			out.println("		try");
			out.println("		{");
			out.println("			stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);");
			out.println("			SQLString sqlObj = new SQLString(\"" + TableName + "\");");
			out.println("			" + SchemaName + " aSchema = this.getSchema();");
			out.println("			sqlObj.setSQL(2,aSchema);");
			out.println("			String sql = \"update " + TableName + " \""
					+ " + sqlObj.getUpdPart() + \" where \" + strWherePart;");
			out.println("");
			out.println("			int operCount = stmt.executeUpdate(sql);");
			out.println("			if( operCount == 0 )");
			out.println("			{");
			out.println("				// @@错误处理");
			out.println("				CError tError = new CError();");
			out.println("				tError.moduleName = \"" + ClassName + "\";");
			out.println("				tError.functionName = \"update\";");
			out.println("				tError.errorMessage = \"更新数据失败!\";");
			out.println("				this.mErrors .addOneError(tError);");
			out.println("");
			out.println("				if (!mflag)");
			out.println("				{");
			out.println("					try");
			out.println("					{");
			out.println("						con.close();");
			out.println("					}");
			out.println("					catch(Exception et){}");
			out.println("				}");
			out.println("				return false;");
			out.println("			}");
			out.println("		}");
			out.println("		catch(Exception e)");
			out.println("	    {");
			out.println("			e.printStackTrace();");
			out.println("			// @@错误处理");
			out.println("			CError tError = new CError();");
			out.println("			tError.moduleName = \"" + ClassName + "\";");
			out.println("			tError.functionName = \"update\";");
			out.println("			tError.errorMessage = e.toString();");
			out.println("			this.mErrors .addOneError(tError);");
			out.println("");
			out.println("			try{ stmt.close(); } catch( Exception ex1 ) {}");
			out.println("");
			out.println("			if (!mflag)");
			out.println("			{");
			out.println("				try");
			out.println("				{");
			out.println("					con.close();");
			out.println("				}");
			out.println("				catch(Exception et){}");
			out.println("			}");
			out.println("			return false;");
			out.println("	    }");
			out.println("	    // 断开数据库连接");
			out.println("		if (!mflag)");
			out.println("		{");
			out.println("			try");
			out.println("			{");
			out.println("				con.close();");
			out.println("			}");
			out.println("			catch(Exception e){}");
			out.println("		}");
			out.println("");
			out.println("		return true;");
			out.println("	}");
			out.println("");

			// 生成prepareData方法
			out.println("/**");
			out.println(" * 准备数据查询条件");
			out.println(" * @param strSQL String");
			out.println(" * @return boolean");
			out.println(" */");
			out.println("public boolean prepareData(String strSQL)");
			out.println("{");
			out.println("    if (mResultSet != null)");
			out.println("    {");
			out.println("        // @@错误处理");
			out.println("        CError tError = new CError();");
			out.println("        tError.moduleName = \"" + ClassName + "\";");
			out.println("        tError.functionName = \"prepareData\";");
			out.println("        tError.errorMessage = \"数据集非空，程序在准备数据集之后，没有关闭！\";");
			out.println("        this.mErrors.addOneError(tError);");
			out.println("        return false;");
			out.println("    }");
			out.println("");
			out.println("    if (!mflag)");
			out.println("    {");
			out.println("        con = DBConnPool.getConnection();");
			out.println("    }");
			out.println("    try");
			out.println("    {");
			out.println("        mStatement = con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);");
			out.println("        mResultSet = mStatement.executeQuery(StrTool.GBKToUnicode(strSQL));");
			out.println("    }");
			out.println("    catch (Exception e)");
			out.println("    {");
			out.println("        // @@错误处理");
			out.println("        CError tError = new CError();");
			out.println("        tError.moduleName = \"" + ClassName + "\";");
			out.println("        tError.functionName = \"prepareData\";");
			out.println("        tError.errorMessage = e.toString();");
			out.println("        this.mErrors.addOneError(tError);");
			out.println("        try");
			out.println("        {");
			out.println("            mResultSet.close();");
			out.println("        }");
			out.println("        catch (Exception ex2)");
			out.println("        {}");
			out.println("        try");
			out.println("        {");
			out.println("            mStatement.close();");
			out.println("        }");
			out.println("        catch (Exception ex3)");
			out.println("        {}");
			out.println("        if (!mflag)");
			out.println("        {");
			out.println("            try");
			out.println("            {");
			out.println("                con.close();");
			out.println("            }");
			out.println("            catch (Exception et)");
			out.println("            {}");
			out.println("        }");
			out.println("        return false;");
			out.println("    }");
			out.println("");
			out.println("    if (!mflag)");
			out.println("    {");
			out.println("        try");
			out.println("        {");
			out.println("            con.close();");
			out.println("        }");
			out.println("        catch (Exception e)");
			out.println("        {}");
			out.println("    }");
			out.println("    return true;");
			out.println("}");
			out.println("");

			// 生成hasMoreData方法
			out.println("/**");
			out.println(" * 获取数据集");
			out.println(" * @return boolean");
			out.println(" */");
			out.println("public boolean hasMoreData()");
			out.println("{");
			out.println("    boolean flag = true;");
			out.println("    if (null == mResultSet)");
			out.println("    {");
			out.println("        CError tError = new CError();");
			out.println("        tError.moduleName = \"" + ClassName + "\";");
			out.println("        tError.functionName = \"hasMoreData\";");
			out.println("        tError.errorMessage = \"数据集为空，请先准备数据集！\";");
			out.println("        this.mErrors.addOneError(tError);");
			out.println("        return false;");
			out.println("    }");
			out.println("    try");
			out.println("    {");
			out.println("        flag = mResultSet.next();");
			out.println("    }");
			out.println("    catch (Exception ex)");
			out.println("    {");
			out.println("        CError tError = new CError();");
			out.println("        tError.moduleName = \"" + ClassName + "\";");
			out.println("        tError.functionName = \"hasMoreData\";");
			out.println("        tError.errorMessage = ex.toString();");
			out.println("        this.mErrors.addOneError(tError);");
			out.println("        try");
			out.println("        {");
			out.println("            mResultSet.close();");
			out.println("            mResultSet = null;");
			out.println("        }");
			out.println("        catch (Exception ex2)");
			out.println("        {}");
			out.println("        try");
			out.println("        {");
			out.println("            mStatement.close();");
			out.println("            mStatement = null;");
			out.println("        }");
			out.println("        catch (Exception ex3)");
			out.println("        {}");
			out.println("        if (!mflag)");
			out.println("        {");
			out.println("            try");
			out.println("            {");
			out.println("                con.close();");
			out.println("            }");
			out.println("            catch (Exception et)");
			out.println("            {}");
			out.println("        }");
			out.println("        return false;");
			out.println("    }");
			out.println("    return flag;");
			out.println("}");
			// 生成getData方法
			out.println("/**");
			out.println(" * 获取定量数据");
			out.println(" * @return " + TableName + "Set");
			out.println(" */");
			out.println("public " + TableName + "Set getData()");
			out.println("{");
			out.println("    int tCount = 0;");
			out.println("    " + TableName + "Set t" + TableName + "Set = new " + TableName + "Set();");
			out.println("    " + TableName + "Schema t" + TableName + "Schema = null;");
			out.println("    if (null == mResultSet)");
			out.println("    {");
			out.println("        CError tError = new CError();");
			out.println("        tError.moduleName = \"" + ClassName + "\";");
			out.println("        tError.functionName = \"getData\";");
			out.println("        tError.errorMessage = \"数据集为空，请先准备数据集！\";");
			out.println("        this.mErrors.addOneError(tError);");
			out.println("        return null;");
			out.println("    }");
			out.println("    try");
			out.println("    {");
			out.println("        tCount = 1;");
			out.println("        t" + TableName + "Schema = new " + TableName + "Schema();");
			out.println("        t" + TableName + "Schema.setSchema(mResultSet, 1);");
			out.println("        t" + TableName + "Set.add(t" + TableName + "Schema);");
			out.println("        //注意mResultSet.next()的作用");
			out.println("        while (tCount++ < SysConst.FETCHCOUNT)");
			out.println("        {");
			out.println("            if (mResultSet.next())");
			out.println("            {");
			out.println("                t" + TableName + "Schema = new " + TableName + "Schema();");
			out.println("                t" + TableName + "Schema.setSchema(mResultSet, 1);");
			out.println("                t" + TableName + "Set.add(t" + TableName + "Schema);");
			out.println("            }");
			out.println("        }");
			out.println("    }");
			out.println("    catch (Exception ex)");
			out.println("    {");
			out.println("        CError tError = new CError();");
			out.println("        tError.moduleName = \"" + ClassName + "\";");
			out.println("        tError.functionName = \"getData\";");
			out.println("        tError.errorMessage = ex.toString();");
			out.println("        this.mErrors.addOneError(tError);");
			out.println("        try");
			out.println("        {");
			out.println("            mResultSet.close();");
			out.println("            mResultSet = null;");
			out.println("        }");
			out.println("        catch (Exception ex2)");
			out.println("        {}");
			out.println("        try");
			out.println("        {");
			out.println("            mStatement.close();");
			out.println("            mStatement = null;");
			out.println("        }");
			out.println("        catch (Exception ex3)");
			out.println("        {}");
			out.println("        if (!mflag)");
			out.println("        {");
			out.println("            try");
			out.println("            {");
			out.println("                con.close();");
			out.println("            }");
			out.println("            catch (Exception et)");
			out.println("            {}");
			out.println("        }");
			out.println("        return null;");
			out.println("    }");
			out.println("    return t" + TableName + "Set;");
			out.println("}");
			// 生成closeData方法
			out.println("/**");
			out.println(" * 关闭数据集");
			out.println(" * @return boolean");
			out.println(" */");
			out.println("public boolean closeData()");
			out.println("{");
			out.println("    boolean flag = true;");
			out.println("    try");
			out.println("    {");
			out.println("        if (null == mResultSet)");
			out.println("        {");
			out.println("            CError tError = new CError();");
			out.println("            tError.moduleName = \"" + ClassName + "\";");
			out.println("            tError.functionName = \"closeData\";");
			out.println("            tError.errorMessage = \"数据集已经关闭了！\";");
			out.println("            this.mErrors.addOneError(tError);");
			out.println("            flag = false;");
			out.println("        }");
			out.println("        else");
			out.println("        {");
			out.println("            mResultSet.close();");
			out.println("            mResultSet = null;");
			out.println("        }");
			out.println("    }");
			out.println("    catch (Exception ex2)");
			out.println("    {");
			out.println("        CError tError = new CError();");
			out.println("        tError.moduleName = \"" + ClassName + "\";");
			out.println("        tError.functionName = \"closeData\";");
			out.println("        tError.errorMessage = ex2.toString();");
			out.println("        this.mErrors.addOneError(tError);");
			out.println("        flag = false;");
			out.println("    }");
			out.println("    try");
			out.println("    {");
			out.println("        if (null == mStatement)");
			out.println("        {");
			out.println("            CError tError = new CError();");
			out.println("            tError.moduleName = \"" + ClassName + "\";");
			out.println("            tError.functionName = \"closeData\";");
			out.println("            tError.errorMessage = \"语句已经关闭了！\";");
			out.println("            this.mErrors.addOneError(tError);");
			out.println("            flag = false;");
			out.println("        }");
			out.println("        else");
			out.println("        {");
			out.println("            mStatement.close();");
			out.println("            mStatement = null;");
			out.println("        }");
			out.println("    }");
			out.println("    catch (Exception ex3)");
			out.println("    {");
			out.println("        CError tError = new CError();");
			out.println("        tError.moduleName = \"" + ClassName + "\";");
			out.println("        tError.functionName = \"closeData\";");
			out.println("        tError.errorMessage = ex3.toString();");
			out.println("        this.mErrors.addOneError(tError);");
			out.println("        flag = false;");
			out.println("    }");
			out.println("    return flag;");
			out.println("}");

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

	private void genSetParamString(FieldInfo fieldInfo, int nParamIndex, String strStatementVar, PrintWriter out,
			String strAppend, boolean bWherePart)
	{
		int nFieldType = fieldInfo.getFieldSQLType();

		if (strStatementVar == null || strStatementVar.equals(""))
		{
			strStatementVar = "pstmt";
		}

		switch (nFieldType)
		{
			case Types.CHAR:
				out.print(strAppend);
				out.println("if(this.get" + fieldInfo.getFieldCode() + "() == null || this.get"
						+ fieldInfo.getFieldCode() + "().equals(\"null\")) {");
				out.print(strAppend);
				out.println("\t" + strStatementVar + ".setNull(" + String.valueOf(nParamIndex) + ", "
						+ String.valueOf(nFieldType) + ");");
				out.print(strAppend);
				out.println("} else {");
				out.print(strAppend);

				if (bWherePart)
				{
					out.println("\t" + strStatementVar + ".setString(" + String.valueOf(nParamIndex)
							+ ", StrTool.space(this.get" + fieldInfo.getFieldCode() + "(), "
							+ fieldInfo.getFieldLength() + "));");
				}
				else
				{
					out.println("\t" + strStatementVar + ".setString(" + String.valueOf(nParamIndex) + ", this.get"
							+ fieldInfo.getFieldCode() + "());");
				}

				out.print(strAppend);
				out.println("}");

				break;
			case Types.VARCHAR:
				out.print(strAppend);
				out.println("if(this.get" + fieldInfo.getFieldCode() + "() == null || this.get"
						+ fieldInfo.getFieldCode() + "().equals(\"null\")) {");
				out.print(strAppend);
				out.println("\t" + strStatementVar + ".setNull(" + String.valueOf(nParamIndex) + ", "
						+ String.valueOf(nFieldType) + ");");
				out.print(strAppend);
				out.println("} else {");
				out.print(strAppend);
				// 2007-12-18 添加 trim部分
				out.println("\t" + strStatementVar + ".setString(" + String.valueOf(nParamIndex) + ", this.get"
						+ fieldInfo.getFieldCode() + "().trim());");
				out.print(strAppend);
				out.println("}");
				break;
			case Types.DATE:
				out.print(strAppend);
				out.println("if(this.get" + fieldInfo.getFieldCode() + "() == null || this.get"
						+ fieldInfo.getFieldCode() + "().equals(\"null\")) {");
				out.print(strAppend);
				out.println("\t" + strStatementVar + ".setNull(" + String.valueOf(nParamIndex) + ", "
						+ String.valueOf(nFieldType) + ");");
				out.print(strAppend);
				out.println("} else {");
				out.print(strAppend);
				out.println("\t" + strStatementVar + ".setDate(" + String.valueOf(nParamIndex)
						+ ", Date.valueOf(this.get" + fieldInfo.getFieldCode() + "()));");
				out.print(strAppend);
				out.println("}");
				break;
			case Types.INTEGER:
				out.print(strAppend);
				out.println(strStatementVar + ".setInt(" + String.valueOf(nParamIndex) + ", this.get"
						+ fieldInfo.getFieldCode() + "());");
				break;
			case Types.NUMERIC:
				out.print(strAppend);
				out.println(strStatementVar + ".setDouble(" + String.valueOf(nParamIndex) + ", this.get"
						+ fieldInfo.getFieldCode() + "());");
				break;
			default:
				System.out.println("unsupported field type");
		}
	}

	/**
	 * 拼写输出PreparedStatement中的sql语句
	 * @param fieldInfo
	 * @return
	 */
	private String genSetValueString(FieldInfo fieldInfo)
	{
		int nFieldType = fieldInfo.getFieldSQLType();
		String tSql = "";
		switch (nFieldType)
		{
			case Types.CHAR:
			case Types.VARCHAR:
			case Types.DATE:
				// 目前对于null的处理不是很好，会生成 CodeType = 'null'，可尝试在最后输出的时候统一替换......
				tSql += fieldInfo.getFieldCode() + "='\"+" + "this.get" + fieldInfo.getFieldCode() + "() + \"'\"";
				break;
			case Types.INTEGER:
			case Types.NUMERIC:
				tSql += fieldInfo.getFieldCode() + "=\"+" + "this.get" + fieldInfo.getFieldCode() + "()";
				break;
			default:
				System.out.println("unsupported field type");
		}
		return tSql;
	}
}