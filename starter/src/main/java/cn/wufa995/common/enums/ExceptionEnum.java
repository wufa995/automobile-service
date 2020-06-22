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
public enum ExceptionEnum {
    /**
     * 错误编号暂定为6位
     * 建议开头的类型
     * 100xxx 数据错误
     * 200xxx 权限错误
     * 300xxx 逻辑错误
     * 400xxx
     * **/

    // 100xxx 数据错误
    SELECT_FILE("100001","查询数据失败"),
    INSERT_FILE("100002","添加数据失败"),
    UPDATE_FILE("100003","修改数据失败"),
    DELETE_FILE("100004","删除数据失败"),
    INSERT_OR_UPDATE_FILE("100006","插入或者修改数据失败"),
    SELECT_BUDGET("100007", "查询项目经费失败"),
    PRINT_BUDGET("100008", "项目经费无法打印"),
    OPEN_SEND_FAIL("100010", "外网审批发送失败"),
    DATE_CONVERT_ERROR("100011", "Date类型转化失败"),

    // 200xxx 权限错误
    NO_PERMISSION("200001","没有权限"),

    // 300xxx 逻辑错误
    EXIST("300001","数据已存在"),
    EXIST_NAME("300002","名称已存在"),
    ACCEPTED("300003","数据已确认,不能进行该操作"),
    SUMMARY_END("300004","本周投产总结确认已结束,不能进行该操作"),
    ACTIVE_PREFIX("300005","流程名称必须以 ACTIVE_ 开头"),
    FONT_NOT_FOUND("300006","未找到字体文件"),
    HTML_TO_PDF_FAIL("300007","HTML转换PDF失败"),
    BASE64_UPLOAD_FAIL("300008","BASE64上传异常"),
    BASE64_CLOSE_FAIL("300009","BASE64流关闭异常"),
    FILE_UPLOAD_FAIL("300010","文件上传异常"),
    EXIST_KEY("300011","关键字已存在"),
    NONSUPPORT_FUN("300012","当前操作还不支持"),

    // 31xxx MQ和手机号审批
    SAVE_FAIL("310001","数据存入失败"),
    SAVE_REPEAT("310002","数据存储重复"),
    PARAM_IMPROPER("310003","参数合法但不符合需求"),
    SEND_FILE("310004","邮件发送异常"),
    DATA_ERROR("310005","参数或数据异常"),
    SYSTEM_ERROR("310006","系统内部异常"),
    EMAIL_URL_ERROR("310007","邮箱地址找不到"),
    STAFF_ERROR("310008","申请人或审核人找不到"),
    NOT_FIND_DATA("310009","在数据库中没获取到数据"),
    NOT_OPERATE("310010","没有操作权限"),
    NOT_CHECK_MAN("310011","登陆用户不是审核人"),
    NOT_EMAIL_RECEIVERS("310012","没有收件人"),


    // 32xxx 企业QQ
    SCHEME_NAME_EMPTY("320001","方案名称不能为空"),
    ITEM_KEY_NAME_EMPTY("320002","配置项关键字不能为空"),
    ITEM_VALUE_NAME_EMPTY("320003","配置项的值不能为空"),

    // 33xxxx 项目经费
    ONE_STEP_AUDIT("330001", "项目一级审批错误"),
    SECOND_STEP_AUDIT("330002", "项目二级审批错误"),

    // 400xxx 验证错误
    NO_ATTACHMENT("400005", "附件不能为空"),
    SEND_CREATE_ERROR("400006", "创新申请邮件发送失败"),

    // 500xxx 投产管理
    PRODUCT_NORMAL_AUDIT_FAIL("500000","常规投产添加失败"),
    PRODUCT_RESEARCH_AUDIT("500001", "研发紧急投产申请添加失败"),
    PRODUCT_BUSINESS_AUDIT_FAIL("500002", "业务紧急投产申请添加失败"),
    PRODUCT_NOTICE_EMAIL("500003", "投产管理结果通知发送失败"),
    PRODUCT_RESEARCH_REPEAT("500004","研发紧急投产已被他人申请"),
    PRODUCT_COUNT_FAIL("500005","统计工单数据异常"),
    PRODUCT_UPDATE_STATE_FAIL("5000006","更新状态失败"),
    PRODUCT_UPDATE_STATE_PROHIBIT("5000007","主流程已被拒绝"),
    PRODUCT_SUMMARY_JIRA("5000008", "请重新点击同步需求按钮"),
    PRODUCT_ADD_FAIL("5000009","投产申请失败"),
    PRODUCT_APPROVER_ERROR("5000010","审批人格式错误"),

    // 900xxx 其他错误
    NULL_ERROR("900001","其他错误"),
    PAGE_ERROR("900002", "分页错误"),
    NOT_FOUNT_GROUP_LEADER_SECOND("900003", "未找到审批人");


    private String code;

    private String message;

    ExceptionEnum(String code, String message) {
        this.setCode(code);
        this.setMessage(message);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    public static String getMessage(String code){
        for (ExceptionEnum exceptionEnum : ExceptionEnum.values()) {
            if (exceptionEnum.getMessage().equals(code)) {
                return exceptionEnum.message;
            }
        }
        return null;
    }
}
