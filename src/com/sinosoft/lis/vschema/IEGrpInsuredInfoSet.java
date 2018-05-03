/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.vschema;

import com.sinosoft.lis.schema.IEGrpInsuredInfoSchema;
import com.sinosoft.utility.*;

/**
 * <p>ClassName: IEGrpInsuredInfoSet </p>
 * <p>Description: IEGrpInsuredInfoSchemaSet���ļ� </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: PhysicalDataModel_2
 * @CreateDate��2017-04-21
 */
public class IEGrpInsuredInfoSet extends SchemaSet
{
	// @Method
	public boolean add(IEGrpInsuredInfoSchema aSchema)
	{
		return super.add(aSchema);
	}

	public boolean add(IEGrpInsuredInfoSet aSet)
	{
		return super.add(aSet);
	}

	public boolean remove(IEGrpInsuredInfoSchema aSchema)
	{
		return super.remove(aSchema);
	}

	public IEGrpInsuredInfoSchema get(int index)
	{
		IEGrpInsuredInfoSchema tSchema = (IEGrpInsuredInfoSchema)super.getObj(index);
		return tSchema;
	}

	public boolean set(int index, IEGrpInsuredInfoSchema aSchema)
	{
		return super.set(index,aSchema);
	}

	public boolean set(IEGrpInsuredInfoSet aSet)
	{
		return super.set(aSet);
	}

	/**
	* ���ݴ������ XML ��ʽ�����˳��μ�<A href ={@docRoot}/dataStructure/tb.html#PrpIEGrpInsuredInfo����/A>���ֶ�
	* @return: String ���ش�����ַ���
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer("");
		int n = this.size();
		for (int i = 1; i <= n; i++)
		{
			IEGrpInsuredInfoSchema aSchema = this.get(i);
			strReturn.append(aSchema.encode());
			if( i != n ) strReturn.append(SysConst.RECORDSPLITER);
		}

		return strReturn.toString();
	}

	/**
	* ���ݽ��
	* @param: str String ������ַ���
	* @return: boolean
	**/
	public boolean decode( String str )
	{
		int nBeginPos = 0;
		int nEndPos = str.indexOf('^');
		this.clear();

		while( nEndPos != -1 )
		{
			IEGrpInsuredInfoSchema aSchema = new IEGrpInsuredInfoSchema();
			if(aSchema.decode(str.substring(nBeginPos, nEndPos)))
			{
			this.add(aSchema);
			nBeginPos = nEndPos + 1;
			nEndPos = str.indexOf('^', nEndPos + 1);
			}
			else
			{
				// @@������
				this.mErrors.copyAllErrors( aSchema.mErrors );
				return false;
			}
		}
		IEGrpInsuredInfoSchema tSchema = new IEGrpInsuredInfoSchema();
		if(tSchema.decode(str.substring(nBeginPos)))
		{
		this.add(tSchema);
		return true;
		}
		else
		{
			// @@������
			this.mErrors.copyAllErrors( tSchema.mErrors );
			return false;
		}
	}

}
