package com.example.project.service.users;

import com.example.project.model.AppUser;
import com.example.project.service.IService;

public interface IAppUserService extends IService<AppUser> {
    AppUser getUserByName(String name);
}
