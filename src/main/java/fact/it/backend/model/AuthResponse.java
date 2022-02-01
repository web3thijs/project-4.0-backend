package fact.it.backend.model;

public class AuthResponse {
    private String response;

    public AuthResponse() {
    }

    public AuthResponse(String response) {
        this.response = response;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
