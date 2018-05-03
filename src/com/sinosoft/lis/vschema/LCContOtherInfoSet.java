/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.vschema;

import com.sinosoft.lis.schema.LCContOtherInfoSchema;
import com.sinosoft.utility.*;

/**
 * <p>ClassName: LCCONTOTHERINFOSet </p>
 * <p>Description: LCCONTOTHERINFOSchemaSet���ļ� </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: PhysicalDataModel_1
 * @CreateDate��2018-05-02
 */
public class LCContOtherInfoSet extends SchemaSet
{
	// @Method
	public boolean add(LCContOtherInfoSchema aSchema)
	{
		return super.add(aSchema);
	}

	public boolean add(LCContOtherInfoSet aSet)
	{
		return super.add(aSet);
	}

	public boolean remove(LCContOtherInfoSchema aSchema)
	{
		return super.remove(aSchema);
	}

	public LCContOtherInfoSchema get(int index)
	{
		LCContOtherInfoSchema tSchema = (LCContOtherInfoSchema)super.getObj(index);
		return tSchema;
	}

	public boolean set(int index, LCContOtherInfoSchema aSchema)
	{
		return super.set(index,aSchema);
	}

	public boolean set(LCContOtherInfoSet aSet)
	{
		return super.set(aSet);
	}

	/**
	* ���ݴ������ XML ��ʽ�����˳��μ�<A href ={@docRoot}/dataStructure/tb.html#PrpLCCONTOTHERINFO����/A>���ֶ�
	* @return: String ���ش�����ַ���
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer("");
		int n = this.size();
		for (int i = 1; i <= n; i++)
		{
			LCContOtherInfoSchema aSchema = this.get(i);
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
			LCContOtherInfoSchema aSchema = new LCContOtherInfoSchema();
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
		LCContOtherInfoSchema tSchema = new LCContOtherInfoSchema();
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
