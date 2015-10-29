package io.vertx.example;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;

/**
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
public class MysqlHandler implements DataHandler {

  private static final String userName = "root";
  private static final String passWord = "coding15619";
  private static final String MYSQL_URL = "jdbc:mysql://ec2-54-173-14-165.compute-1.amazonaws.com:3306/tweet";


  @Override
  public String getQuery2(String userId, String tweetTime) {

//    try {
//      Class.forName("com.mysql.jdbc.Driver").newInstance();
//    } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
//      e.printStackTrace();
//    }
//
//    String url = "jdbc:mysql://localhost:3306/tweet";
//    Connection conn = null;
//    try {
//        conn = DriverManager.getConnection(url, userName, passWord);
//    } catch (SQLException e) {
//        e.printStackTrace();
//    }
//    final Connection con = conn;
//
//    try {
//        Statement sql_statement = (Statement) con.createStatement();
//        System.out.println(tweetTime);
//        String query = "SELECT tid, score, text FROM tweet WHERE uid = '" + userId + "' AND timestamp = '" + tweetTime + "'";
//        ResultSet result = sql_statement.executeQuery(query);
//
//        while (result.next()) {
//            String uid = result.getString("tid");
//            String score = result.getString("score");
//            String text = result.getString("text");
//            System.out.println(uid + score + text);
//
//            resString += uid + ":" + score + ":" + text + "\n";
//        }
//    } catch (SQLException e) {
//        e.printStackTrace();
//    }


    return null;
  }
}



