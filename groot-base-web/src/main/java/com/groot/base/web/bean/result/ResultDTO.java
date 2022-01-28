package com.groot.base.web.bean.result;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ResultDTO<T> extends BaseResultDTO {

    private T data;

    public ResultDTO(Status status, String message) {
        this.status = status;
        this.message = message;
    }

    public ResultDTO(Status status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public static ResultDTO<Void> failure(String message) {
        return new ResultDTO<>(Status.failure, message);
    }

    public static ResultDTO<Void> unauthorized(String message) {
        return new ResultDTO<>(Status.unauthorized,message);
    }

    public static <T> ResultDTO<T> failure(String message, T data) {
        return new ResultDTO<>(Status.failure, message, data);
    }

    public static ResultDTO<Void> success(String message) {
        return new ResultDTO<>(Status.success, message);
    }

    public static <T> ResultDTO<T> success(String message, T data) {
        return new ResultDTO<>(Status.success, message, data);
    }
}
