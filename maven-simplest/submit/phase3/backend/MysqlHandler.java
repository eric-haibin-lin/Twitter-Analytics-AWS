package io.vertx.example;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.sql.*;
import org.json.JSONObject;

import javax.sql.DataSource;
import org.apache.commons.dbcp2.BasicDataSource;

import java.text.SimpleDateFormat;
import java.util.*;
import java.text.ParseException;

import java.math.BigInteger;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
public class MysqlHandler implements DataHandler {

  private static final String userName = "root";
  private static final String passWord = "coding15619";
  private static final String MYSQL_URL = "jdbc:mysql://localhost:3306/tweet";

  private static final DataSource ds = DBCPDataSourceFactory.getDataSource();
  private Map<String, Transaction> transactionMap = new HashMap<>();
  private Map<String, Map<String, String>> appendMap = new ConcurrentHashMap<>();
  private List<String> garbage = new ArrayList<>();

  private static final String END_OPT = "e";
  private static final String READ_OPT = "r";
  private static final String START_OPT = "s";
  private static final String APPEND_OPT = "a";

  public static class DBCPDataSourceFactory {
    public static DataSource getDataSource(){
      BasicDataSource ds = new BasicDataSource();
      ds.setDriverClassName("com.mysql.jdbc.Driver");
      ds.setUrl(MYSQL_URL);
      ds.setUsername(userName);
      ds.setPassword(passWord);
      return ds;
    }
  }

  public MysqlHandler() {
    Transaction.setDataSource(ds);
  }

  public static Connection getConnection() {
    Connection conSql = null;
    try {
        conSql = DriverManager.getConnection(MYSQL_URL, userName, passWord);
    } catch (SQLException e) {
	    e.printStackTrace();
    }
    return conSql;
  }

