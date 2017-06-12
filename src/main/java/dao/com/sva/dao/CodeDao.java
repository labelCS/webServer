/**   
 * @Title: CodeDao.java 
 * @Package com.sva.dao 
 * @Description: CodeDao接口类 
 * @author labelCS   
 * @date 2016年9月19日 下午3:40:36 
 * @version V1.0   
 */
package com.sva.dao;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sva.model.CodeModel;

/** 
 * @ClassName: CodeDao
 * @Description: CodeDao接口类 
 * @author labelCS 
 * @date 2016年9月19日 下午3:40:36 
 *  
 */
public interface CodeDao {
    public List<CodeModel> getData();

    public int saveCodeInfo(CodeModel cmm);

    public int deleteCode(@Param("name")String name, @Param("password")String password);

    public int checkIsValid(@Param("name")String name, @Param("password")String password);
    
    public int checkByName(String name);
}
