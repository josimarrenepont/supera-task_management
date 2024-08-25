package com.gerenciador.tarefas.controller;

import com.gerenciador.tarefas.entities.Item;
import com.gerenciador.tarefas.entities.dto.ItemDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import com.gerenciador.tarefas.service.ItemService;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/items")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @GetMapping
    public ResponseEntity<List<ItemDto>> findAll(){
        List<Item> itemList = itemService.findAll();
        List<ItemDto> itemDtos = itemList.stream().map(ItemDto::new).collect(Collectors.toList());
        return ResponseEntity.ok().body(itemDtos);
    }
    @GetMapping(value = "/{id}")
    public ResponseEntity<ItemDto> findById(@PathVariable Long id){
        Item obj = itemService.findById(id);
        ItemDto itemDto = new ItemDto(obj);
        return ResponseEntity.ok().body(itemDto);
    }
    @GetMapping(value = "/findByTitle")
    public ResponseEntity<List<ItemDto>> findByTitle(@RequestParam String title){
        List<Item> itemList = itemService.findByTitle(title);
        ResponseEntity<List<ItemDto>> responseEntity;
        if (itemList.isEmpty()){
           responseEntity = ResponseEntity.noContent().build();
        }else{
            List<ItemDto> itemDtosList = itemList.stream().map(ItemDto::new).collect(Collectors.toList());
            responseEntity = ResponseEntity.ok().body(itemDtosList);
        }
        return responseEntity;
    }
    @PostMapping
    public ResponseEntity<ItemDto> insert(@RequestBody ItemDto itemDto){
        Item item = itemService.insert(itemDto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(item.getId()).toUri();
        return ResponseEntity.created(uri).body(new ItemDto(item));
    }
    @PutMapping("/{id}/prioritize")
    public ResponseEntity<ItemDto> prioritizeItem(@PathVariable Long id){
        Item updatedItem = itemService.prioritizeItem(id);
        ItemDto itemDto = new ItemDto(updatedItem);
        return ResponseEntity.ok().body(itemDto);
    }
    @PutMapping(value = "/{id}")
    public ResponseEntity<ItemDto> update(@PathVariable Long id, @RequestBody ItemDto itemDto){
        Item obj = itemService.update(id, itemDto);
        ItemDto dto = new ItemDto(obj);
        return ResponseEntity.ok().body(dto);
    }
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        itemService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
