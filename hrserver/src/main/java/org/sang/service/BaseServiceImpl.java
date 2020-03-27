package org.sang.service;

import java.util.List;
import java.util.Map;
import org.sang.mapper.BaseMapper;
import org.sang.service.IBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BaseServiceImpl<T> implements IBaseService<T>{

    @Autowired
    private BaseMapper<T> baseMapper;

    public int add(T t){
        int i =0;
        try {
            i = baseMapper.insert(t);
        }catch (Exception e){
            e.printStackTrace();
        }
        return i;
    }

    public int edit(T t){
        return baseMapper.update(t);
    }

    public List<T> queryPageByMap(Map<String, Object> map){
        return baseMapper.selectPageByMap(map);
    }

    public int queryPageCount(T t){
        return baseMapper.selectPageCount(t);
    }
    
    public int queryPageCountByMap(Map<String, Object> map){
        return baseMapper.selectPageCountByMap(map);
    }
    
    
    public List<T> queryPage(T t){
        return baseMapper.selectPage(t);
    }

    public T queryById(String id){
        return baseMapper.selectById(id);
    }
    
    public int deleteById(String id) {
    	return baseMapper.deleteById(id);
    }

    @Override
    public List<T> queryAll(T t) {
        return baseMapper.selectAll(t);
    }

    public int updateShowEdit(String id){

        try {
          return baseMapper.updateShowEdit(id);
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }
}