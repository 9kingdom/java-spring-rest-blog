package com.pluralsight.blog.data;

import com.pluralsight.blog.model.Author;
import com.pluralsight.blog.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RepositoryRestResource(exported = false) //ta adnotacja powoduje, że AuthorRepository nie jest dostępna czyli nie ma url'i prowadzących do .../authors
public interface AuthorRepository extends JpaRepository<Author, Long> {

}