package com.example.library_app.DAO;

import com.example.library_app.model.Book;
import com.example.library_app.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class BookDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> showBooks(){
        return jdbcTemplate.query("select * from book",new BeanPropertyRowMapper<>(Book.class));
    }

    public Optional<Book> showBook(int id){
        return jdbcTemplate.query("select * from book where book_id=?",new Object[]{id},
                new BeanPropertyRowMapper<>(Book.class)).stream().findAny();
    }
    public Optional<Book> showBook(String name){
        return jdbcTemplate.query("select * from book where name=?",new Object[]{name},
                new BeanPropertyRowMapper<>(Book.class)).stream().findAny();
    }

    public void addBook(Book book){
        jdbcTemplate.update("insert into book(name,author,year) values(?,?,?)",
                book.getName(),book.getAuthor(),book.getYear());
    }

    public void deleteBook(int id){
        jdbcTemplate.update("delete from book where book_id=?",id);
    }

    public void updateBook(int id, Book book){
        jdbcTemplate.update("update book set name=?,author=?,year=? where book_id=?",
                book.getName(),book.getAuthor(),book.getYear(),id);
    }

    public void giveBookToPerson(int book_id, int person_id){
        jdbcTemplate.update("update book set person_id=? where book_id=?",person_id,book_id);
    }
    public Optional<Person> getUserOfBook(int book_id){
        return jdbcTemplate.query("select * from book b left join person p on p.person_id=b.person_id where b.book_id=?",
                new Object[]{book_id},new BeanPropertyRowMapper<Person>(Person.class))
                .stream().findAny();

    }
    public void freeBook(int book_id){
        jdbcTemplate.update("update book set person_id=null where book_id=?",book_id);
    }



}
