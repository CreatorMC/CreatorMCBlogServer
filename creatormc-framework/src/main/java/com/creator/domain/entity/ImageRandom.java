package com.creator.domain.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * (ImageRandom)表实体类
 *
 * @author makejava
 * @since 2023-08-19 23:38:46
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImageRandom {
    //随机图片id
    @TableId
    private Long id;
    //图片链接
    private String url;
    //图片类型（0是电脑图片，1是手机图片）
    private String type;
    //是否参与到随机显示的图片中（0不显示，1显示）
    private String isShow;
    
    private Long createBy;
    
    private Date createTime;
    
    private Long updateBy;
    
    private Date updateTime;
    //删除标志（0代表未删除，1代表已删除）
    private Integer delFlag;
}

