package com.tumtech.authservice.model;

import com.tumtech.authservice.enums.AccountStatus;
import com.tumtech.authservice.enums.AccountType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String accountNo;
@ManyToOne
    private Users users;
    private String accountName;
    private BigDecimal accountBalance;
    private LocalDateTime createdOn;
    private LocalDateTime updated;
    private AccountType accountType;
    private AccountStatus accountStatus;

}
