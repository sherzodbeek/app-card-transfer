package uz.pdp.appcardtransfer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.appcardtransfer.entity.Outcome;

import java.util.List;

public interface OutcomeRepository extends JpaRepository<Outcome, Integer> {

    List<Outcome> findAllByFromCardId_Username(String fromCardId_username);
}
