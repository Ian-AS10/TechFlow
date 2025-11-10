package com.techflow;


import com.techflow.model.Task;
import com.techflow.model.TaskService;

import java.util.List;

/**
 * Classe principal para rodar a aplicação de gerenciamento de tarefas
 * em linha de comando, demonstrando o uso do TaskService.
 */
public class App {

    private static final TaskService taskService = new TaskService();

    public static void main(String[] args) {
        System.out.println("--- INICIANDO SISTEMA DE GERENCIAMENTO DE TAREFAS ---");

        // 1. CREATE: Criação de Tarefas (Exemplo inicial) - Simula COMMIT #5
        Task t1 = taskService.createTask("Configurar Banco de Dados", "Instalar e configurar o PostgreSQL.", 5);
        Task t2 = taskService.createTask("Design da Interface", "Esboçar mockups da tela principal.", 3);
        Task t3 = taskService.createTask("Escrever Testes Unitários", "Criar testes para a TaskService.", 4);

        // Tentativa de criar tarefa inválida (Demonstra validação)
        taskService.createTask("", "Título vazio deve falhar.", 1);

        System.out.println("\n--- LISTA INICIAL DE TAREFAS (Ordenadas por Prioridade) ---");
        displayTasks(taskService.getAllTasks());

        //Atualização de Status (Simula fluxo no Kanban)
        if (t1 != null) {
            taskService.updateTaskStatus(t1.getId(), Task.TaskStatus.IN_PROGRESS);
        }
        if (t3 != null) {
            taskService.updateTaskStatus(t3.getId(), Task.TaskStatus.DONE);
        }

        System.out.println("\n--- LISTA APÓS MUDANÇA DE STATUS ---");
        displayTasks(taskService.getAllTasks());

        //Exclusão de Tarefa (Simula finalização ou cancelamento)
        if (t2 != null) {
            taskService.deleteTask(t2.getId());
        }

        System.out.println("\n--- LISTA FINAL DE TAREFAS ---");
        displayTasks(taskService.getAllTasks());
    }

    private static void displayTasks(List<Task> tasks) {
        if (tasks.isEmpty()) {
            System.out.println("Nenhuma tarefa encontrada.");
            return;
        }
        tasks.forEach(System.out::println);
    }
}
