/**
 * Copyright (c) 2018 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.schema;

import java.sql.*;
import java.util.Date;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.utility.*;
import com.sinosoft.lis.db.LCContOtherInfoDB;

/**
 * <p>Title: Webҵ��ϵͳ</p>
 * <p>ClassName: LCCONTOTHERINFOSchema </p>
 * <p>Description: DB�� Schema ���ļ� </p>
 * @Database: PhysicalDataModel_1
 * @CreateDate��2018-05-02
 */
public class LCContOtherInfoSchema implements Schema, Cloneable
{
	// @Field
	/** Prtno */
	private String prtno;
	/** Referrerrphone */
	private String referrerrphone;
	/** Bak1 */
	private String bak1;
	/** Bak2 */
	private String bak2;
	/** Bak3 */
	private String bak3;
	/** Bak4 */
	private String bak4;
	/** Bak5 */
	private String bak5;
	/** Bak6 */
	private String bak6;
	/** Bak7 */
	private String bak7;
	/** Bak8 */
	private String bak8;
	/** Bak9 */
	private String bak9;
	/** Bak10 */
	private String bak10;
	/** Bak11 */
	private Date bak11;
	/** Bak12 */
	private Date bak12;
	/** Makedate */
	private Date makedate;
	/** Maketime */
	private String maketime;
	/** Modifydate */
	private Date modifydate;
	/** Modifytime */
	private String modifytime;
	/** Flag1 */
	private String flag1;
	/** Flag2 */
	private String flag2;
	/** Flag3 */
	private String flag3;
	/** Flag4 */
	private String flag4;

	public static final int FIELDNUM = 22;	// ���ݿ����ֶθ���

	private static String[] PK;				// ����

	private FDate fDate = new FDate();		// ��������

	public CErrors mErrors;			// ������Ϣ

	// @Constructor
	public LCContOtherInfoSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[0];

