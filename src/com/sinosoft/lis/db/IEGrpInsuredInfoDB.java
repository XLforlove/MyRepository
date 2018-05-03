/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.db;

import java.sql.*;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.IEGrpInsuredInfoSchema;
import com.sinosoft.lis.vschema.IEGrpInsuredInfoSet;
import com.sinosoft.utility.*;

/**
 * <p>ClassName: IEGrpInsuredInfoDB </p>
 * <p>Description: DB层数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: PhysicalDataModel_2
 * @CreateDate：2017-04-21
 */
public class IEGrpInsuredInfoDB extends IEGrpInsuredInfoSchema
{
	// @Field
	private Connection con;
	private DBOper db;
	/**
	* flag = true: 传入Connection
	* flag = false: 不传入Connection
	**/
	private boolean mflag = false;

	public CErrors mErrors = new CErrors();		// 错误信息

	/**
	 * 为批量操作而准备的语句和游标对象
	 */
	private ResultSet mResultSet = null;
	private Statement mStatement = null;
	// @Constructor
	public IEGrpInsuredInfoDB( Connection tConnection )
	{
		con = tConnection;
		db = new DBOper( con, "IEGrpInsuredInfo" );
		mflag = true;
	}

	public IEGrpInsuredInfoDB()
	{
		con = null;
		db = new DBOper( "IEGrpInsuredInfo" );
		mflag = false;
	}

	// @Method
	public boolean deleteSQL()
	{
		IEGrpInsuredInfoSchema tSchema = this.getSchema();
		if (db.deleteSQL(tSchema))
		{
		     return true;
		}
		else
		{
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "IEGrpInsuredInfoDB";
			tError.functionName = "deleteSQL";
			tError.errorMessage = "操作失败!";
			this.mErrors .addOneError(tError);
			return false;
		}
	}

	public int getCount()
	{
		IEGrpInsuredInfoSchema tSchema = this.getSchema();

		int tCount = db.getCount(tSchema);
		if (tCount < 0)
		{
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "IEGrpInsuredInfoDB";
			tError.functionName = "getCount";
			tError.errorMessage = "操作失败!";
			this.mErrors .addOneError(tError);

			return -1;
		}

		return tCount;
	}

