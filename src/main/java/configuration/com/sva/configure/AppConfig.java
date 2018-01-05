/**   
 * @Title: AppConfig.java 
 * @Package com.sva.configure 
 * @Description: springmvc配置类
 * @author labelCS   
 * @date 2018年1月4日 上午10:31:49 
 * @version V1.0   
 */
package com.sva.configure;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/** 
 * @ClassName: AppConfig 
 * @Description: springmvc配置类
 * @author labelCS 
 * @date 2018年1月4日 上午10:31:49 
 *  
 */
@Configuration
@ComponentScan(basePackages={"com.sva"})
public class AppConfig
{

}
