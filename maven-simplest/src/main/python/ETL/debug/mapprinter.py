#!/usr/bin/env python
import sys

try:
	for line in sys.stdin:
		print line
except Exception, e:
	raise