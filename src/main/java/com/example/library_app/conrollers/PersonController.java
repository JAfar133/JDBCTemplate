package com.example.library_app.conrollers;

import com.example.library_app.DAO.BookDAO;
import com.example.library_app.DAO.PersonDAO;
import com.example.library_app.model.Book;
import com.example.library_app.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/people")
public class PersonController {

    private final PersonDAO personDAO;
    private final BookDAO bookDAO;
    @Autowired
    public PersonController(PersonDAO personDAO,BookDAO bookDAO) {
        this.personDAO = personDAO;
        this.bookDAO = bookDAO;
    }


    @GetMapping
    public String showPersonPage(Model model){
        model.addAttribute("people",personDAO.showPeoples());
        return "person/peoples";
    }

    @GetMapping("/{id}")
    public String personPage(@PathVariable("id") int id, Model model){
        model.addAttribute("person",personDAO.showPerson(id).get());
        model.addAttribute("books",personDAO.getPersonBooks(id));
        return "person/person_page";
    }

    @GetMapping("/new")
    public String addPerson(@ModelAttribute("person") Person person){
        return "person/add_person";
    }

    @PostMapping
    public String addPerson(@ModelAttribute("person") Person person, Model model){
        personDAO.addPerson(person);

        return "redirect:/people";
    }






}
