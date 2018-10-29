package data;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Content implements Serializable {
 private static final long serialVersionUID = 1L;
 
 @Id @GeneratedValue(strategy=GenerationType.AUTO)
 
 int id;
 private String title;
 private String director;
 private int year;
 private String category;
 
 
 public Content() {
  super();
 }
 
 public Content(String title, String director, int year, String category) {
  super();
  this.title = title;
  this.director = director;
  this.year = year;
  this.category=category;
 }

 public String getTitle() {
  return title;
 }
 public void setTitle(String title) {
  this.title = title;
 }
 public String Director() {
  return director;
 }
 public void setDirector(String director) {
  this.director = director;
 }
 public int getYear() {
  return year;
 }
 public void setYear(int year) {
	  this.year=year;
 }
 public String getCategory() {
	  return category;
	 }
 public void setCategory(String category) {
	 this.category=category;
 }
 
 @Override
 public String toString() {
  return " title = " +  this.title + " director = " + this.director + " year = " + this.year + "category = "+this.category;
 }
 
 
}