import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;

public class HBaseReducerQ4 {
    public static void main(String[] args) throws FileNotFoundException {
        String line = null;
        ArrayList<HBaseValueOneLine> currentHBaseValue = new ArrayList<HBaseValueOneLine>();
        String currentTag = null;
        String tag = null;
        try {
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(System.in));
            while ((line = bufferedReader.readLine()) != null) {
                /* Input format is tag \t date \t count \t userids \ t text */
                String[] fields = line.split("\t");
                tag = fields[0];

                HBaseValueOneLine hBaseValueOneLine = new HBaseValueOneLine();
                hBaseValueOneLine.setDate(fields[1]);
                hBaseValueOneLine.setCount(Integer.parseInt(fields[2]));
                hBaseValueOneLine.setUserIds(fields[3]);
                hBaseValueOneLine.setSourceText(fields[4]);

                /* Check if we are on the same tag */
                if (currentTag != null && currentTag.equals(tag)) {
                    /*
                     *                   * If on the same tag, the value of this row will add one
                     *                                       * line
                     *                                                           */
                    currentHBaseValue.add(hBaseValueOneLine);
                } else {
                    /* The tag has changed */
                    /* Not first time. Output */
                    if (currentTag != null) {
                        /* Sort first */
                        Collections.sort(currentHBaseValue);
                        String value = currentHBaseValue.toString();
                        value = value.replaceAll("^\\[", "")
                            .replaceAll("\\]$", "").replaceAll(", ", "\b");
                        System.out.println(currentTag + "\t" + value);
                    }
                    currentTag = tag;
                    currentHBaseValue.clear();
                    currentHBaseValue.add(hBaseValueOneLine);
                }
            }
            /* Print out last tag if missed */
            if (currentTag != null && currentTag.equals(tag)) {
                Collections.sort(currentHBaseValue);
                String value = currentHBaseValue.toString();
                value = value.replaceAll("^\\[", "")
                    .replaceAll("\\]$", "").replaceAll(", ", ",");
                System.out.println(currentTag + "\t" + value);
            }
            bufferedReader.close();
        } catch (Exception err) {
            err.printStackTrace();
        }
    }
}

