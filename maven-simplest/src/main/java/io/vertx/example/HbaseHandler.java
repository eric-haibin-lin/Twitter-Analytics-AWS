package io.vertx.example;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.InclusiveStopFilter;
import org.apache.hadoop.hbase.filter.RandomRowFilter;
import org.apache.hadoop.hbase.util.Bytes;
import org.json.JSONObject;
import java.io.IOException;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.text.ParseException;

import java.math.BigInteger;
import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Arrays;


public class HbaseHandler implements DataHandler {

  private static final byte[] DATA = "d".getBytes();
  private static final byte[] RESULT = "r".getBytes();

  private static final byte[] DATA_Q4 = "d".getBytes();
  private static final byte[] RESULT_Q4 = "v".getBytes();
  
  private static final String TABLE_NAME = "q2";
  private static final String TABLE_NAME_Q3 = "q3";
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

  public String getQuery3(String userId, String startDate,
                          String endDate, String number) {
    String resString = "";
    int n = Integer.parseInt(number);
    try {
        HTableInterface tweetTable = pool.getTable(TABLE_NAME_Q3);

        String patternFrom = "yyyy-MM-dd";
        String patternTo = "yyMMdd";
        SimpleDateFormat formatterFrom = new SimpleDateFormat(patternFrom);
        SimpleDateFormat formatterTo = new SimpleDateFormat(patternTo);
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        String qs = "";
        String qe = "";
        try {
            cal1.setTime(formatterFrom.parse(startDate));
            cal2.setTime(formatterFrom.parse(endDate));
            qs = userId + formatterTo.format(cal1.getTime());
            qe = userId + formatterTo.format(cal2.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //System.out.println(qs + "  -  " + qe);
        //creating a scan object with start and stop row keys (inclusive)
        InclusiveStopFilter filter = new InclusiveStopFilter(Bytes.toBytes(qe));
        Scan scan = new Scan(Bytes.toBytes(qs));
        scan.setFilter(filter);
        ResultScanner scanner = tweetTable.getScanner(scan);

        List<ResultQ3> resultList = new ArrayList<ResultQ3>();
        for (Result rowResult = scanner.next(); rowResult != null; rowResult = scanner.next())
        {
            try {
                String key = Bytes.toString(rowResult.getRow());
                System.out.println(key);
                String dateString = key.substring(key.length() - 6, key.length());
                Calendar tempCal = Calendar.getInstance();
                tempCal.setTime(formatterTo.parse(dateString));
                dateString = formatterFrom.format(tempCal.getTime());

                String record = new String(rowResult.getValue(DATA, RESULT), "UTF-8");

                String[] resStringArr = record.split("\b");
                for (String res : resStringArr) {
                    //System.out.println(res);
                    String[] elem = res.split(",");
                    resultList.add(new ResultQ3(dateString, Integer.parseInt(elem[0]), 
                                                elem[1], res.replace("\\n", "\n")));
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        pool.putTable(tweetTable);
        Collections.sort(resultList, new ResultQ3());

        resString += "Positive Tweets\n";
        String negResString = "Negative Tweets\n";
        int posCount = 0;
        int negCount = 0;
        for (int i = 0; i < resultList.size(); i++) {
            if (resultList.get(i).getScore() > 0 && posCount <= n) {
                resString += resultList.get(i).toString();
                posCount++;
            }
            if (resultList.get(resultList.size() - i - 1).getScore() < 0 && negCount <= n) {
                negResString += resultList.get(resultList.size() - i - 1).toString();
                negCount++;
            }
            if (posCount > n && negCount > n) {
                break;
            }
        }
        resString += "\n" + negResString;

    } catch (IOException e) {
        e.printStackTrace();
    }
    return resString;
  }

  public String warmup(String query, String ratio){
    HTableInterface table = pool.getTable(query);
    Float probability = Float.parseFloat(ratio);
    Scan scan = new Scan();
    RandomRowFilter randomRowFilter = new RandomRowFilter(probability);
    scan.setFilter(randomRowFilter);
    try {
      ResultScanner scanner = table.getScanner(scan);
      for (Result res : scanner) {
        System.out.println(res);
      }
      scanner.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return "";
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
