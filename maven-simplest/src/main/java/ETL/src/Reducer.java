import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.json.JSONObject;

public class Reducer {

	private final static String DELIMITER = "@15619@delimiter@";
	private final static String NEWLINE = "@15619@newline@\n";

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
					sb.append(DELIMITER);
					sb.append(jsonObj.get("score"));
					sb.append(DELIMITER);
					sb.append(jsonObj.get("time"));
					sb.append(DELIMITER);
					sb.append(jsonObj.get("epoch"));
					sb.append(DELIMITER);
					sb.append(jsonObj.get("text"));
					sb.append(NEWLINE);
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