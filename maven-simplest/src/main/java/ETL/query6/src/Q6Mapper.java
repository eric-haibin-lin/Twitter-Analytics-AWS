import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;

public class Q6Mapper {
  public static void main(String[] args) throws FileNotFoundException {
    String line = null;
    try{
      BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
			/* do text processing on each record */
      while((line = bufferedReader.readLine()) != null){
        if (!line.isEmpty()){
          int firstTab = line.indexOf("\t");
          String value = line.substring(firstTab + 1);
          int firstColon = value.indexOf(":");
          String tid = value.substring(0, firstColon);
          String rest = value.substring(firstColon + 1);
          int secondColon = rest.indexOf(":");
          String text = rest.substring(secondColon + 1);
          System.out.println(tid + "\t" + text);
        }
      }
      bufferedReader.close();
    }catch(Exception err){
      err.printStackTrace();
    }
  }
}