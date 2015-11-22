import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Q5FilterReducer {
	public static void main(String[] args) {
		String line = null;
		try {
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
			while ((line = bufferedReader.readLine()) != null) {
				System.out.println(line);
			}
			bufferedReader.close();
		} catch (Exception err) {
			err.printStackTrace();
		}
	}
}
