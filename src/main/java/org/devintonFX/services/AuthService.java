package org.devintonFX.services;
import org.devintonFX.model.User;
import org.devintonFX.dao.UserDAO;
import java.sql.ResultSet;
public class AuthService {
    public void register(String fullName, String email, String password) {
        User user = new User(fullName, email, password);
        UserDAO.addUser(user);

        System.out.println("User registered successfully.");
    }

//    public User login(String email, String password) {
//        try {
//            ResultSet rs = UserDAO.findEmail(email);
//            if(rs.next()) {
//                String password2 = rs.getString("password");
//                if(password.equals(password2)) {
//                    System.out.println("Login successful.");
//
//                    return new User(
//                            rs.getInt("id"),
//                            rs.getString("full_name"),
//                            rs.getString("email"),
//                            password2
//                    );
//                }
//            }
//        }catch(Exception e) {
//            System.out.println("Invalid email or password.");
//        }
//        System.out.println("Invalid Credentials.");
//        return null;
//    }
}
