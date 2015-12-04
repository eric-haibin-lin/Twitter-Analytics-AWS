import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import org.json.JSONObject;

public class Q6Mapper {
  public static void main(String[] args) throws FileNotFoundException {
    TextProcessor textProcessor = new TextProcessor();
    String line = null;
    String patternFrom = "EEE MMM dd HH:mm:ss +0000 yyyy";
    SimpleDateFormat formatterFrom;
    formatterFrom = new SimpleDateFormat(patternFrom);
    try{
      BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
			/* do text processing on each record */
      while((line = bufferedReader.readLine()) != null){
        try{
          JSONObject tweet = new JSONObject(line);
          String text = tweet.getString("text");
          String tid = tweet.getString("id_str");
          String censoredText = textProcessor.TextCensor(text);
          censoredText = censoredText.replace("\\", "\\\\");
          censoredText = censoredText.replace("\t", "\\t");
          censoredText = censoredText.replace("\r\n", "\n");
          censoredText = censoredText.replace("\n", "\\n");
          censoredText = censoredText.replace("\r", "\\n");
          System.out.println(tid + "\t" + censoredText);
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