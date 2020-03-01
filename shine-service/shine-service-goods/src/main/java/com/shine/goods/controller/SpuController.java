package com.shine.goods.controller;

import com.github.pagehelper.PageInfo;
import com.shine.common.ServerResponse;
import com.shine.goods.pojo.Spu;
import com.shine.goods.service.SpuService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description:Spu的Dao
 * ----------------------------
 * @Author:shine
 * @Date 2020/02/29 0:12
 * ----------------------------
 *****/
@Api(tags = "商品入口")
@RestController
@RequestMapping("/spu")
@CrossOrigin
public class SpuController {

    @Autowired
    private SpuService spuService;

    /***
     * Spu分页条件搜索实现
     * @param spu
     * @param page
     * @param size
     * @return
     */
    @ApiOperation(value = "Spu条件分页查询")
    //@ApiImplicitParams({
    //        @ApiImplicitParam(paramType = "path", name = "page", value = "当前页", required = true, dataType = "Integer"),
    //        @ApiImplicitParam(paramType = "path", name = "size", value = "每页显示条数", required = true, dataType = "Integer")
    //})
    @PostMapping(value = "/search/{page}/{size}" )
    public ServerResponse findPage(@RequestBody(required = false) @ApiParam(name = "Spu对象",value = "传入JSON数据",required = false) Spu spu, @PathVariable  int page, @PathVariable  int size){
        //调用SpuService实现分页条件查询Spu
        PageInfo<Spu> pageInfo = spuService.findPage(spu, page, size);
        return ServerResponse.success("查询成功",pageInfo);
    }

    /***
     * Spu分页搜索实现
     * @param page:当前页
     * @param size:每页显示多少条
     * @return
     */
    @ApiOperation(value = "Spu分页查询")
    //@ApiImplicitParams({
    //        @ApiImplicitParam(paramType = "path", name = "page", value = "当前页", required = true, dataType = "Integer"),
    //        @ApiImplicitParam(paramType = "path", name = "size", value = "每页显示条数", required = true, dataType = "Integer")
    //})
    @GetMapping(value = "/search/{page}/{size}" )
    public ServerResponse findPage(@PathVariable  int page, @PathVariable  int size){
        //调用SpuService实现分页查询Spu
        PageInfo<Spu> pageInfo = spuService.findPage(page, size);
        return ServerResponse.success("查询成功",pageInfo);
    }

    /***
     * 多条件搜索品牌数据
     * @param spu
     * @return
     */
    @ApiOperation(value = "Spu条件查询")
    @PostMapping(value = "/search" )
    public ServerResponse findList(@RequestBody(required = false) @ApiParam(name = "Spu对象",value = "传入JSON数据",required = false) Spu spu){
        //调用SpuService实现条件查询Spu
        List<Spu> list = spuService.findList(spu);
        return ServerResponse.success("查询成功",list);
    }

    /***
     * 根据ID删除品牌数据
     * @param id
     * @return
     */
    @ApiOperation(value = "Spu根据ID删除")
    @ApiImplicitParam(paramType = "path", name = "id", value = "主键ID", required = true, dataType = "Long")
    @DeleteMapping(value = "/{id}" )
    public ServerResponse delete(@PathVariable Long id){
        //调用SpuService实现根据主键删除
        spuService.delete(id);
        return ServerResponse.success("删除成功");
    }

    /***
     * 修改Spu数据
     * @param spu
     * @param id
     * @return
     */
    @ApiOperation(value = "Spu根据ID修改")
    @ApiImplicitParam(paramType = "path", name = "id", value = "主键ID", required = true, dataType = "Long")
    @PutMapping(value="/{id}")
    public ServerResponse update(@RequestBody @ApiParam(name = "Spu对象",value = "传入JSON数据",required = false) Spu spu, @PathVariable Long id){
        //设置主键值
        spu.setId(id);
        //调用SpuService实现修改Spu
        spuService.update(spu);
        return ServerResponse.success("修改成功");
    }

    /***
     * 新增Spu数据
     * @param spu
     * @return
     */
    @ApiOperation(value = "Spu添加")
    @PostMapping
    public ServerResponse add(@RequestBody  @ApiParam(name = "Spu对象",value = "传入JSON数据",required = true) Spu spu){
        //调用SpuService实现添加Spu
        spuService.add(spu);
        return ServerResponse.success("添加成功");
    }

    /***
     * 根据ID查询Spu数据
     * @param id
     * @return
     */
    @ApiOperation(value = "Spu根据ID查询")
    @ApiImplicitParam(paramType = "path", name = "id", value = "主键ID", required = true, dataType = "Long")
    @GetMapping("/{id}")
    public ServerResponse findById(@PathVariable Long id){
        //调用SpuService实现根据主键查询Spu
        Spu spu = spuService.findById(id);
        return ServerResponse.success("查询成功",spu);
    }

    /***
     * 查询Spu全部数据
     * @return
     */
    @ApiOperation(value = "查询所有Spu")
    @GetMapping
    public ServerResponse findAll(){
        //调用SpuService实现查询所有Spu
        List<Spu> list = spuService.findAll();
        return ServerResponse.success("查询成功",list) ;
    }
}
