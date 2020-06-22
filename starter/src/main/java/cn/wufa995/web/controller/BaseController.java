/**
 * @author: wufa995<wufa995@qq.com>
 * @date: 2019/03/25 20:00
 */
package cn.wufa995.web.controller;

import cn.wufa995.web.entity.PageInfo;
import cn.wufa995.web.response.GenericResponse;
import cn.wufa995.web.service.BaseService;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.concurrent.Callable;

public abstract class BaseController<T, ID extends Serializable> {

    public BaseService service;

    public abstract BaseService getService();

    /** 查询全部数据 **/
    @GetMapping("")
    public Callable<GenericResponse<PageInfo>> findAll() {
        return () -> GenericResponse.success(getService().findAllWithResult(null));
    }

    /** 查询全部数据 可分页 **/
    @PostMapping("/page")
    public Callable<GenericResponse<PageInfo>> findAll(@RequestBody(required = false) T record) {
        return () -> {
            if (record == null) {
                return GenericResponse.success(getService().findAllWithPageInfo(record));
            } else {
                return GenericResponse.success(getService().findPageWithPageInfo(record));
            }
        };
    }

    /** 查询全部数据 可分页 可模糊查询 **/
    @PostMapping("/page/like")
    public Callable<GenericResponse<PageInfo>> findLike(@RequestBody(required = false) T record) {
        return () -> {
            if (record == null) {
                return GenericResponse.success(getService().findAllWithPageInfo(record));
            } else {
                return GenericResponse.success(getService().findPageWithPageInfoLike(record));
            }
        };
    }

    /** 根据ID查询数据 **/
    @GetMapping("/{id}")
    public Callable<GenericResponse<T>> findByID(@PathVariable("id") ID id) {
        return () -> GenericResponse.success(getService().selectById(id));
    }

    /** 添加一条数据 （全量和非全量） **/
    @PostMapping("")
    public Callable<GenericResponse<T>> add(@RequestBody T record) {
        return () ->  GenericResponse.success(getService().insertSelective(record));
    }

    /** 编辑数据 （全量） **/
    @PutMapping("/{id}")
    public Callable<GenericResponse<T>> edit(@RequestBody T record, @PathVariable(value = "id", required = false) ID id) {
        return () -> GenericResponse.success(getService().updateByPrimaryKey(record));
    }

    /** 编辑数据 （非全量） **/
    @PatchMapping("/{id}")
    public Callable<GenericResponse<T>> editSelective(@RequestBody T record, @PathVariable(value = "id", required = false) ID id) {
        return () -> GenericResponse.success(getService().updateByPrimaryKeySelective(record));
    }

    /** 编辑数据 （非全量,针对某些不支持PATCH的地方） **/
    @PostMapping("/edit/{id}")
    public Callable<GenericResponse<T>> editPost(@RequestBody T record, @PathVariable(value = "id", required = false) ID id) {
        return () -> GenericResponse.success(getService().updateByPrimaryKeySelective(record));
    }

    /** 删除数据 **/
    @DeleteMapping("/{id}")
    public Callable<GenericResponse<T>> delete(@PathVariable("id") ID id) {
        return () -> GenericResponse.success(getService().deleteByPrimaryKey(id));
    }

    /** 判断是否存在 **/
    @PostMapping("/exist")
    public Callable<GenericResponse<Boolean>> exist(@RequestBody T record) {
        return () -> GenericResponse.success(getService().exist(record));
    }

    /** 隐藏数据 **/
    @GetMapping("/logical/hide/{id}")
    public Callable<GenericResponse<T>> logicalHide(@PathVariable("id") ID id) {
        return () -> GenericResponse.success(getService().logicalHide(id));
    }

    /** 显示数据 **/
    @GetMapping("/logical/show/{id}")
    public Callable<GenericResponse<T>> logicalShow(@PathVariable("id") ID id) {
        return () -> GenericResponse.success(getService().logicalShow(id));
    }

    /** 逻辑删除数据 **/
    @DeleteMapping("/logical/delete/{id}")
    public Callable<GenericResponse<T>> logicalDelete(@PathVariable("id") ID id) {
        return () -> GenericResponse.success(getService().logicalDelete(id));
    }

}
