package com.creator.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "更新用户信息dto")
public class UpdateUserInfoDto {
    @ApiModelProperty("邮箱")
    private String email;
    @ApiModelProperty("昵称")
    private String nickName;
    @ApiModelProperty(value = "用户性别", notes = "0男，1女，2未知")
    private String sex;
}
