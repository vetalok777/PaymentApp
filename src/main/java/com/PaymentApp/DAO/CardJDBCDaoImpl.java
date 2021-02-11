package com.PaymentApp.DAO;

import com.PaymentApp.entities.Card;
import com.PaymentApp.entities.User;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CardJDBCDaoImpl implements CardDAO {

    private static final String URL = "jdbc:mysql://localhost/payment_system_db?serverTimezone=UTC";
    private static final String PASSWORD = "root";
    private static final String USERNAME = "root";
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String INSERT_NEW_CARD = "INSERT INTO card (card_name, card_number, balance, user_id) VALUES (?, ?, ?, ?);";
    private static final String FIND_ALL_CARDS = "SELECT id, card_number, card_name, balance, card_status, user_id FROM card WHERE user_id=(?);";
    private static final String UPDATE_CARD_BALANCE = "UPDATE card SET card.balance = card.balance + (?) WHERE id=(?);";

    private static CardJDBCDaoImpl cardJDBCDaoImpl;

    public CardJDBCDaoImpl() {
        try {
            Class.forName(DRIVER);
            System.out.println("Successfully!");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static synchronized CardJDBCDaoImpl getInstance() {
        if (cardJDBCDaoImpl == null) {
            cardJDBCDaoImpl = new CardJDBCDaoImpl();
        }
        return cardJDBCDaoImpl;
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }


    @Override
    public int insertCard(Card card) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        CardJDBCDaoImpl cardJDBCDaoImpl = CardJDBCDaoImpl.getInstance();
        int result = 0;
        try {
            connection = cardJDBCDaoImpl.getConnection();
            preparedStatement = connection.prepareStatement(INSERT_NEW_CARD);
            preparedStatement.setString(1, card.getName());
            preparedStatement.setString(2, card.getNumber());
            preparedStatement.setBigDecimal(3, card.getBalance());
            preparedStatement.setInt(4, card.getUserId());
            result = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
        return result;
    }

    @Override
    public List<Card> findAllCards(User user) throws SQLException {
        List<Card> cards = new ArrayList<>();
        ResultSet rs = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = cardJDBCDaoImpl.getConnection();
            preparedStatement = connection.prepareStatement(FIND_ALL_CARDS);
            preparedStatement.setInt(1, user.getId());
            rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Integer id = rs.getInt("id");
                String number = rs.getString("card_number");
                String name = rs.getString("card_name");
                BigDecimal balance = rs.getBigDecimal("balance");
                Integer status = rs.getInt("card_status");
                Integer userId = rs.getInt("user_id");
                Card card = new Card(id, name, number, balance, userId, status);
                cards.add(card);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
        return cards;
    }

    public int updateBalance(Integer id, BigDecimal value) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        CardJDBCDaoImpl cardJDBCDaoImpl = CardJDBCDaoImpl.getInstance();
        int result = 0;
        try {
            connection = cardJDBCDaoImpl.getConnection();
            preparedStatement = connection.prepareStatement(UPDATE_CARD_BALANCE);
            preparedStatement.setBigDecimal(1, value);
            preparedStatement.setInt(2, id);
            result = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
        return result;
    }

    public static String randomCardNumber() {
        BigInteger maxLimit = new BigInteger("9999999999999999");
        BigInteger minLimit = new BigInteger("1000000000000000");
        BigInteger bigInteger = maxLimit.subtract(minLimit);
        Random randNum = new Random();
        int len = maxLimit.bitLength();
        BigInteger res = new BigInteger(len, randNum);
        if (res.compareTo(minLimit) < 0)
            res = res.add(minLimit);
        if (res.compareTo(bigInteger) >= 0)
            res = res.mod(bigInteger).add(minLimit);
        return res.toString();
    }

    public static void main(String[] args) throws SQLException {
        cardJDBCDaoImpl = CardJDBCDaoImpl.getInstance();
        System.out.println(cardJDBCDaoImpl.updateBalance(3, new BigDecimal("20.23")));
    }
}
