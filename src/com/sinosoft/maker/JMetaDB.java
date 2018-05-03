/*
 * <p>ClassName: JMetaDB </p>
 * <p>Description: ������ݿ��еı���Ϣ���ֶ���Ϣ </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @author: HST
 * @version: 1.0
 * @date: 2002-05-31
 */
package com.sinosoft.maker;

import java.sql.*;
import java.util.Vector;

import com.sinosoft.utility.DBConnPool;
import com.sinosoft.utility.JdbcUrl;

public class JMetaDB
{
	// @Constructor
	public JMetaDB(JdbcUrl sUrl)
	{
		JUrl = sUrl;
	}

	// @Field
	private final JdbcUrl JUrl;

	private Vector Fields;

	private Vector Tables;

	// @Method
	public String getJUrl()
	{
		return JUrl.getJdbcUrl();
	}

	public Vector getPrimaryKey(Vector fields, String strTableCode) throws Exception
	{
		PreparedStatement pstmt = null;
		Connection con = null;
		// Statement stmt = null;
		DatabaseMetaData dbmd = null;
		ResultSetMetaData rsmd = null;
		ResultSet pkRS = null;
		// System.out.println("get primary key");
		// �����������ݿ�
		con = DBConnPool.getConnection();
		// System.out.println("after connection");
		if (con == null)
		{
			System.out.println("Connect failed!");
			System.exit(0);
		}

		try
		{
			pstmt = con.prepareStatement("select * from " + strTableCode + " where rownum <= 1", ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			pkRS = pstmt.executeQuery();

			rsmd = pkRS.getMetaData();
			int n2 = rsmd.getColumnCount();
			int n = fields.size();
			if (n != n2)
			{
				System.out.println("n = " + n);
				System.out.println("n2 = " + n2);
				System.out.println("��ṹ�ֶθ�����ͳһ�����ѯ��");
				con.close();
				throw new Exception("��ṹ�ֶθ�����ͳһ�����ѯ��");
			}
			else
			{
				// ���˸�����У�飬����Ҫ����ֶ�˳���У�飬�����Ϳ����ں���Ļ�ȡ��ʹ�����������ֶ�
				for (int m = 1; m <= n; m++)
				{
					FieldInfo f = (FieldInfo) fields.get(m - 1);
					String tPdmColumnName = f.getFieldCode(); // pdm���ֶ�
					String tDataColumnName = rsmd.getColumnName(m); // ���ݿ����ֶ�
					// �������ݿ��ֶε����ͼ�����
					rsmd.getColumnName(m);
					f.setFieldSQLType(rsmd.getColumnType(m));
					f.setFieldLength(rsmd.getColumnDisplaySize(m));

					// ���ͬ˳λ���ֶβ�ͬ�������ʾpdm�еı�ṹ˳�򣦿��в�ͬ����Ҫ��֤��
					if (!tPdmColumnName.equalsIgnoreCase(tDataColumnName))
					{
						System.out.println(strTableCode + "����pdm�е��ֶ�" + tPdmColumnName + "�Ϳ��и��ֶ�λ�ò�ͬ");
						con.close();
						throw new Exception(strTableCode + "����pdm�е��ֶ�" + tPdmColumnName + "�Ϳ��и��ֶ�λ�ò�ͬ");
					}
					// System.out.println(FieldCode + " - pdm�е��ֶ�");
					// System.out.println(rsmd.getColumnName(m) + " - ���ݿ��е��ֶ�");
				}

				dbmd = con.getMetaData();
				strTableCode = strTableCode.toUpperCase();
				// ����Ĳ����ã��ڶ����������÷���db2�Ļ���û�еڶ��������ſ��������ĵõ�pk
				// pkRS = dbmd.getPrimaryKeys(null, null, strTableCode);
				JdbcUrl tJdbcUrl = new JdbcUrl();
				pkRS = dbmd.getPrimaryKeys(null,tJdbcUrl.getUserName().toUpperCase(), strTableCode);

				while (pkRS.next())
				{
					for (int i = 0; i < n; i++)
					{
						FieldInfo f = (FieldInfo) fields.get(i);
						String FieldCode = f.getFieldCode();

//						rsmd.getColumnName(i + 1);
//						f.setFieldSQLType(rsmd.getColumnType(i + 1));
//						f.setFieldLength(rsmd.getColumnDisplaySize(i + 1));

						if (FieldCode.toLowerCase().equals(pkRS.getString(4).toLowerCase()))
						{
							f.setFieldProperty("PK");
							fields.remove(i);
							fields.add(i, f);
						}
					} // end of for
				} // end of while
			}
		} // end of try
		catch (Exception e1)
		{
			e1.printStackTrace();
			con.close();
			throw e1;
		}
		pkRS.close();

		// �Ͽ����ݿ�����
		con.close();

		return fields;
	}

	public Vector getFields(String strTableCode) throws Exception
	{
		Connection con = null;
		Statement stmt = null;
		DatabaseMetaData dbmd = null;
		ResultSet tableRS = null;
		ResultSet pkRS = null;
		ResultSet rs = null;
		ResultSetMetaData rsmd = null;

		strTableCode = strTableCode.toLowerCase();

		// �����������ݿ�
		con = DBConnPool.getConnection();
		if (con == null)
		{
			System.out.println("Connect failed!");
			System.exit(0);
		}

		try
		{
			stmt = con.createStatement();
			dbmd = con.getMetaData();
			tableRS = dbmd.getTables(null, null, null, new String[]
			{ "TABLE" });
			Fields = new Vector();

			while (tableRS.next())
			{
				if (!(tableRS.getString(4).equals("TABLE") && strTableCode.equals(tableRS.getString(3).toLowerCase())))
				{
					continue;
				}

				rs = stmt.executeQuery("select * from " + strTableCode);
				rsmd = rs.getMetaData();

				for (int i = 1; i <= rsmd.getColumnCount(); i++)
				{
					FieldInfo f = new FieldInfo();
					// �����ֶ�����
					f.setFieldCode(rsmd.getColumnName(i));

					// �����ֶ�����
					/*
					 * for informix char--1 decimal--3 int--4 serial --4
					 * smallint --5 varchar--12 date--91 datetime year to second
					 * --93
					 */
					switch (rsmd.getColumnType(i))
					{
						case 1:
						case 12:
						case 91:
						case 93:
							f.setFieldType("String");
							break;
						case 3:
							f.setFieldType("float");
							break;
						case 4:
						case 5:
							f.setFieldType("int");
							break;
						default:
							f.setFieldType("String");
							break;
					} // end of switch

					// 2005-11-11 Kevin
					f.setFieldSQLType(rsmd.getColumnType(i));
					f.setFieldLength(rsmd.getColumnDisplaySize(i));

					Fields.addElement(f);
				} // end of for

				// �����������
				pkRS = dbmd.getPrimaryKeys(null, null, strTableCode);
				int n = Fields.size();
				while (pkRS.next())
				{
					for (int i = 0; i < n; i++)
					{
						FieldInfo f = (FieldInfo) Fields.get(i);
						String FieldCode = f.getFieldCode();
						if (FieldCode.toLowerCase().equals(pkRS.getString(4).toLowerCase()))
						{
							f.setFieldProperty("PK");
							Fields.remove(i);
							Fields.add(i, f);
						}
					} // end of for
				} // end of while
			} // end of while
		} // end of try
		catch (Exception e1)
		{
			e1.printStackTrace();
			con.close();
			throw e1;
		}

		// �Ͽ����ݿ�����
		con.close();

		return Fields;
	}

	public Vector getTables() throws Exception
	{
		Connection con = null;
		DatabaseMetaData dbmd = null;
		ResultSet tableRS = null;

		// �����������ݿ�
		con = DBConnPool.getConnection();
		if (con == null)
		{
			System.out.println("Connect failed!");
			System.exit(0);
		}

		// ��ѯ�ֶ���Ϣ
		try
		{
			dbmd = con.getMetaData();
			tableRS = dbmd.getTables(null, null, null, new String[]
			{ "TABLE" });
			Tables = new Vector();

			while (tableRS.next())
			{
				if (tableRS.getString(4).equals("TABLE"))
				{
					String t = tableRS.getString(3);
					t = t.toLowerCase();
					t = t.substring(0, 1).toUpperCase() + t.substring(1);
					Tables.addElement(t);
				}
			} // end of while
		} // end of try
		catch (Exception e1)
		{
			e1.printStackTrace();
			con.close();
			throw e1;
		}

		// �Ͽ����ݿ�����
		con.close();

		return Tables;
	}
}
