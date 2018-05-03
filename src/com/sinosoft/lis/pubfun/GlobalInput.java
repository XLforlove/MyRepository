package com.sinosoft.lis.pubfun;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 全局变量区
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
	/** 当前操作员 */
	public String Operator;

	/** 当前管理机构 */
	public String ManageCom;

	/** 当前登陆机构 */
	public String ComCode;

	// /** 当前险种 */
	// public String RiskCode;
	// /** 当前险种版本 */
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
