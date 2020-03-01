package com.shine.goods.controller;

import com.github.pagehelper.PageInfo;
import com.shine.common.ServerResponse;
import com.shine.goods.pojo.Para;
import com.shine.goods.service.ParaService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description:Para的Dao
 * ----------------------------
 * @Author:shine
 * @Date 2020/02/29 0:12
 * ----------------------------
 *****/
@Api(tags = "参数入口")
@RestController
@RequestMapping("/para")
@CrossOrigin
public class ParaController {

    @Autowired
    private ParaService paraService;

    /***
     * Para分页条件搜索实现
     * @param para
     * @param page
     * @param size
     * @return
     */
    @ApiOperation(value = "Para条件分页查询")
    //@ApiImplicitParams({
    //        @ApiImplicitParam(paramType = "path", name = "page", value = "当前页", required = true, dataType = "Integer"),
    //        @ApiImplicitParam(paramType = "path", name = "size", value = "每页显示条数", required = true, dataType = "Integer")
    //})
    @PostMapping(value = "/search/{page}/{size}" )
    public ServerResponse findPage(@RequestBody(required = false) @ApiParam(name = "Para对象",value = "传入JSON数据",required = false) Para para, @PathVariable  int page, @PathVariable  int size){
        //调用ParaService实现分页条件查询Para
        PageInfo<Para> pageInfo = paraService.findPage(para, page, size);
        return ServerResponse.success("查询成功",pageInfo);
    }

    /***
     * Para分页搜索实现
     * @param page:当前页
     * @param size:每页显示多少条
     * @return
     */
    @ApiOperation(value = "Para分页查询")
    //@ApiImplicitParams({
    //        @ApiImplicitParam(paramType = "path", name = "page", value = "当前页", required = true, dataType = "Integer"),
    //        @ApiImplicitParam(paramType = "path", name = "size", value = "每页显示条数", required = true, dataType = "Integer")
    //})
    @GetMapping(value = "/search/{page}/{size}" )
    public ServerResponse findPage(@PathVariable  int page, @PathVariable  int size){
        //调用ParaService实现分页查询Para
        PageInfo<Para> pageInfo = paraService.findPage(page, size);
        return ServerResponse.success("查询成功",pageInfo);
    }

    /***
     * 多条件搜索品牌数据
     * @param para
     * @return
     */
    @ApiOperation(value = "Para条件查询")
    @PostMapping(value = "/search" )
    public ServerResponse findList(@RequestBody(required = false) @ApiParam(name = "Para对象",value = "传入JSON数据",required = false) Para para){
        //调用ParaService实现条件查询Para
        List<Para> list = paraService.findList(para);
        return ServerResponse.success("查询成功",list);
    }

    /***
     * 根据ID删除品牌数据
     * @param id
     * @return
     */
    @ApiOperation(value = "Para根据ID删除")
    @ApiImplicitParam(paramType = "path", name = "id", value = "主键ID", required = true, dataType = "Integer")
    @DeleteMapping(value = "/{id}" )
    public ServerResponse delete(@PathVariable Integer id){
        //调用ParaService实现根据主键删除
        paraService.delete(id);
        return ServerResponse.success("删除成功");
    }

    /***
     * 修改Para数据
     * @param para
     * @param id
     * @return
     */
    @ApiOperation(value = "Para根据ID修改")
    @ApiImplicitParam(paramType = "path", name = "id", value = "主键ID", required = true, dataType = "Integer")
    @PutMapping(value="/{id}")
    public ServerResponse update(@RequestBody @ApiParam(name = "Para对象",value = "传入JSON数据",required = false) Para para, @PathVariable Integer id){
        //设置主键值
        para.setId(id);
        //调用ParaService实现修改Para
        paraService.update(para);
        return ServerResponse.success("修改成功");
    }

    /***
     * 新增Para数据
     * @param para
     * @return
     */
    @ApiOperation(value = "Para添加")
    @PostMapping
    public ServerResponse add(@RequestBody  @ApiParam(name = "Para对象",value = "传入JSON数据",required = true) Para para){
        //调用ParaService实现添加Para
        paraService.add(para);
        return ServerResponse.success("添加成功");
    }

    /***
     * 根据ID查询Para数据
     * @param id
     * @return
     */
    @ApiOperation(value = "Para根据ID查询")
    @ApiImplicitParam(paramType = "path", name = "id", value = "主键ID", required = true, dataType = "Integer")
    @GetMapping("/{id}")
    public ServerResponse findById(@PathVariable Integer id){
        //调用ParaService实现根据主键查询Para
        Para para = paraService.findById(id);
        return ServerResponse.success("查询成功",para);
    }

    /***
     * 查询Para全部数据
     * @return
     */
    @ApiOperation(value = "查询所有Para")
    @GetMapping
    public ServerResponse findAll(){
        //调用ParaService实现查询所有Para
        List<Para> list = paraService.findAll();
        return ServerResponse.success("查询成功",list) ;
    }
}
