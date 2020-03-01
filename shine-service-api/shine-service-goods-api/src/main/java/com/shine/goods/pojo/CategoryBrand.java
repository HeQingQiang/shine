package com.shine.goods.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @Description:CategoryBrand的Dao
 * ----------------------------
 * @Author:shine
 * @Date 2020/02/29 0:12
 * ----------------------------
 *****/
@ApiModel(description = "CategoryBrand",value = "CategoryBrand")
@Data
@Table(name="tb_category_brand")
public class CategoryBrand implements Serializable{

	@ApiModelProperty(value = "分类ID",required = false)
	@Id
    @Column(name = "category_id")
	private Integer categoryId;//分类ID
	@ApiModelProperty(value = "品牌ID",required = false)
    @Column(name = "brand_id")
	private Integer brandId;//品牌ID

}
