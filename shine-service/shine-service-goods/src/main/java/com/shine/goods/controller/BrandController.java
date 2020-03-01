package com.shine.goods.controller;

import com.github.pagehelper.PageInfo;
import com.shine.common.ServerResponse;
import com.shine.goods.pojo.Brand;
import com.shine.goods.service.BrandService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description:Brand的Dao
 * ----------------------------
 * @Author:shine
 * @Date 2020/02/29 0:12
 * ----------------------------
 *****/
@Api(tags = "品牌入口")
@RestController
@RequestMapping("/brand")
@CrossOrigin
public class BrandController {

    @Autowired
    private BrandService brandService;

    /***
     * Brand分页条件搜索实现
     * @param brand
     * @param page
     * @param size
     * @return
     */
    @ApiOperation(value = "Brand条件分页查询")
    //@ApiImplicitParams({
    //        @ApiImplicitParam(paramType = "path", name = "page", value = "当前页", required = true, dataType = "Integer"),
    //        @ApiImplicitParam(paramType = "path", name = "size", value = "每页显示条数", required = true, dataType = "Integer")
    //})
    @PostMapping(value = "/search/{page}/{size}" )
    public ServerResponse findPage(@RequestBody(required = false) @ApiParam(name = "Brand对象",value = "传入JSON数据",required = false) Brand brand, @PathVariable  int page, @PathVariable  int size){
        //调用BrandService实现分页条件查询Brand
        PageInfo<Brand> pageInfo = brandService.findPage(brand, page, size);
        return ServerResponse.success("查询成功",pageInfo);
    }

    /***
     * Brand分页搜索实现
     * @param page:当前页
     * @param size:每页显示多少条
     * @return
     */
    @ApiOperation(value = "Brand分页查询")
    //@ApiImplicitParams({
    //        @ApiImplicitParam(paramType = "path", name = "page", value = "当前页", required = true, dataType = "Integer"),
    //        @ApiImplicitParam(paramType = "path", name = "size", value = "每页显示条数", required = true, dataType = "Integer")
    //})
    @GetMapping(value = "/search/{page}/{size}" )
    public ServerResponse findPage(@PathVariable  int page, @PathVariable  int size){
        //调用BrandService实现分页查询Brand
        PageInfo<Brand> pageInfo = brandService.findPage(page, size);
        return ServerResponse.success("查询成功",pageInfo);
    }

    /***
     * 多条件搜索品牌数据
     * @param brand
     * @return
     */
    @ApiOperation(value = "Brand条件查询")
    @PostMapping(value = "/search" )
    public ServerResponse findList(@RequestBody(required = false) @ApiParam(name = "Brand对象",value = "传入JSON数据",required = false) Brand brand){
        //调用BrandService实现条件查询Brand
        List<Brand> list = brandService.findList(brand);
        return ServerResponse.success("查询成功",list);
    }

    /***
     * 根据ID删除品牌数据
     * @param id
     * @return
     */
    @ApiOperation(value = "Brand根据ID删除")
    @ApiImplicitParam(paramType = "path", name = "id", value = "主键ID", required = true, dataType = "Integer")
    @DeleteMapping(value = "/{id}" )
    public ServerResponse delete(@PathVariable Integer id){
        //调用BrandService实现根据主键删除
        brandService.delete(id);
        return ServerResponse.success("删除成功");
    }

    /***
     * 修改Brand数据
     * @param brand
     * @param id
     * @return
     */
    @ApiOperation(value = "Brand根据ID修改")
    @ApiImplicitParam(paramType = "path", name = "id", value = "主键ID", required = true, dataType = "Integer")
    @PutMapping(value="/{id}")
    public ServerResponse update(@RequestBody @ApiParam(name = "Brand对象",value = "传入JSON数据",required = false) Brand brand, @PathVariable Integer id){
        //设置主键值
        brand.setId(id);
        //调用BrandService实现修改Brand
        brandService.update(brand);
        return ServerResponse.success("修改成功");
    }

    /***
     * 新增Brand数据
     * @param brand
     * @return
     */
    @ApiOperation(value = "Brand添加")
    @PostMapping
    public ServerResponse add(@RequestBody  @ApiParam(name = "Brand对象",value = "传入JSON数据",required = true) Brand brand){
        //调用BrandService实现添加Brand
        brandService.add(brand);
        return ServerResponse.success("添加成功");
    }

    /***
     * 根据ID查询Brand数据
     * @param id
     * @return
     */
    @ApiOperation(value = "Brand根据ID查询")
    @ApiImplicitParam(paramType = "path", name = "id", value = "主键ID", required = true, dataType = "Integer")
    @GetMapping("/{id}")
    public ServerResponse findById(@PathVariable Integer id){
        //调用BrandService实现根据主键查询Brand
        Brand brand = brandService.findById(id);
        return ServerResponse.success("查询成功",brand);
    }

    /***
     * 查询Brand全部数据
     * @return
     */
    @ApiOperation(value = "查询所有Brand")
    @GetMapping
    public ServerResponse findAll(){
        //调用BrandService实现查询所有Brand
        List<Brand> list = brandService.findAll();
        return ServerResponse.success("查询成功",list) ;
    }
}
