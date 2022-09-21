package com.example.blog.ability.redis;

import org.apache.ibatis.cache.Cache;

public class RedisCache implements Cache {
    private final String id;

    public RedisCache(String id) {
        this.id=id;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public void putObject(Object o, Object o1) {

    }

    @Override
    public Object getObject(Object o) {
        return null;
    }

    @Override
    public Object removeObject(Object o) {
        return null;
    }

    @Override
    public void clear() {

    }

    @Override
    public int getSize() {
        return 0;
    }
}
