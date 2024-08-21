package entities;

import entities.enums.ItemState;
import jakarta.persistence.*;

import java.util.Objects;

public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String title;

    @Enumerated(EnumType.STRING)
    private ItemState state;
    
    private boolean isHighlighted;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Item item)) return false;
        return isHighlighted() == item.isHighlighted() && Objects.equals(getId(), item.getId()) && Objects.equals(getTitle(), item.getTitle());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getTitle(), isHighlighted());
    }
}
