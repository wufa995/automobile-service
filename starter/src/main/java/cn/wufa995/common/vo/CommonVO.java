/**
 * @author: wufa995[wufa995@qq.com]
 * @date: 2019年04月28日 09时39分
 */
package cn.wufa995.common.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class CommonVO {
    Integer index;
    String type;
    String key;
    Object value;
    Map<String, Object> map;
    List<Object> list;
    Set<Object> set;
}