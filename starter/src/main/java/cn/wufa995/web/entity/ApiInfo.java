package cn.wufa995.web.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ApiInfo {

    private String id;
    private String method;
    private String url;
    private String functionName;
    private String paramsNames;
    private String params;
    private String result;
    private String projectName;
    private String describe;
    private String beasUrl;
    private String classReference;
    private String methodName;
    private Integer isFinish;
}
