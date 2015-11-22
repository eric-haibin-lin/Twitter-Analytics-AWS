import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

public class Query5Reducer {
	public static void main(String args[]) {
		try {
			BufferedReader br = new BufferedReader(
					new InputStreamReader(System.in));
			String input = null;
			String uid = null;
			String currentUid = null;
			HashMap<String, Integer> tweetIds = new HashMap<String, Integer>();

			while ((input = br.readLine()) != null) {
				/* Input: <uid><\t><tid> */
				String[] parts = input.split("\t");
				uid = parts[0];

				if (currentUid != null && currentUid.equals(uid)) {
					tweetIds.put(parts[1], 1);
				} else {
					if (currentUid != null) {
						System.out.println(currentUid + "\t" + tweetIds.size());
					}
					currentUid = uid;
					tweetIds.clear();
					tweetIds.put(parts[1], 1);
				}
			}

			if (currentUid != null && currentUid.equals(uid)) {
				System.out.println(currentUid + "\t" + tweetIds.size());
			}

		} catch (IOException io) {
			io.printStackTrace();
		}
	}
}
