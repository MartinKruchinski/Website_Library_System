package ca.mcgill.ecse321.projectgroup14.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.springframework.transaction.annotation.Transactional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import ca.mcgill.ecse321.projectgroup14.model.*;
import ca.mcgill.ecse321.projectgroup14.model.Item.ItemType;
import ca.mcgill.ecse321.projectgroup14.repository.ItemRepository;

@ExtendWith(SpringExtension.class)
@SpringBootTest

public class ItemPersistence {

    @Autowired
    private ItemRepository itemRepository;


    // Clear repository

    @AfterEach
    public void clearDatabase() {
        itemRepository.deleteAll();
    }

    @Test
    @Transactional
    public void testAndLoadItemPersistence() {

        // Item

        ItemType itemType = ItemType.Book;
        Boolean isBorrowable = true;
        String title = "Book";
        String author = "Martin";


        // Create Item

        Item aItem = new Item();
        aItem.setItemType(itemType);
        aItem.setIsAvailable(isBorrowable);
        aItem.setTitle(title);
        aItem.setAuthor(author);

        // Save

        itemRepository.save(aItem);

        // Reload

        Long Id = aItem.getId();
        aItem = null;
        aItem = itemRepository.findItemById(Id);

        //Assertions

        assertNotNull(aItem);
        assertEquals(itemType, aItem.getItemType());
        assertEquals(isBorrowable, aItem.getIsAvailable());
        assertEquals(title, aItem.getTitle());
        assertEquals(author, aItem.getAuthor());

    }

}
