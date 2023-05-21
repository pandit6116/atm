package org.example.repository;

import org.apache.log4j.Logger;
import org.example.dao.Account;
import org.example.jdbc.DBConnect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AccountRepo {
    private Connection conn;
    static Logger logger= Logger.getLogger(AccountRepo.class);
    public void AccountRepo(){
        this.conn = DBConnect.getConn();
    }
    public Account saveAccount(Account account){
        try {
            conn = DBConnect.getConn();
            String sql = "insert into account values(?,?)";

            PreparedStatement ps = this.conn.prepareStatement(sql);
            ps.setLong(1,account.getAccountNumber());
            ps.setInt(2,account.getPin());

            int row = ps.executeUpdate();
            if(row>0){
                logger.info(" Account created successfully");
                return account;
            }else{
                logger.info("could not create account");
            }
            return null ;
        } catch (Exception e) {
            logger.error("Error creating account",e);
            return null;
        }
    }

    public boolean isValidAccount(Account account){
        try{
            conn = DBConnect.getConn();
            String sql = "select * from account where atm_number = ? and pin = ?";
            PreparedStatement ps = this.conn.prepareStatement(sql);
            ps.setLong(1,account.getAccountNumber());
            ps.setInt(2,account.getPin());
            ResultSet resultSet = ps.executeQuery();
            if(resultSet.next()){
                logger.info("Valid credential");
                return true;
            }
            else{
                logger.info("Invalid credential");
            }
        }catch (Exception e){
            logger.error("Exception while fetching acccount info",e);
        }
        return false;
    }
}
