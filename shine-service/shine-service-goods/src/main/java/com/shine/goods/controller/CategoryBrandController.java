package com.shine.goods.controller;

import com.github.pagehelper.PageInfo;
import com.shine.common.ServerResponse;
import com.shine.goods.pojo.CategoryBrand;
import com.shine.goods.service.CategoryBrandService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description:CategoryBrand的Dao
 * ----------------------------
 * @Author:shine
 * @Date 2020/02/29 0:12
 * ----------------------------
 *****/
@Api(tags = "分类品牌")
@RestController
@RequestMapping("/categoryBrand")
@CrossOrigin
public class CategoryBrandController {

    @Autowired
    private CategoryBrandService categoryBrandService;

    /***
     * CategoryBrand分页条件搜索实现
     * @param categoryBrand
     * @param page
     * @param size
     * @return
     */
    @ApiOperation(value = "CategoryBrand条件分页查询")
    //@ApiImplicitParams({
    //        @ApiImplicitParam(paramType = "path", name = "page", value = "当前页", required = true, dataType = "Integer"),
    //        @ApiImplicitParam(paramType = "path", name = "size", value = "每页显示条数", required = true, dataType = "Integer")
    //})
    @PostMapping(value = "/search/{page}/{size}" )
    public ServerResponse findPage(@RequestBody(required = false) @ApiParam(name = "CategoryBrand对象",value = "传入JSON数据",required = false) CategoryBrand categoryBrand, @PathVariable  int page, @PathVariable  int size){
        //调用CategoryBrandService实现分页条件查询CategoryBrand
        PageInfo<CategoryBrand> pageInfo = categoryBrandService.findPage(categoryBrand, page, size);
        return ServerResponse.success("查询成功",pageInfo);
    }

    /***
     * CategoryBrand分页搜索实现
     * @param page:当前页
     * @param size:每页显示多少条
     * @return
     */
    @ApiOperation(value = "CategoryBrand分页查询")
    //@ApiImplicitParams({
    //        @ApiImplicitParam(paramType = "path", name = "page", value = "当前页", required = true, dataType = "Integer"),
    //        @ApiImplicitParam(paramType = "path", name = "size", value = "每页显示条数", required = true, dataType = "Integer")
    //})
    @GetMapping(value = "/search/{page}/{size}" )
    public ServerResponse findPage(@PathVariable  int page, @PathVariable  int size){
        //调用CategoryBrandService实现分页查询CategoryBrand
        PageInfo<CategoryBrand> pageInfo = categoryBrandService.findPage(page, size);
        return ServerResponse.success("查询成功",pageInfo);
    }

    /***
     * 多条件搜索品牌数据
     * @param categoryBrand
     * @return
     */
    @ApiOperation(value = "CategoryBrand条件查询")
    @PostMapping(value = "/search" )
    public ServerResponse findList(@RequestBody(required = false) @ApiParam(name = "CategoryBrand对象",value = "传入JSON数据",required = false) CategoryBrand categoryBrand){
        //调用CategoryBrandService实现条件查询CategoryBrand
        List<CategoryBrand> list = categoryBrandService.findList(categoryBrand);
        return ServerResponse.success("查询成功",list);
    }

    /***
     * 根据ID删除品牌数据
     * @param id
     * @return
     */
    @ApiOperation(value = "CategoryBrand根据ID删除")
    @ApiImplicitParam(paramType = "path", name = "id", value = "主键ID", required = true, dataType = "Integer")
    @DeleteMapping(value = "/{id}" )
    public ServerResponse delete(@PathVariable Integer id){
        //调用CategoryBrandService实现根据主键删除
        categoryBrandService.delete(id);
        return ServerResponse.success("删除成功");
    }

    /***
     * 修改CategoryBrand数据
     * @param categoryBrand
     * @param id
     * @return
     */
    @ApiOperation(value = "CategoryBrand根据ID修改")
    @ApiImplicitParam(paramType = "path", name = "id", value = "主键ID", required = true, dataType = "Integer")
    @PutMapping(value="/{id}")
    public ServerResponse update(@RequestBody @ApiParam(name = "CategoryBrand对象",value = "传入JSON数据",required = false) CategoryBrand categoryBrand, @PathVariable Integer id){
        //设置主键值
        categoryBrand.setCategoryId(id);
        //调用CategoryBrandService实现修改CategoryBrand
        categoryBrandService.update(categoryBrand);
        return ServerResponse.success("修改成功");
    }

    /***
     * 新增CategoryBrand数据
     * @param categoryBrand
     * @return
     */
    @ApiOperation(value = "CategoryBrand添加")
    @PostMapping
    public ServerResponse add(@RequestBody  @ApiParam(name = "CategoryBrand对象",value = "传入JSON数据",required = true) CategoryBrand categoryBrand){
        //调用CategoryBrandService实现添加CategoryBrand
        categoryBrandService.add(categoryBrand);
        return ServerResponse.success("添加成功");
    }

    /***
     * 根据ID查询CategoryBrand数据
     * @param id
     * @return
     */
    @ApiOperation(value = "CategoryBrand根据ID查询")
    @ApiImplicitParam(paramType = "path", name = "id", value = "主键ID", required = true, dataType = "Integer")
    @GetMapping("/{id}")
    public ServerResponse findById(@PathVariable Integer id){
        //调用CategoryBrandService实现根据主键查询CategoryBrand
        CategoryBrand categoryBrand = categoryBrandService.findById(id);
        return ServerResponse.success("查询成功",categoryBrand);
    }

    /***
     * 查询CategoryBrand全部数据
     * @return
     */
    @ApiOperation(value = "查询所有CategoryBrand")
    @GetMapping
    public ServerResponse findAll(){
        //调用CategoryBrandService实现查询所有CategoryBrand
        List<CategoryBrand> list = categoryBrandService.findAll();
        return ServerResponse.success("查询成功",list) ;
    }
}
