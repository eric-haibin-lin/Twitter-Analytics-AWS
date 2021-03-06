import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONObject;

/**
 * This is the mapper for MySQL data of Q3
 * @author Li Chen
 */
public class MySQLMapperQ3 {
    public static void main(String[] args) throws FileNotFoundException {
        TextProcessorQ3 textProcessor = new TextProcessorQ3();
        String line = null;
        String patternFrom = "EEE MMM dd HH:mm:ss +0000 yyyy";
        String patternTo = "yyMMdd";
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
                    String text = tweet.getString("text");
                    String uid = tweet.getJSONObject("user").getString("id_str");
                    String tid = tweet.getString("id_str");
                    int followers_count = tweet.getJSONObject("user").getInt("followers_count");
                    String censoredText = textProcessor.TextCensor(text);
                    censoredText = censoredText.replace("\\", "\\\\");
                    censoredText = censoredText.replace("\t", "\\t");
                    censoredText = censoredText.replace("\r\n", "\n");
                    censoredText = censoredText.replace("\n", "\\n");
                    censoredText = censoredText.replace("\r", "\\n");
                    String score = String.valueOf(textProcessor.SentimentScore(text) * (1 + followers_count));
                    System.out.println(uid + formatterTo.format(date) + "\t" + score + "," + tid + "," + censoredText);                      
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

