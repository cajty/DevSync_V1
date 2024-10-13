package com.service;

import com.repository.TagRepository;
import com.entities.Tag;

import java.util.List;

public class TagService {
    private TagRepository tagRepository;

    public TagService() {
        this.tagRepository = new TagRepository();
    }
    public List<Tag> getAll() {
        return tagRepository.getAll();
    }
    public Tag getTag(Integer tagId) {
        return tagRepository.getTag(tagId);
    }

}
