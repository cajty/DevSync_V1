package com.dao;

import com.config.DbConnection;
import com.entities.Tag;
import jakarta.persistence.EntityManager;

import java.util.List;

public class TagDAO {
    private EntityManager entityManager;

    public TagDAO() {
        this.entityManager = DbConnection.getInstance().getEntityManager();
    }

    public List<Tag> getAll() {
        try {
            // Enable logging of when the method is called
            System.out.println("Attempting to retrieve all tags...");

            // Wrap with transaction if needed
            List<Tag> tags = entityManager.createQuery("SELECT t FROM Tag t", Tag.class).getResultList();

            // Log the number of tags found
            System.out.println("Number of tags retrieved: " + tags.size());

            return tags;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public Tag getTag(Integer tagId) {
        try {
            // Enable logging of when the method is called
            System.out.println("Attempting to retrieve tag by ID...");

            // Wrap with transaction if needed
            Tag tag = entityManager.find(Tag.class, tagId);

            // Log the tag found
            System.out.println("Tag retrieved: " + tag);

            return tag;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


}