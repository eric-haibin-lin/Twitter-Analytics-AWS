package io.vertx.example;

import io.vertx.core.Vertx;
import java.text.SimpleDateFormat;
import java.util.Date;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.mysql.jdbc.Statement;

/**
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
public class HelloWorldEmbedded {

  public static void main(String[] args) {


    try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
    } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
        
    //String url = "jdbc:mysql://ec2-54-173-14-165.compute-1.amazonaws.com:3306/mysql";
    String url = "jdbc:mysql://localhost:3306/tweet";
    String userName = "root";
    String passWord = "coding15619";
        
    Connection conn = null;
    try {
        conn = DriverManager.getConnection(url, userName, passWord);
    } catch (SQLException e) {
        e.printStackTrace();
    }
    final Connection con = conn;

    Vertx.vertx().createHttpServer().requestHandler(req -> {
        String userId = req.params().get("userid");
        String tweetTime = req.params().get("tweet_time");
        String resString = "Coding Squirrels,9327-7717-4260\n";

        if ( userId == null || tweetTime == null || userId.isEmpty() || tweetTime.isEmpty()) {
            resString = "Parameters invalid!";
        }

        try {
            Statement sql_statement = (Statement) con.createStatement();
            System.out.println(tweetTime);
            String query = "SELECT tid, score, text FROM tweet WHERE uid = '" + userId + "' AND timestamp = '" + tweetTime + "'";
            ResultSet result = sql_statement.executeQuery(query);

            while (result.next()) {
                String uid = result.getString("tid");
                String score = result.getString("score");
                String text = result.getString("text");
                System.out.println(uid + score + text);
                
                resString += uid + "," + score + "," + text + "\n";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        req.response().end(resString);
    }).listen(80);
  }

}
