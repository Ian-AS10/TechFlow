package com.techflow;

import com.techflow.model.TaskService;
import com.techflow.visual.TaskAppGUI;

import javax.swing.SwingUtilities;

/**
 * Classe principal para rodar a aplicação.
 * Toda a inicialização da GUI (Swing) acontece aqui.
 */
public class App {

    // Instancia o TaskService, que será passado para a GUI.
    private static final TaskService taskService = new TaskService();

    public static void main(String[] args) {
        System.out.println("--- INICIANDO SISTEMA DE GERENCIAMENTO DE TAREFAS (SWING) ---");

        // Inicializa a aplicação Swing na Thread de Despacho de Eventos (EDT).
        SwingUtilities.invokeLater(() -> {
            new TaskAppGUI(taskService);
        });
    }
}