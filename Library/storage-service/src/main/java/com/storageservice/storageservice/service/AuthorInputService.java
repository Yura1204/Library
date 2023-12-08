package com.storageservice.storageservice.service;

import com.storageservice.storageservice.model.AuthorInput;
import com.storageservice.storageservice.repository.AuthorInputRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthorInputService {
    private AuthorInputRepository authorInputRepository;

    public AuthorInputService(AuthorInputRepository authorInputRepository) {
        this.authorInputRepository = authorInputRepository;
    }

    @Transactional
    public boolean processAuthorInput(AuthorInput authorInput) {
        try {
            AuthorInput saveAuthorInput = new AuthorInput();
            saveAuthorInput.setAuthorname(authorInput.getAuthorname());
            saveAuthorInput.setBiography(authorInput.getBiography());

            authorInputRepository.save(saveAuthorInput);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
