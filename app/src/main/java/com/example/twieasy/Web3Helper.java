package com.example.twieasy;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.protocol.http.HttpService;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.security.Provider;
import java.security.Security;

import kotlin.jvm.Throws;

//---------------------------------------------------------------------------------------
// Web3Helper
//----------------------------------------------------------------------------------------
// 単一アカウント(Credentials)によるイーサリアム管理
// メモ：[WalletUtils]がファイル読み書きを軸にアカウント管理をしているっぽいので、
// 　　　ヘルパー内でもローカルファイルの保存処理を行う（※欲を言うと入出力は外に出したい）
//----------------------------------------------------------------------------------------
public class Web3Helper {
    //-------------------------------------
    // 定数
    //-------------------------------------
    // イーサリアムネットワーク種別（※とりあえず、各ネットワークとＩＤを合わせておく）
    final static public int MAINNET = 1;
    final static public int ROPSTEN = 3;
    final static public int RINKEBY = 4;
    final static public int KOVAN = 42;

    // 接続先のパスを取得
    static public String GetInfuraPath( int target ){
        // FIXME [Infura]へのパス（※[https://infura.io]でプロジェクトを作って下記をおきかえてください）
        // メモ：ちょっとしたテストであれば下記のパスをそのままお使いいただけますが、
        // 　　　予告なく使えなくなるかもしれないのでご了承ください（※筆者がうっかりプロジェクトを消してしまう等）
        switch( target ){
            case MAINNET:
                return( "https://mainnet.infura.io/v3/e6eb2fc288634fc3bf9378098aad2115" );

            case ROPSTEN:
                return( "https://ropsten.infura.io/v3/e6eb2fc288634fc3bf9378098aad2115" );

            case RINKEBY:
                return( "https://rinkeby.infura.io/v3/c16c6f2b531b43c9a92dd95fe5eac2b5" );

            case KOVAN:
                return( "https://kovan.infura.io/v3/e6eb2fc288634fc3bf9378098aad2115" );
        }

        // ここまで来たら無効値を返しておく
        return( "infura connection error" );
    }

    // 接続先の名前を取得
    static public String GetNetworkName( int target ){
        switch( target ){
            case MAINNET:
                return( "MainNet" );

            case ROPSTEN:
                return( "Ropsten" );

            case RINKEBY:
                return( "Rinkeby" );

            case KOVAN:
                return( "Kovan" );
        }

        return( "Unknown" );
    }


    //-------------------------------------
    // メンバー
    //-------------------------------------
    private Context context;               // コンテキスト（※ファイル入出力用）
    private Web3j web3;                    // web3jインスタンス
    private int curTarget;                // 現在の接続先
    private String curAccountFileName;   // 現在のアカウントファイル名(json読み出し用）
    private Credentials curCredentials;   // 現在のアカウント

    //----------------------------------------------------------------------------
    // コンストラクタ
    //----------------------------------------------------------------------------
    public Web3Helper( Context context ){
        this.context = context;
        clearTarget();
        clearAccount();
    }

    //----------------------------------------
    // 接続先のクリア
    //----------------------------------------
    public void clearTarget(){
        web3 = null;
        curTarget = 0;
    }

    //----------------------------------------------------------------------------
    // 接続先の有効性を判定
    //----------------------------------------------------------------------------
    public boolean isTargetValid() {
        if( curTarget <= 0 ){
            return( false );
        }

        if( web3 == null ){
            return( false );
        }

        return( true );
    }

    //----------------------------------------
    // 接続先を設定
    //----------------------------------------
    public boolean setTarget( int target ){
        // 古い接続先は切断
        clearTarget();

        // 接続先の確認（※無効なら無視）
        String targetPath = GetInfuraPath( target );
        if( targetPath == null || targetPath.equals( "" ) ){
            log( "@ setTarget: UNKNOWN target=" + target );
            return( false );
        }

        // [web3]インスタンスの作成
        Web3j newWeb3 = Web3j.build( new HttpService( targetPath ) );

        try {
            // バージョン確認が出来たら接続成功とみなす
            Web3ClientVersion clientVersion = newWeb3.web3ClientVersion().sendAsync().get();
            if( clientVersion.hasError() ){
                log( "@ setTarget: FAILED error=" + clientVersion.getError().getMessage() );
                return( false );
            }
        } catch ( Exception e ) {
            log( "@ setTarget: EXCEPTION e=" + e.getMessage() );
            return( false );
        }

        // ここまで来たら成功
        web3 = newWeb3;
        curTarget = target;
        return( true );
    }

    //----------------------------------------
    // web3取得
    //----------------------------------------
    public Web3j getWeb3(){
        if( ! isTargetValid() ){
            log( "@ getWeb3: invalid call" );
            return( null );
        }

        return( web3 );
    }

    //----------------------------------------
    // 接続先取得
    //----------------------------------------
    public int getCurTarget(){
        if( ! isTargetValid()  ){
            log( "@ getCurTarget: invalid call" );
            return( 0 );
        }

        return( curTarget );
    }

