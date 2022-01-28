package com.groot.base.web.bean.result;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Setter
@ToString
public class ResultPageDTO<T> extends BaseResultDTO {

    private List<T> data;

    private long total;


    public ResultPageDTO(Status status, String message) {
        this.status = status;
        this.message = message;
    }

    public ResultPageDTO(Status status, String message, List<T> data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public static ResultPageDTO<Void> failure(String message) {
        return new ResultPageDTO<>(Status.failure, message);
    }


    public static ResultPageDTO<Void> success(String message) {
        return new ResultPageDTO<>(Status.success, message);
    }

    public static <T> ResultPageDTO<T> success(String message, Page<T> data) {
        ResultPageDTO<T> result = new ResultPageDTO<>(Status.success, message);
        return pageToList(result, data);
    }


    public static <T> ResultPageDTO<T> pageToList(ResultPageDTO<T> result, Page<T> data) {
        result.data = data.getContent();
        result.total = data.getTotalElements();
        return result;
    }
}
