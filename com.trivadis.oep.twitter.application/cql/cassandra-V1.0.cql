
DROP KEYSPACE oep_demo;

CREATE KEYSPACE oep_demo
WITH replication = {'class': 'SimpleStrategy', 'replication_factor': 3};

USE oep_demo;

-- Tweet storage
DROP TABLE tweet;
CREATE TABLE tweet (tweet_id bigint PRIMARY KEY, 
					created_at timestamp,
					message text, 
					hashtags list<text>, 
					latitude double, 
					longitude double,
					favorite_count int,
					in_reply_to_status_id bigint,
					in_reply_to_user_id bigint,
					lang text,
					retweet_count bigint,
					source text,
					user_favorite_count int,
					user_friends_count int,
					user_id bigint,
					user_lang text,
					user_location text, 
					user_name text,
					user_screen_name text,
					user_statuses_count int,
					user_time_zone text,
					user_utc_offset int,
					is_retweet_original boolean,
					retweet_original_id bigint,
					place_street_address text,
					place_place_type text,
					place_name text,
					place_full_name text,
					place_country text					 
					);


-- Tweet counter by time
DROP TABLE tweet_count;
CREATE TABLE tweet_count (
    sensor_id   uuid,
    bucket_id   text,		
    category	text,
    time_id     timestamp,	-- granualarity 
    keyword     text,
    count       counter,
    PRIMARY KEY((sensor_id, bucket_id, category), time_id, keyword))
    WITH CLUSTERING ORDER BY (time_id DESC)
    AND COMPACT STORAGE
;					