    //----------------------------------------
    // アカウントのクリア
    //----------------------------------------
    public void clearAccount(){
        curAccountFileName = "";
        curCredentials = null;
    }

    //----------------------------------------------------------------------------
    // ヘルパーの有効性を判定
    //----------------------------------------------------------------------------
    public boolean isValid() {
        if( !isTargetValid() ){
            return( false );
        }

        if( curAccountFileName == null || curAccountFileName.equals( "" ) ){
            return( false );
        }

        if( curCredentials == null ){
            return( false );
        }

        return( true );
    }

    //--------------------------------------------------------------------------------------
    // アカウントの作成
    // [dumpName]が空文字であれば作成されたイーサリアムアドレスの名前でファイルが出力される
    //--------------------------------------------------------------------------------------
    public boolean createNewAccount( String password, String dumpName ){
        String newAccountFileName;
        try{
            File walletFolder = new File( context.getFilesDir().getAbsolutePath() );
            log(context.getFilesDir().getAbsolutePath());
            // [generateNewWalletFile]を[useFullScrypt:true]で実行するとメモリ不足でアプリが落ちるので[false]を指定
            // 参考：https://github.com/web3j/web3j/issues/915
            String originalFileName = WalletUtils.generateNewWalletFile( password, walletFolder, false );

            // アカウントファイルを別名で出力（※次回以降のアクセス用）
            newAccountFileName = dumpAccountJson( password, originalFileName, dumpName );

            // 元ファイルは重複になるので消しておく
            context.deleteFile( originalFileName );
        } catch ( Exception e ){
            log( "@ createNewAccount: EXCEPTION e=" + e.getMessage() );
            return( false );
        }

        // 作成したアカウントの読み込み
        return( loadAccount( password, newAccountFileName ) );
    }



    //----------------------------------------
    // アカウントの読み込み
    //----------------------------------------
    public boolean loadAccount( String password, String fileName ){
        // 古いアカウントはクリア
        clearAccount();

        Credentials newCredentials;
        try {
            //String filePath = context.getFilesDir().getAbsolutePath() + "/" + fileName;
            //log( filePath);
            //InputStream abpath = getClass().getResourceAsStream("/assets/key.json");
            //String filePath = new String(InputStreamToByte(abpath ));
            //String filePath = "file:///android_asset/key.json";

            //newCredentials = WalletUtils.loadCredentials( password, filePath );
            newCredentials = WalletUtils.loadJsonCredentials(password,"{\"address\":\"42829f9c5b790812209e5d5808f073960368f4e3\",\"id\":\"659739f1-c987-4b59-ba3b-53be65c835b4\",\"version\":3,\"crypto\":{\"cipher\":\"aes-128-ctr\",\"cipherparams\":{\"iv\":\"5e8b5b7294be2e6a146988da177f71cc\"},\"ciphertext\":\"755db52192800a3b4f29048ba8b09f0e3d826fd2b74392124d9e6018f140ce91\",\"kdf\":\"scrypt\",\"kdfparams\":{\"dklen\":32,\"n\":4096,\"p\":6,\"r\":8,\"salt\":\"c9924d6e4c55923446c62b09b794a4ab4fcf18f9a4fc5c8a437febf68450b2eb\"},\"mac\":\"cef9b801a66121fc6e74d19db41098afc65bd38d84c1c67deed769b5e4500511\"}}");
        } catch ( Exception e ){
            log( "@ loadAccount EXCEPTION e=" + e.getMessage() );
            return( false );
        }

        // ここまで来たら成功
        curAccountFileName = fileName;
        curCredentials = newCredentials;
        return( true );
    }

    //----------------------------------------
    // 現アドレスのファイル名の取得
    //----------------------------------------
    public String getCurAccountFileName(){
        if( ! isValid() ){
            log( "@ getCurAccountFileName: invalid call" );
            return( "" );
        }

        return( curAccountFileName );
    }

    //----------------------------------------
    // 現アカウント(Credentials)の取得
    //----------------------------------------
    public Credentials getCurAccount(){
        if( ! isValid() ){
            log( "@ getCurAccount: invalid call" );
            return( null );
        }

        return( curCredentials );
    }

    //----------------------------------------
    // 現アカウントのJSON文字列の取得
    //----------------------------------------
    public String getCurAccountJson(){
        if( ! isValid() ){
            log( "@ getCurAccountJson: invalid call" );
            return( "" );
        }

        String strJson;
        try{
            strJson = loadLine( curAccountFileName );
        }catch( Exception e ){
            log( "@ getCurAccountJson: EXCEPTION e=" + e.getMessage() );
            return( "" );
        }

        return( strJson );
    }

