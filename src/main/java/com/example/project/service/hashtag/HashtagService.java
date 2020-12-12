package com.example.project.service.hashtag;

import com.example.project.model.Hashtag;
import com.example.project.repository.HashtagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HashtagService implements IHashtagService {

    @Autowired
    private HashtagRepository hashtagRepository;

    @Override
    public Iterable<Hashtag> findAll() {
        return hashtagRepository.findAll();
    }

    @Override
    public Hashtag findById(Integer id) {
        return hashtagRepository.findById(id).get();
    }

    @Override
    public void save(Hashtag hashtag) {
        hashtagRepository.save(hashtag);
    }

    @Override
    public void remove(Integer id) {
        hashtagRepository.deleteById(id);
    }

    @Override
    public Hashtag getAllHashtagByName(String name) {
        return hashtagRepository.getAllByName(name);
    }
}
