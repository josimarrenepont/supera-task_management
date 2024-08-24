package itemTest;

import controller.TaskListController;
import entities.Item;
import entities.TaskList;
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
import service.TaskListService;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class TaskListControllerTest {

    @InjectMocks
    private TaskListController taskListController;

    @Mock
    private TaskListService taskListService;

    private MockMvc mockMvc;
    private TaskList taskList;
    private Item item;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(taskListController).build();
        item = new Item(1L, "Item1", true);
        taskList = new TaskList(1L, "TaskList1", Collections.singleton(item));
    }

    @Test
    public void testFindAll() throws Exception {
        List<TaskList> taskLists = Collections.singletonList(taskList);
        when(taskListService.findAll()).thenReturn(taskLists);

        mockMvc.perform(get("/taskList")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(taskList.getId()))
                .andExpect(jsonPath("$[0].title").value(taskList.getTitle()))
                .andExpect(jsonPath("$[0].items[0].id").value(item.getId()))
                .andExpect(jsonPath("$[0].items[0].title").value(item.getTitle()));
    }
    @Test public void itemsByTaskListsId() throws Exception{
        List<Item> items = Collections.singletonList(item);
        when(taskListService.getItemsByTaskListId(1L, null)).thenReturn(items);

        mockMvc.perform(get("/taskList/1/items")
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(item.getId()))
                .andExpect(jsonPath("$[0].title").value(item.getTitle()));
    }
    @Test
    public void testFindById() throws Exception{
        when(taskListService.findById(1L)).thenReturn(taskList);

        mockMvc.perform(get("/taskList/1")
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(taskList.getId()))
                .andExpect(jsonPath("$.title").value(taskList.getTitle()));
    }
    @Test
    public void testInsert() throws Exception{
        taskList.setId(1L);
        taskList.setTitle("TaskList1");

        when(taskListService.insert(any(TaskList.class))).thenReturn(taskList);

        mockMvc.perform(post("/taskList")
                    .contentType(MediaType.APPLICATION_JSON)
                .content("{\"title\": \"TaskList1\", \"items\": []}"))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(taskList.getId()))
                .andExpect(jsonPath("$.title").value(taskList.getTitle()));
    }

}
