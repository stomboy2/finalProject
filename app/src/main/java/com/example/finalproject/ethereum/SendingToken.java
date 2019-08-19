package com.example.finalproject.ethereum;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import com.example.finalproject.ActivityHealthProductBuy;

import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.utils.Convert;

import java.math.BigDecimal;
import java.math.BigInteger;

public class SendingToken {
    private Credentials mCredentials;
    private Web3j mWeb3j;
    private String fromAddress;
    private String mValueGasPrice;
    private String mValueGasLimit;

    private CBSendingToken cbSendingToken;

    public SendingToken(Web3j web3j, Credentials credentials, String valueGasPrice, String valueGasLimit){
        mWeb3j = web3j;
        mCredentials = credentials;
        fromAddress = credentials.getAddress();
        mValueGasPrice = valueGasPrice;
        mValueGasLimit = valueGasLimit;
    }

    private BigInteger getGasPrice(){
        return BigInteger.valueOf(Long.valueOf(mValueGasPrice));
    }

    private BigInteger getGasLimit(){
        return BigInteger.valueOf(Long.valueOf(mValueGasLimit));
    }

    public void Send(String smartContractAddress, String toAddress, String valueAmmount){
        new SendToken().execute(smartContractAddress, toAddress, valueAmmount);
    }



    private class SendToken extends AsyncTask<String,Void, TransactionReceipt> {


        @Override
        protected TransactionReceipt doInBackground(String... value) {
            BigInteger amount = BigInteger.valueOf(Long.parseLong(value[2]));
            Log.d("TAG","스마트 컨트랙트로 넘어가는 값은 어떻게 나타나는가:::::"+amount);

            System.out.println(getGasPrice());
            System.out.println(getGasLimit());

            Jacken jacken = Jacken.load(value[0], mWeb3j, mCredentials, Convert.toWei(BigDecimal.valueOf(getGasPrice().longValue()),Convert.Unit.GWEI).toBigInteger(), getGasLimit());
            try {
                TransactionReceipt result = jacken.transfer(value[1], amount).send();
                return result;
            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(TransactionReceipt result){
            super.onPostExecute(result);
            cbSendingToken.backSendToken(result);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }
    }

    public void registerCallBackToken(ActivityToken cbSendingToken){
        this.cbSendingToken = cbSendingToken;
    }
    public void registerCallBackToken2(ActivityHealthProductBuy activityHealthProductBuy) {
        this.cbSendingToken = activityHealthProductBuy;
    }
}
