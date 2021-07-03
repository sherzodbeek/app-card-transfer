package uz.pdp.appcardtransfer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.appcardtransfer.entity.Card;
import uz.pdp.appcardtransfer.entity.Income;
import uz.pdp.appcardtransfer.entity.Outcome;
import uz.pdp.appcardtransfer.payload.ApiResponse;
import uz.pdp.appcardtransfer.repository.CardRepository;
import uz.pdp.appcardtransfer.repository.IncomeRepository;
import uz.pdp.appcardtransfer.repository.OutcomeRepository;
import uz.pdp.appcardtransfer.security.JwtProvider;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class CardService {

    private CardRepository cardRepository;
    private JwtProvider jwtProvider;
    private IncomeRepository incomeRepository;
    private OutcomeRepository outcomeRepository;

    @Autowired
    public void setCardRepository(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    @Autowired
    public void setJwtProvider(JwtProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

    @Autowired
    public void setIncomeRepository(IncomeRepository incomeRepository) {
        this.incomeRepository = incomeRepository;
    }

    @Autowired
    public void setOutcomeRepository(OutcomeRepository outcomeRepository) {
        this.outcomeRepository = outcomeRepository;
    }

    public ApiResponse addCard(Card card) {
        boolean existsByNumber = cardRepository.existsByNumber(card.getNumber());
        if(existsByNumber)
            return new ApiResponse("This card number already exists!", false);
        Card addingCard = new Card(
                card.getUsername(),
                card.getNumber(),
                card.getBalance(),
                card.getExpiredDate(),
                card.isActive()
        );
        cardRepository.save(addingCard);
        return new ApiResponse("Card added!", true);
    }

    public List<Income> getIncomeHistory(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        token = token.substring(7);
        String username = jwtProvider.getUsernameFromToken(token);
        return incomeRepository.findAllByToCardId_Username(username);
    }


    public List<Outcome> getOutcomeHistory(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        token = token.substring(7);
        String username = jwtProvider.getUsernameFromToken(token);
        return outcomeRepository.findAllByFromCardId_Username(username);
    }
}
