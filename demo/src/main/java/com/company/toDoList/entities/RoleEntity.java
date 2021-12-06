package com.company.toDoList.entities;

import com.company.toDoList.entities.base.TimedEntity;
import lombok.Data;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "roles")
@Data
public class RoleEntity extends TimedEntity {

    @Column(name = "name", unique = true)
    public String name;

    @ManyToMany(mappedBy = "roles")
    public List<UserEntity> users;

    @ManyToMany
    @JoinTable(name = "roles_permissions",
            joinColumns = {@JoinColumn(name = "role_id")},
            inverseJoinColumns = {@JoinColumn(name = "permission_id")})
    public Collection<PermissionEntity> permissions;
}