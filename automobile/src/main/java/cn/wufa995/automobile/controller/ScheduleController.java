/**
 * @author: wufa995<wufa995@qq.com>
 * @date: 2019/04/25 10:41
 */
package cn.wufa995.automobile.controller;

import cn.wufa995.automobile.entity.Process;
import cn.wufa995.automobile.entity.User;
import cn.wufa995.automobile.service.UserService;
import cn.wufa995.automobile.vo.ScheduleVO;
import cn.wufa995.common.annotation.ApiDescribe;
import cn.wufa995.common.util.DateFormatHelper;
import cn.wufa995.common.vo.CommonVO;
import cn.wufa995.web.entity.PageInfo;
import cn.wufa995.web.response.GenericResponse;
import cn.wufa995.web.service.BaseService;
import cn.wufa995.web.controller.BaseController;
import cn.wufa995.automobile.entity.Schedule;
import cn.wufa995.automobile.service.ScheduleService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.Callable;

/**
 * @description:
 * @author: wufa995<wufa995@qq.com>
 * @date: 2019/04/25 10:41
 * @version: V1.0
 */
@Controller
@RequestMapping("/schedules")
public class ScheduleController extends BaseController<Schedule, String> {

    @Autowired
    private ScheduleService scheduleService;
    @Autowired
    private UserService userService;

    @Override
    public BaseService getService() {
        return scheduleService;
    }

    @GetMapping("/show/{queryDate}")
    public String show(Model model, @PathVariable("queryDate") String queryDate, User user) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = sdf.parse(queryDate);
        List<CommonVO> commonVOList = DateFormatHelper.getWeekListFromNow(date, 7);
        Schedule schedule = new Schedule();
        Map<String, String> likes = new HashMap<>();
        Map<String, String> orders = new HashMap<>();
        orders.put("startDate", "asc");
        schedule.setOrders(orders);
        schedule.setIsDelete(0);
        schedule.setUpdater(user.getId());

        List<ScheduleVO> list = new ArrayList<>();
        commonVOList.forEach(c -> {
            likes.put("startDate", c.getKey());
            likes.put("endDate", c.getKey());
            schedule.setLikes(likes);
            List<Schedule> scheduleList = this.scheduleService.findAllWithResult(schedule);
            scheduleList.forEach(s -> {
                User updater = this.userService.selectById(s.getUpdater());
                if(updater == null) {
                    return;
                }
                list.add(ScheduleVO.builder()
                        .startDate(s.getStartDate())
                        .endDate(s.getEndDate())
                        .updater(updater)
                        .build());
            });
        });
        model.addAttribute("queryDate", queryDate);
        model.addAttribute("scheduleVOList", list);
        model.addAttribute("id", user.getId());
        return "schedules_show";
    }

    @GetMapping("/query/apply/{id}/{type}")
    public String manager(Model model, @PathVariable("id") String id,@PathVariable("type") String type) {
        List<Process> processes = this.scheduleService.queryUpdaterApply(id, type);
        model.addAttribute("page", new PageInfo(processes));
        return "schedule_apply";
    }

    @GetMapping("/set")
    @ApiDescribe(describe = "测试接口")
    public String set(Model model, String queryDate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = sdf.parse(queryDate);
        List<ScheduleVO> scheduleVOList = new ArrayList<>();
        List<CommonVO> commonVOList = DateFormatHelper.getWeekListFromNow(date, 10);
        List<Date> startDateList = new ArrayList<>();
        List<Date> endDateList = new ArrayList<>();
        Schedule schedule = new Schedule();
        Map<String, String> likes = new HashMap<>();
        Map<String, String> orders = new HashMap<>();
        orders.put("startDate", "asc");
        schedule.setOrders(orders);
        schedule.setIsDelete(0);

        commonVOList.forEach(c -> {
            likes.put("startDate", c.getKey());
            likes.put("endDate", c.getKey());
            schedule.setLikes(likes);
            List<Schedule> scheduleList = this.scheduleService.findAllWithResult(schedule);
            if(scheduleList == null || scheduleList.size() < 10) {
                if(scheduleList == null) {
                    scheduleList = new ArrayList<>();
                }
                for(int i = scheduleList.size(); i < 10; i++) {
                    scheduleList.add(new Schedule());
                }
            }
            if(startDateList.size() == 0 || endDateList.size() == 0) {
                scheduleList.forEach(s -> {
                    startDateList.add(s.getStartDate());
                    endDateList.add(s.getEndDate());
                });
            }
            schedule.setLikes(likes);
            scheduleVOList.add(ScheduleVO.builder()
                    .commonVO(c)
                    .scheduleList(scheduleList)
                    .startDateList(startDateList)
                    .endDateList(endDateList)
                    .build());
        });

        model.addAttribute("queryDate", queryDate);
        model.addAttribute("scheduleVOList", scheduleVOList);
        model.addAttribute("updater", this.userService.findAllWithResult(User.builder()
                .isDelete(0)
                .role("updater")
                .build()));
        return "schedules_set";
    }

    @ResponseBody
    @ApiDescribe(describe = "测试接口")
    @PostMapping("/set/{queryDate}")
    public Callable<GenericResponse<String>> set(@PathVariable("queryDate") String queryDate, @RequestBody List<Schedule> scheduleList) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = sdf.parse(queryDate);
        List<CommonVO> commonVOList = DateFormatHelper.getWeekListFromNow(date, 10);

        Schedule schedule = new Schedule();
        Map<String, String> likes = new HashMap<>();
        Map<String, String> orders = new HashMap<>();
        orders.put("startDate", "asc");
        schedule.setOrders(orders);
        schedule.setIsDelete(0);
        commonVOList.forEach(c -> {
            likes.put("startDate", c.getKey());
            likes.put("endDate", c.getKey());
            schedule.setLikes(likes);
            List<Schedule> list = this.scheduleService.findAllWithResult(schedule);
            list.forEach(l -> this.scheduleService.deleteByPrimaryKey(l.getId()));
        });
        scheduleList.forEach(s -> this.scheduleService.insertSelective(s));
        return GenericResponse::success;
    }

    @ResponseBody
    @PostMapping("/query/{queryDate}")
    @ApiDescribe(describe = "测试POST接口")
    public Callable<GenericResponse<List<Schedule>>> queryUpdaterByDate(@PathVariable("queryDate") String queryDate) {
        return () -> GenericResponse.success(this.scheduleService.queryUpdaterByDate(queryDate));
    }
}
