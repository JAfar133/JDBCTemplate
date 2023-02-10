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
public class PersonDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public List<Person> showPeoples(){
        return jdbcTemplate.query("select * from person",new BeanPropertyRowMapper<>(Person.class));
    }

    public Optional<Person> showPerson(int id){
        return jdbcTemplate.query("select * from person where person_id=?",new Object[]{id},
                new BeanPropertyRowMapper<>(Person.class)).stream().findAny();
    }

    public void addPerson(Person person){
        jdbcTemplate.update("insert into person(fio,year) values(?,?)",
                person.getFio(),person.getYear());
    }

    public void deletePerson(int id){
        jdbcTemplate.update("delete from book where person_id=?",id);
    }

    public void updatePerson(int id, Person person){
        jdbcTemplate.update("update person set fio=?,year=? where person_id=?",
               person.getFio(),person.getYear(),id);
    }
    public List<Book> getPersonBooks(int id){
        return jdbcTemplate.query("select * from book where person_id=?",
                new Object[]{id},new BeanPropertyRowMapper<Book>(Book.class));
    }
}
