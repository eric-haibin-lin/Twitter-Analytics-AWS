package io.vertx.example;

import java.util.Comparator;

/**
 * Created by Eric Haibin Lin on 24/11/15.
 */
public class Request implements Comparable<Request>{

  String seq;
  String tag;
  String tweetId;
  String opt;

  public Request(String seq, String tag, String tweetId, String opt) {
    this.seq = seq;
    this.tag = tag;
    this.tweetId = tweetId;
    this.opt = opt;
  }

  @Override
  public int compareTo(Request o) {
    return this.seq.compareTo(o.seq);
  }
}
