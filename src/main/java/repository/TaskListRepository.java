package repository;

import entities.TaskList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskListRepository extends JpaRepository<TaskList, Long> {
    List<TaskList> findByTitleAndPrioritized(String title, Boolean prioritized);

    List<TaskList> findByTitle(String title);

    List<TaskList> findByPrioritized(Boolean prioritized);
}
