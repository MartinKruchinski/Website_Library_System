package ca.mcgill.ecse321.projectgroup14.dto;

public class ItemDto {

    public enum ItemType {
        Book,
        Newspaper,
        Archive,
        Video,
        CD,
    }

    private Long itemId;
    private ItemType itemType;
    private boolean isAvailable;
    private String title;
    private String author;

    public ItemDto () {}

    public ItemDto (Long id, ItemType aItemType, String aTitle, String aAuthor, boolean aIsAvailable) {

        this.itemId = id;
        this.itemType = aItemType;
        this.isAvailable = aIsAvailable;
        this.title = aTitle;
        this.author = aAuthor;

    }

    public Long getId () {
        return itemId;
    }

    public ItemType getItemType () {
        return itemType;
    }

    public boolean getIsAvailable () {
        return isAvailable;
    }

    public String getTitle () {
        return title;
    }

    public String getAuthor () {
        return author;
    }

}
