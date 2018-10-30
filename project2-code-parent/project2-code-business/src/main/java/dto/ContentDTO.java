package dto;

import java.io.Serializable;

import data.Content;

public class ContentDTO implements Serializable{
	int id;
	 private String title;
	 private String director;
	 private int year;
	 private String category;
	 
	 
	 public ContentDTO() {
	  super();
	 }
	 
	 public ContentDTO(Content c)
	 {
		 this(c.getTitle(), c.Director(), c.getYear(), c.getCategory());
	 }
	 
	 public ContentDTO(String title, String director, int year, String category) {
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
