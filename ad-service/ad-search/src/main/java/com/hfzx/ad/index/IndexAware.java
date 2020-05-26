package com.hfzx.ad.index;

/**
 * @Author: zhangyu
 * @Description:
 * @Date: in 2020/4/25 10:28
 */
public interface IndexAware<K, V> {

    V get(K key);

    void add(K key, V value);

    void update(K key, V value);

    void delete(K key, V value);

}
