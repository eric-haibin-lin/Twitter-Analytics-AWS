#!/usr/bin/env python
from __future__ import print_function
import sys
import os
import happybase
import json

TABLE_NAME = "tweets"
DELIMITER = "<15619_delimiter>"
NEWLINE = "<15619_newline>"
#Remember to modify the DNS address of HBase master
DNS = "ec2-54-165-248-154.compute-1.amazonaws.com"
connection = happybase.Connection(DNS)

def create_table(table):
	global connection
	try:
		families = {'info': dict(in_memory=True, block_cache_enabled=True)}
		connection.create_table(table, families)
	except Exception, e:
		pass

def main():
	create_table(TABLE_NAME)
	table = connection.table(TABLE_NAME)
	with table.batch(batch_size=1000) as tweet_batch:
		for line in sys.stdin:
			try:
				tweet = json.loads(line)
				uid = tweet['uid'].encode('utf-8')
				tid = tweet['tid'].encode('utf-8')
				score = tweet['score'].encode('utf-8')
				time = tweet['time'].encode('utf-8')
				epoch = tweet['epoch'].encode('utf-8')
				text = tweet['text'].encode('utf-8')
				tweet_batch.put(
					tweet['uid'],
					{
						'info:score': score,
						'info:text': text,
						'info:tid': tid,
						'info:timestamp': time, 
						'info:epoch': epoch,
					})
				print(tid, uid, score, time, epoch, text, sep=DELIMITER, end=NEWLINE)
			except Exception, e:
				pass

main()