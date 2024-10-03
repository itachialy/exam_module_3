package org.example.demo.service;

import org.example.demo.common.BaseRepository;
import org.example.demo.model.MatBang;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MatBangServiceImpl implements IMatBangService {
    private final BaseRepository baseRepository = new BaseRepository();
    public MatBangServiceImpl() throws SQLException, ClassNotFoundException {
    }
    private static final String FIND_ALL = "select * from premises;";
    private static final String DELETE_MB = "delete from premises where maMb = ?";
    private static final String INSERT_MB ="insert into premises (maMb, dienTich,trThai,tang,loaiVp,giaThue,startDate,endDate)\n" +
            "values (?,?,?,?,?,?,?,?)";
    @Override
    public List<MatBang> findAll() {
        Connection connection = baseRepository.getConnection();
        List<MatBang> list = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                String maMb = resultSet.getString("maMb");
                double dienTich = resultSet.getDouble("dienTich");
                int trThai = resultSet.getInt("trThai");
                int tang = resultSet.getInt("tang");
                int loaiVp = resultSet.getInt("loaiVp");
                double giaThue = resultSet.getDouble("giaThue");
                Date startDate = resultSet.getDate("startDate");
                Date endDate = resultSet.getDate("endDate");
                list.add(new MatBang(maMb,dienTich,trThai,tang,loaiVp,giaThue,startDate,endDate));

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }


    @Override
    public void addMatBang(MatBang matBang) {
        Connection connection = baseRepository.getConnection();
        try {
            PreparedStatement ps = connection.prepareStatement(INSERT_MB);
            ps.setString(1, matBang.getMaMb());
            ps.setDouble(2, matBang.getDienTich());
            ps.setInt(3, matBang.getTrThai());
            ps.setInt(4, matBang.getTang());
            ps.setInt(5, matBang.getLoaiVp());
            ps.setDouble(6, matBang.getGiaThue());
            ps.setDate(7, matBang.getStartDate());
            ps.setDate(8, matBang.getEndDate());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteMatBang(String maMb) {
        Connection connection = baseRepository.getConnection();
        try {
            PreparedStatement ps = connection.prepareStatement(DELETE_MB);
            ps.setString(1, maMb);
            ps.executeUpdate();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
    private List<MatBang> toList(ResultSet result) throws SQLException {
        List<MatBang> list = new ArrayList<>();
        while(result.next()) {
            String maMb = result.getString("maMb");
            double dienTich = result.getDouble("gender");
            int trThai = result.getInt("trThai");
            int tang = result.getInt("tang");
            int loaiVp = result.getInt("loaiVp");
            double giaThue = result.getDouble("giaThue");
            Date startDate = result.getDate("startDate");
            Date endDate = result.getDate("endDate");
            list.add(new MatBang(maMb,dienTich,trThai,tang,loaiVp,giaThue,startDate,endDate));
        }
        return list;
    }
    @Override
    public List<MatBang> filterMatBang(String maMb, Date startDate, Date endDate, int tang) {
        List<MatBang> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM premises WHERE 1=1");
        if (maMb != null && !maMb.isEmpty()) {
            sql.append(" AND maMb LIKE ?");
        }
        if (startDate != null && endDate != null) {
            sql.append(" AND startDate BETWEEN ? AND ?");
        }
        if (tang >= 1 && tang <= 15) {
            sql.append(" AND tang = ?");
        }

        Connection connection = baseRepository.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(sql.toString())) {
            int paramIndex = 1;
            if (maMb != null && !maMb.isEmpty()) {
                statement.setString(paramIndex++, "%" + maMb + "%");
            }
            if (startDate != null && endDate != null) {
                statement.setDate(paramIndex++, startDate);
                statement.setDate(paramIndex++, endDate);
            }
            if (tang >= 1 && tang <= 15) {
                statement.setInt(paramIndex, tang);
            }

            ResultSet resultSet = statement.executeQuery();
            list = toList(resultSet);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return list;
    }



}
