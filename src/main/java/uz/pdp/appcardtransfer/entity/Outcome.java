package uz.pdp.appcardtransfer.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Outcome {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(optional = false)
    private Card fromCard;

    @ManyToOne(optional = false)
    private Card toCard;

    @Column(nullable = false)
    private Double amount;

    @CreationTimestamp
    private Timestamp date;

    @Column(nullable = false)
    private Double commissionAmount;

    public Outcome(Card fromCardId, Card toCardId, Double amount, Double commissionAmount) {
        this.fromCard = fromCardId;
        this.toCard = toCardId;
        this.amount = amount;
        this.commissionAmount = commissionAmount;
    }
}
