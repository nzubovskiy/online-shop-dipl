package com.example.onlineshopdipl.entity;

import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Objects;

@Data
@Entity
@Table(name = "Image")
public class Image {

    @Id
    @GeneratedValue
    private Integer id;

    @Lob
    @Type(type = "org.hibernate.type.BinaryType")
    private byte[] image;

    @ManyToOne
    @JoinColumn(name = "ads_pk")
    private Ads ads;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Image image = (Image) o;
        return id != null && Objects.equals(id, image.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }


}
