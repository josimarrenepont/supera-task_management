package com.gerenciador.tarefas.entities.dto;

import com.gerenciador.tarefas.entities.Item;
import com.gerenciador.tarefas.entities.TaskList;

import java.util.Set;

public class ItemDto {

    private Long id;
    private String title;
    private boolean isHighlighted;
    private Set<TaskList> taskLists;

    public ItemDto(){}

    public ItemDto(Item item){
        this.id = item.getId();
        this.title = item.getTitle();
        this.isHighlighted = item.isHighlighted();
        this.taskLists = getTaskLists();
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

    public boolean isHighlighted() {
        return isHighlighted;
    }

    public void setHighlighted(boolean highlighted) {
        isHighlighted = highlighted;
    }

    public Set<TaskList> getTaskLists() {
        return taskLists;
    }

    public void setTaskLists(Set<TaskList> taskLists) {
        this.taskLists = taskLists;
    }
}
