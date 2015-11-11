import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONObject;

public class Q2HBaseMapper {
	private final static long TIME_FILTER = 1397952000;
	public static void main(String[] args) throws FileNotFoundException {
		TextProcessor textProcessor = new TextProcessor();
		String line = null;
		String patternFrom = "EEE MMM dd HH:mm:ss +0000 yyyy";
		String patternTo = "yyyy-MM-dd HH:mm:ss";
		SimpleDateFormat formatterFrom;
		SimpleDateFormat formatterTo;
		formatterFrom = new SimpleDateFormat(patternFrom);
		formatterTo = new SimpleDateFormat(patternTo);
		try{
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
			/* do text processing on each record */
			while((line = bufferedReader.readLine()) != null){
				try{
					JSONObject tweet = new JSONObject(line);
				    Date date = formatterFrom.parse(tweet.getString("created_at"));
				    long epoch = date.getTime() / 1000;
				    if (epoch >= TIME_FILTER){
				    	String text = tweet.getString("text");
				    	String uid = tweet.getJSONObject("user").getString("id_str");
				    	String tid = tweet.getString("id_str");
				    	String censoredText = textProcessor.TextCensor(text);
				    	censoredText = censoredText.replace("\\", "\\\\");
				    	censoredText = censoredText.replace("\t", "\\t");
				    	censoredText = censoredText.replace("\r\n", "\n");
				    	censoredText = censoredText.replace("\n", "\\n");
				    	censoredText = censoredText.replace("\r", "\\n");
				    	String score = String.valueOf(textProcessor.SentimentScore(text));
						System.out.println(uid + "_" + formatterTo.format(date) + "\t" + tid + "\t" + score + ":" + censoredText);						
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