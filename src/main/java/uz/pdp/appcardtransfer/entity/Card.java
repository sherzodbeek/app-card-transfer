package uz.pdp.appcardtransfer.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false, unique = true)
    private String number;

    @Column(nullable = false)
    private Double balance;

    @Column(nullable = false)
    private Date expiredDate;

    @Column(nullable = false)
    private boolean active;


    public Card(String username, String number, Double balance, Date expiredDate, boolean active) {
        this.username = username;
        this.number = number;
        this.balance = balance;
        this.expiredDate = expiredDate;
        this.active = active;
    }
}
