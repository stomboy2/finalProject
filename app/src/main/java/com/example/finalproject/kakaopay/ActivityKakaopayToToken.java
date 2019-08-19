package com.example.finalproject.kakaopay;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finalproject.R;
import com.example.finalproject.ethereum.CBLoadSmartContract;
import com.example.finalproject.ethereum.CBSendingToken;
import com.example.finalproject.ethereum.Jacken;
import com.example.finalproject.ethereum.LoadSmartContract;
import com.example.finalproject.ethereum.SendingToken;
import com.example.finalproject.retrofit.RetrofitConnect;
import com.example.finalproject.retrofit.RetrofitInterface;
import com.example.finalproject.retrofit.pojo.KakaoPayResult;

import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.Web3jFactory;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;

import java.math.BigInteger;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityKakaopayToToken extends AppCompatActivity implements CBLoadSmartContract, CBSendingToken {

    RetrofitInterface retrofitInterface;

    //토큰 관련 시작
    Context context = this;
    private Web3j mweb3j;
    Credentials credentials;
    SendingToken sendingToken;

    //스마트 컨트랙트 주소
    String mContract = "0x6ff24c91271dec2b06de58010563948bf2ca6bb8";

    int mGasprice = 20;
    int mGasLimit = 1000000;

    //토큰 관련 끝

    //Layout
    TextView act_kakaopay_current_token;
    EditText act_kakaopay_token_buy_edit;
    Button act_kakaopay_token_buy_btn;

    //충전한 토큰 개수
    int tokenCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kakaopay_to_token);

        act_kakaopay_current_token = findViewById(R.id.act_kakaopay_current_token_txt);
        act_kakaopay_token_buy_edit = findViewById(R.id.act_kakaopay_token_buy_edit);
        act_kakaopay_token_buy_btn = findViewById(R.id.act_kakaopay_token_buy_btn);

        // 토큰 관련 시작
        mweb3j = Web3jFactory.build(new HttpService("https://ropsten.infura.io/v3/4d4615512afc4e179d70fdc259a8ab74"));

        //이곳에 메타마스크 지갑의 개인키를 입력하면 그 키에 해당하는 지갑에 대한 정보를 가져온다.
        credentials = Credentials.create("06CE3F710AD1943AAC66ACF83D5B12E818536F575375C3FFEF1C7EAB12E95E09");
        getWalletinfo();
        // 토큰 관련 끝

        //카카오 페이 관련 http 통신
        //카카오페이를 통해서 결제를 하는 부분
        retrofitInterface = RetrofitConnect.getClient2().create(RetrofitInterface.class);

        //버튼 클릭시 충전하기로 입력한 토큰개수만큼 카카오페이에서 결제를 진행.
        act_kakaopay_token_buy_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                tokenCount = Integer.parseInt(act_kakaopay_token_buy_edit.getText().toString());
                int totalAmount = tokenCount * 100;
                Call<KakaoPayResult> payResultCall = retrofitInterface.approvalPrepare(
                        "KakaoAK 3d51acf09951cc15968df201611d1eeb", "TC0ONETIME", "test",
                        "test", "SG토큰 구매", tokenCount, totalAmount, 0,
                        "https://developers.kakao.com","https://developers.kakao.com","https://developers.kakao.com"
                );

                payResultCall.enqueue(new Callback<KakaoPayResult>() {
                    @Override
                    public void onResponse(Call<KakaoPayResult> call, Response<KakaoPayResult> response) {
                        String nextUrl = response.body().next_redirect_mobile_url;
                        String tid = response.body().tid;

                        Intent goApprovalFinish = new Intent(getApplicationContext(), ActivityKakaopayWebview.class);
                        goApprovalFinish.putExtra("url", nextUrl);
                        goApprovalFinish.putExtra("tid", tid);
                        startActivityForResult(goApprovalFinish,100);
                    }

                    @Override
                    public void onFailure(Call<KakaoPayResult> call, Throwable t) {

                    }
                });
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        act_kakaopay_token_buy_edit.setText("");
        sendToken();
    }

    public void sendToken(){
        //토큰을 충전하는것이기 때문에 현재 접속한 사람의 지갑 주소를 입력
        String targetAdd = "0x502f876D13d90B53bc2B8a0e2646f46F71F28ACe";

        //이곳에 메타마스크 지갑의 개인키를 입력하면 그 키에 해당하는 지갑에 대한 정보를 가져온다.
        //계정1의 비밀키
        credentials = Credentials.create("26C949D068A95D6979B3695DC46547F9AFF2FCDB05F94957CD6B93A582B3402D");

        String gasprice = String.valueOf(mGasprice);
        String gaslimit = String.valueOf(mGasLimit);

        sendingToken = new SendingToken(mweb3j, credentials, gasprice, gaslimit);
        sendingToken.registerCallBackToken3(this);
        sendingToken.Send(mContract, targetAdd, String.valueOf(tokenCount));

        //토큰 전송 후 원래 로그인한 사용자의 토큰 개수를 확인하기 위해서 credentials 값을 재 setting
        //계정2의 비밀키
        credentials = Credentials.create("06CE3F710AD1943AAC66ACF83D5B12E818536F575375C3FFEF1C7EAB12E95E09");
    }

    public void getWalletinfo(){
        LoadSmartContract loadSmartContract = new LoadSmartContract(mweb3j, credentials, mContract, BigInteger.valueOf(10), BigInteger.valueOf(300000));

        loadSmartContract.registerCallBack(this);
        loadSmartContract.LoadToken();
    }

    //스마트컨트랙트를 조회후 스마트컨트랙트에 있는 토큰 개수를 Setting 하기.
    @Override
    public void backLoadSmartContract(Map<String, String> result) {
        String tokenTmp = result.get("tokenbalance");
        String token = tokenTmp.substring(18);
        setTokenBalance(token);
    }

    //토큰을 전송하고 난후 지갑 정보를 확인하여서 변한 토큰 개수를 확인하기
    @Override
    public void backSendToken(TransactionReceipt result) {
        Toast.makeText(context, "충전 완료!!", Toast.LENGTH_SHORT).show();
        getWalletinfo();
    }

    //토큰값을 화면에 보여주기 위한 함수
    public void setTokenBalance(String value){
        act_kakaopay_current_token.setText(value);
    }
}
