package com.pluralsight.blog.model;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Post {

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

    @NotNull
    @Size(min=4, max=100)
    private String title;

    @Column(length=1000000)
    @Lob
    private String body;

    @Temporal(TemporalType.DATE)
    @CreationTimestamp
    private Date date;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    private Author author;

    public Post() {
        super();
    }

    public Post(String title, String body){//, Author author) {
        this();
        this.title = title;
        this.body = body;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Date getDate() {
        return date;
    }

    public String getDateStr() {
        DateFormat outputFormatter = new SimpleDateFormat("MM/dd/yyyy");
        return outputFormatter.format(this.date);
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Post))
            return false;
        Post otherPost = (Post)obj;
        return this.title.equals(otherPost.getTitle()) &&
               this.body.equals(otherPost.getBody());
    }
}
