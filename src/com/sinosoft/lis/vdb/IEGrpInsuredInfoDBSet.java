/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.vdb;

import java.sql.*;
import com.sinosoft.lis.vschema.IEGrpInsuredInfoSet;
import com.sinosoft.utility.*;

/**
 * <p>ClassName: IEGrpInsuredInfoDBSet </p>
 * <p>Description: DB层多记录数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: PhysicalDataModel_2
 * @CreateDate：2017-04-21
 */
public class IEGrpInsuredInfoDBSet extends IEGrpInsuredInfoSet
{
	// @Field
	private Connection con;
	private DBOper db;
	/**
	* flag = true: 传入Connection
	* flag = false: 不传入Connection
	**/
	private boolean mflag = false;


	// @Constructor
	public IEGrpInsuredInfoDBSet(Connection tConnection)
	{
		con = tConnection;
		db = new DBOper(con,"IEGrpInsuredInfo");
		mflag = true;
	}

	public IEGrpInsuredInfoDBSet()
	{
		db = new DBOper( "IEGrpInsuredInfo" );
		con = null;
		mflag = false;
	}
	// @Method
	public boolean deleteSQL()
	{
		if (db.deleteSQL(this))
		{
		        return true;
		}
		else
		{
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "IEGrpInsuredInfoDBSet";
			tError.functionName = "deleteSQL";
			tError.errorMessage = "操作失败!";
			this.mErrors .addOneError(tError);
			return false;
		}
	}

