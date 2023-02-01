package ca.mcgill.ecse321.projectgroup14.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.projectgroup14.model.*;
import ca.mcgill.ecse321.projectgroup14.model.Item.ItemType;

public interface ItemRepository extends CrudRepository <Item, Long>{

	Item findItemById (Long id);
	List<Item> findByAuthor(String author);
    List<Item> findByTitle(String title);
    List<Item> findByIsAvailable(Boolean isAvailable);
    List<Item> findByItemType(ItemType type);

}
