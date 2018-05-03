/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.utility;

import java.util.Vector;

/**
 * <p>
 * Title: Webҵ��ϵͳ
 * </p>
 * <p>
 * Description:����Ϊ��������������һ�������е������������ж� ��Ҫ�������࣬�ڷ��������ʱ����Ҫ�ڸ��������ô�����Ϣ
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * @author YT
 * @version 1.0
 * @date: 2002-06-18
 */
public class CErrors implements Cloneable
{
	private Vector vErrors = new Vector();

	private int errorCount = 0;

	private String flag = "";

	private String content = "";

	private String result = "";

	/**
	 * ��¡CErrors���� 2005-04-15 ��������
	 * @return Object
	 * @throws CloneNotSupportedException
	 */
	public Object clone() throws CloneNotSupportedException
	{
		CErrors cloned = (CErrors) super.clone();
		// clone the mutable fields of this class
		return cloned;
	}

	public String getFlag()
	{
		return flag;
	}

	public void setFlag(String f)
	{
		flag = f;
	}

	public String getContent()
	{
		return content;
	}

	public void setContent(String c)
	{
		content = c;
	}

	public String getResult()
	{
		return result;
	}

	public void setResult(String c)
	{
		result = c;
	}

	public CErrors()
	{}

	/**
	 * �����������������һ�����󣬴��������1
	 * @param cError CError
	 */
	public void addOneError(CError cError)
	{
		this.vErrors.add(cError);
		this.errorCount++;
	}

	/**
	 * �����������������һ�����󣬴��������1
	 * @param cErrorString String
	 */
	public void addOneError(String cErrorString)
	{
		CError tError = new CError(cErrorString.trim());
		this.vErrors.add(tError);
		this.errorCount++;
	}

	/**
	 * �Ƴ����Ĵ���
	 */
	public void removeLastError()
	{
		if (errorCount > 0)
		{
			this.vErrors.removeElementAt(errorCount - 1);
			this.errorCount--;
		}
	}

	/**
	 * �Ƴ�ָ���Ĵ�����Ϣ
	 * @param pos int
	 */
	public void removeError(int pos)
	{
		if ((errorCount > 0) && (pos < errorCount))
		{
			this.vErrors.removeElementAt(pos);
			this.errorCount--;
		}
	}

	/**
	 * �����������ڵĴ�����Ϣ��գ���������Ϊ0
	 */
	public void clearErrors()
	{
		this.vErrors.clear();
		this.errorCount = 0;
	}

	/**
	 * �õ������еĴ���ĸ���
	 * @return int
	 */
	public int getErrorCount()
	{
		return this.errorCount;
	}

	/**
	 * �õ�������ָ��λ�õĴ������
	 * @param indexError int
	 * @return CError
	 */
	public CError getError(int indexError)
	{
		CError tError;
		tError = (CError) vErrors.get(indexError);

		return tError;
	}

	/**
	 * �õ�����һ������Ĵ�����Ϣ,���û�д����򷵻ؿ��ַ���""
	 * @return String
	 */
	public String getFirstError()
	{
		try
		{
			CError tError = (CError) this.vErrors.get(0);

			/*
			 * kevin 2002-10-15 ��֤������Ϣ�����һ���ַ����ǻس���������������Ϣ�����javascript�Ĵ���
			 * �滻һЩ������ַ������򣬻����javascript�Ĵ���
			 */
			String strMsg = tError.errorMessage;

			strMsg = strMsg.replace((char) (10), ' ');
			strMsg = strMsg.replace('"', ' ');
			strMsg = strMsg.replace('\'', ' ');

			return strMsg;
		}
		catch (Exception e)
		{
			return "";
		}
	}

	/**
	 * �õ����һ������Ĵ�����Ϣ,���û�д����򷵻ؿ��ַ���""
	 * @return String
	 */
	public String getLastError()
	{
		if (errorCount < 1)
		{
			return "";
		}

		try
		{
			CError tError = (CError) this.vErrors.get(errorCount - 1);

			/*
			 * kevin 2002-10-15 ��֤������Ϣ�����һ���ַ����ǻس���������������Ϣ�����javascript�Ĵ���
			 * �滻һЩ������ַ������򣬻����javascript�Ĵ���
			 */
			String strMsg = tError.errorMessage;

			strMsg = strMsg.replace((char) (10), ' ');
			strMsg = strMsg.replace('"', ' ');
			strMsg = strMsg.replace('\'', ' ');

			return strMsg;
		}
		catch (Exception e)
		{
			return "";
		}
	}

