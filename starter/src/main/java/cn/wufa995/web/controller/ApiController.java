package cn.wufa995.web.controller;

import cn.wufa995.common.annotation.ApiDescribe;
import cn.wufa995.common.annotation.ApiDescribeProcessor;
import cn.wufa995.common.enums.ProjectNameEnum;
import cn.wufa995.common.util.ApplicationContextHelper;
import cn.wufa995.common.util.CheckParam;
import cn.wufa995.common.util.ExcelHelper;
import cn.wufa995.common.util.IDHelper;
import cn.wufa995.web.entity.ApiInfo;
import cn.wufa995.web.entity.ExcelSheetData;
import cn.wufa995.web.repository.ApiInfoRepository;
import io.swagger.models.auth.In;
import javassist.*;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.LocalVariableAttribute;
import javassist.bytecode.MethodInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.springframework.web.context.support.WebApplicationContextUtils.getWebApplicationContext;

@Controller
public class ApiController {

    @Autowired
    private ApiInfoRepository apiInfoRepository;

    @GetMapping("/excel")
    public void excel(HttpServletResponse response) {
        ExcelHelper excel = getExcel();
        excel.exportToResponse(response);
    }

    public ExcelHelper getExcel() {
        // 设置表格属性
        SimpleDateFormat sdf = new SimpleDateFormat("HHmmss");
        ExcelHelper excel = new ExcelHelper();
        String excelFileName = "FILENAME-NOW.xls";
        excelFileName = excelFileName.replaceAll("FILENAME", "测试");
        excelFileName = excelFileName.replaceAll("NOW", sdf.format(new Date()));
        excel.setFileName(excelFileName);
        ExcelSheetData sheet;
        // 设置工作簿的格式和数据
        sheet = new ExcelSheetData();
        sheet.addHeader("id", "主键");
        sheet.addHeader("projectName", "项目名称");
        List<ApiInfo> list = new ArrayList<>();
        list.add(ApiInfo.builder()
                .id("aaa")
                .projectName("bbb")
                .build());

        List<Map<String, Object>> mapList = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        map.put("id", "9999");
        map.put("projectName", "8888");
        Map<String, Object> map2 = new HashMap<>();
        map2.put("id", 6666);
        map2.put("projectName", 6666);
        mapList.add(map);
        mapList.add(map2);
        mapList.add(map);
        mapList.add(map);
        sheet.setData(mapList);
        sheet.setTitle("测试");
        sheet.setIsTrim(true);
        sheet.setIsMap(true);
        excel.addSheet(sheet);
        return excel;
    }

    @GetMapping("/api")
    public String api(Model model, HttpServletRequest request, String methodId, Double scroll) {
        List<ApiInfo> urlMapping = this.getUrlMapping(request);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("projectName", ProjectNameEnum.ALL.getProjectName());
        resultMap.put("data", urlMapping);
        resultMap.put("count", urlMapping.size());
        resultMap.put("scroll", scroll);
        resultMap.put("detail", CheckParam.isEmpty(methodId) ? ApiInfo.builder()
                .id("aaaaaaaaaaaaaa")
                .url("aaaaaaa")
                .params("bbbbbbbbbb")
                .result("ccccccccc")
                .method("dddddddddd")
                .build() : this.apiInfoRepository.selectById(methodId));
        model.addAttribute("api", resultMap);
        return "api";
    }

    @PostMapping("/save")
    @ResponseBody
    public Integer save(@RequestBody Map<String, String> map) {
        String id = map.get("id");
        ApiInfo apiInfo = this.apiInfoRepository.selectById(id);
        this.apiInfoRepository.deleteById(id);
        apiInfo.setUrl(map.get("url"));
        apiInfo.setParams(map.get("params"));
        apiInfo.setResult(map.get("result"));
        return this.apiInfoRepository.insert(apiInfo);
    }

    @GetMapping("/finish")
    public String finish(Model model, HttpServletRequest request, String id, Integer isFinish, Double scroll) {
        this.apiInfoRepository.updateIsFinishById(id,isFinish);
        return this.api(model, request, id, scroll);
    }

    @GetMapping("/test")
    @ResponseBody
    @ApiDescribe(describe = "测试")
    public Map<String, String> test(String id, String name) {
        Map<String, String> map = new HashMap<>();
        map.put("id", id);
        map.put("name", name);
        return map;
    }

