/*
 * 文件名：XmlParser.java
 * 版权：Copyright 2013-2013 Huawei Tech. Co. Ltd. All Rights Reserved. 
 * 描述： XmlParser.java
 * 修改人：dWX182800
 * 修改时间：2013-11-4
 * 修改内容：新增
 */
package com.sva.common;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.springframework.util.StringUtils;

import com.sva.model.PrruModel;

/**
 * 解析xml
 * <p>
 * 详细描述
 * <p>
 * 示例代码
 * 
 * <pre>
 * </pre>
 * 
 * @author dWX182800
 * @version iSoftStone VDS V100R001C04 2013-11-4
 * @since iSoftStone VDS V100R001C04
 */
public class XmlParser
{
    /**
     * 调测日志记录器。
     */
    private static final Logger LOG = Logger.getLogger(XmlParser.class);

    private Document xmldoc = null;

    private Element root = null;

    public void init(File file)
    {
        SAXReader reader = new SAXReader();
        try
        {
            xmldoc = reader.read(file);
        }
        catch (Exception e)
        {
            LOG.error(e);
        }

        root = xmldoc.getRootElement();
    }

    /**
     * 获取属性集合
     * 
     * @param xPath
     * @param values
     * @return
     */
    public List<PrruModel> getAttrVal(String flooNo, int plceId, String xPath,
            String... values)
    {
        @SuppressWarnings("unchecked")
        List<Node> node = root.selectNodes(xPath);

        if (values == null || values.length == 0 || node == null)
        {
            return Collections.emptyList();
        }
        List<PrruModel> results = new ArrayList<PrruModel>(10);
        String result;
        PrruModel pm;
        int si = node.size();
        Map<String,String> mapModel;
        
        for (int i = 0; i < si; i++)
        {
            mapModel = new HashMap<String,String>();
            mapModel.put("id", null);
            mapModel.put("name", null);
            mapModel.put("x", null);
            mapModel.put("y", null);
            mapModel.put("neCode", null);
            pm = new PrruModel();
            pm.setPlaceId(plceId);
            for (String value : values)
            {
                result = node.get(i).valueOf('@' + value);
                if (StringUtils.isEmpty(result))
                {
                    LOG.error(" not find data:" + value);
                    continue;
                }
                mapModel.put(value, result);
            }
            
            pm.setNeId(mapModel.get("id"));
            pm.setNeCode(mapModel.get("neCode"));
            pm.setNeName(mapModel.get("name"));
            pm.setX(zhuanhuan(mapModel.get("x")));
            pm.setY(zhuanhuan(mapModel.get("y")));                
            pm.setFloorNo(flooNo);
            results.add(pm);
        }
        return results;
    }

    public Node getNode(String xPath)
    {
        return root.selectSingleNode(xPath);
    }

    @SuppressWarnings("unchecked")
    public List<Node> getNodes(String xPath)
    {
        return root.selectNodes(xPath);
    }
    
    public String zhuanhuan(String str)
    {
        String reslut = null;
        if (str==null) {
           return null; 
        }else
        {
            try {
                int temp = (Integer.parseInt(str))/10;
                reslut = String.valueOf(temp);
            } catch (Exception e) {
               LOG.error("prru xml error:"+e.getMessage());
            }
            return reslut;
        }
    }
}
