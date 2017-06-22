/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.controllers;

import java.util.List;
import javax.validation.Valid;
import net.model.Person;
import net.services.PersonService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
/**
 *
 * @author Jakub
 */
@Controller
public class personController {
    
    private PersonService personService;
    private static int page;
    public static final double personOnPage=5.0;
    
    public personController(PersonService personService) {
        this.personService = personService;
    }
    
    @RequestMapping("/")
    public String showStartPage() {
        return "index";
    }
      
    @RequestMapping(value="/addPersonView", method=RequestMethod.GET)
    public String showAddForm() {
        return "addPersonView";
    }
    
    @RequestMapping(value = "/addPerson", method = RequestMethod.POST)
    public String addPerson(@Valid Person person, BindingResult result) 
    {
        if (result.hasErrors()) {
            return "addPersonView";
        } else {
            if(personService.find(person.getId()) == null) {
                personService.save(person);
            } else {
                System.out.print("Person with id "+ person.getId() + " exist in database");
            }
        }
        return "index";
    }
    
    @RequestMapping(value = "/showPersonView",method = RequestMethod.GET)
    public ModelAndView showPerson(Person person,@RequestParam("sortedColumn") String sortedColumn,
                                                 @RequestParam("page") int page) {
        
        ModelAndView model = new ModelAndView("showPersonView");
        List<Person> ListPerson = personService.getSortedList(page, sortedColumn,person);
        double amountOfPages = Math.ceil(personService.howManyForSortedList(page, sortedColumn,person)/personOnPage);   
        page = 1;
        model.addObject("person",person);
        model.addObject("page",page);
        model.addObject("listOfPerson", ListPerson);
        model.addObject("sortedColumn", sortedColumn); 
        if(amountOfPages != 0) {
            model.addObject("amountOfPages",amountOfPages);
        } else {
            model.addObject("amountOfPages",1);
        }
        
        return model;
    }
    
}
