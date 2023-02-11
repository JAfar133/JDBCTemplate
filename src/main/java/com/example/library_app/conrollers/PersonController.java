package com.example.library_app.conrollers;

import com.example.library_app.DAO.BookDAO;
import com.example.library_app.DAO.PersonDAO;
import com.example.library_app.model.Book;
import com.example.library_app.model.Person;
import com.example.library_app.util.PersonValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/people")
public class PersonController {

    private final PersonDAO personDAO;
    private final BookDAO bookDAO;
    private final PersonValidator personValidator;
    @Autowired
    public PersonController(PersonDAO personDAO, BookDAO bookDAO, PersonValidator personValidator) {
        this.personDAO = personDAO;
        this.bookDAO = bookDAO;
        this.personValidator = personValidator;
    }


    @GetMapping
    public String showPersonPage(Model model){
        model.addAttribute("people",personDAO.showPeoples());
        return "person/peoples";
    }

    @GetMapping("/{id}")
    public String personPage(@PathVariable("id") int id, Model model,@ModelAttribute Book book){
        model.addAttribute("person",personDAO.showPerson(id).get());
        model.addAttribute("books",personDAO.getPersonBooks(id));
        model.addAttribute("free_books",personDAO.showFreeBooks());
        return "person/person_page";
    }

    @DeleteMapping("/{id}/delete")
    public String delPerson(@PathVariable("id") int id){
        personDAO.deletePerson(id);
        return "redirect:/people";
    }
    @PatchMapping("/{id}/add")
    public String addFreeBook(@PathVariable("id") int id, @ModelAttribute("book") Book book ){
        bookDAO.giveBookToPerson(book.getBook_id(), id);

        return "redirect:/people/"+id;
    }

    @GetMapping("/new")
    public String addPerson(@ModelAttribute("person") Person person){
        return "person/add_person";
    }

    @PostMapping
    public String addPerson(@ModelAttribute("person") @Valid Person person,
                            BindingResult bindingResult,Model model){
        personValidator.validate(person,bindingResult);

        if(bindingResult.hasErrors()) return "person/add_person";
        personDAO.addPerson(person);

        return "redirect:/people";
    }

    @GetMapping("/edit/{id}")
    public String editPerson(@PathVariable("id") int id,Model model){
        model.addAttribute("person",personDAO.showPerson(id).get());
        return "person/edit";
    }

    @PatchMapping("/{id}/edit")
    public String editPerson(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult,
                             @PathVariable("id") int id){
        if(bindingResult.hasErrors()) return "person/edit";
        personDAO.updatePerson(id,person);
        return "redirect:/people";
    }








}
