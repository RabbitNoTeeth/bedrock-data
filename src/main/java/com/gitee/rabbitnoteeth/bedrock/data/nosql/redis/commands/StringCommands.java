package com.gitee.rabbitnoteeth.bedrock.data.nosql.redis.commands;

import redis.clients.jedis.JedisPooled;
import redis.clients.jedis.commands.KeyBinaryCommands;
import redis.clients.jedis.params.GetExParams;
import redis.clients.jedis.params.LCSParams;
import redis.clients.jedis.params.SetParams;
import redis.clients.jedis.resps.LCSMatchResult;

import java.util.List;

public class StringCommands {

    private final JedisPooled jedis;

    public StringCommands(JedisPooled jedis) {
        this.jedis = jedis;
    }


    /**
     * <b><a href="http://redis.io/commands/set">Set Command</a></b>
     * Set the string value as value of the key. The string can't be longer than 1073741824 bytes (1 GB).
     * <p>
     * Time complexity: O(1)
     *
     * @param key
     * @param value
     * @return OK
     */
    public String set(String key, String value) {
        return jedis.set(key, value);
    }

    /**
     * <b><a href="http://redis.io/commands/set">Set Command</a></b>
     * Set the string value as value of the key. Can be used with optional params.
     * <p>
     * Time complexity: O(1)
     *
     * @param key
     * @param value
     * @param params {@link SetParams}
     * @return simple-string-reply {@code OK} if {@code SET} was executed correctly, or {@code null}
     * if the {@code SET} operation was not performed because the user specified the NX or XX option
     * but the condition was not met.
     */
    public String set(String key, String value, SetParams params) {
        return jedis.set(key, value, params);
    }

    /**
     * <b><a href="http://redis.io/commands/get">Get Command</a></b>
     * Get the value of the specified key. If the key does not exist the special value 'nil' is
     * returned. If the value stored at key is not a string an error is returned because GET can only
     * handle string values.
     * <p>
     * Time complexity: O(1)
     *
     * @param key
     * @return The value stored in key
     */
    public String get(String key) {
        return jedis.get(key);
    }

    public String setGet(String key, String value) {
        return jedis.setGet(key, value);
    }

    public String setGet(String key, String value, SetParams params) {
        return jedis.setGet(key, value, params);
    }

    /**
     * <b><a href="http://redis.io/commands/getdel">GetDel Command</a></b>
     * Get the value of key and delete the key. This command is similar to GET, except for the fact
     * that it also deletes the key on success (if and only if the key's value type is a string).
     * <p>
     * Time complexity: O(1)
     *
     * @param key
     * @return The value stored in key
     */
    public String getDel(String key) {
        return jedis.getDel(key);
    }

    /**
     * <b><a href="http://redis.io/commands/getex">GetEx Command</a></b>
     * Get the value of key and optionally set its expiration. GETEX is similar to {@link redis.clients.jedis.commands.StringCommands#get(String) GET},
     * but is a write command with additional options:
     * EX seconds -- Set the specified expire time, in seconds.
     * PX milliseconds -- Set the specified expire time, in milliseconds.
     * EXAT timestamp-seconds -- Set the specified Unix time at which the key will expire, in seconds.
     * PXAT timestamp-milliseconds -- Set the specified Unix time at which the key will expire, in milliseconds.
     * PERSIST -- Remove the time to live associated with the key.
     * <p>
     * Time complexity: O(1)
     *
     * @param key
     * @param params {@link GetExParams}
     * @return The value stored in key
     */
    public String getEx(String key, GetExParams params) {
        return jedis.getEx(key, params);
    }

    /**
     * <b><a href="http://redis.io/commands/setrange">SetRange Command</a></b>
     * GETRANGE overwrite part of the string stored at key, starting at the specified offset, for the entire
     * length of value. If the offset is larger than the current length of the string at key, the string is
     * padded with zero-bytes to make offset fit. Non-existing keys are considered as empty strings, so this
     * command will make sure it holds a string large enough to be able to set value at offset.
     * <p>
     * Time complexity: O(1)
     *
     * @param key
     * @param offset
     * @param value
     * @return The length of the string after it was modified by the command
     */
    public long setrange(String key, long offset, String value) {
        return jedis.setrange(key, offset, value);
    }

    /**
     * <b><a href="http://redis.io/commands/getrange">GetRange Command</a></b>
     * Return the substring of the string value stored at key, determined by the offsets start
     * and end (both are inclusive). Negative offsets can be used in order to provide an offset starting
     * from the end of the string. So -1 means the last character, -2 the penultimate and so forth.
     * <p>
     * Time complexity: O(N) where N is the length of the returned string
     *
     * @param key
     * @param startOffset
     * @param endOffset
     * @return The substring
     */
    public String getrange(String key, long startOffset, long endOffset) {
        return jedis.getrange(key, startOffset, endOffset);
    }

    /**
     * <b><a href="http://redis.io/commands/getset">GetSet Command</a></b>
     * GETSET is an atomic set this value and return the old value command. Set key to the string
     * value and return the old value stored at key. The string can't be longer than 1073741824 byte (1 GB).
     * <p>
     * Time complexity: O(1)
     *
     * @param key
     * @param value
     * @return The old value that was stored in key
     */
    public String getSet(String key, String value) {
        return jedis.getSet(key, value);
    }

