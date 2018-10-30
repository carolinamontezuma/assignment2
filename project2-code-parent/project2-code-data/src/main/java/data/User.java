package data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;


/**
 * Entity implementation class for Entity: Player
 *
 */
@Entity
public class User implements Serializable {
 private static final long serialVersionUID = 1L;
 @Id @GeneratedValue(strategy=GenerationType.AUTO)
 private int id;
 private String username;
 private String email;
 private String creditCard;
 
 @OneToMany
 private List<Content> watchList;
 
/* @Temporal(TemporalType.DATE)
 @ManyToOne
 private Content content;*/

 
 public User() {
  super();
 }

 public User(String username, String email,String creditCard) {
  super();
  this.username = username;
  this.email = email;
  this.creditCard=creditCard;
  this.watchList = new ArrayList<>();
 }

 public String getUserame() {
	 return username;
 }

 public void setUsername(String username) {
	 this.username = username;
 }

 public String getEmail() {
	 return email;
 }

 public void setEmail(String email) {
	  this.email = email;
 }
 
 public String getCreditCard() {
	 return creditCard;
 }

 public void setCreditCard(String creditCard) {
	 this.creditCard = creditCard;
 }
 
 public List<Content> getWatchList() {
	  return watchList;
	 }
 public void setWatchList(List<Content> watchList) {
	 this.watchList = watchList;
 }


 public static long getSerialversionuid() {
  return serialVersionUID;
 }
 
 @Override
 public String toString() {
  return " id = " +  this.id + " user = " + this.username + " email = " + this.email;
 }
   
}