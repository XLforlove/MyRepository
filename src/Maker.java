import com.sinosoft.maker.DBOperMaker;
import com.sinosoft.maker.DBSetMaker;
import com.sinosoft.maker.SchemaMaker;
import com.sinosoft.maker.SetMaker;
import com.sinosoft.utility.JdbcUrl;

/**
 * <p>
 * ClassName:
 * </p>
 * <p>
 * Description: 生成器
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
public class Maker
{
	/**
	 * Maker生成函数 生成Schema、DB、Set、DBSet和OneTable五个文件夹下7个文件
	 * @param args String[]
	 */
	public static void main(String[] args)
	{
		String[] args1;
		args1 = new String[1];
		args1[0] = "my.pdm";
		args = args1;

		JdbcUrl sUrl = new JdbcUrl();
		String s = sUrl.getJdbcUrl();
		System.out.println(s);
		// 生成Schema
		SchemaMaker mkSchema = new SchemaMaker(args[0]);
		mkSchema.makeAllTable(sUrl);
		// 生成DB
		DBOperMaker mkDBOper = new DBOperMaker(args[0]);
		mkDBOper.makeAllTable(sUrl);
		// 生成Set
		SetMaker mkSet = new SetMaker(args[0]);
		mkSet.makeAllTable(sUrl);
		// 生成DBSet
		DBSetMaker mkDBSet = new DBSetMaker(args[0]);
		mkDBSet.makeAllTable(sUrl);
		// 生成VSS脚本
		// BatMaker tBatMakert = new BatMaker(args[0]);
		// tBatMakert.makeAllTable(sUrl);

		// 坏处是，中间出错，没有应急处理，可能导致遗漏（生成文件无法回滚，比较麻烦）
		System.out.println("----------工作结束！----------");
		// 退出操作
		System.exit(0);
	}
}