    /**
     * <b><a href="http://redis.io/commands/setnx">SetNE Command</a></b>
     * SETNX works exactly like {@link redis.clients.jedis.commands.StringCommands#set(String, String) SET} with the only difference that if
     * the key already exists no operation is performed. SETNX actually means "SET if Not Exists".
     * <p>
     * Time complexity: O(1)
     *
     * @param key
     * @param value
     * @return 1 if the key was set, 0 otherwise
     */
    public long setnx(String key, String value) {
        return jedis.setnx(key, value);
    }

    /**
     * <b><a href="http://redis.io/commands/setex">SetEx Command</a></b>
     * The command is exactly equivalent to the following group of commands:
     * {@link redis.clients.jedis.commands.StringCommands#set(String, String) SET} + {@link KeyBinaryCommands#expire(byte[], long) EXPIRE}.
     * The operation is atomic.
     * <p>
     * Time complexity: O(1)
     *
     * @param key
     * @param seconds
     * @param value
     * @return OK
     */
    public String setex(String key, long seconds, String value) {
        return jedis.setex(key, seconds, value);
    }

    /**
     * <b><a href="http://redis.io/commands/psetex">PSetEx Command</a></b>
     * PSETEX works exactly like {@link redis.clients.jedis.commands.StringCommands#setex(String, long, String) SETEX} with the sole difference
     * that the expire time is specified in milliseconds instead of seconds.
     * <p>
     * Time complexity: O(1)
     *
     * @param key
     * @param milliseconds
     * @param value
     * @return OK
     */
    public String psetex(String key, long milliseconds, String value) {
        return jedis.psetex(key, milliseconds, value);
    }

    /**
     * <b><a href="http://redis.io/commands/mget">MGet Command</a></b>
     * Get the values of all the specified keys. If one or more keys don't exist or is not of type
     * String, a 'nil' value is returned instead of the value of the specified key, but the operation
     * never fails.
     * <p>
     * Time complexity: O(1) for every key
     *
     * @param keys
     * @return Multi bulk reply
     */
    public List<String> mget(String... keys) {
        return jedis.mget(keys);
    }

    /**
     * <b><a href="http://redis.io/commands/mset">MSet Command</a></b>
     * Set the respective keys to the respective values. MSET will replace old values with new
     * values, while {@link redis.clients.jedis.commands.StringCommands#msetnx(String...) MSETNX} will not perform any operation at all even
     * if just a single key already exists.
     * <p>
     * Because of this semantic MSETNX can be used in order to set different keys representing
     * different fields of an unique logic object in a way that ensures that either all the fields or
     * none at all are set.
     * <p>
     * Both MSET and MSETNX are atomic operations. This means that for instance if the keys A and B
     * are modified, another connection talking to Redis can either see the changes to both A and B at
     * once, or no modification at all.
     *
     * @param keysvalues pairs of keys and their values
     *                   e.g mset("foo", "foovalue", "bar", "barvalue")
     * @return OK
     */
    public String mset(String... keysvalues) {
        return jedis.mset(keysvalues);
    }

    /**
     * <b><a href="http://redis.io/commands/msetnx">MSetNX Command</a></b>
     * Set the respective keys to the respective values. {@link redis.clients.jedis.commands.StringCommands#mset(String...) MSET} will
     * replace old values with new values, while MSETNX will not perform any operation at all even if
     * just a single key already exists.
     * <p>
     * Because of this semantic MSETNX can be used in order to set different keys representing
     * different fields of an unique logic object in a way that ensures that either all the fields or
     * none at all are set.
     * <p>
     * Both MSET and MSETNX are atomic operations. This means that for instance if the keys A and B
     * are modified, another connection talking to Redis can either see the changes to both A and B at
     * once, or no modification at all.
     *
     * @param keysvalues pairs of keys and their values
     *                   e.g msetnx("foo", "foovalue", "bar", "barvalue")
     * @return 1 if the all the keys were set, 0 if no key was set (at least one key already existed)
     */
    public long msetnx(String... keysvalues) {
        return jedis.msetnx(keysvalues);
    }

    /**
     * <b><a href="http://redis.io/commands/incr">Incr Command</a></b>
     * Increment the number stored at key by one. If the key does not exist or contains a value of a
     * wrong type, set the key to the value of "0" before to perform the increment operation.
     * <p>
     * INCR commands are limited to 64-bit signed integers.
     * <p>
     * Note: this is actually a string operation, that is, in Redis there are not "integer" types.
     * Simply the string stored at the key is parsed as a base 10 64-bit signed integer, incremented,
     * and then converted back as a string.
     * <p>
     * Time complexity: O(1)
     *
     * @param key the key to increment
     * @return The value of the key after the increment
     */
    public long incr(String key) {
        return jedis.incr(key);
    }

