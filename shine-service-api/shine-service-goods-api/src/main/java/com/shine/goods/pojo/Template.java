package com.shine.goods.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @Description:Template的Dao
 * ----------------------------
 * @Author:shine
 * @Date 2020/02/29 0:12
 * ----------------------------
 *****/
@ApiModel(description = "Template",value = "Template")
@Data
@Table(name="tb_template")
public class Template implements Serializable{

	@ApiModelProperty(value = "ID",required = false)
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
	private Integer id;//ID
	@ApiModelProperty(value = "模板名称",required = false)
    @Column(name = "name")
	private String name;//模板名称
	@ApiModelProperty(value = "规格数量",required = false)
    @Column(name = "spec_num")
	private Integer specNum;//规格数量
	@ApiModelProperty(value = "参数数量",required = false)
    @Column(name = "para_num")
	private Integer paraNum;//参数数量

}
