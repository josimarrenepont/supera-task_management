package com.gerenciador.tarefas.repository;

import com.gerenciador.tarefas.entities.Item;
import com.gerenciador.tarefas.entities.TaskList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {

    List<Item> findByTitle(String title);

}
