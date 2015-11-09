import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * This is the reducer for MySQL data
 * @author Yifan
 */
public class HBaseReducer {

	public static void main(String[] args) {
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
		String line = null;
		List<Result> resultList = new ArrayList<Result>();
		List<String> idList = new ArrayList<String>();
		String prevKey = "";
		try {
			while((line = bufferedReader.readLine()) != null){
				try{
					String[] fields = line.split("\t");
					String key = fields[0];
					String tid = fields[1];
					String content = fields[2];
					if (key.equals(prevKey)){
            if (!idList.contains(tid)){
              resultList.add(new Result(tid, content));
              idList.add(tid);
            }
					} else {
            //print results for previous key
            printResult(resultList, prevKey);
            resultList = new ArrayList<Result>();
            idList = new ArrayList<String>();
            prevKey = key;
            resultList.add(new Result(tid, content));
            idList.add(tid);
          }
				} catch (Exception e){
					e.printStackTrace();
				}
			}
      //print results for the last key
      printResult(resultList, prevKey);
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}

  private static void printResult(List<Result> resultList, String prevKey) {
    Collections.sort(resultList, new Result());
    StringBuilder output = new StringBuilder();
    output.append(prevKey);
    output.append("\t");
    for (Result result : resultList){
      output.append(result.toString());
    }
    System.out.println(output.toString());
  }
}