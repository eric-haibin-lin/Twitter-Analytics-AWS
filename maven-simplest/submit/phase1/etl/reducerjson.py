#!/usr/bin/env python
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
		epoch = tweet['epoch'].encode('utf-8')
		del tweet['uid']
		del tweet['tid']
		del tweet['score']
		del tweet['time']
		del tweet['epoch']
		del tweet['original_text']
		print uid+time+tid + DELIMITER + tid + DELIMITER + uid + DELIMITER + score + DELIMITER + time + DELIMITER + epoch + DELIMITER + json.dumps(tweet, ensure_ascii=False).encode('utf8')
	except Exception, e:
		# raise
		pass