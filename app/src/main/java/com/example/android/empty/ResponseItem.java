package com.example.android.empty;

import com.google.gson.annotations.SerializedName;

public class ResponseItem{

	@SerializedName("answerId")
	private String answerId;

	@SerializedName("commentBy")
	private String commentBy;

	@SerializedName("parentComment")
	private String parentComment;

	@SerializedName("postedOn")
	private int postedOn;

	@SerializedName("id")
	private String id;

	@SerializedName("message")
	private String message;

	public void setAnswerId(String answerId){
		this.answerId = answerId;
	}

	public String getAnswerId(){
		return answerId;
	}

	public void setCommentBy(String commentBy){
		this.commentBy = commentBy;
	}

	public String getCommentBy(){
		return commentBy;
	}

	public void setParentComment(String parentComment){
		this.parentComment = parentComment;
	}

	public String getParentComment(){
		return parentComment;
	}

	public void setPostedOn(int postedOn){
		this.postedOn = postedOn;
	}

	public int getPostedOn(){
		return postedOn;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
	}
}