package com.PaymentApp.DAO;

import com.PaymentApp.entities.Card;
import com.PaymentApp.entities.User;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

public interface CardDAO {
    int insertCard(Card card) throws SQLException;

    List<Card> findAllCards(User user, String sort) throws SQLException;

    int updateBalance(Integer id, BigDecimal value) throws SQLException;

    BigDecimal getCardBalance(Card card) throws SQLException;

    boolean findCard(String number) throws SQLException;

    int decreaseBalance(Integer id, BigDecimal value) throws SQLException;

    boolean findCardByName(String name, User user) throws SQLException;

    int getCardId(String number) throws SQLException;

    Integer getCardStatus(Card card) throws SQLException;

    int blockCardById(Integer cardId) throws SQLException;

    int changeCardStatus(Integer cardId) throws SQLException;
}
