package com.PaymentApp.DAO;

import com.PaymentApp.entities.Card;
import com.PaymentApp.entities.User;
import org.apache.log4j.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
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
    private static final String FIND_ALL_CARDS = "SELECT id, card_number, card_name, balance, card_status, user_id FROM card WHERE user_id=(?)";
    private static final String UPDATE_CARD_BALANCE = "UPDATE card SET card.balance = card.balance + (?) WHERE id=(?);";
    private static final String GET_CARD_BALANCE = "SELECT balance FROM card WHERE card_number=(?);";
    private static final String FIND_CARD = "SELECT card_number FROM card WHERE card_number=(?);";
    private static final String GET_CARD_ID = "SELECT id FROM card WHERE card_number=(?);";
    private static final String DECREASE_CARD_BALANCE = "UPDATE card SET card.balance = card.balance - (?) WHERE id=(?);";

    private static final String FIND_CARD_BY_NAME = "SELECT card_name FROM card WHERE (card_name=? AND user_id=?);";
    private static final String GET_CARD_STATUS = "SELECT card_status FROM card WHERE card_number=(?);";
    private static final String BLOCK_CARD = "UPDATE card SET card.card_status = 0 WHERE id=(?);";
    private static final String UPDATE_CARD_STATUS = "UPDATE card SET card.card_status = 1 WHERE id=(?);";
    private static CardJDBCDaoImpl cardJDBCDaoImpl;
    final Logger LOGGER = Logger.getLogger(CardJDBCDaoImpl.class);
    public CardJDBCDaoImpl() {
        try {
            Class.forName(DRIVER);
            LOGGER.info("Driver connected successfully");
        } catch (ClassNotFoundException e) {
            LOGGER.error(e.getMessage());
        }
    }

    public static synchronized CardJDBCDaoImpl getInstance() {
        if (cardJDBCDaoImpl == null) {
            cardJDBCDaoImpl = new CardJDBCDaoImpl();
        }
        return cardJDBCDaoImpl;
    }

    public Connection getConnection() throws SQLException {
        Connection connection = null;
        try {
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:/comp/env");


            DataSource ds = (DataSource) envContext.lookup("jdbc/PaymentDB");
            connection = ds.getConnection();
            LOGGER.info("Connection successfully");
        } catch (NamingException ex) {
            LOGGER.error(ex.getMessage());
        }
        return connection;
    }


    public void commitAndClose(Connection con) {
        try {
            con.commit();
            con.close();
            LOGGER.info("Connection commit and close successfully");
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
        }
    }

    public void rollbackAndClose(Connection con) {
        try {
            con.rollback();
            con.close();
            LOGGER.info("Connection rollback and close");
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
        }
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
            LOGGER.info("Inserting card is successfully");
        } catch (SQLException e) {
            cardJDBCDaoImpl.rollbackAndClose(connection);
            LOGGER.error(e.getMessage());
        } finally {
            cardJDBCDaoImpl.commitAndClose(connection);
        }
        return result;
    }

    @Override
    public List<Card> findAllCards(User user, String sort) throws SQLException {
        List<Card> cards = new ArrayList<>();
        ResultSet rs = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = cardJDBCDaoImpl.getConnection();
            preparedStatement = connection.prepareStatement(FIND_ALL_CARDS + sort);
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
            LOGGER.info("Found all cards");
        } catch (SQLException e) {
            assert connection != null;
            cardJDBCDaoImpl.rollbackAndClose(connection);
            LOGGER.error(e.getMessage());
        } finally {
            assert connection != null;
            cardJDBCDaoImpl.commitAndClose(connection);
        }
        return cards;
    }

    @Override
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
            LOGGER.info("Updated balance");
        } catch (SQLException e) {
            assert connection != null;
            cardJDBCDaoImpl.rollbackAndClose(connection);
            LOGGER.error(e.getMessage());
        } finally {
            assert connection != null;
            cardJDBCDaoImpl.commitAndClose(connection);
        }
        return result;
    }

    @Override
    public int decreaseBalance(Integer id, BigDecimal value) throws SQLException {
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        CardJDBCDaoImpl cardJDBCDaoImpl = CardJDBCDaoImpl.getInstance();
        int result = 0;
        try {
            connection = cardJDBCDaoImpl.getConnection();
            preparedStatement = connection.prepareStatement(DECREASE_CARD_BALANCE);
            preparedStatement.setBigDecimal(1, value);
            preparedStatement.setInt(2, id);
            result = preparedStatement.executeUpdate();
            LOGGER.info("Decreased balance");
        } catch (SQLException e) {
            assert connection != null;
            cardJDBCDaoImpl.rollbackAndClose(connection);
            LOGGER.error(e.getMessage());
        } finally {
            assert connection != null;
            cardJDBCDaoImpl.commitAndClose(connection);
        }
        return result;
    }

    @Override
    public BigDecimal getCardBalance(Card card) throws SQLException {
        BigDecimal result = new BigDecimal(-1);
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        CardJDBCDaoImpl cardJDBCDaoImpl = CardJDBCDaoImpl.getInstance();
        try {
            connection = cardJDBCDaoImpl.getConnection();
            preparedStatement = connection.prepareStatement(GET_CARD_BALANCE);
            preparedStatement.setString(1, card.getNumber());
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                result = rs.getBigDecimal("balance");
            }
            LOGGER.info("Gets card balance");
        } catch (SQLException e) {
            assert connection != null;
            cardJDBCDaoImpl.rollbackAndClose(connection);
            LOGGER.error(e.getMessage());
        } finally {
            assert connection != null;
            cardJDBCDaoImpl.commitAndClose(connection);
        }
        return result;
    }

    @Override
    public boolean findCard(String number) throws SQLException {
        boolean result = false;
        ResultSet rs = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = cardJDBCDaoImpl.getConnection();
            preparedStatement = connection.prepareStatement(FIND_CARD);
            preparedStatement.setString(1, number);
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                result = true;
            }
            LOGGER.info("Found card");
        } catch (SQLException e) {
            assert connection != null;
            cardJDBCDaoImpl.rollbackAndClose(connection);
            LOGGER.error(e.getMessage());
        } finally {
            assert connection != null;
            cardJDBCDaoImpl.commitAndClose(connection);
        }
        return result;
    }

    @Override
    public boolean findCardByName(String name, User user) throws SQLException {
        boolean result = false;
        ResultSet rs = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = cardJDBCDaoImpl.getConnection();
            preparedStatement = connection.prepareStatement(FIND_CARD_BY_NAME);
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, user.getId());
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                result = true;
            }
            LOGGER.info("Found card by name");
        } catch (SQLException e) {
            assert connection != null;
            cardJDBCDaoImpl.rollbackAndClose(connection);
            LOGGER.error(e.getMessage());
        } finally {
            assert connection != null;
            cardJDBCDaoImpl.commitAndClose(connection);
        }
        return result;
    }

    @Override
    public int getCardId(String number) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        try {
            connection = cardJDBCDaoImpl.getConnection();
            preparedStatement = connection.prepareStatement(GET_CARD_ID);
            preparedStatement.setString(1, number);
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                return (rs.getInt("id"));
            }
            LOGGER.info("Got card id");
        } catch (SQLException e) {
            assert connection != null;
            cardJDBCDaoImpl.rollbackAndClose(connection);
            LOGGER.error(e.getMessage());
        } finally {
            assert connection != null;
            cardJDBCDaoImpl.commitAndClose(connection);
        }
        return -1;
    }


    @Override
    public Integer getCardStatus(Card card) throws SQLException {
        int result = 0;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        CardJDBCDaoImpl cardJDBCDaoImpl = CardJDBCDaoImpl.getInstance();
        try {
            connection = cardJDBCDaoImpl.getConnection();
            preparedStatement = connection.prepareStatement(GET_CARD_STATUS);
            preparedStatement.setString(1, card.getNumber());
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                result = rs.getInt("card_status");
            }
            LOGGER.info("Got card status");
        } catch (SQLException e) {
            assert connection != null;
            cardJDBCDaoImpl.rollbackAndClose(connection);
            LOGGER.error(e.getMessage());
        } finally {
            assert connection != null;
            cardJDBCDaoImpl.commitAndClose(connection);
        }
        return result;
    }

    @Override
    public int blockCardById(Integer cardId) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int result = 0;
        CardJDBCDaoImpl cardJDBCDaoImpl = CardJDBCDaoImpl.getInstance();

        try {
            connection = cardJDBCDaoImpl.getConnection();
            preparedStatement = connection.prepareStatement(BLOCK_CARD);
            preparedStatement.setInt(1, cardId);
            result = preparedStatement.executeUpdate();
            LOGGER.info("Card is blocked by id");
        }
        catch (SQLException e) {
            assert connection != null;
            cardJDBCDaoImpl.rollbackAndClose(connection);
            LOGGER.error(e.getMessage());
        } finally {
            assert connection != null;
            cardJDBCDaoImpl.commitAndClose(connection);
        }
        return result;
    }

    @Override
    public int changeCardStatus(Integer cardId) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        CardJDBCDaoImpl cardJDBCDaoImpl = CardJDBCDaoImpl.getInstance();
        int result = 0;
        try {
            connection = cardJDBCDaoImpl.getConnection();
            preparedStatement = connection.prepareStatement(UPDATE_CARD_STATUS);
            preparedStatement.setInt(1, cardId);
            result = preparedStatement.executeUpdate();
            LOGGER.info("Card status has changed");
        } catch (SQLException e) {
            assert connection != null;
            cardJDBCDaoImpl.rollbackAndClose(connection);
            LOGGER.error(e.getMessage());
        } finally {
            assert connection != null;
            cardJDBCDaoImpl.commitAndClose(connection);
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


}
