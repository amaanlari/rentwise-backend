package com.rentwise.util.user;

import org.springframework.stereotype.Component;

@Component
public class UserUtil {

    public String generateSlug(String firstName, String lastName) {
        return (firstName + "-" + lastName).toLowerCase().replaceAll("[^a-z0-9]+", "-");
    }

    public Long generateUserId(long size) {
        return size;
    }
}
