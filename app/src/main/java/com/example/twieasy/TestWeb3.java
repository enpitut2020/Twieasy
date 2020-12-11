package com.example.twieasy;

import android.content.Context;
import android.util.Log;

import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Transfer;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.DefaultGasProvider;
import org.web3j.utils.Convert;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

public class TestWeb3 {
    //-------------------------------------
    // 定数
    //-------------------------------------
    // アカウントファイル名（※初回テスト時に作成されたアカウントがこのファイルに保存され、二回目以降のテスト時に利用されます）
    final private String KEY_FILE = "key.json";

    // FIXME パスワード（※ご自身のパスワードで置き換えてください）
    // メモ：このコードはテスト用なのでソース内にパスワードを書いていますが、
    // 　　　公開を前提としたアプリを作る場合、ソース内にパスワードを書くことは大変危険です！
    final private String PASSWORD = "password";

    // FIXME ご自身のテストに合わせた接続先に変更してください
    final private int TARGET_NET = Web3Helper.RINKEBY;

    // [HelloWorld]コントラクのトアドレス
    // このアドレスを無効（空文字）にした場合、[HelloWorld]コントラクトがデプロイされます
    // デプロイが成功すると[LogCat]にアドレスが表示されるので、その値を下記に設定することで再アクセスが可能です
    //（※コメントアウトされているアドレスは実際にRinkeby上にデプロイされたものなので、テストにお使いいただけます）
    final private String DEFAULT_HELLO_WORLD_ADDRESS = ""; //"0xd21ce6f369f8281b7d39b47372c8f4a8a77841fc";

    //-------------------------------------
    // メンバー
    //-------------------------------------
    private Web3Helper helper;
    private boolean isBusy = false;
    private String curHelloWorldAddress = DEFAULT_HELLO_WORLD_ADDRESS;

    //----------------------------------------------------------------------------
    // コンストラクタ
    //----------------------------------------------------------------------------
    public TestWeb3( Context context ){
        helper = new Web3Helper( context );
    }

    //----------------------------------------------------------------------------
    // テスト
    //----------------------------------------------------------------------------
//    public void test() {
//        // テストの最中なら無視
//        if( isBusy ){
//            log( "@ TestWeb3: BUSY!" );
//            return;
//        }
//
//        isBusy = true;
//
//        // メインスレッドを止めないように別スレッドでテスト
//        new Thread( new Runnable() {
//            @Override
//            public void run(){
//                execTest();
//                isBusy = false;
//            }
//        }).start();
//    }

    //----------------------------------------
    // テスト本体
    //----------------------------------------
//    private void execTest(){
//        log( "@ TestWeb3: START..." );
//
//        // ネットワークへ接続
//        if( setTarget() ){
//            // アカウント設定
//            if( setAccount() ) {
//                // 残高の確認
//                checkBalance();
//
//                // メモ：ここから下の処理にはイーサリアム上で手数料が発生するためテスト中のアカウントに十分な残高がないと、
//                // 　　　例外[Error processing transaction request: insufficient funds for gas * price + value]が発生します
//                // 　　　送信やデプロイのテストをする際は、[MetaMask]等で対象アカウントに十分なイーサを送信しておいてください
//
//                // イーサの送信
//                checkSend();
//
//                // [HelloWorld]コントラクトの確認
//                if( ! execCheckHelloWorld( curHelloWorldAddress ) ) {
//                    // コントラクトが無効であれば[HelloWorld]をデプロイ
//                    curHelloWorldAddress = execDeployHelloWorld();
//                }
//
//                // この時点で[HelloWorld]コントラクトのアドレスが有効であればやりとり開始
//                if( curHelloWorldAddress != null && ! curHelloWorldAddress.equals( "" ) ) {
//                    execInteractHelloWorld( curHelloWorldAddress );
//                }else{
//                    // コントラクトのアドレスが無効
//                    log( "@ TestWeb3: FAILED TO INTERACT [HellowWorld] CONTRACT" );
//                }
//            }else{
//                // アカウントの設定に失敗
//                log( "@ TestWeb3: FAILED TO SET ACCOUNT" );
//            }
//        }else{
//            // 接続に失敗
//            log( "@ TestWeb3: FAILED TO CONNECT TARGET NET" );
//        }
//
//        log( "@ TestWeb3: FINISHED" );
//    }

    //----------------------------------------
    // ネットワークへd接続
    //----------------------------------------
    private boolean setTarget(){
        log( "@ [setTarget]");

        if( helper.setTarget( TARGET_NET ) ) {
            int target = helper.getCurTarget();
            log("@ target=" + target + "(" +  Web3Helper.GetNetworkName(target) + ")" );
            return( true );
        }

        return( false );
    }

    //----------------------------------------
    // アカウント設定
    //----------------------------------------
    private boolean setAccount(){
        log( "@ [setAccount]" );

        // アカウントファイルの読み込み
        if( helper.loadAccount( PASSWORD, KEY_FILE ) ){
            log( "@ FOUND KEY FILE: json=" + helper.getCurAccountJson() );
        }else {
            log( "@ CREATE NEW ACCOUNT" );

            // 新規作成
            if( helper.createNewAccount( PASSWORD, KEY_FILE ) ) {
                log("@ Write down below json code to import generated account into your wallet apps(e.g. MetaMask)");
                log( helper.getCurAccountJson() );

                log("@ privateKey=" + helper.getCurPrivateKey() );
            }
        }

        // ヘルパーが有効になったらイーサリアムアドレスの確認
        if( helper.isValid() ) {
            log("@ CURRENT ACCOUNT");
            log("@ ethereumAddress=" + helper.getCurEthereumAddress() );
            return (true);
        }

        return( false );
    }

