package org.bankapp.backend.dtos;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import org.bankapp.backend.entities.domain.Transfer;

import java.math.BigDecimal;

@Value
@Builder
@Jacksonized
public class TransferGetDTO {

    boolean sent;
    String madeAt;
    String title;
    BigDecimal amount;
    String accountNumber;

    public static TransferGetDTO fromEntity(Transfer transfer, boolean sent) {
        return TransferGetDTO.builder()
                .title(transfer.getTitle())
                .sent(sent)
                .madeAt(transfer.getMadeAt().toString())
                .amount(transfer.getAmount())
                .accountNumber(sent ?
                        transfer.getReceiver().getAccountNumber() :
                        transfer.getSender().getAccountNumber())
                .build();
    }

}
