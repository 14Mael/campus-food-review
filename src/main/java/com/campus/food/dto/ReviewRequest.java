package com.campus.food.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class ReviewRequest {

    @NotNull(message = "店铺ID不能为空")
    private Long shopId;

    @NotNull(message = "评分不能为空")
    @Min(value = 1, message = "评分最低1分")
    @Max(value = 5, message = "评分最高5分")
    private Integer rating;

    @NotBlank(message = "点评内容不能为空")
    private String content;

    public ReviewRequest() {
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
