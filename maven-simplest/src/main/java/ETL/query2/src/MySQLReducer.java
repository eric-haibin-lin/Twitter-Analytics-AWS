import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.json.JSONObject;

/**
 * This is the reducer for MySQL data
 * @author Yifan
 */
public class MySQLReducer {

	public static void main(String[] args) {
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
		String line = null;
		try {		
			while((line = bufferedReader.readLine()) != null){
				try{
					//line = line.split("\t")[1];
					JSONObject jsonObj = new JSONObject(line);
					StringBuilder sb = new StringBuilder();
					sb.append(jsonObj.getString("uid"));
					sb.append(jsonObj.get("score"));
					sb.append(jsonObj.get("time"));
					sb.append(jsonObj.get("epoch"));
					sb.append(jsonObj.get("text"));
					System.out.print(sb.toString());
				} catch (Exception e){
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
}