	/**
	 * �жϲ����Ĵ����Ƿ����ص���Ҫ����
	 * @return boolean
	 */
	public boolean needDealError()
	{
		try
		{
			if (this.getErrorCount() > 0)
			{
				return true;
			}
		}
		catch (Exception e)
		{
			return false;
		}

		return false;
	}

	/**
	 * �������еĴ�����Ϣ��������
	 * @param cSourceErrors CErrors
	 */
	public void copyAllErrors(CErrors cSourceErrors)
	{
		int i = 0;
		int iMax = 0;
		iMax = cSourceErrors.getErrorCount();

		for (i = 0; i < iMax; i++)
		{
			this.addOneError(cSourceErrors.getError(i));
		}
	}

	/**
	 * ��ȡ�������ؼ���
	 * @return String
	 */
	public String getErrType()
	{
		int forbitNum = 0;
		int needSelectNum = 0;
		int allowNum = 0;
		int unknowNum = 0;

		for (int i = 0; i < vErrors.size(); i++)
		{
			CError tError = (CError) this.vErrors.get(i);

			if (tError.errorType.equals(CError.TYPE_FORBID))
			{
				forbitNum++;
			}
			else if (tError.errorType.equals(CError.TYPE_NEEDSELECT))
			{
				needSelectNum++;
			}
			else if (tError.errorType.equals(CError.TYPE_ALLOW))
			{
				allowNum++;
			}
			else
			{
				unknowNum++;
			}
		}

		if (forbitNum > 0)
		{
			return CError.TYPE_FORBID;
		}
		else if (needSelectNum > 0)
		{
			return CError.TYPE_NEEDSELECT;
		}
		else if (allowNum > 0)
		{
			return CError.TYPE_ALLOW;
		}
		else if (unknowNum > 0)
		{
			return CError.TYPE_UNKNOW;
		}
		else
		{
			return CError.TYPE_NONEERR;
		}
	}

	/**
	 * ��ȡ��������,������,���ṩ������ʾ
	 * @return String
	 */
	public String getErrContent()
	{
		content = "��һ������ " + vErrors.size() + " ������\n";
		String forbitContent = "";
		String needSelectContent = "";
		String allowContent = "";
		String unknowContent = "";

		for (int i = 0; i < vErrors.size(); i++)
		{
			CError tError = (CError) this.vErrors.get(i);

			if (tError.errorType.equals(CError.TYPE_FORBID))
			{
				forbitContent = forbitContent + "  " + "��������룺" + tError.errorNo + "��" + tError.errorMessage + "\n";
			}
			else if (tError.errorType.equals(CError.TYPE_NEEDSELECT))
			{
				needSelectContent = needSelectContent + "  " + "��������룺" + tError.errorNo + "��" + tError.errorMessage
						+ "\n";
			}
			else if (tError.errorType.equals(CError.TYPE_ALLOW))
			{
				allowContent = allowContent + "  " + "��������룺" + tError.errorNo + "��" + tError.errorMessage + "\n";
			}
			else
			{
				unknowContent = unknowContent + "  " + "��������룺�������" + tError.errorMessage + "\n";
			}
		}

		if (!forbitContent.equals(""))
		{
			content = content + "���ش�������:\n" + forbitContent;
		}

		if (!needSelectContent.equals(""))
		{
			content = content + "��Ҫѡ��Ĵ�������:\n" + needSelectContent;
		}

		if (!allowContent.equals(""))
		{
			content = content + "������ֵĴ�������:\n" + allowContent;
		}

		if (!unknowContent.equals(""))
		{
			content = content + "�����������:\n" + unknowContent;
		}

		// content = content + forbitContent + needSelectContent + allowContent;
		// content = PubFun.changForJavaScript(content);
		// content = PubFun.changForHTML(content);
		return content;
	}

	/**
	 * У�����
	 * @param cerrors CErrors
	 */
	public void checkErrors(CErrors cerrors)
	{
		if (cerrors.getErrType().equals(CError.TYPE_NONE))
		{
			content = "�����ɹ�";
			flag = "Success";
		}
		else
		{
//			String ErrorContent = cerrors.getErrContent();

			if (cerrors.getErrType().equals(CError.TYPE_ALLOW))
			{
				// content = "����ɹ������ǣ�" + PubFun.changForHTML(ErrorContent);
				flag = "Success";
			}
			else
			{
				// content = "����ʧ�ܣ�ԭ���ǣ�" + PubFun.changForHTML(ErrorContent);
				flag = "Fail";
			}
		}
	}

	/**
	 * ���Ժ���
	 * @param args String[]
	 */
	public static void main(String[] args)
	{}
}
