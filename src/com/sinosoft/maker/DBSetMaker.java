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
 * ClassName: DBSetMaker
 * </p>
 * <p>
 * Description: ���� DBTablenameSet.java �ļ�
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
public class DBSetMaker
{
	// @Field
	private String DBName;

	private String TableName;

	private String FileName;

	private TableInfo table;

	// @Constructor
	public DBSetMaker(String fName)
	{
		FileName = fName;
	}

	public DBSetMaker(String fName, String t)
	{
		FileName = fName;
		TableName = t;
		/*
		 * �����ݿ���ȡ�������ֶ�ʱʹ�� TableName = TableName.toLowerCase(); TableName =
		 * TableName.substring(0,1).toUpperCase() + TableName.substring(1);
		 */
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
				Vector f = db.getFields(table);
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
		String Path = ".\\src\\com\\sinosoft\\lis\\vdb\\";
		String ClassName = TableName + "DBSet";
		FileName = ClassName + ".java";
		String SetName = TableName + "Set";
		// String SchemaName = TableName + "Schema";
		// String DBOperName = TableName + "DB";

		// �Ƿ���ܱ�־
		boolean tJKFlag = false;
		try
		{
			System.out.println("Creating file ......");
			System.out.println("File Name : " + FileName);

			out = new PrintWriter(new FileWriter(new File(Path + FileName)), true);
			// �ļ�ͷ��Ϣ
			out.println("/**");
			out.println(" * Copyright (c) 2002 sinosoft  Co. Ltd.");
			out.println(" * All right reserved.");
			out.println(" */");
			out.println("");
			// @Package
			out.println("package com.sinosoft.lis.vdb;");
			out.println("");
			// @Import
			out.println("import java.sql.*;");
			out.println("import com.sinosoft.lis.vschema." + SetName + ";");
			out.println("import com.sinosoft.utility.*;");
			if (SysConst.JKTABLE.indexOf(TableName + "-") >= 0)
			{
				tJKFlag = true;
				out.println("import com.sinosoft.lis.JKEncrypt.LJKEncrypt;");// 11-13
			}
			out.println("");
			// ��ע��
			out.println("/**");
			out.println(" * <p>ClassName: " + ClassName + " </p>");
			out.println(" * <p>Description: DB����¼���ݿ�������ļ� </p>");
			out.println(" * <p>Copyright: Copyright (c) 2002</p>");
			out.println(" * <p>Company: sinosoft </p>");
			out.println(" * @Database: " + DBName);
			out.println(" * @CreateDate��" + getDate());
			out.println(" */");
			// @Begin
			out.println("public class " + ClassName + " extends " + SetName);
			out.println("{");

			// �������ݳ�Ա����
			out.println("	// @Field");
			out.println("	private Connection con;");
			out.println("	private DBOper db;");
			out.println("	/**");
			out.println("	* flag = true: ����Connection");
			out.println("	* flag = false: ������Connection");
			out.println("	**/");
			out.println("	private boolean mflag = false;");
			out.println("");
			out.println("");

			// ���ɹ�����
			out.println("	// @Constructor");
			out.println("	public " + ClassName + "(Connection tConnection)");
			out.println("	{");
			out.println("		con = tConnection;");
			out.println("		db = new DBOper(con,\"" + TableName + "\");");
			out.println("		mflag = true;");
			out.println("	}");
			out.println("");
			out.println("	public " + ClassName + "()");
			out.println("	{");
			out.println("		db = new DBOper( \"" + TableName + "\" );");
			out.println("		con = null;");
			out.println("		mflag = false;");
			out.println("	}");

			// ���ɷ���
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
					// 2005-12-01 zhuxf
					// �޸���delete��update�����ɷ��������ԣ��ڴ˴�ֱ���˳���
					continue;
				}

				out.println("	public boolean " + oper + "()");
				out.println("	{");
				out.println("		if (db." + oper + "(this))");
				out.println("		{");
				out.println("		        return true;");
				out.println("		}");
				out.println("		else");
				out.println("		{");
				out.println("			// @@������");
				out.println("			this.mErrors.copyAllErrors(db.mErrors);");
				out.println("			CError tError = new CError();");
				out.println("			tError.moduleName = \"" + ClassName + "\";");
				out.println("			tError.functionName = \"" + oper + "\";");
				out.println("			tError.errorMessage = \"����ʧ��!\";");
				out.println("			this.mErrors .addOneError(tError);");
				out.println("			return false;");
				out.println("		}");
				out.println("	}");
				out.println("");
			} // end of for

