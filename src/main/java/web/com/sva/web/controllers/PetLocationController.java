package com.sva.web.controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sva.dao.CommonDao;
import com.sva.dao.PetLocationDao;
import com.sva.model.PetLocationModel;

@Controller
@RequestMapping(value = "/petLocation")
public class PetLocationController {


	@Autowired
	private CommonDao comDao;
	
    @Autowired
    private PetLocationDao dao;
    
    @RequestMapping(value = "/saveData", method = {RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> saveData(
            @RequestParam("x[]") List<String> lisx,
            @RequestParam("y[]") List<String> lisy,
            @RequestParam("count") String count,
            @RequestParam("floorNo") String floorNo,
            @RequestParam("petType") String petType
    		)
    {
    	String deleteSql = "delete from petlocation where z ="+ floorNo+" and petTypes='"+petType+"'";
        Map<String, Object> modelMap = new HashMap<String, Object>(2);
        List<PetLocationModel> lisModel = new ArrayList<>();
        PetLocationModel pet = null;
        long Refresh = System.currentTimeMillis();
        for (int i = 0; i < lisx.size(); i++)
        {
        	pet = new PetLocationModel();
        	pet.setCount(Integer.parseInt(count));
        	pet.setZ(Integer.parseInt(floorNo));
        	pet.setX(Double.parseDouble(lisx.get(i)));
        	pet.setY(Double.parseDouble(lisy.get(i)));
        	pet.setPetTypes(petType);
        	pet.setActualPositionX(Double.parseDouble(lisx.get(i)));
        	pet.setActualPositionY(Double.parseDouble(lisy.get(i)));
        	lisModel.add(pet);
		}
        String sql = "insert into petlocation(x,y,count,z,petTypes,actualPositionX,actualPositionY,petRefreshTime) values";
        String sql1 = null;
        int size = lisModel.size();
        for (int i = 0; i < size; i++)
        {
            double x = lisModel.get(i).getX();
            double y =  lisModel.get(i).getY();
            int z =  lisModel.get(i).getZ();
            int counts =  lisModel.get(i).getCount();
            String petTypes = lisModel.get(i).getPetTypes();
            long petTime = Refresh;
            if (size==1) 
            {
				sql1 =  "("+x+","+y+","+counts+","+z+",'"+petTypes+"',"+x+","+y+","+petTime+");";
			}else
			{
				if (i==0) 
				{
					sql1 = "("+x+","+y+","+counts+","+z+",'"+petTypes+"',"+x+","+y+","+petTime+"),";
				}else
				{
					if (i+1<size) 
					{
						sql1 = sql1+"("+x+","+y+","+counts+","+z+",'"+petTypes+"',"+x+","+y+","+petTime+"),";
					}else
					{
						sql1 = sql1+"("+x+","+y+","+counts+","+z+",'"+petTypes+"',"+x+","+y+","+petTime+");";
					}
				}
				
			}
		}
        String insertSql = sql +sql1;
        int updateResult = 0;
        int insertResult = 0;
       try {
           updateResult = comDao.doUpdate(deleteSql);
	    	  if (size>0) 
	    	  {
	    	      insertResult = comDao.doUpdate(insertSql);
	    	  }
			
       		} catch (Exception e)
       		{
    	  
       		}
       		modelMap.put("updateResult", updateResult);
            modelMap.put("insertResult", insertResult);
       		return modelMap;
    }
    
    @RequestMapping(value = "/getDataByPet", method = {RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> getDataByPet(
            @RequestParam("zSel") String zSel,@RequestParam("petName") String petName)
    {
    	Map<String, Object> modelMap = new HashMap<String, Object>(2);
    	Collection<PetLocationModel> rest = null;
    	if (zSel!="") {
    		rest = dao.getDataByPet(zSel,petName);
		}else
		{
			modelMap.put("error", "Failed to get pet information");
		}
        modelMap.put("data", rest);
        return modelMap;
    }
}
