package com.example.project.service.hashtag;

import com.example.project.model.Hashtag;
import com.example.project.service.IService;

public interface IHashtagService extends IService<Hashtag> {
    Hashtag getAllHashtagByName(String name);
}
