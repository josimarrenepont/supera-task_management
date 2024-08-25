package com.gerenciador.tarefas.taskListTest;

import com.gerenciador.tarefas.controller.TaskListController;
import com.gerenciador.tarefas.entities.Item;
import com.gerenciador.tarefas.entities.TaskList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import com.gerenciador.tarefas.service.TaskListService;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
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
        item = new Item(1L, "Item1", true, "ok");
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
    @Test
    public void testUpdate() throws Exception{
        Long id = 1L;
        TaskList updatedTaskList = new TaskList();
        updatedTaskList.setId(id);
        updatedTaskList.setTitle("Updated TaskList");

        when(taskListService.update(id, updatedTaskList)).thenReturn(updatedTaskList);

        mockMvc.perform(put("/taskList/{id}", id)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"id\": 1, \"title\": \"Updated TaskList\", \"items\": []}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(updatedTaskList.getId()))
                .andExpect(jsonPath("$.title").value(updatedTaskList.getTitle()));
    }
    @Test
    public void testDelete() throws Exception{
        Long taskListId = 1L;
        doNothing().when(taskListService).delete(taskListId);

        mockMvc.perform(delete("/taskList/{id}", taskListId)
                .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isNoContent());

        verify(taskListService, times(1)).delete(taskListId);
    }
}
