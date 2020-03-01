package com.shine.goods.controller;

import com.github.pagehelper.PageInfo;
import com.shine.common.ServerResponse;
import com.shine.goods.pojo.Category;
import com.shine.goods.service.CategoryService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description:Category的Dao
 * ----------------------------
 * @Author:shine
 * @Date 2020/02/29 0:12
 * ----------------------------
 *****/
@Api(tags = "分类入口")
@RestController
@RequestMapping("/category")
@CrossOrigin
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /***
     * Category分页条件搜索实现
     * @param category
     * @param page
     * @param size
     * @return
     */
    @ApiOperation(value = "Category条件分页查询")
    //@ApiImplicitParams({
    //        @ApiImplicitParam(paramType = "path", name = "page", value = "当前页", required = true, dataType = "Integer"),
    //        @ApiImplicitParam(paramType = "path", name = "size", value = "每页显示条数", required = true, dataType = "Integer")
    //})
    @PostMapping(value = "/search/{page}/{size}" )
    public ServerResponse findPage(@RequestBody(required = false) @ApiParam(name = "Category对象",value = "传入JSON数据",required = false) Category category, @PathVariable  int page, @PathVariable  int size){
        //调用CategoryService实现分页条件查询Category
        PageInfo<Category> pageInfo = categoryService.findPage(category, page, size);
        return ServerResponse.success("查询成功",pageInfo);
    }

    /***
     * Category分页搜索实现
     * @param page:当前页
     * @param size:每页显示多少条
     * @return
     */
    @ApiOperation(value = "Category分页查询")
    //@ApiImplicitParams({
    //        @ApiImplicitParam(paramType = "path", name = "page", value = "当前页", required = true, dataType = "Integer"),
    //        @ApiImplicitParam(paramType = "path", name = "size", value = "每页显示条数", required = true, dataType = "Integer")
    //})
    @GetMapping(value = "/search/{page}/{size}" )
    public ServerResponse findPage(@PathVariable  int page, @PathVariable  int size){
        //调用CategoryService实现分页查询Category
        PageInfo<Category> pageInfo = categoryService.findPage(page, size);
        return ServerResponse.success("查询成功",pageInfo);
    }

    /***
     * 多条件搜索品牌数据
     * @param category
     * @return
     */
    @ApiOperation(value = "Category条件查询")
    @PostMapping(value = "/search" )
    public ServerResponse findList(@RequestBody(required = false) @ApiParam(name = "Category对象",value = "传入JSON数据",required = false) Category category){
        //调用CategoryService实现条件查询Category
        List<Category> list = categoryService.findList(category);
        return ServerResponse.success("查询成功",list);
    }

    /***
     * 根据ID删除品牌数据
     * @param id
     * @return
     */
    @ApiOperation(value = "Category根据ID删除")
    @ApiImplicitParam(paramType = "path", name = "id", value = "主键ID", required = true, dataType = "Integer")
    @DeleteMapping(value = "/{id}" )
    public ServerResponse delete(@PathVariable Integer id){
        //调用CategoryService实现根据主键删除
        categoryService.delete(id);
        return ServerResponse.success("删除成功");
    }

    /***
     * 修改Category数据
     * @param category
     * @param id
     * @return
     */
    @ApiOperation(value = "Category根据ID修改")
    @ApiImplicitParam(paramType = "path", name = "id", value = "主键ID", required = true, dataType = "Integer")
    @PutMapping(value="/{id}")
    public ServerResponse update(@RequestBody @ApiParam(name = "Category对象",value = "传入JSON数据",required = false) Category category, @PathVariable Integer id){
        //设置主键值
        category.setId(id);
        //调用CategoryService实现修改Category
        categoryService.update(category);
        return ServerResponse.success("修改成功");
    }

    /***
     * 新增Category数据
     * @param category
     * @return
     */
    @ApiOperation(value = "Category添加")
    @PostMapping
    public ServerResponse add(@RequestBody  @ApiParam(name = "Category对象",value = "传入JSON数据",required = true) Category category){
        //调用CategoryService实现添加Category
        categoryService.add(category);
        return ServerResponse.success("添加成功");
    }

    /***
     * 根据ID查询Category数据
     * @param id
     * @return
     */
    @ApiOperation(value = "Category根据ID查询")
    @ApiImplicitParam(paramType = "path", name = "id", value = "主键ID", required = true, dataType = "Integer")
    @GetMapping("/{id}")
    public ServerResponse findById(@PathVariable Integer id){
        //调用CategoryService实现根据主键查询Category
        Category category = categoryService.findById(id);
        return ServerResponse.success("查询成功",category);
    }

    /***
     * 查询Category全部数据
     * @return
     */
    @ApiOperation(value = "查询所有Category")
    @GetMapping
    public ServerResponse findAll(){
        //调用CategoryService实现查询所有Category
        List<Category> list = categoryService.findAll();
        return ServerResponse.success("查询成功",list) ;
    }
}
