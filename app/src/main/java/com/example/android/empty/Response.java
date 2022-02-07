package com.example.android.empty;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Response implements Serializable {
	private List<ResponseItem> response;

	public void setResponse(List<ResponseItem> response){
		this.response = response;
	}

	public List<ResponseItem> getResponse(){
		return response;
	}
}