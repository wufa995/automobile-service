/**
 * @author: wufa995<wufa995@qq.com>
 * @date: 2019/04/25 10:41
 */
package cn.wufa995.automobile.controller;

import cn.wufa995.automobile.entity.Repair;
import cn.wufa995.automobile.entity.Schedule;
import cn.wufa995.automobile.entity.User;
import cn.wufa995.automobile.service.RepairService;
import cn.wufa995.automobile.service.ScheduleService;
import cn.wufa995.automobile.service.UserService;
import cn.wufa995.common.annotation.ApiDescribe;
import cn.wufa995.common.util.CheckParam;
import cn.wufa995.common.util.JsoupHelper;
import cn.wufa995.common.util.SendEmailHelper;
import cn.wufa995.web.entity.PageInfo;
import cn.wufa995.web.response.GenericResponse;
import cn.wufa995.web.service.BaseService;
import cn.wufa995.web.controller.BaseController;
import cn.wufa995.automobile.entity.Process;
import cn.wufa995.automobile.service.ProcessService;
import javassist.*;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.LocalVariableAttribute;
import javassist.bytecode.MethodInfo;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.Callable;

/**
 * @description:
 * @author: wufa995<wufa995@qq.com>
 * @date: 2019/04/25 10:41
 * @version: V1.0
 */
@Controller
@RequestMapping("/processs")
public class ProcessController extends BaseController<Process, String> {

    @Autowired
    private ProcessService processService;
    @Autowired
    private RepairService repairService;
    @Autowired
    private ScheduleService scheduleService;
    @Autowired
    private UserService userService;

    @Override
    public BaseService getService() {
        return processService;
    }

    @PostMapping("/invoice/update")
    public String invoiceUpdate(Process process) {
        this.processService.updateByPrimaryKeySelective(process);
        return "redirect:/processs/invoice/" + process.getId();
    }

    @GetMapping("/manager")
    @ApiDescribe(describe = "测试接口")
    public String manager(Model model,Process process, Integer queryType) {
        Map<String,String> map = new HashMap<>();
        map.put("createDate", "desc");
        process.setOrders(map);
        process.setType(queryType);
        PageInfo pageWithPageInfo = this.processService.findPageWithPageInfo(process);
        model.addAttribute("page",pageWithPageInfo);
        model.addAttribute("queryType",queryType);
        return "process_manager";
    }

    @ResponseBody
    @PostMapping("/add/{processId}/{userId}")
    @ApiDescribe(describe = "测试接口")
    public Callable<GenericResponse> add(@PathVariable("processId") String processId,@PathVariable("userId") String userId) {
        this.processService.updateByPrimaryKeySelective(Process.builder()
                .id(processId)
                .userId(userId)
                .build());
        return GenericResponse::success;
    }

    @GetMapping("/apply")
    @ApiDescribe(describe = "测试接口")
    public String processApply(Model model) {
        List<String> list = new ArrayList<>();
        list.add("长春市南关区吉林建筑大学维修区");
        list.add("长春市南关区净月维修区");
        list.add("长春市朝阳区维修区");
        Repair build = Repair.builder().isDelete(0).isUse(1).build();
        build.setPageNo(1);
        build.setPageSize(6);
        PageInfo pageInfo = this.repairService
                .findPageWithPageInfo(build);
        List<PageInfo> pageInfoList = new ArrayList<>();
        pageInfoList.add(pageInfo);
        for(int i = 2; i < pageInfo.getTotalPage(); i++) {
            build.setPageNo(i);
            pageInfoList.add(this.repairService
                    .findPageWithPageInfo(build));
        }
        model.addAttribute("address", list);
        model.addAttribute("repair_page_list",pageInfoList);
        return "process_apply";
    }

    @GetMapping("/invoice/{id}")
    @ApiDescribe(describe = "测试接口")
    public String invoice(Model model, @PathVariable("id") String id) {
        Process process = this.processService.selectById(id);
        List<Repair> repairList = new ArrayList<>();
        for(String repairId : process.getRepairs().split(",")) {
            repairList.add(this.repairService.selectById(repairId));
        }
        Schedule schedule = this.scheduleService.selectById(process.getScheduleId());
        User updater = this.userService.selectById(schedule.getUpdater());
        model.addAttribute("schedule", schedule);
        model.addAttribute("updater", updater);
        model.addAttribute("process", process);
        model.addAttribute("repairList", repairList);
        return "process_invoice";
    }

    @GetMapping("/done/{id}")
    public String done(@PathVariable("id") String id) {
        Process process = this.processService.selectById(id);
        process.setType(3);
        this.processService.updateByPrimaryKeySelective(process);
        return "redirect:/processs/invoice/" + id;
    }

