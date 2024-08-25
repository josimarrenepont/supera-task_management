package com.gerenciador.tarefas.repository;

import com.gerenciador.tarefas.entities.Item;
import com.gerenciador.tarefas.entities.TaskList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskListRepository extends JpaRepository<TaskList, Long> {

}
