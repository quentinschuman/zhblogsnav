package com.example.zhblogsnav.common;

/**
 * zhblogsnav
 *
 * @author qianshu
 * @date 2022/10/17
 */
public class BaseResult<T> {

    private static final Boolean SUCCESS_TYPE = true;

    private static final Boolean FAIL_TYPE = false;

    private Boolean success;

    private T data;

    public BaseResult() {
    }

    public BaseResult(Boolean success, T data) {
        this.success = success;
        this.data = data;
    }

    public static <T> BaseResult<T> success(T data) {
        return new BaseResult<>(SUCCESS_TYPE, data);
    }

    public static <T> BaseResult<T> fail(T data) {
        return new BaseResult<>(FAIL_TYPE, data);
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
