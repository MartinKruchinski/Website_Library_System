package ca.mcgill.ecse321.projectgroup14.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Entity
public class Item {

    public enum ItemType {
        Book,
        Newspaper,
        Archive,
        Video,
        CD,
    }

    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private Long id;

    private ItemType itemType;
    private Boolean isAvailable;
    private String title;
    private String author;

    public Long getId () {
        return id;
    }

    public void setId (Long id) {
        this.id = id;
    }

    public ItemType getItemType () {
        return itemType;
    }

    public void setItemType (ItemType itemType) {
        this.itemType = itemType;
    }

    public Boolean getIsAvailable () {
        return this.isAvailable;
    }

    public void setIsAvailable (Boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    public String getTitle () {
        return title;
    }

    public void setTitle (String title) {
        this.title = title;
    }

    public String getAuthor () {
        return author;
    }

    public void setAuthor (String author) {
        this.author = author;
    }

}
