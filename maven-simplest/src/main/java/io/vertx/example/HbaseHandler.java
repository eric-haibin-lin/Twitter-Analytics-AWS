package io.vertx.example;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.json.JSONObject;
import java.io.IOException;


public class HbaseHandler implements DataHandler {

  private static final byte[] DATA = "d".getBytes();
  private static final byte[] RESULT = "r".getBytes();
  //TODO Table name CHANGE TO q2
  private static final String TABLE_NAME = "q2_32_snap_row";
  private static HTablePool pool;
  //private HTable tweetTable;

  /**
   * initialize hbase connector
   */
  private void init(){
    try{
      Configuration config = HBaseConfiguration.create();
      config.set("hbase.zookeeper.quorum", "ec2-54-172-150-4.compute-1.amazonaws.com");
      HBaseAdmin admin = new HBaseAdmin(config);
      //Integer.MAX_VALUE
      pool = new HTablePool(config, 40);
      //tweetTable = new HTable(config, TABLE_NAME);
      System.out.println("Done init HBase server");
    } catch (IOException e){
      e.printStackTrace();
    }
  }

  public HbaseHandler(){
    init();
  }

  @Override
  public String getQuery2(String userId, String tweetTime) {
    String rowKey = userId + "_" + tweetTime;
    String result = "";
    try {
      HTableInterface tweetTable = pool.getTable(TABLE_NAME);
      Get get = new Get(Bytes.toBytes(rowKey));
      Result hResult = tweetTable.get(get);
      String record = new String(hResult.getValue(DATA, RESULT), "UTF-8");
      pool.putTable(tweetTable);
      record = record.replace("\\n", "\n");
      result += record;
    } catch (IOException e) {
      e.printStackTrace();
    }
    return result;
  }

  public String getQuery3(){
    //String userTimeStart = userId + "_" + tweetTime;
    //String userTimeEnd = userTimeStart + "_a";
    //creating a scan object with start and stop row keys
    //Scan scan = new Scan(Bytes.toBytes(userTimeStart),Bytes.toBytes(userTimeEnd));
    //And then you can get a scanner object and iterate through your results
    //ResultScanner scanner = tweetTable.getScanner(scan);
    //for (Result rowResult = scanner.next(); rowResult != null; rowResult = scanner.next())
    //{
    //TODO possibly have to replace \\t with \t?
    //String record = new String(rowResult.getValue(DATA, RESULT), "UTF-8");
    //record = record.replace("\\n", "\n");
    //result += record;
    //}
    return null;
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