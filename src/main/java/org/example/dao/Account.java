package org.example.dao;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Account {
    private Long accountNumber;
    private int pin;
}
