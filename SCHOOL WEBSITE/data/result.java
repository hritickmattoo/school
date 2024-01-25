package data;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;

public class result {
    private static final String URL = "jdbc:mysql://localhost:3306/studentlistdb";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "Hritz@3054";
    private static final String TABLE_NAME = "classxii_result";
    private static final String HTML_FILE_NAME = "data.html";

    public static void main(String args[]) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Establish a connection
            try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
                System.out.println("Connected to the existing database");

                // You can now perform operations on the existing database and table
                retrieveData(connection);
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    private static void retrieveData(Connection connection) throws SQLException {
        String retrieveDataSQL = "SELECT StudentID, FirstName, LastName, Gender, `Class`, Result FROM " + TABLE_NAME;

        try (Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(retrieveDataSQL)) {

            System.out.println("Retrieving data from the table:");

            // Create HTML content
            StringBuilder htmlContent = new StringBuilder();
            htmlContent.append("<html><head><title>Result Data</title></head><body>");
            htmlContent.append("<h1>Student Result Data</h1>");
            htmlContent.append(
                    "<table border='1'><tr><th>StudentID</th><th>FirstName</th><th>LastName</th><th>Gender</th><th>Class</th><th>Result</th></tr>");

            while (resultSet.next()) {
                int studentID = resultSet.getInt("StudentID");
                String firstName = resultSet.getString("FirstName");
                String lastName = resultSet.getString("LastName");
                String gender = resultSet.getString("Gender");
                String studentClass = resultSet.getString("Class");
                String result = resultSet.getString("Result");

                htmlContent.append("<tr><td>").append(studentID).append("</td><td>").append(firstName)
                        .append("</td><td>").append(lastName).append("</td><td>").append(gender).append("</td><td>")
                        .append(studentClass).append("</td><td>").append(result).append("</td></tr>");
            }

            htmlContent.append("</table></body></html>");

            // Write HTML content to a file
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(HTML_FILE_NAME))) {
                writer.write(htmlContent.toString());
                System.out.println("HTML file '" + HTML_FILE_NAME + "' generated successfully.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}