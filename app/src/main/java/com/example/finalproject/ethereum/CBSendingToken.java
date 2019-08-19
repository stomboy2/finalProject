package com.example.finalproject.ethereum;

import org.web3j.protocol.core.methods.response.TransactionReceipt;

public interface CBSendingToken {
    void backSendToken(TransactionReceipt result);
}
