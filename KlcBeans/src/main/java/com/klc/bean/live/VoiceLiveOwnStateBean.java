package com.klc.bean.live;

public class VoiceLiveOwnStateBean {
    public static final int Anchor = 1;//主播
    public static final int Administrators = 2;//副播(在麦上)
    public static final int Audience = 3;//观众
    //是否在麦上
    public static boolean IsMike = false;

    //语音房间自己的身份  1 主播 2 管理员 3观众
    public static int OwnIdentity;

    //自己麦序
    public static int MICID;

    //是否闭麦
    public static boolean PROHIBIT = false;

    //开闭麦状态 0关麦1开麦
    public static int MikeState = 1;

    //语音身份

    public static int mSocketUserType = 30;//socket用户类型  30 普通用户  40 管理员  50 主播  60超管

    public static String SMALL_TAB = "VOICESMALLWINDOW";

    public static String CLOSEROOM = "closeroom";


    //pk类型
    public static String VoicePKType ;

    //个人pk
    public static String AlonePK = "Alone";
    //激情pk
    public static String TeamPK = "Team";
    //团队pk
    public static String RoomPK = "Room";


    //房间pk胜利标记
    public static String RoomPKWin ;

    public static String RED = "Red" ;

    public static String BLUE = "Blue" ;


    //判断自己是否被自己静音了
    public static boolean IsMute = false;

    //判断是否打开3t云视频 false 打开 true 关闭
    public static boolean IsOpen3T = false;
}
