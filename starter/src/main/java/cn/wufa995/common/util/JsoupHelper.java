/**
 * @author: wufa995[wufa995@qq.com]
 * @date: 2019年05月01日 14时27分
 */
package cn.wufa995.common.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class JsoupHelper {
    public static Document parseUrl(String url) {
        Document doc = null;
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return doc;
    }
}