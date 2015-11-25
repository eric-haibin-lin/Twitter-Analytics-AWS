package io.vertx.example;

import org.apache.hadoop.hbase.util.Hash;

import javax.sql.DataSource;
import java.sql.*;
import java.util.*;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * Created by Eric Haibin Lin on 24/11/15.
 */
public class Transaction {

  private static DataSource ds = null;
  private static List<String> seqList = Arrays.asList("0", "1", "2", "3", "4", "5", "6");

  private static final String START_OPT = "s";
  private static final String END_OPT = "e";
  private static final String APPEND_OPT = "a";
  private static final String READ_OPT = "r";

  private Map<String, String> tagMap = new HashMap<>();
  private Map<String, String> result = new HashMap<>();
  private PriorityBlockingQueue<Request> requestQueue = new PriorityBlockingQueue<>();
  private final Object lock = new Object();
  private boolean hasEnded = false;

  public static void setDataSource(DataSource dataSource){
    ds = dataSource;
  }

  public Transaction() {
    Thread t = new Thread(new Runnable() {
      @Override
      public void run() {
        synchronized (lock){
          int i = 0;
          while (true){
            while (requestQueue.isEmpty() || !requestQueue.peek().seq.equals(seqList.get(i))) {
              try {
                lock.wait();
              } catch (InterruptedException e) {
                e.printStackTrace();
              }
            }
            Request request = requestQueue.poll();
            switch (request.opt){
              case READ_OPT:
                processRead(request);
                break;
              case APPEND_OPT:
                tagMap.put(request.tweetId, request.tag);
                break;
              case END_OPT:
                hasEnded = true;
                lock.notifyAll();
                return;
              default:
                break;
            }
            i++;
          }
        }
      }
    });
    t.start();
  }

  private void processRead(Request request){
    String tweetId = request.tweetId;
    String resString = "";
    try {
      Connection con = ds.getConnection();
      Statement sql_statement = con.createStatement();
      String q = tweetId;
      String query = "SELECT r FROM q6 WHERE q = '" + q +"'";
      ResultSet result = sql_statement.executeQuery(query);

      if (result.next()) {
        Blob b = result.getBlob("r");
        long l = b.length();
        byte[] bytes = b.getBytes(1, (int) l);
        resString = new String(bytes);
      }
      if (result != null) result.close();
      if(sql_statement != null) sql_statement.close();
      if(con != null) con.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    if (tagMap.containsKey(tweetId)){
      resString += tagMap.get(tweetId);
    }
    result.put(tweetId, resString);
    lock.notifyAll();
  }

  public String read(String seq, String tweetId) {
    requestQueue.offer(new Request(seq, "", tweetId, READ_OPT));
    String resString;
    synchronized (lock){
      lock.notifyAll();
      while (!result.containsKey(tweetId)){
        try {
          lock.wait();
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
      resString = result.get(tweetId);
    }
    return resString;
  }

  public void execute(String seq, String tweetId, String opt, String tag) {
    synchronized (lock){
      switch (opt){
        case START_OPT:
          requestQueue.offer(new Request("0", tag, tweetId, opt));
          break;
        default:
          requestQueue.offer(new Request(seq, tag, tweetId, opt));
      }
      lock.notifyAll();
    }
  }

  public void end() {
    requestQueue.offer(new Request("6", "", "", END_OPT));
    synchronized (lock){
      lock.notifyAll();
      while (!hasEnded){
        try {
          lock.wait();
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    }
  }
}
