/**
 * @author: wufa995<wufa995@qq.com>
 * @date: 2019/04/25 10:41
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
 * @date: 2019/04/25 10:41
 * @version: V1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Repair extends PageableEntity {
    /** 创建时间 **/
    private Date createDate;
    /** 说明 **/
    private String description;
    /** 主键 **/
    private String id;
    /** 删除 **/
    private Integer isDelete;
    /** 使用？ **/
    private Integer isUse;
    /** 花费多少钱 **/
    private Double money;
    /** 维修项目名 **/
    private String name;
    /** 大约用时 **/
    private Integer useHour;
    /** 创建时间 **/
    private String createDateStr;
}
