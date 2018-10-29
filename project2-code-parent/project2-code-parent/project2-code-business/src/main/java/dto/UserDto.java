package dto;

import java.io.Serializable;

public class UserDTO implements Serializable{
	private int id;
	 private String username;
	 private String email;
	 private String creditCard;
	 
	 /*private List<Content> watchList;*/
	 
	 /*@Temporal(TemporalType.DATE)*/
	 /*@ManyToOne
	 private Content content;*/

	 
	 public UserDTO() {
	  super();
	 }

	 public UserDTO(String username, String email,String creditCard) {
	  super();
	  this.username = username;
	  this.email = email;
	  this.creditCard=creditCard;
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
	 
	/* public List<Content> getWatchList() {
		  return watchList;
		 }
	 public void setWatchList(List<Content> watchList) {
		 this.watchList = watchList;
	 }*/


	
	 @Override
	 public String toString() {
	  return " id = " +  this.id + " user = " + this.username + " email = " + this.email;
	 }
}
