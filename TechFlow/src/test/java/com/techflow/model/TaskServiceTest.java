package com.techflow.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe de testes unitários para validar a lógica de negócio em TaskService.
 * Testes cobrem CRUD, ordenação e validações.
 */
public class TaskServiceTest {

    private TaskService taskService;
    private Task highPriorityTask;
    private Task lowPriorityTask;

    @BeforeEach
    void setUp() {
        // Inicializa um novo TaskService antes de cada teste para garantir isolamento
        taskService = new TaskService();

        // Cria tarefas iniciais para uso em múltiplos testes
        highPriorityTask = taskService.createTask("Tarefa Importante", "Com urgência", 5);
        lowPriorityTask = taskService.createTask("Tarefa de Rotina", "Pode esperar", 1);
        taskService.createTask("Tarefa Média", "Com prioridade 3", 3);
    }

    // --- Testes de Criação (CREATE) ---

    @Test
    void testCreateTaskSuccess() {
        Task t4 = taskService.createTask("Nova Tarefa", "Teste de criação", 2);
        assertNotNull(t4, "A tarefa deve ser criada com sucesso.");
        assertEquals("Nova Tarefa", t4.getTitle());
        assertEquals(Task.TaskStatus.TO_DO, t4.getStatus(), "O status inicial deve ser TO_DO.");
    }

    @Test
    void testCreateTaskWithEmptyTitleFails() {
        // Tenta criar tarefa com título vazio e espera que retorne null (conforme a lógica do service)
        Task tInvalid = taskService.createTask("", "Título vazio", 3);
        assertNull(tInvalid, "A criação deve falhar se o título for vazio.");
    }

    @Test
    void testCreateTaskWithInvalidPriorityFails() {
        // Tenta criar tarefa com prioridade fora do range (1-5)
        Task tInvalid = taskService.createTask("Prioridade Inválida", "Prioridade 6", 6);
        assertNull(tInvalid, "A criação deve falhar se a prioridade for > 5.");

        tInvalid = taskService.createTask("Prioridade Inválida", "Prioridade 0", 0);
        assertNull(tInvalid, "A criação deve falhar se a prioridade for < 1.");
    }

    // --- Testes de Leitura (READ) ---

    @Test
    void testGetAllTasksOrder() {
        List<Task> tasks = taskService.getAllTasks();
        assertEquals(3, tasks.size(), "Deve haver 3 tarefas iniciais.");

        // Verifica se a ordenação é decrescente por prioridade (5, 4, 3, 2, 1)
        // A prioridade 5 deve ser a primeira, 4 a segunda, etc.
        assertTrue(tasks.get(0).getPriority() >= tasks.get(1).getPriority(), "A primeira tarefa deve ter prioridade >= à segunda.");
        assertEquals(5, tasks.get(0).getPriority(), "A primeira tarefa deve ser a de maior prioridade (5).");
        assertEquals(1, tasks.get(2).getPriority(), "A última tarefa deve ser a de menor prioridade (1).");
    }

    @Test
    void testGetTaskById() {
        Optional<Task> foundTask = taskService.getTaskById(highPriorityTask.getId());
        assertTrue(foundTask.isPresent(), "A tarefa de alta prioridade deve ser encontrada pelo ID.");
        assertEquals(highPriorityTask.getTitle(), foundTask.get().getTitle());

        Optional<Task> notFound = taskService.getTaskById("non-existent-id");
        assertFalse(notFound.isPresent(), "Nenhuma tarefa deve ser encontrada com ID inválido.");
    }

    // --- Testes de Atualização (UPDATE) ---

    @Test
    void testUpdateTaskStatusSuccess() {
        assertTrue(taskService.updateTaskStatus(highPriorityTask.getId(), Task.TaskStatus.IN_PROGRESS), "A atualização de status deve ser bem-sucedida.");

        Optional<Task> updatedTaskOpt = taskService.getTaskById(highPriorityTask.getId());
        assertEquals(Task.TaskStatus.IN_PROGRESS, updatedTaskOpt.get().getStatus(), "O status deve ser alterado para IN_PROGRESS.");
    }

    @Test
    void testUpdateTaskDetailsSuccess() {
        String newTitle = "Título Editado";
        int newPriority = 2;

        assertTrue(taskService.updateTaskDetails(highPriorityTask.getId(), newTitle, "Nova descrição", newPriority), "A atualização de detalhes deve ser bem-sucedida.");

        Optional<Task> updatedTaskOpt = taskService.getTaskById(highPriorityTask.getId());
        assertEquals(newTitle, updatedTaskOpt.get().getTitle(), "O título deve ser atualizado.");
        assertEquals(newPriority, updatedTaskOpt.get().getPriority(), "A prioridade deve ser atualizada.");
    }

    @Test
    void testUpdateTaskDetailsInvalidPriorityFails() {
        // Tenta atualizar com prioridade inválida
        assertFalse(taskService.updateTaskDetails(highPriorityTask.getId(), "Novo", "Desc", 99), "A atualização deve falhar com prioridade inválida.");

        // Verifica se os dados originais NÃO foram alterados
        assertEquals(5, taskService.getTaskById(highPriorityTask.getId()).get().getPriority());
    }

    // --- Testes de Exclusão (DELETE) ---

    @Test
    void testDeleteTaskSuccess() {
        int initialSize = taskService.getAllTasks().size();

        assertTrue(taskService.deleteTask(lowPriorityTask.getId()), "A exclusão deve ser bem-sucedida.");
        assertEquals(initialSize - 1, taskService.getAllTasks().size(), "O número de tarefas deve diminuir em 1.");

        Optional<Task> deletedTask = taskService.getTaskById(lowPriorityTask.getId());
        assertFalse(deletedTask.isPresent(), "A tarefa não deve mais existir.");
    }

    @Test
    void testDeleteTaskNotFound() {
        assertFalse(taskService.deleteTask("non-existent-id"), "A exclusão deve falhar se o ID não existir.");
        assertEquals(3, taskService.getAllTasks().size(), "O número de tarefas não deve mudar.");
    }
}