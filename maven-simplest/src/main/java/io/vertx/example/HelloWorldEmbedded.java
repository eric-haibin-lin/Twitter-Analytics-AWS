package io.vertx.example;

import io.vertx.core.Vertx;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.IOException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.mysql.jdbc.Statement;

/**
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
public class HelloWorldEmbedded {

    private static final String MODE = "Hbase";
    private static final String HBASE_MODE = "Hbase";
    private static final String MYSQL_MODE = "Mysql";
    private static final String USER_ID = "userid";
    private static final String TWEET_TIME = "tweet_time";
    private static final String TEAM_INFO = "Coding Squirrels,9327-7717-4260\n";


  public static void main(String[] args) {

    try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
    } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
        e.printStackTrace();
    }

    //String url = "jdbc:mysql://localhost:3306/tweet";

    DataHandler dataHandler;
    if (MODE.equals(HBASE_MODE)){
      dataHandler = new HbaseHandler();
    } else {
      dataHandler = new MysqlHandler();
    }

    //Connection conn = null;
    //try {
    //    conn = DriverManager.getConnection(url, userName, passWord);
    //} catch (SQLException e) {
    //    e.printStackTrace();
    //}
    //final Connection con = conn;

    //TODO path should be /q2
    Vertx.vertx().createHttpServer().requestHandler(req -> {
        String userId = req.params().get(USER_ID);
        String tweetTime = req.params().get(TWEET_TIME);
        String resString = TEAM_INFO;

        if ( userId == null || tweetTime == null || userId.isEmpty() || tweetTime.isEmpty()) {
            resString = "Parameters invalid!";
        } else {

            System.out.println(tweetTime);
            tweetTime = tweetTime.replace(" ", "+");
            resString = TEAM_INFO + dataHandler.getQuery2(userId, tweetTime);
            //try {
            //    Statement sql_statement = (Statement) con.createStatement();
            //    System.out.println(tweetTime);
            //    String query = "SELECT tid, score, text FROM tweet WHERE uid = '" + userId + "' AND timestamp = '" + tweetTime + "'";
            //    ResultSet result = sql_statement.executeQuery(query);

            //    while (result.next()) {
            //        String uid = result.getString("tid");
            //        String score = result.getString("score");
            //        String text = result.getString("text");
            //        System.out.println(uid + score + text);
            //        
            //        resString += uid + ":" + score + ":" + text + "\n";
            //    }
            //} catch (SQLException e) {
            //    e.printStackTrace();
            //}
        }
        req.response().end(resString);
    }).listen(80);
  }

}



