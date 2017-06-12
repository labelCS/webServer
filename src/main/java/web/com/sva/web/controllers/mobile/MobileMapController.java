/**   
 * @Title: MapController.java 
 * @Package com.sva.web.controllers.mobile 
 * @Description: 地图显示处理逻辑
 * @author labelCS   
 * @date 2016年11月10日 下午6:57:56 
 * @version V1.0   
 */
package com.sva.web.controllers.mobile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sva.dao.StoreDao;
import com.sva.model.StoreModel;
import com.sva.web.controllers.StoreController;

/** 
 * @ClassName: MapController 
 * @Description: 地图显示处理逻辑
 * @author labelCS 
 * @date 2016年11月10日 下午6:57:56 
 *  
 */
@Controller
@RequestMapping(value = "/mobile/store")
public class MobileMapController {
    /** 
     * @Fields dao : 数据库操作句柄
     */ 
    @Autowired
    private StoreDao dao;
    
    /** 
     * @Fields LOG : 日志处理模块
     */ 
    private static final Logger LOG = Logger.getLogger(StoreController.class);
    
    /** 
     * @Title: getTableData 
     * @Description: 获取所有商场数据
     * @return 
     */
    @RequestMapping(value = "/api/getData", method = {RequestMethod.GET})
    @ResponseBody
    public Map<String, Object> getTableData()
    {
        LOG.info("StoreController:getTableData");
        Map<String, Object> modelMap = new HashMap<String, Object>(2);
        // 查询所有商场
        List<StoreModel> resultList = dao.doStores();
        
        modelMap.put("error", null);
        modelMap.put("data", resultList);

        return modelMap;
    }
}
