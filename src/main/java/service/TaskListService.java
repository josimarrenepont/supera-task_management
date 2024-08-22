package service;

import entities.Item;
import entities.TaskList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.ItemRepository;
import repository.TaskListRepository;

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

    public TaskList insert(TaskList obj) {
        TaskList taskList = new TaskList();
        taskList.setTitle(obj.getTitle());
        taskList.setItems(obj.getItems());

        return taskListRepository.save(taskList);
    }

    public TaskList update(Long id, TaskList obj) {
        TaskList entity = taskListRepository.getReferenceById(id);
        updateData(entity, obj);
        return taskListRepository.save(entity);
    }

    private void updateData(TaskList entity, TaskList obj) {
        entity.setTitle(obj.getTitle());
        entity.setItems(obj.getItems());
    }

    public void delete(Long id) {
        taskListRepository.deleteById(id);
    }

    public TaskList findById(Long id) {
        Optional<TaskList> obj = taskListRepository.findById(id);
        return obj.orElseThrow();
    }
}
