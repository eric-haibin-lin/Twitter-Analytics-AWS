package io.vertx.example;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.json.JSONObject;
import java.io.IOException;


public class HbaseHandler implements DataHandler {

  private static final byte[] DATA = "data".getBytes();
  private static final byte[] TEXT = "text".getBytes();
  private static final byte[] TID = "tid".getBytes();
  private static final byte[] SCORE = "score".getBytes();
  private static final String TABLE_NAME = "tweet";

  private HTable tweetTable;

  /**
   * initialize hbase connector
   */
  private void init(){
    try{
      Configuration config = HBaseConfiguration.create();
      config.set("hbase.zookeeper.quorum", "localhost");
      HBaseAdmin admin = new HBaseAdmin(config);
      tweetTable = new HTable(config, TABLE_NAME);
    } catch (IOException e){
      e.printStackTrace();
    }
  }

  public HbaseHandler(){
    init();
  }

  @Override
  public String getQuery2(String userId, String tweetTime) {
    String userTimeStart = userId + tweetTime;
    String userTimeEnd = userTimeStart + "a";
    String result = "";
    try {
      //creating a scan object with start and stop row keys
      Scan scan = new Scan(Bytes.toBytes(userTimeStart),Bytes.toBytes(userTimeEnd));

      //And then you can get a scanner object and iterate through your results
      ResultScanner scanner = tweetTable.getScanner(scan);
      for (Result rowResult = scanner.next(); rowResult != null; rowResult = scanner.next())
      {
        String textJson = new String(rowResult.getValue(DATA, TEXT), "UTF-8");
        String tid = new String(rowResult.getValue(DATA, TID), "UTF-8");
        String score = new String(rowResult.getValue(DATA, SCORE), "UTF-8");
        result += tid + ":" + score + ":" + getText(textJson) + "\n";
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return result;
  }

  /**
   * get the text field from a json object
   * @param textStr the string of the json object
   * @return the text field
   */
  private String getText(String textStr){
    String result = null;
    try {
      JSONObject json = new JSONObject(textStr);
      result = json.getString("text");
      System.out.println(result);
    } catch (Exception e){
      return "Exception during getText";
    }
    return result;
  }

}



