/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.vdb;

import java.sql.*;
import com.sinosoft.lis.vschema.LCContOtherInfoSet;
import com.sinosoft.utility.*;

/**
 * <p>ClassName: LCCONTOTHERINFODBSet </p>
 * <p>Description: DB层多记录数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: PhysicalDataModel_1
 * @CreateDate：2018-05-02
 */
public class LCContOtherInfoDBSet extends LCContOtherInfoSet
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
	public LCContOtherInfoDBSet(Connection tConnection)
	{
		con = tConnection;
		db = new DBOper(con,"LCCONTOTHERINFO");
		mflag = true;
	}

	public LCContOtherInfoDBSet()
	{
		db = new DBOper( "LCCONTOTHERINFO" );
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
			tError.moduleName = "LCCONTOTHERINFODBSet";
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
			pstmt = con.prepareStatement("DELETE FROM LCCONTOTHERINFO WHERE ");
            for (int i = 1; i <= tCount; i++)
            {
                pstmt.addBatch();
            }
            pstmt.executeBatch();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			ex.printStackTrace();
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LCCONTOTHERINFODBSet";
			tError.functionName = "delete()";
			tError.errorMessage = ex.toString();
			this.mErrors .addOneError(tError);

            int tCount = this.size();
            for (int i = 1; i <= tCount; i++)
            {
		// only for debug purpose
		SQLString sqlObj = new SQLString("LCCONTOTHERINFO");
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
			pstmt = con.prepareStatement("UPDATE LCCONTOTHERINFO SET  prtno = ? , referrerrphone = ? , bak1 = ? , bak2 = ? , bak3 = ? , bak4 = ? , bak5 = ? , bak6 = ? , bak7 = ? , bak8 = ? , bak9 = ? , bak10 = ? , bak11 = ? , bak12 = ? , makedate = ? , maketime = ? , modifydate = ? , modifytime = ? , flag1 = ? , flag2 = ? , flag3 = ? , flag4 = ? WHERE ");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getprtno() == null || this.get(i).getprtno().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getprtno().trim());
			}
			if(this.get(i).getreferrerrphone() == null || this.get(i).getreferrerrphone().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getreferrerrphone().trim());
			}
			if(this.get(i).getbak1() == null || this.get(i).getbak1().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getbak1().trim());
			}
			if(this.get(i).getbak2() == null || this.get(i).getbak2().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getbak2().trim());
			}
			if(this.get(i).getbak3() == null || this.get(i).getbak3().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getbak3().trim());
			}
			if(this.get(i).getbak4() == null || this.get(i).getbak4().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getbak4().trim());
			}
			if(this.get(i).getbak5() == null || this.get(i).getbak5().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getbak5().trim());
			}
			if(this.get(i).getbak6() == null || this.get(i).getbak6().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getbak6().trim());
			}
			if(this.get(i).getbak7() == null || this.get(i).getbak7().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getbak7().trim());
			}
			if(this.get(i).getbak8() == null || this.get(i).getbak8().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getbak8().trim());
			}
			if(this.get(i).getbak9() == null || this.get(i).getbak9().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getbak9().trim());
			}
			if(this.get(i).getbak10() == null || this.get(i).getbak10().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, this.get(i).getbak10().trim());
			}
			if(this.get(i).getbak11() == null || this.get(i).getbak11().equals("null")) {
				pstmt.setDate(13,null);
			} else {
				pstmt.setDate(13, Date.valueOf(this.get(i).getbak11()));
			}
			if(this.get(i).getbak12() == null || this.get(i).getbak12().equals("null")) {
				pstmt.setDate(14,null);
			} else {
				pstmt.setDate(14, Date.valueOf(this.get(i).getbak12()));
			}
			if(this.get(i).getmakedate() == null || this.get(i).getmakedate().equals("null")) {
				pstmt.setDate(15,null);
			} else {
				pstmt.setDate(15, Date.valueOf(this.get(i).getmakedate()));
			}
			if(this.get(i).getmaketime() == null || this.get(i).getmaketime().equals("null")) {
				pstmt.setString(16,null);
			} else {
				pstmt.setString(16, this.get(i).getmaketime().trim());
			}
			if(this.get(i).getmodifydate() == null || this.get(i).getmodifydate().equals("null")) {
				pstmt.setDate(17,null);
			} else {
				pstmt.setDate(17, Date.valueOf(this.get(i).getmodifydate()));
			}
			if(this.get(i).getmodifytime() == null || this.get(i).getmodifytime().equals("null")) {
				pstmt.setString(18,null);
			} else {
				pstmt.setString(18, this.get(i).getmodifytime().trim());
			}
			if(this.get(i).getflag1() == null || this.get(i).getflag1().equals("null")) {
				pstmt.setString(19,null);
			} else {
				pstmt.setString(19, this.get(i).getflag1().trim());
			}
			if(this.get(i).getflag2() == null || this.get(i).getflag2().equals("null")) {
				pstmt.setString(20,null);
			} else {
				pstmt.setString(20, this.get(i).getflag2().trim());
			}
			if(this.get(i).getflag3() == null || this.get(i).getflag3().equals("null")) {
				pstmt.setString(21,null);
			} else {
				pstmt.setString(21, this.get(i).getflag3().trim());
			}
			if(this.get(i).getflag4() == null || this.get(i).getflag4().equals("null")) {
				pstmt.setString(22,null);
			} else {
				pstmt.setString(22, this.get(i).getflag4().trim());
			}
			// set where condition
                pstmt.addBatch();
            }
            pstmt.executeBatch();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			ex.printStackTrace();
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LCCONTOTHERINFODBSet";
			tError.functionName = "update()";
			tError.errorMessage = ex.toString();
			this.mErrors .addOneError(tError);

            int tCount = this.size();
            for (int i = 1; i <= tCount; i++)
            {
		// only for debug purpose
		SQLString sqlObj = new SQLString("LCCONTOTHERINFO");
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
			pstmt = con.prepareStatement("INSERT INTO LCCONTOTHERINFO VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getprtno() == null || this.get(i).getprtno().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getprtno().trim());
			}
			if(this.get(i).getreferrerrphone() == null || this.get(i).getreferrerrphone().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getreferrerrphone().trim());
			}
			if(this.get(i).getbak1() == null || this.get(i).getbak1().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getbak1().trim());
			}
			if(this.get(i).getbak2() == null || this.get(i).getbak2().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getbak2().trim());
			}
			if(this.get(i).getbak3() == null || this.get(i).getbak3().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getbak3().trim());
			}
			if(this.get(i).getbak4() == null || this.get(i).getbak4().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getbak4().trim());
			}
			if(this.get(i).getbak5() == null || this.get(i).getbak5().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getbak5().trim());
			}
			if(this.get(i).getbak6() == null || this.get(i).getbak6().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getbak6().trim());
			}
			if(this.get(i).getbak7() == null || this.get(i).getbak7().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getbak7().trim());
			}
			if(this.get(i).getbak8() == null || this.get(i).getbak8().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getbak8().trim());
			}
			if(this.get(i).getbak9() == null || this.get(i).getbak9().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getbak9().trim());
			}
			if(this.get(i).getbak10() == null || this.get(i).getbak10().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, this.get(i).getbak10().trim());
			}
			if(this.get(i).getbak11() == null || this.get(i).getbak11().equals("null")) {
				pstmt.setDate(13,null);
			} else {
				pstmt.setDate(13, Date.valueOf(this.get(i).getbak11()));
			}
			if(this.get(i).getbak12() == null || this.get(i).getbak12().equals("null")) {
				pstmt.setDate(14,null);
			} else {
				pstmt.setDate(14, Date.valueOf(this.get(i).getbak12()));
			}
			if(this.get(i).getmakedate() == null || this.get(i).getmakedate().equals("null")) {
				pstmt.setDate(15,null);
			} else {
				pstmt.setDate(15, Date.valueOf(this.get(i).getmakedate()));
			}
			if(this.get(i).getmaketime() == null || this.get(i).getmaketime().equals("null")) {
				pstmt.setString(16,null);
			} else {
				pstmt.setString(16, this.get(i).getmaketime().trim());
			}
			if(this.get(i).getmodifydate() == null || this.get(i).getmodifydate().equals("null")) {
				pstmt.setDate(17,null);
			} else {
				pstmt.setDate(17, Date.valueOf(this.get(i).getmodifydate()));
			}
			if(this.get(i).getmodifytime() == null || this.get(i).getmodifytime().equals("null")) {
				pstmt.setString(18,null);
			} else {
				pstmt.setString(18, this.get(i).getmodifytime().trim());
			}
			if(this.get(i).getflag1() == null || this.get(i).getflag1().equals("null")) {
				pstmt.setString(19,null);
			} else {
				pstmt.setString(19, this.get(i).getflag1().trim());
			}
			if(this.get(i).getflag2() == null || this.get(i).getflag2().equals("null")) {
				pstmt.setString(20,null);
			} else {
				pstmt.setString(20, this.get(i).getflag2().trim());
			}
			if(this.get(i).getflag3() == null || this.get(i).getflag3().equals("null")) {
				pstmt.setString(21,null);
			} else {
				pstmt.setString(21, this.get(i).getflag3().trim());
			}
			if(this.get(i).getflag4() == null || this.get(i).getflag4().equals("null")) {
				pstmt.setString(22,null);
			} else {
				pstmt.setString(22, this.get(i).getflag4().trim());
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
			tError.moduleName = "LCCONTOTHERINFODBSet";
			tError.functionName = "insert()";
			tError.errorMessage = ex.toString();
			this.mErrors .addOneError(tError);

            int tCount = this.size();
            for (int i = 1; i <= tCount; i++)
            {
		// only for debug purpose
		SQLString sqlObj = new SQLString("LCCONTOTHERINFO");
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
