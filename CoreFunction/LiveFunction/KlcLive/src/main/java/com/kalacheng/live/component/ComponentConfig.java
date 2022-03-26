package com.kalacheng.live.component;

import com.kalacheng.live.component.componentlive.AnchorInteractiveComponent;
import com.kalacheng.live.component.componentlive.AudienceVideoComponent;
import com.kalacheng.live.component.componentlive.LiveAnchorVideoComponent;
import com.kalacheng.live.component.componentlive.LiveDialogFragmentComponent;
import com.kalacheng.live.component.componentlive.LiveInfoComponent;
import com.kalacheng.live.component.componentlive.LiveMicComponent;
import com.kalacheng.live.component.componentlive.LivePkComponent;
import com.kalacheng.live.component.componentlive.MoveViewComponent;
import com.kalacheng.live.component.componentlive.OpenLivePreComponent;
import com.kalacheng.util.bean.SimpleImageSrcTextBean;
import com.kalacheng.util.utils.WordUtil;

import com.kalacheng.live.R;

import com.kalacheng.commonview.component.FloatingScreenDialogComponent;
import com.kalacheng.livecommon.component.LiveMessageComponent;
import com.kalacheng.livecommon.component.LiveMusicComponent;
import com.kalacheng.commonview.component.LiveGiftComponent;

import java.util.ArrayList;
import java.util.List;

public class ComponentConfig {
    /**
     * 直播分为五层
     */
    // 主播
    public static java.lang.Class[] LIVECOMPONENT1 = {LiveAnchorVideoComponent.class};
    public static java.lang.Class[] LIVECOMPONENT2 = {LiveInfoComponent.class, LivePkComponent.class, LiveMessageComponent.class, LiveMusicComponent.class, OpenLivePreComponent.class};
    public static java.lang.Class[] LIVECOMPONENT4 = {LiveGiftComponent.class, AnchorInteractiveComponent.class, LiveMicComponent.class, LiveDialogFragmentComponent.class};
    public static java.lang.Class[] LIVECOMPONENT5 = {MoveViewComponent.class, FloatingScreenDialogComponent.class};

    // 观众
    public static java.lang.Class[] AUDIENCECOMPONENT1 = {AudienceVideoComponent.class};
    public static java.lang.Class[] AUDIENCECOMPONENT2 = {LiveInfoComponent.class, LivePkComponent.class, LiveMessageComponent.class};
    public static java.lang.Class[] AUDIENCECOMPONENT4 = {LiveGiftComponent.class, AnchorInteractiveComponent.class, LiveMicComponent.class, LiveDialogFragmentComponent.class};
    public static java.lang.Class[] AUDIENCECOMPONENT5 ={FloatingScreenDialogComponent.class};
    public static java.lang.Class[] AUDIENCECOMPONENT6 ={MoveViewComponent.class};

    public final static List<SimpleImageSrcTextBean> FUNCTIONMENU = new ArrayList<>();

    static {
        FUNCTIONMENU.add(new SimpleImageSrcTextBean(R.mipmap.icon_live_func_beauty, WordUtil.getString(R.string.live_beauty)));
        FUNCTIONMENU.add(new SimpleImageSrcTextBean(R.mipmap.icon_live_func_camera, WordUtil.getString(R.string.live_camera)));
        FUNCTIONMENU.add(new SimpleImageSrcTextBean(R.mipmap.icon_live_func_flash, WordUtil.getString(R.string.live_flash)));
        FUNCTIONMENU.add(new SimpleImageSrcTextBean(R.mipmap.icon_live_func_music, WordUtil.getString(R.string.live_music)));
        FUNCTIONMENU.add(new SimpleImageSrcTextBean(R.mipmap.icon_live_func_game, WordUtil.getString(R.string.live_game)));
        FUNCTIONMENU.add(new SimpleImageSrcTextBean(R.mipmap.icon_live_func_rp, WordUtil.getString(R.string.live_red_pack)));
        FUNCTIONMENU.add(new SimpleImageSrcTextBean(R.mipmap.icon_live_func_lm, WordUtil.getString(R.string.live_link_mic)));
        FUNCTIONMENU.add(new SimpleImageSrcTextBean(R.mipmap.icon_live_func_share, WordUtil.getString(R.string.live_message)));
        FUNCTIONMENU.add(new SimpleImageSrcTextBean(R.mipmap.icon_live_func_wish, WordUtil.getString(R.string.live_wish)));
    }
}
