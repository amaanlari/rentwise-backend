package com.rentwise.user.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Tenant {
    private Long tenantId;
    private Long userId;
    private Long unitId;
}
