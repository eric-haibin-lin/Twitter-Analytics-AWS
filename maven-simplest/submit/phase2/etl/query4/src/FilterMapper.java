import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONObject;

public class FilterMapper {
    public static void main(String[] args) throws FileNotFoundException {
        String line = null;
        String patternFrom = "EEE MMM dd HH:mm:ss +0000 yyyy";
        String patternTo = "yyyy-MM-dd+HH:mm:ss";
        SimpleDateFormat formatterFrom = new SimpleDateFormat(patternFrom);
        SimpleDateFormat formatterTo = new SimpleDateFormat(patternTo);
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
            while ((line = bufferedReader.readLine()) != null) {
                try {
                    JSONObject result = new JSONObject();
                    JSONObject jsonObj = new JSONObject(line);
                    Date date = formatterFrom.parse(jsonObj.getString("created_at"));
                    String text = jsonObj.getString("text");
                    String uid = jsonObj.getJSONObject("user").getString("id_str");
                    JSONArray hashtags = jsonObj.getJSONObject("entities").getJSONArray("hashtags");
                    /*                  text = text.replace("\\", "\\\\").replace("\t", "\\t")
                     *                                              .replace("\r\n", "\n").replace("\n", "\\n")
                     *                                                                          .replace("\r", "\\n");*/

                    if (hashtags.length() > 0) {
                        for (int i = 0; i < hashtags.length(); i++) {
                            JSONObject tag = hashtags.getJSONObject(i);
                            tag.remove("indices");
                            hashtags.put(i, tag);
                        }
                        result.put("time", formatterTo.format(date));
                        result.put("text", text);
                        result.put("uid", uid);
                        result.put("hashtags", hashtags.toString());
                        System.out.println(result.toString());
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
