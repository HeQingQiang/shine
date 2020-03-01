package com.shine.goods.service;

import com.github.pagehelper.PageInfo;
import com.shine.goods.pojo.StockBack;

import java.util.List;

/**
 * @Description:StockBack的Dao
 * ----------------------------
 * @Author:shine
 * @Date 2020/02/29 0:12
 * ----------------------------
 *****/
public interface StockBackService {

    /***
     * StockBack多条件分页查询
     * @param stockBack
     * @param page
     * @param size
     * @return
     */
    PageInfo<StockBack> findPage(StockBack stockBack, int page, int size);

    /***
     * StockBack分页查询
     * @param page
     * @param size
     * @return
     */
    PageInfo<StockBack> findPage(int page, int size);

    /***
     * StockBack多条件搜索方法
     * @param stockBack
     * @return
     */
    List<StockBack> findList(StockBack stockBack);

    /***
     * 删除StockBack
     * @param id
     */
    void delete(String id);

    /***
     * 修改StockBack数据
     * @param stockBack
     */
    void update(StockBack stockBack);

    /***
     * 新增StockBack
     * @param stockBack
     */
    void add(StockBack stockBack);

    /**
     * 根据ID查询StockBack
     * @param id
     * @return
     */
     StockBack findById(String id);

    /***
     * 查询所有StockBack
     * @return
     */
    List<StockBack> findAll();
}
