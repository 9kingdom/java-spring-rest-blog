package com.pluralsight.blog.data;

import com.pluralsight.blog.model.Author;
import com.pluralsight.blog.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@RepositoryRestResource(exported = false) //ta adnotacja powoduje, że AuthorRepository nie jest dostępna czyli wszytkie url'e wynikające z tego interfejsu nie są dostępne
public interface AuthorRepository extends JpaRepository<Author, Long> {

}