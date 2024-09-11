package repository;

import Config.Database;
import entities.Consumption;
import entities.Food;
import entities.Housing;
import entities.Transport;

import java.sql.*;
import java.util.List;
import java.util.Optional;

public class ConsumptionRepository implements CrudRepository<Consumption> {

    private Connection connection;

    public ConsumptionRepository() throws SQLException {
        this.connection = Database.getInstance().getConnection();
    }


    @Override
    public Optional<Consumption> save(Consumption consumption) {
        int consumption_id = -1;
        try {
            String query = "INSERT INTO consumption (user_id, start_date, end_date, value, consumption_type, consumption_impact) " +
                    "VALUES (?, ?, ?, ?, CAST(? AS consumption_type), ?) RETURNING id";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, consumption.getUser().getId());
            statement.setDate(2, Date.valueOf(consumption.getStartDate()));
            statement.setDate(3, Date.valueOf(consumption.getEndDate()));
            statement.setDouble(4, consumption.getValue());
            statement.setString(5, consumption.getConsumptionType().name());
            statement.setDouble(6, consumption.calculateConsumptionImpact());
            ResultSet result = statement.executeQuery();

            if (result.next()) {
                consumption_id = result.getInt("id");
            }

            if (consumption instanceof Housing) {
                saveHousing((Housing) consumption , consumption_id);
            } else if (consumption instanceof Transport) {
                saveTranport((Transport) consumption , consumption_id);
            } else if (consumption instanceof Food) {
                saveFood((Food) consumption , consumption_id);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return Optional.empty();
        }
        return Optional.of(consumption);
    }

    public void saveHousing(Housing housing , int consumption_id) {
        try {
            String query = "INSERT INTO housing (consumption_id , energy_type , energy_consumption) VALUES (? , ? , ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, consumption_id);
            statement.setString(2, housing.getEnergyType());
            statement.setDouble(3, housing.getEnergyConsumption());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();

        }
    }

    public void saveTranport(Transport transport , int consumption_id) {
        try {
            String query = "INSERT INTO transport (consumption_id , distance , vehicle_type) VALUES (? , ? , ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, consumption_id);
            statement.setString(2, transport.getTransportType());
            statement.setDouble(3, transport.getDistance());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();

        }
    }

    public void saveFood(Food food , int consumption_id) {
        try {
            String query = "INSERT INTO food (consumption_id , food_type , weight) VALUES (? , ? , ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, consumption_id);
            statement.setString(2, food.getFoodType());
            statement.setDouble(3, food.getWeight());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Consumption> update(Consumption entity) {
        return Optional.empty();
    }

    @Override
    public Optional<Consumption> delete(int id) {
        return Optional.empty();
    }

    @Override
    public Optional<Consumption> findById(int id) {
        return Optional.empty();
    }

    @Override
    public List<Consumption> findAll() {
        return List.of();
    }
}
