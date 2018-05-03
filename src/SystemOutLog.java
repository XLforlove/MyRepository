public class SystemOutLog
{

	static
	{
		Log4jPrintStream.redirectSystemOut();
	}

	public static void main(String[] args)
	{
		System.out.print("error sql is ....................");
		for (int i = 0; i < 10; i++)
		{
			System.out.print("abc");
			System.out.print(i);
			System.out.print((char) (i + 0x21));
		}
		System.out.print("big bug errorsql is ....................");
		String a = null;
		try
		{
			a.equals("b");
		}
		catch (Exception e)
		{
			e.printStackTrace();
			// TODO: handle exception
		}
	}
}
