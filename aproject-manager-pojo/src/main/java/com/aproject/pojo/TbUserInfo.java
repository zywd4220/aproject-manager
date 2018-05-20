package com.aproject.pojo;

public class TbUserInfo {
    private Integer id;

    private Long userId;

    private String email;

    private Integer birthdayyear;

    private Integer birthdaymonth;

    private Integer birthdayday;

    private String nickname;

    private String realname;

    private Integer sex;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public Integer getBirthdayyear() {
        return birthdayyear;
    }

    public void setBirthdayyear(Integer birthdayyear) {
        this.birthdayyear = birthdayyear;
    }

    public Integer getBirthdaymonth() {
        return birthdaymonth;
    }

    public void setBirthdaymonth(Integer birthdaymonth) {
        this.birthdaymonth = birthdaymonth;
    }

    public Integer getBirthdayday() {
        return birthdayday;
    }

    public void setBirthdayday(Integer birthdayday) {
        this.birthdayday = birthdayday;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname == null ? null : nickname.trim();
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname == null ? null : realname.trim();
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }
}