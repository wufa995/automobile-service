/**
 * @author: wufa995<wufa995@qq.com>
 * @date: 2019/03/25 20:00
 */
package cn.wufa995.web.service;

import cn.wufa995.web.entity.PageInfo;
import cn.wufa995.web.repository.BaseRepository;

import java.io.Serializable;
import java.util.List;

public interface BaseService<T, ID extends Serializable> {

    BaseRepository getRepository();

    /**
     * 隐藏数据
     * @param id 数据编号
     * @return
     */
    int logicalHide(ID id);

    /**
     *显示数据
     * @param id 数据编号
     * @return
     */
    int logicalShow(ID id);

    /**
     *逻辑删除
     * @param id 数据编号
     * @return
     */
    int logicalDelete(ID id);

    /**
     *物理删除
     * @param id 数据编号
     * @return
     */
    int deleteByPrimaryKey(ID id);

    /**
     * 全量添加数据
     * @param record 数据对象
     * @return
     */
    int insert(T record);

    /**
     * 仅添加有值的数据
     * @param record 数据对象
     * @return
     */
    String insertSelective(T record);

    /**
     * 编辑数据 （非全量）
     * @param record 数据对象
     * @return
     */
    int updateByPrimaryKeySelective(T record);

    /**
     * 编辑数据 （全量）
     * @param record 数据对象
     * @return
     */
    int updateByPrimaryKey(T record);

    /**
     * 根据ID查询数据
     * @param id 数据编号
     * @return
     */
    T selectById(ID id);

    /**
     * 查询第一条符合条件得数据
     * @param record 数据对象
     * @return
     */
    T findOne(T record);

    /**
     * 判断是否存在
     * @param record 数据对象
     * @return
     */
    Boolean exist(T record);

    /**
     * 根据条件查询所有数据
     * @param record 数据对象
     * @return
     */
    List<T> findAllWithResult(T record);

    /**
     * 根据条件查询分页数据
     * @param record 数据对象
     * @return
     */
    List<T> findPageWithResult(T record);

    /**
     * 根据条件查询所有数据（带分页信息）
     * @param record 数据对象
     * @return
     */
    PageInfo findAllWithPageInfo(T record);

    /**
     * 根据条件查询分页数据（带分页信息）
     * @param record 数据对象
     * @return
     */
    PageInfo findPageWithPageInfo(T record);

    /**
     * 根据条件查询分页数据的总条数
     * @param record 数据对象
     * @return
     */
    Integer findPageWithCount(T record);

    /**
     * 根据条件查询分页数据（支持like查询）
     * @param record 数据对象
     * @return
     */
    PageInfo findPageWithPageInfoLike(T record);
}

