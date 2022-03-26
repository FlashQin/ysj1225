package com.kalacheng.onevoicelive.component;


import com.kalacheng.livecommon.component.LiveMessageComponent;
import com.kalacheng.livecommon.component.LiveMusicComponent;
import com.kalacheng.commonview.component.FloatingScreenDialogComponent;
import com.kalacheng.commonview.component.LiveGiftComponent;

public class ComponentConfig {
    /*正常语音和视频*/
    public static Class[] ONEVOICELIVECOMPONENT1 = {OneVoiceLiveComponent.class};
    public static Class[] ONEVOICELIVECOMPONENT2 = {OneVoiceInformationComponent.class, OneVoiceBottomComponent.class, LiveMessageComponent.class, LiveMusicComponent.class, OneVoiceLaunchComponent.class};
    public static Class[] ONEVOICELIVECOMPONENT3 = {OneVoiceViewComponet.class};
    public static Class[] ONEVOICELIVECOMPONENT4 = {LiveGiftComponent.class, OneVoiceDialogComponent.class};
    public static Class[] ONEVOICELIVECOMPONENT5 = {FloatingScreenDialogComponent.class};

    /*求聊用户*/
    public static Class[] One2OneSeekChatANCHORCOMPONENT1 = {OneVoiceLiveComponent.class};
    public static Class[] One2OneSeekChatANCHORCOMPONENT2 = {OneVoiceInformationComponent.class, LiveMessageComponent.class, LiveMusicComponent.class, OneVoiceBottomComponent.class, One2OneVoiceSeekChatLaunchComponent.class};
    public static Class[] One2OneSeekChatANCHORCOMPONENT3 = {OneVoiceViewComponet.class};
    public static Class[] One2OneSeekChatANCHORCOMPONENT4 = {LiveGiftComponent.class, OneVoiceDialogComponent.class};
    public static Class[] One2OneSeekChatANCHORCOMPONENT5 = {FloatingScreenDialogComponent.class};

    /*求聊主播加入*/
    public static Class[] One2OneSeekChatCOMPONENT1 = {OneVoiceLiveComponent.class};
    public static Class[] One2OneSeekChatCOMPONENT2 = {OneVoiceInformationComponent.class, LiveMessageComponent.class, LiveMusicComponent.class, OneVoiceBottomComponent.class};
    public static Class[] One2OneSeekChatCOMPONENT3 = {OneVoiceViewComponet.class};
    public static Class[] One2OneSeekChatCOMPONENT4 = {LiveGiftComponent.class, OneVoiceDialogComponent.class};
    public static Class[] One2OneSeekChatCOMPONENT5 = {FloatingScreenDialogComponent.class};


}
