package com.greenjava.swagger.resource;

import com.greenjava.swagger.model.Contact;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.Configuration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/api")
public class ContactResource {

    Map<String, Contact> contacts=new ConcurrentHashMap<>();

    @GetMapping("/{id}")
    @ApiOperation(value = "find contact By Id",
        notes = "Provide an id to took up specific contact",
        response = Configuration.class)
    public Contact getContact(@ApiParam(value = "Id value for the contact you need to retrieve.",
            required = true)
            @PathVariable String id){
        return contacts.get(id);
    }
    @GetMapping("/")
    public List<Contact> getAllContact(){
        return new ArrayList<>(contacts.values());
    }
    @PostMapping("/")
    public Contact addContact(@RequestBody Contact contact){
        contacts.put(contact.getId(),contact);
        return contact;
    }
}
