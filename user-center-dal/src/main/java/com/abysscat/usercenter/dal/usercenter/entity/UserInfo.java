package com.abysscat.usercenter.dal.usercenter.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@Table(name = "`user_info`")
public class UserInfo implements Serializable {
    /**
     * 主键
     */
    @Id
    @Column(name = "`id`")
    @GeneratedValue(generator = "JDBC")
    private Long id;

    /**
     * 用户唯一id
     */
    @Column(name = "`user_id`")
    private Long userId;

    /**
     * 用户头像
     */
    @Column(name = "`avatar`")
    private String avatar;

    /**
     * 用户昵称
     */
    @Column(name = "`nickname`")
    private String nickname;

    /**
     * 用户性别
     */
    @Column(name = "`gender`")
    private Integer gender;

    /**
     * 用户邮箱
     */
    @Column(name = "`email`")
    private String email;

    /**
     * 创建时间
     */
    @Column(name = "`create_time`")
    private Date createTime;

    /**
     * 创建用户，调用方标识
     */
    @Column(name = "`create_user`")
    private String createUser;

    /**
     * 更新时间
     */
    @Column(name = "`update_time`")
    private Date updateTime;

    /**
     * 更新用户，调用方标识
     */
    @Column(name = "`update_user`")
    private String updateUser;

    /**
     * 是否删除，0 未删除、1 已删除
     */
    @Column(name = "`is_del`")
    private Integer isDel;


    public static final String FIELD_ID = "id";

    public static final String FIELD_USER_ID = "userId";

    public static final String FIELD_AVATAR = "avatar";

    public static final String FIELD_NICKNAME = "nickname";

    public static final String FIELD_GENDER = "gender";

    public static final String FIELD_EMAIL = "email";

    public static final String FIELD_CREATE_TIME = "createTime";

    public static final String FIELD_CREATE_USER = "createUser";

    public static final String FIELD_UPDATE_TIME = "updateTime";

    public static final String FIELD_UPDATE_USER = "updateUser";

    public static final String FIELD_IS_DEL = "isDel";


    private static final long serialVersionUID = 1L;
}