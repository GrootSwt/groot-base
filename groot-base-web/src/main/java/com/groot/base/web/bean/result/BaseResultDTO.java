package com.groot.base.web.bean.result;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
public abstract class BaseResultDTO implements Serializable {

    protected Status status;

    protected String message;

    public enum Status {
        success, failure, unauthorized;
    }
}
