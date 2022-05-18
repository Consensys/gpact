package net.consensys.gpact.applications.twentyacts.helpers;

import java.nio.ByteBuffer;
import net.consensys.gpact.applications.twentyacts.TwentyActs;
import net.consensys.gpact.common.FormatConversion;
import net.consensys.gpact.common.crypto.Hash;
import org.apache.tuweni.bytes.Bytes;

/**
 * Functions to encode and message digest the TxInfo struct.
 *
 * <p>struct TxInfo { uint256 crosschainTxId; // Crosschain Transaction id created by the sender.
 * uint256 sourceBcId; // Source blockchain id. address sourceErc20Address; // Address of ERC 20
 * contract on source blockchain. uint256 targetBcId; // Target blockchain id. address
 * targetErc20Address; // Address of ERC 20 contract on the target blockchain. address sender; //
 * Sender address on source. address recipient; // Recipient address on target address
 * liquidityProvider; // Liquidity Provider address on both chains. uint256 amount; // Amount on
 * target uint256 lpFee; // Liquidity Provider Fee: Amount on source = amount + lpFee + inFee
 * uint256 inFee; // Infrastructure Provider Fee uint256 biddingPeriodEnd; // Bidding Period end in
 * seconds uint256 timeout; // Timeout by which time Prepare On Source must be mined by }
 */
public class TxInfoDigest {
  /**
   * Keccak256 of ABI encoded TxInfo data structure.
   *
   * @param txInfo Crosschain transaction information.
   * @return
   */
  public static byte[] txInfoDigest(final TwentyActs.TxInfo txInfo) {
    byte[] abiEncodedTxInfo = abiEncodeTxInfo(txInfo);
    return Hash.keccak256(Bytes.wrap(abiEncodedTxInfo)).toArray();
  }

  /**
   * Get the ABI encoding of the txInfo data structure.
   *
   * @param txInfo Crosschain transaction information.
   * @return ABI encoding of TxInfo struct.
   */
  public static byte[] abiEncodeTxInfo(final TwentyActs.TxInfo txInfo) {
    ByteBuffer buf = ByteBuffer.allocate(100000);
    byte[] crosschainTxId = FormatConversion.bigIntegerToUint256ByteArray(txInfo.crosschainTxId);
    byte[] sourceBcId = FormatConversion.bigIntegerToUint256ByteArray(txInfo.sourceBcId);
    byte[] sourceErc20Address =
        FormatConversion.addressStringToPaddedBytes(txInfo.sourceErc20Address);
    byte[] targetBcId = FormatConversion.bigIntegerToUint256ByteArray(txInfo.targetBcId);
    byte[] targetErc20Address =
        FormatConversion.addressStringToPaddedBytes(txInfo.targetErc20Address);
    byte[] sender = FormatConversion.addressStringToPaddedBytes(txInfo.sender);
    byte[] recipient = FormatConversion.addressStringToPaddedBytes(txInfo.recipient);
    byte[] liquidityProvider =
        FormatConversion.addressStringToPaddedBytes(txInfo.liquidityProvider);
    byte[] amount = FormatConversion.bigIntegerToUint256ByteArray(txInfo.amount);
    byte[] lpFee = FormatConversion.bigIntegerToUint256ByteArray(txInfo.lpFee);
    byte[] inFee = FormatConversion.bigIntegerToUint256ByteArray(txInfo.inFee);
    byte[] biddingPeriodEnd =
        FormatConversion.bigIntegerToUint256ByteArray(txInfo.biddingPeriodEnd);
    byte[] timeout = FormatConversion.bigIntegerToUint256ByteArray(txInfo.timeout);

    buf.put(crosschainTxId);
    buf.put(sourceBcId);
    buf.put(sourceErc20Address);
    buf.put(targetBcId);
    buf.put(targetErc20Address);
    buf.put(sender);
    buf.put(recipient);
    buf.put(liquidityProvider);
    buf.put(amount);
    buf.put(lpFee);
    buf.put(inFee);
    buf.put(biddingPeriodEnd);
    buf.put(timeout);
    buf.flip();
    byte[] output = new byte[buf.limit()];
    buf.get(output);
    return output;
  }
}
