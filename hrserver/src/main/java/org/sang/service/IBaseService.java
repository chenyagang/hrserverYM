package org.sang.service;

import java.util.List;
import java.util.Map;

public interface IBaseService<T> {

    int add(T t);

    int edit(T t);

    List<T> queryPageByMap(Map<String, Object> map);

    List<T> queryPage(T t);

    T queryById(String id);

    List<T> queryAll(T t);

}