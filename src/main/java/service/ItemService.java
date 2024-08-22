package service;

import entities.Item;
import entities.dto.ItemDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.ItemRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    public List<Item> findAll(){
        return itemRepository.findAll();
    }

    public Item findById(Long id) {
        Optional<Item> obj = itemRepository.findById(id);
        return obj.orElseThrow();
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
        Item entity = itemRepository.getReferenceById(id);
        updateData(entity, itemDto);
        return itemRepository.save(entity);
    }

    private void updateData(Item entity, ItemDto itemDto) {
        entity.setTitle(itemDto.getTitle());
    }

    public void delete(Long id) {
        itemRepository.deleteById(id);
    }
}
