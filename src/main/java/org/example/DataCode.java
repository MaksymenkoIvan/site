package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DataCode extends  Config {
    private Scanner scanner = new Scanner(System.in);
    private Connection connection = null;
    private List<String> messages = new ArrayList<>();
    private StringBuilder string = new StringBuilder();

    public DataCode() {
        try {

            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(HOST, USER, PASS);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean code(String email) {
        try {
            PreparedStatement insert = connection.prepareStatement("INSERT INTO checkcode (code)" + "VALUES(?)");
            insert.setString(1, email);
            insert.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
}