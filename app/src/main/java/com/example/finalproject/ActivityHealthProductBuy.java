package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.finalproject.ethereum.CBLoadSmartContract;
import com.example.finalproject.ethereum.CBSendingToken;
import com.example.finalproject.ethereum.Jacken;
import com.example.finalproject.ethereum.LoadSmartContract;
import com.example.finalproject.ethereum.SendingToken;

import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.Web3jFactory;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.utils.Convert;

import java.math.BigInteger;
import java.util.Map;

public class ActivityHealthProductBuy extends AppCompatActivity implements CBLoadSmartContract, CBSendingToken {

    String productImg;
    String productName;
    String productPrice;
    ImageView act_health_prod_buy_img;
    TextView act_health_prod_buy_token;
    TextView act_health_prod_buy_name;
    TextView act_health_prod_buy_price;
    TextView act_health_prod_buy_title_name;

    Button act_health_prod_buy_btn;


    //토큰 관련

    Context context = this;
    private Web3j mweb3j;
    private Jacken jacken;
    Credentials credentials;
    EthGetBalance ethGetBalance;
    SendingToken sendingToken;
    String exchage;

    //스마트 컨트랙트 주소
    String mContract = "0x6ff24c91271dec2b06de58010563948bf2ca6bb8";

    //토큰 관련

    int mGasprice = 20;
    int mGasLimit = 1000000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_product_buy);

        //토큰 관련
        mweb3j = Web3jFactory.build(new HttpService("https://ropsten.infura.io/v3/4d4615512afc4e179d70fdc259a8ab74"));
        //이곳에 메타마스크 지갑의 개인키를 입력하면 그 키에 해당하는 지갑에 대한 정보를 가져온다.
        credentials = Credentials.create("06CE3F710AD1943AAC66ACF83D5B12E818536F575375C3FFEF1C7EAB12E95E09");

        getWalletinfo();

        //토큰 관련 끝
        Intent getIntent = getIntent();
        productImg = getIntent.getExtras().getString("img");
        productName = getIntent.getExtras().getString("name");
        productPrice = getIntent.getExtras().getString("price");

        act_health_prod_buy_img = findViewById(R.id.act_health_prod_buy_img);
        act_health_prod_buy_token = findViewById(R.id.act_health_prod_buy_token);
        act_health_prod_buy_name = findViewById(R.id.act_health_prod_buy_name);
        act_health_prod_buy_price = findViewById(R.id.act_health_prod_buy_price);
        act_health_prod_buy_title_name = findViewById(R.id.act_health_prod_buy_title_name);
        act_health_prod_buy_btn = findViewById(R.id.act_health_prod_buy_btn);

        Glide.with(this).load(productImg).into(act_health_prod_buy_img);
        act_health_prod_buy_title_name.setText(productName);
        act_health_prod_buy_name.setText(productName);
        act_health_prod_buy_price.setText(productPrice + " SG");

        act_health_prod_buy_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendToken();
            }
        });
    }


    public void sendToken(){
        String targetAdd = "0x84E5Cb28753aA0AB485C5E6092cF1F6D16C7B73C";

        String gasprice = String.valueOf(mGasprice);
        String gaslimit = String.valueOf(mGasLimit);

        sendingToken = new SendingToken(mweb3j, credentials, gasprice, gaslimit);
        sendingToken.registerCallBackToken2(this);
        sendingToken.Send(mContract, targetAdd, productPrice);
    }

    public void getWalletinfo(){
        LoadSmartContract loadSmartContract = new LoadSmartContract(mweb3j, credentials, mContract, BigInteger.valueOf(10), BigInteger.valueOf(300000));

        Log.e("TAG", "이곳에서 credentials는 무슨 값인가?::"+credentials);

        loadSmartContract.registerCallBack(this);
        loadSmartContract.LoadToken();
    }

    @Override
    public void backSendToken(TransactionReceipt result) {
        Toast.makeText(context, "물품 구매 성공!!", Toast.LENGTH_SHORT).show();
        getWalletinfo();
    }

    @Override
    public void backLoadSmartContract(Map<String, String> result) {
        String tokenTmp = result.get("tokenbalance");
        String token = tokenTmp.substring(18);
        setTokenBalance(token);
    }

    //토큰 반환값
    public void setTokenBalance(String value){
        act_health_prod_buy_token.setText(value);
    }
}
