'''
@date Oct 20, 2015
@author: Hechao Li
'''
from __future__ import print_function
import json
import time
import sys
from TextProcessor import TextProcessor

DELIMITER = "@15619@delimiter@".encode('utf-8')
NEWLINE = "@15619@newline@\n".encode('utf-8')

if __name__ == '__main__':
    textProcessor = TextProcessor()
    # text = 'RT @AMAZlNGPICTURES: "Blood Moon" a magical natural phenomenon http://t.co/9iYoLuNXQH'
    # sentiment = textProcessor.SentimentScore(text)
    # text = textProcessor.TextCensoring(text)
    # print sentiment, text
    # text = "I love Cloud compz... cloud TAs are the best... Yinz shld tell yr frnz: TAKE CLOUD COMPUTING NEXT SEMESTER!!! Awesome. It's cloudy tonight."
    # sentiment = textProcessor.SentimentScore(text)
    # text = textProcessor.TextCensoring(text)
    # print sentiment, text
    # t0 = time.clock()
    # with open(r'./part-00000') as data_file: 
    for line in sys.stdin:
        twit = json.loads(line)
        text = twit['text']
        id = twit['id_str']
        timeString, timeInt = textProcessor.ConvertTime(twit['created_at'])
        sentiment = textProcessor.SentimentScore(text)
        text = textProcessor.TextCensoring(text)
        print(id, timeString, timeInt, sentiment, text.encode('utf-8'), sep=DELIMITER, end=NEWLINE)
    # print(time.clock() - t0)
