package repository;

import entities.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {

    List<Item> findItem(String title);

    List<Item> findByTaskListAndStatus(Long taskListId, String status);

    List<Item> findByTaskListId(Long taskListId);
}
