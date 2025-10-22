package model;

import java.util.Date;

public abstract class AbstractEntity {
    protected int id;
    protected String name;
    protected Date productionDate;
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Date getProductionDate() { return productionDate; }
    public void setProductionDate(Date productionDate) { this.productionDate = productionDate; }
    public abstract String toString();
}
