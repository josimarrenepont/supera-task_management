package itemTest;

import controller.ItemController;
import entities.Item;
import entities.dto.ItemDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import service.ItemService;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class ItemControllerTest {

    @InjectMocks
    private ItemController itemController;

    @Mock
    private ItemService itemService;

    private MockMvc mockMvc;
    private Item item;

    @BeforeEach
    public void setUp(){
        mockMvc = MockMvcBuilders.standaloneSetup(itemController).build();
        item = new Item(1L, "Item1", false);
    }

    @Test
    public void testFindAll() throws Exception{
        List<Item> items = Collections.singletonList(item);
        when(itemService.findAll()).thenReturn(items);

        ResultActions result = mockMvc.perform(get("/items")
                .contentType(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(item.getId()))
                .andExpect(jsonPath("$[0].title").value(item.getTitle()));
    }
    @Test
    public void testFindById() throws Exception{
        when(itemService.findById(1L)).thenReturn(item);

        ResultActions result = mockMvc.perform(get("/items/1")
                .contentType(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(item.getId()))
                .andExpect(jsonPath("$.title").value(item.getTitle()));
    }
    @Test
    public void testFindItem() throws Exception{
        List<Item> items = Collections.singletonList(item);
        when(itemService.findItem("Item1")).thenReturn(items);

        ResultActions result = mockMvc.perform(get("/items/findItem")
                        .param("title", "Item1")
                .contentType(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(item.getId()))
                .andExpect(jsonPath("$[0].title").value(item.getTitle()));
    }
    @Test
    public void testInsert() throws Exception{
        when(itemService.insert(any(ItemDto.class))).thenReturn(item);

        String itemJson = "{\"id\": 1, \"title\": \"Item1\", \"isHighlighted\": false}";

        ResultActions result = mockMvc.perform(post("/items")
                        .content(itemJson)
                .contentType(MediaType.APPLICATION_JSON));

        result.andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(item.getId()))
                .andExpect(jsonPath("$.title").value(item.getTitle()));
    }
    @Test
    public void testPrioritizeItem() throws Exception{
        when(itemService.prioritizeItem(1L)).thenReturn(item);

        ResultActions result = mockMvc.perform(put("/items/1/prioritize")
                .contentType(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(item.getId()))
                .andExpect(jsonPath("$.title").value(item.getTitle()));
    }
    @Test
    public void testUpdate() throws Exception{
        Item item = new Item(1L, "Item1", false);
        ItemDto itemDto = new ItemDto(item);
        when(itemService.update(eq(1L), any(ItemDto.class))).thenReturn(item);

        String itemJson = "{\"id\": 1, \"title\": \"Item1\", \"isHighlighted\": false}";

        ResultActions result = mockMvc.perform(put("/items/1")
                        .content(itemJson)
                .contentType(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(item.getId()))
                .andExpect(jsonPath("$.title").value(item.getTitle()));
    }
    @Test
    public void testDelete() throws Exception{
        Long itemId = 1L;
        doNothing().when(itemService).delete(itemId);

        ResultActions result = mockMvc.perform(delete("/items/{id}", itemId)
                .contentType(MediaType.APPLICATION_JSON));

        result.andExpect(status().isNoContent());
        verify(itemService, times(1)).delete(itemId);
    }
    @Test
    public void testFilter() throws Exception{
        Item item = new Item();
        item.setId(1L);
        item.setTitle("Item1");

        when(itemService.getItemByStatusAndTaskList(anyString(), anyLong()))
                .thenReturn(Collections.singletonList(item));

        ResultActions result = mockMvc.perform(get("/items/filter")
                        .param("status", "completed")
                        .param("taskListId", "1")
                .contentType(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(item.getId()))
                .andExpect(jsonPath("$[0].title").value(item.getTitle()));
    }
}
