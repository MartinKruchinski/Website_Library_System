package ca.mcgill.ecse321.projectgroup14.services;

import java.util.List;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import ca.mcgill.ecse321.projectgroup14.model.Item;
import ca.mcgill.ecse321.projectgroup14.model.Item.ItemType;
import ca.mcgill.ecse321.projectgroup14.repository.ItemRepository;
import ca.mcgill.ecse321.projectgroup14.repository.LibraryRepository;




@Service
public class ItemService {

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    LibraryRepository libraryRepository;

    private Item currentItem;

    @Transactional
    public Item createItem (String author, String title, Boolean isAvailable, String itemTypeString) {

        // Parameter Verification

        if (author == null || author == "") throw new IllegalArgumentException("Author cannot be null");
        if (title == null || title == "") throw new IllegalArgumentException("Title cannot be null");
        if (itemTypeString == null || itemTypeString == "") throw new IllegalArgumentException("ItemType cannot be null");

        ItemType itemType = ItemType.valueOf(itemTypeString);

        // Create Item

        Item item = new Item();
        item.setAuthor(author);
        item.setTitle(title);
        item.setIsAvailable(isAvailable);
        item.setItemType(itemType);
        itemRepository.save(item);

        return item;

    }

    @Transactional
    public Item ediItemAvailability (Item item) {

        // Parameter Verification

        if (item == null) throw new IllegalArgumentException("Item cannot be null");
        if (!itemRepository.existsById(item.getId())) throw new IllegalArgumentException("Item does not exist");

        // Update Item

        item.setIsAvailable(!item.getIsAvailable());
        itemRepository.save(item);

        return item;

    }

    @Transactional
    public Item deleteItem(Item item){

        // Parameter Verification

        if (item == null) throw new IllegalArgumentException("Item cannot be null");
        if (!itemRepository.existsById(item.getId())) throw new IllegalArgumentException("Item does not exist");

        // Delete Item

        itemRepository.delete(item);
        item = null;
        return item;

    }

    @Transactional
    public List<Item> getAllItems () {

        return toList(itemRepository.findAll());

    }

    @Transactional
    public Item getItemById (Long id) {

        // Parameter Verification

        if (!itemRepository.existsById(id)) throw new IllegalArgumentException("Item does not exist");

        // Find Item

        return itemRepository.findItemById(id);

    }

    @Transactional
    public List<Item> getItemsByAuthor (String author) {

        // Parameter Verfication

        if (author == null || author == "")  throw new IllegalArgumentException("Author cannot be null");

        // Find Items

        return itemRepository.findByAuthor(author);

    }

    @Transactional
    public List<Item> getItemsByTitle(String title){

        if (title == null || title == "") throw new IllegalArgumentException("Author cannot be null");

        return itemRepository.findByTitle(title);

    }

    @Transactional
    public Item getCurrentItemId(){

        return currentItem;

    }

    @Transactional
    public void setCurrentItemId(Long Id){
        this.currentItem = itemRepository.findItemById(Id);

    }

    @Transactional
    public List<Item> getAvailableItems(){

        return itemRepository.findByIsAvailable(true);

    }

    @Transactional
    public List<Item> getItemsByType (String itemTypeString) {

        // Parameter Verification

        if (itemTypeString == null || itemTypeString == "") throw new IllegalArgumentException("ItemType cannot be null");

        ItemType itemType = ItemType.valueOf(itemTypeString);

        // Find Items

        return itemRepository.findByItemType(itemType);
    }

    private <T> List<T> toList(Iterable<T> iterable){
		List<T> resultList = new ArrayList<T>();
		for (T t : iterable) {
			resultList.add(t);
		}
		return resultList;
	}

}
