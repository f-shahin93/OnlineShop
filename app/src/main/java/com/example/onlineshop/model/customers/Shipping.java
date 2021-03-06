package com.example.onlineshop.model.customers;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class Shipping extends RealmObject {

	@SerializedName("country")
	private String country;

	@SerializedName("city")
	private String city;

	@SerializedName("address_1")
	private String address1;

	@SerializedName("address_2")
	private String address2;

	@SerializedName("postcode")
	private String postcode;

	@SerializedName("last_name")
	private String lastName;

	@SerializedName("company")
	private String company;

	@SerializedName("state")
	private String state;

	@SerializedName("first_name")
	private String firstName;

	public void setCountry(String country){
		this.country = country;
	}

	public String getCountry(){
		return country;
	}

	public void setCity(String city){
		this.city = city;
	}

	public String getCity(){
		return city;
	}

	public void setAddress1(String address1){
		this.address1 = address1;
	}

	public String getAddress1(){
		return address1;
	}

	public void setAddress2(String address2){
		this.address2 = address2;
	}

	public String getAddress2(){
		return address2;
	}

	public void setPostcode(String postcode){
		this.postcode = postcode;
	}

	public String getPostcode(){
		return postcode;
	}

	public void setLastName(String lastName){
		this.lastName = lastName;
	}

	public String getLastName(){
		return lastName;
	}

	public void setCompany(String company){
		this.company = company;
	}

	public String getCompany(){
		return company;
	}

	public void setState(String state){
		this.state = state;
	}

	public String getState(){
		return state;
	}

	public void setFirstName(String firstName){
		this.firstName = firstName;
	}

	public String getFirstName(){
		return firstName;
	}

	@Override
 	public String toString(){
		return 
			"Shipping{" + 
			"country = '" + country + '\'' + 
			",city = '" + city + '\'' + 
			",address_1 = '" + address1 + '\'' + 
			",address_2 = '" + address2 + '\'' + 
			",postcode = '" + postcode + '\'' + 
			",last_name = '" + lastName + '\'' + 
			",company = '" + company + '\'' + 
			",state = '" + state + '\'' + 
			",first_name = '" + firstName + '\'' + 
			"}";
		}

	public Shipping(String firstName,String lastName) {
		this.lastName = lastName;
		this.firstName = firstName;
	}

	public Shipping() {
	}

	public Shipping(String country, String city, String address1, String address2, String postcode, String lastName, String company, String state, String firstName) {
		this.country = country;
		this.city = city;
		this.address1 = address1;
		this.address2 = address2;
		this.postcode = postcode;
		this.lastName = lastName;
		this.company = company;
		this.state = state;
		this.firstName = firstName;
	}
}