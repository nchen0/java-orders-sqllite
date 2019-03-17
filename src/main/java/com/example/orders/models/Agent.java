package com.example.orders.models;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="agents") // Here, we're putting a table of agent into our table.
public class Agent {
    // Now we are creating the columns in our table.

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // This tells java that we want this to be a primary key.
    @Column(nullable = false)
    private long agentcode;

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "agent")
    private Set<Customer> customer;

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "agent")
    private Set<Order> orders;

    private String agentname;
    private String workingarea;
    private double commission;
    private String phone;
    private String country;



    public Agent() {

    }

    public Set<Customer> getCustomer() {
        return customer;
    }

    public void setCustomer(Set<Customer> customer) {
        this.customer = customer;
    }

    public Set<Order> getOrders() {
        return orders;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }

    public String getAgentname() {
        return agentname;
    }

    public void setAgentname(String agentname) {
        this.agentname = agentname;
    }

    public String getWorkingarea() {
        return workingarea;
    }

    public void setWorkingarea(String workingarea) {
        this.workingarea = workingarea;
    }

    public double getCommission() {
        return commission;
    }

    public void setCommission(double commission) {
        this.commission = commission;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public long getAgentcode() {
        return agentcode;
    }
}
