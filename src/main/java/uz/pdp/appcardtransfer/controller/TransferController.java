package uz.pdp.appcardtransfer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.appcardtransfer.payload.ApiResponse;
import uz.pdp.appcardtransfer.payload.TransferDto;
import uz.pdp.appcardtransfer.service.TransferService;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/transfer")
public class TransferController {

    private TransferService transferService;

    @Autowired
    public void setTransferService(TransferService transferService) {
        this.transferService = transferService;
    }

    @PostMapping
    public ResponseEntity<?> transferMoney(HttpServletRequest request, @RequestBody TransferDto transferDto) {
        ApiResponse apiResponse = transferService.transferMoney(request, transferDto);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }
}
