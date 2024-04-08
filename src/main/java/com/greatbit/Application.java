package com.greatbit;


import com.greatbit.models.Book;
//import com.greatbit.models.BooksStorage;
import org.h2.jdbcx.JdbcDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

@SpringBootApplication
public class Application {

    @Bean
    public DataSource h2DataSource(@Value("${jdbcUrl}") String jdbcUrl) {
        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setURL(jdbcUrl);
        dataSource.setUser("user");
        dataSource.setPassword("password");
        return dataSource;
    }

    @Bean
    public CommandLineRunner cmd(DataSource dataSource) {
        return args -> {
            try (InputStream inputStream = this.getClass().getResourceAsStream("/initial.sql")) {
                String sql = new String(inputStream.readAllBytes());
                try (
                        Connection connection = dataSource.getConnection();
                        Statement statement = connection.createStatement()
                ) {
                    statement.executeUpdate(sql);


//                    String insertSql = ("INSERT INTO book (name,author,pages) VALUES (?,?,?)");
//                    try (PreparedStatement preparedStatement = connection.prepareStatement(insertSql)) {
//                        preparedStatement.setString(1, "Java book");
//                        preparedStatement.setString(2, "Oracle");
//                        preparedStatement.setInt(3, 329);
//                        preparedStatement.executeUpdate();
//                    }


                    //System.out.println("Printing books from db...");
                    ResultSet rs = statement.executeQuery("SELECT book_id,name,author, pages FROM book");
                    while (rs.next()) {
                        Book book = new Book(rs.getString(1), rs.getString(2),
                                rs.getString(3), rs.getInt(4));
                        System.out.println(book);
                    }
                }
            }
        };
    }

    public static void main(String[] args) {

        SpringApplication.run(Application.class, args);  //запуск приложения на Spring Boot
    }

}
