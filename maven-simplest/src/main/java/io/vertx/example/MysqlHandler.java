package io.vertx.example;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.sql.*;
import org.json.JSONObject;

import javax.sql.DataSource;
import org.apache.commons.dbcp2.BasicDataSource;

/**
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
public class MysqlHandler implements DataHandler {

	private static final String userName = "root";
	private static final String passWord = "coding15619";
	private static final String MYSQL_URL = "jdbc:mysql://localhost:3306/tweet";

	private static final DataSource ds = DBCPDataSourceFactory.getDataSource();

	public static class DBCPDataSourceFactory {
		public static DataSource getDataSource() {
			BasicDataSource ds = new BasicDataSource();
			ds.setDriverClassName("com.mysql.jdbc.Driver");
			ds.setUrl(MYSQL_URL);
			ds.setUsername(userName);
			ds.setPassword(passWord);

			return ds;
		}
	}

	public MysqlHandler() {
	}

	public static Connection getConnection() {
		Connection conSql = null;
		try {
			conSql = DriverManager.getConnection(MYSQL_URL, userName, passWord);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return conSql;
	}

	@Override
	public String getQuery4(String hashtag, Integer n) {
		String resString = "";
		try {
			Connection con = ds.getConnection();

			Statement sql_statement = con.createStatement();
			String q = hashtag;
			String query = "SELECT r FROM q4 WHERE q = '" + q + "'";
			ResultSet result = sql_statement.executeQuery(query);

			if (result.next()) {

				Blob b = result.getBlob("r");
				long l = b.length();
				byte[] bytes = b.getBytes(1, (int) l);
				String record = new String (bytes);
				String[] lines = record.split("\b");
				resString = "";
				for (int i = 0; i < lines.length && i < n; i++) {
					resString += lines[i] + "\n";
				}
			}

			if (result != null)
				result.close();
			if (sql_statement != null)
				sql_statement.close();
			if (con != null)
				con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resString;
	}

	@Override
	public String getQuery2(String userId, String tweetTime) {
		String resString = "";
		try {
			Connection con = ds.getConnection();

			Statement sql_statement = con.createStatement();
			String q = userId + "_" + tweetTime;
			String query = "SELECT r FROM q2 WHERE q = '" + q + "'";
			ResultSet result = sql_statement.executeQuery(query);

			if (result.next()) {

				Blob b = result.getBlob("r");
				long l = b.length();
				byte[] bytes = b.getBytes(1, (int) l);
				resString = new String(bytes);

			}

			if (result != null)
				result.close();
			if (sql_statement != null)
				sql_statement.close();
			if (con != null)
				con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resString;
	}
}
