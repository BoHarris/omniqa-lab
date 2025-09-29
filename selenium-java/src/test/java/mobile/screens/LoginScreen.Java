package mobile.screens;

public interface LoginScreen{
    LoginScreen enterUserName(String user);
    LoginScreen enterPassword(String pass);
    void tapLogin();
    String flashMessage();
}