			// 2005-11-11 Kevin
			// ���ɰ�����������WHERE�Ӿ䣬����Ӿ�������Ķ�����ֶ����õ���
			// ����UPDATE��������Ҫ�������ֶε��־䡣���磬ContNo = ? , PolNo = ?
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
			// get rid of the first " AND" part.
			sbPKWhereClause = sbPKWhereClause.delete(0, 4);
			// get rid of the first " ," part.
			sbInsertColumnClause = sbInsertColumnClause.delete(0, 2);
			// get rid of the first " ," part.
			sbUpdateColumnClause = sbUpdateColumnClause.delete(0, 2);

			int nFieldIndex = 0;
			int nParamIndex = 1; // �������������ĳ�����лᾭ���õ��������ڴ˴�ͳһ���塣

			// 2005-11-14 Kevin
			// �޸�delete������ʹ��preparedStatement������
			out.println("    /**");
			out.println("     * ɾ������");
			out.println("     * ɾ������������");
			out.println("     * @return boolean");
			out.println("     */");
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
			out.println("            int tCount = this.size();");
			out.println("			pstmt = con.prepareStatement(\"DELETE FROM " + TableName + " WHERE " + sbPKWhereClause
					+ "\");");
			out.println("            for (int i = 1; i <= tCount; i++)");
			out.println("            {");

			// �������ò����Ĳ��֡�
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
			out.println("                pstmt.addBatch();");
			out.println("            }");
			out.println("            pstmt.executeBatch();");
			// out.println(" pstmt.executeUpdate();");
			out.println("			pstmt.close();");
			out.println("		} catch (Exception ex) {");
			out.println("			// @@������");
			out.println("			ex.printStackTrace();");
			out.println("			this.mErrors.copyAllErrors(db.mErrors);");
			out.println("			CError tError = new CError();");
			out.println("			tError.moduleName = \"" + ClassName + "\";");
			out.println("			tError.functionName = \"delete()\";");
			out.println("			tError.errorMessage = ex.toString();");
			out.println("			this.mErrors .addOneError(tError);");
			out.println();
			out.println("            int tCount = this.size();");
			out.println("            for (int i = 1; i <= tCount; i++)");
			out.println("            {");
			out.println("		// only for debug purpose");
			out.println("		SQLString sqlObj = new SQLString(\"" + TableName + "\");");
			out.println("		sqlObj.setSQL(4, this.get(i));");
			out.println("		sqlObj.getSQL();");
			out.println("            }");
			out.println();
			out.println("			try {");
			out.println("				pstmt.close();");
			out.println("			} catch (Exception e){e.printStackTrace();}");
			out.println();
			out.println("			if( !mflag ) {");
			out.println("				try {");
			out.println("					con.close();");
			out.println("				} catch (Exception e){e.printStackTrace();}");
			out.println("			}");
			out.println();
			out.println("			return false;");
			out.println("		}");
			out.println();
			out.println("		if( !mflag ) {");
			out.println("			try {");
			out.println("				con.close();");
			out.println("			} catch (Exception e){e.printStackTrace();}");
			out.println("		}");
			out.println();
			out.println("		return true;");
			out.println("	}");
			out.println("");
			// 2005-11-14 Kevin
			// �޸�delete���������ɷ���

			// 2005-11-14 Kevin
			// �޸�update������ʹ��preparedStatement������
			out.println("    /**");
			out.println("     * ���²���");
			out.println("     * ��������������");
			out.println("     * @return boolean");
			out.println("     */");
			out.println("	public boolean update()");
			out.println("	{");
			out.println("		PreparedStatement pstmt = null;");
			out.println();
			out.println("		if( !mflag ) {");
			out.println("			con = DBConnPool.getConnection();");
			out.println("		}");
			out.println();
			out.println("		try");
			out.println("		{");
			out.println("            int tCount = this.size();");
			out.println("			pstmt = con.prepareStatement(\"UPDATE " + TableName + " SET " + sbUpdateColumnClause
					+ " WHERE " + sbPKWhereClause + "\");");
			out.println("            for (int i = 1; i <= tCount; i++)");
			out.println("            {");

			if (tJKFlag)
			{
				out.println("           //**********************���ܴ���*************************");// 11-13
				out.println("           LJKEncrypt tLJKEncrypt= new LJKEncrypt();");// 11-13
				out.println("           this.set(i,tLJKEncrypt." + TableName + "toEncrypt(con, this.get(i)));");// 11-13
				out.println("           //******************************************************");// 11-13
			}

