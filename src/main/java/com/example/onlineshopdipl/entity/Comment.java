package com.example.onlineshopdipl.entity;


import javax.persistence.*;
import java.util.Objects;

@Entity(name = "comments")
public class Comment {

    private String createdAt;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer pk;
    private String text;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private User id;

    public Comment() {
        this.createdAt = null;
        this.pk = 0;
        this.text = null;
    }

    public Comment(String createdAt, String text) {
        this.createdAt = createdAt;
        this.text = text;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getPk() {
        return pk;
    }

    public void setPk(Integer pk) {
        this.pk = pk;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comment comment = (Comment) o;
        return createdAt.equals(comment.createdAt) && pk.equals(comment.pk) && text.equals(comment.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(createdAt, pk, text);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class Comment {\n");
        sb.append("    createdAt: ").append(toIndentedString(createdAt)).append("\n");
        sb.append("    pk: ").append(toIndentedString(pk)).append("\n");
        sb.append("    text: ").append(toIndentedString(text)).append("\n");
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
