package com.gitee.rabbitnoteeth.bedrock.data.nosql.redis.commands;

import redis.clients.jedis.JedisPooled;
import redis.clients.jedis.args.SortedSetOption;
import redis.clients.jedis.commands.SortedSetCommands;
import redis.clients.jedis.params.*;
import redis.clients.jedis.resps.ScanResult;
import redis.clients.jedis.resps.Tuple;
import redis.clients.jedis.util.KeyValue;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class ZSetCommands {

    private final JedisPooled jedis;

    public ZSetCommands(JedisPooled jedis) {
        this.jedis = jedis;
    }


    /**
     * Add the specified member having the specified score to the sorted set stored at key. If member
     * is already a member of the sorted set the score is updated, and the element reinserted in the
     * right position to ensure sorting. If key does not exist a new sorted set with the specified
     * member as sole member is created. If the key exists but does not hold a sorted set value an
     * error is returned.
     * <p>
     * The score value can be the string representation of a double precision floating point number.
     * <p>
     * Time complexity O(log(N)) with N being the number of elements in the sorted set
     *
     * @param key
     * @param score
     * @param member
     * @return 1 if the new element was added, 0 if the element was already a member of the sorted
     * set and the score was updated
     */
    public long add(String key, double score, String member) {
        return jedis.zadd(key, score, member);
    }

    /**
     * Similar to {@link SortedSetCommands#zadd(String, double, String) ZADD} but can be used with optional params.
     *
     * @param key
     * @param score
     * @param member
     * @param params {@link ZAddParams}
     * @return 1 if the new element was added, 0 if the element was already a member of the sorted
     * set and the score was updated
     * @see SortedSetCommands#zadd(String, double, String)
     */
    public long add(String key, double score, String member, ZAddParams params) {
        return jedis.zadd(key, score, member, params);
    }

    /**
     * Similar to {@link SortedSetCommands#zadd(String, double, String) ZADD} but for multiple members.
     *
     * @param key
     * @param scoreMembers
     * @return The number of elements added to the sorted set (excluding score updates).
     * @see SortedSetCommands#zadd(String, double, String)
     */
    public long add(String key, Map<String, Double> scoreMembers) {
        return jedis.zadd(key, scoreMembers);
    }

    /**
     * Similar to {@link SortedSetCommands#zadd(String, double, String) ZADD} but can be used with optional params,
     * and fits for multiple members.
     *
     * @param key
     * @param scoreMembers
     * @param params       {@link ZAddParams}
     * @return The number of elements added to the sorted set (excluding score updates).
     * @see SortedSetCommands#zadd(String, double, String)
     */
    public long add(String key, Map<String, Double> scoreMembers, ZAddParams params) {
        return jedis.zadd(key, scoreMembers, params);
    }

    /**
     * Increments the score of member in the sorted set stored at key by increment. If member does not
     * exist in the sorted set, it is added with increment as its score (as if its previous score was 0.0).
     * If key does not exist, a new sorted set with the specified member as its sole member is created.
     * <p>
     * The score value should be the string representation of a numeric value, and accepts double precision
     * floating point numbers. It is possible to provide a negative value to decrement the score.
     * <p>
     * Time complexity O(log(N)) with N being the number of elements in the sorted set
     *
     * @param key
     * @param score
     * @param member
     * @param params {@link ZAddParams}
     * @return 1 if the new element was added, 0 if the element was already a member of the sorted
     * set and the score was updated
     */
    public Double addIncr(String key, double score, String member, ZAddParams params) {
        return jedis.zaddIncr(key, score, member, params);
    }

    /**
     * Remove the specified member from the sorted set value stored at key. If member was not a member
     * of the set no operation is performed. If key does not hold a set value an error is returned.
     * <p>
     * Time complexity O(log(N)) with N being the number of elements in the sorted set
     *
     * @param key
     * @param members
     * @return 1 if the new element was removed, 0 if the new element was not a member of the set
     */
    public long rem(String key, String... members) {
        return jedis.zrem(key, members);
    }

    /**
     * If member already exists in the sorted set adds the increment to its score and updates the
     * position of the element in the sorted set accordingly. If member does not already exist in the
     * sorted set it is added with increment as score (that is, like if the previous score was
     * virtually zero). If key does not exist a new sorted set with the specified member as sole
     * member is created. If the key exists but does not hold a sorted set value an error is returned.
     * <p>
     * The score value can be the string representation of a double precision floating point number.
     * It's possible to provide a negative value to perform a decrement.
     * <p>
     * For an introduction to sorted sets check the Introduction to Redis data types page.
     * <p>
     * Time complexity O(log(N)) with N being the number of elements in the sorted set
     *
     * @param key
     * @param increment
     * @param member
     * @return The new score
     */
    public double incrby(String key, double increment, String member) {
        return jedis.zincrby(key, increment, member);
    }

    /**
     * Similar to {@link SortedSetCommands#zincrby(String, double, String) ZINCRBY} but can be used with optionals params.
     *
     * @param key
     * @param increment
     * @param member
     * @param params    {@link ZIncrByParams}
     * @return The new score for key
     * @see SortedSetCommands#zincrby(String, double, String)
     */
    public Double incrby(String key, double increment, String member, ZIncrByParams params) {
        return jedis.zincrby(key, increment, member, params);
    }

    /**
     * Return the rank (or index) of member in the sorted set at key, with scores being ordered from
     * low to high.
     * <p>
     * When the given member does not exist in the sorted set, the special value 'nil' is returned.
     * The returned rank (or index) of the member is 0-based for both commands.
     * <p>
     * Time complexity O(log(N))
     *
     * @param key
     * @param member
     * @return The rank of the element as an integer reply if the element exists. A nil bulk reply
     * if there is no such element
     */
    public Long rank(String key, String member) {
        return jedis.zrank(key, member);
    }

    /**
     * Return the rank (or index) of member in the sorted set at key, with scores being ordered from
     * high to low.
     * <p>
     * When the given member does not exist in the sorted set, the special value 'nil' is returned.
     * The returned rank (or index) of the member is 0-based for both commands.
     * <p>
     * Time complexity O(log(N))
     *
     * @param key
     * @param member
     * @return The rank of the element as an integer reply if the element exists. A nil bulk reply
     * if there is no such element
     */
    public Long revrank(String key, String member) {
        return jedis.zrevrank(key, member);
    }

    /**
     * Returns the rank and the score of member in the sorted set stored at key, with the scores
     * ordered from low to high.
     *
     * @param key    the key
     * @param member the member
     * @return the KeyValue contains rank and score.
     */
    public KeyValue<Long, Double> rankWithScore(String key, String member) {
        return jedis.zrankWithScore(key, member);
    }

    /**
     * Returns the rank and the score of member in the sorted set stored at key, with the scores
     * ordered from high to low.
     *
     * @param key    the key
     * @param member the member
     * @return the KeyValue contains rank and score.
     */
    public KeyValue<Long, Double> revrankWithScore(String key, String member) {
        return jedis.zrevrankWithScore(key, member);
    }

    /**
     * Returns the specified range of elements in the sorted set stored at key.
     * <p>
     * Time complexity O(log(N)+M) with N being the number of elements in the sorted set and M the
     * number of elements returned.
     *
     * @param key   the key to query
     * @param start the minimum index
     * @param stop  the maximum index
     * @return A List of Strings in the specified range
     */
    public List<String> range(String key, long start, long stop) {
        return jedis.zrange(key, start, stop);
    }

    /**
     * Returns the specified range of elements in the sorted set stored at key. The elements are
     * considered to be ordered from the highest to the lowest score. Descending lexicographical
     * order is used for elements with equal score.
     * <p>
     * Time complexity O(log(N)+M) with N being the number of elements in the sorted set and M the
     * number of elements returned.
     *
     * @param key   the key to query
     * @param start the minimum index
     * @param stop  the maximum index
     * @return A List of Strings in the specified range
     */
    public List<String> revrange(String key, long start, long stop) {
        return jedis.zrevrange(key, start, stop);
    }

    /**
     * Returns the specified range of elements in the sorted set stored at key with the scores.
     *
     * @param key   the key to query
     * @param start the minimum index
     * @param stop  the maximum index
     * @return A List of Tuple in the specified range (elements names and their scores)
     */
    public List<Tuple> rangeWithScores(String key, long start, long stop) {
        return jedis.zrangeWithScores(key, start, stop);
    }

    /**
     * Similar to {@link SortedSetCommands#zrevrange(String, long, long) ZREVRANGE} but the reply will
     * include the scores of the returned elements.
     *
     * @param key   the key to query
     * @param start the minimum index
     * @param stop  the maximum index
     * @return A List of Tuple in the specified range (elements names and their scores)
     * @see SortedSetCommands#zrevrange(String, long, long)
     */
    public List<Tuple> revrangeWithScores(String key, long start, long stop) {
        return jedis.zrevrangeWithScores(key, start, stop);
    }

    /**
     * Similar to {@link SortedSetCommands#zrange(String, long, long) ZRANGE} but can be used with additional params.
     *
     * @param key          the key to query
     * @param zRangeParams {@link ZRangeParams}
     * @return A List of Strings in the specified range
     * @see SortedSetCommands#zrange(String, long, long)
     */
    public List<String> range(String key, ZRangeParams zRangeParams) {
        return jedis.zrange(key, zRangeParams);
    }

    /**
     * Similar to {@link SortedSetCommands#zrangeWithScores(String, long, long) ZRANGE} but can be used with additional params.
     *
     * @param key          the key to query
     * @param zRangeParams {@link ZRangeParams}
     * @return A List of Tuple in the specified range (elements names and their scores)
     * @see SortedSetCommands#zrangeWithScores(String, long, long)
     */
    public List<Tuple> rangeWithScores(String key, ZRangeParams zRangeParams) {
        return jedis.zrangeWithScores(key, zRangeParams);
    }

    /**
     * Similar to {@link SortedSetCommands#zrange(String, ZRangeParams) ZRANGE} but stores the result in {@code dest}.
     *
     * @param dest         the storing key
     * @param src          the key to query
     * @param zRangeParams {@link ZRangeParams}
     * @return The number of elements in the resulting sorted set
     * @see SortedSetCommands#zrange(String, ZRangeParams)
     */
    public long rangestore(String dest, String src, ZRangeParams zRangeParams) {
        return jedis.zrangestore(dest, src, zRangeParams);
    }

    /**
     * Return a random element from the sorted set value stored at key.
     * <p>
     * Time complexity O(N) where N is the number of elements returned
     *
     * @param key
     * @return Random String from the set
     */
    public String randmember(String key) {
        return jedis.zrandmember(key);
    }

    /**
     * Return an array of distinct elements. The array's length is either count or the sorted set's
     * cardinality ({@link SortedSetCommands#zcard(String) ZCARD}), whichever is lower.
     * <p>
     * Time complexity O(N) where N is the number of elements returned
     *
     * @param key
     * @param count choose up to count elements
     * @return A list of distinct Strings from the set
     */
    public List<String> randmember(String key, long count) {
        return jedis.zrandmember(key, count);
    }

    /**
     * Similar to {@link SortedSetCommands#zrandmember(String, long) ZRANDMEMBER} but the replay will
     * include the scores with the result.
     *
     * @param key
     * @param count choose up to count elements
     * @return A List of distinct Strings with their scores
     * @see SortedSetCommands#zrandmember(String, long)
     */
    public List<Tuple> randmemberWithScores(String key, long count) {
        return jedis.zrandmemberWithScores(key, count);
    }

    /**
     * Return the sorted set cardinality (number of elements). If the key does not exist 0 is
     * returned, like for empty sorted sets.
     * <p>
     * Time complexity O(1)
     *
     * @param key
     * @return The cardinality (number of elements) of the set as an integer
     */
    public long card(String key) {
        return jedis.zcard(key);
    }

    /**
     * Return the score of the specified element of the sorted set at key. If the specified element
     * does not exist in the sorted set, or the key does not exist at all, a special 'nil' value is
     * returned.
     * <p>
     * Time complexity O(1)
     *
     * @param key
     * @param member
     * @return The score
     */
    public Double score(String key, String member) {
        return jedis.zscore(key, member);
    }

    /**
     * Return the scores associated with the specified members in the sorted set stored at key.
     * For every member that does not exist in the sorted set, a nil value is returned.
     * <p>
     * Time complexity O(N) where N is the number of members being requested
     *
     * @param key
     * @param members
     * @return The scores
     */
    public List<Double> mscore(String key, String... members) {
        return jedis.zmscore(key, members);
    }

    /**
     * Remove and return the member with the highest score in the sorted set stored at key.
     * <p>
     * Time complexity O(log(N)) with N being the number of elements in the sorted set
     *
     * @param key
     * @return The popped element and the score
     */
    public Tuple popmax(String key) {
        return jedis.zpopmax(key);
    }

    /**
     * Remove and return up to count members with the highest scores in the sorted set stored at key.
     * <p>
     * Time complexity O(log(N)*M) with N being the number of elements in the sorted set, and M being
     * the number of elements popped.
     *
     * @param key
     * @param count the number of elements to pop
     * @return A List of popped elements and scores
     */
    public List<Tuple> popmax(String key, int count) {
        return jedis.zpopmax(key, count);
    }

    /**
     * Remove and return the member with the lowest score in the sorted set stored at key.
     * <p>
     * Time complexity O(log(N)) with N being the number of elements in the sorted set
     *
     * @param key
     * @return The popped element and the score
     */
    public Tuple popmin(String key) {
        return jedis.zpopmin(key);
    }

    /**
     * Remove and return up to count members with the lowest scores in the sorted set stored at key.
     * <p>
     * Time complexity O(log(N)*M) with N being the number of elements in the sorted set, and M being
     * the number of elements popped.
     *
     * @param key
     * @param count the number of elements to pop
     * @return A List of popped elements and scores
     */
    public List<Tuple> popmin(String key, int count) {
        return jedis.zpopmin(key, count);
    }

    /**
     * Return the number of elements in the sorted set at key with a score between min and max.
     * <p>
     * Time complexity O(log(N)) with N being the number of elements in the sorted set.
     *
     * @param key the key to query
     * @param min minimum score
     * @param max maximum score
     * @return The number of elements in the specified score range.
     */
    public long count(String key, double min, double max) {
        return jedis.zcount(key, min, max);
    }

    /**
     * Similar to {@link SortedSetCommands#zcount(String, double, double) ZCOUNT} but with <i>exclusive</i> range.
     *
     * @see SortedSetCommands#zcount(String, double, double)
     */
    public long count(String key, String min, String max) {
        return jedis.zcount(key, min, max);
    }

    /**
     * Return all the elements in the sorted set at key with a score between min and max
     * (including elements with score equal to min or max). The elements are considered to
     * be ordered from low to high scores.
     * <p>
     * Time complexity O(log(N)+M) with N being the number of elements in the sorted set
     * and M the number of elements being returned.
     *
     * @param key the key to query
     * @param min minimum score
     * @param max maximum score
     * @return A List of elements in the specified score range
     */
    public List<String> rangeByScore(String key, double min, double max) {
        return jedis.zrangeByScore(key, min, max);
    }

    /**
     * Similar to {@link SortedSetCommands#zrangeByScore(String, double, double) ZRANGE} but with <i>exclusive</i> range.
     *
     * @see SortedSetCommands#zrangeByScore(String, double, double)
     */
    public List<String> rangeByScore(String key, String min, String max) {
        return jedis.zrangeByScore(key, min, max);
    }

    /**
     * Return all the elements in the sorted set at key with a score between max and min
     * (including elements with score equal to max or min). In contrary to the default
     * ordering of sorted sets, for this command the elements are considered to be ordered
     * from high to low scores.
     * <p>
     * The elements having the same score are returned in reverse lexicographical order.
     * <p>
     * Time complexity O(log(N)+M) with N being the number of elements in the sorted set
     * and M the number of elements being returned.
     *
     * @param key the key to query
     * @param max maximum score
     * @param min minimum score
     * @return A List of elements in the specified score range
     */
    public List<String> revrangeByScore(String key, double max, double min) {
        return jedis.zrevrangeByScore(key, max, min);
    }

    /**
     * Similar to {@link SortedSetCommands#zrangeByScore(String, double, double) ZRANGE} but with <i>exclusive</i> range.
     *
     * @param key    the key to query
     * @param min    minimum score
     * @param max    maximum score
     * @param offset the first index of the sub-range
     * @param count  count of the sub-range. A negative count returns all elements from the offset
     * @return A List of elements in the specified score range
     * @see SortedSetCommands#zrangeByScore(String, double, double)
     */
    public List<String> rangeByScore(String key, double min, double max, int offset, int count) {
        return jedis.zrangeByScore(key, min, max, offset, count);
    }

    /**
     * Similar to {@link SortedSetCommands#zrevrangeByScore(String, double, double) ZREVRANGE} but with <i>exclusive</i> range.
     *
     * @see SortedSetCommands#zrevrangeByScore(String, double, double)
     */
    public List<String> revrangeByScore(String key, String max, String min) {
        return jedis.zrevrangeByScore(key, max, min);
    }

    /**
     * Similar to {@link SortedSetCommands#zrangeByScore(String, double, double) ZRANGE} but with <i>limit</i> option,
     *
     * @param key    the key to query
     * @param min    minimum score
     * @param max    maximum score
     * @param offset the first index of the sub-range
     * @param count  count of the sub-range. A negative count returns all elements from the offset
     * @return A List of elements in the specified score range
     * @see SortedSetCommands#zrangeByScore(String, double, double)
     * and with <i>exclusive</i> range.
     */
    public List<String> rangeByScore(String key, String min, String max, int offset, int count) {
        return jedis.zrangeByScore(key, min, max, offset, count);
    }

    /**
     * Similar to {@link SortedSetCommands#zrevrangeByScore(String, double, double) ZRANGE} but with <i>limit</i> option,
     *
     * @param key    the key to query
     * @param max    maximum score
     * @param min    minimum score
     * @param offset the first index of the sub-range
     * @param count  count of the sub-range. A negative count returns all elements from the offset
     * @return A List of elements in the specified score range
     * @see SortedSetCommands#zrevrangeByScore(String, double, double)
     */
    public List<String> revrangeByScore(String key, double max, double min, int offset, int count) {
        return jedis.zrevrangeByScore(key, max, min, offset, count);
    }

    /**
     * Similar to {@link SortedSetCommands#zrangeByScore(String, double, double) ZRANGE} but return with scores.
     *
     * @param key the key to query
     * @param min minimum score
     * @param max maximum score
     * @return A List of elements with scores in the specified score range
     * @see SortedSetCommands#zrangeByScore(String, double, double)
     * return both the element and its score, instead of the element alone.
     */
    public List<Tuple> rangeByScoreWithScores(String key, double min, double max) {
        return jedis.zrangeByScoreWithScores(key, min, max);
    }

    /**
     * Similar to {@link SortedSetCommands#zrevrangeByScore(String, double, double) ZREVRANGE} but return with scores.
     *
     * @param key the key to query
     * @param max maximum score
     * @param min minimum score
     * @return A List of elements with scores in the specified score range
     * @see SortedSetCommands#zrevrangeByScore(String, double, double)
     * return both the element and its score, instead of the element alone.
     */
    public List<Tuple> revrangeByScoreWithScores(String key, double max, double min) {
        return jedis.zrevrangeByScoreWithScores(key, max, min);
    }

    /**
     * Similar to {@link SortedSetCommands#zrangeByScore(String, double, double) ZRANGE} but with <i>limit</i> option,
     * and return with scores.
     *
     * @param key    the key to query
     * @param min    minimum score
     * @param max    maximum score
     * @param offset the first index of the sub-range
     * @param count  count of the sub-range. A negative count returns all elements from the offset
     * @return A List of elements in the specified score range
     * @see SortedSetCommands#zrangeByScore(String, double, double)
     */
    public List<Tuple> rangeByScoreWithScores(String key, double min, double max, int offset, int count) {
        return jedis.zrangeByScoreWithScores(key, min, max, offset, count);
    }

    /**
     * Similar to {@link SortedSetCommands#zrevrangeByScore(String, double, double) ZREVRANGE} but with <i>limit</i> option,
     *
     * @param key    the key to query
     * @param max    maximum score
     * @param min    minimum score
     * @param offset the first index of the sub-range
     * @param count  count of the sub-range. A negative count returns all elements from the offset
     * @return A List of elements in the specified score range
     * @see SortedSetCommands#zrevrangeByScore(String, double, double)
     * and with <i>exclusive</i> range.
     */
    public List<String> revrangeByScore(String key, String max, String min, int offset, int count) {
        return jedis.zrevrangeByScore(key, max, min, offset, count);
    }

    /**
     * Similar to {@link SortedSetCommands#zrangeByScore(String, double, double) ZRANGE} but with <i>exclusive</i> range,
     * and return with scores.
     *
     * @see SortedSetCommands#zrangeByScore(String, double, double)
     */
    public List<Tuple> rangeByScoreWithScores(String key, String min, String max) {
        return jedis.zrangeByScoreWithScores(key, min, max);
    }

    /**
     * Similar to {@link SortedSetCommands#zrevrangeByScore(String, double, double) ZREVRANGE} but with <i>exclusive</i> range,
     * and return with scores.
     *
     * @see SortedSetCommands#zrevrangeByScore(String, double, double)
     */
    public List<Tuple> revrangeByScoreWithScores(String key, String max, String min) {
        return jedis.zrevrangeByScoreWithScores(key, max, min);
    }

    /**
     * Similar to {@link SortedSetCommands#zrangeByScore(String, double, double) ZRANGE} but with <i>exclusive</i> range,
     * with <i>limit</i> options and return with scores.
     *
     * @param key    the key to query
     * @param min    minimum score
     * @param max    maximum score
     * @param offset the first index of the sub-range
     * @param count  count of the sub-range. A negative count returns all elements from the offset
     * @return A List of elements in the specified score range
     * @see SortedSetCommands#zrangeByScore(String, String, String)
     */
    public List<Tuple> rangeByScoreWithScores(String key, String min, String max, int offset, int count) {
        return jedis.zrangeByScoreWithScores(key, min, max, offset, count);
    }

    /**
     * Similar to {@link SortedSetCommands#zrevrangeByScore(String, double, double) ZREVRANGE} but with
     * <i>limit</i> options and return with scores.
     *
     * @param key    the key to query
     * @param max    maximum score
     * @param min    minimum score
     * @param offset the first index of the sub-range
     * @param count  count of the sub-range. A negative count returns all elements from the offset
     * @return A List of elements in the specified score range
     * @see SortedSetCommands#zrevrangeByScore(String, double, double)
     */
    public List<Tuple> revrangeByScoreWithScores(String key, double max, double min, int offset, int count) {
        return jedis.zrevrangeByScoreWithScores(key, max, min, offset, count);
    }

    /**
     * Similar to {@link SortedSetCommands#zrevrangeByScore(String, double, double) ZREVRANGE} but with
     * <i>exclusive</i> range, with <i>limit</i> options and return with scores.
     *
     * @param key    the key to query
     * @param max    maximum score
     * @param min    minimum score
     * @param offset the first index of the sub-range
     * @param count  count of the sub-range. A negative count returns all elements from the offset
     * @return A List of elements in the specified score range
     * @see SortedSetCommands#zrevrangeByScore(String, double, double)
     */
    public List<Tuple> revrangeByScoreWithScores(String key, String max, String min, int offset, int count) {
        return jedis.zrevrangeByScoreWithScores(key, max, min, offset, count);
    }

    /**
     * Remove all elements in the sorted set at key with rank between start and end. Start and end are
     * 0-based with rank 0 being the element with the lowest score. Both start and end can be negative
     * numbers, where they indicate offsets starting at the element with the highest rank. For
     * example: -1 is the element with the highest score, -2 the element with the second highest score
     * and so forth.
     * <p>
     * Time complexity O(log(N))+O(M) with N being the number of elements in the sorted set and M the
     * number of elements removed by the operation.
     *
     * @param key
     * @param start
     * @param stop
     * @return The number of elements removed
     */
    public long remrangeByRank(String key, long start, long stop) {
        return jedis.zremrangeByRank(key, start, stop);
    }

    /**
     * Remove all the elements in the sorted set at key with a score between min and max (including
     * elements with score equal to min or max).
     * <p>
     * Time complexity O(log(N))+O(M) with N being the number of elements in the sorted set and M the
     * number of elements removed by the operation.
     *
     * @param key
     * @param min minimum score to remove
     * @param max maximum score to remove
     * @return The number of elements removed
     */
    public long remrangeByScore(String key, double min, double max) {
        return jedis.zremrangeByScore(key, min, max);
    }

    /**
     * Similar to {@link SortedSetCommands#zremrangeByScore(String, double, double) ZREMRANGE} but with <i>limit</i> option.
     *
     * @see SortedSetCommands#zremrangeByScore(String, double, double)
     */
    public long remrangeByScore(String key, String min, String max) {
        return jedis.zremrangeByScore(key, min, max);
    }

    /**
     * Return the number of elements in the sorted set at key with a value between min and max, when all
     * the elements in a sorted set are inserted with the same score, in order to force lexicographical ordering.
     * <p>
     * Time complexity O(log(N)) with N being the number of elements in the sorted set.
     *
     * @param key
     * @param min minimum value
     * @param max maximum value
     * @return The number of elements in the specified score range
     */
    public long lexcount(String key, String min, String max) {
        return jedis.zlexcount(key, min, max);
    }

    /**
     * Return all the elements in the sorted set at key with a value between min and max, when all
     * the elements in a sorted set are inserted with the same score, in order to force lexicographical ordering.
     * <p>
     * Time complexity O(log(N)+M) with N being the number of elements in the sorted set and M the number of
     * elements being returned.
     *
     * @param key
     * @param min minimum value
     * @param max maximum value
     * @return A List of elements in the specified score range
     */
    public List<String> rangeByLex(String key, String min, String max) {
        return jedis.zrangeByLex(key, min, max);
    }

    /**
     * Similar to {@link SortedSetCommands#zrangeByLex(String, String, String) ZRANGE} but with <i>limit</i> option.
     *
     * @param key
     * @param min    minimum value
     * @param max    maximum value
     * @param offset the first index of the sub-range
     * @param count  count of the sub-range. A negative count returns all elements from the offset
     * @return A List of elements in the specified score range
     * @see SortedSetCommands#zrangeByLex(String, String, String)
     */
    public List<String> rangeByLex(String key, String min, String max, int offset, int count) {
        return jedis.zrangeByLex(key, min, max, offset, count);
    }

    /**
     * Return all the elements in the sorted set at key with a value between max and min, when all
     * the elements in a sorted set are inserted with the same score, in order to force lexicographical ordering.
     * <p>
     * Time complexity O(log(N)+M) with N being the number of elements in the sorted set and M the number of
     * elements being returned.
     *
     * @param key
     * @param max maximum value
     * @param min minimum value
     * @return A List of elements in the specified score range
     */
    public List<String> revrangeByLex(String key, String max, String min) {
        return jedis.zrevrangeByLex(key, max, min);
    }

    /**
     * Similar to {@link SortedSetCommands#zrevrangeByLex(String, String, String) ZRANGE} but with <i>limit</i> option.
     *
     * @param key
     * @param max    maximum value
     * @param min    minimum value
     * @param offset the first index of the sub-range
     * @param count  count of the sub-range. A negative count returns all elements from the offset
     * @return A List of elements in the specified score range
     * @see SortedSetCommands#zrevrangeByLex(String, String, String)
     */
    public List<String> revrangeByLex(String key, String max, String min, int offset, int count) {
        return jedis.zrevrangeByLex(key, max, min, offset, count);
    }

    /**
     * Remove all elements in the sorted set stored at key between the lexicographical range specified by min and max,
     * when all the elements in a sorted set are inserted with the same score, in order to force lexicographical ordering.
     * <p>
     * Time complexity O(log(N)+M) with N being the number of elements in the sorted set and M the number of elements
     * removed by the operation.
     *
     * @param key
     * @param min minimum value to remove
     * @param max maximum value to remove
     * @return The number of elements removed
     */
    public long remrangeByLex(String key, String min, String max) {
        return jedis.zremrangeByLex(key, min, max);
    }

    public ScanResult<Tuple> scan(String key, String cursor) {
        return scan(key, cursor, new ScanParams());
    }

    public ScanResult<Tuple> scan(String key, String cursor, ScanParams params) {
        return jedis.zscan(key, cursor, params);
    }

    /**
     * The blocking version of {@link SortedSetCommands#zpopmax(String) ZPOPMAX}
     *
     * @param timeout specifying the maximum number of seconds to block. A timeout of zero can
     *                be used to block indefinitely.
     * @param keys
     */
    public KeyValue<String, Tuple> bpopmax(double timeout, String... keys) {
        return jedis.bzpopmax(timeout, keys);
    }

    /**
     * The blocking version of {@link SortedSetCommands#zpopmin(String) ZPOPMIN}
     *
     * @param timeout specifying the maximum number of seconds to block. A timeout of zero can
     *                be used to block indefinitely.
     * @param keys
     */
    public KeyValue<String, Tuple> bpopmin(double timeout, String... keys) {
        return jedis.bzpopmin(timeout, keys);
    }

    /**
     * Compute the difference between all the sets in the given keys.
     * <p>
     * Time complexity O(L + (N-K)log(N)) worst case where L is the total number of elements in
     * all the sets, N is the size of the first set, and K is the size of the result set.
     *
     * @param keys group of sets
     * @return The result of the difference
     */
    public List<String> diff(String... keys) {
        return jedis.zdiff(keys);
    }

    /**
     * Compute the difference between all the sets in the given keys. Return the result with scores.
     *
     * @param keys group of sets
     * @return The result of the difference with their scores
     */
    public List<Tuple> diffWithScores(String... keys) {
        return jedis.zdiffWithScores(keys);
    }

    /**
     * Compute the difference between all the sets in the given keys. Store the result in dstkey.
     *
     * @param dstkey
     * @param keys   group of sets
     * @return The number of elements in the resulting sorted set at dstkey.
     */
    public long diffstore(String dstkey, String... keys) {
        return jedis.zdiffstore(dstkey, keys);
    }

    /**
     * Compute the intersection between all the sets in the given keys.
     * <p>
     * Time complexity O(N*K)+O(M*log(M)) worst case with N being the smallest input sorted set, K being
     * the number of input sorted sets and M being the number of elements in the resulting sorted set.
     *
     * @param params {@link ZParams}
     * @param keys   group of sets
     * @return The result of the intersection
     */
    public List<String> inter(ZParams params, String... keys) {
        return jedis.zinter(params, keys);
    }

    /**
     * Compute the intersection between all the sets in the given keys. Return the result with scores.
     *
     * @param params {@link ZParams}
     * @param keys   group of sets
     * @return The result of the intersection with their scores
     */
    public List<Tuple> interWithScores(ZParams params, String... keys) {
        return jedis.zinterWithScores(params, keys);
    }

    /**
     * Compute the intersection between all the sets in the given keys. Store the result in dstkey.
     *
     * @param dstkey
     * @param sets   group of sets
     * @return The number of elements in the resulting sorted set at dstkey
     */
    public long interstore(String dstkey, String... sets) {
        return jedis.zinterstore(dstkey, sets);
    }

    /**
     * Compute the intersection between all the sets in the given keys. Store the result in dstkey.
     *
     * @param dstkey
     * @param params {@link ZParams}
     * @param sets   group of sets
     * @return The number of elements in the resulting sorted set at dstkey
     */
    public long interstore(String dstkey, ZParams params, String... sets) {
        return jedis.zinterstore(dstkey, params, sets);
    }

    /**
     * Similar to {@link SortedSetCommands#zinter(ZParams, String...) ZINTER}, but
     * instead of returning the result set, it returns just the cardinality of the result.
     * <p>
     * Time complexity O(N*K) worst case with N being the smallest input sorted set, K
     * being the number of input sorted sets
     *
     * @param keys group of sets
     * @return The number of elements in the resulting intersection
     * @see SortedSetCommands#zinter(ZParams, String...)
     */
    public long intercard(String... keys) {
        return jedis.zintercard(keys);
    }

    /**
     * Similar to {@link SortedSetCommands#zinter(ZParams, String...) ZINTER}, but
     * instead of returning the result set, it returns just the cardinality of the result.
     * <p>
     * Time complexity O(N*K) worst case with N being the smallest input sorted set, K
     * being the number of input sorted sets
     *
     * @param limit If the intersection cardinality reaches limit partway through the computation,
     *              the algorithm will exit and yield limit as the cardinality
     * @param keys  group of sets
     * @return The number of elements in the resulting intersection
     * @see SortedSetCommands#zinter(ZParams, String...)
     */
    public long intercard(long limit, String... keys) {
        return jedis.zintercard(limit, keys);
    }

    /**
     * Compute the union between all the sets in the given keys.
     * <p>
     * Time complexity O(N)+O(M log(M)) with N being the sum of the sizes of the input sorted sets,
     * and M being the number of elements in the resulting sorted set.
     *
     * @param params {@link ZParams}
     * @param keys   group of sets
     * @return The result of the union
     */
    public List<String> union(ZParams params, String... keys) {
        return jedis.zunion(params, keys);
    }

    /**
     * Compute the union between all the sets in the given keys. Return the result with scores.
     *
     * @param params {@link ZParams}
     * @param keys   group of sets
     * @return The result of the union with their scores
     */
    public List<Tuple> unionWithScores(ZParams params, String... keys) {
        return jedis.zunionWithScores(params, keys);
    }

    /**
     * Compute the union between all the sets in the given keys. Store the result in dstkey.
     *
     * @param dstkey
     * @param sets   group of sets
     * @return The number of elements in the resulting sorted set at dstkey
     */
    public long unionstore(String dstkey, String... sets) {
        return jedis.zunionstore(dstkey, sets);
    }

    /**
     * Compute the union between all the sets in the given keys. Store the result in dstkey.
     *
     * @param dstkey
     * @param params {@link ZParams}
     * @param sets   group of sets
     * @return The number of elements in the resulting sorted set at dstkey
     */
    public long unionstore(String dstkey, ZParams params, String... sets) {
        return jedis.zunionstore(dstkey, params, sets);
    }

    public KeyValue<String, List<Tuple>> mpop(SortedSetOption option, String... keys) {
        return jedis.zmpop(option, keys);
    }

    public KeyValue<String, List<Tuple>> mpop(SortedSetOption option, int count, String... keys) {
        return jedis.zmpop(option, count, keys);
    }

    public KeyValue<String, List<Tuple>> bmpop(double timeout, SortedSetOption option, String... keys) {
        return jedis.bzmpop(timeout, option, keys);
    }

    public KeyValue<String, List<Tuple>> bmpop(double timeout, SortedSetOption option, int count, String... keys) {
        return jedis.bzmpop(timeout, option, count, keys);
    }
}
