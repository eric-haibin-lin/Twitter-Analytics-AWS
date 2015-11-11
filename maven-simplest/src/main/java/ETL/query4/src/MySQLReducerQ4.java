import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;

public class MySQLReducerQ4 {
    public static void main(String[] args) throws FileNotFoundException {
        String line = null;
        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(System.in));
        int currentCount = 0;
        String currentTagOnDate = null;
        ArrayList<BigInteger> currentUserIdList = new ArrayList<BigInteger>();
        String currentSourceText = null;
        String currentEarliestTime = null;
        String tagOnDate = null;
        String time = null;
        BigInteger uid = null;
        String text = null;
        try {
            /* line: tag \b date \t time \t uid \t text */
            while ((line = bufferedReader.readLine()) != null) {
                String[] fields = line.split("\t");
                tagOnDate = fields[0];
                time = fields[1];
                uid = new BigInteger(fields[2]);
                text = fields[3];

                /* Check if we on the same tagOndate */
                if (currentTagOnDate != null
                        && currentTagOnDate.equals(tagOnDate)) {
                    /* Count of this tag on this date ++ */
                    currentCount++;
                    /* If the user is not on the list, add it */
                    if (!currentUserIdList.contains(uid)) {
                        currentUserIdList.add(uid);
                    }

                    /*
                     *                   * This text is earlier than currentText, Update text and
                     *                                       * earliest time
                     *                                                           */
                    if (currentEarliestTime.compareTo(time) > 0) {
                        currentSourceText = text;
                        currentEarliestTime = time;
                    } else if (currentEarliestTime.equals(time)) {
                        /*
                         *                       * There is a tie, return the text with the higher
                         *                                               * alphabetical order. Need not to update earliest time
                         *                                                                       */
                        currentSourceText = currentSourceText
                            .compareTo(text) > 0 ? currentSourceText : text;
                    }
                } else { /* The tagOnDate has changed */
                    /* Not the first time */
                    if (currentTagOnDate != null) {
                        /*
                         *                       * Output tag \t date \t count \t userlist \t sourcetext
                         *                                               */
                        /* Sort userIds */
                        Collections.sort(currentUserIdList);
                        String currentUserIds = currentUserIdList.toString();
                        currentUserIds = currentUserIds.replaceAll("^\\[", "")
                            .replaceAll("\\]$", "").replaceAll(", ", ",");
                        System.out.println(currentTagOnDate.replace('\b', '\t')
                                + '\t' + currentCount + '\t' + currentUserIds
                                + '\t' + currentSourceText);
                    }
                    /* Initial these values */
                    currentTagOnDate = tagOnDate;
                    currentCount = 1;
                    currentSourceText = text;
                    currentUserIdList.clear();
                    currentUserIdList.add(uid);
                    currentEarliestTime = time;
                }
            }

            /* Print out last tag if missed */
            if (currentTagOnDate != null
                    && currentTagOnDate.equals(tagOnDate)) {
                /*
                 *               * Output tag \t date \t count \t userlist \t sourcetext
                 *                               */
                /* Sort userIds */
                Collections.sort(currentUserIdList);
                String currentUserIds = currentUserIdList.toString();
                currentUserIds = currentUserIds.replaceAll("^\\[", "")
                    .replaceAll("\\]$", "").replaceAll(", ", ",");
                System.out.println(currentTagOnDate.replace('\b', '\t') + '\t'
                        + currentCount + '\t' + currentUserIds + '\t'
                        + currentSourceText);
                    }
            bufferedReader.close();
        } catch (Exception err) {
            err.printStackTrace();
        }
    }
}

