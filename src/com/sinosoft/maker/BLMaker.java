/*
 * <p>ClassName: BLMaker </p>
 * <p>Description: ���� TablenameBL.java �ļ� </p>
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
		String SetName = "O" + TableName;
		String ClassName = SetName + "BL";
		String FileName = ClassName + ".java";
		try
		{
			System.out.println("Creating file ......");
			System.out.println("File Name : " + FileName);

			out = new PrintWriter(new FileWriter(new File(Path + FileName)), true);

			// �����ļ�ͷ��Ϣ
			out.println("/*");
			out.println(" * <p>ClassName: " + ClassName + " </p>");
			out.println(" * <p>Description: " + TableName + "BL���ļ� </p>");
			out.println(" * <p>Copyright: Copyright (c) 2002</p>");
			out.println(" * <p>Company: sinosoft </p>");
			out.println(" * @Database: " + DBName);
			out.println(" * @CreateDate��" + getDate());
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
			out.println("/** �������࣬ÿ����Ҫ����������ж����ø��� */");
			out.println("public  CErrors mErrors=new CErrors();");
			out.println("private VData mResult = new VData();");
			out.println(" /** �����洫�����ݵ����� */");
			out.println(" private VData mInputData;");
			out.println(" /** ȫ������ */");
			out.println(" private GlobalInput mGlobalInput =new GlobalInput() ;");
			out.println(" /** ���ݲ����ַ��� */");
			out.println(" private String mOperate;");
			out.println("/** ҵ������ر��� */");
			out.println(" private " + TableName + "Schema m" + TableName + "Schema=new " + TableName + "Schema();");
			out.println(" private " + TableName + "Set m" + TableName + "Set=new " + TableName + "Set();");
			out.println(" public " + ClassName + "() {");
			out.println(" }");
			out.println("public static void main(String[] args) {");

			out.println("}");

			out.println("/**");
			out.println("* �������ݵĹ�������");
			out.println("	* @param: cInputData ���������");
			out.println("   *         cOperate ���ݲ���");
			out.println("* @return:");
			out.println("*/");
			out.println(" public boolean submitData(VData cInputData,String cOperate)");
			out.println(" {");
			out.println("       //���������ݿ�����������");
			out.println("       this.mOperate =cOperate;");
			out.println("       //�õ��ⲿ���������,�����ݱ��ݵ�������");
			out.println("       if (!getInputData(cInputData))");
			out.println("            return false;");
			out.println("       //����ҵ����");
			out.println("       if (!dealData())");
			out.println("       {");
			out.println("	       // @@������");
			out.println("          CError tError = new CError();");
			out.println("          tError.moduleName = \"" + TableName + "BL\";");
			out.println("          tError.functionName = \"submitData\";");
			out.println("          tError.errorMessage = \"���ݴ���ʧ��" + TableName + "BL-->dealData!\";");
			out.println("          this.mErrors .addOneError(tError) ;");
			out.println("          return false;");
			out.println("       }");
			out.println("     //׼������̨������");
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

			out.println("        //�������Ҫ����Ĵ����򷵻�");
			out.println("        if (t" + SetName + "BLS.mErrors.needDealError())");
			out.println("        {");
			out.println("		// @@������");
			out.println("		this.mErrors.copyAllErrors(t" + SetName + "BLS.mErrors);");
			out.println("		CError tError = new CError();");
			out.println("		tError.moduleName = \"" + SetName + "BL\";");
			out.println("		tError.functionName = \"submitDat\";");
			out.println("		tError.errorMessage =\"�����ύʧ��!\";");
			out.println("		this.mErrors .addOneError(tError) ;");
			out.println("		return false;");
			out.println("         }");
			out.println("    }");
			out.println("        mInputData=null;");
			out.println("        return true;");
			out.println("}");
			out.println(" /**");
			out.println(" * ����ǰ����������ݣ�����BL�߼�����");
			out.println(" * ����ڴ�������г����򷵻�false,���򷵻�true");
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
			out.println(" * �����������еõ����ж���");
			out.println(" *��������û�еõ��㹻��ҵ�����ݶ����򷵻�false,���򷵻�true");
			out.println(" */");
			out.println("private boolean getInputData(VData cInputData)");
			out.println("{");
			out.println("         this.m" + TableName + "Schema.setSchema((" + TableName
					+ "Schema)cInputData.getObjectByObjectName(\"" + TableName + "Schema\",0));");
			out.println("         this.mGlobalInput.setSchema((GlobalInput)cInputData.getObjectByObjectName(\"GlobalInput\",0));");
			out.println("         return true;");
			out.println("}");
			out.println("/**");
			out.println("* ׼��������������Ҫ������");
			out.println("* ��������׼������ʱ���������򷵻�false,���򷵻�true");
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
			out.println("		//�������Ҫ����Ĵ����򷵻�");
			out.println("		if (t" + TableName + "DB.mErrors.needDealError())");
			out.println(" 		{");
			out.println("		// @@������");
			out.println("  			this.mErrors.copyAllErrors(t" + TableName + "DB.mErrors);");
			out.println("			CError tError = new CError();");
			out.println("			tError.moduleName = \"" + TableName + "BL\";");
			out.println("			tError.functionName = \"submitData\";");
			out.println("			tError.errorMessage = \"�����ύʧ��!\";");
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
			out.println(" 		 // @@������");
			out.println("		CError tError =new CError();");
			out.println(" 		tError.moduleName=\"" + TableName + "BL\";");
			out.println(" 		tError.functionName=\"prepareData\";");
			out.println(" 		tError.errorMessage=\"��׼������㴦������Ҫ������ʱ����\";");
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