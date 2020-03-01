package com.shine.goods.controller;

import com.github.pagehelper.PageInfo;
import com.shine.common.ServerResponse;
import com.shine.goods.pojo.Spec;
import com.shine.goods.service.SpecService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description:Spec的Dao
 * ----------------------------
 * @Author:shine
 * @Date 2020/02/29 0:12
 * ----------------------------
 *****/
@Api(tags = "规格入口")
@RestController
@RequestMapping("/spec")
@CrossOrigin
public class SpecController {

    @Autowired
    private SpecService specService;

    /***
     * Spec分页条件搜索实现
     * @param spec
     * @param page
     * @param size
     * @return
     */
    @ApiOperation(value = "Spec条件分页查询")
    //@ApiImplicitParams({
    //        @ApiImplicitParam(paramType = "path", name = "page", value = "当前页", required = true, dataType = "Integer"),
    //        @ApiImplicitParam(paramType = "path", name = "size", value = "每页显示条数", required = true, dataType = "Integer")
    //})
    @PostMapping(value = "/search/{page}/{size}" )
    public ServerResponse findPage(@RequestBody(required = false) @ApiParam(name = "Spec对象",value = "传入JSON数据",required = false) Spec spec, @PathVariable  int page, @PathVariable  int size){
        //调用SpecService实现分页条件查询Spec
        PageInfo<Spec> pageInfo = specService.findPage(spec, page, size);
        return ServerResponse.success("查询成功",pageInfo);
    }

    /***
     * Spec分页搜索实现
     * @param page:当前页
     * @param size:每页显示多少条
     * @return
     */
    @ApiOperation(value = "Spec分页查询")
    //@ApiImplicitParams({
    //        @ApiImplicitParam(paramType = "path", name = "page", value = "当前页", required = true, dataType = "Integer"),
    //        @ApiImplicitParam(paramType = "path", name = "size", value = "每页显示条数", required = true, dataType = "Integer")
    //})
    @GetMapping(value = "/search/{page}/{size}" )
    public ServerResponse findPage(@PathVariable  int page, @PathVariable  int size){
        //调用SpecService实现分页查询Spec
        PageInfo<Spec> pageInfo = specService.findPage(page, size);
        return ServerResponse.success("查询成功",pageInfo);
    }

    /***
     * 多条件搜索品牌数据
     * @param spec
     * @return
     */
    @ApiOperation(value = "Spec条件查询")
    @PostMapping(value = "/search" )
    public ServerResponse findList(@RequestBody(required = false) @ApiParam(name = "Spec对象",value = "传入JSON数据",required = false) Spec spec){
        //调用SpecService实现条件查询Spec
        List<Spec> list = specService.findList(spec);
        return ServerResponse.success("查询成功",list);
    }

    /***
     * 根据ID删除品牌数据
     * @param id
     * @return
     */
    @ApiOperation(value = "Spec根据ID删除")
    @ApiImplicitParam(paramType = "path", name = "id", value = "主键ID", required = true, dataType = "Integer")
    @DeleteMapping(value = "/{id}" )
    public ServerResponse delete(@PathVariable Integer id){
        //调用SpecService实现根据主键删除
        specService.delete(id);
        return ServerResponse.success("删除成功");
    }

    /***
     * 修改Spec数据
     * @param spec
     * @param id
     * @return
     */
    @ApiOperation(value = "Spec根据ID修改")
    @ApiImplicitParam(paramType = "path", name = "id", value = "主键ID", required = true, dataType = "Integer")
    @PutMapping(value="/{id}")
    public ServerResponse update(@RequestBody @ApiParam(name = "Spec对象",value = "传入JSON数据",required = false) Spec spec, @PathVariable Integer id){
        //设置主键值
        spec.setId(id);
        //调用SpecService实现修改Spec
        specService.update(spec);
        return ServerResponse.success("修改成功");
    }

    /***
     * 新增Spec数据
     * @param spec
     * @return
     */
    @ApiOperation(value = "Spec添加")
    @PostMapping
    public ServerResponse add(@RequestBody  @ApiParam(name = "Spec对象",value = "传入JSON数据",required = true) Spec spec){
        //调用SpecService实现添加Spec
        specService.add(spec);
        return ServerResponse.success("添加成功");
    }

    /***
     * 根据ID查询Spec数据
     * @param id
     * @return
     */
    @ApiOperation(value = "Spec根据ID查询")
    @ApiImplicitParam(paramType = "path", name = "id", value = "主键ID", required = true, dataType = "Integer")
    @GetMapping("/{id}")
    public ServerResponse findById(@PathVariable Integer id){
        //调用SpecService实现根据主键查询Spec
        Spec spec = specService.findById(id);
        return ServerResponse.success("查询成功",spec);
    }

    /***
     * 查询Spec全部数据
     * @return
     */
    @ApiOperation(value = "查询所有Spec")
    @GetMapping
    public ServerResponse findAll(){
        //调用SpecService实现查询所有Spec
        List<Spec> list = specService.findAll();
        return ServerResponse.success("查询成功",list) ;
    }
}
