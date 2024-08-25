package com.gerenciador.tarefas.itemTest;

import com.gerenciador.tarefas.entities.Item;
import com.gerenciador.tarefas.entities.dto.ItemDto;
import com.gerenciador.tarefas.repository.ItemRepository;
import com.gerenciador.tarefas.service.ItemService;
import com.gerenciador.tarefas.service.exceptions.ResourceNotFoundExceptions;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ItemServiceTest {

    @Mock
    private ItemRepository itemRepository;

    @InjectMocks
    private ItemService itemService;

    private Item item;

    @BeforeEach
    public void setUp(){
        item = new Item(1L, "Item1", false, "TODO");
        MockitoAnnotations.openMocks(this);
    }
    @Test
    public void testFindAll(){
        List<Item> itemList = Collections.singletonList(item);
        when(itemRepository.findAll()).thenReturn(itemList);

        List<Item> result = itemService.findAll();

        assertEquals(1, result.size());
        assertEquals(item, result.get(0));
    }
    @Test
    public void testFindById(){
        when(itemRepository.findById(1L)).thenReturn(Optional.of(item));

        Item result = itemService.findById(1L);

        assertEquals(1, result.getId());
    }
    @Test
    public void testFindById_NotFound(){
        when(itemRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundExceptions.class, () -> {
            itemService.findById(1L);
        });
    }
    @Test
    public void testFindByTitle(){
        Item item1 = new Item(1L, "Item1", false, "TODO");
        Item item2 = new Item(2L, "Item2", false, "TODO");

        when(itemRepository.findByTitle("Item1")).thenReturn(Arrays.asList(item1,item2));

        List<Item> result = itemService.findByTitle("Item1");

        assertEquals(2, result.size());
        assertEquals("Item1", result.get(0).getTitle());
        assertEquals("Item2", result.get(1).getTitle());
    }
    @Test
    public void testInsert(){
        Item item = new Item(1L, "Item1", false, "Pendente");

        when(itemRepository.save(any(Item.class))).thenReturn(item);

        Item result = itemService.insert(new ItemDto(item));

        assertEquals(item, result);
    }
    @Test
    public void testUpdate(){
        Item updateItem = new Item(1L, "Item1", true, "TODO");
        ItemDto itemDto = new ItemDto();
        itemDto.setTitle("Item1");
        itemDto.setHighlighted(true);

        when(itemRepository.getReferenceById(1L)).thenReturn(updateItem);
        when(itemRepository.save(any(Item.class))).thenReturn(item);

        Item result = itemService.update(1L, itemDto);

        verify(itemRepository).save(updateItem);

        assertEquals("Item1 update", updateItem.getTitle());
        assertEquals(true, updateItem.isHighlighted());
    }
    @Test
    public void testUpdateNotFound(){
        when(itemRepository.getReferenceById(anyLong())).thenThrow(new EntityNotFoundException());

        assertThrows(ResourceNotFoundExceptions.class, () -> {
            itemService.update(1L, new ItemDto());
        });

        verify(itemRepository, never()).save(any(Item.class));
    }
}
