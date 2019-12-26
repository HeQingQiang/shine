package com.shine.goods.controller;

import com.github.pagehelper.PageInfo;
import com.shine.common.ServerResponse;
import com.shine.goods.pojo.Brand;
import com.shine.goods.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/***********
 *@Author: Shine
 *@Description: 品牌控制器
 *@Data: Created in 2019/12/25 20:27
 *@Modified By:
 *****/
@RestController
@RequestMapping(value = "/brand")
@CrossOrigin   //跨域：A域名访问B域名的数据，存在跨域（域名或者请求端口或者协议不一致）
public class BrandController {

    @Autowired
    private BrandService brandService;

    /**
     * 查询全部品牌.
     *
     * @return
     */
    @GetMapping
    public ServerResponse getAllBrand() {
        return ServerResponse.success("查询全部品牌成功！", brandService.findAll());
    }

    /**
     * 按ID查询品牌
     *
     * @param id 品牌ID
     * @return 品牌
     */
    @GetMapping(value = "/{id}")
    public ServerResponse<Brand> getBrandById(@PathVariable(value = "id") Integer id) {
        return ServerResponse.success("查询品牌成功！", brandService.findById(id));
    }

    /**
     * 添加品牌
     *
     * @param brand 品牌参数
     * @return ServerResponse
     */
    @PostMapping
    public ServerResponse add(@RequestBody Brand brand) {
        brandService.add(brand);
        return ServerResponse.successMessage("创建品牌" + brand.getName() + "成功!");
    }

    /**
     * 根据ID修改品牌
     *
     * @param brand 品牌参数
     * @return ServerResponse
     */
    @PutMapping(value = "/{id}")
    public ServerResponse update(@RequestBody Brand brand, @PathVariable(value = "id") Integer id) {
        brand.setId(id);
        brandService.update(brand);
        return ServerResponse.successMessage("修改品牌成功!");
    }

    /**
     * 根据ID删除品牌
     *
     * @param id 品牌ID
     * @return ServerResponse
     */
    @DeleteMapping(value = "/{id}")
    public ServerResponse delete(@PathVariable(value = "id") Integer id) {
        brandService.delete(id);
        return ServerResponse.successMessage("删除品牌成功!");
    }

    /**
     * 条件查询.
     *
     * @param brand 品牌
     * @return 结果
     */
    @PostMapping("/search")
    public ServerResponse findList(@RequestBody Brand brand) {
        List<Brand> brands = brandService.findByList(brand);
        return ServerResponse.success("条件搜索查询品牌成功！", brands);
    }

    /**
     * 分页查询
     *
     * @param page 当前页
     * @param size 每页数量
     * @return 结果
     */
    @GetMapping("/search/{page}/{size}")
    public ServerResponse<PageInfo<Brand>> findPage(@PathVariable(value = "page") Integer page,
                                                    @PathVariable(value = "size") Integer size) {
        PageInfo<Brand> pageInfo = brandService.findPage(page, size);
        return ServerResponse.success("分页查询成功！", pageInfo);
    }

    /**
     * 分页条件查询
     *
     * @param page 当前页
     * @param size 每页数量
     * @return 结果
     */
    @PostMapping("/search/{page}/{size}")
    public ServerResponse<PageInfo<Brand>> findPage(@PathVariable(value = "page") Integer page,
                                                    @PathVariable(value = "size") Integer size,
                                                    @RequestBody Brand brand) {
        PageInfo<Brand> pageInfo = brandService.findPage(brand, page, size);
        return ServerResponse.success("分页查询成功！", pageInfo);
    }

}
