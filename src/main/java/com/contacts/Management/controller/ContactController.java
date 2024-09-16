package com.contacts.Management.controller;

import com.contacts.Management.dto.ContactUser;
import com.contacts.Management.dto.ContactResponse;
import com.contacts.Management.exception.ContactServiceException;
import com.contacts.Management.service.ContactService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contacts")
public class ContactController {

    private final ContactService contactService;

    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContactResponse> getContactById(@PathVariable Long id) {
        try {
            ContactUser contact = contactService.getContactById(id);
            return ResponseEntity.ok(new ContactResponse("Contact found", false, contact));
        } catch (ContactServiceException e) {
            return ResponseEntity.status(e.getHttpStatus())
                    .body(new ContactResponse(e.getMessage(), true, null));
        }
    }

    @GetMapping
    public ResponseEntity<ContactResponse> getAllContacts() {
        try {
            List<ContactUser> contacts = contactService.getAllContacts();
            return ResponseEntity.ok(new ContactResponse("Contacts retrieved successfully", false, contacts));
        } catch (ContactServiceException e) {
            return ResponseEntity.status(e.getHttpStatus())
                    .body(new ContactResponse(e.getMessage(), true, null));
        }
    }

    @PostMapping
    public ResponseEntity<ContactResponse> createContact(@RequestBody ContactUser contactUser) {
        try {
            ContactUser createdContact = contactService.createContact(contactUser);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ContactResponse("Contact created successfully", false, createdContact));
        } catch (ContactServiceException e) {
            return ResponseEntity.status(e.getHttpStatus())
                    .body(new ContactResponse(e.getMessage(), true, null));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ContactResponse> updateContact(@PathVariable Long id, @RequestBody ContactUser contactUser) {
        try {
            ContactUser updatedContact = contactService.updateContact(id, contactUser);
            return ResponseEntity.ok(new ContactResponse("Contact updated successfully", false, updatedContact));
        } catch (ContactServiceException e) {
            return ResponseEntity.status(e.getHttpStatus())
                    .body(new ContactResponse(e.getMessage(), true, null));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ContactResponse> deleteContact(@PathVariable Long id) {
        try {
            contactService.deleteContact(id);
            return ResponseEntity.ok(new ContactResponse("Contact deleted successfully", false, null));
        } catch (ContactServiceException e) {
            return ResponseEntity.status(e.getHttpStatus())
                    .body(new ContactResponse(e.getMessage(), true, null));
        }
    }

    @DeleteMapping("/batchdelete")
    public ResponseEntity<ContactResponse> batchDeleteContacts(@RequestBody List<Long> contactIds) {
        try {
            contactService.batchDeleteContacts(contactIds);
            return ResponseEntity.ok(new ContactResponse("Contacts deleted successfully", false, null));
        } catch (ContactServiceException e) {
            return ResponseEntity.status(e.getHttpStatus())
                    .body(new ContactResponse(e.getMessage(), true, null));
        }
    }
}