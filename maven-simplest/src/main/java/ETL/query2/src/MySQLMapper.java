import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONObject;

public class MySQLMapper {
	private final static long TIME_FILTER = 1397952000;
	public static void main(String[] args) throws FileNotFoundException {
		TextProcessor textProcessor = new TextProcessor();
		String line = null;
		String patternFrom = "EEE MMM dd HH:mm:ss +0000 yyyy";
		String patternTo = "yyyy-MM-dd+HH:mm:ss";
		SimpleDateFormat formatterFrom;
		SimpleDateFormat formatterTo;
		formatterFrom = new SimpleDateFormat(patternFrom);
		formatterTo = new SimpleDateFormat(patternTo);
		try{
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
			/* do text processing on each record */
			while((line = bufferedReader.readLine()) != null){
				try{
					JSONObject result = new JSONObject();
					JSONObject jsonObj = new JSONObject(line);
				    Date date = formatterFrom.parse(jsonObj.getString("created_at"));
				    long epoch = date.getTime() / 1000;
				    if (epoch >= TIME_FILTER){
				    	String text = jsonObj.getString("text");
				    	String uid = jsonObj.getJSONObject("user").getString("id_str");
				    	String tid = jsonObj.getString("id_str");
						result.put("score", String.valueOf(textProcessor.SentimentScore(text)));
						result.put("text", textProcessor.TextCensor(text));
						result.put("original_text", text);
						result.put("uid", uid);
						result.put("tid", tid);
						result.put("time", formatterTo.format(date));
						result.put("epoch", String.valueOf(epoch));
						System.out.println(result.toString());
				    }
				} catch(Exception err){
					err.printStackTrace();
				}
			}
			bufferedReader.close();
		}catch(Exception err){
			err.printStackTrace();
		}
	}
}