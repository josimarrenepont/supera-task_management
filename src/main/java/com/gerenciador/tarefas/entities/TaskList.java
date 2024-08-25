package com.gerenciador.tarefas.entities;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "tb_taskList")
public class TaskList implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @OneToMany(mappedBy = "taskList", cascade = CascadeType.ALL)
    private Set<Item> items = new HashSet<>();

    public TaskList(){}

    public TaskList(Long id, String title, Set<Item> items) {
        this.id = id;
        this.title = title;
        this.items = items;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Set<Item> getItems() {
        return items;
    }

    public void setItems(Set<Item> items) {
        this.items = items;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TaskList taskList)) return false;
        return Objects.equals(getId(), taskList.getId()) && Objects.equals(getTitle(), taskList.getTitle()) && Objects.equals(getItems(), taskList.getItems());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getTitle(), getItems());
    }
}
