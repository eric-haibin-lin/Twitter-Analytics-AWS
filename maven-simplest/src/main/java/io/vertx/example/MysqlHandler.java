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
    //Connection con = null;
    try {
        //Connection con = DriverManager.getConnection(MYSQL_URL, userName, passWord);

        Statement sql_statement = con.createStatement();
        String query = "SELECT tid, score, text FROM tweet WHERE uid = '" 
                            + userId + "' AND timestamp = '" + tweetTime + "'";
        ResultSet result = sql_statement.executeQuery(query);

        while (result.next()) {
            String tid = result.getString("tid");
            String score = result.getString("score");
            String text = result.getString("text");

            //System.out.println(tid + score + text);
            text = new JSONObject(text).getString("text");
            //System.out.println(tid + score + text);

            resString += tid + ":" + score + ":" + text + "\n";
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return resString;
  }
}



