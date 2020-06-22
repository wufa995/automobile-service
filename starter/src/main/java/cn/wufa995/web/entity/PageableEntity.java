/**
 * @author: wufa995[wufa995@qq.com]
 * @date: 2019年03月25日 20时25分
 */
package cn.wufa995.web.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class PageableEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 页数 起始页为0 **/
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Integer pageNo;

    /** 行数 **/
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Integer pageSize;

    /** 开始条数 起始条数为0 **/
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Integer start;

    /** 排序 **/
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Map<String, String> orders;

    /** 模糊匹配 **/
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Map<String, String> likes;

    public Integer getStart() {
        if(pageNo == null || pageSize == null){
            return null;
        }
        start = (pageNo - 1) * pageSize;
        return start;
    }
}
