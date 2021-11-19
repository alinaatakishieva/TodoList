package com.company.toDoList.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "roles")
@Data
public class RoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    public String name;

    @ManyToMany(mappedBy = "roles")
    public List<UserEntity> users;

    @ManyToMany
    @JoinTable(name = "roles_permissions",
            joinColumns = {@JoinColumn(name = "role_id")},
            inverseJoinColumns = {@JoinColumn(name = "permission_id")})
    public Collection<PermissionEntity> permissions;
}