#!/usr/bin/env python
from __future__ import print_function
import sys
import os
import happybase
import json

# DELIMITER = "<15619_delimiter>"
# NEWLINE = "<15619_newline>\n"

DELIMITER = "\t"

for line in sys.stdin:
	try:
		tweet = json.loads(line)
		uid = tweet['uid'].encode('utf-8')
		tid = tweet['tid'].encode('utf-8')
		score = tweet['score'].encode('utf-8')
		time = tweet['time'].encode('utf-8')
		epoch = tweet['epoch'].encode('utf-8')
		text = tweet['text'].encode('utf-8')
		print(uid+time+tid, tid, uid, score, time, epoch, json.dumps(tweet, ensure_ascii=False).encode('utf8'), sep=DELIMITER)

	except Exception, e:
		raise