package com.example.Football;

public class Player {

    private  String id;
    private  String name;
    private  String country;
    private  String status;
    private  int salary;
/*    private  String club;
   */

    public Player(){
    }


    public Player(String id, String name, String country, String status, int salary) {
        this.id = id;
        this.name = name;
        this.country = country;
        this.status = status;
        this.salary = salary;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }
}
