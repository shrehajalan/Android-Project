package com.example.vhsince81.DBPOJOpackage;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;


import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.HashMap;

@Entity(tableName = "VH_CartTable")
public class CARTITEMDTO implements Serializable {


	@NonNull
	@PrimaryKey(autoGenerate = true)
	@ColumnInfo(name = "Id")
	@SerializedName("Id")
	private int iD;

	@ColumnInfo(name = "Username")
	@SerializedName("Username")
	private String username;

	@ColumnInfo(name = "Product Category")
	@SerializedName("Product Category")
	private String productCategory;

	@ColumnInfo(name = "Entry Date")
	@SerializedName("Entry Date")
	private String entryDate;

	@ColumnInfo(name = "Style Id")
	@SerializedName("Style Id")
	private String styleId;

	@ColumnInfo(name = "Pack of 1")
	@SerializedName("Pack of 1")
	private String packOf1;

	@SerializedName("Pack of 3")
	private String packOf3;

	@ColumnInfo(name = "Solid color")
	@SerializedName("Solid color")
	private String solidColor;

	@ColumnInfo(name = "Print color")
	@SerializedName("Print color")
	private String printColor;

	@ColumnInfo(name = "Size")
	@SerializedName("Size")
	private String size;

	@ColumnInfo(name = "Price per Quantity")
	@SerializedName("Price per Quantity")
	private String pricePerQuantity;

	@ColumnInfo(name = "Quantity")
	@SerializedName("Quantity")
	private String quantity;

	@ColumnInfo(name = "Status")
	@SerializedName("Status")
	private String status;

	@ColumnInfo(name = "Previous Status ")
	@SerializedName("Previous Status")
	private String previousStatus;



	public void setID(int iD){
		this.iD = iD;
	}

	public int getID(){
		return iD;
	}

	public void setUsername(String username){
		this.username= username;
	}

	public String getUsername(){ return username; }

	public void setStatus(String status){
		this.status= status;
	}

	public void setPreviousStatus(String statusPrev){
		this.previousStatus= previousStatus;
	}

	public String getStatus(){ return status; }

	public String getPreviousStatus(){ return previousStatus; }

	public void setProductCategory(String productCategory){
		this.productCategory = productCategory;
	}

	public String getProductCategory(){
		return productCategory;
	}

	public void setEntryDate(String entryDate){
		this.entryDate = entryDate;
	}

	public String getEntryDate(){
		return entryDate;
	}

	public void setStyleId(String styleId){
		this.styleId = styleId;
	}

	public String getStyleId(){
		return styleId;
	}

	public void setPackOf1(String packOf1){
		this.packOf1 = packOf1;
	}

	public String getPackOf1(){
		return packOf1;
	}

	public void setPackOf3(String packOf3){
		this.packOf3 = packOf3;
	}

	public String getPackOf3(){
		return packOf3;
	}

	public void setSolidColor(String solidColor){
		this.solidColor = solidColor;
	}

	public String getSolidColor(){
		return solidColor;
	}

	public void setPrintColor(String printColor){
		this.printColor = printColor;
	}

	public String getPrintColor(){
		return printColor;
	}

	public void setSize(String size){
		this.size = size;
	}

	public String getSize(){
		return size;
	}

	public void setPricePerQuantity(String pricePerQuantity){
		this.pricePerQuantity = pricePerQuantity;
	}

	public String getPricePerQuantity(){
		return pricePerQuantity;
	}

	public void setQuantity(String quantity){
		this.quantity = quantity;
	}

	public String getQuantity(){
		return quantity;
	}

	@Override
	public String toString(){
		return
				"CARTITEMDTO{" +
						"iD = '" + iD + '\'' +
						",username = '" + username + '\'' +
						",product Category = '" + productCategory + '\'' +
						",entry Date = '" + entryDate + '\'' +
						",style Id = '" + styleId + '\'' +
						",pack of 1 = '" + packOf1 + '\'' +
						",pack of 3 = '" + packOf3 + '\'' +
						",solid color = '" + solidColor + '\'' +
						",print color = '" + printColor + '\'' +
						",size = '" + size + '\'' +
						",status = '" + status + '\'' +
						",previous status = '" + previousStatus + '\'' +
						",price per Quantity = '" + pricePerQuantity + '\'' +
						",quantity = '" + quantity + '\'' +
						"}";
	}
}