	public boolean delete()
	{
		PreparedStatement pstmt = null;

		if( !mflag ) {
			con = DBConnPool.getConnection();
		}

		try
		{
			pstmt = con.prepareStatement("DELETE FROM IEGrpInsuredInfo WHERE  TransType = ? AND TransCode = ? AND TNodeCode = ?");
			if(this.getTransType() == null || this.getTransType().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getTransType().trim());
			}
			if(this.getTransCode() == null || this.getTransCode().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getTransCode().trim());
			}
			if(this.getTNodeCode() == null || this.getTNodeCode().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getTNodeCode().trim());
			}
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			ex.printStackTrace();
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "IEGrpInsuredInfoDB";
			tError.functionName = "delete()";
			tError.errorMessage = ex.toString();
			this.mErrors .addOneError(tError);

		// only for debug purpose
		SQLString sqlObj = new SQLString("IEGrpInsuredInfo");
		sqlObj.setSQL(4, this);
		sqlObj.getSQL();

			try {
				pstmt.close();
			} catch (Exception e){}

			if( !mflag ) {
				try {
					con.close();
				} catch (Exception e){}
			}

			return false;
		}

		if( !mflag ) {
			try {
				con.close();
			} catch (Exception e){}
		}

		return true;
	}

	public boolean update()
	{
		PreparedStatement pstmt = null;

		if( !mflag ) {
			con = DBConnPool.getConnection();
		}


		try
		{
			pstmt = con.prepareStatement("UPDATE IEGrpInsuredInfo SET  TransType = ? , TransCode = ? , TNodeCode = ? , TParentNodeCode = ? , CutomerSequenceNo = ? , CustomerNo = ? , OccupationCode = ? , InsuredEffectiveDate = ? , InsuredExpireDate = ? , InsuredType = ? , LivingAddress = ? , Address = ? , HouseHoldAddress = ? , PostalCode = ? , PhoneNo = ? , Email = ? , HomePhone = ? , Mobile = ? , Operator = ? , MakeDate = ? , MakeTime = ? , ModifyDate = ? , ModifyTime = ? , ContNo = ? , InsuredNo = ? , EdorNo = ? WHERE  TransType = ? AND TransCode = ? AND TNodeCode = ?");
			if(this.getTransType() == null || this.getTransType().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getTransType().trim());
			}
			if(this.getTransCode() == null || this.getTransCode().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getTransCode().trim());
			}
			if(this.getTNodeCode() == null || this.getTNodeCode().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getTNodeCode().trim());
			}
			if(this.getTParentNodeCode() == null || this.getTParentNodeCode().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getTParentNodeCode().trim());
			}
			if(this.getCutomerSequenceNo() == null || this.getCutomerSequenceNo().equals("null")) {
				pstmt.setNull(5, 12);
			} else {
				pstmt.setString(5, this.getCutomerSequenceNo().trim());
			}
			if(this.getCustomerNo() == null || this.getCustomerNo().equals("null")) {
				pstmt.setNull(6, 12);
			} else {
				pstmt.setString(6, this.getCustomerNo().trim());
			}
			if(this.getOccupationCode() == null || this.getOccupationCode().equals("null")) {
				pstmt.setNull(7, 12);
			} else {
				pstmt.setString(7, this.getOccupationCode().trim());
			}
			if(this.getInsuredEffectiveDate() == null || this.getInsuredEffectiveDate().equals("null")) {
				pstmt.setNull(8, 91);
			} else {
				pstmt.setDate(8, Date.valueOf(this.getInsuredEffectiveDate()));
			}
			if(this.getInsuredExpireDate() == null || this.getInsuredExpireDate().equals("null")) {
				pstmt.setNull(9, 91);
			} else {
				pstmt.setDate(9, Date.valueOf(this.getInsuredExpireDate()));
			}
			if(this.getInsuredType() == null || this.getInsuredType().equals("null")) {
				pstmt.setNull(10, 12);
			} else {
				pstmt.setString(10, this.getInsuredType().trim());
			}
			if(this.getLivingAddress() == null || this.getLivingAddress().equals("null")) {
				pstmt.setNull(11, 12);
			} else {
				pstmt.setString(11, this.getLivingAddress().trim());
			}
			if(this.getAddress() == null || this.getAddress().equals("null")) {
				pstmt.setNull(12, 12);
			} else {
				pstmt.setString(12, this.getAddress().trim());
			}
			if(this.getHouseHoldAddress() == null || this.getHouseHoldAddress().equals("null")) {
				pstmt.setNull(13, 12);
			} else {
				pstmt.setString(13, this.getHouseHoldAddress().trim());
			}
			if(this.getPostalCode() == null || this.getPostalCode().equals("null")) {
				pstmt.setNull(14, 12);
			} else {
				pstmt.setString(14, this.getPostalCode().trim());
			}
			if(this.getPhoneNo() == null || this.getPhoneNo().equals("null")) {
				pstmt.setNull(15, 12);
			} else {
				pstmt.setString(15, this.getPhoneNo().trim());
			}
			if(this.getEmail() == null || this.getEmail().equals("null")) {
				pstmt.setNull(16, 12);
			} else {
				pstmt.setString(16, this.getEmail().trim());
			}
			if(this.getHomePhone() == null || this.getHomePhone().equals("null")) {
				pstmt.setNull(17, 12);
			} else {
				pstmt.setString(17, this.getHomePhone().trim());
			}
			if(this.getMobile() == null || this.getMobile().equals("null")) {
				pstmt.setNull(18, 12);
			} else {
				pstmt.setString(18, this.getMobile().trim());
			}
			if(this.getOperator() == null || this.getOperator().equals("null")) {
				pstmt.setNull(19, 12);
			} else {
				pstmt.setString(19, this.getOperator().trim());
			}
			if(this.getMakeDate() == null || this.getMakeDate().equals("null")) {
				pstmt.setNull(20, 91);
			} else {
				pstmt.setDate(20, Date.valueOf(this.getMakeDate()));
			}
			if(this.getMakeTime() == null || this.getMakeTime().equals("null")) {
				pstmt.setNull(21, 12);
			} else {
				pstmt.setString(21, this.getMakeTime().trim());
			}
			if(this.getModifyDate() == null || this.getModifyDate().equals("null")) {
				pstmt.setNull(22, 91);
			} else {
				pstmt.setDate(22, Date.valueOf(this.getModifyDate()));
			}
			if(this.getModifyTime() == null || this.getModifyTime().equals("null")) {
				pstmt.setNull(23, 12);
			} else {
				pstmt.setString(23, this.getModifyTime().trim());
			}
			if(this.getContNo() == null || this.getContNo().equals("null")) {
				pstmt.setNull(24, 12);
			} else {
				pstmt.setString(24, this.getContNo().trim());
			}
			if(this.getInsuredNo() == null || this.getInsuredNo().equals("null")) {
				pstmt.setNull(25, 12);
			} else {
				pstmt.setString(25, this.getInsuredNo().trim());
			}
			if(this.getEdorNo() == null || this.getEdorNo().equals("null")) {
				pstmt.setNull(26, 12);
			} else {
				pstmt.setString(26, this.getEdorNo().trim());
			}
			// set where condition
			if(this.getTransType() == null || this.getTransType().equals("null")) {
				pstmt.setNull(27, 12);
			} else {
				pstmt.setString(27, this.getTransType().trim());
			}
			if(this.getTransCode() == null || this.getTransCode().equals("null")) {
				pstmt.setNull(28, 12);
			} else {
				pstmt.setString(28, this.getTransCode().trim());
			}
			if(this.getTNodeCode() == null || this.getTNodeCode().equals("null")) {
				pstmt.setNull(29, 12);
			} else {
				pstmt.setString(29, this.getTNodeCode().trim());
			}
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			ex.printStackTrace();
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "IEGrpInsuredInfoDB";
			tError.functionName = "update()";
			tError.errorMessage = ex.toString();
			this.mErrors .addOneError(tError);

		// only for debug purpose
		SQLString sqlObj = new SQLString("IEGrpInsuredInfo");
		sqlObj.setSQL(2, this);
		sqlObj.getSQL();

			try {
				pstmt.close();
			} catch (Exception e){}

			if( !mflag ) {
				try {
					con.close();
				} catch (Exception e){}
			}

			return false;
		}

		if( !mflag ) {
			try {
				con.close();
			} catch (Exception e){}
		}

		return true;
	}

	public boolean insert()
	{
		PreparedStatement pstmt = null;

		if( !mflag ) {
			con = DBConnPool.getConnection();
		}


		try
		{
			pstmt = con.prepareStatement("INSERT INTO IEGrpInsuredInfo VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
			if(this.getTransType() == null || this.getTransType().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getTransType().trim());
			}
			if(this.getTransCode() == null || this.getTransCode().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getTransCode().trim());
			}
			if(this.getTNodeCode() == null || this.getTNodeCode().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getTNodeCode().trim());
			}
			if(this.getTParentNodeCode() == null || this.getTParentNodeCode().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getTParentNodeCode().trim());
			}
			if(this.getCutomerSequenceNo() == null || this.getCutomerSequenceNo().equals("null")) {
				pstmt.setNull(5, 12);
			} else {
				pstmt.setString(5, this.getCutomerSequenceNo().trim());
			}
			if(this.getCustomerNo() == null || this.getCustomerNo().equals("null")) {
				pstmt.setNull(6, 12);
			} else {
				pstmt.setString(6, this.getCustomerNo().trim());
			}
			if(this.getOccupationCode() == null || this.getOccupationCode().equals("null")) {
				pstmt.setNull(7, 12);
			} else {
				pstmt.setString(7, this.getOccupationCode().trim());
			}
			if(this.getInsuredEffectiveDate() == null || this.getInsuredEffectiveDate().equals("null")) {
				pstmt.setNull(8, 91);
			} else {
				pstmt.setDate(8, Date.valueOf(this.getInsuredEffectiveDate()));
			}
			if(this.getInsuredExpireDate() == null || this.getInsuredExpireDate().equals("null")) {
				pstmt.setNull(9, 91);
			} else {
				pstmt.setDate(9, Date.valueOf(this.getInsuredExpireDate()));
			}
			if(this.getInsuredType() == null || this.getInsuredType().equals("null")) {
				pstmt.setNull(10, 12);
			} else {
				pstmt.setString(10, this.getInsuredType().trim());
			}
			if(this.getLivingAddress() == null || this.getLivingAddress().equals("null")) {
				pstmt.setNull(11, 12);
			} else {
				pstmt.setString(11, this.getLivingAddress().trim());
			}
			if(this.getAddress() == null || this.getAddress().equals("null")) {
				pstmt.setNull(12, 12);
			} else {
				pstmt.setString(12, this.getAddress().trim());
			}
			if(this.getHouseHoldAddress() == null || this.getHouseHoldAddress().equals("null")) {
				pstmt.setNull(13, 12);
			} else {
				pstmt.setString(13, this.getHouseHoldAddress().trim());
			}
			if(this.getPostalCode() == null || this.getPostalCode().equals("null")) {
				pstmt.setNull(14, 12);
			} else {
				pstmt.setString(14, this.getPostalCode().trim());
			}
			if(this.getPhoneNo() == null || this.getPhoneNo().equals("null")) {
				pstmt.setNull(15, 12);
			} else {
				pstmt.setString(15, this.getPhoneNo().trim());
			}
			if(this.getEmail() == null || this.getEmail().equals("null")) {
				pstmt.setNull(16, 12);
			} else {
				pstmt.setString(16, this.getEmail().trim());
			}
			if(this.getHomePhone() == null || this.getHomePhone().equals("null")) {
				pstmt.setNull(17, 12);
			} else {
				pstmt.setString(17, this.getHomePhone().trim());
			}
			if(this.getMobile() == null || this.getMobile().equals("null")) {
				pstmt.setNull(18, 12);
			} else {
				pstmt.setString(18, this.getMobile().trim());
			}
			if(this.getOperator() == null || this.getOperator().equals("null")) {
				pstmt.setNull(19, 12);
			} else {
				pstmt.setString(19, this.getOperator().trim());
			}
			if(this.getMakeDate() == null || this.getMakeDate().equals("null")) {
				pstmt.setNull(20, 91);
			} else {
				pstmt.setDate(20, Date.valueOf(this.getMakeDate()));
			}
			if(this.getMakeTime() == null || this.getMakeTime().equals("null")) {
				pstmt.setNull(21, 12);
			} else {
				pstmt.setString(21, this.getMakeTime().trim());
			}
			if(this.getModifyDate() == null || this.getModifyDate().equals("null")) {
				pstmt.setNull(22, 91);
			} else {
				pstmt.setDate(22, Date.valueOf(this.getModifyDate()));
			}
			if(this.getModifyTime() == null || this.getModifyTime().equals("null")) {
				pstmt.setNull(23, 12);
			} else {
				pstmt.setString(23, this.getModifyTime().trim());
			}
			if(this.getContNo() == null || this.getContNo().equals("null")) {
				pstmt.setNull(24, 12);
			} else {
				pstmt.setString(24, this.getContNo().trim());
			}
			if(this.getInsuredNo() == null || this.getInsuredNo().equals("null")) {
				pstmt.setNull(25, 12);
			} else {
				pstmt.setString(25, this.getInsuredNo().trim());
			}
			if(this.getEdorNo() == null || this.getEdorNo().equals("null")) {
				pstmt.setNull(26, 12);
			} else {
				pstmt.setString(26, this.getEdorNo().trim());
			}
			// execute sql
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			ex.printStackTrace();
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "IEGrpInsuredInfoDB";
			tError.functionName = "insert()";
			tError.errorMessage = ex.toString();
			this.mErrors .addOneError(tError);

		// only for debug purpose
		SQLString sqlObj = new SQLString("IEGrpInsuredInfo");
		sqlObj.setSQL(1, this);
		sqlObj.getSQL();

			try {
				pstmt.close();
			} catch (Exception e){}

			if( !mflag ) {
				try {
					con.close();
				} catch (Exception e){}
			}

			return false;
		}

		if( !mflag ) {
			try {
				con.close();
			} catch (Exception e){}
		}

		return true;
	}

	public boolean getInfo()
	{
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		if( !mflag ) {
			con = DBConnPool.getConnection();
		}

		try
		{
			pstmt = con.prepareStatement("SELECT * FROM IEGrpInsuredInfo WHERE  TransType = ? AND TransCode = ? AND TNodeCode = ?", 
				ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
			if(this.getTransType() == null || this.getTransType().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getTransType().trim());
			}
			if(this.getTransCode() == null || this.getTransCode().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getTransCode().trim());
			}
			if(this.getTNodeCode() == null || this.getTNodeCode().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getTNodeCode().trim());
			}
			rs = pstmt.executeQuery();
			int i = 0;
			while (rs.next())
			{
				i++;
				if (!this.setSchema(rs,i))
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "IEGrpInsuredInfoDB";
					tError.functionName = "getInfo";
					tError.errorMessage = "取数失败!";
					this.mErrors .addOneError(tError);

					try{ rs.close(); } catch( Exception ex ) {}
					try{ pstmt.close(); } catch( Exception ex1 ) {}

					if (!mflag)
					{
						try
						{
							con.close();
						}
						catch(Exception et){}
					}
					return false;
				}
				break;
			}
			try{ rs.close(); } catch( Exception ex2 ) {}
			try{ pstmt.close(); } catch( Exception ex3 ) {}

			if( i == 0 )
			{
				if (!mflag)
				{
					try
					{
						con.close();
					}
					catch(Exception et){}
				}
				return false;
			}
		}
		catch(Exception e)
	    {
			e.printStackTrace();
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "IEGrpInsuredInfoDB";
			tError.functionName = "getInfo";
			tError.errorMessage = e.toString();
			this.mErrors .addOneError(tError);

			try{ rs.close(); } catch( Exception ex ) {}
			try{ pstmt.close(); } catch( Exception ex1 ) {}

			if (!mflag)
			{
				try
				{
					con.close();
				}
				catch(Exception et){}
			}
			return false;
	    }
	    // 断开数据库连接
		if (!mflag)
		{
			try
			{
				con.close();
			}
			catch(Exception e){}
		}

		return true;
	}

	public IEGrpInsuredInfoSet query()
	{
		Statement stmt = null;
		ResultSet rs = null;
		IEGrpInsuredInfoSet aIEGrpInsuredInfoSet = new IEGrpInsuredInfoSet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			SQLString sqlObj = new SQLString("IEGrpInsuredInfo");
			IEGrpInsuredInfoSchema aSchema = this.getSchema();
			sqlObj.setSQL(5,aSchema);
			String sql = sqlObj.getSQL();
			stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
			rs = stmt.executeQuery(sql);
			int i = 0;
			while (rs.next())
			{
				i++;
				IEGrpInsuredInfoSchema s1 = new IEGrpInsuredInfoSchema();
				s1.setSchema(rs,i);
				aIEGrpInsuredInfoSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			e.printStackTrace();
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "IEGrpInsuredInfoDB";
			tError.functionName = "query";
			tError.errorMessage = e.toString();
			this.mErrors .addOneError(tError);

			try{ rs.close(); } catch( Exception ex2 ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}

			if (!mflag)
			{
				try
				{
					con.close();
				}
				catch(Exception et){}
			}
	    }

		if (!mflag)
		{
			try
			{
				con.close();
			}
			catch(Exception e){}
		}

		return aIEGrpInsuredInfoSet;
	}

	public IEGrpInsuredInfoSet executeQuery(String sql)
	{
		Statement stmt = null;
		ResultSet rs = null;
		IEGrpInsuredInfoSet aIEGrpInsuredInfoSet = new IEGrpInsuredInfoSet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);

			rs = stmt.executeQuery(StrTool.GBKToUnicode(sql));
			int i = 0;
			while (rs.next())
			{
				i++;
				IEGrpInsuredInfoSchema s1 = new IEGrpInsuredInfoSchema();
				if (!s1.setSchema(rs,i))
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "IEGrpInsuredInfoDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aIEGrpInsuredInfoSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			e.printStackTrace();
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "IEGrpInsuredInfoDB";
			tError.functionName = "executeQuery";
			tError.errorMessage = e.toString();
			this.mErrors .addOneError(tError);

			try{ rs.close(); } catch( Exception ex2 ) {}
			try{ stmt.close(); } catch( Exception ex3 ) {}

			if (!mflag)
			{
				try
				{
					con.close();
				}
				catch(Exception et){}
			}
	    }

		if (!mflag)
		{
			try
			{
				con.close();
			}
			catch(Exception e){}
		}

		return aIEGrpInsuredInfoSet;
	}

	public IEGrpInsuredInfoSet query(int nStart, int nCount)
	{
		Statement stmt = null;
		ResultSet rs = null;
		IEGrpInsuredInfoSet aIEGrpInsuredInfoSet = new IEGrpInsuredInfoSet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			SQLString sqlObj = new SQLString("IEGrpInsuredInfo");
			IEGrpInsuredInfoSchema aSchema = this.getSchema();
			sqlObj.setSQL(5,aSchema);
			String sql = sqlObj.getSQL();
			stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
			rs = stmt.executeQuery(sql);
			int i = 0;
			while (rs.next())
			{
				i++;

				if( i < nStart ) {
					continue;
				}

				if( i >= nStart + nCount ) {
					break;
				}

				IEGrpInsuredInfoSchema s1 = new IEGrpInsuredInfoSchema();
				s1.setSchema(rs,i);
				aIEGrpInsuredInfoSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			e.printStackTrace();
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "IEGrpInsuredInfoDB";
			tError.functionName = "query";
			tError.errorMessage = e.toString();
			this.mErrors .addOneError(tError);

			try{ rs.close(); } catch( Exception ex2 ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}

			if (!mflag)
			{
				try
				{
					con.close();
				}
				catch(Exception et){}
			}
	    }

		if (!mflag)
		{
			try
			{
				con.close();
			}
			catch(Exception e){}
		}

		return aIEGrpInsuredInfoSet;
	}

	public IEGrpInsuredInfoSet executeQuery(String sql, int nStart, int nCount)
	{
		Statement stmt = null;
		ResultSet rs = null;
		IEGrpInsuredInfoSet aIEGrpInsuredInfoSet = new IEGrpInsuredInfoSet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);

			rs = stmt.executeQuery(StrTool.GBKToUnicode(sql));
			int i = 0;
			while (rs.next())
			{
				i++;

				if( i < nStart ) {
					continue;
				}

				if( i >= nStart + nCount ) {
					break;
				}

				IEGrpInsuredInfoSchema s1 = new IEGrpInsuredInfoSchema();
				if (!s1.setSchema(rs,i))
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "IEGrpInsuredInfoDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aIEGrpInsuredInfoSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			e.printStackTrace();
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "IEGrpInsuredInfoDB";
			tError.functionName = "executeQuery";
			tError.errorMessage = e.toString();
			this.mErrors .addOneError(tError);

			try{ rs.close(); } catch( Exception ex2 ) {}
			try{ stmt.close(); } catch( Exception ex3 ) {}

			if (!mflag)
			{
				try
				{
					con.close();
				}
				catch(Exception et){}
			}
	    }

		if (!mflag)
		{
			try
			{
				con.close();
			}
			catch(Exception e){}
		}

		return aIEGrpInsuredInfoSet;
	}

	public boolean update(String strWherePart)
	{
		Statement stmt = null;

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
			SQLString sqlObj = new SQLString("IEGrpInsuredInfo");
			IEGrpInsuredInfoSchema aSchema = this.getSchema();
			sqlObj.setSQL(2,aSchema);
			String sql = "update IEGrpInsuredInfo " + sqlObj.getUpdPart() + " where " + strWherePart;

			int operCount = stmt.executeUpdate(sql);
			if( operCount == 0 )
			{
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "IEGrpInsuredInfoDB";
				tError.functionName = "update";
				tError.errorMessage = "更新数据失败!";
				this.mErrors .addOneError(tError);

				if (!mflag)
				{
					try
					{
						con.close();
					}
					catch(Exception et){}
				}
				return false;
			}
		}
		catch(Exception e)
	    {
			e.printStackTrace();
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "IEGrpInsuredInfoDB";
			tError.functionName = "update";
			tError.errorMessage = e.toString();
			this.mErrors .addOneError(tError);

			try{ stmt.close(); } catch( Exception ex1 ) {}

			if (!mflag)
			{
				try
				{
					con.close();
				}
				catch(Exception et){}
			}
			return false;
	    }
	    // 断开数据库连接
		if (!mflag)
		{
			try
			{
				con.close();
			}
			catch(Exception e){}
		}

		return true;
	}

