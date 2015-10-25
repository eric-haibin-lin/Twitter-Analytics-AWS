#!/usr/bin/env python
from __future__ import print_function
import sys
import os
import happybase
import json

TABLE_NAME = "tweets2"
DELIMITER = "@15619@delimiter@"
NEWLINE = "@15619@newline@\n"
DNS = 'ec2-52-91-82-11.compute-1.amazonaws.com'
connection = happybase.Connection(DNS)

def create_table(table):
	global connection
	try:
		families = {'info': dict(in_memory=True, block_cache_enabled=True)}
		connection.create_table(table, families)
	except Exception, e:
		pass

create_table(TABLE_NAME)
table = connection.table(TABLE_NAME)

with table.batch(batch_size=1000) as tweet_batch:
	for line in sys.stdin:
		try:
			tweet = json.loads(line)
			uid = tweet['uid']
			tid = tweet['tid']
			score = tweet['score']
			time = tweet['time']
			epoch = tweet['epoch']
			text = tweet['text']
			tweet_batch.put(
				tweet['tid'], 
				{
					'info:score': score,
					'info:text': text,
					'info:uid': uid,
					'info:timestamp': time, 
					'info:epoch': epoch,
				})
	        # print(tid, uid, score, time, epoch, text, sep=DELIMITER, end=NEWLINE)
		except Exception, e:
			raise