    @GetMapping("/invoice/mail/{id}")
    @ApiDescribe(describe = "测试接口")
    public String invoiceMail(Model model, @PathVariable("id") String id) {
        Process process = this.processService.selectById(id);
        List<Repair> repairList = new ArrayList<>();
        for(String repairId : process.getRepairs().split(",")) {
            repairList.add(this.repairService.selectById(repairId));
        }
        Schedule schedule = this.scheduleService.selectById(process.getScheduleId());
        User updater = this.userService.selectById(schedule.getUpdater());
        model.addAttribute("process", process);
        model.addAttribute("repairList", repairList);
        model.addAttribute("schedule", schedule);
        model.addAttribute("updater", updater);
        return "mail_invoice";
    }

    @GetMapping("/invoice/mail/finish/{id}/{type}")
    @ApiDescribe(describe = "测试接口")
    public String invoiceMailFinish(Model model, @PathVariable("id") String id, @PathVariable("type") Integer type) {
        Process process = this.processService.selectById(id);
        List<Repair> repairList = new ArrayList<>();
        for(String repairId : process.getRepairs().split(",")) {
            repairList.add(this.repairService.selectById(repairId));
        }
        Schedule schedule = this.scheduleService.selectById(process.getScheduleId());
        User updater = this.userService.selectById(schedule.getUpdater());
        model.addAttribute("process", process);
        model.addAttribute("repairList", repairList);
        model.addAttribute("schedule", schedule);
        model.addAttribute("updater", updater);
        if(type == 1) {
            model.addAttribute("message", "成功，请按时前来维修");
        } else {
            model.addAttribute("message", "失败，如有需要请重新申请");
        }
        return "mail_finish";
    }

    @PostMapping("/apply")
    @ApiDescribe(describe = "测试接口")
    public String processApplyCheck(Process process) throws Exception {
        Schedule schedule = this.scheduleService.selectById(process.getScheduleId());
        process.setCreateDate(new Date());
        process.setDealDate(schedule.getStartDate());
        Integer applyNum = schedule.getApplyNum();
        String id;

        User user = this.userService.selectById(process.getUserId());
        if(user != null && user.getRole().equals("admin")) {
            process.setType(3);
            id = this.processService.insertSelective(process);
        } else {
            id = this.processService.insertSelective(process);
            this.scheduleService.updateByPrimaryKeySelective(Schedule.builder()
                    .id(process.getScheduleId())
                    .applyNum(applyNum + 1)
                    .build());
            // 发送邮件
            SendEmailHelper.PROXY_MAIL = "wufa995@qq.com";
            SendEmailHelper.PASSWORD = "sygpnrxmbmsigica";
            Document document = JsoupHelper.parseUrl("http://localhost:8080/ams/processs/invoice/mail/" + id);
            SendEmailHelper.email(process.getEmail(), document.toString());
        }
        return "redirect:/processs/invoice/" + id;
    }

    @ResponseBody
    @PostMapping("/apply/agree/{id}")
    @ApiDescribe(describe = "测试接口")
    @Transactional(rollbackFor = Exception.class)
    public Callable<GenericResponse> processApplyAgree(@PathVariable("id") String id) throws Exception {
        Process process = this.processService.selectById(id);
        this.processService.updateByPrimaryKeySelective(Process.builder()
                .id(id)
                .type(2)
                .build());
        // 发送邮件
        SendEmailHelper.PROXY_MAIL = "wufa995@qq.com";
        SendEmailHelper.PASSWORD = "sygpnrxmbmsigica";
        Document document = JsoupHelper.parseUrl("http://localhost:8080/ams/processs/invoice/mail/finish/" + id + "/1");
        SendEmailHelper.email(process.getEmail(), document.toString());
        return GenericResponse::success;
    }

    @ResponseBody
    @PostMapping("/apply/disagree/{id}")
    @Transactional(rollbackFor = Exception.class)
    @ApiDescribe(describe = "测试接口")
    public Callable<GenericResponse> processApplyDisagree(@PathVariable("id") String id) throws Exception {
        Process process = this.processService.selectById(id);
        this.processService.updateByPrimaryKeySelective(Process.builder()
                .id(id)
                .type(4)
                .build());
        // 发送邮件
        SendEmailHelper.PROXY_MAIL = "wufa995@qq.com";
        SendEmailHelper.PASSWORD = "sygpnrxmbmsigica";
        Document document = JsoupHelper.parseUrl("http://localhost:8080/ams/processs/invoice/mail/finish/" + id + "/2");
        SendEmailHelper.email(process.getEmail(), document.toString());
        return GenericResponse::success;
    }


}