			// �����������ݵĲ��֡�
			nParamIndex = 1;
			for (nFieldIndex = 0; nFieldIndex < fields.size(); nFieldIndex++)
			{
				FieldInfo f = (FieldInfo) fields.get(nFieldIndex);
				genSetParamString(f, nParamIndex++, "pstmt", out, "			", false);
			}
			out.println("			// set where condition");
			// �����������������Ĳ��֡�
			for (nFieldIndex = 0; nFieldIndex < fields.size(); nFieldIndex++)
			{
				FieldInfo f = (FieldInfo) fields.get(nFieldIndex);
				String FieldProperty = f.getFieldProperty();
				if (FieldProperty.equals("PK"))
				{
					genSetParamString(f, nParamIndex++, "pstmt", out, "			", true);
				}
			}
			out.println("                pstmt.addBatch();");
			out.println("            }");
			out.println("            pstmt.executeBatch();");
			// out.println(" pstmt.executeUpdate();");
			out.println("			pstmt.close();");
			out.println("		} catch (Exception ex) {");
			out.println("			// @@������");
			out.println("			ex.printStackTrace();");
			out.println("			this.mErrors.copyAllErrors(db.mErrors);");
			out.println("			CError tError = new CError();");
			out.println("			tError.moduleName = \"" + ClassName + "\";");
			out.println("			tError.functionName = \"update()\";");
			out.println("			tError.errorMessage = ex.toString();");
			out.println("			this.mErrors .addOneError(tError);");
			out.println();
			out.println("            int tCount = this.size();");
			out.println("            for (int i = 1; i <= tCount; i++)");
			out.println("            {");
			out.println("		// only for debug purpose");
			out.println("		SQLString sqlObj = new SQLString(\"" + TableName + "\");");
			out.println("		sqlObj.setSQL(2, this.get(i));");
			out.println("		sqlObj.getSQL();");
			out.println("            }");
			out.println();
			out.println("			try {");
			out.println("				pstmt.close();");
			out.println("			} catch (Exception e){e.printStackTrace();}");
			out.println();
			out.println("			if( !mflag ) {");
			out.println("				try {");
			out.println("					con.close();");
			out.println("				} catch (Exception e){e.printStackTrace();}");
			out.println("			}");
			out.println();
			out.println("			return false;");
			out.println("		}");
			out.println();
			out.println("		if( !mflag ) {");
			out.println("			try {");
			out.println("				con.close();");
			out.println("			} catch (Exception e){e.printStackTrace();}");
			out.println("		}");
			out.println();
			out.println("		return true;");
			out.println("	}");
			out.println("");
			// 2005-11-14 Kevin
			// �޸�update���������ɷ���

			// 2005-11-14 Kevin
			// �޸�insert������ʹ��preparedStatement������
			out.println("    /**");
			out.println("     * ��������");
			out.println("     * @return boolean");
			out.println("     */");
			out.println("	public boolean insert()");
			out.println("	{");
			out.println("		PreparedStatement pstmt = null;");
			out.println();
			out.println("		if( !mflag ) {");
			out.println("			con = DBConnPool.getConnection();");
			out.println("		}");
			out.println();
			out.println("		try");
			out.println("		{");
			out.println("            int tCount = this.size();");
			out.println("			pstmt = con.prepareStatement(\"INSERT INTO " + TableName + " VALUES("
					+ sbInsertColumnClause + ")\");");
			out.println("            for (int i = 1; i <= tCount; i++)");
			out.println("            {");

			if (tJKFlag)
			{
				out.println("           //**********************���ܴ���*************************");// 11-13
				out.println("           LJKEncrypt tLJKEncrypt= new LJKEncrypt();");// 11-13
				out.println("           this.set(i,tLJKEncrypt." + TableName + "toEncrypt(con, this.get(i)));");// 11-13
				out.println("           //******************************************************");// 11-13
			}

			// �����������ݵĲ��֡�
			nParamIndex = 1;
			for (nFieldIndex = 0; nFieldIndex < fields.size(); nFieldIndex++)
			{
				FieldInfo f = (FieldInfo) fields.get(nFieldIndex);
				genSetParamString(f, nParamIndex++, "pstmt", out, "			", false);
			}
			out.println("                pstmt.addBatch();");
			out.println("            }");
			out.println("            pstmt.executeBatch();");
			// out.println(" // execute sql");
			// out.println(" pstmt.executeUpdate();");
			out.println("			pstmt.close();");
			out.println("		} catch (Exception ex) {");
			out.println("			// @@������");
			out.println("			ex.printStackTrace();");
			out.println("			this.mErrors.copyAllErrors(db.mErrors);");
			out.println("			CError tError = new CError();");
			out.println("			tError.moduleName = \"" + ClassName + "\";");
			out.println("			tError.functionName = \"insert()\";");
			out.println("			tError.errorMessage = ex.toString();");
			out.println("			this.mErrors .addOneError(tError);");
			out.println();
			out.println("            int tCount = this.size();");
			out.println("            for (int i = 1; i <= tCount; i++)");
			out.println("            {");
			out.println("		// only for debug purpose");
			out.println("		SQLString sqlObj = new SQLString(\"" + TableName + "\");");
			out.println("		sqlObj.setSQL(1, this.get(i));");
			out.println("		sqlObj.getSQL();");
			out.println("            }");
			out.println();
			out.println("			try {");
			out.println("				pstmt.close();");
			out.println("			} catch (Exception e){e.printStackTrace();}");
			out.println();
			out.println("			if( !mflag ) {");
			out.println("				try {");
			out.println("					con.close();");
			out.println("				} catch (Exception e){e.printStackTrace();}");
			out.println("			}");
			out.println();
			out.println("			return false;");
			out.println("		}");
			out.println();
			out.println("		if( !mflag ) {");
			out.println("			try {");
			out.println("				con.close();");
			out.println("			} catch (Exception e){e.printStackTrace();}");
			out.println("		}");
			out.println();
			out.println("		return true;");
			out.println("	}");
			out.println("");
			// 2005-11-14 Kevin
			// �޸�insert���������ɷ���

