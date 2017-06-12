/**   
 * @Title: PrruServiceTest.java 
 * @Package com.sva.test.service 
 * @Description: 特征库采集service测试类
 * @author labelCS   
 * @date 2017年3月20日 下午4:10:07 
 * @version V1.0   
 */
package com.sva.test.service;

import static org.junit.Assert.*;
import java.math.BigDecimal;
import java.util.Map;
import javax.annotation.Resource;
import org.junit.Test;
import com.sva.service.PrruService;
import com.sva.web.models.PrruFeatureApiModel;

/** 
 * @ClassName: PrruServiceTest 
 * @Description: 特征库采集service测试类
 * @author labelCS 
 * @date 2017年3月20日 下午4:10:07 
 *  
 */
public class PrruServiceTest extends BasicServiceTest
{
    @Resource
    PrruService service;

    @Test
    public void testExcute1() {
      //测试数据
        PrruFeatureApiModel model = new PrruFeatureApiModel();
        model.setLength(15);
        model.setRadius(new BigDecimal(100000));
        model.setTimeInSeconds(60);
        model.setUserId("07070707");
        model.setX(new BigDecimal(10));
        model.setY(new BigDecimal(10));
        
          try {
              //调用service中的方法
              Map<String, Object> result = service.collectFeatureValue(model);
              assertEquals("the return value is ", "采集进行中，请稍后", result.get("data"));
        }catch(Exception e) {
            System.out.println();
        }



    }
}
