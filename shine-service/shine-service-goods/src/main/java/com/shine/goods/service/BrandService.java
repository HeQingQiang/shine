package com.shine.goods.service;

import com.github.pagehelper.PageInfo;
import com.shine.goods.pojo.Brand;

import java.util.List;

/***********
 *@Author: Shine
 *@Description:
 *@Data: Created in 2019/12/25 20:27
 *@Modified By:
 *****/
public interface BrandService {

    /**
     * 查询所有品牌。
     * @return 品牌集合
     */
    List<Brand> findAll();

    /**
     * 根据ID查询.
     * @param id 品牌ID
     * @return 品牌
     */
    Brand findById(Integer id);

    /**
     * 添加品牌.
     * @param brand 品牌
     */
    void add(Brand brand);

    /**
     * 根据ID修改品牌
     * @param brand 品牌
     */
    void update(Brand brand);

    /**
     * 根据ID删除品牌
     * @param id 品牌ID
     */
    void delete(Integer id);

    /**
     * 多条件查找品牌
     * @param brand 品牌
     * @return 结果
     */
    List<Brand> findByList(Brand brand);

    /**
     * 分页
     * @param page 当前页
     * @param size 每页显示多少
     * @return 结果
     */
    PageInfo<Brand> findPage(Integer page,Integer size);

    /**
     * 条件分页
     * @param brand 品牌
     * @param page 当前页
     * @param size 每页显示多少
     * @return 结果
     */
    PageInfo<Brand> findPage(Brand brand,Integer page,Integer size);
}
