package com.sinosoft.utility;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author lh
 * @version 1.0
 */
import java.util.Vector;

public class SSRS
{
	private Vector RData = new Vector();

	public int MaxCol = 0;

	public int MaxRow = 0;

	public int MaxNumber = 0;

	public CErrors mErrors = new CErrors(); // ������Ϣ

	// falseû�д�true�д�
	public boolean ErrorFlag = false;

	public SSRS()
	{}

	public SSRS(int n)
	{
		MaxCol = n;
	}

	public void SetText(String strValue)
	{
		RData.addElement(strValue);
		MaxNumber = RData.size();
		if ((MaxNumber % MaxCol) == 0)
		{
			MaxRow = MaxNumber / MaxCol;
		}
		else
		{
			MaxRow = MaxNumber / MaxCol + 1;
		}

	}

	public String GetText(int cRow, int cCol)
	{
		String Result = "";
		int Number = (cRow - 1) * MaxCol + cCol - 1;
		if (Number <= MaxNumber)
		{
			Result = (String) RData.get(Number);
		}
		else
		{
			// @@������
			CError tError = new CError();
			tError.moduleName = "SSRS";
			tError.functionName = "GetText";
			tError.errorMessage = "ָ����λ���ڽ������û������";
			this.mErrors.addOneError(tError);
			this.ErrorFlag = true;
		}
		return Result;
	}

	public int getMaxCol()
	{
		return MaxCol;
	}

	public int getMaxRow()
	{
		return MaxRow;
	}

	public int getMaxNumber()
	{
		return MaxNumber;
	}

	public void Clear()
	{
		RData.clear();
		MaxCol = 0;
		MaxNumber = 0;
	}

	public String encode()
	{
		String strReturn = "";
		if (MaxNumber != 0)
		{
			strReturn = "0" + SysConst.PACKAGESPILTER + String.valueOf(MaxRow) + SysConst.RECORDSPLITER;
			for (int i = 1; i <= MaxRow; i++)
			{
				for (int j = 1; j <= MaxCol; j++)
				{
					if (j != MaxCol)
					{
						strReturn = strReturn + GetText(i, j) + SysConst.PACKAGESPILTER;
					}
					else
					{
						strReturn = strReturn + GetText(i, j);
					}
				}
				if (i != MaxRow)
				{
					strReturn = strReturn + SysConst.RECORDSPLITER;
				}
			}
		}
		return strReturn;
	}

	public static void main(String[] args)
	{}
}