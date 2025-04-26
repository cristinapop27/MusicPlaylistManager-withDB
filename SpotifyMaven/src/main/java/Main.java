import model.UserManager;
import view.LoginPageView;
public class Main {
    public static void main(String[] args) {
        UserManager userManager = new UserManager();
        LoginPageView loginPageView = new LoginPageView(userManager);
    }
}
