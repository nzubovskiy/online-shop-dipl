package com.example.onlineshopdipl.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "authorities")
public class Authorities {

@Id
private String username;
private String authority;
}
