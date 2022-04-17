package com.example.vhsince81.POJOpackage;

import java.util.List;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class PojoDTO implements Serializable{


	@SerializedName("PRODUCTS")
	private List<PRODUCTSDTO> pRODUCTS;

	public void setPRODUCTS(List<PRODUCTSDTO> pRODUCTS){
		this.pRODUCTS = pRODUCTS;
	}

	public List<PRODUCTSDTO> getPRODUCTS(){
		return pRODUCTS;
	}

	@Override
 	public String toString(){
		return 
			"PojoDTO{" + 
			"pRODUCTS = '" + pRODUCTS + '\'' + 
			"}";
		}
}