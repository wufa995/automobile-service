/**
 * @author: wufa995[wufa995@qq.com]
 * @date: 2019年03月27日 15时42分
 */
package cn.wufa995.common.util;

import java.util.List;

public class CheckParam {

    /**
     * 判断是否为空或空字符串
     * @param o 任意的参数
     * @return 真或假
     */
    public static Boolean notEmpty(Object o) {
        return o != null && !"".equals(o.toString().trim());
    }

    /**
     * 判断是否为空或空集合
     * @param list 集合
     * @return 真或假
     */
    public static Boolean notEmpty(List<Object> list) {
        return list != null && list.size() != 0;
    }

    public static Boolean isEmpty(Object o) {
        return !notEmpty(o);
    }

    public static Boolean isEmpty(List<Process> list) {
        return !notEmpty(list);
    }
}