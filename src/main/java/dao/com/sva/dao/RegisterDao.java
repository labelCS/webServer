package com.sva.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sva.model.RegisterModel;


/** 
 * @ClassName: RegisterDao 
 * @Description: 找朋友注册表
 * @author JunWang 
 * @date 2016年9月29日 下午4:00:40 
 *  
 */
public interface RegisterDao
{


    /** 
     * @Title: saveRegister 
     * @Description: 保存注册信息
     * @param register
     * @return 
     */
    public int saveRegister(RegisterModel register);

    /** 
     * @Title: refreshRegister 
     * @Description: 刷新注册信息表ip 
     * @param register
     * @return 
     */
    public int refreshRegister(RegisterModel register);


    /** 
     * @Title: getDataByUserName 
     * @Description: 查看是否有人找你
     * @param userName
     * @return 
     */
    public List<RegisterModel> getDataByUserName(String userName);

    /** 
     * @Title: getDataByPhoneNumber 
     * @Description: 查询号码是否存在
     * @param phone
     * @return 
     */
    public List<RegisterModel> getDataByPhoneNumber(String phone);

    /** 
     * @Title: getDataByIsTrue 
     * @Description: 查看对方是否接受
     * @param phone
     * @param isTrue
     * @return 
     */
    public List<RegisterModel> getDataByIsTrue(@Param("phoneNumber")String phone,@Param("isTrue") String isTrue);
 
    /** 
     * @Title: getDataByStatus 
     * @Description: 查看响应状态
     * @param phone
     * @param status
     * @return 
     */
    public List<RegisterModel> getDataByStatus(@Param("phoneNumber")String phone,@Param("stataus")String status);

    /** 
     * @Title: updateIsTrue 
     * @Description: 修改是否接受状态
     * @param phone
     * @param isTrue
     * @return 
     */
    public int updateIsTrue(@Param("phoneNumber")String phone,@Param("isTrue")String isTrue);

    /** 
     * @Title: updataStatus 
     * @Description: 修改被找状态
     * @param phone
     * @param otherPhone
     * @return 
     */
    public int updataStatus(@Param("phoneNumber")String phone,@Param("otherPhone")String otherPhone);

    /** 
     * @Title: updateStatusByPhoneNumber 
     * @Description: 通过电话号码修改状态
     * @param phone
     * @param status
     * @return 
     */
    public int updateStatusByPhoneNumber(@Param("phoneNumber")String phone,@Param("status")String status);
    
    public int updateLoginByPhoneNumber(@Param("phoneNumber")String phone,@Param("status")String status);

    /** 
     * @Title: getIpByUserName 
     * @Description: 根据电话查询
     * @param phoneNumber
     * @return 
     */
    public List<String> getIpByUserName(String phoneNumber);

    /** 
     * @Title: getStatusByphoneNumber2 
     * @Description: 
     * @param phoneNumber
     * @return 
     */
    public List<String> getStatusByphoneNumber2(String phoneNumber);

    /** 
     * @Title: getStatusByIsTrue 
     * @Description: 根据标志获取用户状态
     * @param phone
     * @param isTrue
     * @return 
     */
    public List<String> getStatusByIsTrue(@Param("phoneNumber")String phone,@Param("isTrue")String isTrue);

    /** 
     * @Title: checkLogin1 
     * @Description:多人登录验证
     * @param model
     * @return 
     */
    public RegisterModel checkLogin1(RegisterModel model);


    /** 
     * @Title: setLoginStatus 
     * @Description: 修改登录状态
     * @param phone
     * @param loginStatus
     * @return 
     */
    public int setLoginStatus(@Param("phoneNumber")String phone,@Param("loginStatus")String loginStatus);

}
