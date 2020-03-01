package com.shine.goods.controller;

import com.github.pagehelper.PageInfo;
import com.shine.common.ServerResponse;
import com.shine.goods.pojo.Pref;
import com.shine.goods.service.PrefService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description:Pref的Dao
 * ----------------------------
 * @Author:shine
 * @Date 2020/02/29 0:12
 * ----------------------------
 *****/
@Api(tags = "活动入口")
@RestController
@RequestMapping("/pref")
@CrossOrigin
public class PrefController {

    @Autowired
    private PrefService prefService;

    /***
     * Pref分页条件搜索实现
     * @param pref
     * @param page
     * @param size
     * @return
     */
    @ApiOperation(value = "Pref条件分页查询")
    //@ApiImplicitParams({
    //        @ApiImplicitParam(paramType = "path", name = "page", value = "当前页", required = true, dataType = "Integer"),
    //        @ApiImplicitParam(paramType = "path", name = "size", value = "每页显示条数", required = true, dataType = "Integer")
    //})
    @PostMapping(value = "/search/{page}/{size}" )
    public ServerResponse findPage(@RequestBody(required = false) @ApiParam(name = "Pref对象",value = "传入JSON数据",required = false) Pref pref, @PathVariable  int page, @PathVariable  int size){
        //调用PrefService实现分页条件查询Pref
        PageInfo<Pref> pageInfo = prefService.findPage(pref, page, size);
        return ServerResponse.success("查询成功",pageInfo);
    }

    /***
     * Pref分页搜索实现
     * @param page:当前页
     * @param size:每页显示多少条
     * @return
     */
    @ApiOperation(value = "Pref分页查询")
    //@ApiImplicitParams({
    //        @ApiImplicitParam(paramType = "path", name = "page", value = "当前页", required = true, dataType = "Integer"),
    //        @ApiImplicitParam(paramType = "path", name = "size", value = "每页显示条数", required = true, dataType = "Integer")
    //})
    @GetMapping(value = "/search/{page}/{size}" )
    public ServerResponse findPage(@PathVariable  int page, @PathVariable  int size){
        //调用PrefService实现分页查询Pref
        PageInfo<Pref> pageInfo = prefService.findPage(page, size);
        return ServerResponse.success("查询成功",pageInfo);
    }

    /***
     * 多条件搜索品牌数据
     * @param pref
     * @return
     */
    @ApiOperation(value = "Pref条件查询")
    @PostMapping(value = "/search" )
    public ServerResponse findList(@RequestBody(required = false) @ApiParam(name = "Pref对象",value = "传入JSON数据",required = false) Pref pref){
        //调用PrefService实现条件查询Pref
        List<Pref> list = prefService.findList(pref);
        return ServerResponse.success("查询成功",list);
    }

    /***
     * 根据ID删除品牌数据
     * @param id
     * @return
     */
    @ApiOperation(value = "Pref根据ID删除")
    @ApiImplicitParam(paramType = "path", name = "id", value = "主键ID", required = true, dataType = "Integer")
    @DeleteMapping(value = "/{id}" )
    public ServerResponse delete(@PathVariable Integer id){
        //调用PrefService实现根据主键删除
        prefService.delete(id);
        return ServerResponse.success("删除成功");
    }

    /***
     * 修改Pref数据
     * @param pref
     * @param id
     * @return
     */
    @ApiOperation(value = "Pref根据ID修改")
    @ApiImplicitParam(paramType = "path", name = "id", value = "主键ID", required = true, dataType = "Integer")
    @PutMapping(value="/{id}")
    public ServerResponse update(@RequestBody @ApiParam(name = "Pref对象",value = "传入JSON数据",required = false) Pref pref, @PathVariable Integer id){
        //设置主键值
        pref.setId(id);
        //调用PrefService实现修改Pref
        prefService.update(pref);
        return ServerResponse.success("修改成功");
    }

    /***
     * 新增Pref数据
     * @param pref
     * @return
     */
    @ApiOperation(value = "Pref添加")
    @PostMapping
    public ServerResponse add(@RequestBody  @ApiParam(name = "Pref对象",value = "传入JSON数据",required = true) Pref pref){
        //调用PrefService实现添加Pref
        prefService.add(pref);
        return ServerResponse.success("添加成功");
    }

    /***
     * 根据ID查询Pref数据
     * @param id
     * @return
     */
    @ApiOperation(value = "Pref根据ID查询")
    @ApiImplicitParam(paramType = "path", name = "id", value = "主键ID", required = true, dataType = "Integer")
    @GetMapping("/{id}")
    public ServerResponse findById(@PathVariable Integer id){
        //调用PrefService实现根据主键查询Pref
        Pref pref = prefService.findById(id);
        return ServerResponse.success("查询成功",pref);
    }

    /***
     * 查询Pref全部数据
     * @return
     */
    @ApiOperation(value = "查询所有Pref")
    @GetMapping
    public ServerResponse findAll(){
        //调用PrefService实现查询所有Pref
        List<Pref> list = prefService.findAll();
        return ServerResponse.success("查询成功",list) ;
    }
}
