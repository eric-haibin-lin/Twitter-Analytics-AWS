package io.vertx.example;

import io.vertx.core.Vertx;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
public class HbaseHandler implements DataHandler {

  private static final byte[] DATA = "data".getBytes();
  private static final byte[] TEXT = "text".getBytes();
  private static final String TABLE_NAME = "tweettest";

  private HTable tweetTable;

  private void init(){
    try{
      Configuration config = HBaseConfiguration.create();
      config.set("hbase.zookeeper.quorum", "localhost");
      //config.set("hbase.zookeeper.property.clientPort", "2181");
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
        //TODO handle more column information retrieval
        String textJson = new String(rowResult.getValue(DATA, TEXT), "UTF-8");
        result += getText(textJson) + "\n";
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return result;
  }

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



