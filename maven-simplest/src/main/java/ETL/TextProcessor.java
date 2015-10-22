import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class TextProcessor {
	private static HashMap<String, String> bannedWordsMap = new HashMap<String, String>();
	private static HashMap<String, Integer> sentimentScoresMap = new HashMap<String, Integer>();
	private static boolean bInitialized = false;

	private void Init() {
		String line = null;
		try {
			/* Banned words */
			FileReader fileReader = new FileReader("banned.txt");
			BufferedReader bufferedReader = new BufferedReader(fileReader);

			while ((line = bufferedReader.readLine()) != null) {
				String bannedWord = ROT13(line);
				String censoredWord = new String(new char[bannedWord.length() - 2]).replace('\0', '*');
				bannedWordsMap.put(bannedWord, censoredWord);
			}
			bufferedReader.close();
			/*
			 * for (String key: bannedWordsMap.keySet()) {
			 * System.out.println(key+": " + bannedWordsMap.get(key)); }
			 */
			
			/* Sentiment scores */
			fileReader = new FileReader("afinn.txt");
			bufferedReader = new BufferedReader(fileReader);

			while ((line = bufferedReader.readLine()) != null) {
				String[] tmp = line.split("\t", 2);
				String word = tmp[0];
				String score = tmp[1];
				sentimentScoresMap.put(word, Integer.parseInt(score));
			}
			bufferedReader.close();

			/*
			 * for (String key: sentimentScoresMap.keySet()) {
			 * System.out.println(key+": " + sentimentScoresMap.get(key)); }
			 */
		} catch (FileNotFoundException ex) {
			System.out.println("File 'banned.txt' Not Found!");
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		bInitialized = true;
	}

	public TextProcessor() {
		if (!bInitialized) {
			Init();
		}
	}

	public String ROT13(String text) {
		String result = "";
		for (char ch : text.toCharArray()) {
			if (Character.isUpperCase(ch)) {
				ch = (char) ((ch - 'A' + 13) % 26 + 'A');
			} else if (Character.isLowerCase(ch)) {
				ch = (char) ((ch - 'a' + 13) % 26 + 'a');
			}
			result += ch;
		}
		return result;
	}

	public int SentimentScore(String text) {
		int score = 0;
		String[] words = text.split("[^\\w]+");
		for (int i = 0; i < words.length; i++) {
			String word = words[i].toLowerCase();
			if (sentimentScoresMap.containsKey(word)) {
				score += sentimentScoresMap.get(word);
			}
		}
		return score;
	}

	public String TextCensor(String text) {
		String cencoredText = text;
		String[] words = text.split("[^\\w]+");
		for (int i = 0; i < words.length; i++) {
			String word = words[i].toLowerCase();
			if (bannedWordsMap.containsKey(word)) {
				String cencoredWord = words[i].charAt(0) + bannedWordsMap.get(word)
						+ words[i].charAt(word.length() - 1);
				cencoredText = cencoredText.replaceAll("\\b" + words[i] + "\\b", cencoredWord);
			}
		}
		return cencoredText;
	}
}
