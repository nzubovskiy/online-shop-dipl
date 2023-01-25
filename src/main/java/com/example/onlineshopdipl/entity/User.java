package com.example.onlineshopdipl.entity;

import com.example.onlineshopdipl.dto.Role;
import lombok.Data;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Objects;
import java.util.Set;

@Entity(name = "users")
public class User {
    private String email;
    private String firstName;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String lastName;
    private String phone;
    private String regDate;
    private String city;
    private String mediaType;
    private byte[] image;
    private Role role;

    @OneToMany(mappedBy = "users")
    private Set<Comment> comments;

    @OneToMany(mappedBy = "users")
    private Set<Ads> ads;



    public User() {
        this.email = null;
        this.firstName = null;
        this.id = 0;
        this.lastName = null;
        this.phone = null;
        this.regDate = null;
        this.city = null;
        this.mediaType = null;
        this.image = null;
        this.role = Role.USER;
    }

    public User(String email, String firstName, String lastName, String phone, String regDate, String city, String mediaType, byte[] image) {
        this.email = email;
        this.firstName = firstName;
        this.id = 0;
        this.lastName = lastName;
        this.phone = phone;
        this.regDate = regDate;
        this.city = city;
        this.mediaType = mediaType;
        this.image = image;
        this.role = Role.USER;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRegDate() {
        return regDate;
    }

    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return email.equals(user.email) && Objects.equals(firstName, user.firstName) && id.equals(user.id) && Objects.equals(lastName, user.lastName) && Objects.equals(phone, user.phone) && regDate.equals(user.regDate) && Objects.equals(city, user.city) && Objects.equals(mediaType, user.mediaType) && Arrays.equals(image, user.image) && role == user.role;
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(email, firstName, id, lastName, phone, regDate, city, mediaType, role);
        result = 31 * result + Arrays.hashCode(image);
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class User {\n");
        sb.append("    email: ").append(toIndentedString(email)).append("\n");
        sb.append("    firstName: ").append(toIndentedString(firstName)).append("\n");
        sb.append("    id: ").append(toIndentedString(id)).append("\n");
        sb.append("    lastName: ").append(toIndentedString(lastName)).append("\n");
        sb.append("    phone: ").append(toIndentedString(phone)).append("\n");
        sb.append("    regDate: ").append(toIndentedString(regDate)).append("\n");
        sb.append("    city: ").append(toIndentedString(city)).append("\n");
        sb.append("    image: ").append(toIndentedString(image)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private String toIndentedString(Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}
