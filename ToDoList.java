package org.hibernate.lesson5;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class ToDoList {
    private SessionFactory sessionFactory;

    public ToDoList() {
        sessionFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .buildSessionFactory();
    }

    public void addItem(String item) {
        ToDoItem toDoItem = new ToDoItem();
        toDoItem.setDescription(item);
        toDoItem.setCreatedAt(LocalDateTime.now());

        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(toDoItem);
            session.getTransaction().commit();
        }
    }

    public void deleteItem(int id) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            ToDoItem toDoItem = session.get(ToDoItem.class, id);
            if (toDoItem != null) {
                session.delete(toDoItem);
                session.getTransaction().commit();
            } else {
                System.out.println("Item not found.");
            }
        }
    }

    public void displayList() {
        try (Session session = sessionFactory.openSession()) {
            List<ToDoItem> itemList = session.createQuery("FROM ToDoItem", ToDoItem.class).getResultList();
            if (itemList.isEmpty()) {
                System.out.println("Your To-Do list is empty! Go relax and have fun!");
            } else {
                for (ToDoItem item : itemList) {
                    System.out.println(item.getId() + ". " + item.getDescription());
                }
            }
        }
    }

    public static void main(String[] args) {
        ToDoList todoList = new ToDoList();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("What would you like to do? (add, delete, view, quit):");
            String command = scanner.nextLine().toLowerCase(); // Convert input to lowercase
            if (command.equalsIgnoreCase("add")) {
                System.out.println("Enter an item to add:");
                String item = scanner.nextLine();
                todoList.addItem(item);
            } else if (command.equalsIgnoreCase("delete")) {
                System.out.println("Enter the ID of the item to delete:");
                int id = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character
                todoList.deleteItem(id);
            } else if (command.equalsIgnoreCase("view")) {
                System.out.println("The items on your To-Do List are:\n");
                todoList.displayList();
            } else if (command.equalsIgnoreCase("quit")) {
                System.out.println("Goodbye! Have a great day!");
                break;
            } else {
                System.out.println("Invalid command!");
            }
        }
        scanner.close();
    }
}