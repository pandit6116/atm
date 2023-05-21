package org.example.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDetail {
    private int tid;
    private Long atmNumber;
    private Integer debit;
    private Integer credit;
    private Integer total;
    private Date createdDate;
    private Date modifiedDate;
}
