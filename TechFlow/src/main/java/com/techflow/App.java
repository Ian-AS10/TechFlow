package com.techflow;

import com.techflow.controller.TaskController;
import com.techflow.model.TaskService;
// Removida importação de java.util.List, pois o App não lista mais as tarefas diretamente

/**
 * Classe principal para rodar a aplicação de gerenciamento de tarefas
 * em linha de comando, demonstrando o uso do TaskController.
 */
public class App {

    // Apenas instanciamos o TaskService aqui
    private static final TaskService taskService = new TaskService();

    public static void main(String[] args) {

        // 1. POPULANDO DADOS INICIAIS (Setup)
        // Isso simula o carregamento inicial de tarefas para testar o menu
        taskService.createTask("Configurar Banco de Dados", "Instalar e configurar o PostgreSQL.", 5);
        taskService.createTask("Design da Interface", "Esboçar mockups da tela principal.", 3);
        taskService.createTask("Escrever Testes Unitários", "Criar testes para a TaskService.", 4);

        // 2. INICIANDO O CONTROLLER
        TaskController controller = new TaskController(taskService);
        controller.start(); // Inicia o loop de menu interativo

        // A lógica antiga de simulação de fluxo (CREATE, UPDATE, DELETE)
        // foi movida para o TaskController, que agora lida com o fluxo
        // interativo.
    }

    // O método displayTasks não é mais necessário, pois o Controller o fará.
}