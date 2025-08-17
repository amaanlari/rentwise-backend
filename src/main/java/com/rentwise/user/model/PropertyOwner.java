package com.rentwise.user.model;

/*
id: BIGINT, Primary Key
userId: BIGINT, One-to-One relationship with User
accountConfig: VARCHAR(50), Enum: BUILDING, ROOM
*/

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PropertyOwner {
    private String accountConfig;
    private Long userId;
}
