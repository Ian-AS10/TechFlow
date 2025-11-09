package com.techflow.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TaskService {
    // Simula o armazenamento em memória (repositório simples)
    private final List<Task> taskRepository = new ArrayList<>();


    /* Cria e adiciona uma nova tarefa ao repositório, validando a entrada.
     * @param title Título da tarefa.
     * @param description Descrição detalhada.
     * @param priority Nível de prioridade (1 a 5).
     * @return A tarefa criada ou null em caso de falha na validação.
     */
    public Task createTask(String title, String description, int priority) {
        try {
            // A validação do título e prioridade é feita no construtor da Task.
            Task newTask = new Task(title, description, priority);
            taskRepository.add(newTask);
            // Mensagem de sucesso para o console da aplicação
            System.out.println("Tarefa criada com sucesso: " + newTask.getTitle());
            return newTask;
        } catch (IllegalArgumentException e) {
            // Mensagem de erro para o console
            System.err.println("ERRO ao criar tarefa: " + e.getMessage());
            return null;
        }
    }

    // READ

    /**
     * Retorna todas as tarefas, ordenadas decrescentemente por prioridade.
     *
     * @return Lista de tarefas.
     */
    public List<Task> getAllTasks() {
        // Ordena as tarefas por prioridade (prioridade mais alta primeiro).
        taskRepository.sort((t1, t2) -> Integer.compare(t2.getPriority(), t1.getPriority()));
        return taskRepository;
    }

    // READ (Por ID)

    /**
     * Busca uma tarefa pelo ID único.
     *
     * @param id ID da tarefa a ser buscada.
     * @return Optional contendo a tarefa, se encontrada.
     */
    public Optional<Task> getTaskById(String id) {
        return taskRepository.stream()
                .filter(task -> task.getId().equals(id))
                .findFirst();
    }
}