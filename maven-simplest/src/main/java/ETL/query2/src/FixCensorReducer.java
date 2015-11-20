import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;

public class FixCensorReducer {
	public static void main(String[] args) throws FileNotFoundException {
		String line = null;
		try{
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
			/* do text processing on each record */
			while((line = bufferedReader.readLine()) != null){
				System.out.println(line);
			}
			bufferedReader.close();
		}catch(Exception err){
			err.printStackTrace();
		}
	}
}