  @Override
	public String getQuery4(String hashtag, Integer n) {
		String resString = "";
		try {
			Connection con = ds.getConnection();

			Statement sql_statement = con.createStatement();
			String q = hashtag;
			String query = "SELECT r FROM q4 WHERE q = '" + q + "'";
			ResultSet result = sql_statement.executeQuery(query);

			if (result.next()) {

				Blob b = result.getBlob("r");
				long l = b.length();
				byte[] bytes = b.getBytes(1, (int) l);
				String record = new String (bytes);
				record = record.replace("\\t", "\t").replace("\\n", "\n")
					.replace("\\\\", "\\");
				String[] lines = record.split("\b");
				resString = "";
				for (int i = 0; i < lines.length && i < n; i++) {
					resString += lines[i] + "\n";
				}
			}

			if (result != null)
				result.close();
			if (sql_statement != null)
				sql_statement.close();
			if (con != null)
				con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resString;
	}
	
	@Override
	public String getQuery5(String uid_min, String uid_max) {
		String resString = "";
		Integer count1=0, count2=0;
		if (Long.parseLong(uid_min) > Long.parseLong(uid_max)) {
			return "0";
		}
		try {
			Connection con = ds.getConnection();

			Statement sql_statement = con.createStatement();
			String query = "SELECT count FROM q5 WHERE uid = (SELECT MAX(uid) FROM q5 WHERE uid < "+uid_min+")"+
			"union select count from q5 where uid = (SELECT MAX(uid) FROM q5 WHERE uid <= "+uid_max+")";
			ResultSet result = sql_statement.executeQuery(query);

			if (result.next()) {
				count1 = result.getInt(1);
				if (result.next()) {
					count2 = result.getInt(1);
					Integer n = count2 - count1;
					resString = n.toString();
				}
				else {
					resString = count1.toString();
				}
			}
			else {
				resString = "0";
			}

			if (result != null)
				result.close();
			if (sql_statement != null)
				sql_statement.close();
			if (con != null)
				con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resString;
	}

  /*
   * This was intended for query 6 but replaced by getQuery6Fast()
   */
  @Override
  public String getQuery6(String opt, String tid, String seq, String tweetId, String tag){
    Transaction transaction;
    String resString = "";
//    if (!transactionMap.containsKey(tid)){
//      transaction = new Transaction();
//      transactionMap.put(tid, transaction);
//    } else {
//      transaction = transactionMap.get(tid);
//    }
//
//    if (opt.equals(READ_OPT)) {
//      resString = transaction.read(seq, tweetId);
//      return resString;
//    } else if (opt.equals(END_OPT)){
//      resString = "0";
//      transaction.end();
//      transactionMap.remove(tid);
//    } else {
//      transaction.execute(seq, tweetId, opt, tag);
//      //return 0 for start and end opt
//      resString = tag == null ? "0" : tag;
//    }
    return resString + "\n";
  }

  @Override
  public String getQuery6Fast(String opt, String tid, String seq, String tweetId, String tag) {
    String resString = "0\n";
    Map<String, String> map;
    switch (opt){
      case START_OPT:
        if (!appendMap.containsKey(tid)){
          appendMap.put(tid, new HashMap<>());
        }
        break;
      case END_OPT:
        try {
          Thread.sleep(1);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        appendMap.remove(tid);
        break;
      case READ_OPT:
        Integer seqNum = Integer.parseInt(seq);
        map = getAppendEntries(tid);
        resString = getTweet(tweetId);
        if (map.size() != seqNum - 1){
          try {
            Thread.sleep(1);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }
        String appendTag = "";
        if (map.containsKey(tweetId)){
          appendTag = map.get(tweetId);
          resString += "ILOVE15619!" + appendTag + "\n";
        } else {
          resString += "\n";
        }
        map.put(tweetId + "-", "");
        break;
      case APPEND_OPT:
        map = getAppendEntries(tid);
        map.put(tweetId, tag.substring(11));
        resString = tag + "\n";
        break;
    }
    return resString;
  }

  private Map<String, String> getAppendEntries(String tid) {
    Map<String, String> map;
    if (!appendMap.containsKey(tid)){
      map = new HashMap<>();
      appendMap.put(tid, map);
    } else {
      map = appendMap.get(tid);
    }
    return map;
  }

  private String getTweet(String tweetId) {
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
    return resString;
  }

  @Override
  public String getQuery3(String userId, String startDate,
                          String endDate, String number) {
    String resString = "";
    int n = Integer.parseInt(number);
    try {
        Connection con = ds.getConnection();

        Statement sql_statement = con.createStatement();
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
        String query = "SELECT q,r FROM q3 WHERE q BETWEEN " + qs +" AND " + qe;
        ResultSet result = sql_statement.executeQuery(query);
        List<ResultQ3> resultList = new ArrayList<ResultQ3>();

        while (result.next()) {
            try {
                String s = result.getString("q");
                String dateString = s.substring(s.length() - 6, s.length());
                Calendar tempCal = Calendar.getInstance();
                tempCal.setTime(formatterTo.parse(dateString));
                dateString = formatterFrom.format(tempCal.getTime());
                Blob b = result.getBlob("r");
                long l = b.length();
                byte[] bytes = b.getBytes(1, (int) l);
                String blobString = new String(bytes);
                blobString = blobString.substring(0, blobString.length() - 1);
                String[] resStringArr = blobString.split("\b");
                for (String res : resStringArr) {
                    String[] elem = res.split(",");
                    try {
                        resultList.add(new ResultQ3(dateString, Integer.parseInt(elem[0]), 
                                                elem[1], res));
                    } catch (ArrayIndexOutOfBoundsException e1) {
                        System.out.println(s + " " + res);
                    }
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        Collections.sort(resultList, new ResultQ3());

        resString += "Positive Tweets\n";
        String negResString = "Negative Tweets\n";
        int posCount = 0;
        int negCount = 0;
        for (int i = 0; i < resultList.size(); i++) {
            if (resultList.get(i).getScore() > 0 && posCount < n) {
                resString += resultList.get(i).toString();
                posCount++;
            }
            if (resultList.get(resultList.size() - i - 1).getScore() < 0 && negCount < n) {
                negResString += resultList.get(resultList.size() - i - 1).toString();
                negCount++;
            }
            if (posCount >= n && negCount >= n) {
                break;
            }
        }

        resString += "\n" + negResString;

        if (result != null) result.close();
        if(sql_statement != null) sql_statement.close();
        if(con != null) con.close();

    } catch (SQLException e) {
        e.printStackTrace();
    }
    return resString;
  }

  @Override
  public String getQuery2(String userId, String tweetTime) {
    String resString = "";
    try {
        Connection con = ds.getConnection();

        Statement sql_statement = con.createStatement();
        String q = userId + "_" + tweetTime;
        String query = "SELECT r FROM q2 WHERE q = '" + q +"'";
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
    return resString;
  }
}