    /**
     * 删除操作
     * 删除条件：主键
     * @return boolean
     */
	public boolean delete()
	{
		PreparedStatement pstmt = null;

		if( !mflag ) {
			con = DBConnPool.getConnection();
		}

		try
		{
            int tCount = this.size();
			pstmt = con.prepareStatement("DELETE FROM IEGrpInsuredInfo WHERE  TransType = ? AND TransCode = ? AND TNodeCode = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getTransType() == null || this.get(i).getTransType().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getTransType().trim());
			}
			if(this.get(i).getTransCode() == null || this.get(i).getTransCode().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getTransCode().trim());
			}
			if(this.get(i).getTNodeCode() == null || this.get(i).getTNodeCode().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getTNodeCode().trim());
			}
                pstmt.addBatch();
            }
            pstmt.executeBatch();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			ex.printStackTrace();
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "IEGrpInsuredInfoDBSet";
			tError.functionName = "delete()";
			tError.errorMessage = ex.toString();
			this.mErrors .addOneError(tError);

            int tCount = this.size();
            for (int i = 1; i <= tCount; i++)
            {
		// only for debug purpose
		SQLString sqlObj = new SQLString("IEGrpInsuredInfo");
		sqlObj.setSQL(4, this.get(i));
		sqlObj.getSQL();
            }

			try {
				pstmt.close();
			} catch (Exception e){e.printStackTrace();}

			if( !mflag ) {
				try {
					con.close();
				} catch (Exception e){e.printStackTrace();}
			}

			return false;
		}

		if( !mflag ) {
			try {
				con.close();
			} catch (Exception e){e.printStackTrace();}
		}

		return true;
	}

    /**
     * 更新操作
     * 更新条件：主键
     * @return boolean
     */
	public boolean update()
	{
		PreparedStatement pstmt = null;

		if( !mflag ) {
			con = DBConnPool.getConnection();
		}

		try
		{
            int tCount = this.size();
			pstmt = con.prepareStatement("UPDATE IEGrpInsuredInfo SET  TransType = ? , TransCode = ? , TNodeCode = ? , TParentNodeCode = ? , CutomerSequenceNo = ? , CustomerNo = ? , OccupationCode = ? , InsuredEffectiveDate = ? , InsuredExpireDate = ? , InsuredType = ? , LivingAddress = ? , Address = ? , HouseHoldAddress = ? , PostalCode = ? , PhoneNo = ? , Email = ? , HomePhone = ? , Mobile = ? , Operator = ? , MakeDate = ? , MakeTime = ? , ModifyDate = ? , ModifyTime = ? , ContNo = ? , InsuredNo = ? , EdorNo = ? WHERE  TransType = ? AND TransCode = ? AND TNodeCode = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getTransType() == null || this.get(i).getTransType().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getTransType().trim());
			}
			if(this.get(i).getTransCode() == null || this.get(i).getTransCode().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getTransCode().trim());
			}
			if(this.get(i).getTNodeCode() == null || this.get(i).getTNodeCode().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getTNodeCode().trim());
			}
			if(this.get(i).getTParentNodeCode() == null || this.get(i).getTParentNodeCode().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getTParentNodeCode().trim());
			}
			if(this.get(i).getCutomerSequenceNo() == null || this.get(i).getCutomerSequenceNo().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getCutomerSequenceNo().trim());
			}
			if(this.get(i).getCustomerNo() == null || this.get(i).getCustomerNo().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getCustomerNo().trim());
			}
			if(this.get(i).getOccupationCode() == null || this.get(i).getOccupationCode().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getOccupationCode().trim());
			}
			if(this.get(i).getInsuredEffectiveDate() == null || this.get(i).getInsuredEffectiveDate().equals("null")) {
				pstmt.setDate(8,null);
			} else {
				pstmt.setDate(8, Date.valueOf(this.get(i).getInsuredEffectiveDate()));
			}
			if(this.get(i).getInsuredExpireDate() == null || this.get(i).getInsuredExpireDate().equals("null")) {
				pstmt.setDate(9,null);
			} else {
				pstmt.setDate(9, Date.valueOf(this.get(i).getInsuredExpireDate()));
			}
			if(this.get(i).getInsuredType() == null || this.get(i).getInsuredType().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getInsuredType().trim());
			}
			if(this.get(i).getLivingAddress() == null || this.get(i).getLivingAddress().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getLivingAddress().trim());
			}
			if(this.get(i).getAddress() == null || this.get(i).getAddress().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, this.get(i).getAddress().trim());
			}
			if(this.get(i).getHouseHoldAddress() == null || this.get(i).getHouseHoldAddress().equals("null")) {
				pstmt.setString(13,null);
			} else {
				pstmt.setString(13, this.get(i).getHouseHoldAddress().trim());
			}
			if(this.get(i).getPostalCode() == null || this.get(i).getPostalCode().equals("null")) {
				pstmt.setString(14,null);
			} else {
				pstmt.setString(14, this.get(i).getPostalCode().trim());
			}
			if(this.get(i).getPhoneNo() == null || this.get(i).getPhoneNo().equals("null")) {
				pstmt.setString(15,null);
			} else {
				pstmt.setString(15, this.get(i).getPhoneNo().trim());
			}
			if(this.get(i).getEmail() == null || this.get(i).getEmail().equals("null")) {
				pstmt.setString(16,null);
			} else {
				pstmt.setString(16, this.get(i).getEmail().trim());
			}
			if(this.get(i).getHomePhone() == null || this.get(i).getHomePhone().equals("null")) {
				pstmt.setString(17,null);
			} else {
				pstmt.setString(17, this.get(i).getHomePhone().trim());
			}
			if(this.get(i).getMobile() == null || this.get(i).getMobile().equals("null")) {
				pstmt.setString(18,null);
			} else {
				pstmt.setString(18, this.get(i).getMobile().trim());
			}
			if(this.get(i).getOperator() == null || this.get(i).getOperator().equals("null")) {
				pstmt.setString(19,null);
			} else {
				pstmt.setString(19, this.get(i).getOperator().trim());
			}
			if(this.get(i).getMakeDate() == null || this.get(i).getMakeDate().equals("null")) {
				pstmt.setDate(20,null);
			} else {
				pstmt.setDate(20, Date.valueOf(this.get(i).getMakeDate()));
			}
			if(this.get(i).getMakeTime() == null || this.get(i).getMakeTime().equals("null")) {
				pstmt.setString(21,null);
			} else {
				pstmt.setString(21, this.get(i).getMakeTime().trim());
			}
			if(this.get(i).getModifyDate() == null || this.get(i).getModifyDate().equals("null")) {
				pstmt.setDate(22,null);
			} else {
				pstmt.setDate(22, Date.valueOf(this.get(i).getModifyDate()));
			}
			if(this.get(i).getModifyTime() == null || this.get(i).getModifyTime().equals("null")) {
				pstmt.setString(23,null);
			} else {
				pstmt.setString(23, this.get(i).getModifyTime().trim());
			}
			if(this.get(i).getContNo() == null || this.get(i).getContNo().equals("null")) {
				pstmt.setString(24,null);
			} else {
				pstmt.setString(24, this.get(i).getContNo().trim());
			}
			if(this.get(i).getInsuredNo() == null || this.get(i).getInsuredNo().equals("null")) {
				pstmt.setString(25,null);
			} else {
				pstmt.setString(25, this.get(i).getInsuredNo().trim());
			}
			if(this.get(i).getEdorNo() == null || this.get(i).getEdorNo().equals("null")) {
				pstmt.setString(26,null);
			} else {
				pstmt.setString(26, this.get(i).getEdorNo().trim());
			}
			// set where condition
			if(this.get(i).getTransType() == null || this.get(i).getTransType().equals("null")) {
				pstmt.setString(27,null);
			} else {
				pstmt.setString(27, this.get(i).getTransType().trim());
			}
			if(this.get(i).getTransCode() == null || this.get(i).getTransCode().equals("null")) {
				pstmt.setString(28,null);
			} else {
				pstmt.setString(28, this.get(i).getTransCode().trim());
			}
			if(this.get(i).getTNodeCode() == null || this.get(i).getTNodeCode().equals("null")) {
				pstmt.setString(29,null);
			} else {
				pstmt.setString(29, this.get(i).getTNodeCode().trim());
			}
                pstmt.addBatch();
            }
            pstmt.executeBatch();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			ex.printStackTrace();
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "IEGrpInsuredInfoDBSet";
			tError.functionName = "update()";
			tError.errorMessage = ex.toString();
			this.mErrors .addOneError(tError);

            int tCount = this.size();
            for (int i = 1; i <= tCount; i++)
            {
		// only for debug purpose
		SQLString sqlObj = new SQLString("IEGrpInsuredInfo");
		sqlObj.setSQL(2, this.get(i));
		sqlObj.getSQL();
            }

			try {
				pstmt.close();
			} catch (Exception e){e.printStackTrace();}

			if( !mflag ) {
				try {
					con.close();
				} catch (Exception e){e.printStackTrace();}
			}

			return false;
		}

		if( !mflag ) {
			try {
				con.close();
			} catch (Exception e){e.printStackTrace();}
		}

		return true;
	}

    /**
     * 新增操作
     * @return boolean
     */
	public boolean insert()
	{
		PreparedStatement pstmt = null;

		if( !mflag ) {
			con = DBConnPool.getConnection();
		}

		try
		{
            int tCount = this.size();
			pstmt = con.prepareStatement("INSERT INTO IEGrpInsuredInfo VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getTransType() == null || this.get(i).getTransType().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getTransType().trim());
			}
			if(this.get(i).getTransCode() == null || this.get(i).getTransCode().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getTransCode().trim());
			}
			if(this.get(i).getTNodeCode() == null || this.get(i).getTNodeCode().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getTNodeCode().trim());
			}
			if(this.get(i).getTParentNodeCode() == null || this.get(i).getTParentNodeCode().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getTParentNodeCode().trim());
			}
			if(this.get(i).getCutomerSequenceNo() == null || this.get(i).getCutomerSequenceNo().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getCutomerSequenceNo().trim());
			}
			if(this.get(i).getCustomerNo() == null || this.get(i).getCustomerNo().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getCustomerNo().trim());
			}
			if(this.get(i).getOccupationCode() == null || this.get(i).getOccupationCode().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getOccupationCode().trim());
			}
			if(this.get(i).getInsuredEffectiveDate() == null || this.get(i).getInsuredEffectiveDate().equals("null")) {
				pstmt.setDate(8,null);
			} else {
				pstmt.setDate(8, Date.valueOf(this.get(i).getInsuredEffectiveDate()));
			}
			if(this.get(i).getInsuredExpireDate() == null || this.get(i).getInsuredExpireDate().equals("null")) {
				pstmt.setDate(9,null);
			} else {
				pstmt.setDate(9, Date.valueOf(this.get(i).getInsuredExpireDate()));
			}
			if(this.get(i).getInsuredType() == null || this.get(i).getInsuredType().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getInsuredType().trim());
			}
			if(this.get(i).getLivingAddress() == null || this.get(i).getLivingAddress().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getLivingAddress().trim());
			}
			if(this.get(i).getAddress() == null || this.get(i).getAddress().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, this.get(i).getAddress().trim());
			}
			if(this.get(i).getHouseHoldAddress() == null || this.get(i).getHouseHoldAddress().equals("null")) {
				pstmt.setString(13,null);
			} else {
				pstmt.setString(13, this.get(i).getHouseHoldAddress().trim());
			}
			if(this.get(i).getPostalCode() == null || this.get(i).getPostalCode().equals("null")) {
				pstmt.setString(14,null);
			} else {
				pstmt.setString(14, this.get(i).getPostalCode().trim());
			}
			if(this.get(i).getPhoneNo() == null || this.get(i).getPhoneNo().equals("null")) {
				pstmt.setString(15,null);
			} else {
				pstmt.setString(15, this.get(i).getPhoneNo().trim());
			}
			if(this.get(i).getEmail() == null || this.get(i).getEmail().equals("null")) {
				pstmt.setString(16,null);
			} else {
				pstmt.setString(16, this.get(i).getEmail().trim());
			}
			if(this.get(i).getHomePhone() == null || this.get(i).getHomePhone().equals("null")) {
				pstmt.setString(17,null);
			} else {
				pstmt.setString(17, this.get(i).getHomePhone().trim());
			}
			if(this.get(i).getMobile() == null || this.get(i).getMobile().equals("null")) {
				pstmt.setString(18,null);
			} else {
				pstmt.setString(18, this.get(i).getMobile().trim());
			}
			if(this.get(i).getOperator() == null || this.get(i).getOperator().equals("null")) {
				pstmt.setString(19,null);
			} else {
				pstmt.setString(19, this.get(i).getOperator().trim());
			}
			if(this.get(i).getMakeDate() == null || this.get(i).getMakeDate().equals("null")) {
				pstmt.setDate(20,null);
			} else {
				pstmt.setDate(20, Date.valueOf(this.get(i).getMakeDate()));
			}
			if(this.get(i).getMakeTime() == null || this.get(i).getMakeTime().equals("null")) {
				pstmt.setString(21,null);
			} else {
				pstmt.setString(21, this.get(i).getMakeTime().trim());
			}
			if(this.get(i).getModifyDate() == null || this.get(i).getModifyDate().equals("null")) {
				pstmt.setDate(22,null);
			} else {
				pstmt.setDate(22, Date.valueOf(this.get(i).getModifyDate()));
			}
			if(this.get(i).getModifyTime() == null || this.get(i).getModifyTime().equals("null")) {
				pstmt.setString(23,null);
			} else {
				pstmt.setString(23, this.get(i).getModifyTime().trim());
			}
			if(this.get(i).getContNo() == null || this.get(i).getContNo().equals("null")) {
				pstmt.setString(24,null);
			} else {
				pstmt.setString(24, this.get(i).getContNo().trim());
			}
			if(this.get(i).getInsuredNo() == null || this.get(i).getInsuredNo().equals("null")) {
				pstmt.setString(25,null);
			} else {
				pstmt.setString(25, this.get(i).getInsuredNo().trim());
			}
			if(this.get(i).getEdorNo() == null || this.get(i).getEdorNo().equals("null")) {
				pstmt.setString(26,null);
			} else {
				pstmt.setString(26, this.get(i).getEdorNo().trim());
			}
                pstmt.addBatch();
            }
            pstmt.executeBatch();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			ex.printStackTrace();
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "IEGrpInsuredInfoDBSet";
			tError.functionName = "insert()";
			tError.errorMessage = ex.toString();
			this.mErrors .addOneError(tError);

            int tCount = this.size();
            for (int i = 1; i <= tCount; i++)
            {
		// only for debug purpose
		SQLString sqlObj = new SQLString("IEGrpInsuredInfo");
		sqlObj.setSQL(1, this.get(i));
		sqlObj.getSQL();
            }

			try {
				pstmt.close();
			} catch (Exception e){e.printStackTrace();}

			if( !mflag ) {
				try {
					con.close();
				} catch (Exception e){e.printStackTrace();}
			}

			return false;
		}

		if( !mflag ) {
			try {
				con.close();
			} catch (Exception e){e.printStackTrace();}
		}

		return true;
	}

}
