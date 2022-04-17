package com.example.vhsince81.POJOpackage;

import java.util.List;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;


public class PRODUCTSDTO implements Serializable {

	@SerializedName("title")
	private String title;

	@SerializedName("data")
	private List<DataDTO> data;

	public void setTitle(String title){
		this.title = title;
	}

	public String getTitle(){
		return title;
	}

	public void setData(List<DataDTO> data){
		this.data = data;
	}

	public List<DataDTO> getData(){
		return data;
	}

	@Override
 	public String toString(){
		return 
			"PRODUCTSDTO{" + 
			"title = '" + title + '\'' + 
			",data = '" + data + '\'' + 
			"}";
		}
}