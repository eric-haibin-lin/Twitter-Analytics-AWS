import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;

public class FixCensorMapper {
	public static void main(String[] args) throws FileNotFoundException {
		TextProcessor textProcessor = new TextProcessor();
		String line = null;
		try{
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
			/* do text processing on each record */
			while((line = bufferedReader.readLine()) != null){
				String censoredLine = textProcessor.TextCensor(line);
				System.out.println(censoredLine);
				/* Just to see which ones are wrong */
				if (!censoredLine.equals(line)) {
					System.err.println("Original: " + line);
					System.err.println("Now: " + censoredLine);
				}
			}
			bufferedReader.close();
		}catch(Exception err){
			err.printStackTrace();
		}
	}
}