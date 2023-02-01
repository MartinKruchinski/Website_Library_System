package ca.mcgill.ecse321.projectgroup14.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.projectgroup14.dto.ItemDto;
import ca.mcgill.ecse321.projectgroup14.dto.ItemDto.ItemType;
import ca.mcgill.ecse321.projectgroup14.model.Item;
import ca.mcgill.ecse321.projectgroup14.services.ItemService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;


@CrossOrigin(origins = "*")
@RestController
public class ItemController {

    @Autowired
    private ItemService itemService;

    @PostMapping(value = { "/items/{itemType}", "/items/{itemType}/"})
    public ItemDto createItem(
    @PathVariable(name = "itemType") String itemType,
    @RequestParam(name = "author") String author,
    @RequestParam(name = "title")  String title,
    @RequestParam(name = "isAvailable") boolean isAvailable)
    throws IllegalArgumentException{

        Item item = itemService.createItem(author, title, isAvailable, itemType);
        return convertToDto(item);

    }

    @DeleteMapping(value = { "/items/delete/{itemId}", "/items/delete/{itemId}/" })
    public void deleteItem(
    @PathVariable ("itemId") Long itemId)
    throws IllegalArgumentException {

        itemService.deleteItem(itemService.getItemById(itemId));

    }

    @PatchMapping(value = { "/items/edit/{itemId}", "/items/edit/{itemId}/" })
	public void editItemAvailability(
    @PathVariable("itemId") Long itemId)
	throws IllegalArgumentException {

        itemService.ediItemAvailability(itemService.getItemById(itemId));

	}

    @GetMapping(value = { "/items", "/items/" })
    public List<ItemDto> getAllItems(){
        return itemService.getAllItems().stream().map(item -> convertToDto(item)).collect(Collectors.toList());
    }

    @GetMapping(value = { "/availableItems", "/availableItems/" }) 
    public List<ItemDto> getAvailableItems(){
        return itemService.getAvailableItems().stream().map(item -> convertToDto(item)).collect(Collectors.toList());
    }

    @GetMapping(value = { "/items/currentItem", "/items/currentItem/" }) 
    public ItemDto getCurrentItem(){
        return convertToDto(itemService.getCurrentItemId());
    }

    @PatchMapping(value = { "/items/currentItem/set/{itemId}", "/items/currentItem/set/{itemId}" }) 
    public void setCurrentItem(@PathVariable("itemId") Long itemId){
        itemService.setCurrentItemId(itemId);
    }

    @GetMapping(value = { "/items/{itemId}", "/items/{itemId}/" })
    public ItemDto getItemById(
    @PathVariable("itemId") Long itemId)
    throws IllegalArgumentException{

        return convertToDto(itemService.getItemById(itemId));

    }

    @GetMapping(value = { "/items/author/{author}", "/items/author/{author}/" })
    public List<ItemDto> getItemsByAuthor(
    @PathVariable("author") String author)
    throws IllegalArgumentException{

        List<Item> items = itemService.getItemsByAuthor(author);
        List<ItemDto> itemDtos = new ArrayList<ItemDto>();
        for (Item i : items) itemDtos.add(convertToDto(i));
        return itemDtos;

    }

    @GetMapping(value = { "/items/title/{title}", "/items/title/{title}/" })
    public List<ItemDto> getItemsByTitle(
    @PathVariable("title") String title)
    throws IllegalArgumentException{

        List<Item> items = itemService.getItemsByTitle(title);
        List<ItemDto> itemDtos = new ArrayList<ItemDto>();
        for (Item i : items) itemDtos.add(convertToDto(i));
        return itemDtos;
    }

    @GetMapping(value = { "/items/type/{itemType}", "/items/type/{itemType}/" })
    public List<ItemDto> getItemsByType(
    @PathVariable("itemType") String itemType)
    throws IllegalArgumentException{

        List<Item> items = itemService.getItemsByType(itemType);
        List<ItemDto> itemDtos = new ArrayList<ItemDto>();
        for (Item i : items) itemDtos.add(convertToDto(i));
        return itemDtos;

    }

    public static ItemDto convertToDto(Item item) {

        if (item == null) throw new IllegalArgumentException("Item cannot be null");

        ItemDto itemDto = new ItemDto(item.getId(), ItemType.valueOf(item.getItemType().toString()), item.getTitle(), item.getAuthor(), item.getIsAvailable());
        return itemDto;

    }

}
