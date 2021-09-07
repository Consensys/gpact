package net.consensys.gpact.cbc.calltree;

import net.consensys.gpact.common.FormatConversion;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import static net.consensys.gpact.common.FormatConversion.addressStringToBytes;

public class CallExecutionTree {
    BigInteger blockchainId;
    String contractAddress;
    String functionCallData;
    // In order list of functions called by this function.
    ArrayList<CallExecutionTree> calledFunctions;

    public CallExecutionTree(BigInteger blockchainId, String contractAddress, String functionCallData) {
        this.blockchainId = blockchainId;
        this.contractAddress = contractAddress;
        this.functionCallData = functionCallData;
    }

    public CallExecutionTree(BigInteger blockchainId, String contractAddress, String functionCallData, ArrayList<CallExecutionTree> calledFunctions) {
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

    public ArrayList<CallExecutionTree> getCalledFunctions() {
        return calledFunctions;
    }

    public byte[] encode() throws CallTreeException {
        return encodeRecursive(this);

    }

    public void verify() throws CallTreeException {
        verify(encode());
    }

    public String dump() throws CallTreeException {
        return dump(encode());
    }

    @Override
    public String toString() {
        try {
            return dump(encode());
        } catch (CallTreeException ex) {
            return ex.getMessage();
        }
    }


    public static final byte LEAF_FUNCTION = 0;
    public static final int MAX_CALL_EX_TREE_SIZE = 1000000;
    public static final int BYTES_PER_LENGTH = 4;
    public static final int MAX_CALLED_FUNCTIONS = 255;


    private byte[] encodeRecursive(CallExecutionTree function) throws CallTreeException {
        List<CallExecutionTree> calledFunctions = function.getCalledFunctions();

        ByteBuffer buf = ByteBuffer.allocate(MAX_CALL_EX_TREE_SIZE);
        boolean isLeafFunction = calledFunctions == null || calledFunctions.isEmpty();
        if (isLeafFunction) {
            buf.put(LEAF_FUNCTION);
            buf.put(function.encodeFunctionCall());
        } else {
            List<byte[]> encodedCalledFunctions = new ArrayList<>();
            encodedCalledFunctions.add(function.encodeFunctionCall());
            for (CallExecutionTree func : calledFunctions) {
                encodedCalledFunctions.add(encodeRecursive(func));
            }

            // Non-leaf function
            int numCalledFunctions = calledFunctions.size();
            if (numCalledFunctions > MAX_CALLED_FUNCTIONS) {
                throw new CallTreeException("Too many called functions: " + calledFunctions.size());
            }
            buf.put((byte) numCalledFunctions);
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
    public static String dump(byte[] encodedCallExecutionTree) throws CallTreeException {
        StringBuilder out = new StringBuilder();

        out.append("Encoded Call Execution Tree: ");
        out.append(encodedCallExecutionTree.length);
        out.append(" bytes");
        out.append("\n");

        process(encodedCallExecutionTree, out);

        return out.toString();
    }

    public static void verify(byte[] encodedCallExecutionTree) throws CallTreeException {
        process(encodedCallExecutionTree, null);
    }


    /**
     * Dump an encoded Call Execution Tree.
     *
     * @param encodedCallExecutionTree Tree to be processed.
     */
    public static void process(final byte[] encodedCallExecutionTree, final StringBuilder out) throws CallTreeException {
        ByteBuffer buf = ByteBuffer.wrap(encodedCallExecutionTree);
        int size = processRecursive(out, buf, 0);
        if (buf.remaining() != 0) {
            throw new CallTreeException(
                    "Not all bytes from Call Execution Tree processed: " + buf.remaining() + "remaining", out);
        }
        if (size != buf.capacity()) {
            throw new CallTreeException(
                    "Not all bytes from Call Execution Tree processed: " + size + ", " + buf.capacity(), out);
        }
    }

    private static int processRecursive(StringBuilder out, ByteBuffer buf, int level) throws CallTreeException {
        int size = 0;
        final int NUM_FUNCS_CALLED_SIZE = 1;
        size += NUM_FUNCS_CALLED_SIZE;
        int numFuncsCalled = uint8(buf.get());

        if (numFuncsCalled > 0) {
            final int OFFSET_SIZE = 4;
            int[] offsets = new int[numFuncsCalled + 1];
            for (int i = 0; i < numFuncsCalled + 1; i++) {
                size += OFFSET_SIZE;
                int offset = buf.getInt();
                offsets[i] = offset;
                if (out != null) {
                    addSpaces(out, level);
                    out.append("Offset of Function: ");
                    out.append(offset);
                    out.append("\n");
                }
            }

            int used = decodeFunction(out, buf, level);
            if (used != offsets[1] - offsets[0]) {
                throw new CallTreeException(
                        "Function offsets " + offsets[0] + " and " + offsets[1] + " do not match the length " + used, out);
            }
            size += used;

            for (int i = 1; i < numFuncsCalled + 1; i++) {
                used = processRecursive(out, buf, level + 1);
                if (i < numFuncsCalled) {
                    if (used != offsets[i + 1] - offsets[i]) {
                        throw new CallTreeException(
                                "Function offsets " + offsets[i] + " and " + offsets[i + 1] + " do not match the length " + used, out);
                    }
                }
                size += used;
            }
        }
        else {
            size += decodeFunction(out, buf, level);
        }
        return size;
    }

    private static int decodeFunction(StringBuilder out, ByteBuffer buf, int level) throws CallTreeException {
        final int BLOCKCHAIN_ID_SIZE = 32;
        byte[] blockchainId = new byte[BLOCKCHAIN_ID_SIZE];
        buf.get(blockchainId);

        final int ADDRESS_SIZE = 20;
        byte[] address = new byte[ADDRESS_SIZE];
        buf.get(address);

        // Length is an unsigned short (uint16)
        final int DATA_LEN_SIZE_SIZE = 2;
        int len = uint16(buf.getShort());
        if (len > buf.remaining()) {
            throw new CallTreeException(
                    "Decoded length " + len + "bytes, longer than remaining space " + buf.remaining() + " bytes", out);
        }
        byte[] data = new byte[len];
        buf.get(data);

        if (out != null) {
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

        return BLOCKCHAIN_ID_SIZE + ADDRESS_SIZE + DATA_LEN_SIZE_SIZE + len;
    }

    private static void addSpaces(StringBuilder out, int level) {
        for (int j = 0; j < level; j++) {
            out.append(" ");
        }
    }

    private static int uint16(final short val) {
        int valInt = val;
        if (valInt < 0) {
            valInt += (Short.MAX_VALUE + 1) * 2;
        }
        return valInt;
    }

    private static int uint8(final byte val) {
        int valInt = val;
        if (valInt < 0) {
            valInt += (Byte.MAX_VALUE + 1) * 2;
        }
        return valInt;
    }
}
