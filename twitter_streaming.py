import tweepy
import stop_words as sw

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
    # listner = TweetStreamListener()
    # stream = tweepy.streaming.Stream(auth, listner)
    # stream.filter(languages=['en'], locations=[-180, -90, 180, 90 ])
    
    # parse the file into words
    with open('tweets.txt', 'r') as tweets:
    	tweets_string = tweets.read().replace('\n', '')
    	tweets_words = tweets_string.split(' ')
    	tweets_wordcount = len(tweets_words)

    	stop_words = sw.get_stop_words('english')
    	# change encoding scheme from unicode to utf-8 to match tweets
    	stop_words = [stop_word.encode('utf-8') for stop_word in stop_words]
    	
    	# filter out stop words
    	tweets_words = [word for word in tweets_words if word not in stop_words]