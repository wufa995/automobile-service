/**
 * @author: wufa995[wufa995@suixingpay.com]
 * @date: 2019年03月28日 11时35分
 */
package cn.wufa995.common.util;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.util.Assert;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Slf4j
public class IDHelper {
    private static SnowFlake snowFlake = new SnowFlake(0,0);

    private static Long nextLongValue() {

        return snowFlake.nextId();
    }

    public static String nextStringValue() {
        return Long.toString(nextLongValue());
    }
}