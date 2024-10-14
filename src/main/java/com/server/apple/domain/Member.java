package com.server.apple.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Bean;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Member {

    @Id
    private String id;

    @Column
    private String password;

    @Column
    private String name;

    @Column
    private String email;

    @Column
    private String phone;


}
