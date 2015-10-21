'''
@date Oct 20, 2015
@author: Hechao Li
'''
import re

class TextProcessor:
    banned_words = []
    sentiment_dict = {}
    
    def ROT13(self,text):
        result = ''
        for c in text:
            if c.isupper():
                c = chr((ord(c) - ord('A') + 13) % 26 + ord('A'))
            elif c.islower():
                c = chr((ord(c) - ord('a') + 13) % 26 + ord('a'))
            result += c
        return result
    
    def SentimentScore(self, text):
        sentiment = 0
        words = re.findall(r'\w+', text)
        for word in words:
            lower_word = word.lower()
            if lower_word in self.sentiment_dict:
                sentiment += self.sentiment_dict[lower_word]
        return sentiment
    
    def TextCensoring(self, text):
        words = re.findall(r'\w+', text)
        for word in words:
            if word.lower() in self.banned_words:
                # Do not check if len < 2 because we trust banned.txt file
                censored_word = word[0]+('*'*(len(word)-2))+word[-1]
                text = re.sub(r'\b' + word + r'\b', censored_word, text)
        return text
    
    # Load file banned.txt and afinn.txt
    def __init__(self):
        with open('banned.txt') as f:
            for line in f:
                self.banned_words.append(self.ROT13(line.rstrip()))
        with open('afinn.txt') as f:
            for line in f:
                word, score = line.rstrip('\n').split('\t')
                self.sentiment_dict[word] = int(score)