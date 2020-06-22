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
public class Schedule extends PageableEntity {
    /** 预定数量 **/
    private Integer applyNum;
    /** 结束时间 **/
    private Date endDate;
    /** 主键 **/
    private String id;
    /** 删除 **/
    private Integer isDelete;
    /** 开始时间 **/
    private Date startDate;
    /** 修理人 **/
    private String updater;
}
