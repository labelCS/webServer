package com.sva.model;

import java.util.Date;

public class AccountModel
{
    private int id;

    private String username;

    private String password;

    private Date updateTime;
    
    private RoleModel role;

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public Date getUpdateTime()
    {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime)
    {
        this.updateTime = updateTime;
    }

    /**
     * @return the role
     */
    public RoleModel getRole() {
        return role;
    }

    /**
     * @param role the role to set
     */
    public void setRole(RoleModel role) {
        this.role = role;
    }

}
