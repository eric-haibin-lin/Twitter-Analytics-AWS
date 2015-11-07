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

/**
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
public class MysqlHandler implements DataHandler {

  private static final String userName = "root";
  private static final String passWord = "coding15619";
  private static final String MYSQL_URL = "jdbc:mysql://localhost:3306/tweet";

  private static final Connection con = getConnection();

  public MysqlHandler() {
    try {
      Class.forName("com.mysql.jdbc.Driver").newInstance();
    } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
      e.printStackTrace();
    }
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
  public String getQuery2(String userId, String tweetTime) {
    String resString = "";
    try {

        Statement sql_statement = con.createStatement();
        String q = userId + "_" + tweetTime;
        String query = "SELECT r FROM tweet1 WHERE q = '" + q +"'";
        ResultSet result = sql_statement.executeQuery(query);

        System.out.println(q);

        if (result.next()) {
            
            Blob b = result.getBlob("r");
            long l = b.length();
            byte[] bytes = b.getBytes(1, (int) l);
            resString = new String(bytes);
            System.out.println(resString);

        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return resString;
  }
}



