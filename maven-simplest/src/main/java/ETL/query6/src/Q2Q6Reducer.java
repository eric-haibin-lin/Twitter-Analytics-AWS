import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;

public class Q2Q6Reducer {
  public static void main(String[] args) throws FileNotFoundException {
    String line = null;
    try{
      BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
			/* do text processing on each record */
      while((line = bufferedReader.readLine()) != null){
        if (!line.isEmpty()){
          System.out.println(line);
        }
      }
      bufferedReader.close();
    }catch(Exception err){
      err.printStackTrace();
    }
  }
}
