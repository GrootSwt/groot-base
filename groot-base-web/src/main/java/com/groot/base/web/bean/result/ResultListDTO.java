package com.groot.base.web.bean.result;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class ResultListDTO<T> extends BaseResultDTO {

    private List<T> data;


    public ResultListDTO(Status status, String message) {
        this.status = status;
        this.message = message;
    }

    public ResultListDTO(Status status, String message, List<T> data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public static ResultListDTO<Void> failure(String message) {
        return new ResultListDTO<>(Status.failure, message);
    }


    public static ResultListDTO<Void> success(String message) {
        return new ResultListDTO<>(Status.success, message);
    }

    public static <T> ResultListDTO<T> success(String message, List<T> data) {
        return new ResultListDTO<>(Status.success, message, data);
    }
}
