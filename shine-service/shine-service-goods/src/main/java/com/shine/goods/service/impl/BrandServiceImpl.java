package com.shine.goods.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shine.goods.dao.BrandMapper;
import com.shine.goods.pojo.Brand;
import com.shine.goods.service.BrandService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/***********
 *@Author: Shine
 *@Description:
 *@Data: Created in 2019/12/25 20:26
 *@Modified By:
 *****/
@Service
public class BrandServiceImpl implements BrandService {

    @Autowired
    private BrandMapper brandMapper;

    @Override
    public List<Brand> findAll() {
        return brandMapper.selectAll();
    }

    @Override
    public Brand findById(Integer id) {
        return brandMapper.selectByPrimaryKey(id);
    }

    @Override
    public void add(Brand brand) {
        brandMapper.insertSelective(brand);
    }

    @Override
    public void update(Brand brand) {
        brandMapper.updateByPrimaryKeySelective(brand);
    }

    @Override
    public void delete(Integer id) {
        brandMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<Brand> findByList(Brand brand) {
        Example example = createExample(brand);
        return brandMapper.selectByExample(example);
    }

    @Override
    public PageInfo<Brand> findPage(Integer page, Integer size) {
        //page当前页，size每页显示多少条
        PageHelper.startPage(page, size);
        //集合查询
        List<Brand> brands = brandMapper.selectAll();
        return new PageInfo<>(brands);
    }

    @Override
    public PageInfo<Brand> findPage(Brand brand, Integer page, Integer size) {
        //page当前页，size每页显示多少条
        PageHelper.startPage(page, size);
        //条件查询
        Example example = createExample(brand);
        List<Brand> brands = brandMapper.selectByExample(example);
        return new PageInfo<>(brands);
    }

    /**
     * 条件构建
     * @param brand 品牌对象
     * @return 条件构造器
     */
    public Example createExample(Brand brand){
        //自定义条件搜索对象Example
        Example example = new Example(Brand.class);
        //条件构造器
        Example.Criteria criteria = example.createCriteria();

        if (brand != null) {
            if (StringUtils.isNotEmpty(brand.getName())) {
                //1查询属性名；2占位符参数+搜索条件
                criteria.andLike("name", "%"+brand.getName()+"%");
            }
            if (StringUtils.isNotEmpty(brand.getLetter())) {
                criteria.andEqualTo("letter", brand.getLetter());
            }
        }

        return example;
    }

}
