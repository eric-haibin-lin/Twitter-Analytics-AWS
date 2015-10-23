import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Reducer {

	public static void main(String[] args) {
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
		try {
			String line;
			while((line = bufferedReader.readLine()) != null){
			    System.out.println(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
}