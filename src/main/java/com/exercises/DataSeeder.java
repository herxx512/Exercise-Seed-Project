package com.exercises;

import org.hibernate.HibernateException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.BufferedReader;
import java.io.FileReader;

public class DataSeeder {
    private static EntityManagerFactory factory;

    public static void seedData() throws Exception{
        factory = Persistence.createEntityManagerFactory("exercises-persistenceUnit");

        seedExercises();
    }

    private static void seedExercises() throws Exception{
        EntityManager entityManager = factory.createEntityManager();

        entityManager.getTransaction().begin();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("seed_data/exercises.txt"))){
            String line;
            while ((line = bufferedReader.readLine()) != null){
                String[] columnValues = line.split("\\|");
                int id = Integer.parseInt(columnValues[0]);
                String name = columnValues[1];
                String photo = columnValues[2];
                String tutorial = columnValues[3];

                try {
                    Exercises exercises = new Exercises();
                    exercises.setId(id);
                    exercises.setName(name);
                    exercises.setPhoto(photo);
                    exercises.setTutorial(tutorial);

                    entityManager.persist(exercises);
                } catch (HibernateException e){
                    e.printStackTrace();
                }
            }
        }
        entityManager.getTransaction().commit();
    }
}

