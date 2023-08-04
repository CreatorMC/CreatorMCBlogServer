package com.creator.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "用户登录dto")
public class LoginDto {
    @ApiModelProperty(notes = "用户名")
    private String userName;
    @ApiModelProperty(notes = "密码")
    private String password;
}
