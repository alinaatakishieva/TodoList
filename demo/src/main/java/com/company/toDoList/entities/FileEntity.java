package com.company.toDoList.entities;

import com.company.toDoList.entities.base.BaseEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "files")
public class FileEntity extends BaseEntity {
    @Column(name = "name")
    public String name;

    @Column(name = "extension")
    public String extension;

    @Column(name = "size")
    public Long size;

    public byte[] data;

    @OneToOne
    public TodoEntity todo;

    public FileEntity(String name, String extension, byte[] data) {
        this.name = name;
        this.extension = extension;
        this.data = data;
    }
}
