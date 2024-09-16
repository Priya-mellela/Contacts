package com.contacts.Management.Service;


import com.contacts.Management.dto.ContactUser;
import com.contacts.Management.Exception.ContactServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ContactService {

    private List<ContactUser> contacts = new ArrayList<>();
    private long idCounter = 1;

    public ContactUser createContact(ContactUser contactUser) throws ContactServiceException {
        contactUser.setId(idCounter++);
        contactUser.setCreatedDate(java.time.LocalDateTime.now());
        contacts.add(contactUser);
        return contactUser;
    }

    public ContactUser getContactById(Long id) throws ContactServiceException {
        return contacts.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ContactServiceException("Contact not found", HttpStatus.NOT_FOUND));
    }

    public List<ContactUser> getAllContacts() throws ContactServiceException {
        return contacts;
    }

    public ContactUser updateContact(Long id, ContactUser contactUser) throws ContactServiceException {
        ContactUser existingContact = getContactById(id);
        existingContact.setName(contactUser.getName());
        existingContact.setEmail(contactUser.getEmail());
        existingContact.setPhone(contactUser.getPhone());
        existingContact.setAddress(contactUser.getAddress());
        existingContact.setPosition(contactUser.getPosition());
        existingContact.setUpdatedDate(java.time.LocalDateTime.now());
        return existingContact;
    }

    public void deleteContact(Long id) throws ContactServiceException {
        boolean removed = contacts.removeIf(c -> c.getId().equals(id));
        if (!removed) {
            throw new ContactServiceException("Contact not found", HttpStatus.NOT_FOUND);
        }
    }

    public void batchDeleteContacts(List<Long> contactIds) throws ContactServiceException {
        List<Long> existingIds = contacts.stream()
                .map(ContactUser::getId)
                .collect(Collectors.toList());
        if (!existingIds.containsAll(contactIds)) {
            throw new ContactServiceException("Some contacts not found", HttpStatus.NOT_FOUND);
        }
        contacts.removeIf(c -> contactIds.contains(c.getId()));
    }
}