    //----------------------------------------
    // 現アカウントの秘密鍵の取得
    //----------------------------------------
    public String getCurPrivateKey(){
        if( ! isValid() ){
            log( "@ getCurPrivateKey: invalid call" );
            return( "" );
        }

        // 接頭子"0x"のつかない６４文字の１６進数文字列が返る
        ECKeyPair keyPair = curCredentials.getEcKeyPair();
        BigInteger biPrivateKey = keyPair.getPrivateKey();
        return( String.format( "%064x", biPrivateKey ) );
    }

    //------------------------------------------
    // 現アカウントのイーサリアムアドレスの取得
    //------------------------------------------
    public String getCurEthereumAddress(){
        if( ! isValid() ){
            log( "@ getCurEthereumAddress: invalid call" );
            return( "" );
        }

        // 接頭子"0x"のついた４０文字の１６進数文字列が返る
        return( curCredentials.getAddress() );
    }

    //----------------------------------------
    // 現アカウントの残高の取得
    //----------------------------------------
    public BigInteger getCurBalance( ){
        if( ! isValid() ){
            log( "@ getCurBalance: invalid call" );
            return( BigInteger.ZERO );
        }

        BigInteger balance;

        try {
            String address = getCurEthereumAddress();
            EthGetBalance ethGetBalance = web3.ethGetBalance(address, DefaultBlockParameterName.LATEST).sendAsync().get();
            balance = ethGetBalance.getBalance();
        } catch ( Exception e ){
            log( "@ getCurBalance: EXCEPTION e=" + e.getMessage() );
            return( BigInteger.ZERO );
        }

        return( balance );
    }

    //---------------------------------------------------------------------------------------------------------------
    // アカウントファイルの出力（※返値は出力ファイル名）
    // メモ：[WalletUtils.generateNewWalletFile]はアカウントファイルを生成するが、そのファイル名が
    // 　　　[UTC--2020-02-28T18-47-09.7Z--2b1f80c8ad200b5245a665c5a0e3737e4950f360.json]という形式で管理しづらいので、
    //       出力ファイル名を[dumpName]で指定して別ファイルに吐き出す
    // 　　　[dumpName]が空であればアドレス形式[0x2b1f80c8ad200b5245a665c5a0e3737e4950f360.json]で吐き出す
    //---------------------------------------------------------------------------------------------------------------
    private String dumpAccountJson( String password, String originalFile, String dumpName ) {
        String jsonName;

        try {
            // 出力名の設定が無効なら
            if( dumpName == null || dumpName.equals( "" ) ) {
                // アドレスをファイル名につける
                String filePath = context.getFilesDir().getAbsolutePath() + "/" + originalFile;
                Credentials credentials = WalletUtils.loadCredentials( password, filePath );
                jsonName = credentials.getAddress() + ".json";
            }else{
                jsonName = dumpName;
            }

            // JSON内容の取得
            String strJson = loadLine( originalFile );

            // 指定の名前でダンプ
            dumpString( jsonName, strJson );
        }catch( Exception e ){
            log( "@ dumpAccountJson: EXCEPTION e=" + e.getMessage() );
            return( "" );
        }

        return( jsonName );
    }

    //----------------------------------------------------------------------------------------------
    // ファイルから１行読む（※例外は呼び出し元にお任せ）
    //----------------------------------------------------------------------------------------------
    private String loadLine( String fileName ) throws Exception{
        FileInputStream is = context.openFileInput( fileName );
        BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
        String strLine = reader.readLine();
        reader.close();

        return( strLine );
    }

    //----------------------------------------------------------------------------------------------
    // ファイルに文字列を出力する（※例外は呼び出し元にお任せ）
    //----------------------------------------------------------------------------------------------
    private void dumpString( String fileName, String str ) throws Exception{
        FileOutputStream os = context.openFileOutput( fileName, Context.MODE_PRIVATE );
        os.write( str.getBytes() );
    }

    //----------------------------------------------------------------------------------------------
    // ログの出力
    //----------------------------------------------------------------------------------------------
    private void log( String msg ){
        Log.i( "Web3Helper", msg );
    }

    //----------------------------------------------------------------------------------------------
    // [BouncyCastle]の設定（※[MainActivity]の[onCreate]で呼ぶこと）
    // 参考：https://github.com/web3j/web3j/issues/915
    //----------------------------------------------------------------------------------------------
    static public void SetupBouncyCastle() {
        final Provider provider = Security.getProvider(BouncyCastleProvider.PROVIDER_NAME);
        if (provider == null) {
            // Web3j will set up the provider lazily when it's first used.
            return;
        }
        if (provider.getClass().equals(BouncyCastleProvider.class)) {
            // BC with same package name, shouldn't happen in real life.
            return;
        }
        // Android registers its own BC provider. As it might be outdated and might not include
        // all needed ciphers, we substitute it with a known BC bundled in the app.
        // Android's BC has its package rewritten to "com.android.org.bouncycastle" and because
        // of that it's possible to have another BC implementation loaded in VM.
        Security.removeProvider(BouncyCastleProvider.PROVIDER_NAME);
        Security.insertProviderAt(new BouncyCastleProvider(), 1);
    }
}