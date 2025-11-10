package com.techflow.controller;

import com.techflow.model.Task;
import com.techflow.model.TaskService;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * Controladora de Tarefas: Lida com a interação do usuário no console,
 * processa comandos e utiliza o TaskService para executar a lógica de negócio.
 */
public class TaskController {

    private final TaskService taskService;
    private final Scanner scanner;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        System.out.println("--- BEM-VINDO AO GERENCIADOR DE TAREFAS (CONTROLLER MODE) ---");
        boolean running = true;
        while (running) {
            displayMenu();
            String choice = scanner.nextLine();
            System.out.println("---------------------------------------------------------");

            try {
                switch (choice) {
                    case "1":
                        createTaskPrompt();
                        break;
                    case "2":
                        listTasks();
                        break;
                    case "3":
                        updateTaskStatusPrompt();
                        break;
                    case "4":
                        updateTaskDetailsPrompt();
                        break;
                    case "5":
                        deleteTaskPrompt();
                        break;
                    case "0":
                        running = false;
                        System.out.println("👋 Encerrando o sistema. Até mais!");
                        break;
                    default:
                        System.err.println("❌ Opção inválida. Digite um número de 0 a 5.");
                }
            } catch (Exception e) {
                System.err.println("❌ Um erro inesperado ocorreu: " + e.getMessage());
            }
        }
    }

    private void displayMenu() {
        System.out.println("\n--- MENU ---");
        System.out.println("1. Criar Nova Tarefa");
        System.out.println("2. Listar Todas as Tarefas (Por Prioridade)");
        System.out.println("3. Atualizar Status de Tarefa");
        System.out.println("4. Atualizar Detalhes (Título, Descrição, Prioridade)");
        System.out.println("5. Excluir Tarefa");
        System.out.println("0. Sair");
        System.out.print("Escolha uma opção: ");
    }

    private void createTaskPrompt() {
        System.out.print("Título da Tarefa: ");
        String title = scanner.nextLine();
        System.out.print("Descrição: ");
        String description = scanner.nextLine();
        System.out.print("Prioridade (1 a 5): ");
        int priority = Integer.parseInt(scanner.nextLine());

        taskService.createTask(title, description, priority);
    }

    private void listTasks() {
        List<Task> tasks = taskService.getAllTasks();
        if (tasks.isEmpty()) {
            System.out.println("🔎 Nenhuma tarefa encontrada.");
            return;
        }
        System.out.println("--- LISTA DE TAREFAS (Alta Prioridade Primeiro) ---");
        tasks.forEach(System.out::println);
    }

    private void updateTaskStatusPrompt() {
        listTasks();
        System.out.print("Digite os 4 primeiros dígitos do ID da Tarefa a atualizar: ");
        String partialId = scanner.nextLine();

        // Encontra o ID completo
        Task taskToUpdate = taskService.getAllTasks().stream()
                .filter(t -> t.getId().startsWith(partialId))
                .findFirst()
                .orElse(null);

        if (taskToUpdate == null) {
            System.err.println("❌ Tarefa não encontrada com o ID parcial fornecido.");
            return;
        }

        System.out.println("Status atuais: TO_DO, IN_PROGRESS, DONE");
        System.out.print("Digite o NOVO Status (Ex: DONE): ");
        String newStatusStr = scanner.nextLine().toUpperCase();

        try {
            Task.TaskStatus newStatus = Task.TaskStatus.valueOf(newStatusStr);
            taskService.updateTaskStatus(taskToUpdate.getId(), newStatus);
        } catch (IllegalArgumentException e) {
            System.err.println("❌ Status inválido. Use TO_DO, IN_PROGRESS ou DONE.");
        }
    }

    private void updateTaskDetailsPrompt() {
        listTasks();
        System.out.print("Digite os 4 primeiros dígitos do ID da Tarefa a editar: ");
        String partialId = scanner.nextLine();

        Task taskToUpdate = taskService.getAllTasks().stream()
                .filter(t -> t.getId().startsWith(partialId))
                .findFirst()
                .orElse(null);

        if (taskToUpdate == null) {
            System.err.println("❌ Tarefa não encontrada com o ID parcial fornecido.");
            return;
        }

        System.out.println("\n--- EDITANDO TAREFA: " + taskToUpdate.getTitle() + " ---");
        System.out.print("Novo Título (atual: " + taskToUpdate.getTitle() + "): ");
        String newTitle = scanner.nextLine();

        System.out.print("Nova Descrição (atual: " + taskToUpdate.getDescription() + "): ");
        String newDescription = scanner.nextLine();

        System.out.print("Nova Prioridade (1-5) (atual: " + taskToUpdate.getPriority() + "): ");
        // Garantindo que a entrada não esteja vazia
        String priorityInput = scanner.nextLine();
        int newPriority = priorityInput.isEmpty() ? taskToUpdate.getPriority() : Integer.parseInt(priorityInput);

        // Chamada ao novo metodo de servico
        taskService.updateTaskDetails(taskToUpdate.getId(), newTitle, newDescription, newPriority);
    }

    private void deleteTaskPrompt() {
        listTasks();
        System.out.print("Digite os 4 primeiros dígitos do ID da Tarefa para Excluir: ");
        String partialId = scanner.nextLine();

        Task taskToDelete = taskService.getAllTasks().stream()
                .filter(t -> t.getId().startsWith(partialId))
                .findFirst()
                .orElse(null);

        if (taskToDelete != null) {
            taskService.deleteTask(taskToDelete.getId());
        } else {
            System.err.println("❌ Tarefa não encontrada com o ID parcial fornecido.");
        }
    }
}