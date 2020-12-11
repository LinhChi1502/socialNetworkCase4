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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.List;

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
    //lay thong tin nguoi dung dang dang nhap
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
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("user", new AppUser());
        modelAndView.setViewName("login");
        try {
            usersService.signUpUser(user);
        } catch (Exception e) {
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

    // anhnbt
    @GetMapping("/home2")
    public ModelAndView home2() {
        List<Post> posts = postService.findAllByFriendAndUser(user());
        ModelAndView modelAndView = new ModelAndView("home2");
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
    public ModelAndView searchPostByContent(@RequestParam(value = "searchContent", required = false) String searchContent) {
        Iterable<Post> posts = postService.getAllPostByContentContaining(searchContent);
        ModelAndView modelAndView = new ModelAndView("home");
        modelAndView.addObject("posts", posts);
        modelAndView.addObject("user", user());
        modelAndView.addObject("post", new Post());
        return modelAndView;
    }

    //Toan, tim` kiem danh sach nguoi dung, neu la ban co nut unfriend, neu ko la ban co nut add friend.
    @PostMapping("/search-user-by-name")
    public ModelAndView searchUserByName(@RequestParam(name = "searchName", required = false) String keySearch) {
        List<AppUser> appUsers = usersService.searchAllUserByNameAndGiveFlagToFriend(keySearch);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("listUsers", appUsers);
        modelAndView.addObject("user", user());
        modelAndView.addObject("keySearch", keySearch);
        modelAndView.setViewName("usersearchresult");
        return modelAndView;
    }

    //Toan
    @GetMapping("/sending-friend-request/{friendId}")
    public ResponseEntity<AppUser> sendingFriendRequest(@PathVariable(name = "friendId") int id) {
        friendshipService.sendFriendRequest(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //Toan
    @GetMapping("/search-user-by-name")
    public ResponseEntity<List<AppUser>> searchUserByNameAPI(@RequestParam(name = "name", required = false) String keySearch) {
        List<AppUser> appUserss = usersService.searchAllUserByNameAndGiveFlagToFriend(keySearch);
        return new ResponseEntity<>(appUserss, HttpStatus.OK);
    }

    //Toan
    @GetMapping("/remove-friend/{friendId}")
    public ResponseEntity<AppUser> removeFriendRequestAndRemoveFriend(@PathVariable(name = "friendId") int id) {
        usersService.removeFriendshipsByUser1IsAndUser2Is(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @GetMapping("/layout2")
    public String layout2() {
        return "layout2";
    }



    @GetMapping("/api/getuserfriend/")
    public ResponseEntity<Iterable<AppUser>> getUserFriends() {
        AppUser user = user();
        Iterable<AppUser> listUserFriend = usersService.searchAllFriendsByAppUser(user);
        return new ResponseEntity<>(listUserFriend, HttpStatus.OK);
    }


    @GetMapping("/bell-notification")
    public ResponseEntity<Iterable<AppUser>> bellNotification() {
        List<AppUser> pendingUsers = usersService.searchAllPendingFriendsByUser(user());
        return new ResponseEntity<>(pendingUsers, HttpStatus.OK);
    }
}

