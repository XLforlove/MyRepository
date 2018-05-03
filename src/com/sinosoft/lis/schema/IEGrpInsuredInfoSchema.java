/**
 * Copyright (c) 2017 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.schema;

import java.sql.*;
import java.util.Date;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.utility.*;
import com.sinosoft.lis.db.IEGrpInsuredInfoDB;

/**
 * <p>Title: Web业务系统</p>
 * <p>ClassName: IEGrpInsuredInfoSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * @Database: PhysicalDataModel_2
 * @CreateDate：2017-04-21
 */
public class IEGrpInsuredInfoSchema implements Schema, Cloneable
{
	// @Field
	/** Transtype */
	private String TransType;
	/** Transcode */
	private String TransCode;
	/** Tnodecode */
	private String TNodeCode;
	/** Tparentnodecode */
	private String TParentNodeCode;
	/** Cutomersequenceno */
	private String CutomerSequenceNo;
	/** Customerno */
	private String CustomerNo;
	/** Occupationcode */
	private String OccupationCode;
	/** Insuredeffectivedate */
	private Date InsuredEffectiveDate;
	/** Insuredexpiredate */
	private Date InsuredExpireDate;
	/** Insuredtype */
	private String InsuredType;
	/** Livingaddress */
	private String LivingAddress;
	/** Address */
	private String Address;
	/** Householdaddress */
	private String HouseHoldAddress;
	/** Postalcode */
	private String PostalCode;
	/** Phoneno */
	private String PhoneNo;
	/** Email */
	private String Email;
	/** Homephone */
	private String HomePhone;
	/** Mobile */
	private String Mobile;
	/** Operator */
	private String Operator;
	/** Makedate */
	private Date MakeDate;
	/** Maketime */
	private String MakeTime;
	/** Modifydate */
	private Date ModifyDate;
	/** Modifytime */
	private String ModifyTime;
	/** Contno */
	private String ContNo;
	/** Insuredno */
	private String InsuredNo;
	/** Edorno */
	private String EdorNo;

	public static final int FIELDNUM = 26;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public IEGrpInsuredInfoSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[3];
		pk[0] = "TransType";
		pk[1] = "TransCode";
		pk[2] = "TNodeCode";

