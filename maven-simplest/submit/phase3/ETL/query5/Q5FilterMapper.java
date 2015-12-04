import java.io.BufferedReader;
import java.io.InputStreamReader;
import org.json.JSONObject;

public class Q5FilterMapper {
	public static void main(String[] args) {
		String line = null;
		try {
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
			while ((line = bufferedReader.readLine()) != null) {
				try {
					JSONObject jsonObj = new JSONObject(line);
					String uid = jsonObj.getJSONObject("user").getString("id_str");
					String tid = jsonObj.getString("id_str");
					System.out.println(uid+"\t"+tid);

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