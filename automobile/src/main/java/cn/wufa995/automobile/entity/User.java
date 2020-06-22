/**
 * @author: wufa995<wufa995@qq.com>
 * @date: 2019/04/25 10:41
 */
package cn.wufa995.automobile.entity;

import cn.wufa995.common.vo.CommonVO;
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
 * @date: 2019/04/25 10:41
 * @version: V1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class User extends PageableEntity {
    /** 创建时间 **/
    private Date createDate;
    /** 邮箱 **/
    private String email;
    /** 主键 **/
    private String id;
    /** 删除 **/
    private Integer isDelete;
    /** 等级 **/
    private Integer level;
    /** 密码 **/
    private String password;
    /** 身份 **/
    private String role;
    /** 手机号 **/
    private String tel;
    /** 用户名 **/
    private String userName;
    /** 保留字段 **/
    private CommonVO commonVO;
}
