import java.io.BufferedReader;
import java.io.InputStreamReader;
import org.json.JSONObject;

public class Q6FilterMapper {
  public static void main(String[] args) {
    String line = null;
    try {
      BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
      while ((line = bufferedReader.readLine()) != null) {
        try {
          JSONObject jsonObj = new JSONObject(line);
          String tid = jsonObj.getString("id_str");
          String text = jsonObj.getString("text");
          JSONObject newObj = new JSONObject();
          newObj.put("id_str", tid);
          newObj.put("text", text);
          System.out.println(newObj.toString());
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