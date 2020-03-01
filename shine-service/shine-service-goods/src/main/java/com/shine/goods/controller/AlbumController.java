package com.shine.goods.controller;

import com.github.pagehelper.PageInfo;
import com.shine.common.ServerResponse;
import com.shine.goods.pojo.Album;
import com.shine.goods.service.AlbumService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description:Album的Dao
 * ----------------------------
 * @Author:shine
 * @Date 2020/02/29 0:12
 * ----------------------------
 *****/
@Api(tags = "图片入口")
@RestController
@RequestMapping("/album")
@CrossOrigin
public class AlbumController {

    @Autowired
    private AlbumService albumService;

    /***
     * Album分页条件搜索实现
     * @param album
     * @param page
     * @param size
     * @return
     */
    @ApiOperation(value = "Album条件分页查询")
    //@ApiImplicitParams({
    //        @ApiImplicitParam(paramType = "path", name = "page", value = "当前页", required = true, dataType = "Integer"),
    //        @ApiImplicitParam(paramType = "path", name = "size", value = "每页显示条数", required = true, dataType = "Integer")
    //})
    @PostMapping(value = "/search/{page}/{size}" )
    public ServerResponse findPage(@RequestBody(required = false) @ApiParam(name = "Album对象",value = "传入JSON数据",required = false) Album album, @PathVariable  int page, @PathVariable  int size){
        //调用AlbumService实现分页条件查询Album
        PageInfo<Album> pageInfo = albumService.findPage(album, page, size);
        return ServerResponse.success("查询成功",pageInfo);
    }

    /***
     * Album分页搜索实现
     * @param page:当前页
     * @param size:每页显示多少条
     * @return
     */
    @ApiOperation(value = "Album分页查询")
    //@ApiImplicitParams({
    //        @ApiImplicitParam(paramType = "path", name = "page", value = "当前页", required = true, dataType = "Integer"),
    //        @ApiImplicitParam(paramType = "path", name = "size", value = "每页显示条数", required = true, dataType = "Integer")
    //})
    @GetMapping(value = "/search/{page}/{size}" )
    public ServerResponse findPage(@PathVariable  int page, @PathVariable  int size){
        //调用AlbumService实现分页查询Album
        PageInfo<Album> pageInfo = albumService.findPage(page, size);
        return ServerResponse.success("查询成功",pageInfo);
    }

    /***
     * 多条件搜索品牌数据
     * @param album
     * @return
     */
    @ApiOperation(value = "Album条件查询")
    @PostMapping(value = "/search" )
    public ServerResponse findList(@RequestBody(required = false) @ApiParam(name = "Album对象",value = "传入JSON数据",required = false) Album album){
        //调用AlbumService实现条件查询Album
        List<Album> list = albumService.findList(album);
        return ServerResponse.success("查询成功",list);
    }

    /***
     * 根据ID删除品牌数据
     * @param id
     * @return
     */
    @ApiOperation(value = "Album根据ID删除")
    @ApiImplicitParam(paramType = "path", name = "id", value = "主键ID", required = true, dataType = "Long")
    @DeleteMapping(value = "/{id}" )
    public ServerResponse delete(@PathVariable Long id){
        //调用AlbumService实现根据主键删除
        albumService.delete(id);
        return ServerResponse.success("删除成功");
    }

    /***
     * 修改Album数据
     * @param album
     * @param id
     * @return
     */
    @ApiOperation(value = "Album根据ID修改")
    @ApiImplicitParam(paramType = "path", name = "id", value = "主键ID", required = true, dataType = "Long")
    @PutMapping(value="/{id}")
    public ServerResponse update(@RequestBody @ApiParam(name = "Album对象",value = "传入JSON数据",required = false) Album album, @PathVariable Long id){
        //设置主键值
        album.setId(id);
        //调用AlbumService实现修改Album
        albumService.update(album);
        return ServerResponse.success("修改成功");
    }

    /***
     * 新增Album数据
     * @param album
     * @return
     */
    @ApiOperation(value = "Album添加")
    @PostMapping
    public ServerResponse add(@RequestBody  @ApiParam(name = "Album对象",value = "传入JSON数据",required = true) Album album){
        //调用AlbumService实现添加Album
        albumService.add(album);
        return ServerResponse.success("添加成功");
    }

    /***
     * 根据ID查询Album数据
     * @param id
     * @return
     */
    @ApiOperation(value = "Album根据ID查询")
    @ApiImplicitParam(paramType = "path", name = "id", value = "主键ID", required = true, dataType = "Long")
    @GetMapping("/{id}")
    public ServerResponse findById(@PathVariable Long id){
        //调用AlbumService实现根据主键查询Album
        Album album = albumService.findById(id);
        return ServerResponse.success("查询成功",album);
    }

    /***
     * 查询Album全部数据
     * @return
     */
    @ApiOperation(value = "查询所有Album")
    @GetMapping
    public ServerResponse findAll(){
        //调用AlbumService实现查询所有Album
        List<Album> list = albumService.findAll();
        return ServerResponse.success("查询成功",list) ;
    }
}
