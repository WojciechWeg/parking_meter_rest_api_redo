package com.wojtek.parkingmeter.profit;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "profits")
public class ProfitEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date")
    LocalDate date;

    @Column(name = "income")
    BigDecimal income;

    public ProfitEntity() {
    }

    public ProfitEntity(LocalDate date, BigDecimal income) {
        this.date = date;
        this.income = income;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public BigDecimal getIncome() {
        return income;
    }

    public void setIncome(BigDecimal income) {
        this.income = income;
    }
}
