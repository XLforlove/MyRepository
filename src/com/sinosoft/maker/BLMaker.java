/*
 * <p>ClassName: BLMaker </p>
 * <p>Description: 生成 TablenameBL.java 文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @author: GYJ
 * @version: 1.0
 * @date: 2002-08-6
 */
package com.sinosoft.maker;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Vector;

import com.sinosoft.utility.JdbcUrl;

public class BLMaker
{
	// @Field
	private String DBName;

	private String TableName;

	private String FileName;

	private TableInfo table;

	// @Constructor
	public BLMaker(String fName)
	{
		FileName = fName;
	}

	public BLMaker(String fName, String t)
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
		String SetName = "O" + TableName;
		String ClassName = SetName + "BL";
		String FileName = ClassName + ".java";
		try
		{
			System.out.println("Creating file ......");
			System.out.println("File Name : " + FileName);

			out = new PrintWriter(new FileWriter(new File(Path + FileName)), true);

			// 生成文件头信息
			out.println("/*");
			out.println(" * <p>ClassName: " + ClassName + " </p>");
			out.println(" * <p>Description: " + TableName + "BL类文件 </p>");
			out.println(" * <p>Copyright: Copyright (c) 2002</p>");
			out.println(" * <p>Company: sinosoft </p>");
			out.println(" * @Database: " + DBName);
			out.println(" * @CreateDate：" + getDate());
			out.println(" */");
			// @Package
			out.println("package com.sinosoft.lis.onetable;");
			out.println("");
			// @Import
			out.println("import com.sinosoft.lis.db.*;");
			out.println("import com.sinosoft.lis.vdb.*;");
			out.println("import com.sinosoft.lis.onetable.*;");
			out.println("import com.sinosoft.lis.schema.*;");
			out.println("import com.sinosoft.lis.vschema.*;");
			out.println("import com.sinosoft.utility.*;");
			out.println("import com.sinosoft.lis.pubfun.*;");
			out.println("");
			// @Begin
			out.println("public class " + ClassName + "  {");
			out.println("/** 错误处理类，每个需要错误处理的类中都放置该类 */");
			out.println("public  CErrors mErrors=new CErrors();");
			out.println("private VData mResult = new VData();");
			out.println(" /** 往后面传输数据的容器 */");
			out.println(" private VData mInputData;");
			out.println(" /** 全局数据 */");
			out.println(" private GlobalInput mGlobalInput =new GlobalInput() ;");
			out.println(" /** 数据操作字符串 */");
			out.println(" private String mOperate;");
			out.println("/** 业务处理相关变量 */");
			out.println(" private " + TableName + "Schema m" + TableName + "Schema=new " + TableName + "Schema();");
			out.println(" private " + TableName + "Set m" + TableName + "Set=new " + TableName + "Set();");
			out.println(" public " + ClassName + "() {");
			out.println(" }");
			out.println("public static void main(String[] args) {");

			out.println("}");

			out.println("/**");
			out.println("* 传输数据的公共方法");
			out.println("	* @param: cInputData 输入的数据");
			out.println("   *         cOperate 数据操作");
			out.println("* @return:");
			out.println("*/");
			out.println(" public boolean submitData(VData cInputData,String cOperate)");
			out.println(" {");
			out.println("       //将操作数据拷贝到本类中");
			out.println("       this.mOperate =cOperate;");
			out.println("       //得到外部传入的数据,将数据备份到本类中");
			out.println("       if (!getInputData(cInputData))");
			out.println("            return false;");
			out.println("       //进行业务处理");
			out.println("       if (!dealData())");
			out.println("       {");
			out.println("	       // @@错误处理");
			out.println("          CError tError = new CError();");
			out.println("          tError.moduleName = \"" + TableName + "BL\";");
			out.println("          tError.functionName = \"submitData\";");
			out.println("          tError.errorMessage = \"数据处理失败" + TableName + "BL-->dealData!\";");
			out.println("          this.mErrors .addOneError(tError) ;");
			out.println("          return false;");
			out.println("       }");
			out.println("     //准备往后台的数据");
			out.println("     if (!prepareOutputData())");
			out.println("        return false;");
			out.println("     if (this.mOperate.equals(\"QUERY||MAIN\"))");
			out.println("     {");
			out.println("            this.submitquery();");
			out.println("     }");
			out.println("     else");
			out.println("    {");
			out.println("        System.out.println(\"Start " + SetName + "BL Submit...\");");
			out.println("        " + SetName + "BLS t" + SetName + "BLS=new " + SetName + "BLS();");
			out.println("        t" + SetName + "BLS.submitData(mInputData,cOperate);");
			out.println("        System.out.println(\"End " + SetName + "BL Submit...\");");

			out.println("        //如果有需要处理的错误，则返回");
			out.println("        if (t" + SetName + "BLS.mErrors.needDealError())");
			out.println("        {");
			out.println("		// @@错误处理");
			out.println("		this.mErrors.copyAllErrors(t" + SetName + "BLS.mErrors);");
			out.println("		CError tError = new CError();");
			out.println("		tError.moduleName = \"" + SetName + "BL\";");
			out.println("		tError.functionName = \"submitDat\";");
			out.println("		tError.errorMessage =\"数据提交失败!\";");
			out.println("		this.mErrors .addOneError(tError) ;");
			out.println("		return false;");
			out.println("         }");
			out.println("    }");
			out.println("        mInputData=null;");
			out.println("        return true;");
			out.println("}");
			out.println(" /**");
			out.println(" * 根据前面的输入数据，进行BL逻辑处理");
			out.println(" * 如果在处理过程中出错，则返回false,否则返回true");
			out.println("*/");
			out.println("private boolean dealData()");
			out.println("{");
			out.println("         boolean tReturn =true;");
			out.println("      //   this.m" + TableName + "Schema.setModifyDate(getDate());");
			out.println("     //  this.m" + TableName + "Schema.setModifyTime();");
			out.println("         tReturn=true;");
			out.println("         return tReturn ;");
			out.println("}");
			out.println(" /**");
			out.println(" * 从输入数据中得到所有对象");
			out.println(" *输出：如果没有得到足够的业务数据对象，则返回false,否则返回true");
			out.println(" */");
			out.println("private boolean getInputData(VData cInputData)");
			out.println("{");
			out.println("         this.m" + TableName + "Schema.setSchema((" + TableName
					+ "Schema)cInputData.getObjectByObjectName(\"" + TableName + "Schema\",0));");
			out.println("         this.mGlobalInput.setSchema((GlobalInput)cInputData.getObjectByObjectName(\"GlobalInput\",0));");
			out.println("         return true;");
			out.println("}");
			out.println("/**");
			out.println("* 准备往后层输出所需要的数据");
			out.println("* 输出：如果准备数据时发生错误则返回false,否则返回true");
			out.println("*/");
			out.println(" private boolean submitquery()");
			out.println("{");
			out.println("           this.mResult.clear();");
			out.println("           System.out.println(\"Start " + TableName + "BLQuery Submit...\");");
			out.println(" 	        " + TableName + "DB t" + TableName + "DB=new " + TableName + "DB();");
			out.println("	        t" + TableName + "DB.setSchema(this.m" + TableName + "Schema);");
			out.println("	        this.m" + TableName + "Set=t" + TableName + "DB.query();");
			out.println("	        this.mResult.add(this.m" + TableName + "Set);");
			out.println("		System.out.println(\"End " + TableName + "BLQuery Submit...\");");
			out.println("		//如果有需要处理的错误，则返回");
			out.println("		if (t" + TableName + "DB.mErrors.needDealError())");
			out.println(" 		{");
			out.println("		// @@错误处理");
			out.println("  			this.mErrors.copyAllErrors(t" + TableName + "DB.mErrors);");
			out.println("			CError tError = new CError();");
			out.println("			tError.moduleName = \"" + TableName + "BL\";");
			out.println("			tError.functionName = \"submitData\";");
			out.println("			tError.errorMessage = \"数据提交失败!\";");
			out.println("			this.mErrors .addOneError(tError) ;");
			out.println(" 			return false;");
			out.println("}");
			out.println("                   mInputData=null;");
			out.println("                   return true;");
			out.println("}");
			out.println(" private boolean prepareOutputData()");
			out.println(" {");
			out.println("   try");
			out.println("	{");
			out.println("		this.mInputData=new VData();");
			out.println("		this.mInputData.add(this.mGlobalInput);");
			out.println("		this.mInputData.add(this.m" + TableName + "Schema);");
			out.println("	}");
			out.println("	catch(Exception ex)");
			out.println("	{");
			out.println(" 		 // @@错误处理");
			out.println("		CError tError =new CError();");
			out.println(" 		tError.moduleName=\"" + TableName + "BL\";");
			out.println(" 		tError.functionName=\"prepareData\";");
			out.println(" 		tError.errorMessage=\"在准备往后层处理所需要的数据时出错。\";");
			out.println(" 		this.mErrors .addOneError(tError) ;");
			out.println("		return false;");
			out.println("	}");
			out.println("	return true;");
			out.println("	}");
			out.println("	public VData getResult()");
			out.println("	{");
			// out.println(" return this.m"+TableName+"Set;");
			out.println("  		return this.mResult;");
			out.println("	}");
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