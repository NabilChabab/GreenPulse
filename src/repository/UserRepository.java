package repository;

import Config.Database;
import entities.UserEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserRepository implements RepositoryInterface<UserEntity>{

    private Connection connection;

    public UserRepository() throws SQLException {
        this.connection = Database.getInstance().getConnection();
    }


    @Override
    public void add(UserEntity user) {
        String query = "INSERT INTO users (name , age) VALUES (?, ?)";
        try {
            PreparedStatement preparedStatement =  connection.prepareStatement(query);
            preparedStatement.setString(1, user.getName());
            preparedStatement.setInt(2, user.getAge());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public UserEntity getById(int id){
        String query = "SELECT * FROM users WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new UserEntity(rs.getInt("id") , rs.getString("name"), rs.getInt("age"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public List<UserEntity> getAll() {
        List<UserEntity> users = new ArrayList<>();
        String query = "SELECT * FROM users";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                users.add(new UserEntity(rs.getInt("id") , rs.getString("name"), rs.getInt("age")));
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    @Override
    public void update(UserEntity user) {
        String query = "UPDATE users SET name = ?, age = ? WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, user.getName());
            ps.setInt(2, user.getAge());
            ps.setInt(3, user.getId());
            ps.executeUpdate();
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(int id){
        String query = "DELETE FROM users WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
