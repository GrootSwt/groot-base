package com.groot.base.common;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 操作员信息
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class OperatorInfo {

    /**
     * 用户名
     */
    private String username;
    /**
     * 登录账号
     */
    private String loginName;
    /**
     * 手机号
     */
    private String phoneNumber;
    /**
     * 邮箱
     */
    private String email;
}
