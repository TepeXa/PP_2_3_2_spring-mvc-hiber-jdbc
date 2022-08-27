package web.dao;

import org.springframework.stereotype.Component;
import web.models.Car;


//import java.sql.Connection;
import javax.sql.DataSource;
//import java.sql.DriverManager;
//import java.sql.DriverManager;
//import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.sql.*;


@Component
public class carDAO {
    private static int CAR_COUNT;

    /* private List<Car> cars;
        {
        cars = new ArrayList<>();
        cars.add(new Car(++CAR_COUNT,"Audi", 249, "black"));
        cars.add(new Car(++CAR_COUNT,"Lada",77,"Baklazan"));
        cars.add(new Car(++CAR_COUNT,"Mers", 200, "white"));
        cars.add(new Car(++CAR_COUNT,"Scoda",180, "red"));
        cars.add(new Car(++CAR_COUNT,"Lamba",999, "purple"));
    }
    */

    private static final String URL="jdbc:mysql://localhost:3306/new_schema_test";
    private static final String USERNAME="Yanewuser";
    private static final String PASSWORD="Yanewuser!";

    private static Connection connection;

    static {
        try {
            Class.forName("com.mysql.jdbc.Driver"); //com.mysql.jdbc.Driver  //com.mysql.cj.jdbc.Driver
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            connection = DriverManager.getConnection(URL, USERNAME,PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Car> index() {
        List<Car> cars = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String SQL = "SELECT * FROM CAR";
            ResultSet resultSet = statement.executeQuery(SQL);

            while (resultSet.next()) {
                Car car = new Car();
                car.setId(resultSet.getInt("id"));
                car.setName(resultSet.getString("name"));
                car.setPower(resultSet.getInt("power"));
                car.setColor(resultSet.getString("color"));
                cars.add(car);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return cars;
    }

    public Car show(int id) {
        Car car = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM CAR WHERE ID=?");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            car = new Car();
            car.setId(resultSet.getInt("id"));
            car.setName(resultSet.getString("name"));
            car.setPower(resultSet.getInt("power"));
            car.setColor(resultSet.getString("color"));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return car;
        //return cars.stream().filter(car -> car.getId() == id).findAny().orElse(null);
    }

    public void save (Car car){
    //    car.setId(++CAR_COUNT);
    //    cars.add(car);

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO CAR VALUES (1,?,?,?)");
            //Statement statement = connection.createStatement();
            //String SQL = "INSERT INTO CAR VALUES ("+1+",'"+ car.getName()+ "','"+ car.getPower()+ "','"+ car.getColor()+ "')";
            //statement.executeUpdate(SQL);
            preparedStatement.setString(1, car.getName());
            preparedStatement.setInt(2, car.getPower());
            preparedStatement.setString(3, car.getColor());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    public void update(int id, Car updateCar) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE CAR SET name=?, power=?, color =? WHERE id = ?");
            preparedStatement.setString(1, updateCar.getName());
            preparedStatement.setInt(2, updateCar.getPower());
            preparedStatement.setString(3, updateCar.getColor());
            preparedStatement.setInt(4, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        //    Car carToBeUpdated = show(id);
        //    carToBeUpdated.setName(updateCar.getName());
        //    carToBeUpdated.setPower(updateCar.getPower());
        //    carToBeUpdated.setColor(updateCar.getColor());
    }

    public void delete(int id) {
        //cars.removeIf(p->p.getId()==id);
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement("DELETE FROM CAR WHERE id = ?");
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

        //public Object showall(int count) {
       //if (count == 0) {
        //       return null;//cars;
       // }
       // return cars.stream().limit(count).collect(Collectors.toList());
        // }
}
