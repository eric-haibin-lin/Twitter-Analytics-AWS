package io.vertx.example;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServerRequest;


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

  public static void main(String[] args) {

    DataHandler dataHandler;
    if (MODE.equals(HBASE_MODE)){
      dataHandler = new HbaseHandler();
    } else {
      dataHandler = new MysqlHandler();
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
    String userId = req.params().get(USER_ID);
    String tweetTime = req.params().get(TWEET_TIME);
    String resString = TEAM_INFO;

    if (userId == null || tweetTime == null || userId.isEmpty() || tweetTime.isEmpty()) {
      resString = "Parameters invalid!";
    } else {
      System.out.println(tweetTime);
      tweetTime = tweetTime.replace(" ", "+");
      resString = TEAM_INFO + dataHandler.getQuery2(userId, tweetTime);
    }
    req.response().headers().add("Content-Type", "text/plain; charset=UTF-8");
    req.response().end(resString);
  }

  /**
   * handles query 1
   * @param req
   */
  private static void handleQ1Request(HttpServerRequest req) {
    req.response().end("auth result");
  }

}



