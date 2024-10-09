package com.service;

import com.dao.TagDAO;
import com.entities.Tag;

import java.util.List;

public class TagService {
    private TagDAO tagDAO;

    public TagService() {
        this.tagDAO = new TagDAO();
    }
    public List<Tag> getAll() {
        return tagDAO.getAll();
    }
    public Tag getTag(Integer tagId) {
        return tagDAO.getTag(tagId);
    }

}
