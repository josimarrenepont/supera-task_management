package entities;

import entities.enums.ItemState;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "tb_item")
public class Item implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Enumerated(EnumType.STRING)
    private ItemState state;

    private boolean isHighlighted;

    @ManyToOne(fetch = FetchType.EAGER, cascade=CascadeType.PERSIST)
    //@JoinTable(name = "taskList_items", joinColumns = @JoinColumn(name = "items_id"), inverseJoinColumns = @JoinColumn(name = "taskLists_id"))
    @JoinColumn(name = "taskList_id")
    private TaskList taskList;

    public Item(){}

    public Item(Long id, String title, boolean isHighlighted) {
        this.id = id;
        this.title = title;
        this.isHighlighted = isHighlighted;
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

    public TaskList getTaskList() {
        return taskList;
    }

    public void setTaskList(TaskList taskList) {
        this.taskList = taskList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return id != null && id.equals(item.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
