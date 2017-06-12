/**   
 * @Title: XmlService.java 
 * @Package com.sva.service 
 * @Description: 负责xml的生成与读取操作
 * @author labelCS   
 * @date 2016年11月3日 下午4:53:00 
 * @version V1.0   
 */
package com.sva.service;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.springframework.stereotype.Service;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/** 
 * @ClassName: XmlService 
 * @Description: 负责xml的生成与读取操作
 * @author labelCS 
 * @date 2016年11月3日 下午4:53:00 
 *  
 */
@Service
public class XmlService {
    /**
     * @Fields log ： 输出日志
     */
    private static final Logger LOG = Logger.getLogger(XmlService.class);
    
    /**   
     * @Title: createSimplePathFile   
     * @Description: 生成简化版的xml
     * @param fileName
     * @param jsonObject
     * @param optParam
     * @return：int       
     * @throws   
     */ 
    public int createSimplePathFile(String fileName, JSONObject jsonObject, JSONObject optParam){
        LOG.debug("待生成的文件名称："+fileName);
        // 创建document
        Element root = DocumentHelper.createElement("root");  
        Document document = DocumentHelper.createDocument(root);  
        
        // 写入参数
        Element elInit = root.addElement("initialize");
        elInit.addAttribute("widthPx", jsonObject.getString("width"))
            .addAttribute("widthGL", optParam.getString("width3D"))
            .addAttribute("heightGL", optParam.getString("height3D"))
            .addAttribute("left_upGL_x", optParam.getString("upperLeftX3D"))
            .addAttribute("left_upGL_y", optParam.getString("upperLeftY3D"))
            .addAttribute("widthMi", jsonObject.getString("widthReal"));
        
        // 组装数据
        JSONArray pointArray = (JSONArray) jsonObject.get("point");
        JSONArray dataArray = (JSONArray) jsonObject.get("data");
        Map<Integer, List<String>> temp = new HashMap<Integer, List<String>>();
        // 遍历关系数组，组成以点id为key，对应另一个点的list为value的map
        for(int i = 0; i<dataArray.size(); i++){
            JSONArray lineArray = dataArray.getJSONArray(i);
            Integer from = lineArray.getInt(0);
            Integer to = lineArray.getInt(1);
            // from点的对应点
            if(temp.get(from) != null){
                temp.get(from).add(to.toString());
            }else{
                temp.put(from, new ArrayList<String>());
                temp.get(from).add(to.toString());
            }
            // to点的对应点
            if(temp.get(to) != null){
                temp.get(to).add(from.toString());
            }else{
                temp.put(to, new ArrayList<String>());
                temp.get(to).add(from.toString());
            }
        }
        
        // 遍历map，写入关系element
        for(int i = 0; i<pointArray.size(); i++){
            List<String> values = temp.get(i);
            Element el = root.addElement("line");
            el.addAttribute("y", String.valueOf(i)).addText(arrayToString(values));  
        }
        
        // 遍历点的数组，写入点element
        for(int i = 0; i<pointArray.size(); i++){
            JSONObject p = (JSONObject) pointArray.get(i);
            String x = p.getString("x");
            String y = p.getString("y");
            Element el = root.addElement("points");
            el.addAttribute("x", x).addText(y);  
        }
        
        return createXml(document, fileName);
    }
    