/**
 * 准备数据查询条件
 * @param strSQL String
 * @return boolean
 */
public boolean prepareData(String strSQL)
{
    if (mResultSet != null)
    {
        // @@错误处理
        CError tError = new CError();
        tError.moduleName = "IEGrpInsuredInfoDB";
        tError.functionName = "prepareData";
        tError.errorMessage = "数据集非空，程序在准备数据集之后，没有关闭！";
        this.mErrors.addOneError(tError);
        return false;
    }

    if (!mflag)
    {
        con = DBConnPool.getConnection();
    }
    try
    {
        mStatement = con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
        mResultSet = mStatement.executeQuery(StrTool.GBKToUnicode(strSQL));
    }
    catch (Exception e)
    {
        // @@错误处理
        CError tError = new CError();
        tError.moduleName = "IEGrpInsuredInfoDB";
        tError.functionName = "prepareData";
        tError.errorMessage = e.toString();
        this.mErrors.addOneError(tError);
        try
        {
            mResultSet.close();
        }
        catch (Exception ex2)
        {}
        try
        {
            mStatement.close();
        }
        catch (Exception ex3)
        {}
        if (!mflag)
        {
            try
            {
                con.close();
            }
            catch (Exception et)
            {}
        }
        return false;
    }

    if (!mflag)
    {
        try
        {
            con.close();
        }
        catch (Exception e)
        {}
    }
    return true;
}

