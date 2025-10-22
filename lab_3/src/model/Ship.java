package model;

import java.util.Date;

public class Ship extends AbstractEntity {
    private String type;
    private double tonnage;
    private double speed;
    private double price;

    public Ship(int id, String name, String type, double tonnage, double speed, Date productionDate, double price) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.tonnage = tonnage;
        this.speed = speed;
        this.productionDate = productionDate;
        this.price = price;
    }

    public Ship() {}

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public double getTonnage() { return tonnage; }
    public void setTonnage(double tonnage) { this.tonnage = tonnage; }
    public double getSpeed() { return speed; }
    public void setSpeed(double speed) { this.speed = speed; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public String toString() {
        return "ID=" + id +
                ", Name='" + name + '\'' +
                ", Type='" + type + '\'' +
                ", Tonnage=" + tonnage +
                ", Speed=" + speed +
                ", ProductionDate=" + productionDate +
                ", Price=" + price;
    }
}