    @ResponseBody
    @GetMapping("/getUrlMapping")
    @ApiDescribe(describe = "测试接口")
    public List<ApiInfo> getUrlMapping(HttpServletRequest request) {

        Map<String, Object> beanWithAnnotation = ApplicationContextHelper.getBeanWithAnnotation(ApiDescribe.class);

        Map<String, String> apiDescribeMap = ApiDescribeProcessor.API_DESCRIBE_MAP;

        WebApplicationContext wc = getWebApplicationContext(request.getSession().getServletContext());
        RequestMappingHandlerMapping rmhp = wc.getBean(RequestMappingHandlerMapping.class);
        Map<RequestMappingInfo, HandlerMethod> map = rmhp.getHandlerMethods();
        List<ApiInfo> resultList = new ArrayList<>();
        for (Iterator<RequestMappingInfo> iterator = map.keySet().iterator(); iterator
                .hasNext();) {
            RequestMappingInfo info = iterator.next();
            System.out.print(info.getConsumesCondition());
            System.out.print(info.getCustomCondition());
            System.out.print(info.getHeadersCondition());
            System.out.print(info.getMethodsCondition());
            System.out.print(info.getParamsCondition());
            System.out.print(info.getPatternsCondition());
            System.out.print(info.getProducesCondition());

            System.out.print("===");

            HandlerMethod method = map.get(info);
            String name = method.getMethod().getName();
            String s = method.getMethod().getDeclaringClass().toString();
            //System.out.println("【bean=" + substring + "】");
            String substring = s.substring(6);
            String methodParams = this.getMethodParams(substring, name);

            System.out.print(methodParams + "--");
            String[] params = method.getMethodAnnotation(RequestMapping.class).params();
            System.out.print(Arrays.toString(Arrays.asList(params).toArray()));

            String describe = apiDescribeMap.get(substring + "." + name);
            if(describe == null) {
                continue;
            }
            System.out.println("[" + describe +"]");
            System.out.println();
            String s1 = info.getMethodsCondition().toString();
            String s2 = info.getPatternsCondition().toString();
            String beseUrl = "http://localhost:8080/ams";
            ApiInfo apiInfo = this.apiInfoRepository.findOneByClassReferenceAndMethodName(substring, name);
            String methodId = apiInfo == null ? IDHelper.nextStringValue() : apiInfo.getId();
            if(apiInfo == null) {
                this.apiInfoRepository.insert(ApiInfo.builder()
                        .id(methodId)
                        .url(beseUrl.concat(s2.substring(1, s2.length() - 1)))
                        .method(s1.substring(1, s1.length() - 1))
                        .classReference(substring)
                        .methodName(name)
                        .params(methodParams)
                        .result("无")
                        .describe("无")
                        .build());
            }
            resultList.add(ApiInfo.builder()
                    .id(methodId)
                    .method(s1.substring(1, s1.length() - 1))
                    .beasUrl(beseUrl)
                    .url(s2.substring(1, s2.length() - 1))
                    .functionName(name)
                    .paramsNames(methodParams)
                    .params("")
                    .projectName("测试")
                    .describe(describe)
                    .classReference(substring)
                    .methodName(name)
                    .isFinish(apiInfo == null ? 0 : apiInfo.getIsFinish())
                    .build());
        }
        return resultList;
    }

    /**
     * @param className 类名
     * @param methodName 方法名
     * @return 该方法的声明部分
     * @author Elwin ZHANG
     * 创建时间：2017年3月8日 上午11:47:16
     * 功能：返回一个方法的声明部分，包括参数类型和参数名
     */
    private String getMethodParams(String className,String methodName){
        String result="";
        try{
            ClassPool pool=ClassPool.getDefault();
            ClassClassPath classPath = new ClassClassPath(this.getClass());
            pool.insertClassPath(classPath);
            CtMethod cm =pool.getMethod(className, methodName);
            // 使用javaassist的反射方法获取方法的参数名
            MethodInfo methodInfo = cm.getMethodInfo();
            CodeAttribute codeAttribute = methodInfo.getCodeAttribute();
            LocalVariableAttribute attr = (LocalVariableAttribute) codeAttribute.getAttribute(LocalVariableAttribute.tag);
            result=cm.getName() + "(";
            if (attr == null) {
                return result + ")";
            }
            CtClass[] pTypes=cm.getParameterTypes();
            String[] paramNames = new String[pTypes.length];
            int pos = Modifier.isStatic(cm.getModifiers()) ? 0 : 1;
            for (int i = 0; i < paramNames.length; i++) {
                if(!pTypes[i].getSimpleName().startsWith("HttpServletRe")){
                    result += pTypes[i].getSimpleName();
                    paramNames[i] = attr.variableName(i + pos);
                    result += " " + paramNames[i]+",";
                }
            }
            if(result.endsWith(",")){
                result=result.substring(0, result.length()-1);
            }
            result+=")";
        }catch(Exception e){
            e.printStackTrace();
            //this.getMethodParams("cn.wufa995.web.controller.BaseController", methodName);
        }
        return result;
    }


}
