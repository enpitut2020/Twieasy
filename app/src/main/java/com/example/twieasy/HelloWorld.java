package com.example.twieasy;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 4.2.0.
 */
public class HelloWorld extends Contract {
    private static final String BINARY = "60806040523480156100115760006000fd5b5060405161063d38038061063d833981810160405260208110156100355760006000fd5b81019080805160405193929190846401000000008211156100565760006000fd5b8382019150602082018581111561006d5760006000fd5b825186600182028301116401000000008211171561008b5760006000fd5b8083526020830192505050908051906020019080838360005b838110156100c05780820151818401525b6020810190506100a4565b50505050905090810190601f1680156100ed5780820380516001836020036101000a031916815260200191505b506040526020015050505b8060006000509080519060200190610111929190610119565b505b506101c9565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f1061015a57805160ff191683800117855561018d565b8280016001018555821561018d579182015b8281111561018c578251826000509090559160200191906001019061016c565b5b50905061019a919061019e565b5090565b6101c691906101a8565b808211156101c257600081815060009055506001016101a8565b5090565b90565b610465806101d86000396000f3fe60806040523480156100115760006000fd5b50600436106100465760003560e01c80632f64d3861461004c578063cd048de6146100d0578063ed40a8c81461019357610046565b60006000fd5b610054610217565b6040518080602001828103825283818151815260200191508051906020019080838360005b838110156100955780820151818401525b602081019050610079565b50505050905090810190601f1680156100c25780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b610191600480360360208110156100e75760006000fd5b81019080803590602001906401000000008111156101055760006000fd5b8201836020820111156101185760006000fd5b8035906020019184600183028401116401000000008311171561013b5760006000fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600081840152601f19601f820116905080830192505050505050509090919290909192905050506102b8565b005b61019b6102d6565b6040518080602001828103825283818151815260200191508051906020019080838360005b838110156101dc5780820151818401525b6020810190506101c0565b50505050905090810190601f1680156102095780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b60006000508054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156102b05780601f10610285576101008083540402835291602001916102b0565b820191906000526020600020905b81548152906001019060200180831161029357829003601f168201915b505050505081565b80600060005090805190602001906102d1929190610380565b505b50565b606060006000508054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156103715780601f1061034657610100808354040283529160200191610371565b820191906000526020600020905b81548152906001019060200180831161035457829003601f168201915b5050505050905061037d565b90565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f106103c157805160ff19168380011785556103f4565b828001600101855582156103f4579182015b828111156103f357825182600050909055916020019190600101906103d3565b5b5090506104019190610405565b5090565b61042d919061040f565b80821115610429576000818150600090555060010161040f565b5090565b9056fea265627a7a72315820a07c63f6aebddbab92be8a33bb0286b34742ad57d286c45de2977c43bde436b064736f6c634300050c0032";

    public static final String FUNC_GETWORD = "getWord";

    public static final String FUNC_SETWORD = "setWord";

    public static final String FUNC_WORD = "word";

    @Deprecated
    protected HelloWorld(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected HelloWorld(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected HelloWorld(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected HelloWorld(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteCall<String> getWord() {
        final Function function = new Function(FUNC_GETWORD,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<TransactionReceipt> setWord(String _word) {
        final Function function = new Function(
                FUNC_SETWORD,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_word)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<String> word() {
        final Function function = new Function(FUNC_WORD,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    @Deprecated
    public static HelloWorld load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new HelloWorld(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static HelloWorld load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new HelloWorld(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static HelloWorld load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new HelloWorld(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static HelloWorld load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new HelloWorld(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<HelloWorld> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider, String _word) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_word)));
        return deployRemoteCall(HelloWorld.class, web3j, credentials, contractGasProvider, BINARY, encodedConstructor);
    }

    public static RemoteCall<HelloWorld> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider, String _word) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_word)));
        return deployRemoteCall(HelloWorld.class, web3j, transactionManager, contractGasProvider, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<HelloWorld> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, String _word) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_word)));
        return deployRemoteCall(HelloWorld.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<HelloWorld> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, String _word) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_word)));
        return deployRemoteCall(HelloWorld.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
    }
}