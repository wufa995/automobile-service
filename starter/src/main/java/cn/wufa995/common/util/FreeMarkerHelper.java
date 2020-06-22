/**
 * @author: wufa995<wufa995@qq.com>
 * @date: 2017/12/5
 */
package cn.wufa995.common.util;

import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

@Slf4j
public class FreeMarkerHelper {

    public static String processTemplateIntoString(String templateName, Map<String, Object> model) {
        try {
            Configuration configuration = new Configuration(Configuration.VERSION_2_3_26);
            configuration.setClassForTemplateLoading(FreeMarkerHelper.class, "/templates");
            Template tmp = configuration.getTemplate(templateName + ".ftl");
            return FreeMarkerTemplateUtils.processTemplateIntoString(tmp, model);
        } catch (Exception e) {
            log.error("转换模版失败！templateName={}", templateName, e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取模版添加之后的文本
     *
     * @param templateTxt
     * @param map
     * @return
     */
    public static String getFreeMarkerText(String templateTxt, Map<String, Object> map) {
        String result = null;
        Configuration config = new Configuration(Configuration.VERSION_2_3_23);
        try {
            StringTemplateLoader e = new StringTemplateLoader();
            e.putTemplate("t", templateTxt);
            config.setTemplateLoader(e);
            config.setDefaultEncoding("UTF-8");
            Template template = config.getTemplate("t", "UTF-8");
            StringWriter out = new StringWriter();
            template.process(map, out);
            if (CheckParam.isEmpty(out.toString())) {
                log.info("模版已转成html！");
            }
            return out.toString();
        } catch (IOException iex) {
            log.error(iex.getMessage());
            throw new RuntimeException("获取freemark模版出错", iex);
        } catch (TemplateException ex) {
            throw new RuntimeException("freemark模版处理异常", ex);
        }
    }
}
