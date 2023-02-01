package ca.mcgill.ecse321.projectgroup14.services;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import ca.mcgill.ecse321.projectgroup14.model.Item;
import ca.mcgill.ecse321.projectgroup14.model.Item.ItemType;
import ca.mcgill.ecse321.projectgroup14.repository.ItemRepository;


@ExtendWith(MockitoExtension.class)
public class TestItemService {
    @Mock
    private ItemRepository itemRepository;

    @InjectMocks
    private ItemService service;

    //AVAILABLE ITEM 1
    private static final String ITEM_AUTHOR = "Marwan";
    private static final String ITEM_TITLE = "Math";
    private static final String ITEM_TYPE = "Book";
    private static final Long ITEM_ID = 01234l;
    //NON EXISTING ITEM
    private static final Long NON_EXISTING_ITEM_ID = 54354L;

    //AVAILABLE ITEM 2
    private static final String ITEM_AUTHOR_2 = "Martin";
    private static final String ITEM_TITLE_2 = "Physics";
    private static final String ITEM_TYPE_2 = "CD";
    private static final Long ITEM_ID_2 = 01234567l;

    // //NON AVAILABLE ITEM
    // private static final String ITEM_AUTHOR_3 = "SABA";
    // private static final String ITEM_TITLE_3 = "Prison Archive";
    // private static final ItemType ITEM_TYPE_3 = ItemType.Archive;
    // private static final boolean IS_AVAILABLE_3 = false;

    // private static final Long ITEM_ID_3 = 014567l;



