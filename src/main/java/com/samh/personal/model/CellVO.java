package com.samh.personal.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@ApiModel(value = "CellVO", description = "分群画像小组件返回类")
@Data
public class CellVO implements Serializable {

    private static final long serialVersionUID = -1;

    @ApiModelProperty(value = "人数", dataType = "Long")
    private Long people;
    @ApiModelProperty(value = "百分比", dataType = "Float")
    private Float percent;
    @ApiModelProperty(value = "开始下标", dataType = "String")
    private String bucket_start;
    @ApiModelProperty(value = "结束下标", dataType = "String")
    private String bucket_end;
}