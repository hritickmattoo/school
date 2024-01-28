package data;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;

public class library {
    private static final String URL = "jdbc:mysql://localhost:3306/school_library_management_system";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "Hritz@3054";
    private static final String TABLENAME = "book";
    private static final String HTML_FILE_NAME = "library.html";

    public static void main(String arg[]) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Establish a connection
            try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
                System.out.println("Connected to the existing database");
                retrieveData(connection);
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    private static void retrieveData(Connection connection) throws SQLException {
        String retrieveDataSQL = "SELECT BookID, ISBN, BookName, AuthorName, BookGenre, BookPublishedYear, BookConditionID, BookExpectedLifeYear FROM "
                + TABLENAME;
        try (Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(retrieveDataSQL)) {

            System.out.println("Retrieving data from the table:");
            StringBuilder htmlContent = new StringBuilder();
            htmlContent.append("<html><head><title>Library Data</title></head><body>");
            htmlContent.append("<h1>Book Data</h1>");
            htmlContent.append(
                    "<table border='2'><tr><th>BOOK ID</th><th>ISBN</th><th>Book Name</th><th>Author Name</th><th>Book Genre</th><th>Book Published Year</th><th>Book Condition ID</th><th>Book Shelf Life</th></tr>");

            while (resultSet.next()) {
                int BookID = resultSet.getInt("BookID");
                String ISBN = resultSet.getString("ISBN");
                String BookName = resultSet.getString("BookName");
                String AuthorName = resultSet.getString("AuthorName");
                String BookGenre = resultSet.getString("BookGenre");
                String BookPublishedYear = resultSet.getString("BookPublishedYear");
                int BookConditionID = resultSet.getInt("BookConditionID");
                int BookExpectedLifeYear = resultSet.getInt("BookExpectedLifeYear");

                htmlContent.append("<tr><td>").append(BookID).append("</td><td>").append(ISBN).append("</td><td>")
                        .append(BookName).append("</td><td>").append(AuthorName).append("</td><td>").append(BookGenre)
                        .append("</td><td>").append(BookPublishedYear).append("</td><<td>")
                        .append(BookConditionID).append("</td><td>").append(BookExpectedLifeYear).append("</td></tr>");

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