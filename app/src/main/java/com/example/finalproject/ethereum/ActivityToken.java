package com.example.finalproject.ethereum;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finalproject.R;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.Web3jFactory;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.utils.Convert;

import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class ActivityToken extends AppCompatActivity implements CBLoadSmartContract, CBSendingToken{

    Context context = this;

    private ImageView qr_small;         //qr코드 작은버전
    private ImageView qr_big;           //qr코드 큰 버전(작은 버전의 qr코드를 클릭했을때 뜨는 qr코드)
    private TextView walletAddress;     //지갑 주소
    private TextView etheAccount;       //이더 잔액
    private TextView tokenAccount;      //토큰 잔액
    private TextView receiver_address;
    private Button scan_qr;
    private EditText send_value;
    private Button sendbtn;             //전송 버튼

    private Web3j mweb3j;
    private Jacken jacken;
    Credentials credentials;
    EthGetBalance ethGetBalance;
    SendingToken sendingToken;
    String exchage;


    int mGasprice = 20;
    int mGasLimit = 1000000;

    //스마트 컨트랙트 주소
    String mContract = "0x6ff24c91271dec2b06de58010563948bf2ca6bb8";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_token);
        qr_small = (ImageView)findViewById(R.id.qr_small);
        walletAddress = (TextView)findViewById(R.id.walletAddress);
        etheAccount = (TextView)findViewById(R.id.etheAccount);
        tokenAccount = (TextView)findViewById(R.id.tokenAccount);
        receiver_address = (TextView)findViewById(R.id.receiver_address);
        scan_qr = (Button)findViewById(R.id.scan_qr);
        send_value = (EditText)findViewById(R.id.send_value);
        sendbtn = (Button)findViewById(R.id.sendbtn);
        receiver_address.setText("0x84E5Cb28753aA0AB485C5E6092cF1F6D16C7B73C");

        mweb3j = Web3jFactory.build(new HttpService("https://ropsten.infura.io/v3/4d4615512afc4e179d70fdc259a8ab74"));
        try {

            credentials = Credentials.create("06CE3F710AD1943AAC66ACF83D5B12E818536F575375C3FFEF1C7EAB12E95E09"); //이곳에 메타마스크 지갑의 개인키를 입력하면 그 키에 해당하는 지갑에 대한 정보를 가져온다.
            //현재는 test 지갑의 개인키 사용

            Log.d("TAG","주소값은 어떻게 나오나::"+credentials.getAddress());

            ethGetBalance = mweb3j.ethGetBalance(credentials.getAddress(), DefaultBlockParameterName.LATEST).sendAsync().get();
            BigInteger wei = ethGetBalance.getBalance();
            exchage = Convert.fromWei(wei.toString(), Convert.Unit.ETHER).toString();
            Log.d("TAG","지갑 정보는:::"+exchage);
        }catch (InterruptedException | ExecutionException e){
            e.printStackTrace();
        }

        walletAddress.setText(credentials.getAddress()); //메타마스크 지갑 주소
        etheAccount.setText(exchage); //메타마스크 지갑에 보유중인 이더 량
        qr_small.setImageBitmap(new Generate().Get(credentials.getAddress(), 800, 800));

//        String Address = credentials.getAddress();
//        Log.d("TAGG","주소값은? "+Address);

        getWalletinfo();

        scan_qr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new ScanIntergrator(getParent()).startScan();
            }
        });
        sendbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendToken();
            }
        });
    }

    public String getEtherAddress(){
        return credentials.getAddress();
    }

    public void getWalletinfo(){
        LoadSmartContract loadSmartContract = new LoadSmartContract(mweb3j, credentials, mContract, BigInteger.valueOf(10), BigInteger.valueOf(300000));

        Log.d("TAG", "이곳에서 credentials는 무슨 값인가?::"+credentials);

        loadSmartContract.registerCallBack(this);
        loadSmartContract.LoadToken();
    }

    @Override
    public void backLoadSmartContract(Map<String, String> result) {
//        setTokenAddress(result.get("tokenaddress"));
//        setTokenName(result.get("tokenname"));
//        setTokenSymbol(result.get("tokensymbol"));
        setTokenBalance(result.get("tokenbalance"));
//        setTokenSupply(result.get("tokensupply"));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null){
            if (result.getContents() == null){
                Toast.makeText(context, "Result Not Found", Toast.LENGTH_SHORT).show();
            }else {
                receiver_address.setText(result.getContents());
                Log.d("TTTTTTT","수취인 주소::::::::"+result.getContents());
                Toast.makeText(context,"수취인 주소", Toast.LENGTH_SHORT).show();
            }
        }else {
            super.onActivityResult(requestCode, resultCode,data);
        }
    }



    public void setTokenBalance(String value){
        tokenAccount.setText(value);
    }

    public void sendToken(){
        String targetAdd = receiver_address.getText().toString();
        String ADD = targetAdd.replaceFirst("ethereum:","");
        Log.d("TAGGGG","받는사람 주소:::::"+ADD);

        Integer trans1 = Integer.valueOf(send_value.getText().toString());
        Log.d("Tag","1차 변환 값은:::::"+trans1);
        BigInteger trans2 = BigInteger.valueOf(trans1);
        Log.d("Tag","2차 변환 값은:::::"+trans2);

        String gasprice = String.valueOf(mGasprice);
        String gaslimit = String.valueOf(mGasLimit);

        sendingToken = new SendingToken(mweb3j, credentials, gasprice, gaslimit);
        sendingToken.registerCallBackToken(this);
        sendingToken.Send(mContract, ADD, send_value.getText().toString());
    }

    @Override
    public void backSendToken(TransactionReceipt result){
        Toast.makeText(context, "토큰 전송 성공!!", Toast.LENGTH_SHORT).show();
        getWalletinfo();
    }

}
