/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.db;

import java.sql.*;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCContOtherInfoSchema;
import com.sinosoft.lis.vschema.LCContOtherInfoSet;
import com.sinosoft.utility.*;

/**
 * <p>ClassName: LCCONTOTHERINFODB </p>
 * <p>Description: DB层数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: PhysicalDataModel_1
 * @CreateDate：2018-05-02
 */
public class LCContOtherInfoDB extends LCContOtherInfoSchema
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
	public LCContOtherInfoDB( Connection tConnection )
	{
		con = tConnection;
		db = new DBOper( con, "LCCONTOTHERINFO" );
		mflag = true;
	}

	public LCContOtherInfoDB()
	{
		con = null;
		db = new DBOper( "LCCONTOTHERINFO" );
		mflag = false;
	}

	// @Method
	public boolean deleteSQL()
	{
		LCContOtherInfoSchema tSchema = this.getSchema();
		if (db.deleteSQL(tSchema))
		{
		     return true;
		}
		else
		{
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LCCONTOTHERINFODB";
			tError.functionName = "deleteSQL";
			tError.errorMessage = "操作失败!";
			this.mErrors .addOneError(tError);
			return false;
		}
	}

	public int getCount()
	{
		LCContOtherInfoSchema tSchema = this.getSchema();

		int tCount = db.getCount(tSchema);
		if (tCount < 0)
		{
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LCCONTOTHERINFODB";
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
			pstmt = con.prepareStatement("DELETE FROM LCCONTOTHERINFO WHERE ");
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			ex.printStackTrace();
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LCCONTOTHERINFODB";
			tError.functionName = "delete()";
			tError.errorMessage = ex.toString();
			this.mErrors .addOneError(tError);

		// only for debug purpose
		SQLString sqlObj = new SQLString("LCCONTOTHERINFO");
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
			pstmt = con.prepareStatement("UPDATE LCCONTOTHERINFO SET  prtno = ? , referrerrphone = ? , bak1 = ? , bak2 = ? , bak3 = ? , bak4 = ? , bak5 = ? , bak6 = ? , bak7 = ? , bak8 = ? , bak9 = ? , bak10 = ? , bak11 = ? , bak12 = ? , makedate = ? , maketime = ? , modifydate = ? , modifytime = ? , flag1 = ? , flag2 = ? , flag3 = ? , flag4 = ? WHERE ");
			if(this.getprtno() == null || this.getprtno().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getprtno().trim());
			}
			if(this.getreferrerrphone() == null || this.getreferrerrphone().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getreferrerrphone().trim());
			}
			if(this.getbak1() == null || this.getbak1().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getbak1().trim());
			}
			if(this.getbak2() == null || this.getbak2().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getbak2().trim());
			}
			if(this.getbak3() == null || this.getbak3().equals("null")) {
				pstmt.setNull(5, 12);
			} else {
				pstmt.setString(5, this.getbak3().trim());
			}
			if(this.getbak4() == null || this.getbak4().equals("null")) {
				pstmt.setNull(6, 12);
			} else {
				pstmt.setString(6, this.getbak4().trim());
			}
			if(this.getbak5() == null || this.getbak5().equals("null")) {
				pstmt.setNull(7, 12);
			} else {
				pstmt.setString(7, this.getbak5().trim());
			}
			if(this.getbak6() == null || this.getbak6().equals("null")) {
				pstmt.setNull(8, 12);
			} else {
				pstmt.setString(8, this.getbak6().trim());
			}
			if(this.getbak7() == null || this.getbak7().equals("null")) {
				pstmt.setNull(9, 12);
			} else {
				pstmt.setString(9, this.getbak7().trim());
			}
			if(this.getbak8() == null || this.getbak8().equals("null")) {
				pstmt.setNull(10, 12);
			} else {
				pstmt.setString(10, this.getbak8().trim());
			}
			if(this.getbak9() == null || this.getbak9().equals("null")) {
				pstmt.setNull(11, 12);
			} else {
				pstmt.setString(11, this.getbak9().trim());
			}
			if(this.getbak10() == null || this.getbak10().equals("null")) {
				pstmt.setNull(12, 12);
			} else {
				pstmt.setString(12, this.getbak10().trim());
			}
			if(this.getbak11() == null || this.getbak11().equals("null")) {
				pstmt.setNull(13, 91);
			} else {
				pstmt.setDate(13, Date.valueOf(this.getbak11()));
			}
			if(this.getbak12() == null || this.getbak12().equals("null")) {
				pstmt.setNull(14, 91);
			} else {
				pstmt.setDate(14, Date.valueOf(this.getbak12()));
			}
			if(this.getmakedate() == null || this.getmakedate().equals("null")) {
				pstmt.setNull(15, 91);
			} else {
				pstmt.setDate(15, Date.valueOf(this.getmakedate()));
			}
			if(this.getmaketime() == null || this.getmaketime().equals("null")) {
				pstmt.setNull(16, 12);
			} else {
				pstmt.setString(16, this.getmaketime().trim());
			}
			if(this.getmodifydate() == null || this.getmodifydate().equals("null")) {
				pstmt.setNull(17, 91);
			} else {
				pstmt.setDate(17, Date.valueOf(this.getmodifydate()));
			}
			if(this.getmodifytime() == null || this.getmodifytime().equals("null")) {
				pstmt.setNull(18, 12);
			} else {
				pstmt.setString(18, this.getmodifytime().trim());
			}
			if(this.getflag1() == null || this.getflag1().equals("null")) {
				pstmt.setNull(19, 12);
			} else {
				pstmt.setString(19, this.getflag1().trim());
			}
			if(this.getflag2() == null || this.getflag2().equals("null")) {
				pstmt.setNull(20, 12);
			} else {
				pstmt.setString(20, this.getflag2().trim());
			}
			if(this.getflag3() == null || this.getflag3().equals("null")) {
				pstmt.setNull(21, 12);
			} else {
				pstmt.setString(21, this.getflag3().trim());
			}
			if(this.getflag4() == null || this.getflag4().equals("null")) {
				pstmt.setNull(22, 12);
			} else {
				pstmt.setString(22, this.getflag4().trim());
			}
			// set where condition
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			ex.printStackTrace();
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LCCONTOTHERINFODB";
			tError.functionName = "update()";
			tError.errorMessage = ex.toString();
			this.mErrors .addOneError(tError);

		// only for debug purpose
		SQLString sqlObj = new SQLString("LCCONTOTHERINFO");
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
			pstmt = con.prepareStatement("INSERT INTO LCCONTOTHERINFO VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
			if(this.getprtno() == null || this.getprtno().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getprtno().trim());
			}
			if(this.getreferrerrphone() == null || this.getreferrerrphone().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getreferrerrphone().trim());
			}
			if(this.getbak1() == null || this.getbak1().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getbak1().trim());
			}
			if(this.getbak2() == null || this.getbak2().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getbak2().trim());
			}
			if(this.getbak3() == null || this.getbak3().equals("null")) {
				pstmt.setNull(5, 12);
			} else {
				pstmt.setString(5, this.getbak3().trim());
			}
			if(this.getbak4() == null || this.getbak4().equals("null")) {
				pstmt.setNull(6, 12);
			} else {
				pstmt.setString(6, this.getbak4().trim());
			}
			if(this.getbak5() == null || this.getbak5().equals("null")) {
				pstmt.setNull(7, 12);
			} else {
				pstmt.setString(7, this.getbak5().trim());
			}
			if(this.getbak6() == null || this.getbak6().equals("null")) {
				pstmt.setNull(8, 12);
			} else {
				pstmt.setString(8, this.getbak6().trim());
			}
			if(this.getbak7() == null || this.getbak7().equals("null")) {
				pstmt.setNull(9, 12);
			} else {
				pstmt.setString(9, this.getbak7().trim());
			}
			if(this.getbak8() == null || this.getbak8().equals("null")) {
				pstmt.setNull(10, 12);
			} else {
				pstmt.setString(10, this.getbak8().trim());
			}
			if(this.getbak9() == null || this.getbak9().equals("null")) {
				pstmt.setNull(11, 12);
			} else {
				pstmt.setString(11, this.getbak9().trim());
			}
			if(this.getbak10() == null || this.getbak10().equals("null")) {
				pstmt.setNull(12, 12);
			} else {
				pstmt.setString(12, this.getbak10().trim());
			}
			if(this.getbak11() == null || this.getbak11().equals("null")) {
				pstmt.setNull(13, 91);
			} else {
				pstmt.setDate(13, Date.valueOf(this.getbak11()));
			}
			if(this.getbak12() == null || this.getbak12().equals("null")) {
				pstmt.setNull(14, 91);
			} else {
				pstmt.setDate(14, Date.valueOf(this.getbak12()));
			}
			if(this.getmakedate() == null || this.getmakedate().equals("null")) {
				pstmt.setNull(15, 91);
			} else {
				pstmt.setDate(15, Date.valueOf(this.getmakedate()));
			}
			if(this.getmaketime() == null || this.getmaketime().equals("null")) {
				pstmt.setNull(16, 12);
			} else {
				pstmt.setString(16, this.getmaketime().trim());
			}
			if(this.getmodifydate() == null || this.getmodifydate().equals("null")) {
				pstmt.setNull(17, 91);
			} else {
				pstmt.setDate(17, Date.valueOf(this.getmodifydate()));
			}
			if(this.getmodifytime() == null || this.getmodifytime().equals("null")) {
				pstmt.setNull(18, 12);
			} else {
				pstmt.setString(18, this.getmodifytime().trim());
			}
			if(this.getflag1() == null || this.getflag1().equals("null")) {
				pstmt.setNull(19, 12);
			} else {
				pstmt.setString(19, this.getflag1().trim());
			}
			if(this.getflag2() == null || this.getflag2().equals("null")) {
				pstmt.setNull(20, 12);
			} else {
				pstmt.setString(20, this.getflag2().trim());
			}
			if(this.getflag3() == null || this.getflag3().equals("null")) {
				pstmt.setNull(21, 12);
			} else {
				pstmt.setString(21, this.getflag3().trim());
			}
			if(this.getflag4() == null || this.getflag4().equals("null")) {
				pstmt.setNull(22, 12);
			} else {
				pstmt.setString(22, this.getflag4().trim());
			}
			// execute sql
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			ex.printStackTrace();
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LCCONTOTHERINFODB";
			tError.functionName = "insert()";
			tError.errorMessage = ex.toString();
			this.mErrors .addOneError(tError);

		// only for debug purpose
		SQLString sqlObj = new SQLString("LCCONTOTHERINFO");
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
			pstmt = con.prepareStatement("SELECT * FROM LCCONTOTHERINFO WHERE ", 
				ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
			rs = pstmt.executeQuery();
			int i = 0;
			while (rs.next())
			{
				i++;
				if (!this.setSchema(rs,i))
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LCCONTOTHERINFODB";
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
			tError.moduleName = "LCCONTOTHERINFODB";
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

	public LCContOtherInfoSet query()
	{
		Statement stmt = null;
		ResultSet rs = null;
		LCContOtherInfoSet aLCCONTOTHERINFOSet = new LCContOtherInfoSet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			SQLString sqlObj = new SQLString("LCCONTOTHERINFO");
			LCContOtherInfoSchema aSchema = this.getSchema();
			sqlObj.setSQL(5,aSchema);
			String sql = sqlObj.getSQL();
			stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
			rs = stmt.executeQuery(sql);
			int i = 0;
			while (rs.next())
			{
				i++;
				LCContOtherInfoSchema s1 = new LCContOtherInfoSchema();
				s1.setSchema(rs,i);
				aLCCONTOTHERINFOSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			e.printStackTrace();
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCCONTOTHERINFODB";
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

		return aLCCONTOTHERINFOSet;
	}

	public LCContOtherInfoSet executeQuery(String sql)
	{
		Statement stmt = null;
		ResultSet rs = null;
		LCContOtherInfoSet aLCCONTOTHERINFOSet = new LCContOtherInfoSet();

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
				LCContOtherInfoSchema s1 = new LCContOtherInfoSchema();
				if (!s1.setSchema(rs,i))
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LCCONTOTHERINFODB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLCCONTOTHERINFOSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			e.printStackTrace();
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCCONTOTHERINFODB";
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

		return aLCCONTOTHERINFOSet;
	}

	public LCContOtherInfoSet query(int nStart, int nCount)
	{
		Statement stmt = null;
		ResultSet rs = null;
		LCContOtherInfoSet aLCCONTOTHERINFOSet = new LCContOtherInfoSet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			SQLString sqlObj = new SQLString("LCCONTOTHERINFO");
			LCContOtherInfoSchema aSchema = this.getSchema();
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

				LCContOtherInfoSchema s1 = new LCContOtherInfoSchema();
				s1.setSchema(rs,i);
				aLCCONTOTHERINFOSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			e.printStackTrace();
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCCONTOTHERINFODB";
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

		return aLCCONTOTHERINFOSet;
	}

	public LCContOtherInfoSet executeQuery(String sql, int nStart, int nCount)
	{
		Statement stmt = null;
		ResultSet rs = null;
		LCContOtherInfoSet aLCCONTOTHERINFOSet = new LCContOtherInfoSet();

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

				LCContOtherInfoSchema s1 = new LCContOtherInfoSchema();
				if (!s1.setSchema(rs,i))
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LCCONTOTHERINFODB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLCCONTOTHERINFOSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			e.printStackTrace();
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCCONTOTHERINFODB";
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

		return aLCCONTOTHERINFOSet;
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
			SQLString sqlObj = new SQLString("LCCONTOTHERINFO");
			LCContOtherInfoSchema aSchema = this.getSchema();
			sqlObj.setSQL(2,aSchema);
			String sql = "update LCCONTOTHERINFO " + sqlObj.getUpdPart() + " where " + strWherePart;

			int operCount = stmt.executeUpdate(sql);
			if( operCount == 0 )
			{
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "LCCONTOTHERINFODB";
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
			tError.moduleName = "LCCONTOTHERINFODB";
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
        tError.moduleName = "LCCONTOTHERINFODB";
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
        tError.moduleName = "LCCONTOTHERINFODB";
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
        tError.moduleName = "LCCONTOTHERINFODB";
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
        tError.moduleName = "LCCONTOTHERINFODB";
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
 * @return LCCONTOTHERINFOSet
 */
public LCContOtherInfoSet getData()
{
    int tCount = 0;
    LCContOtherInfoSet tLCCONTOTHERINFOSet = new LCContOtherInfoSet();
    LCContOtherInfoSchema tLCCONTOTHERINFOSchema = null;
    if (null == mResultSet)
    {
        CError tError = new CError();
        tError.moduleName = "LCCONTOTHERINFODB";
        tError.functionName = "getData";
        tError.errorMessage = "数据集为空，请先准备数据集！";
        this.mErrors.addOneError(tError);
        return null;
    }
    try
    {
        tCount = 1;
        tLCCONTOTHERINFOSchema = new LCContOtherInfoSchema();
        tLCCONTOTHERINFOSchema.setSchema(mResultSet, 1);
        tLCCONTOTHERINFOSet.add(tLCCONTOTHERINFOSchema);
        //注意mResultSet.next()的作用
        while (tCount++ < SysConst.FETCHCOUNT)
        {
            if (mResultSet.next())
            {
                tLCCONTOTHERINFOSchema = new LCContOtherInfoSchema();
                tLCCONTOTHERINFOSchema.setSchema(mResultSet, 1);
                tLCCONTOTHERINFOSet.add(tLCCONTOTHERINFOSchema);
            }
        }
    }
    catch (Exception ex)
    {
        CError tError = new CError();
        tError.moduleName = "LCCONTOTHERINFODB";
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
    return tLCCONTOTHERINFOSet;
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
            tError.moduleName = "LCCONTOTHERINFODB";
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
        tError.moduleName = "LCCONTOTHERINFODB";
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
            tError.moduleName = "LCCONTOTHERINFODB";
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
        tError.moduleName = "LCCONTOTHERINFODB";
        tError.functionName = "closeData";
        tError.errorMessage = ex3.toString();
        this.mErrors.addOneError(tError);
        flag = false;
    }
    return flag;
}
}
