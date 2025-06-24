package xyz.keroro.hikvisionfacedata.library;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;
import xyz.keroro.hikvisionfacedata.constant.HikConstant;

/**
 * @author wangpeng
 * @since 2025年06月23日 14:52
 */
public interface HCNetSDK extends Library {

    NativeLibrary NATIVE_LIBRARY = NativeLibrary.getInstance(HikConstant.HikLibPath + "/libhcnetsdk.so");


}
