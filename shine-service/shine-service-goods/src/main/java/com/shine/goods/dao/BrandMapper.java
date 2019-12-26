package com.shine.goods.dao;

import com.shine.goods.pojo.Brand;
import tk.mybatis.mapper.common.Mapper;

/***********
 *@Author: Shine
 *@Description: 品牌DAO(使用通用Mapper)
 *@Data: Created in 2019/12/25 20:26
 *@Modified By:
 *****/
@org.apache.ibatis.annotations.Mapper
public interface BrandMapper extends Mapper<Brand> {
}
