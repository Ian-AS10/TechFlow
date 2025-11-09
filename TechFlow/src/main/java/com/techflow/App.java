package com.techflow;


import com.techflow.model.Task;
import com.techflow.model.TaskService;

import java.util.List;

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


    }

    private static void displayTasks(List<Task> allTasks) {
    }
}