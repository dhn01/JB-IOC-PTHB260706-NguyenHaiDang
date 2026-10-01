package persistence;

import entity.TicketBooking;
import utils.ConnectionDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TicketBookingDAO {

    public List<TicketBooking> getAll() {
        List<TicketBooking> ticketBookingList = new ArrayList<>();
        try (
                Connection conn = ConnectionDB.getConnection();
                PreparedStatement preparedStatement = conn.prepareStatement(
                        "SELECT * FROM fn_get_all_ticket_bookings()"
                );
                ResultSet resultSet = preparedStatement.executeQuery();
        ) {
            while (resultSet.next()) {
                TicketBooking ticketBooking = new TicketBooking(
                        resultSet.getInt("booking_id"),
                        resultSet.getString("movie_title"),
                        resultSet.getString("customer_name"),
                        resultSet.getTimestamp("show_time").toLocalDateTime(),
                        resultSet.getTimestamp("booking_date").toLocalDateTime(),
                        resultSet.getInt("seat_quantity"),
                        resultSet.getString("status")
                );
                ticketBookingList.add(ticketBooking);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return ticketBookingList;
    }

    public void add(TicketBooking ticketBooking) {
        try (
                Connection conn = ConnectionDB.getConnection();
                CallableStatement callableStatement = conn.prepareCall(
                        "CALL proc_add_ticket_booking(?, ?, ?, ?,?,?)"
                )
        ) {

            callableStatement.setString(1, ticketBooking.getMovieTitle());
            callableStatement.setString(2, ticketBooking.getCustomerName());
            callableStatement.setTimestamp(3, Timestamp.valueOf(ticketBooking.getShowtime()));
            callableStatement.setTimestamp(4, Timestamp.valueOf(ticketBooking.getBooking_date()));
            callableStatement.setInt(5, ticketBooking.getQuantity());
            callableStatement.setString(6, ticketBooking.getStatus());

            try {
                callableStatement.execute();
                System.out.println("Thêm mới thành công.");
            } catch (SQLException e) {
                String errorMessage = e.getMessage();
                System.out.println(errorMessage.substring(errorMessage.indexOf(" ") + 1, errorMessage.indexOf("\n")));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(int bookingId, TicketBooking ticketBooking) {
        try (
                Connection conn = ConnectionDB.getConnection();
                CallableStatement callableStatement = conn.prepareCall(
                        "CALL proc_update_ticket_booking_by_id(?, ?, ?, ?, ?,?,?)"
                )
        ) {

            callableStatement.setInt(1, bookingId);
            callableStatement.setString(2, ticketBooking.getMovieTitle());
            callableStatement.setString(3, ticketBooking.getCustomerName());
            callableStatement.setTimestamp(4, Timestamp.valueOf(ticketBooking.getShowtime()));
            callableStatement.setTimestamp(5, Timestamp.valueOf(ticketBooking.getBooking_date()));
            callableStatement.setInt(6, ticketBooking.getQuantity());
            callableStatement.setString(7, ticketBooking.getStatus());

            try {
                callableStatement.execute();
                System.out.println("Cập nhật thành công");
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }

        } catch (SQLException e) {
            String errorMessage = e.getMessage();
            System.out.println(errorMessage.substring(errorMessage.indexOf(" ") + 1, errorMessage.indexOf("\n")));
        }
    }

    public void delete(int ticketBookingId) {
        try (
                Connection conn = ConnectionDB.getConnection();
                CallableStatement callableStatement = conn.prepareCall(
                        "CALL proc_delete_ticket_booking_by_id(?)"
                )
        ) {

            callableStatement.setInt(1, ticketBookingId);

            try {
                callableStatement.execute();
                System.out.println("Xóa thành công.");
            } catch (SQLException e) {
                String errorMessage = e.getMessage();
                System.out.println(errorMessage.substring(errorMessage.indexOf(" ") + 1, errorMessage.indexOf("\n")));
            }

        } catch (SQLException e) {
            String errorMessage = e.getMessage();
            System.out.println(errorMessage.substring(errorMessage.indexOf(" ") + 1, errorMessage.indexOf("\n")));
        }

    }

    public List<TicketBooking> searchByCustomerName(String customerName) {
        List<TicketBooking> ticketBookingList = new ArrayList<>();
        try (
                Connection conn = ConnectionDB.getConnection();
                PreparedStatement preparedStatement = conn.prepareStatement(
                        "SELECT * FROM fn_get_ticket_booking_by_customer_name(?)"
                );
        ) {
            preparedStatement.setString(1, customerName);
            addTicketBooking(preparedStatement, ticketBookingList);
        } catch (SQLException e) {
            String errorMessage = e.getMessage();
            System.out.println(errorMessage.substring(errorMessage.indexOf(" ") + 1, errorMessage.indexOf("\n")));
        }

        return ticketBookingList;
    }

    public List<TicketBooking> searchByMovieTitle(String movieTitle) {
        List<TicketBooking> ticketBookingList = new ArrayList<>();
        try (
                Connection conn = ConnectionDB.getConnection();
                PreparedStatement preparedStatement = conn.prepareStatement(
                        "SELECT * FROM fn_get_ticket_booking_by_movie_title_keyword(?)"
                );
        ) {
            preparedStatement.setString(1, movieTitle);

            addTicketBooking(preparedStatement, ticketBookingList);
        } catch (SQLException e) {
            String errorMessage = e.getMessage();
            System.out.println(errorMessage.substring(errorMessage.indexOf(" ") + 1, errorMessage.indexOf("\n")));
        }

        return ticketBookingList;
    }

    private static void addTicketBooking(PreparedStatement preparedStatement, List<TicketBooking> ticketBookingList) {
        try (
                ResultSet resultSet = preparedStatement.executeQuery();
        ) {
            while (resultSet.next()) {
                TicketBooking ticketBooking = new TicketBooking(
                        resultSet.getInt("booking_id"),
                        resultSet.getString("movie_title"),
                        resultSet.getString("customer_name"),
                        resultSet.getTimestamp("show_time").toLocalDateTime(),
                        resultSet.getTimestamp("booking_date").toLocalDateTime(),
                        resultSet.getInt("seat_quantity"),
                        resultSet.getString("status")
                );
                ticketBookingList.add(ticketBooking);
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public void printTicketBookingList(List<TicketBooking> ticketBookingList, String title) {
        System.out.printf("""
                        =======================================================================================================================================
                                                                            %s
                        =======================================================================================================================================
                        | %-3s | %-35s | %-20s | %-20s | %-20s | %-3s | %-12s |
                        =======================================================================================================================================
                        """,
                title,
                "ID",
                "TÊN PHIM",
                "TÊN KHÁCH HÀNG",
                "SUẤT CHIẾU",
                "NGÀY ĐẶT VÉ",
                "SL",
                "TRẠNG THÁI"
        );
        if (ticketBookingList.isEmpty()) {
            System.out.println("Không có dữ liệu.");
        } else {
            ticketBookingList.stream()
                    .sorted(Comparator.comparing(TicketBooking::getBookingId))
                    .forEach(System.out::println);
        }
        System.out.println("=======================================================================================================================================");
    }
}
