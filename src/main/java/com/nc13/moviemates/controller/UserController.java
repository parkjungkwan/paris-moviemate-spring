package com.nc13.moviemates.controller;

import com.nc13.moviemates.component.model.UserModel;
import com.nc13.moviemates.entity.HistoryEntity;
import com.nc13.moviemates.entity.UserEntity;
import com.nc13.moviemates.service.HistoryService;
import com.nc13.moviemates.service.UserService;
import com.nc13.moviemates.serviceImpl.UserServiceImpl;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final UserService service;
    private final HistoryService historyService;
    private final UserService userService;

    @GetMapping("/mypage/{id}")
    public String getList(Model model, @PathVariable Long id){
        Optional<UserEntity> userOptional = userService.findById(id);
        if (userOptional.isPresent()) {
            model.addAttribute("user", userOptional.get());  // 값이 있으면 ReviewEntity를 넘김
        } else {
            throw new RuntimeException("User not found");
        }
        HistoryEntity histories = historyService.findById(id).orElse(null);
        model.addAttribute("histories", histories);
        return "profile/main";
    }




    @GetMapping("/login")
    public String login() {
        return "admin/auth-login";
    }

    /*@GetMapping("/")
    public String loginSuccess(@AuthenticationPrincipal OAuth2User principal, Model model) {
        model.addAttribute("name", principal.getAttribute("name"));
        model.addAttribute("email", principal.getAttribute("email"));
        return "admin/login"; // loginSuccess.html 뷰를 반환
    }*/



    @GetMapping("/login/oauth2/code/google")
    public String loginOAuth() {
        return "login";
    }

    @GetMapping("/logout")
    public String logout() {
        return "redirect:/";
    }


    @GetMapping("/list")
    public ResponseEntity<List<?>> getList() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        if (service.findById(id).isPresent()) {
            return ResponseEntity.ok(service.findById(id).get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

    }

    @GetMapping("/register")
    public String showRegisterPage() {
        return "register"; // register.html을 반환
    }

    @ResponseBody
    @PostMapping("/register")
    public ResponseEntity<Boolean> insert(@RequestBody UserEntity user) {
        System.out.println(user);
        return ResponseEntity.ok(service.insert(user));
    }

    @GetMapping("/profile/setting/{id}")
    public String getProfile(Model model, @PathVariable Long id)
    {
        Optional<UserEntity> userOptional = service.findById(id);
        if(userOptional.isPresent()) {
            UserEntity user = userOptional.get();
            // user의 필드를 다루는 로직을 추가할 수 있습니다.
        }
        model.addAttribute("userId", 1);
        model.addAttribute("user", userOptional.orElse(null));
        System.out.println(userOptional.get());
        System.out.println(userOptional);
        return "profile/setting";}

    @ResponseBody
    @PostMapping("/updateMany")
    public ResponseEntity<Boolean> updateByJspreadsheet(@RequestBody List<UserModel> userList) {
        return ResponseEntity.ok(service.update(userList));
    }

    @PutMapping("/update")
    public ResponseEntity<Boolean> update(@RequestBody UserEntity user) {
        return ResponseEntity.ok(service.save(user));
    }
    @ResponseBody
    @PostMapping("/update/{userId}")
    public ResponseEntity<Boolean> update(@RequestPart("userData") UserModel userData, @RequestPart("password") String password,
                                          @RequestPart(value = "file", required = false) MultipartFile file) {
        System.out.println("넘어온 값" + userData);
        System.out.println("넘어온 값" + password);
        if(!service.existsByPassword(password)){
            return ResponseEntity.ok(false);
        }
        return ResponseEntity.ok(service.updateUserInfo(userData, file));
    }

    @ResponseBody
    @PostMapping("/deleteMany")
    public ResponseEntity<Boolean> deleteMany(@RequestBody List<Long> userIdList){
        return ResponseEntity.ok(service.deleteMany(userIdList));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable Long id) {
        return ResponseEntity.ok(service.deleteById(id));
    }

    @GetMapping("/quantity")
    public ResponseEntity<Long> count() {
        return ResponseEntity.ok(service.count());
    }

    @GetMapping("/presence/{id}")
    public ResponseEntity<Boolean> existsById(@PathVariable Long id) {
        return ResponseEntity.ok(service.existsById(id));
    }
}
