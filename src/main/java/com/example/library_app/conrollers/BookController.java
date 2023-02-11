package com.example.library_app.conrollers;

import com.example.library_app.DAO.BookDAO;
import com.example.library_app.DAO.PersonDAO;
import com.example.library_app.model.Book;
import com.example.library_app.model.Person;
import com.example.library_app.util.BookValidator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/book")
public class BookController {


    private final BookDAO bookDAO;
    private final PersonDAO personDAO;
    private final BookValidator bookValidator;

    @Autowired
    public BookController(BookDAO bookDAO, PersonDAO personDAO, BookValidator bookValidator) {
        this.bookDAO = bookDAO;
        this.personDAO = personDAO;
        this.bookValidator = bookValidator;
    }
    @GetMapping
    public String showBooksPage(Model model){

        model.addAttribute("books",bookDAO.showBooks());


        return "book/books";
    }

    @GetMapping("/{id}")
    public String showBookPage(@PathVariable("id") int id,
                               @ModelAttribute("person") Person person,Model model){
        model.addAttribute("book",bookDAO.showBook(id).get());
        Person person1;
        try {
            person1 = bookDAO.getUserOfBook(id).get();
        }catch (Exception e){
            person1=null;
        }
        model.addAttribute("personWhoUsingBook",person1);
        model.addAttribute("people",personDAO.showPeoples());

        return "book/book_page";
    }

    @GetMapping("/new")
    public String addBook(@ModelAttribute("book") Book book){
        return "book/add_book";
    }

    @PostMapping
    public String addBook(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult, Model model){
        bookValidator.validate(book,bindingResult);
        if(bindingResult.hasErrors()) return "/book/new";

        bookDAO.addBook(book);

        return "redirect:/book";
    }

    @PatchMapping("/{id}/give")
    public String giveBook( @ModelAttribute("person") Person person,
                            @PathVariable("id") int id, HttpServletRequest request){
        String give = request.getParameter("give");
        String free = request.getParameter("freeBook");
        if(give!=null)
            bookDAO.giveBookToPerson(id, person.getPerson_id());
        else if(free!=null)
            bookDAO.freeBook(id);

        return "redirect:/book/"+id;
    }
    @DeleteMapping("/{id}/delete")
    public String delBook(@PathVariable("id") int id){
        bookDAO.deleteBook(id);
        return "redirect:/book";
    }

    @GetMapping("/edit/{id}")
    public String editBook(@PathVariable("id") int id, Model model){
        System.out.println(bookDAO.showBook(id).get());
        model.addAttribute("book",bookDAO.showBook(id).get());
        return "book/edit";
    }

    @PatchMapping("/{id}/edit")
    public String editBook(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult,
                           @PathVariable("id") int id){
        if(bindingResult.hasErrors()) return "/book/edit";
        bookDAO.updateBook(id,book);
        return "redirect:/book";
    }
}
