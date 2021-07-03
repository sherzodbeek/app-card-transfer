package uz.pdp.appcardtransfer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.appcardtransfer.entity.Card;
import uz.pdp.appcardtransfer.entity.Income;
import uz.pdp.appcardtransfer.entity.Outcome;
import uz.pdp.appcardtransfer.payload.ApiResponse;
import uz.pdp.appcardtransfer.payload.TransferDto;
import uz.pdp.appcardtransfer.repository.CardRepository;
import uz.pdp.appcardtransfer.repository.IncomeRepository;
import uz.pdp.appcardtransfer.repository.OutcomeRepository;
import uz.pdp.appcardtransfer.security.JwtProvider;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Service
public class TransferService {

    private IncomeRepository incomeRepository;
    private OutcomeRepository outcomeRepository;
    private CardRepository cardRepository;
    private JwtProvider jwtProvider;

    @Autowired
    public void setIncomeRepository(IncomeRepository incomeRepository) {
        this.incomeRepository = incomeRepository;
    }

    @Autowired
    public void setOutcomeRepository(OutcomeRepository outcomeRepository) {
        this.outcomeRepository = outcomeRepository;
    }

    @Autowired
    public void setCardRepository(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    @Autowired
    public void setJwtProvider(JwtProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }


    public ApiResponse transferMoney(HttpServletRequest request, TransferDto transferDto) {
        Optional<Card> fromCardOptional = cardRepository.findById(transferDto.getFromCardId());
        if (fromCardOptional.isEmpty())
            return new ApiResponse("Card not found!", false);
        Card fromCard = fromCardOptional.get();
        if (!fromCard.isActive())
            return new ApiResponse("Your card is not active!", false);
        String cardHolder = fromCard.getUsername();
        String token = request.getHeader("Authorization");
        token = token.substring(7);
        String username = jwtProvider.getUsernameFromToken(token);
        if (!cardHolder.equals(username))
            return new ApiResponse("You are not owner this card!", false);
        Optional<Card> toCardOptional = cardRepository.findById(transferDto.getToCardId());
        if (toCardOptional.isEmpty())
            return new ApiResponse("Card not found!", false);
        Card toCard = toCardOptional.get();
        if (!toCard.isActive())
            return new ApiResponse("Second card is not active!", false);
        double commissionAmount = transferDto.getAmount() * 0.01;
        if (fromCard.getBalance() < transferDto.getAmount() + commissionAmount)
            return new ApiResponse("There are not enough funds in the account!", false);
        fromCard.setBalance(fromCard.getBalance() - transferDto.getAmount() - commissionAmount);
        toCard.setBalance(toCard.getBalance() + transferDto.getAmount());
        cardRepository.save(fromCard);
        cardRepository.save(toCard);
        Income income = new Income(fromCard, toCard, transferDto.getAmount());
        incomeRepository.save(income);
        Outcome outcome = new Outcome(fromCard, toCard, transferDto.getAmount(), commissionAmount);
        outcomeRepository.save(outcome);
        return new ApiResponse("Funds transferred!", true);
    }
}