/**
 * 获取数据集
 * @return boolean
 */
public boolean hasMoreData()
{
    boolean flag = true;
    if (null == mResultSet)
    {
        CError tError = new CError();
        tError.moduleName = "IEGrpInsuredInfoDB";
        tError.functionName = "hasMoreData";
        tError.errorMessage = "数据集为空，请先准备数据集！";
        this.mErrors.addOneError(tError);
        return false;
    }
    try
    {
        flag = mResultSet.next();
    }
    catch (Exception ex)
    {
        CError tError = new CError();
        tError.moduleName = "IEGrpInsuredInfoDB";
        tError.functionName = "hasMoreData";
        tError.errorMessage = ex.toString();
        this.mErrors.addOneError(tError);
        try
        {
            mResultSet.close();
            mResultSet = null;
        }
        catch (Exception ex2)
        {}
        try
        {
            mStatement.close();
            mStatement = null;
        }
        catch (Exception ex3)
        {}
        if (!mflag)
        {
            try
            {
                con.close();
            }
            catch (Exception et)
            {}
        }
        return false;
    }
    return flag;
}
/**
 * 获取定量数据
 * @return IEGrpInsuredInfoSet
 */
public IEGrpInsuredInfoSet getData()
{
    int tCount = 0;
    IEGrpInsuredInfoSet tIEGrpInsuredInfoSet = new IEGrpInsuredInfoSet();
    IEGrpInsuredInfoSchema tIEGrpInsuredInfoSchema = null;
    if (null == mResultSet)
    {
        CError tError = new CError();
        tError.moduleName = "IEGrpInsuredInfoDB";
        tError.functionName = "getData";
        tError.errorMessage = "数据集为空，请先准备数据集！";
        this.mErrors.addOneError(tError);
        return null;
    }
    try
    {
        tCount = 1;
        tIEGrpInsuredInfoSchema = new IEGrpInsuredInfoSchema();
        tIEGrpInsuredInfoSchema.setSchema(mResultSet, 1);
        tIEGrpInsuredInfoSet.add(tIEGrpInsuredInfoSchema);
        //注意mResultSet.next()的作用
        while (tCount++ < SysConst.FETCHCOUNT)
        {
            if (mResultSet.next())
            {
                tIEGrpInsuredInfoSchema = new IEGrpInsuredInfoSchema();
                tIEGrpInsuredInfoSchema.setSchema(mResultSet, 1);
                tIEGrpInsuredInfoSet.add(tIEGrpInsuredInfoSchema);
            }
        }
    }
    catch (Exception ex)
    {
        CError tError = new CError();
        tError.moduleName = "IEGrpInsuredInfoDB";
        tError.functionName = "getData";
        tError.errorMessage = ex.toString();
        this.mErrors.addOneError(tError);
        try
        {
            mResultSet.close();
            mResultSet = null;
        }
        catch (Exception ex2)
        {}
        try
        {
            mStatement.close();
            mStatement = null;
        }
        catch (Exception ex3)
        {}
        if (!mflag)
        {
            try
            {
                con.close();
            }
            catch (Exception et)
            {}
        }
        return null;
    }
    return tIEGrpInsuredInfoSet;
}
/**
 * 关闭数据集
 * @return boolean
 */
