package io.vertx.example;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServerRequest;
import java.text.SimpleDateFormat;
import java.util.TimeZone;
import java.util.Date;


public class HelloWorldEmbedded {

    private static final String MODE = "Mysql";
    private static final String HBASE_MODE = "Hbase";
    private static final String MYSQL_MODE = "Mysql";
    private static final String USER_ID = "userid";
    private static final String TWEET_TIME = "tweet_time";
    private static final String TEAM_INFO = "Coding Squirrels,9327-7717-4260\n";
    private static final String Q1_ENDPOINT = "/q1";
    private static final String Q2_ENDPOINT = "/q2";
    private static final String Q3_ENDPOINT = "/q3";
    private static final String Q4_ENDPOINT = "/q4";
    //private static final String PRIVATE_KEY = System.getenv("$PRIVATE_KEY");
    private static final String PRIVATE_KEY = "8271997208960872478735181815578166723519929177896558845922250595511921395049126920528021164569045773";

  public static void main(String[] args) {

    DataHandler dataHandler;
    if (MODE.equals(HBASE_MODE)) {
      dataHandler = new HbaseHandler();
    } else if (MODE.equals(MYSQL_MODE)) {
      dataHandler = new MysqlHandler();
    } else {
      dataHandler = null;
      System.out.println("Testing only Q1...");
    }

    Vertx.vertx().createHttpServer().requestHandler(req -> {
      String path = req.path();
      switch (path) {
        case (Q1_ENDPOINT):
          handleQ1Request(req);
          break;
        case Q2_ENDPOINT:
          handleQ2Request(dataHandler, req);
          break;
        case Q3_ENDPOINT:
          handleQ3Request(dataHandler, req);
          break;
        default:
          req.response().end("Unrecognized endpoint");
          break;
      }
    }).listen(80);
  }

  /**
   * handles query 3
   * @param dataHandler
   * @param req request
   */
  private static void handleQ3Request(DataHandler dataHandler, HttpServerRequest req) {
    req.response().end("Not implemented yet");
  }

  /**
   * handles query 2
   * @param dataHandler
   * @param req
   */
  private static void handleQ2Request(DataHandler dataHandler, HttpServerRequest req) {
    Thread t = new Thread(new Runnable() {
        public void run() {
            String userId = req.params().get(USER_ID);
            String tweetTime = req.params().get(TWEET_TIME);
            String resString = TEAM_INFO;
            if (userId == null || tweetTime == null || 
                userId.isEmpty() || tweetTime.isEmpty()) {
              resString = "Parameters invalid!";
            } else {
              //System.out.println(tweetTime);
              //tweetTime = tweetTime.replace(" ", "+");
              resString = TEAM_INFO + dataHandler.getQuery2(userId, tweetTime);
            }
            req.response().headers().add("Content-Type", "text/plain; charset=UTF-8");
            req.response().end(resString);
        }
    });
    t.start();
  }

  /**
   * handles query 1
   * @param req
   */
  private static void handleQ1Request(HttpServerRequest req) {
    Thread t = new Thread(new Runnable() {
        public void run() {
          PhaistosDiscCipher pdc = new PhaistosDiscCipher(PRIVATE_KEY);
          SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          timeFormat.setTimeZone(TimeZone.getTimeZone("GMT-4"));
          String now = timeFormat.format(new Date());


          String publicKeyY = req.params().get("key");
          String messageM = req.params().get("message");
          String resString = TEAM_INFO;

          if (messageM == null || publicKeyY == null || 
              messageM.isEmpty() || publicKeyY.isEmpty()) {
              resString = "Parameters invalid!";
          } else {
              resString = pdc.decrypt(messageM, publicKeyY);
          }

          resString = TEAM_INFO + now + "\n" + resString + "\n";

          req.response().end(resString);
      }
    });
    t.start();
  }
}



