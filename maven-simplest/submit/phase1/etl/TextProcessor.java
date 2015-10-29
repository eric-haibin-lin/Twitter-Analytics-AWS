import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class TextProcessor {
	/* Key: bannedWord, Valuse: '*'s in cencored result */
	private static HashMap<String, String> bannedWordsMap = new HashMap<String, String>();
	/* Key: word, Valuse: sentiment score */
	private static HashMap<String, Integer> sentimentScoresMap = new HashMap<String, Integer>();
	private static boolean bInitialized = false;
	/* Load banned words from file */
	private void Init() {
		String line = null;
		try {
			/* Banned words file*/
			FileReader fileReader = new FileReader("banned.txt");
			BufferedReader bufferedReader = new BufferedReader(fileReader);

			/* Read banned words line by line and put them in bannedWordsMap */
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

			/* Read sentiment words line by line and put them in bannedWordsMap with their scores */
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
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		/* Only initialize once */
		bInitialized = true;
	}

	public TextProcessor() {
		/* Only initialize once */
		/* Only initialize once */
		if (!bInitialized) {
			Init();
		}
	}

	/**
	 * rotate the text by 13 positions 
	 * @param text
	 * @return the text after rotation
	 */
	public String ROT13(String text) {
		String result = "";
		for (char ch : text.toCharArray()) {
			/* Consider only alpha characters */
			if (Character.isUpperCase(ch)) {
				ch = (char) ((ch - 'A' + 13) % 26 + 'A');
			} else if (Character.isLowerCase(ch)) {
				ch = (char) ((ch - 'a' + 13) % 26 + 'a');
			}
			result += ch;
		}
		return result;
	}

	/**
	 * calculate the sentiment score of a text
	 * @param text
	 * @return the score
	 */
	public int SentimentScore(String text) {
		int score = 0;
		/* Split by alphanumeric characters */
		String[] words = text.split("[^a-zA-Z0-9]+");
		for (int i = 0; i < words.length; i++) {
			/* Convert to lowercase and see if it is in the sentiment word map */
			String word = words[i].toLowerCase();
			if (sentimentScoresMap.containsKey(word)) {
				score += sentimentScoresMap.get(word);
			}
		}
		return score;
	}

	/**
	 * censor text and replace with *'s
	 * @param text
	 * @return the censored text
	 */
	public String TextCensor(String text) {
		String cencoredText = text;
		String[] words = text.split("[^a-zA-Z0-9]+");
		for (int i = 0; i < words.length; i++) {
			String word = words[i].toLowerCase();
			if (bannedWordsMap.containsKey(word)) {
				String cencoredWord = words[i].charAt(0) + bannedWordsMap.get(word)
						+ words[i].charAt(word.length() - 1);
				/* Replace the word whose prefix character and suffix character are all not alphanumeric */
				cencoredText = cencoredText.replaceAll("(?<=[^a-zA-Z0-9])" + words[i] + "(?=[^a-zA-Z0-9])", cencoredWord);
			}
		}
		return cencoredText;
	}
}
