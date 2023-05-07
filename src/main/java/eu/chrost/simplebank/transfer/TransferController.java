package eu.chrost.simplebank.transfer;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transfer")
@RequiredArgsConstructor
public class TransferController {
    private final TransferService transferService;

    @PostMapping
    public ResponseEntity<Void> makeTransfer(@RequestBody TransferRequest transferRequest) {
        transferService.makeTransfer(transferRequest.fromAccountId(), transferRequest.toAccountId(), transferRequest.amount());
        return ResponseEntity.ok().build();
    }
}
