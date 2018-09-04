package test1.android.com.test1.Models;

import com.google.gson.annotations.SerializedName;

public class Post {
    @SerializedName("id")
    private String id;
    @SerializedName("user_id")
    private String userId;

    @SerializedName("post_title")
    private String postTitle;

    @SerializedName("post_content")
    private String postContent;

    public Post(String id, String userId, String postTitle, String postContent) {
        this.id = id;
        this.userId = userId;
        this.postTitle = postTitle;
        this.postContent = postContent;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPostTitle() {
        return postTitle;
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }

    public String getPostContent() {
        return postContent;
    }

    public void setPostContent(String postContent) {
        this.postContent = postContent;
    }
}
