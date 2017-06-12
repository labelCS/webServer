package com.sva.web.controllers;

import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sva.dao.PetAttributesDao;
import com.sva.model.PetAttributesModel;

@Controller
@RequestMapping(value = "/petAttributes")
public class PetAttributesController {

    
    @Autowired
    private PetAttributesDao dao;
    
    @RequestMapping(value = "/saveData", method = {RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> saveData(
            @RequestParam("petName") String petName,
            @RequestParam("viewRange") String viewRange,
            @RequestParam("captureRange") String captureRange,
            @RequestParam("probability") String probability,
            @RequestParam("count") String count,
            @RequestParam("id") String id)
    {
        
        PetAttributesModel pet = new PetAttributesModel();
        pet.setPetName(petName);
        pet.setViewRange(Double.parseDouble(viewRange));
        pet.setCaptureRange(Double.parseDouble(captureRange));
        pet.setProbability(Double.parseDouble(probability));
        pet.setCount(Integer.parseInt(count));
        Map<String, Object> modelMap = new HashMap<String, Object>(2);
        int result = 0;
        if (StringUtils.isEmpty(id)) {
            result = dao.saveData(pet);
        }else
        {
            pet.setId(Integer.parseInt(id));
            result =  dao.updateData(pet);
        }
        long petTime = System.currentTimeMillis();
        dao.updatePetTime(petName,petTime);
        modelMap.put("data", result);
        return modelMap;
    }
    

    @RequestMapping(value = "/api/getTableData", method = {RequestMethod.GET})
    @ResponseBody
    public Map<String, Object> getTableData(HttpServletRequest request, Model model) 
    {

        Map<String, Object> modelMap = new HashMap<String, Object>(2);
        Collection<PetAttributesModel> pet = dao.getAllData();

        modelMap.put("data", pet);

        return modelMap;
    }
    
    
    @RequestMapping(value = "/deleteData", method = {RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> deleteData(
            @RequestParam("id") String id)
    {
        Map<String, Object> modelMap = new HashMap<String, Object>(2);
        int result = dao.deleteData(id);
        modelMap.put("data", result);
        return modelMap;
    }
    

}
