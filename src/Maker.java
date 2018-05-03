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
 * Description: ������
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
	 * Maker���ɺ��� ����Schema��DB��Set��DBSet��OneTable����ļ�����7���ļ�
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
		// ����Schema
		SchemaMaker mkSchema = new SchemaMaker(args[0]);
		mkSchema.makeAllTable(sUrl);
		// ����DB
		DBOperMaker mkDBOper = new DBOperMaker(args[0]);
		mkDBOper.makeAllTable(sUrl);
		// ����Set
		SetMaker mkSet = new SetMaker(args[0]);
		mkSet.makeAllTable(sUrl);
		// ����DBSet
		DBSetMaker mkDBSet = new DBSetMaker(args[0]);
		mkDBSet.makeAllTable(sUrl);
		// ����VSS�ű�
		// BatMaker tBatMakert = new BatMaker(args[0]);
		// tBatMakert.makeAllTable(sUrl);

		// �����ǣ��м����û��Ӧ���������ܵ�����©�������ļ��޷��ع����Ƚ��鷳��
		System.out.println("----------����������----------");
		// �˳�����
		System.exit(0);
	}
}
