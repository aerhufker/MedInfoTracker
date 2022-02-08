package com.medinfotracker.medinfotracker.controllers;

import com.medinfotracker.medinfotracker.models.User;
import com.medinfotracker.medinfotracker.models.data.UserRepository;
import com.medinfotracker.medinfotracker.models.dto.LoginFormDTO;
import com.medinfotracker.medinfotracker.models.dto.RegisterFormDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Optional;

@Controller
public class AuthenticationController {

        @Autowired
        UserRepository userRepository;

        private static final String userSessionKey = "user";

        public User getUserFromSession(@NotNull HttpSession session) {
            Integer id = (Integer) session.getAttribute(userSessionKey);
            if (id == null) {
                return null;
            }

            Optional<User> user = userRepository.findById(id);

            if (user.isEmpty()) {
                return null;
            }

            return user.get();
        }

        private static void setUserInSession(HttpSession session, User user) {
            session.setAttribute(userSessionKey, user.getId());
        }

        @GetMapping("/register")
        public String displayRegistrationForm(Model model) {
            model.addAttribute(new RegisterFormDTO());
            model.addAttribute("title", "Register");
            return "register";
        }

        @PostMapping("/register")
        public String processRegistrationForm(@ModelAttribute @Valid RegisterFormDTO registerFormDTO,
                                              Errors errors, HttpServletRequest request,
                                              Model model) {

            if (errors.hasErrors()) {
                model.addAttribute("title", "Register");
                return "register";
            }

            User existingUser = userRepository.findByUserName(registerFormDTO.getUserName());

            if (existingUser != null) {
                errors.rejectValue("userName", "userName.alreadyexists", "That username is already in use.");
                model.addAttribute("title", "Register");
                return "register";
            }

            String password = registerFormDTO.getPassword();
            String verifyPassword = registerFormDTO.getVerifyPassword();
            if (!password.equals(verifyPassword)) {
                errors.rejectValue("password", "passwords.mismatch", "Passwords entered do not match");
                model.addAttribute("title", "Register");
                return "register";
            }

            User newUser = new User(registerFormDTO.getUserName(), registerFormDTO.getPassword());
            userRepository.save(newUser);
            setUserInSession(request.getSession(), newUser);

            return "redirect:";
        }

        @GetMapping("/login")
        public String displayLoginForm(Model model) {
            model.addAttribute(new LoginFormDTO());
            model.addAttribute("title", "Log In");
            return "login";
        }

        @PostMapping("/login")
        public String processLoginForm(@ModelAttribute @Valid LoginFormDTO loginFormDTO,
                                       Errors errors, HttpServletRequest request,
                                       Model model) {

            if (errors.hasErrors()) {
                model.addAttribute("title", "Log In");
                return "login";
            }

            User theUser = userRepository.findByUserName(loginFormDTO.getUserName());

            if (theUser == null) {
                errors.rejectValue("userName", "user.invalid", "The given username does not exist");
                model.addAttribute("title", "Log In");
                return "login";
            }

            String password = loginFormDTO.getPassword();

            if (!theUser.isMatchingPassword(password)) {
                errors.rejectValue("password", "password.invalid", "Invalid password");
                model.addAttribute("title", "Log In");
                return "login";
            }

            setUserInSession(request.getSession(), theUser);

            return "redirect:";
        }

        @GetMapping("/logout")
        public String logout(HttpServletRequest request){
            request.getSession().invalidate();
            return "redirect:/login";
        }
    }
