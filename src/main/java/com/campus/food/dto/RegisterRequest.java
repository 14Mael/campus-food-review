package com.campus.food.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class RegisterRequest {

    @NotBlank(message = "用户名不能为空")
    @Size(min = 3, max = 20, message = "用户名长度3-20个字符")
    private String username;

    @NotBlank(message = "密码不能为空")
    @Size(min = 4, max = 20, message = "密码长度4-20个字符")
    private String password;

    @Size(max = 20, message = "昵称最长20个字符")
    private String nickname;

    public RegisterRequest() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
