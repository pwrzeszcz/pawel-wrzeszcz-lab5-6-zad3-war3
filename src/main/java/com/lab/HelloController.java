package com.lab;

import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.facebook.api.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HelloController {

    private Facebook facebook;
    private ConnectionRepository connectionRepository;

    public HelloController(Facebook facebook, ConnectionRepository connectionRepository)
    {
        this.facebook = facebook;
        this.connectionRepository = connectionRepository;
    }

    @GetMapping
    public String helloFacebook(Model model)
    {
        if (connectionRepository.findPrimaryConnection(Facebook.class) == null)
        {
            return "redirect:/connect/facebook";
        }

        String [] fields = { "id", "email",  "first_name", "last_name" };
        User userProfile = facebook.fetchObject("me", User.class, fields);

        model.addAttribute("facebookProfile", userProfile);
        PagedList<User> friends = facebook.friendOperations().getFriendProfiles();
        model.addAttribute("friends", friends);

        return "friends";
    }

}