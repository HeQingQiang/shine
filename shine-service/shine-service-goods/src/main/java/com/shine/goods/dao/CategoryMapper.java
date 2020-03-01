package com.shine.goods.dao;

import com.shine.goods.pojo.Category;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @Description:Category的Dao
 * ----------------------------
 * @Author:shine
 * @Date 2020/02/29 0:12
 * ----------------------------
 *****/
public interface CategoryMapper extends Mapper<Category> {

    /**根据父节点ID查询分类*/
    @Select("SELECT tb.* FROM tb_brand tb,tb_category_brand tcb WHERE tb.id = tcb.brand_id AND tcb.category_id = #{pid}")
    List<Category> findByParentId(Integer pid);
}
