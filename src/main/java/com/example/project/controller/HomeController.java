package com.example.project.controller;

import com.example.project.model.AppUser;
import com.example.project.service.commentlike.CommentLikeService;
import com.example.project.service.friendship.FriendshipService;
import com.example.project.service.post.PostService;
import com.example.project.service.postcomment.PostCommentService;
import com.example.project.service.postlike.PostlikeService;
import com.example.project.service.users.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@SessionAttributes("user")
@Controller
public class HomeController {


    @Autowired
    private AppUserService usersService;
    @Autowired
    private PostService postService;
    @Autowired
    private FriendshipService friendshipService;
    @Autowired
    private PostlikeService postlikeService;
    @Autowired
    private PostCommentService postCommentService;
    @Autowired
    private CommentLikeService commentLikeService;

    // login page
    @GetMapping("/login")
    public ModelAndView login() {
        ModelAndView modelAndView = new ModelAndView("/login");
        modelAndView.addObject("user", new AppUser());
        return modelAndView;
    }


    @PostMapping("/register")
    public ModelAndView register(@Valid @ModelAttribute AppUser user) {
        ModelAndView modelAndView;
        try {
            usersService.signUpUser(user);
            modelAndView = new ModelAndView("login");
        } catch (Exception e) {
            modelAndView = new ModelAndView("login");
            modelAndView.addObject("user", new AppUser());
            modelAndView.addObject("message", "Username has already exist");
        }
        return modelAndView;
    }

    @GetMapping("/home")
    public ModelAndView home() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        AppUser appUser = usersService.getUserByName(name);
        ModelAndView modelAndView = new ModelAndView("home");
        modelAndView.addObject("user", appUser);
        return modelAndView;
    }


    //Toan
    @GetMapping("/usersearch")
    public ModelAndView userSearch(@RequestParam(name = "keySearch") String keySearch) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        AppUser appUser = usersService.getUserByName(name);


        //listall user search
        Iterable<AppUser> searchLists = usersService.getAppUserByUserNameContaining(keySearch);

        //


        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("usersearchresult");
        modelAndView.addObject("searchLists",searchLists);


        return modelAndView;

    }


}

