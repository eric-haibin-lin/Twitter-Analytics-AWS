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
        List<ResultQ3ETL> resultList = new ArrayList<ResultQ3ETL>();
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
                        String[] contentSplit = content.split(",");
                        if (key.equals(prevKey)){
                          resultList.add(new ResultQ3ETL(
                            Integer.parseInt(contentSplit[0]), contentSplit[1], content));
                        } else {
                            //print results for previous key (uid)
                            printResult(resultList, prevKey);
                            resultList = new ArrayList<ResultQ3ETL>();
                            prevKey = key;
                            resultList.add(new ResultQ3ETL(
                            Integer.parseInt(contentSplit[0]), contentSplit[1], content));
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

  private static void printResult(List<ResultQ3ETL> resultList, String prevKey) {
    Collections.sort(resultList, new ResultQ3ETL());
    StringBuilder output = new StringBuilder();
    output.append(prevKey);
    output.append("\t");
    int posCount = 0;
    int negCount = 0;
    for (int i = 0; i < resultList.size(); i++) {
        if (resultList.get(i).getScore() > 0 && posCount < 10) {
            output.append(resultList.get(i).toString() + "\b");
            posCount++;
        }
        if (resultList.get(resultList.size() - i - 1).getScore() < 0 && negCount < 10) {
            output.append(resultList.get(resultList.size() - i - 1).toString() + "\b");
            negCount++;
        }
        if (posCount >= 10 && negCount >= 10) {
            break;
        }
    }
    if (posCount != 0 || negCount != 0) {
        System.out.println(output.toString());
    }
  }
}

