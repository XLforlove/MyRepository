package com.sinosoft.utility;

/**
 * <p>
 * Title: Life Information System
 * </p>
 * <p>
 * Description: ���ݿ�ͬ������
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * @author Kevin
 * @version 1.0
 */

public class DBSemaphore
{

	private static boolean m_bInUse = false;

	private static String m_szObject = "DBSemaphore";

	public DBSemaphore()
	{}

	/**
	 * ����"��־"����ԭ�����󡰱�־�������á���־���Ĳ����ŵ�һ��������С� �����ǰ����־���Ѿ���ʹ�ã�����true��
	 * �����ǰ����־��û�б�ʹ�ã����ϱ�־������false;
	 * @return
	 */
	protected static synchronized boolean getFlag()
	{
		if (m_bInUse)
		{
			System.out.println(m_szObject);
			return true;
		}
		else
		{
			setFlag(true);
			return false;
		}
	}

	protected static synchronized void setFlag(boolean bNewValue)
	{
		m_bInUse = bNewValue;
	}

	protected static void Lock() throws Exception
	{
		Lock(0);
	}

	protected static void Lock(int nSeconds) throws Exception
	{
		if (nSeconds <= 0)
		{
			while (getFlag())
			{
				Thread.sleep(100);
			}
		}
		else
		{
			while (getFlag() && nSeconds-- > 0)
			{
				Thread.sleep(100);
			}

			if (nSeconds == 0)
			{
				throw new Exception("Lock time out");
			}
		}

		// setFlag(true);
	}

	protected static void UnLock()
	{
		setFlag(false);
	}
}