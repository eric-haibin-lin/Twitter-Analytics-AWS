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

    private static final byte[] DATA = "data".getBytes();
    private static final byte[] TEXT = "text".getBytes();
    private static final String TABLE_NAME = "tweets";
    private static final String MYSQL_URL = "jdbc:mysql://ec2-54-173-14-165.compute-1.amazonaws.com:3306/tweet";
    private static final String userName = "root";
    private static final String passWord = "coding15619";

  public static void main(String[] args) {

    try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
    } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
        e.printStackTrace();
    }

    String url = MYSQL_URL;
    //String url = "jdbc:mysql://localhost:3306/tweet";

    try{
        Configuration config = HBaseConfiguration.create();
        config.set("hbase.zookeeper.quorum", "localhost");
        //config.set("hbase.zookeeper.property.clientPort", "2181");
        HBaseAdmin admin = new HBaseAdmin(config);
        String row = "12408871992014-05-15+09:02:21466866162106511360";
        HTable tableTweets = new HTable(config, TABLE_NAME);
        Get get = new Get(Bytes.toBytes(row));
        Result result = tableTweets.get(get);
        System.out.println("Get: " + new String(result.getValue(DATA, TEXT), "UTF-8"));
    } catch (IOException e){
        e.printStackTrace();
    }

    //Connection conn = null;
    //try {
    //    conn = DriverManager.getConnection(url, userName, passWord);
    //} catch (SQLException e) {
    //    e.printStackTrace();
    //}
    //final Connection con = conn;

    Vertx.vertx().createHttpServer().requestHandler(req -> {
        String userId = req.params().get("userid");
        String tweetTime = req.params().get("tweet_time");
        String resString = "Coding Squirrels,9327-7717-4260\n";

        if ( userId == null || tweetTime == null || userId.isEmpty() || tweetTime.isEmpty()) {
            resString = "Parameters invalid!";
        } else {
            tweetTime = tweetTime.replace(" ", "+");
          
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



