package com.mtech.assgnproj.controller;

import java.util.Date;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.mtech.assgnproj.model.User;
import com.mtech.assgnproj.service.UserService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


@RestController
@RequestMapping("user")
public class UserController {

	@Autowired
	UserService userService;
	
	@Value("${jwt.secret}")
    private String jwtSecret;
	
    @RequestMapping(value = { "/register" }, method = { RequestMethod.POST }, produces = {"application/json;charset=utf-8" })
	@ResponseBody
    public ResponseEntity<Object> registerUser(@RequestBody User user) {
        if (user.getName() == null || user.getEmail() == null || user.getPassword() == null) {
            return ResponseEntity.badRequest().build();
        }

        if (userService.findUserByEmail(user.getEmail()) != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email is already registered");
        }

        User registeredUser = userService.registerUser(user);
        
        String jwtToken = Jwts.builder()
                .setSubject(registeredUser.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 864000000))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
        
        JSONObject output = new JSONObject();
        output.put("jwtToken", jwtToken);
        output.put("userid", registeredUser.getEmail());

        return ResponseEntity.status(HttpStatus.OK).body(output);
    }
    
    @RequestMapping(value = { "/login" }, method = { RequestMethod.POST }, produces = {"application/json;charset=utf-8" })
	@ResponseBody
    public ResponseEntity<Object> loginUser(@RequestBody User user) {
        JSONObject output = new JSONObject();

    	try {
			if (userService.findUserByEmailAndPassword(user.getEmail(), user.getPassword()) == null) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User not found");
			} else {
				String jwtToken = Jwts.builder().setSubject(user.getEmail()).setIssuedAt(new Date())
						.setExpiration(new Date(System.currentTimeMillis() + 864000000))
						.signWith(SignatureAlgorithm.HS512, jwtSecret).compact();

				output.put("jwtToken", jwtToken);
				output.put("userid", user.getEmail());
			} 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.OK).body(output);
    }
    
    
}
