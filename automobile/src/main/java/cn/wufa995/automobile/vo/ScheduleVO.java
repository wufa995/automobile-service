/**
 * All rights Reserved, Designed By Suixingpay.
 *
 * @author: qinbo[qin_bo@suixingpay.com]
 * @date: 2019年04月28日 09时59分
 * @Copyright 2018 Suixingpay. All rights reserved.
 * 注意：本内容仅限于随行付支付有限公司内部传阅，禁止外泄以及用于其他的商业用途。
 */
package cn.wufa995.automobile.vo;

import cn.wufa995.automobile.entity.Schedule;
import cn.wufa995.automobile.entity.User;
import cn.wufa995.common.vo.CommonVO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ScheduleVO {

    private CommonVO commonVO;

    private List<Schedule> scheduleList;

    private List<Date> startDateList;

    private List<Date> endDateList;

    /** 开始时间 **/
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date startDate;
    /** 结束时间 **/
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date endDate;
    /** 修理人 **/
    private String updater_id;
    /** 修理师 **/
    private User updater;
}