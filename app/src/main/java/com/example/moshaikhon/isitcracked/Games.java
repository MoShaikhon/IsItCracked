package com.example.moshaikhon.isitcracked;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by MoShaikhon on 03-Jan-18.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Games {

    @JsonProperty("title")
    private String title;

    @JsonProperty("image")
    private String image;

    @JsonProperty("crackDate")
    private String crackDate;

    @JsonProperty("title")
    public String getTitle() {
        return title;
    }

    @JsonProperty("title")
    public void setTitle(String title) {
        this.title = title;
    }

    @JsonProperty("crackDate")
    public String getCrackDate() {
        return crackDate;
    }

    @JsonProperty("crackDate")
    public void setCrackDate(String crackDate) {
        this.crackDate = crackDate;
    }

    @JsonProperty("image")
    public String getImage() {
        return image;
    }

    @JsonProperty("image")
    public void setImage(String image) {
        this.image = image;
    }
}
