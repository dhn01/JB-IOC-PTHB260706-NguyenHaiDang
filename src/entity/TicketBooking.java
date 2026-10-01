package entity;

import java.time.LocalDateTime;

public class TicketBooking {
    private int bookingId;
    private String movieTitle;
    private String customerName;
    private LocalDateTime showtime;
    private LocalDateTime booking_date;
    private int quantity;
    private String status;

    public TicketBooking() {
    }

    public TicketBooking(int bookingId, String movieTitle, String customerName, LocalDateTime showtime, LocalDateTime booking_date, int quantity, String status) {
        this.bookingId = bookingId;
        this.movieTitle = movieTitle;
        this.customerName = customerName;
        this.showtime = showtime;
        this.booking_date = booking_date;
        this.quantity = quantity;
        this.status = status;
    }

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public LocalDateTime getShowtime() {
        return showtime;
    }

    public void setShowtime(LocalDateTime showtime) {
        this.showtime = showtime;
    }

    public LocalDateTime getBooking_date() {
        return booking_date;
    }

    public void setBooking_date(LocalDateTime booking_date) {
        this.booking_date = booking_date;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "| %-3d | %-35s | %-20s | %-20s | %-20s | %-3d | %-12s |".formatted(
                bookingId,
                movieTitle,
                customerName,
                showtime,
                booking_date,
                quantity,
                status
        );

    }
}
