import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;

public class HBaseMapper {
    public static void main(String[] args) throws FileNotFoundException {
        String line = null;
        try {
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(System.in));
            while ((line = bufferedReader.readLine()) != null) {
                /* Just output. The reducer needs result sorted by key. */
                System.out.println(line);
            }
            bufferedReader.close();
        } catch (Exception err) {
            err.printStackTrace();
        }
    }
}

