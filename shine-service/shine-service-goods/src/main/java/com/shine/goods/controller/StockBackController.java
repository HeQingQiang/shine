package com.shine.goods.controller;

import com.github.pagehelper.PageInfo;
import com.shine.common.ServerResponse;
import com.shine.goods.pojo.StockBack;
import com.shine.goods.service.StockBackService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description:StockBack的Dao
 * ----------------------------
 * @Author:shine
 * @Date 2020/02/29 0:12
 * ----------------------------
 *****/
@Api(tags = "库存回滚")
@RestController
@RequestMapping("/stockBack")
@CrossOrigin
public class StockBackController {

    @Autowired
    private StockBackService stockBackService;

    /***
     * StockBack分页条件搜索实现
     * @param stockBack
     * @param page
     * @param size
     * @return
     */
    @ApiOperation(value = "StockBack条件分页查询")
    //@ApiImplicitParams({
    //        @ApiImplicitParam(paramType = "path", name = "page", value = "当前页", required = true, dataType = "Integer"),
    //        @ApiImplicitParam(paramType = "path", name = "size", value = "每页显示条数", required = true, dataType = "Integer")
    //})
    @PostMapping(value = "/search/{page}/{size}" )
    public ServerResponse findPage(@RequestBody(required = false) @ApiParam(name = "StockBack对象",value = "传入JSON数据",required = false) StockBack stockBack, @PathVariable  int page, @PathVariable  int size){
        //调用StockBackService实现分页条件查询StockBack
        PageInfo<StockBack> pageInfo = stockBackService.findPage(stockBack, page, size);
        return ServerResponse.success("查询成功",pageInfo);
    }

    /***
     * StockBack分页搜索实现
     * @param page:当前页
     * @param size:每页显示多少条
     * @return
     */
    @ApiOperation(value = "StockBack分页查询")
    //@ApiImplicitParams({
    //        @ApiImplicitParam(paramType = "path", name = "page", value = "当前页", required = true, dataType = "Integer"),
    //        @ApiImplicitParam(paramType = "path", name = "size", value = "每页显示条数", required = true, dataType = "Integer")
    //})
    @GetMapping(value = "/search/{page}/{size}" )
    public ServerResponse findPage(@PathVariable  int page, @PathVariable  int size){
        //调用StockBackService实现分页查询StockBack
        PageInfo<StockBack> pageInfo = stockBackService.findPage(page, size);
        return ServerResponse.success("查询成功",pageInfo);
    }

    /***
     * 多条件搜索品牌数据
     * @param stockBack
     * @return
     */
    @ApiOperation(value = "StockBack条件查询")
    @PostMapping(value = "/search" )
    public ServerResponse findList(@RequestBody(required = false) @ApiParam(name = "StockBack对象",value = "传入JSON数据",required = false) StockBack stockBack){
        //调用StockBackService实现条件查询StockBack
        List<StockBack> list = stockBackService.findList(stockBack);
        return ServerResponse.success("查询成功",list);
    }

    /***
     * 根据ID删除品牌数据
     * @param id
     * @return
     */
    @ApiOperation(value = "StockBack根据ID删除")
    @ApiImplicitParam(paramType = "path", name = "id", value = "主键ID", required = true, dataType = "String")
    @DeleteMapping(value = "/{id}" )
    public ServerResponse delete(@PathVariable String id){
        //调用StockBackService实现根据主键删除
        stockBackService.delete(id);
        return ServerResponse.success("删除成功");
    }

    /***
     * 修改StockBack数据
     * @param stockBack
     * @param id
     * @return
     */
    @ApiOperation(value = "StockBack根据ID修改")
    @ApiImplicitParam(paramType = "path", name = "id", value = "主键ID", required = true, dataType = "String")
    @PutMapping(value="/{id}")
    public ServerResponse update(@RequestBody @ApiParam(name = "StockBack对象",value = "传入JSON数据",required = false) StockBack stockBack, @PathVariable String id){
        //设置主键值
        stockBack.setSkuId(id);
        //调用StockBackService实现修改StockBack
        stockBackService.update(stockBack);
        return ServerResponse.success("修改成功");
    }

    /***
     * 新增StockBack数据
     * @param stockBack
     * @return
     */
    @ApiOperation(value = "StockBack添加")
    @PostMapping
    public ServerResponse add(@RequestBody  @ApiParam(name = "StockBack对象",value = "传入JSON数据",required = true) StockBack stockBack){
        //调用StockBackService实现添加StockBack
        stockBackService.add(stockBack);
        return ServerResponse.success("添加成功");
    }

    /***
     * 根据ID查询StockBack数据
     * @param id
     * @return
     */
    @ApiOperation(value = "StockBack根据ID查询")
    @ApiImplicitParam(paramType = "path", name = "id", value = "主键ID", required = true, dataType = "String")
    @GetMapping("/{id}")
    public ServerResponse findById(@PathVariable String id){
        //调用StockBackService实现根据主键查询StockBack
        StockBack stockBack = stockBackService.findById(id);
        return ServerResponse.success("查询成功",stockBack);
    }

    /***
     * 查询StockBack全部数据
     * @return
     */
    @ApiOperation(value = "查询所有StockBack")
    @GetMapping
    public ServerResponse findAll(){
        //调用StockBackService实现查询所有StockBack
        List<StockBack> list = stockBackService.findAll();
        return ServerResponse.success("查询成功",list) ;
    }
}