			// ���ɽ�β
			out.println("}");
			System.out.println("Create file success!!");
		} // end of try
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}

	// �õ���ǰ����
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

	/**
	 * �������͸�ֵ
	 * @param fieldInfo FieldInfo
	 * @param nParamIndex int
	 * @param strStatementVar String
	 * @param out PrintWriter
	 * @param strAppend String
	 * @param bWherePart boolean
	 */
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
				out.println("if(this.get(i).get" + fieldInfo.getFieldCode() + "() == null || this.get(i).get"
						+ fieldInfo.getFieldCode() + "().equals(\"null\")) {");
				out.print(strAppend);
				// out.println("\t" + strStatementVar + ".setNull("
				// + String.valueOf(nParamIndex) + ", " +
				// String.valueOf(nFieldType) + ");");
				out.println("\t" + strStatementVar + ".setString(" + String.valueOf(nParamIndex) + ",null);");
				out.print(strAppend);
				out.println("} else {");
				out.print(strAppend);

				if (bWherePart)
				{
					out.println("\t" + strStatementVar + ".setString(" + String.valueOf(nParamIndex)
							+ ", StrTool.space(this.get(i).get" + fieldInfo.getFieldCode() + "(), "
							+ fieldInfo.getFieldLength() + "));");
				}
				else
				{
					out.println("\t" + strStatementVar + ".setString(" + String.valueOf(nParamIndex)
							+ ", this.get(i).get" + fieldInfo.getFieldCode() + "());");
				}

				out.print(strAppend);
				out.println("}");

				break;
			case Types.VARCHAR:
				out.print(strAppend);
				out.println("if(this.get(i).get" + fieldInfo.getFieldCode() + "() == null || this.get(i).get"
						+ fieldInfo.getFieldCode() + "().equals(\"null\")) {");
				out.print(strAppend);
				// out.println("\t" + strStatementVar + ".setNull("
				// + String.valueOf(nParamIndex) + ", " +
				// String.valueOf(nFieldType) + ");");
				out.println("\t" + strStatementVar + ".setString(" + String.valueOf(nParamIndex) + ",null);");
				out.print(strAppend);
				out.println("} else {");
				out.print(strAppend);
				// 2007-12-18 ��� trim����
				out.println("\t" + strStatementVar + ".setString(" + String.valueOf(nParamIndex) + ", this.get(i).get"
						+ fieldInfo.getFieldCode() + "().trim());");
				out.print(strAppend);
				out.println("}");
				break;
			case Types.DATE:
				out.print(strAppend);
				out.println("if(this.get(i).get" + fieldInfo.getFieldCode() + "() == null || this.get(i).get"
						+ fieldInfo.getFieldCode() + "().equals(\"null\")) {");
				out.print(strAppend);
				// out.println("\t" + strStatementVar + ".setNull("
				// + String.valueOf(nParamIndex) + ", " +
				// String.valueOf(nFieldType) + ");");
				out.println("\t" + strStatementVar + ".setDate(" + String.valueOf(nParamIndex) + ",null);");
				out.print(strAppend);
				out.println("} else {");
				out.print(strAppend);
				out.println("\t" + strStatementVar + ".setDate(" + String.valueOf(nParamIndex)
						+ ", Date.valueOf(this.get(i).get" + fieldInfo.getFieldCode() + "()));");
				out.print(strAppend);
				out.println("}");
				break;
			case Types.INTEGER:
				out.print(strAppend);
				out.println(strStatementVar + ".setInt(" + String.valueOf(nParamIndex) + ", this.get(i).get"
						+ fieldInfo.getFieldCode() + "());");
				break;
			case Types.NUMERIC:
				out.print(strAppend);
				out.println(strStatementVar + ".setDouble(" + String.valueOf(nParamIndex) + ", this.get(i).get"
						+ fieldInfo.getFieldCode() + "());");
				break;
			default:
				System.out.println("unsupported field type");
		}
	}
}