package com.example.train_spotter.mvc.models;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name="trains")
public class Train {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min=1, max=200, message="Name must not be blank")
    @Pattern(regexp = "([A-Z&]+)\\s([0-9]+)", message = "Invalid reporting mark! ex. AL&G 1812")
    private String name;

    @NotNull
    @Size(min=1, max=200, message="Wheel Arrangement must not be blank")
    private String wheelArrangement;

    @NotNull
    @Size(min=1, max=200, message="Engine Type must not be blank")
    @Pattern(regexp = "Steam|Diesel|Electric", message = "Invalid engine type!")
    private String engineType;

    @NotNull
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date buildDate;

    @NotNull
    @Size(min=1, max=200, message="Location must not be blank")
    private String location;

    @NotNull
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date dateSpotted;

    private String imagePath;
    private String imageContentType;
    private Long imageSize;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date createdAt;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="users_id", referencedColumnName = "id")
    private User user;

    @Transient
    private MultipartFile image;

    public Train(){
    }
    public Train(String name, String wheelArrangement, String engineType, Date buildDate, String location, Date dateSpotted, User user) {
        this.name = name;
        this.wheelArrangement = wheelArrangement;
        this.engineType = engineType;
        this.buildDate = buildDate;
        this.location = location;
        this.dateSpotted = dateSpotted;
        this.user = user;
        this.createdAt = new Date();
        this.updatedAt = new Date();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWheelArrangement() {
        return wheelArrangement;
    }

    public void setWheelArrangement(String wheelArrangement) {
        this.wheelArrangement = wheelArrangement;
    }

    public String getEngineType() {
        return engineType;
    }

    public void setEngineType(String engineType) {
        this.engineType = engineType;
    }

    public Date getBuildDate() {
        return buildDate;
    }
    public void setBuildDate(Date buildDate) {
        this.buildDate = buildDate;
    }

    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }

    public Date getDateSpotted() {
        return dateSpotted;
    }
    public void setDateSpotted(Date dateSpotted) {
        this.dateSpotted = dateSpotted;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public MultipartFile getImage() {
        return image;
    }
    public void setImage(MultipartFile image) {
        this.image = image;
    }

    public String getImagePath() {
        return imagePath;
    }
    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getImageContentType() {
        return imageContentType;
    }
    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
    }

    public Long getImageSize() {
        return imageSize;
    }
    public void setImageSize(Long imageSize) {
        this.imageSize = imageSize;
    }

}
