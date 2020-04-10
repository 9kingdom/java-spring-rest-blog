package com.pluralsight.blog.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.Hibernate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
public class Author {

    public static final PasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Version //To ensure that the API client(s) aren’t using stale data, we can add a version to the Post and Author entities.
    //// Then every time a record gets updated, hibernate will automatically increment the version counter with 1.
    // //And the clients only need to update if the version has changed.
    //To see this working, first re-run the app. Then in the terminal, run curl -H "Accept: application/json" -i http://localhost:8080/posts/1.
    // You should see ETag: “0” at the top along with the Post’s data.
    //Now we can do another GET, but instead only ask for results if the ETag is not 0 - curl -H "Accept: application/json" -H 'If-None-Match: "0"' -i http://localhost:8080/posts/1.
    //This time, we get HTTP/1.1 304 Not Modified since the ETag is still 0.
    private Long version;

    private String firstname;
    private String lastname;

    @JsonIgnore
    private String username;

    @JsonIgnore
    private String password;

    @OneToMany
    private List<Post> posts;

    public Author() {
        super();
        posts = new ArrayList<>();
    }

    public Author(String username, String firstname, String lastname, String password) {
        this();
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        setPassword(password);
    }

    public void setPassword(String password) {
        this.password = PASSWORD_ENCODER.encode(password);
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstname;
    }

    public void setFirstName(String firstName) {
        this.firstname = firstName;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public List<Post> getPosts() {
        return posts;
    }

    @Override
    public boolean equals(Object obj) {
        Author inputAuthor = (Author) obj;
        if (!this.firstname.equals(inputAuthor.getFirstName())) {
            System.out.println("firstname not equal");
            return false;
        }
        if (!this.lastname.equals(inputAuthor.getLastname())) {
            System.out.println("lastname not equal");
            return false;
        }
        if (!this.username.equals(inputAuthor.getUsername())) {
            System.out.println("username not equal");
            return false;
        }
        return true;
    }

    public void addPost(Post post) {
        posts.add(post);
    }
}
