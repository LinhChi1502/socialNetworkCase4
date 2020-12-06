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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

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
    public String login() {
        return "login";
    }

    //register
    @GetMapping("/register")
    public ModelAndView register() {
        ModelAndView modelAndView = new ModelAndView("register");
        modelAndView.addObject("user", new AppUser());
        return modelAndView;
    }

    @PostMapping("/register")
    public ModelAndView register(@ModelAttribute AppUser user) {
        usersService.save(user);
        ModelAndView modelAndView = new ModelAndView("/login");
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

    @GetMapping
    public String home() {
        return "personal";
    }

    @GetMapping("/home")
    public String home1() {
        return "home";
    }

    @GetMapping("/fr")
    public String home2() {
        return "friendrequest";
    }

    @GetMapping("/ss")
    public String home3() {
        return "usersearchresult";
    }

    @GetMapping("/fp")
    public String home4() {
        return "friendpage";
    }

    @GetMapping("/fl")
    public String home6() {
        return "friendlist";
    }

    @GetMapping("/ep")
    public String home7() {
        return "editprofile";
    }
}

