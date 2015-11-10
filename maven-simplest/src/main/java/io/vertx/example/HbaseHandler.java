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

  private static final byte[] DATA_Q4 = "d".getBytes();
  private static final byte[] RESULT_Q4 = "v".getBytes();
  
  private static final String TABLE_NAME = "q2_32_snap_row";
  private static final String TABLE_NAME_Q4 = "q4";
  
  private static HTablePool pool;


  /**
   * initialize hbase connector
   */
  private void init(){
    try{
      Configuration config = HBaseConfiguration.create();
      config.set("hbase.zookeeper.quorum", "localhost");
      HBaseAdmin admin = new HBaseAdmin(config);
      
      pool = new HTablePool(config, 40);
      
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

    return null;
  }
  
  @Override
  public String getQuery4(String hashtag, Integer n){
	    String rowKey = hashtag;
	    String result = "";
	    try {
	      HTableInterface q4Table = pool.getTable(TABLE_NAME_Q4);
	      Get get = new Get(Bytes.toBytes(rowKey));
	      Result hResult = q4Table.get(get);
	      if (hResult == null) {
                  System.out.println("Cannot find rowKey: " + rowKey);
                  return "";
              }

	      byte[] value = hResult.getValue(DATA_Q4, RESULT_Q4);
              if (value == null) {
                  System.out.println("Cannot get value for rowKey: "+ rowKey);
                  return "";
              }
              String record = new String(value, "UTF-8");

	      pool.putTable(q4Table);
	      record = record.replace("\\t", "\t")
	    		  .replace("\\n", "\n").replace("\\\\", "\\");
	      String [] lines = record.split("\b");
	      for(int i = 0 ; i < lines.length && i < n; i++ ) {
	    	  result += lines[i] + "\n";
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