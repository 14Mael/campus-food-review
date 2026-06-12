package com.campus.food.dto;

import javax.validation.constraints.Size;

public class ProfileUpdateRequest {

    @Size(max = 20, message = "昵称最长20个字符")
    private String nickname;

    public ProfileUpdateRequest() {}

    public String getNickname() { return nickname; }
    public void setNickname(String nickname) { this.nickname = nickname; }
}
