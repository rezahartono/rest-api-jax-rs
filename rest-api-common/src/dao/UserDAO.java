package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import vo.UserVO;
import vo.custom.rest.JWTUserData;

public class UserDAO {

    private static UserDAO userDAO;

    public static UserDAO getInstance() {
        if (userDAO == null) {
            userDAO = new UserDAO();
        }
        return userDAO;
    }

    public List<UserVO> getUsers(String schemaName, Connection db) {
        List<UserVO> users = new ArrayList<>();
        String query = "SELECT * FROM " + schemaName + ".\"USERS\" LIMIT " + 10;

        System.out.println("query =>> " + query);

        try {
            PreparedStatement sql = db.prepareStatement(query);
            System.out.println("sql ==> " + sql);

            ResultSet res = sql.executeQuery();

            while (res.next()) {
                int i = 1;
                UserVO user = new UserVO();

                user.setId(res.getString(i++));
                user.setFirst_name(res.getString(i++));
                user.setLast_name(res.getString(i++));
                user.setUsername(res.getString(i++));
                user.setEmail(res.getString(i++));
                user.setPassword(res.getString(i++));
                user.setCreated_at(res.getDate(i++));
                user.setUpdated_at(res.getDate(i++));
                user.setSession_id(res.getString(i++));

                System.out.println(user.toString());

                users.add(user);
            }

        } catch (SQLException e) {
            System.out.println(e);
        }

        return users;
    }

    public UserVO signIn(UserVO user ,String schemaName, Connection db) {
        UserVO newUser = new UserVO();
        List<UserVO> users = new ArrayList<>();
        String query = "SELECT * FROM " + schemaName + ".\"USERS\" WHERE "+"\"username\" = ? AND "+"\"password\" = ?";

        System.out.println("query =>> " + query);

        try {
            PreparedStatement sql = db.prepareStatement(query);
            sql.setString(1, user.getUsername());
            sql.setString(2, user.getPassword());
            System.out.println("sql ==> " + sql);

            ResultSet res = sql.executeQuery();

            while (res.next()) {
                int i = 1;

                newUser.setId(res.getString(i++));
                newUser.setFirst_name(res.getString(i++));
                newUser.setLast_name(res.getString(i++));
                newUser.setUsername(res.getString(i++));
                newUser.setEmail(res.getString(i++));
                newUser.setPassword(res.getString(i++));
                newUser.setCreated_at(res.getDate(i++));
                newUser.setUpdated_at(res.getDate(i++));
                newUser.setSession_id(res.getString(i++));

                System.out.println(newUser.toString());
                users.add(newUser);
            }
            
            newUser = users.get(0);

        } catch (SQLException e) {
            System.out.println(e);
        }

        return newUser;
    }
    
    public void updateSession(UserVO user, String schema, Connection db) {
        String query = "UPDATE "+schema+".\"USERS\" set "+"\"session_id\" = ? WHERE "+"\"id\" = '"+user.getId()+"'";
        System.out.println("Query ===> "+query);
        
        try {
            PreparedStatement sql = db.prepareStatement(query);
            sql.setString(1, user.getSession_id());
            System.out.println("sql ==> " + sql);
            
            sql.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    
    
    public boolean sessionCheck(JWTUserData userData, Connection db) {
        int numberOfRow = 0;
        String query = "SELECT COUNT(*) FROM " + userData.getSchema_name() + ".\"USERS\" WHERE "+"\"id\" = ? AND "+"\"session_id\" = ?";

        System.out.println("query =>> " + query);

        try {
            PreparedStatement sql = db.prepareStatement(query);
            sql.setString(1, userData.getUser_id());
            sql.setString(2, userData.getSession_id());
            System.out.println("sql ==> " + sql);

            ResultSet res = sql.executeQuery();

            while (res.next()) {
                numberOfRow = res.getInt(1);
            }

        } catch (SQLException e) {
            System.out.println(e);
        }

        return numberOfRow == 1;
    }
}