    /**   
     * @Title: readSimplePathFile   
     * @Description: 从简化版的xml中读取数据
     * @param fileName
     * @return：Map<String,Object>       
     * @throws   
     */ 
    public Map<String, Object> readSimplePathFile(String fileName){
        Map<String, Object> result = new HashMap<String,Object>();
        Map<String, Object> data = new HashMap<String,Object>();
        
        // 获取根节点
        Element root = getRootElement(fileName);
        // 读取异常，返回
        if(root == null){
            result.put("error", "read xml failed,Check if file existed!");
            return result;
        }
        // 参数节点
        Element optParam = root.element("initialize");
        String width3D = optParam.attribute("widthGL").getText();
        String height3D = optParam.attribute("heightGL").getText();
        String upperLeftX3D = optParam.attribute("left_upGL_x").getText();
        String upperLeftY3D = optParam.attribute("left_upGL_y").getText();
        data.put("width3D", width3D);
        data.put("height3D", height3D);
        data.put("upperLeftX3D", upperLeftX3D);
        data.put("upperLeftY3D", upperLeftY3D);
        
        // 点节点
        @SuppressWarnings("unchecked")
        List<Element> points = root.elements("points");
        List<Map<String, Object>> pointData = new ArrayList<Map<String, Object>>();
        int i = 0;
        for(Iterator<Element> it = points.iterator(); it.hasNext(); i++){
            Element point = (Element) it.next();
            Map<String, Object> temp = new HashMap<String, Object>();
            temp.put("id", i);
            temp.put("x", point.attribute("x").getText());
            temp.put("y", point.getText());
            pointData.add(temp);
        }
        
        // 线节点
        @SuppressWarnings("unchecked")
        List<Element> nodes = root.elements("line");
        // 去重判断用
        Map<String, Boolean> mapper = new HashMap<String, Boolean>();
        // 线
        List<List<String>> lineData = new ArrayList<List<String>>();
        for (Iterator<Element> it = nodes.iterator(); it.hasNext();) {
            Element line = (Element) it.next();
            String fromId = line.attribute("y").getText();
            String[] targets = line.getText().split(",");
            for(int j=0; j<targets.length; j++){
                List<String> oneLine = new ArrayList<String>();
                String tmp = targets[j];
                // 如果该点为未注册点，可以添加；否则不添加
                if(!mapper.containsKey(tmp)){
                    oneLine.add(fromId);
                    oneLine.add(tmp);
                    lineData.add(oneLine);
                }
            }
            // fromId已经统计结束，在mapper上注册
            mapper.put(fromId, true);
        }
        
        data.put("point", pointData);
        data.put("data", lineData);
        result.put("data", data);
        return result;
    }
    
    /** 
     * @Title: createXml 
     * @Description: 根据doc生成xml文档
     * @param doc
     * @param fileName
     * @return 
     */
    private int createXml(Document doc, String fileName){
        int returnValue = 0;
        try{  
            // 设置输出的编码UTF-8
            OutputFormat format = OutputFormat.createPrettyPrint(); 
            format.setEncoding("utf-8");
            // 将document中的内容写入文件中
            File targetFile = new File(fileName);
            // 如果父目录不存在，则创建目录
            if (!targetFile.getParentFile().exists()) {  
                targetFile.getParentFile().mkdirs(); 
            }
            // 如果文件不存在，则生成新文件
//            if (!targetFile.exists())
//            {
//                targetFile.createNewFile();
//            }
            XMLWriter writer = new XMLWriter(new FileOutputStream(targetFile),format);
            writer.write(doc);             
            writer.close();             
            // 执行成功,需返回1
            returnValue = 1;  
        }catch(Exception e){  
            LOG.error(e);
        } 
        return returnValue;
    }
    
    /**   
     * @Title: getRootElement   
     * @Description: 读取xml文件，获取跟节点element
     * @param fileName
     * @return：Element       
     * @throws   
     */ 
    private Element getRootElement(String fileName){
        Element root = null;
        try
        {
            // 获取document
            SAXReader reader = new SAXReader();
            Document document;
            document = reader.read(new File(fileName));
            // 根节点
            root = document.getRootElement();
        }
        catch (Exception e)
        {
            LOG.error(e);
        }
        return root;
    }
    
    /** 
     * @Title: arrayToString 
     * @Description: 将字符串list按指定格式输出字符串
     * @param arr
     * @return 
     */
    private String arrayToString(List<String> arr){
        StringBuilder result = new StringBuilder();
        for(String tmp : arr){
            result.append(tmp).append(",");
        }
        // 如果result有值，将末尾的逗号删掉
        if(result.length()>0){
            result.deleteCharAt(result.length()-1);
        }
        return result.toString();
    }
}
