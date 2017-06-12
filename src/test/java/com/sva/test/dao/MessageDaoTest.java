/**    
 * @Title:  MessageDaoTest.java   
 * @Package com.sva.test.dao   
 * @Description:    MessageDao测试类   
 * @author: LabelCS    
 * @date:   2016年9月3日 下午9:20:39   
 * @version V1.0     
 */  
package com.sva.test.dao;

import java.math.BigDecimal;
import java.util.List;
import javax.annotation.Resource;
import org.junit.Test;
import com.sva.dao.MessageDao;
import com.sva.model.AreaModel;
import com.sva.model.LocationModel;
import com.sva.model.MapsModel;
import com.sva.model.MessageModel;
import com.sva.model.StoreModel;
import org.junit.Assert;

/**   
 * @ClassName:  MessageDaoTest   
 * @Description: MessageDao测试类  
 * @author: LabelCS  
 * @date:   2016年9月3日 下午9:20:39   
 *      
 */
public class MessageDaoTest extends BasicDaoTest {
    
    @Resource
    MessageDao messageDao;

    @Test
    public void doqueryTest(){
        List<MessageModel> result = messageDao.doquery();
        Assert.assertEquals("结果为0",0, result.size());
    }
    
    @Test
    public void doqueryByStoreidTest(){
        int storeId = 1;
        List<MessageModel> result = messageDao.doqueryByStoreid(storeId);
        Assert.assertEquals("结果为0",0, result.size());
    }

    @Test
    public void queryByLocationTest(){
        LocationModel loc = new LocationModel();
        loc.setX(new BigDecimal(100));
        loc.setY(new BigDecimal(100));
        loc.setZ(new BigDecimal(10001));
        List<MessageModel> result = messageDao.queryByLocation(loc);
        Assert.assertEquals("结果为0",0, result.size());
    }
    
    @Test
    public void queryByLocation1Test(){
        LocationModel loc = new LocationModel();
        loc.setX(new BigDecimal(100));
        loc.setY(new BigDecimal(100));
        loc.setZ(new BigDecimal(10001));
        List<MessageModel> result = messageDao.queryByLocation1(loc);
        Assert.assertEquals("结果为0",0, result.size());
    }
    
    @Test
    public void queryByLocation3Test(){
        LocationModel loc = new LocationModel();
        loc.setX(new BigDecimal(100));
        loc.setY(new BigDecimal(100));
        loc.setZ(new BigDecimal(10001));
        List<MessageModel> result = messageDao.queryByLocation3(loc);
        Assert.assertEquals("结果为0",0, result.size());
    }
    
    @Test
    public void saveMsgInfoTest(){
        MessageModel mm = new MessageModel();
        mm.setMessage("aa");
        mm.setMoviePath("f:/");
        mm.setPictruePath("d:/");
        mm.setxSpot(new BigDecimal(11));
        mm.setX1Spot(new BigDecimal(11));
        mm.setySpot(new BigDecimal(11));
        mm.setY1Spot(new BigDecimal(11));
        mm.setRangeSpot(new BigDecimal(11));
        mm.setIsEnable("true");
        mm.setTicketPath("d:/");
        mm.setTimeInterval(100);
        MapsModel map = new MapsModel();
        map.setFloorNo(new BigDecimal(1));
        mm.setMaps(map);
        StoreModel store = new StoreModel();
        store.setId(1);
        mm.setStore(store);
        AreaModel area = new AreaModel();
        area.setId("1");
        area.setAreaName("temp");
        mm.setArea(area);
        int result = messageDao.saveMsgInfo(mm);
        Assert.assertEquals("结果为1",1, result);
    }
    
    @Test
    public void deleteMessageTest(){
        String xSpot = "11"; 
        String ySpot = "12";
        String floorNo = "10011";
        int result = messageDao.deleteMessage(xSpot,ySpot,floorNo);
        Assert.assertEquals("结果为0",0, result);
    }
    
    @Test
    public void updateMsgInfoTest(){
        MessageModel mm = new MessageModel();
        mm.setId("1");
        mm.setMessage("aa");
        mm.setMoviePath("f:/");
        mm.setPictruePath("d:/");
        mm.setxSpot(new BigDecimal(11));
        mm.setX1Spot(new BigDecimal(11));
        mm.setySpot(new BigDecimal(11));
        mm.setY1Spot(new BigDecimal(11));
        mm.setRangeSpot(new BigDecimal(11));
        mm.setIsEnable("true");
        mm.setTicketPath("d:/");
        mm.setTimeInterval(100);
        MapsModel map = new MapsModel();
        map.setFloorNo(new BigDecimal(1));
        mm.setMaps(map);
        StoreModel store = new StoreModel();
        store.setId(1);
        mm.setStore(store);
        AreaModel area = new AreaModel();
        area.setId("1");
        area.setAreaName("temp");
        mm.setArea(area);
        int result = messageDao.updateMsgInfo(mm);
        Assert.assertEquals("结果为0",0, result);
    }
    
    @Test
    public void getAllMessageDataTest(){
        List<MessageModel> result = messageDao.getAllMessageData();
        Assert.assertEquals("结果为0",0, result.size());
    }
    
    @Test
    public void getAllMessageDataByAreaIdTest(){
        String areaId = "1";
        List<MessageModel> result = messageDao.getAllMessageDataByAreaId(areaId);
        Assert.assertEquals("结果为0",0, result.size());
    }
    
    @Test
    public void getAllMessageDataByAreaIdNewTest(){
        String areaId = "1";
        List<MessageModel> result = messageDao.getAllMessageDataByAreaIdNew(areaId);
        Assert.assertEquals("结果为0", 0, result.size());
    }
    
    @Test
    public void getAreaIdByMessageTest(){
        String messageId = "1";
        String result = messageDao.getAreaIdByMessage(messageId);
        Assert.assertNull("结果为null",result);
    }
    
    @Test
    public void getTiketPathByAreaIdTest(){
        String messageId = "1";
        List<String> result = messageDao.getTiketPathByAreaId(messageId);
        Assert.assertEquals("结果为0", 0, result.size());
    }
    
    @Test
    public void getMsgIdTest(){
        String messageId = "1";
        List<String> result = messageDao.getMsgId(messageId);
        Assert.assertEquals("结果为0", 0, result.size());
    }
}
