package com.example.onlineshop.model.orders;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class LineItemsItem{

	@SerializedName("quantity")
	private int quantity;

	@SerializedName("tax_class")
	private String taxClass;

	@SerializedName("taxes")
	private List<Object> taxes;

	@SerializedName("total_tax")
	private String totalTax;

	@SerializedName("total")
	private String total;

	@SerializedName("variation_id")
	private int variationId;

	@SerializedName("subtotal")
	private String subtotal;

	@SerializedName("price")
	private int price;

	@SerializedName("product_id")
	private int productId;

	@SerializedName("name")
	private String name;

	@SerializedName("meta_data")
	private List<Object> metaData;

	@SerializedName("id")
	private int id;

	@SerializedName("subtotal_tax")
	private String subtotalTax;

	@SerializedName("sku")
	private String sku;

	public void setQuantity(int quantity){
		this.quantity = quantity;
	}

	public int getQuantity(){
		return quantity;
	}

	public void setTaxClass(String taxClass){
		this.taxClass = taxClass;
	}

	public String getTaxClass(){
		return taxClass;
	}

	public void setTaxes(List<Object> taxes){
		this.taxes = taxes;
	}

	public List<Object> getTaxes(){
		return taxes;
	}

	public void setTotalTax(String totalTax){
		this.totalTax = totalTax;
	}

	public String getTotalTax(){
		return totalTax;
	}

	public void setTotal(String total){
		this.total = total;
	}

	public String getTotal(){
		return total;
	}

	public void setVariationId(int variationId){
		this.variationId = variationId;
	}

	public int getVariationId(){
		return variationId;
	}

	public void setSubtotal(String subtotal){
		this.subtotal = subtotal;
	}

	public String getSubtotal(){
		return subtotal;
	}

	public void setPrice(int price){
		this.price = price;
	}

	public int getPrice(){
		return price;
	}

	public void setProductId(int productId){
		this.productId = productId;
	}

	public int getProductId(){
		return productId;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setMetaData(List<Object> metaData){
		this.metaData = metaData;
	}

	public List<Object> getMetaData(){
		return metaData;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setSubtotalTax(String subtotalTax){
		this.subtotalTax = subtotalTax;
	}

	public String getSubtotalTax(){
		return subtotalTax;
	}

	public void setSku(String sku){
		this.sku = sku;
	}

	public String getSku(){
		return sku;
	}

	@Override
 	public String toString(){
		return 
			"LineItemsItem{" + 
			"quantity = '" + quantity + '\'' + 
			",tax_class = '" + taxClass + '\'' + 
			",taxes = '" + taxes + '\'' + 
			",total_tax = '" + totalTax + '\'' + 
			",total = '" + total + '\'' + 
			",variation_id = '" + variationId + '\'' + 
			",subtotal = '" + subtotal + '\'' + 
			",price = '" + price + '\'' + 
			",product_id = '" + productId + '\'' + 
			",name = '" + name + '\'' + 
			",meta_data = '" + metaData + '\'' + 
			",id = '" + id + '\'' + 
			",subtotal_tax = '" + subtotalTax + '\'' + 
			",sku = '" + sku + '\'' + 
			"}";
		}
}