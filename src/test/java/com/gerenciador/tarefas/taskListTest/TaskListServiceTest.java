package com.gerenciador.tarefas.taskListTest;

import com.gerenciador.tarefas.entities.Item;
import com.gerenciador.tarefas.entities.TaskList;
import com.gerenciador.tarefas.repository.TaskListRepository;
import com.gerenciador.tarefas.service.TaskListService;
import com.gerenciador.tarefas.service.exceptions.DatabaseExceptions;
import com.gerenciador.tarefas.service.exceptions.ResourceNotFoundExceptions;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TaskListServiceTest {

    @Mock
    private TaskListRepository taskListRepository;

    @InjectMocks
    private TaskListService taskListService;

    private TaskList taskList;
    private Item item;

    @BeforeEach
    public void setUp(){
        item = new Item(1L, "Item1", true, "TODO");
        taskList = new TaskList(1L, "TaskList1", Collections.singleton(item));
        MockitoAnnotations.openMocks(this);
    }
    @Test
    public void testFindAll(){
        List<TaskList> taskLists = Collections.singletonList(taskList);
        when(taskListRepository.findAll()).thenReturn(taskLists);

        TaskList result = taskListService.findAll().get(0);

        assertEquals(taskList, result);
    }
    @Test
    public void testFindById(){
        when(taskListRepository.findById(1L)).thenReturn(Optional.of(taskList));

        TaskList result = taskListService.findById(1L);

        assertEquals(1L, result.getId());
        assertEquals(taskList, result);
    }
    @Test
    public void testFindByIdNotFound(){
        when(taskListRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundExceptions.class, () -> {
            taskListService.findById(1L);
        });
    }
    @Test
    public void testInsert(){

        taskList.setTitle("TaskList1");

        when(taskListRepository.save(any(TaskList.class))).thenReturn(taskList);

        TaskList result = taskListService.insert(taskList);

        assertEquals(taskList, result);
    }
    @Test
    public void testUpdate(){
        when(taskListRepository.getReferenceById(1L)).thenReturn(taskList);
        when(taskListRepository.save(any(TaskList.class))).thenReturn(taskList);

        TaskList result = taskListService.update(1L, taskList);

        verify(taskListRepository).save(taskList);
        assertEquals("TaskList1", taskList.getTitle());
    }
    @Test
    public void testUpdateNotFound(){
        when(taskListRepository.getReferenceById(anyLong())).thenThrow(new EntityNotFoundException());

        assertThrows(ResourceNotFoundExceptions.class, () -> {
            taskListService.update(1L, taskList);
        });
        verify(taskListRepository, never()).save(any(TaskList.class));
    }
    @Test
    public void testDelete(){
        Long taskListId = 1L;
        doNothing().when(taskListRepository).deleteById(taskListId);

        taskListService.delete(taskListId);

        verify(taskListRepository).deleteById(taskListId);
    }
    @Test
    public void testDeleteResourceNotFound(){
        Long taskListId = 1L;

        doThrow(new EmptyResultDataAccessException(1)).when(taskListRepository).deleteById(taskListId);

        assertThrows(ResourceNotFoundExceptions.class, () -> {
            taskListService.delete(taskListId);
        });
        verify(taskListRepository).deleteById(taskListId);
    }
    @Test
    public void testDeleteDataBaseException(){
        Long taskListId = 1L;

        doThrow(new DataIntegrityViolationException("Integrity Violation"))
                .when(taskListRepository).deleteById(taskListId);

        assertThrows(DatabaseExceptions.class, () -> {
            taskListService.delete(taskListId);
        });

        verify(taskListRepository).deleteById(taskListId);
    }
}
