package repository;

import Config.Database;
import entities.*;
import entities.enums.ConsumptionType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class UserRepository implements CrudRepository<User> {

    private Connection connection;

    public UserRepository() throws SQLException {
        this.connection = Database.getInstance().getConnection();
    }


    @Override
    public Optional<User> save(User entity) {
        try {
            String query = "INSERT INTO users (name, age) VALUES (?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, entity.getName());
            statement.setInt(2, entity.getAge());
            statement.executeUpdate();
            return Optional.of(entity);
        } catch (SQLException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public Optional<User> update(User entity) {
        try {
            String query = "UPDATE users SET name = ?, age = ? WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, entity.getName());
            statement.setInt(2, entity.getAge());
            statement.setInt(3, entity.getId());
            statement.executeUpdate();
            return Optional.of(entity);
        } catch (SQLException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public Optional<User> delete(int id) {
        try {
            String query = "DELETE FROM users WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            statement.executeUpdate();
            return Optional.of(new User());
        } catch (SQLException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public Optional<User> findById(int id) {
        try {
            String query = "SELECT * FROM users WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setName(resultSet.getString("name"));
                user.setAge(resultSet.getInt("age"));
                return Optional.of(user);
            }
            return Optional.empty();
        } catch (SQLException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public List<User> findAll() {
        try {
            String query = "SELECT * FROM users";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            List<User> users = new ArrayList<>();
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setName(resultSet.getString("name"));
                user.setAge(resultSet.getInt("age"));
                users.add(user);
            }
            return users;
        } catch (SQLException e) {
            e.printStackTrace();
            return List.of();
        }
    }



    public List<User> findAllUsersWithConsumptions() {
        Map<Integer , User> userMap = new HashMap<>();

        String query = "SELECT u.id as user_id, u.name, u.age, " +
                "c.id as consumption_id, c.start_date, c.end_date, " +
                "c.value, c.consumption_impact, c.consumption_type, " +
                "t.distance, t.vehicle_type, " +
                "h.energy_consumption, h.energy_type, " +
                "f.food_type, f.weight " +
                "FROM users u " +
                "LEFT JOIN consumption c ON u.id = c.user_id " +
                "LEFT JOIN transport t ON c.id = t.id " +
                "LEFT JOIN housing h ON c.id = h.id " +
                "LEFT JOIN food f ON c.id = f.id;";

        try (PreparedStatement stm = connection.prepareStatement(query)) {
            ResultSet result = stm.executeQuery();
            while (result.next()) {
                int userId = result.getInt("user_id");

                User user = userMap.get(userId);
                if (user == null) {
                    user = new User();
                    user.setId(userId);
                    user.setName(result.getString("name"));
                    user.setAge(result.getInt("age"));
                    user.setConsumptions(new ArrayList<>());
                    userMap.put(userId, user);
                }

                int consumptionId = result.getInt("consumption_id");
                if (consumptionId != 0) {
                    Consumption consumption = null;
                    String consumptionType = result.getString("consumption_type");

                    switch (ConsumptionType.valueOf(consumptionType)) {
                        case TRANSPORT:
                            Transport transport = new Transport();
                            transport.setDistance(result.getDouble("distance"));
                            transport.setTransportType(result.getString("vehicle_type"));
                            consumption = transport;
                            break;
                        case HOUSING:
                            Housing housing = new Housing();
                            housing.setEnergyConsumption(result.getDouble("energy_consumption"));
                            housing.setEnergyType(result.getString("energy_type"));
                            consumption = housing;
                            break;
                        case FOOD:
                            Food food = new Food();
                            food.setFoodType(result.getString("food_type"));
                            food.setWeight(result.getDouble("weight"));
                            consumption = food;
                            break;
                    }

                    if (consumption != null) {
                        consumption.setId(consumptionId);
                        consumption.setStartDate(result.getDate("start_date").toLocalDate());
                        consumption.setEndDate(result.getDate("end_date").toLocalDate());
                        consumption.setValue(result.getFloat("value"));
                        consumption.setConsumptionImpact(result.getDouble("consumption_impact"));
                        consumption.setConsumptionType(ConsumptionType.valueOf(consumptionType));
                        user.getConsumptions().add(consumption);
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return new ArrayList<>(userMap.values());
    }
}