    /**
     * <b><a href="http://redis.io/commands/incrby">IncrBy Command</a></b>
     * INCRBY work just like {@link redis.clients.jedis.commands.StringCommands#incr(String) INCR} but instead to increment by 1 the
     * increment is integer.
     * <p>
     * INCR commands are limited to 64-bit signed integers.
     * <p>
     * Note: this is actually a string operation, that is, in Redis there are not "integer" types.
     * Simply the string stored at the key is parsed as a base 10 64-bit signed integer, incremented,
     * and then converted back as a string.
     * <p>
     * Time complexity: O(1)
     *
     * @param key       the key to increment
     * @param increment the value to increment by
     * @return The value of the key after the increment
     */
    public long incrBy(String key, long increment) {
        return jedis.incrBy(key, increment);
    }

    /**
     * <b><a href="http://redis.io/commands/incrbyfloat">IncrByFloat Command</a></b>
     * INCRBYFLOAT work just like {@link redis.clients.jedis.commands.StringCommands#incrBy(String, long)} INCRBY} but increments by floats
     * instead of integers.
     * <p>
     * INCRBYFLOAT commands are limited to double precision floating point values.
     * <p>
     * Note: this is actually a string operation, that is, in Redis there are not "double" types.
     * Simply the string stored at the key is parsed as a base double precision floating point value,
     * incremented, and then converted back as a string. There is no DECRYBYFLOAT but providing a
     * negative value will work as expected.
     * <p>
     * Time complexity: O(1)
     *
     * @param key       the key to increment
     * @param increment the value to increment by
     * @return The value of the key after the increment
     */
    public double incrByFloat(String key, double increment) {
        return jedis.incrByFloat(key, increment);
    }

    /**
     * <b><a href="http://redis.io/commands/decr">Decr Command</a></b>
     * Decrement the number stored at key by one. If the key does not exist or contains a value of a
     * wrong type, set the key to the value of "0" before to perform the decrement operation.
     * <p>
     * DECR commands are limited to 64-bit signed integers.
     * <p>
     * Note: this is actually a string operation, that is, in Redis there are not "integer" types.
     * Simply the string stored at the key is parsed as a base 10 64-bit signed integer, incremented,
     * and then converted back as a string.
     * <p>
     * Time complexity: O(1)
     *
     * @param key the key to decrement
     * @return The value of the key after the decrement
     */
    public long decr(String key) {
        return jedis.decr(key);
    }

    /**
     * <b><a href="http://redis.io/commands/decrby">DecrBy Command</a></b>
     * DECRBY work just like {@link redis.clients.jedis.commands.StringCommands#decr(String) DECR} but instead to decrement by 1 the
     * decrement is integer.
     * <p>
     * DECRBY commands are limited to 64-bit signed integers.
     * <p>
     * Note: this is actually a string operation, that is, in Redis there are not "integer" types.
     * Simply the string stored at the key is parsed as a base 10 64-bit signed integer, incremented,
     * and then converted back as a string.
     * <p>
     * Time complexity: O(1)
     *
     * @param key       the key to decrement
     * @param decrement the value to decrement by
     * @return The value of the key after the decrement
     */
    public long decrBy(String key, long decrement) {
        return jedis.decrBy(key, decrement);
    }

    /**
     * <b><a href="http://redis.io/commands/append">Append Command</a></b>
     * If the key already exists and is a string, this command appends the provided value at the end
     * of the string. If the key does not exist it is created and set as an empty string, so APPEND
     * will be very similar to SET in this special case.
     * <p>
     * Time complexity: O(1). The amortized time complexity is O(1) assuming the appended value is
     * small and the already present value is of any size, since the dynamic string library used by
     * Redis will double the free space available on every reallocation.
     *
     * @param key   the key to append to
     * @param value the value to append
     * @return The total length of the string after the append operation.
     */
    public long append(String key, String value) {
        return jedis.append(key, value);
    }

    /**
     * <b><a href="http://redis.io/commands/substr">SubStr Command</a></b>
     * Return a subset of the string from offset start to offset end (both offsets are inclusive).
     * Negative offsets can be used in order to provide an offset starting from the end of the string.
     * So -1 means the last char, -2 the penultimate and so forth.
     * <p>
     * The function handles out of range requests without raising an error, but just limiting the
     * resulting range to the actual length of the string.
     * <p>
     * Time complexity: O(start+n) (with start being the start index and n the total length of the
     * requested range). Note that the lookup part of this command is O(1) so for small strings this
     * is actually an O(1) command.
     *
     * @param key
     * @param start
     * @param end
     * @return The substring
     */
    public String substr(String key, int start, int end) {
        return jedis.substr(key, start, end);
    }

    /**
     * <b><a href="http://redis.io/commands/strlen">StrLen Command</a></b>
     * Return the length of the string value stored at key.
     *
     * @param key
     * @return The length of the string at key, or 0 when key does not exist
     */
    public long strlen(String key) {
        return jedis.strlen(key);
    }

    /**
     * Calculate the longest common subsequence of keyA and keyB.
     *
     * @param keyA
     * @param keyB
     * @param params
     * @return According to LCSParams to decide to return content to fill LCSMatchResult.
     */
    public LCSMatchResult lcs(String keyA, String keyB, LCSParams params) {
        return jedis.lcs(keyA, keyB, params);
    }


}
