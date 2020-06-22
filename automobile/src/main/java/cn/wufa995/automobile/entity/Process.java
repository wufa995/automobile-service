/**
 * @author: wufa995<wufa995@qq.com>
 * @date: 2019/05/01 10:22
 */
package cn.wufa995.automobile.entity;

import cn.wufa995.web.entity.PageableEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.lang.Double;
import java.lang.Integer;
import java.lang.String;
import java.util.Date;

/**
 * @description:
 * @author: wufa995<wufa995@qq.com>
 * @date: 2019/05/01 10:22
 * @version: V1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Process extends PageableEntity {
    /** 维修地址 **/
    private String address;
    /** 总花费 **/
    private Double allCost;
    /** 车牌号 **/
    private String carNum;
    /** 回应 **/
    private String content;
    /** 创建时间 **/
    private Date createDate;
    /** 处理时间 **/
    private Date dealDate;
    /** 驾驶证号 **/
    private String driverNum;
    /** 邮箱 **/
    private String email;
    /** 主键 **/
    private String id;
    /** 删除 **/
    private Integer isDelete;
    /** 维修项目 **/
    private String repairs;
    /** 预约ID **/
    private String scheduleId;
    /** 电话 **/
    private String tel;
    /** 1申请，2处理中，3已完成 **/
    private Integer type;
    /** 用户ID **/
    private String userId;
    /** 用户名 **/
    private String userName;
}
