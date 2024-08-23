package service;

import entities.Item;
import entities.TaskList;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import repository.ItemRepository;
import repository.TaskListRepository;
import service.exceptions.DatabaseExceptions;
import service.exceptions.ResourceNotFoundExceptions;

import java.util.List;
import java.util.Optional;

@Service
public class TaskListService {

    @Autowired
    private TaskListRepository taskListRepository;

    @Autowired
    private ItemRepository itemRepository;

    public List<TaskList> getTaskLists(String title, Boolean prioritized){
        if(title != null && prioritized != null){
            return taskListRepository.findByTitleAndPrioritized(title, prioritized);
        }else if(title != null){
            return taskListRepository.findByTitle(title);
        }else if(prioritized != null){
            return taskListRepository.findByPrioritized(prioritized);
        }else{
            return taskListRepository.findAll();
        }
    }
    public List<Item> getItemsByTaskListId(Long taskListId, String status){
        if(status != null){
            return itemRepository.findByTaskListAndStatus(taskListId, status);
        }else{
            return itemRepository.findByTaskListId(taskListId);
        }

    }

    public List<TaskList> findAll() {
        return taskListRepository.findAll();
    }

    public TaskList findById(Long id) {
        Optional<TaskList> obj = taskListRepository.findById(id);
        return obj.orElseThrow(() -> new ResourceNotFoundExceptions(id));
    }

    public TaskList insert(TaskList obj) {
        TaskList taskList = new TaskList();
        taskList.setTitle(obj.getTitle());
        taskList.setItems(obj.getItems());

        return taskListRepository.save(taskList);
    }

    public TaskList update(Long id, TaskList obj) {
        try {
            TaskList entity = taskListRepository.getReferenceById(id);
            updateData(entity, obj);
            return taskListRepository.save(entity);
        }catch (EntityNotFoundException e){
            throw new ResourceNotFoundExceptions(id);
        }
    }

    private void updateData(TaskList entity, TaskList obj) {
        entity.setTitle(obj.getTitle());
        entity.setItems(obj.getItems());
    }

    public void delete(Long id) {
        try {
            taskListRepository.deleteById(id);
        }catch (EmptyResultDataAccessException e){
            throw new ResourceNotFoundExceptions(id);
        }catch (DataIntegrityViolationException e){
            throw new DatabaseExceptions(e.getMessage());
        }
    }
}
