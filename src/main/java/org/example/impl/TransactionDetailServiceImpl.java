package org.example.impl;

import org.example.dao.Account;
import org.example.dao.TransactionDetail;
import org.example.repository.TransactionDetailRepo;
import org.example.service.TransactionDetailService;

import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class TransactionDetailServiceImpl implements TransactionDetailService {
    private Account account;
    private TransactionDetailRepo transactionDetailRepo = new TransactionDetailRepo();
    public TransactionDetailServiceImpl(Account account){
        this.account = account;
    }
    @Override
    public void showTransactionScreen() {
        System.out.println("\n========== Welcome to Anurag's ATM ============");
        while(true){
            System.out.print("\n1.Check Balance\n2.Mini Statement\n3.Withdraw Amount\n4.Deposit Amount\n5.Exit\n");
            System.out.print("Enter your choice: ");
            Scanner sc = new Scanner(System.in);
            int ch = sc.nextInt();
            switch (ch){
                case 1:
                    Integer balance = transactionDetailRepo.getTotalAccountBalance(account.getAccountNumber());
                    if (Objects.isNull(balance)){
                        System.out.println("Server error while fetching account balance. Please try again later..");
                    }else{
                        System.out.println("Your account balance is "+ balance);
                    }
                    break;
                case 2:
                    List<TransactionDetail> transactionDetailList = transactionDetailRepo.getTransactionDetailsByAtmNumberOrderByModifiedDate(account.getAccountNumber());
                    if(Objects.isNull(transactionDetailList)){
                        System.out.println("could not fetch mini statement due to server error. please try again later...");
                    } else if (transactionDetailList.isEmpty()) {
                        System.out.println("No transaction done for this account !!");
                    }else {
                        System.out.printf("| %-5s | %-10s | %-10s | %-15s | %n","sno","debit","credit","total");
                        int sno = 1;
                        for(TransactionDetail transactionDetail : transactionDetailList){
                            System.out.printf("| %-5d | %-10d | %-10d | %-15d | %n",sno, transactionDetail.getDebit(),transactionDetail.getCredit(),transactionDetail.getTotal());
                            sno++;
                        }
                    }
                    break;
                case 3:
                    System.out.print("Enter amount to withdraw: ");
                    int withdraw = sc.nextInt();
                    Integer currentBalance = transactionDetailRepo.getTotalAccountBalance(account.getAccountNumber());
                    if(Objects.isNull(currentBalance)){
                        System.out.println("Cannot withdraw due to server error.Please try again later...");
                        break;
                    }
                    if(withdraw>currentBalance){
                        System.out.println("Insufficient balance in your account !!");
                        break;
                    }
                    TransactionDetail withdrawTransaction = new TransactionDetail();
                    withdrawTransaction.setAtmNumber(account.getAccountNumber());
                    withdrawTransaction.setDebit(withdraw);
                    withdrawTransaction.setCredit(0);
                    withdrawTransaction.setTotal(currentBalance - withdraw);
                    TransactionDetail transactionDetail1 = transactionDetailRepo.save(withdrawTransaction);
                    if(Objects.isNull(transactionDetail1)){
                        System.out.println("Withdraw failed !!");
                    }else{
                        System.out.println("Withdrawn "+withdraw+" successfully !!");
                    }
                    break;
                case 4:
                    System.out.println("\nEnter amount to deposit: ");
                    int amount = sc.nextInt();
                    Integer totalBalance = transactionDetailRepo.getTotalAccountBalance(account.getAccountNumber());
                    if (Objects.isNull(totalBalance)){
                        System.out.println("could not deposit amount due to server issue. Please try again..");
                        break;
                    }
                    TransactionDetail transactionDetail = new TransactionDetail();
                    transactionDetail.setAtmNumber(account.getAccountNumber());
                    transactionDetail.setDebit(0);
                    transactionDetail.setCredit(amount);
                    transactionDetail.setTotal(totalBalance + amount);
                    TransactionDetail transactionDetail2 = transactionDetailRepo.save(transactionDetail);
                    if(Objects.isNull(transactionDetail2)){
                        System.out.println("deposit "+amount+" failed !!");
                    }else{
                        System.out.println("deposit "+amount+" successfull !!");
                    }
                    break;
                case 5:
                    System.out.println("=========== Thank you for using Anurag atm ==========");
                default:
                    System.out.println("Invalid choice...");
                    break;
            }
            if(ch == 5){
                break;
            }
        }
    }
}
