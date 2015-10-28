package io.vertx.example;

import io.vertx.core.Vertx;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
public class HbaseHandler implements DataHandler {

  private static final byte[] DATA = "info".getBytes();
  private static final byte[] TEXT = "text".getBytes();
  private static final String TABLE_NAME = "tweets";

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
    String row = "10051589832014-05-16+09:53:17467241367769714688";
    Get get = new Get(Bytes.toBytes(row));
    String result = null;
    try {
      Result hResult = tweetTable.get(get);

      //TODO parse json object getText(result)
      result = new String(hResult.getValue(DATA, TEXT), "UTF-8");
    } catch (IOException e) {
      e.printStackTrace();
    }
    return result;
  }

  private String getText(String textStr){
    JSONObject json = new JSONObject(textStr);
    return json.getString("text");
  }

}



