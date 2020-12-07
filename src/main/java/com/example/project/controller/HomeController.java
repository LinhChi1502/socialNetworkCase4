package com.example.project.controller;

import com.example.project.model.AppUser;
import com.example.project.model.CommentLike;
import com.example.project.model.PostLike;
import com.example.project.service.commentlike.CommentLikeService;
import com.example.project.service.friendship.FriendshipService;
import com.example.project.service.post.PostService;
import com.example.project.service.postcomment.PostCommentService;
import com.example.project.service.postlike.PostlikeService;
import com.example.project.service.users.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

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



    //register
    @GetMapping("/register")
    public ModelAndView register() {
        ModelAndView modelAndView = new ModelAndView("register");
        modelAndView.addObject("user", new AppUser());
        return modelAndView;
    }

    @PostMapping("/register")
    public ModelAndView register(@Valid @ModelAttribute AppUser user){
        ModelAndView modelAndView;

            usersService.signUpUser(user);
           modelAndView = new ModelAndView("login");

        return modelAndView;
    }


    @GetMapping("/")
    public ModelAndView home5() {
        ModelAndView modelAndView = new ModelAndView();
//      CommentLike commentLike=new CommentLike();

//        Iterable<PostLike> all = postlikeService.findAll();
//        Iterable<CommentLike> all1 = commentLikeService.findAll();


//        modelAndView.addObject("commentLike", commentLike);
        modelAndView.setViewName("home");
        return modelAndView;
    }

    @GetMapping("/home")
    public String home() {
        return "home";
    }

}

