import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import org.json.JSONObject;

public class Main {

	public static void main(String[] args) {
		TextProcessor textProcessor = new TextProcessor();
		String text = "RT @AMAZlNGPICTURES: \"Blood Moon\" a magical natural phenomenon http://t.co/9iYoLuNXQH";
		System.out.println(textProcessor.SentimentScore(text) + "," +textProcessor.TextCensor(text));
		
		text = "I love Cloud compz... cloud TAs are the best... Yinz shld tell yr frnz: TAKE CLOUD COMPUTING NEXT SEMESTER!!! Awesome. It's cloudy tonight.";
		System.out.println(textProcessor.SentimentScore(text) + "," +textProcessor.TextCensor(text));
		long startTime = System.nanoTime();
		try {
			String line = null;
			FileReader fileReader = new FileReader("D:\\CMU\\15619\\part-00661");
			BufferedReader bufferedReader = new BufferedReader(fileReader);

			while ((line = bufferedReader.readLine()) != null) {
				JSONObject jsonObj = new JSONObject(line);
				text = (String) jsonObj.get("text");
				int score = textProcessor.SentimentScore(text);
				String censoredText = textProcessor.TextCensor(text);
				//System.out.println(score + "," + censoredText);
			}
			bufferedReader.close();
		} catch (FileNotFoundException ex) {
			System.out.println("File 'banned.txt' Not Found!");
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		long endTime = System.nanoTime();
		long duration = (endTime - startTime);
		System.out.println(duration / 1000000000.0 + " seconds");
	}
}
