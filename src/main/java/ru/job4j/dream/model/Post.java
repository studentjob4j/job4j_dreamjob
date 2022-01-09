package ru.job4j.dream.model;

/**
 * @author Shegai Evgenii
 * @since 23.06.2021
 * @version 1.0
 */

import java.time.LocalDate;
import java.util.Objects;

public class Post {

    private int id;
    private String name;
    private String desc;
    private LocalDate create;

    public Post(int id, String name, String desc, LocalDate create) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.create = create;
    }

    public Post(String name, String desc, LocalDate create) {
        this.name = name;
        this.desc = desc;
        this.create = create;
    }

    public Post() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public LocalDate getCreate() {
        return create;
    }

    public void setCreate(LocalDate create) {
        this.create = create;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return id == post.id && Objects.equals(name, post.name) && Objects.equals(desc, post.desc) && Objects.equals(create, post.create);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, desc, create);
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", name= '" + name + '\'' +
                ", desc= '" + desc + '\'' +
                ", create= " + create +
                '}';
    }
}
