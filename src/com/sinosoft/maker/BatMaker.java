package com.sinosoft.maker;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Vector;

import com.sinosoft.utility.JdbcUrl;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * ClassName: BatMaker
 * </p>
 * <p>
 * Description: VSS脚本生成
 * </p>
 * @author ZhuXF
 * @version 1.0
 * @modify 2007-4-10
 * @depict 人生五十載，去事恍如夢幻，普天之下，豈有長生不滅者！
 */
public class BatMaker
{
	private String TableName;

	private String FileName;

	private TableInfo table;

	/**
	 * 构建函数
	 * @param fName
	 */
	public BatMaker(String fName)
	{
		FileName = fName;
	}

	/**
	 * 生成所有表的vss脚本
	 * @param sUrl
	 */
	public void makeAllTable(JdbcUrl sUrl)
	{
		PDM db = new PDM(FileName);
		try
		{
			Vector t = db.getTables();
			this.create(t);
		}
		catch (Exception e)
		{
			// e.printStackTrace();
			System.out.println("Create failde!!");
			System.out.println(e.toString());
			System.exit(0);
		}
	}

	/**
	 * 创建批处理脚本
	 * @param cPDM
	 */
	public void create(Vector cPDM)
	{
		PrintWriter out = null;
		String Path = "D:/";
		FileName = "vss_check_in_boc.bat";

		// 本地vss工程路径，tPath个险路径，tPath2团险路径

		String tPath = "D:\\workspace\\reinsure\\src\\com\\sinosoft\\lis\\";
		// 进入本地vss工作目录
		String tLocalPath = "cd " + tPath;
		// vss工程目录

		String tVSSPath = "$/开发域/03_编码/reinsure/src/com/sinosoft/lis/";
		// vss命令行参数，-K签入的同时签出，-I-无备注信息，-Y用户和密码
		String tCLD = " -I- -Ywang,1";
		// vss命令行参数2
		String tPWD = " -I- -Ywang,1";

		String tMakerPath = null;
		try
		{
			tMakerPath = new File("src/com/sinosoft/lis").getCanonicalPath() + "\\";
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try
		{
			out = new PrintWriter(new FileWriter(new File(Path + FileName)), true);
			out.println("echo on");
			out.println("");
			// 设置vss数据库目录
			out.println("set SSDIR=\\\\10.100.11.126\\vss\\");
			out.println("");
			out.println("D:");
			out.println("");
			out.println(tLocalPath + "db\\");
			int n = cPDM.size();
			for (int i = 0; i < n; i++)
			{
				table = (TableInfo) cPDM.get(i);
				TableName = table.getTableCode();
				File tFile = new File(tPath + "db\\" + TableName + "DB.java");
				out.println("attrib -r " + tFile);
				out.println("copy /y /b " + tMakerPath + "db\\" + TableName + "DB.java" + " " + tFile);
				if (tFile.exists())
				{

					// 将文件checkout出来，在Checkin进去
					out.println("ss Checkout " + tVSSPath + "db/" + TableName + "DB.java" + tPWD);
					out.println("ss Checkin " + tVSSPath + "db/" + TableName + "DB.java" + tCLD);
				}
				else
				{
					// 进入需要add的vss功能目录
					out.println("ss Cp " + tVSSPath + "db/" + tPWD);
					// 添加文件
					out.println("ss Add " + TableName + "DB.java" + tPWD);
				}
			}
			out.println("");
			out.println(tLocalPath + "schema\\");
			for (int i = 0; i < n; i++)
			{
				table = (TableInfo) cPDM.get(i);
				TableName = table.getTableCode();
				File tFile = new File(tPath + "schema\\" + TableName + "Schema.java");
				out.println("attrib -r " + tFile);
				out.println("copy /y /b " + tMakerPath + "schema\\" + TableName + "Schema.java" + " " + tFile);
				if (tFile.exists())
				{
					out.println("ss Checkout " + tVSSPath + "schema/" + TableName + "Schema.java" + tPWD);
					out.println("ss Checkin " + tVSSPath + "schema/" + TableName + "Schema.java" + tCLD);
				}
				else
				{
					out.println("ss Cp " + tVSSPath + "schema/" + tPWD);
					out.println("ss Add " + TableName + "Schema.java" + tPWD);
				}
			}
			out.println("");
			out.println(tLocalPath + "vdb\\");
			for (int i = 0; i < n; i++)
			{
				table = (TableInfo) cPDM.get(i);
				TableName = table.getTableCode();
				File tFile = new File(tPath + "vdb\\" + TableName + "DBSet.java");
				out.println("attrib -r " + tFile);
				out.println("copy /y /b " + tMakerPath + "vdb\\" + TableName + "DBSet.java" + " " + tFile);
				if (tFile.exists())
				{
					out.println("ss Checkout " + tVSSPath + "vdb/" + TableName + "DBSet.java" + tPWD);
					out.println("ss Checkin " + tVSSPath + "vdb/" + TableName + "DBSet.java" + tCLD);
				}
				else
				{
					out.println("ss Cp " + tVSSPath + "vdb/" + tPWD);
					out.println("ss Add " + TableName + "DBSet.java" + tPWD);
				}
			}
			out.println("");
			out.println(tLocalPath + "vschema\\");
			for (int i = 0; i < n; i++)
			{
				table = (TableInfo) cPDM.get(i);
				TableName = table.getTableCode();
				File tFile = new File(tPath + "vschema\\" + TableName + "Set.java");
				out.println("attrib -r " + tFile);
				out.println("copy /y /b " + tMakerPath + "vschema\\" + TableName + "Set.java" + " " + tFile);
				if (tFile.exists())
				{
					out.println("ss Checkout " + tVSSPath + "vschema/" + TableName + "Set.java" + tPWD);
					out.println("ss Checkin " + tVSSPath + "vschema/" + TableName + "Set.java" + tCLD);
				}
				else
				{
					out.println("ss Cp " + tVSSPath + "vschema/" + tPWD);
					out.println("ss Add " + TableName + "Set.java" + tPWD);
				}
			}
			out.println("");

			out.println("pause");
			out.close();
			System.out.println("Create file success!!");
		} // end of try
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}

	public static void main(String[] args) throws IOException
	{
		System.out.println();
	}
}
