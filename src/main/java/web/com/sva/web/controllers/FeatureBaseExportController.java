/**   
 * @Title: FeatureBaseExportController.java 
 * @Package com.sva.web.controllers 
 * @Description: 特征库导出控制器
 * @author labelCS   
 * @date 2016年11月24日 上午10:00:24 
 * @version V1.0   
 */
package com.sva.web.controllers;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.sva.common.ConvertUtil;
import com.sva.dao.PrruSignalDao;
import com.sva.model.FeatureBaseExportModel;
import com.sva.model.PrruFeatureDetailModel;
import com.sva.model.PrruFeatureModel;
import com.sva.service.core.ExcelService;

/** 
 * @ClassName: FeatureBaseExportController 
 * @Description: 特征库导出控制器
 * @author labelCS 
 * @date 2016年11月24日 上午10:00:24 
 *  
 */
@Controller
@RequestMapping(value = "/featureBase")
public class FeatureBaseExportController {
    /**
     * @Fields log 输出日志
     */
    private static final Logger LOG = Logger.getLogger(FeatureBaseExportController.class);
    
    /** 
     * @Fields prruSignalDao : prruSignal数据库操作DAO
     */ 
    @Autowired
    private PrruSignalDao dao;
    
    @Autowired
    private PrruSignalDao prruSignalDao;
    
    /** 
     * @Title: exportExcel 
     * @Description: excel导出
     * @param request
     * @param response 
     */
    @RequestMapping(value = "/api/ExportExcel")
    @ResponseBody
    public void exportExcel(HttpServletResponse response, @RequestParam("placeId") String placeId){
        LOG.info("download excel");
        ExcelService<FeatureBaseExportModel> ex = new ExcelService<FeatureBaseExportModel>();
        // Excel标题
        String[] headers = {
                "id", 
                "mapId", 
                "x", 
                "y", 
                "floorid", 
                "checkValue", 
                "featureRadius", 
                "userId", 
                "gpp", 
                "featureValue", 
                "timestamp", 
                "formatDate" 
        };
        // 特征库数据
        List<FeatureBaseExportModel> dataset = dao.getFeatureBaseData(placeId);
        LOG.debug("data count"+dataset.size());
        
        try  
        {
            // 设置输出流
            response.reset();
            response.setContentType("application/vnd.ms-excel;charset=UTF-8");
            // 设定输出文件头
            response.setHeader("Content-disposition", "attachment; filename=featureBase"
                    + ConvertUtil.dateFormat(new Date(), "yyyyMMddHHmmss")
                    + ".xls");
            // 导出excel
            ex.exportExcel("featureBase", headers, dataset, response.getOutputStream(), null);
            
        } catch (FileNotFoundException e) {  
            LOG.error(e);
        } catch (IOException e) {  
            LOG.error(e);
        } catch (Exception e){
            LOG.error(e);
        }
    }
    
    /** 
     * @Title: exportTxt 
     * @Description: txt文件导出
     * @param request
     * @param response 
     */
    @RequestMapping(value = "/api/ExportTxt")
    @ResponseBody
    public void exportTxt(HttpServletResponse response, @RequestParam("placeId") String placeId){
        LOG.info("download txt");
        // 特征库数据
        List<FeatureBaseExportModel> dataset = dao.getFeatureBaseData(placeId); 
        
        // 设置输出流
        response.reset();
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        // 设定输出文件头
        response.setHeader("Content-disposition", "attachment; filename=featureBase"
                + ConvertUtil.dateFormat(new Date(), "yyyyMMddHHmmss")
                + ".txt");
        // 输出
        PrintWriter output;
        try {
            output = response.getWriter();
            for(int i = 0; i < dataset.size(); i ++){
                output.println("ADD FEATUREPOINT: " + dataset.get(i).toString());
            }
            output.close();
        } catch (IOException e) {
            LOG.error(e);
        }
    }
    
    /** 
     * @Title: getData 
     * @Description: 获取表格数据
     * @param request
     * @param response
     * @return 
     */
    @RequestMapping(value = "/api/getTableData")
    @ResponseBody
    public Map<String, Object> getData(@RequestParam("placeId") String placeId){
        LOG.debug("placeId:" + placeId);
        List<FeatureBaseExportModel> dataset = dao.getFeatureBaseData(placeId);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("data", dataset);
        return result;
    }
    
    /** 
     * @Title: getCurrentPrruWithRsrp
     * @Description: 获取当前的prru及对应的信号强度
     * @param userId
     * @return 
     */
    @RequestMapping(value = "/recover", method = {RequestMethod.GET})
    @ResponseBody
    public Map<String, Object> getCurrentPrruWithRsrp()
    {
        Map<String, Object> result = new HashMap<String, Object>();
        LOG.debug("recover started!");
        List<Map<String,String>> content = new ArrayList<Map<String,String>>();
        // 对读取Excel表格内容测试
        try{
            InputStream is = new FileInputStream("d:\\test.xls");
            ExcelService es = new ExcelService();
            content = es.readExcelContent(is);
        }catch(Exception e){
            result.put("error", e);
            return result;
        }
        Map<String,List<PrruFeatureDetailModel>> formated = new HashMap<String,List<PrruFeatureDetailModel>>();
        
        for(int i=0; i<content.size(); i++){
            if(formated.containsKey(content.get(i).get("id"))){
                List<PrruFeatureDetailModel> tmp = formated.get(content.get(i).get("id"));
                PrruFeatureDetailModel tempDetail = new PrruFeatureDetailModel();
                tempDetail.setFeatureId(Integer.parseInt(content.get(i).get("id")));
                tempDetail.setFeatureValue(new BigDecimal(content.get(i).get("featureValue")));
                tempDetail.setGpp(content.get(i).get("gpp"));
                tmp.add(tempDetail);
            }else{
                List<PrruFeatureDetailModel> tmp = new ArrayList<PrruFeatureDetailModel>();
                PrruFeatureDetailModel tempDetail = new PrruFeatureDetailModel();
                tempDetail.setFeatureId(Integer.parseInt(content.get(i).get("id")));
                tempDetail.setFeatureValue(new BigDecimal(content.get(i).get("featureValue")));
                tempDetail.setGpp(content.get(i).get("gpp"));
                tmp.add(tempDetail);
                
                formated.put(content.get(i).get("id"), tmp);
                
                // 特征数据入库
                PrruFeatureModel featureModel = new PrruFeatureModel();
                featureModel.setId(Integer.parseInt(content.get(i).get("id")));
                featureModel.setFeatureRadius(new BigDecimal(content.get(i).get("featureRadius")));
                featureModel.setX(content.get(i).get("x"));
                featureModel.setY(content.get(i).get("y"));
                featureModel.setFloorNo(content.get(i).get("floorid"));
                featureModel.setTimestamp(Long.parseLong(content.get(i).get("timestamp")));
                featureModel.setUserId(content.get(i).get("userId"));
                int id = prruSignalDao.savePrruFeature(featureModel);
            }
        }
        
        // 遍历特征值，生成bean
        Iterator<Entry<String,List<PrruFeatureDetailModel>>> it = formated.entrySet().iterator();  
        while(it.hasNext()){
            Entry<String,List<PrruFeatureDetailModel>> entity = it.next();
            List<PrruFeatureDetailModel> featureDetail = entity.getValue();
            prruSignalDao.savePrruFeatureDetail(featureDetail);
        }
        result.put("error", null);
        return result;
    }
}
