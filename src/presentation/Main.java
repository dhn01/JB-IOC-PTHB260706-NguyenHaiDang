package presentation;

import entity.TicketBooking;
import persistence.TicketBookingDAO;
import utils.InputUtil;

import java.time.LocalDateTime;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        TicketBookingDAO ticketBookingDAO = new TicketBookingDAO();
        int choice;
        do {
            System.out.print("""
                    ====================================================================
                                    CINEMA TICKET BOOKING MANAGEMENT
                    ====================================================================
                    1. Danh sách tất cả phiếu đặt vé
                    2. Thêm mới phiếu đặt vé
                    3. Cập nhật thông tin phiếu đặt vé
                    4. Xóa phiếu đặt vé
                    5. Tìm kiếm phiếu đặt vé theo tên khách hàng
                    6. Tìm kiếm phiếu đặt vé theo tên phim
                    7. Thoát
                    Lựa chọn của bạn: 
                    """);
            while (true) {
                choice = InputUtil.getInt();
                if (choice < 1 || choice > 7) {
                    System.out.println("Vui lòng nhập giá trị 1-7.");
                } else {
                    break;
                }
            }

            switch (choice) {
                case 1: {
                    List<TicketBooking> bookingList = ticketBookingDAO.getAll();
                    ticketBookingDAO.printTicketBookingList(bookingList, "DANH SÁCH TICKET BOOKING");
                }
                break;
                case 2: {
                    System.out.println("===== THÊM MỚI BOOKING =====");
                    TicketBooking ticketBooking = inputTicketBooking(0);
                    ticketBookingDAO.add(ticketBooking);
                }
                break;
                case 3: {
                    System.out.println("===== SỬA BOOKING =====");
                    System.out.println("Nhập vào mã booking muốn sửa: ");
                    int bookingId = InputUtil.getPositiveInt("Nhập vào mã booking phải lớn hơn 0.");
                    TicketBooking ticketBooking = inputTicketBooking(bookingId);
                    ticketBookingDAO.update(bookingId, ticketBooking);
                }
                break;
                case 4: {
                    System.out.println("Nhập vào mã booking muốn xóa: ");
                    int bookingId = InputUtil.getPositiveInt("Nhập vào mã booking phải lớn hơn 0.");
                    System.out.print("""
                            Bạn có chắc chắn muốn xóa không?
                            1. Có               0.Không
                            """);
                    int deleteChoice;
                    do {
                        deleteChoice = InputUtil.getInt();
                        if (deleteChoice > 1 || deleteChoice < 0) {
                            System.out.println("Vui lòng nhập 0-1.");
                        } else {
                            if (deleteChoice == 1) {
                                ticketBookingDAO.delete(bookingId);
                            } else {
                                System.out.println("Đã dừng thao tác xóa đặt vé.");
                            }
                            break;
                        }
                    } while (true);
                }
                break;
                case 5: {
                    System.out.println("Nhập vào tên khách hàng muốn tìm: ");
                    String customerName = InputUtil.getNonBlankString();
                    List<TicketBooking> ticketBookingList = ticketBookingDAO.searchByCustomerName(customerName);
                    ticketBookingDAO.printTicketBookingList(ticketBookingList, "DANH SÁCH TICKET BOOKING THEO TÊN KHÁCH HÀNG");
                }
                break;
                case 6: {
                    System.out.println("Nhập vào tên phim muốn tìm: ");
                    String movieTitle = InputUtil.getNonBlankString();
                    List<TicketBooking> ticketBookingList = ticketBookingDAO.searchByMovieTitle(movieTitle);
                    ticketBookingDAO.printTicketBookingList(ticketBookingList, "DANH SÁCH TICKET BOOKING THEO TÊN PHIM");
                }
                break;
                case 7:
                    System.out.println("Cảm ơn bạn đã sử dụng.");
                    break;
            }

        } while (true);
    }

    private static TicketBooking inputTicketBooking(int bookingId) {
        System.out.println("Nhập vào tiêu đề phim: ");
        String movieTitle = InputUtil.getNonBlankString();
        System.out.println("Nhập vào tên khách hàng: ");
        String customerName = InputUtil.getNonBlankString();
        System.out.println("Nhập vào suất chiếu (dạng dd/MM/yyyy HH:mm:ss): ");
        LocalDateTime showtime = InputUtil.getDateTime();
        System.out.println("Nhập vào ngày đặt vé (dạng dd/MM/yyyy HH:mm:ss): ");
        LocalDateTime bookingTime = InputUtil.getDateTime();
        System.out.println("Nhập vào số lượng ghế đặt: ");
        int quantity = InputUtil.getPositiveInt("Vui lòng nhập số ghế > 0.");
        System.out.println("Nhập vào trạng thái (Booked|Cancelled|CheckedIn): ");
        String status = InputUtil.getString("(Booked|Cancelled|CheckedIn)");
        return new TicketBooking(
                bookingId,
                movieTitle,
                customerName,
                showtime,
                bookingTime,
                quantity,
                status
        );
    }
}
