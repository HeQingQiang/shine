package com.shine.goods.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @Description:Brand的Dao
 * ----------------------------
 * @Author:shine
 * @Date 2020/02/29 0:12
 * ----------------------------
 *****/
@ApiModel(description = "Brand",value = "Brand")
@Data
@Table(name="tb_brand")
public class Brand implements Serializable{

	@ApiModelProperty(value = "品牌id",required = false)
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
	private Integer id;//品牌id
	@ApiModelProperty(value = "品牌名称",required = false)
    @Column(name = "name")
	private String name;//品牌名称
	@ApiModelProperty(value = "品牌图片地址",required = false)
    @Column(name = "image")
	private String image;//品牌图片地址
	@ApiModelProperty(value = "品牌的首字母",required = false)
    @Column(name = "letter")
	private String letter;//品牌的首字母
	@ApiModelProperty(value = "排序",required = false)
    @Column(name = "seq")
	private Integer seq;//排序

}