    @BeforeEach
    public void setMockOutput() {
        Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
			return invocation.getArgument(0);
		};
        lenient().when(itemRepository.save(any(Item.class))).thenAnswer(returnParameterAsAnswer);
        lenient().when(itemRepository.findItemById(anyLong())).thenAnswer((InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(ITEM_ID)) {
                Item item = new Item();
                item.setAuthor(ITEM_AUTHOR);
                item.setTitle(ITEM_TITLE);
                item.setIsAvailable(true);
                item.setItemType(getType(ITEM_TYPE));
                item.setId(ITEM_ID);

                return item;
			} else {
				return null;
			}
		});

        lenient().when(itemRepository.findByAuthor(anyString())).thenAnswer((InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(ITEM_AUTHOR)) {
                List<Item> itemsList = new ArrayList<Item>();
                Item item = new Item();
                item.setAuthor(ITEM_AUTHOR);
                item.setTitle(ITEM_TITLE);
                item.setIsAvailable(true);
                item.setItemType(getType(ITEM_TYPE));
                item.setId(ITEM_ID);
                itemsList.add(item);
                return itemsList;
			} else {
				return null;
			}
		});


        lenient().when(itemRepository.findByTitle(anyString())).thenAnswer((InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(ITEM_TITLE)) {
                List<Item> itemsList = new ArrayList<Item>();
                Item item = new Item();
                item.setAuthor(ITEM_AUTHOR);
                item.setTitle(ITEM_TITLE);
                item.setIsAvailable(true);
                item.setItemType(getType(ITEM_TYPE));
                item.setId(ITEM_ID);
                itemsList.add(item);
                return itemsList;
			} else {
				return null;
			}
		});

        lenient().when(itemRepository.findByItemType(any())).thenAnswer((InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(getType(ITEM_TYPE))) {
                List<Item> itemsList = new ArrayList<Item>();
                Item item = new Item();
                item.setAuthor(ITEM_AUTHOR);
                item.setTitle(ITEM_TITLE);
                item.setIsAvailable(true);
                item.setItemType(getType(ITEM_TYPE));
                item.setId(ITEM_ID);
                itemsList.add(item);
                return itemsList;
			} else {
				return null;
			}
		});

        lenient().when(itemRepository.findAll()).thenAnswer((InvocationOnMock invocation) -> {
                List<Item> itemsList = new ArrayList<Item>();
                Item item = new Item();
                item.setAuthor(ITEM_AUTHOR);
                item.setTitle(ITEM_TITLE);
                item.setIsAvailable(true);
                item.setItemType(getType(ITEM_TYPE));
                item.setId(ITEM_ID);

                Item item_2 = new Item();
                item_2.setAuthor(ITEM_AUTHOR_2);
                item_2.setTitle(ITEM_TITLE_2);
                item_2.setIsAvailable(true);
                item_2.setItemType(getType(ITEM_TYPE_2));
                item_2.setId(ITEM_ID_2);
                itemsList.add(item);
                itemsList.add(item_2);
                return itemsList;
		});

        lenient().when(itemRepository.findByIsAvailable(true)).thenAnswer((InvocationOnMock invocation) -> {
            List<Item> itemsList = new ArrayList<Item>();
            Item item = new Item();
            item.setAuthor(ITEM_AUTHOR);
            item.setTitle(ITEM_TITLE);
            item.setIsAvailable(true);
            item.setItemType(getType(ITEM_TYPE));
            item.setId(ITEM_ID);

            Item item_2 = new Item();
            item_2.setAuthor(ITEM_AUTHOR_2);
            item_2.setTitle(ITEM_TITLE_2);
            item_2.setIsAvailable(true);
            item_2.setItemType(getType(ITEM_TYPE_2));
            item_2.setId(ITEM_ID_2);
            itemsList.add(item);
            itemsList.add(item_2);
            return itemsList;
    });
    }


    @Test
    public void testCreateItem(){
        String author = "Martin";
        String title = "ECSE321";
        boolean isAvailable = true;
        String itemType = "Book";
        Item item = null;
        try{
            item = service.createItem(author, title, isAvailable, itemType);
        } catch (IllegalArgumentException e){ //check that no error occurred
            System.out.println(e.getMessage());
            fail();
        }
        assertNotNull(item);
        assertEquals(author, item.getAuthor());
        assertEquals(title, item.getTitle());
        assertEquals(isAvailable, item.getIsAvailable());
        assertEquals(getType(itemType), item.getItemType());
    }

    @Test
    public void testCreateItemNull(){
        String error = null;
        String author = null;
        String title = "ECSE321";
        boolean isAvailable = true;
        String itemType = "Book";
        Item item = null;
        try{
            item = service.createItem(author, title, isAvailable, itemType);
        } catch (IllegalArgumentException e){ //check that no error occurred
            error = e.getMessage();
            System.out.println(error);

        }
        assertNull(item);
        assertEquals(error, "Author cannot be null");
    }

    @Test
    public void testCreateItemEmpty(){
        String error = null;
        String author = "Martin";
        String title = "";
        boolean isAvailable = true;
        String itemType = "";
        Item item = null;
        try{
            item = service.createItem(author, title, isAvailable, itemType);
        } catch (IllegalArgumentException e){ //check that no error occurred
            error = e.getMessage();
            System.out.println(error);

        }
        assertNull(item);
        assertEquals(error, "Title cannot be null");
    }

    @Test
    public void testDeleteItem(){
        String author = "Martin";
        String title = "ECSE321";
        boolean isAvailable = true;
        String itemType = "Book";
        Item item = service.createItem(author, title, isAvailable, itemType);
        lenient().when(itemRepository.existsById(any())).thenReturn(true);

        try {
            item = service.deleteItem(item);
        } catch (Exception e) {
            fail();
        }
        assertNull(item);
    }

    @Test
    public void testEditItemAvailability(){
        String author = "Martin";
        String title = "ECSE321";
        boolean isAvailable = true;
        String itemType = "Book";
        Item item = service.createItem(author, title, isAvailable, itemType);
        item.setId(ITEM_ID);
        lenient().when(itemRepository.existsById(any())).thenReturn(true);

        try {
            item = service.ediItemAvailability(item);
        } catch (Exception e) {
            fail();
        }
        assertNotNull(item);
        assertEquals(false, item.getIsAvailable());
    }

    @Test
    public void testEditNonExistingItemAvailability(){
        String error = null;
        String author = "Martin";
        String title = "ECSE321";
        boolean isAvailable = true;
        String itemType = "Book";
        Item item = service.createItem(author, title, isAvailable, itemType);
        item.setId(123l);
        try {
            item = service.ediItemAvailability(item);
        } catch (Exception e) {
            item = null;
            error = e.getMessage();
        }
        assertNull(item);
        assertEquals("Item does not exist", error);
    }

    @Test
    public void testEditNullItemAvailability(){
        String error = null;
        Item item = null;
        try {
            item = service.ediItemAvailability(item);
        } catch (Exception e) {
            item = null;
            error = e.getMessage();
        }
        assertNull(item);
        assertEquals("Item cannot be null", error);
    }


    @Test
    public void testGetExistingItem(){
        Item item= null;
        lenient().when(itemRepository.existsById(any())).thenReturn(true);

        try{
            item = service.getItemById(ITEM_ID);
        } catch(Exception e){
            fail();
        }
        assertNotNull(item);
        assertEquals(ITEM_AUTHOR, item.getAuthor());
        assertEquals(ITEM_TITLE, item.getTitle());
        assertEquals(getType(ITEM_TYPE), item.getItemType());
        assertEquals(ITEM_AUTHOR, item.getAuthor());
        assertEquals(ITEM_ID, item.getId());
    }

    @Test
    public void testGetNonExistingItem(){
        String error = null;
        Item item= null;
        try{
            item = service.getItemById(NON_EXISTING_ITEM_ID);
        } catch(Exception e){
            error = e.getMessage();
        }
        assertNull(item);
        assertEquals("Item does not exist", error);
    }

    @Test
    public void testGetAllItems(){
        List<Item> items = null;
        try{
            items = service.getAllItems();
        } catch(Exception e){
            System.out.println(e.getMessage());
            fail();
        }
        assertNotNull(items);
        assertEquals(2, items.size());

        assertEquals(ITEM_AUTHOR, items.get(0).getAuthor());
        assertEquals(ITEM_TITLE, items.get(0).getTitle());
        assertEquals(getType(ITEM_TYPE), items.get(0).getItemType());
        assertEquals(true, items.get(0).getIsAvailable());
        assertEquals(ITEM_ID, items.get(0).getId());

        assertEquals(ITEM_AUTHOR_2, items.get(1).getAuthor());
        assertEquals(ITEM_TITLE_2, items.get(1).getTitle());
        assertEquals(getType(ITEM_TYPE_2), items.get(1).getItemType());
        assertEquals(true, items.get(1).getIsAvailable());
        assertEquals(ITEM_ID_2, items.get(1).getId());
    }

    @Test
    public void testGetItemsByAuthor(){
        List<Item> items = null;
        try{
            items = service.getItemsByAuthor(ITEM_AUTHOR);
        } catch(Exception e){
            System.out.println(e.getMessage());
            fail();
        }
        assertNotNull(items);
        assertEquals(1, items.size());
        assertEquals(ITEM_AUTHOR, items.get(0).getAuthor());
        assertEquals(ITEM_TITLE, items.get(0).getTitle());
        assertEquals(getType(ITEM_TYPE), items.get(0).getItemType());
        assertEquals(true, items.get(0).getIsAvailable());
        assertEquals(ITEM_ID, items.get(0).getId());

    }

    @Test
    public void testGetItemsByTitle(){
        List<Item> items = null;
        try{
            items = service.getItemsByTitle(ITEM_TITLE);
        } catch(Exception e){
            System.out.println(e.getMessage());
            fail();
        }
        assertNotNull(items);
        assertEquals(1, items.size());
        assertEquals(ITEM_AUTHOR, items.get(0).getAuthor());
        assertEquals(ITEM_TITLE, items.get(0).getTitle());
        assertEquals(getType(ITEM_TYPE), items.get(0).getItemType());
        assertEquals(true, items.get(0).getIsAvailable());
        assertEquals(ITEM_ID, items.get(0).getId());

    }

    @Test
    public void testGetItemsByType(){
        List<Item> items = null;
        try{
            items = service.getItemsByType(ITEM_TYPE);
        } catch(Exception e){
            System.out.println(e.getMessage());
            fail();
        }
        assertNotNull(items);
        assertEquals(1, items.size());
        assertEquals(ITEM_AUTHOR, items.get(0).getAuthor());
        assertEquals(ITEM_TITLE, items.get(0).getTitle());
        assertEquals(getType(ITEM_TYPE), items.get(0).getItemType());
        assertEquals(true, items.get(0).getIsAvailable());
        assertEquals(ITEM_ID, items.get(0).getId());

    }

    @Test
    public void testGetAvailableItems(){
        List<Item> items = null;
        try{
            items = service.getAvailableItems();
        } catch(Exception e){
            System.out.println(e.getMessage());
            fail();
        }
        assertNotNull(items);
        assertEquals(2, items.size());

        assertEquals(ITEM_AUTHOR, items.get(0).getAuthor());
        assertEquals(ITEM_TITLE, items.get(0).getTitle());
        assertEquals(getType(ITEM_TYPE), items.get(0).getItemType());
        assertEquals(true, items.get(0).getIsAvailable());
        assertEquals(ITEM_ID, items.get(0).getId());

        assertEquals(ITEM_AUTHOR_2, items.get(1).getAuthor());
        assertEquals(ITEM_TITLE_2, items.get(1).getTitle());
        assertEquals(getType(ITEM_TYPE_2), items.get(1).getItemType());
        assertEquals(true, items.get(1).getIsAvailable());
        assertEquals(ITEM_ID_2, items.get(1).getId());

    }


    private ItemType getType(String type){
        if(type == null || type.trim().length() == 0){
            throw new IllegalArgumentException("There is no such item type");
        }
        ItemType itemType = null;
        if(type.equals("Archive") || type.equals("archive")){
            itemType = ItemType.Archive;
        }
        else if(type.equals("Book") || type.equals("book")){
            itemType = ItemType.Book;
        }
        else if(type.equals("Newspaper") || type.equals("newspaper")){
            itemType = ItemType.Newspaper;
        }
        else if(type.equals("Video") || type.equals("video")){
            itemType = ItemType.Video;
        }
        else if(type.equals("CD") || type.equals("cd")){
            itemType = ItemType.CD;
        }
        return itemType;
    }



}
