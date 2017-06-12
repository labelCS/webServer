package com.sva.test.dao;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;

import com.sva.dao.TicketDao;
import com.sva.model.MessageModel;
import com.sva.model.TicketModel;

public class TicketDaoTest extends BasicDaoTest{

   @Resource
   TicketDao dao;
   
   @Test
   public void getAllTicketTest(){
       List<TicketModel> lis = dao.getAllTicket("999");
       Assert.assertEquals("reslut 0", 0, lis.size());
   }
   
   @Test
   public void getAllTicketByIdTest(){
       List<TicketModel> lis = dao.getAllTicketById(999, "999");
       Assert.assertEquals("reslut 0", 0, lis.size());
   }
   
   @Test
   public void saveTicketTest(){
       TicketModel ti = new TicketModel();
       MessageModel ms = new MessageModel();
       ms.setId("999");
       ti.setChances("11");
       ti.setMsg(ms);
       ti.setTicketPath("ticke.path");
       int reslut = dao.saveTicket(ti);
       Assert.assertEquals("reslut 0",1,reslut);
   }
   
   @Test
   public void updataTicketTest(){
       TicketModel ti = new TicketModel();
       MessageModel ms = new MessageModel();
       ms.setId("1000");
       ti.setChances("11");
       ti.setMsg(ms);
       ti.setTicketPath("ticke.path");
       ti.setId("888");
       int reslut = dao.updataTicket(ti);
       Assert.assertEquals("reslut 0",0,reslut);
   }
}
