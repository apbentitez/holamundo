package es.upm.dit.isst.todo.model;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
@Entity
public class Todo implements Serializable {
private static final long serialVersionUID = 1L;
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
private String author;
private String summary;
private String description;
private String url;
private String date;
boolean finished;
public Todo(String author, String summary, String description,
String url, String date) {
this.author = author;
this.summary = summary;
this.description = description;
this.url = url;
this.date = date;
finished = false;
}
public Long getId() {
return id;
}
public String getAuthor() {
return author;
}
public void setAuthor(String author) {
this.author = author;
}
public String getShortDescription() {
return summary;
}
public void setShortDescription(String shortDescription) {
this.summary = shortDescription;
}
public String getLongDescription() {
return description;
}
public void setLongDescription(String longDescription) {
this.description = longDescription;
}

public String getDate() {
return date;
}
public void setDate(String date) {
this.date = date;
}

public String getUrl() {
return url;
}
public void setUrl(String url) {
this.url = url;
}
public boolean isFinished() {
return finished;
}
public void setFinished(boolean finished) {
this.finished = finished;
}
}