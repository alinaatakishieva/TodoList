package com.company.toDoList.entities;

import com.company.toDoList.entities.base.TimedEntity;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "permissions")
@Data
public class PermissionEntity extends TimedEntity {

    @Column(name = "name", unique = true)
    public String name;
}