    //----------------------------------------
    // 残高確認
    //----------------------------------------
    private void checkBalance(){
        log( "@ [checkBalance]");

        BigInteger balance = helper.getCurBalance();
        log( "@ balance=" +  balance + "wei" );
    }

    //----------------------------------------
    // イーサの送信
    //----------------------------------------
    private void checkSend(){
        log( "@ [checkSend]" );

        String hash;
        try{
            Web3j web3 = helper.getWeb3();
            Credentials credentials = helper.getCurAccount();
            String toAddress = "0xebfCB28c530a9aAD2e5819d873EB5Cc7b215d1E1";    // rinkeby account
            BigInteger biVal = new BigInteger( "10000000000000000" );        // 0.01 eth

            // ヘルパーのアカウントに十分な残高がないと例外が発生するので注意
            TransactionReceipt receipt = Transfer.sendFunds( web3,credentials,toAddress, new BigDecimal(biVal), Convert.Unit.WEI).sendAsync().get();
            hash = receipt.getTransactionHash();
        }catch (Exception e){
            log( "@ EXCEPTION e=" + e.getMessage() );
            return;
        }

        log( "@ transaction hash=" + hash );
    }

    //-------------------------------------
    // [HelloWorld]コントラクトの確認
    //-------------------------------------
//    private boolean execCheckHelloWorld( String contractAddress ){
//        log( "@ [execCheckHelloWorld]" );
//
//        try {
//            Web3j web3 = helper.getWeb3();
//            Credentials credentials = helper.getCurAccount();
//            ContractGasProvider gasProvider = new DefaultGasProvider();
//
//            // コントラクトが読み込めたら有効とみなす
//            HelloWorld contract = HelloWorld.load(
//                    contractAddress,
//                    web3,
//                    credentials,
//                    gasProvider
//            );
//        } catch ( Exception e ){
//            log( "@ EXCEPTION e=" + e.getMessage() );
//            return( false );
//        }
//
//        log( "@ VALID: contractAddress=" + contractAddress  );
//        return( true );
//    }

    //-------------------------------------
    // [HelloWorld]コントラクトのデプロイ
    //-------------------------------------
//    private String execDeployHelloWorld(){
//        log( "@ [execDeployHelloWorld]");
//
//        String contractAddress;
//        try {
//            Web3j web3 = helper.getWeb3();
//            Credentials credentials = helper.getCurAccount();
//            ContractGasProvider gasProvider = new DefaultGasProvider();
//
//            // デプロイ開始（※ヘルパーのあかんとに十分な残高がないと例外が発生する）
//            HelloWorld contract = HelloWorld.deploy(
//                    web3,
//                    credentials,
//                    gasProvider,
//                    "Hello web3j world"   // この文字列は[HelloWorld]のコンストラクタへの引数
//            ).send();
//
//            // アドレスの取得
//            contractAddress = contract.getContractAddress();
//        } catch ( Exception e ){
//            log( "@ EXCEPTION e=" + e.getMessage() );
//            return( "" );
//        }
//
//        log( "@ DEPLOYED: contractAddress=" + contractAddress  );
//        return( contractAddress );
//    }

    //-------------------------------------
    // [HelloWorld]コントラクトとやり取り
    //-------------------------------------
//    private boolean execInteractHelloWorld( String contractAddress ){
//        log( "@ [execInteractHelloWorld]");
//
//        try {
//            Web3j web3 = helper.getWeb3();
//            Credentials credentials = helper.getCurAccount();
//            ContractGasProvider gasProvider = new DefaultGasProvider();
//
//            // コントラクトの読み込み
//            HelloWorld contract = HelloWorld.load(
//                    contractAddress,
//                    web3,
//                    credentials,
//                    gasProvider
//            );
//
//            // [getWord]メソッドのコール（※これは[view]メソッドなので手数料は０）
//            log("@ BEFORE: HelloWorld.getWord()=" + contract.getWord().send());
//
//            // [setWord]メソッドのコール（※これはブロックチェーンに書き込むのでヘルパーのアカウントに十分な残高がないと例外が発生する）
//            Date d = new Date();
//            String sendWord = "Greeting from web3j at " + d.toString();
//            log( "@ HelloWorld.setWord( " + sendWord + " )" );
//            TransactionReceipt transactionReceipt = contract.setWord( sendWord ).send();
//
//            // 再度[getWord]を呼ぶ（※[setWord]で設定した文字列が返ってくることの確認）
//            log( "@ AFTER: HelloWorld.getWorld()=" + contract.getWord().send() );
//        } catch ( Exception e ){
//            log( "@ execInteractHelloWorld: EXCEPTION e=" + e.getMessage() );
//            return( false );
//        }
//
//        return( true );
//    }

    //----------------------------------------------------------------------------------------------
    // ログの出力
    //----------------------------------------------------------------------------------------------
    private void log( String msg ){
        Log.i( "TestWeb3", msg );
    }
}