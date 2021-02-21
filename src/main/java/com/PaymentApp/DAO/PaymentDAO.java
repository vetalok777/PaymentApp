package com.PaymentApp.DAO;

import com.PaymentApp.DTO.PaymentDTO;
import com.PaymentApp.entities.Payment;
import com.PaymentApp.entities.User;

import java.sql.SQLException;
import java.util.List;

public interface PaymentDAO {
    int insertPayment(Payment payment) throws SQLException;

    int updatePaymentStatus(String status, Integer id) throws SQLException;

    Integer getNumberOfRows(Integer userId) throws SQLException;

    List<PaymentDTO> getPaymentsRecords(User user, int currentPage, int recordsPerPage, String str) throws SQLException;

}
