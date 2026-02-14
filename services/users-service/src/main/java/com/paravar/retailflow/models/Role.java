package com.paravar.retailflow.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "roles")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Role {

    @Id
    @Column(name = "id", length = 36)
    private String id;

    @Column(nullable = false, unique = true, length = 50)
    private String name;           // e.g. ROLE_USER, ROLE_ADMIN, ROLE_SELLER

    @Column(length = 255)
    private String description;
}