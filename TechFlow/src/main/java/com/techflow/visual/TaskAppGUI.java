package com.techflow.visual;

import com.techflow.model.Task;
import com.techflow.model.TaskService;
import com.techflow.model.Task.TaskStatus;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;
import java.util.Optional;

/**
 * Interface Gráfica usando Swing para o gerenciamento de tarefas.
 * É instanciada APENAS pela classe App.java.
 */
public class TaskAppGUI extends JFrame {

    private final TaskService taskService; // Recebido via construtor
    private JTable taskTable;
    private DefaultTableModel tableModel;

    // Construtor atualizado para receber o TaskService
    public TaskAppGUI(TaskService service) {
        super("Gerenciador de Tarefas - Swing Edition");
        this.taskService = service;

        // 1. Configuração Inicial
        initializeData();

        // 2. Setup da Interface
        setupUI();

        // 3. Configurações da Janela
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setVisible(true);

        // 4. Carregar Dados Iniciais
        refreshTable();
    }

    // O restante dos métodos (initializeData, setupUI, refreshTable, etc.)
    // permanece o mesmo da implementação anterior.

    private void initializeData() {
        // Inicializa dados para teste
        taskService.createTask("Aprender Swing", "Criar a nova interface gráfica.", 5);
        taskService.createTask("Refatorar App Console", "Remover código antigo do App.java.", 3);
        taskService.createTask("Entregar Projeto", "Finalizar documentação e build.", 4);
    }

    private void setupUI() {
        setLayout(new BorderLayout());

        // --- 1. Tabela de Tarefas (Centro) ---
        String[] columnNames = {"ID", "Título", "Prioridade", "Status", "Criado em"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        taskTable = new JTable(tableModel);

        taskTable.getColumnModel().getColumn(0).setPreferredWidth(50);

        add(new JScrollPane(taskTable), BorderLayout.CENTER);

        // --- 2. Painel de Botões (Topo) ---
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnCreate = new JButton("Criar Nova");
        JButton btnUpdateStatus = new JButton("Mudar Status (P/ Próximo)");
        JButton btnEditDetails = new JButton("Editar Detalhes");
        JButton btnDelete = new JButton("Excluir Selecionada");

        btnCreate.addActionListener(this::createTaskAction);
        btnUpdateStatus.addActionListener(this::updateTaskStatusAction);
        btnEditDetails.addActionListener(this::editTaskDetailsAction);
        btnDelete.addActionListener(this::deleteTaskAction);

        buttonPanel.add(btnCreate);
        buttonPanel.add(btnUpdateStatus);
        buttonPanel.add(btnEditDetails);
        buttonPanel.add(btnDelete);

        add(buttonPanel, BorderLayout.NORTH);
    }

    private void refreshTable() {
        tableModel.setRowCount(0);

        List<Task> tasks = taskService.getAllTasks();

        for (Task task : tasks) {
            tableModel.addRow(new Object[]{
                    task.getId().substring(0, 4),
                    task.getTitle(),
                    task.getPriority(),
                    task.getStatus(),
                    task.getCreatedAt()
            });
        }
    }

    private Optional<Task> getSelectedTask() {
        int selectedRow = taskTable.getSelectedRow();
        if (selectedRow >= 0) {
            String partialId = (String) tableModel.getValueAt(selectedRow, 0);

            return taskService.getAllTasks().stream()
                    .filter(t -> t.getId().startsWith(partialId))
                    .findFirst();
        }
        return Optional.empty();
    }

    private void createTaskAction(ActionEvent e) {
        String title = JOptionPane.showInputDialog(this, "Título da Tarefa:");
        if (title != null && !title.trim().isEmpty()) {
            String description = JOptionPane.showInputDialog(this, "Descrição:");
            String priorityStr = JOptionPane.showInputDialog(this, "Prioridade (1-5):");

            try {
                int priority = Integer.parseInt(priorityStr);
                taskService.createTask(title, description, priority);
                refreshTable();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Prioridade inválida. Use um número de 1 a 5.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void updateTaskStatusAction(ActionEvent e) {
        getSelectedTask().ifPresentOrElse(task -> {
            TaskStatus currentStatus = task.getStatus();
            TaskStatus nextStatus;

            if (currentStatus == TaskStatus.TO_DO) {
                nextStatus = TaskStatus.IN_PROGRESS;
            } else if (currentStatus == TaskStatus.IN_PROGRESS) {
                nextStatus = TaskStatus.DONE;
            } else {
                nextStatus = TaskStatus.TO_DO;
            }

            taskService.updateTaskStatus(task.getId(), nextStatus);
            refreshTable();
        }, () -> JOptionPane.showMessageDialog(this, "Selecione uma tarefa para mudar o status.", "Aviso", JOptionPane.WARNING_MESSAGE));
    }

    private void editTaskDetailsAction(ActionEvent e) {
        getSelectedTask().ifPresentOrElse(task -> {
            String newTitle = JOptionPane.showInputDialog(this, "Novo Título:", task.getTitle());
            if (newTitle == null) return;

            String newDescription = JOptionPane.showInputDialog(this, "Nova Descrição:", task.getDescription());
            String newPriorityStr = JOptionPane.showInputDialog(this, "Nova Prioridade (1-5):", String.valueOf(task.getPriority()));

            try {
                int newPriority = Integer.parseInt(newPriorityStr);

                taskService.updateTaskDetails(task.getId(), newTitle, newDescription, newPriority);
                refreshTable();
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage(), "Erro de Entrada", JOptionPane.ERROR_MESSAGE);
            }
        }, () -> JOptionPane.showMessageDialog(this, "Selecione uma tarefa para editar.", "Aviso", JOptionPane.WARNING_MESSAGE));
    }

    private void deleteTaskAction(ActionEvent e) {
        getSelectedTask().ifPresentOrElse(task -> {
            int result = JOptionPane.showConfirmDialog(this,
                    "Tem certeza que deseja excluir '" + task.getTitle() + "'?",
                    "Confirmar Exclusão",
                    JOptionPane.YES_NO_OPTION);

            if (result == JOptionPane.YES_OPTION) {
                taskService.deleteTask(task.getId());
                refreshTable();
            }
        }, () -> JOptionPane.showMessageDialog(this, "Selecione uma tarefa para excluir.", "Aviso", JOptionPane.WARNING_MESSAGE));
    }

    // O método main FOI REMOVIDO para que a classe App seja o único ponto de entrada.
}