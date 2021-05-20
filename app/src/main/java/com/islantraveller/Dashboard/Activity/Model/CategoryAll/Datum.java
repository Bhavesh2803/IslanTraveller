package com.islantraveller.Dashboard.Activity.Model.CategoryAll;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {

@SerializedName("category_id")
@Expose
private String categoryId;

    public String getCategory_color() {
        return category_color;
    }

    public void setCategory_color(String category_color) {
        this.category_color = category_color;
    }

    @SerializedName("category_color")
@Expose
private String category_color;
@SerializedName("category_title")
@Expose
private String categoryTitle;
@SerializedName("category_image")
@Expose
private String categoryImage;

public String getCategoryId() {
return categoryId;
}

public void setCategoryId(String categoryId) {
this.categoryId = categoryId;
}

public String getCategoryTitle() {
return categoryTitle;
}

public void setCategoryTitle(String categoryTitle) {
this.categoryTitle = categoryTitle;
}

public String getCategoryImage() {
return categoryImage;
}

public void setCategoryImage(String categoryImage) {
this.categoryImage = categoryImage;
}

}