		PK = pk;
	}

            /**
             * Schema��¡
             * @return Object
             * @throws CloneNotSupportedException
             */
            public Object clone()
                    throws CloneNotSupportedException
            {
                LCContOtherInfoSchema cloned = (LCContOtherInfoSchema)super.clone();
                cloned.fDate = (FDate) fDate.clone();
                cloned.mErrors = (CErrors) mErrors.clone();
                return cloned;
            }

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getprtno()
	{
		return prtno;
	}
	public void setprtno(String aprtno)
	{
		prtno = StrTool.cTrim(aprtno);
	}
	public String getreferrerrphone()
	{
		return referrerrphone;
	}
	public void setreferrerrphone(String areferrerrphone)
	{
		referrerrphone = StrTool.cTrim(areferrerrphone);
	}
	public String getbak1()
	{
		return bak1;
	}
	public void setbak1(String abak1)
	{
		bak1 = StrTool.cTrim(abak1);
	}
	public String getbak2()
	{
		return bak2;
	}
	public void setbak2(String abak2)
	{
		bak2 = StrTool.cTrim(abak2);
	}
	public String getbak3()
	{
		return bak3;
	}
	public void setbak3(String abak3)
	{
		bak3 = StrTool.cTrim(abak3);
	}
	public String getbak4()
	{
		return bak4;
	}
	public void setbak4(String abak4)
	{
		bak4 = StrTool.cTrim(abak4);
	}
	public String getbak5()
	{
		return bak5;
	}
	public void setbak5(String abak5)
	{
		bak5 = StrTool.cTrim(abak5);
	}
	public String getbak6()
	{
		return bak6;
	}
	public void setbak6(String abak6)
	{
		bak6 = StrTool.cTrim(abak6);
	}
	public String getbak7()
	{
		return bak7;
	}
	public void setbak7(String abak7)
	{
		bak7 = StrTool.cTrim(abak7);
	}
	public String getbak8()
	{
		return bak8;
	}
	public void setbak8(String abak8)
	{
		bak8 = StrTool.cTrim(abak8);
	}
	public String getbak9()
	{
		return bak9;
	}
	public void setbak9(String abak9)
	{
		bak9 = StrTool.cTrim(abak9);
	}
	public String getbak10()
	{
		return bak10;
	}
	public void setbak10(String abak10)
	{
		bak10 = StrTool.cTrim(abak10);
	}
	public String getbak11()
	{
		if( bak11 != null )
			return fDate.getString(bak11);
		else
			return null;
	}
	public void setbak11(Date abak11)
	{
		bak11 = abak11;
	}
	public void setbak11(String abak11)
	{
		if (abak11 != null && !abak11.equals("") )
		{
			bak11 = fDate.getDate( abak11 );
		}
		else
			bak11 = null;
	}

	public String getbak12()
	{
		if( bak12 != null )
			return fDate.getString(bak12);
		else
			return null;
	}
	public void setbak12(Date abak12)
	{
		bak12 = abak12;
	}
	public void setbak12(String abak12)
	{
		if (abak12 != null && !abak12.equals("") )
		{
			bak12 = fDate.getDate( abak12 );
		}
		else
			bak12 = null;
	}

	public String getmakedate()
	{
		if( makedate != null )
			return fDate.getString(makedate);
		else
			return null;
	}
	public void setmakedate(Date amakedate)
	{
		makedate = amakedate;
	}
	public void setmakedate(String amakedate)
	{
		if (amakedate != null && !amakedate.equals("") )
		{
			makedate = fDate.getDate( amakedate );
		}
		else
			makedate = null;
	}

	public String getmaketime()
	{
		return maketime;
	}
	public void setmaketime(String amaketime)
	{
		maketime = StrTool.cTrim(amaketime);
	}
	public String getmodifydate()
	{
		if( modifydate != null )
			return fDate.getString(modifydate);
		else
			return null;
	}
	public void setmodifydate(Date amodifydate)
	{
		modifydate = amodifydate;
	}
	public void setmodifydate(String amodifydate)
	{
		if (amodifydate != null && !amodifydate.equals("") )
		{
			modifydate = fDate.getDate( amodifydate );
		}
		else
			modifydate = null;
	}

	public String getmodifytime()
	{
		return modifytime;
	}
	public void setmodifytime(String amodifytime)
	{
		modifytime = StrTool.cTrim(amodifytime);
	}
	public String getflag1()
	{
		return flag1;
	}
	public void setflag1(String aflag1)
	{
		flag1 = StrTool.cTrim(aflag1);
	}
	public String getflag2()
	{
		return flag2;
	}
	public void setflag2(String aflag2)
	{
		flag2 = StrTool.cTrim(aflag2);
	}
	public String getflag3()
	{
		return flag3;
	}
	public void setflag3(String aflag3)
	{
		flag3 = StrTool.cTrim(aflag3);
	}
	public String getflag4()
	{
		return flag4;
	}
	public void setflag4(String aflag4)
	{
		flag4 = StrTool.cTrim(aflag4);
	}

	/**
	* ʹ������һ�� LCCONTOTHERINFOSchema ����� Schema ��ֵ
	* @param: aLCCONTOTHERINFOSchema LCCONTOTHERINFOSchema
	**/
	public void setSchema(LCContOtherInfoSchema aLCCONTOTHERINFOSchema)
	{
		this.prtno = aLCCONTOTHERINFOSchema.getprtno();
		this.referrerrphone = aLCCONTOTHERINFOSchema.getreferrerrphone();
		this.bak1 = aLCCONTOTHERINFOSchema.getbak1();
		this.bak2 = aLCCONTOTHERINFOSchema.getbak2();
		this.bak3 = aLCCONTOTHERINFOSchema.getbak3();
		this.bak4 = aLCCONTOTHERINFOSchema.getbak4();
		this.bak5 = aLCCONTOTHERINFOSchema.getbak5();
		this.bak6 = aLCCONTOTHERINFOSchema.getbak6();
		this.bak7 = aLCCONTOTHERINFOSchema.getbak7();
		this.bak8 = aLCCONTOTHERINFOSchema.getbak8();
		this.bak9 = aLCCONTOTHERINFOSchema.getbak9();
		this.bak10 = aLCCONTOTHERINFOSchema.getbak10();
		this.bak11 = fDate.getDate( aLCCONTOTHERINFOSchema.getbak11());
		this.bak12 = fDate.getDate( aLCCONTOTHERINFOSchema.getbak12());
		this.makedate = fDate.getDate( aLCCONTOTHERINFOSchema.getmakedate());
		this.maketime = aLCCONTOTHERINFOSchema.getmaketime();
		this.modifydate = fDate.getDate( aLCCONTOTHERINFOSchema.getmodifydate());
		this.modifytime = aLCCONTOTHERINFOSchema.getmodifytime();
		this.flag1 = aLCCONTOTHERINFOSchema.getflag1();
		this.flag2 = aLCCONTOTHERINFOSchema.getflag2();
		this.flag3 = aLCCONTOTHERINFOSchema.getflag3();
		this.flag4 = aLCCONTOTHERINFOSchema.getflag4();
	}

	/**
	* ʹ�� ResultSet �еĵ� i �и� Schema ��ֵ
	* @param: rs ResultSet
	* @param: i int
	* @return: boolean
	**/
	public boolean setSchema(ResultSet rs,int i)
	{
		try
		{
			//rs.absolute(i);		// �ǹ����α�
			if( rs.getString(1) == null )
				this.prtno = null;
			else
				this.prtno = rs.getString(1).trim();

			if( rs.getString(2) == null )
				this.referrerrphone = null;
			else
				this.referrerrphone = rs.getString(2).trim();

			if( rs.getString(3) == null )
				this.bak1 = null;
			else
				this.bak1 = rs.getString(3).trim();

			if( rs.getString(4) == null )
				this.bak2 = null;
			else
				this.bak2 = rs.getString(4).trim();

			if( rs.getString(5) == null )
				this.bak3 = null;
			else
				this.bak3 = rs.getString(5).trim();

			if( rs.getString(6) == null )
				this.bak4 = null;
			else
				this.bak4 = rs.getString(6).trim();

			if( rs.getString(7) == null )
				this.bak5 = null;
			else
				this.bak5 = rs.getString(7).trim();

			if( rs.getString(8) == null )
				this.bak6 = null;
			else
				this.bak6 = rs.getString(8).trim();

			if( rs.getString(9) == null )
				this.bak7 = null;
			else
				this.bak7 = rs.getString(9).trim();

			if( rs.getString(10) == null )
				this.bak8 = null;
			else
				this.bak8 = rs.getString(10).trim();

			if( rs.getString(11) == null )
				this.bak9 = null;
			else
				this.bak9 = rs.getString(11).trim();

			if( rs.getString(12) == null )
				this.bak10 = null;
			else
				this.bak10 = rs.getString(12).trim();

			this.bak11 = rs.getDate(13);
			this.bak12 = rs.getDate(14);
			this.makedate = rs.getDate(15);
			if( rs.getString(16) == null )
				this.maketime = null;
			else
				this.maketime = rs.getString(16).trim();

			this.modifydate = rs.getDate(17);
			if( rs.getString(18) == null )
				this.modifytime = null;
			else
				this.modifytime = rs.getString(18).trim();

			if( rs.getString(19) == null )
				this.flag1 = null;
			else
				this.flag1 = rs.getString(19).trim();

			if( rs.getString(20) == null )
				this.flag2 = null;
			else
				this.flag2 = rs.getString(20).trim();

			if( rs.getString(21) == null )
				this.flag3 = null;
			else
				this.flag3 = rs.getString(21).trim();

			if( rs.getString(22) == null )
				this.flag4 = null;
			else
				this.flag4 = rs.getString(22).trim();

		}
		catch(SQLException sqle)
		{
			System.out.println("���ݿ��е�LCCONTOTHERINFO���ֶθ�����Schema�е��ֶθ�����һ�£�����ִ��db.executeQuery��ѯʱû��ʹ��select * from tables");
			// @@������
			CError tError = new CError();
			tError.moduleName = "LCCONTOTHERINFOSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LCContOtherInfoSchema getSchema()
	{
		LCContOtherInfoSchema aLCCONTOTHERINFOSchema = new LCContOtherInfoSchema();
		aLCCONTOTHERINFOSchema.setSchema(this);
		return aLCCONTOTHERINFOSchema;
	}

	public LCContOtherInfoDB getDB()
	{
		LCContOtherInfoDB aDBOper = new LCContOtherInfoDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* ���ݴ������ XML ��ʽ���
	* @return: String ���ش�����ַ���
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
strReturn.append(StrTool.cTrim(prtno)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(referrerrphone)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(bak1)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(bak2)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(bak3)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(bak4)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(bak5)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(bak6)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(bak7)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(bak8)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(bak9)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(bak10)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(fDate.getString( bak11 ))); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(fDate.getString( bak12 ))); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(fDate.getString( makedate ))); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(maketime)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(fDate.getString( modifydate ))); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(modifytime)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(flag1)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(flag2)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(flag3)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(flag4));
		return strReturn.toString();
	}

	/**
	* ���ݽ��
	* @param: strMessage String ����һ����¼���ݵ��ַ���
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			// ������Ҫ�Դ����strMessage����һ��ת��
        	strMessage = StrTool.GBKToUnicode(strMessage);
			prtno = StrTool.getStr(strMessage, 1, SysConst.PACKAGESPILTER );
			referrerrphone = StrTool.getStr(strMessage, 2, SysConst.PACKAGESPILTER );
			bak1 = StrTool.getStr(strMessage, 3, SysConst.PACKAGESPILTER );
			bak2 = StrTool.getStr(strMessage, 4, SysConst.PACKAGESPILTER );
			bak3 = StrTool.getStr(strMessage, 5, SysConst.PACKAGESPILTER );
			bak4 = StrTool.getStr(strMessage, 6, SysConst.PACKAGESPILTER );
			bak5 = StrTool.getStr(strMessage, 7, SysConst.PACKAGESPILTER );
			bak6 = StrTool.getStr(strMessage, 8, SysConst.PACKAGESPILTER );
			bak7 = StrTool.getStr(strMessage, 9, SysConst.PACKAGESPILTER );
			bak8 = StrTool.getStr(strMessage, 10, SysConst.PACKAGESPILTER );
			bak9 = StrTool.getStr(strMessage, 11, SysConst.PACKAGESPILTER );
			bak10 = StrTool.getStr(strMessage, 12, SysConst.PACKAGESPILTER );
			bak11 = fDate.getDate(StrTool.getStr(strMessage, 13,SysConst.PACKAGESPILTER));
			bak12 = fDate.getDate(StrTool.getStr(strMessage, 14,SysConst.PACKAGESPILTER));
			makedate = fDate.getDate(StrTool.getStr(strMessage, 15,SysConst.PACKAGESPILTER));
			maketime = StrTool.getStr(strMessage, 16, SysConst.PACKAGESPILTER );
			modifydate = fDate.getDate(StrTool.getStr(strMessage, 17,SysConst.PACKAGESPILTER));
			modifytime = StrTool.getStr(strMessage, 18, SysConst.PACKAGESPILTER );
			flag1 = StrTool.getStr(strMessage, 19, SysConst.PACKAGESPILTER );
			flag2 = StrTool.getStr(strMessage, 20, SysConst.PACKAGESPILTER );
			flag3 = StrTool.getStr(strMessage, 21, SysConst.PACKAGESPILTER );
			flag4 = StrTool.getStr(strMessage, 22, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@������
			CError tError = new CError();
			tError.moduleName = "LCCONTOTHERINFOSchema";
			tError.functionName = "decode";
			tError.errorMessage = ex.toString();
			this.mErrors .addOneError(tError);

			return false;
		}
		return true;
	}

	/**
	* ȡ�ö�Ӧ���������String��ʽ���ֶ�ֵ
	* @param: FCode String ϣ��ȡ�õ��ֶ���
	* @return: String
	* ���û�ж�Ӧ���ֶΣ�����""
	* ����ֶ�ֵΪ�գ�����"null"
	**/
	public String getV(String FCode)
	{
		String strReturn = "";
		if (FCode.equalsIgnoreCase("prtno"))
		{
			strReturn = StrTool.cTrim(prtno);
		}
		else if (FCode.equalsIgnoreCase("referrerrphone"))
		{
			strReturn = StrTool.cTrim(referrerrphone);
		}
		else if (FCode.equalsIgnoreCase("bak1"))
		{
			strReturn = StrTool.cTrim(bak1);
		}
		else if (FCode.equalsIgnoreCase("bak2"))
		{
			strReturn = StrTool.cTrim(bak2);
		}
		else if (FCode.equalsIgnoreCase("bak3"))
		{
			strReturn = StrTool.cTrim(bak3);
		}
		else if (FCode.equalsIgnoreCase("bak4"))
		{
			strReturn = StrTool.cTrim(bak4);
		}
		else if (FCode.equalsIgnoreCase("bak5"))
		{
			strReturn = StrTool.cTrim(bak5);
		}
		else if (FCode.equalsIgnoreCase("bak6"))
		{
			strReturn = StrTool.cTrim(bak6);
		}
		else if (FCode.equalsIgnoreCase("bak7"))
		{
			strReturn = StrTool.cTrim(bak7);
		}
		else if (FCode.equalsIgnoreCase("bak8"))
		{
			strReturn = StrTool.cTrim(bak8);
		}
		else if (FCode.equalsIgnoreCase("bak9"))
		{
			strReturn = StrTool.cTrim(bak9);
		}
		else if (FCode.equalsIgnoreCase("bak10"))
		{
			strReturn = StrTool.cTrim(bak10);
		}
		else if (FCode.equalsIgnoreCase("bak11"))
		{
			strReturn = StrTool.cTrim(this.getbak11());
		}
		else if (FCode.equalsIgnoreCase("bak12"))
		{
			strReturn = StrTool.cTrim(this.getbak12());
		}
		else if (FCode.equalsIgnoreCase("makedate"))
		{
			strReturn = StrTool.cTrim(this.getmakedate());
		}
		else if (FCode.equalsIgnoreCase("maketime"))
		{
			strReturn = StrTool.cTrim(maketime);
		}
		else if (FCode.equalsIgnoreCase("modifydate"))
		{
			strReturn = StrTool.cTrim(this.getmodifydate());
		}
		else if (FCode.equalsIgnoreCase("modifytime"))
		{
			strReturn = StrTool.cTrim(modifytime);
		}
		else if (FCode.equalsIgnoreCase("flag1"))
		{
			strReturn = StrTool.cTrim(flag1);
		}
		else if (FCode.equalsIgnoreCase("flag2"))
		{
			strReturn = StrTool.cTrim(flag2);
		}
		else if (FCode.equalsIgnoreCase("flag3"))
		{
			strReturn = StrTool.cTrim(flag3);
		}
		else if (FCode.equalsIgnoreCase("flag4"))
		{
			strReturn = StrTool.cTrim(flag4);
		}
		if (strReturn.equals(""))
		{
			strReturn = "null";
		}

		return strReturn;
	}


	/**
	* ȡ��Schema��ָ������ֵ����Ӧ���ֶ�ֵ
	* @param: nFieldIndex int ָ�����ֶ�����ֵ
	* @return: String
	* ���û�ж�Ӧ���ֶΣ�����""
	* ����ֶ�ֵΪ�գ�����"null"
	**/
	public String getV(int nFieldIndex)
	{
		String strFieldValue = "";
		switch(nFieldIndex) {
			case 0:
				strFieldValue = StrTool.cTrim(prtno);
				break;
			case 1:
				strFieldValue = StrTool.cTrim(referrerrphone);
				break;
			case 2:
				strFieldValue = StrTool.cTrim(bak1);
				break;
			case 3:
				strFieldValue = StrTool.cTrim(bak2);
				break;
			case 4:
				strFieldValue = StrTool.cTrim(bak3);
				break;
			case 5:
				strFieldValue = StrTool.cTrim(bak4);
				break;
			case 6:
				strFieldValue = StrTool.cTrim(bak5);
				break;
			case 7:
				strFieldValue = StrTool.cTrim(bak6);
				break;
			case 8:
				strFieldValue = StrTool.cTrim(bak7);
				break;
			case 9:
				strFieldValue = StrTool.cTrim(bak8);
				break;
			case 10:
				strFieldValue = StrTool.cTrim(bak9);
				break;
			case 11:
				strFieldValue = StrTool.cTrim(bak10);
				break;
			case 12:
				strFieldValue = StrTool.cTrim(this.getbak11());
				break;
			case 13:
				strFieldValue = StrTool.cTrim(this.getbak12());
				break;
			case 14:
				strFieldValue = StrTool.cTrim(this.getmakedate());
				break;
			case 15:
				strFieldValue = StrTool.cTrim(maketime);
				break;
			case 16:
				strFieldValue = StrTool.cTrim(this.getmodifydate());
				break;
			case 17:
				strFieldValue = StrTool.cTrim(modifytime);
				break;
			case 18:
				strFieldValue = StrTool.cTrim(flag1);
				break;
			case 19:
				strFieldValue = StrTool.cTrim(flag2);
				break;
			case 20:
				strFieldValue = StrTool.cTrim(flag3);
				break;
			case 21:
				strFieldValue = StrTool.cTrim(flag4);
				break;
			default:
				strFieldValue = "";
		};
		if( strFieldValue.equals("") ) {
			strFieldValue = "null";
		}
		return strFieldValue;
	}

	/**
	* ���ö�Ӧ���������String��ʽ���ֶ�ֵ
	* @param: FCode String ��Ҫ��ֵ�Ķ���
	* @param: FValue String Ҫ����ֵ
	* @return: boolean
	**/
	public boolean setV(String FCode ,String FValue)
	{
		if( StrTool.cTrim( FCode ).equals( "" ))
			return false;

		if (FCode.equalsIgnoreCase("prtno"))
		{
			if( FValue != null)
			{
				prtno = FValue.trim();
			}
			else
				prtno = null;
		}
		else if (FCode.equalsIgnoreCase("referrerrphone"))
		{
			if( FValue != null)
			{
				referrerrphone = FValue.trim();
			}
			else
				referrerrphone = null;
		}
		else if (FCode.equalsIgnoreCase("bak1"))
		{
			if( FValue != null)
			{
				bak1 = FValue.trim();
			}
			else
				bak1 = null;
		}
		else if (FCode.equalsIgnoreCase("bak2"))
		{
			if( FValue != null)
			{
				bak2 = FValue.trim();
			}
			else
				bak2 = null;
		}
		else if (FCode.equalsIgnoreCase("bak3"))
		{
			if( FValue != null)
			{
				bak3 = FValue.trim();
			}
			else
				bak3 = null;
		}
		else if (FCode.equalsIgnoreCase("bak4"))
		{
			if( FValue != null)
			{
				bak4 = FValue.trim();
			}
			else
				bak4 = null;
		}
		else if (FCode.equalsIgnoreCase("bak5"))
		{
			if( FValue != null)
			{
				bak5 = FValue.trim();
			}
			else
				bak5 = null;
		}
		else if (FCode.equalsIgnoreCase("bak6"))
		{
			if( FValue != null)
			{
				bak6 = FValue.trim();
			}
			else
				bak6 = null;
		}
		else if (FCode.equalsIgnoreCase("bak7"))
		{
			if( FValue != null)
			{
				bak7 = FValue.trim();
			}
			else
				bak7 = null;
		}
		else if (FCode.equalsIgnoreCase("bak8"))
		{
			if( FValue != null)
			{
				bak8 = FValue.trim();
			}
			else
				bak8 = null;
		}
		else if (FCode.equalsIgnoreCase("bak9"))
		{
			if( FValue != null)
			{
				bak9 = FValue.trim();
			}
			else
				bak9 = null;
		}
		else if (FCode.equalsIgnoreCase("bak10"))
		{
			if( FValue != null)
			{
				bak10 = FValue.trim();
			}
			else
				bak10 = null;
		}
		else if (FCode.equalsIgnoreCase("bak11"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				bak11 = fDate.getDate( FValue );
			}
			else
				bak11 = null;
		}
		else if (FCode.equalsIgnoreCase("bak12"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				bak12 = fDate.getDate( FValue );
			}
			else
				bak12 = null;
		}
		else if (FCode.equalsIgnoreCase("makedate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				makedate = fDate.getDate( FValue );
			}
			else
				makedate = null;
		}
		else if (FCode.equalsIgnoreCase("maketime"))
		{
			if( FValue != null)
			{
				maketime = FValue.trim();
			}
			else
				maketime = null;
		}
		else if (FCode.equalsIgnoreCase("modifydate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				modifydate = fDate.getDate( FValue );
			}
			else
				modifydate = null;
		}
		else if (FCode.equalsIgnoreCase("modifytime"))
		{
			if( FValue != null)
			{
				modifytime = FValue.trim();
			}
			else
				modifytime = null;
		}
		else if (FCode.equalsIgnoreCase("flag1"))
		{
			if( FValue != null)
			{
				flag1 = FValue.trim();
			}
			else
				flag1 = null;
		}
		else if (FCode.equalsIgnoreCase("flag2"))
		{
			if( FValue != null)
			{
				flag2 = FValue.trim();
			}
			else
				flag2 = null;
		}
		else if (FCode.equalsIgnoreCase("flag3"))
		{
			if( FValue != null)
			{
				flag3 = FValue.trim();
			}
			else
				flag3 = null;
		}
		else if (FCode.equalsIgnoreCase("flag4"))
		{
			if( FValue != null)
			{
				flag4 = FValue.trim();
			}
			else
				flag4 = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LCContOtherInfoSchema other = (LCContOtherInfoSchema)otherObject;
		return
			prtno.equals(other.getprtno())
			&& referrerrphone.equals(other.getreferrerrphone())
			&& bak1.equals(other.getbak1())
			&& bak2.equals(other.getbak2())
			&& bak3.equals(other.getbak3())
			&& bak4.equals(other.getbak4())
			&& bak5.equals(other.getbak5())
			&& bak6.equals(other.getbak6())
			&& bak7.equals(other.getbak7())
			&& bak8.equals(other.getbak8())
			&& bak9.equals(other.getbak9())
			&& bak10.equals(other.getbak10())
			&& fDate.getString(bak11).equals(other.getbak11())
			&& fDate.getString(bak12).equals(other.getbak12())
			&& fDate.getString(makedate).equals(other.getmakedate())
			&& maketime.equals(other.getmaketime())
			&& fDate.getString(modifydate).equals(other.getmodifydate())
			&& modifytime.equals(other.getmodifytime())
			&& flag1.equals(other.getflag1())
			&& flag2.equals(other.getflag2())
			&& flag3.equals(other.getflag3())
			&& flag4.equals(other.getflag4());
	}

	/**
	* ȡ��Schemaӵ���ֶε�����
       * @return: int
	**/
	public int getFieldCount()
	{
 		return FIELDNUM;
	}

	/**
	* ȡ��Schema��ָ���ֶ�������Ӧ������ֵ
	* ���û�ж�Ӧ���ֶΣ�����-1
       * @param: strFieldName String
       * @return: int
	**/
	public int getFieldIndex(String strFieldName)
	{
		if( strFieldName.equals("prtno") ) {
			return 0;
		}
		else if( strFieldName.equals("referrerrphone") ) {
			return 1;
		}
		else if( strFieldName.equals("bak1") ) {
			return 2;
		}
		else if( strFieldName.equals("bak2") ) {
			return 3;
		}
		else if( strFieldName.equals("bak3") ) {
			return 4;
		}
		else if( strFieldName.equals("bak4") ) {
			return 5;
		}
		else if( strFieldName.equals("bak5") ) {
			return 6;
		}
		else if( strFieldName.equals("bak6") ) {
			return 7;
		}
		else if( strFieldName.equals("bak7") ) {
			return 8;
		}
		else if( strFieldName.equals("bak8") ) {
			return 9;
		}
		else if( strFieldName.equals("bak9") ) {
			return 10;
		}
		else if( strFieldName.equals("bak10") ) {
			return 11;
		}
		else if( strFieldName.equals("bak11") ) {
			return 12;
		}
		else if( strFieldName.equals("bak12") ) {
			return 13;
		}
		else if( strFieldName.equals("makedate") ) {
			return 14;
		}
		else if( strFieldName.equals("maketime") ) {
			return 15;
		}
		else if( strFieldName.equals("modifydate") ) {
			return 16;
		}
		else if( strFieldName.equals("modifytime") ) {
			return 17;
		}
		else if( strFieldName.equals("flag1") ) {
			return 18;
		}
		else if( strFieldName.equals("flag2") ) {
			return 19;
		}
		else if( strFieldName.equals("flag3") ) {
			return 20;
		}
		else if( strFieldName.equals("flag4") ) {
			return 21;
		}
		return -1;
	}

	/**
	* ȡ��Schema��ָ������ֵ����Ӧ���ֶ���
	* ���û�ж�Ӧ���ֶΣ�����""
       * @param: nFieldIndex int
       * @return: String
	**/
	public String getFieldName(int nFieldIndex)
	{
		String strFieldName = "";
		switch(nFieldIndex) {
			case 0:
				strFieldName = "prtno";
				break;
			case 1:
				strFieldName = "referrerrphone";
				break;
			case 2:
				strFieldName = "bak1";
				break;
			case 3:
				strFieldName = "bak2";
				break;
			case 4:
				strFieldName = "bak3";
				break;
			case 5:
				strFieldName = "bak4";
				break;
			case 6:
				strFieldName = "bak5";
				break;
			case 7:
				strFieldName = "bak6";
				break;
			case 8:
				strFieldName = "bak7";
				break;
			case 9:
				strFieldName = "bak8";
				break;
			case 10:
				strFieldName = "bak9";
				break;
			case 11:
				strFieldName = "bak10";
				break;
			case 12:
				strFieldName = "bak11";
				break;
			case 13:
				strFieldName = "bak12";
				break;
			case 14:
				strFieldName = "makedate";
				break;
			case 15:
				strFieldName = "maketime";
				break;
			case 16:
				strFieldName = "modifydate";
				break;
			case 17:
				strFieldName = "modifytime";
				break;
			case 18:
				strFieldName = "flag1";
				break;
			case 19:
				strFieldName = "flag2";
				break;
			case 20:
				strFieldName = "flag3";
				break;
			case 21:
				strFieldName = "flag4";
				break;
			default:
				strFieldName = "";
		};
		return strFieldName;
	}

	/**
	* ȡ��Schema��ָ���ֶ�������Ӧ���ֶ�����
	* ���û�ж�Ӧ���ֶΣ�����Schema.TYPE_NOFOUND
       * @param: strFieldName String
       * @return: int
	**/
	public int getFieldType(String strFieldName)
	{
		if( strFieldName.equals("prtno") ) {
			return Schema.TYPE_STRING;
		}
		else if( strFieldName.equals("referrerrphone") ) {
			return Schema.TYPE_STRING;
		}
		else if( strFieldName.equals("bak1") ) {
			return Schema.TYPE_STRING;
		}
		else if( strFieldName.equals("bak2") ) {
			return Schema.TYPE_STRING;
		}
		else if( strFieldName.equals("bak3") ) {
			return Schema.TYPE_STRING;
		}
		else if( strFieldName.equals("bak4") ) {
			return Schema.TYPE_STRING;
		}
		else if( strFieldName.equals("bak5") ) {
			return Schema.TYPE_STRING;
		}
		else if( strFieldName.equals("bak6") ) {
			return Schema.TYPE_STRING;
		}
		else if( strFieldName.equals("bak7") ) {
			return Schema.TYPE_STRING;
		}
		else if( strFieldName.equals("bak8") ) {
			return Schema.TYPE_STRING;
		}
		else if( strFieldName.equals("bak9") ) {
			return Schema.TYPE_STRING;
		}
		else if( strFieldName.equals("bak10") ) {
			return Schema.TYPE_STRING;
		}
		else if( strFieldName.equals("bak11") ) {
			return Schema.TYPE_DATE;
		}
		else if( strFieldName.equals("bak12") ) {
			return Schema.TYPE_DATE;
		}
		else if( strFieldName.equals("makedate") ) {
			return Schema.TYPE_DATE;
		}
		else if( strFieldName.equals("maketime") ) {
			return Schema.TYPE_STRING;
		}
		else if( strFieldName.equals("modifydate") ) {
			return Schema.TYPE_DATE;
		}
		else if( strFieldName.equals("modifytime") ) {
			return Schema.TYPE_STRING;
		}
		else if( strFieldName.equals("flag1") ) {
			return Schema.TYPE_STRING;
		}
		else if( strFieldName.equals("flag2") ) {
			return Schema.TYPE_STRING;
		}
		else if( strFieldName.equals("flag3") ) {
			return Schema.TYPE_STRING;
		}
		else if( strFieldName.equals("flag4") ) {
			return Schema.TYPE_STRING;
		}
		return Schema.TYPE_NOFOUND;
	}

	/**
	* ȡ��Schema��ָ������ֵ����Ӧ���ֶ�����
	* ���û�ж�Ӧ���ֶΣ�����Schema.TYPE_NOFOUND
       * @param: nFieldIndex int
       * @return: int
	**/
	public int getFieldType(int nFieldIndex)
	{
		int nFieldType = Schema.TYPE_NOFOUND;
		switch(nFieldIndex) {
			case 0:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 1:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 2:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 3:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 4:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 5:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 6:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 7:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 8:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 9:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 10:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 11:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 12:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 13:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 14:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 15:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 16:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 17:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 18:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 19:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 20:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 21:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

	/**
	* ȡ��Schema��ָ������ֵ����Ӧ���ֶ����ݿ�����
	* ���û�ж�Ӧ���ֶΣ�����Types.VARCHAR
   * @param: nFieldIndex int
   * @return: int[] ,{Types.CHA),12}; 
	* Types.CHAR Ϊ�����������ṩ�ֶγ��ȣ������ɲ������ֶγ��ȡ�
	**/
	public int[] getFieldSQLType(int nFieldIndex)
	{
		int[] fieldSQLType = new int[2];
		switch(nFieldIndex) {
			case 0:
				fieldSQLType[0] = Types.VARCHAR;
				break;
			case 1:
				fieldSQLType[0] = Types.VARCHAR;
				break;
			case 2:
				fieldSQLType[0] = Types.VARCHAR;
				break;
			case 3:
				fieldSQLType[0] = Types.VARCHAR;
				break;
			case 4:
				fieldSQLType[0] = Types.VARCHAR;
				break;
			case 5:
				fieldSQLType[0] = Types.VARCHAR;
				break;
			case 6:
				fieldSQLType[0] = Types.VARCHAR;
				break;
			case 7:
				fieldSQLType[0] = Types.VARCHAR;
				break;
			case 8:
				fieldSQLType[0] = Types.VARCHAR;
				break;
			case 9:
				fieldSQLType[0] = Types.VARCHAR;
				break;
			case 10:
				fieldSQLType[0] = Types.VARCHAR;
				break;
			case 11:
				fieldSQLType[0] = Types.VARCHAR;
				break;
			case 12:
				fieldSQLType[0] = Types.DATE;
				break;
			case 13:
				fieldSQLType[0] = Types.DATE;
				break;
			case 14:
				fieldSQLType[0] = Types.DATE;
				break;
			case 15:
				fieldSQLType[0] = Types.VARCHAR;
				break;
			case 16:
				fieldSQLType[0] = Types.DATE;
				break;
			case 17:
				fieldSQLType[0] = Types.VARCHAR;
				break;
			case 18:
				fieldSQLType[0] = Types.VARCHAR;
				break;
			case 19:
				fieldSQLType[0] = Types.VARCHAR;
				break;
			case 20:
				fieldSQLType[0] = Types.VARCHAR;
				break;
			case 21:
				fieldSQLType[0] = Types.VARCHAR;
				break;
			default:
				fieldSQLType[0] = Types.NULL;
		};
		return fieldSQLType;
	}
}
