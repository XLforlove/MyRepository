package com.sinosoft.lis.pubfun;

/**
 * <p>
 * Title: Webҵ��ϵͳ
 * </p>
 * <p>
 * Description: ȫ�ֱ�����
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * @author YT
 * @version 1.0
 */

public class GlobalInput
{
	/** ��ǰ����Ա */
	public String Operator;

	/** ��ǰ������� */
	public String ManageCom;

	/** ��ǰ��½���� */
	public String ComCode;

	// /** ��ǰ���� */
	// public String RiskCode;
	// /** ��ǰ���ְ汾 */
	// public String RiskVersion;

	public GlobalInput()
	{}

	public static void main(String[] args)
	{
//		GlobalInput globalInput1 = new GlobalInput();
	}

	public void setSchema(GlobalInput cGlobalInput)
	{
		if (cGlobalInput.Operator != null && !cGlobalInput.Operator.equals(""))
		{
			this.Operator = cGlobalInput.Operator;
		}

		if (cGlobalInput.ComCode != null && !cGlobalInput.ComCode.equals(""))
		{
			this.ComCode = cGlobalInput.ComCode;
		}

		if (cGlobalInput.ManageCom != null && !cGlobalInput.ManageCom.equals(""))
		{
			this.ManageCom = cGlobalInput.ManageCom;
		}
	}
}
