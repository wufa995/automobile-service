/**
 * @author: wufa995<wufa995@qq.com>
 * @date: 2018/3/21
 */
package cn.wufa995.common.enums;

/**
 * @description: 异常枚举
 * @author: wufa995<wufa995@qq.com>
 * @date: 2018/3/21 10:53
 * @version: V1.0
 */
public enum ProjectNameEnum {
    ALL("100000","通用系统"),
    AMS("100001","网上汽车维修预约系统"),
    TEST01("100002","测试01"),
    TEST02("100003","测试02");


    private String code;

    private String projectName;

    ProjectNameEnum(String code, String message) {
        this.setCode(code);
        this.setMessage(message);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setMessage(String message) {
        this.projectName = message;
    }

    public static String getMessage(String code){
        for (ProjectNameEnum exceptionEnum : ProjectNameEnum.values()) {
            if (exceptionEnum.getProjectName().equals(code)) {
                return exceptionEnum.projectName;
            }
        }
        return null;
    }
}
