package com.example.library_app.DAO;

import com.example.library_app.model.Person;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PersonMapper implements RowMapper<Person> {
    @Override
    public Person mapRow(ResultSet rs, int rowNum) throws SQLException {
        Person person = new Person();

        person.setPerson_id(rs.getInt("person_id"));
        person.setFio(rs.getString("fio"));
        person.setYear(rs.getInt("year"));
        return person;
    }
}
