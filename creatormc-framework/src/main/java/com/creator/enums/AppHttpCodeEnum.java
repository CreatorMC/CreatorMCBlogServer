package com.creator.enums;

public enum AppHttpCodeEnum {
    // 成功
    SUCCESS(200,"操作成功"),
    // 登录
    NEED_LOGIN(401,"需要登录后操作"),
    NO_OPERATOR_AUTH(403,"无权限操作"),
    SYSTEM_ERROR(500,"出现错误"),
    USERNAME_EXIST(501,"用户名已存在"),
    PHONENUMBER_EXIST(502,"手机号已存在"),
    EMAIL_EXIST(503, "邮箱已存在"),
    REQUIRE_USERNAME(504, "必需填写用户名"),
    LOGIN_ERROR(505,"用户名或密码错误"),
    CONTENT_NOT_NULL(506, "评论内容不能为空"),
    FILE_TYPE_ERROR(507, "文件类型错误"),
    FILE_SIZE_ERROR(508, "文件超出大小限制"),
    USERNAME_NOT_NULL(509, "用户名不能为空"),
    NICKNAME_NOT_NULL(510, "昵称不能为空"),
    PASSWORD_NOT_NULL(511, "密码不能为空"),
    EMAIL_NOT_NULL(512, "邮箱不能为空"),
    NICKNAME_EXIST(513, "昵称已存在"),
    IMAGE_RANDOM_NULL(514, "没有图片可以显示"),
    ARTICLE_IS_NULL(515, "文章不存在"),
    EMAIL_IS_NULL(516, "邮箱未注册"),
    EMAIL_CODE_NOT_EQUALS(517, "验证码错误或已过期"),
    UPDATE_PASSWORD_ERROR(518, "密码更新失败，请检查邮箱输入是否正确"),
    SENSITIVE_WORD_EXISTED(519, "敏感词已存在"),
    USER_BAN(520, "您已被封禁，解封时间：%s。"),
    MENU_DELETE_ERROR(500, "存在子菜单不允许删除");
    int code;
    String msg;

    AppHttpCodeEnum(int code, String errorMessage){
        this.code = code;
        this.msg = errorMessage;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}

