/**   
 * @Title: PathPlanController.java 
 * @Package com.sva.web.controllers 
 * @Description: 生成路径规划文件controller
 * @author labelCS   
 * @date 2016年11月4日 上午10:47:48 
 * @version V1.0   
 */
package com.sva.web.controllers;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.sva.service.XmlService;
import net.sf.json.JSONObject;

/** 
 * @ClassName: PathPlanController 
 * @Description: 生成路径规划文件controller
 * @author labelCS 
 * @date 2016年11月4日 上午10:47:48 
 *  
 */
@Controller
@RequestMapping(value = "/pathplan")
public class PathPlanController {
    /** 
     * @Fields service : xml生成逻辑
     */ 
    @Autowired
    private XmlService service;
    /** 
     * @Fields LOG : 日志处理句柄
     */ 
    private static final Logger LOG = Logger.getLogger(PathPlanController.class);
    
    /** 
     * @Title: createXml 
     * @Description: 处理生成xml的请求
     * @param map
     * @return 
     */
    @RequestMapping(value = "/api/createXml", method = {RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> createXml(HttpServletRequest request, @RequestBody Map<String,Object> map){
        LOG.debug("start create xml");
        // 从请求中抽取参数
        String fileName = (String) map.get("fileName");
        JSONObject data = JSONObject.fromObject(map.get("data"));
        JSONObject optParam = JSONObject.fromObject(map.get("optParam"));
        // xml文件保存路径
        String pathFileName = request.getSession().getServletContext().getRealPath(File.separator + "WEB-INF"
                + File.separator+"upload") + File.separator + "pathFile" + File.separator + fileName;
        // 调用service，生成xml
        int result = service.createSimplePathFile(pathFileName, data, optParam);
        
        // 生成返回值
        Map<String, Object> returnVal = new HashMap<String, Object>();
        returnVal.put("returnCode", result);
        returnVal.put("file", fileName);
        return returnVal;
    }
    
    /** 
     * @Title: createXml 
     * @Description: 处理生成xml的请求
     * @param map
     * @return 
     */
    @RequestMapping(value = "/api/getSimpleXmlData", method = {RequestMethod.GET})
    @ResponseBody
    public Map<String, Object> getSimpleXmlData(HttpServletRequest request,String fileName){
        LOG.debug("start read xml");
        // xml文件保存路径
        String pathFileName = request.getSession().getServletContext().getRealPath(File.separator + "WEB-INF"
                + File.separator+"upload") + File.separator + "pathFile" + File.separator + fileName;
        // 调用service，生成xml
        Map<String, Object> result = service.readSimplePathFile(pathFileName);
        return result;
    }

}
