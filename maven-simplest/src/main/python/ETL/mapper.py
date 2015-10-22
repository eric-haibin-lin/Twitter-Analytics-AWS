#!/usr/bin/env python
'''
@date Oct 21, 2015
@author: Haibin Lin
'''
from __future__ import print_function
import json
import time
import sys
from TextProcessor import TextProcessor

DELIMITER = "@15619@delimiter@".encode('utf-8')
NEWLINE = "@15619@newline@\n".encode('utf-8')

textProcessor = TextProcessor()
for line in sys.stdin:
    try:
        twit = json.loads(line)
        text = twit['text']
        id = twit['id_str']
        timeString, timeInt = textProcessor.ConvertTime(twit['created_at'])
        sentiment = textProcessor.SentimentScore(text)
        text = textProcessor.TextCensoring(text)
        print(id, timeString, timeInt, sentiment, text.encode('utf-8'), sep=DELIMITER, end=NEWLINE)
    except Exception, e:
        pass