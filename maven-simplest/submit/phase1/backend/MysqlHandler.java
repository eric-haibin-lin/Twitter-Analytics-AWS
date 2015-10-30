package io.vertx.example;

import io.vertx.example.utils.Profiler;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.sql.*;

/**
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
public class MysqlHandler implements DataHandler {

  private static final String userName = "?";
  private static final String passWord = "?";
  private static final String MYSQL_URL = "jdbc:mysql://localhost:3306/?";

  public MysqlHandler() {
    try {
      Class.forName("com.mysql.jdbc.Driver").newInstance();
    } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
      e.printStackTrace();
    }
  }

  @Override
  public String getQuery2(String userId, String tweetTime) {
    Profiler profiler = new Profiler();
    profiler.start("Connect to DB");
    String resString = "";
    Connection conn = null;
    try {
        conn = DriverManager.getConnection(MYSQL_URL, userName, passWord);
    } catch (SQLException e) {
        e.printStackTrace();
    }
    profiler.end("Connect to DB");
    final Connection con = conn;
    try {
        profiler.start("Execute query");
        Statement sql_statement = con.createStatement();
        System.out.println(tweetTime);
        String query = "SELECT tid, score, text FROM tweet WHERE uid = '" + userId + "' AND timestamp = '" + tweetTime + "'";
        ResultSet result = sql_statement.executeQuery(query);
        profiler.end("Execute query");
        profiler.start("Parse DB result");
        //TODO add json parsing part
        while (result.next()) {
            String uid = result.getString("tid");
            String score = result.getString("score");
            String text = result.getString("text");
            System.out.println(uid + score + text);
            resString += uid + ":" + score + ":" + text + "\n";
        }
      profiler.end("Parse DB result");
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return resString;
  }
}



