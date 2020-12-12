package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.EpiUserType;
import epi.test_framework.GenericTest;
import epi.test_framework.TestFailure;

import java.util.LinkedHashMap;
import java.util.List;

public class LruCache {
    int capacity;
    LinkedHashMap<Integer, Integer> cache;
    LruCache(final int capacity) {
        this.capacity = capacity;
        cache = new LinkedHashMap();
    }

    public Integer lookup(Integer key) {
        if (cache.containsKey(key)) {
            int val = cache.remove(key);
            // Readd pair to the cache, essentially making it the youngest in the cache
            cache.put(key, val);
            return val;
        }
        return -1;
    }

    public void insert(Integer key, Integer value) {
        if (cache.containsKey(key)) {
            // Readd pair to the cache, essentially making it the youngest in the cache
            cache.put(key, cache.remove(key));
            return;
        }
        // If at capacity, remove the first entry (should be the oldest)
        if (cache.size() >= capacity) {
            // Removes the oldest by pulling the first key from the keySet
            cache.remove(cache.keySet().iterator().next());
        }
        cache.put(key, value);
    }

    public Boolean erase(Object key) {
        return cache.remove(key) != null;
    }

    @EpiUserType(ctorParams = {String.class, int.class, int.class})
    public static class Op {
        String code;
        int arg1;
        int arg2;

        public Op(String code, int arg1, int arg2) {
            this.code = code;
            this.arg1 = arg1;
            this.arg2 = arg2;
        }
    }

    @EpiTest(testDataFile = "lru_cache.tsv")
    public static void lruCacheTester(List<Op> commands) throws TestFailure {
        if (commands.isEmpty() || !commands.get(0).code.equals("LruCache")) {
            throw new RuntimeException("Expected LruCache as first command");
        }
        LruCache cache = new LruCache(commands.get(0).arg1);
        for (Op op : commands.subList(1, commands.size())) {
            int result;
            switch (op.code) {
                case "lookup":
                    result = cache.lookup(op.arg1);
                    if (result != op.arg2) {
                        throw new TestFailure("Lookup: expected " + String.valueOf(op.arg2) +
                                ", got " + String.valueOf(result));
                    }
                    break;
                case "insert":
                    cache.insert(op.arg1, op.arg2);
                    break;
                case "erase":
                    result = cache.erase(op.arg1) ? 1 : 0;
                    if (result != op.arg2) {
                        throw new TestFailure("Erase: expected " + String.valueOf(op.arg2) +
                                ", got " + String.valueOf(result));
                    }
                    break;
                default:
                    throw new RuntimeException("Unexpected command " + op.code);
            }
        }
    }

    public static void main(String[] args) {
        System.exit(
                GenericTest
                        .runFromAnnotations(args, "LruCache.java",
                                new Object() {
                                }.getClass().getEnclosingClass())
                        .ordinal());
    }
}
