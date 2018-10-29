package dto;

import java.io.Serializable;
import java.util.List;

public class UserDto implements Serializable {
	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	 private String username;
	 private String email;
	 private String creditCard;
	 
	 private List<ContentDto> watchList;
	 	 
	 public UserDto() {
	  super();
	 }

	 public UserDto(String username, String email,String creditCard) {
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
	 
	 public List<ContentDto> getWatchList() {
		  return watchList;
		 }
	 public void setWatchList(List<ContentDto> watchList) {
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