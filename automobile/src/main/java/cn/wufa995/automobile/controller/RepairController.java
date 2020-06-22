/**
 * @author: wufa995<wufa995@qq.com>
 * @date: 2019/04/25 10:41
 */
package cn.wufa995.automobile.controller;

import cn.wufa995.automobile.repository.RepairRepository;
import cn.wufa995.automobile.schedule.DynamicTaskScheduler;
import cn.wufa995.common.annotation.ApiDescribe;
import cn.wufa995.common.util.CheckParam;
import cn.wufa995.web.entity.PageInfo;
import cn.wufa995.web.response.GenericResponse;
import cn.wufa995.web.service.BaseService;
import cn.wufa995.web.controller.BaseController;
import cn.wufa995.automobile.entity.Repair;
import cn.wufa995.automobile.service.RepairService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

/**
 * @description:
 * @author: wufa995<wufa995@qq.com>
 * @date: 2019/04/25 10:41
 * @version: V1.0
 */
@Slf4j
@Controller
@RequestMapping("/repairs")
public class RepairController extends BaseController<Repair, String> {

    @Autowired
    private RepairService repairService;

    @Autowired
    private RepairRepository repairRepository;

    @Autowired
    private DynamicTaskScheduler dynamicTaskScheduler;

    @Override
    public BaseService getService() {
        return repairService;
    }

    @GetMapping("/add")
    @ApiDescribe(describe = "测试")
    public String addRepair() {
        return "repair_add";
    }

    @GetMapping("/update/{id}")
    public String updateUser(Model model, @PathVariable("id") String id) {
        model.addAttribute("repair_info", this.repairService.selectById(id));
        return "repair_update";
    }

    @ResponseBody
    @ApiDescribe(describe = "测试接口")
    @PostMapping("/update/type/{id}")
    public Callable<GenericResponse> updateType(@PathVariable("id") String id) {
        Repair repair = this.repairService.selectById(id);
        if(repair.getIsUse() == 0) {
            repair.setIsUse(1);
        } else {
            repair.setIsUse(0);
        }
        this.repairService.updateByPrimaryKeySelective(repair);
        return GenericResponse::success;
    }

    @ResponseBody
    @ApiDescribe(describe = "测试接口")
    @PostMapping("/delete/{id}")
    public Callable<GenericResponse> deleteUser(@PathVariable("id") String id) {
        this.repairService.updateByPrimaryKeySelective(Repair.builder()
                .id(id)
                .isDelete(1)
                .build());
        return GenericResponse::success;
    }

    @GetMapping("/manager")
    @ApiDescribe(describe = "测试接口")
    public String manager(Model model, String queryName, Repair repair) {
        Map<String, String> orders = new HashMap<>();
        Map<String, String> likes = new HashMap<>();
        likes.put("name", queryName);
        orders.put("createDate", "desc");
        repair.setIsDelete(0);
        repair.setLikes(likes);
        repair.setOrders(orders);
        PageInfo allWithPageInfo = this.repairService.findPageWithPageInfo(repair);
        model.addAttribute("page", allWithPageInfo);
        model.addAttribute("query", queryName);
        return "repair_manager";
    }

    @PostMapping("/add")
    @ApiDescribe(describe = "测试接口")
    public String addCheck(Model model, Repair repair) {
        if(CheckParam.notEmpty(repair)) {
            repair.setCreateDate(new Date());
            this.repairService.insertSelective(repair);
            model.addAttribute("message","添加成功！");
        } else {
            model.addAttribute("message","添加失败，邮箱重复！");
        }
        return "repair_add";
    }

    @PostMapping("/update")
    @ApiDescribe(describe = "测试接口")
    public String updateCheck(Repair repair) {
        this.repairService.updateByPrimaryKeySelective(repair);
        return "redirect:/repairs/update/" + repair.getId();
    }

    @GetMapping("/find/all/test")
    @ApiDescribe(describe = "测试接口")
    @ResponseBody
    public Callable<GenericResponse<Object>> findAllTest() {
        return () -> GenericResponse.success(this.repairRepository.findAllTest());
    }

    @GetMapping("/schedule/start/{name}")
    @ResponseBody
    public String startTest(@RequestParam(value = "cron", defaultValue = "0/10 * * * * ?") String cron,@PathVariable("name") String name) {
        log.info("cron={}", cron);
        dynamicTaskScheduler.startCron(
                () -> {
                    log.info("模拟执行作业,cron={},name={}", cron, name);
                },
                cron,
                name
        );
        return "success";
    }

    @GetMapping("/schedule/stop/{name}")
    @ResponseBody
    public String stopTest(@RequestParam(value = "cron", defaultValue = "0/10 * * * * ?") String cron,@PathVariable("name") String name) {
        dynamicTaskScheduler.stopCron(name);
        return "success";
    }
}
