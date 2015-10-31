#!/usr/bin/env python
from __future__ import print_function
import sys
import os
import happybase
import json

DELIMITER = "\t"

for line in sys.stdin:
	try:
		tweet = json.loads(line)
		uid = tweet['uid'].encode('utf-8')
		tid = tweet['tid'].encode('utf-8')
		score = tweet['score'].encode('utf-8')
		time = tweet['time'].encode('utf-8')
		textObj = {}
		textObj['text'] = tweet['text'].encode('utf-8')
		print(uid+"_"+time+"_"+tid, tid, score,  json.dumps(textObj), sep=DELIMITER, end="\n")
	except Exception, e:
		# raise
		pass