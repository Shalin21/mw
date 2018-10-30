package com.minerworks.webapp.controller;

import com.minerworks.webapp.model.ApiResponse;
import com.minerworks.webapp.model.Role;
import com.minerworks.webapp.model.SignUpRequest;
import com.minerworks.webapp.model.User;
import com.minerworks.webapp.repository.FarmEntityRepository;
import com.minerworks.webapp.repository.RoleRepository;
import com.minerworks.webapp.repository.UserRepository;
import com.minerworks.webapp.service.MessageSender;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Collections;
import java.util.Map;

@RestController
public class SecuredRestController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private FarmEntityRepository farmEntityRepository;

    @Autowired
    private MessageSender sender;

    @Autowired
    private Map<String, String> userMap;

    @RequestMapping(value="/user", method = RequestMethod.POST)
    public String listUser(HttpServletRequest request, @RequestBody String command){
        JSONObject commandObject = new JSONObject(command);

        String sessionId = userMap.get(request.getHeader("Username"));
        if(sessionId != null) {
            sender.sendEventToClient(commandObject.toString(), sessionId);
        }

        System.out.println(commandObject.get("Message").toString());
        System.out.println(request.getHeader("Username"));

        return "fuckoff";
    }

    @RequestMapping(value="/toggle", method = RequestMethod.POST)
    public void toggle(HttpServletRequest request, @RequestBody String command){
       // JSONObject commandObject = new JSONObject(command);

        String sessionId = userMap.get(request.getHeader("Username"));

        if(sessionId != null) {
            sender.sendEventToClient(command.toString(), sessionId);
        }

        //System.out.println(commandObject.get("Message").toString());
        System.out.println(command);
        System.out.println(request.getHeader("Username"));

        //return "fuckoff";
    }

//    @RequestMapping(value = "/registration", method = RequestMethod.POST)
//    public int createNewUser(@Valid User user, BindingResult bindingResult) {
//        User userExists = userService.findUserByEmail(user.getEmail());
//        if (userExists != null) {
//            return Response.SC_CONFLICT;
//        }
//         else { userService.saveUser(user);
//           return Response.SC_OK;
//
//        }
//
//    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return new ResponseEntity(new ApiResponse(false, "Username is already taken!"),
                    HttpStatus.BAD_REQUEST);
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return new ResponseEntity(new ApiResponse(false, "Email Address already in use!"),
                    HttpStatus.BAD_REQUEST);
        }
        User user = new User();
        user.setName(signUpRequest.getName());
        user.setEmail(signUpRequest.getEmail());
        //user.setPassword(signUpRequest.getPassword());
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));

        Role userRole = roleRepository.findByRole("ROLE_USER");


        user.setRoles(Collections.singleton(userRole));

        userRepository.save(user);

        return new ResponseEntity(new ApiResponse(true, "User was created"),
                HttpStatus.OK);
    }

//    @RequestMapping(value = "/user", method = RequestMethod.POST)
//    public User create(@RequestBody User user){
//        return userService.save(user);
//    }

//    @RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
//    public String delete(@PathVariable(value = "id") Long id){
//        userService.delete(id);
//        return "success";
//    }

}
