package com.example.twieasy;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Type;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.Log;
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
public class Owner extends Contract {
    private static final String BINARY = "608060405234801561001057600080fd5b50336000806101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555060008054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16600073ffffffffffffffffffffffffffffffffffffffff167f342827c97908e5e2f71151c08502a66d44b6f758e3ac2f1de95f02eb95f0a73560405160405180910390a3610290806100db6000396000f3fe608060405234801561001057600080fd5b50600436106100365760003560e01c8063893d20e81461003b578063a6f9dae11461006f575b600080fd5b6100436100b3565b604051808273ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b6100b16004803603602081101561008557600080fd5b81019080803573ffffffffffffffffffffffffffffffffffffffff1690602001909291905050506100dc565b005b60008060009054906101000a900473ffffffffffffffffffffffffffffffffffffffff16905090565b60008054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff161461019d576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260138152602001807f43616c6c6572206973206e6f74206f776e65720000000000000000000000000081525060200191505060405180910390fd5b8073ffffffffffffffffffffffffffffffffffffffff1660008054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff167f342827c97908e5e2f71151c08502a66d44b6f758e3ac2f1de95f02eb95f0a73560405160405180910390a3806000806101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055505056fea264697066735822122016bd32cc2ed3eb34c813a9e6d5ec1ba3c321b402b940041e4b06d885b08a64fe64736f6c634300060c0033";

    public static final String FUNC_CHANGEOWNER = "changeOwner";

    public static final String FUNC_GETOWNER = "getOwner";

    public static final Event OWNERSET_EVENT = new Event("OwnerSet", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}));
    ;

    @Deprecated
    protected Owner(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected Owner(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected Owner(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected Owner(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public List<OwnerSetEventResponse> getOwnerSetEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(OWNERSET_EVENT, transactionReceipt);
        ArrayList<OwnerSetEventResponse> responses = new ArrayList<OwnerSetEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            OwnerSetEventResponse typedResponse = new OwnerSetEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.oldOwner = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.newOwner = (String) eventValues.getIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<OwnerSetEventResponse> ownerSetEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, OwnerSetEventResponse>() {
            @Override
            public OwnerSetEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(OWNERSET_EVENT, log);
                OwnerSetEventResponse typedResponse = new OwnerSetEventResponse();
                typedResponse.log = log;
                typedResponse.oldOwner = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.newOwner = (String) eventValues.getIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<OwnerSetEventResponse> ownerSetEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(OWNERSET_EVENT));
        return ownerSetEventFlowable(filter);
    }

    public RemoteCall<TransactionReceipt> changeOwner(String newOwner) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_CHANGEOWNER, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(newOwner)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> getOwner() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_GETOWNER, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static Owner load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new Owner(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static Owner load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Owner(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static Owner load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new Owner(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static Owner load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new Owner(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<Owner> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(Owner.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    public static RemoteCall<Owner> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(Owner.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<Owner> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Owner.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<Owner> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Owner.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public static class OwnerSetEventResponse {
        public Log log;

        public String oldOwner;

        public String newOwner;
    }
}
