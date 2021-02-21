package com.PaymentApp.DAO;

import com.PaymentApp.entities.UnblockRequest;

import java.sql.SQLException;
import java.util.List;

public interface UnblockRequestDAO {
    int insertRequest(UnblockRequest unblockRequest) throws SQLException;

    int findRequest(Integer cardId) throws SQLException;

    List<UnblockRequest> findAllRequests() throws SQLException;

    int changeRequestStatus(Integer id, String status) throws SQLException;
}
