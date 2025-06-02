package aut.ap;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.Scanner;

public class Main {
    static SessionFactory sessionFactory;
    public static void main(String[] args) {
        setUpSessionFactory();
        Scanner scanner = new Scanner(System.in);
        System.out.println("1.[L]ogin, 2.[S]ign up:");
        String choice = scanner.nextLine();

        if (choice.equals("L") || choice.equals("1")) {
            login(scanner);
        } else if (choice.equals("S") || choice.equals("2")) {
            signup(scanner);
        }else{
            System.err.println("Invalid choice");
        }
        closeSessionFactory();
    }
    private static void signup(Scanner scanner) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();

        try {
            System.out.println("First name: ");
            String firstName = scanner.nextLine();

            System.out.println("Last name: ");
            String lastName = scanner.nextLine();

            System.out.println("Age: ");
            Integer age = scanner.nextInt();
            scanner.nextLine();

            System.out.println("Email: ");
            String email = scanner.nextLine();

            System.out.println("Password: ");
            String password = scanner.nextLine();

            User existingUser = session.createQuery("from User where email = :email", User.class)
                    .setParameter("email", email)
                    .uniqueResult();

            if (existingUser != null) {
                System.err.println("An account with this email already exists");
                tx.rollback();
                return;
            }
            if (password.length() < 8) {
                System.err.println("Weak password");
                tx.rollback();
                return;
            }

            User user = new User(firstName, lastName, age, email, password);
            session.persist(user);
            tx.commit();
            System.out.println("Signup successful!");

        } catch (Exception e) {
            tx.rollback();
            System.err.println("Error: " + e.getMessage());
        } finally {
            session.close();
        }
    }
    private static void login(Scanner scanner) {
        Session session = sessionFactory.openSession();

        System.out.println("Email: ");
        String email = scanner.nextLine();

        System.out.println("Password: ");
        String password = scanner.nextLine();

        User user = session.createQuery("from User where email = :email and password = :password", User.class)
                .setParameter("email", email)
                .setParameter("password", password)
                .uniqueResult();
        if (user == null) {
            System.err.println("Invalid email or password");
        }else{
            System.out.println("Welcome, " + user.getFirstName() + " " + user.getLastName() + "!");
        }
        session.close();
    }
    private static void setUpSessionFactory() {
        sessionFactory = new Configuration().configure().buildSessionFactory();
    }
    private static void closeSessionFactory() {
        sessionFactory.close();
    }
}