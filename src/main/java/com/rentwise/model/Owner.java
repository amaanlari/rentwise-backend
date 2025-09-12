package com.rentwise.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.Instant;

@Builder
@Entity
@Table(name = "owners")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Owner {
    @Id
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "name", nullable = false)
    String name;

    @Column(name = "email", unique = true, nullable = false)
    String email;

    @Column(name = "phone_number", unique = true, nullable = false)
    String phoneNumber;

    @Column(name = "password", nullable = false)
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
