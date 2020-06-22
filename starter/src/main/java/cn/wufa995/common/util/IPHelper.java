/**
 * @author: wufa995[wufa995@qq.com]
 * @date: 2019年03月28日 11时35分
 */
package cn.wufa995.common.util;

import lombok.extern.slf4j.Slf4j;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Slf4j
public class IPHelper {

    /**
     * 获取当前主机host
     * @return IP地址
     */
    public static String host() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            log.error("获取当前主机host失败！", e);
            throw new RuntimeException(e);
        }
    }
}