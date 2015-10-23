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


    /********************mysql test start****************/
    try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
    } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
        
    String url = "jdbc:mysql://localhost:3306/mysql";
    String userName = "root";
    String passWord = "coding15619";
        
    Connection con = null;
    try {
        con = DriverManager.getConnection(url, userName, passWord);
    } catch (SQLException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
    try {
        Statement sql_statement = (Statement) con.createStatement();
        String query = "select * from user";
        ResultSet result = sql_statement.executeQuery(query);
        while (result.next()) {
            String res = result.getString("Host");
            System.out.println(res);
        }
    } catch (SQLException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
    /*********************mysql test end*****************/


    // Create an HTTP server which simply returns "Hello World!" to each request.
    PhaistosDiscCipher pdc = new PhaistosDiscCipher("8271997208960872478735181815578166723519929177896558845922250595511921395049126920528021164569045773");
    String now = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    Vertx.vertx().createHttpServer().requestHandler(req -> {
        String publicKeyY = req.params().get("key");
        String messageM = req.params().get("message");
	String result = "";
        if (messageM == null || publicKeyY == null || messageM.isEmpty() || publicKeyY.isEmpty()) {
	    result = "Parameters invalid!";
	} else {
            result = pdc.decrypt(messageM, publicKeyY);
	}
        req.response().end("Coding Squirrels,9327-7717-4260\n" + now + "\n" + result + "\n");
    }).listen(80);
  }

}
