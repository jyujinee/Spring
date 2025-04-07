package com.hello.member.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @TableName MEMBERS
 * @TableComment null
 */
public class MembersVO {
	
    private String email;

    private String name;
    
    @JsonIgnore
    private String password;

    @JsonIgnore
    private String salt;

    @JsonIgnore
    private String joinDate;

    @JsonIgnore
    private String latestLoginIp;

    @JsonIgnore
    private String latestPasswordChangeDate;

    @JsonIgnore
    private String latestLoginDate;

    @JsonIgnore
    private String latestLoginFailDate;

    @JsonIgnore
    private int loginFailCount;

    @JsonIgnore
    private String loginYn;

    @JsonIgnore
    private String blockYn;
    
    

    public String getEmail() {
        return this.email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getPassword() {
        return this.password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getSalt() {
        return this.salt;
    }
    
    public void setSalt(String salt) {
        this.salt = salt;
    }
    
    public String getJoinDate() {
        return this.joinDate;
    }
    
    public void setJoinDate(String joinDate) {
        this.joinDate = joinDate;
    }
    
    public String getLatestLoginIp() {
        return this.latestLoginIp;
    }
    
    public void setLatestLoginIp(String latestLoginIp) {
        this.latestLoginIp = latestLoginIp;
    }
    
    public String getLatestPasswordChangeDate() {
        return this.latestPasswordChangeDate;
    }
    
    public void setLatestPasswordChangeDate(String latestPasswordChangeDate) {
        this.latestPasswordChangeDate = latestPasswordChangeDate;
    }
    
    public String getLatestLoginDate() {
        return this.latestLoginDate;
    }
    
    public void setLatestLoginDate(String latestLoginDate) {
        this.latestLoginDate = latestLoginDate;
    }
    
    public String getLatestLoginFailDate() {
        return this.latestLoginFailDate;
    }
    
    public void setLatestLoginFailDate(String latestLoginFailDate) {
        this.latestLoginFailDate = latestLoginFailDate;
    }
    
    public int getLoginFailCount() {
        return this.loginFailCount;
    }
    
    public void setLoginFailCount(int loginFailCount) {
        this.loginFailCount = loginFailCount;
    }
    
    public String getLoginYn() {
        return this.loginYn;
    }
    
    public void setLoginYn(String loginYn) {
        this.loginYn = loginYn;
    }
    
    public String getBlockYn() {
        return this.blockYn;
    }
    
    public void setBlockYn(String blockYn) {
        this.blockYn = blockYn;
    }
    
    @Override
    public String toString() {
        return "MembersVO(email: " + email + ", name: " + name + ", password: " + password + ", salt: " + salt + ", joinDate: " + joinDate + ", latestLoginIp: " + latestLoginIp + ", latestPasswordChangeDate: " + latestPasswordChangeDate + ", latestLoginDate: " + latestLoginDate + ", latestLoginFailDate: " + latestLoginFailDate + ", loginFailCount: " + loginFailCount + ", loginYn: " + loginYn + ", blockYn: " + blockYn + ", )";
    }
}