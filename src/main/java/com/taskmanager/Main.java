package com.taskmanager;

import com.taskmanager.controller.TaskController;
import com.taskmanager.model.Task;
import com.taskmanager.utils.TaskStatus;

import java.util.Scanner;

public class Main {
    private static TaskController controller = new TaskController();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("=== Task Management System ===");
        System.out.println("Welcome to your personal task manager!");
        
        boolean running = true;
        while (running) {
            displayMenu();
            int choice = getIntInput("Enter your choice: ");
            
            switch (choice) {
                case 1:
                    addTask();
                    break;
                case 2:
                    viewAllTasks();
                    break;
                case 3:
                    updateTaskStatus();
                    break;
                case 4:
                    deleteTask();
                    break;
                case 5:
                    searchTasks();
                    break;
                case 6:
                    running = false;
                    System.out.println("Thank you for using Task Management System. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
        scanner.close();
    }

    private static void displayMenu() {
        System.out.println("\n=== Main Menu ===");
        System.out.println("1. Add New Task");
        System.out.println("2. View All Tasks");
        System.out.println("3. Update Task Status");
        System.out.println("4. Delete Task");
        System.out.println("5. Search Tasks");
        System.out.println("6. Exit");
    }

    private static void addTask() {
        System.out.println("\n=== Add New Task ===");
        System.out.print("Enter task title: ");
        String title = scanner.nextLine();
        System.out.print("Enter task description: ");
        String description = scanner.nextLine();
        
        Task task = controller.createTask(title, description);
        System.out.println("Task created successfully with ID: " + task.getId());
    }

    private static void viewAllTasks() {
        System.out.println("\n=== All Tasks ===");
        if (controller.getAllTasks().isEmpty()) {
            System.out.println("No tasks found. Start by adding a task!");
        } else {
            controller.getAllTasks().forEach(System.out::println);
        }
    }

    private static void updateTaskStatus() {
        System.out.println("\n=== Update Task Status ===");
        int id = getIntInput("Enter task ID: ");
        System.out.println("Select new status:");
        System.out.println("1. PENDING");
        System.out.println("2. IN_PROGRESS");
        System.out.println("3. COMPLETED");
        int statusChoice = getIntInput("Enter choice: ");
        
        TaskStatus newStatus;
        switch (statusChoice) {
            case 1:
                newStatus = TaskStatus.PENDING;
                break;
            case 2:
                newStatus = TaskStatus.IN_PROGRESS;
                break;
            case 3:
                newStatus = TaskStatus.COMPLETED;
                break;
            default:
                System.out.println("Invalid status choice.");
                return;
        }
        
        if (controller.updateTaskStatus(id, newStatus)) {
            System.out.println("Task status updated successfully!");
        } else {
            System.out.println("Task not found with ID: " + id);
        }
    }

    private static void deleteTask() {
        System.out.println("\n=== Delete Task ===");
        int id = getIntInput("Enter task ID to delete: ");
        if (controller.deleteTask(id)) {
            System.out.println("Task deleted successfully!");
        } else {
            System.out.println("Task not found with ID: " + id);
        }
    }

    private static void searchTasks() {
        System.out.println("\n=== Search Tasks ===");
        System.out.print("Enter search keyword: ");
        String keyword = scanner.nextLine();
        var results = controller.searchTasks(keyword);
        
        if (results.isEmpty()) {
            System.out.println("No tasks found matching: " + keyword);
        } else {
            System.out.println("Found " + results.size() + " task(s):");
            results.forEach(System.out::println);
        }
    }

    private static int getIntInput(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextInt()) {
            System.out.print("Please enter a valid number: ");
            scanner.next();
        }
        int value = scanner.nextInt();
        scanner.nextLine(); // consume newline
        return value;
    }
}
