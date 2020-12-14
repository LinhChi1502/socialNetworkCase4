package com.example.project.controller;

import com.example.project.model.*;
import com.example.project.service.commentlike.CommentLikeService;
import com.example.project.service.friendship.FriendshipService;
import com.example.project.service.hashtag.HashtagService;
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
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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
    private HashtagService hashtagService;
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
        ModelAndView modelAndView;
        try {
            usersService.signUpUser(user);
            modelAndView = new ModelAndView("login");
            modelAndView.addObject("user", new AppUser());
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
    public ModelAndView editProfile(@Validated @ModelAttribute AppUser appUser, BindingResult bindingResult) {
        ModelAndView modelAndView;
        boolean isValidated = true;
        if (bindingResult.hasFieldErrors()) {
            isValidated = false;
            modelAndView = new ModelAndView("editprofile");
            modelAndView.addObject("user", user());
            modelAndView.addObject("isValidated", isValidated);
            return modelAndView;
        }

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

        modelAndView = new ModelAndView("home");
        List<Post> posts = postService.findAllByFriendAndUser(user());
        modelAndView.addObject("user", user());
        modelAndView.addObject("post", new Post());
        modelAndView.addObject("posts", posts);
        modelAndView.addObject("isValidated", isValidated);
        return modelAndView;
    }

    // Chi
    @PostMapping("/create-post")
    public ModelAndView createPost(@ModelAttribute Post post) {
        // lấy ra tag
        String content = post.getContent();
        Pattern pattern = Pattern.compile("\\B(\\#[a-zA-Z]+\\b)(?!;)");
        Matcher matcher = pattern.matcher(content);
        Set<Hashtag> hashtags = new HashSet<>();
        while (matcher.find()) {
            String match = matcher.group(1);
            Hashtag hashtag = new Hashtag();
            hashtag.setName(match);
            hashtags.add(hashtag);
            hashtagService.save(hashtag);
        }
        post.setTags(hashtags);
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

        post.setAppUser(user());
        //lưu post
        postService.save(post);
        ModelAndView modelAndView = new ModelAndView("home");
        modelAndView.addObject("user", user());
        modelAndView.addObject("post", new Post());
        List<Post> posts = postService.findAllByFriendAndUser(user());
        modelAndView.addObject("posts", posts);
        return modelAndView;
    }

    // show trang cá nhân của mình hoặc của friend hoặc chưa phải friend // Chi
    @GetMapping("/show-personal-page/{userID}")
    public ModelAndView showPersonalPage(@PathVariable(name = "userID") int userID) {
        ModelAndView modelAndView = null;
        Iterable<Post> posts = new ArrayList<>();

        if (userID == user().getUserId()) {
            // chuyển sang trang cá nhân của mình
            modelAndView = new ModelAndView("personal");
            posts = postService.getAllByAppUserIs(usersService.findById(userID));
        } else if (friendshipService.checkIsFriend(user(), usersService.findById(userID))) {
            // chuyển sang trang cá nhận của friend
            modelAndView = new ModelAndView("friendpage");
            modelAndView.addObject("friend", usersService.findById(userID));
            posts = postService.getAllByAppUserIs(usersService.findById(userID));
        } else {
            // chuyen sang trang ca nhan cua nguoi khong phai friend
            modelAndView = new ModelAndView("friendpage");
            modelAndView.addObject("friend", usersService.findById(userID));
            posts = postService.getAllPostByStatusIsAndAppUserIs(true, usersService.findById(userID));
        }
        List<Post> userPosts = StreamSupport.stream(posts.spliterator(), true).collect(Collectors.toList());
        int size = userPosts.size();
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
        post.setAppUser(user());
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
// lưu hashtag
        String content = post.getContent();
        Pattern pattern = Pattern.compile("\\B(\\#[a-zA-Z]+\\b)(?!;)");
        Matcher matcher = pattern.matcher(content);
        Set<Hashtag> hashtags = new HashSet<>();
        while (matcher.find()) {
            String match = matcher.group(1);
            Hashtag hashtag = new Hashtag();
            hashtag.setName(match);
            hashtags.add(hashtag);
            hashtagService.save(hashtag);
        }
        post.setTags(hashtags);
// lưu post
        postService.save(post);
        Iterable<Post> posts = postService.getAllByAppUserIs(user());
        ModelAndView modelAndView = new ModelAndView("personal");
        modelAndView.addObject("user", user());
        modelAndView.addObject("posts", posts);
        return modelAndView;
    }

    //Chi //delete post
    @GetMapping("/delete-post/{postID}")
    public ModelAndView deletePost(@PathVariable int postID) {
        postService.remove(postID);
        Iterable<Post> posts = postService.getAllByAppUserIs(user());
        List<Post> userPosts = StreamSupport.stream(posts.spliterator(), true).collect(Collectors.toList());
        int size = userPosts.size();
        ModelAndView modelAndView = new ModelAndView("personal");
        modelAndView.addObject("user", user());
        modelAndView.addObject("posts", posts);
        modelAndView.addObject("size", size);
        return modelAndView;
    }

    // Chi //Tìm tất cả bài viết theo tag
    @GetMapping("/findtag")
    public ModelAndView findByTag(@RequestParam String tag) {
        List<Post> posts = postService.findAllPostByTag('#' + tag);
        ModelAndView modelAndView = new ModelAndView("home");
        modelAndView.addObject("posts", posts);
        modelAndView.addObject("user", user());
        modelAndView.addObject("post", new Post());
        return modelAndView;
    }

    // CHi //Tìm tất cả bài viết theo tag trong trang cá nhân của mình
    @GetMapping("/find-by-tag-personal")
    public ModelAndView findByTagPersonal(@RequestParam String tag) {
        List<Post> posts = postService.findAllPostByTagIsAndAppUserIs('#' + tag, user());
        ModelAndView modelAndView = new ModelAndView("personal");
        modelAndView.addObject("posts", posts);
        modelAndView.addObject("user", user());

        return modelAndView;
    }

    // CHi //Tìm tất cả bài viết theo tag trong trang cá nhân của friend
    @GetMapping("/find-by-tag-friend")
    public ModelAndView findByTagFriend(@RequestParam(name = "tag") String tag, @RequestParam(name = "id") String id) {
        Post post = postService.findById(Integer.parseInt(id));
        List<Post> posts = postService.findAllPostByTagIsAndAppUserIs('#' + tag, post.getAppUser());
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
    @DeleteMapping("/remove-friend/{friendId}")
    public ResponseEntity<AppUser> removeFriendRequestAndRemoveFriend(@PathVariable(name = "friendId") int id) {
        usersService.removeFriendshipsByUser1IsAndUser2Is(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @GetMapping("/layout2")
    public String layout2() {
        return "layout2";
    }

    //Minh
    @GetMapping("/api/getuserfriend/")
    public ResponseEntity<Iterable<AppUser>> getUserFriends() {
        AppUser user = user();
        Iterable<AppUser> listUserFriend = usersService.searchAllFriendsByAppUser(user);
        return new ResponseEntity<>(listUserFriend, HttpStatus.OK);
    }

        //toan
    @GetMapping("/bell-notification")
    public ResponseEntity<Iterable<AppUser>> bellNotification() {
        List<AppUser> pendingUsers = usersService.searchAllUserByPendingRequestToCurrentUser();
        return new ResponseEntity<>(pendingUsers, HttpStatus.OK);
    }
      //toan ham` test ko dung`
      @PutMapping("/accept-friend-request/{friendId}")
      public ResponseEntity<AppUser> acceptFriendRequest(@PathVariable(name = "friendId",required = true) int id) {
          friendshipService.acceptFriendRequest(usersService.findById(id));
          return new ResponseEntity<>(HttpStatus.OK);
      }

    //toan accept friend request
      @GetMapping("/accept")
    public ResponseEntity<AppUser> accept(@RequestParam(name = "name")int id){
          friendshipService.acceptFriendRequest(usersService.findById(id));
          return new ResponseEntity<>(HttpStatus.OK);
      }

    //Minh
    @GetMapping("/api/searchFriendByName")
    public ResponseEntity<List<AppUser>> getFriendsByName(@RequestParam(name = "name", required = false) String inputName){
        List<AppUser> listFriendByName = usersService.searchAllFriendByNameContaining(inputName);
        return new ResponseEntity<>(listFriendByName, HttpStatus.OK);
    }

    //Minh
    @GetMapping("/searchfriendbyname**")
    public ModelAndView getFriendByName(@RequestParam(name = "input", required = false) String inputName){
        ModelAndView modelAndView = new ModelAndView("friendlist");
        List<AppUser> listFriendByName = usersService.searchAllFriendByNameContaining(inputName);
        modelAndView.addObject("listFriendByName", listFriendByName);
        return modelAndView;
    }

    //Minh
    @GetMapping("/api/removefriend/{friendId}")
    public ResponseEntity<AppUser> deleteFriendInList(@PathVariable (name = "friendId") int id){
        if (user().getUserId() < id){
            friendshipService.deleteByUser1UserIdAndUser2UserId(user().getUserId(), id);
        } else {
            friendshipService.deleteByUser1UserIdAndUser2UserId(id, user().getUserId());
        }
        return  new ResponseEntity<>(HttpStatus.OK);
    }

    //Minh
    @GetMapping("/api/getpostcommentbypost/{postID}")
    public ResponseEntity<List<PostComment>> getAllPostComment(@PathVariable (name = "postID") int postID){
        List<PostComment> postComments = postService.findById(postID).getPostComments();
        return new ResponseEntity<>(postComments, HttpStatus.OK);
    }

    //Minh
    @PutMapping("/api/createpostcomment/{postId}")
    public ResponseEntity<Iterable<PostComment>> createPostComment(@PathVariable(name = "postId") int postId, @RequestBody PostComment postComment){
        PostComment comment = postComment;
        Post post = postService.findById(postId);
        comment.setUser(user());
        comment.setPost(post);
        postCommentService.save(comment);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //Minh
    @GetMapping("/api/getpostlikebypost/{postID}")
    public ResponseEntity<Set<PostLike>> getPostLikes(@PathVariable (name = "postID") int postID){
        Post post = postService.findById(postID);
        Set<PostLike> postLikes = post.getPostLikes();
        return new ResponseEntity<>(postLikes, HttpStatus.OK);
    }

    //Minh
    @PutMapping("/api/likepost/{postID}")
    public ResponseEntity<PostLike> likePost(@PathVariable (name = "postID") int postID){
        PostLike postLike = new PostLike();
        Post post = postService.findById(postID);
        List<PostLike> checkList = (List<PostLike>) postlikeService.findAll();
        boolean flag = false;
        int pos = 0;

        for (int i = 0; i < checkList.size(); i++) {
            if (checkList.get(i).getUser() == user() && checkList.get(i).getPost() == post){
                flag = true;
                pos = checkList.get(i).getPostLikeID();
                break;
            }
        }

        if (flag == false){
            postLike.setPost(post);
            postLike.setUser(user());
            postlikeService.save(postLike);
        } else {
            postlikeService.remove(pos);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }
}

