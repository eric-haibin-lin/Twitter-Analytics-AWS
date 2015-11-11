import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * This is the reducer for MySQL data of Q3
 * @author Li Chen
 */
public class MySQLReducerQ3 {

    public static void main(String[] args) {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        String line = null;
        List<String> resultList = new ArrayList<String>();
        String prevKey = "";
        try {
            //First line
            line = bufferedReader.readLine();
            if (line != null) {
                String firstKey = line.split("\t")[0];
                prevKey = firstKey;
                do {
                    try{
                        String[] fields = line.split("\t");
                        String key = fields[0];
                        String content = fields[1];
                        if (key.equals(prevKey)){
                          resultList.add(content);
                        } else {
                            //print results for previous key (uid)
                            printResult(resultList, prevKey);
                            resultList = new ArrayList<String>();
                            prevKey = key;
                            resultList.add(content);
                          }
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                } while((line = bufferedReader.readLine()) != null);
            }
      //print results for the last key
      printResult(resultList, prevKey);
        } catch (IOException e) {
            e.printStackTrace();
        }   
    }

  private static void printResult(List<String> resultList, String prevKey) {
    StringBuilder output = new StringBuilder();
    output.append(prevKey);
    output.append("\t");
    for (String result : resultList){
      output.append(result + "\b");
    }
    //output.append("\\n");
    System.out.println(output.toString());
  }
}

