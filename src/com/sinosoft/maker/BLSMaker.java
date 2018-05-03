/*
 * <p>ClassName: BLSMaker </p>
 * <p>Description: 生成 TablenameBLS.java 文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @author: GYJ
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

public class BLSMaker
{
	// @Field
	private String DBName;

	private String TableName;

	private String FileName;

	private TableInfo table;

	// @Constructor
	public BLSMaker(String fName)
	{
		FileName = fName;
	}

	public BLSMaker(String fName, String t)
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
		String Path = ".\\com\\sinosoft\\lis\\onetable\\";
		String ClassName = "O" + TableName + "BLS";
		String FileName = ClassName + ".java";
		try
		{
			System.out.println("Creating file ......");
			System.out.println("File Name : " + FileName);

			out = new PrintWriter(new FileWriter(new File(Path + FileName)), true);

			// 生成文件头信息
			out.println("/*");
			out.println(" * <p>ClassName: " + ClassName + " </p>");
			out.println(" * <p>Description: " + TableName + "BLS类文件 </p>");
			out.println(" * <p>Copyright: Copyright (c) 2002</p>");
			out.println(" * <p>Company: sinosoft </p>");
			out.println(" * @Database: " + DBName);
			out.println(" * @CreateDate：" + getDate());
			out.println(" */");
			// @Package
			out.println("package com.sinosoft.lis.onetable;");
			out.println("");
			// @Import
			out.println("import com.sinosoft.lis.vbl.*;");
			out.println("import com.sinosoft.lis.db.*;");
			out.println("import com.sinosoft.lis.vdb.*;");
			out.println("import com.sinosoft.lis.schema.*;");
			out.println("import com.sinosoft.lis.vschema.*;");
			out.println("import com.sinosoft.utility.*;");
			out.println("import java.sql.*;");
			out.println("import com.sinosoft.lis.pubfun.*;");
			out.println("");
			// @Begin
			out.println(" public class " + ClassName + " {");
			out.println("/** 错误处理类，每个需要错误处理的类中都放置该类 */");
			out.println("public  CErrors mErrors=new CErrors();");
			out.println(" /** 数据操作字符串 */");
			out.println("private String mOperate;");
			out.println("public " + ClassName + "() {");
			out.println("	}");
			out.println("public static void main(String[] args) {");
			out.println("}");

			out.println(" /**");
			out.println(" 传输数据的公共方法");
			out.println("*/");
			out.println("public boolean submitData(VData cInputData,String cOperate)");
			out.println("{");
			out.println("     boolean tReturn =false;");
			out.println("     //将操作数据拷贝到本类中");
			out.println("     this.mOperate =cOperate;");
			out.println("");
			out.println("    System.out.println(\"Start " + TableName + "BLS Submit...\");");
			out.println("    if(this.mOperate.equals(\"INSERT||MAIN\"))");
			out.println("    {tReturn=save" + TableName + "(cInputData);}");
			out.println("    if (this.mOperate.equals(\"DELETE||MAIN\"))");
			out.println("    {tReturn=delete" + TableName + "(cInputData);}");
			out.println("    if (this.mOperate.equals(\"UPDATE||MAIN\"))");
			out.println("    {tReturn=update" + TableName + "(cInputData);}");
			out.println("    if (tReturn)");
			out.println("        System.out.println(\" sucessful\");");
			out.println("    else");
			out.println("        System.out.println(\"Save failed\") ;");
			out.println("        System.out.println(\"End " + TableName + "BLS Submit...\");");
			out.println("  return tReturn;");
			out.println("}");
			out.println(" /**");
			out.println("* 保存函数");
			out.println("*/");
			out.println("private boolean save" + TableName + "(VData mInputData)");
			out.println("{");
			out.println("  boolean tReturn =true;");
			out.println("  System.out.println(\"Start Save...\");");
			out.println("  Connection conn;");
			out.println("  conn=null;");
			out.println("  conn=DBConnPool.getConnection();");
			out.println("  if (conn==null)");
			out.println("  {");
			out.println("  		// @@错误处理");
			out.println(" 		CError tError = new CError();");
			out.println("           tError.moduleName = \"" + TableName + "BLS\";");
			out.println("           tError.functionName = \"saveData\";");
			out.println("           tError.errorMessage = \"数据库连接失败!\";");
			out.println("           this.mErrors .addOneError(tError) ;");
			out.println("	        return false;");
			out.println("   }");
			out.println("	try{");
			out.println(" 		conn.setAutoCommit(false);");
			out.println("		System.out.println(\"Start 保存...\");");
			out.println("           " + TableName + "DB t" + TableName + "DB=new " + TableName + "DB(conn);");
			out.println("           t" + TableName + "DB.setSchema((" + TableName
					+ "Schema)mInputData.getObjectByObjectName(\"" + TableName + "Schema\",0));");
			out.println("           if (!t" + TableName + "DB.insert())");
			out.println("           {");
			out.println("		// @@错误处理");
			out.println("	   	this.mErrors.copyAllErrors(t" + TableName + "DB.mErrors);");
			out.println("		CError tError = new CError();");
			out.println("           tError.moduleName = \"" + TableName + "BLS\";");
			out.println("           tError.functionName = \"saveData\";");
			out.println("           tError.errorMessage = \"数据保存失败!\";");
			out.println("           this.mErrors .addOneError(tError) ;");
			out.println("           conn.rollback();");
			out.println("           conn.close() ;");
			out.println("           return false;");
			out.println("}");
			out.println("           conn.commit() ;");
			out.println("           conn.close() ;");
			out.println("}");
			out.println("           catch (Exception ex)");
			out.println("           {");
			out.println("           // @@错误处理");
			out.println("               CError tError =new CError();");
			out.println("               tError.moduleName=\"" + TableName + "BLS\";");
			out.println("               tError.functionName=\"submitData\";");
			out.println("               tError.errorMessage=ex.toString();");
			out.println("               this.mErrors .addOneError(tError);");
			out.println("               tReturn=false;");
			out.println("               try{conn.rollback();conn.close();} catch(Exception e){}");
			out.println("	       }");
			out.println("               return tReturn;");
			out.println("   }");
			out.println("    /**");
			out.println("    * 保存函数");
			out.println("    */");
			out.println("    private boolean delete" + TableName + "(VData mInputData)");
			out.println("    {");
			out.println("        boolean tReturn =true;");
			out.println("        System.out.println(\"Start Save...\");");
			out.println("        Connection conn;");
			out.println("        conn=null;");
			out.println("        conn=DBConnPool.getConnection();");
			out.println("        if (conn==null)");
			out.println("        {");
			out.println("		// @@错误处理");
			out.println("		CError tError = new CError();");
			out.println("           tError.moduleName = \"" + TableName + "BLS\";");
			out.println("           tError.functionName = \"saveData\";");
			out.println("           tError.errorMessage = \"数据库连接失败!\";");
			out.println("           this.mErrors .addOneError(tError) ;");
			out.println("           return false;");
			out.println("        }");
			out.println("        try{");
			out.println("           conn.setAutoCommit(false);");
			out.println("           System.out.println(\"Start 保存...\");");
			out.println("           " + TableName + "DB t" + TableName + "DB=new " + TableName + "DB(conn);");
			out.println("           t" + TableName + "DB.setSchema((" + TableName
					+ "Schema)mInputData.getObjectByObjectName(\"" + TableName + "Schema\",0));");
			out.println("           if (!t" + TableName + "DB.delete())");
			out.println("           {");
			out.println("		// @@错误处理");
			out.println("		    this.mErrors.copyAllErrors(t" + TableName + "DB.mErrors);");
			out.println(" 		    CError tError = new CError();");
			out.println("		    tError.moduleName = \"" + TableName + "BLS\";");
			out.println("		    tError.functionName = \"saveData\";");
			out.println("		    tError.errorMessage = \"数据删除失败!\";");
			out.println("		    this.mErrors .addOneError(tError) ;");
			out.println("               conn.rollback();");
			out.println("           conn.close() ;");
			out.println("               return false;");
			out.println("           }");
			out.println("               conn.commit() ;");
			out.println("           conn.close() ;");
			out.println("         }");
			out.println("       catch (Exception ex)");
			out.println("       {");
			out.println("      // @@错误处理");
			out.println("          CError tError =new CError();");
			out.println("          tError.moduleName=\"" + TableName + "BLS\";");
			out.println("          tError.functionName=\"submitData\";");
			out.println("          tError.errorMessage=ex.toString();");
			out.println("          this.mErrors .addOneError(tError);");
			out.println("          tReturn=false;");
			out.println("          try{conn.rollback();conn.close();} catch(Exception e){}");
			out.println("         }");
			out.println("         return tReturn;");
			out.println("}");
			out.println("/**");
			out.println("  * 保存函数");
			out.println("*/");
			out.println("private boolean update" + TableName + "(VData mInputData)");
			out.println("{");
			out.println("     boolean tReturn =true;");
			out.println("     System.out.println(\"Start Save...\");");
			out.println("     Connection conn;");
			out.println("     conn=null;");
			out.println("     conn=DBConnPool.getConnection();");
			out.println("     if (conn==null)");
			out.println("     {");
			// @@错误处理");
			out.println("	     CError tError = new CError();");
			out.println("        tError.moduleName = \"" + TableName + "BLS\";");
			out.println("        tError.functionName = \"updateData\";");
			out.println("        tError.errorMessage = \"数据库连接失败!\";");
			out.println("        this.mErrors .addOneError(tError) ;");
			out.println("        return false;");
			out.println("     }");
			out.println("     try{");
			out.println("           conn.setAutoCommit(false);");
			out.println("           System.out.println(\"Start 保存...\");");
			out.println("           " + TableName + "DB t" + TableName + "DB=new " + TableName + "DB(conn);");
			out.println("		t" + TableName + "DB.setSchema((" + TableName + "Schema)mInputData.getObjectByObjectName(\""
					+ TableName + "Schema\",0));");
			out.println("           if (!t" + TableName + "DB.update())");
			out.println("           {");
			out.println("	          // @@错误处理");
			out.println("	         this.mErrors.copyAllErrors(t" + TableName + "DB.mErrors);");
			out.println("	         CError tError = new CError();");
			out.println("	         tError.moduleName = \"" + TableName + "BLS\";");
			out.println("	         tError.functionName = \"saveData\";");
			out.println("            tError.errorMessage = \"数据保存失败!\";");
			out.println("            this.mErrors .addOneError(tError) ;");
			out.println("            conn.rollback();");
			out.println("           conn.close() ;");
			out.println("            return false;");
			out.println("            }");
			out.println("            conn.commit() ;");
			out.println("           conn.close() ;");
			out.println("       }");
			out.println("       catch (Exception ex)");
			out.println("       {");
			out.println("       // @@错误处理");
			out.println("               CError tError =new CError();");
			out.println("               tError.moduleName=\"" + TableName + "BLS\";");
			out.println("               tError.functionName=\"submitData\";");
			out.println("               tError.errorMessage=ex.toString();");
			out.println("               this.mErrors .addOneError(tError);");
			out.println("               tReturn=false;");
			out.println("               try{conn.rollback();conn.close();} catch(Exception e){}");
			out.println("     }");
			out.println("               return tReturn;");
			out.println("     }");
			out.println("}");
			// 生成结尾
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
