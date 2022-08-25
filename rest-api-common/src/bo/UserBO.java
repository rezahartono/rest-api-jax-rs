package bo;

import java.util.ArrayList;
import java.util.List;

import common.DatabaseConnectionManager;
import dao.UserDAO;
import vo.UserVO;
import vo.custom.rest.JWTUserData;

public class UserBO extends DatabaseConnectionManager {

    private static UserBO userBO;

    public static UserBO getInstance() {
        if (userBO == null) {
            userBO = new UserBO();
        }
        return userBO;
    }

    public List<UserVO> getUsers() {
        List<UserVO> users = new ArrayList<>();
        try {
            users = UserDAO.getInstance().getUsers(schemaName, db);
        } catch (Exception e) {
            // TODO: handle exception
        }
        return users;
    }
    
    public UserVO signIn(UserVO user){
        UserVO newUser = new UserVO();
        try {
            newUser = UserDAO.getInstance().signIn(user, schemaName, db);
        } catch (Exception e) {
            // TODO: handle exception
        }
        return newUser;
    }

    public void updateSession(UserVO user, String schema) {
        try {
            UserDAO.getInstance().updateSession(user, schema, db);
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
    
    public boolean sessionCheck(JWTUserData userData){
        boolean isActive = false;
        
        try {
            if(UserDAO.getInstance().sessionCheck(userData, db)){
                isActive = true;
            }
        } catch (Exception e) {
        }
        return isActive;
    }
    
    public boolean signOut(JWTUserData userData){
        boolean isActive = false;
        
        try {
            if(sessionCheck(userData)){
                UserVO user = new UserVO();
                user.setId(userData.getUser_id());
                user.setSession_id(null);
                UserDAO.getInstance().updateSession(user, userData.getSchema_name(), db);
                isActive = true;
                
            }else{
                isActive = false;
            }
        } catch (Exception e) {
        }
        return isActive;
    }
}
