#!/usr/bin/env python
'''
@date Oct 21, 2015
@author: Haibin Lin
'''
from __future__ import print_function
import json
import time
import sys
import re
import calendar


class TextProcessor:
    # key: banned word; Value: asterisks 
    banned_words = {}
    sentiment_dict = {}
    TIME_FORMAT_FROM = "%a %b %d %X +0000 %Y"
    TIME_FORMAT_TO = "%Y-%m-%d+%X"

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
            lower_word = word.lower()
            if lower_word in self.banned_words:
                # Do not check if len < 2 because we trust banned.txt file
                censored_word = word[0] + self.banned_words[lower_word] + word[-1]
                text = re.sub(r'\b' + word + r'\b', censored_word, text)
        return text
    

    # Parse time and get its string & int representation since Epoch 
    def ConvertTime(self, text):
        timeStruct = time.strptime(text, self.TIME_FORMAT_FROM)
        timeString = time.strftime(self.TIME_FORMAT_TO, timeStruct)
        timeInt = calendar.timegm(timeStruct)
        return timeString, timeInt

    # Load file banned.txt and afinn.txt
    def __init__(self):
        with open('banned.txt') as f:
            for line in f:
                banned_word = self.ROT13(line.rstrip())
                censored_word = '*' * (len(banned_word)-2)
                self.banned_words[banned_word] = censored_word
        with open('afinn.txt') as f:
            for line in f:
                word, score = line.rstrip('\n').split('\t')
                self.sentiment_dict[word] = int(score)


DELIMITER = "@15619@delimiter@".encode('utf-8')
NEWLINE = "@15619@newline@\n".encode('utf-8')

textProcessor = TextProcessor()
for line in sys.stdin:
    try:
        tweet = json.loads(line)
        text = tweet['text']
        id = tweet['id_str']
        timeString, timeInt = textProcessor.ConvertTime(tweet['created_at'])
        sentiment = textProcessor.SentimentScore(text)
        text = textProcessor.TextCensoring(text)
        print(id, timeString, timeInt, sentiment, text.encode('utf-8'), sep=DELIMITER, end=NEWLINE)
    except Exception, e:
        pass
