package com.example.onlineshopdipl.entity;

import com.example.onlineshopdipl.dto.Role;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Objects;

@Entity(name = "ads")
public class Ads {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer pk;
    @Column(name = "mediaType")
    private String mediaType;
    private byte[] image;
    private Integer price;
    private String title;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User users;




    public Ads(User id, String mediaType, byte[] image, Integer price, String title) {
        this.mediaType = mediaType;
        this.image = image;
        this.price = price;
        this.title = title;
    }

    public Ads() {

    }

    public Integer getPk() {
        return pk;
    }

    public void setPk(Integer pk) {
        this.pk = pk;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ads ads = (Ads) o;
        return pk.equals(ads.pk) && mediaType.equals(ads.mediaType) && Arrays.equals(image, ads.image) && Objects.equals(price, ads.price) && title.equals(ads.title);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(pk, mediaType, price, title);
        result = 31 * result + Arrays.hashCode(image);
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class Ads {\n");
        sb.append("    image: ").append(toIndentedString(image)).append("\n");
        sb.append("    pk: ").append(toIndentedString(pk)).append("\n");
        sb.append("    price: ").append(toIndentedString(price)).append("\n");
        sb.append("    title: ").append(toIndentedString(title)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    private String toIndentedString(Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }

}
