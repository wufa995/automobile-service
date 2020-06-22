/**
 * @author: wufa995[wufa995@suixingpay.com]
 * @date: 2019年03月25日 20时54分
 */
package cn.wufa995.web.response;

import cn.wufa995.common.enums.ExceptionEnum;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.Objects;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class GenericResponse<T> {
    /**
     * 默认成功code
     */
    public static final String DEFAULT_SUCCESS_CODE = "0";

    /**
     * 默认成功message
     */
    public static final String DEFAULT_SUCCESS_MESSAGE = "SUCCESS";

    /**
     * 默认失败code
     */
    public static final String DEFAULT_FAILED_CODE = "9999";

    /**
     * 默认失败message
     */
    public static final String DEFAULT_FAILED_MESSAGE = "FAILED";

    @Builder.Default private String code = DEFAULT_SUCCESS_CODE;

    @Builder.Default private String message = DEFAULT_SUCCESS_MESSAGE;

    private T data;

    /**
     * 返回成功方法
     *
     * @return
     */
    public static GenericResponse success() {
        return success(null);
    }

    /**
     * 返回带数据成功方法
     *
     * @param data
     * @param <T>
     * @return
     */
    public static <T> GenericResponse success(T data) {
        return success("0", "SUCCESS", data);
    }

    /**
     * 自定义code message成功方法
     *
     * @param code
     * @param message
     * @param data
     * @param <T>
     * @return
     */
    public static <T> GenericResponse success(String code, String message, T data) {
        Objects.requireNonNull(code);
        Objects.requireNonNull(message);
        return GenericResponse.<T>builder().code(code).message(message).data(data).build();
    }

    /**
     * 默认失败方法
     *
     * @return
     */
    public static GenericResponse failed() {
        return failed(DEFAULT_FAILED_CODE, DEFAULT_FAILED_MESSAGE);
    }

    /**
     * 自定义message失败方法
     *
     * @param message
     * @return
     */
    public static GenericResponse failed(String message) {
        return failed(DEFAULT_FAILED_CODE, message);
    }

    /**
     * 自定义code message失败方法
     *
     * @param code
     * @param message
     * @return
     */
    public static GenericResponse failed(String code, String message) {
        return GenericResponse.builder().code(code).message(message).build();
    }

    public Boolean successed() {
        return DEFAULT_SUCCESS_CODE.equals(this.getCode());
    }

    @Override @SneakyThrows(JsonProcessingException.class) public String toString() {
        return new ObjectMapper().writeValueAsString(this);
    }

    /**
     * 直接传入异常枚举失败方法
     *
     * @param exceptionEnum
     * @return
     */
    public static GenericResponse failed(ExceptionEnum exceptionEnum) {
        return GenericResponse.builder().code(exceptionEnum.getCode()).message(exceptionEnum.getMessage()).build();
    }
}