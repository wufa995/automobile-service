/**
 * @author: wufa995<wufa995@qq.com>
 * @date: 2019/03/25 20:00
 */
package cn.wufa995.web.repository;

import java.io.Serializable;
import java.util.List;

public interface BaseRepository<T, ID extends Serializable> {
    int deleteByPrimaryKey(ID id);

    int insert(T record);

    int insertSelective(T record);

    int updateByPrimaryKeySelective(T record);

    int updateByPrimaryKey(T record);

    T selectById(ID id);

    T findOne(T record);

    List<T> findAllWithResult(T record);

    List<T> findPageWithResult(T record);

    Integer findPageWithCount(T record);

    List<T> findPageWithResultLike(T record);

    Integer findPageWithCountLike(T record);
}
