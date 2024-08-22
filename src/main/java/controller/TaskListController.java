package controller;

import entities.Item;
import entities.TaskList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import service.TaskListService;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/taskList")
public class TaskListController {

    @Autowired
    private TaskListService taskListService;

    @GetMapping
    public List<TaskList> getAllTaskLists(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) Boolean prioritized){
        return taskListService.getTaskLists(title, prioritized);
    }
    @GetMapping("/{id}/items")
    public List<Item> getItemsByTaskListsId(
            @PathVariable Long id, @RequestParam(required = false) String status){
        return taskListService.getItemsByTaskListId(id, status);
    }
    @GetMapping
    public ResponseEntity<List<TaskList>> findAll(){
        List<TaskList> taskLists = taskListService.findAll();
        return ResponseEntity.ok().body(taskLists);
    }
    @GetMapping(value = "/{id}")
    public ResponseEntity<Object> findById(@PathVariable Long id){
        TaskList obj = taskListService.findById(id);
        return ResponseEntity.ok().body(obj);
    }
    @PostMapping
    public ResponseEntity<TaskList> insert(@RequestBody TaskList obj){
        TaskList taskList = taskListService.insert(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).body(taskList);
    }
    @PutMapping(value = "/{id}")
    public ResponseEntity<TaskList> update(@PathVariable Long id, @RequestBody TaskList obj){
        TaskList taskList = taskListService.update(id, obj);
        return ResponseEntity.ok().body(taskList);
    }
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        taskListService.delete(id);
        return ResponseEntity.noContent().build();
    }
    }

