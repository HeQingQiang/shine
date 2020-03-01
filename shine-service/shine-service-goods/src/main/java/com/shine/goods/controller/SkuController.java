package com.shine.goods.controller;

import com.github.pagehelper.PageInfo;
import com.shine.common.ServerResponse;
import com.shine.goods.pojo.Sku;
import com.shine.goods.service.SkuService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description:Sku的Dao
 * ----------------------------
 * @Author:shine
 * @Date 2020/02/29 0:12
 * ----------------------------
 *****/
@Api(tags = "商品明细")
@RestController
@RequestMapping("/sku")
@CrossOrigin
public class SkuController {

    @Autowired
    private SkuService skuService;

    /***
     * Sku分页条件搜索实现
     * @param sku
     * @param page
     * @param size
     * @return
     */
    @ApiOperation(value = "Sku条件分页查询")
    //@ApiImplicitParams({
    //        @ApiImplicitParam(paramType = "path", name = "page", value = "当前页", required = true, dataType = "Integer"),
    //        @ApiImplicitParam(paramType = "path", name = "size", value = "每页显示条数", required = true, dataType = "Integer")
    //})
    @PostMapping(value = "/search/{page}/{size}" )
    public ServerResponse findPage(@RequestBody(required = false) @ApiParam(name = "Sku对象",value = "传入JSON数据",required = false) Sku sku, @PathVariable  int page, @PathVariable  int size){
        //调用SkuService实现分页条件查询Sku
        PageInfo<Sku> pageInfo = skuService.findPage(sku, page, size);
        return ServerResponse.success("查询成功",pageInfo);
    }

    /***
     * Sku分页搜索实现
     * @param page:当前页
     * @param size:每页显示多少条
     * @return
     */
    @ApiOperation(value = "Sku分页查询")
    //@ApiImplicitParams({
    //        @ApiImplicitParam(paramType = "path", name = "page", value = "当前页", required = true, dataType = "Integer"),
    //        @ApiImplicitParam(paramType = "path", name = "size", value = "每页显示条数", required = true, dataType = "Integer")
    //})
    @GetMapping(value = "/search/{page}/{size}" )
    public ServerResponse findPage(@PathVariable  int page, @PathVariable  int size){
        //调用SkuService实现分页查询Sku
        PageInfo<Sku> pageInfo = skuService.findPage(page, size);
        return ServerResponse.success("查询成功",pageInfo);
    }

    /***
     * 多条件搜索品牌数据
     * @param sku
     * @return
     */
    @ApiOperation(value = "Sku条件查询")
    @PostMapping(value = "/search" )
    public ServerResponse findList(@RequestBody(required = false) @ApiParam(name = "Sku对象",value = "传入JSON数据",required = false) Sku sku){
        //调用SkuService实现条件查询Sku
        List<Sku> list = skuService.findList(sku);
        return ServerResponse.success("查询成功",list);
    }

    /***
     * 根据ID删除品牌数据
     * @param id
     * @return
     */
    @ApiOperation(value = "Sku根据ID删除")
    @ApiImplicitParam(paramType = "path", name = "id", value = "主键ID", required = true, dataType = "Long")
    @DeleteMapping(value = "/{id}" )
    public ServerResponse delete(@PathVariable Long id){
        //调用SkuService实现根据主键删除
        skuService.delete(id);
        return ServerResponse.success("删除成功");
    }

    /***
     * 修改Sku数据
     * @param sku
     * @param id
     * @return
     */
    @ApiOperation(value = "Sku根据ID修改")
    @ApiImplicitParam(paramType = "path", name = "id", value = "主键ID", required = true, dataType = "Long")
    @PutMapping(value="/{id}")
    public ServerResponse update(@RequestBody @ApiParam(name = "Sku对象",value = "传入JSON数据",required = false) Sku sku, @PathVariable Long id){
        //设置主键值
        sku.setId(id);
        //调用SkuService实现修改Sku
        skuService.update(sku);
        return ServerResponse.success("修改成功");
    }

    /***
     * 新增Sku数据
     * @param sku
     * @return
     */
    @ApiOperation(value = "Sku添加")
    @PostMapping
    public ServerResponse add(@RequestBody  @ApiParam(name = "Sku对象",value = "传入JSON数据",required = true) Sku sku){
        //调用SkuService实现添加Sku
        skuService.add(sku);
        return ServerResponse.success("添加成功");
    }

    /***
     * 根据ID查询Sku数据
     * @param id
     * @return
     */
    @ApiOperation(value = "Sku根据ID查询")
    @ApiImplicitParam(paramType = "path", name = "id", value = "主键ID", required = true, dataType = "Long")
    @GetMapping("/{id}")
    public ServerResponse findById(@PathVariable Long id){
        //调用SkuService实现根据主键查询Sku
        Sku sku = skuService.findById(id);
        return ServerResponse.success("查询成功",sku);
    }

    /***
     * 查询Sku全部数据
     * @return
     */
    @ApiOperation(value = "查询所有Sku")
    @GetMapping
    public ServerResponse findAll(){
        //调用SkuService实现查询所有Sku
        List<Sku> list = skuService.findAll();
        return ServerResponse.success("查询成功",list) ;
    }
}
