import java.io.BufferedReader;
import java.io.InputStreamReader;
import org.json.JSONObject;

public class Mapper {

	public static void main(String[] args) {
		TextProcessor textProcessor = new TextProcessor();
		try{
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
	        String line;
	        //While we have input on stdin
			while((line = bufferedReader.readLine()) != null){
				try{
					JSONObject jsonObj = new JSONObject(line);
					String text = (String) jsonObj.get("text");
					int score = textProcessor.SentimentScore(text);
					String censoredText = textProcessor.TextCensor(text);
					System.out.println(score + "," + censoredText);
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