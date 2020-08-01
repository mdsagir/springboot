package com.green.springbootjwt.request;

public class RefreshTokenRequest {

    private String client_Id;
    private String secret_id;
    private String refresh_token;

    public RefreshTokenRequest(String client_Id, String secret_id, String refresh_token) {
        this.client_Id = client_Id;
        this.secret_id = secret_id;
        this.refresh_token = refresh_token;
    }

    public RefreshTokenRequest() {
    }

    public String getClient_Id() {
        return client_Id;
    }

    public void setClient_Id(String client_Id) {
        this.client_Id = client_Id;
    }

    public String getSecret_id() {
        return secret_id;
    }

    public void setSecret_id(String secret_id) {
        this.secret_id = secret_id;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }
}
