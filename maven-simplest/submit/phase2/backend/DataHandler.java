package io.vertx.example;

/**
 * Created by Eric Haibin Lin on 28/10/15.
 */
public interface DataHandler {
  String getQuery2(String userId, String tweetTime);
  String getQuery3(String userId, String startDate, String endDate, String number);
  String getQuery4(String hashtag, Integer n);
}
