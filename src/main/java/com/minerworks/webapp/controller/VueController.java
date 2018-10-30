package com.minerworks.webapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class VueController {

    @RequestMapping(value = "/Login")
    public String Login() {
        return "forward:/index.html";
    }

    @RequestMapping(value = "/Farms")
    public String Farms() {
        return "forward:/index.html";
    }

    @RequestMapping(value = "/Farm")
    public String Farm() {
        return "forward:/index.html";
    }

    @RequestMapping(value = "/Farm/{id}")
    public String FarmId() {
        return "forward:/index.html";
    }

    @RequestMapping(value = "/Settings")
    public String Settings() {
        return "forward:/index.html";
    }

    @RequestMapping(value = "/Logout")
    public String Logout() {
        return "forward:/index.html";
    }

}
