package com.example.project.controller;

import com.example.project.model.AppUser;
import com.example.project.model.Post;
import com.example.project.service.commentlike.CommentLikeService;
import com.example.project.service.friendship.FriendshipService;
import com.example.project.service.post.PostService;
import com.example.project.service.postcomment.PostCommentService;
import com.example.project.service.postlike.PostlikeService;
import com.example.project.service.users.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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
    @Autowired
    Environment env;

    @ModelAttribute("user")
    public AppUser user() {
        return usersService.getCurrentUser();
    }


    // login page //Chi
    @GetMapping("/login")
    public ModelAndView login() {
        ModelAndView modelAndView = new ModelAndView("/login");
        modelAndView.addObject("user", new AppUser());
        return modelAndView;
    }

    // register // Chi
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
//Chi //page 403
    @GetMapping("/page403")
    public String page403() {
        return "403";
    }
    //Chi
    @GetMapping("/home")
    public ModelAndView home() {
        List<Post> posts = postService.findAllByFriendAndUser(user());
        ModelAndView modelAndView = new ModelAndView("home");
        modelAndView.addObject("user", user());
        modelAndView.addObject("posts", posts);
        modelAndView.addObject("post", new Post());
        return modelAndView;
    }

    //Chi
    @GetMapping("/editprofile")
    public ModelAndView editProfile() {
        ModelAndView modelAndView = new ModelAndView("editprofile");
        modelAndView.addObject("user", user());
        return modelAndView;
    }

    //Chi
    @PostMapping("/editprofile")
    public ModelAndView editProfile(@ModelAttribute AppUser appUser) {
        AppUser appUserDB = usersService.findById(appUser.getUserId());
        String avatarURLDB = appUserDB.getAvatarURL();


        MultipartFile avatar = appUser.getAvatar();

        if (!avatar.getOriginalFilename().equals("")) {
            String avatarURL = avatar.getOriginalFilename();
            appUser.setAvatarURL(avatarURL);
            String fileUpload = env.getProperty("upload.path").toString();
            try {
                FileCopyUtils.copy(appUser.getAvatar().getBytes(), new File(fileUpload + avatarURL));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            appUser.setAvatarURL(avatarURLDB);
        }


        usersService.save(appUser);

        ModelAndView modelAndView = new ModelAndView("home");
        List<Post> posts = postService.findAllByFriendAndUser(user());
        modelAndView.addObject("user", user());
        modelAndView.addObject("post", new Post());
        modelAndView.addObject("posts", posts);

        return modelAndView;
    }

    // Chi
    @PostMapping("/create-post")
    public ModelAndView createPost(@ModelAttribute Post post) {
        // lấy ra tag
        String content = post.getContent();
        String[] splitContent = content.split("\r\n");
        String splitTag = splitContent[0];
        String tag = splitTag.substring(1, splitTag.length());
        post.setTag(tag);
        //lưu content
        String concatContent = "";
        for (int i = 1; i < splitContent.length; i++) {
            concatContent += splitContent[i] + "\r\n";
        }
        post.setContent(concatContent);
        // lưu ảnh và set image cho post
        MultipartFile image = post.getImage();
        String imageURL = image.getOriginalFilename();
        post.setImageUrl(imageURL);
        String fileUpload = env.getProperty("upload.path").toString();
        try {
            FileCopyUtils.copy(post.getImage().getBytes(), new File(fileUpload + imageURL));
        } catch (IOException e) {
            e.printStackTrace();
        }

        post.setUsers(user());
        //lưu post
        postService.save(post);
        ModelAndView modelAndView = new ModelAndView("home");
        modelAndView.addObject("user", user());
        modelAndView.addObject("post", new Post());
        List<Post> posts = postService.findAllByFriendAndUser(user());
        modelAndView.addObject("posts", posts);
        return modelAndView;
    }

    // show trang cá nhân của mình hoặc của bạn bè // Chi
    @GetMapping("/show-personal-page/{userID}")
    public ModelAndView showPersonalPage(@PathVariable(name = "userID") int userID) {
        ModelAndView modelAndView;
        Iterable<Post> posts = postService.getAllByAppUserIs(usersService.findById(userID));
        List<Post> userPosts = StreamSupport.stream(posts.spliterator(), true).collect(Collectors.toList());
        int size = userPosts.size();
        if (userID == user().getUserId()) {
            // chuyển sang trang cá nhân của mình
            modelAndView = new ModelAndView("personal");


        } else {
            // chuyển sang trang cá nhận của friend
            modelAndView = new ModelAndView("friendpage");
            modelAndView.addObject("friend", usersService.findById(userID));

        }
        modelAndView.addObject("posts", posts);
        modelAndView.addObject("user", user());
        modelAndView.addObject("size", size);
        return modelAndView;
    }

    //Chi edit post
    @GetMapping("/edit-post/{postID}")
    public ModelAndView editPost(@PathVariable(name = "postID") int postID) {
        ModelAndView modelAndView = new ModelAndView("editpost");
        modelAndView.addObject("user", user());
        modelAndView.addObject("post", postService.findById(postID));
        return modelAndView;
    }

    @PostMapping("/edit-post/{postID}")
    public ModelAndView editPost(@ModelAttribute Post post) {
        post.setUsers(user());
        MultipartFile image = post.getImage();
        String imageURL = image.getOriginalFilename();
        // lấy link ảnh trong DB
        Post postDB = postService.findById(post.getPostID());
        String imageUrlDB = postDB.getImageUrl();

        if (!imageURL.equals("")) {
            post.setImageUrl(imageURL);
            String fileUpload = env.getProperty("upload.path").toString();
            try {
                FileCopyUtils.copy(post.getImage().getBytes(), new File(fileUpload + imageURL));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            post.setImageUrl(imageUrlDB);
        }


        postService.save(post);
        Iterable<Post> posts = postService.getAllByAppUserIs(user());
        ModelAndView modelAndView = new ModelAndView("personal");
        modelAndView.addObject("user", user());
        modelAndView.addObject("posts", posts);
        return modelAndView;
    }

    // CHi //Tìm tất cả bài viết theo tag
    @GetMapping("/find-by-tag/{postID}")
    public ModelAndView findByTag(@PathVariable(name = "postID") int postID) {
        Post post = postService.findById(postID);
        String tag = post.getTag();
        Iterable<Post> posts = postService.getAllByTag(tag);
        ModelAndView modelAndView = new ModelAndView("home");
        modelAndView.addObject("posts", posts);
        modelAndView.addObject("user", user());
        modelAndView.addObject("post", new Post());
        return modelAndView;
    }

    // CHi //Tìm tất cả bài viết theo tag trong trang cá nhân của mình
    @GetMapping("/find-by-tag-personal/{postID}")
    public ModelAndView findByTagPersonal(@PathVariable(name = "postID") int postID) {
        Post post = postService.findById(postID);
        String tag = post.getTag();
        Iterable<Post> posts = postService.getAllPostByTagIsAndAndAppUserIs(tag, user());

        ModelAndView modelAndView = new ModelAndView("personal");
        modelAndView.addObject("posts", posts);
        modelAndView.addObject("user", user());

        return modelAndView;
    }

    // CHi //Tìm tất cả bài viết theo tag trong trang cá nhân của friend
    @GetMapping("/find-by-tag-friend/{postID}")
    public ModelAndView findByTagFriend(@PathVariable(name = "postID") int postID) {
        Post post = postService.findById(postID);
        String tag = post.getTag();
        Iterable<Post> posts = postService.getAllPostByTagIsAndAndAppUserIs(tag, post.getAppUser());

        ModelAndView modelAndView = new ModelAndView("friendpage");
        modelAndView.addObject("posts", posts);
        modelAndView.addObject("friend", post.getAppUser());

        return modelAndView;
    }

    @PostMapping("/search-post-by-content")
    public ModelAndView searchPostByContent(@RequestParam(value = "searchContent") String searchContent) {
        Iterable<Post> posts = postService.getAllPostByContentContaining(searchContent);
        ModelAndView modelAndView = new ModelAndView("home");
        modelAndView.addObject("posts", posts);
        modelAndView.addObject("user", user());
        modelAndView.addObject("post", new Post());
        return modelAndView;
    }


    @PostMapping("/search-user-by-name")
    public ModelAndView searchUserByName(@RequestParam(name = "searchName") String keySearch) {
        List<AppUser> appUsers = usersService.searchAllUserByNameAndGiveFlagToFriend(keySearch);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("listUsers", appUsers);
        modelAndView.addObject("user",user());
        modelAndView.setViewName("usersearchresult");
        return modelAndView;
    }

    @GetMapping("/layout2")
    public String layout2(){
        return "layout2";
    }


}

