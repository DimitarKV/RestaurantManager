package com.dim.RestaurantManager.web.rest;

import com.dim.RestaurantManager.service.UserService;
import com.dim.RestaurantManager.web.rest.responses.UsernameAvailabilityResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UsernameAvailabilityController {

    private final UserService userService;

    public UsernameAvailabilityController(UserService userService) {
        this.userService = userService;
    }

    // NOTE: This feature is for demonstration purposes only.
    // In a production app without additional security this is considered to be a security breach.
    @GetMapping("/user/register/check/{username}")
    public ResponseEntity<UsernameAvailabilityResponse> usernameAvailable(@PathVariable(name = "username") String username) throws InterruptedException {
        Thread.sleep(1000);
        if (this.userService.usernameExists(username))
            return ResponseEntity.ok(new UsernameAvailabilityResponse().setAvailable(false));
        return ResponseEntity.ok(new UsernameAvailabilityResponse().setAvailable(true));
    }
}
