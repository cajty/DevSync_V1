package com.repository;

import com.entities.Tag;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.List;

public class TagRepository {
    private final EntityManagerFactory entityManagerFactory;

    public TagRepository() {
        this.entityManagerFactory = Persistence.createEntityManagerFactory("DevSync");
    }

    public List<Tag> getAll() {
        EntityManager entityManager = null;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            System.out.println("Attempting to retrieve all tags...");
            List<Tag> tags = entityManager.createQuery("SELECT t FROM Tag t", Tag.class).getResultList();
            System.out.println("Number of tags retrieved: " + tags.size());
            return tags;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    public Tag getTag(Integer tagId) {
        EntityManager entityManager = null;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            System.out.println("Attempting to retrieve tag by ID...");
            Tag tag = entityManager.find(Tag.class, tagId);
            System.out.println("Tag retrieved: " + tag);
            return tag;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

}