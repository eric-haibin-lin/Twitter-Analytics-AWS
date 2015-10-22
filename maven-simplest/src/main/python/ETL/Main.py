'''
@date Oct 20, 2015
@author: Hechao Li
'''
import json
import time
from TextProcessor import TextProcessor
if __name__ == '__main__':
    textProcessor = TextProcessor()
    text = 'RT @AMAZlNGPICTURES: "Blood Moon" a magical natural phenomenon http://t.co/9iYoLuNXQH'
    sentiment = textProcessor.SentimentScore(text)
    text = textProcessor.TextCensoring(text)
    print sentiment, text
    
    text = "I love Cloud compz... cloud TAs are the best... Yinz shld tell yr frnz: TAKE CLOUD COMPUTING NEXT SEMESTER!!! Awesome. It's cloudy tonight."
    sentiment = textProcessor.SentimentScore(text)
    text = textProcessor.TextCensoring(text)
    print sentiment, text
    t0 = time.clock()
    with open(r'D:\CMU\15619\part-00661') as data_file: 
        for line in data_file:
            text = json.loads(line)['text']
            sentiment = textProcessor.SentimentScore(text)
            text = textProcessor.TextCensoring(text)
            #print sentiment, text
    print time.clock() - t0