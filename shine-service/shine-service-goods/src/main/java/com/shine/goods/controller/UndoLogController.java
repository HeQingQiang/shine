package com.shine.goods.controller;

import com.github.pagehelper.PageInfo;
import com.shine.common.ServerResponse;
import com.shine.goods.pojo.UndoLog;
import com.shine.goods.service.UndoLogService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description:UndoLog的Dao
 * ----------------------------
 * @Author:shine
 * @Date 2020/02/29 0:12
 * ----------------------------
 *****/
@Api(tags = "日志入口")
@RestController
@RequestMapping("/undoLog")
@CrossOrigin
public class UndoLogController {

    @Autowired
    private UndoLogService undoLogService;

    /***
     * UndoLog分页条件搜索实现
     * @param undoLog
     * @param page
     * @param size
     * @return
     */
    @ApiOperation(value = "UndoLog条件分页查询")
    //@ApiImplicitParams({
    //        @ApiImplicitParam(paramType = "path", name = "page", value = "当前页", required = true, dataType = "Integer"),
    //        @ApiImplicitParam(paramType = "path", name = "size", value = "每页显示条数", required = true, dataType = "Integer")
    //})
    @PostMapping(value = "/search/{page}/{size}" )
    public ServerResponse findPage(@RequestBody(required = false) @ApiParam(name = "UndoLog对象",value = "传入JSON数据",required = false) UndoLog undoLog, @PathVariable  int page, @PathVariable  int size){
        //调用UndoLogService实现分页条件查询UndoLog
        PageInfo<UndoLog> pageInfo = undoLogService.findPage(undoLog, page, size);
        return ServerResponse.success("查询成功",pageInfo);
    }

    /***
     * UndoLog分页搜索实现
     * @param page:当前页
     * @param size:每页显示多少条
     * @return
     */
    @ApiOperation(value = "UndoLog分页查询")
    //@ApiImplicitParams({
    //        @ApiImplicitParam(paramType = "path", name = "page", value = "当前页", required = true, dataType = "Integer"),
    //        @ApiImplicitParam(paramType = "path", name = "size", value = "每页显示条数", required = true, dataType = "Integer")
    //})
    @GetMapping(value = "/search/{page}/{size}" )
    public ServerResponse findPage(@PathVariable  int page, @PathVariable  int size){
        //调用UndoLogService实现分页查询UndoLog
        PageInfo<UndoLog> pageInfo = undoLogService.findPage(page, size);
        return ServerResponse.success("查询成功",pageInfo);
    }

    /***
     * 多条件搜索品牌数据
     * @param undoLog
     * @return
     */
    @ApiOperation(value = "UndoLog条件查询")
    @PostMapping(value = "/search" )
    public ServerResponse findList(@RequestBody(required = false) @ApiParam(name = "UndoLog对象",value = "传入JSON数据",required = false) UndoLog undoLog){
        //调用UndoLogService实现条件查询UndoLog
        List<UndoLog> list = undoLogService.findList(undoLog);
        return ServerResponse.success("查询成功",list);
    }

    /***
     * 根据ID删除品牌数据
     * @param id
     * @return
     */
    @ApiOperation(value = "UndoLog根据ID删除")
    @ApiImplicitParam(paramType = "path", name = "id", value = "主键ID", required = true, dataType = "Long")
    @DeleteMapping(value = "/{id}" )
    public ServerResponse delete(@PathVariable Long id){
        //调用UndoLogService实现根据主键删除
        undoLogService.delete(id);
        return ServerResponse.success("删除成功");
    }

    /***
     * 修改UndoLog数据
     * @param undoLog
     * @param id
     * @return
     */
    @ApiOperation(value = "UndoLog根据ID修改")
    @ApiImplicitParam(paramType = "path", name = "id", value = "主键ID", required = true, dataType = "Long")
    @PutMapping(value="/{id}")
    public ServerResponse update(@RequestBody @ApiParam(name = "UndoLog对象",value = "传入JSON数据",required = false) UndoLog undoLog, @PathVariable Long id){
        //设置主键值
        undoLog.setId(id);
        //调用UndoLogService实现修改UndoLog
        undoLogService.update(undoLog);
        return ServerResponse.success("修改成功");
    }

    /***
     * 新增UndoLog数据
     * @param undoLog
     * @return
     */
    @ApiOperation(value = "UndoLog添加")
    @PostMapping
    public ServerResponse add(@RequestBody  @ApiParam(name = "UndoLog对象",value = "传入JSON数据",required = true) UndoLog undoLog){
        //调用UndoLogService实现添加UndoLog
        undoLogService.add(undoLog);
        return ServerResponse.success("添加成功");
    }

    /***
     * 根据ID查询UndoLog数据
     * @param id
     * @return
     */
    @ApiOperation(value = "UndoLog根据ID查询")
    @ApiImplicitParam(paramType = "path", name = "id", value = "主键ID", required = true, dataType = "Long")
    @GetMapping("/{id}")
    public ServerResponse findById(@PathVariable Long id){
        //调用UndoLogService实现根据主键查询UndoLog
        UndoLog undoLog = undoLogService.findById(id);
        return ServerResponse.success("查询成功",undoLog);
    }

    /***
     * 查询UndoLog全部数据
     * @return
     */
    @ApiOperation(value = "查询所有UndoLog")
    @GetMapping
    public ServerResponse findAll(){
        //调用UndoLogService实现查询所有UndoLog
        List<UndoLog> list = undoLogService.findAll();
        return ServerResponse.success("查询成功",list) ;
    }
}
