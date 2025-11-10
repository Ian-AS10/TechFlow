package com.techflow.model;

import java.time.LocalDate;
import java.util.UUID;

/**
 * Classe que representa o modelo de dados de uma Tarefa no sistema.
 * Este arquivo é essencial para o COMMIT #3.
 */
public class Task {

    /**
     * Enumeração para definir o status da tarefa, mapeando as colunas do Kanban.
     */
    public enum TaskStatus {
        TO_DO,          // A Fazer (To Do)
        IN_PROGRESS,    // Em Progresso (In Progress)
        DONE            // Concluído (Done)
    }

    private final String id;
    private String title;
    private String description;
    private int priority; // 1 (Baixa) a 5 (Alta)
    private TaskStatus status;
    private LocalDate createdAt;

    // Adicionado no COMMIT #10 (Mudança de Escopo)
    private LocalDate deliveryDate;

    /**
     * Construtor da Tarefa.
     * @param title Título da tarefa (Não pode ser vazio).
     * @param description Descrição.
     * @param priority Prioridade (de 1 a 5).
     * @throws IllegalArgumentException Se o título for inválido.
     */
    public Task(String title, String description, int priority) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("O título da tarefa não pode ser vazio.");
        }
        if (priority < 1 || priority > 5) {
            throw new IllegalArgumentException("A prioridade deve ser entre 1 e 5.");
        }

        this.id = UUID.randomUUID().toString();
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.status = TaskStatus.TO_DO; // Status inicial no Kanban
        this.createdAt = LocalDate.now();
        this.deliveryDate = null;
    }

    // --- Getters ---

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getPriority() {
        return priority;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }

    // --- Setters ---

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public void setDeliveryDate(LocalDate deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    @Override
    public String toString() {
        String deliveryInfo = deliveryDate != null ? " | Entrega: " + deliveryDate : "";
        return String.format("[ID: %s] Título: %s | Prioridade: %d | Status: %s%s",
                id.substring(0, 4), title, priority, status, deliveryInfo);
    }
}