package com.example.team4.global.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DataResponse<T> extends BaseResponse {

    private final T data;

    private DataResponse(HttpStatus status, T data) {
        super(status);
        this.data = data;
    }

    public static <T> DataResponse<T> from(T data) {
        return new DataResponse<>(HttpStatus.OK, data);
    }

    public static <T> DataResponse<Void> ok() {
        return new DataResponse<>(HttpStatus.OK, null);
    }

    public static <T> DataResponse<T> created(T data) {
        return new DataResponse<>(HttpStatus.CREATED, data);
    }
}
