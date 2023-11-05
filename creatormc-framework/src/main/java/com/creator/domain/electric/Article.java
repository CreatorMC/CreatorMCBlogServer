package com.creator.domain.electric;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;
import java.util.List;

@Data
@Document(indexName = "article")
public class Article {
    @Id
    @Field(type = FieldType.Long)
    private Long id;
    //标题
    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String title;
    //文章内容
    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String content;
    //文章摘要
    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String summary;
    //所属分类id
    @Field(type = FieldType.Long)
    private Long categoryId;
    //是否置顶（0否，1是）
    @Field(type = FieldType.Text)
    private String isTop;
    //状态（0已发布，1草稿）
    //只有已发布的文章在 ElectricSearch 里，需要在发布文章的消息队列的生产者处进行判断！！！
    //private String status;
    //标签id列表
    @Field(type = FieldType.Long)
    private List<Long> tags;

    @Field(type = FieldType.Long)
    private Long createBy;

    @Field(type = FieldType.Date,format = DateFormat.basic_date_time_no_millis)
    private Date createTime;

    @Field(type = FieldType.Long)
    private Long updateBy;

    @Field(type = FieldType.Date,format = DateFormat.basic_date_time_no_millis)
    private Date updateTime;
}
