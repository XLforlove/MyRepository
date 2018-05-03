/*
 * <p>ClassName: BLSMaker </p>
 * <p>Description: ���� TablenameBLS.java �ļ� </p>
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
		 * �����ݿ���ȡ�������ֶ�ʱʹ�� TableName = TableName.toLowerCase(); TableName =
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
		 * �����ݿ���ȡ�������ֶ�ʱʹ�� JMetaDB db = new JMetaDB(sUrl); DBName =
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
		 * �����ݿ���ȡ�������ֶ�ʱʹ�� JMetaDB db = new JMetaDB(sUrl); DBName =
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

			// �����ļ�ͷ��Ϣ
			out.println("/*");
			out.println(" * <p>ClassName: " + ClassName + " </p>");
			out.println(" * <p>Description: " + TableName + "BLS���ļ� </p>");
			out.println(" * <p>Copyright: Copyright (c) 2002</p>");
			out.println(" * <p>Company: sinosoft </p>");
			out.println(" * @Database: " + DBName);
			out.println(" * @CreateDate��" + getDate());
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
			out.println("/** �������࣬ÿ����Ҫ����������ж����ø��� */");
			out.println("public  CErrors mErrors=new CErrors();");
			out.println(" /** ���ݲ����ַ��� */");
			out.println("private String mOperate;");
			out.println("public " + ClassName + "() {");
			out.println("	}");
			out.println("public static void main(String[] args) {");
			out.println("}");

			out.println(" /**");
			out.println(" �������ݵĹ�������");
			out.println("*/");
			out.println("public boolean submitData(VData cInputData,String cOperate)");
			out.println("{");
			out.println("     boolean tReturn =false;");
			out.println("     //���������ݿ�����������");
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
			out.println("* ���溯��");
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
			out.println("  		// @@������");
			out.println(" 		CError tError = new CError();");
			out.println("           tError.moduleName = \"" + TableName + "BLS\";");
			out.println("           tError.functionName = \"saveData\";");
			out.println("           tError.errorMessage = \"���ݿ�����ʧ��!\";");
			out.println("           this.mErrors .addOneError(tError) ;");
			out.println("	        return false;");
			out.println("   }");
			out.println("	try{");
			out.println(" 		conn.setAutoCommit(false);");
			out.println("		System.out.println(\"Start ����...\");");
			out.println("           " + TableName + "DB t" + TableName + "DB=new " + TableName + "DB(conn);");
			out.println("           t" + TableName + "DB.setSchema((" + TableName
					+ "Schema)mInputData.getObjectByObjectName(\"" + TableName + "Schema\",0));");
			out.println("           if (!t" + TableName + "DB.insert())");
			out.println("           {");
			out.println("		// @@������");
			out.println("	   	this.mErrors.copyAllErrors(t" + TableName + "DB.mErrors);");
			out.println("		CError tError = new CError();");
			out.println("           tError.moduleName = \"" + TableName + "BLS\";");
			out.println("           tError.functionName = \"saveData\";");
			out.println("           tError.errorMessage = \"���ݱ���ʧ��!\";");
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
			out.println("           // @@������");
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
			out.println("    * ���溯��");
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
			out.println("		// @@������");
			out.println("		CError tError = new CError();");
			out.println("           tError.moduleName = \"" + TableName + "BLS\";");
			out.println("           tError.functionName = \"saveData\";");
			out.println("           tError.errorMessage = \"���ݿ�����ʧ��!\";");
			out.println("           this.mErrors .addOneError(tError) ;");
			out.println("           return false;");
			out.println("        }");
			out.println("        try{");
			out.println("           conn.setAutoCommit(false);");
			out.println("           System.out.println(\"Start ����...\");");
			out.println("           " + TableName + "DB t" + TableName + "DB=new " + TableName + "DB(conn);");
			out.println("           t" + TableName + "DB.setSchema((" + TableName
					+ "Schema)mInputData.getObjectByObjectName(\"" + TableName + "Schema\",0));");
			out.println("           if (!t" + TableName + "DB.delete())");
			out.println("           {");
			out.println("		// @@������");
			out.println("		    this.mErrors.copyAllErrors(t" + TableName + "DB.mErrors);");
			out.println(" 		    CError tError = new CError();");
			out.println("		    tError.moduleName = \"" + TableName + "BLS\";");
			out.println("		    tError.functionName = \"saveData\";");
			out.println("		    tError.errorMessage = \"����ɾ��ʧ��!\";");
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
			out.println("      // @@������");
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
			out.println("  * ���溯��");
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
			// @@������");
			out.println("	     CError tError = new CError();");
			out.println("        tError.moduleName = \"" + TableName + "BLS\";");
			out.println("        tError.functionName = \"updateData\";");
			out.println("        tError.errorMessage = \"���ݿ�����ʧ��!\";");
			out.println("        this.mErrors .addOneError(tError) ;");
			out.println("        return false;");
			out.println("     }");
			out.println("     try{");
			out.println("           conn.setAutoCommit(false);");
			out.println("           System.out.println(\"Start ����...\");");
			out.println("           " + TableName + "DB t" + TableName + "DB=new " + TableName + "DB(conn);");
			out.println("		t" + TableName + "DB.setSchema((" + TableName + "Schema)mInputData.getObjectByObjectName(\""
					+ TableName + "Schema\",0));");
			out.println("           if (!t" + TableName + "DB.update())");
			out.println("           {");
			out.println("	          // @@������");
			out.println("	         this.mErrors.copyAllErrors(t" + TableName + "DB.mErrors);");
			out.println("	         CError tError = new CError();");
			out.println("	         tError.moduleName = \"" + TableName + "BLS\";");
			out.println("	         tError.functionName = \"saveData\";");
			out.println("            tError.errorMessage = \"���ݱ���ʧ��!\";");
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
			out.println("       // @@������");
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
			// ���ɽ�β
			System.out.println("Create file success!!");
		} // end of try
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}

	// �õ���ǰ����
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