		PK = pk;
	}

            /**
             * Schema克隆
             * @return Object
             * @throws CloneNotSupportedException
             */
            public Object clone()
                    throws CloneNotSupportedException
            {
                IEGrpInsuredInfoSchema cloned = (IEGrpInsuredInfoSchema)super.clone();
                cloned.fDate = (FDate) fDate.clone();
                cloned.mErrors = (CErrors) mErrors.clone();
                return cloned;
            }

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getTransType()
	{
		return TransType;
	}
	public void setTransType(String aTransType)
	{
		TransType = StrTool.cTrim(aTransType);
	}
	public String getTransCode()
	{
		return TransCode;
	}
	public void setTransCode(String aTransCode)
	{
		TransCode = StrTool.cTrim(aTransCode);
	}
	public String getTNodeCode()
	{
		return TNodeCode;
	}
	public void setTNodeCode(String aTNodeCode)
	{
		TNodeCode = StrTool.cTrim(aTNodeCode);
	}
	public String getTParentNodeCode()
	{
		return TParentNodeCode;
	}
	public void setTParentNodeCode(String aTParentNodeCode)
	{
		TParentNodeCode = StrTool.cTrim(aTParentNodeCode);
	}
	public String getCutomerSequenceNo()
	{
		return CutomerSequenceNo;
	}
	public void setCutomerSequenceNo(String aCutomerSequenceNo)
	{
		CutomerSequenceNo = StrTool.cTrim(aCutomerSequenceNo);
	}
	public String getCustomerNo()
	{
		return CustomerNo;
	}
	public void setCustomerNo(String aCustomerNo)
	{
		CustomerNo = StrTool.cTrim(aCustomerNo);
	}
	public String getOccupationCode()
	{
		return OccupationCode;
	}
	public void setOccupationCode(String aOccupationCode)
	{
		OccupationCode = StrTool.cTrim(aOccupationCode);
	}
	public String getInsuredEffectiveDate()
	{
		if( InsuredEffectiveDate != null )
			return fDate.getString(InsuredEffectiveDate);
		else
			return null;
	}
	public void setInsuredEffectiveDate(Date aInsuredEffectiveDate)
	{
		InsuredEffectiveDate = aInsuredEffectiveDate;
	}
	public void setInsuredEffectiveDate(String aInsuredEffectiveDate)
	{
		if (aInsuredEffectiveDate != null && !aInsuredEffectiveDate.equals("") )
		{
			InsuredEffectiveDate = fDate.getDate( aInsuredEffectiveDate );
		}
		else
			InsuredEffectiveDate = null;
	}

	public String getInsuredExpireDate()
	{
		if( InsuredExpireDate != null )
			return fDate.getString(InsuredExpireDate);
		else
			return null;
	}
	public void setInsuredExpireDate(Date aInsuredExpireDate)
	{
		InsuredExpireDate = aInsuredExpireDate;
	}
	public void setInsuredExpireDate(String aInsuredExpireDate)
	{
		if (aInsuredExpireDate != null && !aInsuredExpireDate.equals("") )
		{
			InsuredExpireDate = fDate.getDate( aInsuredExpireDate );
		}
		else
			InsuredExpireDate = null;
	}

	public String getInsuredType()
	{
		return InsuredType;
	}
	public void setInsuredType(String aInsuredType)
	{
		InsuredType = StrTool.cTrim(aInsuredType);
	}
	public String getLivingAddress()
	{
		return LivingAddress;
	}
	public void setLivingAddress(String aLivingAddress)
	{
		LivingAddress = StrTool.cTrim(aLivingAddress);
	}
	public String getAddress()
	{
		return Address;
	}
	public void setAddress(String aAddress)
	{
		Address = StrTool.cTrim(aAddress);
	}
	public String getHouseHoldAddress()
	{
		return HouseHoldAddress;
	}
	public void setHouseHoldAddress(String aHouseHoldAddress)
	{
		HouseHoldAddress = StrTool.cTrim(aHouseHoldAddress);
	}
	public String getPostalCode()
	{
		return PostalCode;
	}
	public void setPostalCode(String aPostalCode)
	{
		PostalCode = StrTool.cTrim(aPostalCode);
	}
	public String getPhoneNo()
	{
		return PhoneNo;
	}
	public void setPhoneNo(String aPhoneNo)
	{
		PhoneNo = StrTool.cTrim(aPhoneNo);
	}
	public String getEmail()
	{
		return Email;
	}
	public void setEmail(String aEmail)
	{
		Email = StrTool.cTrim(aEmail);
	}
	public String getHomePhone()
	{
		return HomePhone;
	}
	public void setHomePhone(String aHomePhone)
	{
		HomePhone = StrTool.cTrim(aHomePhone);
	}
	public String getMobile()
	{
		return Mobile;
	}
	public void setMobile(String aMobile)
	{
		Mobile = StrTool.cTrim(aMobile);
	}
	public String getOperator()
	{
		return Operator;
	}
	public void setOperator(String aOperator)
	{
		Operator = StrTool.cTrim(aOperator);
	}
	public String getMakeDate()
	{
		if( MakeDate != null )
			return fDate.getString(MakeDate);
		else
			return null;
	}
	public void setMakeDate(Date aMakeDate)
	{
		MakeDate = aMakeDate;
	}
	public void setMakeDate(String aMakeDate)
	{
		if (aMakeDate != null && !aMakeDate.equals("") )
		{
			MakeDate = fDate.getDate( aMakeDate );
		}
		else
			MakeDate = null;
	}

	public String getMakeTime()
	{
		return MakeTime;
	}
	public void setMakeTime(String aMakeTime)
	{
		MakeTime = StrTool.cTrim(aMakeTime);
	}
	public String getModifyDate()
	{
		if( ModifyDate != null )
			return fDate.getString(ModifyDate);
		else
			return null;
	}
	public void setModifyDate(Date aModifyDate)
	{
		ModifyDate = aModifyDate;
	}
	public void setModifyDate(String aModifyDate)
	{
		if (aModifyDate != null && !aModifyDate.equals("") )
		{
			ModifyDate = fDate.getDate( aModifyDate );
		}
		else
			ModifyDate = null;
	}

	public String getModifyTime()
	{
		return ModifyTime;
	}
	public void setModifyTime(String aModifyTime)
	{
		ModifyTime = StrTool.cTrim(aModifyTime);
	}
	public String getContNo()
	{
		return ContNo;
	}
	public void setContNo(String aContNo)
	{
		ContNo = StrTool.cTrim(aContNo);
	}
	public String getInsuredNo()
	{
		return InsuredNo;
	}
	public void setInsuredNo(String aInsuredNo)
	{
		InsuredNo = StrTool.cTrim(aInsuredNo);
	}
	public String getEdorNo()
	{
		return EdorNo;
	}
	public void setEdorNo(String aEdorNo)
	{
		EdorNo = StrTool.cTrim(aEdorNo);
	}

	/**
	* 使用另外一个 IEGrpInsuredInfoSchema 对象给 Schema 赋值
	* @param: aIEGrpInsuredInfoSchema IEGrpInsuredInfoSchema
	**/
	public void setSchema(IEGrpInsuredInfoSchema aIEGrpInsuredInfoSchema)
	{
		this.TransType = aIEGrpInsuredInfoSchema.getTransType();
		this.TransCode = aIEGrpInsuredInfoSchema.getTransCode();
		this.TNodeCode = aIEGrpInsuredInfoSchema.getTNodeCode();
		this.TParentNodeCode = aIEGrpInsuredInfoSchema.getTParentNodeCode();
		this.CutomerSequenceNo = aIEGrpInsuredInfoSchema.getCutomerSequenceNo();
		this.CustomerNo = aIEGrpInsuredInfoSchema.getCustomerNo();
		this.OccupationCode = aIEGrpInsuredInfoSchema.getOccupationCode();
		this.InsuredEffectiveDate = fDate.getDate( aIEGrpInsuredInfoSchema.getInsuredEffectiveDate());
		this.InsuredExpireDate = fDate.getDate( aIEGrpInsuredInfoSchema.getInsuredExpireDate());
		this.InsuredType = aIEGrpInsuredInfoSchema.getInsuredType();
		this.LivingAddress = aIEGrpInsuredInfoSchema.getLivingAddress();
		this.Address = aIEGrpInsuredInfoSchema.getAddress();
		this.HouseHoldAddress = aIEGrpInsuredInfoSchema.getHouseHoldAddress();
		this.PostalCode = aIEGrpInsuredInfoSchema.getPostalCode();
		this.PhoneNo = aIEGrpInsuredInfoSchema.getPhoneNo();
		this.Email = aIEGrpInsuredInfoSchema.getEmail();
		this.HomePhone = aIEGrpInsuredInfoSchema.getHomePhone();
		this.Mobile = aIEGrpInsuredInfoSchema.getMobile();
		this.Operator = aIEGrpInsuredInfoSchema.getOperator();
		this.MakeDate = fDate.getDate( aIEGrpInsuredInfoSchema.getMakeDate());
		this.MakeTime = aIEGrpInsuredInfoSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aIEGrpInsuredInfoSchema.getModifyDate());
		this.ModifyTime = aIEGrpInsuredInfoSchema.getModifyTime();
		this.ContNo = aIEGrpInsuredInfoSchema.getContNo();
		this.InsuredNo = aIEGrpInsuredInfoSchema.getInsuredNo();
		this.EdorNo = aIEGrpInsuredInfoSchema.getEdorNo();
	}

	/**
	* 使用 ResultSet 中的第 i 行给 Schema 赋值
	* @param: rs ResultSet
	* @param: i int
	* @return: boolean
	**/
	public boolean setSchema(ResultSet rs,int i)
	{
		try
		{
			//rs.absolute(i);		// 非滚动游标
			if( rs.getString(1) == null )
				this.TransType = null;
			else
				this.TransType = rs.getString(1).trim();

			if( rs.getString(2) == null )
				this.TransCode = null;
			else
				this.TransCode = rs.getString(2).trim();

			if( rs.getString(3) == null )
				this.TNodeCode = null;
			else
				this.TNodeCode = rs.getString(3).trim();

			if( rs.getString(4) == null )
				this.TParentNodeCode = null;
			else
				this.TParentNodeCode = rs.getString(4).trim();

			if( rs.getString(5) == null )
				this.CutomerSequenceNo = null;
			else
				this.CutomerSequenceNo = rs.getString(5).trim();

			if( rs.getString(6) == null )
				this.CustomerNo = null;
			else
				this.CustomerNo = rs.getString(6).trim();

			if( rs.getString(7) == null )
				this.OccupationCode = null;
			else
				this.OccupationCode = rs.getString(7).trim();

			this.InsuredEffectiveDate = rs.getDate(8);
			this.InsuredExpireDate = rs.getDate(9);
			if( rs.getString(10) == null )
				this.InsuredType = null;
			else
				this.InsuredType = rs.getString(10).trim();

			if( rs.getString(11) == null )
				this.LivingAddress = null;
			else
				this.LivingAddress = rs.getString(11).trim();

			if( rs.getString(12) == null )
				this.Address = null;
			else
				this.Address = rs.getString(12).trim();

			if( rs.getString(13) == null )
				this.HouseHoldAddress = null;
			else
				this.HouseHoldAddress = rs.getString(13).trim();

			if( rs.getString(14) == null )
				this.PostalCode = null;
			else
				this.PostalCode = rs.getString(14).trim();

			if( rs.getString(15) == null )
				this.PhoneNo = null;
			else
				this.PhoneNo = rs.getString(15).trim();

			if( rs.getString(16) == null )
				this.Email = null;
			else
				this.Email = rs.getString(16).trim();

			if( rs.getString(17) == null )
				this.HomePhone = null;
			else
				this.HomePhone = rs.getString(17).trim();

			if( rs.getString(18) == null )
				this.Mobile = null;
			else
				this.Mobile = rs.getString(18).trim();

			if( rs.getString(19) == null )
				this.Operator = null;
			else
				this.Operator = rs.getString(19).trim();

			this.MakeDate = rs.getDate(20);
			if( rs.getString(21) == null )
				this.MakeTime = null;
			else
				this.MakeTime = rs.getString(21).trim();

			this.ModifyDate = rs.getDate(22);
			if( rs.getString(23) == null )
				this.ModifyTime = null;
			else
				this.ModifyTime = rs.getString(23).trim();

			if( rs.getString(24) == null )
				this.ContNo = null;
			else
				this.ContNo = rs.getString(24).trim();

			if( rs.getString(25) == null )
				this.InsuredNo = null;
			else
				this.InsuredNo = rs.getString(25).trim();

			if( rs.getString(26) == null )
				this.EdorNo = null;
			else
				this.EdorNo = rs.getString(26).trim();

		}
		catch(SQLException sqle)
		{
			System.out.println("数据库中的IEGrpInsuredInfo表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "IEGrpInsuredInfoSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public IEGrpInsuredInfoSchema getSchema()
	{
		IEGrpInsuredInfoSchema aIEGrpInsuredInfoSchema = new IEGrpInsuredInfoSchema();
		aIEGrpInsuredInfoSchema.setSchema(this);
		return aIEGrpInsuredInfoSchema;
	}

	public IEGrpInsuredInfoDB getDB()
	{
		IEGrpInsuredInfoDB aDBOper = new IEGrpInsuredInfoDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
strReturn.append(StrTool.cTrim(TransType)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(TransCode)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(TNodeCode)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(TParentNodeCode)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(CutomerSequenceNo)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(CustomerNo)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(OccupationCode)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(fDate.getString( InsuredEffectiveDate ))); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(fDate.getString( InsuredExpireDate ))); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(InsuredType)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(LivingAddress)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(Address)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(HouseHoldAddress)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(PostalCode)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(PhoneNo)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(Email)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(HomePhone)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(Mobile)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(ModifyTime)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(ContNo)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(InsuredNo)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(EdorNo));
		return strReturn.toString();
	}

	/**
	* 数据解包
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			// 仅仅需要对传入的strMessage进行一次转换
        	strMessage = StrTool.GBKToUnicode(strMessage);
			TransType = StrTool.getStr(strMessage, 1, SysConst.PACKAGESPILTER );
			TransCode = StrTool.getStr(strMessage, 2, SysConst.PACKAGESPILTER );
			TNodeCode = StrTool.getStr(strMessage, 3, SysConst.PACKAGESPILTER );
			TParentNodeCode = StrTool.getStr(strMessage, 4, SysConst.PACKAGESPILTER );
			CutomerSequenceNo = StrTool.getStr(strMessage, 5, SysConst.PACKAGESPILTER );
			CustomerNo = StrTool.getStr(strMessage, 6, SysConst.PACKAGESPILTER );
			OccupationCode = StrTool.getStr(strMessage, 7, SysConst.PACKAGESPILTER );
			InsuredEffectiveDate = fDate.getDate(StrTool.getStr(strMessage, 8,SysConst.PACKAGESPILTER));
			InsuredExpireDate = fDate.getDate(StrTool.getStr(strMessage, 9,SysConst.PACKAGESPILTER));
			InsuredType = StrTool.getStr(strMessage, 10, SysConst.PACKAGESPILTER );
			LivingAddress = StrTool.getStr(strMessage, 11, SysConst.PACKAGESPILTER );
			Address = StrTool.getStr(strMessage, 12, SysConst.PACKAGESPILTER );
			HouseHoldAddress = StrTool.getStr(strMessage, 13, SysConst.PACKAGESPILTER );
			PostalCode = StrTool.getStr(strMessage, 14, SysConst.PACKAGESPILTER );
			PhoneNo = StrTool.getStr(strMessage, 15, SysConst.PACKAGESPILTER );
			Email = StrTool.getStr(strMessage, 16, SysConst.PACKAGESPILTER );
			HomePhone = StrTool.getStr(strMessage, 17, SysConst.PACKAGESPILTER );
			Mobile = StrTool.getStr(strMessage, 18, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(strMessage, 19, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(strMessage, 20,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(strMessage, 21, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(strMessage, 22,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(strMessage, 23, SysConst.PACKAGESPILTER );
			ContNo = StrTool.getStr(strMessage, 24, SysConst.PACKAGESPILTER );
			InsuredNo = StrTool.getStr(strMessage, 25, SysConst.PACKAGESPILTER );
			EdorNo = StrTool.getStr(strMessage, 26, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "IEGrpInsuredInfoSchema";
			tError.functionName = "decode";
			tError.errorMessage = ex.toString();
			this.mErrors .addOneError(tError);

			return false;
		}
		return true;
	}

	/**
	* 取得对应传入参数的String形式的字段值
	* @param: FCode String 希望取得的字段名
	* @return: String
	* 如果没有对应的字段，返回""
	* 如果字段值为空，返回"null"
	**/
	public String getV(String FCode)
	{
		String strReturn = "";
		if (FCode.equalsIgnoreCase("TransType"))
		{
			strReturn = StrTool.cTrim(TransType);
		}
		else if (FCode.equalsIgnoreCase("TransCode"))
		{
			strReturn = StrTool.cTrim(TransCode);
		}
		else if (FCode.equalsIgnoreCase("TNodeCode"))
		{
			strReturn = StrTool.cTrim(TNodeCode);
		}
		else if (FCode.equalsIgnoreCase("TParentNodeCode"))
		{
			strReturn = StrTool.cTrim(TParentNodeCode);
		}
		else if (FCode.equalsIgnoreCase("CutomerSequenceNo"))
		{
			strReturn = StrTool.cTrim(CutomerSequenceNo);
		}
		else if (FCode.equalsIgnoreCase("CustomerNo"))
		{
			strReturn = StrTool.cTrim(CustomerNo);
		}
		else if (FCode.equalsIgnoreCase("OccupationCode"))
		{
			strReturn = StrTool.cTrim(OccupationCode);
		}
		else if (FCode.equalsIgnoreCase("InsuredEffectiveDate"))
		{
			strReturn = StrTool.cTrim(this.getInsuredEffectiveDate());
		}
		else if (FCode.equalsIgnoreCase("InsuredExpireDate"))
		{
			strReturn = StrTool.cTrim(this.getInsuredExpireDate());
		}
		else if (FCode.equalsIgnoreCase("InsuredType"))
		{
			strReturn = StrTool.cTrim(InsuredType);
		}
		else if (FCode.equalsIgnoreCase("LivingAddress"))
		{
			strReturn = StrTool.cTrim(LivingAddress);
		}
		else if (FCode.equalsIgnoreCase("Address"))
		{
			strReturn = StrTool.cTrim(Address);
		}
		else if (FCode.equalsIgnoreCase("HouseHoldAddress"))
		{
			strReturn = StrTool.cTrim(HouseHoldAddress);
		}
		else if (FCode.equalsIgnoreCase("PostalCode"))
		{
			strReturn = StrTool.cTrim(PostalCode);
		}
		else if (FCode.equalsIgnoreCase("PhoneNo"))
		{
			strReturn = StrTool.cTrim(PhoneNo);
		}
		else if (FCode.equalsIgnoreCase("Email"))
		{
			strReturn = StrTool.cTrim(Email);
		}
		else if (FCode.equalsIgnoreCase("HomePhone"))
		{
			strReturn = StrTool.cTrim(HomePhone);
		}
		else if (FCode.equalsIgnoreCase("Mobile"))
		{
			strReturn = StrTool.cTrim(Mobile);
		}
		else if (FCode.equalsIgnoreCase("Operator"))
		{
			strReturn = StrTool.cTrim(Operator);
		}
		else if (FCode.equalsIgnoreCase("MakeDate"))
		{
			strReturn = StrTool.cTrim(this.getMakeDate());
		}
		else if (FCode.equalsIgnoreCase("MakeTime"))
		{
			strReturn = StrTool.cTrim(MakeTime);
		}
		else if (FCode.equalsIgnoreCase("ModifyDate"))
		{
			strReturn = StrTool.cTrim(this.getModifyDate());
		}
		else if (FCode.equalsIgnoreCase("ModifyTime"))
		{
			strReturn = StrTool.cTrim(ModifyTime);
		}
		else if (FCode.equalsIgnoreCase("ContNo"))
		{
			strReturn = StrTool.cTrim(ContNo);
		}
		else if (FCode.equalsIgnoreCase("InsuredNo"))
		{
			strReturn = StrTool.cTrim(InsuredNo);
		}
		else if (FCode.equalsIgnoreCase("EdorNo"))
		{
			strReturn = StrTool.cTrim(EdorNo);
		}
		if (strReturn.equals(""))
		{
			strReturn = "null";
		}

		return strReturn;
	}


	/**
	* 取得Schema中指定索引值所对应的字段值
	* @param: nFieldIndex int 指定的字段索引值
	* @return: String
	* 如果没有对应的字段，返回""
	* 如果字段值为空，返回"null"
	**/
	public String getV(int nFieldIndex)
	{
		String strFieldValue = "";
		switch(nFieldIndex) {
			case 0:
				strFieldValue = StrTool.cTrim(TransType);
				break;
			case 1:
				strFieldValue = StrTool.cTrim(TransCode);
				break;
			case 2:
				strFieldValue = StrTool.cTrim(TNodeCode);
				break;
			case 3:
				strFieldValue = StrTool.cTrim(TParentNodeCode);
				break;
			case 4:
				strFieldValue = StrTool.cTrim(CutomerSequenceNo);
				break;
			case 5:
				strFieldValue = StrTool.cTrim(CustomerNo);
				break;
			case 6:
				strFieldValue = StrTool.cTrim(OccupationCode);
				break;
			case 7:
				strFieldValue = StrTool.cTrim(this.getInsuredEffectiveDate());
				break;
			case 8:
				strFieldValue = StrTool.cTrim(this.getInsuredExpireDate());
				break;
			case 9:
				strFieldValue = StrTool.cTrim(InsuredType);
				break;
			case 10:
				strFieldValue = StrTool.cTrim(LivingAddress);
				break;
			case 11:
				strFieldValue = StrTool.cTrim(Address);
				break;
			case 12:
				strFieldValue = StrTool.cTrim(HouseHoldAddress);
				break;
			case 13:
				strFieldValue = StrTool.cTrim(PostalCode);
				break;
			case 14:
				strFieldValue = StrTool.cTrim(PhoneNo);
				break;
			case 15:
				strFieldValue = StrTool.cTrim(Email);
				break;
			case 16:
				strFieldValue = StrTool.cTrim(HomePhone);
				break;
			case 17:
				strFieldValue = StrTool.cTrim(Mobile);
				break;
			case 18:
				strFieldValue = StrTool.cTrim(Operator);
				break;
			case 19:
				strFieldValue = StrTool.cTrim(this.getMakeDate());
				break;
			case 20:
				strFieldValue = StrTool.cTrim(MakeTime);
				break;
			case 21:
				strFieldValue = StrTool.cTrim(this.getModifyDate());
				break;
			case 22:
				strFieldValue = StrTool.cTrim(ModifyTime);
				break;
			case 23:
				strFieldValue = StrTool.cTrim(ContNo);
				break;
			case 24:
				strFieldValue = StrTool.cTrim(InsuredNo);
				break;
			case 25:
				strFieldValue = StrTool.cTrim(EdorNo);
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
	* 设置对应传入参数的String形式的字段值
	* @param: FCode String 需要赋值的对象
	* @param: FValue String 要赋的值
	* @return: boolean
	**/
	public boolean setV(String FCode ,String FValue)
	{
		if( StrTool.cTrim( FCode ).equals( "" ))
			return false;

		if (FCode.equalsIgnoreCase("TransType"))
		{
			if( FValue != null)
			{
				TransType = FValue.trim();
			}
			else
				TransType = null;
		}
		else if (FCode.equalsIgnoreCase("TransCode"))
		{
			if( FValue != null)
			{
				TransCode = FValue.trim();
			}
			else
				TransCode = null;
		}
		else if (FCode.equalsIgnoreCase("TNodeCode"))
		{
			if( FValue != null)
			{
				TNodeCode = FValue.trim();
			}
			else
				TNodeCode = null;
		}
		else if (FCode.equalsIgnoreCase("TParentNodeCode"))
		{
			if( FValue != null)
			{
				TParentNodeCode = FValue.trim();
			}
			else
				TParentNodeCode = null;
		}
		else if (FCode.equalsIgnoreCase("CutomerSequenceNo"))
		{
			if( FValue != null)
			{
				CutomerSequenceNo = FValue.trim();
			}
			else
				CutomerSequenceNo = null;
		}
		else if (FCode.equalsIgnoreCase("CustomerNo"))
		{
			if( FValue != null)
			{
				CustomerNo = FValue.trim();
			}
			else
				CustomerNo = null;
		}
		else if (FCode.equalsIgnoreCase("OccupationCode"))
		{
			if( FValue != null)
			{
				OccupationCode = FValue.trim();
			}
			else
				OccupationCode = null;
		}
		else if (FCode.equalsIgnoreCase("InsuredEffectiveDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				InsuredEffectiveDate = fDate.getDate( FValue );
			}
			else
				InsuredEffectiveDate = null;
		}
		else if (FCode.equalsIgnoreCase("InsuredExpireDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				InsuredExpireDate = fDate.getDate( FValue );
			}
			else
				InsuredExpireDate = null;
		}
		else if (FCode.equalsIgnoreCase("InsuredType"))
		{
			if( FValue != null)
			{
				InsuredType = FValue.trim();
			}
			else
				InsuredType = null;
		}
		else if (FCode.equalsIgnoreCase("LivingAddress"))
		{
			if( FValue != null)
			{
				LivingAddress = FValue.trim();
			}
			else
				LivingAddress = null;
		}
		else if (FCode.equalsIgnoreCase("Address"))
		{
			if( FValue != null)
			{
				Address = FValue.trim();
			}
			else
				Address = null;
		}
		else if (FCode.equalsIgnoreCase("HouseHoldAddress"))
		{
			if( FValue != null)
			{
				HouseHoldAddress = FValue.trim();
			}
			else
				HouseHoldAddress = null;
		}
		else if (FCode.equalsIgnoreCase("PostalCode"))
		{
			if( FValue != null)
			{
				PostalCode = FValue.trim();
			}
			else
				PostalCode = null;
		}
		else if (FCode.equalsIgnoreCase("PhoneNo"))
		{
			if( FValue != null)
			{
				PhoneNo = FValue.trim();
			}
			else
				PhoneNo = null;
		}
		else if (FCode.equalsIgnoreCase("Email"))
		{
			if( FValue != null)
			{
				Email = FValue.trim();
			}
			else
				Email = null;
		}
		else if (FCode.equalsIgnoreCase("HomePhone"))
		{
			if( FValue != null)
			{
				HomePhone = FValue.trim();
			}
			else
				HomePhone = null;
		}
		else if (FCode.equalsIgnoreCase("Mobile"))
		{
			if( FValue != null)
			{
				Mobile = FValue.trim();
			}
			else
				Mobile = null;
		}
		else if (FCode.equalsIgnoreCase("Operator"))
		{
			if( FValue != null)
			{
				Operator = FValue.trim();
			}
			else
				Operator = null;
		}
		else if (FCode.equalsIgnoreCase("MakeDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				MakeDate = fDate.getDate( FValue );
			}
			else
				MakeDate = null;
		}
		else if (FCode.equalsIgnoreCase("MakeTime"))
		{
			if( FValue != null)
			{
				MakeTime = FValue.trim();
			}
			else
				MakeTime = null;
		}
		else if (FCode.equalsIgnoreCase("ModifyDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				ModifyDate = fDate.getDate( FValue );
			}
			else
				ModifyDate = null;
		}
		else if (FCode.equalsIgnoreCase("ModifyTime"))
		{
			if( FValue != null)
			{
				ModifyTime = FValue.trim();
			}
			else
				ModifyTime = null;
		}
		else if (FCode.equalsIgnoreCase("ContNo"))
		{
			if( FValue != null)
			{
				ContNo = FValue.trim();
			}
			else
				ContNo = null;
		}
		else if (FCode.equalsIgnoreCase("InsuredNo"))
		{
			if( FValue != null)
			{
				InsuredNo = FValue.trim();
			}
			else
				InsuredNo = null;
		}
		else if (FCode.equalsIgnoreCase("EdorNo"))
		{
			if( FValue != null)
			{
				EdorNo = FValue.trim();
			}
			else
				EdorNo = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		IEGrpInsuredInfoSchema other = (IEGrpInsuredInfoSchema)otherObject;
		return
			TransType.equals(other.getTransType())
			&& TransCode.equals(other.getTransCode())
			&& TNodeCode.equals(other.getTNodeCode())
			&& TParentNodeCode.equals(other.getTParentNodeCode())
			&& CutomerSequenceNo.equals(other.getCutomerSequenceNo())
			&& CustomerNo.equals(other.getCustomerNo())
			&& OccupationCode.equals(other.getOccupationCode())
			&& fDate.getString(InsuredEffectiveDate).equals(other.getInsuredEffectiveDate())
			&& fDate.getString(InsuredExpireDate).equals(other.getInsuredExpireDate())
			&& InsuredType.equals(other.getInsuredType())
			&& LivingAddress.equals(other.getLivingAddress())
			&& Address.equals(other.getAddress())
			&& HouseHoldAddress.equals(other.getHouseHoldAddress())
			&& PostalCode.equals(other.getPostalCode())
			&& PhoneNo.equals(other.getPhoneNo())
			&& Email.equals(other.getEmail())
			&& HomePhone.equals(other.getHomePhone())
			&& Mobile.equals(other.getMobile())
			&& Operator.equals(other.getOperator())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& ModifyTime.equals(other.getModifyTime())
			&& ContNo.equals(other.getContNo())
			&& InsuredNo.equals(other.getInsuredNo())
			&& EdorNo.equals(other.getEdorNo());
	}

	/**
	* 取得Schema拥有字段的数量
       * @return: int
	**/
	public int getFieldCount()
	{
 		return FIELDNUM;
	}

	/**
	* 取得Schema中指定字段名所对应的索引值
	* 如果没有对应的字段，返回-1
       * @param: strFieldName String
       * @return: int
	**/
	public int getFieldIndex(String strFieldName)
	{
		if( strFieldName.equals("TransType") ) {
			return 0;
		}
		else if( strFieldName.equals("TransCode") ) {
			return 1;
		}
		else if( strFieldName.equals("TNodeCode") ) {
			return 2;
		}
		else if( strFieldName.equals("TParentNodeCode") ) {
			return 3;
		}
		else if( strFieldName.equals("CutomerSequenceNo") ) {
			return 4;
		}
		else if( strFieldName.equals("CustomerNo") ) {
			return 5;
		}
		else if( strFieldName.equals("OccupationCode") ) {
			return 6;
		}
		else if( strFieldName.equals("InsuredEffectiveDate") ) {
			return 7;
		}
		else if( strFieldName.equals("InsuredExpireDate") ) {
			return 8;
		}
		else if( strFieldName.equals("InsuredType") ) {
			return 9;
		}
		else if( strFieldName.equals("LivingAddress") ) {
			return 10;
		}
		else if( strFieldName.equals("Address") ) {
			return 11;
		}
		else if( strFieldName.equals("HouseHoldAddress") ) {
			return 12;
		}
		else if( strFieldName.equals("PostalCode") ) {
			return 13;
		}
		else if( strFieldName.equals("PhoneNo") ) {
			return 14;
		}
		else if( strFieldName.equals("Email") ) {
			return 15;
		}
		else if( strFieldName.equals("HomePhone") ) {
			return 16;
		}
		else if( strFieldName.equals("Mobile") ) {
			return 17;
		}
		else if( strFieldName.equals("Operator") ) {
			return 18;
		}
		else if( strFieldName.equals("MakeDate") ) {
			return 19;
		}
		else if( strFieldName.equals("MakeTime") ) {
			return 20;
		}
		else if( strFieldName.equals("ModifyDate") ) {
			return 21;
		}
		else if( strFieldName.equals("ModifyTime") ) {
			return 22;
		}
		else if( strFieldName.equals("ContNo") ) {
			return 23;
		}
		else if( strFieldName.equals("InsuredNo") ) {
			return 24;
		}
		else if( strFieldName.equals("EdorNo") ) {
			return 25;
		}
		return -1;
	}

	/**
	* 取得Schema中指定索引值所对应的字段名
	* 如果没有对应的字段，返回""
       * @param: nFieldIndex int
       * @return: String
	**/
	public String getFieldName(int nFieldIndex)
	{
		String strFieldName = "";
		switch(nFieldIndex) {
			case 0:
				strFieldName = "TransType";
				break;
			case 1:
				strFieldName = "TransCode";
				break;
			case 2:
				strFieldName = "TNodeCode";
				break;
			case 3:
				strFieldName = "TParentNodeCode";
				break;
			case 4:
				strFieldName = "CutomerSequenceNo";
				break;
			case 5:
				strFieldName = "CustomerNo";
				break;
			case 6:
				strFieldName = "OccupationCode";
				break;
			case 7:
				strFieldName = "InsuredEffectiveDate";
				break;
			case 8:
				strFieldName = "InsuredExpireDate";
				break;
			case 9:
				strFieldName = "InsuredType";
				break;
			case 10:
				strFieldName = "LivingAddress";
				break;
			case 11:
				strFieldName = "Address";
				break;
			case 12:
				strFieldName = "HouseHoldAddress";
				break;
			case 13:
				strFieldName = "PostalCode";
				break;
			case 14:
				strFieldName = "PhoneNo";
				break;
			case 15:
				strFieldName = "Email";
				break;
			case 16:
				strFieldName = "HomePhone";
				break;
			case 17:
				strFieldName = "Mobile";
				break;
			case 18:
				strFieldName = "Operator";
				break;
			case 19:
				strFieldName = "MakeDate";
				break;
			case 20:
				strFieldName = "MakeTime";
				break;
			case 21:
				strFieldName = "ModifyDate";
				break;
			case 22:
				strFieldName = "ModifyTime";
				break;
			case 23:
				strFieldName = "ContNo";
				break;
			case 24:
				strFieldName = "InsuredNo";
				break;
			case 25:
				strFieldName = "EdorNo";
				break;
			default:
				strFieldName = "";
		};
		return strFieldName;
	}

	/**
	* 取得Schema中指定字段名所对应的字段类型
	* 如果没有对应的字段，返回Schema.TYPE_NOFOUND
       * @param: strFieldName String
       * @return: int
	**/
	public int getFieldType(String strFieldName)
	{
		if( strFieldName.equals("TransType") ) {
			return Schema.TYPE_STRING;
		}
		else if( strFieldName.equals("TransCode") ) {
			return Schema.TYPE_STRING;
		}
		else if( strFieldName.equals("TNodeCode") ) {
			return Schema.TYPE_STRING;
		}
		else if( strFieldName.equals("TParentNodeCode") ) {
			return Schema.TYPE_STRING;
		}
		else if( strFieldName.equals("CutomerSequenceNo") ) {
			return Schema.TYPE_STRING;
		}
		else if( strFieldName.equals("CustomerNo") ) {
			return Schema.TYPE_STRING;
		}
		else if( strFieldName.equals("OccupationCode") ) {
			return Schema.TYPE_STRING;
		}
		else if( strFieldName.equals("InsuredEffectiveDate") ) {
			return Schema.TYPE_DATE;
		}
		else if( strFieldName.equals("InsuredExpireDate") ) {
			return Schema.TYPE_DATE;
		}
		else if( strFieldName.equals("InsuredType") ) {
			return Schema.TYPE_STRING;
		}
		else if( strFieldName.equals("LivingAddress") ) {
			return Schema.TYPE_STRING;
		}
		else if( strFieldName.equals("Address") ) {
			return Schema.TYPE_STRING;
		}
		else if( strFieldName.equals("HouseHoldAddress") ) {
			return Schema.TYPE_STRING;
		}
		else if( strFieldName.equals("PostalCode") ) {
			return Schema.TYPE_STRING;
		}
		else if( strFieldName.equals("PhoneNo") ) {
			return Schema.TYPE_STRING;
		}
		else if( strFieldName.equals("Email") ) {
			return Schema.TYPE_STRING;
		}
		else if( strFieldName.equals("HomePhone") ) {
			return Schema.TYPE_STRING;
		}
		else if( strFieldName.equals("Mobile") ) {
			return Schema.TYPE_STRING;
		}
		else if( strFieldName.equals("Operator") ) {
			return Schema.TYPE_STRING;
		}
		else if( strFieldName.equals("MakeDate") ) {
			return Schema.TYPE_DATE;
		}
		else if( strFieldName.equals("MakeTime") ) {
			return Schema.TYPE_STRING;
		}
		else if( strFieldName.equals("ModifyDate") ) {
			return Schema.TYPE_DATE;
		}
		else if( strFieldName.equals("ModifyTime") ) {
			return Schema.TYPE_STRING;
		}
		else if( strFieldName.equals("ContNo") ) {
			return Schema.TYPE_STRING;
		}
		else if( strFieldName.equals("InsuredNo") ) {
			return Schema.TYPE_STRING;
		}
		else if( strFieldName.equals("EdorNo") ) {
			return Schema.TYPE_STRING;
		}
		return Schema.TYPE_NOFOUND;
	}

	/**
	* 取得Schema中指定索引值所对应的字段类型
	* 如果没有对应的字段，返回Schema.TYPE_NOFOUND
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 8:
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_STRING;
				break;
			case 13:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 14:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 15:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 16:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 17:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 18:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 19:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 20:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 21:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 22:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 23:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 24:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 25:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

	/**
	* 取得Schema中指定索引值所对应的字段数据库类型
	* 如果没有对应的字段，返回Types.VARCHAR
   * @param: nFieldIndex int
   * @return: int[] ,{Types.CHA),12}; 
	* Types.CHAR 为特殊类型需提供字段长度；其他可不设置字段长度。
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
				fieldSQLType[0] = Types.DATE;
				break;
			case 8:
				fieldSQLType[0] = Types.DATE;
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
				fieldSQLType[0] = Types.VARCHAR;
				break;
			case 13:
				fieldSQLType[0] = Types.VARCHAR;
				break;
			case 14:
				fieldSQLType[0] = Types.VARCHAR;
				break;
			case 15:
				fieldSQLType[0] = Types.VARCHAR;
				break;
			case 16:
				fieldSQLType[0] = Types.VARCHAR;
				break;
			case 17:
				fieldSQLType[0] = Types.VARCHAR;
				break;
			case 18:
				fieldSQLType[0] = Types.VARCHAR;
				break;
			case 19:
				fieldSQLType[0] = Types.DATE;
				break;
			case 20:
				fieldSQLType[0] = Types.VARCHAR;
				break;
			case 21:
				fieldSQLType[0] = Types.DATE;
				break;
			case 22:
				fieldSQLType[0] = Types.VARCHAR;
				break;
			case 23:
				fieldSQLType[0] = Types.VARCHAR;
				break;
			case 24:
				fieldSQLType[0] = Types.VARCHAR;
				break;
			case 25:
				fieldSQLType[0] = Types.VARCHAR;
				break;
			default:
				fieldSQLType[0] = Types.NULL;
		};
		return fieldSQLType;
	}
}
