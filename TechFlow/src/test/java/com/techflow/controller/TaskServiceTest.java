package com.techflow.controller;

import com.techflow.model.Task;
import com.techflow.model.Task.TaskStatus;
import com.techflow.model.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes Unitários para a classe TaskService (CRUD).
 * Usando JUnit 5.
 * * Este arquivo representa o COMMIT #4: Adiciona testes unitários para a classe TaskService.
 */
public class TaskServiceTest {

    private TaskService taskService;

    // Configuração inicial antes de cada teste
    @BeforeEach
    void setUp() {
        taskService = new TaskService();
        // Cria algumas tarefas iniciais para os testes de Read e Update/Delete
        taskService.createTask("Tarefa Teste 1", "Descrição 1", 3);
        taskService.createTask("Tarefa Teste 2", "Descrição 2", 5); // Maior prioridade
    }

    // Teste de Criação
    @Test
    void testCreateTask() {
        Task t = taskService.createTask("Nova Tarefa", "Detalhes", 2);
        assertNotNull(t, "A tarefa deve ser criada e não nula.");
        assertEquals("Nova Tarefa", t.getTitle(), "O título deve ser o mesmo do input.");
        assertEquals(TaskStatus.TO_DO, t.getStatus(), "O status inicial deve ser TO_DO.");
        assertEquals(3, taskService.getAllTasks().size(), "Deve haver 3 tarefas após a criação.");
    }


    // Teste de Leitura e Ordenação
    @Test
    void testGetAllTasksAndOrdering() {
        List<Task> tasks = taskService.getAllTasks();
        assertEquals(2, tasks.size(), "Deve haver 2 tarefas iniciais.");
        // Verifica se a ordenação por prioridade está correta (5 deve vir antes de 3)
        assertTrue(tasks.get(0).getPriority() > tasks.get(1).getPriority(), "A primeira tarefa deve ter maior prioridade (5 > 3).");
    }
}