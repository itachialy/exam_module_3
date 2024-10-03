package org.example.demo.controller;



import org.example.demo.model.MatBang;
import org.example.demo.service.IMatBangService;
import org.example.demo.service.MatBangServiceImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "StudentServlet", value = "")

public class MatBangServlet extends HttpServlet {
    IMatBangService iMatBangService = new MatBangServiceImpl();

    public MatBangServlet() throws SQLException, ClassNotFoundException {
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }
        switch (action) {
            case "create":
                showCreateForm(request,response);
                break;
//
            case "delete":
                deleteMatBang(request, response);
                break;
            default:
                findAll(request,response);
        }

    }


    private void deleteMatBang(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String maMb = request.getParameter("sid");
        iMatBangService.deleteMatBang(maMb);
        response.sendRedirect("/");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }
        switch (action) {

            case "create":
                addMatBang(request,response);
                break;
            case "filter":
                filterStudent(request,response);
                break;
            default:
                findAll(request,response);
        }
    }

    private void filterStudent(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("maMb");
        String startDateStr = request.getParameter("startDate");
        String endDateStr = request.getParameter("endDate");
        int tang = Integer.parseInt(request.getParameter("tang"));


        Date startDate = null;
        Date endDate = null;

        if (startDateStr != null && !startDateStr.isEmpty()) {
            startDate = Date.valueOf(startDateStr);
        }
        if (endDateStr != null && !endDateStr.isEmpty()) {
            endDate = Date.valueOf(endDateStr);
        }
        List<MatBang> list = iMatBangService.filterMatBang(name, startDate, endDate, tang);
        request.setAttribute("list", list);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("list.jsp");
        requestDispatcher.forward(request, response);
    }
    private void addMatBang(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String maMb = request.getParameter("maMb");
        String regexMaMb = "^[A-Z0-9]{3}-[A-Z0-9]{2}-[A-Z0-9]{2}$";
        if (!maMb.matches(regexMaMb)) {
            request.setAttribute("errorMessage", "Mã mặt bằng không đúng định dạng XXX-XX-XX.");
            showCreateForm(request, response);
            return;
        }

        double dienTich = Double.parseDouble(request.getParameter("dienTich"));
        if (dienTich <= 20) {
            request.setAttribute("errorMessage", "Diện tích phải lớn hơn 20m².");
            showCreateForm(request, response);
            return;
        }

        int trThai = Integer.parseInt(request.getParameter("trThai"));
        int tang = Integer.parseInt(request.getParameter("tang"));
        int loaiVp = Integer.parseInt(request.getParameter("loaiVp"));
        double giaThue = Double.parseDouble(request.getParameter("giaThue"));
        if (giaThue <= 1000000) {
            request.setAttribute("errorMessage", "Giá thuê phải lớn hơn 1.000.000 VNĐ.");
            showCreateForm(request, response);
            return;
        }

        Date startDate = Date.valueOf(request.getParameter("startDate"));
        Date endDate = Date.valueOf(request.getParameter("endDate"));
        long diffInMillies = Math.abs(endDate.getTime() - startDate.getTime());
        long diffInMonths = diffInMillies / (1000L * 60 * 60 * 24 * 30);
        if (diffInMonths < 6) {
            request.setAttribute("errorMessage", "Ngày bắt đầu phải nhỏ hơn ngày kết thúc ít nhất 6 tháng.");
            showCreateForm(request, response);
            return;
        }

        // Kiểm tra mã mặt bằng có tồn tại trong cơ sở dữ liệu không
//        if (iMatBangService.checkMaMbExists(maMb)) {
//            request.setAttribute("errorMessage", "Mã mặt bằng vừa thêm đã tồn tại.");
//            showCreateForm(request, response);
//            return;
//        }

        // Nếu tất cả đều hợp lệ thì thêm mới
        iMatBangService.addMatBang(new MatBang(maMb, dienTich, trThai, tang, loaiVp, giaThue, startDate, endDate));
        response.sendRedirect("/");
    }

    private void showCreateForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<MatBang> list = iMatBangService.findAll();
        request.setAttribute("listCr", list);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("create.jsp");
        requestDispatcher.forward(request, response);
    }


    private void findAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("listMB", iMatBangService.findAll());
        request.getRequestDispatcher("list.jsp").forward(request,response);
    }


}
