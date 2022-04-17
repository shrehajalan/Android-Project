

package com.example.vhsince81.POJOpackage;


import java.util.List;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class DataDTO implements Serializable {

	@SerializedName("Style_id")
	private String styleId;

	@SerializedName("src")
	private String src;

	@SerializedName("availble_Colors_Pack_Of_1")
	private List<String> availbleColorsPackOf1;

	@SerializedName("availble_Colors_Pack_Of_3")
	private List<String> availbleColorsPackOf3;

	@SerializedName("availble_sizes")
	private List<Object> availbleSizes;


	@SerializedName("availble_colors")
	private List<Object> availbleColors;

	@SerializedName("availble_print_colors")
	private List<Object> availblePrintColors;

	@SerializedName("prices")
	private List<Object> prices;

	public void setStyleId(String styleId){
		this.styleId = styleId;
	}

	public String getStyleId(){
		return styleId;
	}

	public void setSrc(String src){
		this.src = src;
	}

	public String getSrc(){
		return src;
	}

	public void setAvailbleColorsPackOf1(List<String> availbleColorsPackOf1){
		this.availbleColorsPackOf1 = availbleColorsPackOf1;
	}

	public List<String> getAvailbleColorsPackOf1(){
		return availbleColorsPackOf1;
	}

	public void setAvailbleColorsPackOf3(List<String> availbleColorsPackOf3){
		this.availbleColorsPackOf3 = availbleColorsPackOf3;
	}

	public List<String> getAvailbleColorsPackOf3(){
		return availbleColorsPackOf3;
	}

	public void setAvailbleSizes(List<Object> availbleSizes){
		this.availbleSizes = availbleSizes;
	}

	public List<Object> getAvailbleSizes(){
		return availbleSizes;
	}

	public void setAvailbleColors(List<Object> availbleColors){
		this.availbleColors = availbleColors;
	}

	public List<Object> getAvailbleColors(){
		return availbleColors;
	}

	public void setAvailblePrintColors(List<Object> availblePrintColors){
		this.availblePrintColors = availblePrintColors;
	}

	public List<Object> getAvailblePrintColors(){
		return availblePrintColors;
	}

	public void setPrices(List<Object> prices){
		this.prices = prices;
	}

	public List<Object> getPrices(){
		return prices;
	}

	@Override
	public String toString(){
		return
				"DataDTO{" +
						"style_id = '" + styleId + '\'' +
						",src = '" + src + '\'' +
						",availble_Colors_Pack_Of_1 = '" + availbleColorsPackOf1 + '\'' +
						",availble_Colors_Pack_Of_3 = '" + availbleColorsPackOf3 + '\'' +
						",availble_sizes = '" + availbleSizes + '\'' +
						",availble_colors = '" + availbleColors + '\'' +
						",availble_print_colors = '" + availblePrintColors + '\'' +
						",prices = '" + prices + '\'' +
						"}";
	}
}