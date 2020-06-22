/**
 * @author: wufa995<wufa995@qq.com>
 * @date: 2018/4/3
 */
package cn.wufa995.web.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @description:
 * @author: wufa995<wufa995@qq.com>
 * @date: 2018/4/3 16:59
 * @version: V1.0
 */
@Data
//@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class PageInfo<T> {
    /** 页数 **/
    private Integer pageNo;

    /** 行数 **/
    private Integer pageSize;

    /** 总数 **/
    private Integer totalNum;

    /** 总页数 **/
    private Integer totalPage;

    private T data;

    public PageInfo(PageableEntity page, Integer totalNum, T data) {
        this.pageNo = page.getPageNo();
        this.pageSize = page.getPageSize();
        this.totalNum = totalNum;
        this.totalPage = (this.totalNum / this.pageSize);
        if (this.totalNum % this.pageSize > 0) {
            this.totalPage++;
        }
        this.data = data;
    }

    public PageInfo(T data) {
        this.pageNo = 0;
        this.pageSize = 0;
        this.totalNum = 0;
        this.totalPage = 0;
        this.data = data;
    }

    public PageInfo() {
    }
}
