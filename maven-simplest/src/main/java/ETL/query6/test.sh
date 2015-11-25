#test 1: basic 
curl 'http://localhost/q6?tid=3000001&opt=s' 
curl 'http://localhost/q6?tid=3000001&seq=1&opt=a&tweetid=466867940529147904&tag=ILOVE15619!12'
curl 'http://localhost/q6?tid=3000001&seq=2&opt=a&tweetid=466866174715002880&tag=ILOVE15619!30'
curl 'http://localhost/q6?tid=3000001&seq=3&opt=a&tweetid=466866174697799680&tag=ILOVE15619!215'
curl 'http://localhost/q6?tid=3000001&seq=4&opt=a&tweetid=466866170528681984&tag=ILOVE15619!311'
curl 'http://localhost/q6?tid=3000001&seq=5&opt=r&tweetid=466866174697799680' &
curl 'http://localhost/q6?tid=3000001&opt=e' 

#test 3: test1 cleanup 
curl 'http://localhost/q6?tid=3000001&opt=s' 
curl 'http://localhost/q6?tid=3000001&seq=1&opt=r&tweetid=466866174697799680'


#test 2: unordered sequence number 
curl 'http://localhost/q6?tid=3000002&opt=s' 
curl 'http://localhost/q6?tid=3000002&seq=3&opt=r&tweetid=466866191479209984' &
curl 'http://localhost/q6?tid=3000002&seq=2&opt=a&tweetid=466866191479209984&tag=ILOVE15619!2' 
curl 'http://localhost/q6?tid=3000002&seq=1&opt=a&tweetid=466866191496007680&tag=ILOVE15619!2' 


curl 'http://localhost/q6?tid=3000003&opt=s' 
curl 'http://localhost/q6?tid=3000003&seq=3&opt=r&tweetid=466866191479209984' &
curl 'http://localhost/q6?tid=3000003&seq=2&opt=a&tweetid=466866191479209984&tag=ILOVE15619!2' 
curl 'http://localhost/q6?tid=3000003&seq=1&opt=a&tweetid=466866191496007680&tag=ILOVE15619!1' 
curl 'http://localhost/q6?tid=3000003&seq=5&opt=a&tweetid=466866187293691904&tag=ThisTagShouldNotShowUp' 
curl 'http://localhost/q6?tid=3000003&seq=4&opt=r&tweetid=466866187293691904' &