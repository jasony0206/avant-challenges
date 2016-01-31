import tweepy
import stop_words as sw
import operator

access_token = "4856028904-WRZgbA4C2V6kLHKaNNFP4ag75cQc3QMVceySBYy"
access_token_secret = "yfVs5cwz8TiooQbBeEAJKzAB0Ws4sYVvKvtFtDpoW6f3i"
consumer_key = "acMGdyXzBrhtNIrN4OJcnYNw2"
consumer_secret = "FwAzvXeFGbQtSHPKqsQWKxJQq0K5LQQHimvEVZG0cPSAZSg5Rg"
auth = tweepy.OAuthHandler(consumer_key, consumer_secret)
auth.set_access_token(access_token, access_token_secret)

class TweetStreamListener(tweepy.StreamListener):
    def __init__(self, api=None):
        self.api = api or tweepy.API()
        # Approx 300000 tweets/min, firehose pulls 1% sample, thus 3000/min
        # in 5 min, approx 15000 tweets
        self.max_count = 15000
        self.count = 0
        self.output = open('tweets.txt', 'w')

    def on_status(self, status):
        if (self.count < self.max_count):
        	text = status.text.encode('utf8')
        	self.output.write(text + "\n")
        	self.count += 1
        	return True
        else:
        	# done collecting tweets, close file and stop listening
        	self.output.close()
        	return False

if __name__ == '__main__':
    listner = TweetStreamListener()
    stream = tweepy.streaming.Stream(auth, listner)
    stream.filter(languages=['en'], locations=[-180, -90, 180, 90 ])
    
    # process the data
    with open('tweets.txt', 'r') as tweets:
    	tweets_string = tweets.read().replace('\n', '')
    	# normalize by turning all words to lowercase
    	tweets_words = [word.lower() for word in tweets_string.split(' ')]
    	total_word_count = len(tweets_words)
        print "Total word count:", total_word_count

    	stop_words = sw.get_stop_words('english')
    	# change encoding scheme from unicode to utf-8 to match tweets
    	stop_words = [stop_word.encode('utf-8') for stop_word in stop_words]
    	stop_words += ['', '@', '-']
    	
    	# filter out stop words
    	tweets_words = [word for word in tweets_words if word not in stop_words]

    	word_freq_dict = {}
    	for word in tweets_words:
    		if word in word_freq_dict.keys():
    			word_freq_dict[word] += 1
    		else:
    			word_freq_dict[word] = 1

    	# sort based on the frequency count (descending order)
    	sorted_word_freq = sorted(word_freq_dict.items(), key=operator.itemgetter(1), reverse=True)

    	for word_tuple in sorted_word_freq[:10]:
    	 	print word_tuple[0]
