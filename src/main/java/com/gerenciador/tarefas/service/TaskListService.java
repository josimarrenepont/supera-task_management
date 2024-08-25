package com.gerenciador.tarefas.service;

import com.gerenciador.tarefas.entities.Item;
import com.gerenciador.tarefas.entities.TaskList;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import com.gerenciador.tarefas.repository.ItemRepository;
import com.gerenciador.tarefas.repository.TaskListRepository;
import com.gerenciador.tarefas.service.exceptions.DatabaseExceptions;
import com.gerenciador.tarefas.service.exceptions.ResourceNotFoundExceptions;

import java.util.List;
import java.util.Optional;

@Service
public class TaskListService {

    @Autowired
    private TaskListRepository taskListRepository;

    @Autowired
    private ItemRepository itemRepository;

    public List<TaskList> findAll() {
        return taskListRepository.findAll();
    }

    public TaskList findById(Long id) {
        Optional<TaskList> obj = taskListRepository.findById(id);
        return obj.orElseThrow(() -> new ResourceNotFoundExceptions(id));
    }

    public TaskList insert(TaskList obj) {
        return taskListRepository.save(obj);
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
