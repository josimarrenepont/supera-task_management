package service;

import entities.Item;
import entities.dto.ItemDto;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import repository.ItemRepository;
import service.exceptions.DatabaseExceptions;
import service.exceptions.ResourceNotFoundExceptions;

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

    public List<Item> findItem(String title) {
        List<Item> itemList = itemRepository.findItem(title);
        if (itemList.isEmpty()) {
            return new ArrayList<>();
        } else {
            return itemList;
        }
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
    public List<Item> getItemByStatusAndTaskList(String status, Long taskListsId){
        try {
            return itemRepository.findByTaskListAndStatus(taskListsId, status);
        } catch(EmptyResultDataAccessException e){
            throw new DatabaseExceptions(e.getMessage());
        }
    }
}
