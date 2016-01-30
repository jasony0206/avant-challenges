import tweepy

access_token = "4856028904-WRZgbA4C2V6kLHKaNNFP4ag75cQc3QMVceySBYy"
access_token_secret = "yfVs5cwz8TiooQbBeEAJKzAB0Ws4sYVvKvtFtDpoW6f3i"
consumer_key = "acMGdyXzBrhtNIrN4OJcnYNw2"
consumer_secret = "FwAzvXeFGbQtSHPKqsQWKxJQq0K5LQQHimvEVZG0cPSAZSg5Rg"
auth = tweepy.OAuthHandler(consumer_key, consumer_secret)
auth.set_access_token(access_token, access_token_secret)

class TweetStreamListener(tweepy.StreamListener):
    def __init__(self, api=None):
        self.api = api or tweepy.API()
        self.output = open('tweets.txt', 'w')

    def on_status(self, status):
        text = status.text.encode('utf8')
        self.output.write(text)

if __name__ == '__main__':
    listner = TweetStreamListener()
    stream = tweepy.streaming.Stream(auth, listner)
    stream.filter(languages=['en'], locations=[-180, -90, 180, 90 ])
    