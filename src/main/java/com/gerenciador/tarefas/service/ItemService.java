package com.gerenciador.tarefas.service;

import com.gerenciador.tarefas.entities.Item;
import com.gerenciador.tarefas.entities.dto.ItemDto;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import com.gerenciador.tarefas.repository.ItemRepository;
import com.gerenciador.tarefas.service.exceptions.DatabaseExceptions;
import com.gerenciador.tarefas.service.exceptions.ResourceNotFoundExceptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    public List<Item> findAll() {
        return itemRepository.findAll();
    }

    public Item findById(Long id) {
        Optional<Item> obj = itemRepository.findById(id);
        return obj.orElseThrow(() -> new ResourceNotFoundExceptions(id));
    }

    public List<Item> findByTitle(String title) {
        return itemRepository.findByTitle(title);
    }

    public Item insert(ItemDto itemDto) {
        Item item = new Item();
        item.setTitle(itemDto.getTitle());
        item.setHighlighted(itemDto.isHighlighted());

        return itemRepository.save(item);
    }

    public Item update(Long id, ItemDto itemDto) {
        try {
            Item entity = itemRepository.getReferenceById(id);
            updateData(entity, itemDto);
            return itemRepository.save(entity);
        }catch (EntityNotFoundException e){
            throw new ResourceNotFoundExceptions(id);
        }
    }

    private void updateData(Item entity, ItemDto itemDto) {
        entity.setTitle(itemDto.getTitle());
        entity.setHighlighted(itemDto.isHighlighted());
    }

    public void delete(Long id) {
        try {
            itemRepository.deleteById(id);
        }catch (EmptyResultDataAccessException e){
            throw new ResourceNotFoundExceptions(id);
        }catch (DataIntegrityViolationException e){
            throw new DatabaseExceptions(e.getMessage());
        }
    }
    public Item prioritizeItem(Long itemId) {
        Item item = itemRepository.findById(itemId).orElseThrow(() -> new ResourceNotFoundExceptions(itemId));
        item.setHighlighted(true);
        return itemRepository.save(item);
    }
}
