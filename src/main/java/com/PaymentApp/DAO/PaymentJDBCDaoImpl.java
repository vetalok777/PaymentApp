package com.PaymentApp.DAO;

import com.PaymentApp.DTO.PaymentDTO;
import com.PaymentApp.entities.Payment;
import com.PaymentApp.entities.User;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class PaymentJDBCDaoImpl implements PaymentDAO {
    private static final String URL = "jdbc:mysql://localhost/payment_system_db?serverTimezone=UTC";
    private static final String PASSWORD = "root";
    private static final String USERNAME = "root";
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String INSERT_NEW_PAYMENT = "INSERT INTO payment (amount, creation_date, sender_id, receiver_id) VALUES (?, ?, ?, ?);";
    private static final String FIND_ALL_PAYMENTS = "select payment.id, amount, creation_date, payment_status, s.card_number, r.card_number\n" +
            "from payment inner join card as s\n" +
            "on payment.sender_id = s.id\n" +
            "inner join card as r\n" +
            "on payment.receiver_id = r.id\n" +
            "inner join user\n" +
            "on s.user_id = user.id\n" +
            "where user.id = ?\n" +
            ";";
    private static final String UPDATE_PAYMENT_STATUS = "UPDATE payment SET payment.payment_status = (?)  WHERE id=(?);";
    private static final String GET_NUMBER_OF_ROWS = "select count(payment.id)\n" +
            "from payment inner join card \n" +
            "on sender_id = card.id\n" +
            "where user_id = (?);";


    private static PaymentJDBCDaoImpl paymentJDBCDaoImpl;

    public PaymentJDBCDaoImpl() {
        try {
            Class.forName(DRIVER);
            System.out.println("Successfully!");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static synchronized PaymentJDBCDaoImpl getInstance() {
        if (paymentJDBCDaoImpl == null) {
            paymentJDBCDaoImpl = new PaymentJDBCDaoImpl();
        }
        return paymentJDBCDaoImpl;
    }

    public Connection getConnection() throws SQLException {
        Connection connection = null;
        try {
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:/comp/env");


            DataSource ds = (DataSource) envContext.lookup("jdbc/PaymentDB");
            connection = ds.getConnection();
        } catch (NamingException ex) {
            ex.printStackTrace();
        }
        return connection;
    }


    public void commitAndClose(Connection con) {
        try {
            con.commit();
            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void rollbackAndClose(Connection con) {
        try {
            con.rollback();
            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public int insertPayment(Payment payment) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        PaymentJDBCDaoImpl paymentJDBCDaoImpl = PaymentJDBCDaoImpl.getInstance();
        int result = 0;
        try {
            connection = paymentJDBCDaoImpl.getConnection();
            preparedStatement = connection.prepareStatement(INSERT_NEW_PAYMENT);
            preparedStatement.setBigDecimal(1, payment.getAmount());
            preparedStatement.setString(2, String.valueOf(payment.getDate()));
            preparedStatement.setInt(3, payment.getSender_id());
            preparedStatement.setInt(4, payment.getReceiver_id());
            result = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            assert connection != null;
            paymentJDBCDaoImpl.rollbackAndClose(connection);
            e.printStackTrace();
        } finally {
            assert connection != null;
            paymentJDBCDaoImpl.commitAndClose(connection);
        }
        return result;
    }

    public List<PaymentDTO> findAllPayments(User user) throws SQLException {
        List<PaymentDTO> payments = new ArrayList<>();
        ResultSet rs = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = paymentJDBCDaoImpl.getConnection();
            preparedStatement = connection.prepareStatement(FIND_ALL_PAYMENTS);
            preparedStatement.setInt(1, user.getId());
            rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Integer id = rs.getInt("id");
                BigDecimal amount = rs.getBigDecimal("amount");
                LocalDateTime date = LocalDateTime.parse(rs.getString("creation_date"), DateTimeFormatter.ofPattern("yyyy-M-d HH':'mm':'ss.SSSSSS"));
                String status = rs.getString("payment_status");
                String sender = rs.getString("s.card_number");
                String receiver = rs.getString("r.card_number");
                PaymentDTO payment = new PaymentDTO(id, amount, date, status, sender, receiver);
                payments.add(payment);
            }
        } catch (SQLException e) {
            assert connection != null;
            paymentJDBCDaoImpl.rollbackAndClose(connection);
            e.printStackTrace();
        } finally {
            assert connection != null;
            paymentJDBCDaoImpl.commitAndClose(connection);
        }
        return payments;
    }

    @Override
    public int updatePaymentStatus(String status, Integer id) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        PaymentJDBCDaoImpl paymentJDBCDaoImpl = PaymentJDBCDaoImpl.getInstance();
        int result = 0;
        try {
            connection = paymentJDBCDaoImpl.getConnection();
            preparedStatement = connection.prepareStatement(UPDATE_PAYMENT_STATUS);
            preparedStatement.setString(1, status);
            preparedStatement.setInt(2, id);
            result = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            assert connection != null;
            paymentJDBCDaoImpl.rollbackAndClose(connection);
            e.printStackTrace();
        } finally {
            assert connection != null;
            paymentJDBCDaoImpl.commitAndClose(connection);
        }
        return result;
    }

    @Override
    public Integer getNumberOfRows(Integer userId) throws SQLException {
        int numOfRows = 0;
        ResultSet rs = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        PaymentJDBCDaoImpl paymentJDBCDaoImpl = PaymentJDBCDaoImpl.getInstance();
        try {
            connection = paymentJDBCDaoImpl.getConnection();
            preparedStatement = connection.prepareStatement(GET_NUMBER_OF_ROWS);
            preparedStatement.setInt(1, userId);
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                numOfRows = rs.getInt(1);
            }
        } catch (SQLException e) {
            assert connection != null;
            paymentJDBCDaoImpl.rollbackAndClose(connection);
            e.printStackTrace();
        } finally {
            assert connection != null;
            paymentJDBCDaoImpl.commitAndClose(connection);
        }
        return numOfRows;
    }

    @Override
    public List<PaymentDTO> getPaymentsRecords(User user, int currentPage, int recordsPerPage, String str) throws SQLException {
        List<PaymentDTO> payments = new ArrayList<>();
        int start = currentPage * recordsPerPage - recordsPerPage;
        ResultSet rs = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = paymentJDBCDaoImpl.getConnection();
            preparedStatement = connection.prepareStatement("(select payment.id, amount, creation_date, payment_status, s.card_number, r.card_number\n" +
                    "from payment  inner join card as s\n" +
                    "on payment.sender_id = s.id\n" +
                    "inner join card as r\n" +
                    "on payment.receiver_id = r.id\n" +
                    "inner join user\n" +
                    "on s.user_id = user.id\n" +
                    "where user.id = (?))" + str + "  LIMIT ?, ? ");
            preparedStatement.setInt(1, user.getId());
            preparedStatement.setInt(2, start);
            preparedStatement.setInt(3, recordsPerPage);

            rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Integer id = rs.getInt("id");
                BigDecimal amount = rs.getBigDecimal("amount");
                LocalDateTime date = LocalDateTime.parse(rs.getString("creation_date"), DateTimeFormatter.ofPattern("yyyy-M-d HH':'mm':'ss.SSSSSS"));
                String status = rs.getString("payment_status");
                String sender = rs.getString("s.card_number");
                String receiver = rs.getString("r.card_number");
                PaymentDTO payment = new PaymentDTO(id, amount, date, status, sender, receiver);
                payments.add(payment);
            }
        } catch (SQLException e) {
            assert connection != null;
            paymentJDBCDaoImpl.rollbackAndClose(connection);
            e.printStackTrace();
        } finally {
            assert connection != null;
            paymentJDBCDaoImpl.commitAndClose(connection);
        }
        return payments;
    }


}


