package com.rentwise.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.Instant;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "owner", indexes = {
        @Index(name = "idx_owner_email", columnList = "email, deleted"),
        @Index(name = "idx_owner_phone_number", columnList = "phone_number, deleted"),
})
public class Owner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    String name;

    @Column(unique = true, nullable = false)
    String email;

    @Column(unique = true, nullable = false)
    String phoneNumber;

    @Column(nullable = false)
    String password;

    @Column(nullable = false, columnDefinition = "boolean default false")
    boolean deleted;

    @CreationTimestamp
    @CreatedDate
    Instant createdAt;

    @UpdateTimestamp
    @LastModifiedDate
    Instant updatedAt;
}
