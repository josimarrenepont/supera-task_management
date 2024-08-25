package com.gerenciador.tarefas.service.exceptions;

public class DatabaseExceptions extends RuntimeException{
    public DatabaseExceptions(String msg){
        super(msg);
    }
}
