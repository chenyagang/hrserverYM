package org.sang.mapper;

import java.util.List;
import java.util.Map;

public interface BaseMapper<T> {

    int insert(T t);

    int update(T t);

    List<T> selectPageByMap(Map<String, Object> map);

    List<T> selectPage(T T);

    T selectById(String id);
    
    int deleteById(String id);

    int selectPageCount(T T);

    int selectPageCountByMap(Map<String, Object> map);
    
    List<T> selectAll(T t);

}