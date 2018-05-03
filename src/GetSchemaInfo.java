import java.io.FileWriter;
import java.lang.reflect.Field;

class GetSchemaInfo
{
	public static void main(String args[])
	{
		System.out.println(args.length);
		if (args.length < 1)
		{
			System.out.println("Usage : GetSchemaInfo TableName");
			return;
		}

		FileWriter fw = null;
		String strTableName = args[0];
		strTableName = "com.sinosoft.lis.schema." + strTableName + "Schema";

		Field fields[] = null;
		// String strFields[] = null;

		try
		{
			fw = new FileWriter("out.dat");
			fields = Class.forName(strTableName).getDeclaredFields();
			for (int nIndex = 0; nIndex < fields.length; nIndex++)
			{
				fw.write("tLCPolSchema.get" + fields[nIndex].getName() + "();\n");
			}
			fw.close();
		}
		catch (ClassNotFoundException ex)
		{
			ex.printStackTrace();
			return;
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			return;
		}
	}
}
