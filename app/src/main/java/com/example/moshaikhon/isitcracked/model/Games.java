package com.example.moshaikhon.isitcracked.model;


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

    @JsonProperty("releaseDate")
    private String releaseDate;

    @JsonProperty("isAAA")
    private String isAAA;

    @JsonProperty("imageCover")
    private String imageCover;

    @JsonProperty("NFOsCount")
    private String nfosCount;

    @JsonProperty("ratings")
    private String ratings;

    @JsonProperty("OriginalPrice")
    private String originalPrice;

    @JsonProperty("DRM1")
    private String drm1;

    @JsonProperty("SceneGroup1")
    private String sceneGroup1;

    @JsonProperty("BestPrice1")
    private String alternativePrice;

    @JsonProperty("Origin")
    private String origin;


    @JsonProperty("OriginalPlatform")
    private String platform;


    @JsonProperty("releaseDate")
    public String getReleaseDate() {
        return releaseDate;
    }

    @JsonProperty("releaseDate")
    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }


    @JsonProperty("isAAA")
    public String getIsAAA() {
        return isAAA;
    }

    @JsonProperty("isAAA")
    public void setIsAAA(String isAAA) {
        this.isAAA = isAAA;
    }

    @JsonProperty("imageCover")
    public String getImageCover() {
        return imageCover;
    }

    @JsonProperty("imageCover")
    public void setImageCover(String imageCover) {
        this.imageCover = imageCover;
    }

    @JsonProperty("NFOsCount")
    public String getNfosCount() {
        return nfosCount;
    }

    @JsonProperty("NFOsCount")
    public void setNfosCount(String nfosCount) {
        this.nfosCount = nfosCount;
    }

    @JsonProperty("ratings")
    public String getRatings() {
        return ratings;
    }

    @JsonProperty("ratings")
    public void setRatings(String ratings) {
        this.ratings = ratings;
    }

    @JsonProperty("OriginalPrice")
    public String getOriginalPrice() {
        return originalPrice;
    }

    @JsonProperty("OriginalPrice")
    public void setOriginalPrice(String originalPrice) {
        this.originalPrice = originalPrice;
    }

    @JsonProperty("DRM1")
    public String getDrm1() {
        return drm1;
    }

    @JsonProperty("DRM1")
    public void setDrm1(String drm1) {
        this.drm1 = drm1;
    }

    @JsonProperty("SceneGroup1")
    public String getSceneGroup1() {
        return sceneGroup1;
    }

    @JsonProperty("SceneGroup1")
    public void setSceneGroup1(String sceneGroup1) {
        this.sceneGroup1 = sceneGroup1;
    }

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

    @JsonProperty("BestPrice1")
    public String getAlternativePrice() {
        return alternativePrice;
    }

    @JsonProperty("BestPrice1")
    public void setAlternativePrice(String alternativePrice) {
        this.alternativePrice = alternativePrice;
    }

    @JsonProperty("Origin")
    public String getOrigin() {
        return origin;
    }

    @JsonProperty("Origin")
    public void setOrigin(String origin) {
        this.origin = origin;
    }

    @JsonProperty("OriginalPlatform")
    public String getPlatform() {
        return platform;
    }

    @JsonProperty("OriginalPlatform")
    public void setPlatform(String platform) {
        this.platform = platform;
    }
}
