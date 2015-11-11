import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;

import org.json.JSONArray;
import org.json.JSONObject;

public class MySQLMapperQ4 {
    public static void main(String[] args) throws FileNotFoundException {
        String line = null;
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
            while ((line = bufferedReader.readLine()) != null) {
                try {
                    JSONObject jsonInput = new JSONObject(line);
                    /* Get data field */
                    String[] dateTime = jsonInput.getString("time").split("\\+");
                    String date = dateTime[0];
                    String time = dateTime[1];
                    String text = jsonInput.getString("text");
                    String uid = jsonInput.getString("uid");
                    JSONArray hashtags = new JSONArray(jsonInput.getString("hashtags"));

                    /* Escape \,\t,\r\n,\r,\n */
                    text = text.replace("\\", "\\\\").replace("\t", "\\t")
                        .replace("\r\n", "\n").replace("\n", "\\n")
                        .replace("\r", "\\n");

                    /* Get all tags */
                    if (hashtags.length() > 0) {
                        for (int i = 0; i < hashtags.length(); i++) {
                            JSONObject jsonTag = hashtags.getJSONObject(i);
                            String tag = jsonTag.getString("text");
                            /* Key is tag+'\b'+date to aggregate tag on date  */
                            System.out.println(tag+'\b'+date+'\t'+time+'\t'+uid+'\t'+text);
                        }
                    }
                } catch (Exception err) {
                    err.printStackTrace();
                }
            }
            bufferedReader.close();
        } catch (Exception err) {
            err.printStackTrace();
        }
    }
}

