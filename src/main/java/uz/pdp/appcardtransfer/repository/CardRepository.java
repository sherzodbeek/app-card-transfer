package uz.pdp.appcardtransfer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.appcardtransfer.entity.Card;

public interface CardRepository extends JpaRepository<Card, Integer> {

    boolean existsByNumber(String number);
}
