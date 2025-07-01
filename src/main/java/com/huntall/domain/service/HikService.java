package com.huntall.domain.service;

import com.huntall.common.util.CommonUtil;
import com.huntall.common.constant.HikConstant;
import com.huntall.common.sdk.HCNetSDK;
import com.huntall.domain.entity.FMSGCallBack_V31;
import com.sun.jna.Native;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 海康设备service
 *
 * @author wangpeng
 * @since 2025年06月30日 17:12
 */
@Service
public class HikService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * SDK实例
     */
    public HCNetSDK hCNetSDK = null;

    /**
     * 用户句柄
     */
    public int lUserID = -1;

    /**
     * 报警布防句柄
     */
    public int lAlarmHandle = -1;

    /**
     * 报警布防回调函数
     */
    public HCNetSDK.FMSGCallBack_V31 fMSFCallBack_V31 = null;

    /**
     * 创建SDK实例
     * 根据不同操作系统选择不同的库文件和库路径
     *
     * @return 是否成功
     */
    private boolean createSDKInstance() {
        logger.info("开始创建SDK实例");
        if (hCNetSDK == null) {
            synchronized (HCNetSDK.class) {
                String strDllPath = "";
                try {
                    // System.setProperty("jna.debug_load", "true");
                    if (CommonUtil.isWindows())
                        // win系统加载库路径
                        strDllPath = HikConstant.LIB_PATH_PREFIX + "\\lib\\HCNetSDK.dll";
                    else if (CommonUtil.isLinux())
                        // Linux系统加载库路径
                        strDllPath = HikConstant.LIB_PATH_PREFIX + "/lib/libhcnetsdk.so";
                    hCNetSDK = (HCNetSDK) Native.loadLibrary(strDllPath, HCNetSDK.class);
                } catch (Exception ex) {
                    logger.error("loadLibrary: {} Error: {}", strDllPath, ex.getMessage());
                    return false;
                }
            }
        }
        logger.info("创建SDK实例成功");
        return true;
    }


    /**
     * 登录设备，支持 V40 和 V30 版本，功能一致。
     *
     * @param ip   设备IP地址
     * @param port SDK端口，默认为设备的8000端口
     * @param user 设备用户名
     * @param psw  设备密码
     * @return 登录成功返回用户ID，失败返回-1
     */
    private int loginDevice(String ip, short port, String user, String psw) {
        logger.info("开始登录设备");

        // 创建设备登录信息和设备信息对象
        HCNetSDK.NET_DVR_USER_LOGIN_INFO loginInfo = new HCNetSDK.NET_DVR_USER_LOGIN_INFO();
        HCNetSDK.NET_DVR_DEVICEINFO_V40 deviceInfo = new HCNetSDK.NET_DVR_DEVICEINFO_V40();

        // 设置设备IP地址
        byte[] deviceAddress = new byte[HCNetSDK.NET_DVR_DEV_ADDRESS_MAX_LEN];
        byte[] ipBytes = ip.getBytes();
        System.arraycopy(ipBytes, 0, deviceAddress, 0, Math.min(ipBytes.length, deviceAddress.length));
        loginInfo.sDeviceAddress = deviceAddress;

        // 设置用户名和密码
        byte[] userName = new byte[HCNetSDK.NET_DVR_LOGIN_USERNAME_MAX_LEN];
        byte[] password = psw.getBytes();
        System.arraycopy(user.getBytes(), 0, userName, 0, Math.min(user.length(), userName.length));
        System.arraycopy(password, 0, loginInfo.sPassword, 0, Math.min(password.length, loginInfo.sPassword.length));
        loginInfo.sUserName = userName;

        // 设置端口和登录模式
        loginInfo.wPort = port;
        // 同步登录
        loginInfo.bUseAsynLogin = false;
        // 使用SDK私有协议
        loginInfo.byLoginMode = 0;

        // 执行登录操作
        int userID = hCNetSDK.NET_DVR_Login_V40(loginInfo, deviceInfo);
        if (userID == -1) {
            logger.error("登录失败，错误码为: {}", hCNetSDK.NET_DVR_GetLastError());
        } else {
            logger.info("{} 设备登录成功！", ip);
            // 处理通道号逻辑
            int startDChan = deviceInfo.struDeviceV30.byStartDChan;
            logger.info("预览起始通道号: {}", startDChan);
        }
        // 返回登录结果
        return userID;
    }

    /**
     * 海康设备初始化
     */
    public void init() {
        if (hCNetSDK == null) {
            if (!createSDKInstance()) {
                logger.error("Load SDK fail");
                return;
            }
        }
        // linux系统建议调用以下接口加载组件库
        if (CommonUtil.isLinux()) {
            HCNetSDK.BYTE_ARRAY ptrByteArray1 = new HCNetSDK.BYTE_ARRAY(256);
            HCNetSDK.BYTE_ARRAY ptrByteArray2 = new HCNetSDK.BYTE_ARRAY(256);
            // 这里是库的绝对路径，请根据实际情况修改，注意改路径必须有访问权限
            String strPath1 = HikConstant.LIB_PATH_PREFIX + "/lib/libcrypto.so.1.1";
            String strPath2 = HikConstant.LIB_PATH_PREFIX + "/lib/libssl.so.1.1";
            System.arraycopy(strPath1.getBytes(), 0, ptrByteArray1.byValue, 0, strPath1.length());
            ptrByteArray1.write();
            hCNetSDK.NET_DVR_SetSDKInitCfg(HCNetSDK.NET_SDK_INIT_CFG_LIBEAY_PATH, ptrByteArray1.getPointer());
            System.arraycopy(strPath2.getBytes(), 0, ptrByteArray2.byValue, 0, strPath2.length());
            ptrByteArray2.write();
            hCNetSDK.NET_DVR_SetSDKInitCfg(HCNetSDK.NET_SDK_INIT_CFG_SSLEAY_PATH, ptrByteArray2.getPointer());
            String strPathCom = HikConstant.LIB_PATH_PREFIX + "/lib/";
            HCNetSDK.NET_DVR_LOCAL_SDK_PATH struComPath = new HCNetSDK.NET_DVR_LOCAL_SDK_PATH();
            System.arraycopy(strPathCom.getBytes(), 0, struComPath.sPath, 0, strPathCom.length());
            struComPath.write();
            hCNetSDK.NET_DVR_SetSDKInitCfg(HCNetSDK.NET_SDK_INIT_CFG_SDK_PATH, struComPath.getPointer());
        }
        // 初始化
        hCNetSDK.NET_DVR_Init();
        logger.info("SDK初始化成功");

        // 加载日志
        hCNetSDK.NET_DVR_SetLogToFile(3, "./sdklog", false);

        // 设置报警回调函数
        if (fMSFCallBack_V31 == null) {
            fMSFCallBack_V31 = new FMSGCallBack_V31();
            if (!hCNetSDK.NET_DVR_SetDVRMessageCallBack_V31(fMSFCallBack_V31, null)) {
                logger.error("设置回调函数失败!");
                return;
            } else {
                logger.info("设置回调函数成功!");
            }
        }

        //设备登录
        lUserID = loginDevice(HikConstant.DEVICE_IP, HikConstant.DEVICE_PORT, HikConstant.DEVICE_USERNAME, HikConstant.DEVICE_PASSWORD); //登录设备
    }

    /**
     * 海康设备注销
     */
    public void logout() {
        logger.info("开始注销设备");
        // 注销登录，程序退出时调用；程序同时对接多台设备时，每一台设备分别调用一次接口，传入不同的登录句柄
        if (lUserID >= 0) {
            hCNetSDK.NET_DVR_Logout(lUserID);
            logger.info("注销成功");
        } else {
            logger.info("用户句柄为空");
        }

        // SDK反初始化，释放资源，程序退出时调用
        hCNetSDK.NET_DVR_Cleanup();
        logger.info("SDK反初始化成功");
    }


    /**
     * 报警布防
     *
     * @param userID 设备登录句柄ID
     * @return 出现错误时返回-1，其他情况返回布防句柄
     */
    public int setAlarmChan(int userID) {
        if (userID == -1) {
            logger.info("请先注册");
            return -1;
        }
        // 尚未布防,需要布防
        if (lAlarmHandle < 0) {
            // 报警布防参数设置
            HCNetSDK.NET_DVR_SETUPALARM_PARAM alarmInfo = new HCNetSDK.NET_DVR_SETUPALARM_PARAM();
            alarmInfo.dwSize = alarmInfo.size();
            // 布防等级
            alarmInfo.byLevel = 0;
            // 智能交通报警信息上传类型：0- 老报警信息（NET_DVR_PLATE_RESULT），1- 新报警信息(NET_ITS_PLATE_RESULT)
            alarmInfo.byAlarmInfoType = 1;
            // 布防类型：0-客户端布防，1-实时布防，客户端布防仅支持一路
            alarmInfo.byDeployType = 1;
            alarmInfo.write();
            lAlarmHandle = hCNetSDK.NET_DVR_SetupAlarmChan_V41(userID, alarmInfo);
            if (lAlarmHandle == -1) {
                logger.error("布防失败，错误码为{}", hCNetSDK.NET_DVR_GetLastError());
                return -1;
            } else {
                logger.info("布防成功");
                return lAlarmHandle;
            }
        } else {
            logger.info("设备已经布防，请先撤防！");
            return lAlarmHandle;
        }
    }

    /**
     * 设备撤防
     *
     * @param AlarmHandle 布防句柄
     */
    public void closedAlarmChan(int AlarmHandle) {
        logger.info("开始撤防");
        if (AlarmHandle <= -1) {
            logger.info("设备未布防！");
            return;
        }
        if (!hCNetSDK.NET_DVR_CloseAlarmChan(AlarmHandle)) {
            logger.error("撤防失败，err {}", hCNetSDK.NET_DVR_GetLastError());
            return;
        }
        logger.info("撤防成功");
    }
}
