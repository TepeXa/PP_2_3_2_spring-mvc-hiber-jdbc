package web.models;

import javax.persistence.*;
import javax.validation.constraints.*;
@Entity
@Table(name = "Car")
public class Car {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    @NotEmpty(message = "Name should not be empty")
    @Size(min = 2, max = 40, message = "Name length should be between 2 and 40 characters")
    private String name;

    @Column(name = "power")
    @Min(value=0, message = "Power should be greater than 0")
    @Max(value=999,message = "Power should be smaller than 999" )
    private int power;

    @Column(name = "color")
    @NotEmpty(message = "Color should not be empty")
    private String color;



    public Car() {}
    public Car(int id, String name, int power, String color) {
        this.id = id;
        this.name = name;
        this.power = power;
        this.color = color;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
