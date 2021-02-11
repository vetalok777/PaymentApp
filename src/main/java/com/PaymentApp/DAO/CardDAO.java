package com.PaymentApp.DAO;

import com.PaymentApp.entities.Card;
import com.PaymentApp.entities.User;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

public interface CardDAO {
    int insertCard(Card card) throws SQLException;

    List<Card> findAllCards(User user) throws SQLException;

    int updateBalance(Integer id, BigDecimal value) throws SQLException;
}
