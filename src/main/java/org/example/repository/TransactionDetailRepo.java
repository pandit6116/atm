package org.example.repository;

import org.apache.log4j.Logger;
import org.example.dao.TransactionDetail;
import org.example.jdbc.DBConnect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class TransactionDetailRepo {
    private Connection conn;
    static Logger logger= Logger.getLogger(TransactionDetailRepo.class);
    public void TransactionDetailRepo(){
        this.conn = DBConnect.getConn();
    }
    public TransactionDetail save(TransactionDetail transactionDetail){

        try{
            conn = DBConnect.getConn();
            String sql = "insert into transaction_detail(atm_number, debit, credit, total) values(?,?,?,?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setLong(1,transactionDetail.getAtmNumber());
            ps.setInt(2, transactionDetail.getDebit());
            ps.setInt(3, transactionDetail.getCredit());
            ps.setInt(4, transactionDetail.getTotal());
            int row  = ps.executeUpdate();
            if(row>0){
                logger.info("TransactionDetails saved successfully !!");
                return transactionDetail;
            }else{
                logger.info("Could not save transaction detail !!");
            }
        }catch(Exception e){
            logger.error("exception while saving transaction detail", e);
        }
        return null;
    }
    public List<TransactionDetail> getTransactionDetailsByAtmNumberOrderByModifiedDate(Long atmNumber){
        try{
            conn = DBConnect.getConn();
            String sql = "select * from transaction_detail where atm_number = ? order by modified_date desc";
            PreparedStatement ps= conn.prepareStatement(sql);
            ps.setLong(1,atmNumber);
            ResultSet rs = ps.executeQuery();
            if(Objects.isNull(rs)){
                logger.info("No record found for atm number "+ atmNumber);
                return Collections.emptyList();
            }else{
                return convertResultSetToDao(rs);
            }
        }catch(Exception e){
            logger.error("exception while fetching transaction detail for atm number "+atmNumber, e);
        }
        return null;
    }

    private List<TransactionDetail> convertResultSetToDao(ResultSet rs) throws SQLException {
        List<TransactionDetail> transactionDetails = new ArrayList<>();
       while(rs.next()){
           TransactionDetail transactionDetail = new TransactionDetail();
           transactionDetail.setTid(rs.getInt(1));
           transactionDetail.setAtmNumber(rs.getLong(2));
           transactionDetail.setDebit(rs.getInt(3));
           transactionDetail.setCredit(rs.getInt(4));
           transactionDetail.setTotal(rs.getInt(5));
           transactionDetail.setCreatedDate(rs.getDate(6));
           transactionDetail.setModifiedDate(rs.getDate(7));
           transactionDetails.add(transactionDetail);
       }
        return transactionDetails;
    }

    public Integer getTotalAccountBalance(Long atmNumber){
        try{
            conn = DBConnect.getConn();
            String sql = "select * from transaction_detail where atm_number = ? order by modified_date desc limit 1";
            PreparedStatement ps= this.conn.prepareStatement(sql);
            ps.setLong(1,atmNumber);
            ResultSet rs = ps.executeQuery();
            if(Objects.isNull(rs)){
                logger.info("No record found for atm number "+ atmNumber);
                return 0;
            }else{
                while(rs.next()){
                    return rs.getInt(5);
                }
                return 0;
            }
        }catch(Exception e){
            logger.error("exception while fetching transaction detail for atm number "+atmNumber, e);
        }
        return null;
    }
}
