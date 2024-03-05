package com.example.demo.entity;

import com.example.demo.enums.GenericStatusEnum;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author meow
 */
@Setter
@Getter
@Entity
@Table(name = "user")
public class UserDO {

    @Id
    private String id;

    private String username;

    private String password;
}