public boolean closeData()
{
    boolean flag = true;
    try
    {
        if (null == mResultSet)
        {
            CError tError = new CError();
            tError.moduleName = "IEGrpInsuredInfoDB";
            tError.functionName = "closeData";
            tError.errorMessage = "数据集已经关闭了！";
            this.mErrors.addOneError(tError);
            flag = false;
        }
        else
        {
            mResultSet.close();
            mResultSet = null;
        }
    }
    catch (Exception ex2)
    {
        CError tError = new CError();
        tError.moduleName = "IEGrpInsuredInfoDB";
        tError.functionName = "closeData";
        tError.errorMessage = ex2.toString();
        this.mErrors.addOneError(tError);
        flag = false;
    }
    try
    {
        if (null == mStatement)
        {
            CError tError = new CError();
            tError.moduleName = "IEGrpInsuredInfoDB";
            tError.functionName = "closeData";
            tError.errorMessage = "语句已经关闭了！";
            this.mErrors.addOneError(tError);
            flag = false;
        }
        else
        {
            mStatement.close();
            mStatement = null;
        }
    }
    catch (Exception ex3)
    {
        CError tError = new CError();
        tError.moduleName = "IEGrpInsuredInfoDB";
        tError.functionName = "closeData";
        tError.errorMessage = ex3.toString();
        this.mErrors.addOneError(tError);
        flag = false;
    }
    return flag;
}
}
