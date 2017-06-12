/**   
 * @Title: ViewMapperController.java 
 * @Package com.sva.web.controllers.mobile 
 * @Description: web app页面跳转控制器
 * @author labelCS   
 * @date 2016年11月10日 下午5:04:38 
 * @version V1.0   
 */
package com.sva.web.controllers.mobile;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/** 
 * @ClassName: ViewMapperController 
 * @Description: web app页面跳转控制器
 * @author labelCS 
 * @date 2016年11月10日 下午5:04:38 
 *  
 */
@Controller
@RequestMapping(value = "/mobile")
public class ViewMapperController {
    private static final Logger LOG = Logger.getLogger(ViewMapperController.class);

    @RequestMapping(value = "/showMap", method = {RequestMethod.GET})
    public String showSvaMng(Model model)
    {
        LOG.debug("visit map page");
        return "mobile/map";
    }

}
