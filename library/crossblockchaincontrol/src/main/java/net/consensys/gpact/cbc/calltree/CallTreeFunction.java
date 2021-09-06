package net.consensys.gpact.cbc.calltree;

import net.consensys.gpact.common.FormatConversion;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import static net.consensys.gpact.common.FormatConversion.addressStringToBytes;

public class CallTreeFunction {
    BigInteger blockchainId;
    String contractAddress;
    String functionCallData;
    // In order list of functions called by this function.
    ArrayList<CallTreeFunction> calledFunctions;

    public CallTreeFunction(BigInteger blockchainId, String contractAddress, String functionCallData) {
        this.blockchainId = blockchainId;
        this.contractAddress = contractAddress;
        this.functionCallData = functionCallData;
    }

    public CallTreeFunction(BigInteger blockchainId, String contractAddress, String functionCallData, ArrayList<CallTreeFunction> calledFunctions) {
        this.blockchainId = blockchainId;
        this.contractAddress = contractAddress;
        this.functionCallData = functionCallData;
        this.calledFunctions = calledFunctions;
    }

    public BigInteger getBlockchainId() {
        return blockchainId;
    }

    public String getContractAddress() {
        return contractAddress;
    }

    public String getFunctionCallData() {
        return functionCallData;
    }

    public ArrayList<CallTreeFunction> getCalledFunctions() {
        return calledFunctions;
    }

    public byte[] encode() throws Exception {
        return encodeRecursive(this);

    }


    public static final byte LEAF_FUNCTION = 0;
    public static final int MAX_CALL_EX_TREE_SIZE = 1000000;
    public static final int BYTES_PER_LENGTH = 4;
    public static final int MAX_CALLED_FUNCTIONS = 127;


    private byte[] encodeRecursive(CallTreeFunction function) throws Exception {
        List<CallTreeFunction> calledFunctions = function.getCalledFunctions();

        ByteBuffer buf = ByteBuffer.allocate(MAX_CALL_EX_TREE_SIZE);
        boolean isLeafFunction = calledFunctions == null || calledFunctions.isEmpty();
        if (isLeafFunction) {
            buf.put(LEAF_FUNCTION);
            buf.put(function.encodeFunctionCall());
        }
        else {
            List<byte[]> encodedCalledFunctions = new ArrayList<>();
            encodedCalledFunctions.add(function.encodeFunctionCall());
            for (CallTreeFunction func: calledFunctions) {
                encodedCalledFunctions.add(encodeRecursive(func));
            }

            // Non-leaf function
            int numCalledFunctions = calledFunctions.size();
            if (numCalledFunctions > MAX_CALLED_FUNCTIONS) {
                throw new Exception("Too many called functions: " + calledFunctions.size());
            }
            byte numCalledFunctionsB = (byte) numCalledFunctions;
            buf.put(numCalledFunctionsB);
            int offset = numCalledFunctions * BYTES_PER_LENGTH;
            for (byte[] encodedCalledFunc : encodedCalledFunctions) {
                buf.putInt(offset);
                offset += encodedCalledFunc.length;
            }
            for (byte[] encodedCalledFunc : encodedCalledFunctions) {
                buf.put(encodedCalledFunc);
            }
        }

        buf.flip();
        byte[] output = new byte[buf.limit()];
        buf.get(output);
        return output;
    }

    private byte[] encodeFunctionCall() {
        ByteBuffer buf = ByteBuffer.allocate(MAX_CALL_EX_TREE_SIZE);
        byte[] blockchainIdBytes = FormatConversion.bigIntegerToByteArray(this.blockchainId);
        byte[] address = addressStringToBytes(this.contractAddress);
        buf.put(blockchainIdBytes);
        buf.put(address);
        byte[] data = FormatConversion.hexStringToByteArray(this.functionCallData);
        buf.putShort((short) data.length);
        buf.put(data);
        buf.flip();
        byte[] output = new byte[buf.limit()];
        buf.get(output);
        return output;
    }


    /**
     * Dump an encoded Call Execution Tree.
     *
     * @param encodedCallExecutionTree Tree to be processed.
     */
    public static String dump(byte[] encodedCallExecutionTree) throws Exception {
        StringBuilder out = new StringBuilder();
        ByteBuffer buf = ByteBuffer.wrap(encodedCallExecutionTree);

        out.append("Encoded Call Execution Tree: ");
        out.append(encodedCallExecutionTree.length);
        out.append(" bytes");
        out.append("\n");

        dumpRecursive(out, buf, 0);

        return out.toString();
    }

    private static void dumpRecursive(StringBuilder out, ByteBuffer buf, int level) throws Exception {
        byte numFuncsCalled = buf.get();

        if (numFuncsCalled < 0) {
            // That is 128 to 255 is not allowed.
            throw new Exception("Num Called Functions must be between 0 and 127");
        }
        for (int i = 0; i < numFuncsCalled + 1; i++) {
            int offset = buf.getInt();
            addSpaces(out, level);
            out.append("Offset of Function: ");
            out.append(offset);
            out.append("\n");
        }

        decodeFunction(out, buf, level);

        for (int i = 0; i < numFuncsCalled + 1; i++) {
            dumpRecursive(out, buf, level + 1);
        }
    }

    private static void decodeFunction(StringBuilder out, ByteBuffer buf, int level) {
        byte[] blockchainId = new byte[32];
        buf.get(blockchainId);

        byte[] address = new byte[20];
        buf.get(address);

        short len = buf.getShort();
        byte[] data = new byte[len];
        buf.get(data);

        addSpaces(out, level);
        out.append("Blockchain Id: 0x");
        out.append(FormatConversion.byteArrayToString(blockchainId));
        out.append("\n");
        addSpaces(out, level);
        out.append("Contract: 0x");
        out.append(FormatConversion.byteArrayToString(address));
        out.append("\n");
        addSpaces(out, level);
        out.append("Data: 0x");
        out.append(FormatConversion.byteArrayToString(data));
        out.append("\n");
    }

    private static void addSpaces(StringBuilder out, int level) {
        for (int j = 0; j < level; j++) {
            out.append(" ");
        }
    }
}
