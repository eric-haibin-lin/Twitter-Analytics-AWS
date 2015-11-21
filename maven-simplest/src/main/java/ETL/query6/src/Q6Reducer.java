import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * This is the reducer for MySQL data
 * @author Yifan
 */
public class Q6Reducer {

  public static void main(String[] args) {
    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    String line = null;
    String prevId = "";
    String prevContent = "";
    try {
      while((line = bufferedReader.readLine()) != null){
        try{
          String[] fields = line.split("\t");
          String tid = fields[0];
          String content = fields[1];
          if (tid.equals(prevId)){
            //ignore duplicated tweets
            continue;
          } else {
            if (!prevId.isEmpty()){
              //print results for previous tid
              System.out.println(prevId + "\t" + prevContent);
            }
            prevId = tid;
            prevContent = content;
          }
        } catch (Exception e){
          e.printStackTrace();
        }
      }
      //print results for the last tid
      System.out.println(prevId + "\t" + prevContent);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}